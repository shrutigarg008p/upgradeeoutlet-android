package com.eoutlet.Eoutlet.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.ThankyouActivity;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.WalletPaymentStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThreedpasswordFragments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThreedpasswordFragments extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private WebView threedpaymentwebview;
    ParentDialogsListener parentDialogListener;
    private String url, orderId, token_name, totalvalue, coupencode;
    private final int interval = 5000; // 1 Second
    Handler handler = new Handler();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    Timer timer = new Timer();
    public static boolean isthreedpasswordwalletwebview = false;

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

    public ThreedpasswordFragments() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThreedpasswordFragments.
     */
    // TODO: Rename and change types and number of parameters
    public static ThreedpasswordFragments newInstance(String param1, String param2) {
        ThreedpasswordFragments fragment = new ThreedpasswordFragments();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.activity_threedpasswordactivity, container, false);
        threedpaymentwebview = view.findViewById(R.id.threedpaymentWebview);

        isthreedpasswordwalletwebview = true;

       /* url = getIntent().getStringExtra("url");
        orderId = getIntent().getStringExtra("order_id");*/

        threedpaymentwebview.getSettings().setJavaScriptEnabled(true);

        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();


        Bundle bundel = getArguments();


        url = bundel.getString("url");
        orderId = bundel.getString("order_id");
        token_name = bundel.getString("token_name");

        totalvalue = bundel.getString("totalvalue");
        coupencode = bundel.getString("coupencode");


        parentDialogListener.showProgressDialog();


        threedpaymentwebview.getSettings().setJavaScriptEnabled(true);
        threedpaymentwebview.getSettings().setUseWideViewPort(true);
        threedpaymentwebview.getSettings().setLoadWithOverviewMode(true);
        threedpaymentwebview.clearCache(true);
        threedpaymentwebview.clearHistory();
        threedpaymentwebview.clearFormData();
        threedpaymentwebview.setWebViewClient(new MyBrowser());
        threedpaymentwebview.loadUrl(url);


        return view;

    }

    private class MyBrowser extends WebViewClient {


        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            //  parentDialogListener.showProgressDialog();
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
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("---------inside timer-------------");
                    rungetstatusAPI();
                    //Toast.makeText(getContext(), "C'Mom no hands!", Toast.LENGTH_SHORT).show();
                }
            }, 0, 7000);

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(getContext(), "Error" + description + errorCode, Toast.LENGTH_SHORT).show();
        }


    }

    public void rungetstatusAPI() {


        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();


        query.put("order_id", orderId);
        query.put("token_name", token_name);
        query.put("is_wallet", "1");
        query.put("devicetype", "android");

        //Call<WalletPaymentStatus> call = apiService.getpaymentstatus(query);
        Call<WalletPaymentStatus> call = apiService.getcheckoutpaymentstatus(query);
        call.enqueue(new Callback<WalletPaymentStatus>() {
            @Override
            public void onResponse(Call<WalletPaymentStatus> call, Response<WalletPaymentStatus> response) {


                if (response.body() != null) {

                    parentDialogListener.hideProgressDialog();

                    if (response.body().msg.equals("success")) {
                        timer.cancel();

                        isthreedpasswordwalletwebview = false;
                        if (getContext() != null) {
                            MySharedPreferenceClass.setBedgeCount(getContext(), 0);
                        }

                        // Toast.makeText(view.getContext(), "تم اضافة الرصيد بنجاح الى حسابك", Toast.LENGTH_SHORT).show();
                        if (view.getContext() != null) {
                            Intent in = new Intent(view.getContext(), ThankyouActivity.class);
                            in.putExtra("orderId", orderId);
                            in.putExtra("statusflag", response.body().msg);
                            in.putExtra("totalvalue", totalvalue);
                            in.putExtra("coupencode", coupencode);
                            Activity activity = getActivity();
                            if (isAdded() && activity != null) {
                                startActivity(in);
                            }

                        }



                        Activity activity = getActivity();
                        if (isAdded() && activity != null) {
                            getActivity().finish();
                        }
                        //clearBackStack();
                    } else if (response.body().msg.equals("failed")) {
                        timer.cancel();
                        isthreedpasswordwalletwebview = false;
                        //  Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_SHORT).show();


                        Intent in = new Intent(getContext(), ThankyouActivity.class);
                        in.putExtra("orderId", orderId);
                        in.putExtra("statusflag", response.body().msg);

                        startActivity(in);
                        Activity activity = getActivity();
                        if (isAdded() && activity != null) {
                            getActivity().finish();
                        }

                        //clearBackStack();
                    }


                }

            }


            @Override
            public void onFailure(Call<WalletPaymentStatus> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                // progressDialog.hide();
                Log.e(TAG, t.toString());
                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });

    }


    private void clearBackStack() {

        if (getFragmentManager() != null) {
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

    }


}