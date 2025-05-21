package com.hcmus.app_computer_store_management.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hcmus.app_computer_store_management.DatabaseHelper;
import com.hcmus.app_computer_store_management.models.Order;
import com.hcmus.app_computer_store_management.models.OrderDetail;
import com.hcmus.app_computer_store_management.R;
import com.hcmus.app_computer_store_management.adapters.OrderDetailAdapter;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    private TextView orderId, orderDate, customerId, totalAmount;
    private RecyclerView orderDetailsRecyclerView;
    private Button backButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        orderId = findViewById(R.id.orderId);
        orderDate = findViewById(R.id.orderDate);
        customerId = findViewById(R.id.customerId);
        totalAmount = findViewById(R.id.totalAmount);
        orderDetailsRecyclerView = findViewById(R.id.orderDetailsRecyclerView);
        backButton = findViewById(R.id.backButton);
        dbHelper = new DatabaseHelper(this);

        Order order = (Order) getIntent().getSerializableExtra("ORDER");
        if (order != null) {
            orderId.setText("Mã đơn: " + order.getId());
            orderDate.setText("Ngày: " + order.getDate());
            customerId.setText("Khách hàng ID: " + order.getCustomerId());
            totalAmount.setText("Tổng tiền: " + order.getTotalAmount());
            loadOrderDetails(order.getId());
        }

        backButton.setOnClickListener(v -> finish());
    }

    private void loadOrderDetails(int orderId) {
        List<OrderDetail> orderDetails = dbHelper.getOrderDetails(orderId);
        OrderDetailAdapter adapter = new OrderDetailAdapter(this, orderDetails);
        orderDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderDetailsRecyclerView.setAdapter(adapter);
    }
}