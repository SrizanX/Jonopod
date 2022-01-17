package com.olympiandroids.jonopod.utils;

import android.util.Patterns;

public class Validate {

    public static boolean emailIsValid(String email){

        if(email.length()!=0 && Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            return true;
        }else {
            return false;
        }
    }
    public static boolean passwordIsValid(String password){


        if(password.length()<6){
            return false;
        }
        else {
            return true;
        }
    }
}
