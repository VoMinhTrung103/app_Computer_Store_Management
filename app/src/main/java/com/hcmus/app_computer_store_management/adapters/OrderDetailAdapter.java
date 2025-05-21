package com.hcmus.app_computer_store_management.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hcmus.app_computer_store_management.models.OrderDetail;
import com.hcmus.app_computer_store_management.R;
import com.hcmus.app_computer_store_management.utils.Utils;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {
    private List<OrderDetail> orderDetailList;
    private Context context;

    public OrderDetailAdapter(Context context, List<OrderDetail> orderDetailList) {
        this.context = context;
        this.orderDetailList = orderDetailList;
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_detail, parent, false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {
        OrderDetail orderDetail = orderDetailList.get(position);
        holder.productIdTextView.setText("Mã sản phẩm: " + orderDetail.getProductId());
        holder.quantityTextView.setText("Số lượng: " + orderDetail.getQuantity());
        holder.unitPriceTextView.setText("Đơn giá: " + Utils.formatCurrency(orderDetail.getUnitPrice()));
        holder.totalPriceTextView.setText("Thành tiền: " + Utils.formatCurrency(orderDetail.getQuantity() * orderDetail.getUnitPrice()));
    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    static class OrderDetailViewHolder extends RecyclerView.ViewHolder {
        TextView productIdTextView, quantityTextView, unitPriceTextView, totalPriceTextView;

        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            productIdTextView = itemView.findViewById(R.id.productIdTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            unitPriceTextView = itemView.findViewById(R.id.unitPriceTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);
        }
    }
}