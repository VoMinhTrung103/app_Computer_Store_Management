package com.hcmus.app_computer_store_management;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditProductActivity extends AppCompatActivity {
    private EditText productNameEditText, productDescriptionEditText, productPriceEditText, productStockEditText;
    private Button saveButton, backButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_product);

        productNameEditText = findViewById(R.id.productNameEditText);
        productDescriptionEditText = findViewById(R.id.productDescriptionEditText);
        productPriceEditText = findViewById(R.id.productPriceEditText);
        productStockEditText = findViewById(R.id.productStockEditText);
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);
        dbHelper = new DatabaseHelper(this);

        saveButton.setOnClickListener(v -> {
            String name = productNameEditText.getText().toString().trim();
            String description = productDescriptionEditText.getText().toString().trim();
            String priceStr = productPriceEditText.getText().toString().trim();
            String stockStr = productStockEditText.getText().toString().trim();

            if (name.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceStr);
            int stock = Integer.parseInt(stockStr);
            Product product = new Product(0, name, price, price * 0.8);
            dbHelper.addProduct(product);
            Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
            finish();
        });

        backButton.setOnClickListener(v -> finish());
    }
}