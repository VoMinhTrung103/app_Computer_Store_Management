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
import com.hcmus.app_computer_store_management.R;
import com.hcmus.app_computer_store_management.models.Product;
import com.hcmus.app_computer_store_management.adapters.ProductAdapter;
import java.util.ArrayList;
import java.util.List;
import android.text.Editable;
import android.text.TextWatcher;
import com.hcmus.app_computer_store_management.utils.Utils;

public class SaleActivity extends AppCompatActivity implements ProductAdapter.OnSelectionChangedListener {
    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private Button createOrderButton, backButton;
    private TextView totalAmountTextView;
    private DatabaseHelper dbHelper;
    private List<Product> selectedProducts;
    private List<EditText> quantityInputs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        productRecyclerView = findViewById(R.id.productRecyclerView);
        createOrderButton = findViewById(R.id.createOrderButton);
        backButton = findViewById(R.id.backButton);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);
        dbHelper = new DatabaseHelper(this);
        selectedProducts = new ArrayList<>();
        quantityInputs = new ArrayList<>();

        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadProducts();

        createOrderButton.setEnabled(false);
        createOrderButton.setOnClickListener(v -> createOrder());
        backButton.setOnClickListener(v -> finish());
    }

    private void loadProducts() {
        List<Product> productList = dbHelper.getAllProducts();
        productAdapter = new ProductAdapter(this, productList, this, true); // Bật chế độ bán hàng
        productRecyclerView.setAdapter(productAdapter);
    }

    private void createOrder() {
        List<DatabaseHelper.OrderDetailItem> orderDetails = new ArrayList<>();
        double totalAmount = 0;

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
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Số lượng không hợp lệ cho sản phẩm " + product.getName(), Toast.LENGTH_SHORT).show();
                return;
            }

            if (quantity <= 0) {
                Toast.makeText(this, "Số lượng phải lớn hơn 0 cho sản phẩm " + product.getName(), Toast.LENGTH_SHORT).show();
                return;
            }

            if (quantity > product.getStock()) {
                Toast.makeText(this, "Không đủ hàng tồn kho cho sản phẩm " + product.getName(), Toast.LENGTH_SHORT).show();
                return;
            }

            double unitPrice = product.getSellingPrice();
            totalAmount += quantity * unitPrice;
            orderDetails.add(new DatabaseHelper.OrderDetailItem(product.getId(), quantity, unitPrice));
        }

        try {
            // Sử dụng customerId = 1 (Customer 1) từ dữ liệu mẫu trong bảng Customer
            long orderId = dbHelper.createOrder("1", "2025-05-21", orderDetails);
            Toast.makeText(this, "Tạo đơn hàng thành công, ID: " + orderId, Toast.LENGTH_SHORT).show();
            selectedProducts.clear();
            quantityInputs.clear();
            productAdapter.clearSelections();
            totalAmountTextView.setText("Tổng tiền: 0 VNĐ");
            createOrderButton.setEnabled(false);
            loadProducts(); // Làm mới danh sách để cập nhật số lượng tồn kho
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi khi tạo đơn hàng: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
                        if (quantityInput != null) {
                            quantityInputs.add(quantityInput);

                            // Xóa các TextWatcher cũ để tránh lặp lại
                            quantityInput.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                                @Override
                                public void afterTextChanged(Editable s) {
                                    updateTotalAndButton();
                                }
                            });
                        }
                    }
                }
            }
        }
        updateTotalAndButton();
    }

    // Hàm cập nhật tổng tiền và trạng thái nút
    private void updateTotalAndButton() {
        double totalAmount = 0;
        boolean allValid = true;

        for (int i = 0; i < selectedProducts.size(); i++) {
            Product product = selectedProducts.get(i);
            EditText quantityInput = quantityInputs.get(i);
            String quantityStr = quantityInput.getText().toString().trim();
            if (!quantityStr.isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    if (quantity > 0 && quantity <= product.getStock()) {
                        totalAmount += quantity * product.getSellingPrice();
                    } else {
                        allValid = false;
                    }
                } catch (NumberFormatException e) {
                    allValid = false;
                }
            } else {
                allValid = false;
            }
        }

        totalAmountTextView.setText("Tổng tiền: " + Utils.formatCurrency(totalAmount));
        createOrderButton.setEnabled(allValid && !selectedProducts.isEmpty());
    }
}