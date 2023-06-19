package com.example.authenticationwithviewmodel.views.companyRegister;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.viewModel.AuthViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompanyRegisterFragment4 extends Fragment implements AuthViewModel.FragmentCallback {
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private AuthViewModel authViewModel;
    private Button btnSaveLocation;
    private MapView mapView;
    private GoogleMap mMap;
    private Boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LatLng markerLatlng;
    private EditText etSearchText;
    private float DefaultZoom = 15f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        authViewModel.setFragmentCallback(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_company_register4, container, false);
        btnSaveLocation = view.findViewById(R.id.btnSaveLocation);
        mapView = view.findViewById(R.id.mapView);
        etSearchText = view.findViewById(R.id.input_search);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(callback);
        return view;
    }
    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLocationPermission();
        mapView.onResume();
        btnSaveLocation.setOnClickListener(v -> {
            authViewModel.saveFourthPageCompanyCredentials(markerLatlng);
        });

        etSearchText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
            || event.getAction() == KeyEvent.ACTION_DOWN
            || event.getAction() == KeyEvent.KEYCODE_ENTER)
                //  execute our method for searching
                hideSoftKeyboard();
                geoLocate();
            return false;
        });
    }

    private void geoLocate() {
        String searchString = etSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(requireContext());
        List<Address> list = new ArrayList<>();

        try {
            list = geocoder.getFromLocationName(searchString,1);
        }catch (IOException e){
            Log.e("geoLocate: IOException", "geoLocate: IOException" + e.getMessage());
        }

        if (list.size() > 0){
            Address address = list.get(0);
            Log.d("geoLocate: found a location", "geoLocate: found a location" + address.toString());

            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DefaultZoom);

        }

    }


    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            if(mLocationPermissionGranted){
                getDeviceLocation();
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                moveCameraAnimated(new LatLng(31.065983, 34.877725),7f);
                mMap.setMyLocationEnabled(true);
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        mMap.clear();
                        markerLatlng = latLng;
                        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                                .title("Your Business Location");
                        mMap.addMarker(markerOptions);
                    }
                });

            }

        }
    };

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }



    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationTask = mFusedLocationProviderClient.getLastLocation();
                locationTask.addOnCompleteListener(requireActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = task.getResult();

                        } else {
                            // Handle unsuccessful task
                            Log.e(TAG, "getLastLocation: Exception: " + task.getException());
                        }
                    }
                });
            }

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(requireContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(requireContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            }
        } else {
            requestPermissionLauncher.launch(permissions);
        }
    }
    private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                    isGranted -> {
                        boolean allGranted = true;
                        for (Boolean granted : isGranted.values()) {
                            if (!granted) {
                                mLocationPermissionGranted = false;
                                allGranted = false;
                                break;
                            }
                        }
                        if (allGranted) {
                            // Permissions are granted, do something here
                            mLocationPermissionGranted = true;
                            initMap();
                        }
                    });
    private void moveCamera(LatLng latLng, float zoom) {
        if (mMap != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        }
    }
    private void moveCameraAnimated(LatLng latLng, float zoom) {
        if (mMap != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(zoom)
                    .build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusView = requireActivity().getCurrentFocus();
        if (imm != null && currentFocusView != null) {
            imm.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0);
        }
    }




    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(getId(), fragment)
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void showToast(String message){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}