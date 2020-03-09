package com.misha.sitesproject.area_activities;

import com.misha.sitesproject.sites_activities.eSite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum eArea {
    NORTH("צפון", new eCity[] {eCity.RAMAT_HAGOLAN, eCity.HAGALIL_HAELYON, eCity.HAGALIL_HATACHTON,
            eCity.HAKINERET, eCity.HEIFA, eCity.HERMON}),
    CENTER("מרכז", new eCity[] {eCity.JERUSALEM, eCity.TEL_AVIV, eCity.HASHFELA}),
    SOUTHׂׂ("דרום", new eCity[] {eCity.HANEGEV, eCity.EILAT, eCity.ASHDOD, eCity.ASHKELON, eCity.BEER_SHEVA});

    private final String title;
    private final eCity[] cities;

    private eArea(String title, eCity[] cities) {
        this.title = title;
        this.cities = cities;
    }

    public String getTitle() {
        return title;
    }

    public eCity[] getCities() {
        return cities;
    }

    public List<eSite> getSites() {
        List<eSite> sites = new ArrayList<>();

        for(eCity city : getCities()) {
            sites.addAll(Arrays.asList(city.getSites()));
        }

        return sites;
    }

    public static String[] getTitles() {
        return new String[] {NORTH.getTitle(), CENTER.getTitle(), SOUTHׂׂ.getTitle()};
    }
}