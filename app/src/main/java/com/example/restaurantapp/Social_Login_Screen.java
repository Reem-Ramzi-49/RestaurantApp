package com.example.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Social_Login_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_social_login_screen);

        Button btnPassword = findViewById(R.id.btnPassword);
        TextView signUp = findViewById(R.id.signUp);

        btnPassword.setOnClickListener(v -> {
            Intent intent = new Intent(Social_Login_Screen.this, Authentication.class);
             startActivity(intent);
        });

        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(Social_Login_Screen.this, Authentication.class);
             intent.putExtra("open_tab", "signup");
            startActivity(intent);
        });
    }
}
