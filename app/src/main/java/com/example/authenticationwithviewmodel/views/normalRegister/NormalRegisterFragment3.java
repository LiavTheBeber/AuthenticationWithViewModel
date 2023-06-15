package com.example.authenticationwithviewmodel.views.normalRegister;

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
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.viewModel.AuthViewModel;
import com.example.authenticationwithviewmodel.views.companyRegister.CompanyRegisterFragment3;

public class NormalRegisterFragment3 extends Fragment implements AuthViewModel.FragmentCallback{
    private AppCompatButton btnBack,btnFinish;
    private CheckBox acceptTermsCheckBox;
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
        View view =  inflater.inflate(R.layout.fragment_normal_register3, container, false);
        btnBack = view.findViewById(R.id.btnBack);
        btnFinish = view.findViewById(R.id.btnFinish);
        acceptTermsCheckBox = view.findViewById(R.id.checkbox_terms);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnBack.setOnClickListener(v -> {
            replaceFragment(new CompanyRegisterFragment3());
        });

        btnFinish.setOnClickListener(v -> {
            if (acceptTermsCheckBox.isChecked()){
                Boolean IsTermsAccepted = acceptTermsCheckBox.isChecked();
                String TAG = "normalTAG";
                authViewModel.saveThirdPageNormalUserCredentials(IsTermsAccepted,TAG);
                authViewModel.registerUser(requireContext());
            }
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