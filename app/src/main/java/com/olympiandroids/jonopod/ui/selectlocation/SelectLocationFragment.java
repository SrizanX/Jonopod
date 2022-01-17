package com.olympiandroids.jonopod.ui.selectlocation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.olympiandroids.jonopod.R;
import com.olympiandroids.jonopod.databinding.SelectLocationBinding;
import com.olympiandroids.jonopod.model.UserLocation;
import com.olympiandroids.jonopod.ui.profile.editserviceprofile.EditServiceProfileViewModel;
import com.olympiandroids.jonopod.ui.report.ReportViewModel;

import java.util.List;

public class SelectLocationFragment extends BottomSheetDialogFragment implements AdapterView.OnItemSelectedListener {
    SelectLocationBinding binding;
    SelectLocationViewModel mSelectLocationViewModel;
    SelectLocationFragmentArgs args;
    EditServiceProfileViewModel profileViewModel;
    ReportViewModel reportViewModel;
    ArrayAdapter<String> divisionAdapter, districtAdapter, upazilaAdapter, unionAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(requireActivity()).get(EditServiceProfileViewModel.class);
        reportViewModel = new ViewModelProvider(requireActivity()).get(ReportViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SelectLocationBinding.inflate(inflater, container, false);
        mSelectLocationViewModel = new ViewModelProvider(this).get(SelectLocationViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        args = SelectLocationFragmentArgs.fromBundle(getArguments());

        binding.spinnerDivision.setOnItemSelectedListener(this);
        binding.spinnerDistrict.setOnItemSelectedListener(this);
        binding.spinnerUpazila.setOnItemSelectedListener(this);
        binding.spinnerUnion.setOnItemSelectedListener(this);
        //
        binding.buttonDone.setOnClickListener(v -> {
//            UserLocation userLocation = new UserLocation(
//                    binding.spinnerDivision.getSelectedItem().toString(),
//                    binding.spinnerDistrict.getSelectedItem().toString(),
//                    binding.spinnerUpazila.getSelectedItem().toString(),
//                    binding.spinnerUnion.getSelectedItem().toString()

            if(args.getOpenedFromProfileFragment()){

                profileViewModel.setUserLocation(generateLocation());
            }
            else if(args.getOpenedFromReportFragment()){
                reportViewModel.setUserLocation(generateLocation());

            }
            else {
                Context context = getActivity();
                assert context != null;
                SharedPreferences sharedPref = context.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString(LocationContract.DIVISION_NAME,binding.spinnerDivision.getSelectedItem()==null?"n/a":binding.spinnerDivision.getSelectedItem().toString() );
                editor.putString(LocationContract.DISTRICT_NAME,binding.spinnerDistrict.getSelectedItem()==null?"n/a":binding.spinnerDistrict.getSelectedItem().toString());
                editor.putString(LocationContract.UPAZILLA_NAME,binding.spinnerUpazila.getSelectedItem()==null?"n/a":binding.spinnerUpazila.getSelectedItem().toString());
                editor.putString(LocationContract.UNION_NAME, binding.spinnerUnion.getSelectedItem()==null?"n/a":binding.spinnerUnion.getSelectedItem().toString());
                editor.apply();

            }
            dismiss();
        });

        //Division Observer
        mSelectLocationViewModel.getDivisions().observe(getViewLifecycleOwner(), strings -> {
            divisionAdapter = createAdapter(strings);
            binding.spinnerDivision.setAdapter(divisionAdapter);
            binding.progressBarSelectLocaton.setVisibility(View.INVISIBLE);

        });
        mSelectLocationViewModel.getDistricts().observe(getViewLifecycleOwner(), strings -> {
            binding.progressBarSelectLocaton.setVisibility(View.VISIBLE);
            districtAdapter = createAdapter(strings);
            binding.spinnerDistrict.setAdapter(districtAdapter);
            binding.progressBarSelectLocaton.setVisibility(View.INVISIBLE);

        });
        //
        mSelectLocationViewModel.getUpazilas().observe(getViewLifecycleOwner(), strings -> {
            binding.progressBarSelectLocaton.setVisibility(View.VISIBLE);
            upazilaAdapter = createAdapter(strings);
            binding.spinnerUpazila.setAdapter(upazilaAdapter);
            binding.progressBarSelectLocaton.setVisibility(View.INVISIBLE);
        });

        mSelectLocationViewModel.getUnions().observe(getViewLifecycleOwner(), strings -> {
            binding.progressBarSelectLocaton.setVisibility(View.VISIBLE);
            unionAdapter = createAdapter(strings);
            binding.spinnerUnion.setAdapter(unionAdapter);
            binding.progressBarSelectLocaton.setVisibility(View.INVISIBLE);
        });
        //setCancelable(false);
    }
    @Override
    public void onItemSelected(AdapterView<?> spinner, View view, int position, long id) {
        if (spinner.getId() == binding.spinnerDivision.getId()) {
            mSelectLocationViewModel.setDistricts(spinner.getSelectedItem().toString());
        }
        if (spinner.getId() == binding.spinnerDistrict.getId()) {
            mSelectLocationViewModel.setUpazilas(spinner.getSelectedItem().toString());
        }

        if (spinner.getId() == binding.spinnerUpazila.getId()) {
            mSelectLocationViewModel.setUnions(binding.spinnerDistrict.getSelectedItem().toString(),spinner.getSelectedItem().toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private ArrayAdapter<String> createAdapter(List<String> strings) {
        ArrayAdapter<String> adapter;
        //String[] m = (String[]) strings.toArray(new String[0]);
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, strings);
        return adapter;
    }
    private UserLocation generateLocation(){
        UserLocation userLocation = new UserLocation(
                binding.spinnerDivision.getSelectedItem()==null?"n/a":binding.spinnerDivision.getSelectedItem().toString(),
                binding.spinnerDistrict.getSelectedItem()==null?"n/a":binding.spinnerDistrict.getSelectedItem().toString(),
                binding.spinnerUpazila.getSelectedItem()==null?"n/a":binding.spinnerUpazila.getSelectedItem().toString(),
                binding.spinnerUnion.getSelectedItem()==null?"n/a":binding.spinnerUnion.getSelectedItem().toString()

                );


        return userLocation;
    }

}