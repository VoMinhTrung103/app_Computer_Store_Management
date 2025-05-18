package com.hcmus.app_computer_store_management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.SupplierViewHolder> {
    private List<Supplier> supplierList;
    private Context context;

    public SupplierAdapter(Context context, List<Supplier> supplierList) {
        this.context = context;
        this.supplierList = supplierList;
    }

    @NonNull
    @Override
    public SupplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_supplier, parent, false);
        return new SupplierViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierViewHolder holder, int position) {
        Supplier supplier = supplierList.get(position);
        holder.supplierNameTextView.setText(supplier.getName());
        holder.supplierPhoneTextView.setText("SĐT: " + supplier.getPhone());
    }

    @Override
    public int getItemCount() {
        return supplierList.size();
    }

    static class SupplierViewHolder extends RecyclerView.ViewHolder {
        TextView supplierNameTextView, supplierPhoneTextView;

        public SupplierViewHolder(@NonNull View itemView) {
            super(itemView);
            supplierNameTextView = itemView.findViewById(R.id.supplierNameTextView);
            supplierPhoneTextView = itemView.findViewById(R.id.supplierPhoneTextView);
        }
    }
}