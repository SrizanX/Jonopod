package com.olympiandroids.jonopod.ui.report;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.olympiandroids.jonopod.R;
import com.olympiandroids.jonopod.databinding.FragmentReportBinding;
import com.olympiandroids.jonopod.model.Report;
import com.olympiandroids.jonopod.model.UserLocation;

import java.util.Calendar;
import java.util.Objects;

public class ReportFragment extends Fragment implements View.OnClickListener {
    private FragmentReportBinding binding;
    private ActivityResultLauncher<String> mGetImage;
    private ReportViewModel reportViewModel;
    UserLocation mUserLocation;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    StorageReference storageRef;
//    private String imageUrl;
    private Uri imageUri;
    public LatLng latLng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        reportViewModel = new ViewModelProvider(requireActivity()).get(ReportViewModel.class);

        mGetImage = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if(result!=null) {
                binding.imageView1Report.setImageURI(result);
                imageUri = result;


            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.imageView1Report.setOnClickListener(v -> mGetImage.launch("image/*"));
        binding.imageButtonAddImage.setOnClickListener(this);
        binding.buttonGetLocation.setOnClickListener(this);
        binding.buttonSubmitReport.setOnClickListener(this);
        binding.buttonSelectAuthority.setOnClickListener(this);



        // Create the observer which updates the UI.
        final Observer<UserLocation> nameObserver = userLocation -> {
            mUserLocation = userLocation;
            String COMMA_SPACE = ", ";
            String location = userLocation.getDivision() + COMMA_SPACE
                    + userLocation.getDistrict() + COMMA_SPACE
                    + userLocation.getUpazila() + COMMA_SPACE
                    + userLocation.getUnion();
            binding.textViewAuthorityLocation.setText(location);
            //Toast.makeText(requireContext(), location, Toast.LENGTH_SHORT).show();

        };
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        reportViewModel.getUserLocation().observe(requireActivity(), nameObserver);
        reportViewModel.getLocationCoordinates().observe(requireActivity(), latLng -> {
            this.latLng = latLng;

            String latitude = getString(R.string.latitude, String.valueOf(latLng.latitude));
            String longitude = getString(R.string.longitude, String.valueOf(latLng.longitude));
            binding.textViewLatitude.setText(latitude);
            binding.textViewLongitude.setText(longitude);
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==binding.buttonSubmitReport.getId()){
            if(imageUri!=null){
                submitReport();
            }

            else{
                Toast.makeText(requireContext(), "Select Image!", Toast.LENGTH_SHORT).show();
            }

        }
        if(view.getId()==binding.buttonGetLocation.getId()){
            Navigation.findNavController(view).navigate(R.id.action_navigation_report_to_mapsFragment);
        }
        if(view.getId()==binding.buttonSelectAuthority.getId()){
            ReportFragmentDirections.ActionNavigationReportToNavigationSelectLocation
                    action = ReportFragmentDirections.actionNavigationReportToNavigationSelectLocation();
            action.setOpenedFromReportFragment(true);
            Navigation.findNavController(view).navigate(action);
        }
    }

    private void submitReport() {
        StorageReference image = storageRef.child("images/" + imageUri.getLastPathSegment()+ Calendar.getInstance().getTimeInMillis());
        UploadTask uploadTask = image.putFile(imageUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();
                postDocument(imageUrl);
                //Toast.makeText(requireContext(), uri.toString(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void postDocument(String imageUrl) {

        String statement = Objects.requireNonNull(binding.tietReportStatement.getText()).toString();
        String category = binding.spinnerReportCategory.getSelectedItem().toString();
        String userUID= firebaseAuth.getUid();
        GeoPoint geoPoint = new GeoPoint(latLng.latitude,latLng.longitude);

        boolean anonymous = binding.checkBoxAnonymous.isChecked();
        boolean publicPost = binding.checkBoxPublicPost.isChecked();

        Report report = new Report(
                category,
                statement,
                geoPoint,
                imageUrl,
                userUID,
                mUserLocation,
                anonymous,
                publicPost);

        firestore.collection("reports").add(report)
                .addOnSuccessListener(documentReference -> Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show());


    }

}