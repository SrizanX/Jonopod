package com.olympiandroids.jonopod.ui.health;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class HealthPagerAdapter extends FragmentStateAdapter {

    private static final int HOSPITAL_PAGE_INDEX = 0;
    public static final  int AMBULANCE_PAGE_INDEX = 1;


    private final Fragment[] mFragments = new Fragment[] {//Initialize fragments views
//Fragment views are initialized like any other fragment (Extending Fragment)
            new HospitalFragment(),//First fragment to be displayed within the pager tab number 1
            new AmbulanceFragment(),
            new PrivateChamberFragment()
    };
    public final String[] mFragmentNames = new String[] {//Tabs names array
            "Hospital",
            "Ambulance",
            "Private Chamber"
    };


    public HealthPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }



    @Override
    public int getItemCount() {
        return mFragments.length;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
     return mFragments[position];
    }
}
