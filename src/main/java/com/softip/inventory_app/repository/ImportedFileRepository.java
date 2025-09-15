package com.softip.inventory_app.repository;

import com.softip.inventory_app.model.ImportedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportedFileRepository extends JpaRepository<ImportedFile, Long> {
    // used to check if a file with the same name was already imported
    boolean existsByFilename(String filename);
}
