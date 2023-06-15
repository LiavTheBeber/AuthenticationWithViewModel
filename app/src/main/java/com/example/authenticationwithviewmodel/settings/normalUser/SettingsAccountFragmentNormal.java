package com.example.authenticationwithviewmodel.settings.normalUser;

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
import android.widget.Toast;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.adapters.AccountItemAdapter;
import com.example.authenticationwithviewmodel.settings.normalUser.account.EditUserValuesFragment;
import com.example.authenticationwithviewmodel.sideClasses.AccountItem;
import com.example.authenticationwithviewmodel.viewModel.MainViewModel;
import com.example.authenticationwithviewmodel.views.AuthActivity;

import java.util.ArrayList;
import java.util.List;

public class SettingsAccountFragmentNormal extends Fragment {

    private ListView listViewItems1,listViewItems2;
    private Button btnLogOut;
    private List<AccountItem> accountItems1,accountItems2;
    private AccountItemAdapter accountItemAdapter;
    private MainViewModel mainViewModel;
    private EditUserValuesFragment editUserValuesFragment;
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
        View view = inflater.inflate(R.layout.fragment_settings_account_normal, container, false);
        listViewItems1 = view.findViewById(R.id.list_view_items);
        listViewItems2 = view.findViewById(R.id.list_view_items2);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        editUserValuesFragment = new EditUserValuesFragment();
        args = new Bundle();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountItems1 =  new ArrayList<>();
        accountItemAdapter  = new AccountItemAdapter(requireContext(),accountItems1);
        listViewItems1.setAdapter(accountItemAdapter);

        accountItems2 =  new ArrayList<>();
        accountItemAdapter  = new AccountItemAdapter(requireContext(),accountItems2);
        listViewItems2.setAdapter(accountItemAdapter);

        fetchInfoFromViewModel();

        accountItems2.add(new AccountItem("LifeResume","click to update"));
        accountItemAdapter.notifyDataSetChanged();





        listViewItems1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                AccountItem accountItem = (AccountItem) parent.getItemAtPosition(position);

                fragmentName = accountItem.getItemTitle();
                args.putString(KEY_FRAGMENT_NAME, fragmentName);
                editUserValuesFragment.setArguments(args);

                if (accountItem.getItemTitle().equals("FirstName")){
                    replaceFragment(editUserValuesFragment);
                }
                else if (accountItem.getItemTitle().equals("Surname")){
                    replaceFragment(editUserValuesFragment);
                }
                else if (accountItem.getItemTitle().equals("Phone")){
                    replaceFragment(editUserValuesFragment);
                }
                else if (accountItem.getItemTitle().equals("City")){
                    replaceFragment(editUserValuesFragment);
                }
            }
        });

        listViewItems2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                AccountItem accountItem = (AccountItem) parent.getItemAtPosition(position);

                fragmentName = accountItem.getItemTitle();
                args.putString(KEY_FRAGMENT_NAME, fragmentName);
                editUserValuesFragment.setArguments(args);

                if (accountItem.getItemTitle().equals("LifeResume")){
                    replaceFragment(editUserValuesFragment);
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

    private void fetchInfoFromViewModel(){

        // updating the first litView
        mainViewModel.getSavedFirstNameNormal().observe(getViewLifecycleOwner(),stringFirstName->{
            accountItems1.add(new AccountItem("FirstName",stringFirstName));
            accountItemAdapter.notifyDataSetChanged();
        });
        mainViewModel.getSavedSurnameNormal().observe(getViewLifecycleOwner(),stringSurname->{
            accountItems1.add(new AccountItem("Surname",stringSurname));
            accountItemAdapter.notifyDataSetChanged();
        });
        mainViewModel.getSavedPhoneNormal().observe(getViewLifecycleOwner(),stringPhone->{
            accountItems1.add(new AccountItem("Phone",stringPhone));
            accountItemAdapter.notifyDataSetChanged();
        });
        mainViewModel.getSavedGenderNormal().observe(getViewLifecycleOwner(),stringGender->{
            accountItems1.add(new AccountItem("Gender",stringGender));
            accountItemAdapter.notifyDataSetChanged();
        });
        mainViewModel.getSavedCityNormal().observe(getViewLifecycleOwner(),stringCity->{
            accountItems1.add(new AccountItem("City",stringCity));
            accountItemAdapter.notifyDataSetChanged();
        });
        mainViewModel.getSavedAgeNormal().observe(getViewLifecycleOwner(),intAge->{
            accountItems1.add(new AccountItem("Age",String.valueOf(intAge)));
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