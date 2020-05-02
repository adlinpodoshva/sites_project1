package com.misha.sitesproject.authentication_activities;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.misha.sitesproject.R;
import com.misha.sitesproject.area_activities.AreaSelectionActivity;
import com.misha.sitesproject.data.User;

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
        if(getInputEmail().isEmpty() || getInputPassword().isEmpty() || getInputFullName().isEmpty()
            || getInputPhone().isEmpty()) {
            Toast.makeText(this, "יש למלא את כל השדות", Toast.LENGTH_LONG).show();
            return;
        }
        if(!AuthenticationUtils.isValidEmail(getInputEmail())) {
            Toast.makeText(this, "יש להזין כתובת דואר אלקטרוני תקינה", Toast.LENGTH_LONG).show();
            return;
        }
        if(getInputPassword().length() < AuthenticationUtils.MIN_PASSWORD_LENGTH) {
            Toast.makeText(this, String.format("על הסיסמה להכיל לפחות %d תווים",
                    AuthenticationUtils.MIN_PASSWORD_LENGTH),
                    Toast.LENGTH_LONG).show();
            return;
        }
        if(!AuthenticationUtils.isValidPhone(getInputPhone())) {
            Toast.makeText(this, "יש להזין מספר טלפון תקין", Toast.LENGTH_LONG).show();
            return;
        }

        final User user = new User(getInputEmail(), getInputFullName(), getInputPhone());
        final FirebaseAuth auth = FirebaseAuth.getInstance();

        // create user account
        auth.createUserWithEmailAndPassword(user.getEmail(), getInputPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    // add user data to database
                    DatabaseReference dbRoot = FirebaseDatabase.getInstance().getReference();
                    dbRoot.child("users").child(auth.getUid()).setValue(user);
                    Toast.makeText(SignupActivity.this, "נרשמתם בהצלחה", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SignupActivity.this, AreaSelectionActivity.class));
                } else {
                    Toast.makeText(SignupActivity.this, "לא היה ניתן להשלים את ההרשמה, אנא נסו שנית",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
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

    private String getInputPhone() {
        EditText phoneEditText = findViewById(R.id.phoneEditText);
        return phoneEditText.getText().toString().trim();
    }
}
