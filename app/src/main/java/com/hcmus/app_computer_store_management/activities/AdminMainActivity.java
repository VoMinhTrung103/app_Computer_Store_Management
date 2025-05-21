package com.hcmus.app_computer_store_management.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.hcmus.app_computer_store_management.R;

public class AdminMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        Button statisticsButton = findViewById(R.id.statisticsButton);
        Button productListButton = findViewById(R.id.productListButton);
        Button logoutButton = findViewById(R.id.logoutButton);

        statisticsButton.setOnClickListener(v -> startActivity(new Intent(this, StatisticActivity.class)));
        productListButton.setOnClickListener(v -> startActivity(new Intent(this, ProductListActivity.class)));
        logoutButton.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}