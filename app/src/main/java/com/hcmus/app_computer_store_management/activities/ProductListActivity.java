package com.hcmus.app_computer_store_management.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hcmus.app_computer_store_management.DatabaseHelper;
import com.hcmus.app_computer_store_management.models.Product;
import com.hcmus.app_computer_store_management.adapters.ProductAdapter;
import com.hcmus.app_computer_store_management.R;
import com.hcmus.app_computer_store_management.utils.SampleDataGenerator;
import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ProductAdapter.OnSelectionChangedListener {
    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private Button addProductButton, backButton, deleteProductButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        productRecyclerView = findViewById(R.id.productRecyclerView);
        addProductButton = findViewById(R.id.addProductButton);
        backButton = findViewById(R.id.backButton);
        deleteProductButton = findViewById(R.id.deleteProductButton);
        dbHelper = new DatabaseHelper(this);

        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        insertSampleDataIfEmpty(); // Kiểm tra và chèn dữ liệu mẫu
        loadProducts();

        addProductButton.setOnClickListener(v -> {
            startActivity(new Intent(ProductListActivity.this, AddProductActivity.class));
        });

        backButton.setOnClickListener(v -> finish());

        // Initially disable delete button
        deleteProductButton.setEnabled(false);

        // Handle global delete button with confirmation dialog
        deleteProductButton.setOnClickListener(v -> {
            List<Integer> selectedIds = productAdapter.getSelectedProductIds();
            Log.d("ProductListActivity", "Deleting IDs: " + selectedIds);
            if (!selectedIds.isEmpty()) {
                new AlertDialog.Builder(this)
                        .setTitle("Confirm Deletion")
                        .setMessage("Are you sure you want to delete " + selectedIds.size() + " product(s)?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            dbHelper.deleteProducts(selectedIds);
                            productAdapter.clearSelections();
                            loadProducts();
                            deleteProductButton.setEnabled(false);
                        })
                        .setNegativeButton("No", null)
                        .show();
            } else {
                Log.d("ProductListActivity", "No products selected for deletion");
            }
        });
    }

    private void insertSampleDataIfEmpty() {
        List<Product> productList = dbHelper.getAllProducts();
        if (productList.isEmpty()) {
            for (Product product : SampleDataGenerator.getSampleProducts()) {
                dbHelper.addProduct(product);
            }
        }
    }

    private void loadProducts() {
        List<Product> productList = dbHelper.getAllProducts();
        productAdapter = new ProductAdapter(this, productList, this, false); // Pass false for showQuantityInput
        productRecyclerView.setAdapter(productAdapter);
        deleteProductButton.setEnabled(!productAdapter.getSelectedProductIds().isEmpty());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }

    @Override
    public void onSelectionChanged(boolean hasSelections) {
        Log.d("ProductListActivity", "Selection changed, hasSelections: " + hasSelections);
        deleteProductButton.setEnabled(hasSelections);
    }
}