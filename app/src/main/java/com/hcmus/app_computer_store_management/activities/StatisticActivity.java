package com.hcmus.app_computer_store_management.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hcmus.app_computer_store_management.DatabaseHelper;
import com.hcmus.app_computer_store_management.adapters.InventoryAdapter;
import com.hcmus.app_computer_store_management.models.InventoryStat;
import com.hcmus.app_computer_store_management.models.ProductStat;
import com.hcmus.app_computer_store_management.R;
import com.hcmus.app_computer_store_management.adapters.RevenueAdapter;
import com.hcmus.app_computer_store_management.models.RevenueStat;
import com.hcmus.app_computer_store_management.adapters.TopProductsAdapter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class StatisticActivity extends AppCompatActivity {
    private Button backButton, filterButton;
    private TextView filterTextView;
    private DatabaseHelper dbHelper;
    private String startDate, endDate;
    private RecyclerView revenueRecyclerView, topProductsRecyclerView, inventoryRecyclerView;
    private RevenueAdapter revenueAdapter;
    private TopProductsAdapter topProductsAdapter;
    private InventoryAdapter inventoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_statistic);

        dbHelper = new DatabaseHelper(this);
        backButton = findViewById(R.id.backButton);
        filterButton = findViewById(R.id.filterButton);
        filterTextView = findViewById(R.id.filterTextView);
        revenueRecyclerView = findViewById(R.id.revenueRecyclerView);
        topProductsRecyclerView = findViewById(R.id.topProductsRecyclerView);
        inventoryRecyclerView = findViewById(R.id.inventoryRecyclerView);

        // Setup RecyclerViews
        setupRevenueChart();
        setupTopProducts();
        setupInventory();

        // Setup filter button
        filterButton.setOnClickListener(v -> showDateRangePicker());

        backButton.setOnClickListener(v -> finish());
    }

    private void setupRevenueChart() {
        revenueRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<RevenueStat> revenueStats = dbHelper.getMonthlyRevenue(startDate, endDate);
        revenueAdapter = new RevenueAdapter(revenueStats);
        revenueRecyclerView.setAdapter(revenueAdapter);
    }

    private void setupTopProducts() {
        topProductsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<ProductStat> productStats = dbHelper.getTopSellingProducts(5, startDate, endDate);
        topProductsAdapter = new TopProductsAdapter(productStats);
        topProductsRecyclerView.setAdapter(topProductsAdapter);
    }

    private void setupInventory() {
        inventoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<InventoryStat> inventoryStats = dbHelper.getInventoryStatus();
        inventoryAdapter = new InventoryAdapter(inventoryStats);
        inventoryRecyclerView.setAdapter(inventoryAdapter);
    }

    private void showDateRangePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog startDatePicker = new DatePickerDialog(this,
                (view, year, month, day) -> {
                    startDate = String.format(Locale.US, "%d-%02d-%02d", year, month + 1, day);
                    DatePickerDialog endDatePicker = new DatePickerDialog(this,
                            (view1, year1, month1, day1) -> {
                                endDate = String.format(Locale.US, "%d-%02d-%02d", year1, month1 + 1, day1);
                                filterTextView.setText(String.format("Từ %s đến %s", startDate, endDate));
                                updateData();
                            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    endDatePicker.show();
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        startDatePicker.show();
    }

    private void updateData() {
        // Update revenue recycler view
        List<RevenueStat> revenueStats = dbHelper.getMonthlyRevenue(startDate, endDate);
        revenueAdapter.updateData(revenueStats);
        revenueRecyclerView.setAdapter(revenueAdapter);

        // Update top products
        List<ProductStat> productStats = dbHelper.getTopSellingProducts(5, startDate, endDate);
        topProductsAdapter.updateData(productStats);
        topProductsRecyclerView.setAdapter(topProductsAdapter);
    }
}