package com.hcmus.app_computer_store_management;

public class InventoryStat {
    private int productId;
    private String productName;
    private int stock;

    public InventoryStat(int productId, String productName, int stock) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getStock() {
        return stock;
    }
}