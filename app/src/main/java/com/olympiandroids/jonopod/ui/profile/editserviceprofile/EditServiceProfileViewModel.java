package com.olympiandroids.jonopod.ui.profile.editserviceprofile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.olympiandroids.jonopod.model.UserLocation;

public class EditServiceProfileViewModel extends ViewModel {
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
