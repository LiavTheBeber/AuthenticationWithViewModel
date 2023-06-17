package com.example.authenticationwithviewmodel.settings.companyUser;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.adapters.AccountItemAdapter;
import com.example.authenticationwithviewmodel.settings.companyUser.account.EditCompanyUserValues;
import com.example.authenticationwithviewmodel.settings.normalUser.account.EditUserValuesFragment;
import com.example.authenticationwithviewmodel.sideClasses.AccountItem;
import com.example.authenticationwithviewmodel.viewModel.MainViewModel;
import com.example.authenticationwithviewmodel.views.AuthActivity;

import java.util.ArrayList;
import java.util.List;


public class SettingsAccountFragmentCompany extends Fragment {

    private ListView listViewItems;
    private Button btnLogOut;
    private List<AccountItem> accountItems;
    private AccountItemAdapter accountItemAdapter;
    private MainViewModel mainViewModel;
    private EditCompanyUserValues editCompanyUserValues;
    private Bundle args;
    private String fragmentName,KEY_FRAGMENT_NAME = "FragmentName";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_account_company, container, false);
        listViewItems = view.findViewById(R.id.list_view_items);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        editCompanyUserValues = new EditCompanyUserValues();
        args = new Bundle();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountItems =  new ArrayList<>();
        accountItemAdapter  = new AccountItemAdapter(requireContext(),accountItems);
        listViewItems.setAdapter(accountItemAdapter);

        fetchInfoFromViewModel();

        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                AccountItem accountItem = (AccountItem) parent.getItemAtPosition(position);

                fragmentName = accountItem.getItemTitle();
                args.putString(KEY_FRAGMENT_NAME, fragmentName);
                editCompanyUserValues.setArguments(args);

                if (accountItem.getItemTitle().equals("Company Name")){
                    replaceFragment(editCompanyUserValues);
                }
                else if (accountItem.getItemTitle().equals("Company Bio")){
                    replaceFragment(editCompanyUserValues);
                }
                else if (accountItem.getItemTitle().equals("Company Phone")){
                    replaceFragment(editCompanyUserValues);
                }
            }
        });




        btnLogOut.setOnClickListener(v -> {
            mainViewModel.logOutUser();
            Intent intent = new Intent(requireContext(), AuthActivity.class);
            startActivity(intent);
            getActivity().finish();
        });



    }

    private void fetchInfoFromViewModel() {
        mainViewModel.getSavedNameCompany().observe(getViewLifecycleOwner(),companyName->{
            accountItems.add(new AccountItem("Company Name",companyName));
            accountItemAdapter.notifyDataSetChanged();
        });
        mainViewModel.getSavedBioCompany().observe(getViewLifecycleOwner(),companyBio->{
            accountItems.add(new AccountItem("Company Bio","Click To Change Bio"));
            accountItemAdapter.notifyDataSetChanged();
        });
        mainViewModel.getSavedPhoneCompany().observe(getViewLifecycleOwner(),companyPhone->{
            accountItems.add(new AccountItem("Company Phone",companyPhone));
            accountItemAdapter.notifyDataSetChanged();
        });
    }

    public void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(getId(), fragment)
                .addToBackStack(null)
                .commit();
    }

}