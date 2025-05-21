package com.hcmus.app_computer_store_management.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.hcmus.app_computer_store_management.R;
import com.hcmus.app_computer_store_management.models.InventoryStat;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    private List<InventoryStat> inventoryStats;

    public InventoryAdapter(List<InventoryStat> inventoryStats) {
        this.inventoryStats = inventoryStats;
    }

    public void updateData(List<InventoryStat> newInventoryStats) {
        this.inventoryStats.clear();
        this.inventoryStats.addAll(newInventoryStats);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inventory_stat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InventoryStat stat = inventoryStats.get(position);
        holder.productIdTextView.setText(String.valueOf(stat.getProductId()));
        holder.productNameTextView.setText(stat.getProductName());
        holder.stockTextView.setText(String.valueOf(stat.getStock()));
    }

    @Override
    public int getItemCount() {
        return inventoryStats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productIdTextView, productNameTextView, stockTextView;

        public ViewHolder(View view) {
            super(view);
            productIdTextView = view.findViewById(R.id.productIdTextView);
            productNameTextView = view.findViewById(R.id.productNameTextView);
            stockTextView = view.findViewById(R.id.stockTextView);
        }
    }
}