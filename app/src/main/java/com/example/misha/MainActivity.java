package com.example.misha;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.misha.area_activities.AreaSelectionActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, AreaSelectionActivity.class));
    }

    public void onButtonClick(View v) {
        Toast.makeText(this, "my message", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
        finish();
    }
}
