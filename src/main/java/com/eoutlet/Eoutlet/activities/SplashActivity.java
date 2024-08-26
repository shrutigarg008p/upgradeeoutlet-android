package com.eoutlet.Eoutlet.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.utility.Util;

import java.util.Locale;
import java.util.Map;

public class SplashActivity extends ParentActivity {
    private static int SPLASH_TIME_OUT = 500;
    ImageView mainlogo;
    public static boolean ispopupshow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        ispopupshow =true;
        if (MySharedPreferenceClass.getDeeplinkingPage(getApplicationContext()) != null) {
            //if (MySharedPreferenceClass.getDeeplinkingPage(getApplicationContext()).equalsIgnoreCase("category")) {
                MySharedPreferenceClass.setDeeplinkingNotification(this, null);
                MySharedPreferenceClass.setDeeplinkingId(getApplicationContext(), null);
                MySharedPreferenceClass.setDeeplinkingName(getApplicationContext(), null);
                MySharedPreferenceClass.setDeeplinkingPage(getApplicationContext(), null);
            //}
        }
        setContentView(R.layout.activity_splash);
        mainlogo = findViewById(R.id.mainlogo);

        if(MySharedPreferenceClass.getChoosenlanguage(getApplicationContext())!=null) {
          if(MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equalsIgnoreCase("en")){

              mainlogo.setImageDrawable(getDrawable(R.drawable.logo_english_black));
          }




        }
        else if(Locale.getDefault().getLanguage().equalsIgnoreCase("en")){

            mainlogo.setImageDrawable(getDrawable(R.drawable.logo_english_black));

        }


        if (Util.checkConnection(this)) {
            if (!MySharedPreferenceClass.getIsFirstTime(getApplicationContext())) {
                openNextWelComeActivity();
            } else {

                openNextMainActivity();
            }

        } else {

            openNoInternerdialog();


        }


    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Toast.makeText(getApplicationContext(),"from splash",Toast.LENGTH_LONG).show();
    }

    public void openNoInternerdialog() {
        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialogBuilder.setTitle("No Internet Connection");
        alertDialogBuilder.setMessage("Sorry,no Internet connectivity detected.Please reconnect and try again");


        alertDialogBuilder.setPositiveButton("RETRY",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (Util.checkConnection(getApplicationContext())) {
                            if (!MySharedPreferenceClass.getIsFirstTime(getApplicationContext())) {
                                openNextWelComeActivity();
                            } else {

                                openNextMainActivity();
                            }


                        } else {

                            openNoInternerdialog();


                        }

                    }
                });

        alertDialogBuilder.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void openNextWelComeActivity() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent in = new Intent(SplashActivity.this, WelcomeScreen.class);
                //Intent in = new Intent(SplashActivity.this, EoutletSplash.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(in);

                // overridePendingTransition(R.anim.slide_to_left,R.anim.slide_to_left);

                finish();



            }
        }, SPLASH_TIME_OUT);


    }

    public void openNextMainActivity() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent in = new Intent(SplashActivity.this, MainActivity.class);
                //Intent in = new Intent(SplashActivity.this, EoutletSplash.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(in);

                // overridePendingTransition(R.anim.slide_to_left,R.anim.slide_to_left);

                finish();

            }
        }, SPLASH_TIME_OUT);


    }
}
