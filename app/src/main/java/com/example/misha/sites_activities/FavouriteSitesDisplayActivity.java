package com.example.misha.sites_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.misha.FavouritesManager;
import com.example.misha.R;
import com.example.misha.area_activities.CityDisplayActivity;
import com.example.misha.area_activities.eArea;
import com.example.misha.area_activities.eCity;

import java.util.List;

public class FavouriteSitesDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_display);
        initListViews();
    }

    private void initListViews() {
        //  init north, center, south areas
        initFavouritesListView((ListView)findViewById(R.id.favouriteSitesNorthListView), eArea.NORTH);
        initFavouritesListView((ListView)findViewById(R.id.favouriteSitesCenterListView), eArea.CENTER);
        initFavouritesListView((ListView)findViewById(R.id.favouriteSitesSouthListView), eArea.SOUTHׂׂ);
    }

    private void initFavouritesListView(ListView listView, eArea area) {
        final List<eSite> favouriteSites = FavouritesManager.getFavouriteSites(area, this);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                eSite.getTitles(favouriteSites)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                eSite selectedSite = favouriteSites.get(position);
                Intent intent = new Intent(FavouriteSitesDisplayActivity.this, SiteDisplayActivity.class);
                intent.putExtra(SiteDisplayActivity.SITE_NAME_EXTRA_KEY, selectedSite.name());
                startActivity(intent);
            }
        });
    }
}
