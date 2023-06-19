package com.example.authenticationwithviewmodel.views.companyRegister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.authenticationwithviewmodel.R;

public class CompanyRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);

        replaceFragment(new CompanyRegisterFragment4());
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.companyRegister_frame_layout, fragment)
                .commit();
    }

}