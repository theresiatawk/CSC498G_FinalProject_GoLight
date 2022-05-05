package com.lau.csc489g_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class FoodTrackingActivity extends AppCompatActivity {
    String [] breakfast, lunch, dinner, snack;
    String destination, user_id, picked_date;
    SharedPreferences shared;

    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            String first_param = params[0];
            String second_param = params[1];

            URL url;
            HttpURLConnection http;

            try{
                url = new URL(params[2]);

                // Opening a connection between android app and the url
                http = (HttpURLConnection) url.openConnection();

                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                // I need an Output Stream to sent params to the API
                OutputStream out_stream = http.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out_stream, "UTF-8"));

                String post1 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8");
                bw.write(post1);
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
            Intent intentt = new Intent(getApplicationContext(), WhatFoodActivity.class);
            super.onPostExecute(result);
            //If result incorrect print a toast
            if(result.equals("No data")){
                Toast.makeText(getApplicationContext(),"No data on this day", Toast.LENGTH_LONG).show();
            }
            // If result correct convert the received json object to string
            else{
                try{
                    JSONArray array = new JSONArray(result);
                    ArrayList<Object> list = new ArrayList<>();
                    JSONObject obj;

                    for (int i = 0; i < array.length(); i ++){
                        list.add(array.get(i));
                    }
                    breakfast = new String[array.length()];
                    lunch = new String[array.length()];
                    dinner = new String[array.length()];
                    snack = new String[array.length()];

                    obj = (JSONObject) array.get(0);
                    breakfast[0] = obj.getString("breakfast");
                    lunch[0] = obj.getString("lunch");
                    dinner[0] = obj.getString("dinner");
                    snack[0] = obj.getString("snack");

                    intentt.putExtra("breakfast", breakfast[0]);
                    intentt.putExtra("lunch", lunch[0]);
                    intentt.putExtra("dinner", dinner[0]);
                    intentt.putExtra("snack", snack[0]);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            intentt.putExtra("destination", destination);
            startActivity(intentt);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracking);

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();
    }

    public void goToHome(View v){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    public void goToWaterTracking(View v){
        Intent intent = new Intent(getApplicationContext(), WaterTrackingActivity.class);
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

    public void goToEdit(View v){
        // Getting the tag of the view clicked
        destination = v.getTag().toString();
        shared = getSharedPreferences("com.lau.csc489g_finalproject", Context.MODE_PRIVATE);
        user_id = shared.getString("id", "");
        picked_date = shared.getString("chosen_date", "");

        String url = "http://192.168.106.1/CSC498G_FinalProject_GoLight/Backend/food_track.php";
        DownloadTask task = new DownloadTask();
        task.execute(user_id, picked_date, url);
    }
}