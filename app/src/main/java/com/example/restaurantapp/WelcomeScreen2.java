package com.example.restaurantapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.restaurantapp.databinding.ActivityWelcomeScreen1Binding;
import com.example.restaurantapp.databinding.ActivityWelcomeScreen2Binding;

public class WelcomeScreen2 extends AppCompatActivity {
    ActivityWelcomeScreen2Binding binding ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding =ActivityWelcomeScreen2Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        // زر الانتقال للشاشة 3
        binding.btnGetStarted.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeScreen2.this, WelcomeScreem3.class);

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    WelcomeScreen2.this,
                    Pair.create(binding.foodTopLeft, "foodTopLeft"),
                    Pair.create(binding.foodTopRight, "foodTopRight"),
                    Pair.create(binding.foodCenter, "foodCenter"),
                    Pair.create(binding.foodLeft, "foodLeft"),
                    Pair.create(binding.foodRight, "foodRight")
            );

            startActivity(intent, options.toBundle());
        });

    }
}