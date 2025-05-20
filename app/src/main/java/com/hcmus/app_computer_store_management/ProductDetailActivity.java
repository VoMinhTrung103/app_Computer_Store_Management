package com.hcmus.app_computer_store_management;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {
    private EditText productName, productDescription, productPrice, productImportPrice, productStock, productType;
    private Button backButton, editButton, saveButton;
    private Product product;
    private DatabaseHelper dbHelper;
    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productPrice = findViewById(R.id.productPrice);
        productImportPrice = findViewById(R.id.productImportPrice);
        productStock = findViewById(R.id.productStock);
        productType = findViewById(R.id.productType);
        backButton = findViewById(R.id.backButton);
        editButton = findViewById(R.id.editButton);
        saveButton = findViewById(R.id.saveButton);

        dbHelper = new DatabaseHelper(this);

        product = (Product) getIntent().getSerializableExtra("PRODUCT");
        if (product != null) {
            loadProductDetails();
        }

        setupButtonListeners();
    }

    private void loadProductDetails() {
        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productPrice.setText(Utils.formatCurrency(product.getSellingPrice()));
        productImportPrice.setText(Utils.formatCurrency(product.getImportPrice()));
        productStock.setText(String.valueOf(product.getStock()));
        productType.setText(product.getType());
    }

    private void setupButtonListeners() {
        editButton.setOnClickListener(v -> {
            if (!isEditing) {
                enableEditing(true);
                // Khi chuyển sang chế độ edit, hiển thị giá dạng số không có định dạng
                productPrice.setText(String.valueOf(product.getSellingPrice()));
                productImportPrice.setText(String.valueOf(product.getImportPrice()));
            }
        });

        saveButton.setOnClickListener(v -> {
            if (isEditing && product != null) {
                try {
                    updateProductFromInputs();
                    dbHelper.updateProduct(product);
                    enableEditing(false);
                    loadProductDetails(); // Reload để hiển thị lại giá đã định dạng
                    Toast.makeText(this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Vui lòng nhập số hợp lệ cho giá và tồn kho", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(v -> finish());
    }

    private void updateProductFromInputs() {
        product.setName(productName.getText().toString());
        product.setDescription(productDescription.getText().toString());
        product.setSellingPrice(Double.parseDouble(productPrice.getText().toString().replaceAll("[^\\d.]", "")));
        product.setImportPrice(Double.parseDouble(productImportPrice.getText().toString().replaceAll("[^\\d.]", "")));
        product.setStock(Integer.parseInt(productStock.getText().toString()));
        product.setType(productType.getText().toString());
    }

    private void enableEditing(boolean enable) {
        isEditing = enable;
        productName.setEnabled(enable);
        productDescription.setEnabled(enable);
        productPrice.setEnabled(enable);
        productImportPrice.setEnabled(enable);
        productStock.setEnabled(enable);
        productType.setEnabled(enable);

        editButton.setVisibility(enable ? View.GONE : View.VISIBLE);
        saveButton.setVisibility(enable ? View.VISIBLE : View.GONE);
    }
}