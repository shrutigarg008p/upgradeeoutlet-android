package com.eoutlet.Eoutlet.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;


import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;


import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.utility.Constants;

import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SizeChartWebView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SizeChartWebView extends Fragment {

    String sizechartposition;
    private View view;
    public ParentDialogsListener parentDialogListener;
    private ImageView backarrow;
    private Toolbar toolbar1;
    private WebView threedpaymentwebview;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    public SizeChartWebView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SizeChartWebView.
     */
    // TODO: Rename and change types and number of parameters
    public static SizeChartWebView newInstance(String param1, String param2) {
        SizeChartWebView fragment = new SizeChartWebView();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
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


        sizechartposition = getArguments().getString("sizechartposition");

        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
            view = inflater.inflate(R.layout.fragment_size_chart_web_view, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_size_chart_web_view_arabic, container, false);
        }

       threedpaymentwebview = view.findViewById(R.id.sizechartwebview);

        toolbar1 = view.findViewById(R.id.toolbar);
        backarrow = toolbar1.findViewById((R.id.backarrow));
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        //firebase_view_item(value);


        Bundle bundel = getArguments();

        sizechartposition = bundel.getString("sizechartposition");
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();


        parentDialogListener.showProgressDialog();

        threedpaymentwebview.getSettings().setJavaScriptEnabled(true);
        threedpaymentwebview.getSettings().setBuiltInZoomControls(true);
        threedpaymentwebview.getSettings().setDisplayZoomControls(false);
        threedpaymentwebview.getSettings().setUseWideViewPort(true);
        threedpaymentwebview.getSettings().setLoadWithOverviewMode(true);
        threedpaymentwebview.clearCache(true);
        threedpaymentwebview.clearHistory();
        threedpaymentwebview.clearFormData();
        threedpaymentwebview.setWebViewClient(new SizeChartWebView.MyBrowser());

        System.out.println("URL is-->>>" + Constants.HOST_LINK + "webservice/index/sizechart?" + "sizechart=" + sizechartposition);

        threedpaymentwebview.loadUrl(Constants.HOST_LINK + "webservice/index/sizechart?" + "sizechart=" + sizechartposition);

        return view;


    }


    private class MyBrowser extends WebViewClient {


        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            //parentDialogListener.showProgressDialog();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return super.shouldOverrideUrlLoading(view, url);
/*
            view.loadUrl(url);



            return true;*/
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            parentDialogListener.hideProgressDialog();


        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(getContext(), "Error" + description + errorCode, Toast.LENGTH_SHORT).show();
        }


    }

}