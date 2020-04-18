package com.misha.sitesproject.area_activities;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.misha.sitesproject.R;

public class AreaDisplayActivity extends AppCompatActivity {
    public final static String AREA_KEY = "KEY_AREA";

    private eArea area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_display);

        this.area = eArea.valueOf(getIntent().getStringExtra(AREA_KEY));
        initListView();

    }

    private void initListView() {
        ListView listView = findViewById(R.id.citySelectionListView);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                eCity.getTitles(this.area.getCities())));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                eCity selectedCity = AreaDisplayActivity.this.area.getCities()[position];
                Intent intent = new Intent(getApplicationContext(), CityDisplayActivity.class);
                intent.putExtra(CityDisplayActivity.CITY_KEY, selectedCity.name());
                startActivity(intent);
            }
        });
    }
}
