package com.example.authenticationwithviewmodel.sideClasses;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;
import java.util.List;

public class CompanyUser {

    private String username;
    private String email;
    private String password;
    private String phone;
    private String bio;
    private Uri profilePic;
    private List<Uri> extraImages;
    private LatLng location;
    private float ratingBar;
    private String TAG;

    public CompanyUser(String username, String email, String password, String phone,String bio,Uri profilePic,List<Uri> extraImages,LatLng location,float ratingBar,String TAG) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.profilePic = profilePic;
        this.extraImages = extraImages;
        this.location = location;
        this.ratingBar = ratingBar;
        this.bio = bio;
        this.TAG = TAG;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Uri getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Uri profilePic) {
        this.profilePic = profilePic;
    }

    public List<Uri> getExtraImages() {
        return extraImages;
    }

    public void setExtraImages(List<Uri> extraImages) {
        this.extraImages = extraImages;
    }


    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public float getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(float ratingBar) {
        this.ratingBar = ratingBar;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }
}
