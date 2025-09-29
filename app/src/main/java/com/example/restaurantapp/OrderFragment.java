package com.example.restaurantapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.restaurantapp.databinding.FragmentOrderBinding;

public class OrderFragment extends Fragment {

    private FragmentOrderBinding binding;
    private AppViewModel viewModel;
    private AdapterOrders adapter;

    public OrderFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        // RecyclerView for past orders
        adapter = new AdapterOrders();
        binding.recyclerOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerOrders.setAdapter(adapter);

         viewModel.getAllOrders().observe(getViewLifecycleOwner(), orders -> {
            adapter.setOrders(orders);

            if (orders != null && !orders.isEmpty()) {
                 Entity_Orders current = orders.get(0);
                binding.currentOrderCard.setVisibility(View.VISIBLE);

                Log.d("ORDER_FLOW", "UI received status = " + current.getStatus());

                updateOrderUI(current.getStatus(), current.getOrder_date());

                 if ("pending".equalsIgnoreCase(current.getStatus())) {
                    startOrderSimulation(current.getOrder_id());
                }

            } else {
                Log.d("ORDER_FLOW", "No orders found.");
                binding.currentOrderCard.setVisibility(View.VISIBLE);
                updateOrderUI("pending", "");
            }
        });

        return binding.getRoot();
    }

    private void startOrderSimulation(int orderId) {
        Handler handler = new Handler(Looper.getMainLooper());

        //
        handler.postDelayed(() -> {
            viewModel.updateOrderStatus(orderId, "preparing");
        }, 5000);

         handler.postDelayed(() -> {
            viewModel.updateOrderStatus(orderId, "completed");
        }, 6000);
    }


    private void updateOrderUI(String status, String orderDate) {
        int orange = ContextCompat.getColor(requireContext(), R.color.orange);
        int gray = ContextCompat.getColor(requireContext(), android.R.color.darker_gray);

        binding.currentOrderTime.setText(orderDate);

        switch (status.toLowerCase()) {
            case "pending":
                binding.currentOrderStatus.setText(getString(R.string.status_pending));
                binding.currentOrderStatus.setTextColor(gray);
                binding.currentOrderStatus.setTypeface(null, Typeface.NORMAL);

                binding.iconCart.setColorFilter(orange);
                binding.lineCartToBowl.setBackgroundColor(gray);
                binding.iconBowl.setColorFilter(gray);
                binding.lineBowlToBag.setBackgroundColor(gray);
                binding.iconBag.setColorFilter(gray);
                break;

            case "preparing":
                binding.currentOrderStatus.setText(getString(R.string.status_preparing));
                binding.currentOrderStatus.setTextColor(orange);
                binding.currentOrderStatus.setTypeface(null, Typeface.BOLD);

                binding.iconCart.setColorFilter(orange);
                binding.lineCartToBowl.setBackgroundColor(orange);
                binding.iconBowl.setColorFilter(orange);
                binding.lineBowlToBag.setBackgroundColor(orange);
                binding.iconBag.setColorFilter(gray);
                break;

            case "completed":
                binding.currentOrderStatus.setText(getString(R.string.status_completed));
                binding.currentOrderStatus.setTextColor(orange);
                binding.currentOrderStatus.setTypeface(null, Typeface.BOLD_ITALIC);

                binding.iconCart.setColorFilter(orange);
                binding.lineCartToBowl.setBackgroundColor(orange);
                binding.iconBowl.setColorFilter(orange);
                binding.lineBowlToBag.setBackgroundColor(orange);
                binding.iconBag.setColorFilter(orange);
                break;

            default:
                binding.currentOrderStatus.setText(getString(R.string.status_generic, status));
                binding.currentOrderStatus.setTextColor(gray);
                binding.currentOrderStatus.setTypeface(null, Typeface.NORMAL);

                binding.iconCart.setColorFilter(gray);
                binding.lineCartToBowl.setBackgroundColor(gray);
                binding.iconBowl.setColorFilter(gray);
                binding.lineBowlToBag.setBackgroundColor(gray);
                binding.iconBag.setColorFilter(gray);
                break;
        }
    }
}
