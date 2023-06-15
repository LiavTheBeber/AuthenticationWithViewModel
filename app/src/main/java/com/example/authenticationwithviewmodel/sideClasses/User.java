package com.example.authenticationwithviewmodel.sideClasses;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User {

    private String email,password,FirstName,Surname,phone,city,lifeResume,TAG,gender;
    private Boolean isMale,isFemale,isOther;
    private Uri profilePic;
    private List<Uri> extraImages;
    private Date birthdate;
    public User(String email,String password,String FirstName,String Surname,String phone,String city,Date birthdate,String gender,
                Boolean isMale,Boolean isFemale,Boolean isOther,String lifeResume,Uri profilePic,List<Uri> extraImages,String TAG) {
        this.email = email;
        this.password = password;
        this.FirstName = FirstName;
        this.Surname = Surname;
        this.phone = phone;
        this.city = city;
        this.birthdate = birthdate;
        this.gender = gender;
        this.isMale = isMale;
        this.isFemale = isFemale;
        this.isOther = isOther;
        this.lifeResume = lifeResume;
        this.profilePic = profilePic;
        this.extraImages = extraImages;
        this.TAG = TAG;
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
    public String getFirstName() {
        return FirstName;
    }
    public void setFirstName(String firstName) {
        FirstName = firstName;
    }
    public String getSurname() {
        return Surname;
    }
    public void setSurname(String surname) {
        Surname = surname;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public Date getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    public Boolean getMale() {
        return isMale;
    }
    public void setMale(Boolean male) {
        isMale = male;
    }
    public Boolean getFemale() {
        return isFemale;
    }
    public void setFemale(Boolean female) {
        isFemale = female;
    }
    public Boolean getOther() {
        return isOther;
    }
    public void setOther(Boolean other) {
        isOther = other;
    }
    public String getLifeResume() {
        return lifeResume;
    }
    public void setLifeResume(String lifeResume) {
        this.lifeResume = lifeResume;
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
    public String getTAG() {
        return TAG;
    }
    public void setTAG(String TAG) {
        this.TAG = TAG;
    }
}

