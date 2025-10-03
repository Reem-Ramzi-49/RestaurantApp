package com.example.restaurantapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantapp.databinding.ItemOrderBinding;

import java.util.ArrayList;
import java.util.List;

public class AdapterAdminOrders extends RecyclerView.Adapter<AdapterAdminOrders.AdminOrderViewHolder> {

    private List<OrderWithUser> orders = new ArrayList<>();
    private final OnAdminOrderClickListener listener;

    // ✅ واجهة Listener للأكشنز (تأكيد، إكمال، حذف...)
    public interface OnAdminOrderClickListener {
        void onOrderClick(OrderWithUser order);
    }

    public AdapterAdminOrders(OnAdminOrderClickListener listener) {
        this.listener = listener;
    }

    public void setOrders(List<OrderWithUser> newOrders) {
        this.orders = newOrders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding binding = ItemOrderBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new AdminOrderViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrderViewHolder holder, int position) {
        OrderWithUser order = orders.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class AdminOrderViewHolder extends RecyclerView.ViewHolder {
        private final ItemOrderBinding binding;

        public AdminOrderViewHolder(ItemOrderBinding binding, OnAdminOrderClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onOrderClick((OrderWithUser) v.getTag());
                }
            });
        }

        public void bind(OrderWithUser order) {
            binding.getRoot().setTag(order);

            // ✅ عرض بيانات الطلب
            binding.orderTitle.setText("# Order " + order.getOrder_id());
            binding.orderTotal.setText("$" + order.getTotal_price());
            binding.orderDetails.setText(order.getItems_count() + " items");

            // ✅ التاريخ + اسم المستخدم
            String details = order.getOrder_date() + " | " + order.getUserName();
            binding.orderDate.setText(details);
        }
    }
}
