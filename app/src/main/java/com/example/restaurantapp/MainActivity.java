package com.example.restaurantapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.restaurantapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home_bottom){}

                else if (item.getItemId() ==R.id.order_bottom) {
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.container , OrderFragment.newInstance("Order")).commit();
                }

                  else if (item.getItemId() ==R.id.cart_bottom) {
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.container , CartFragment.newInstance("Cart" ,"")).commit();
                }

                  else if (item.getItemId() ==R.id.profile_bottom) {
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.container , ProfileFragment.newInstance("Profile" ,"")).commit();
                }


                return true;
            }
        });


    }
}