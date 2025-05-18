package com.hcmus.app_computer_store_management;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {
    private EditText productName, productDescription, productPrice, productStock;
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
        productStock = findViewById(R.id.productStock);
        backButton = findViewById(R.id.backButton);
        editButton = findViewById(R.id.editButton);
        saveButton = findViewById(R.id.saveButton);
        dbHelper = new DatabaseHelper(this);

        product = (Product) getIntent().getSerializableExtra("PRODUCT");
        if (product != null) {
            productName.setText(product.getName());
            productDescription.setText(product.getDescription());
            productPrice.setText(String.valueOf(product.getSellingPrice()));
            productStock.setText(String.valueOf(product.getStock()));
        }

        editButton.setOnClickListener(v -> {
            if (!isEditing) {
                isEditing = true;
                productName.setEnabled(true);
                productDescription.setEnabled(true);
                productPrice.setEnabled(true);
                productStock.setEnabled(true);
                editButton.setVisibility(android.view.View.GONE);
                saveButton.setVisibility(android.view.View.VISIBLE);
            }
        });

        saveButton.setOnClickListener(v -> {
            if (isEditing && product != null) {
                try {
                    product.setName(productName.getText().toString());
                    product.setDescription(productDescription.getText().toString());
                    product.setSellingPrice(Double.parseDouble(productPrice.getText().toString()));
                    product.setStock(Integer.parseInt(productStock.getText().toString()));
                    dbHelper.updateProduct(product);
                    isEditing = false;
                    productName.setEnabled(false);
                    productDescription.setEnabled(false);
                    productPrice.setEnabled(false);
                    productStock.setEnabled(false);
                    editButton.setVisibility(android.view.View.VISIBLE);
                    saveButton.setVisibility(android.view.View.GONE);
                } catch (NumberFormatException e) {
                    // Handle invalid number input (e.g., show a toast)
                    e.printStackTrace();
                }
            }
        });

        backButton.setOnClickListener(v -> finish());
    }
}