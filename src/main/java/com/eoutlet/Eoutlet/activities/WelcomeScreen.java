package com.eoutlet.Eoutlet.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.eoutlet.Eoutlet.R;

import java.util.Locale;

public class WelcomeScreen extends AppCompatActivity {
private String userlanguage = "en";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       userlanguage =  Locale.getDefault().getLanguage();

        if(userlanguage.equals("en")) {
            setContentView(R.layout.activity_welcome_screen);
        }
        else{

            setContentView(R.layout.activity_welcome_screen_arabic);

        }

        findViewById(R.id.continuetonext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(WelcomeScreen.this, CategorySelectionActivity.class);
                //Intent in = new Intent(SplashActivity.this, EoutletSplash.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(in);

                finish();
            }
        });


    }
}