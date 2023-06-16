package com.example.authenticationwithviewmodel.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.authenticationwithviewmodel.sideClasses.CompanyUser;
import com.example.authenticationwithviewmodel.sideClasses.User;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<User> transferredUser = new MutableLiveData<>();
    private MutableLiveData<CompanyUser> transferredCompanyUser = new MutableLiveData<>();

    public void setTransferredUser(User user){this.transferredUser.setValue(user);}
    public LiveData<User> getTransferredUser(){return transferredUser;}

    public void setTransferredCompanyUser(CompanyUser companyUser){this.transferredCompanyUser.setValue(companyUser);}
    public LiveData<CompanyUser> getTransferredCompanyUser(){return transferredCompanyUser;}




}
