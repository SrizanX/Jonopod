package com.olympiandroids.jonopod.ui.localgov;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.olympiandroids.jonopod.adapter.ServiceProfileAdapterAll;
import com.olympiandroids.jonopod.databinding.ServiceProfilesBinding;
import com.olympiandroids.jonopod.model.ServiceProfile;


public class LocalGovFragment extends Fragment {

    FirebaseFirestore firestore;
    CollectionReference profilesRef;
    ServiceProfileAdapterAll adapter;
    ServiceProfilesBinding binding;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firestore = FirebaseFirestore.getInstance();
        profilesRef = firestore.collection("serviceProfies");

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

        Query query = profilesRef.whereEqualTo("sector","Local Government");
        FirestoreRecyclerOptions<ServiceProfile> options = new FirestoreRecyclerOptions.Builder<ServiceProfile>()
                .setQuery(query, ServiceProfile.class)
                .build();
        adapter = new ServiceProfileAdapterAll(options);

        Log.d("ASD", adapter.toString());
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
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.add("asadasda");


    }
}