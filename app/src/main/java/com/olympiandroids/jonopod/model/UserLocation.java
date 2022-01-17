package com.olympiandroids.jonopod.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserLocation implements Parcelable {
    private String division;
    private String district;
    private String upazila;
    private String union;

    public UserLocation() {
    }

    public UserLocation(String division, String district, String upazila, String union) {
        this.division = division;
        this.district = district;
        this.upazila = upazila;
        this.union = union;
    }

    protected UserLocation(Parcel in) {
        division = in.readString();
        district = in.readString();
        upazila = in.readString();
        union = in.readString();
    }

    public static final Creator<UserLocation> CREATOR = new Creator<UserLocation>() {
        @Override
        public UserLocation createFromParcel(Parcel in) {
            return new UserLocation(in);
        }

        @Override
        public UserLocation[] newArray(int size) {
            return new UserLocation[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(division);
        dest.writeString(district);
        dest.writeString(upazila);
        dest.writeString(union);
    }
}
