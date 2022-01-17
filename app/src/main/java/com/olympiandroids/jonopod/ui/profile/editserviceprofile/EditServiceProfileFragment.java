package com.olympiandroids.jonopod.ui.profile.editserviceprofile;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.olympiandroids.jonopod.R;
import com.olympiandroids.jonopod.databinding.FragmentEditServiceProfileBinding;
import com.olympiandroids.jonopod.model.ServiceProfile;
import com.olympiandroids.jonopod.model.UserLocation;

public class EditServiceProfileFragment extends Fragment implements View.OnClickListener{
    FragmentEditServiceProfileBinding binding;
    UserLocation mUserLocation;
    EditServiceProfileViewModel profileViewModel;
    private ActivityResultLauncher<String> mGetImage;
    private Uri imageUri;

    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    StorageReference storageRef;

    //EditServiceProfileFragmentArgs args;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Firebase Instances
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        //
        profileViewModel = new ViewModelProvider(requireActivity()).get(EditServiceProfileViewModel.class);


        mGetImage = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if(result!=null) {
                binding.imageViewEditProfile.setImageURI(result);
                imageUri = result;
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditServiceProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Adapter For Sectors
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.sectors, android.R.layout.simple_spinner_dropdown_item);
        binding.tietEditProfileSector.setAdapter(adapter);
        //Button Listeners
        binding.buttonOpenSelectLocation.setOnClickListener(this);
        binding.buttonAddProfile.setOnClickListener(this);

        binding.buttonImageEdit.setOnClickListener(this);




        // Create the observer which updates the UI.
        final Observer<UserLocation> nameObserver = new Observer<UserLocation>() {
            @Override
            public void onChanged(UserLocation userLocation) {
                mUserLocation = userLocation;
                String COMMA_SPACE = ", ";
                String location = userLocation.getDivision() + COMMA_SPACE
                        + userLocation.getDistrict() + COMMA_SPACE
                        + userLocation.getUpazila() + COMMA_SPACE
                        + userLocation.getUnion();
                binding.textViewFullLocation.setText(location);
            }
        };
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        profileViewModel.getUserLocation().observe(requireActivity(), nameObserver);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==binding.buttonImageEdit.getId()){
            mGetImage.launch("image/*");
        }
        //Select Location Button
        if (view.getId() == binding.buttonOpenSelectLocation.getId()) {
            EditServiceProfileFragmentDirections.ActionNavigationEditServiceProfileFragmentToNavigationSelectLocation
                    action = EditServiceProfileFragmentDirections.actionNavigationEditServiceProfileFragmentToNavigationSelectLocation();
            action.setOpenedFromProfileFragment(true);
            Navigation.findNavController(view).navigate(action);
        }

        //Add Profile Button
        if (view.getId() == binding.buttonAddProfile.getId()) {
            if(TextUtils.isEmpty(binding.tietEditProfileName.getText().toString().trim())){
                binding.tietEditProfileName.setError("Enter Name");
            }
            else if(TextUtils.isEmpty(binding.tietEditProfileSector.getText().toString().trim())){
                binding.tietEditProfileSector.setError("Select sector");
            }
            else if(TextUtils.isEmpty(binding.tietEditProfileDesignation.getText().toString().trim())){
                binding.tietEditProfileDesignation.setError("Enter Designation");
            }
            else if(TextUtils.isEmpty(binding.tietEditProfilePhone.getText().toString().trim())){
                binding.tietEditProfilePhone.setError("Enter Phone Number!");
            }
            else if(imageUri==null){
                Toast.makeText(requireContext(), "Select Image!", Toast.LENGTH_SHORT).show();
            }
            else {
                addServiceProfile();
                Navigation.findNavController(view).navigate(R.id.navigation_profile);
                Toast.makeText(requireContext(), "Submitted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addServiceProfile() {
        StorageReference image = storageRef.child("images/" + imageUri.getLastPathSegment());
        UploadTask uploadTask = image.putFile(imageUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uploadedImageUrl) {

                    ServiceProfile serviceProfile = new ServiceProfile();
                    serviceProfile.setLocation(mUserLocation);
                    serviceProfile.setUUID(firebaseAuth.getUid());
                    serviceProfile.setImageLink(uploadedImageUrl.toString());
                    serviceProfile.setSector(binding.tietEditProfileSector.getText().toString().trim());
                    serviceProfile.setName(binding.tietEditProfileName.getText().toString().trim());
                    serviceProfile.setDesignation(binding.tietEditProfileDesignation.getText().toString().trim());
                    serviceProfile.setPhone(binding.tietEditProfilePhone.getText().toString().trim());
                    firestore.collection("serviceProfies").add(serviceProfile);

                }
            });

        }).addOnFailureListener(e -> {
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        });
    }

}