package com.misha.sitesproject.sites_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.misha.sitesproject.R;
import com.misha.sitesproject.data.Review;
import com.misha.sitesproject.data.User;

import java.util.List;

public class ViewSiteReviewActivity extends AppCompatActivity {
    public enum eReviewDisplayType {
        ANIMALS, PLACES, ALL
    }

    public static final String REVIEW_KEY_EXTRA = "EXTRA_REVIEW_KEY";
    public static final String SITE_NAME_EXTRA = "EXTRA_SITE_NAME";
    public static final String REVIEW_DISPLAY_TYPE_NAME_EXTRA = "EXTRA_REVIEW_DISPLAY_TYPE_NAME";
    private static final String IMAGE_DOWNLOAD_ERROR_MESSAGE = "התרחשה שגיאה בעת נסיון טעינת תמונה";

    private String reviewKey;
    private eSite site;
    private Review review;
    private eReviewDisplayType reviewDisplayType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_review);
        this.reviewKey = getIntent().getStringExtra(REVIEW_KEY_EXTRA);
        this.site = eSite.valueOf(getIntent().getStringExtra(SITE_NAME_EXTRA));
        this.reviewDisplayType =
                eReviewDisplayType.valueOf(getIntent().getStringExtra(REVIEW_DISPLAY_TYPE_NAME_EXTRA));
        initReviewdata();
    }

    private void initReviewdata() {
        setDataFieldsVisibility();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("reviews").child(this.site.name()).child(this.reviewKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ViewSiteReviewActivity.this.review = dataSnapshot.getValue(Review.class);
                updateTextViews(ViewSiteReviewActivity.this.review);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        downloadImages();
    }

    private void setDataFieldsVisibility() {
        if(this.reviewDisplayType != eReviewDisplayType.ALL) {
            findViewById(R.id.contentHeaderTextView).setVisibility(View.GONE);
            findViewById(R.id.contentTextView).setVisibility(View.GONE);
            findViewById(R.id.contentHorizontalScrollView).setVisibility(View.GONE);

            if(this.reviewDisplayType == eReviewDisplayType.ANIMALS) {
                findViewById(R.id.dangerousPlacesHeaderTextView).setVisibility(View.GONE);
                findViewById(R.id.dangerousPlacesTextView).setVisibility(View.GONE);
                findViewById(R.id.dangerousPlacesHorizontalScrollView).setVisibility(View.GONE);
            } else { // places
                findViewById(R.id.dangerousAnimalsHeaderTextView).setVisibility(View.GONE);
                findViewById(R.id.dangerousAnimalsTextView).setVisibility(View.GONE);
                findViewById(R.id.dangerousAnimalsHorizontalScrollView).setVisibility(View.GONE);
            }
        }
    }

    private void updateTextViews(Review review) {
        // update reporter full name
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("users").child(review.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                ((AppCompatTextView) findViewById(R.id.reporterUserFullNameTextView)).setText(user.getFullName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(this.reviewDisplayType == eReviewDisplayType.ALL)
            ((AppCompatTextView) findViewById(R.id.contentTextView)).setText(review.getContent());
        if(this.reviewDisplayType != eReviewDisplayType.ANIMALS)
            ((AppCompatTextView) findViewById(R.id.dangerousPlacesTextView)).setText(review.getDangerousPlaces());
        if(this.reviewDisplayType != eReviewDisplayType.PLACES)
            ((AppCompatTextView) findViewById(R.id.dangerousAnimalsTextView)).setText(review.getDangerousAnimals());
    }

     // download all review images
    private void downloadImages() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        if(this.reviewDisplayType == eReviewDisplayType.ALL)
            downloadImages(storageRef, "content", (LinearLayoutCompat)findViewById(R.id.contentImagesLinearLayout));

        if(this.reviewDisplayType != eReviewDisplayType.ANIMALS)
            downloadImages(storageRef, "danger_places",
                    (LinearLayoutCompat)findViewById(R.id.dangerousPlacesImagesLinearLayout));

        if(this.reviewDisplayType != eReviewDisplayType.PLACES)
            downloadImages(storageRef, "danger_animals",
                    (LinearLayoutCompat)findViewById(R.id.dangerousaAnimalsImagesLinearLayout));
    }

    // download images from a specific subref
    private void downloadImages(StorageReference rootStorageRef, String subRefName, final LinearLayoutCompat linearLayout) {
        final StorageReference imagesTreeRef = rootStorageRef.child("images/reviews").child(this.site.name())
                .child(this.reviewKey).child(subRefName);
        imagesTreeRef.listAll().addOnCompleteListener(new OnCompleteListener<ListResult>() {
            @Override
            public void onComplete(@NonNull Task<ListResult> task) {
                List<StorageReference> imageRefList = task.getResult().getItems();

                for(StorageReference imageStorageRef : imageRefList) {
                    downloadImage(imageStorageRef, linearLayout);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ViewSiteReviewActivity.this, IMAGE_DOWNLOAD_ERROR_MESSAGE,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void downloadImage(StorageReference imageStorageRef, final LinearLayoutCompat linearLayout) {
        final long ONE_MEGABYTE = 1024 * 1024; // max size to download
        imageStorageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                ImageView imageView = new ImageView(ViewSiteReviewActivity.this);

                // init layout params
                int dp = 200;   // layoutparams are specified in px, not dp
                int px =  (int)(dp * getResources().getDisplayMetrics().density);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(px, px);
                lp.setMargins(10, 0, 10, 0);
                imageView.setLayoutParams(lp);

                // attch bitmap and add to linearlayout
                imageView.setImageBitmap(bmp);
                linearLayout.addView(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ViewSiteReviewActivity.this, IMAGE_DOWNLOAD_ERROR_MESSAGE,
                        Toast.LENGTH_LONG).show();
            }
        });

    }
}
