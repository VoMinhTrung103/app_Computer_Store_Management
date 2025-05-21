package com.hcmus.app_computer_store_management.activities;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hcmus.app_computer_store_management.DatabaseHelper;
import com.hcmus.app_computer_store_management.models.Order;
import com.hcmus.app_computer_store_management.R;
import com.hcmus.app_computer_store_management.adapters.OrderAdapter;
import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {
    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
    private Button backButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        orderRecyclerView = findViewById(R.id.orderRecyclerView);
        backButton = findViewById(R.id.backButton);
        dbHelper = new DatabaseHelper(this);

        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadOrders();

        backButton.setOnClickListener(v -> finish());
    }

    private void loadOrders() {
        List<Order> orderList = new ArrayList<>();
        // Lấy danh sách đơn hàng từ database
        // Hiện tại chưa có dữ liệu mẫu, sẽ hiển thị danh sách rỗng
        // Có thể thêm dữ liệu mẫu trong DatabaseHelper nếu cần
        orderAdapter = new OrderAdapter(this, orderList);
        orderRecyclerView.setAdapter(orderAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOrders();
    }
}