package com.example.authenticationwithviewmodel.views.main.userFragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.viewModel.AuthViewModel;
import com.example.authenticationwithviewmodel.viewModel.MainViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class NormalUserProfileFragment extends Fragment {

    private MainViewModel mainViewModel;
    private TextView tvFirstName, tvSurname ,tvPhone, tvEmail,tvDescription,tvGender,tvCity,tvAge;
    private CircleImageView circleImageView;
    private List<Uri> imagesUris;
    private LinearLayout imageContainer;
    private int age;
    private Calendar currentDate = Calendar.getInstance();
    private Calendar birthdateCalendar = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_normal_user_profile, container, false);
        tvFirstName = view.findViewById(R.id.tvFirstName);
        tvSurname = view.findViewById(R.id.tvSurname);
        tvPhone = view.findViewById(R.id.phoneId);
        tvEmail = view.findViewById(R.id.email);
        tvDescription = view.findViewById(R.id.textDescription);
        tvGender = view.findViewById(R.id.tvGender);
        tvCity = view.findViewById(R.id.tvCity);
        tvAge = view.findViewById(R.id.tvAge);
        circleImageView = view.findViewById(R.id.imageProfile);
        imageContainer = view.findViewById(R.id.imageContainer);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateProfileValues();

    }

    private void updateProfileValues(){
        mainViewModel.getSavedFirstNameNormal().observe(getViewLifecycleOwner(),stringFirstName->{
            tvFirstName.setText(stringFirstName);
        });
        mainViewModel.getSavedSurnameNormal().observe(getViewLifecycleOwner(),stringSurname->{
            tvSurname.setText(stringSurname);
        });
        mainViewModel.getSavedEmailNormal().observe(getViewLifecycleOwner(), stringNormalEmail ->
                tvEmail.setText("Email: " + stringNormalEmail));

        mainViewModel.getSavedPhoneNormal().observe(getViewLifecycleOwner(), stringNormalPhone ->
                tvPhone.setText("Phone: " + stringNormalPhone));

        mainViewModel.getSavedLifeResumeNormal().observe(getViewLifecycleOwner(), stringLifeResumeNormal ->
                tvDescription.setText("LifeResume: " + stringLifeResumeNormal));

        mainViewModel.getSavedGenderNormal().observe(getViewLifecycleOwner(), stringNormalGender ->
                tvGender.setText("Gender: " + stringNormalGender));

        mainViewModel.getSavedCityNormal().observe(getViewLifecycleOwner(), stringNormalCity ->
                tvCity.setText("City: " + stringNormalCity));

        mainViewModel.getSavedAgeNormal().observe(getViewLifecycleOwner(), age -> {
            tvAge.setText("Age: " + age);
        });
        mainViewModel.getSavedProfilePicNormal().observe(getViewLifecycleOwner(), uri -> {
            Picasso.get().load(uri).into(circleImageView);
        });
        mainViewModel.getSavedExtraImagesNormal().observe(getViewLifecycleOwner(), uris -> {
            imagesUris = uris;
            Log.d("images", "onChanged: images are saved to list uri" + imagesUris.size());
            for (int i = 0; i<imagesUris.size(); i++){

                Uri imageUri = imagesUris.get(i);

                ImageView imageView = new ImageView(requireContext());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Picasso.get().load(imageUri).into(imageView);
                Log.d("images", "onChanged: saved image");
                // Add the ImageView to the container
                imageContainer.addView(imageView);
            }
        });
    }

}