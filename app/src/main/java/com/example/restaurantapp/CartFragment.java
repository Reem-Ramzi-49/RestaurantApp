package com.example.restaurantapp;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantapp.databinding.FragmentCartBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private AppViewModel viewModel;
    private AdapterCart adapter;

    private static final int REQUEST_CODE_NOTIFICATIONS = 101;
    private static final String CHANNEL_ID = "orders_channel";

    public CartFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        adapter = new AdapterCart(new ArrayList<>(), new AdapterCart.OnCartActionListener() {
            @Override
            public void onIncrease(CartItemDTO item) {
                viewModel.addToCart(item.getDishId(), 1);
            }

            @Override
            public void onDecrease(CartItemDTO item) {
                if (item.getQuantity() > 1) {
                    viewModel.setCartQuantity(item.getCartId(), item.getQuantity() - 1);
                } else {
                    viewModel.deleteCartItem(item.getCartId());
                }
            }

            @Override
            public void onDelete(CartItemDTO item) {
                viewModel.deleteCartItem(item.getCartId());
            }
        });

        binding.recyclerCart.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerCart.setAdapter(adapter);

        viewModel.observeCartItems().observe(getViewLifecycleOwner(), items -> {
            adapter.setItems(items);
        });

        viewModel.observeCartTotal().observe(getViewLifecycleOwner(), subtotal -> {
            if (subtotal == null) subtotal = 0.0;
            double fee = 3.99;
            double total = subtotal + fee;

            binding.cartSubtotal.setText(String.format("$%.2f", subtotal));
            binding.cartFee.setText(String.format("$%.2f", fee));
            binding.cartTotal.setText(String.format("$%.2f", total));
        });

        binding.btnPlaceOrder.setOnClickListener(v -> {
            if (adapter.getItemCount() == 0) {
                Toast.makeText(getContext(), getString(R.string.order_empty), Toast.LENGTH_SHORT).show();
                return;
            }

            View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm_order, null);

            AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.CustomDialog)
                    .setView(dialogView)
                    .create();

            Button btnCancel = dialogView.findViewById(R.id.btnCancel);
            Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);

            btnCancel.setOnClickListener(view -> dialog.dismiss());

            btnConfirm.setOnClickListener(view -> {
                int userId = SignInFragment.SessionManager.getUserId(requireContext());
                 viewModel.placeOrderNow("pending", userId);

                showNotification(
                        1001,
                        getString(R.string.order_pending),
                        getString(R.string.order_pending_msg),
                        R.drawable.icon_cart
                );

                BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottomNavigation);
                bottomNav.setSelectedItemId(R.id.order_bottom);

                viewModel.clearCart();

                Toast.makeText(getContext(), getString(R.string.order_confirm_success), Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            });

            dialog.show();
        });

        return binding.getRoot();
    }

    private void showNotification(int id, String title, String message, int iconRes) {
        createNotificationChannel();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(requireContext(),
                    android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_CODE_NOTIFICATIONS);
                return;
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(iconRes)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
        notificationManager.notify(id, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Orders",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = requireContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
