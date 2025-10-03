package com.example.restaurantapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class AdminOrdersFragment extends Fragment {

    private AppViewModel viewModel;
    private AdapterOrders adapter;

    private static final int REQUEST_CODE_NOTIFICATIONS = 200;
    private static final String CHANNEL_ID = "orders_channel";

    public AdminOrdersFragment() {}

    public static AdminOrdersFragment newInstance() {
        return new AdminOrdersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_orders, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerOrdersAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        adapter = new AdapterOrders((AdapterOrders.OnAdminOrderClickListener) order -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.dialog_manage_order) + " #" + order.getDisplay_number())
                    .setMessage(getString(R.string.dialog_user) + ": " + order.getUserName() +
                            "\n" + getString(R.string.dialog_status) + ": " + order.getStatus())
                    .setPositiveButton(getString(R.string.dialog_confirm), (dialog, which) -> {
                        viewModel.updateOrderStatus(order.getOrder_id(), "confirmed");

                        viewModel.getUserById(order.getUser_id()).observe(getViewLifecycleOwner(), user -> {
                            if (user != null) {
                                showNotification(
                                        order.getUser_id(),
                                        getString(R.string.order_confirmed_title),
                                        getString(R.string.order_confirmed_msg, user.getName())
                                );
                            }
                        });

                        Toast.makeText(getContext(), getString(R.string.order_confirmed), Toast.LENGTH_SHORT).show();
                    })
                    .setNeutralButton(getString(R.string.dialog_complete), (dialog, which) -> {
                        viewModel.updateOrderStatus(order.getOrder_id(), "completed");

                        viewModel.getUserById(order.getUser_id()).observe(getViewLifecycleOwner(), user -> {
                            if (user != null) {
                                showNotification(
                                        order.getUser_id(),
                                        getString(R.string.order_completed_title),
                                        getString(R.string.order_completed_msg1, user.getName())
                                );
                            }
                        });

                        Toast.makeText(getContext(), getString(R.string.order_completed), Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton(getString(R.string.dialog_delete), (dialog, which) -> {
                        viewModel.deleteOrder(order.getOrder_id());
                        Toast.makeText(getContext(), getString(R.string.order_deleted), Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });

        recyclerView.setAdapter(adapter);

        viewModel.getActiveOrdersWithUser().observe(getViewLifecycleOwner(), (List<OrderWithUser> orders) -> {
            adapter.setOrdersWithUser(orders);
        });

        return root;
    }

    private void showNotification(int id, String title, String message) {
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
                .setSmallIcon(R.drawable.icon_cart)
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
                    getString(R.string.channel_orders),
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = requireContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
