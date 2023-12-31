package com.example.authenticationwithviewmodel.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.authenticationwithviewmodel.sideClasses.SettingsItem;
import com.example.authenticationwithviewmodel.R;

import java.util.List;

public class SettingsItemAdapter extends ArrayAdapter<SettingsItem> {

    public SettingsItemAdapter(Context context, List<SettingsItem> settingItems) {
        super(context, 0, settingItems);
    }

    private static class ViewHolder{
        ImageView imageView;
        TextView textViewTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SettingsItem settingItem = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_settings_item, parent, false);
            viewHolder.imageView = convertView.findViewById(R.id.iconImageView);
            viewHolder.textViewTitle = convertView.findViewById(R.id.TitleTextView);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.imageView.setImageResource(settingItem.getImageResource());
        viewHolder.textViewTitle.setText(settingItem.getItemTitle());

        return convertView;

    }
}
