package com.hcmus.app_computer_store_management;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class StatisticActivity extends AppCompatActivity {
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }
}