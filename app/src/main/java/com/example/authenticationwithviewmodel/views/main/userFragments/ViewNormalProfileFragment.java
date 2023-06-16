package com.example.authenticationwithviewmodel.views.main.userFragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.sideClasses.User;
import com.example.authenticationwithviewmodel.viewModel.SharedViewModel;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ViewNormalProfileFragment extends Fragment {
    private CircleImageView profilePic;
    private TextView tvFirstName,tvSurname,tvPhone,tvEmail,tvGender,tvCity,tvAge,tvLifeResume;
    private LinearLayout imageContainer;
    private List<Uri> imagesUris;
    private Integer age;
    private SharedViewModel sharedViewModel;
    private Calendar birthdateCalendar = Calendar.getInstance();
    private Calendar currentDate = Calendar.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_normal_profile, container, false);
        tvFirstName = view.findViewById(R.id.tvFirstName);
        tvSurname = view.findViewById(R.id.tvSurname);
        tvPhone = view.findViewById(R.id.phoneId);
        tvEmail = view.findViewById(R.id.email);
        tvGender = view.findViewById(R.id.tvGender);
        tvCity = view.findViewById(R.id.tvCity);
        tvAge = view.findViewById(R.id.tvAge);
        tvLifeResume = view.findViewById(R.id.textDescription);
        profilePic = view.findViewById(R.id.imageProfile);
        imageContainer = view.findViewById(R.id.imageContainer);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateTransferredUserData();
    }

    private void updateTransferredUserData(){
        sharedViewModel.getTransferredUser().observe(getViewLifecycleOwner(),transferredUser->{
            Picasso.get().load(transferredUser.getProfilePic()).into(profilePic);
            imagesUris = transferredUser.getExtraImages();

            tvFirstName.setText(transferredUser.getFirstName());
            tvSurname.setText(transferredUser.getSurname());
            tvPhone.setText("Phone: " + transferredUser.getPhone());
            tvEmail.setText("Email: " + transferredUser.getEmail());
            tvGender.setText("Gender: " + transferredUser.getGender());
            tvCity.setText("City: " + transferredUser.getCity());

            birthdateCalendar.setTimeInMillis(transferredUser.getBirthdate().getTime());
            age = currentDate.get(Calendar.YEAR) - birthdateCalendar.get(Calendar.YEAR);
            if (currentDate.get(Calendar.MONTH) < birthdateCalendar.get(Calendar.MONTH) ||
                    (currentDate.get(Calendar.MONTH) == birthdateCalendar.get(Calendar.MONTH) &&
                            currentDate.get(Calendar.DAY_OF_MONTH) < birthdateCalendar.get(Calendar.DAY_OF_MONTH))) {
                age--;
            }
            tvAge.setText("Age: " +age);

            tvLifeResume.setText(transferredUser.getLifeResume());

            for (int i = 0; i < imagesUris.size(); i++) {
                Uri imageUri = imagesUris.get(i);

                ImageView imageView = new ImageView(requireContext());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Picasso.get().load(imageUri).into(imageView);
                // Add the ImageView to the container
                imageContainer.addView(imageView);
            }

        });
    }


}