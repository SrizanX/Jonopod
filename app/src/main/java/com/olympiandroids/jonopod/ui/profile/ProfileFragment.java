package com.olympiandroids.jonopod.ui.profile;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.olympiandroids.jonopod.R;
import com.olympiandroids.jonopod.databinding.FragmentProfileBinding;
import com.olympiandroids.jonopod.model.ServiceProfile;
import com.olympiandroids.jonopod.model.User;

import java.util.Objects;

public class ProfileFragment extends Fragment implements ServiceProfileRecyclerAdapter.ServiceProfileClickListener {
    private ProfileViewModel mProfileViewModel;
    private FragmentProfileBinding binding;
    private FirebaseUser mUser;
    private ServiceProfileRecyclerAdapter adapter;
    FirebaseFirestore firestore;
    CollectionReference profilesRef;
    FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        firestore = FirebaseFirestore.getInstance();
        // Create a reference to the service profiles collection
        profilesRef = firestore.collection("serviceProfies");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        mProfileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mUser == null) {
            Navigation.findNavController(view).navigate(R.id.action_navigation_profile_to_navigation_authentication);
        }

        mProfileViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
            if (firebaseUser == null) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_profile_to_navigation_authentication);
            }
        });

        if(mUser!=null){
            Query query = profilesRef.whereEqualTo("uuid",mUser.getUid());
            FirestoreRecyclerOptions<ServiceProfile> options = new FirestoreRecyclerOptions.Builder<ServiceProfile>()
                    .setQuery(query, ServiceProfile.class)
                    .build();
            adapter = new ServiceProfileRecyclerAdapter(options,this);
            binding.recyclerView.setAdapter(adapter);
            getUserData();

        }


        binding.imageButtonAddServiceProfile.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_navigation_profile_to_navigation_editServiceProfileFragment);
        });

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


    //Recycler Views Click Listener
    @Override
    public void onEditButtonClick(DocumentSnapshot documentSnapshot, int position) {

        Toast.makeText(requireContext(), "Edit: " + documentSnapshot.getId(), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDeleteButtonClick(DocumentSnapshot documentSnapshot, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Delete Profile?");
        builder.setPositiveButton("Confirm", (dialog, which) -> {

            firestore.collection("serviceProfies").document(documentSnapshot.getId())
                    .delete();

            Toast.makeText(requireContext(), "Deletdeafbamndf", Toast.LENGTH_SHORT).show();

        });
        builder.show();
        Toast.makeText(requireContext(), "Delete: "+ documentSnapshot.getId(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_profile_logout) {
            mProfileViewModel.logOut();
            Toast.makeText(requireActivity(), "Logout", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    public void getUserData(){
        firestore.collection("users").document(Objects.requireNonNull(mAuth.getUid()))
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        setUserData(user);
                        assert user != null;
                        Log.d("ASD", user.getEmail());
                    }
                });
    }

    private void setUserData(User user) {
        if(user!=null){
            binding.textViewUserName.setText(user.getName());
            binding.textViewEmailAddress.setText(user.getEmail());
            binding.textViewGender.setText(user.getGender());
        }
    }
}