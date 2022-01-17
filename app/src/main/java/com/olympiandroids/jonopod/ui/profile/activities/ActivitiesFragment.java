package com.olympiandroids.jonopod.ui.profile.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.olympiandroids.jonopod.databinding.FragmentActivitiesBinding;


public class ActivitiesFragment extends Fragment {
    FragmentActivitiesBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentActivitiesBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}