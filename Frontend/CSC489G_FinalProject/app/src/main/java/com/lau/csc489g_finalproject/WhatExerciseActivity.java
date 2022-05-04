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

public class WhatExerciseActivity extends AppCompatActivity {

    TextView exercise_header;
    EditText exercise_info;
    String header_to_display, user_id, picked_date, info_to_add;
    SharedPreferences shared;

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

                String post1 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8")+"&"+URLEncoder.encode("running", "UTF-8")+"="+ URLEncoder.encode(third_param, "UTF-8");
                String post2 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8")+"&"+URLEncoder.encode("dancing", "UTF-8")+"="+ URLEncoder.encode(third_param, "UTF-8");
                String post3 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8")+"&"+URLEncoder.encode("boxing", "UTF-8")+"="+ URLEncoder.encode(third_param, "UTF-8");
                String post4 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8")+"&"+URLEncoder.encode("baseball", "UTF-8")+"="+ URLEncoder.encode(third_param, "UTF-8");
                String post5 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8")+"&"+URLEncoder.encode("basketball", "UTF-8")+"="+ URLEncoder.encode(third_param, "UTF-8");
                String post6 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8")+"&"+URLEncoder.encode("football", "UTF-8")+"="+ URLEncoder.encode(third_param, "UTF-8");
                String post7 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8")+"&"+URLEncoder.encode("swimming", "UTF-8")+"="+ URLEncoder.encode(third_param, "UTF-8");
                String post8 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8")+"&"+URLEncoder.encode("skiing", "UTF-8")+"="+ URLEncoder.encode(third_param, "UTF-8");
                String post9 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8")+"&"+URLEncoder.encode("hiking", "UTF-8")+"="+ URLEncoder.encode(third_param, "UTF-8");
                String post10 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8")+"&"+URLEncoder.encode("gymnastics", "UTF-8")+"="+ URLEncoder.encode(third_param, "UTF-8");
                String post11 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8")+"&"+URLEncoder.encode("tennis", "UTF-8")+"="+ URLEncoder.encode(third_param, "UTF-8");
                String post12 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8")+"&"+URLEncoder.encode("golf", "UTF-8")+"="+ URLEncoder.encode(third_param, "UTF-8");

                if (header_to_display.equalsIgnoreCase("running")){
                    bw.write(post1);
                }
                else if (header_to_display.equalsIgnoreCase("dancing")){
                    bw.write(post2);
                }
                else if (header_to_display.equalsIgnoreCase("boxing")){
                    bw.write(post3);
                }
                else if (header_to_display.equalsIgnoreCase("baseball")){
                    bw.write(post4);
                }
                else if (header_to_display.equalsIgnoreCase("basketball")){
                    bw.write(post5);
                }
                else if (header_to_display.equalsIgnoreCase("football")){
                    bw.write(post6);
                }
                else if (header_to_display.equalsIgnoreCase("swimming")){
                    bw.write(post7);
                }
                else if (header_to_display.equalsIgnoreCase("skiing")){
                    bw.write(post8);
                }
                else if (header_to_display.equalsIgnoreCase("hiking")){
                    bw.write(post9);
                }
                else if (header_to_display.equalsIgnoreCase("gymnastics")){
                    bw.write(post10);
                }
                else if (header_to_display.equalsIgnoreCase("tennis")){
                    bw.write(post11);
                }
                else if (header_to_display.equalsIgnoreCase("golf")){
                    bw.write(post12);
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
            Intent intent = new Intent(getApplicationContext(), WhatExerciseActivity.class);
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
        }
    }

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
        header_to_display = intent.getStringExtra("destination");

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
        shared = getSharedPreferences("com.lau.csc489g_finalproject", Context.MODE_PRIVATE);
        user_id = shared.getString("id", "");
        picked_date = shared.getString("chosen_date", "");
        info_to_add = exercise_info.getText().toString();

        String url = "http://192.168.106.1/CSC498G_FinalProject_GoLight/Backend/add_exercises.php";
        DownloadTask task = new DownloadTask();
        task.execute(user_id, picked_date, info_to_add, url);
    }

}