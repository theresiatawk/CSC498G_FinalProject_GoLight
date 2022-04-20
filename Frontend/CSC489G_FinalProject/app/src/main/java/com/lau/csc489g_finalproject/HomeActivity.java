package com.lau.csc489g_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();
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