package com.misha.sitesproject.sites_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.misha.sitesproject.R;
import com.google.android.material.textfield.TextInputLayout;
import com.misha.sitesproject.Utils;
import com.misha.sitesproject.authentication_activities.SignupActivity;
import com.misha.sitesproject.data.Review;
import com.misha.sitesproject.data.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddReviewActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_add_review);

        this.site = eSite.valueOf(getIntent().getStringExtra(SITE_NAME_EXTRA_KEY));
        this.auth = FirebaseAuth.getInstance();

        if(!assertSignedIn()) {
            return;
        }

        initFixedDataFields();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if(eImageAddType.isActivityResultGalleryRequestCode(requestCode)) {
            Uri galleryImageUri = imageReturnedIntent.getData();

            if(galleryImageUri != null) {
                eImageAddType imageAddType = eImageAddType.getImageAddTypeByActivityResultGalleryRequestCode(requestCode);
                LinearLayoutCompat imagesLinearLayout = getImagesLinearLayout(imageAddType);
                ImageView imageView = getNewImageView();
                imageView.setImageURI(galleryImageUri);
                imagesLinearLayout.addView(imageView);
            }
        } else { // camera request code
            Bundle extras = imageReturnedIntent.getExtras();

            if(extras != null && extras.get("data") != null) {
                Bitmap cameraImageBitmap = (Bitmap) extras.get("data");
                eImageAddType imageType = eImageAddType.getImageAddTypeByActivityResultCameraRequestCode(requestCode);
                LinearLayoutCompat imagesLinearLayout = getImagesLinearLayout(imageType);
                ImageView imageView = getNewImageView();
                imageView.setImageBitmap(cameraImageBitmap);
                imagesLinearLayout.addView(imageView);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        assertSignedIn();
    }

    public void onSubmitButtonClick(View v) {
        if(!assertSignedIn()) {
            return;
        }

        clearDataFieldsErros();
        if (assertDataFieldsValidAndSetErrors()) {
            Review review = new Review(this.auth.getUid(), this.site.getTitle(), getContentText(),
                    getDangerAnimalsText(), getDangerPlacesText());
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference pushedRef = databaseReference.child("reviews").child(this.site.name()).push();
            final String pushedKey = pushedRef.getKey();

            pushedRef.setValue(review).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        uploadImagesToFireStore(pushedKey);
                    } else {
                        Toast.makeText(AddReviewActivity.this, "לא היה ניתן להשלים את הפעולה בזמן זה, אנא נסו שנית מאוחר יותר",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void uploadImagesToFireStore(final String reviewKey) {
        // get bitmap lists from corresponding linearlayouts
        final List<Bitmap> contentBitmaps = getImageBitmaps((LinearLayoutCompat)findViewById(R.id.contentImagesLinearLayout));
        final List<Bitmap> dangerousPlacesBitmaps =
                getImageBitmaps((LinearLayoutCompat)findViewById(R.id.dangerousPlacesImagesLinearLayout));
        final List<Bitmap> dangerousAnimalsBitmaps =
                getImageBitmaps((LinearLayoutCompat)findViewById(R.id.dangerousAnimalsImagesLinearLayout));

        // init progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("מעלה תמונות לשרת..");
        progressDialog.show();

        // udefine success / failure listeners
        final OnFailureListener failureListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(progressDialog.isShowing()) progressDialog.dismiss();
                Toast.makeText(AddReviewActivity.this, "התרחשה שגיאה בעת העלאת התמונות", Toast.LENGTH_LONG).show();
            }
        };

        OnSuccessListener<UploadTask.TaskSnapshot> successListener = new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Bitmap bitmap;
                String refSub;

                // get an image to upload
                if(contentBitmaps.size() > 0) {
                    bitmap = contentBitmaps.get(contentBitmaps.size() - 1);
                    contentBitmaps.remove(contentBitmaps.size() - 1);
                    refSub = "content/image" + contentBitmaps.size();
                } else if(dangerousPlacesBitmaps.size() > 0) {
                    bitmap = dangerousPlacesBitmaps.get(dangerousPlacesBitmaps.size() - 1);
                    dangerousPlacesBitmaps.remove(dangerousPlacesBitmaps.size() - 1);
                    refSub = "danger_places/image" + dangerousPlacesBitmaps.size();
                } else if(dangerousAnimalsBitmaps.size() > 0) {
                    bitmap = dangerousAnimalsBitmaps.get(dangerousAnimalsBitmaps.size() - 1);
                    dangerousAnimalsBitmaps.remove(dangerousAnimalsBitmaps.size() - 1);
                    refSub = "danger_animals/image" + dangerousAnimalsBitmaps.size();
                } else { // finished uplading images
                    if(progressDialog.isShowing()) progressDialog.dismiss();
                    Toast.makeText(AddReviewActivity.this, "חוות הדעת הועלתה בהצלחה", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddReviewActivity.this, SiteDisplayActivity.class);
                    intent.putExtra(SiteDisplayActivity.SITE_NAME_EXTRA_KEY, AddReviewActivity.this.site.name());
                    startActivity(intent);
                    finish();
                    return;
                }

                Bitmap scaledBitmap = scaleBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                bitmap.recycle();
                scaledBitmap.recycle();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                        .child("images").child("reviews").child(AddReviewActivity.this.site.name())
                                .child(reviewKey).child(refSub);
                UploadTask uploadTask = storageReference.putBytes(data);

                uploadTask.addOnSuccessListener(this);
                uploadTask.addOnFailureListener(failureListener);
            }
        };

        // upload images
        successListener.onSuccess(null);

    }

    private static Bitmap scaleBitmap(Bitmap bitmap) {
        int newWidth = Math.min(360, bitmap.getWidth());
        int newHeight = (int)(bitmap.getHeight() * ((double)newWidth / bitmap.getWidth()));

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
    }

    public void onDangerousAnimalsAddImageButtonClick(View v) {
        showImageAddDialog(eImageAddType.DANGEROUS_ANIMALS);
    }

    public void onDangerousPlacesAddImageButtonClick(View v) {
        showImageAddDialog(eImageAddType.DANGEROUS_PLACES);
    }

    public void onContentAddImageButtonClick(View v) {
        showImageAddDialog(eImageAddType.CONTENT);
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

    private ImageView getNewImageView() {
        ImageView imageView = new ImageView(this);

        // layoutparams are specified in px, not dp
        int dp = 70;
        int px =  (int)(dp * getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(px, px);
        lp.setMargins(10, 0, 10, 0);
        imageView.setLayoutParams(lp);

        return imageView;
    }

    private void showImageAddDialog(final eImageAddType imageType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,
                R.style.AlertDialogRightJustify); // style defined in values/styles.xml
        builder.setTitle("הוספת תמונה");
        builder.setItems(new String[]{"בחירה מגלריה", "צילום תמונה"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == SELECT_IMAGE_FROM_GALLERY_CODE) {
                    if(assertWritePermissionGranted()) {
                        //Toast.makeText(AddReviewActivity.this, "granted1", Toast.LENGTH_LONG).show();
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , imageType.getActivityResultGalleryRequestCode());
                    }
                } else { // which == ADD_IMAGE_FROM_CAMERA_CODE
                    if(assertCameraPermissionGranted()) {
                        //Toast.makeText(AddReviewActivity.this, "granted2", Toast.LENGTH_LONG).show();
                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, imageType.getActivtyResultCameraRequestCode());
                    }

                }
            }
        });

        builder.show();
    }

    private List<Bitmap> getImageBitmaps(LinearLayoutCompat linearLayoutCompat) {
        List<Bitmap> bitmaps = new ArrayList<>();

        for(int i = 0; i < linearLayoutCompat.getChildCount(); i++) {
            ImageView imageView = (ImageView)linearLayoutCompat.getChildAt(i);
            bitmaps.add(((BitmapDrawable)imageView.getDrawable()).getBitmap());
        }

        return bitmaps;
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

    private LinearLayoutCompat getImagesLinearLayout(eImageAddType imageAddType) {
        if(imageAddType == eImageAddType.CONTENT) {
            return findViewById(R.id.contentImagesLinearLayout);
        } else if(imageAddType == eImageAddType.DANGEROUS_ANIMALS) {
            return findViewById(R.id.dangerousAnimalsImagesLinearLayout);
        } else { //  eImageAddType.DANGEROUS_PLACES
            return findViewById(R.id.dangerousPlacesImagesLinearLayout);
        }
    }

    private enum eImageAddType {
        DANGEROUS_ANIMALS(0, 1),
        DANGEROUS_PLACES(2, 3),
        CONTENT(4, 5);

        private final int activityResultGalleryRequestCode;
        private final int activtyResultCameraRequestCode;

        eImageAddType(int activityResultGalleryRequestCode, int activtyResultCameraRequestCode) {
            this.activityResultGalleryRequestCode = activityResultGalleryRequestCode;
            this.activtyResultCameraRequestCode = activtyResultCameraRequestCode;
        }

        public int getActivityResultGalleryRequestCode() {
            return activityResultGalleryRequestCode;
        }

        public int getActivtyResultCameraRequestCode() {
            return activtyResultCameraRequestCode;
        }

        public static boolean isActivityResultGalleryRequestCode(int activityResultRequestCode) {
            return activityResultRequestCode % 2 == 0;
        }

        public static boolean isActivityResultCameraRequestCode(int activityResultRequestCode) {
            return activityResultRequestCode % 2 == 1;
        }

        public static eImageAddType getImageAddTypeByActivityResultGalleryRequestCode(int activityResultRequestCode) {
            for(eImageAddType imageAddType : eImageAddType.values()) {
                if(imageAddType.getActivityResultGalleryRequestCode() == activityResultRequestCode) {
                    return imageAddType;
                }
            }

            return null;
        }

        public static eImageAddType getImageAddTypeByActivityResultCameraRequestCode(int activityResultRequestCode) {
            for(eImageAddType imageAddType : eImageAddType.values()) {
                if(imageAddType.getActivtyResultCameraRequestCode() == activityResultRequestCode) {
                    return imageAddType;
                }
            }

            return null;
        }
    }
}
