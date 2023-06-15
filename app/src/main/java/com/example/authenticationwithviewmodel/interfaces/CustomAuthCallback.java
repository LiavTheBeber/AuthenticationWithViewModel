package com.example.authenticationwithviewmodel.interfaces;

import com.google.firebase.auth.FirebaseUser;

public interface CustomAuthCallback extends AuthCallback {
    void onSuccessWithUserType(FirebaseUser user, String userType);
    void onErrorWithUserType(Exception e);
    void onSuccess(FirebaseUser user);
    void onError(Exception e);
}
