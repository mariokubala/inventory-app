package com.softip.inventory_app.repository;

import com.softip.inventory_app.model.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetTypeRepository extends JpaRepository<AssetType, Long> {
    Optional<AssetType> findByCode(Integer code);
}
