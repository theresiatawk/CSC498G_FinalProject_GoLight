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

public class ExerciseTrackingActivity extends AppCompatActivity {

    String[] running, dancing, boxing, baseball, basketball, football, swimming, skiing, hiking, gymnastics, tennis, golf;
    String user_id, picked_date, destination;
    String running_info, dancing_info, boxing_info, baseball_info, basketball_info, football_info, swimming_info, skiing_info, hiking_info, gymnastics_info, tennis_info, golf_info;
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
            Intent intent = new Intent(getApplicationContext(), WhatExerciseActivity.class);
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
                    running = new String[array.length()];
                    dancing = new String[array.length()];
                    boxing = new String[array.length()];
                    baseball = new String[array.length()];
                    basketball = new String[array.length()];
                    football = new String[array.length()];
                    swimming = new String[array.length()];
                    skiing = new String[array.length()];
                    hiking = new String[array.length()];
                    gymnastics = new String[array.length()];
                    tennis = new String[array.length()];
                    golf = new String[array.length()];

                    obj = (JSONObject) array.get(0);
                    running[0] = obj.getString("running");
                    dancing[0] = obj.getString("dancing");
                    boxing[0] = obj.getString("boxing");
                    baseball[0] = obj.getString("baseball");
                    basketball[0] = obj.getString("basketball");
                    football[0] = obj.getString("football");
                    swimming[0] = obj.getString("swimming");
                    skiing[0] = obj.getString("skiing");
                    hiking[0] = obj.getString("hiking");
                    gymnastics[0] = obj.getString("gymnastics");
                    tennis[0] = obj.getString("tennis");
                    golf[0] = obj.getString("golf");

                    intent.putExtra("running", running[0]);
                    intent.putExtra("dancing", dancing[0]);
                    intent.putExtra("boxing", boxing[0]);
                    intent.putExtra("baseball", baseball[0]);
                    intent.putExtra("basketball", basketball[0]);
                    intent.putExtra("football", football[0]);
                    intent.putExtra("swimming", swimming[0]);
                    intent.putExtra("skiing", skiing[0]);
                    intent.putExtra("hiking", hiking[0]);
                    intent.putExtra("gymnastics", gymnastics[0]);
                    intent.putExtra("tennis", tennis[0]);
                    intent.putExtra("golf", golf[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            intent.putExtra("destination", destination);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_tracking);

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

    public void goToFoodTracking(View v){
        Intent intent = new Intent(getApplicationContext(), FoodTrackingActivity.class);
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

        String url = "http://192.168.106.1/CSC498G_FinalProject_GoLight/Backend/exercise_track.php";
        DownloadTask task = new DownloadTask();
        task.execute(user_id, picked_date, url);
    }
}