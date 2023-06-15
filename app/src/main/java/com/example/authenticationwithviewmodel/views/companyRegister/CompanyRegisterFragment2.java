package com.example.authenticationwithviewmodel.views.companyRegister;

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
import android.widget.Button;
import android.widget.Toast;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.viewModel.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;


public class CompanyRegisterFragment2 extends Fragment implements AuthViewModel.FragmentCallback{

    private AppCompatButton btnBack,btnProceed;
    private AuthViewModel authViewModel;
    private TextInputEditText etRegCompanyPhone,etRegCompanyBio;

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
        View view =  inflater.inflate(R.layout.fragment_company_register2, container, false);
        btnBack = view.findViewById(R.id.btnBack);
        btnProceed = view.findViewById(R.id.btnProceed);
        etRegCompanyPhone = view.findViewById(R.id.etRegCompanyPhone);
        etRegCompanyBio = view.findViewById(R.id.etRegInfo);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authViewModel.getSavedCompanyPhone().observe(getViewLifecycleOwner(), companyPhone -> {
            etRegCompanyPhone.setText(companyPhone);
        });

        authViewModel.getSavedCompanyBio().observe(getViewLifecycleOwner(), companyBio -> {
            etRegCompanyBio.setText(companyBio);
        });


        btnBack.setOnClickListener(v -> {
            replaceFragment(new CompanyRegisterFragment1());
        });

        btnProceed.setOnClickListener(v -> {
            String companyPhone = etRegCompanyPhone.getText().toString();
            String companyBio = etRegCompanyBio.getText().toString();
            authViewModel.saveSecondCompanyPageCredentials(companyPhone,companyBio);
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