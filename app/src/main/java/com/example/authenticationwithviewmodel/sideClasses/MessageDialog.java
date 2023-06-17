package com.example.authenticationwithviewmodel.sideClasses;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.example.authenticationwithviewmodel.R;
import com.example.authenticationwithviewmodel.viewModel.SharedViewModel;
import com.example.authenticationwithviewmodel.views.main.companyUserFragments.ViewCompanyProfileFragment;
import com.example.authenticationwithviewmodel.views.main.userFragments.ViewNormalProfileFragment;

public class MessageDialog extends Dialog  {
    private EditText etMessage;
    private TextView textView;
    private Button btnSave,btnDismiss;
    private String messageString;
    private ViewNormalProfileFragment viewNormalProfileFragment;


    public MessageDialog(Context context, ViewNormalProfileFragment viewNormalProfileFragment) {
        super(context);
        this.viewNormalProfileFragment = viewNormalProfileFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_dialog);

        etMessage = findViewById(R.id.etMessage);
        textView = findViewById(R.id.messageTitle);
        btnSave = findViewById(R.id.btnSave);
        btnDismiss = findViewById(R.id.btnDismiss);



        // Set the dialog dimensions
        Window window = getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        btnDismiss.setOnClickListener(v -> {
            dismiss();
        });


        btnSave.setOnClickListener(v -> {
            messageString = etMessage.getText().toString();
            if (!messageString.equals("")) {
                viewNormalProfileFragment.sendSmsMessage(messageString);
                Log.d("button clicked", "onCreate: button clicked" + messageString);
                dismiss();
            }
            else
                Toast.makeText(getContext(), "Message Cannot Be Null", Toast.LENGTH_SHORT).show();
        });
    }
}
