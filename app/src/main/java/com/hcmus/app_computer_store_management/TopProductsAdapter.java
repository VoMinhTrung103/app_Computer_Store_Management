package com.hcmus.app_computer_store_management;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TopProductsAdapter extends RecyclerView.Adapter<TopProductsAdapter.ViewHolder> {
    private List<ProductStat> productStats;

    public TopProductsAdapter(List<ProductStat> productStats) {
        this.productStats = productStats;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_stat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductStat stat = productStats.get(position);
        holder.productIdTextView.setText(String.valueOf(stat.getProductId()));
        holder.productNameTextView.setText(stat.getProductName());
        holder.totalSoldTextView.setText(String.valueOf(stat.getTotalSold()));
    }

    @Override
    public int getItemCount() {
        return productStats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productIdTextView, productNameTextView, totalSoldTextView;

        public ViewHolder(View view) {
            super(view);
            productIdTextView = view.findViewById(R.id.productIdTextView);
            productNameTextView = view.findViewById(R.id.productNameTextView);
            totalSoldTextView = view.findViewById(R.id.totalSoldTextView);
        }
    }
}