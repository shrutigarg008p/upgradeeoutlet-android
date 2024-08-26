package com.eoutlet.Eoutlet.utility;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class CustomDialogClassforNotification extends DialogFragment implements
        View.OnClickListener {

    public Activity c;
    private View view;
    private Dialog dialog;
    public Button yes, no;
    private GoogleSignInClient mGoogleSignInClient;

    public CustomDialogClassforNotification(Activity a) {

        // TODO Auto-generated constructor stub
        this.c = a;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);


        LayoutInflater inflater = getActivity().getLayoutInflater();

        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            view = inflater.inflate(R.layout.custom_dialog_notification, null);
        }
        else{
            view = inflater.inflate(R.layout.custom_dialog_notification_arabic, null);

        }

        //dialog.requestWindowFeature(getContext());
        yes = view.findViewById(R.id.btn_yes);
        no = view.findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        builder.setView(view);
        dialog = builder.create();
      /*  dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));*/
        dialog.show();
        return dialog;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                if (getTargetFragment() != null) {
                    Bundle bundle = new Bundle();

                    Intent mIntent = new Intent();
                    mIntent.putExtras(bundle);
                    getTargetFragment().onActivityResult(1002,
                            Activity.RESULT_OK, mIntent);
                }
                getDialog().cancel();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

}