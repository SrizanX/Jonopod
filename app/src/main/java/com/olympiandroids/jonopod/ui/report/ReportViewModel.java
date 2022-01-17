package com.olympiandroids.jonopod.ui.report;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.olympiandroids.jonopod.model.UserLocation;

public class ReportViewModel extends ViewModel {

    MutableLiveData<UserLocation> userLocationMutableLiveData;
    public MutableLiveData<UserLocation> getUserLocation() {
        if(userLocationMutableLiveData == null){
            userLocationMutableLiveData = new MutableLiveData<>();
        }
        return userLocationMutableLiveData;
    }

    public void setUserLocation(UserLocation userLocation) {
        this.userLocationMutableLiveData.setValue(userLocation);
    }
}
