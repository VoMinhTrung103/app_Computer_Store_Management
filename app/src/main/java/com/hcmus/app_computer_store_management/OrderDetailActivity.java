package com.hcmus.app_computer_store_management;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OrderDetailActivity extends AppCompatActivity {
    private TextView orderId, orderDate, customerId, totalAmount;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        orderId = findViewById(R.id.orderId);
        orderDate = findViewById(R.id.orderDate);
        customerId = findViewById(R.id.customerId);
        totalAmount = findViewById(R.id.totalAmount);
        backButton = findViewById(R.id.backButton);

        Order order = (Order) getIntent().getSerializableExtra("ORDER");
        if (order != null) {
            orderId.setText("Mã đơn: " + order.getId());
            orderDate.setText("Ngày: " + order.getDate());
            customerId.setText("Khách hàng ID: " + order.getCustomerId());
            totalAmount.setText("Tổng tiền: " + order.getTotalAmount());
        }

        backButton.setOnClickListener(v -> finish());
    }
}