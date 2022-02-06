package com.olympiandroids.jonopod.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.olympiandroids.jonopod.R;
import com.olympiandroids.jonopod.ui.report.ReportViewModel;

import java.util.Locale;

public class MapsFragment extends Fragment {

    private ReportViewModel reportViewModel;

    private GoogleMap map;
    private LatLng currentLatlong;
    private int REQUEST_LOCATION_PERMISSION = 1;

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            LatLng dhaka = new LatLng(24, 90);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dhaka, 6.5f));
            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setAllGesturesEnabled(true);

            map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    if(map.getMyLocation()!=null){
                        currentLatlong = new LatLng(map.getMyLocation().getLatitude(),map.getMyLocation().getLongitude());
                        Toast.makeText(requireActivity(), currentLatlong.toString(), Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });


            googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDrag(@NonNull Marker marker) {
                    //Toast.makeText(requireContext(), marker.getPosition().toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onMarkerDragEnd(@NonNull Marker marker) {
                    currentLatlong = marker.getPosition();
                    Toast.makeText(requireContext(), marker.getPosition().toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onMarkerDragStart(@NonNull Marker marker) {
                    //Toast.makeText(requireContext(), marker.getPosition().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            setMapLongClick(googleMap);
            enableMyLocation();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reportViewModel = new ViewModelProvider(requireActivity()).get(ReportViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.map_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.map_select_done) {
            if(map.getMyLocation()!=null){
                LatLng latLng = new LatLng(map.getMyLocation().getLatitude(),map.getMyLocation().getLongitude());
                //Toast.makeText(requireActivity(), latLng.toString(), Toast.LENGTH_SHORT).show();
                reportViewModel.setLocationCoordinates(latLng);


                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).popBackStack();
            }

        }
        return true;
    }

    private void setMapLongClick(GoogleMap map) {
        map.setOnMapLongClickListener(latLng -> {
            String snippet = String.format(Locale.getDefault(),
                    "Lat: %1$.5f, Long: %2$.5f",
                    latLng.latitude, latLng.longitude);
            map.getMyLocation();
            map.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Dropped Pin")
                    .draggable(true)
                    .snippet(snippet));
        });

    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void enableMyLocation() {
        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            map.setMyLocationEnabled(true);
        }
        else {
            ActivityCompat.requestPermissions(
                    requireActivity(),
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation();
            }
        }
    }
}