package com.misha.sitesproject.sites_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.misha.sitesproject.FavouritesManager;
import com.misha.sitesproject.R;
import com.misha.sitesproject.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SiteDisplayActivity extends AppCompatActivity {
    public static final String SITE_NAME_EXTRA_KEY = "EXTRA_SITE_NAME";
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_ENTERTAINMENT_SITE_CODE = 0;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_DANGEROUS_PATHS_CODE = 1;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_NATURAL_PHENOMENA_CODE = 2;
    private static final int NUM_REQUEST_CODES = WRITE_EXTERNAL_STORAGE_REQUEST_NATURAL_PHENOMENA_CODE + 1;

    private eSite site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_display);

        this.site = eSite.valueOf(getIntent().getStringExtra(SITE_NAME_EXTRA_KEY));
        setTitle(this.site.getTitle());
        initLayout();
    }

    public void onEntertainmentSiteButtonClicked(View v) {
        if(!Utils.isWritePermissionGranted(this)) {
            Utils.requestWritePermission(this, WRITE_EXTERNAL_STORAGE_REQUEST_ENTERTAINMENT_SITE_CODE);
            return;
        }

        try {
            Utils.openPdfViaIntent(getHatarFilename(), this);
        } catch(IOException e) {
            Toast.makeText(this, "תקלה בפתיחת קובץ", Toast.LENGTH_LONG).show();
        }
    }

    public void onNavigationButtonClicked(View v) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(String.format("google.navigation:q=%f,%f", this.site.getLongitude(), this.site.getLatitude())));
        startActivity(intent); }

    public void onGasStationsAndRestaurantsButtonClicked(View v) {
        Uri gmmIntentUri = Uri.parse(String.format("geo:%f,%f?q=restaurants+gas+stations", this.site.getLongitude(), this.site.getLatitude()));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void onWeatherButtonClicked(View v) {
        String url = String.format("https:////www.accuweather.com//en//il//%s//%d//weather-forecast//%d",
                this.site.getAccuweatherName(),
                this.site.getAccuweatherCode(),
                this.site.getAccuweatherCode());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }

    public void onBackgroundButtonClicked(View v) {
        // init dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogRightJustify);
        builder.setTitle("בחירת צבע");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item);
        eColor[] colors = eColor.values();
        arrayAdapter.addAll(eColor.getTitleList(colors));

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eColor selectedColor = eColor.values()[which];
                getWindow().getDecorView().setBackgroundColor(selectedColor.getColor());
            }
        });

        builder.show();
    }

    public void onDangerousPathsButtonClicked(View v) {
        if(!Utils.isWritePermissionGranted(this)) {
            Utils.requestWritePermission(this, WRITE_EXTERNAL_STORAGE_REQUEST_DANGEROUS_PATHS_CODE);
            return;
        }

        try {
            Utils.openPdfViaIntent(getDangerousPathsFileName(), this);
        } catch(IOException e) {
            Toast.makeText(this, "תקלה בפתיחת קובץ", Toast.LENGTH_LONG).show();
        }
    }

    public void onNaturalPhenomenaButtonClicked(View v) {
        if(!Utils.isWritePermissionGranted(this)) {
            Utils.requestWritePermission(this,WRITE_EXTERNAL_STORAGE_REQUEST_NATURAL_PHENOMENA_CODE);
            return;
        }

        try {
            Utils.openPdfViaIntent(getNaturalPhenomenaFilename(), this);
        } catch(IOException e) {
            Toast.makeText(this, "תקלה בפתיחת קובץ", Toast.LENGTH_LONG).show();
        }
    }



    public void onAddReviewsButtonClicked(View v) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null) {
            Intent intent = new Intent(this, AddReviewActivity.class);
            intent.putExtra(AddReviewActivity.SITE_NAME_EXTRA_KEY, this.site.name());
            startActivity(intent);
        } else { // user not signed in
            Toast.makeText(this, "יש להתחבר עם שם משתמש לצורך השארת חוות דעת", Toast.LENGTH_LONG).show();
        }

    }

    public void onFavouritesButtonClicked(View v) {
        boolean setFavourite = !FavouritesManager.isFavourite(this.site, this);
        FavouritesManager.setFavouriteFlag(this. site, setFavourite, this);
        setFavouritesButtonText();
    }

    public void onViewReviewListButtonClicked(View v) {
        Intent intent = new Intent(this, SiteReviewListActivity.class);
        intent.putExtra(SiteReviewListActivity.SITE_NAME_EXTRA_KEY, this.site.name());
        intent.putExtra(SiteReviewListActivity.REVIEW_DISPLAY_TYPE_NAME_EXTRA_KEY,
                ViewSiteReviewActivity.eReviewDisplayType.ALL.name());
        startActivity(intent);
    }

    public void onAnimalsButtonClicked(View v) {
        Intent intent = new Intent(this, SiteAnimalsActivity.class);
        intent.putExtra(SiteAnimalsActivity.SITE_NAME_EXTRA, this.site.name());
        startActivity(intent);
    }

    private void initLayout() {
        setFavouritesButtonText();
    }

    private void setFavouritesButtonText() {
        Button button = findViewById(R.id.favouritesButton);

        // set text for favourites button
        if(FavouritesManager.isFavourite(this.site, this)) {
            button.setText("הסרה ממועדפים");
        } else {
            button.setText("הוספה למועדפים");
        }
    }


    private String getHatarFilename() {
        return "hatar_" + this.site.name().toLowerCase();
    }


    private String getNaturalPhenomenaFilename() {
        return "tofaot_teva_" + this.site.name().toLowerCase();
    }


    @Override
    // called after user chose to accept / reject a permission
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode >= 0 && requestCode < NUM_REQUEST_CODES) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // permission granted
                if(requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_ENTERTAINMENT_SITE_CODE) {
                    onEntertainmentSiteButtonClicked(findViewById(R.id.entertainmentSiteButton));
                } else if(requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_DANGEROUS_PATHS_CODE) {
                    onDangerousPathsButtonClicked(findViewById(R.id.dangerousPathsButton));
                } else if(requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_NATURAL_PHENOMENA_CODE) {
                    onNaturalPhenomenaButtonClicked(findViewById(R.id.naturalPhenomenaButton));
                }
            } else { // not granted
                Toast.makeText(this, "יש לאשר הרשאת כתיבה לצורך צפיה בקבצי התוכן", Toast.LENGTH_LONG).show();
            }
        }
    }
}
