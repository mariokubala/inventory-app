package com.softip.inventory_app.controller;

import com.softip.inventory_app.service.ImportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ImportController {

    private final ImportService importService;

    public ImportController(ImportService importService) {
        this.importService = importService;
    }

    /**
     * Example call:
     *          POST http://localhost:8080/api/loadFromFile?path=C:\temp\inventura.csv
     * curl -X POST "http://localhost:8080/api/loadFromFile?path=D:/data/inventura.csv"
     * The path must be accessible from the machine where the Spring Boot app runs.
     */
    @PostMapping("/loadFromFile")
    public ResponseEntity<String> loadFromFile(@RequestParam("path") String path) {
        try {
            importService.loadFromFile(path);
            return ResponseEntity.ok("Import finished (check err.log for invalid rows)");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("I/O error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Import error: " + e.getMessage());
        }
    }
}
