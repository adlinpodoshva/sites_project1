package com.misha.sitesproject.sites_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.misha.sitesproject.R;
import com.misha.sitesproject.Utils;
import com.misha.sitesproject.area_activities.AreaSelectionActivity;
import com.misha.sitesproject.area_activities.eArea;

import java.util.ArrayList;
import java.util.List;

public class FavouriteSitesDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_display);

        if(!Utils.finishActivityIfNotSignedIn(FirebaseAuth.getInstance(),
                this, new Intent(this, AreaSelectionActivity.class))) {
            return;
        }

        initListViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.finishActivityIfNotSignedIn(FirebaseAuth.getInstance(),
                this, new Intent(this, AreaSelectionActivity.class));
    }

    private void initListViews() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        databaseReference.child("favourites").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<eSite> northSites = new ArrayList<>();
                List<eSite> centerSites = new ArrayList<>();
                List<eSite> southSites = new ArrayList<>();

                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    eSite site = eSite.valueOf(child.getKey());
                    boolean isFavourited = child.getValue(Boolean.class);

                    if(isFavourited) {
                        if(eArea.NORTH.getSites().contains(site)) northSites.add(site);
                        else if(eArea.CENTER.getSites().contains(site)) centerSites.add(site);
                        else if(eArea.SOUTHׂׂ.getSites().contains(site)) southSites.add(site);
                    }
                }

                updateFavouritesListViews(northSites, (ListView)findViewById(R.id.favouriteSitesNorthListView));
                updateFavouritesListViews(centerSites, (ListView)findViewById(R.id.favouriteSitesCenterListView));
                updateFavouritesListViews(southSites, (ListView)findViewById(R.id.favouriteSitesSouthListView));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateFavouritesListViews(final List<eSite> sites, ListView areaListView) {
        areaListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                eSite.getTitles(sites)));
        areaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                eSite selectedSite = sites.get(position);
                Intent intent = new Intent(FavouriteSitesDisplayActivity.this, SiteDisplayActivity.class);
                intent.putExtra(SiteDisplayActivity.SITE_NAME_EXTRA_KEY, selectedSite.name());
                startActivity(intent);
            }
        });
    }
}
