package com.hcmus.app_computer_store_management;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerListActivity extends AppCompatActivity {
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }
}