package com.hcmus.app_computer_store_management;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "computer_store.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT UNIQUE, password TEXT, role TEXT)");
        db.execSQL("CREATE TABLE Product (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, sellingPrice REAL, importPrice REAL, stock INTEGER, type TEXT)");
        db.execSQL("CREATE TABLE Customer (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT, address TEXT)");
        db.execSQL("CREATE TABLE 'Order' (id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, customerId INTEGER, totalAmount REAL, FOREIGN KEY(customerId) REFERENCES Customer(id))");
        db.execSQL("CREATE TABLE OrderDetail (orderId INTEGER, productId INTEGER, quantity INTEGER, unitPrice REAL, PRIMARY KEY(orderId, productId), FOREIGN KEY(orderId) REFERENCES 'Order'(id), FOREIGN KEY(productId) REFERENCES Product(id))");
        db.execSQL("CREATE TABLE Supplier (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT, address TEXT)");

        // Sample data
        db.execSQL("INSERT INTO User (name, email, password, role) VALUES ('Admin', 'admin@shop.com', 'admin123', 'Admin')");
        db.execSQL("INSERT INTO User (name, email, password, role) VALUES ('Nhan Vien 1', 'nv1@shop.com', 'nv123', 'Employee')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Product");
        db.execSQL("DROP TABLE IF EXISTS Customer");
        db.execSQL("DROP TABLE IF EXISTS 'Order'");
        db.execSQL("DROP TABLE IF EXISTS OrderDetail");
        db.execSQL("DROP TABLE IF EXISTS Supplier");
        onCreate(db);
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE email = ? AND password = ?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public String getUserRole(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT role FROM User WHERE email = ?", new String[]{email});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String role = cursor.getString(cursor.getColumnIndex("role"));
            cursor.close();
            return role;
        }
        cursor.close();
        return null;
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("sellingPrice", product.getSellingPrice());
        values.put("importPrice", product.getImportPrice());
        values.put("stock", product.getStock());
        values.put("type", product.getType());
        db.insert("Product", null, values);
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Product", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Product product = new Product(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("sellingPrice")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("importPrice"))
                );
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                product.setStock(cursor.getInt(cursor.getColumnIndexOrThrow("stock")));
                product.setType(cursor.getString(cursor.getColumnIndexOrThrow("type")));
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productList;
    }

    public void deleteProduct(int productId) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete("OrderDetail", "productId = ?", new String[]{String.valueOf(productId)});
            db.delete("Product", "id = ?", new String[]{String.valueOf(productId)});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error deleting product: " + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public void deleteProducts(List<Integer> productIds) {
        if (productIds.isEmpty()) {
            Log.d("DatabaseHelper", "No product IDs provided for deletion");
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            StringBuilder placeholders = new StringBuilder();
            String[] args = new String[productIds.size()];
            for (int i = 0; i < productIds.size(); i++) {
                placeholders.append(i == 0 ? "?" : ",?");
                args[i] = String.valueOf(productIds.get(i));
            }
            db.delete("OrderDetail", "productId IN (" + placeholders.toString() + ")", args);
            int deletedRows = db.delete("Product", "id IN (" + placeholders.toString() + ")", args);
            Log.d("DatabaseHelper", "Deleted " + deletedRows + " products, IDs: " + productIds);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error deleting products: " + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public void deleteAllProducts() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("DELETE FROM OrderDetail");
            db.execSQL("DELETE FROM Product");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error deleting all products: " + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }
}