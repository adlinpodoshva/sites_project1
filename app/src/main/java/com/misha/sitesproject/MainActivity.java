package com.misha.sitesproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.misha.sitesproject.area_activities.AreaSelectionActivity;
import com.misha.sitesproject.authentication_activities.SignupActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onEnterWithUserButtonClick(View v) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void onEnterWithoutUserButtonClick(View v) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null) {
            auth.signOut();
        }

        Intent intent = new Intent(this, AreaSelectionActivity.class);
        startActivity(intent);
    }
}
