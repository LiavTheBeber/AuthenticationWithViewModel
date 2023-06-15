package com.example.authenticationwithviewmodel.views.normalRegister.resume;

import android.app.DatePickerDialog;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.viewModel.AuthViewModel;
import com.example.authenticationwithviewmodel.views.normalRegister.NormalRegisterFragment1;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class NormalRegisterFragment2_1 extends Fragment implements AuthViewModel.FragmentCallback{
    private AppCompatButton btnBack,btnForward;
    private AuthViewModel authViewModel;
    private TextInputEditText etRegFirstName,etRegSurname,etRegPhone,etRegCity;
    private TextView etRegBirthdate;
    private CheckBox checkMale,checkFemale,checkOther;

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
        View view =  inflater.inflate(R.layout.fragment_normal_register2_1, container, false);
        btnBack = view.findViewById(R.id.btnBack);
        btnForward = view.findViewById(R.id.btnForward);
        etRegFirstName = view.findViewById(R.id.etFirstName);
        etRegSurname = view.findViewById(R.id.etSurname);
        etRegPhone = view.findViewById(R.id.etPhone);
        etRegCity = view.findViewById(R.id.etCity);
        etRegBirthdate = view.findViewById(R.id.etBirthdate);
        checkMale = view.findViewById(R.id.checkbox_male);
        checkFemale = view.findViewById(R.id.checkbox_female);
        checkOther = view.findViewById(R.id.checkbox_other);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authViewModel.getSavedFirstName().observe(getViewLifecycleOwner(), firstName -> {
            etRegFirstName.setText(firstName);
        });
        authViewModel.getSavedSurname().observe(getViewLifecycleOwner(), surname -> {
            etRegSurname.setText(surname);
        });
        authViewModel.getSavedPhone().observe(getViewLifecycleOwner(), phone -> {
            etRegPhone.setText(phone);
        });
        authViewModel.getSavedCity().observe(getViewLifecycleOwner(), city -> {
            etRegCity.setText(city);
        });
        authViewModel.getSavedBirthdate().observe(getViewLifecycleOwner(), birthdate -> {
            etRegBirthdate.setText(birthdate);
        });
        authViewModel.getSavedCheckboxMale().observe(getViewLifecycleOwner(),BoolCheckMale -> {
            checkMale.setChecked(BoolCheckMale);
        });
        authViewModel.getSavedCheckboxFemale().observe(getViewLifecycleOwner(),BoolCheckFemale -> {
            checkFemale.setChecked(BoolCheckFemale);
        });
        authViewModel.getSavedCheckboxOther().observe(getViewLifecycleOwner(),BoolCheckOther -> {
            checkOther.setChecked(BoolCheckOther);
        });


        btnBack.setOnClickListener(v -> {
            replaceFragment(new NormalRegisterFragment1());
        });
        etRegBirthdate.setOnClickListener(v -> {
            showDatePicker();
        });
        checkMale.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Male checkbox is checked
                // Set other checkboxes to false
                checkFemale.setChecked(false);
                checkOther.setChecked(false);
            }
        });
        checkFemale.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Female checkbox is checked
                // Set other checkboxes to false
                checkMale.setChecked(false);
                checkOther.setChecked(false);
            }
        });
        checkOther.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Other checkbox is checked
                // Set other checkboxes to false
                checkMale.setChecked(false);
                checkFemale.setChecked(false);
            }
        });
        btnForward.setOnClickListener(v -> {
            String firstName = etRegFirstName.getText().toString();
            String surname = etRegSurname.getText().toString();
            String phone = etRegPhone.getText().toString();
            String city = etRegCity.getText().toString();
            String birthDate = etRegBirthdate.getText().toString();
            Boolean checkboxMale = checkMale.isChecked();
            Boolean checkboxFemale = checkFemale.isChecked();
            Boolean checkboxOther = checkOther.isChecked();
            authViewModel.saveResumeFirstPageCredentials(firstName,surname,phone,city,birthDate,checkboxMale,checkboxFemale,checkboxOther);
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
    private void showDatePicker(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog and set the initial date to the current date
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // Handle the selected date
                        handleSelectedDate(year, month, day);
                    }
                }, year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();

    }
    private void handleSelectedDate(int year, int month, int day) {
        // Create a Calendar instance and set the selected date
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        // Get the selected date as a Date object
        Date selectedDate = calendar.getTime();

        // Save the selected date in your AuthViewModel
        authViewModel.setBirthdate(selectedDate);

        // Display the selected date in the TextInputEditText
        String formattedDate = formatDate(selectedDate);
        etRegBirthdate.setText(formattedDate);
    }
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(date);
    }
}