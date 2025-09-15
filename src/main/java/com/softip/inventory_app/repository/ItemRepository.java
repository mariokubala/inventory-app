package com.softip.inventory_app.repository;

import com.softip.inventory_app.model.Item;
import com.softip.inventory_app.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByStateOrderByPriceDesc(State state);
    List<Item> findByOutDateNotNullOrderByPriceDesc();
}
