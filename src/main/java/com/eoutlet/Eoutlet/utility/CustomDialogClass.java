package com.eoutlet.Eoutlet.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.fragments.LoginNewFragment;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.GuestUser;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

public class CustomDialogClass extends DialogFragment implements
        android.view.View.OnClickListener {

    public Activity c;
    private TextView textdescription;
    private View view;
    private Dialog dialog;
    public Button yes, no;
    private GoogleSignInClient mGoogleSignInClient;
    int requestcode;
    public CustomDialogClass(Activity a) {

        // TODO Auto-generated constructor stub
        this.c = a;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            view = inflater.inflate(R.layout.custom_dialog, null);
        }
        else{
            view = inflater.inflate(R.layout.custom_dialog_arabic, null);

        }
        requestcode =  getTargetRequestCode();
        Log.e("request Code",String.valueOf(requestcode)+"requestcode");


        //dialog.requestWindowFeature(getContext());
        yes = view.findViewById(R.id.btn_yes);
        no = view.findViewById(R.id.btn_no);
        textdescription = view.findViewById(R.id.txt_description);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));

        if(requestcode == 1003){
            if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
                textdescription.setText("Are you sure you want to delete your account?");
            }
            else
            {
            textdescription.setText("هل انت متأكد انك تريد حذف حسابك؟");
            }
        }
        dialog.show();
        return dialog;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                if (getTargetFragment() != null) {
                    if(requestcode == 1002) {
                        Bundle bundle = new Bundle();

                        Intent mIntent = new Intent();
                        mIntent.putExtras(bundle);
                        getTargetFragment().onActivityResult(1002,
                                Activity.RESULT_OK, mIntent);
                    }
                    else{
                        Bundle bundle = new Bundle();

                        Intent mIntent = new Intent();
                        mIntent.putExtras(bundle);
                        getTargetFragment().onActivityResult(1003,
                                Activity.RESULT_OK, mIntent);

                    }
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