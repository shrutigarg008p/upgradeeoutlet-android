package com.eoutlet.Eoutlet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.GetCategoryCode;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

//public class CategorySelectionActivity extends AppCompatActivity,  {
public class CategorySelectionActivity extends ParentActivity {
    private String userlanguage = "en";
LinearLayout categorySelectionContainer;

    private String menId, womenId, babiesId, childerenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userlanguage = Locale.getDefault().getLanguage();


        MySharedPreferenceClass.setChoosenLanguage(getApplicationContext(), userlanguage);

        if (userlanguage.equals("en")) {
            setContentView(R.layout.activity_category_selection);
        } else {

            setContentView(R.layout.activity_category_selection_arabic);

        }
     categorySelectionContainer = findViewById(R.id.categorySelectionContainer);
        categorySelectionContainer.setVisibility(View.GONE);
        getUpgradedLatestversionCode();
        findViewById(R.id.shopwomen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(CategorySelectionActivity.this, MainActivity.class);
                //Intent in = new Intent(SplashActivity.this, EoutletSplash.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("CatId", womenId);

                MySharedPreferenceClass.setCatId(getApplicationContext(), womenId);
                MySharedPreferenceClass.setIsFirstTime(getApplicationContext(), true);

                startActivity(in);
                finish();


            }
        });
        findViewById(R.id.shopmen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(CategorySelectionActivity.this, MainActivity.class);
                //Intent in = new Intent(SplashActivity.this, EoutletSplash.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("CatId", menId);
                MySharedPreferenceClass.setCatId(getApplicationContext(), menId);
                MySharedPreferenceClass.setIsFirstTime(getApplicationContext(), true);
                startActivity(in);
                finish();

            }
        });
        findViewById(R.id.shopkids).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(CategorySelectionActivity.this, MainActivity.class);
                //Intent in = new Intent(SplashActivity.this, EoutletSplash.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("CatId", babiesId);
                MySharedPreferenceClass.setCatId(getApplicationContext(), babiesId);
                MySharedPreferenceClass.setIsFirstTime(getApplicationContext(), true);
                startActivity(in);
                finish();

            }
        });
        findViewById(R.id.shopchild).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(CategorySelectionActivity.this, MainActivity.class);
                //Intent in = new Intent(SplashActivity.this, EoutletSplash.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("CatId", childerenId);
                MySharedPreferenceClass.setCatId(getApplicationContext(), childerenId);
                MySharedPreferenceClass.setIsFirstTime(getApplicationContext(), true);
                startActivity(in);

                finish();

            }
        });


    }


    public void getTokenID(String username, String password) {
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        map1.put("username", username);
        map1.put("password", password);

        // mainparam.put("param",map1);

        Call<String> call = apiService.createtokenbyId(map1);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null) {

                    Toast.makeText(CategorySelectionActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("Response------->>>>", response.body().toString());

                    MySharedPreferenceClass.setToken(getApplicationContext(), response.body().toString());


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, t.toString());

                Toast.makeText(getApplicationContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void getUpgradedLatestversionCode() {
        showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Call<GetCategoryCode> call = apiService.getUpgradedLatestAppVersion();
        call.enqueue(new Callback<GetCategoryCode>() {
            @Override
            public void onResponse(Call<GetCategoryCode> call, Response<GetCategoryCode> response) {
                if (response.body() != null) {
                    if (response.body().response.equals("success")) {
                        hideDialog();
                        menId = response.body().menId;
                        womenId = response.body().womenId;
                        childerenId = response.body().childrenId;
                        babiesId = response.body().babiesId;
                        categorySelectionContainer.setVisibility(View.VISIBLE);

                        //Toast.makeText(MainActivity.this, "Inside version code", Toast.LENGTH_SHORT).show();

                    } else {
                        hideDialog();
                    }
                } else {
                    hideDialog();
                }
            }

            @Override
            public void onFailure(Call<GetCategoryCode> call, Throwable t) {
                hideDialog();
                Log.e(TAG, t.toString());
            }
        });
    }

}