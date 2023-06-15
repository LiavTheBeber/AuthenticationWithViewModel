package com.example.authenticationwithviewmodel.views.main.userFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.adapters.CompanyItemAdapter;
import com.example.authenticationwithviewmodel.sideClasses.CompanyUser;
import com.example.authenticationwithviewmodel.viewModel.MainViewModel;
import com.example.authenticationwithviewmodel.views.main.companyUserFragments.ViewCompanyProfileFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class NormalUserHomeFragment extends Fragment {
    private CompanyItemAdapter companyItemAdapter;
    private List<CompanyUser> companyItems;
    private ListView listViewItems;
    private MainViewModel mainViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_normal_user_home, container, false);
        listViewItems = view.findViewById(R.id.listViewCompanyItems);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        companyItems = new ArrayList<>();
        companyItemAdapter = new CompanyItemAdapter(requireContext(),companyItems,this);
        listViewItems.setAdapter(companyItemAdapter);

        mainViewModel.getSavedNormalItems().observe(getViewLifecycleOwner(),normalItems->{
            Log.d("normalItems", "onViewCreated: d" + normalItems);
            for (int i = 0;i<normalItems.size(); i++){
                companyItems.add(normalItems.get(i));
            }
            companyItemAdapter.notifyDataSetChanged();
        });




    }

    public void navigateToViewCompanyProfileFragment() {
        ViewCompanyProfileFragment viewCompanyProfileFragment = new ViewCompanyProfileFragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(getId(), viewCompanyProfileFragment)
                .commit();
    }


}