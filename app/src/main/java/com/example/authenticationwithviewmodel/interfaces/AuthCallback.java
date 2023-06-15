package com.example.authenticationwithviewmodel.interfaces;

import com.google.firebase.auth.FirebaseUser;

public interface AuthCallback {
    void onSuccess(FirebaseUser user);
    void onError(Exception e);
}
