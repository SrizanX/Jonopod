package com.olympiandroids.jonopod.ui.authentication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.olympiandroids.jonopod.R;
import com.olympiandroids.jonopod.databinding.FragmentRegisterBinding;
import com.olympiandroids.jonopod.utils.Validate;

public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    AuthenticationViewModel mAuthenticationViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        mAuthenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuthenticationViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
            if(firebaseUser!=null){
                Navigation.findNavController(view).navigate(R.id.navigation_profile);
            }
        });

        binding.buttonRegister.setOnClickListener(v -> {
            String name = binding.etName.getText().toString();
            String gender = binding.spinnerGender.getSelectedItem().toString();
            String email = binding.etEmailR.getText().toString();
            String password = binding.etPasswordConfirmR.getText().toString();

             if(TextUtils.isEmpty(name)){
                Toast.makeText(requireActivity(), "Enter Your name!", Toast.LENGTH_SHORT).show();
            }
            else if(!Validate.emailIsValid(email)){
                Toast.makeText(requireActivity(), "Invalid Email", Toast.LENGTH_SHORT).show();
            }

            else if(!Validate.passwordIsValid(password)) {
                Toast.makeText(requireActivity(), "Invalid Password", Toast.LENGTH_SHORT).show();
            }
            else {
                mAuthenticationViewModel.register(name,email, password,gender);
            }
        });
    }
}
