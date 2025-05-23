package com.hcmus.app_computer_store_management.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.hcmus.app_computer_store_management.DatabaseHelper;
import com.hcmus.app_computer_store_management.R;
import java.util.List;
import java.util.Arrays;

public class AdminMainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private TextView tvTotalCustomers, tvTotalProducts, tvTotalOrders, tvTotalInventory;
    private ListView lvLowStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        dbHelper = new DatabaseHelper(this);

        tvTotalCustomers = findViewById(R.id.tvTotalCustomers);
        tvTotalProducts = findViewById(R.id.tvTotalProducts);
        tvTotalOrders = findViewById(R.id.tvTotalOrders);
        tvTotalInventory = findViewById(R.id.tvTotalInventory);
        lvLowStock = findViewById(R.id.lvLowStock);

        // Update dashboard data (an toàn hơn)
        tvTotalCustomers.setText(getSafeTotalCustomers());
        tvTotalProducts.setText(getSafeTotalProducts());
        tvTotalOrders.setText(getSafeTotalOrders());
        tvTotalInventory.setText(getSafeTotalInventory());

        List<String> lowStockProducts = getSafeLowStockProducts();
        lvLowStock.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lowStockProducts));

        // Bottom navigation
        ImageButton statisticsButton = findViewById(R.id.statisticsButton);
        ImageButton productListButton = findViewById(R.id.productListButton);
        ImageButton logoutButton = findViewById(R.id.logoutButton);

        statisticsButton.setOnClickListener(v -> startActivity(new Intent(this, StatisticActivity.class)));
        productListButton.setOnClickListener(v -> startActivity(new Intent(this, ProductListActivity.class)));
        logoutButton.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    // Các hàm lấy tổng số liệu an toàn
    private String getSafeTotalCustomers() {
        try {
            return String.valueOf(dbHelper.getTotalCustomers());
        } catch (Exception e) {
            return "0";
        }
    }

    private String getSafeTotalProducts() {
        try {
            return String.valueOf(dbHelper.getTotalProducts());
        } catch (Exception e) {
            return "0";
        }
    }

    private String getSafeTotalOrders() {
        try {
            return String.valueOf(dbHelper.getTotalOrders());
        } catch (Exception e) {
            return "0";
        }
    }

    private String getSafeTotalInventory() {
        try {
            // Nếu DatabaseHelper chưa có hàm này, trả về 0 hoặc giá trị mẫu
            return String.valueOf(dbHelper.getTotalInventory());
        } catch (Exception e) {
            return "0";
        }
    }

    private List<String> getSafeLowStockProducts() {
        try {
            // Nếu DatabaseHelper chưa có hàm này, trả về danh sách mẫu
            return dbHelper.getLowStockProducts();
        } catch (Exception e) {
            return Arrays.asList("No data");
        }
    }
}