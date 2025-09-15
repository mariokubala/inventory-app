package com.softip.inventory_app.repository;

import com.softip.inventory_app.model.Item;
import com.softip.inventory_app.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByStateOrderByPriceDesc(State state);
    List<Item> findByOutDateNotNullOrderByPriceDesc();
}
