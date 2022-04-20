package com.lau.csc489g_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class WaterTrackingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_tracking);

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();
    }
}