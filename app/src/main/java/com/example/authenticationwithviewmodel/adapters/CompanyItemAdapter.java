package com.example.authenticationwithviewmodel.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.sideClasses.CompanyUser;
import com.example.authenticationwithviewmodel.views.main.userFragments.NormalUserHomeFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompanyItemAdapter extends ArrayAdapter<CompanyUser> {

    public static class ViewHolder{
        CircleImageView profilePic;
        TextView companyName,companyPhone,companyEmail;
        RatingBar ratingBar;
    }

    NormalUserHomeFragment normalUserHomeFragment;
    public CompanyItemAdapter(Context context, List<CompanyUser> companyItems,NormalUserHomeFragment normalUserHomeFragment){
        super(context,0,companyItems);
        this.normalUserHomeFragment = normalUserHomeFragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CompanyUser companyItem = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_company_item,parent,false);
            viewHolder.companyName = convertView.findViewById(R.id.tvCompanyName);
            viewHolder.companyPhone = convertView.findViewById(R.id.phoneId);
            viewHolder.companyEmail = convertView.findViewById(R.id.email);
            viewHolder.ratingBar = convertView.findViewById(R.id.ratingBar);
            viewHolder.profilePic = convertView.findViewById(R.id.imageProfile);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set the data for each view in the item layout
        viewHolder.companyName.setText(companyItem.getUsername());
        viewHolder.companyEmail.setText(companyItem.getEmail());
        viewHolder.companyPhone.setText(companyItem.getPhone());
        viewHolder.ratingBar.setRating(companyItem.getRatingBar());
        viewHolder.ratingBar.setIsIndicator(true);
        viewHolder.profilePic.setImageURI(companyItem.getProfilePic());
        Picasso.get().load(companyItem.getProfilePic()).into(viewHolder.profilePic);


        return convertView;

    }




}
