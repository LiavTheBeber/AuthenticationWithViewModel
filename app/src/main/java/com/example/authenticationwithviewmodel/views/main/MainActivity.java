package com.example.authenticationwithviewmodel.views.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.settings.SettingsFragment;
import com.example.authenticationwithviewmodel.viewModel.AuthViewModel;
import com.example.authenticationwithviewmodel.viewModel.MainViewModel;
import com.example.authenticationwithviewmodel.views.main.companyUserFragments.CompanyUserHomeFragment;
import com.example.authenticationwithviewmodel.views.main.companyUserFragments.CompanyUserProfileFragment;
import com.example.authenticationwithviewmodel.views.main.userFragments.NormalUserHomeFragment;
import com.example.authenticationwithviewmodel.views.main.userFragments.NormalUserProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavView;
    private MainViewModel mainViewModel;
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavView = findViewById(R.id.bottomNavigationView);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        String userType = getIntent().getStringExtra("userType");

        if (userType != null) {
            mainViewModel.setUserType(userType);
            if (userType.equals("normalUser")) {
                TAG = "normalUser";
                Toast.makeText(this, "Normal User Is Connected", Toast.LENGTH_SHORT).show();
            } else if (userType.equals("companyUser")) {
                TAG = "companyUser";
                Toast.makeText(this, "Company User Is Connected", Toast.LENGTH_SHORT).show();
            }
        }

        if (!mainViewModel.isUserDataFetched()) {
            if (TAG.equals("normalUser")){
                mainViewModel.fetchNormalUserData();
            }
            else if (TAG.equals("companyUser")){
                mainViewModel.fetchCompanyUserData();
            }
        }


        bottomNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home){
                    if (TAG.equals("normalUser")){
                        replaceFragment(new NormalUserHomeFragment());
                        return true;
                    }
                    else if (TAG.equals("companyUser")){
                        replaceFragment(new CompanyUserHomeFragment());
                        return true;
                    }
                }
                else if (item.getItemId() == R.id.nav_profile){
                    if (TAG.equals("normalUser")){
                        replaceFragment(new NormalUserProfileFragment());
                        return true;
                    }
                    else if (TAG.equals("companyUser")){
                        replaceFragment(new CompanyUserProfileFragment());
                        return true;
                    }
                }
                else if(item.getItemId() == R.id.nav_settings){
                    replaceFragment(new SettingsFragment());
                    return true;
                }

                return false;
            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame_layout, fragment)
                .commit();
    }
}