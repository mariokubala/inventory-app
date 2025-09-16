package com.softip.inventory_app.controller;

import com.softip.inventory_app.repository.RoomRepository;
import com.softip.inventory_app.model.Room;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RoomController {

    private final RoomRepository roomRepo;

    public RoomController(RoomRepository roomRepo) {
        this.roomRepo = roomRepo;
    }

    // getRooms - returns unique list of rooms as JSON array of strings
    @GetMapping("/rooms")
    public List<String> getRooms() {
        return roomRepo.findAll()
                .stream()
                .map(Room::getName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
