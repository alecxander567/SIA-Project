package com.example.demo.Item;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tblitems")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Item_ID")
    private Integer id;

    @Column(name = "itemName", nullable = false, length = 50)
    private String itemName;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "imagePath", nullable = true)
    private String imagePath;

    // Constructors
    public Item() {
    }

    public Item(String itemName, int quantity, String category, BigDecimal price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.category = category;
        this.price = price;

    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
