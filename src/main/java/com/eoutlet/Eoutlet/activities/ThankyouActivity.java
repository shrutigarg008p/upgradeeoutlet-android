package com.eoutlet.Eoutlet.activities;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.fragments.CartFragment;
import com.eoutlet.Eoutlet.fragments.GiftboxFragment;
import com.eoutlet.Eoutlet.fragments.SuccessThankyouFragment;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.GuestUser;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.moe.pushlibrary.MoEHelper;
import com.moengage.core.Properties;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

public class ThankyouActivity extends ParentActivity {
  private   LinearLayout thankmain;
   private  String Locale = " ";
   private  String statusflag = " ";
 private   TextView message, status;
    private String orderId = " ", coupencode = " ";
    private String price = " ";
    private TextView tvorderId;
    private ImageView statusimage;
    private FirebaseAnalytics mFirebaseAnalytics;
    private ReviewManager reviewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
            Locale ="ar";
            setContentView(R.layout.activity_thankyou);
        }
        else{
            setContentView(R.layout.activity_thankyou_english);
            Locale = "en";
        }



        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        orderId = getIntent().getStringExtra("orderId");
        price = getIntent().getStringExtra("totalvalue");
        coupencode = getIntent().getStringExtra("coupencode");

        if (getIntent().getStringExtra("statusflag") != null) {
            statusflag = getIntent().getStringExtra("statusflag");
        }
        tvorderId = (TextView) findViewById(R.id.resultOrderId);
        statusimage = (ImageView) findViewById(R.id.statuImage);
        message = (TextView) findViewById(R.id.acc1);
        status = (TextView) findViewById(R.id.statusflag);

        thankmain = (LinearLayout) findViewById(R.id.thankyoumain);
        tvorderId.setText(orderId);

        MySharedPreferenceClass.setCheckbofflag(this, false);


        getUpgradedguesttoken();


    }

    public void getUpgradedguesttoken() {


        //  showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Call<GuestUser> call = apiService.getUpgradedGuestToken();
        call.enqueue(new Callback<GuestUser>() {
            @Override
            public void onResponse(Call<GuestUser> call, Response<GuestUser> response) {

                if (response.body() != null && response.body().msg != null) {
                    if (response.body().msg.equals("success")) {

                        initUI();
                        //  hideProgressDialog();
                        MySharedPreferenceClass.setMaskkey(getBaseContext(), response.body().maskKey);
                        MySharedPreferenceClass.setCartId(getBaseContext(), response.body().quoteId);
                        getQuoteID(MySharedPreferenceClass.getToken(getApplicationContext()));
                        Log.d("MaskKey---->>>", MySharedPreferenceClass.getMaskkey(getApplicationContext()).toString());
                        Log.d("CArtID---->>>", String.valueOf(MySharedPreferenceClass.getCartId(getApplicationContext())));
                    } else {
                        //  hideProgressDialog();

                        initUI();


                    }
                }

            }


            @Override
            public void onFailure(Call<GuestUser> call, Throwable t) {
                hideProgressDialog();
                Log.e(TAG, t.toString());

            }
        });


    }
    public void getQuoteID(String authtoken){
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        map1.put("customer_id",/*"83188" */MySharedPreferenceClass.getMyUserId(getApplicationContext()));


        // mainparam.put("param",map1);

        Call<String> call = apiService.getQuotebyId("Bearer"+" "+authtoken ,map1);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null) {
                    MySharedPreferenceClass.setQuoteId(getApplicationContext(),response.body().toString());

                    //  Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_SHORT).show();




                    Log.d("Response------->>>>",response.body().toString());



                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, t.toString());

                Toast.makeText(getApplicationContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent in = new Intent(ThankyouActivity.this, MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);
        finish();

    }
    public  void moengagePurchasedEvent(){

        ArrayList<String> catname = new ArrayList();
        catname.add("abc");
        catname.add("def");
/* currency,price,order_status,user_id,order_id,coupon*/
        Properties properties = new Properties();


        properties .addAttribute("currency", MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()))

                .addAttribute("price", price)
       .addAttribute("firstName", MySharedPreferenceClass.getMyFirstNamePref(getApplicationContext()))

                .addAttribute("lastName", MySharedPreferenceClass.getMyLastNamePref(getApplicationContext()))
                .addAttribute("order_status",  statusflag)

                .addAttribute("user_id",MySharedPreferenceClass.getMyUserId(getApplicationContext()))

                .addAttribute("order_id",orderId)
                .addAttribute("coupon", coupencode)
                //.addAttribute("category_name", catname)
                .addAttribute("category_name", MySharedPreferenceClass.getPurchaseCategorynames(getApplicationContext()))
                .addAttribute("category_id", MySharedPreferenceClass.getPurchaseCategoryIds(getApplicationContext()))
                .addAttribute("brand_name", MySharedPreferenceClass.getPurchaseBrandname(getApplicationContext()));


        MoEHelper.getInstance(getApplicationContext()).trackEvent("Purchase", properties);
        Log.e("catename",MySharedPreferenceClass.getPurchaseCategorynames(getApplicationContext()));
        Log.e("cateid",MySharedPreferenceClass.getPurchaseCategoryIds(getApplicationContext()));
        Log.e("brandname", MySharedPreferenceClass.getPurchaseBrandname(getApplicationContext()));
          moengagePaymentInfo();

    }
    public  void moengagePurchasedCategoryEvent(){
        /* currency,price,order_status,user_id,order_id,coupon*/
        Properties properties = new Properties();


        properties.addAttribute("price", price);



        MoEHelper.getInstance(getApplicationContext()).trackEvent("Purchase Product Category", properties);


    }
    public void appsflyer_event_purchase() {
        //Purchase


        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put(AFInAppEventParameterName.PRICE, price);
        eventValue.put(AFInAppEventParameterName.CONTENT_ID, "");
// for multiple product categories, set the param value as: // new String {"221", "124"}
        eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, "");
// for multiple product categories,, set the param value as: new String {"shoes", "pants"}
        eventValue.put(AFInAppEventParameterName.CURRENCY, MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()));

        eventValue.put(AFInAppEventParameterName.QUANTITY, "");
// for multiple product categories, set the param value as: new int {2, 5}
        eventValue.put(AFInAppEventParameterName.RECEIPT_ID, orderId);
        eventValue.put("af_order_id", orderId);
        eventValue.put(AFInAppEventParameterName.REVENUE, price);
        eventValue.put(AFInAppEventParameterName.COUPON_CODE, coupencode);
        AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), AFInAppEventType.PURCHASE, eventValue);
        appsflyer_payment_info();
    }


    public void appsflyer_payment_info() {
        Map<String, Object> eventValue = new HashMap<String, Object>();

        eventValue.put(AFInAppEventParameterName.CUSTOMER_USER_ID, MySharedPreferenceClass.getMyUserId(getApplicationContext()));
        eventValue.put(AFInAppEventParameterName.CURRENCY, MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()));
        eventValue.put(AFInAppEventParameterName.PRICE, price);

        eventValue.put(AFInAppEventParameterName.QUANTITY, " ");

        eventValue.put(AFInAppEventParameterName.PAYMENT_INFO_AVAILIBLE, orderId);


        AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), AFInAppEventType.ADD_PAYMENT_INFO, eventValue);

        firebase_event_purchase();

    }


    public void firebase_event_purchase() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.COUPON, coupencode);
        bundle.putString(FirebaseAnalytics.Param.CURRENCY, MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()));
        if (price != " " && price != null) {
            bundle.putInt(FirebaseAnalytics.Param.VALUE, Integer.valueOf(price));
        }
        bundle.putString(FirebaseAnalytics.Param.TAX, " ");
        bundle.putString(FirebaseAnalytics.Param.SHIPPING, " ");
        bundle.putString(FirebaseAnalytics.Param.TRANSACTION_ID, orderId);

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ECOMMERCE_PURCHASE, bundle);
       moengagePurchasedEvent();

    }

    public  void moengagePaymentInfo(){
        /* currency,price,order_status,user_id,order_id,coupon*/
        Properties properties = new Properties();

        properties .addAttribute("currency", MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()))

                .addAttribute("price", price)



                .addAttribute("user_id",MySharedPreferenceClass.getMyUserId(getApplicationContext()));




        MoEHelper.getInstance(getApplicationContext()).trackEvent("Add Payment Info", properties);


    }

    public void initUI() {


        if (!statusflag.equals(" ")) {

            if (statusflag.equals("failed") || statusflag.equals("cancel")) {

                thankmain.setVisibility(View.VISIBLE);

                if (statusflag.equals("failed")) {
                    if(Locale.equals("ar")) {

                        status.setText("فشل");
                        message.setText("آسف فشل طلبك");
                    }
                    else{
                        status.setText("Failed");
                        message.setText("Sorry, your request failed");
                    }


                } else if (statusflag.equals("cancel")) {

                    if(Locale.equals("ar")) {
                        thankmain.setVisibility(View.VISIBLE);
                        status.setText("طلب ملغي");
                        message.setText("نأسف لقد تم الغاء طلبك");
                    }
                    else{
                        thankmain.setVisibility(View.VISIBLE);
                        status.setText("Request canceled");
                        message.setText("Sorry, your request has been canceled");

                    }


                }


                statusimage.setImageDrawable(getDrawable(R.drawable.ic_cancel));
                statusimage.setVisibility(View.VISIBLE);


            } else {
                statusimage.setVisibility(View.VISIBLE);
                thankmain.setVisibility(View.VISIBLE);
                if (MainActivity.fromcart) {

                    try {
                        reviewManager = ReviewManagerFactory.create(this);
                        //showRateApp();


                        DialogFragment prFrag = new SuccessThankyouFragment();
                        Bundle bund = new Bundle();
                        prFrag.setArguments(bund);
                        prFrag.show(getSupportFragmentManager(), "Thankyou");


                    } catch (Exception e) {


                    }
                }

                //appsflyer_event_purchase();

            }


        } else {
            if (MainActivity.fromcart) {


                //appsflyer_event_purchase();


            }


        }

        appsflyer_event_purchase();
    }




}
