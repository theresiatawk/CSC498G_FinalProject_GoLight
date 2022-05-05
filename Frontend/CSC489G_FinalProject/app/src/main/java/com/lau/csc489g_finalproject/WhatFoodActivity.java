package com.lau.csc489g_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class WhatFoodActivity extends AppCompatActivity {

    TextView food_header;
    EditText food_info;
    String header_to_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_food);

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();

        food_header = (TextView) findViewById(R.id.header);
        food_info = (EditText) findViewById(R.id.food_info);

        // Getting the tag transferred from Food activity page
        Intent intent = getIntent();
        header_to_display = intent.getStringExtra("destination");

        String breakfast = intent.getStringExtra("breakfast");
        String lunch = intent.getStringExtra("lunch");
        String dinner = intent.getStringExtra("dinner");
        String snack = intent.getStringExtra("snack");

        food_header.setText(header_to_display);

        if(breakfast != "" && header_to_display.equalsIgnoreCase("breakfast")){
            food_info.setText(breakfast);
        }
        else if(lunch != "" && header_to_display.equalsIgnoreCase("lunch")){
            food_info.setText(lunch);
        }
        else if(dinner != "" && header_to_display.equalsIgnoreCase("dinner")){
            food_info.setText(dinner);
        }
        else if(snack != "" && header_to_display.equalsIgnoreCase("snacks")){
            food_info.setText(snack);
        }
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