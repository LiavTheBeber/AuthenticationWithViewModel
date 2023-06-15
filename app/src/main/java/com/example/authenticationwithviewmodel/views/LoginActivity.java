package com.example.authenticationwithviewmodel.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.viewModel.AuthViewModel;
import com.example.authenticationwithviewmodel.views.main.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private AppCompatButton btnSignIn;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        etEmail = findViewById(R.id.etLogInEmail);
        etPassword = findViewById(R.id.etLogInPass);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String pass = etPassword.getText().toString();
            authViewModel.loginUser(email,pass);
        });

        // Observe the currentUserLiveData and errorMessageLiveData in the AuthViewModel
        authViewModel.getUserTypeLiveData().observe(this, userTypeString -> {
            Log.d("TAG", "onCreate: " + userTypeString);
            if (userTypeString != null) {
                Intent intent;
                if (userTypeString.equals("normalUser")) {
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                } else if (userTypeString.equals("companyUser")) {
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                } else {
                    // Handle unknown user type or error case
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                }
                intent.putExtra("userType", userTypeString);
                startActivity(intent);
                finish();
            } else {
                // Display a toast message indicating an issue with retrieving the user type
                Toast.makeText(LoginActivity.this, "Failed to retrieve user type.", Toast.LENGTH_SHORT).show();
            }
        });



        authViewModel.getErrorMessageLiveData().observe(this, errorMessage -> {
            // Handle changes to the errorMessageLiveData
            // For example, display an error message to the user
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });

    }
}