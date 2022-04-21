package com.lau.csc489g_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WhatFoodActivity extends AppCompatActivity {

    TextView header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_food);

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();

        header = (TextView) findViewById(R.id.header);

        // Getting the tag transferred from Food activity page
        Intent intent = getIntent();
        String header_to_display = intent.getStringExtra("destination");
        header.setText(header_to_display);
    }
    public void goToHome(View v){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    public void goToWaterTracking(View v){
        Intent intent = new Intent(getApplicationContext(), WaterTrackingActivity.class);
        startActivity(intent);
    }

    public void goToFoodTracking(View v){
        Intent intent = new Intent(getApplicationContext(), FoodTrackingActivity.class);
        startActivity(intent);
    }

    public void goToExerciseTracking(View v){
        Intent intent = new Intent(getApplicationContext(), ExerciseTrackingActivity.class);
        startActivity(intent);
    }
    public void goToProfile(View v){
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }

}