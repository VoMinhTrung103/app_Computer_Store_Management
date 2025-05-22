package com.hcmus.app_computer_store_management.models;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    private int orderId;
    private int productId;
    private int quantity;
    private double unitPrice;
    private String productName;
    public String getProductName() {
    return productName;
}
public void setProductName(String productName) {
    this.productName = productName;
}

    public OrderDetail(int orderId, int productId, int quantity, double unitPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getOrderId() { return orderId; }
    public int getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
}