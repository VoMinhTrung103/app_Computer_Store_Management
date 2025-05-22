package com.hcmus.app_computer_store_management.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import com.hcmus.app_computer_store_management.DatabaseHelper;
import com.hcmus.app_computer_store_management.models.Product;
import com.hcmus.app_computer_store_management.R;
import com.hcmus.app_computer_store_management.utils.Utils;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView productName, productDescription, productPrice; // cho employee
    private EditText productNameEdit, productDescriptionEdit, productPriceEdit, productImportPrice, productStock, productType; // cho admin
    
    private Button backButton, editButton, saveButton;
    private Product product;
    private DatabaseHelper dbHelper;
    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean forEmployee = getIntent().getBooleanExtra("FOR_EMPLOYEE", false);

        if (forEmployee) {
            setContentView(R.layout.product_detail_for_customer);

            productName = findViewById(R.id.productName);
            productDescription = findViewById(R.id.productDescription);
            productPrice = findViewById(R.id.productPrice);

        } else {
            setContentView(R.layout.activity_product_detail);

            productNameEdit = findViewById(R.id.productName);
            productDescriptionEdit = findViewById(R.id.productDescription);
            productPriceEdit = findViewById(R.id.productPrice);
            productImportPrice = findViewById(R.id.productImportPrice);
            productStock = findViewById(R.id.productStock);
            productType = findViewById(R.id.productType);
            backButton = findViewById(R.id.backButton);
            editButton = findViewById(R.id.editButton);
            saveButton = findViewById(R.id.saveButton);

            setupButtonListeners();
        }

        dbHelper = new DatabaseHelper(this);

        product = (Product) getIntent().getSerializableExtra("PRODUCT");
        if (product != null) {
            loadProductDetails(forEmployee);
        }
    }
    private void loadProductDetails(boolean forEmployee) {
        if (forEmployee) {
            productName.setText(product.getName());
            productDescription.setText(product.getDescription());
            productPrice.setText(Utils.formatCurrency(product.getSellingPrice()));
        } else {
            productNameEdit.setText(product.getName());
            productDescriptionEdit.setText(product.getDescription());
            productPriceEdit.setText(Utils.formatCurrency(product.getSellingPrice()));
            productImportPrice.setText(Utils.formatCurrency(product.getImportPrice()));
            productStock.setText(String.valueOf(product.getStock()));
            productType.setText(product.getType());
        }
    }

    private void setupButtonListeners() {
        editButton.setOnClickListener(v -> {
            if (!isEditing) {
                enableEditing(true);
                // Khi chuyển sang chế độ edit, hiển thị giá dạng số không có định dạng
                productPriceEdit.setText(String.valueOf(product.getSellingPrice()));
                productImportPrice.setText(String.valueOf(product.getImportPrice()));
            }
        });

        saveButton.setOnClickListener(v -> {
            if (isEditing && product != null) {
                try {
                    updateProductFromInputs();
                    dbHelper.updateProduct(product);
                    enableEditing(false);
                    loadProductDetails(false); // Reload để hiển thị lại giá đã định dạng
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
        productNameEdit.setEnabled(enable);
        productDescriptionEdit.setEnabled(enable);
        productPriceEdit.setEnabled(enable);
        productImportPrice.setEnabled(enable);
        productStock.setEnabled(enable);
        productType.setEnabled(enable);

        editButton.setVisibility(enable ? View.GONE : View.VISIBLE);
        saveButton.setVisibility(enable ? View.VISIBLE : View.GONE);
    }
}