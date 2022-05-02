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
import org.w3c.dom.Text;

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

public class ProfileActivity extends AppCompatActivity {

    SharedPreferences shared;
    String user_id;
    String [] first_name, last_name, email, gender, weight, height, date_of_birth;
    TextView username;
    EditText birth_date_text, mail_text, gender_text,weight_text, height_text;
    // Implementing the post request using this class
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
            //If result incorrect print a toast
            if(result.equals("Incorrect Username or password")){
                username.setText(result);
                Toast.makeText(getApplicationContext(),"Invalid Credentials", Toast.LENGTH_LONG).show();
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
                    first_name = new String[array.length()];
                    last_name = new String[array.length()];
                    email = new String[array.length()];
                    gender = new String[array.length()];
                    weight = new String[array.length()];
                    height = new String[array.length()];
                    date_of_birth = new String[array.length()];

                    obj = (JSONObject) array.get(0);
                    first_name[0] = obj.getString("first_name");
                    last_name[0] = obj.getString("last_name");
                    email[0] = obj.getString("email");
                    gender[0] = obj.getString("gender");
                    weight[0] = obj.getString("weight");
                    height[0] = obj.getString("height");
                    date_of_birth[0] = obj.getString("date_of_birth");

                    username.setText(first_name[0]+" "+last_name[0]);
                    birth_date_text.setText(date_of_birth[0]);
                    mail_text.setText(email[0]);
                    gender_text.setText(gender[0]);
                    weight_text.setText(weight[0]);
                    height_text.setText(height[0]);
                    birth_date_text.setText(date_of_birth[0]);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();
        username = (TextView) findViewById(R.id.username);
        birth_date_text = (EditText) findViewById(R.id.date_of_birth);
        mail_text = (EditText) findViewById(R.id.user_email);
        gender_text = (EditText) findViewById(R.id.user_gender);
        weight_text = (EditText) findViewById(R.id.user_weight);
        height_text = (EditText) findViewById(R.id.user_height);

        shared = getSharedPreferences("com.lau.csc489g_finalproject", Context.MODE_PRIVATE);
        user_id = shared.getString("id","");
        String url = "http://192.168.106.1/CSC498G_FinalProject_GoLight/Backend/getProfile.php";
        ProfileActivity.DownloadTask task = new ProfileActivity.DownloadTask();
        task.execute(user_id,url);
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
    public void editInfo(View v){
        
    }
}