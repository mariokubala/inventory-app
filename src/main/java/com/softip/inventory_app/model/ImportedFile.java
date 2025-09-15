package com.softip.inventory_app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Tracks which files were already imported (filename must be unique).
 * We keep this so repeated imports of the same file name can be detected.
 */
@Entity
@Table(name = "imported_files")
public class ImportedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // original file name (unique)
    @Column(nullable = false, unique = true)
    private String filename;

    // timestamp when the import finished
    @Column(name = "imported_at", nullable = false)
    private LocalDateTime importedAt;

    public ImportedFile() {}

    public ImportedFile(String filename, LocalDateTime importedAt) {
        this.filename = filename;
        this.importedAt = importedAt;
    }

    public Long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public LocalDateTime getImportedAt() {
        return importedAt;
    }

    public void setImportedAt(LocalDateTime importedAt) {
        this.importedAt = importedAt;
    }
}
