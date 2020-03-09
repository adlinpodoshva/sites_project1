package com.misha.sitesproject;

import android.content.Context;
import android.content.SharedPreferences;

import com.misha.sitesproject.area_activities.eArea;
import com.misha.sitesproject.sites_activities.eSite;
import java.util.ArrayList;
import java.util.List;

public class FavouritesManager {
    public static void setFavouriteFlag(eSite site, boolean value, Context context) {
        SharedPreferences sharedPreferences = Utils.getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(site.name(), value);
        editor.commit();
    }

    public static boolean isFavourite(eSite site, Context context) {
        SharedPreferences sharedPreferences = Utils.getSharedPreferences(context);
        return sharedPreferences.getBoolean(site.name(), false);
    }

    public static List<eSite> getFavouriteSites(Context context) {
        List<eSite> favouriteSites = new ArrayList<>();

        for(eSite site : eSite.values()) {
            if(isFavourite(site, context)) {
                favouriteSites.add(site);
            }
        }

        return favouriteSites;
    }

    public  static List<eSite> getFavouriteSites(eArea area, Context context) {
        List<eSite> favouriteSites = new ArrayList<>();

        for(eSite site : area.getSites()) {
            if(isFavourite(site, context)) {
                favouriteSites.add(site);
            }
        }

        return favouriteSites;
    }
}
