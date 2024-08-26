package com.eoutlet.Eoutlet.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.AboutUsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AboutUsFragment extends Fragment {

    private View view;
    public ParentDialogsListener parentDialogListener;
    private WebView aboutUsWebview;
    private String value = " ";
    String Locale;
    ImageView logo;

    public AboutUsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            parentDialogListener = (ParentDialogsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " Activity's Parent should be Parent Activity");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about_us, container, false);
        logo =  view.findViewById(R.id.logo);

        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {


                Locale = "ar";
            } else {
                Locale = "en";
                logo.setImageDrawable(getContext().getDrawable(R.drawable.logo_english_black));

            }
        } else {
            Locale = "ar";
        }
//        value = "https://upgrade.eoutlet.com/about-us";
        initUi(view);
        getAboutUsDetails();
        return view;

    }

    private void getAboutUsDetails() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<AboutUsResponse> call = Locale == "ar" ? apiService.getAboutUs("https://upgrade.eoutlet.com/rest/V1/api/getstaticpage/arabic-about-us") : apiService.getAboutUs("https://upgrade.eoutlet.com/rest/V1/api/getstaticpage/about-us");
        call.enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {
                if (response.body() != null) {

                    if (response.body().response.equals("success")) {
                        value = response.body().data;
                        aboutUsWebview.loadDataWithBaseURL(null, value, "text/html", "UTF-8", null);
                        parentDialogListener.hideProgressDialog();
                    } else {
                        parentDialogListener.hideProgressDialog();
                        Log.e("Faq request", "failed");
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AboutUsResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initUi(View view) {
        aboutUsWebview = view.findViewById(R.id.aboutUsWebview);
        aboutUsWebview.setWebViewClient(new MyBrowser());
        aboutUsWebview.getSettings().setJavaScriptEnabled(true);
//        aboutUsWebview.loadUrl(value);
//        parentDialogListener.showProgressDialog();
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
         //   parentDialogListener.hideProgressDialog();
        }

        @TargetApi(android.os.Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
           // parentDialogListener.showProgressDialog();
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }


        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        //    parentDialogListener.showProgressDialog();
            Toast.makeText(getContext(), "Error" + description + errorCode, Toast.LENGTH_SHORT).show();
        }
    }
}