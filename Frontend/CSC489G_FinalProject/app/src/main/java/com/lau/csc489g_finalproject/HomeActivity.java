package com.lau.csc489g_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    double weight_value, height_value, bmi_value;
    String picked_date, user_id, bmi, date_to_display;
    String [] weight, height;
    TextView bmi_analysis, bmi_result, select_date;
    SharedPreferences shared;
    DatePickerDialog date_picker_dialog;
    Button date_button;

    // Implementing the post and get request using this class
    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            String first_param = params[0];

            URL url;
            HttpURLConnection http;

            try{
                url = new URL(params[1]);

                // Opening a connection between android app and the url
                http = (HttpURLConnection) url.openConnection();

                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                // I need an Output Stream to sent params to the API
                OutputStream out_stream = http.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out_stream, "UTF-8"));

                // Sending user_id to the api in order to fetch user_information
                String post1 = URLEncoder.encode("user_id", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8");
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
            super.onPostExecute(result);
            //If user_id is not found show a toast
            if(result.equals("Invalid user.")){
                Toast.makeText(getApplicationContext(),"Invalid Credentials", Toast.LENGTH_LONG).show();
            }
            // If result correct convert the received json object to string and get the needed info
            else{
                try{
                    JSONArray array = new JSONArray(result);
                    ArrayList<Object> list = new ArrayList<>();
                    JSONObject obj;

                    for (int i = 0; i < array.length(); i ++){
                        list.add(array.get(i));
                    }
                    // Getting the height and weight f this specific user
                    weight = new String[array.length()];
                    height = new String[array.length()];

                    obj = (JSONObject) array.get(0);
                    weight[0] = obj.getString("weight");
                    height[0] = obj.getString("height");

                    // Calculating the bmi result of the logged in user
                    weight_value = Double.parseDouble(weight[0]);
                    height_value = Double.parseDouble(height[0]);
                    DecimalFormat df = new DecimalFormat("#.###");
                    bmi = df.format(weight_value/((height_value/100)*(height_value/100)));
                    bmi_result.setText("Your BMI is "+ bmi);
                    bmi_value = Double.parseDouble(bmi);

                    // Analyzing the bmi result
                    if (bmi_value < 18){
                        bmi_analysis.setText("You are Under Weight");
                    }
                    else if(bmi_value <= 25){
                        bmi_analysis.setText("Your are Normal");
                    }
                    else if(bmi_value <= 28){
                        bmi_analysis.setText("You are Over Weight");
                    }
                    else{
                        bmi_analysis.setText("You are Malnourished");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();

        // Linking variables to components of the layout
        bmi_analysis = (TextView) findViewById(R.id.bmi_analysis);
        bmi_result = (TextView) findViewById(R.id.bmi_result);
        date_button = findViewById(R.id.datePickerButton);
        select_date = findViewById(R.id.select_date);

        // Get the user id from the shared preference
        shared = getSharedPreferences("com.lau.csc489g_finalproject", Context.MODE_PRIVATE);
        user_id = shared.getString("id","");

        // Call the class to fetch info of the user and display bmi
        String url = "http://192.168.106.1/CSC498G_FinalProject_GoLight/Backend/getProfile.php";
        HomeActivity.DownloadTask task = new HomeActivity.DownloadTask();
        task.execute(user_id,url);

        // initiate a date picker
        initDatePicker();

        if (picked_date != ""){
            date_button.setText(date_to_display);
        }
        else {
            date_button.setText(getTodaysDate());
        }


    }
    // Diplay automatically today's date
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }
    // taking the date and storing it in a hared preference
    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                // Since january by default is 0
                month = month + 1;
                String date = makeDateString(day, month, year);
                date_button.setText(date);
                picked_date = day + "" + month + "" + year;
                shared.edit().putString("chosen_date",picked_date).commit();
                date_button.setText(date);
                date_to_display = date;
            }
        };

        // Getting Today's Date
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        date_picker_dialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        // Setting the maximum date to be today's date
        //date_picker_dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    // Extracting the date as a string
    private String makeDateString(int day, int month, int year)
    {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    // diplaying the month in a specific format
    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    // Show the date picker once clicking on it
    public void openDatePicker(View view)
    {

        date_picker_dialog.show();
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
    public void goToExerciseTracking(View v) {
        Intent intent = new Intent(getApplicationContext(), ExerciseTrackingActivity.class);
        startActivity(intent);
    }
    // On click on the user icon go to the profile page
    public void goToProfile(View v){
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }
}