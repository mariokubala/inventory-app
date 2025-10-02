package com.softip.inventory_app.service;

import com.softip.inventory_app.model.Item;
import com.softip.inventory_app.model.State;
import com.softip.inventory_app.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getItemsByState(State state) {
        return itemRepository.findByStateOrderByPriceDesc(state);
    }

    public List<Item> getRemovedItems() {
        return itemRepository.findByOutDateNotNullOrderByPriceDesc();
    }

    // DEBUG: return all items
//    public List<Item> getAllItems() {
//        return itemRepository.findAll();
//    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
