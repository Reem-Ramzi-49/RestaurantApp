package com.example.restaurantapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

        binding.recyclerOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        int userId = SignInFragment.SessionManager.getUserId(requireContext());

        viewModel.getUserById(userId).observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                if (user.getRole() == 1) {
                    // ✅ أدمن
                    adapter = new AdapterOrders((AdapterOrders.OnAdminOrderClickListener) order -> {
                        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                                .setTitle("إدارة الطلب #" + order.getDisplay_number())
                                .setMessage("المستخدم: " + order.getUserName() + "\nالحالة: " + order.getStatus())
                                .setPositiveButton("تأكيد", (dialog, which) -> {
                                    viewModel.updateOrderStatus(order.getOrder_id(), "confirmed");
                                    Toast.makeText(getContext(), "تم تأكيد الطلب ✅", Toast.LENGTH_SHORT).show();
                                })
                                .setNeutralButton("اكتمال", (dialog, which) -> {
                                    viewModel.updateOrderStatus(order.getOrder_id(), "completed");
                                    Toast.makeText(getContext(), "تم اكتمال الطلب ✅", Toast.LENGTH_SHORT).show();
                                })
                                .setNegativeButton("حذف", (dialog, which) -> {
                                    viewModel.deleteOrder(order.getOrder_id());
                                    Toast.makeText(getContext(), "تم حذف الطلب ❌", Toast.LENGTH_SHORT).show();
                                })
                                .show();
                    });

                    binding.recyclerOrders.setAdapter(adapter);

                    viewModel.getAllOrdersWithUser().observe(getViewLifecycleOwner(), orders -> {
                        adapter.setOrdersWithUser(orders);
                    });

                } else {
                    // ✅ مستخدم عادي
                    adapter = new AdapterOrders((AdapterOrders.OnOrderClickListener) null);
                    binding.recyclerOrders.setAdapter(adapter);

                    viewModel.getOrdersByUser(userId).observe(getViewLifecycleOwner(), orders -> {
                        adapter.setOrders(orders);

                        if (orders != null && !orders.isEmpty()) {
                            Entity_Orders current = orders.get(0);
                            binding.currentOrderCard.setVisibility(View.VISIBLE);

                            Log.d("ORDER_FLOW", "UI received status = " + current.getStatus());
                            updateOrderUI(current.getStatus(), current.getOrder_date());
                        } else {
                            binding.currentOrderCard.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });

        return binding.getRoot();
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

            case "confirmed":
                binding.currentOrderStatus.setText("Confirmed");
                binding.currentOrderStatus.setTextColor(orange);
                binding.currentOrderStatus.setTypeface(null, Typeface.BOLD);

                binding.iconCart.setColorFilter(orange);
                binding.lineCartToBowl.setBackgroundColor(orange);
                binding.iconBowl.setColorFilter(orange);
                binding.lineBowlToBag.setBackgroundColor(gray);
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
