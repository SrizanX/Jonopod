package com.olympiandroids.jonopod.ui.fireservice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.olympiandroids.jonopod.R;
import com.olympiandroids.jonopod.adapter.ServiceProfileAdapterAll;
import com.olympiandroids.jonopod.databinding.ServiceProfilesBinding;
import com.olympiandroids.jonopod.model.ServiceProfile;
import com.olympiandroids.jonopod.model.UserLocation;
import com.olympiandroids.jonopod.ui.selectlocation.LocationContract;


public class FireServiceFragment extends Fragment implements ServiceProfileAdapterAll.ProfileClickListener {

    FirebaseFirestore firestore;
    CollectionReference profilesRef;
    ServiceProfileAdapterAll adapter;
    ServiceProfilesBinding binding;
    SharedPreferences sharedPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
        profilesRef = firestore.collection("serviceProfies");
        sharedPref = requireContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ServiceProfilesBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String division = sharedPref.getString(LocationContract.DIVISION_NAME, "Not available");
        String district = sharedPref.getString(LocationContract.DISTRICT_NAME, "Not available");
        String upazila = sharedPref.getString(LocationContract.UPAZILLA_NAME, "Not available");
        String union = sharedPref.getString(LocationContract.UNION_NAME, "Not available");

        UserLocation location = new UserLocation(division, district, upazila, union);
        Query query = profilesRef.whereEqualTo("sector","Fire service")
                .whereEqualTo("location", location);
        FirestoreRecyclerOptions<ServiceProfile> options = new FirestoreRecyclerOptions.Builder<ServiceProfile>()
                .setQuery(query, ServiceProfile.class)
                .build();
        adapter = new ServiceProfileAdapterAll(options,this);


        binding.recyclerViewAllProfile.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(adapter!=null)
            adapter.stopListening();
        binding = null;
    }

    @Override
    public void onCallButtonClick(DocumentSnapshot documentSnapshot, int position) {
        DocumentReference profile = firestore.collection("serviceProfies").document(documentSnapshot.getId());
        profile.get().addOnSuccessListener(documentSnapshot1 -> {
            String phone = documentSnapshot1.getString("phone");
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
            startActivity(intent);
        });
    }
}