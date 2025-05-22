package com.hcmus.app_computer_store_management.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hcmus.app_computer_store_management.models.Product;
import com.hcmus.app_computer_store_management.R;
import com.hcmus.app_computer_store_management.utils.Utils;
import com.hcmus.app_computer_store_management.activities.ProductDetailActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;
    private List<Integer> selectedProductIds;
    private OnSelectionChangedListener selectionChangedListener;
    private boolean isSaleMode;
    private HashMap<Integer, String> quantityMap = new HashMap<>(); // Lưu số lượng nhập cho từng sản phẩm

    public interface OnSelectionChangedListener {
        void onSelectionChanged(boolean hasSelections);
    }
    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(Context context, List<Product> productList, OnSelectionChangedListener listener, boolean isSaleMode) {
        this.context = context;
        this.productList = productList;
        this.selectedProductIds = new ArrayList<>();
        this.selectionChangedListener = listener;
        this.isSaleMode = isSaleMode;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_sale, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productNameTextView.setText(product.getName());
        holder.productPriceTextView.setText("Giá: " + Utils.formatCurrency(product.getSellingPrice()));
        holder.productStockTextView.setText("Tồn kho: " + product.getStock());

        if (isSaleMode) {
            holder.checkboxSelect.setVisibility(View.GONE);
            holder.quantityInput.setVisibility(View.VISIBLE);

            // Giữ lại số lượng đã nhập khi scroll
            String quantityStr = quantityMap.getOrDefault(product.getId(), "");
            if (!quantityStr.equals(holder.quantityInput.getText().toString()))
                holder.quantityInput.setText(quantityStr);

            // Xóa TextWatcher cũ trước khi gán mới để tránh lặp callback
            holder.quantityInput.setTag(holder.quantityInput.getId(), null);
            holder.quantityInput.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    String value = s.toString().trim();
                    if (!value.isEmpty() && value.matches("\\d+")) {
                        int quantity = Integer.parseInt(value);
                        if (quantity > 0) {
                            if (!selectedProductIds.contains(product.getId()))
                                selectedProductIds.add(product.getId());
                            quantityMap.put(product.getId(), value);
                        } else {
                            selectedProductIds.remove((Integer) product.getId());
                            quantityMap.remove(product.getId());
                        }
                    } else {
                        selectedProductIds.remove((Integer) product.getId());
                        quantityMap.remove(product.getId());
                    }
                    if (selectionChangedListener != null) {
                        selectionChangedListener.onSelectionChanged(!selectedProductIds.isEmpty());
                    }
                }
            });
                // Chỉ gán 1 lần sự kiện click để xem chi tiết sản phẩm
                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("PRODUCT", product);
                    intent.putExtra("FOR_EMPLOYEE", true);
                    context.startActivity(intent);
                });
        } else {
            holder.checkboxSelect.setVisibility(View.VISIBLE);
            holder.quantityInput.setVisibility(View.GONE);

            holder.checkboxSelect.setOnCheckedChangeListener(null);
            holder.checkboxSelect.setChecked(selectedProductIds.contains(product.getId()));

            holder.checkboxSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    if (!selectedProductIds.contains(product.getId()))
                        selectedProductIds.add(product.getId());
                } else {
                    selectedProductIds.remove((Integer) product.getId());
                }
                if (selectionChangedListener != null) {
                    selectionChangedListener.onSelectionChanged(!selectedProductIds.isEmpty());
                }
            });

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("PRODUCT", product);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public List<Integer> getSelectedProductIds() {
        return new ArrayList<>(selectedProductIds);
    }

    public String getQuantityForProduct(int productId) {
        return quantityMap.getOrDefault(productId, "");
    }

    public void clearSelections() {
        selectedProductIds.clear();
        quantityMap.clear();
        notifyDataSetChanged();
    }

    public int getPositionForId(int id) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView, productPriceTextView, productStockTextView;
        CheckBox checkboxSelect;
        EditText quantityInput;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            productStockTextView = itemView.findViewById(R.id.productStockTextView);
            checkboxSelect = itemView.findViewById(R.id.checkboxSelect);
            quantityInput = itemView.findViewById(R.id.quantityInput);
        }
    }
}