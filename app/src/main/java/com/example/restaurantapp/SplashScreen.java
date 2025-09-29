package com.example.restaurantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
              SharedPreferences sp = getSharedPreferences("session", MODE_PRIVATE);
            boolean loggedIn = sp.getBoolean("logged_in", false);

            if (loggedIn) {
                 startActivity(new Intent(SplashScreen.this, MainActivity.class));
            } else {
                 startActivity(new Intent(SplashScreen.this, WelcomeScreen1.class));
                         }

            finish();
        }, 3000);

    }
}