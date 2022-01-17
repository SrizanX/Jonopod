package com.olympiandroids.jonopod.ui.health;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.olympiandroids.jonopod.databinding.FragmentHealthServiceBinding;

import java.util.Objects;


public class HealthServiceFragment extends Fragment {
    FragmentHealthServiceBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHealthServiceBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.pagerHealth.setAdapter(new HealthPagerAdapter(getActivity()));

        TabLayoutMediator.TabConfigurationStrategy strategy = (tab, position) -> {
            tab.setText( ((HealthPagerAdapter)(Objects.requireNonNull(binding.pagerHealth.getAdapter()))).mFragmentNames[position]);

        };

        new TabLayoutMediator(binding.tabLHealth, binding.pagerHealth, strategy ).attach();
    }
}