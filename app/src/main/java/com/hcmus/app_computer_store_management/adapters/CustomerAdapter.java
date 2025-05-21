package com.hcmus.app_computer_store_management.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hcmus.app_computer_store_management.models.Customer;
import com.hcmus.app_computer_store_management.R;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
    private List<Customer> customerList;
    private Context context;

    public CustomerAdapter(Context context, List<Customer> customerList) {
        this.context = context;
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = customerList.get(position);
        holder.customerNameTextView.setText(customer.getName());
        holder.customerEmailTextView.setText("Email: " + customer.getEmail());
        holder.customerPhoneTextView.setText("Số điện thoại: " + customer.getPhone());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView customerNameTextView, customerEmailTextView, customerPhoneTextView;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            customerNameTextView = itemView.findViewById(R.id.customerNameTextView);
            customerEmailTextView = itemView.findViewById(R.id.customerEmailTextView);
            customerPhoneTextView = itemView.findViewById(R.id.customerPhoneTextView);
        }
    }
}