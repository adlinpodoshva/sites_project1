package com.misha.sitesproject.sites_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.misha.sitesproject.R;
import com.misha.sitesproject.Utils;

import java.io.IOException;

public class SiteAnimalsActivity extends AppCompatActivity {
    public static final String SITE_NAME_EXTRA = "EXTRA_SITE_NAME";
    private static final int  WRITE_EXTERNAL_STORAGE_REQUEST_ANIMALS_CODE = 0;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_DANGEROUS_ANIMALS_CODE = 1;
    private static final int NUM_REQUEST_CODES = WRITE_EXTERNAL_STORAGE_REQUEST_DANGEROUS_ANIMALS_CODE + 1;

    private eSite site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_animals);
        this.site = eSite.valueOf(getIntent().getStringExtra(SITE_NAME_EXTRA));
        initListView();
    }

    @Override
    // called after user chose to accept / reject a permission
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode >= 0 && requestCode < NUM_REQUEST_CODES) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // permission granted
                if(requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_ANIMALS_CODE) {
                    onAnimalsOptionSelected();
                } else if(requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_DANGEROUS_ANIMALS_CODE) {
                    onDangerousAnimalsOptionSelected();
                }
            } else { // not granted
                Toast.makeText(this, "יש לאשר הרשאת כתיבה לצורך צפיה בקבצי התוכן", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initListView(){
        ListView listView = findViewById(R.id.selectionListView);
        String[] options = eOption.getTitles();
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(eOption.values()[position] == eOption.ANIMALS) {
                    onAnimalsOptionSelected();
                } else if(eOption.values()[position] == eOption.DANGEROUS_ANIMALS) {
                    onDangerousAnimalsOptionSelected();
                } else if(eOption.values()[position] == eOption.ANIMAL_REVIEWS) {
                    onAnimalsReviewsOptionSelected();
                }
            }
        });
    }

    private void onAnimalsOptionSelected() {
        if(!Utils.isWritePermissionGranted(this)) {
            Utils.requestWritePermission(this, WRITE_EXTERNAL_STORAGE_REQUEST_ANIMALS_CODE);
            return;
        }

        try {
            Utils.openPdfViaIntentFromStorage(getAnimalsFilename(this.site), this);
        } catch(IOException | IllegalArgumentException e) {
            Toast.makeText(this, "אין מידע זמין", Toast.LENGTH_LONG).show();
        } catch(ActivityNotFoundException e) {
            Toast.makeText(this, "יש להתקין על המכשיר אפליקציה לקריאת קבצי PDF על מנת לצפות בקבצים", Toast.LENGTH_LONG).show();
        }
        ;
    }

    private void onDangerousAnimalsOptionSelected() {
        if(!Utils.isWritePermissionGranted(this)) {
            Utils.requestWritePermission(this, WRITE_EXTERNAL_STORAGE_REQUEST_DANGEROUS_ANIMALS_CODE);
            return;
        }

        try {
            Utils.openPdfViaIntentFromStorage(getDangerousAnimalsFilename(this.site), this);
        } catch(IOException | IllegalArgumentException e) {
            Toast.makeText(this, "אין מידע זמין", Toast.LENGTH_LONG).show();
        } catch(ActivityNotFoundException e) {
            Toast.makeText(this, "יש להתקין על המכשיר אפליקציה לקריאת קבצי PDF על מנת לצפות בקבצים", Toast.LENGTH_LONG).show();
        }
        ;
    }

    private void onAnimalsReviewsOptionSelected() {
        Intent intent = new Intent(this, SiteReviewListActivity.class);
        intent.putExtra(SiteReviewListActivity.SITE_NAME_EXTRA_KEY, this.site.name());
        intent.putExtra(SiteReviewListActivity.REVIEW_DISPLAY_TYPE_NAME_EXTRA_KEY,
                ViewSiteReviewActivity.eReviewDisplayType.ANIMALS.name());
        startActivity(intent);
    }

    private static String getAnimalsFilename(eSite site) {
        return "baaley_hayim_" + site.name().toLowerCase();
    }

    private static String getDangerousAnimalsFilename(eSite site) {
        return "baaley_hayim_mesukanim_" + site.name().toLowerCase();
    }

    private enum eOption {
        ANIMALS("בעלי חיים"), DANGEROUS_ANIMALS("בעלי חיים מסוכנים"),
        ANIMAL_REVIEWS("צפייה בחוות דעת על בעלי חיים באתר");

        private final String title;

        eOption(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public static String[] getTitles() {
            String[] titles = new String[eOption.values().length];

            for(int i = 0; i < titles.length; i++) {
                titles[i] = eOption.values()[i].getTitle();
            }

            return titles;
        }
    }
}
