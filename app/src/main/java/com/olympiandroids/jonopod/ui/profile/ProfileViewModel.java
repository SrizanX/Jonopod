package com.olympiandroids.jonopod.ui.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.olympiandroids.jonopod.data.Repository;

public class ProfileViewModel extends AndroidViewModel {
    private final Repository mRepository;
    private final MutableLiveData<FirebaseUser> mUserMutableLiveData;


    public ProfileViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        mUserMutableLiveData = mRepository.getUserMutableLiveData();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return mUserMutableLiveData;
    }


    public void logOut(){
        mRepository.logOut();
    }

}