package com.hcmus.app_computer_store_management;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView productName, productDescription, productPrice, productStock;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productPrice = findViewById(R.id.productPrice);
        productStock = findViewById(R.id.productStock);
        backButton = findViewById(R.id.backButton);

        Product product = (Product) getIntent().getSerializableExtra("PRODUCT");
        if (product != null) {
            productName.setText(product.getName());
            productDescription.setText(product.getDescription());
            productPrice.setText("Giá bán: " + product.getSellingPrice());
            productStock.setText("Tồn kho: " + product.getStock());
        }

        backButton.setOnClickListener(v -> finish());
    }
}