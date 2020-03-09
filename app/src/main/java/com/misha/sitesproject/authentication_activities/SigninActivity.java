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
import com.misha.sitesproject.R;
import com.misha.sitesproject.area_activities.AreaSelectionActivity;

public class SigninActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
    }

    public void onSigninButtonClick(View v) {
        if(getInputEmail().isEmpty() || getInputPassword().isEmpty()) {
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

        final FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(getInputEmail(), getInputPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(SigninActivity.this, "התחברתם בהצלחה", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SigninActivity.this, AreaSelectionActivity.class));
                }
                else {
                    Toast.makeText(SigninActivity.this, "בעיה בהתחברות, אנא ודאו את נכונות הפרטים ונסו שנית",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private String getInputPassword() {
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        return passwordEditText.getText().toString().trim();
    }

    private String getInputEmail() {
        EditText emailEditText = findViewById(R.id.emailEditText);
        return emailEditText.getText().toString().trim();
    }
}
