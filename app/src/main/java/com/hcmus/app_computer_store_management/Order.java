package com.hcmus.app_computer_store_management;

import java.io.Serializable;

public class Order implements Serializable {
    private int id;
    private String date;
    private int customerId;
    private double totalAmount;

    public Order(int id, String date, int customerId, double totalAmount) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
    }

    public int getId() { return id; }
    public String getDate() { return date; }
    public int getCustomerId() { return customerId; }
    public double getTotalAmount() { return totalAmount; }
}