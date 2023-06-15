package com.example.authenticationwithviewmodel.views.main.companyUserFragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.viewModel.MainViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class CompanyUserProfileFragment extends Fragment {
    private MainViewModel mainViewModel;
    private TextView tvUsername,tvPhone,tvEmail,tvBio,tvRatingBar;
    private RatingBar ratingBar;
    private MapView mapView;
    private GoogleMap mMap;
    private CircleImageView profilePic;
    private LinearLayout imageContainer;
    private LatLng markerLatlng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_company_user_profile, container, false);
        tvUsername = view.findViewById(R.id.tvCompanyName);
        tvPhone = view.findViewById(R.id.phoneId);
        tvEmail = view.findViewById(R.id.email);
        tvBio = view.findViewById(R.id.textDescription);
        tvRatingBar = view.findViewById(R.id.rating_number);
        imageContainer = view.findViewById(R.id.imageContainer);
        profilePic = view.findViewById(R.id.imageProfile);
        mapView = view.findViewById(R.id.mapView);
        ratingBar = view.findViewById(R.id.ratingBar);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(callback);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateProfileValues();
        ratingBar.setIsIndicator(true);
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
                moveCameraAnimated(markerLatlng,7f);
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


    private void updateProfileValues(){
        mainViewModel.getSavedNameCompany().observe(getViewLifecycleOwner(),companyName->{
            tvUsername.setText(companyName);
        });
        mainViewModel.getSavedPhoneCompany().observe(getViewLifecycleOwner(),companyPhone->{
            tvPhone.setText("Phone: " + companyPhone);
        });
        mainViewModel.getSavedEmailCompany().observe(getViewLifecycleOwner(),companyEmail->{
            tvEmail.setText("Email: " + companyEmail);
        });
        mainViewModel.getSavedRatingBarValue().observe(getViewLifecycleOwner(),companyRatingBarFloat->{
            ratingBar.setRating(companyRatingBarFloat);
            tvRatingBar.setText(String.valueOf(companyRatingBarFloat));
        });
        mainViewModel.getSavedBioCompany().observe(getViewLifecycleOwner(),companyBio->{
            tvBio.setText(companyBio);
        });
        mainViewModel.getSavedProfilePicCompany().observe(getViewLifecycleOwner(),companyProfilePic->{
            Picasso.get().load(companyProfilePic).into(profilePic);
        });
        mainViewModel.getSavedExtraImagesCompany().observe(getViewLifecycleOwner(),companyExtraImages->{
            for (int i = 0; i<companyExtraImages.size(); i++){

                Uri imageUri = companyExtraImages.get(i);

                ImageView imageView = new ImageView(requireContext());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Picasso.get().load(imageUri).into(imageView);
                Log.d("images", "onChanged: saved image");
                // Add the ImageView to the container
                imageContainer.addView(imageView);
            }
        });
        mainViewModel.getSavedLocationCompany().observe(getViewLifecycleOwner(),companyLocation->{
            markerLatlng = companyLocation;
            mapView.getMapAsync(callback);
            mapView.onResume();
        });
    }
}