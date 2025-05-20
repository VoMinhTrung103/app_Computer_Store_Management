package com.hcmus.app_computer_store_management;

public class RevenueStat {
    private String month;
    private double revenue;

    public RevenueStat(String month, double revenue) {
        this.month = month;
        this.revenue = revenue;
    }

    public String getMonth() {
        return month;
    }

    public double getRevenue() {
        return revenue;
    }
}