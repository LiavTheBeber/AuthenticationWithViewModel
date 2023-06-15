package com.example.authenticationwithviewmodel.settings.normalUser.account;

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
import com.example.authenticationwithviewmodel.viewModel.AuthViewModel;
import com.example.authenticationwithviewmodel.viewModel.MainViewModel;
import com.google.android.material.textfield.TextInputEditText;


public class EditUserValuesFragment extends Fragment implements MainViewModel.FragmentCallback{

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
        View view =  inflater.inflate(R.layout.fragment_edit_user_values, container, false);
        tvTitle = view.findViewById(R.id.tvTitle);
        etNewValue = view.findViewById(R.id.etNewValue);
        btnSave = view.findViewById(R.id.btnSave);
        args = getArguments();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (args != null){
            fragmentName = args.getString(KEY_FRAGMENT_NAME);
            Log.d("fragmentName", "onViewCreated: "+ fragmentName);
        }

        setPageInformation(fragmentName);

        btnSave.setOnClickListener(v -> {
            if (fragmentName.equals("FirstName")){
                mainViewModel.updateNormalUserFirstName(etNewValue.getText().toString(),"normalFirstName");
            }
            else if (fragmentName.equals("Surname")){
                mainViewModel.updateNormalUserSurname(etNewValue.getText().toString(),"normalSurname");
            }
            else if (fragmentName.equals("Phone")){
                mainViewModel.updateNormalUserPhone(etNewValue.getText().toString(),"normalPhone");
            }
            else if (fragmentName.equals("City")){
                mainViewModel.updateNormalUserCity(etNewValue.getText().toString(),"normalCity");
            }
            else if (fragmentName.equals("LifeResume")){
                mainViewModel.updateNormalUserLifeResume(etNewValue.getText().toString(),"normalLifeResume");
            }
        });

    }

    private void setPageInformation(String fragmentName){
        if (fragmentName.equals("FirstName")){
            tvTitle.setText("Insert Your First Name Here");
            etNewValue.setHint("First Name");
        }
        else if (fragmentName.equals("Surname")){
            tvTitle.setText("Insert Your Surname Here");
            etNewValue.setHint("Surname");
        }
        else if (fragmentName.equals("Phone")){
            tvTitle.setText("Insert Your Phone Here");
            etNewValue.setHint("Phone");
        }
        else if (fragmentName.equals("City")){
            tvTitle.setText("Insert Your City Here");
            etNewValue.setHint("City");
        }
        else if (fragmentName.equals("LifeResume")){
            tvTitle.setText("Insert Your Life Resume Here");
            etNewValue.setHint("Life Resume");
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