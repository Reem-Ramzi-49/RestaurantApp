package com.example.restaurantapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.restaurantapp.databinding.ActivityWelcomeScreem3Binding;
import com.example.restaurantapp.databinding.ActivityWelcomeScreen2Binding;

public class WelcomeScreem3 extends AppCompatActivity {
    ActivityWelcomeScreem3Binding binding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding =ActivityWelcomeScreem3Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.btnGetStarted.setOnClickListener(view -> {
            Intent intent = new Intent(WelcomeScreem3.this, Social_Login_Screen.class);

            startActivity(intent);
        });

    }
}