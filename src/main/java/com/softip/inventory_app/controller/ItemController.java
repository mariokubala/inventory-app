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
        if ("removed".equalsIgnoreCase(state)) {
            return itemService.getRemovedItems();
        }
        return itemService.getItemsByState(State.valueOf(state.toUpperCase()));
    }
}
