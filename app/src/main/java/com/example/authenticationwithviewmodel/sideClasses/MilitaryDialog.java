package com.example.authenticationwithviewmodel.sideClasses;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.authenticationwithviewmodel.R;

public class MilitaryDialog extends Dialog {
    private EditText editBranch, editRank, editYears, editAdditional;
    private Button btnSave;
    private OnSaveClickListener onSaveClickListener;

    public MilitaryDialog(Context context) {
        super(context);
    }

    public interface OnSaveClickListener {
        void onSaveClicked(String branch, String rank, String years, String additional);
    }

    public void setOnSaveClickListener(OnSaveClickListener listener) {
        onSaveClickListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.military_dialog);

        editBranch = findViewById(R.id.editBranch);
        editRank = findViewById(R.id.editRank);
        editYears = findViewById(R.id.editYears);
        editAdditional = findViewById(R.id.editAdditional);
        btnSave = findViewById(R.id.btnSave);

        // Set the dialog dimensions
        Window window = getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String branch = editBranch.getText().toString();
                String rank = editRank.getText().toString();
                String years = editYears.getText().toString();
                String additional = editAdditional.getText().toString();

                if (onSaveClickListener != null) {
                    onSaveClickListener.onSaveClicked(branch, rank, years, additional);
                }

                dismiss();
            }
        });
    }
}
