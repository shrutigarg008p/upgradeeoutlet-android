package com.eoutlet.Eoutlet.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;

public class EmailAddressDialog extends DialogFragment {
    View view;
    Dialog dialog;
    private Button submit;
    private EditText edtemail;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
            view = inflater.inflate(R.layout.email_filled_dialog, null);
        } else {
            view = inflater.inflate(R.layout.email_filled_dialog_arabic, null);
        }

         submit = view.findViewById(R.id.submit);
        edtemail = view.findViewById(R.id.edittext_email);
        builder.setView(view);
        //initViews();
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        dialog.setCanceledOnTouchOutside(false);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (getTargetFragment() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("email", edtemail.getText().toString());
                    Intent mIntent = new Intent();
                    mIntent.putExtras(bundle);
                    getTargetFragment().onActivityResult(1003,
                            Activity.RESULT_OK, mIntent);
                    getDialog().cancel();
                }


            }
        });


        return dialog;
    }

}

