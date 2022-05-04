package com.lau.csc489g_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class WhatExerciseActivity extends AppCompatActivity {

    TextView exercise_header;
    EditText exercise_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_exercise);

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();

        exercise_header = (TextView) findViewById(R.id.exercise_header);
        exercise_info = (EditText) findViewById(R.id.exercise_info);
        // Getting the tag transferred from Food activity page
        Intent intent = getIntent();
        String header_to_display = intent.getStringExtra("destination");

        String running = intent.getStringExtra("running");
        String dancing = intent.getStringExtra("dancing");
        String boxing = intent.getStringExtra("boxing");
        String baseball = intent.getStringExtra("baseball");
        String basketball = intent.getStringExtra("basketball");
        String football = intent.getStringExtra("football");
        String swimming = intent.getStringExtra("swimming");
        String skiing = intent.getStringExtra("skiing");
        String hiking = intent.getStringExtra("hiking");
        String gymnastics = intent.getStringExtra("gymnastics");
        String tennis = intent.getStringExtra("tennis");
        String golf = intent.getStringExtra("golf");
        exercise_header.setText(header_to_display);

        if(running != "" && header_to_display.equalsIgnoreCase("running")){
            exercise_info.setText(running);
        }
        else if(dancing != "" && header_to_display.equalsIgnoreCase("dancing")){
            exercise_info.setText(dancing);
        }
        else if(boxing != "" && header_to_display.equalsIgnoreCase("boxing")){
            exercise_info.setText(boxing);
        }
        else if(baseball != "" && header_to_display.equalsIgnoreCase("baseball")){
            exercise_info.setText(baseball);
        }
        else if(basketball != "" && header_to_display.equalsIgnoreCase("basketball")){
            exercise_info.setText(basketball);
        }
        else if(football != "" && header_to_display.equalsIgnoreCase("football")){
            exercise_info.setText(football);
        }
        else if(swimming != "" && header_to_display.equalsIgnoreCase("swimming")){
            exercise_info.setText(swimming);
        }
        else if(skiing != "" && header_to_display.equalsIgnoreCase("skiing")){
            exercise_info.setText(skiing);
        }
        else if(hiking != "" && header_to_display.equalsIgnoreCase("hiking")){
            exercise_info.setText(hiking);
        }
        else if(gymnastics != "" && header_to_display.equalsIgnoreCase("gymnastics")){
            exercise_info.setText(gymnastics);
        }
        else if(tennis != "" && header_to_display.equalsIgnoreCase("tennis")){
            exercise_info.setText(tennis);
        }
        else if(golf != "" && header_to_display.equalsIgnoreCase("golf")){
            exercise_info.setText(golf);
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
    public void addInfo(View v){

    }

}