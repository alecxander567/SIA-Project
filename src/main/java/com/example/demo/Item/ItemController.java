package com.example.demo.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*") // allow frontend access (can be restricted later)
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<Item> getAllItems(@RequestParam(required = false) String category) {
        if (category != null && !category.equalsIgnoreCase("All Categories")) {
            return itemService.getItemsByCategory(category);
        } else {
            return itemService.getAllItems();
        }
    }

    @GetMapping("/{id}")
    public Optional<Item> getItemById(@PathVariable Integer id) {
        return itemService.getItemById(id);
    }

    @PostMapping(consumes = "multipart/form-data")
    public Item addItem(
            @RequestParam("itemName") String itemName,
            @RequestParam("quantity") int quantity,
            @RequestParam("category") String category,
            @RequestParam("price") double price,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {

        Item item = new Item();
        item.setItemName(itemName);
        item.setQuantity(quantity);
        item.setCategory(category);
        item.setPrice(BigDecimal.valueOf(price));

        if (image != null && !image.isEmpty()) {
            String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path imagePath = Paths.get("uploads", imageName);
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, image.getBytes());

            item.setImagePath(imagePath.toString());
        }

        return itemService.addItem(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(
            @PathVariable Integer id,
            @ModelAttribute Item item,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        return ResponseEntity.ok(itemService.updateItem(id, item, imageFile));
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Integer id) {
        itemService.deleteItem(id);
    }

    @GetMapping("/search")
    public List<Item> searchItemsByName(@RequestParam String name) {
        return itemService.searchItemsByName(name);
    }
}