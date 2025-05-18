package com.hcmus.app_computer_store_management;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {
    private EditText productNameEditText, productDescriptionEditText, productPriceEditText, productStockEditText, productTypeEditText;
    private Button saveButton, backButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        productNameEditText = findViewById(R.id.productNameEditText);
        productDescriptionEditText = findViewById(R.id.productDescriptionEditText);
        productPriceEditText = findViewById(R.id.productPriceEditText);
        productStockEditText = findViewById(R.id.productStockEditText);
        productTypeEditText = findViewById(R.id.productTypeEditText);
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);
        dbHelper = new DatabaseHelper(this);

        saveButton.setOnClickListener(v -> {
            String name = productNameEditText.getText().toString().trim();
            String description = productDescriptionEditText.getText().toString().trim();
            String priceStr = productPriceEditText.getText().toString().trim();
            String stockStr = productStockEditText.getText().toString().trim();
            String type = productTypeEditText.getText().toString().trim();

            if (name.isEmpty() || description.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty() || type.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double price = Double.parseDouble(priceStr);
                int stock = Integer.parseInt(stockStr);
                double importPrice = price * 0.8; // Giá nhập = 80% giá bán (giả lập)
                Product product = new Product(0, name, price, importPrice);
                dbHelper.addProduct(product);
                Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                finish();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá bán và tồn kho phải là số", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(v -> finish());
    }
}