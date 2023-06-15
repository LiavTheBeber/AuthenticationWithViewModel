package com.example.authenticationwithviewmodel.viewModel;

import android.net.Uri;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.example.authenticationwithviewmodel.interfaces.AuthCallback;
import com.example.authenticationwithviewmodel.interfaces.CustomAuthCallback;
import com.example.authenticationwithviewmodel.sideClasses.CompanyUser;
import com.example.authenticationwithviewmodel.sideClasses.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AuthRepository
{
    private String userId;
    private User fetchedNormalUser;
    private CompanyUser fetchedCompanyUser;
    private List<Uri> uriList = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore UsersDB,CompanyUsersDB;
    private CollectionReference companyCollection,normalCollection;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageRef;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private DocumentReference userRef;

    public AuthRepository(){
        firebaseAuth = FirebaseAuth.getInstance();
        UsersDB = FirebaseFirestore.getInstance();
        CompanyUsersDB = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageRef = firebaseStorage.getReference();

        companyCollection = CompanyUsersDB.collection("CompanyUsersDB");
        normalCollection = UsersDB.collection("UsersDB");
    }
    public interface CurrentUserTypeCallback {
        void onCurrentUserType(String userType);
    }
    public interface OnUserDataCallback {
        void onSuccess(User userData);

        void onError(String errorMessage);
    }

    public interface OnCompanyUserDataCallback {
        void onSuccess(CompanyUser companyUserData);

        void onError(String errorMessage);
    }

    public interface OnNormalItemsDataCallback {
        void onSuccess(List<CompanyUser> companyUsers);

        void onError(String errorMessage);
    }

    public interface OnCompanyItemsDataCallback {
        void onSuccess(List<User> normalUsers);
        void onError(String errorMessage);
    }

    public void updateFieldNormalUser(String fieldName, Object value) {
        // Create a map with the field name and its updated value
        Map<String, Object> updates = new HashMap<>();
        updates.put(fieldName, value);
        userId = firebaseAuth.getCurrentUser().getUid();
        userRef = firestore.collection("UsersDB").document(userId);
        userRef.update(updates);
    }
    public void updateFieldCompanyUser(String fieldName, Object value) {
        // Create a map with the field name and its updated value
        Map<String, Object> updates = new HashMap<>();
        updates.put(fieldName, value);
        userId = firebaseAuth.getCurrentUser().getUid();
        userRef = firestore.collection("CompanyUsersDB").document(userId);
        userRef.update(updates);
    }
    public void registerUser(String email, String password, String firstName, String surname, String phone, String city, Date birthdate,String gender
            ,Boolean isMale, Boolean isFemale, Boolean isOther, String lifeResume,Uri profilePic,List<Uri> extraImages,String TAG, final AuthCallback callback)
    {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        // Handle successful registration

                        User user1 = new User(email,password,firstName,surname,phone,city,birthdate,null,isMale,isFemale,isOther,
                                lifeResume,profilePic,extraImages,TAG);


                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("normalEmail", email);
                        userMap.put("normalFirstName", firstName);
                        userMap.put("normalSurname", surname);
                        userMap.put("normalPhone", phone);
                        userMap.put("normalCity", city);
                        userMap.put("normalBirthdate", birthdate);

                        if (isMale)
                            userMap.put("normalGender", "Male");
                        else if (isOther)
                            userMap.put("normalGender", "Other");
                        else if (isFemale)
                            userMap.put("normalGender", "Female");

                        userMap.put("normalLifeResume", lifeResume);
                        userMap.put("TAG",TAG);

                        UsersDB.collection("UsersDB").document(user.getUid()).set(userMap);

                        String profilePicFolderName = "profilePic";
                        String extraImagesFolderName = "extraImages";
                        String userId = user.getUid();

                        // Create a reference to the parent folder
                        StorageReference folderRef = storageRef.child("normalUserImages");

                        // Create a reference to the user's folder
                        StorageReference userFolderRef = folderRef.child(userId);

                        // Create a reference to the profile picture folder
                        StorageReference profilePicFolderRef = userFolderRef.child(profilePicFolderName);

                        // Create a reference to the extra images folder
                        StorageReference extraImagesFolderRef = userFolderRef.child(extraImagesFolderName);

                        // Upload the profile picture
                        UploadTask uploadProfilePic = profilePicFolderRef.putFile(profilePic);

                        // Upload the extra images
                        for (int i = 0; i < extraImages.size(); i++) {
                            Uri imageUri = extraImages.get(i);
                            String fileName = "image" + i; // Generate a unique file name for each image

                            // Create a reference to the image file
                            StorageReference imageRef = extraImagesFolderRef.child(fileName);

                            // Upload the image file
                            UploadTask uploadImage = imageRef.putFile(imageUri);
                        }

                        callback.onSuccess(user);
                    }
                    else {
                        // Handle registration failure;
                        callback.onError(task.getException());
                    }
                });
    }
    public void registerCompanyUser(String name, String email, String password, String phone, String bio, Uri profilePic, List<Uri> extraImages , LatLng location , float ratingBar , String TAG, final AuthCallback callback)
    {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        // Handle successful registration
                        CompanyUser companyUser = new CompanyUser(name,email,password,phone,bio,profilePic,extraImages,location,ratingBar,TAG);
                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("companyName", name);
                        userMap.put("companyEmail", email);
                        userMap.put("companyPhone", phone);
                        userMap.put("companyLocation", location);
                        userMap.put("companyRatingBar",ratingBar);
                        userMap.put("companyBio",bio);
                        userMap.put("TAG",TAG);
                        CompanyUsersDB.collection("CompanyUsersDB").document(user.getUid()).set(userMap);

                        String profilePicFolderName = "profilePic";
                        String extraImagesFolderName = "extraImages";
                        String userId = user.getUid();

                        // Create a reference to the parent folder
                        StorageReference folderRef = storageRef.child("companyUserImages");

                        // Create a reference to the user's folder
                        StorageReference userFolderRef = folderRef.child(userId);

                        // Create a reference to the profile picture folder
                        StorageReference profilePicFolderRef = userFolderRef.child(profilePicFolderName);

                        // Create a reference to the extra images folder
                        StorageReference extraImagesFolderRef = userFolderRef.child(extraImagesFolderName);

                        // Upload the profile picture
                        UploadTask uploadProfilePic = profilePicFolderRef.putFile(profilePic);

                        // Upload the extra images
                        for (int i = 0; i < extraImages.size(); i++) {
                            Uri imageUri = extraImages.get(i);
                            String fileName = "image" + i; // Generate a unique file name for each image

                            // Create a reference to the image file
                            StorageReference imageRef = extraImagesFolderRef.child(fileName);

                            // Upload the image file
                            UploadTask uploadImage = imageRef.putFile(imageUri);
                        }
                        callback.onSuccess(user);
                    }
                    else {
                        // Handle registration failure
                        callback.onError(task.getException());
                    }
                });
    }
    public void loginUser(String email, String password, final CustomAuthCallback callback) {
        boolean isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean isPasswordValid = password.length() >= 6;

        if (isEmailValid && isPasswordValid) {
            // Email and password are valid
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            DocumentReference userRef = db.collection("UsersDB").document(user.getUid());
                            DocumentReference companyUserRef = db.collection("CompanyUsersDB").document(user.getUid());

                            userRef.get().addOnSuccessListener(documentSnapshot -> {
                                if (documentSnapshot.exists()) {
                                    // User is a normal user
                                    String userType = documentSnapshot.getString("TAG");
                                    Log.d("", "User Type: " + userType);
                                    callback.onSuccessWithUserType(user, userType);
                                } else {
                                    companyUserRef.get().addOnSuccessListener(companyDocumentSnapshot -> {
                                        if (companyDocumentSnapshot.exists()) {
                                            // User is a company user
                                            String userType = companyDocumentSnapshot.getString("TAG");
                                            Log.d("", "User Type: " + userType);
                                            callback.onSuccessWithUserType(user, userType);
                                        } else {
                                            // User document does not exist in either collection
                                            callback.onError(new Exception("User document does not exist"));
                                        }
                                    }).addOnFailureListener(e -> {
                                        // Error retrieving company user document
                                        callback.onError(e);
                                    });
                                }
                            }).addOnFailureListener(e -> {
                                // Error retrieving normal user document
                                callback.onError(e);
                            });
                        }
                    });
        }
    }
    public void logoutUser() {
        firebaseAuth.signOut();
    }
    public void checkEmailExists(String email, AuthCallback callback) {
        firebaseAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> signInMethods = task.getResult().getSignInMethods();
                        if (signInMethods != null && !signInMethods.isEmpty()) {
                            // Email exists, invoke onError callback
                            callback.onError(new Exception("Email already exists"));
                        } else {
                            // Email is available, invoke onSuccess callback
                            callback.onSuccess(null);
                        }
                    } else {
                        // Error occurred, invoke onError callback
                        callback.onError(task.getException());
                    }
                });
    }
    public boolean checkCurrentUser() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public void checkCurrentUserType(CurrentUserTypeCallback callback) {
        if (checkCurrentUser()) {
            String userId = firebaseAuth.getCurrentUser().getUid();
            DocumentReference docRef = UsersDB.collection("UsersDB").document(userId);
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    callback.onCurrentUserType("normalUser");
                } else {
                    callback.onCurrentUserType("companyUser");
                }
            }).addOnFailureListener(e -> {
                callback.onCurrentUserType("companyUser");
            });
        } else {
            callback.onCurrentUserType(null);
        }
    }
    public void getNormalUserData(OnUserDataCallback callback) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference userRef = firestore.collection("UsersDB").document(userId);
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    // Retrieve the user data from the document snapshot
                    String normalEmail = document.getString("normalEmail");
                    String normalFirstName = document.getString("normalFirstName");
                    String normalSurname = document.getString("normalSurname");
                    String normalPhone = document.getString("normalPhone");
                    String normalCity = document.getString("normalCity");

                    Date normalBirthdate = document.getDate("normalBirthdate");

                    String normalGender = document.getString("normalGender");
                    String normalLifeResume = document.getString("normalLifeResume");
                    String TAG = document.getString("TAG");


                    fetchedNormalUser = new User(normalEmail,null,normalFirstName,normalSurname,normalPhone,normalCity,normalBirthdate,normalGender
                            ,null,null,null,normalLifeResume,null,null,TAG);

                    String profilePicPath = "normalUserImages/" + userId + "/profilePic";
                    StorageReference profilePic = storageRef.child(profilePicPath);
                    profilePic.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            fetchedNormalUser.setProfilePic(uri);
                            Log.d("image found", "onSuccess: image founded beachh" + fetchedNormalUser.getProfilePic());

                            String extraImagesPath = "normalUserImages/" + userId + "/extraImages/";
                            StorageReference folderRef = storageRef.child(extraImagesPath);

                            folderRef.listAll()
                                    .addOnSuccessListener(listResult -> {
                                        // Iterate over the items (files) in the folder
                                        for (StorageReference item : listResult.getItems()) {
                                            // Get the download URL for each item
                                            item.getDownloadUrl().addOnSuccessListener(uri2 ->
                                            {
                                                Log.d("working", "onSuccess: " + uri2);
                                                // Add the URI to the list
                                                uriList.add(uri2);
                                                Log.d("uri list value", "onSuccess: " + uriList.size());

                                                if (uriList.size() == listResult.getItems().size()){
                                                    Log.d("uri determin size", "onSuccess: " + uriList.size());
                                                    fetchedNormalUser.setExtraImages(uriList);
                                                    // Invoke the callback with the retrieved user data
                                                    callback.onSuccess(fetchedNormalUser);
                                                }

                                            });
                                        }
                                    });
                        }
                    });
                }
                else {
                    // Document doesn't exist or is null
                    callback.onError("User document doesn't exist");
                }
            }
            else {
                // Error occurred while retrieving the document
                callback.onError(task.getException().getMessage());
            }
        });
    }
    public void getCompanyUserData(OnCompanyUserDataCallback callback){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference userRef = firestore.collection("CompanyUsersDB").document(userId);
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    // Retrieve the user data from the document snapshot
                    String companyEmail = document.getString("companyEmail");
                    String companyName = document.getString("companyName");
                    String companyPhone = document.getString("companyPhone");
                    String companyBio = document.getString("companyBio");
                    float companyRatingBar = document.getDouble("companyRatingBar").floatValue();
                    String TAG = document.getString("TAG");


                    Map<String, Object> companyLocation = (Map<String, Object>) document.getData().get("companyLocation");
                    double latitude = (double) companyLocation.get("latitude");
                    double longitude = (double) companyLocation.get("longitude");
                    LatLng location = new LatLng(latitude,longitude);


                    fetchedCompanyUser = new CompanyUser(companyName,companyEmail,null,companyPhone,companyBio,null,null,location,companyRatingBar
                            ,TAG);

                    String profilePicPath = "companyUserImages/" + userId + "/profilePic";
                    StorageReference profilePic = storageRef.child(profilePicPath);
                    profilePic.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            fetchedCompanyUser.setProfilePic(uri);
                            Log.d("image found", "onSuccess: image founded beachh" + fetchedCompanyUser.getProfilePic());

                            String extraImagesPath = "companyUserImages/" + userId + "/extraImages/";
                            StorageReference folderRef = storageRef.child(extraImagesPath);

                            folderRef.listAll()
                                    .addOnSuccessListener(listResult -> {
                                        // Iterate over the items (files) in the folder
                                        for (StorageReference item : listResult.getItems()) {
                                            // Get the download URL for each item
                                            item.getDownloadUrl().addOnSuccessListener(uri2 ->
                                            {
                                                Log.d("working", "onSuccess: " + uri2);
                                                // Add the URI to the list
                                                uriList.add(uri2);
                                                Log.d("uri list value", "onSuccess: " + uriList.size());

                                                if (uriList.size() == listResult.getItems().size()){
                                                    Log.d("uri determin size", "onSuccess: " + uriList.size());
                                                    fetchedCompanyUser.setExtraImages(uriList);
                                                    // Invoke the callback with the retrieved user data
                                                    callback.onSuccess(fetchedCompanyUser);
                                                }

                                            });
                                        }
                                    });
                        }
                    });
                }
                else {
                    // Document doesn't exist or is null
                    callback.onError("User document doesn't exist");
                }
            }
            else {
                // Error occurred while retrieving the document
                callback.onError(task.getException().getMessage());
            }
        });
    }

    public void getCompanyItems(OnNormalItemsDataCallback callback) {
        companyCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<CompanyUser> companyUsers = new ArrayList<>();

                int totalTasks = task.getResult().size(); // Total number of documents
                AtomicInteger completedTasks = new AtomicInteger(0); // Counter for completed tasks

                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Retrieve document fields
                    userId = document.getId();
                    String companyEmail = document.getString("companyEmail");
                    String companyName = document.getString("companyName");
                    String companyPhone = document.getString("companyPhone");
                    String companyBio = document.getString("companyBio");
                    float companyRatingBar = document.getDouble("companyRatingBar").floatValue();
                    String TAG = document.getString("TAG");

                    Map<String, Object> companyLocation = (Map<String, Object>) document.getData().get("companyLocation");
                    double latitude = (double) companyLocation.get("latitude");
                    double longitude = (double) companyLocation.get("longitude");
                    LatLng location = new LatLng(latitude, longitude);

                    String profilePicPath = "companyUserImages/" + userId + "/profilePic";
                    StorageReference profilePic = storageRef.child(profilePicPath);

                    CompanyUser companyUser = new CompanyUser(companyName, companyEmail, null, companyPhone,
                            companyBio, null, null, location, companyRatingBar, TAG);


                    profilePic.getDownloadUrl().addOnSuccessListener(uri -> {
                        Log.d("uriExist", "getNormalCompanyItems: " + uri);
                        companyUser.setProfilePic(uri);
                        companyUsers.add(companyUser);

                        int completedCount = completedTasks.incrementAndGet();
                        if (completedCount == totalTasks) {
                            // All profile pictures retrieved, invoke the callback
                            callback.onSuccess(companyUsers);
                        }
                    });
                }
            } else {
                // Task failed, handle the error
                callback.onError("Failed to retrieve company items");
            }
        });
    }

    public void getNormalItems(OnCompanyItemsDataCallback callback){
        normalCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<User> normalUsers = new ArrayList<>();

                int totalTasks = task.getResult().size(); // Total number of documents
                AtomicInteger completedTasks = new AtomicInteger(0); // Counter for completed tasks

                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Retrieve document fields
                    userId = document.getId();
                    String normalEmail = document.getString("normalEmail");
                    String normalFirstName = document.getString("normalFirstName");
                    String normalSurname = document.getString("normalSurname");
                    String normalPhone = document.getString("normalPhone");
                    String normalCity = document.getString("normalCity");
                    String normalLifeResume = document.getString("normalLifeResume");
                    String normalGender = document.getString("normalGender");
                    String TAG = document.getString("TAG");

                    Date normalBirthdate = document.getDate("normalBirthdate");


                    String profilePicPath = "normalUserImages/" + userId + "/profilePic";
                    StorageReference profilePic = storageRef.child(profilePicPath);

                    User user = new User(normalEmail, null, normalFirstName, normalSurname,
                            normalPhone, normalCity, normalBirthdate,normalGender,null,null,null,normalLifeResume
                            ,null,null,TAG);


                    profilePic.getDownloadUrl().addOnSuccessListener(uri -> {
                        user.setProfilePic(uri);
                        normalUsers.add(user);
                        Log.d("bob2", "getNormalItems: " + normalUsers);
                        int completedCount = completedTasks.incrementAndGet();
                        if (completedCount == totalTasks) {
                            // All profile pictures retrieved, invoke the callback
                            Log.d("bob", "getNormalItems: " + normalUsers);
                            callback.onSuccess(normalUsers);
                        }
                    });
                }
            } else {
                // Task failed, handle the error
                callback.onError("Failed to retrieve user items");
            }
        });
    }


}


