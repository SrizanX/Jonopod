package com.olympiandroids.jonopod.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.olympiandroids.jonopod.ui.home.pages.AnnouncementFragment;

public class HomePagerAdapter extends FragmentStateAdapter {
    private final Fragment[] mFragments = new Fragment[]{
            new AnnouncementFragment()
    };
    public HomePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments[position];
    }

    @Override
    public int getItemCount() {

        return mFragments.length;
    }
}
