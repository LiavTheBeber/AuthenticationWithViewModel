package com.example.authenticationwithviewmodel.views.main.companyUserFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.adapters.CompanyItemAdapter;
import com.example.authenticationwithviewmodel.adapters.NormalItemAdapter;
import com.example.authenticationwithviewmodel.sideClasses.CompanyUser;
import com.example.authenticationwithviewmodel.sideClasses.User;
import com.example.authenticationwithviewmodel.viewModel.MainViewModel;
import com.example.authenticationwithviewmodel.viewModel.SharedViewModel;
import com.example.authenticationwithviewmodel.views.main.userFragments.ViewNormalProfileFragment;

import java.util.ArrayList;
import java.util.List;


public class CompanyUserHomeFragment extends Fragment {

    private MainViewModel mainViewModel;
    private NormalItemAdapter normalItemAdapter;
    private List<User> userItems;
    private ListView listViewItems;
    private SharedViewModel sharedViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_company_user_home, container, false);
        listViewItems = view.findViewById(R.id.listViewNormalItems);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userItems = new ArrayList<>();
        normalItemAdapter = new NormalItemAdapter(requireContext(),userItems,this);
        listViewItems.setAdapter(normalItemAdapter);

        mainViewModel.getSavedCompanyItems().observe(getViewLifecycleOwner(),companyItems->{
            userItems.addAll(companyItems);
            normalItemAdapter.notifyDataSetChanged();
        });

        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) parent.getItemAtPosition(position);
                sharedViewModel.setTransferredUser(user);
                replaceFragment(new ViewNormalProfileFragment());
            }
        });





    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(getId(), fragment)
                .commit();
    }
}