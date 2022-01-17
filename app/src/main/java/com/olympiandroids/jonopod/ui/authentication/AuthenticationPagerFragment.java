package com.olympiandroids.jonopod.ui.authentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.olympiandroids.jonopod.databinding.FragmentAuthenticationBinding;

import java.util.Objects;

public class AuthenticationPagerFragment extends Fragment {
    private FragmentAuthenticationBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthenticationBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.pagerAuthentication.setAdapter(new AuthenticationViewPagerAdapter(getActivity()));//Attach the adapter with our ViewPagerAdapter passing the host activity

        new TabLayoutMediator(
                binding.tabsAuthentication,
                binding.pagerAuthentication,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        //Sets tabs names as mentioned in ViewPagerAdapter fragmentNames array, this can be implemented in many different ways.
                        tab.setText( ((AuthenticationViewPagerAdapter)(Objects.requireNonNull(binding.pagerAuthentication.getAdapter()))).mFragmentNames[position] );
                    }
                }
        ).attach();

//        https://developer.android.com/guide/navigation/navigation-custom-back
//        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                Toast.makeText(getContext(), "Back Pressed", Toast.LENGTH_SHORT).show();
//
//            }
//        };
//        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), onBackPressedCallback);
//
    }
}