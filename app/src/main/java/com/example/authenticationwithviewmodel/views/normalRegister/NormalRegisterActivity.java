package com.example.authenticationwithviewmodel.views.normalRegister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.views.companyRegister.CompanyRegisterFragment1;
import com.example.authenticationwithviewmodel.views.normalRegister.resume.NormalRegisterFragment2_1;
import com.example.authenticationwithviewmodel.views.normalRegister.resume.NormalRegisterFragment2_2;

public class NormalRegisterActivity extends AppCompatActivity {
    private ImageView circle1,circle2,circle3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_register);
        replaceFragment(new NormalRegisterFragment1());

        circle1 = findViewById(R.id.firstPage);
        circle2 = findViewById(R.id.secondPage);
        circle3 = findViewById(R.id.thirdPage);

        circle1.setOnClickListener(v -> {
            replaceFragment(new NormalRegisterFragment2_1());
        });
        circle2.setOnClickListener(v -> {
            replaceFragment(new NormalRegisterFragment2_2());
        });
        circle3.setOnClickListener(v -> {
            replaceFragment(new NormalRegisterFragment3());
        });
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.normalRegister_frame_layout, fragment)
                .commit();
    }
}