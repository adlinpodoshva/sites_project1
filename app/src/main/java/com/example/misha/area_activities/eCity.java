package com.example.misha.area_activities;

import com.example.misha.sites_activities.eSite;

public enum eCity {
    RAMAT_HAGOLAN("רמת הגולן"),
    HAGALIL_HAELYON("הגליל העליון",
            new eSite[] {eSite.ROSH_HANIKRA, eSite.HAR_HASHFANIM, eSite.NAHAL_ZALMON,
                eSite.MAARAT_PAAR, eSite.SHMURAT_NAHAL_DISHON, eSite.SHMURAT_BREICHAT_DOVEV, eSite.SHMURAT_MARGALIYOT}),
    HAGALIL_HATACHTON("הגליל התחתון"),
    HAKINERET("הכינר,"), HEIFA("חיפה"), HERMON("חרמון"),
    JERUSALEM("ירושלים"), TEL_AVIV("תל אביב"), HASHFELA("השפלה"),
    HANEGEV("הנגב"), EILAT("אילת"), ASHDOD("אשדוד"), ASHKELON("אשקלון"), BEER_SHEVA("באר שבע");

    private final String title;
    private final eSite[] sites;

    eCity(String title, eSite[] sites) {
        this.title = title;
        this.sites = sites;
    }

    eCity(String title) {
        this.title = title;
        this.sites = new eSite[] {};
    }

    public String getTitle() {
        return title;
    }

    public eSite[] getSites() {
        return sites;
    }

    public static String[] getTitles() {
        return getTitles(eCity.values());
    }

    public static String[] getTitles(eCity[] cities) {
        String[] titles = new String[cities.length];

        for(int i = 0; i < titles.length; i++) {
            titles[i] = cities[i].getTitle();
        }

        return titles;
    }
}
