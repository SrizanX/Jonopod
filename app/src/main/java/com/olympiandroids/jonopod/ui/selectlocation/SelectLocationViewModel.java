package com.olympiandroids.jonopod.ui.selectlocation;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SelectLocationViewModel extends ViewModel implements LocationRepository.OnTaskCompletedListener {
    LocationRepository locationRepository;
    private final MutableLiveData<List<String>> divisions, districts, upazilas, unions;

    public SelectLocationViewModel() {
        locationRepository = new LocationRepository(this);
        divisions = new MutableLiveData<>();
        districts = new MutableLiveData<>();
        upazilas = new MutableLiveData<>();
        unions = new MutableLiveData<>();
    }

    public MutableLiveData<List<String>> getDivisions() {
        locationRepository.getDivisions();
        return divisions;
    }

    public MutableLiveData<List<String>> getDistricts() {
        return districts;
    }

    public MutableLiveData<List<String>> getUpazilas() {
        return upazilas;
    }

    public MutableLiveData<List<String>> getUnions() { return unions;}

    public void setDistricts(String division) {
        locationRepository.getDistricts(division);
    }

    public void setUpazilas(String district) {
        locationRepository.getUpazilas(district);
    }

    public void setUnions(String district,String upazila) {
        locationRepository.getUnions(district, upazila);
    }


    @Override
    public void onLoadingComplete(List<String> stringList, int category) {
        switch (category) {
            case LocationContract.CATEGORY_DIVISION:
                divisions.setValue(stringList);
                break;
            case LocationContract.CATEGORY_DISTRICT:
                districts.setValue(stringList);
                break;
            case LocationContract.CATEGORY_UPAZILA:
                upazilas.setValue(stringList);
                break;
            case LocationContract.CATEGORY_WORD_UNION:
                Log.d("UNION_VM",stringList.toString());
                unions.setValue(stringList);
                break;
        }

    }


    @Override
    public void onLoadingFailed(Exception exception) {

    }
}
