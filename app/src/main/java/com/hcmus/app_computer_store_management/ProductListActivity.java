package com.hcmus.app_computer_store_management;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private Button addProductButton, backButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        productRecyclerView = findViewById(R.id.productRecyclerView);
        addProductButton = findViewById(R.id.addProductButton);
        backButton = findViewById(R.id.backButton);
        dbHelper = new DatabaseHelper(this);

        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadProducts();

        addProductButton.setOnClickListener(v -> {
            startActivity(new Intent(ProductListActivity.this, AddProductActivity.class));
        });

        backButton.setOnClickListener(v -> finish());
    }

    private void loadProducts() {
        List<Product> productList = dbHelper.getAllProducts();
        if (productList.isEmpty()) {
            dbHelper.addProduct(new Product(0, "Laptop Dell", 1500.0, 1200.0));
            dbHelper.addProduct(new Product(0, "Mouse Logitech", 25.0, 20.0));
            dbHelper.addProduct(new Product(0, "RAM 8GB", 60.0, 48.0));
            productList = dbHelper.getAllProducts();
        }

        productAdapter = new ProductAdapter(this, productList);
        productRecyclerView.setAdapter(productAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }
}