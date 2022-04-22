package com.lau.csc489g_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText edit_text_first_name, edit_text_last_name, edit_text_email, edit_text_password, edit_text_gender;
    EditText edit_text_birth, edit_text_height, edit_text_weight;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();

        edit_text_first_name = (EditText) findViewById(R.id.first_name);
        edit_text_last_name = (EditText) findViewById(R.id.last_name);
        edit_text_email = (EditText) findViewById(R.id.email_signup);
        edit_text_password = (EditText) findViewById(R.id.password_signup);
        edit_text_gender = (EditText) findViewById(R.id.gender);
        edit_text_birth = (EditText) findViewById(R.id.birth);
        edit_text_weight = (EditText) findViewById(R.id.weight);
        edit_text_height = (EditText) findViewById(R.id.height);
        text = (TextView) findViewById(R.id.text_signup);

    }
    public void goToLogin(View v){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }
    public void onSignUp(View v){
        String entered_first_name = edit_text_first_name.getText().toString();
        String entered_last_name = edit_text_last_name.getText().toString();
        String entered_email = edit_text_email.getText().toString();
        String entered_password = edit_text_password.getText().toString();
        String entered_gender = edit_text_gender.getText().toString();
        String entered_birth = edit_text_birth.getText().toString();
        String entered_weight = edit_text_weight.getText().toString();
        String entered_height = edit_text_height.getText().toString();

        if(!entered_first_name.matches( "[a-zA-Z]*" ) || entered_first_name.length() == 0){
            text.setText("Invalid first name format!");
        }
        else if(!entered_last_name.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" ) || entered_last_name.length() == 0){
            text.setText("Invalid last name format!");
        }
        else if(!(entered_email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") || entered_email.length() == 0)){
            text.setText("Invalid email format!");
        }
        else if(entered_password.length()<8 || !isValidPassword(entered_password)){
            text.setText("Password must contain minimum 8 characters at least 1 Alphabet, 1 Number and 1 Special Character");
        }
         else if(!isValidDate(entered_birth) || entered_birth.length() == 0){
            text.setText("Invalid birth date format! Make sure it will be of the form dd/MM/yyyy");
        }
        else if(!entered_gender.equalsIgnoreCase("male") && !entered_gender.equalsIgnoreCase("female") && !entered_gender.equalsIgnoreCase("none")){
            text.setText("Invalid gender format! Please enter female, male or none");
        }
        else if(!entered_height.matches( "[0-9]*" ) || entered_height.length() == 0){
            text.setText("Invalid height format!");
        }
        else if(!entered_weight.matches( "[0-9]*" ) || entered_weight.length() == 0){
            text.setText("Invalid weight format!");
        }

    }

    // Checking whether the entered password contains minimum 8 characters, at least 1 Alphabet, 1 Number and 1 Special Character
    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidDate(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            Date d1 = sdf.parse(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}