package com.olympiandroids.jonopod.ui.report;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.olympiandroids.jonopod.model.UserLocation;

public class ReportViewModel extends ViewModel {

    MutableLiveData<UserLocation> userLocationMutableLiveData;
    MutableLiveData<LatLng> locationCoordinates;

    public MutableLiveData<UserLocation> getUserLocation() {
        if(userLocationMutableLiveData == null){
            userLocationMutableLiveData = new MutableLiveData<>();
        }
        return userLocationMutableLiveData;
    }

    public void setUserLocation(UserLocation userLocation) {
        this.userLocationMutableLiveData.setValue(userLocation);
    }

    public MutableLiveData<LatLng> getLocationCoordinates() {
        if(locationCoordinates==null){
            locationCoordinates = new MutableLiveData<>();
        }
        return locationCoordinates;
    }

    public void setLocationCoordinates(LatLng latLng) {
        if(locationCoordinates == null){
            locationCoordinates = new MutableLiveData<>();
        }
        this.locationCoordinates.setValue(latLng);
    }
}
