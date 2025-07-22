package com.example.demo.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByCategoryIgnoreCase(String category);
    List<Item> findByItemNameContainingIgnoreCase(String name);
}