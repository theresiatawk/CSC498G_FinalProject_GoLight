package com.lau.csc489g_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class LoginActivity extends AppCompatActivity {

    EditText edit_text_email, edit_text_password;
    Button login_button;
    TextView text;
    String [] user_id;
    SharedPreferences shared;


    // Implementing the post and get request using this class
    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            String first_param = params[0];
            String second_param= params[1];

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

                // Sending the email and password to the backend
                String post1 = URLEncoder.encode("email", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("password", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8");
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
                Toast.makeText(getApplicationContext(),"Invalid Credentials", Toast.LENGTH_LONG).show();
            }
            // If result correct convert the received json object to string and go to home page
            else{
                try{
                    JSONArray array = new JSONArray(result);
                    ArrayList<Object> list = new ArrayList<>();
                    JSONObject obj;

                    for (int i = 0; i < array.length(); i ++){
                        list.add(array.get(i));
                    }
                    // receiving the user_id from the api
                    user_id = new String[array.length()];
                    obj = (JSONObject) array.get(0);
                    user_id[0] = obj.getString("user_id");
                    // adding user_id in a shared preference
                    shared = getApplicationContext().getSharedPreferences("com.lau.csc489g_finalproject", Context.MODE_PRIVATE);
                    shared.edit().putString("id",user_id[0]).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"Welcome", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();

        // Linking variables to components of the layout
        edit_text_email = (EditText) findViewById(R.id.email);
        edit_text_password = (EditText) findViewById(R.id.password);
        login_button = (Button) findViewById(R.id.buttonLogin);
        text = (TextView) findViewById(R.id.textView4);

    }
    // OnClick on sign up go to register page
    public void goToRegister(View v){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
    // on click on login check credentials with db
    public void onLogin(View v){
        String entered_email = edit_text_email.getText().toString();
        String entered_password = edit_text_password.getText().toString();

        if (entered_email.equals("") || entered_password.equals("")){
            Toast.makeText(getApplicationContext(),"Missing Info",Toast.LENGTH_LONG).show();
        }
        else {
            String url = "http://192.168.106.1/CSC498G_FinalProject_GoLight/Backend/login.php";
            DownloadTask task = new DownloadTask();
            task.execute(entered_email, entered_password, url);
        }
    }
}