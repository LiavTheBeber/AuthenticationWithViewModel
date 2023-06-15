package com.example.authenticationwithviewmodel.viewModel;

import android.net.Uri;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.authenticationwithviewmodel.sideClasses.CompanyUser;
import com.example.authenticationwithviewmodel.sideClasses.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainViewModel extends ViewModel {

    private boolean isUserDataFetched = false;
    private int age;
    private Calendar currentDate = Calendar.getInstance();
    private Calendar birthdateCalendar = Calendar.getInstance();
    private MutableLiveData<String> userType;

    private AuthRepository authRepository;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;



    // Normal User's Mutable Live Data
    private MutableLiveData<String> stringEmailNormal, stringFirstnameNormal, stringSurnameNormal, stringPhoneNormal, stringLifeResumeNormal, stringGenderNormal, stringCityNormal,stringUsernameNormal;
    private MutableLiveData<Date> normalBirthdate;
    private MutableLiveData<Integer> normalAge;
    private MutableLiveData<Uri> normalProfilePic;
    private MutableLiveData<List<Uri>> normalExtraImages;


    // Company User Mutable Live Data
    private MutableLiveData<String> stringEmailCompany,stringNameCompany,stringPhoneCompany,stringCompanyBio;
    private MutableLiveData<Float> floatRatingBarCompany;
    private MutableLiveData<LatLng> companyLocation;
    private MutableLiveData<Uri> profilePicCompany;
    private MutableLiveData<List<Uri>> extraImagesCompany;


    // Normal Item For Home Fragment
    private MutableLiveData<List<CompanyUser>> companyItemsLiveData;
    private MutableLiveData<List<User>> normalItemsLiveData;



    private FragmentCallback fragmentCallback;
    public MainViewModel() {
        authRepository = new AuthRepository();
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userType = new MutableLiveData<>();
        companyItemsLiveData = new MutableLiveData<>();
        normalItemsLiveData = new MutableLiveData<>();

        // Normal User's String Values
        stringEmailNormal = new MutableLiveData<>();
        stringFirstnameNormal = new MutableLiveData<>();
        stringSurnameNormal = new MutableLiveData<>();
        stringPhoneNormal = new MutableLiveData<>();
        stringLifeResumeNormal = new MutableLiveData<>();
        stringGenderNormal = new MutableLiveData<>();
        stringCityNormal = new MutableLiveData<>();
        stringUsernameNormal = new MutableLiveData<>();
        // Normal User's Date Values
        normalBirthdate = new MutableLiveData<>();
        // Normal User's Integer Values
        normalAge = new MutableLiveData<>();
        // Normal User's Uri Values
        normalProfilePic = new MutableLiveData<>();
        normalExtraImages = new MutableLiveData<>();


        // Company User's String Values
        stringEmailCompany = new MutableLiveData<>();
        stringNameCompany = new MutableLiveData<>();
        stringPhoneCompany = new MutableLiveData<>();
        stringCompanyBio = new MutableLiveData<>();
        // Company User's Float Values
        floatRatingBarCompany = new MutableLiveData<>();
        // Company User's Latlng Values
        companyLocation = new MutableLiveData<>();
        // Company User's Uri Values
        profilePicCompany = new MutableLiveData<>();
        // Company User's List<Uri> Values
        extraImagesCompany = new MutableLiveData<>();

    }

    public interface FragmentCallback {
        void replaceFragment(Fragment fragment);
        void showToast(String message);
    }

    public void setFragmentCallback(FragmentCallback callback) {
        this.fragmentCallback = callback;
    }

    private void performShowToast(String message) {
        if (fragmentCallback != null) {
            fragmentCallback.showToast(message);
        }
    }
    private void performReplaceFragment(Fragment fragment) {
        if (fragmentCallback != null) {
            fragmentCallback.replaceFragment(fragment);
        }
    }


    public boolean isUserDataFetched() {
        return isUserDataFetched;
    }
    public void setUserDataFetched(boolean value) {
        isUserDataFetched = value;
    }




    // Update Normal User Values
    public void updateNormalUserFirstName(String value,String fieldName){
        stringFirstnameNormal.setValue(value);
        performShowToast("Values Updated Successfully");
        authRepository.updateFieldNormalUser(fieldName,value);
    }
    public void updateNormalUserSurname(String value,String fieldName){
        stringSurnameNormal.setValue(value);
        performShowToast("Values Updated Successfully");
        authRepository.updateFieldNormalUser(fieldName,value);
    }
    public void updateNormalUserPhone(String value,String fieldName){
        stringPhoneNormal.setValue(value);
        performShowToast("Values Updated Successfully");
        authRepository.updateFieldNormalUser(fieldName,value);
    }
    public void updateNormalUserCity(String value,String fieldName){
        stringCityNormal.setValue(value);
        performShowToast("Values Updated Successfully");
        authRepository.updateFieldNormalUser(fieldName,value);
    }
    public void updateNormalUserLifeResume(String value,String fieldName){
        stringLifeResumeNormal.setValue(value);
        performShowToast("Values Updated Successfully");
        authRepository.updateFieldNormalUser(fieldName,value);
    }

    // Update Company's User Values
    public void updateCompanyUserName(String value,String fieldName){
        stringNameCompany.setValue(value);
        performShowToast("Values Updated Successfully");
        authRepository.updateFieldCompanyUser(fieldName,value);
    }
    public void updateCompanyUserBio(String value,String fieldName){
        stringCompanyBio.setValue(value);
        performShowToast("Values Updated Successfully");
        authRepository.updateFieldCompanyUser(fieldName,value);
    }
    public void updateCompanyPhone(String value,String fieldName){
        stringPhoneCompany.setValue(value);
        performShowToast("Values Updated Successfully");
        authRepository.updateFieldCompanyUser(fieldName,value);
    }




    public LiveData<String> getSavedUserType(){return userType;}
    public void  setUserType(String value){userType.setValue(value);}

    public LiveData<List<CompanyUser>> getSavedNormalItems(){return companyItemsLiveData;}
    public LiveData<List<User>> getSavedCompanyItems(){return normalItemsLiveData;}




    // Normal User getting LiveData Values
    public LiveData<String> getSavedEmailNormal() {
        return stringEmailNormal;
    }
    public LiveData<String> getSavedUsernameNormal() {
        return stringUsernameNormal;
    }
    public LiveData<String> getSavedFirstNameNormal() {
        return stringFirstnameNormal;
    }

    public LiveData<String> getSavedSurnameNormal() {
        return stringSurnameNormal;
    }

    public LiveData<String> getSavedPhoneNormal() {
        return stringPhoneNormal;
    }

    public LiveData<String> getSavedLifeResumeNormal() {
        return stringLifeResumeNormal;
    }

    public LiveData<String> getSavedGenderNormal() {
        return stringGenderNormal;
    }

    public LiveData<String> getSavedCityNormal() {
        return stringCityNormal;
    }
    public LiveData<Date> getSavedBirthdateNormal() {
        return normalBirthdate;
    }
    public LiveData<Integer> getSavedAgeNormal(){return normalAge;}
    public LiveData<Uri> getSavedProfilePicNormal() {
        return normalProfilePic;
    }
    public LiveData<List<Uri>> getSavedExtraImagesNormal() {
        return normalExtraImages;
    }


    // Company User getting LiveData Values
    public LiveData<String> getSavedNameCompany() {
        return stringNameCompany;
    }
    public LiveData<String> getSavedPhoneCompany() {
        return stringPhoneCompany;
    }
    public LiveData<String> getSavedEmailCompany() {
        return stringEmailCompany;
    }
    public LiveData<String> getSavedBioCompany() {
        return stringCompanyBio;
    }
    public LiveData<Float> getSavedRatingBarValue(){return floatRatingBarCompany;}
    public LiveData<Uri> getSavedProfilePicCompany(){return profilePicCompany;}
    public LiveData<List<Uri>> getSavedExtraImagesCompany(){return extraImagesCompany;}
    public LiveData<LatLng> getSavedLocationCompany(){return companyLocation;}



    public void logOutUser() {
        setUserDataFetched(false);
        authRepository.logoutUser();
    }

    // Function To Check For Existing User
    public boolean checkCurrentUser() {
        return authRepository.checkCurrentUser();
    }
    public LiveData<User> getNormalUserData() {
        MutableLiveData<User> userData = new MutableLiveData<>();

        authRepository.getNormalUserData(new AuthRepository.OnUserDataCallback() {
            @Override
            public void onSuccess(User user) {
                // Update the LiveData with the retrieved user data
                userData.setValue(user);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error, such as showing an error message
            }
        });

        return userData;
    }
    public void fetchNormalUserData(){
        if (!isUserDataFetched){
            getNormalUserData().observeForever(user -> {
                if (user != null){
                    stringEmailNormal.setValue(user.getEmail());
                    stringFirstnameNormal.setValue(user.getFirstName());
                    stringSurnameNormal.setValue(user.getSurname());
                    stringPhoneNormal.setValue(user.getPhone());
                    stringLifeResumeNormal.setValue(user.getLifeResume());
                    stringGenderNormal.setValue(user.getGender());
                    stringCityNormal.setValue(user.getCity());

                    normalBirthdate.setValue(user.getBirthdate());

                    normalProfilePic.setValue(user.getProfilePic());
                    normalExtraImages.setValue(user.getExtraImages());

                    birthdateCalendar.setTimeInMillis(normalBirthdate.getValue().getTime());

                    age = currentDate.get(Calendar.YEAR) - birthdateCalendar.get(Calendar.YEAR);

                    if (currentDate.get(Calendar.MONTH) < birthdateCalendar.get(Calendar.MONTH) ||
                            (currentDate.get(Calendar.MONTH) == birthdateCalendar.get(Calendar.MONTH) &&
                                    currentDate.get(Calendar.DAY_OF_MONTH) < birthdateCalendar.get(Calendar.DAY_OF_MONTH))) {
                        age--;
                    }

                    normalAge.setValue(age);

                    getCompanyItems().observeForever(companyUsers -> {
                        if (companyUsers != null){
                            Log.d("companyUsers3", "fetchNormalUserData: " + companyUsers);
                            companyItemsLiveData.setValue(companyUsers);
                        }
                    });

                }
            });
            isUserDataFetched = true;
        }
    }
    public LiveData<CompanyUser> getCompanyUserData(){
        MutableLiveData<CompanyUser> userData = new MutableLiveData<>();
        authRepository.getCompanyUserData(new AuthRepository.OnCompanyUserDataCallback() {
            @Override
            public void onSuccess(CompanyUser companyUserData) {
                userData.setValue(companyUserData);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error, such as showing an error message
            }
        });

        return userData;
    }
    public void fetchCompanyUserData(){
        if (!isUserDataFetched){
            getCompanyUserData().observeForever(companyUser -> {
                if (companyUser != null){

                    stringNameCompany.setValue(companyUser.getUsername());
                    stringEmailCompany.setValue(companyUser.getEmail());
                    stringPhoneCompany.setValue(companyUser.getPhone());
                    stringCompanyBio.setValue(companyUser.getBio());

                    companyLocation.setValue(companyUser.getLocation());

                    floatRatingBarCompany.setValue(companyUser.getRatingBar());

                    profilePicCompany.setValue(companyUser.getProfilePic());
                    extraImagesCompany.setValue(companyUser.getExtraImages());

                    getNormalItems().observeForever(normalUsers -> {
                        if (normalUsers != null){
                            Log.d("companyUsers3", "fetchNormalUserData: " + normalUsers);
                            normalItemsLiveData.setValue(normalUsers);
                        }
                    });
                }
            });
            isUserDataFetched = true;
        }
    }



    public LiveData<List<CompanyUser>> getCompanyItems(){
        MutableLiveData<List<CompanyUser>> companyItems = new MutableLiveData<>();
        authRepository.getCompanyItems(new AuthRepository.OnNormalItemsDataCallback() {
            @Override
            public void onSuccess(List<CompanyUser> companyUsers) {
                companyItems.setValue(companyUsers);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error, such as showing an error message
            }
        });

        return companyItems;
    }
    public LiveData<List<User>> getNormalItems(){
        MutableLiveData<List<User>> userItems = new MutableLiveData<>();
        authRepository.getNormalItems(new AuthRepository.OnCompanyItemsDataCallback() {
            @Override
            public void onSuccess(List<User> normalUsers) {
                userItems.setValue(normalUsers);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error, such as showing an error message
            }
        });

        return userItems;
    }

}
