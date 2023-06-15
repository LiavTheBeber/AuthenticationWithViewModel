package com.example.authenticationwithviewmodel.views.companyRegister;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.viewModel.AuthViewModel;
import com.example.authenticationwithviewmodel.views.AuthActivity;
import com.google.android.material.textfield.TextInputEditText;


public class CompanyRegisterFragment1 extends Fragment implements AuthViewModel.FragmentCallback{

    private AppCompatButton btnProceed,btnBack;
    private TextInputEditText etCompanyName,etEmail,etPass,etConfirmPass;
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
        View view =  inflater.inflate(R.layout.fragment_company_register1, container, false);
        btnProceed = view.findViewById(R.id.btnProceed);
        btnBack = view.findViewById(R.id.btnBack);
        etCompanyName = view.findViewById(R.id.etRegCompanyName);
        etEmail = view.findViewById(R.id.etRegEmail);
        etPass = view.findViewById(R.id.etRegPass);
        etConfirmPass = view.findViewById(R.id.etRegConfirmPass);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authViewModel.getSavedCompanyName().observe(getViewLifecycleOwner(),companyName ->{
            etCompanyName.setText(companyName);
        });
        authViewModel.getSavedCompanyEmail().observe(getViewLifecycleOwner(),companyEmail ->{
            etEmail.setText(companyEmail);
        });
        authViewModel.getSavedCompanyPass().observe(getViewLifecycleOwner(),companyPass ->{
            etPass.setText(companyPass);
        });
        authViewModel.getSavedCompanyConfirmPass().observe(getViewLifecycleOwner(),companyConfirmPass ->{
            etConfirmPass.setText(companyConfirmPass);
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AuthActivity.class);
            startActivity(intent);
        });

        btnProceed.setOnClickListener(v -> {
            String companyName = etCompanyName.getText().toString();
            String email = etEmail.getText().toString();
            String pass = etPass.getText().toString();
            String confirmPass = etConfirmPass.getText().toString();
            authViewModel.saveFirstCompanyPageCredentials(companyName,email,pass,confirmPass);
        });
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