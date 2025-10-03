package com.example.restaurantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantapp.databinding.ItemMenuBinding;

import java.util.ArrayList;
import java.util.List;

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.MenuViewHolder> {

    private ArrayList<Entity_Dishes> dishes = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Entity_Dishes dish);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AdapterMenu(ArrayList<Entity_Dishes> dishes, OnItemClickListener listener) {
        if (dishes != null) {
            this.dishes = dishes;
        }
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMenuBinding binding = ItemMenuBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new MenuViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Entity_Dishes dish = dishes.get(position);
        Context context = holder.itemView.getContext();

         if (dish.getNameResId() != 0) {
            holder.binding.foodName.setText(context.getString(dish.getNameResId()));
        } else if (dish.getNameText() != null && !dish.getNameText().isEmpty()) {
            holder.binding.foodName.setText(dish.getNameText());
        } else {
            holder.binding.foodName.setText("Unnamed");
        }

        String fullDesc = null;
        if (dish.getDescResId() != 0) {
            fullDesc = context.getString(dish.getDescResId());
        } else if (dish.getDescText() != null && !dish.getDescText().isEmpty()) {
            fullDesc = dish.getDescText();
        }

        if (fullDesc != null) {
            holder.binding.foodDesc.setText(getShortDescription(fullDesc));
        } else {
            holder.binding.foodDesc.setText("No description");
        }

        holder.binding.price.setText(String.format("$%.2f", dish.getPrice()));

        if (dish.getImageUrl() != null && !dish.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(dish.getImageUrl())
                    .placeholder(R.drawable.kinfe)
                    .error(R.drawable.error_image)
                    .into(holder.binding.foodImage);
        } else if (dish.getImageInt() != 0) {
            holder.binding.foodImage.setImageResource(dish.getImageInt());
        } else {
            holder.binding.foodImage.setImageResource(R.drawable.error_image);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(dish);
            }
        });
    }

    private String getShortDescription(String fullDesc) {
        String[] words = fullDesc.trim().split("\\s+");
        if (words.length <= 2) {
            return fullDesc;
        } else {
            return words[0] + " " + words[1] + "...";
        }
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public void setDishes(List<Entity_Dishes> newDishes) {
        if (newDishes != null) {
            this.dishes = new ArrayList<>(newDishes);
            notifyDataSetChanged();
        }
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        ItemMenuBinding binding;

        public MenuViewHolder(ItemMenuBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
