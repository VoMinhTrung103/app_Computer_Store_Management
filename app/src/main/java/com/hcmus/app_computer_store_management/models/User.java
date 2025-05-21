package com.hcmus.app_computer_store_management.models;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String role; // "Admin" or "Employee"

    public User(int id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}