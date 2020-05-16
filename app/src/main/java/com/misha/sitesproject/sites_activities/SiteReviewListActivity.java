package com.misha.sitesproject.sites_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.misha.sitesproject.R;
import com.misha.sitesproject.data.Review;

import java.util.ArrayList;
import java.util.List;

public class SiteReviewListActivity extends AppCompatActivity {
    public static final String SITE_NAME_EXTRA_KEY = "EXTRA_SITE_NAME";
    public static final String REVIEW_DISPLAY_TYPE_NAME_EXTRA_KEY = "EXTRA_REVIEW_DISPLAY_TYPE_NAME";

    private List<Review> reviewList = new ArrayList<>();
    private List<String> reviewKeyList = new ArrayList<>();

    private eSite site;
    private ViewSiteReviewActivity.eReviewDisplayType reviewDisplayType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_review_list);

        this.site = eSite.valueOf(getIntent().getStringExtra(SITE_NAME_EXTRA_KEY));
        this.reviewDisplayType = ViewSiteReviewActivity.eReviewDisplayType.valueOf(
                getIntent().getStringExtra(REVIEW_DISPLAY_TYPE_NAME_EXTRA_KEY));
        initReviewList();
    }

    private void initReviewList() {
        ListView reviewSelectionList = findViewById(R.id.reviewSelectionListView);

        reviewSelectionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String reviewKey = SiteReviewListActivity.this.reviewKeyList.get(position);
                Intent intent = new Intent(SiteReviewListActivity.this, ViewSiteReviewActivity.class);
                intent.putExtra(ViewSiteReviewActivity.REVIEW_KEY_EXTRA, reviewKey);
                intent.putExtra(ViewSiteReviewActivity.SITE_NAME_EXTRA, SiteReviewListActivity.this.site.name());
                intent.putExtra(ViewSiteReviewActivity.REVIEW_DISPLAY_TYPE_NAME_EXTRA,
                        SiteReviewListActivity.this.reviewDisplayType.name());
                startActivity(intent);
            }
        });

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("reviews").child(this.site.name()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SiteReviewListActivity.this.reviewList.clear();
                SiteReviewListActivity.this.reviewKeyList.clear();

                for(DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()) {
                    Review review = dataSnapshotChild.getValue(Review.class);
                    SiteReviewListActivity.this.reviewList.add(review);
                    SiteReviewListActivity.this.reviewKeyList.add(dataSnapshotChild.getKey());
                }

                updateReviewListView(reviewList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateReviewListView(List<Review> reviewList) {
        List<String> titles = new ArrayList<>();

        for(Review review : reviewList) {
            String title = review.getContent().substring(0, Math.min(10, review.getContent().length())) + "..";
            titles.add(title);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.simple_text_aligned_right, android.R.id.text1, titles);
        ((ListView)findViewById(R.id.reviewSelectionListView)).setAdapter(adapter);
    }
}
