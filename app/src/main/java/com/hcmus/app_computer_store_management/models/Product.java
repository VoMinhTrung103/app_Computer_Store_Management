package com.hcmus.app_computer_store_management.models;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String description;
    private double sellingPrice;
    private double importPrice;
    private int stock;
    private String type;

    public Product(int id, String name, double sellingPrice, double importPrice) {
        this.id = id;
        this.name = name;
        this.description = "";
        this.sellingPrice = sellingPrice;
        this.importPrice = importPrice;
        this.stock = 0;
        this.type = "";
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getSellingPrice() { return sellingPrice; }
    public double getImportPrice() { return importPrice; }
    public int getStock() { return stock; }
    public String getType() { return type; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }
    public void setImportPrice(double importPrice) { this.importPrice = importPrice; }
    public void setStock(int stock) { this.stock = stock; }
    public void setType(String type) { this.type = type; }
}