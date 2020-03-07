package com.example.misha;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onSigninButtonClick(View v) {
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
    }


    public void onSignupButtonClick(View v) {
        if(getInputEmail().isEmpty() || getInputPassword().isEmpty() || getInputFullName().isEmpty()) {
            Toast.makeText(this, "Input fields must not be empty.", Toast.LENGTH_LONG).show();
            return;
        }

        if(!isValidEmail(getInputEmail())) {
            Toast.makeText(this, "Email must ve valid.", Toast.LENGTH_LONG).show();
            return;
        }
        if(getInputPassword().length() < 6) {
            Toast.makeText(this, "Password must contain at least 6 characters.", Toast.LENGTH_LONG).show();
            return;
        }

        // register
    }

    private String getInputFullName() {
        EditText fullNameEditText = findViewById(R.id.fullNameEditText);
        return fullNameEditText.getText().toString().trim();
    }

    private String getInputPassword() {
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        return passwordEditText.getText().toString().trim();
    }

    private String getInputEmail() {
        EditText emailEditText = findViewById(R.id.emailEditText);
        return emailEditText.getText().toString().trim();
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
