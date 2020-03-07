package com.example.misha.sites_activities;

import java.util.ArrayList;
import java.util.List;

public enum eSite {
    ROSH_HANIKRA("ראש הנקרה",214239,"nahariyya", 33.0861999,35.1200279),
    HAR_HASHFANIM("הר השפנים", 214299, "karmi%27el", 32.9644787,35.4136208),
    NAHAL_ZALMON("נחל צלמון",  214233,"akko",  32.888672,35.4635566),
    MAARAT_PAAR("מערת פער", 214241, "pinna", 33.0313455,35.3879467),
    SHMURAT_NAHAL_DISHON("שמורת נחל דישון", 214361, "yir%27on",  33.1473156,35.6986395),
    SHMURAT_BREICHAT_DOVEV("שמורת בריכת דובב", 214240, "qiryat-shemona",33.0518934,35.4104844),
    SHMURAT_MARGALIYOT("שמורת מרגליות", 214240, "qiryat-shemona", 33.2151516,35.5541403);

    private final String title;
    private final int accuweatherCode;
    private final String accuweatherName;
    private final double longitude;
    private final double latitude;

    eSite(String title, int accuweatherCode, String accuweatherName, double longitude, double latitude) {
        this.title = title;
        this.accuweatherCode = accuweatherCode;
        this.accuweatherName = accuweatherName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getTitle() {
        return title;
    }

    public int getAccuweatherCode() {
        return accuweatherCode;
    }

    public String getAccuweatherName() {
        return accuweatherName;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public static List<String> getTitles(List<eSite> sites) {
        List<String> titles = new ArrayList<>();

        for(eSite site : sites) {
            titles.add(site.getTitle());
        }

        return titles;
    }
}
