package com.example.misha.sites_activities;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public enum eColor {
    WHITE(Color.WHITE, "לבן"), GRAY(Color.GRAY, "אפור"),
    MAGENTA(Color.MAGENTA, "סגול"), CYAN(Color.CYAN, "כחול"), GREEN(Color.GREEN,"ירוק");

    private final int color;
    private final String title;

    eColor(int color, String title) {
        this.color = color;
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }

    public static List<String> getTitleList(eColor[] colors) {
        List<String> titleList = new ArrayList<>();

        for(eColor color : colors) {
            titleList.add(color.getTitle());
        }

        return titleList;
    }
}
