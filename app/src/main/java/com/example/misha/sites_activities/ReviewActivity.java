package com.example.misha.sites_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.misha.R;
import com.google.android.material.textfield.TextInputLayout;

public class ReviewActivity extends AppCompatActivity {
    public static final String SITE_NAME_EXTRA_KEY = "EXTRA_SITE_NAME";

    private final static int MIN_DATA_FIELD_LENGTH = 5;
    private final static String SWEAR_WORDS_MESSAGE = "אין להזין מילים גסות";
    private static final String[] SWEAR_WORDS = new String[] {
         "זונה", "שרמוטה"
    };

    private eSite site;
    // add data field for dangerous places + animals
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        this.site = eSite.valueOf(getIntent().getStringExtra(SITE_NAME_EXTRA_KEY));
    }

    public void onSubmitButtonClick(View v) {
        clearDataFieldsErros();
        assertDataFieldsValidAndSetErrors();
    }

    private boolean assertDataFieldsValidAndSetErrors() {
        boolean result = true;

        result &= checkLegalName();
        result &= checkLegalContent();
        result &= checkLegalDangerAnimals();
        result &= checkLegalDangerPlaces();

        Toast.makeText(this, result + "", Toast.LENGTH_LONG).show();
        return result;
    }

    private void clearDataFieldsErros() {
        ((TextInputLayout)findViewById(R.id.nameTextInputLayout)).setError(null);
        ((TextInputLayout)findViewById(R.id.contentTextInputLayout)).setError(null);
        ((TextInputLayout)findViewById(R.id.dangerPlacesTextInputLayout)).setError(null);
        ((TextInputLayout)findViewById(R.id.dangerAnimalsTextInputLayout)).setError(null);
    }

    private boolean checkLegalName() {
        TextInputLayout nameTextInputLayout = findViewById(R.id.nameTextInputLayout);
        String name = getNameText();

        if(name.length() < MIN_DATA_FIELD_LENGTH) {
            nameTextInputLayout.setError(String.format("השם חייב להיות באורך לפחות %d תווים", MIN_DATA_FIELD_LENGTH));
            return false;
        }
        if(!isFreeOfSwearWords(name)) {
            nameTextInputLayout.setError(SWEAR_WORDS_MESSAGE);
            return false;
        }

        return true;
    }

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
}
