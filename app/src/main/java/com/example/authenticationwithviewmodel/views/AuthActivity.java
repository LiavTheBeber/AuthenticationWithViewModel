package com.example.authenticationwithviewmodel.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.views.companyRegister.CompanyRegisterActivity;
import com.example.authenticationwithviewmodel.views.normalRegister.NormalRegisterActivity;

public class AuthActivity extends AppCompatActivity {
    private Button btnSignUpNormal;
    private Button btnSignUpCompany;
    private Button btnLogIn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUpNormal = findViewById(R.id.btnSignUpNormal);
        btnSignUpCompany = findViewById(R.id.btnSignUpCompany);

        btnLogIn.setOnClickListener(v -> {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        btnSignUpNormal.setOnClickListener(v -> {
            intent = new Intent(this, NormalRegisterActivity.class);
            startActivity(intent);
        });

        btnSignUpCompany.setOnClickListener(v -> {
            intent = new Intent(this, CompanyRegisterActivity.class);
            startActivity(intent);
        });





    }
}