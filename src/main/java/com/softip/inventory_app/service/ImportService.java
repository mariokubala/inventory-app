package com.softip.inventory_app.service;

import com.softip.inventory_app.model.ImportedFile;
import com.softip.inventory_app.model.Item;
import com.softip.inventory_app.model.Room;
import com.softip.inventory_app.model.AssetType;
import com.softip.inventory_app.model.State;

import com.softip.inventory_app.repository.ImportedFileRepository;
import com.softip.inventory_app.repository.ItemRepository;
import com.softip.inventory_app.repository.RoomRepository;
import com.softip.inventory_app.repository.AssetTypeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static javax.print.attribute.standard.MediaPrintableArea.MM;

/**
 * Service for importing CSV inventory files.
 * - Accepts a path to a CSV file (semicolon separated)
 * - Checks if file was already imported (by file name)
 * - Parses each line, validates lookup values
 * - Stores valid items to DB
 * - Writes invalid lines to err.log (with error message)
 * - Registers the imported file in imported_files table
 */
@Service
public class ImportService {

    private final Logger logger = LoggerFactory.getLogger(ImportService.class);

    private final ImportedFileRepository importedFileRepo;
    private final ItemRepository itemRepo;
    private final RoomRepository roomRepo;
    private final AssetTypeRepository assetTypeRepo;

    // Use yyyyMMdd for input dates like 20100101
    private final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyyMMdd");

    public ImportService(ImportedFileRepository importedFileRepo,
                         ItemRepository itemRepo,
                         RoomRepository roomRepo,
                         AssetTypeRepository assetTypeRepo) {
        this.importedFileRepo = importedFileRepo;
        this.itemRepo = itemRepo;
        this.roomRepo = roomRepo;
        this.assetTypeRepo = assetTypeRepo;
    }

    /**
     * Load CSV from a local path and import it.
     * @param pathString absolute or relative path to CSV (UTF-8, semicolon separated)
     * @throws IOException when file cannot be read
     */
    @Transactional
    public void loadFromFile(String pathString) throws IOException {
        Path csvPath = Paths.get(pathString);
        if (!Files.exists(csvPath)) {
            throw new IOException("File does not exist: " + csvPath.toAbsolutePath());
        }

        String filename = csvPath.getFileName().toString();

        // 1) Check repeated import - write to log error about repeated import
        if (importedFileRepo.existsByFilename(filename)) {
            String msg = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"))
                    + " Pokus o opakovaný import súboru: " + filename;
            logger.error(msg);

            // append to logs/err.log so it's visible outside IDE too
            try {
                Path logDir = Paths.get("logs");
                if (!Files.exists(logDir)) {
                    Files.createDirectories(logDir);
                }
                Path errFile = logDir.resolve("err.log");
                Files.writeString(errFile, msg + System.lineSeparator(),
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (Exception e) {
                logger.warn("Failed to write to logs/err.log: " + e.getMessage());
            }
            return; // do not re-import
        }

        // Prepare err.log (append mode). - in logs/err.log
        Path errLogPath = Paths.get("logs/err.log");
        Files.createDirectories(errLogPath.getParent() == null ? Paths.get(".") : errLogPath.getParent());

        // 2) Read CSV, parse lines
        try (BufferedReader br = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8);
             BufferedWriter errWriter = Files.newBufferedWriter(errLogPath, StandardCharsets.UTF_8,
                     StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

            // read header
            String header = br.readLine(); // skip header line
            int lineNo = 1;
            String line;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) continue; // skip empty lines
                try {
                    // Split using semicolon (CSV uses ';', but in the assignment you want comma separated values)
                    // - the CVS files has numbers as prices with comma, so we can't use comma as separator
                    String[] cols = line.split(";", -1); // -1 preserves empty trailing columns
                    if (cols.length < 8) {
                        throw new IllegalArgumentException("Wrong column count (expected 8), got " + cols.length);
                    }

                    // Parse fields
                    long csvAssetId = Long.parseLong(cols[0].trim());         // ID
                    String name = cols[1].trim();                             // NAME
                    String roomName = cols[2].trim();                         // ROOM (maybe empty)
                    int typeCode = Integer.parseInt(cols[3].trim());          // TYPE 0/1
//                    int typeCode = Integer.valueOf(cols[3].trim());
                    String priceRaw = cols[4].trim();                         // PRICE e.g. "11299,15EUR"
                    String inDateRaw = cols[5].trim();                        // IN_DATE YYYYMMDD
                    String outDateRaw = cols[6].trim();                       // OUT_DATE or empty
                    String stateRaw = cols[7].trim();                         // STATE char O/M/V

                    // PRICE: remove non-digit except comma/dot/minus, replace comma -> dot, parse BigDecimal
                    String cleanedPrice = priceRaw.replaceAll("[^0-9,.-]", "").replace(",", ".");
                    if (cleanedPrice.isEmpty()) throw new IllegalArgumentException("Empty price");
                    BigDecimal price = new BigDecimal(cleanedPrice);

                    // DATE parsing
                    LocalDate inDate = LocalDate.parse(inDateRaw, DATE_FMT);
                    LocalDate outDate = outDateRaw.isEmpty() ? null
                            : LocalDate.parse(outDateRaw, DATE_FMT);

                    // Validate TYPE exists
                    AssetType type = assetTypeRepo.findByCode(typeCode)
                            .orElseThrow(() -> new IllegalArgumentException("Unknown TYPE: " + typeCode));

                    // Validate STATE (enum) — use enum State with O/M/V
                    State state;
                    try {
                        state = State.valueOf(stateRaw.toUpperCase());
                    } catch (Exception ex) {
                        throw new IllegalArgumentException("Unknown STATE: " + stateRaw);
                    }

                    // Room: if missing name, we can store null; if name present, find or create room
                    Room room = null;
                    if (!roomName.isEmpty()) {
                        Optional<Room> roomOpt = roomRepo.findByName(roomName);
                        room = roomOpt.orElseGet(() -> roomRepo.save(new Room(roomName)));
                    }

                    // Create and save Item
                    Item item = new Item();
                    item.setAssetId(csvAssetId); // CSV's ID
                    item.setName(name);
                    item.setRoom(room);
                    item.setType(type);
                    item.setPrice(price);
                    item.setInDate(inDate);
                    item.setOutDate(outDate);
                    item.setState(state);

                    itemRepo.save(item);

                } catch (Exception ex) {
                    // On any parsing/validation error: write the original line + error message into err.log
                    String errLine = "Line " + lineNo + ": ERROR: " + ex.getMessage() + "  ||  " + line;
                    errWriter.write(errLine);
                    errWriter.newLine();
                    errWriter.flush();
                    // continue with next line
                }
            } // end while lines

            // 3) Mark the file as imported (save into imported_files table)
            ImportedFile importedFile = new ImportedFile();
            importedFile.setFilename(filename);
            importedFile.setImportedAt(LocalDateTime.now());
            importedFileRepo.save(importedFile);
            logger.info("Imported file saved: {}", filename);

        } // try-with-resources closes readers/writers
    }
}
