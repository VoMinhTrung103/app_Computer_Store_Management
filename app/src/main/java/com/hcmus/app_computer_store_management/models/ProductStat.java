package com.hcmus.app_computer_store_management.models;

public class ProductStat {
    private int productId;
    private String productName;
    private int totalSold;

    public ProductStat(int productId, String productName, int totalSold) {
        this.productId = productId;
        this.productName = productName;
        this.totalSold = totalSold;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getTotalSold() {
        return totalSold;
    }
}