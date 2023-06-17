package com.example.authenticationwithviewmodel.views.main.userFragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Parcelable;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.sideClasses.MessageDialog;
import com.example.authenticationwithviewmodel.sideClasses.User;
import com.example.authenticationwithviewmodel.viewModel.SharedViewModel;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ViewNormalProfileFragment extends Fragment {
    private static final int REQUEST_SEND_SMS_PERMISSION = 123;
    private Boolean isPermissionGranted;
    private String phoneNumber;
    private MessageDialog messageDialog;
    private Button sendMessage;
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
        sendMessage = view.findViewById(R.id.btnSetInterview);

        messageDialog = new MessageDialog(requireContext(),this);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateTransferredUserData();

        isPermissionGranted();

        sendMessage.setOnClickListener(v -> {
            if (isPermissionGranted){
                messageDialog.show();
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

    public void sendSmsMessage(String message){
        SharedViewModel sharedViewModel1;
        sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel1.getTransferredUser().observe(getViewLifecycleOwner(),transferredUser->{
            phoneNumber = transferredUser.getPhone();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber,null,message,null,null);
            Toast.makeText(requireContext(), "sms sent", Toast.LENGTH_SHORT).show();
        });
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