package com.example.restaurantapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurantapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private AppViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AppViewModel.class);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new HomeFragment())
                    .commit();
        }

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.home_bottom) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new HomeFragment())
                            .commit();

                } else if (item.getItemId() == R.id.order_bottom) {
                    int userId = SignInFragment.SessionManager.getUserId(MainActivity.this);
                    viewModel.getUserById(userId).observe(MainActivity.this, user -> {
                        if (user != null && user.getRole() == 1) {
                            // أدمن → عرض جميع الطلبات
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, new AdminOrdersFragment())
                                    .commit();
                        } else {
                            // مستخدم عادي → عرض طلباته فقط
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, new OrderFragment())
                                    .commit();
                        }
                    });

                } else if (item.getItemId() == R.id.cart_bottom) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new CartFragment())
                            .commit();

                } else if (item.getItemId() == R.id.profile_bottom) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new ProfileFragment())
                            .commit();

                } else if (item.getItemId() == R.id.map_bottom) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new MapFragment())
                            .commit();
                }

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        int selectedItemId = binding.bottomNavigation.getSelectedItemId();
        if (selectedItemId != R.id.home_bottom) {
            binding.bottomNavigation.setSelectedItemId(R.id.home_bottom);
        } else {
            super.onBackPressed();
        }
    }
}
