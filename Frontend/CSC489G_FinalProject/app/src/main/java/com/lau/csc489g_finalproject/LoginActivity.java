package com.lau.csc489g_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();
    }
}