package com.lau.csc489g_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WhatFoodActivity extends AppCompatActivity {

    TextView food_header;
    EditText food_info;
    String header_to_display, user_id, picked_date, info_to_add;
    SharedPreferences shared;

    // Implementing the post request of info using this class
    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            String first_param = params[0];
            String second_param = params[1];
            String third_param = params[2];

            URL url;
            HttpURLConnection http;

            try{
                url = new URL(params[3]);

                // Opening a connection between android app and the url
                http = (HttpURLConnection) url.openConnection();

                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                // I need an Output Stream to sent params to the API
                OutputStream out_stream = http.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out_stream, "UTF-8"));

                // Sending the information entered to a specific attribute in the database table
                String post1 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8")+"&"+URLEncoder.encode("breakfast", "UTF-8")+"="+ URLEncoder.encode(third_param, "UTF-8");
                String post2 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8")+"&"+URLEncoder.encode("lunch", "UTF-8")+"="+ URLEncoder.encode(third_param, "UTF-8");
                String post3 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8")+"&"+URLEncoder.encode("dinner", "UTF-8")+"="+ URLEncoder.encode(third_param, "UTF-8");
                String post4 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8")+"&"+URLEncoder.encode("snack", "UTF-8")+"="+ URLEncoder.encode(third_param, "UTF-8");

                if (header_to_display.equalsIgnoreCase("breakfast")){
                    bw.write(post1);
                }
                else if (header_to_display.equalsIgnoreCase("lunch")){
                    bw.write(post2);
                }
                else if (header_to_display.equalsIgnoreCase("dinner")){
                    bw.write(post3);
                }
                else if (header_to_display.equalsIgnoreCase("snacks")){
                    bw.write(post4);
                }
                bw.flush();
                bw.close();
                out_stream.close();

                // Reading the result from the API
                InputStream in_stream = http.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in_stream, "iso-8859-1"));
                String result = "";
                String line = "";
                while((line = br.readLine())!= null){
                    result += line;
                }
                br.close();
                in_stream.close();
                http.disconnect();
                return result;
            }
            catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_food);

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();

        // Linking variables to components of the layout
        food_header = (TextView) findViewById(R.id.header);
        food_info = (EditText) findViewById(R.id.food_info);

        // Getting the tag transferred from Food activity page
        Intent intent = getIntent();
        header_to_display = intent.getStringExtra("destination");

        // Getting all the transferred data from the previous page and display one of them based on the header of the page
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
    // On click on the home icon go to the home page
    public void goToHome(View v){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }
    // On click on the water icon go to the water page
    public void goToWaterTracking(View v){
        Intent intent = new Intent(getApplicationContext(), WaterTrackingActivity.class);
        startActivity(intent);
    }
    // On click on the fork and knife icon go to the food page
    public void goToFoodTracking(View v){
        Intent intent = new Intent(getApplicationContext(), FoodTrackingActivity.class);
        startActivity(intent);
    }
    // On click on the dumbbell icon go to the exercise page
    public void goToExerciseTracking(View v){
        Intent intent = new Intent(getApplicationContext(), ExerciseTrackingActivity.class);
        startActivity(intent);
    }
    // On click on the user icon go to the profile page
    public void goToProfile(View v){
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }
    // On click on save button update the database accordingly using the class of post request
    public void addInfo(View v){
        // Getting the user_id and date from shared preference
        shared = getSharedPreferences("com.lau.csc489g_finalproject", Context.MODE_PRIVATE);
        user_id = shared.getString("id", "");
        picked_date = shared.getString("chosen_date", "");

        // Getting the info entered to add them to the database
        info_to_add = food_info.getText().toString();

        String url = "http://192.168.106.1/CSC498G_FinalProject_GoLight/Backend/add_food.php";
        DownloadTask task = new DownloadTask();
        task.execute(user_id, picked_date, info_to_add, url);
    }

}