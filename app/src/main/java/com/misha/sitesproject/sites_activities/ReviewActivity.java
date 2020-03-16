package com.misha.sitesproject.sites_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.misha.sitesproject.R;
import com.google.android.material.textfield.TextInputLayout;
import com.misha.sitesproject.Utils;
import com.misha.sitesproject.authentication_activities.SignupActivity;
import com.misha.sitesproject.data.Review;
import com.misha.sitesproject.data.User;

public class ReviewActivity extends AppCompatActivity {
    public static final String SITE_NAME_EXTRA_KEY = "EXTRA_SITE_NAME";

    private static final int MIN_DATA_FIELD_LENGTH = 5;
    private static final String SWEAR_WORDS_MESSAGE = "אין להזין מילים גסות";
    private static final String[] SWEAR_WORDS = new String[] {
         "זונה", "שרמוטה"
    };

    private static final int SELECT_IMAGE_FROM_GALLERY_CODE = 0;
    private static final int ADD_IMAGE_FROM_CAMERA_CODE = 1;

    private eSite site;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        this.site = eSite.valueOf(getIntent().getStringExtra(SITE_NAME_EXTRA_KEY));
        this.auth = FirebaseAuth.getInstance();

        if(!assertSignedIn()) {
            return;
        }

        initFixedDataFields();
    }

    public void onSubmitButtonClick(View v) {
        clearDataFieldsErros();
        if (assertDataFieldsValidAndSetErrors()) {
            Review review = new Review();
        }
    }

    public void onDangerousAnimalsAddImageButtonClick(View v) {
        showImageAddDialog(eImageType.DANGEROUS_ANIMALS);
    }

    public void onDangerousPlacesAddImageButtonClick(View v) {
        showImageAddDialog(eImageType.DANGEROUS_PLACES);
    }

    public void onContentAddImageButtonClick(View v) {
        showImageAddDialog(eImageType.CONTENT);
    }

    @Override
    // called after user chose to accept / reject a permission
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == SELECT_IMAGE_FROM_GALLERY_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // permission granted

            } else { // not granted
                Toast.makeText(this, "יש לאשר הרשאת כתיבה לצורך הוספת תמונה מגלריה", Toast.LENGTH_LONG).show();
            }
        } else if(requestCode == ADD_IMAGE_FROM_CAMERA_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // permission granted

            } else { // not granted
                Toast.makeText(this, "יש לאפשר הרשאת גישה למצלמה לצורך צילום תמונה", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showImageAddDialog(eImageType imageType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,
                R.style.AlertDialogRightJustify); // style defined in values/styles.xml
        builder.setTitle("הוספת תמונה");
        builder.setItems(new String[]{"בחירה מגלריה", "צילום תמונה"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == SELECT_IMAGE_FROM_GALLERY_CODE) {
                    if(assertWritePermissionGranted()) {
                        //Toast.makeText(ReviewActivity.this, "granted1", Toast.LENGTH_LONG).show();

                    }
                } else { // which == ADD_IMAGE_FROM_CAMERA_CODE
                    if(assertCameraPermissionGranted()) {
                        //Toast.makeText(ReviewActivity.this, "granted2", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        builder.show();
    }

    private boolean assertWritePermissionGranted() {
        if(!Utils.isWritePermissionGranted(this)) {
            Utils.requestWritePermission(this, SELECT_IMAGE_FROM_GALLERY_CODE);
            return false;
        }

        return true;
    }

    private boolean assertCameraPermissionGranted() {
        if(!Utils.isCameraPermissionGranted(this)) {
            Utils.requestCameraPermission(this, ADD_IMAGE_FROM_CAMERA_CODE);
            return false;
        }

        return true;
    }

    private boolean assertDataFieldsValidAndSetErrors() {
        boolean result = true;

        result &= checkLegalContent();
        result &= checkLegalDangerAnimals();
        result &= checkLegalDangerPlaces();

        return result;
    }

    private void initFixedDataFields() {
        ((AppCompatEditText)findViewById(R.id.siteNameEditText)).setText(this.site.getTitle());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(this.auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                ((AppCompatEditText)findViewById(R.id.nameEditText)).setText(user.getFullName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void clearDataFieldsErros() {
        ((TextInputLayout)findViewById(R.id.contentTextInputLayout)).setError(null);
        ((TextInputLayout)findViewById(R.id.dangerPlacesTextInputLayout)).setError(null);
        ((TextInputLayout)findViewById(R.id.dangerAnimalsTextInputLayout)).setError(null);
    }
//
//    private boolean checkLegalName() {
//        TextInputLayout nameTextInputLayout = findViewById(R.id.nameTextInputLayout);
//        String name = getNameText();
//
//        if(name.length() < MIN_DATA_FIELD_LENGTH) {
//            nameTextInputLayout.setError(String.format("השם חייב להיות באורך לפחות %d תווים", MIN_DATA_FIELD_LENGTH));
//            return false;
//        }
//        if(!isFreeOfSwearWords(name)) {
//            nameTextInputLayout.setError(SWEAR_WORDS_MESSAGE);
//            return false;
//        }
//
//        return true;
//    }

    private boolean checkLegalContent() {
        String content = getContentText();
        TextInputLayout contentTextInputLayout = findViewById(R.id.contentTextInputLayout);

        if(content.length() < MIN_DATA_FIELD_LENGTH) {
            contentTextInputLayout.setError(String.format("התוכן חייב להיות באורך לפחות %d תווים", MIN_DATA_FIELD_LENGTH));
            return false;
        }
        if(!isFreeOfSwearWords(content)) {
            contentTextInputLayout.setError(SWEAR_WORDS_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean checkLegalDangerPlaces() {
        String content = getDangerPlacesText();
        TextInputLayout dangerPlacesTextInputLayout = findViewById(R.id.dangerPlacesTextInputLayout);

        if(!isFreeOfSwearWords(content)) {
            dangerPlacesTextInputLayout.setError(SWEAR_WORDS_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean checkLegalDangerAnimals() {
        String content = getDangerAnimalsText();
        TextInputLayout dangerAnimalsTextInputLayout = findViewById(R.id.dangerAnimalsTextInputLayout);

        if(!isFreeOfSwearWords(content)) {
            dangerAnimalsTextInputLayout.setError(SWEAR_WORDS_MESSAGE);
            return false;
        }

        return true;
    }

    private String getContentText() {
        AppCompatEditText editText = findViewById(R.id.contentEditText);
        return editText.getText().toString().trim();
    }

    private String getDangerPlacesText() {
        AppCompatEditText editText = findViewById(R.id.dangerPlacesEditText);
        return editText.getText().toString().trim();
    }

    private String getDangerAnimalsText() {
        AppCompatEditText editText = findViewById(R.id.dangerAnimalsEditText);
        return editText.getText().toString().trim();
    }

    private String getNameText() {
        AppCompatEditText editText = findViewById(R.id.nameEditText);
        return editText.getText().toString().trim();
    }

    private static boolean isFreeOfSwearWords(String str) {
        for (String swear : SWEAR_WORDS) {
            if (str.contains(swear)) {
                return false;
            }
        }

        return true;
    }

    // returns true if user is signed in, else false
    private boolean assertSignedIn() {
        if(auth.getCurrentUser() == null) {
            Toast.makeText(this, "התנתקתם מהמערכת, אנא התחברו שנית", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, SignupActivity.class));
            finish();
            return false;
        } else {
            return true;
        }
    }

    private enum eImageType {
        DANGEROUS_ANIMALS, DANGEROUS_PLACES, CONTENT;
    }
}
