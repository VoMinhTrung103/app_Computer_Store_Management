package com.hcmus.app_computer_store_management.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.hcmus.app_computer_store_management.R;
import com.hcmus.app_computer_store_management.models.RevenueStat;
import com.hcmus.app_computer_store_management.utils.Utils;
import java.util.List;

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.ViewHolder> {
    private List<RevenueStat> revenueStats;

    public RevenueAdapter(List<RevenueStat> revenueStats) {
        this.revenueStats = revenueStats;
    }

    public void updateData(List<RevenueStat> newRevenueStats) {
        this.revenueStats.clear();
        this.revenueStats.addAll(newRevenueStats);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_revenue_stat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RevenueStat stat = revenueStats.get(position);
        holder.monthTextView.setText(stat.getMonth());
        holder.revenueTextView.setText(Utils.formatCurrency(stat.getRevenue()));
    }

    @Override
    public int getItemCount() {
        return revenueStats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView monthTextView, revenueTextView;

        public ViewHolder(View view) {
            super(view);
            monthTextView = view.findViewById(R.id.monthTextView);
            revenueTextView = view.findViewById(R.id.revenueTextView);
        }
    }
}