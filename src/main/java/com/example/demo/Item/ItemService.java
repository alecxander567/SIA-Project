package com.example.demo.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(Integer id) {
        return itemRepository.findById(id);
    }

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Integer id, Item updatedItem, MultipartFile imageFile) {
        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with ID: " + id));

        existingItem.setItemName(updatedItem.getItemName());
        existingItem.setCategory(updatedItem.getCategory());
        existingItem.setPrice(updatedItem.getPrice());
        existingItem.setQuantity(updatedItem.getQuantity());

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = imageFile.getOriginalFilename();
                Path path = Paths.get("uploads", fileName);
                Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                existingItem.setImagePath("uploads/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save image", e);
            }
        }

        return itemRepository.save(existingItem);
    }

    public void deleteItem(Integer id) {
        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("Item not found with ID: " + id);
        }
        itemRepository.deleteById(id);
    }
}