package com.example.authenticationwithviewmodel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.settings.normalUser.SettingsAccountFragmentNormal;
import com.example.authenticationwithviewmodel.sideClasses.AccountItem;

import java.util.List;

public class AccountItemAdapter extends ArrayAdapter<AccountItem> {
    public AccountItemAdapter(Context context, List<AccountItem> accountItems) {
        super(context, 0, accountItems);
    }
    SettingsAccountFragmentNormal settingsAccountFragment;
    private static class ViewHolder{
        TextView textItemDescription;
        TextView textViewTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AccountItem accountItem = getItem(position);

        AccountItemAdapter.ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new AccountItemAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_account_item, parent, false);
            viewHolder.textItemDescription = convertView.findViewById(R.id.infoTextView);
            viewHolder.textViewTitle = convertView.findViewById(R.id.titleTextView);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (AccountItemAdapter.ViewHolder) convertView.getTag();

        viewHolder.textItemDescription.setText(accountItem.getItemDescription());
        viewHolder.textViewTitle.setText(accountItem.getItemTitle());

        return convertView;

    }
}
