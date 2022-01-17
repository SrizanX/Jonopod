package com.olympiandroids.jonopod.ui.authentication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.olympiandroids.jonopod.data.Repository;

public class AuthenticationViewModel extends AndroidViewModel {
    private final Repository mRepository;
    private final MutableLiveData<FirebaseUser> mUserMutableLiveData;

    public AuthenticationViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        mUserMutableLiveData = mRepository.getUserMutableLiveData();
    }

    public void register(String name, String email, String password, String gender){
        mRepository.register(name, email, password, gender);
    }

    public void login(String email, String password){
        mRepository.login(email, password);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return mUserMutableLiveData;
    }
}
