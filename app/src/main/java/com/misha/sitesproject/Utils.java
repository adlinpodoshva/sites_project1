package com.misha.sitesproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.misha.sitesproject.authentication_activities.SignupActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("TiyulimBaaretz", Context.MODE_PRIVATE);
    }

    public static boolean isWritePermissionGranted(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestWritePermission(Activity activity, int requsetCode) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, requsetCode);
        }
    }

    public static boolean isCameraPermissionGranted(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestCameraPermission(Activity activity, int requsetCode) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(new String[] {Manifest.permission.CAMERA}, requsetCode);
        }
    }

    public static void openPdfViaIntentFromStorage(String filenameWithoutExtension, Activity activity) throws IOException {
        File pdfFile = new File(getAppPdfDir(), filenameWithoutExtension + ".pdf");

        if(!pdfFile.exists()) {
            throw new IOException("File does not exist");
        }

        // send intent to open pdf vie third party
        Uri fileUri = FileProvider.getUriForFile(activity,
                activity.getApplicationContext().getPackageName() + ".fileprovider",
                pdfFile);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(fileUri, "application/pdf");
        activity.startActivity(intent);
    }

    public static File getAppPdfDir() {
        return new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/Tiyulim/pdfs");
    }


    // returns true if user is signed in, else false
    public static boolean finishActivityIfNotSignedIn(FirebaseAuth auth, Activity activity, Intent moveToIntent) {
        if(auth.getCurrentUser() == null) {
            Toast.makeText(activity, "התנתקתם מהמערכת, אנא התחברו שנית", Toast.LENGTH_LONG).show();
            activity.startActivity(moveToIntent);
            activity.finish();
            return false;
        } else {
            return true;
        }
    }
}
