package com.hcmus.app_computer_store_management;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button manageProductsButton, manageOrdersButton, manageCustomersButton, statisticsButton, importProductButton, logoutButton, backButton;
    private SessionManager sessionManager;
    private String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manageProductsButton = findViewById(R.id.manageProductsButton);
        manageOrdersButton = findViewById(R.id.manageOrdersButton);
        manageCustomersButton = findViewById(R.id.manageCustomersButton);
        statisticsButton = findViewById(R.id.statisticsButton);
        importProductButton = findViewById(R.id.importProductButton);
        logoutButton = findViewById(R.id.logoutButton);
        backButton = findViewById(R.id.backButton);

        sessionManager = new SessionManager(this);
        userRole = getIntent().getStringExtra("ROLE");

        if ("Employee".equals(userRole)) {
            manageProductsButton.setEnabled(false);
            manageCustomersButton.setEnabled(false);
            statisticsButton.setEnabled(false);
            importProductButton.setEnabled(false);
        }

        manageProductsButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProductListActivity.class)));
        manageOrdersButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, OrderListActivity.class)));
        manageCustomersButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CustomerListActivity.class)));
        statisticsButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StatisticActivity.class)));
        importProductButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ImportProductActivity.class)));
        logoutButton.setOnClickListener(v -> sessionManager.logout());
        backButton.setOnClickListener(v -> finish());
    }
}