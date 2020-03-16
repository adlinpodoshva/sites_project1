package com.misha.sitesproject.data;

// empty constructor for firebase
public class Review {
    private String uid;
    private String siteName;
    private String content;
    private String dangerousAnimals;
    private String dangerousPlaces;

    public Review() {

    }

    public Review(String uid, String siteName, String content, String dangerousAnimals, String dangerousPlaces) {
        this.uid = uid;
        this.siteName = siteName;
        this.content = content;
        this.dangerousAnimals = dangerousAnimals;
        this.dangerousPlaces = dangerousPlaces;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDangerousAnimals() {
        return dangerousAnimals;
    }

    public void setDangerousAnimals(String dangerousAnimals) {
        this.dangerousAnimals = dangerousAnimals;
    }

    public String getDangerousPlaces() {
        return dangerousPlaces;
    }

    public void setDangerousPlaces(String dangerousPlaces) {
        this.dangerousPlaces = dangerousPlaces;
    }
}
