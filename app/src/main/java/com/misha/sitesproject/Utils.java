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


    public static void openPdfViaIntent(String filenameWithoutExtension, Activity activity) throws IOException {
        int resId = activity.getResources().getIdentifier(filenameWithoutExtension, "raw",
                activity.getPackageName());

        try (InputStream inputStream = activity.getResources().openRawResource(resId)) {
            // copy file to sdcard
            File folderPath = getAppTempDir();
            folderPath.mkdirs();
            File pdfCopy = new File(folderPath, filenameWithoutExtension + ".pdf");
            pdfCopy.createNewFile();
            writeStreamToFile(inputStream, pdfCopy);

            // send intent to open pdf file via third party
            Uri fileUri = FileProvider.getUriForFile(activity,
                    activity.getApplicationContext().getPackageName() + ".fileprovider",
                    pdfCopy);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(fileUri, "application/pdf");
            activity.startActivity(intent);
        }
    }

    private static void writeStreamToFile(@NonNull InputStream input, @NonNull File file) throws IOException {
        try (OutputStream output = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
            output.flush();
        }
    }

    public static File getAppTempDir() {
        return new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/Tiyulim/temp");
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
