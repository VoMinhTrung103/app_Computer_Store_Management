package com.hcmus.app_computer_store_management;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;
    private List<Integer> selectedProductIds;
    private OnSelectionChangedListener selectionChangedListener;

    public interface OnSelectionChangedListener {
        void onSelectionChanged(boolean hasSelections);
    }

    public ProductAdapter(Context context, List<Product> productList, OnSelectionChangedListener listener) {
        this.context = context;
        this.productList = productList;
        this.selectedProductIds = new ArrayList<>();
        this.selectionChangedListener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productNameTextView.setText(product.getName());
        holder.productPriceTextView.setText("Giá: " + product.getSellingPrice());

        // Handle checkbox state
        holder.checkboxSelect.setChecked(selectedProductIds.contains(product.getId()));
        holder.checkboxSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedProductIds.add(product.getId());
            } else {
                selectedProductIds.remove(Integer.valueOf(product.getId()));
            }
            Log.d("ProductAdapter", "Selected IDs: " + selectedProductIds);
            if (selectionChangedListener != null) {
                selectionChangedListener.onSelectionChanged(!selectedProductIds.isEmpty());
            }
        });

        // Handle item click to view details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("PRODUCT", product);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public List<Integer> getSelectedProductIds() {
        return new ArrayList<>(selectedProductIds);
    }

    public void clearSelections() {
        selectedProductIds.clear();
        notifyDataSetChanged();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView, productPriceTextView;
        CheckBox checkboxSelect;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            checkboxSelect = itemView.findViewById(R.id.checkboxSelect);
        }
    }
}