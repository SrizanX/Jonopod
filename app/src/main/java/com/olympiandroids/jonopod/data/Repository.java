package com.olympiandroids.jonopod.data;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.olympiandroids.jonopod.model.User;

public class Repository {
    private final FirebaseFirestore firestore;
    private final Application mApplication;
    private final FirebaseAuth mAuth;
    private final MutableLiveData<FirebaseUser> mFirebaseUserMutableLiveData;


    public Repository(Application application){
        this.mApplication = application;
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        mFirebaseUserMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return mFirebaseUserMutableLiveData;
    }


    public void register(String name, String email, String password, String gender){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        mFirebaseUserMutableLiveData.postValue(mAuth.getCurrentUser());
                        User newUser = new User(mAuth.getUid(),name,email,password,gender);
                        firestore.collection("users").document(mAuth.getUid())
                                .set(newUser);
                    }
                    else {
                        Toast.makeText(mApplication, "Registration Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        mFirebaseUserMutableLiveData.postValue(mAuth.getCurrentUser());
                        Toast.makeText(mApplication, "Login Successful!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(mApplication, "Login Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void logOut(){
        mAuth.signOut();
        mFirebaseUserMutableLiveData.postValue(null);
    }


}

