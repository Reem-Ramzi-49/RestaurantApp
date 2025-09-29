package com.example.restaurantapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantapp.databinding.ItemCartBinding;

import java.util.ArrayList;
import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.CartViewHolder> {

    private List<CartItemDTO> items = new ArrayList<>();

     public interface OnCartActionListener {
        void onIncrease(CartItemDTO item);
        void onDecrease(CartItemDTO item);
        void onDelete(CartItemDTO item);
    }

    private final OnCartActionListener listener;

    public AdapterCart(List<CartItemDTO> items, OnCartActionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public void setItems(List<CartItemDTO> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding binding = ItemCartBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new CartViewHolder(binding);
    }

     @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItemDTO item = items.get(position);

        holder.binding.cartFoodName.setText(item.getDishName());
        holder.binding.cartUnitPrice.setText(String.format("$%.2f", item.getUnitPrice()));
        holder.binding.cartItemQuantityCircle.setText(String.valueOf(item.getQuantity()));

        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.food1)
                    .into(holder.binding.cartFoodImage);
        } else if (item.getImageRes() != 0) {
            holder.binding.cartFoodImage.setImageResource(item.getImageRes());
        } else {
            holder.binding.cartFoodImage.setImageResource(R.drawable.food1);
        }

        holder.binding.btnIncrease.setOnClickListener(v -> listener.onIncrease(item));
        holder.binding.btnDecrease.setOnClickListener(v -> listener.onDecrease(item));
        holder.binding.btnDelete.setOnClickListener(v -> listener.onDelete(item));
    }


    @Override
    public int getItemCount() {
        return (items != null) ? items.size() : 0;
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ItemCartBinding binding;

        public CartViewHolder(ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
