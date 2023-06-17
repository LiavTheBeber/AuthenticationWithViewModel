package com.example.authenticationwithviewmodel.views.main.companyUserFragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.sideClasses.MessageDialog;
import com.example.authenticationwithviewmodel.viewModel.MainViewModel;
import com.example.authenticationwithviewmodel.viewModel.SharedViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ViewCompanyProfileFragment extends Fragment {
    private SharedViewModel sharedViewModel;
    private MainViewModel mainViewModel;
    private TextView tvName,tvPhone,tvEmail,tvBio,tvRating;
    private RatingBar ratingBar;
    private MapView mapView;
    private GoogleMap mMap;
    private CircleImageView profilePic;
    private LinearLayout imageContainer;
    private List<Uri> imagesUris;
    private LatLng markerLatlng;
    private Button sendMessage;
    private static final int REQUEST_SEND_SMS_PERMISSION = 123;
    private Boolean isPermissionGranted;
    private String phoneNumber,username,stringFirstName,stringSurname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_view_company_profile, container, false);
        tvName = view.findViewById(R.id.tvCompanyName);
        tvPhone = view.findViewById(R.id.phoneId);
        tvEmail = view.findViewById(R.id.email);
        tvBio = view.findViewById(R.id.textDescription);
        tvRating = view.findViewById(R.id.rating_number);
        ratingBar = view.findViewById(R.id.ratingBar);
        mapView = view.findViewById(R.id.mapView);
        profilePic = view.findViewById(R.id.imageProfile);
        imageContainer = view.findViewById(R.id.imageContainer);
        sendMessage = view.findViewById(R.id.btnSetInterview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(callback);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateTransferredCompanyUserData();

        isPermissionGranted();


        sendMessage.setOnClickListener(v -> {
            if (isPermissionGranted){
                sendSmsMessage();
            }
            else {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.SEND_SMS},
                        REQUEST_SEND_SMS_PERMISSION);
                Toast.makeText(requireContext(), "pls accept permission", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void isPermissionGranted(){
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.SEND_SMS},
                    REQUEST_SEND_SMS_PERMISSION);
            isPermissionGranted = false;
        }
        else
            isPermissionGranted = true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SEND_SMS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, send the SMS
                isPermissionGranted = true;
            } else {
                // Permission denied
                // Handle the case where the user denied the permission
                isPermissionGranted = false;
            }
        }
    }

    public void sendSmsMessage(){
        mainViewModel.getSavedFirstNameNormal().observe(getViewLifecycleOwner(),firstName->{
            stringFirstName = firstName;
            mainViewModel.getSavedSurnameNormal().observe(getViewLifecycleOwner(),surname->{
                stringSurname = surname;
                username = firstName + " " + surname;
            });
        });

        String messageToCompany = "Hello! The user " + username + " wants to work for your company.\n" +
                "If you want to have a further conversation with the user please reply.";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber,null,messageToCompany,null,null);
        Toast.makeText(requireContext(), "sms sent", Toast.LENGTH_SHORT).show();
    }



    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mMap = googleMap;
            if (markerLatlng!=null){
                MarkerOptions markerOptions = new MarkerOptions().position(markerLatlng)
                        .title("Your Business Location");
                mMap.addMarker(markerOptions);

                // Move the camera to the marker location
                moveCameraAnimated(markerLatlng,15f);
            }
        }
    };

    private void moveCameraAnimated(LatLng latLng, float zoom) {
        if (mMap != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(zoom)
                    .build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private void updateTransferredCompanyUserData(){
        sharedViewModel.getTransferredCompanyUser().observe(getViewLifecycleOwner(),transferredCompanyUser->{

            phoneNumber = transferredCompanyUser.getPhone();


            Picasso.get().load(transferredCompanyUser.getProfilePic()).into(profilePic);
            imagesUris = transferredCompanyUser.getExtraImages();
            for (int i = 0; i < imagesUris.size(); i++) {
                Uri imageUri = imagesUris.get(i);

                ImageView imageView = new ImageView(requireContext());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Picasso.get().load(imageUri).into(imageView);
                // Add the ImageView to the container
                imageContainer.addView(imageView);
            }

            tvName.setText("Company Name: " + transferredCompanyUser.getUsername());
            tvPhone.setText("Phone: " + transferredCompanyUser.getPhone());
            tvEmail.setText("Email: " + transferredCompanyUser.getEmail());
            tvRating.setText(String.valueOf(transferredCompanyUser.getRatingBar()));
            tvBio.setText(transferredCompanyUser.getBio());
            ratingBar.setRating(transferredCompanyUser.getRatingBar());
            markerLatlng = transferredCompanyUser.getLocation();
            mapView.getMapAsync(callback);
            mapView.onResume();


        });
    }


}