package com.example.misha;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {
    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("TiyulimBaaretz", Context.MODE_PRIVATE);
    }
}
