package com.example.misha.area_activities;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.misha.R;
import com.example.misha.sites_activities.FavouriteSitesDisplayActivity;

public class AreaSelectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_selection);
        initListView();
    }

    private void initListView() {
        ListView listView = findViewById(R.id.areaSelectionListView);

        final String[] areaTitles = eArea.getTitles();
        String[] buttonsTexts = new String[areaTitles.length + 1];
        System.arraycopy(areaTitles, 0, buttonsTexts, 0, areaTitles.length);
        buttonsTexts[areaTitles.length] = "אתרים מועדפים";

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, buttonsTexts));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position < areaTitles.length) {
                    eArea selectedArea = eArea.values()[position];
                    Intent intent = new Intent(getApplicationContext(), AreaDisplayActivity.class);
                    intent.putExtra(AreaDisplayActivity.AREA_KEY, selectedArea.name());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), FavouriteSitesDisplayActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
