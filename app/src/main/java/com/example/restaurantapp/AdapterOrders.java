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

    public void setOrders(List<Entity_Orders> newOrders) {
        this.orders = newOrders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding binding = ItemOrderBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Entity_Orders order = orders.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final ItemOrderBinding binding;

        public OrderViewHolder(ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Entity_Orders order) {
             binding.orderTitle.setText("# Order " + order.getOrder_id());

             binding.orderTotal.setText("$" + order.getTotal_price());

             binding.orderDetails.setText(order.getItems_count() + " items");

             binding.orderDate.setText(order.getOrder_date());
        }
    }
}
