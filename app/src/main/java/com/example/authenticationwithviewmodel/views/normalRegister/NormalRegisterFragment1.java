package com.example.authenticationwithviewmodel.views.normalRegister;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.authenticationwithviewmodel.views.normalRegister.resume.NormalRegisterFragment2_1;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;


public class NormalRegisterFragment1 extends Fragment implements AuthViewModel.FragmentCallback {

    private FloatingActionButton btnNext,btnBack;
    private TextInputEditText etRegEmail,etRegPass,etRegConfirmPass;
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
        View view =  inflater.inflate(R.layout.fragment_normal_register1, container, false);
        btnNext = view.findViewById(R.id.btnNext);
        etRegEmail = view.findViewById(R.id.etRegEmail);
        etRegPass = view.findViewById(R.id.etRegPass);
        etRegConfirmPass = view.findViewById(R.id.etRegConfirmPass);
        btnBack = view.findViewById(R.id.btnBack);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authViewModel.getSavedEmail().observe(getViewLifecycleOwner(), email -> {
            etRegEmail.setText(email);
        });

        authViewModel.getSavedPass().observe(getViewLifecycleOwner(), pass -> {
            etRegPass.setText(pass);
        });

        authViewModel.getSavedConfirmPass().observe(getViewLifecycleOwner(), confirmPass -> {
            etRegConfirmPass.setText(confirmPass);
        });


        btnNext.setOnClickListener(v -> {
            String email = etRegEmail.getText().toString();
            String pass = etRegPass.getText().toString();
            String confirmPass = etRegConfirmPass.getText().toString();

            authViewModel.saveFirstPageCredentials(email,pass,confirmPass);
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AuthActivity.class);
            startActivity(intent);
        });



    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(getId(), fragment)
                .commit();
    }

    public void showToast(String message){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

}