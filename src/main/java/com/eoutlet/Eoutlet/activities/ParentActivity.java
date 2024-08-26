package com.eoutlet.Eoutlet.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;


import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.intrface.OkDialogListener;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.pojo.Message;
import com.eoutlet.Eoutlet.utility.PermissionsChecker;

import java.io.IOException;

import retrofit2.Response;



public class ParentActivity extends AppCompatActivity implements OkDialogListener, ParentDialogsListener {

    private static final String TAG = "ParentActivity";

    public static final int PERMISSIONS_GRANTED = 0;
    public static final int PERMISSIONS_DENIED = 1;

    private static final int PERMISSION_REQUEST_CODE = 0;
    private static final String PACKAGE_URL_SCHEME = "package:";

    ProgressDialog progressDialog;



    ProgressBar progressBar;

    PermissionsChecker checker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
       // progressDialog = new ProgressDialog(this);
       // progressDialog.setMessage("Loading...");
       // progressDialog.setCanceledOnTouchOutside(false);
//        checker = new PermissionsChecker(this);
      //  progressbar = new ProgressBar(this);



    }

    public void showDialog() {
        progressDialog.show();
    }

    public void hideDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showProgressDialog() {
        progressDialog = ProgressDialog.show( this, null, null, false, false);
        progressDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        progressDialog.setContentView( R.layout.progress_bar );
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void hideProgressDialog() {

        try {



            //progressDialog.hide();

           progressDialog.dismiss();
        } catch (Exception e) {

        }

    }

    @Override
    public boolean isProgressBarRunning() {


        //return false;

        //we will check that why its still showing that progressbar is running after hiding it.
          return progressDialog.isShowing();
    }

    @Override
    public <T> void showMessageDialog(Response<T> response, Throwable error, String message) {
        try {
            if (response != null) {
                if (response.isSuccessful()) {
                    message = "success";

                    if (response.body() instanceof Message) {
                        message = ((Message) response.body()).message;
                    }
                } else {
                    String s = response.raw().request().url().toString();
                    Message errorMessage;
                    message = "Not Found";
                    if (s != null) {


                    }

                }

            } else if (error != null) {
                String errorDesc = "";
                if (error instanceof IOException) {
                    message = "Connection Timeout";
                    errorDesc = String.valueOf(error.getCause());
                } else if (error instanceof IllegalStateException) {
                    message = "ConversionError";
                    errorDesc = String.valueOf(error.getCause());
                } else {
                    message = "Other Error";
                    errorDesc = String.valueOf(error.getLocalizedMessage());
                }
                Log.d(TAG, "Retrofeit Message Eror: " + errorDesc);
            }

            Toast.makeText(ParentActivity.this, message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }


    }


    @Override
    public void onOkButtonClick(DialogFragment dialog) {

        dialog.dismiss();
    }


    public boolean isLacksPermissionsAndStartPermissionsActivity(String[] permission, int requestCode) {

        if (checker.lacksPermissions(permission)) {
            requestPermissions(requestCode, permission);
            return true;
        } else {
            return false;
        }


    }


    private void requestPermissions(int requestCode, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (hasAllPermissionsGranted(grantResults)) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        } else {

            showMissingPermissionDialog();
        }
    }

    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    public void showMissingPermissionDialog() {

    }
    protected void hideSoftKeyboard(EditText input) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }
    private void startAppSettings() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }
}
