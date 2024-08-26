package com.eoutlet.Eoutlet.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
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
import com.eoutlet.Eoutlet.pojo.ShippingAndDeliveryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShippingFragment extends Fragment {
    private WebView shippingWebview;
    private String value = " ";
    public ParentDialogsListener parentDialogListener;
    View view;
    String Locale;
    ImageView logo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shipping, container, false);
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
//        value = "https://upgrade.eoutlet.com/shipping-policy";
//        Log.e("value", value);
        initUi(view);
        getShippingandDeliveryPolicy();

        return view;
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

    public void initUi(View view) {
        shippingWebview = view.findViewById(R.id.shippingWebview);
        shippingWebview.setWebViewClient(new MyBrowser());
        shippingWebview.getSettings().setJavaScriptEnabled(true);
    }

    private void getShippingandDeliveryPolicy() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<ShippingAndDeliveryResponse> call = Locale == "ar" ? apiService.getShippingAndDelivery("https://upgrade.eoutlet.com/rest/V1/api/getstaticpage/arabic-shipping-policy") : apiService.getShippingAndDelivery("https://upgrade.eoutlet.com/rest/V1/api/getstaticpage/shipping-policy");
        call.enqueue(new Callback<ShippingAndDeliveryResponse>() {
            @Override
            public void onResponse(Call<ShippingAndDeliveryResponse> call, Response<ShippingAndDeliveryResponse> response) {
                if (response.body() != null) {

                    if (response.body().response.equals("success")) {
                        value = response.body().data;
                        shippingWebview.loadDataWithBaseURL(null, value, "text/html", "UTF-8", null);
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
            public void onFailure(Call<ShippingAndDeliveryResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
    }


    protected class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
//            parentDialogListener.hideProgressDialog();
        }

        @TargetApi(android.os.Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
//            parentDialogListener.showProgressDialog();
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }


        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            Toast.makeText(getContext(), "Error" + description + errorCode, Toast.LENGTH_SHORT).show();
        }

    }

}
