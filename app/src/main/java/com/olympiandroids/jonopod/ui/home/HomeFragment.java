package com.olympiandroids.jonopod.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.olympiandroids.jonopod.R;
import com.olympiandroids.jonopod.databinding.FragmentHomeBinding;
import com.olympiandroids.jonopod.ui.selectlocation.LocationContract;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private FragmentHomeBinding binding;
    SharedPreferences sharedPref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = getActivity();
        assert context != null;
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setLocation();
        binding.ibLocalGov.setOnClickListener(this);
        binding.ibPolice.setOnClickListener(this);
        binding.ibHealth.setOnClickListener(this);
        binding.ibFireStation.setOnClickListener(this);
        binding.buttonSelectLocation.setOnClickListener(this);
    }


    private void setLocation() {
        binding.textViewDivisionHome.append(sharedPref.getString(LocationContract.DIVISION_NAME, "Not available"));
        binding.textViewDistrictHome.append(sharedPref.getString(LocationContract.DISTRICT_NAME, "Not available"));
        binding.textViewUpazilaHome.append(sharedPref.getString(LocationContract.UPAZILLA_NAME, "Not available"));
        binding.textViewWardHome.append(sharedPref.getString(LocationContract.UNION_NAME, "Not available"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==binding.buttonSelectLocation.getId())
        Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_dialogFra);

        if(view.getId()==binding.ibLocalGov.getId())
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_nav_local_gov);

        if(view.getId()==binding.ibPolice.getId())
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_nav_police);

        if(view.getId()==binding.ibHealth.getId())
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_nav_health_service);

        if(view.getId()==binding.ibFireStation.getId())
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_fireServiceFragment);


    }
}