package com.hcmus.app_computer_store_management.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.hcmus.app_computer_store_management.R;

public class ClientMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        Button saleButton = findViewById(R.id.saleButton);
        Button orderListButton = findViewById(R.id.orderListButton);
        Button logoutButton = findViewById(R.id.logoutButton);

        saleButton.setOnClickListener(v -> startActivity(new Intent(this, SaleActivity.class)));
        orderListButton.setOnClickListener(v -> startActivity(new Intent(this, OrderListActivity.class)));
        logoutButton.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}