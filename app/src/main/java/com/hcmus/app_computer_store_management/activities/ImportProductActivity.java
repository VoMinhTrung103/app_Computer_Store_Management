package com.hcmus.app_computer_store_management.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hcmus.app_computer_store_management.DatabaseHelper;
import com.hcmus.app_computer_store_management.models.Product;
import com.hcmus.app_computer_store_management.R;
import com.hcmus.app_computer_store_management.adapters.ProductAdapter;
import java.util.ArrayList;
import java.util.List;

public class ImportProductActivity extends AppCompatActivity implements ProductAdapter.OnSelectionChangedListener {
    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private Button importButton, backButton;
    private DatabaseHelper dbHelper;
    private List<Product> selectedProducts;
    private List<EditText> quantityInputs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_product);

        productRecyclerView = findViewById(R.id.productRecyclerView);
        importButton = findViewById(R.id.importButton);
        backButton = findViewById(R.id.backButton);
        dbHelper = new DatabaseHelper(this);
        selectedProducts = new ArrayList<>();
        quantityInputs = new ArrayList<>();

        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadProducts();

        importButton.setEnabled(false);
        importButton.setOnClickListener(v -> importProducts());
        backButton.setOnClickListener(v -> finish());
    }

    private void loadProducts() {
        List<Product> productList = dbHelper.getAllProducts();
        productAdapter = new ProductAdapter(this, productList, this, true); // Sử dụng chế độ nhập số lượng
        productRecyclerView.setAdapter(productAdapter);
    }

    private void importProducts() {
        for (int i = 0; i < selectedProducts.size(); i++) {
            Product product = selectedProducts.get(i);
            EditText quantityInput = quantityInputs.get(i);
            String quantityStr = quantityInput.getText().toString().trim();

            if (quantityStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập số lượng cho sản phẩm " + product.getName(), Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    Toast.makeText(this, "Số lượng phải lớn hơn 0 cho sản phẩm " + product.getName(), Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Số lượng không hợp lệ cho sản phẩm " + product.getName(), Toast.LENGTH_SHORT).show();
                return;
            }

            // Cập nhật số lượng tồn kho
            int newStock = product.getStock() + quantity;
            product.setStock(newStock);
            dbHelper.updateProduct(product);
        }

        Toast.makeText(this, "Nhập sản phẩm thành công", Toast.LENGTH_SHORT).show();
        selectedProducts.clear();
        quantityInputs.clear();
        productAdapter.clearSelections();
        importButton.setEnabled(false);
        loadProducts();
    }

    @Override
    public void onSelectionChanged(boolean hasSelections) {
        selectedProducts.clear();
        quantityInputs.clear();

        if (hasSelections) {
            List<Integer> selectedIds = productAdapter.getSelectedProductIds();
            for (Integer id : selectedIds) {
                Product product = dbHelper.getProductById(id);
                if (product != null) {
                    selectedProducts.add(product);
                    int position = productAdapter.getPositionForId(id);
                    View view = productRecyclerView.getLayoutManager().findViewByPosition(position);
                    if (view != null) {
                        EditText quantityInput = view.findViewById(R.id.quantityInput);
                        quantityInputs.add(quantityInput);
                    }
                }
            }
        }

        importButton.setEnabled(hasSelections && !quantityInputs.isEmpty());
    }
}