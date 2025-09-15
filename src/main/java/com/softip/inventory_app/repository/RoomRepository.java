package com.softip.inventory_app.repository;

import com.softip.inventory_app.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByName(String name);
}
