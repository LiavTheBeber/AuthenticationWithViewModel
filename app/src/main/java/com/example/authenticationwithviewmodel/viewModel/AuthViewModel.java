package com.example.authenticationwithviewmodel.viewModel;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.authenticationwithviewmodel.interfaces.AuthCallback;
import com.example.authenticationwithviewmodel.interfaces.CustomAuthCallback;
import com.example.authenticationwithviewmodel.views.main.MainActivity;
import com.example.authenticationwithviewmodel.views.companyRegister.CompanyRegisterFragment2;
import com.example.authenticationwithviewmodel.views.companyRegister.CompanyRegisterFragment3;
import com.example.authenticationwithviewmodel.views.companyRegister.CompanyRegisterFragment4;
import com.example.authenticationwithviewmodel.views.companyRegister.CompanyRegisterFragment5;
import com.example.authenticationwithviewmodel.views.normalRegister.NormalRegisterFragment3;
import com.example.authenticationwithviewmodel.views.normalRegister.resume.NormalRegisterFragment2_1;
import com.example.authenticationwithviewmodel.views.normalRegister.resume.NormalRegisterFragment2_2;
import com.example.authenticationwithviewmodel.views.normalRegister.resume.NormalRegisterFragment2_3;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AuthViewModel extends ViewModel {
    private AuthRepository authRepository;
    private MutableLiveData<FirebaseUser> currentUserLiveData;
    private MutableLiveData<String> errorMessageLiveData;

    // Normal User's MutableLiveData
    private MutableLiveData<String> stringEmailNormal,stringPassNormal,stringConfirmPassNormal,stringFirstNameNormal,stringSurnameNormal,
            stringPhoneNormal,stringCityNormal,stringBirthdateNormal,stringLifeResumeNormal,stringTAGNormal,stringuserTypeLiveData;
    private MutableLiveData<Date> birthdateLiveData;
    private MutableLiveData<Boolean> checkboxMale,checkboxFemale,checkboxOther;
    private MutableLiveData<Uri> normalUserProfilePic;
    private MutableLiveData<List<Uri>> normalUserExtraImages;





    // Company User's MutableLiveData
    private MutableLiveData<String> stringNameCompany,stringEmailCompany,stringPassCompany,stringConfirmPassCompany,stringPhoneCompany,stringBioCompany,stringTAGCompany;
    private MutableLiveData<Uri> companyProfileImageUri;
    private MutableLiveData<List<Uri>> companyExtraImages;
    private MutableLiveData<com.google.android.gms.maps.model.LatLng> companyLocation;


    private FragmentCallback fragmentCallback;

    public AuthViewModel(){
        authRepository = new AuthRepository();
        currentUserLiveData = new MutableLiveData<>();
        errorMessageLiveData = new MutableLiveData<>();

        //Registration Information - Normal User's
        // string formats
        stringEmailNormal = new MutableLiveData<>();
        stringPassNormal = new MutableLiveData<>();
        stringConfirmPassNormal = new MutableLiveData<>();
        stringFirstNameNormal= new MutableLiveData<>();
        stringSurnameNormal = new MutableLiveData<>();
        stringPhoneNormal = new MutableLiveData<>();
        stringCityNormal = new MutableLiveData<>();
        stringBirthdateNormal = new MutableLiveData<>();
        stringLifeResumeNormal = new MutableLiveData<>();
        stringTAGNormal = new MutableLiveData<>();
        stringuserTypeLiveData = new MutableLiveData<>();

        // date formats
        birthdateLiveData = new MutableLiveData<>();

        // boolean formats
        checkboxMale = new MutableLiveData<>();
        checkboxFemale = new MutableLiveData<>();
        checkboxOther = new MutableLiveData<>();

        // Uri formats
        normalUserProfilePic = new MutableLiveData<>();
        normalUserExtraImages = new MutableLiveData<>();

        //Registration Information - Company User's
        // string formats
        stringNameCompany = new MutableLiveData<>();
        stringEmailCompany = new MutableLiveData<>();
        stringPassCompany = new MutableLiveData<>();
        stringConfirmPassCompany = new MutableLiveData<>();
        stringPhoneCompany = new MutableLiveData<>();
        stringBioCompany = new MutableLiveData<>();
        stringTAGCompany = new MutableLiveData<>();

        // Uri formats
        companyProfileImageUri = new MutableLiveData<>();
        companyExtraImages = new MutableLiveData<>();

        // Latlng formats
        companyLocation = new MutableLiveData<>();
    }



    // Method to call the showToast function in the fragment
    private void performShowToast(String message) {
        if (fragmentCallback != null) {
            fragmentCallback.showToast(message);
        }
    }

    // Setter for the fragment callback
    public void setFragmentCallback(FragmentCallback callback) {
        this.fragmentCallback = callback;
    }

    // Method to call the replaceFragment function
    private void performReplaceFragment(Fragment fragment) {
        if (fragmentCallback != null) {
            fragmentCallback.replaceFragment(fragment);
        }
    }
    // Fragment callback interface
    public interface FragmentCallback {
        void replaceFragment(Fragment fragment);
        void showToast(String message);
    }





    public void setBirthdate(Date birthdate) {
        birthdateLiveData.setValue(birthdate);
    }
    public void setCompanyTAG(String TAG){stringTAGCompany.setValue(TAG);}
    public void setNormalTAG(String TAG){stringTAGNormal.setValue(TAG);}





    // Public method to observe userLiveData
    public LiveData<FirebaseUser> getCurentUserLiveData(){
        return currentUserLiveData;
    }
    public LiveData<String> getErrorMessageLiveData(){
        return errorMessageLiveData;
    }


    // Public method to observe the saved values of normal users
    public LiveData<String> getSavedEmail() {
        return stringEmailNormal;
    }
    public LiveData<String> getSavedPass() {
        return stringPassNormal;
    }
    public LiveData<String> getSavedConfirmPass() {
        return stringConfirmPassNormal;
    }
    public LiveData<String> getSavedFirstName() {
        return stringFirstNameNormal;
    }
    public LiveData<String> getSavedSurname() {
        return stringSurnameNormal;
    }
    public LiveData<String> getSavedPhone() {
        return stringPhoneNormal;
    }
    public LiveData<String> getSavedCity() {
        return stringCityNormal;
    }
    public LiveData<String> getSavedBirthdate() {
        return stringBirthdateNormal;
    }
    public LiveData<Boolean> getSavedCheckboxMale(){return checkboxMale;}
    public LiveData<Boolean> getSavedCheckboxFemale(){return checkboxFemale;}
    public LiveData<Boolean> getSavedCheckboxOther(){return checkboxOther;}
    public LiveData<Date> getSavedBirthdateLiveData() {
        return birthdateLiveData;
    }
    public LiveData<String> getSavedLifeResume(){return stringLifeResumeNormal;}
    public LiveData<Uri> getSavedNormalUserProfileImageUri(){return normalUserProfilePic;}
    public LiveData<List<Uri>> getSavedNormalUserExtraImages(){return normalUserExtraImages;}


    // Public method to observe the saved values of company users
    public LiveData<String> getSavedCompanyName() {
        return stringNameCompany;
    }
    public LiveData<String> getSavedCompanyEmail() {
        return stringEmailCompany;
    }
    public LiveData<String> getSavedCompanyPass() {
        return stringPassCompany;
    }
    public LiveData<String> getSavedCompanyConfirmPass() {
        return stringConfirmPassCompany;
    }
    public LiveData<String> getSavedCompanyPhone() {
        return stringPhoneCompany;
    }
    public LiveData<String> getSavedCompanyBio() {
        return stringBioCompany;
    }
    public LiveData<Uri> getSavedCompanyProfileImageUri(){return companyProfileImageUri;}
    public LiveData<List<Uri>> getSavedCompanyExtraImages(){return companyExtraImages;}
    public LiveData<com.google.android.gms.maps.model.LatLng> getSavedCompanyLocation(){return companyLocation;}
    public LiveData<String> getUserTypeLiveData() {
        return stringuserTypeLiveData;
    }

    public void loginUser(String email, String password) {
        authRepository.loginUser(email, password, new CustomAuthCallback() {
            @Override
            public void onSuccessWithUserType(FirebaseUser user, String userType) {
                currentUserLiveData.postValue(user);
                Log.d("", "onSuccessWithUserType: "+userType);
                stringuserTypeLiveData.postValue(userType);

            }

            @Override
            public void onErrorWithUserType(Exception e) {
                errorMessageLiveData.postValue(e.getMessage());
            }

            @Override
            public void onSuccess(FirebaseUser user) {

            }

            @Override
            public void onError(Exception e) {

            }
        });

    }


    // Saving User Credentials
    public void saveFirstPageCredentials(String email,String pass,String confirmPass){

        boolean isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean isPasswordValid = pass.length() >= 6;
        boolean isConfirmPassValid = confirmPass.equals(pass);
        if (isConfirmPassValid && isPasswordValid && isEmailValid) {
            authRepository.checkEmailExists(email, new AuthCallback() {
                @Override
                public void onSuccess(FirebaseUser user) {
                    // Email is available, proceed with saving credentials and fragment navigation
                    stringEmailNormal.setValue(email);
                    stringPassNormal.setValue(pass);
                    stringConfirmPassNormal.setValue(confirmPass);
                    performReplaceFragment(new NormalRegisterFragment2_1());
                }

                @Override
                public void onError(Exception e) {
                    // Email already exists, show error message
                    performShowToast("Email already exists");
                }
            });
        }
        else {
            performShowToast("Invalid Credentials Inserted");
        }
    }
    public void saveResumeFirstPageCredentials(String firstName,String surname,String phone,String city,String birthDate,Boolean BoolCheckboxMale,Boolean BoolCheckboxFemale,Boolean BoolCheckboxOther){

        boolean isFirstNameValid = !firstName.equals("");
        boolean isSurnameValid = !surname.equals("");
        boolean isCityValid = !city.equals("");
        boolean isPhoneValid = phone.length() == 10;
        boolean isBirthDateValid = !(birthDate.equals(""));
        boolean isGenderValid = !(BoolCheckboxMale == false && BoolCheckboxFemale == false && BoolCheckboxOther == false);

        if (isPhoneValid && isBirthDateValid && isGenderValid && isFirstNameValid && isSurnameValid && isCityValid){
            stringFirstNameNormal.setValue(firstName);
            stringSurnameNormal.setValue(surname);
            stringPhoneNormal.setValue(phone);
            stringCityNormal.setValue(city);
            stringBirthdateNormal.setValue(birthDate);
            checkboxMale.setValue(BoolCheckboxMale);
            checkboxFemale.setValue(BoolCheckboxFemale);
            checkboxOther.setValue(BoolCheckboxOther);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date dateOfBirth = null;
            try {
                dateOfBirth = dateFormat.parse(birthDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            birthdateLiveData.setValue(dateOfBirth);
            performReplaceFragment(new NormalRegisterFragment2_2());
        }
        else {
            performShowToast("Invalid Credentials Inserted");
        }
    }
    public void saveResumeSecondPageCredentials(String lifeResume){
        boolean isLifeResumeValid = !lifeResume.equals("");
        if (isLifeResumeValid){
            stringLifeResumeNormal.setValue(lifeResume);
            performReplaceFragment(new NormalRegisterFragment2_3());
        }
    }
    public void saveResumeThirdPageCredentials(List<Uri> imagesUri,Uri profilePicUri){
        if (profilePicUri != null){
            normalUserExtraImages.setValue(imagesUri);
            normalUserProfilePic.setValue(profilePicUri);
            performReplaceFragment(new NormalRegisterFragment3());
        }
        else
            performShowToast("No Profile Picture Found");
    }
    public void saveThirdPageNormalUserCredentials(Boolean isTermsAccepted,String TAG){
        if (isTermsAccepted)
            setNormalTAG(TAG);
    }
    public void registerUser(Context context) {
        authRepository.registerUser(stringEmailNormal.getValue(), stringPassNormal.getValue(),stringFirstNameNormal.getValue(),stringSurnameNormal.getValue()
                ,stringPhoneNormal.getValue(),stringCityNormal.getValue(),
                birthdateLiveData.getValue(),null,checkboxMale.getValue(),checkboxFemale.getValue(),checkboxOther.getValue()
                ,stringLifeResumeNormal.getValue(),
                normalUserProfilePic.getValue(),normalUserExtraImages.getValue(),stringTAGNormal.getValue(), new AuthCallback() {
                    @Override
                    public void onSuccess(FirebaseUser user) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("userType", "normalUser");
                        context.startActivity(intent);
                        currentUserLiveData.postValue(user);

                    }

                    @Override
                    public void onError(Exception e) {
                        errorMessageLiveData.postValue(e.getMessage());
                    }
                });
    }







    // Saving Company Credentials
    public void saveFirstCompanyPageCredentials(String companyName,String email,String pass,String confirmPass){
        boolean isCompanyNameValid = !companyName.equals("");
        boolean isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean isPasswordValid = pass.length() >= 6;
        boolean isConfirmPassValid = confirmPass.equals(pass);
        if (isConfirmPassValid && isPasswordValid && isEmailValid && isCompanyNameValid) {
            authRepository.checkEmailExists(email, new AuthCallback() {
                @Override
                public void onSuccess(FirebaseUser user) {
                    // Email is available, proceed with saving credentials and fragment navigation
                    stringNameCompany.setValue(companyName);
                    stringEmailCompany.setValue(email);
                    stringPassCompany.setValue(pass);
                    stringConfirmPassCompany.setValue(confirmPass);
                    performReplaceFragment(new CompanyRegisterFragment2());
                }

                @Override
                public void onError(Exception e) {
                    // Email already exists, show error message
                    performShowToast("Email already exists");
                }
            });
        }
        else {
            performShowToast("Invalid Credentials Inserted");
        }
    }
    public void saveSecondCompanyPageCredentials(String phone,String bio){
        boolean isPhoneValid = phone.length() == 10;
        boolean isBioValid = (!bio.equals(""));
        if (isPhoneValid && isBioValid){
            stringPhoneCompany.setValue(phone);
            stringBioCompany.setValue(bio);
            performReplaceFragment(new CompanyRegisterFragment3());
        }
        else
            performShowToast("Invalid Credentials");
    }
    public void saveThirdPageCompanyCredentials(List<Uri> imagesUri,Uri profilePicUri){
        if (profilePicUri != null){
            companyExtraImages.setValue(imagesUri);
            companyProfileImageUri.setValue(profilePicUri);
            performReplaceFragment(new CompanyRegisterFragment4());
        }
        else
            performShowToast("No Profile Picture Found");
    }
    public void saveFourthPageCompanyCredentials(LatLng location){
        boolean isLocationValid = location != null;
        if (isLocationValid){
            companyLocation.setValue(location);
            performShowToast("Location Successfully Saved");
            performReplaceFragment(new CompanyRegisterFragment5());
        }

    }
    public void saveFifthPageCompanyCredentials(Boolean isTermsAccepted,String TAG){
        if (isTermsAccepted){
            setCompanyTAG(TAG);
        }
    }
    public void registerCompanyUser(Context context) {
        authRepository.registerCompanyUser(stringNameCompany.getValue(), stringEmailCompany.getValue(), stringPassCompany.getValue(),
                stringPhoneCompany.getValue(), stringBioCompany.getValue(), companyProfileImageUri.getValue(),
                companyExtraImages.getValue(), companyLocation.getValue(), 0, stringTAGCompany.getValue(), new AuthCallback() {
                    @Override
                    public void onSuccess(FirebaseUser user) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("userType", "companyUser"); // Add the user type as an extra
                        context.startActivity(intent);
                        currentUserLiveData.postValue(user);
                        Toast.makeText(context, "Company user registered successfully!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Exception e) {
                        errorMessageLiveData.postValue(e.getMessage());
                    }
                });
    }

    // Function To Check For Existing User
    public boolean checkCurrentUser() {
        return authRepository.checkCurrentUser();
    }
    public void checkCurrentUserType(AuthRepository.CurrentUserTypeCallback callback) {
        authRepository.checkCurrentUserType(callback);
    }



}
