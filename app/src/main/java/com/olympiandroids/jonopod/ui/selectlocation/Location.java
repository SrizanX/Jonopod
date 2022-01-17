package com.olympiandroids.jonopod.ui.selectlocation;

public class Location {
    private String division;
    private String district;
    private String upazila;
    private String union;

    public Location() {
        this.division = "";
        this.district = "";
        this.upazila = "";
        this.union = "";
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getUpazila() {
        return upazila;
    }

    public void setUpazila(String upazila) {
        this.upazila = upazila;
    }

    public String getUnion() {
        return union;
    }

    public void setUnion(String union) {
        this.union = union;
    }
}
