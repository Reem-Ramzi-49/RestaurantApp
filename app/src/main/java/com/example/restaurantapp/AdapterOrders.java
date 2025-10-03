package com.example.restaurantapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantapp.databinding.ItemOrderBinding;

import java.util.ArrayList;
import java.util.List;

public class AdapterOrders extends RecyclerView.Adapter<AdapterOrders.OrderViewHolder> {

    private List<Entity_Orders> orders = new ArrayList<>();
    private List<OrderWithUser> ordersWithUser = new ArrayList<>();
    private final OnOrderClickListener listenerUser;
    private final OnAdminOrderClickListener listenerAdmin;

     public interface OnOrderClickListener {
        void onOrderClick(Entity_Orders order);
    }

     public interface OnAdminOrderClickListener {
        void onOrderClick(OrderWithUser order);
    }

     public AdapterOrders(OnOrderClickListener listener) {
        this.listenerUser = listener;
        this.listenerAdmin = null;
    }

     public AdapterOrders(OnAdminOrderClickListener listener) {
        this.listenerAdmin = listener;
        this.listenerUser = null;
    }

     public void setOrders(List<Entity_Orders> newOrders) {
        this.orders = newOrders;
        this.ordersWithUser.clear();
        notifyDataSetChanged();
    }

     public void setOrdersWithUser(List<OrderWithUser> newOrders) {
        this.ordersWithUser = newOrders;
        this.orders.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding binding = ItemOrderBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderViewHolder(binding, listenerUser, listenerAdmin);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        if (!ordersWithUser.isEmpty()) {
            holder.bindAdmin(ordersWithUser.get(position));
        } else {
            holder.bindUser(orders.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return !ordersWithUser.isEmpty() ? ordersWithUser.size() : orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final ItemOrderBinding binding;

        public OrderViewHolder(ItemOrderBinding binding,
                               OnOrderClickListener listenerUser,
                               OnAdminOrderClickListener listenerAdmin) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    if (listenerUser != null && v.getTag() instanceof Entity_Orders) {
                        listenerUser.onOrderClick((Entity_Orders) v.getTag());
                    } else if (listenerAdmin != null && v.getTag() instanceof OrderWithUser) {
                        listenerAdmin.onOrderClick((OrderWithUser) v.getTag());
                    }
                }
            });
        }


        public void bindUser(Entity_Orders order) {
            binding.getRoot().setTag(order);

            binding.orderTitle.setText("# Order " + order.getDisplay_number());
            binding.orderTotal.setText("$" + order.getTotal_price());
            binding.orderDetails.setText(order.getItems_count() + " items");
            binding.orderDate.setText(order.getOrder_date());
        }

         public void bindAdmin(OrderWithUser order) {
            binding.getRoot().setTag(order);

            binding.orderTitle.setText("# Order " + order.getDisplay_number()
                    + " - " + order.getUserName());
            binding.orderTotal.setText("$" + order.getTotal_price());
            binding.orderDetails.setText(order.getItems_count() + " items");
            binding.orderDate.setText(order.getOrder_date());
        }
    }
}
