package com.example.authenticationwithviewmodel.views.normalRegister.resume;

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
import com.google.android.material.textfield.TextInputEditText;


public class NormalRegisterFragment2_2 extends Fragment implements AuthViewModel.FragmentCallback{

    private AppCompatButton btnBack,btnNext;
    private AuthViewModel authViewModel;
    private TextInputEditText etLifeResume;

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
        View view =  inflater.inflate(R.layout.fragment_normal_register_fragment2_2, container, false);
        btnBack = view.findViewById(R.id.btnBack);
        btnNext = view.findViewById(R.id.btnForward);
        etLifeResume = view.findViewById(R.id.etRegKorotHaim);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authViewModel.getSavedLifeResume().observe(getViewLifecycleOwner(),lifeResume->{
            etLifeResume.setText(lifeResume);
        });

        btnBack.setOnClickListener(v -> {
            replaceFragment(new NormalRegisterFragment2_1());
        });

        btnNext.setOnClickListener(v -> {
            String lifeResume = etLifeResume.getText().toString();
            authViewModel.saveResumeSecondPageCredentials(lifeResume);
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