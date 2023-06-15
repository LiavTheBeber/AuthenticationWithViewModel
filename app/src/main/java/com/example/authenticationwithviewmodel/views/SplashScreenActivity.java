package com.example.authenticationwithviewmodel.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.viewModel.AuthRepository;
import com.example.authenticationwithviewmodel.viewModel.AuthViewModel;
import com.example.authenticationwithviewmodel.viewModel.MainViewModel;
import com.example.authenticationwithviewmodel.views.main.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private AuthViewModel authViewModel;
    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                authViewModel.checkCurrentUserType(new AuthRepository.CurrentUserTypeCallback() {
                    @Override
                    public void onCurrentUserType(String userType) {
                        if (userType != null) {
                            // There is a current user
                            if (userType.equals("normalUser")) {
                                Toast.makeText(SplashScreenActivity.this, "Normal User Is Connected", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                                intent.putExtra("userType","normalUser");
                                startActivity(intent);
                                finish();
                            } else if (userType.equals("companyUser")) {
                                Toast.makeText(SplashScreenActivity.this, "Company User Is Connected", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                                intent.putExtra("userType","companyUser");
                                startActivity(intent);
                                finish();
                                // Handle the company user case
                            } else {
                                Toast.makeText(SplashScreenActivity.this, "Unknown User Type", Toast.LENGTH_SHORT).show();

                                // Handle unknown user type
                            }
                        } else {
                            // There isn't a current user
                            startActivity(new Intent(SplashScreenActivity.this, AuthActivity.class));
                            finish();
                        }
                    }
                });
            }
        }, 5000);
    }
}