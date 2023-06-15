package com.example.authenticationwithviewmodel.views.normalRegister.resume;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.viewModel.AuthViewModel;
import com.example.authenticationwithviewmodel.views.companyRegister.CompanyRegisterFragment2;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class NormalRegisterFragment2_3 extends Fragment implements AuthViewModel.FragmentCallback{

    private static final int GALLERY_PERMISSION_REQUEST_CODE = 100;
    private static final int PICK_IMAGE_REQUEST_CODE = 101;
    private static final int PICK_IMAGES_REQUEST_CODE = 102;

    private ImageView normalUserProfileImageView;
    private List<Uri> imagesUris;
    private Uri profilePictureUri;
    private LinearLayout imageContainer;

    private AppCompatButton btnBack, btnNext,btnAddProfilePic,btnAddMorePics;
    private AuthViewModel authViewModel;

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
        View view =  inflater.inflate(R.layout.fragment_normal_register_fragment2_3, container, false);
        btnBack = view.findViewById(R.id.btnBack);
        btnNext = view.findViewById(R.id.btnForward);
        btnAddProfilePic = view.findViewById(R.id.btnAddProfPic);
        btnAddMorePics = view.findViewById(R.id.btnAddMorePics);
        normalUserProfileImageView = view.findViewById(R.id.normalUser_profile_imageView);
        imageContainer = view.findViewById(R.id.imageContainer);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        authViewModel.getSavedNormalUserProfileImageUri().observe(getViewLifecycleOwner(), normalUserProfileUri->{
            profilePictureUri = normalUserProfileUri;
            Picasso.get().load(profilePictureUri).into(normalUserProfileImageView);
        });

        authViewModel.getSavedNormalUserExtraImages().observe(getViewLifecycleOwner(),normalUserExtraImages->{
            if (normalUserExtraImages !=null){
                imagesUris = normalUserExtraImages;
                for (int i = 0; i<normalUserExtraImages.size(); i++){

                    Uri imageUri = normalUserExtraImages.get(i);

                    ImageView imageView = new ImageView(requireContext());
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Picasso.get().load(imageUri).into(imageView);

                    // Add the ImageView to the container
                    imageContainer.addView(imageView);
                }
            }
        });


        btnAddProfilePic.setOnClickListener(v -> {
            if (isGalleryPermissionGranted()){
                pickProfilePic();
            }
            else {
                requestGalleryPermission();
            }
        });

        btnAddMorePics.setOnClickListener(v -> {
            if (isGalleryPermissionGranted()){
                picExtraImages();
            }
            else {
                requestGalleryPermission();
            }
        });

        btnBack.setOnClickListener(v -> {
            replaceFragment(new NormalRegisterFragment2_2());
        });

        btnNext.setOnClickListener(v -> {
            authViewModel.saveResumeThirdPageCredentials(imagesUris,profilePictureUri);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            profilePictureUri = data.getData();
            Picasso.get().load(profilePictureUri).into(normalUserProfileImageView);
        }
        if (requestCode == PICK_IMAGES_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                int count = clipData.getItemCount();
                imagesUris = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    imagesUris.add(imageUri);

                    // Create and configure the ImageView
                    ImageView imageView = new ImageView(requireContext());
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Picasso.get().load(imageUri).into(imageView);

                    // Add the ImageView to the container
                    imageContainer.addView(imageView);
                }
            }
        }
    }



    private boolean isGalleryPermissionGranted() {
        int result = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestGalleryPermission() {
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_PERMISSION_REQUEST_CODE);
    }

    private void pickProfilePic() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    private void picExtraImages() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGES_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GALLERY_PERMISSION_REQUEST_CODE) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(requireContext(), "Please Accept Gallery Permission In Order To Continue", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
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