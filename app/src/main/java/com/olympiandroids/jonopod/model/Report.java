package com.olympiandroids.jonopod.model;

import com.google.firebase.firestore.GeoPoint;

public class Report {
    private String category;
    private String statement;
    private GeoPoint geoPoint;
    private String imageUrl;
    private String userUID;
    private UserLocation location;
    private boolean anonymous;
    private boolean publicPost;


    public Report() {

    }

    public Report(String category, String statement, GeoPoint geoPoint, String imageUrl, String userUID, UserLocation location, boolean anonymous, boolean publicPost) {
        this.category = category;
        this.statement = statement;
        this.geoPoint = geoPoint;
        this.imageUrl = imageUrl;
        this.userUID = userUID;
        this.location = location;
        this.anonymous = anonymous;
        this.publicPost = publicPost;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public UserLocation getLocation() {
        return location;
    }

    public void setLocation(UserLocation location) {
        this.location = location;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public boolean isPublicPost() {
        return publicPost;
    }

    public void setPublicPost(boolean publicPost) {
        this.publicPost = publicPost;
    }
}
