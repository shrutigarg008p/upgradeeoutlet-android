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
import com.eoutlet.Eoutlet.pojo.ExchangeAndReturnResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReplacementFragment extends Fragment {
    private WebView replacementWebview;
    private String value = " ";
    public ParentDialogsListener parentDialogListener;
    String Locale;

    ImageView logo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_replacement, container, false);
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
//        value = "https://upgrade.eoutlet.com/return-policy";
        initUi(view);
        getExchangeAndReturnPolicy();
        return view;
    }

    private void getExchangeAndReturnPolicy() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<ExchangeAndReturnResponse> call = Locale == "ar" ? apiService.getExchangeAndReturn("https://upgrade.eoutlet.com/rest/V1/api/getstaticpage/arabic-return-policy") : apiService.getExchangeAndReturn("https://upgrade.eoutlet.com/rest/V1/api/getstaticpage/return-policy");
        call.enqueue(new Callback<ExchangeAndReturnResponse>() {
            @Override
            public void onResponse(Call<ExchangeAndReturnResponse> call, Response<ExchangeAndReturnResponse> response) {
                if (response.body() != null) {

                    if (response.body().response.equals("success")) {
                        value = response.body().data;
//                        ShippingWebview.loadData(value, "text/html", "UTF-8");
                        replacementWebview.loadDataWithBaseURL(null, value, "text/html", "UTF-8", null);
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
            public void onFailure(Call<ExchangeAndReturnResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
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


    private void initUi(View view) {
        replacementWebview = view.findViewById(R.id.replacementWebview);
        replacementWebview.setWebViewClient(new MyBrowser());
        replacementWebview.getSettings().setJavaScriptEnabled(true);
//        replacementWebview.loadUrl(value);
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
          //  parentDialogListener.showProgressDialog();
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }


        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
          //  parentDialogListener.showProgressDialog();
            Toast.makeText(getContext(), "Error" + description + errorCode, Toast.LENGTH_SHORT).show();
        }
    }
}
