package com.example.authenticationwithviewmodel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.sideClasses.CompanyUser;
import com.example.authenticationwithviewmodel.sideClasses.User;
import com.example.authenticationwithviewmodel.views.main.companyUserFragments.CompanyUserHomeFragment;
import com.example.authenticationwithviewmodel.views.main.userFragments.NormalUserHomeFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NormalItemAdapter extends ArrayAdapter<User> {
    public static class ViewHolder{
        CircleImageView profilePic;
        TextView tvFirstName,tvSurname,tvPhone,tvEmail,tvGender;
    }

    CompanyUserHomeFragment companyUserHomeFragment;

    public NormalItemAdapter(Context context, List<User> userItems,CompanyUserHomeFragment companyUserHomeFragment){
        super(context,0,userItems);
        this.companyUserHomeFragment = companyUserHomeFragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User userItem = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_normal_item,parent,false);
            viewHolder.tvFirstName = convertView.findViewById(R.id.tvFirstName);
            viewHolder.tvSurname = convertView.findViewById(R.id.tvSurname);
            viewHolder.tvPhone = convertView.findViewById(R.id.phoneId);
            viewHolder.tvEmail = convertView.findViewById(R.id.email);
            viewHolder.tvGender = convertView.findViewById(R.id.tvGender);
            viewHolder.profilePic = convertView.findViewById(R.id.imageProfile);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set the data for each view in the item layout
        viewHolder.tvFirstName.setText(userItem.getFirstName());
        viewHolder.tvSurname.setText(userItem.getSurname());
        viewHolder.tvPhone.setText(userItem.getPhone());
        viewHolder.tvEmail.setText(userItem.getEmail());
        viewHolder.tvGender.setText(userItem.getEmail());
        viewHolder.profilePic.setImageURI(userItem.getProfilePic());
        Picasso.get().load(userItem.getProfilePic()).into(viewHolder.profilePic);

        convertView.setOnClickListener(v -> {
            companyUserHomeFragment.navigateToViewNormalProfileFragment();
        });

        return convertView;
    }
}
