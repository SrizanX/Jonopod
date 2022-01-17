package com.olympiandroids.jonopod.ui.authentication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AuthenticationViewPagerAdapter extends FragmentStateAdapter {

    private final Fragment[] mFragments = new Fragment[] {//Initialize fragments views
//Fragment views are initialized like any other fragment (Extending Fragment)
            new LoginFragment(),//First fragment to be displayed within the pager tab number 1
            new RegisterFragment(),
    };
    public final String[] mFragmentNames = new String[] {//Tabs names array
            "Login",
            "Register"
    };

    public AuthenticationViewPagerAdapter(FragmentActivity fragmentActivity){//Pager constructor receives Activity instance
        super(fragmentActivity);
    }

    @Override
    public int getItemCount() {
        return mFragments.length;//Number of fragments displayed
    }


//
//    @Override
//    public long getItemId(int position) {
//        return super.getItemId(position);
//    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments[position];
    }
}

