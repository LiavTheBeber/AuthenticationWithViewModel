package com.example.authenticationwithviewmodel.settings.companyUser.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.viewModel.MainViewModel;
import com.google.android.material.textfield.TextInputEditText;


public class EditCompanyUserValues extends Fragment implements MainViewModel.FragmentCallback{

    private TextView tvTitle;
    private TextInputEditText etNewValue;
    private AppCompatButton btnSave;
    private MainViewModel mainViewModel;
    private Bundle args;
    private String fragmentName,KEY_FRAGMENT_NAME = "FragmentName";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.setFragmentCallback(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_company_user_values, container, false);
        tvTitle = view.findViewById(R.id.tvTitle);
        etNewValue = view.findViewById(R.id.etNewValue);
        btnSave = view.findViewById(R.id.btnSave);
        args = getArguments();
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (args != null) {
            fragmentName = args.getString(KEY_FRAGMENT_NAME);
            Log.d("fragmentName", "onViewCreated: " + fragmentName);
        }

        setPageInformation(fragmentName);

        btnSave.setOnClickListener(v -> {
            if (fragmentName.equals("Company Name")){
                mainViewModel.updateCompanyUserName(etNewValue.getText().toString(),"companyName");
            }
            else if (fragmentName.equals("Company Bio")){
                mainViewModel.updateCompanyUserBio(etNewValue.getText().toString(),"companyBio");
            }
            else if (fragmentName.equals("Company Phone")){
                mainViewModel.updateCompanyPhone(etNewValue.getText().toString(),"companyPhone");
            }
        });
    }

    private void setPageInformation(String fragmentName){
        if (fragmentName.equals("Company Name")){
            tvTitle.setText("Insert Your Company Name Here");
            etNewValue.setHint("Company Name");
        }
        else if (fragmentName.equals("Company Bio")){
            tvTitle.setText("Insert Your Company Bio Here");
            etNewValue.setHint("Company Bio");
        }
        else if (fragmentName.equals("Company Phone")){
            tvTitle.setText("Insert Your Company's Phone Here");
            etNewValue.setHint("Company Phone");
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
    public void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}