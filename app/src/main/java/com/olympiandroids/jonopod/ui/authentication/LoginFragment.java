package com.olympiandroids.jonopod.ui.authentication;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseUser;
import com.olympiandroids.jonopod.R;
import com.olympiandroids.jonopod.databinding.FragmentLoginBinding;
import com.olympiandroids.jonopod.utils.Validate;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private AuthenticationViewModel mAuthenticationViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater,container,false);
        mAuthenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuthenticationViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null){
                    Navigation.findNavController(view).navigate(R.id.action_navigation_authentication_to_navigation_profile);
                }
            }
        });


        binding.buttonLogin.setOnClickListener(v -> {
            String email = binding.etEmailL.getText().toString();
            String password = binding.etPasswordL.getText().toString();

            if(!Validate.emailIsValid(email)){
                Toast.makeText(requireActivity(), "Invalid Email", Toast.LENGTH_SHORT).show();
            }
            else if(!Validate.passwordIsValid(password)) {
                Toast.makeText(requireActivity(), "Invalid Password", Toast.LENGTH_SHORT).show();
            }
            else {
                mAuthenticationViewModel.login(email, password);
            }
        });
    }
}
