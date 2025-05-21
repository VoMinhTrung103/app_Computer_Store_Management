package com.hcmus.app_computer_store_management.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.hcmus.app_computer_store_management.DatabaseHelper;
import com.hcmus.app_computer_store_management.models.Product;
import com.hcmus.app_computer_store_management.R;

public class AddProductActivity extends AppCompatActivity {
    private EditText nameEditText, descriptionEditText, sellingPriceEditText, importPriceEditText, stockEditText, typeEditText;
    private Button saveProductButton, backButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        sellingPriceEditText = findViewById(R.id.sellingPriceEditText);
        importPriceEditText = findViewById(R.id.importPriceEditText);
        stockEditText = findViewById(R.id.stockEditText);
        typeEditText = findViewById(R.id.typeEditText);
        saveProductButton = findViewById(R.id.saveProductButton);
        backButton = findViewById(R.id.backButton);
        dbHelper = new DatabaseHelper(this);

        saveProductButton.setOnClickListener(v -> saveProduct());
        backButton.setOnClickListener(v -> finish());
    }

    private void saveProduct() {
        String name = nameEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String sellingPriceStr = sellingPriceEditText.getText().toString().trim();
        String importPriceStr = importPriceEditText.getText().toString().trim();
        String stockStr = stockEditText.getText().toString().trim();
        String type = typeEditText.getText().toString().trim();

        if (name.isEmpty() || sellingPriceStr.isEmpty() || importPriceStr.isEmpty() || stockStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin bắt buộc", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double sellingPrice = Double.parseDouble(sellingPriceStr);
            double importPrice = Double.parseDouble(importPriceStr);
            int stock = Integer.parseInt(stockStr);

            Product product = new Product(0, name, sellingPrice, importPrice);
            product.setDescription(description);
            product.setStock(stock);
            product.setType(type);

            dbHelper.addProduct(product);
            Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập giá và số lượng hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }
}