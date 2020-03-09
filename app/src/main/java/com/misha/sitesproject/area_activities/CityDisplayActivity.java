package com.misha.sitesproject.area_activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.misha.sitesproject.R;
import com.misha.sitesproject.sites_activities.SiteRecyclerViewAdapter;

import java.util.Arrays;

public class CityDisplayActivity extends AppCompatActivity {
    public final static String CITY_KEY = "KEY_CITY";

    private RecyclerView recyclerView;
    private SiteRecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private eCity city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_display);

        this.city = eCity.valueOf(getIntent().getStringExtra(CITY_KEY));
        setTitle(this.city.getTitle());
        initRecyclerView();
    }

    private void initRecyclerView() {
        this.recyclerView = findViewById(R.id.recyclerView);
        this.recyclerViewLayoutManager = new GridLayoutManager(this, 3);
        this.recyclerView.setLayoutManager(this.recyclerViewLayoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerViewAdapter = new SiteRecyclerViewAdapter(Arrays.asList(this.city.getSites()), this);
        this.recyclerView.setAdapter(this.recyclerViewAdapter);
    }
}
