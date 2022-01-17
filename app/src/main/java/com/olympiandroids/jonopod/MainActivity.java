package com.olympiandroids.jonopod;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.olympiandroids.jonopod.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {



    private ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private NavController mNavController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_feeds,
                R.id.navigation_report,
                R.id.navigation_profile)
                .build();

        NavigationUI.setupActionBarWithNavController(this, mNavController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, mNavController);

        mNavController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if(destination.getId()==R.id.navigation_home
            || destination.getId()==R.id.navigation_feeds
            || destination.getId()==R.id.navigation_report
            || destination.getId()==R.id.navigation_profile){
                binding.navView.setVisibility(View.VISIBLE);
            }
            else {
                binding.navView.setVisibility(View.GONE);
            }
        });




    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(mNavController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

}