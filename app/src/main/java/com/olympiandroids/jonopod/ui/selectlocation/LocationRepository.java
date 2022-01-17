package com.olympiandroids.jonopod.ui.selectlocation;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LocationRepository {
    public static final String TAG = LocationRepository.class.getName();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    OnTaskCompletedListener taskCompletedListener;

    public LocationRepository(OnTaskCompletedListener taskCompletedListener) {
        this.taskCompletedListener = taskCompletedListener;
    }

    public void getDivisions(){
        List<String> mDivisionsList = new ArrayList<>();
        firestore.collection(LocationContract.FIRESTORE_ROOT_LOCATIONS)
                .whereEqualTo(LocationContract.IS_DIVISION,true)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())){
                            mDivisionsList.add((String) document.get(LocationContract.DIVISION_NAME));
                        }
                        taskCompletedListener.onLoadingComplete(mDivisionsList,LocationContract.CATEGORY_DIVISION);
                    }
                    else {
                        taskCompletedListener.onLoadingFailed(task.getException());
                        Log.d(TAG, "Error getting divisions: ", task.getException());
                    }
                });
        
    }

    public void getDistricts(String division) {
        List<String> mDistrictsList = new ArrayList<>();
        firestore.collection(LocationContract.FIRESTORE_ROOT_LOCATIONS)
                .whereEqualTo(LocationContract.DIVISION_NAME,division)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())){
                            mDistrictsList.add((String) document.get(LocationContract.DISTRICT_NAME));
                        }
                        taskCompletedListener.onLoadingComplete(mDistrictsList,LocationContract.CATEGORY_DISTRICT);
                    }
                    else {
                        taskCompletedListener.onLoadingFailed(task.getException());
                        Log.d(TAG, "Error getting districts: ", task.getException());
                    }
                });
    }

    public void getUpazilas(String district) {

        CollectionReference upazilas = firestore.collection(LocationContract.FIRESTORE_ROOT_LOCATIONS)
                .document(district)
                .collection(LocationContract.FIRESTORE_UPAZILAS);

        List<String> upazilaList = new ArrayList<>();
        upazilas.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())){
                            upazilaList.add((String) document.get(LocationContract.UPAZILLA_NAME));
                        }
                        taskCompletedListener.onLoadingComplete(upazilaList,LocationContract.CATEGORY_UPAZILA);
                    }
                    else {
                        taskCompletedListener.onLoadingFailed(task.getException());
                        Log.d(TAG, "Error getting upazilas: ", task.getException());
                    }
                });

    }

    public void getUnions(String district, String upazila) {

        CollectionReference unions = firestore.collection(LocationContract.FIRESTORE_ROOT_LOCATIONS)
                .document(district)
                .collection(LocationContract.FIRESTORE_UPAZILAS)
                .document(upazila)
                .collection(LocationContract.FIRESTORE_UNIONS);

        List<String> unionList = new ArrayList<>();
        unions.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())){
                            unionList.add((String) document.get(LocationContract.UNION_NAME));
                        }
                        Log.d("UNION_REPO",unionList.toString());
                        taskCompletedListener.onLoadingComplete(unionList,LocationContract.CATEGORY_WORD_UNION);
                    }
                    else {
                        taskCompletedListener.onLoadingFailed(task.getException());
                        Log.d(TAG, "Error getting upazilas: ", task.getException());
                    }
                });


    }


    public interface OnTaskCompletedListener{
        void onLoadingComplete(List<String> mDivisionsList, int category);
        void onLoadingFailed(Exception exception);

    }
}
