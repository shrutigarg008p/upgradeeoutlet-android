package com.eoutlet.Eoutlet.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.checkout.android_sdk.CheckoutAPIClient;
import com.checkout.android_sdk.Request.CardTokenisationRequest;
import com.checkout.android_sdk.Response.CardTokenisationFail;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.checkout.android_sdk.Utils.Environment;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.activities.OrderPaymentWebViewActivity;
import com.eoutlet.Eoutlet.adpters.RememberCardAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.listener.ViewListener3;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.CardDetail;
import com.eoutlet.Eoutlet.pojo.RememberCardDetail;
import com.eoutlet.Eoutlet.pojo.RememberCardName;
import com.eoutlet.Eoutlet.pojo.UpgardeCheckoutOrderResponse;
import com.eoutlet.Eoutlet.pojo.UpgardeSavedCardDetail;
import com.eoutlet.Eoutlet.pojo.WalletOrderResponse;
import com.eoutlet.Eoutlet.pojo.universalMessage2;
import com.eoutlet.Eoutlet.utility.Constants;
import com.github.dewinjm.monthyearpicker.MonthFormat;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;

import org.json.JSONObject;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletPaymentOptions#newInstance} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class WalletPaymentOptions extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int yearSelected;
    int monthSelected;

    WebView paymentWebview;
    private CardView edtcardinfo;
    private FrameLayout addmoney;
    private TextView amounttext;

    private EditText cardname, cardnumber, dateandmonth, cvv;
    private String fillname, fillcardnumber, filldateandmonth, fillcvv;
    public ParentDialogsListener parentDialogListener;
    private String amount;
    private final int interval = 5000; // 1 Second

    private RecyclerView cardrecycler;
    private RememberCardAdapter cardAdapter;

    private List<RememberCardDetail> carddetail = new ArrayList<>();

    private boolean remembeMeflag = false;
    private ImageView remembermeselect;
    private int selectedcardposition;


    Handler handler = new Handler();
    private View view;

    private ImageView walletpaymentradio;
    private CardView walletpaymentcard;

    private int walletpaumentflag = 1;
    private CardView editcarddetail;

    private String savedcardcvv, tokenname;


    Calendar calendar = Calendar.getInstance();
    MonthYearPickerDialogFragment dialogFragment;
    private String tokennameforcheckout = " ";
    private List<CardDetail> upgradedcarddetail = new ArrayList<>();
    CheckoutAPIClient mCheckoutAPIClient;
    private String Locale = " ";

    private final CheckoutAPIClient.OnTokenGenerated mTokenListener = new CheckoutAPIClient.OnTokenGenerated() {

        @Override
        public void onTokenGenerated(CardTokenisationResponse token) {
            //displayMessage("Success!", token.getId());
            System.out.println("response" + token);
            tokennameforcheckout = token.getId();
            addmoneyupgradeOrder();
            Log.e("tokenId----->>", tokennameforcheckout);


        }

        @Override
        public void onError(CardTokenisationFail error) {
            //displayMessage("Error!", error.getEventId());
        }

        @Override
        public void onNetworkError(VolleyError error) {
            // your network error
        }
    };

    public WalletPaymentOptions() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletPaymentOptions.
     */
    // TODO: Rename and change types and number of parameters
    public static WalletPaymentOptions newInstance(String param1, String param2) {
        WalletPaymentOptions fragment = new WalletPaymentOptions();
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
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
            view = inflater.inflate(R.layout.fragment_wallet_payment_options_english, container, false);
            Locale = "en";
        } else {

            view = inflater.inflate(R.layout.fragment_wallet_payment_options, container, false);
            Locale = "ar";
        }

        MainActivity.fromcart = false;

        amount = getArguments().getString("addamount");
        initUi(view);
        mCheckoutAPIClient = new CheckoutAPIClient(
                getActivity(),
                Constants.CHECKOUT_KEY_LIVE,
                  Environment.LIVE
              //Environment.SANDBOX
        );

        mCheckoutAPIClient.setTokenListener(mTokenListener);

        // getRememberCardName();
        getUpgradedRememberCardName();


        yearSelected = calendar.get(Calendar.YEAR);
        monthSelected = calendar.get(Calendar.MONTH);

        return view;
    }

    private void displayMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void initUi(View view) {
        paymentWebview = view.findViewById(R.id.paymentWebview);
        edtcardinfo = view.findViewById(R.id.edt_card_detail);
        addmoney = view.findViewById(R.id.addmoney);
        amounttext = view.findViewById(R.id.amounttext);

        cardname = view.findViewById(R.id.edtcardname);
        cardnumber = view.findViewById(R.id.edtcardnumber);
        dateandmonth = view.findViewById(R.id.monthandyear);
        cvv = view.findViewById(R.id.cvv);
        walletpaymentcard = view.findViewById(R.id.walletpaymentcard);
        walletpaymentradio = view.findViewById(R.id.walletpaymentradio);
        editcarddetail = view.findViewById(R.id.edt_card_detail);


        remembermeselect = view.findViewById(R.id.remembermeselect);

        remembermeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!remembeMeflag) {
                    remembermeselect.setImageResource(R.drawable.checkboxselected);
                    remembeMeflag = true;
                } else {
                    remembermeselect.setImageResource(R.drawable.checkboxunselected);
                    remembeMeflag = false;
                }


            }
        });


        walletpaymentcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (walletpaumentflag != 1) {

                    walletpaymentradio.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));
                    walletpaumentflag = 1;
                    editcarddetail.setVisibility(View.VISIBLE);
                    initSavedCardRecycler();


                }


            }
        });

        if (Locale.equals("ar")) {
            amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "الاجمالي");
        } else {
            amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));
        }

        dateandmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MonthFormat monthFormat = MonthFormat.LONG; //MonthFormat.LONG or MonthFormat.SHORT


                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.get(Calendar.YEAR);


                Log.d(TAG, "@ thisYear : " + (calendar.get(Calendar.YEAR)));
                Log.d(TAG, "@ thisYear : " + (calendar.get(Calendar.MONTH)));
                Log.d(TAG, "@ thisYear : " + (calendar.get(Calendar.DAY_OF_MONTH)));

                calendar.set(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)), calendar.get(Calendar.DAY_OF_MONTH)); // Set minimum date to show in dialog
                long minDate = calendar.getTimeInMillis();

                calendar.clear();
                //MonthFormat.LONG or MonthFormat.SHORT
                Calendar calendarend = Calendar.getInstance(TimeZone.getDefault());

                calendar.set(calendarend.get(Calendar.YEAR) + 10, (calendarend.get(Calendar.MONTH) + 10), calendarend.get(Calendar.DAY_OF_MONTH) + 10); // Set maximum date to show in dialog
                long maxDate = calendar.getTimeInMillis(); // G


                dialogFragment = MonthYearPickerDialogFragment
                        .getInstance(monthSelected, yearSelected, minDate, maxDate, "", monthFormat);
                dialogFragment.show(getFragmentManager(), null);

                dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int year, int monthOfYear) {
                        // do something
                        Log.e("asds", year + "/" + monthOfYear);
                        int monthofyear = monthOfYear + 1;


                        String actualmonth = String.valueOf(monthofyear);

                        if (actualmonth.toString().length() < 2) {

                            actualmonth = "0" + actualmonth;


                        }

                        String actualyear = String.valueOf(year).substring(String.valueOf(year).length() - 2);

                        String concatstring = actualyear + "/" + actualmonth;


                        dateandmonth.setText(concatstring);


                    }
                });


            }
        });


       /* mCheckoutAPIClient = new CheckoutAPIClient(
                getActivity(),
                "pk_test_33a80189-931c-4a3c-bf96-316b53cb636c",
                Environment.SANDBOX
        );
        mCheckoutAPIClient.setTokenListener(mTokenListener); // pass the callback

*/


        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (walletpaumentflag == 1) {
                    fillname = cardname.getText().toString();
                    fillcardnumber = cardnumber.getText().toString();
                    filldateandmonth = dateandmonth.getText().toString();
                    fillcvv = cvv.getText().toString();

                    boolean isAllConditionFulfilled = true;

                    isAllConditionFulfilled = cardname.getText().toString().trim().length() > 0
                            && cardnumber.getText().toString().trim().length() > 0 &&
                            dateandmonth.getText().toString().trim().length() > 0 &&
                            cardnumber.getText().toString().trim().length() > 0
                            &&
                            cvv.getText().toString().trim().length() > 0;


               /* isAllConditionFulfilled = Util.checkTextViewValidation(cardnumber, "الرجاء إدخال اسم المستخدم")
                        && Util.checkTextViewValidation(cvv, "برجاء كتابة كلمة السر");
*/

                    if (!isAllConditionFulfilled) {
                        if (Locale.equals("ar")) {
                            Toast.makeText(getContext(), "برجاء فحص بيانات البطاقة", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Please check the card information", Toast.LENGTH_SHORT).show();
                        }

                        return;
                    }


                    //addmoneyupgradeOrder();
                    placeorderwithcheckout();
                } else if (walletpaumentflag == 2) {


                    if (!savedcardcvv.isEmpty()) {
                        //addmoneyforsavedcard();
                        addmoneyupgradeOrder();
                    } else {

                        if (Locale.equals("ar")) {
                            Toast.makeText(getContext(), "برجاء فحص بيانات البطاقة", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Please check the card information", Toast.LENGTH_SHORT).show();
                        }


                    }
                } else {
                    if (Locale.equals("ar")) {
                        Toast.makeText(getContext(), "برجاء اختيار طريقة الدفع للاستمرار", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Please choose a payment method to continue", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });


    }

    public void placeorderwithcheckout() {
        parentDialogListener.showProgressDialog();

        // if (formValidationOutcome()) {
        mCheckoutAPIClient.generateToken(
                new CardTokenisationRequest(
                        fillcardnumber,
                        fillname,
                        filldateandmonth.split("/")[1].trim(),
                        filldateandmonth.split("/")[0].trim(),
                        fillcvv


                )
        );
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


        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(getContext(), "Error" + description + errorCode, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //Toast.makeText(getContext(),"dsfdsfds",Toast.LENGTH_SHORT).show();
        try {

            parentDialogListener = (ParentDialogsListener) activity;

        } catch (ClassCastException e) {

            throw new ClassCastException(activity.toString()
                    + " Activity's Parent should be Parent Activity");
        }


    }


    public void clearFragmentBackStack() {
        FragmentManager fm = getFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }


    }


    public void initSavedCardRecycler() {
        cardrecycler = view.findViewById(R.id.cardrecycler);

        cardrecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));


        cardrecycler.setItemAnimator(new DefaultItemAnimator());
        // use a linear layout manager


        // specify an adapter (see also next example)
        cardAdapter = new RememberCardAdapter(getContext(), upgradedcarddetail, selectedcardposition, new ViewListener3() {
            @Override
            public void onClick(int position, View view, String cvv) {
                int id = view.getId();

                savedcardcvv = cvv;
                tokenname = upgradedcarddetail.get(position).gatewayToken;


                switch (id) {

                    case R.id.remembercardclick://button for message

                        walletpaumentflag = 2;

                        editcarddetail.setVisibility(View.GONE);

                        walletpaymentradio.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));

                        break;
                }
            }
        }


        );
        cardrecycler.setAdapter(cardAdapter);
        cardrecycler.setNestedScrollingEnabled(false);
    }

    public void addmoneyupgradeOrder() {
        /*https://upgrade.eoutlet.com/rest/en/V1/api/addmoneytowallet */

/*{
  "param":{
          "price":"100.00",
          "customer_id":"83188"
  }
}*/

        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        map1.put("price", amount);
        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        mainparam.put("param", map1);


        Call<universalMessage2> call = apiService.doupgradedwalletOrder(mainparam);


        call.enqueue(new Callback<universalMessage2>() {
            @Override
            public void onResponse(Call<universalMessage2> call, Response<universalMessage2> response) {


                if (response.body() != null) {

                    getTokenID();


                } else {

                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();

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
                                Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    }


                }
            }

            @Override
            public void onFailure(Call<universalMessage2> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void checkoutcardPaymentInitiation(String orderid) {



      /*  https://upgrade.eoutlet.com/rest/V1/api/checkoutcardpayment
      * {
    "param": {
        "order_id": "15",
        "amount":"12386.25",
        "currency":"SAR",
        "card_token":"src_ojhiimwxtmhefkwxiexx6n5lm4",
        "card_detail":{
            "number":"4111111111111111",
            "date_month":"04/22",
            "securitycode":"100"
        }
    }
}*/

       /* initialorder.put("amount", amounttext.getText().toString().split(" ")[1]);
        initialorder.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        initialorder.put("token_name", tokennameforcheckout);

          paymentdetail.put("name", fillname);
        paymentdetail.put("number", fillcardnumber);
        paymentdetail.put("securitycode", fillcvv);
        paymentdetail.put("expiry", filldateandmonth.replace("/", ""));*/


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        Map<Object, Object> map2 = new HashMap<>();
        Map<Object, Object> cardetail = new HashMap<>();
        map1.put("order_id", orderid);
        map1.put("amount", amounttext.getText().toString().split(" ")[1]);
        map1.put("currency", MySharedPreferenceClass.getSelectedCurrencyName(view.getContext()));
        map1.put("card_token", tokennameforcheckout);
        map2.put("number", fillcardnumber);
        map2.put("date_month", filldateandmonth.replace("/", ""));
        map2.put("securitycode", fillcvv);


        map1.put("card_detail", map2);


        mainparam.put("param", map1);


        Call<UpgardeCheckoutOrderResponse> call = apiService.initiateCheckoutOrder(mainparam);


        call.enqueue(new Callback<UpgardeCheckoutOrderResponse>() {
            @Override
            public void onResponse(Call<UpgardeCheckoutOrderResponse> call, Response<UpgardeCheckoutOrderResponse> response) {


                if (response.body() != null) {
                    if (response.body().msg.equals("success")) {
                        parentDialogListener.hideProgressDialog();

                        Intent in = new Intent(getContext(), OrderPaymentWebViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("order_id", response.body().orderId);
                        bundle.putString("url", response.body().redirect_link);


                        bundle.putString("totalvalue", amounttext.getText().toString().split(" ")[1]);
                        bundle.putString("coupencode", " ");
                        bundle.putString("token_name", tokennameforcheckout);

                        in.putExtras(bundle);
                        startActivity(in);

                        //  Toast.makeText(getContext(), "Order Done", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), response.body().data, Toast.LENGTH_SHORT).show();

                    }


                } else {

                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();

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
                                Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    }


                }
            }

            @Override
            public void onFailure(Call<UpgardeCheckoutOrderResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void UpgradePlaceOrderforWallet() {

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> addresses = new HashMap<>();

        map1.put("method", "checkoutcom_card_payment");
        map2.put("region", MySharedPreferenceClass.getCountryId(getApplicationContext()));

        ArrayList<String> street = new ArrayList<>();
        street.add(MySharedPreferenceClass.getStreetname(getContext()));
        map2.put("street", street);


        map2.put("country_id", MySharedPreferenceClass.getCountryId(getContext()));

        map2.put("city", MySharedPreferenceClass.getCityName(getContext()));
        map2.put("firstname", MySharedPreferenceClass.getfirstAdressname(getContext()));
        map2.put("lastname", MySharedPreferenceClass.getlastAdressname(getContext()));
        map2.put("customer_id", Integer.parseInt(MySharedPreferenceClass.getMyUserId(getContext())));
        map2.put("email", MySharedPreferenceClass.getEmail(getContext()));
        map2.put("telephone", MySharedPreferenceClass.getAdressphone(getContext()));
        addresses.put("billing_address", map2);

        addresses.put("paymentMethod", map1);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getCustomerToken(getContext()));
        headers.put("Cookie", "Bearer" + " " + MySharedPreferenceClass.getCustomerToken(getContext()));

        Call<String> call = apiService.createcustomerOrder(headers, addresses);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();

                    if (walletpaumentflag == 1) {

                        checkoutcardPaymentInitiation(response.body().toString());
                    } else if (walletpaumentflag == 2) {


                        savecardcheckoutcardPaymentInitiation(response.body().toString());
                    }

                } else {

                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();

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
                                Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    }


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void getTokenID() {


        //parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        map1.put("email", MySharedPreferenceClass.getEmail(getContext()));
        map1.put("password", MySharedPreferenceClass.getMyPassword(getContext()));

        mainparam.put("param", map1);


        Call<String> call = apiService.createcustomertoken(mainparam);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    MySharedPreferenceClass.setCustomerToken(getContext(), response.body().toString());
                    UpgradePlaceOrderforWallet();


                } else {

                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();

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
                                Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    }


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    public void getUpgradedRememberCardName() {



        /*http://upgrade.eoutlet.com/rest/en/V1/api/storedsavecards/83188*/


        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));


        /*https://upgrade.eoutlet.com/rest/en/V1/api/customercart/83188*/


        Call<UpgardeSavedCardDetail> call = apiService.getUpgradedSavedCard(Constants.UPGRADED_HOST_LINK + "rest/en/V1/api/storedsavecards/" + MySharedPreferenceClass.getMyUserId(getContext()));
        call.enqueue(new Callback<UpgardeSavedCardDetail>() {
            @Override
            public void onResponse(Call<UpgardeSavedCardDetail> call, Response<UpgardeSavedCardDetail> response) {
                parentDialogListener.hideProgressDialog();

                upgradedcarddetail = response.body().data;


                initSavedCardRecycler();
            }

            @Override
            public void onFailure(Call<UpgardeSavedCardDetail> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void savecardcheckoutcardPaymentInitiation(String orderid) {



      /*  https://upgrade.eoutlet.com/rest/V1/api/checkoutcardpayment
      * {
    "param": {
        "order_id": "15",
        "amount":"12386.25",
        "currency":"SAR",
        "card_token":"src_ojhiimwxtmhefkwxiexx6n5lm4",
        "card_detail":{
            "number":"4111111111111111",
            "date_month":"04/22",
            "securitycode":"100"
        }
    }
}*/

       /* initialorder.put("amount", amounttext.getText().toString().split(" ")[1]);
        initialorder.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        initialorder.put("token_name", tokennameforcheckout);

          paymentdetail.put("name", fillname);
        paymentdetail.put("number", fillcardnumber);
        paymentdetail.put("securitycode", fillcvv);
        paymentdetail.put("expiry", filldateandmonth.replace("/", ""));*/
        /*paymentdetail.put("name", "");
        paymentdetail.put("number", "");
        paymentdetail.put("securitycode", savedcardcvv);
        paymentdetail.put("expiry", "");

        initialorder.put("card_detail", paymentdetail);*/


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        Map<Object, Object> map2 = new HashMap<>();
        Map<Object, Object> cardetail = new HashMap<>();
        map1.put("order_id", orderid);
        map1.put("amount", amounttext.getText().toString().split(" ")[1]);
        map1.put("currency", "SAR");
        map1.put("card_token", tokenname);
        map2.put("number", "");
        map2.put("date_month", "");
        map2.put("securitycode", savedcardcvv);


        map1.put("card_detail", map2);


        mainparam.put("param", map1);


        Call<UpgardeCheckoutOrderResponse> call = apiService.initiateCheckoutOrder(mainparam);


        call.enqueue(new Callback<UpgardeCheckoutOrderResponse>() {
            @Override
            public void onResponse(Call<UpgardeCheckoutOrderResponse> call, Response<UpgardeCheckoutOrderResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();

                    Intent in = new Intent(getContext(), OrderPaymentWebViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("order_id", response.body().orderId);
                    bundle.putString("url", response.body().redirect_link);


                    bundle.putString("totalvalue", amounttext.getText().toString().split(" ")[1]);
                    bundle.putString("coupencode", " ");
                    bundle.putString("token_name", tokennameforcheckout);

                    in.putExtras(bundle);
                    startActivity(in);

                    Toast.makeText(getContext(), "Order Done", Toast.LENGTH_SHORT).show();

                } else {

                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();

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
                                Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    }


                }
            }

            @Override
            public void onFailure(Call<UpgardeCheckoutOrderResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}