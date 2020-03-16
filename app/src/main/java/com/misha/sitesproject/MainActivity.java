package com.misha.sitesproject;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        Intent intent = new Intent(this, AreaSelectionActivity.class);
        startActivity(intent);
    }
}
