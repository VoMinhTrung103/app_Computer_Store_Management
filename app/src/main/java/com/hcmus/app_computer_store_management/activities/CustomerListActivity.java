package com.hcmus.app_computer_store_management.activities;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hcmus.app_computer_store_management.models.Customer;
import com.hcmus.app_computer_store_management.R;
import com.hcmus.app_computer_store_management.adapters.CustomerAdapter;
import java.util.ArrayList;
import java.util.List;

public class CustomerListActivity extends AppCompatActivity {
    private RecyclerView customerRecyclerView;
    private CustomerAdapter customerAdapter;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        customerRecyclerView = findViewById(R.id.customerRecyclerView);
        backButton = findViewById(R.id.backButton);

        customerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadCustomers();

        backButton.setOnClickListener(v -> finish());
    }

    private void loadCustomers() {
        // Dữ liệu mẫu vì chưa có database cho khách hàng
        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer(1, "Nguyen Van A", "a@shop.com", "0123456789"));
        customerList.add(new Customer(2, "Tran Thi B", "b@shop.com", "0987654321"));
        customerAdapter = new CustomerAdapter(this, customerList);
        customerRecyclerView.setAdapter(customerAdapter);
    }
}