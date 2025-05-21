package com.hcmus.app_computer_store_management;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.hcmus.app_computer_store_management.models.InventoryStat;
import com.hcmus.app_computer_store_management.models.Order;
import com.hcmus.app_computer_store_management.models.OrderDetail;
import com.hcmus.app_computer_store_management.models.Product;
import com.hcmus.app_computer_store_management.models.ProductStat;
import com.hcmus.app_computer_store_management.models.RevenueStat;
import com.hcmus.app_computer_store_management.utils.SampleDataGenerator;
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

        // Sample data for User
        db.execSQL("INSERT INTO User (name, email, password, role) VALUES ('Admin', 'admin@shop.com', 'admin123', 'Admin')");
        db.execSQL("INSERT INTO User (name, email, password, role) VALUES ('Nhan Vien 1', 'nv1@shop.com', 'nv123', 'Employee')");

        // Sample data for Customer
        db.execSQL("INSERT INTO Customer (id, name, phone, address) VALUES (1, 'Customer 1', '0901234567', '123 Client St')");
        db.execSQL("INSERT INTO Customer (id, name, phone, address) VALUES (2, 'Customer 2', '0901234568', '456 Client St')");

        // Insert sample products
        insertSampleProducts(db);
    }

    private void insertSampleProducts(SQLiteDatabase db) {
        for (Product product : SampleDataGenerator.getSampleProducts()) {
            ContentValues values = new ContentValues();
            values.put("name", product.getName());
            values.put("description", product.getDescription());
            values.put("sellingPrice", product.getSellingPrice());
            values.put("importPrice", product.getImportPrice());
            values.put("stock", product.getStock());
            values.put("type", product.getType());
            db.insert("Product", null, values);
        }
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

    // Improved user authentication (consider adding password hashing in production)
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM User WHERE email = ? AND password = ?", new String[]{email, password});
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public String getUserRole(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT role FROM User WHERE email = ?", new String[]{email});
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String role = cursor.getString(cursor.getColumnIndexOrThrow("role"));
                return role;
            }
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    // Product management with transactions
    public void addProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("name", product.getName());
            values.put("description", product.getDescription());
            values.put("sellingPrice", product.getSellingPrice());
            values.put("importPrice", product.getImportPrice());
            values.put("stock", product.getStock());
            values.put("type", product.getType());
            db.insert("Product", null, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM Product", null);
            while (cursor.moveToNext()) {
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
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return productList;
    }

    public Product getProductById(int productId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query("Product", null, "id = ?", new String[]{String.valueOf(productId)}, null, null, null);
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") Product product = new Product(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("sellingPrice")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("importPrice"))
                );
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                product.setStock(cursor.getInt(cursor.getColumnIndexOrThrow("stock")));
                product.setType(cursor.getString(cursor.getColumnIndexOrThrow("type")));
                return product;
            }
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("name", product.getName());
            values.put("description", product.getDescription());
            values.put("sellingPrice", product.getSellingPrice());
            values.put("importPrice", product.getImportPrice());
            values.put("stock", product.getStock());
            values.put("type", product.getType());
            db.update("Product", values, "id = ?", new String[]{String.valueOf(product.getId())});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
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

    // Methods for SaleActivity and ClientOrderActivity
    public long createOrder(String customerId, String date, List<OrderDetailItem> items) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Insert Order
            ContentValues orderValues = new ContentValues();
            orderValues.put("customerId", customerId);
            orderValues.put("date", date);
            double totalAmount = 0;
            for (OrderDetailItem item : items) {
                totalAmount += item.quantity * item.unitPrice;
            }
            orderValues.put("totalAmount", totalAmount);
            long orderId = db.insert("'Order'", null, orderValues);

            // Insert OrderDetails and update stock
            for (OrderDetailItem item : items) {
                // Insert OrderDetail
                ContentValues detailValues = new ContentValues();
                detailValues.put("orderId", orderId);
                detailValues.put("productId", item.productId);
                detailValues.put("quantity", item.quantity);
                detailValues.put("unitPrice", item.unitPrice);
                db.insert("OrderDetail", null, detailValues);

                // Update stock
                Product product = getProductById(item.productId);
                if (product != null) {
                    int newStock = product.getStock() - item.quantity;
                    if (newStock < 0) {
                        throw new IllegalStateException("Not enough stock for product ID: " + item.productId);
                    }
                    ContentValues stockValues = new ContentValues();
                    stockValues.put("stock", newStock);
                    db.update("Product", stockValues, "id = ?", new String[]{String.valueOf(item.productId)});
                }
            }

            db.setTransactionSuccessful();
            return orderId;
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error creating order: " + e.getMessage());
            throw e;
        } finally {
            db.endTransaction();
        }
    }

    // Helper class for OrderDetail items
    public static class OrderDetailItem {
        public int productId;
        public int quantity;
        public double unitPrice;

        public OrderDetailItem(int productId, int quantity, double unitPrice) {
            this.productId = productId;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }
    }

    // Methods for OrderListActivity and OrderDetailActivity
    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM 'Order'", null);
            while (cursor.moveToNext()) {
                @SuppressLint("Range") Order order = new Order(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("customerId")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("totalAmount"))
                );
                orderList.add(order);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return orderList;
    }

    public List<OrderDetail> getOrderDetails(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query("OrderDetail", null, "orderId = ?", new String[]{String.valueOf(orderId)}, null, null, null);
            while (cursor.moveToNext()) {
                @SuppressLint("Range") OrderDetail orderDetail = new OrderDetail(
                        cursor.getInt(cursor.getColumnIndexOrThrow("orderId")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("productId")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("quantity")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("unitPrice"))
                );
                orderDetails.add(orderDetail);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return orderDetails;
    }

    // Statistics methods
    public List<RevenueStat> getMonthlyRevenue(String startDate, String endDate) {
        List<RevenueStat> stats = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT strftime('%Y-%m', o.date) as month, " +
                    "SUM(od.quantity * od.unitPrice) as revenue " +
                    "FROM 'Order' o JOIN OrderDetail od ON o.id = od.orderId ";
            if (startDate != null && endDate != null) {
                query += "WHERE o.date BETWEEN ? AND ? ";
            }
            query += "GROUP BY month ORDER BY month";

            cursor = startDate != null && endDate != null ?
                    db.rawQuery(query, new String[]{startDate, endDate}) :
                    db.rawQuery(query, null);

            while (cursor.moveToNext()) {
                stats.add(new RevenueStat(
                        cursor.getString(0),
                        cursor.getDouble(1)
                ));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return stats;
    }

    public List<ProductStat> getTopSellingProducts(int limit, String startDate, String endDate) {
        List<ProductStat> stats = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT p.id, p.name, SUM(od.quantity) as totalSold " +
                    "FROM Product p JOIN OrderDetail od ON p.id = od.productId " +
                    "JOIN 'Order' o ON od.orderId = o.id ";
            if (startDate != null && endDate != null) {
                query += "WHERE o.date BETWEEN ? AND ? ";
            }
            query += "GROUP BY p.id ORDER BY totalSold DESC LIMIT ?";

            cursor = startDate != null && endDate != null ?
                    db.rawQuery(query, new String[]{startDate, endDate, String.valueOf(limit)}) :
                    db.rawQuery(query, new String[]{String.valueOf(limit)});

            while (cursor.moveToNext()) {
                stats.add(new ProductStat(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2)
                ));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return stats;
    }

    public List<InventoryStat> getInventoryStatus() {
        List<InventoryStat> stats = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query("Product", new String[]{"id", "name", "stock"}, null, null, null, null, "stock ASC");
            while (cursor.moveToNext()) {
                stats.add(new InventoryStat(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2)
                ));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return stats;
    }
}