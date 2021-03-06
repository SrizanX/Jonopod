package com.olympiandroids.jonopod.ui.feed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.olympiandroids.jonopod.databinding.FragmentFeedsBinding;
import com.olympiandroids.jonopod.model.Report;

public class FeedFragment extends Fragment implements FeedAdapter.FeedClickListener {

    private FeedViewModel feedViewModel;
    private FragmentFeedsBinding binding;
    private FeedAdapter adapter;
    CollectionReference feedsRef;
    FirebaseFirestore firestore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
        feedsRef = firestore.collection("reports");
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        feedViewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        binding = FragmentFeedsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Query query = feedsRef.whereEqualTo("publicPost",true);

        FirestoreRecyclerOptions<Report> options = new FirestoreRecyclerOptions.Builder<Report>()
                .setQuery(query, Report.class)
                .build();
        adapter = new FeedAdapter(options,this);
        binding.recyclerViewFeeds.setAdapter(adapter);
        //getUserData();

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
        binding = null;
    }


    @Override
    public void onFeedClick(DocumentSnapshot documentSnapshot, int position) {


        DocumentReference report = firestore.collection("reports").document(documentSnapshot.getId());
        report.get().addOnSuccessListener(documentSnapshot1 -> {
            GeoPoint geoPoint = documentSnapshot1.getGeoPoint("geoPoint");
            assert geoPoint != null;
            double lat = geoPoint.getLatitude();
            double lng = geoPoint.getLongitude();

            Uri gmmIntentUri = Uri.parse("geo:"+lat+"," + lng+"?q="+lat+"," + lng);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivity(mapIntent);
            }

        });
    }
}