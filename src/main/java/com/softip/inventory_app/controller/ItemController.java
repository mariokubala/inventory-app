package com.softip.inventory_app.controller;

import com.softip.inventory_app.model.Item;
import com.softip.inventory_app.model.State;
import com.softip.inventory_app.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/state/{state}")
    public List<Item> getByState(@PathVariable String state) {
        // --- normalize parameter ---
        if (state == null || state.trim().isEmpty()) {
            throw new IllegalArgumentException("state must be provided");
        }

        String s = state.trim().toUpperCase();

        // --- handle "removed" (items with OUT_DATE != null) ---
        if ("REMOVED".equals(s) || "REM".equals(s)) {
            // <- this calls your existing service for removed items
            return itemService.getRemovedItems();
        }

        // --- map human-friendly names to enum codes ---
        if ("OK".equals(s)) s = "O";
        if ("MISSING".equals(s)) s = "M";
        if ("MOVED".equals(s)) s = "V";

        // --- try to convert to State enum ---
        try {
            State st = State.valueOf(s);   // valid values: O, M, V
            return itemService.getItemsByState(st);
        } catch (IllegalArgumentException ex) {
            // nice message instead of 500 error
            throw new IllegalArgumentException("Unknown state: " + state);
        }
    }
}
