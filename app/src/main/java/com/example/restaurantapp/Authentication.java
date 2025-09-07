package com.example.restaurantapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.restaurantapp.databinding.ActivityAuthenticationBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class Authentication extends AppCompatActivity {
    ActivityAuthenticationBinding binding ;
    ArrayList <String> tabsArray ;
    ArrayList <Fragment> fragmentsArray ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        tabsArray =new ArrayList<>();
        tabsArray.add("Sign IN");
        tabsArray.add("Sign UP");


        fragmentsArray =new ArrayList<>();
        fragmentsArray.add(new SignInFragment());
        fragmentsArray.add(new SignUpFragment());


        binding.viewPager.setAdapter(new AdapterAuthentication(this , fragmentsArray));
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                tab.setText(tabsArray.get(position));
            }
        }).attach();




    }
}