package com.eoutlet.Eoutlet.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.fragments.OrderPaymentWebView;
import com.eoutlet.Eoutlet.fragments.ThreedpasswordFragments;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.StcRetainApi;
import com.eoutlet.Eoutlet.pojo.UpgardeCheckoutOrderResponse;
import com.eoutlet.Eoutlet.pojo.WalletPaymentStatus;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class OrderPaymentWebViewActivity extends ParentActivity {
    private WebView threedpaymentwebview;
    Timer timer = new Timer();
    private String url, orderId, totalvalue, coupencode,token_name,paymentId;
    public static boolean ispaymentwebview = false;
    public boolean rembermeflag;
    boolean istimerrunning =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order_payment_web_view);

        //Bundle bundel = getArguments();

        url = getIntent().getExtras().getString("url");
        orderId = getIntent().getExtras().getString("order_id");
        totalvalue =getIntent().getExtras().getString("totalvalue");
        coupencode = getIntent().getExtras().getString("coupencode");
        token_name = getIntent().getExtras().getString("token_name");
        paymentId = getIntent().getExtras().getString("paymentId");
        rembermeflag = getIntent().getExtras().getBoolean("rememberme");

        ispaymentwebview = true;

        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();


          showProgressDialog();
        threedpaymentwebview = findViewById(R.id.threedpaymentWebview);
        threedpaymentwebview.getSettings().setJavaScriptEnabled(true);
        threedpaymentwebview.getSettings().setUseWideViewPort(true);
        threedpaymentwebview.getSettings().setLoadWithOverviewMode(true);
        threedpaymentwebview.clearCache(true);
        threedpaymentwebview.clearHistory();
        threedpaymentwebview.clearFormData();
        threedpaymentwebview.setWebViewClient(new OrderPaymentWebViewActivity.MyBrowser());

        threedpaymentwebview.loadUrl(url);



    }



    private class MyBrowser extends WebViewClient {


        public void onPageStarted(WebView view, String url, Bitmap favicon) {


            if(!istimerrunning) {

                istimerrunning =true;

                timer.scheduleAtFixedRate(new TimerTask() {

                    @Override
                    public void run() {
                        System.out.println("---------inside timer-------------");

                        //rungetstatusAPI();

                        if(url.contains("paypal.com")){


                            getUpgradedRunStatusAPIforPaypal();
                        }
                        else {
                            getUpgradedRunStatusAPI();
                        }


                    }
                }, 0, 3000);
            }
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
            hideProgressDialog();


           /* timer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    System.out.println("---------inside timer-------------");
                    rungetstatusAPI();

                }
            }, 5, 7000);*/
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(getApplicationContext(), "Error" + description + errorCode, Toast.LENGTH_SHORT).show();
        }


    }


    public void getUpgradedRunStatusAPI()
    {

/*https://upgrade.eoutlet.com/rest/V1/api/checkoutcardresponse
{
      "param": {
          "order_id":"000000036",
          "remember_me": 1,
          "devicetype":"ios"
      }
}
* */

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        Map<Object, Object> map2 = new HashMap<>();
        Map<Object, Object> cardetail = new HashMap<>();
        map1.put("order_id", orderId);
        if(!rembermeflag) {
            map1.put("remember_me", "0");
        }
        else{
            map1.put("remember_me", "1");
        }


        map1.put("devicetype", "android");



        mainparam.put("param", map1);


        Call<WalletPaymentStatus> call = apiService.UpgradeGetStatusAPi(mainparam);


        call.enqueue(new Callback<WalletPaymentStatus>() {
            @Override
            public void onResponse(Call<WalletPaymentStatus> call, Response<WalletPaymentStatus> response) {
/*{"msg":"success","data":[],"order_id":"2000000051"}*/

                if (response.body() != null) {
                    if (response.body().msg.equals("success")) {

                        timer.cancel();
                        ispaymentwebview = false;
                        if (getApplicationContext() != null && MySharedPreferenceClass.getPrefs(getApplicationContext()) != null) {
                            MySharedPreferenceClass.setBedgeCount(getApplicationContext(), 0);
                        }
                        Intent in = new Intent(getApplicationContext(), ThankyouActivity.class);
                        in.putExtra("orderId", orderId);
                        in.putExtra("statusflag", response.body().msg);
                        in.putExtra("totalvalue", totalvalue);
                        in.putExtra("coupencode", coupencode);

                        if (in != null) {
                            if (OrderPaymentWebViewActivity.this != null) {
                                startActivity(in);
                            }
                        }

                        try {
                            Activity activity = OrderPaymentWebViewActivity.this;
                            if (activity != null) {
                                OrderPaymentWebViewActivity.this.finish();
                            }

                        } catch (Exception e) {


                        }
                    }

                else if (response.body().msg.equals("failed")) {
                    //rungetfailedstatusAPI();
                    timer.cancel();
                    istimerrunning=false;
                    timer = null;
                    ispaymentwebview = false;
                    retainstcorder(orderId);

                }
                }




                 else {

                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), userMessage, Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (response.code() == 401) {

                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), userMessage, Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    }


                }
            }

            @Override
            public void onFailure(Call<WalletPaymentStatus> call, Throwable t) {

                Log.e(TAG, t.toString());

                Toast.makeText(getApplicationContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void getUpgradedRunStatusAPIforPaypal()
    {

/*https://upgrade.eoutlet.com/rest/en/V1/api/paypalresponse
{
    "param":{
        "payment_id":"pay_zl5kk7k3jh2udb3eku4o6k4234",
        "order_id":"000000095",
        "devicetype":"ios"
    }
}*/

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        Map<Object, Object> map2 = new HashMap<>();
        Map<Object, Object> cardetail = new HashMap<>();
        map1.put("order_id", orderId);
        map1.put("payment_id", paymentId);
        map1.put("devicetype", "android");
        mainparam.put("param", map1);


        Call<WalletPaymentStatus> call = apiService.UpgradeGetStatusAPiforPaypal(mainparam);


        call.enqueue(new Callback<WalletPaymentStatus>() {
            @Override
            public void onResponse(Call<WalletPaymentStatus> call, Response<WalletPaymentStatus> response) {
                /*{"msg":"success","data":[],"order_id":"2000000051"}*/

                if (response.body() != null) {
                    if (response.body().msg.equals("success")) {

                        timer.cancel();
                        ispaymentwebview = false;
                        if (getApplicationContext() != null && MySharedPreferenceClass.getPrefs(getApplicationContext()) != null) {
                            MySharedPreferenceClass.setBedgeCount(getApplicationContext(), 0);
                        }
                        Intent in = new Intent(getApplicationContext(), ThankyouActivity.class);
                        in.putExtra("orderId", orderId);
                        in.putExtra("statusflag", response.body().msg);
                        in.putExtra("totalvalue", totalvalue);
                        in.putExtra("coupencode", coupencode);

                        if (in != null) {
                            if (OrderPaymentWebViewActivity.this != null) {
                                startActivity(in);
                            }
                        }

                        try {
                            Activity activity = OrderPaymentWebViewActivity.this;
                            if (activity != null) {
                                OrderPaymentWebViewActivity.this.finish();
                            }

                        } catch (Exception e) {


                        }
                    }

                    else if (response.body().msg.equals("failed")) {
                        timer.cancel();
                        istimerrunning=false;
                        timer = null;
                        ispaymentwebview = false;
                        retainstcorder(orderId);

                    }
                }




                else {

                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), userMessage, Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (response.code() == 401) {

                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), userMessage, Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    }


                }
            }

            @Override
            public void onFailure(Call<WalletPaymentStatus> call, Throwable t) {

                Log.e(TAG, t.toString());

                Toast.makeText(getApplicationContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }


    public void rungetfailedstatusAPI() {


        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();


        query.put("order_id", orderId);
        query.put("token_name", token_name);
        query.put("is_wallet", "0");
        query.put("devicetype", "android");

        Call<WalletPaymentStatus> call = apiService.updatecheckoutfailedpaymentstatus(query);
        call.enqueue(new Callback<WalletPaymentStatus>() {
            @Override
            public void onResponse(Call<WalletPaymentStatus> call, Response<WalletPaymentStatus> response) {


                if (response.body() != null) {

                    hideProgressDialog();

                    if (response.body().msg.equals("success")) {


                    } else if (response.body().msg.equals("failed")) {


                        Intent in = new Intent(getApplicationContext(), ThankyouActivity.class);
                        in.putExtra("orderId", orderId);
                        in.putExtra("statusflag", response.body().msg);
                        in.putExtra("totalvalue", totalvalue);
                        in.putExtra("coupencode", coupencode);

                        //in.putExtra("coupencode",coupencode);
                        if (in != null) {
                            startActivity(in);
                        }
                        Activity activity = OrderPaymentWebViewActivity.this;
                        if (activity != null) {
                            OrderPaymentWebViewActivity.this.finish();
                        }



                    }


                }

            }


            @Override
            public void onFailure(Call<WalletPaymentStatus> call, Throwable t) {
                hideProgressDialog();
                // progressDialog.hide();
                Log.e(TAG, t.toString());

                //Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void retainstcorder(String orderid) {


/*"{
    ""param"": {
        ""order_id"": ""000000028"",
        ""devicetype"":""android"",
         ""cart_id"" :""4564""
    }
}"*/
        /*https://upgrade.eoutlet.com/rest/V1/api/stccartretain*/


          showDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> query = new HashMap<>();


        query.put("order_id", orderid);
        query.put("devicetype", "android");
        query.put("cart_id", MySharedPreferenceClass.getQuoteId(getApplicationContext()));

        mainparam.put("param", query);


        Call<StcRetainApi> call = apiService.getRetainorderfromStc(mainparam);
        call.enqueue(new Callback<StcRetainApi>() {
            @Override
            public void onResponse(Call<StcRetainApi> call, Response<StcRetainApi> response) {


                if (response.body() != null) {


                    if (response.body().msg.equals("success")) {
                        Intent in = new Intent(getApplicationContext(), ThankyouActivity.class);
                        in.putExtra("orderId", orderId);
                        in.putExtra("statusflag", "failed");
                        in.putExtra("totalvalue", totalvalue);
                        in.putExtra("coupencode", coupencode);

                        //in.putExtra("coupencode",coupencode);
                        if (in != null) {
                            startActivity(in);
                        }
                        Activity activity = OrderPaymentWebViewActivity.this;
                        if (activity != null) {
                            OrderPaymentWebViewActivity.this.finish();
                        }


                    } else {
                        //Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_SHORT).show();



                    }
                }


            }


            @Override
            public void onFailure(Call<StcRetainApi> call, Throwable t) {
              hideDialog();
                Log.e(ContentValues.TAG, t.toString());

                //Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        istimerrunning=false;
        timer = null;
        ispaymentwebview = false;
      retainstcorder(orderId);

        super.onBackPressed();


    }


}