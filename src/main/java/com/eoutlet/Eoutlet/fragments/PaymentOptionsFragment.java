package com.eoutlet.Eoutlet.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.checkout.android_sdk.CheckoutAPIClient;
import com.checkout.android_sdk.Request.CardTokenisationRequest;
import com.checkout.android_sdk.Response.CardTokenisationFail;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.checkout.android_sdk.Utils.Environment;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.OrderPaymentWebViewActivity;
import com.eoutlet.Eoutlet.activities.ThankyouActivity;
import com.eoutlet.Eoutlet.adpters.RememberCardAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.listener.CartListener;
import com.eoutlet.Eoutlet.listener.ViewListener3;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.ApplyWalletResult;
import com.eoutlet.Eoutlet.pojo.CardDetail;
import com.eoutlet.Eoutlet.pojo.CheckoutOrderResponse2;
import com.eoutlet.Eoutlet.pojo.GetCategoryCode;
import com.eoutlet.Eoutlet.pojo.GetOrderdetail;
import com.eoutlet.Eoutlet.pojo.GetOrderdetailforCod;
import com.eoutlet.Eoutlet.pojo.GetPaymentInfo;
import com.eoutlet.Eoutlet.pojo.GetShippingMethod;
import com.eoutlet.Eoutlet.pojo.GetTamaraCheckoutResponse;
import com.eoutlet.Eoutlet.pojo.OrderResponseStc;
import com.eoutlet.Eoutlet.pojo.PaymentMethod;
import com.eoutlet.Eoutlet.pojo.PaypalPaymentResponse;
import com.eoutlet.Eoutlet.pojo.RememberCardDetail;
import com.eoutlet.Eoutlet.pojo.StcRetainApi;
import com.eoutlet.Eoutlet.pojo.TamaraOrderResponse;
import com.eoutlet.Eoutlet.pojo.TamaraPaymentType;
import com.eoutlet.Eoutlet.pojo.TamaraPaymentUrlResponse;
import com.eoutlet.Eoutlet.pojo.UpgardeCheckoutOrderResponse;
import com.eoutlet.Eoutlet.pojo.UpgardeSavedCardDetail;
import com.eoutlet.Eoutlet.pojo.UpgradedCartItems;
import com.eoutlet.Eoutlet.pojo.UpgradedItem;
import com.eoutlet.Eoutlet.pojo.UpgradedWalletHistory;
import com.eoutlet.Eoutlet.pojo.ViewCartData;
import com.eoutlet.Eoutlet.pojo.WalletResponse;
import com.eoutlet.Eoutlet.utility.Constants;
import com.eoutlet.Eoutlet.utility.Util;
import com.github.dewinjm.monthyearpicker.MonthFormat;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.tamara.sdk.PaymentResult;
import co.tamara.sdk.TamaraPayment;
import co.tamara.sdk.TamaraPaymentHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentOptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 4658 5840 9000 0001	01/99 (any future date)	257
 */


@RequiresApi(api = Build.VERSION_CODES.N)
public class PaymentOptionsFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String carrier_code, method_code;
    private List<ViewCartData> getdata = new ArrayList<>();
    WebView paymentWebview;
    private String coupencode = " ", shippingmethod, orderId, amount, codelabel;
    private ImageView remembermeselect;
    public ParentDialogsListener parentDialogListener;
    private EditText edittext_stcMobile;
    private List<UpgradedItem> upgradedcartData;
    private String TamaraInternalOrderId;
    Activity thiscontext;
    private String TamaraOrderId;
    private String TamarincrementId;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int yearSelected;
    int monthSelected;
    int paylatermaxamount,paylaterminamount,payinstallmentmaxamount,payinstallmentminamount;

    private ImageView walletcheckbox;
    private boolean walletaddflag = false;
    private int selectpaymentflag = 99;
    private CardView cardradiobutton, codradiobutton, leftbalancecard, tamarainstallmentscard, stcButton, paypalbutton, walletcardview;
    private RelativeLayout callLayout, stcpaymain;
    private ImageView cardradiobtn, codradiobtn, stcRadiobutton, paypalradiobutton, tamararadiobtn;
    private TextView amounttext, codcharges, walletamount, leftwalletamount;
    private List<RememberCardDetail> carddetail = new ArrayList<>();

    private List<CardDetail> upgradedcarddetail = new ArrayList<>();
    private FrameLayout confirmorder;
    private String codamount;
    private int shippingCharge;
    private EditText cardname, cardnumber, dateandmonth, cvv;
    private boolean is_partialpayment = false;

    private boolean selectesavedcard = false;
    private boolean isgiftwrap = false;

    private CardView edt_card_detail;
    private int freshwalletAmount;
    private boolean remembermeflag = false;
    private RecyclerView cardrecycler;
    private RememberCardAdapter cardAdapter;
    private LinearLayout cardrecyclerlayout, cvvlayout;
    View view;
    private String fillname, fillcardnumber, filldateandmonth, fillcvv, savedcardcvv, tokenname;

    private int selectedcardposition = 99;
    private CartListener cartreference;
    Calendar calendar = Calendar.getInstance();
    MonthYearPickerDialogFragment dialogFragment;
    private CardView tamaraCardClcik;


    private String tokennameforcheckout = " ";

    CheckoutAPIClient mCheckoutAPIClient;
    String Locale = " ";
    String TamarathreedigitId;

    private List<PaymentMethod> paymentMethods;
    private List<String> paymentsmethodcode = new ArrayList<>();

    private TextView stctext, paypaltext, Tamaratext, Tamarainstallment;
    private Toolbar toolbar;
    private ImageView backarrow, tamarainstallmentradiobtn;

    boolean isinstallationtamaraflag = false;


    private final CheckoutAPIClient.OnTokenGenerated mTokenListener = new CheckoutAPIClient.OnTokenGenerated() {

        @Override
        public void onTokenGenerated(CardTokenisationResponse token) {
            //displayMessage("Success!", token.getId());

            System.out.println("response" + token);
            tokennameforcheckout = token.getId();


            //placeorderforcheckout();
            UpgradePlaceOrderforCheckout();


        }

        @Override
        public void onError(CardTokenisationFail error) {
            //displayMessage("Error!", error.getEventId());
            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNetworkError(VolleyError error) {
            // your network error
        }
    };

    public PaymentOptionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentOptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentOptionsFragment newInstance(String param1, String param2) {
        PaymentOptionsFragment fragment = new PaymentOptionsFragment();
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

    /* public PaymentOptionsFragment(CartFragment cl) {

        cartreference = cl;


    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_payment_options, container, false);
        } else {
            Locale = "en";
            view = inflater.inflate(R.layout.fragment_payment_options_english, container, false);
            /*  logo.setImageDrawable(getContext().getDrawable(R.drawable.logo_english_black));*/
        }
        initUi(view);
        // getCartData();
        yearSelected = calendar.get(Calendar.YEAR);
        monthSelected = calendar.get(Calendar.MONTH);
     //   thiscontext = container.getContext();
        return view;
    }

    public void initUi(View view) {
        callLayout = view.findViewById(R.id.callLayout);
        stcpaymain = view.findViewById(R.id.stcPaymain);
        paymentWebview = view.findViewById(R.id.paymentWebview);
        walletcheckbox = view.findViewById(R.id.walletcheckbox);
        cardradiobutton = view.findViewById(R.id.cardradiobutton);
        walletcardview = view.findViewById(R.id.walletcardview);
        codradiobutton = view.findViewById(R.id.codradiobutton);
        tamarainstallmentscard = view.findViewById(R.id.tamarainstallmentscard);
        tamarainstallmentradiobtn = view.findViewById(R.id.tamarainstallmentradiobtn);


        cardradiobtn = view.findViewById(R.id.cardradiobtn);
        codradiobtn = view.findViewById(R.id.codradiobtn);
        edt_card_detail = view.findViewById(R.id.edt_card_detail);
        amounttext = view.findViewById(R.id.checkoueamounttext);
        confirmorder = view.findViewById(R.id.checkoutconfirmorder);
        codcharges = view.findViewById(R.id.codcharges);
        walletamount = view.findViewById(R.id.walletamount);
        leftwalletamount = view.findViewById(R.id.leftwalletamount);

        stcRadiobutton = view.findViewById(R.id.stcRadiobutton);
        paypalradiobutton = view.findViewById(R.id.paypalRadiobutton);
        tamararadiobtn = view.findViewById(R.id.tamararadiobtn);
        stcButton = view.findViewById(R.id.stcButton);
        paypalbutton = view.findViewById(R.id.paypalButton);

        edittext_stcMobile = view.findViewById(R.id.edittext_stcMobile);

        cardname = view.findViewById(R.id.edtcardname);
        cardnumber = view.findViewById(R.id.edtcardnumber);
        dateandmonth = view.findViewById(R.id.monthandyear);
        cvv = view.findViewById(R.id.cvv);
        remembermeselect = view.findViewById(R.id.remembermeselect);
        cardrecyclerlayout = view.findViewById(R.id.cardrecyclerlayout);
        cvvlayout = view.findViewById(R.id.cvvlayout);
        tamaraCardClcik = view.findViewById(R.id.tamaracardclick);
        stctext = view.findViewById(R.id.stcAmount);
        paypaltext = view.findViewById(R.id.paypaltext);
        Tamaratext = view.findViewById(R.id.Tamaratext);
        Tamarainstallment = view.findViewById(R.id.Tamarainstallment);
        toolbar = view.findViewById(R.id.toolbar);
        backarrow = toolbar.findViewById((R.id.backarrow));
        coupencode = getArguments().getString("coupencode");



        shippingmethod = getArguments().getString("shippingmethod");
        amount = getArguments().getString("amount");
        codamount = getArguments().getString("codcharges");
        shippingCharge = getArguments().getInt("shippingcharge");
        isgiftwrap = getArguments().getBoolean("wrappingflag");
        codelabel = getArguments().getString("codelabel");
        paymentMethods = (List<PaymentMethod>) getArguments().getSerializable("paymentmethods");
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        /*for (int i = 0; i < paymentMethods.size(); i++) {
            paymentsmethodcode.add(paymentMethods.get(i).code);

            System.out.println(paymentMethods.get(i).title + " " + "title");
            System.out.println(paymentMethods.get(i).code + " " + "code");
            if(paymentMethods.get(i).code.equalsIgnoreCase("stcpay")){
                stctext.setText(paymentMethods.get(i).title);
            }
            if(paymentMethods.get(i).code.equalsIgnoreCase("checkout_paypal")){
               paypaltext.setText(paymentMethods.get(i).title);
            }
            if(paymentMethods.get(i).code.equalsIgnoreCase("tamara_pay_later")){
                Tamaratext.setText(paymentMethods.get(i).title);
            }
            if(paymentMethods.get(i).code.equalsIgnoreCase("tamara_pay_later")){
                Tamaratext.setText(paymentMethods.get(i).title);
            }
            if(paymentMethods.get(i).code.equalsIgnoreCase("tamara_pay_by_instalments")){
                Tamarainstallment.setText(paymentMethods.get(i).title);
            }

        }*/

/*

        if(!paymentsmethodcode.contains("checkoutcom_card_payment")){
            cardradiobutton.setVisibility(View.GONE);
        }
        else{

        }
        if(!paymentsmethodcode.contains("wallet")){
            walletcardview.setVisibility(View.GONE);
        }

        if(!paymentsmethodcode.contains("cashondelivery")){
           codradiobutton.setVisibility(View.GONE);
        }
        if(!paymentsmethodcode.contains("stcpay")){
            stcButton.setVisibility(View.GONE);
        }
        else{




        }
        if(!paymentsmethodcode.contains("checkout_paypal")){
            paypalbutton.setVisibility(View.GONE);
        }
        if(!paymentsmethodcode.contains("tamara_pay_later")){
            tamaraCardClcik.setVisibility(View.GONE);
        }

        if(!paymentsmethodcode.contains("tamara_pay_by_instalments")){
            tamarainstallmentscard.setVisibility(View.GONE);
        }

        if(!MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()).equals("SAR")){
           tamaraCardClcik.setVisibility(View.GONE);
        }
*/

        if (!MySharedPreferenceClass.getCountryId(getContext()).equalsIgnoreCase("SA") || isgiftwrap || Integer.parseInt(amount) > 3000) {

            codradiobutton.setVisibility(View.GONE);

        }
        if (!MySharedPreferenceClass.getCountryId(getContext()).equalsIgnoreCase("SA")) {

            stcButton.setVisibility(View.GONE);


        }


        int subtotal = Math.round((Float.parseFloat(amount) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext())));


        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {

            amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "إجمالي الطلب");

        } else {

            amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

        }


        //     if (codelabel != null) {
        //codcharges.setText(codelabel);
        //}


        int mainwalletamount = Math.round(freshwalletAmount * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));


        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
            walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + mainwalletamount + " " + "- المحفظة");
        } else {
            walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + mainwalletamount);

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


        mCheckoutAPIClient = new CheckoutAPIClient(
                getActivity(),
                Constants.CHECKOUT_KEY_LIVE,
               Environment.LIVE
               //  Environment.SANDBOX
        );
        mCheckoutAPIClient.setTokenListener(mTokenListener); // pass the callback


        remembermeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!remembermeflag) {
                    remembermeselect.setImageResource(R.drawable.checkboxselected);
                    remembermeflag = true;
                } else {
                    remembermeselect.setImageResource(R.drawable.checkboxunselected);
                    remembermeflag = false;
                }


            }
        });


        getUpgradeCartData();


        confirmorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.checkConnection(getApplicationContext())) {
                    Util.preventTwoClick(confirmorder);

                    System.out.println("clicked");

                    fillname = cardname.getText().toString();
                    fillcardnumber = cardnumber.getText().toString();
                    filldateandmonth = dateandmonth.getText().toString();
                    fillcvv = cvv.getText().toString();


                    if (selectpaymentflag == 1) {
                        boolean isAllConditionFulfilled = true;

                        isAllConditionFulfilled = cardname.getText().toString().trim().length() > 0
                                && cardnumber.getText().toString().trim().length() > 0 &&
                                dateandmonth.getText().toString().trim().length() > 0 &&
                                cardnumber.getText().toString().trim().length() > 0
                                &&
                                cvv.getText().toString().trim().length() > 0;

                        if (!isAllConditionFulfilled) {
                            if (Locale.equals("ar")) {

                                Toast.makeText(getContext(), "برجاء فحص بيانات البطاقة", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Please check the card Detail", Toast.LENGTH_SHORT).show();
                            }
                            return;

                        }


                        placeorderwithcheckout();


                    } else if (selectpaymentflag == 2) {


                        UpgradePlaceOrderforCod();


                    } else if (selectpaymentflag == 0) {
                        if (freshwalletAmount >= Math.round(Float.parseFloat(amount))) {


                            UpgradePlaceOrderforWallet();


                        } else {
                            if (Locale.equals("ar")) {
                                Toast.makeText(getContext(), "برجاء اختيار طريقة الدفع للاستمرار", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "Please choose a payment method to continue", Toast.LENGTH_LONG).show();
                            }

                        }

                    } else if (selectpaymentflag == 3) {
                        if (!savedcardcvv.isEmpty()) {


                            UpgradePlaceOrderforCheckout();

                        } else {
                            if (Locale.equals("ar")) {
                                Toast.makeText(getContext(), "ادخل الكود (cvv) ", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "Enter the code (cvv)", Toast.LENGTH_LONG).show();
                            }

                        }


                    } else if (selectpaymentflag == 4) {

                        String trimstcNumber = edittext_stcMobile.getText().toString().trim();

                        if (!MySharedPreferenceClass.getMobileNumber(getContext()).equals("")) {
                            if (trimstcNumber.equals("") /*|| trimstcNumber.length() != MySharedPreferenceClass.getMobileNumber(getContext()).length()*/) {

                                if (Locale.equals("ar")) {
                                    Toast.makeText(view.getContext(), "من فضلك أدخل رقم الجوال", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(view.getContext(), "Please enter the mobile number", Toast.LENGTH_SHORT).show();

                                }


                                return;

                            }
                        }


                        UpgradeGenerateOrder();

                    } else if (selectpaymentflag == 5) {

                        UpgradePlaceOrderforPaypal();


                    } else if (selectpaymentflag == 6) {

                        TamaraPayment.Companion.initialize(Constants.AUTH_TOKEN, Constants.TAMARA_API_URL, Constants.NOTIFICATION_WEB_HOOK_URL);


                        UpgradePlaceOrderforTamara();


                    } else if (selectpaymentflag == 7) {
                       TamaraPayment.Companion.initialize(Constants.AUTH_TOKEN, Constants.TAMARA_API_URL, Constants.NOTIFICATION_WEB_HOOK_URL);

                        UpgradePlaceOrderforTamara();


                    } else {
                        if (Locale.equals("ar")) {
                            Toast.makeText(getContext(), "رصيدك لايكفي برجاء اختيار طريقة دفع اضافية لسداد باقي قيمة الطلب", Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(getContext(), "Please choose a  payment method for continue", Toast.LENGTH_LONG).show();
                        }

                    }

                } else {
                    if (Locale.equals("en")) {
                        Toast.makeText(getApplicationContext(), "Please Check your Internet Connection and try again", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "يرجى التحقق من اتصالك بالإنترنت وحاول مرة أخرى", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        tamarainstallmentscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isinstallationtamaraflag = true;
                if(coupencode!=" ") {
                    openCoupenRemoveDialog();
                }
                else{
                    onselectTamaraInstallments();

                }

                /*initSavedCardRecycler();
                selectpaymentflag = 7;


                if (amounttext.getText().toString().split(" ")[1].equals("0")) {

                    walletcheckbox.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                    walletaddflag = false;
                    is_partialpayment = false;


                    int mainwalletamount = Math.round(Float.parseFloat(MySharedPreferenceClass.getWalletamount(getContext())) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                        walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()) + " " + "-المحفظة");
                    } else {
                        walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()));

                    }


                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                        amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "إجمالي الطلب");

                    } else {
                        amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                    }


                    leftwalletamount.setVisibility(View.GONE);

                } else {
                    if (walletaddflag) {
                        int balance = Math.round(Float.parseFloat(amount)) - freshwalletAmount;

                        //int subtotal = Math.round((balance) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                            amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + balance + " " + "إجمالي الطلب");

                        } else {
                            amounttext.setText("Total" + " " + balance + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                        }


                    } else {
                        //int subtotal = Math.round(Float.parseFloat(amount) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                            amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "إجمالي الطلب");

                        } else {
                            amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                        }

                    }

                }
                paypalradiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                stcRadiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                tamarainstallmentradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));


                if (edt_card_detail.getVisibility() == View.VISIBLE) {
                    edt_card_detail.setVisibility(View.GONE);
                }
                selectedcardposition = 0;
*/
            }


        });


        tamaraCardClcik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isinstallationtamaraflag = false;

                if(coupencode!=" ") {
                    openCoupenRemoveDialog();
                }
                else{

                    onselectTamaraLater();
                }

                /*initSavedCardRecycler();
                selectpaymentflag = 6;


                if (amounttext.getText().toString().split(" ")[1].equals("0")) {

                    walletcheckbox.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                    walletaddflag = false;
                    is_partialpayment = false;


                    int mainwalletamount = Math.round(Float.parseFloat(MySharedPreferenceClass.getWalletamount(getContext())) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                        walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()) + " " + "-المحفظة");
                    } else {
                        walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()));

                    }


                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                        amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "إجمالي الطلب");

                    } else {
                        amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                    }


                    leftwalletamount.setVisibility(View.GONE);

                } else {
                    if (walletaddflag) {
                        int balance = Math.round(Float.parseFloat(amount)) - freshwalletAmount;


                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                            amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + balance + " " + "إجمالي الطلب");

                        } else {
                            amounttext.setText("Total" + " " + balance + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                        }


                    } else {

                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                            amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "إجمالي الطلب");

                        } else {
                            amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                        }

                    }

                }
                paypalradiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                stcRadiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));
                tamarainstallmentradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                if (edt_card_detail.getVisibility() == View.VISIBLE) {
                    edt_card_detail.setVisibility(View.GONE);
                }
                selectedcardposition = 0;*/

            }


        });


        paypalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSavedCardRecycler();
                selectpaymentflag = 5;


                if (amounttext.getText().toString().split(" ")[1].equals("0")) {

                    walletcheckbox.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                    walletaddflag = false;
                    is_partialpayment = false;


                    int mainwalletamount = Math.round(Float.parseFloat(MySharedPreferenceClass.getWalletamount(getContext())) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                        walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()) + " " + "-المحفظة");
                    } else {
                        walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()));

                    }


                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                        amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "إجمالي الطلب");

                    } else {
                        amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                    }


                    leftwalletamount.setVisibility(View.GONE);

                } else {
                    if (walletaddflag) {
                        int balance = Math.round(Float.parseFloat(amount)) - freshwalletAmount;

                        //int subtotal = Math.round((balance) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                            amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + balance + " " + "إجمالي الطلب");

                        } else {
                            amounttext.setText("Total" + " " + balance + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                        }


                    } else {
                        //int subtotal = Math.round(Float.parseFloat(amount) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                            amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "إجمالي الطلب");

                        } else {
                            amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                        }

                    }

                }
                paypalradiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));
                stcRadiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                tamarainstallmentradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));

                if (edt_card_detail.getVisibility() == View.VISIBLE) {
                    edt_card_detail.setVisibility(View.GONE);
                }
                selectedcardposition = 0;
                checkTamaraVisibility();
            }
        });
        stcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSavedCardRecycler();
                selectpaymentflag = 4;


                setEditTextMaxLength(edittext_stcMobile, 10);

                // }
                callLayout.setVisibility(View.VISIBLE);


                if (amounttext.getText().toString().split(" ")[1].equals("0")) {

                    walletcheckbox.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                    walletaddflag = false;
                    is_partialpayment = false;


                    int mainwalletamount = Math.round(Float.parseFloat(MySharedPreferenceClass.getWalletamount(getContext())) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));

                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                        walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()) + " " + "-المحفظة");
                    } else {
                        walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()));

                    }
                    //walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + mainwalletamount + " " + "-المحفظة");

                    /*if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                        walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + mainwalletamount + " " + "- المحفظة");
                    } else {
                        walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + mainwalletamount);

                    }*/

                    // int subtotal = Math.round((Float.parseFloat(amount) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext())));
                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                        amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "إجمالي الطلب");

                    } else {
                        amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                    }

                    leftwalletamount.setVisibility(View.GONE);

                } else {
                    if (walletaddflag) {
                        int balance = Math.round(Float.parseFloat(amount)) - freshwalletAmount;

                        //  int subtotal = Math.round((balance) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                            amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + balance + " " + "إجمالي الطلب");

                        } else {
                            amounttext.setText("Total" + " " + balance + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                        }

                    } else {
                        // int subtotal = Math.round(Float.parseFloat(amount) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                            amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "إجمالي الطلب");

                        } else {
                            amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                        }

                    }

                }
                tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                tamarainstallmentradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                paypalradiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                stcRadiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));
                cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                if (edt_card_detail.getVisibility() == View.VISIBLE) {
                    edt_card_detail.setVisibility(View.GONE);
                }

                selectedcardposition = 0;

                checkTamaraVisibility();
            }


        });


        cardradiobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                if (walletaddflag == true && amounttext.getText().toString().split(" ")[1].equals("0") && selectpaymentflag != 1) {

                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                        walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()) + " " + "-المحفظة");
                    } else {
                        walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()));

                    }


                    walletcheckbox.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                    leftwalletamount.setVisibility(View.GONE);
                    cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));
                    walletaddflag = false;
                    is_partialpayment = false;
                    selectedcardposition = 0;
                    initSavedCardRecycler();
                }

                if (selectpaymentflag != 1) {


                    if (!walletaddflag) {

                        // Toast.makeText(getContext(),walletaddflag+"inside",Toast.LENGTH_SHORT).show();
                        if (callLayout.getVisibility() == View.VISIBLE) {
                            callLayout.setVisibility(View.GONE);
                        }
                        paypalradiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                        stcRadiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                        cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));
                        codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                        tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                        tamarainstallmentradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));

                        // int subtotal = Math.round(Float.parseFloat(amount) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));

                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                            amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "إجمالي الطلب");

                        } else {
                            amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                        }


                        edt_card_detail.setVisibility(View.VISIBLE);
                        cardrecyclerlayout.setVisibility(View.VISIBLE);
                        selectpaymentflag = 1;

                        selectedcardposition = 0;
                        initSavedCardRecycler();
                    } else {
                        if (callLayout.getVisibility() == View.VISIBLE) {
                            callLayout.setVisibility(View.GONE);
                        }
                        paypalradiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                        stcRadiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                        cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));
                        codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                        tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                        tamarainstallmentradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));

                        int amount_afterwallet = Math.round(Float.parseFloat(amount)) - freshwalletAmount;

                        //int subtotal = Math.round((amount_afterwallet) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                            amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount_afterwallet + " " + "إجمالي الطلب");

                        } else {
                            amounttext.setText("Total" + " " + amount_afterwallet + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                        }


                        edt_card_detail.setVisibility(View.VISIBLE);
                        cardrecyclerlayout.setVisibility(View.VISIBLE);
                        selectpaymentflag = 1;
                        selectedcardposition = 0;
                        initSavedCardRecycler();
                    }
                }
               checkTamaraVisibility();

            }

        });


        codradiobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                tamarainstallmentradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                paypalradiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                stcRadiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                if (walletaddflag && amounttext.getText().toString().split(" ")[1].equals("0") && selectpaymentflag != 2) {

                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                        walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()) + " " + "-المحفظة");
                    } else {
                        walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()));

                    }

                    walletcheckbox.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                    leftwalletamount.setVisibility(View.GONE);
                    codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));


                    walletaddflag = false;
                    is_partialpayment = false;

                    selectedcardposition = 0;
                    initSavedCardRecycler();

                }


                if (selectpaymentflag != 2) {
                    if (callLayout.getVisibility() == View.VISIBLE) {
                        callLayout.setVisibility(View.GONE);
                    }

                    if (!walletaddflag) {
                        if (freshwalletAmount <= Integer.parseInt(amounttext.getText().toString().split(" ")[1])) {
                            codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));
                            cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));


                            int amountpluscod = Integer.parseInt(amounttext.getText().toString().split(" ")[1]) + Integer.parseInt(codamount);

                            // int subtotal = Math.round((amountpluscod) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));

                            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amountpluscod + " " + "إجمالي الطلب");

                            } else {
                                amounttext.setText("Total" + " " + amountpluscod + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                            }


                            selectedcardposition = 0;
                            initSavedCardRecycler();
                        }

                        if (freshwalletAmount > Integer.parseInt(amounttext.getText().toString().split(" ")[1])) {
                            codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));
                            cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                            int amountpluscod = Integer.parseInt(amount) + Integer.parseInt(codamount);
                            // int subtotal = Math.round((amountpluscod) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
                            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amountpluscod + " " + "إجمالي الطلب");

                            } else {
                                amounttext.setText("Total" + " " + amountpluscod + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                            }

                            selectedcardposition = 0;
                            initSavedCardRecycler();
                        }

                        edt_card_detail.setVisibility(View.GONE);
                        selectpaymentflag = 2;

                    } else {

                        codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));
                        cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                        int amountpluscod = Math.round(Float.parseFloat(amount)) + Math.round(Float.parseFloat(codamount)) - Integer.parseInt(MySharedPreferenceClass.getWalletamount(getContext()))/*-shippingCharge*/;

                        // int subtotal = Math.round((amountpluscod) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));

                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                            amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amountpluscod + " " + "إجمالي الطلب");

                        } else {
                            amounttext.setText("Total" + " " + amountpluscod + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                        }


                        edt_card_detail.setVisibility(View.GONE);
                        // cardrecyclerlayout.setVisibility(View.GONE);
                        selectpaymentflag = 2;
                        selectedcardposition = 0;
                        initSavedCardRecycler();


                    }
                }
                checkTamaraVisibility();

            }
        });


        walletcheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onwalletselction();
            }

        });


        tamararadiobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isinstallationtamaraflag = false;
                if(coupencode!=" ") {
                    openCoupenRemoveDialog();
                }
                else{

                    onselectTamaraLater();
                }
               /* initSavedCardRecycler();
                tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));
                selectpaymentflag = 6;

                if (amounttext.getText().toString().split(" ")[1].equals("0")) {

                    walletcheckbox.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                    walletaddflag = false;
                    is_partialpayment = false;


                    int mainwalletamount = Math.round(Float.parseFloat(MySharedPreferenceClass.getWalletamount(getContext())) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));

                    //walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + mainwalletamount + " " + "-المحفظة");

                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                        walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + mainwalletamount + " " + "-المحفظة");
                    } else {
                        walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + mainwalletamount);

                    }


                    //int subtotal = Math.round((Float.parseFloat(amount) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext())));


                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                        amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "إجمالي الطلب");

                    } else {
                        amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                    }


                    leftwalletamount.setVisibility(View.GONE);

                } else {
                    if (walletaddflag) {
                        int balance = Math.round(Float.parseFloat(amount)) - freshwalletAmount;

                        // int subtotal = Math.round((balance) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                            amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + balance + " " + "إجمالي الطلب");

                        } else {
                            amounttext.setText("Total" + " " + balance + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                        }


                    } else {
                        // int subtotal = Math.round(Float.parseFloat(amount) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                            amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "إجمالي الطلب");

                        } else {
                            amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                        }

                    }

                }
                paypalradiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                stcRadiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));
                tamarainstallmentradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));

                if (edt_card_detail.getVisibility() == View.VISIBLE) {
                    edt_card_detail.setVisibility(View.GONE);
                }
                selectedcardposition = 0;*/

            }


        });


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


    public static void setEditTextMaxLength(EditText editText, int length) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(length);
        editText.setFilters(FilterArray);
    }

    public void placeorderwithcheckout() {
        // parentDialogListener.showProgressDialog();

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


    public void UpgradeGenerateOrder() {
        // https://upgrade.eoutlet.com/en/V1/carts/mine/payment-information
        /*https://upgrade.eoutlet.com/rest/en/V1/carts/mine/payment-information*/
/*{
  "paymentMethod": {
    "method": "checkmo"
  },
  "billing_address": {
    "email": "jdoe@example.com",
    "region": "New York",
    "region_id": 43,
    "region_code": "NY",
    "country_id": "SA",
    "street": ["160 1st St."],
    "postcode": "11501",
    "city": "Mineola",
    "telephone": "516-555-1111",
    "firstname": "Jane",
    "lastname": "Doe"
  }
}*/

        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> addresses = new HashMap<>();

        map1.put("method", "stcpay");
        map2.put("region", MySharedPreferenceClass.getCountryId(getApplicationContext()));

        ArrayList<String> street = new ArrayList<>();

        street.add(MySharedPreferenceClass.getStreetname(getContext()));
        map2.put("street", street);


        map2.put("country_id", MySharedPreferenceClass.getCountryId(getApplicationContext()));
        //  map2.put("postcode", "11501");
        map2.put("city", MySharedPreferenceClass.getCityName(getContext()));
        map2.put("firstname", MySharedPreferenceClass.getfirstAdressname(getContext()));
        map2.put("lastname", MySharedPreferenceClass.getlastAdressname(getContext()));
        map2.put("customer_id", Integer.parseInt(MySharedPreferenceClass.getMyUserId(getContext())));
        map2.put("email", MySharedPreferenceClass.getEmail(getContext()));
        map2.put("telephone", MySharedPreferenceClass.getAdressphone(getApplicationContext()));
        addresses.put("billing_address", map2);

        addresses.put("paymentMethod", map1);


        Call<String> call;
        HashMap<String, String> headers = new HashMap<>();

        headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getCustomerToken(getContext()));
        headers.put("Cookie", MySharedPreferenceClass.getCookies(getContext()));


        if (!walletaddflag) {

            if (Locale.equals("en")) {
                call = apiService.createcustomerOrder(headers, addresses);
            } else {
                call = apiService.createcustomerOrderforarbic(headers, addresses);

            }
        } else {
            if (Locale.equals("en")) {
                call = apiService.createcustomerOrderforwallet(headers, "wallet", addresses);
            } else {
                call = apiService.createcustomerOrderforwalletforarabic(headers, "wallet", addresses);
            }
        }

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null) {


                    upgradeSendStcOtp(response.body().toString());

                } else {
                    parentDialogListener.hideProgressDialog();
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
                        parentDialogListener.hideProgressDialog();
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

    public void upgradeSendStcOtp(String orderid) {
        /*  https://upgrade.eoutlet.com/rest/V1/api/sendstcotp*/

       /* {
            "param": {
            "order_id": "15",
                    "amount":"12386.25",
                    "stcmobile":"+966557877988"
        }
        }*/

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        map1.put("order_id", orderid);
        map1.put("amount", amounttext.getText().toString().split(" ")[1]);
        map1.put("stcmobile", "+966" + edittext_stcMobile.getText().toString());

        mainparam.put("param", map1);


        Call<OrderResponseStc> call = apiService.initiateStcOrder(mainparam);


        call.enqueue(new Callback<OrderResponseStc>() {
            @Override
            public void onResponse(Call<OrderResponseStc> call, Response<OrderResponseStc> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();

                    MySharedPreferenceClass.setBedgeCount(getContext(), 0);
                    if (response.body().orderId != null && response.body().otpReference != null) {
                        DialogFragment prFrag = new StcOtpFragment();
                        Bundle bund = new Bundle();
                        bund.putString("orderId", response.body().orderId);
                        bund.putString("otp_refernce", response.body().otpReference);
                        bund.putString("sTCPayPmtReference", response.body().sTCPayPmtReference);
                        bund.putString("amount", amount);
                        bund.putString("mobileNumber", "+966" + edittext_stcMobile.getText().toString());

                        prFrag.setArguments(bund);


                        prFrag.show(getFragmentManager(), "paymentoptionfragment");
                    } else {
                        if (Locale.equals("ar")) {
                            Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                        }
                        retainstcorder(orderid);
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
            public void onFailure(Call<OrderResponseStc> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                retainstcorder(orderid);
                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
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

        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> query = new HashMap<>();


        query.put("order_id", orderid);
        query.put("devicetype", "android");
        query.put("cart_id", MySharedPreferenceClass.getQuoteId(getContext()));

        mainparam.put("param", query);


        Call<StcRetainApi> call = apiService.getRetainorderfromStc(mainparam);
        call.enqueue(new Callback<StcRetainApi>() {
            @Override
            public void onResponse(Call<StcRetainApi> call, Response<StcRetainApi> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();

                    if (response.body().msg.equals("success")) {


                    } else {
                        //Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_SHORT).show();
                        parentDialogListener.hideProgressDialog();


                    }
                }


            }


            @Override
            public void onFailure(Call<StcRetainApi> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(ContentValues.TAG, t.toString());

                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void openNavigatetoCartdialog(String message) {
        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        alertDialogBuilder.setMessage(message);


        alertDialogBuilder.setPositiveButton("موافق",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        cartreference.setUserHintcall();//}
                        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    }


                });

        alertDialogBuilder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();


            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    public void UpgradePlaceOrderforPaypal() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> addresses = new HashMap<>();

        map1.put("method", "checkout_paypal");
        map2.put("region", MySharedPreferenceClass.getCountryId(getApplicationContext()));

        ArrayList<String> street = new ArrayList<>();

        street.add(MySharedPreferenceClass.getStreetname(getContext()));
        map2.put("street", street);


        map2.put("country_id", MySharedPreferenceClass.getCountryId(getApplicationContext()));
        //  map2.put("postcode", "11501");
        map2.put("city", MySharedPreferenceClass.getCityName(getContext()));
        map2.put("firstname", MySharedPreferenceClass.getfirstAdressname(getContext()));
        map2.put("lastname", MySharedPreferenceClass.getlastAdressname(getContext()));
        map2.put("customer_id", Integer.parseInt(MySharedPreferenceClass.getMyUserId(getContext())));
        map2.put("email", MySharedPreferenceClass.getEmail(getContext()));
        map2.put("telephone", MySharedPreferenceClass.getAdressphone(getApplicationContext()));
        addresses.put("billing_address", map2);

        addresses.put("paymentMethod", map1);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getCustomerToken(getContext()));
        headers.put("Cookie", MySharedPreferenceClass.getCookies(getContext()));

        Call<String> call;
        if (!walletaddflag) {
            call = apiService.createcustomerOrder(headers, addresses);
        } else {
            call = apiService.createcustomerOrderforwallet(headers, "wallet", addresses);
        }

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null) {
                    // parentDialogListener.hideProgressDialog();

                    dopaypalPayment(response.body().toString());

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

    public void UpgradePlaceOrderforCheckout() {
        parentDialogListener.showProgressDialog();

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


        map2.put("country_id", MySharedPreferenceClass.getCountryId(getApplicationContext()));
        //
        map2.put("city", MySharedPreferenceClass.getCityName(getContext()));
        map2.put("firstname", MySharedPreferenceClass.getfirstAdressname(getContext()));
        map2.put("lastname", MySharedPreferenceClass.getlastAdressname(getContext()));
        map2.put("customer_id", Integer.parseInt(MySharedPreferenceClass.getMyUserId(getContext())));
        map2.put("email", MySharedPreferenceClass.getEmail(getContext()));
        map2.put("telephone", MySharedPreferenceClass.getAdressphone(getApplicationContext()));
        addresses.put("billing_address", map2);

        addresses.put("paymentMethod", map1);


        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getCustomerToken(getContext()));
        headers.put("Cookie", MySharedPreferenceClass.getCookies(getContext()));

        Call<String> call;
        if (!walletaddflag) {
            if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
                call = apiService.createcustomerOrder(headers, addresses);
            } else {
                call = apiService.createcustomerOrderforarbic(headers, addresses);
            }

        } else {
            if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
                call = apiService.createcustomerOrderforwallet(headers, "wallet", addresses);
            } else {
                call = apiService.createcustomerOrderforwalletforarabic(headers, "wallet", addresses);
            }
        }

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null) {
                    // parentDialogListener.hideProgressDialog();

                    if (selectpaymentflag == 1) {

                        checkoutcardPaymentInitiation(response.body().toString());
                    } else if (selectpaymentflag == 3) {


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

    public void UpgradePlaceOrderforTamara() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> addresses = new HashMap<>();





        if (selectpaymentflag == 6) {
            map1.put("method", "tamara_pay_later");
        } else {
            map1.put("method", "tamara_pay_by_instalments");//tamara_pay_by_instalments


        }

        map2.put("region", MySharedPreferenceClass.getCountryId(getApplicationContext()));

        ArrayList<String> street = new ArrayList<>();

        street.add(MySharedPreferenceClass.getStreetname(getContext()));
        map2.put("street", street);


        map2.put("country_id", MySharedPreferenceClass.getCountryId(getApplicationContext()));
        // map2.put("postcode", "11501");
        map2.put("city", MySharedPreferenceClass.getCityName(getContext()));
        map2.put("firstname", MySharedPreferenceClass.getfirstAdressname(getContext()));
        map2.put("lastname", MySharedPreferenceClass.getlastAdressname(getContext()));
        map2.put("customer_id", Integer.parseInt(MySharedPreferenceClass.getMyUserId(getContext())));
        map2.put("email", MySharedPreferenceClass.getEmail(getContext()));
        map2.put("telephone", MySharedPreferenceClass.getAdressphone(getApplicationContext()) /*"+966505348769"*/);
        addresses.put("billing_address", map2);

        addresses.put("paymentMethod", map1);

        HashMap<String, String> headers = new HashMap<>();

        headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getCustomerToken(getContext()));
        headers.put("Cookie", MySharedPreferenceClass.getCookies(getApplicationContext()));
        Call<String> call;
        if (!walletaddflag) {

            if (Locale.equals("en")) {
                call = apiService.createcustomerOrder(headers, addresses);
            } else {
                call = apiService.createcustomerOrderforarbic(headers, addresses);

            }
        } else {
            if (Locale.equals("en")) {
                call = apiService.createcustomerOrderforwallet(headers, "wallet", addresses);
            } else {
                call = apiService.createcustomerOrderforwalletforarabic(headers, "wallet", addresses);
            }
        }

      //  Call<String> call = apiService.createcustomerOrder(headers, addresses);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null) {


                    // getOrderDetailforTamara(response.body().toString());


                    TamarathreedigitId = response.body().toString();


                    doTamaraCheckoutOrder(TamarathreedigitId);

                } else {

                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            parentDialogListener.hideProgressDialog();
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
                            parentDialogListener.hideProgressDialog();
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

    public void UpgradePlaceOrderforCod() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> addresses = new HashMap<>();

        map1.put("method", "cashondelivery");
        map2.put("region", MySharedPreferenceClass.getCountryId(getApplicationContext()));

        ArrayList<String> street = new ArrayList<>();

        street.add(MySharedPreferenceClass.getStreetname(getContext()));
        map2.put("street", street);


        map2.put("country_id", MySharedPreferenceClass.getCountryId(getApplicationContext()));
        // map2.put("postcode", "11501");
        map2.put("city", MySharedPreferenceClass.getCityName(getContext()));
        map2.put("firstname", MySharedPreferenceClass.getfirstAdressname(getContext()));
        map2.put("lastname", MySharedPreferenceClass.getlastAdressname(getContext()));
        map2.put("customer_id", Integer.parseInt(MySharedPreferenceClass.getMyUserId(getContext())));
        map2.put("email", MySharedPreferenceClass.getEmail(getContext()));
        map2.put("telephone", MySharedPreferenceClass.getAdressphone(getApplicationContext()));
        addresses.put("billing_address", map2);

        addresses.put("paymentMethod", map1);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getCustomerToken(getContext()));
        headers.put("Cookie", MySharedPreferenceClass.getCookies(getContext()));
        Call<String> call;
        if (!walletaddflag) {

            if (Locale.equals("en")) {
                call = apiService.createcustomerOrder(headers, addresses);
            } else {
                call = apiService.createcustomerOrderforarbic(headers, addresses);
            }


        } else {

            if (Locale.equals("ar")) {
                call = apiService.createcustomerOrderforwallet(headers, "wallet", addresses);
            } else {

                call = apiService.createcustomerOrderforwalletforarabic(headers, "wallet", addresses);
            }
        }
        //call = apiService.createcustomerOrder(headers, addresses);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null && response.isSuccessful()) {
                    // parentDialogListener.hideProgressDialog();

                    getOrderDetailforCod(response.body().toString());


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

    public void getOrderDetailforCod(String orderId) {

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
/*{
    "param": {
        "order_id":101789
    }
}*/

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        map1.put("order_id", orderId);


        mainparam.put("param", map1);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getCustomerToken(getContext()));
        headers.put("Cookie", MySharedPreferenceClass.getCookies(getContext()));
        Call<GetOrderdetailforCod> call;
        if (Locale.equals("en")) {
            call = apiService.getOrderDetailforCod(headers, mainparam);
        } else {
            call = apiService.getOrderDetailforCodforArbic(headers, mainparam);
        }


        call.enqueue(new Callback<GetOrderdetailforCod>() {
            @Override
            public void onResponse(Call<GetOrderdetailforCod> call, Response<GetOrderdetailforCod> response) {


                if (response.body() != null && response.isSuccessful()) {
                    parentDialogListener.hideProgressDialog();
                    MySharedPreferenceClass.setBedgeCount(getContext(), 0);
                    Intent in = new Intent(getContext(), ThankyouActivity.class);
                    in.putExtra("orderId", response.body().incrementId);
                    in.putExtra("statusflag", "success");
                    in.putExtra("totalvalue", amounttext.getText().toString().split(" ")[1]);
                    in.putExtra("coupencode", coupencode);
                    //in.putExtra("coupencode",coupencode);
                    startActivity(in);

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
            public void onFailure(Call<GetOrderdetailforCod> call, Throwable t) {
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


    public void getOrderDetailforTamara(String orderId, String status) {

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Call<GetOrderdetail> call = apiService.getOrderDetail("Bearer" + " " + MySharedPreferenceClass.getToken(getContext()), "https://upgrade.eoutlet.com/rest/V1/orders/" + orderId);


        call.enqueue(new Callback<GetOrderdetail>() {
            @Override
            public void onResponse(Call<GetOrderdetail> call, Response<GetOrderdetail> response) {


                if (response.body() != null && response.isSuccessful()) {
                    TamaraInternalOrderId = response.body().incrementId.toString();

                    if (status.equals("success")) {
                        sendTamarStatustoWeb(status);
                    } else if (status.equals("failed")) {
                        retainstcorder(response.body().incrementId.toString());

                        sendTamarStatustoWeb(status);

                    } else {

                        sendTamarStatustoWeb(status);

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
            public void onFailure(Call<GetOrderdetail> call, Throwable t) {
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

    public void getTamaraPaymentTypes(String orderid) {

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Map<Object, Object> map1 = new HashMap<>();


        Call<ArrayList<TamaraPaymentType>> call =
                apiService.getTamaraPaymentsTypes("Bearer " + Constants.AUTH_TOKEN, "https://api-sandbox.tamara.co/checkout/payment-types?country=SA&currency=SAR");


        call.enqueue(new Callback<ArrayList<TamaraPaymentType>>() {
            @Override
            public void onResponse(Call<ArrayList<TamaraPaymentType>> call, Response<ArrayList<TamaraPaymentType>> response) {


                if (response.body() != null && response.isSuccessful()) {

                    // getTamarapaymentUrl(orderid);
                    //doTamaraCheckoutOrder(orderid);


                }
            }

            @Override
            public void onFailure(Call<ArrayList<TamaraPaymentType>> call, Throwable t) {
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

    public void doTamaraCheckoutOrder(String orderid) {

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Map<Object, Object> map1 = new HashMap<>();
        Call<GetTamaraCheckoutResponse> call;

        HashMap<String, String> headers = new HashMap<>();

        headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getToken(getContext()));
        headers.put("Cookie", MySharedPreferenceClass.getCookies(getApplicationContext()));


        call = apiService.doTamaraCheckoutOrder(headers, "https://upgrade.eoutlet.com/rest/V1/tamara/checkout/" + orderid);

        call.enqueue(new Callback<GetTamaraCheckoutResponse>() {
            @Override
            public void onResponse(Call<GetTamaraCheckoutResponse> call, Response<GetTamaraCheckoutResponse> response) {


                if (response.body() != null && response.isSuccessful()) {

                    parentDialogListener.hideProgressDialog();
                    TamaraOrderId = response.body().tamaraOrderId;

                    Log.e("TamaraOrderId", TamaraOrderId);
                    Log.e("Redirect URL",response.body().redirectUrl);
                    Log.e("Payment Success URl",response.body().paymentSuccessRedirectUrl);
                    Log.e("Payment Failure URL",response.body().paymentFailureRedirectUrl);
                    Log.e("Payment Canecel URL", response.body().paymentCancelRedirectUrl);
                    /*response.body().paymentSuccessRedirectUrl*/
                  getView().post(new Runnable() {
                      @Override
                      public void run() {

                          TamaraPayment.Companion.startPayment(PaymentOptionsFragment.this, response.body().redirectUrl,response.body().paymentSuccessRedirectUrl/*"https://upgrade.eoutlet.com/tamara/payment/"+ orderid+"/success"*/, response.body().paymentFailureRedirectUrl, response.body().paymentCancelRedirectUrl);


                      }
                  });


                }
            }

            @Override
            public void onFailure(Call<GetTamaraCheckoutResponse> call, Throwable t) {
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
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> addresses = new HashMap<>();

        map1.put("method", "wallet");
        map2.put("region", MySharedPreferenceClass.getCountryId(getApplicationContext()));

        ArrayList<String> street = new ArrayList<>();

        street.add(MySharedPreferenceClass.getStreetname(getContext()));
        map2.put("street", street);


        map2.put("country_id", MySharedPreferenceClass.getCountryId(getApplicationContext()));
        //map2.put("postcode", "11501");
        map2.put("city", MySharedPreferenceClass.getCityName(getContext()));
        map2.put("firstname", MySharedPreferenceClass.getfirstAdressname(getContext()));
        map2.put("lastname", MySharedPreferenceClass.getlastAdressname(getContext()));
        map2.put("customer_id", Integer.parseInt(MySharedPreferenceClass.getMyUserId(getContext())));
        map2.put("email", MySharedPreferenceClass.getEmail(getContext()));
        map2.put("telephone", MySharedPreferenceClass.getAdressphone(getApplicationContext()));
        addresses.put("billing_address", map2);

        addresses.put("paymentMethod", map1);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getCustomerToken(getContext()));
        headers.put("Cookie", MySharedPreferenceClass.getCookies(getContext()));


        Call<String> call;
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            call = apiService.createcustomerOrder(headers, addresses);
        } else {

            call = apiService.createcustomerOrderforarbic(headers, addresses);
        }


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null) {


                    fullpaymentwithWallet(response.body().toString());


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


    public void upgradeplaceorderwithsavedcard() {
        HashMap<Object, Object> initialorder = new HashMap<>();
        initialorder.put("amount", amounttext.getText().toString().split(" ")[1]);
        initialorder.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        initialorder.put("token_name", tokenname);
        if (isgiftwrap) {
            initialorder.put("gift_wrap_fee", "1");
        } else {
            initialorder.put("gift_wrap_fee", "0");

        }
        if (!remembermeflag) {
            initialorder.put("remember_me", "NO");
        } else {
            initialorder.put("remember_me", "YES");
        }
        if (!walletaddflag) {
            initialorder.put("is_partial", "0");
        } else {
            initialorder.put("is_partial", "1");
        }

        initialorder.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        initialorder.put("user_id", MySharedPreferenceClass.getMyUserId(getContext()));
        initialorder.put("payment_method", "checkoutcom_card_payment");
        initialorder.put("shipping_method", shippingmethod);
        initialorder.put("devicetype", "android");

        if (coupencode != " ") {
            initialorder.put("coupon", coupencode);
        } else {

            initialorder.put("coupon", " ");


        }


        HashMap<Object, Object> payment = new HashMap<>();
        payment.put("firstname", MySharedPreferenceClass.getMyFirstNamePref(getContext()));
        payment.put("lastname", MySharedPreferenceClass.getMyLastNamePref(getContext()));
        payment.put("city", MySharedPreferenceClass.getCityName(getContext()));

        payment.put("country_id", "SA");
        payment.put("region", "Riyadh");
        payment.put("telephone", MySharedPreferenceClass.getMobileNumber(getContext()));

        HashMap<String, String> street = new HashMap<>();
        street.put("0", MySharedPreferenceClass.getStreetname(getContext()));
        street.put("1", " ");
        payment.put("street", street);

        initialorder.put("payment", payment);


        HashMap<Object, Object> paymentdetail = new HashMap<>();
        paymentdetail.put("name", "");
        paymentdetail.put("number", "");
        paymentdetail.put("securitycode", savedcardcvv);
        paymentdetail.put("expiry", "");

        initialorder.put("card_detail", paymentdetail);


        HashMap<Object, Object> shipping = new HashMap<>();

        shipping.put("firstname", MySharedPreferenceClass.getfirstAdressname(getContext()));
        shipping.put("lastname", MySharedPreferenceClass.getlastAdressname(getContext()));
        shipping.put("street", street);
        shipping.put("city", MySharedPreferenceClass.getCityName(getContext()));
        shipping.put("country_id", MySharedPreferenceClass.getCountryId(getContext()));
        shipping.put("region", MySharedPreferenceClass.getCountryId(getContext()));
        shipping.put("telephone", MySharedPreferenceClass.getAdressphone(getContext()));

        initialorder.put("shipping", shipping);


        HashMap<String, String> products = new HashMap<>();


        ArrayList<Object> mainarraylist = new ArrayList<>();


        for (int i = 0; i < getdata.size(); i++) {

            HashMap<String, Object> superattribute = new HashMap<>();
            //HashMap<String,String>  size = new HashMap<>();
            HashMap<String, String> colorandsize = new HashMap<>();

            if (getdata.get(i).option.size() > 0) {
                colorandsize.put(getdata.get(i).option.get(0).color.id.toString(), getdata.get(i).option.get(0).color.value.toString());
                colorandsize.put(getdata.get(i).option.get(0).size.id.toString(), getdata.get(i).option.get(0).size.value.toString());
                superattribute.put("super_attribute", colorandsize);

            }

            superattribute.put("product_id", getdata.get(i).productId);
            superattribute.put("qty", getdata.get(i).qty);

            mainarraylist.add(superattribute);

        }


        initialorder.put("products", mainarraylist);
        initialorder.put("transaction_id", " ");


        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));


        Call<CheckoutOrderResponse2> call = apiService.createorderwithcheckout2(initialorder);
        call.enqueue(new Callback<CheckoutOrderResponse2>() {
            @Override
            public void onResponse(Call<CheckoutOrderResponse2> call, Response<CheckoutOrderResponse2> response) {

                parentDialogListener.hideProgressDialog();
                if (response.body() != null) {

                    if (response.body().msg.equals("success")) {


                        if (!response.body().redirect_link.isEmpty() && !response.body().orderId.equals(null)) {
                            Fragment prFrag = new OrderPaymentWebView();
                            Bundle bundle = new Bundle();
                            bundle.putString("order_id", response.body().orderId);
                            bundle.putString("url", response.body().redirect_link);
                            bundle.putString("totalvalue", amounttext.getText().toString().split(" ")[1]);
                            bundle.putString("coupencode", coupencode);
                            bundle.putString("token_name", tokenname);
                            prFrag.setArguments(bundle);

                            getFragmentManager()
                                    .beginTransaction().addToBackStack(null)
                                    .add(R.id.cartcontainer, prFrag)
                                    .commit();


                        }
                    } else {
                        // openNavigatetoCartdialog(response.body().data.toString());
                        parentDialogListener.hideProgressDialog();
                        if (Locale.equals("ar")) {
                            Toast.makeText(getContext(), "برجاء فحص بيانات البطاقة", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Please check the card information", Toast.LENGTH_LONG).show();
                        }

                    }


                }


            }


            @Override
            public void onFailure(Call<CheckoutOrderResponse2> call, Throwable t) {
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

    public void getUpgradeCartData() {


        parentDialogListener.hideProgressDialog();
        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));


        /*https://upgrade.eoutlet.com/rest/en/V1/api/customercart/83188*/

        Call<UpgradedCartItems> call;
        if (Locale.equals("en")) {

            call = apiService.getUpgradedCartDetail(Constants.UPGRADED_HOST_LINK + "rest/en/V1/api/customercart/" + MySharedPreferenceClass.getMyUserId(getContext()));
        } else {
            call = apiService.getUpgradedCartDetail(Constants.UPGRADED_HOST_LINK + "rest/ar/V1/api/customercart/" + MySharedPreferenceClass.getMyUserId(getContext()));
        }

        call.enqueue(new Callback<UpgradedCartItems>() {
            @Override
            public void onResponse(Call<UpgradedCartItems> call, Response<UpgradedCartItems> response) {
                parentDialogListener.hideProgressDialog();
                int quant;
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().response.equals("success")) {
                        upgradedcartData = response.body().data.items;
                        if (!MySharedPreferenceClass.getCountryId(getContext()).equalsIgnoreCase("SA") || Integer.parseInt(response.body().data.gift_wrap_fee) > 0 || Integer.parseInt(amount) > 3000) {


                            codradiobutton.setVisibility(View.GONE);


                        }


                        getUpgradedWalletHistory();


                    }
                } else {


                }

            }


            @Override
            public void onFailure(Call<UpgradedCartItems> call, Throwable t) {
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


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       // Toast.makeText(getContext(),"from on attach",Toast.LENGTH_SHORT).show();
        try {
        thiscontext = activity;
            parentDialogListener = (ParentDialogsListener) activity;

        } catch (ClassCastException e) {

            throw new ClassCastException(activity.toString()
                    + " Activity's Parent should be Parent Activity");
        }


    }


    public void getUpgradedWalletHistory() {
        /*https://upgrade.eoutlet.com/rest/V1/api/getwallet/83188*/
        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Map<String, String> map1 = new HashMap<>();
        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));

        Call<UpgradedWalletHistory> call = Locale == "en" ?
                apiService.getUpgradedwallethistory("https://upgrade.eoutlet.com/rest/en/V1/api/getwallet/" +
                        MySharedPreferenceClass.getMyUserId(getContext())) : apiService.getUpgradedwallethistory("https://upgrade.eoutlet.com/rest/ar/V1/api/getwallet/" +
                MySharedPreferenceClass.getMyUserId(getContext()));


        call.enqueue(new Callback<UpgradedWalletHistory>() {
            @Override
            public void onResponse(Call<UpgradedWalletHistory> call, Response<UpgradedWalletHistory> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();

                    int walletamount_in_selected_currency = Math.round((response.body().data.amount) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));


                    MySharedPreferenceClass.setWalletAmount(getContext(), String.valueOf(walletamount_in_selected_currency));
                    //MySharedPreferenceClass.setWalletAmount(getContext(),response.body().data.amount.toString());

                    freshwalletAmount = walletamount_in_selected_currency;
                    //freshwalletAmount = response.body().data.amount;

                    if (freshwalletAmount == 0) {

                        walletcheckbox.setEnabled(false);


                    } else {
                        walletcheckbox.setEnabled(true);


                    }
                    MySharedPreferenceClass.setWalletAmount(getContext(), String.valueOf(freshwalletAmount));

                    //    walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()) + " " + "-المحفظة");

                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                        walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()) + " " + "-المحفظة");
                    } else {
                        walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()));

                    }
                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                        leftwalletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + freshwalletAmount + " " + "الرصيد المتبقي بحسابكم:");

                    } else {
                        leftwalletamount.setText("Remaining balance in your account:" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + freshwalletAmount);


                    }
                    getcodcharges();


                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<UpgradedWalletHistory> call, Throwable t) {
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
        query.put("customer_id", MySharedPreferenceClass.getMyUserId(getApplicationContext()));


        /*https://upgrade.eoutlet.com/rest/en/V1/api/customercart/83188*/

        Call<UpgardeSavedCardDetail> call;
        if (Locale.equals("en")) {
            call = apiService.getUpgradedSavedCard(Constants.UPGRADED_HOST_LINK + "rest/en/V1/api/storedsavecards/" + MySharedPreferenceClass.getMyUserId(getApplicationContext()));
        } else {
            call = apiService.getUpgradedSavedCard(Constants.UPGRADED_HOST_LINK + "rest/ar/V1/api/storedsavecards/" + MySharedPreferenceClass.getMyUserId(getApplicationContext()));
        }

        call.enqueue(new Callback<UpgardeSavedCardDetail>() {
            @Override
            public void onResponse(Call<UpgardeSavedCardDetail> call, Response<UpgardeSavedCardDetail> response) {
                parentDialogListener.hideProgressDialog();

                upgradedcarddetail = response.body().data;

                getUpgradedLatestversionCode();
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
                System.out.println(cvv + "and" + savedcardcvv);
                tokenname = upgradedcarddetail.get(position).gatewayToken;

                selectedcardposition = position;


                switch (id) {

                    case R.id.remembercardclick://button for message


                        if (walletaddflag == true && amounttext.getText().toString().split(" ")[1].equals("0") && selectpaymentflag != 3) {

                            selectedcardposition = position;
                            // walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext())+ MySharedPreferenceClass.getWalletamount(getContext()) + " " + "-المحفظة");

                            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()) + " " + "-المحفظة");
                            } else {
                                walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()));

                            }

                            cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                            codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                            tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                            walletcheckbox.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                            tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                            tamarainstallmentradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));

                            if (callLayout.getVisibility() == View.VISIBLE) {
                                callLayout.setVisibility(View.GONE);
                            }
                            stcRadiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));


                            walletaddflag = false;
                            is_partialpayment = false;

                        }
                        if (selectpaymentflag != 3) {


                            if (!walletaddflag) {

                                selectedcardposition = position;
                                if (callLayout.getVisibility() == View.VISIBLE) {
                                    callLayout.setVisibility(View.GONE);
                                }
                                paypalradiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                                stcRadiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                                tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                                cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                                codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                                tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                                tamarainstallmentradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));

                                //int subtotal = Math.round((Float.parseFloat(amount) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext())));
                                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                    amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "إجمالي الطلب");

                                } else {
                                    amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                                }


                                edt_card_detail.setVisibility(View.GONE);
                                selectpaymentflag = 3;
                            } else {


                                if (callLayout.getVisibility() == View.VISIBLE) {
                                    callLayout.setVisibility(View.GONE);
                                }
                                stcRadiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                                paypalradiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                                selectedcardposition = position;
                                tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                                tamarainstallmentradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));

                                cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                                codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                                tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                                int amount_afterwallet = Integer.parseInt(amount) - freshwalletAmount;


                                // int subtotal = Math.round((amount_afterwallet) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
                                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                    amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount_afterwallet + " " + "إجمالي الطلب");

                                } else {
                                    amounttext.setText("Total" + " " + amount_afterwallet + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                                }


                                edt_card_detail.setVisibility(View.GONE);
                                selectpaymentflag = 3;


                            }


                        }


                        break;
                }
            }
        }


        );
        cardrecycler.setAdapter(cardAdapter);
        cardrecycler.setNestedScrollingEnabled(false);
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


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        Map<Object, Object> map2 = new HashMap<>();
        Map<Object, Object> cardetail = new HashMap<>();
        map1.put("order_id", orderid);
        map1.put("amount", amounttext.getText().toString().split(" ")[1]);
        map1.put("currency", MySharedPreferenceClass.getSelectedCurrencyName(getContext()));
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
                    parentDialogListener.hideProgressDialog();

                    Intent in = new Intent(getContext(), OrderPaymentWebViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("order_id", response.body().orderId);
                    bundle.putString("url", response.body().redirect_link);
                    bundle.putBoolean("rememberme", remembermeflag);


                    bundle.putString("totalvalue", amounttext.getText().toString().split(" ")[1]);
                    bundle.putString("coupencode", coupencode);
                    bundle.putString("token_name", tokennameforcheckout);

                    in.putExtras(bundle);
                    startActivity(in);


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


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        Map<Object, Object> map2 = new HashMap<>();
        Map<Object, Object> cardetail = new HashMap<>();
        map1.put("order_id", orderid);
        map1.put("amount", amounttext.getText().toString().split(" ")[1]);
        map1.put("currency", MySharedPreferenceClass.getSelectedCurrencyName(getContext()));
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
                    bundle.putString("coupencode", coupencode);
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

    public void dopaypalPayment(String orderid) {


        /*{
    "param": {
        "order_id": "218",
        "amount":"1.00"
    }
}*/
        // parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        Map<Object, Object> map2 = new HashMap<>();
        Map<Object, Object> cardetail = new HashMap<>();

        map1.put("order_id", orderid);
        map1.put("amount", amounttext.getText().toString().split(" ")[1]);

        mainparam.put("param", map1);


        Call<PaypalPaymentResponse> call = apiService.dopaypalpayment(mainparam);


        call.enqueue(new Callback<PaypalPaymentResponse>() {
            @Override
            public void onResponse(Call<PaypalPaymentResponse> call, Response<PaypalPaymentResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();

                    Intent in = new Intent(getContext(), OrderPaymentWebViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("order_id", response.body().orderId);
                    bundle.putString("url", response.body().redirectLink);
                    bundle.putString("paymentId", response.body().paymentId);


                    bundle.putString("totalvalue", amounttext.getText().toString().split(" ")[1]);
                    bundle.putString("coupencode", coupencode);
                    bundle.putString("token_name", tokennameforcheckout);

                    in.putExtras(bundle);
                    startActivity(in);

                    //Toast.makeText(getContext(), "Order Done", Toast.LENGTH_SHORT).show();

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
            public void onFailure(Call<PaypalPaymentResponse> call, Throwable t) {
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

    public void fullpaymentwithWallet(String orderId) {
        /* https://upgrade.eoutlet.com/rest/en/V1/api/walletpayment*/

        /*"{
    ""param"": {
           ""customer_id"":83188,
           ""order_id"":""168""
        }
    }"*/


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        Map<Object, Object> map2 = new HashMap<>();
        Map<Object, Object> cardetail = new HashMap<>();

        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        map1.put("order_id", orderId);

        mainparam.put("param", map1);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getCustomerToken(getContext()));
        headers.put("Cookie", MySharedPreferenceClass.getCookies(getContext()));


        Call<WalletResponse> call;
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            call = apiService.doUpgradedpaymentwithwallet(mainparam);
        } else {
            call = apiService.doUpgradedpaymentwithwalletinArabic(mainparam);

        }


        call.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {


                if (response.body() != null && response.isSuccessful()) {
                    parentDialogListener.hideProgressDialog();
                    Intent in = new Intent(getContext(), ThankyouActivity.class);
                    in.putExtra("orderId", response.body().incrementId);
                    in.putExtra("statusflag", response.body().incrementId);
                    in.putExtra("totalvalue", amounttext.getText().toString().split(" ")[1]);
                    in.putExtra("coupencode", coupencode);
                    //in.putExtra("coupencode",coupencode);
                    startActivity(in);


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
            public void onFailure(Call<WalletResponse> call, Throwable t) {
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

    public void onwalletselction() {
        /*https://upgrade.eoutlet.com/rest/en/V1/api/selectwallet*/

        /*"{
    ""param"": {
           ""customer_id"":83188,
           ""quote_id"":""1724"",
           ""wallet_status"":""select""
        }
}"*/

        /*wallet_status : select   /   unselect*/

        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        Map<Object, Object> map2 = new HashMap<>();
        Map<Object, Object> cardetail = new HashMap<>();

        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        map1.put("quote_id", MySharedPreferenceClass.getQuoteId(getContext()));
        HashMap<String, String> headers = new HashMap<>();
        if (!walletaddflag) {
            map1.put("wallet_status", "select");
            headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getCustomerToken(getContext()));
            headers.put("Cookie", MySharedPreferenceClass.getCookies(getContext()));
            headers.put("walletmethod", "wallet");

        } else {
            map1.put("wallet_status", "unselect");
            headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getCustomerToken(getContext()));
            headers.put("Cookie", MySharedPreferenceClass.getCookies(getContext()));

        }


        mainparam.put("param", map1);


        Call<ApplyWalletResult> call;
        if (Locale.equals("en")) {
            call = apiService.dowalletupdate(headers, mainparam);
        } else {
            call = apiService.dowalletupdateforarabic(headers, mainparam);
        }

        call.enqueue(new Callback<ApplyWalletResult>() {
            @Override
            public void onResponse(Call<ApplyWalletResult> call, Response<ApplyWalletResult> response) {


                if (response.body() != null && response.isSuccessful()) {
                    parentDialogListener.hideProgressDialog();
                    int subtotal;
                    if (selectpaymentflag == 2) {


                        subtotal = Math.round((Float.parseFloat(response.body().needToPay.toString()) * MySharedPreferenceClass.getSelectedCurrencyRate(getApplicationContext()))) + Integer.valueOf(codamount);
                    } else {
                        subtotal = Math.round((Float.parseFloat(response.body().needToPay.toString()) * MySharedPreferenceClass.getSelectedCurrencyRate(getApplicationContext())));

                    }


                    if (!walletaddflag) {

                        leftwalletamount.setVisibility(View.VISIBLE);
                        walletcheckbox.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));
                        walletaddflag = true;
                        is_partialpayment = true;


                        if (selectpaymentflag == 1 || selectpaymentflag == 3 || selectpaymentflag == 4 || selectpaymentflag == 5 || selectpaymentflag == 6|| selectpaymentflag == 7 || selectpaymentflag == 99) {
                            if (freshwalletAmount <= Math.round(Float.parseFloat(amounttext.getText().toString().split(" ")[1]))) {

                                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                    leftwalletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + "0" + " " + "الرصيد المتبقي بحسابكم:");

                                } else {
                                    leftwalletamount.setText("Left wallet balance:" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + "0");


                                }


                                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                    amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + subtotal + " " + "إجمالي الطلب");

                                } else {
                                    amounttext.setText("Total" + " " + subtotal + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                                }


                            } else if (freshwalletAmount > Math.round(Float.parseFloat(amounttext.getText().toString().split(" ")[1]))) {


                                int walletbalance = freshwalletAmount - Math.round(Float.parseFloat(amounttext.getText().toString().split(" ")[1]));


                                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                    leftwalletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + walletbalance + " " + "الرصيد المتبقي بحسابكم:");

                                } else {
                                    leftwalletamount.setText("Left wallet balance:" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + walletbalance);


                                }


                                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                    walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + Math.round((Float.parseFloat(amount))) + " " + "-المحفظة");
                                } else {
                                    walletamount.setText("Wallet-" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + Math.round((Float.parseFloat(amount))));

                                }


                                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                    amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + subtotal + " " + "إجمالي الطلب");

                                } else {
                                    amounttext.setText("Total" + " " + subtotal + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                                }
                                // amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + subtotal+ " " + "إجمالي الطلب");
                                selectpaymentflag = 0;


                            }
                        } else if (selectpaymentflag == 2) {

                            if (freshwalletAmount <= Math.round(Float.parseFloat(amount))) {

                                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                    leftwalletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + "0" + " " + "الرصيد المتبقي بحسابكم:");

                                } else {
                                    leftwalletamount.setText("Left wallet balance:" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + "0");


                                }
                                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                    amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + subtotal + " " + "إجمالي الطلب");

                                } else {
                                    amounttext.setText("Total" + " " + subtotal + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                                }

                                //amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + subtotal+ " " + "إجمالي الطلب");
                            } else if (freshwalletAmount > subtotal) {

                                int walletbalance = freshwalletAmount - Math.round(Float.parseFloat(amount))/*subtotal*/;


                                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                    leftwalletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + walletbalance + " " + "الرصيد المتبقي بحسابكم:");

                                } else {
                                    leftwalletamount.setText("Left wallet balance:" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + walletbalance);


                                }

                                //  leftwalletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + walletbalance + " " + ": الرصيد المتبقي بحسابكم");
                                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                    walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + Math.round((Float.parseFloat(amount) /** MySharedPreferenceClass.getSelectedCurrencyRate(getContext())*/)) + " " + "-المحفظة");
                                } else {
                                    walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + Math.round((Float.parseFloat(amount))));

                                }


                                //walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " +  Math.round((Float.parseFloat(amount)* MySharedPreferenceClass.getSelectedCurrencyRate(getContext()))) + " " + "-المحفظة");
                                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                    amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + subtotal + " " + "إجمالي الطلب");

                                } else {
                                    amounttext.setText("Total" + " " + subtotal + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                                }

                            }

                        }
                        if (selectpaymentflag == 1 || selectpaymentflag == 0 /*&& amounttext.getText().toString().split(" ")[1].equals("0")*/) {
                            if (amounttext.getText().toString().split(" ")[1].equals("0")) {
                                cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                                selectpaymentflag = 0;
                                edt_card_detail.setVisibility(View.GONE);

                                initSavedCardRecycler(); // changes
                            }
                        } else if (selectpaymentflag == 2 /*&& amounttext.getText().toString().split(" ")[1].equals("0")*/) {

                            if (amounttext.getText().toString().split(" ")[1].equals("0")) {


                                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                    walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + Math.round((Float.parseFloat(amount))) + " " + "-المحفظة");
                                } else {
                                    walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + Math.round((Float.parseFloat(amount))));

                                }


                                codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                                selectpaymentflag = 0;
                            }

                        } else if (selectpaymentflag == 3 /*&& amounttext.getText().toString().split(" ")[1].equals("0")*/) {
                            if (amounttext.getText().toString().split(" ")[1].equals("0")) {
                                selectedcardposition = 0;
                                initSavedCardRecycler();
                                selectpaymentflag = 0;

                            }
                        }
                        if (selectpaymentflag == 4 || selectpaymentflag == 0/* && amounttext.getText().toString().split(" ")[1].equals("0")*/) {

                            if (amounttext.getText().toString().split(" ")[1].equals("0")) {
                                stcRadiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));

                                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                    walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + Math.round((Float.parseFloat(amount))) + " " + "-المحفظة");
                                } else {
                                    walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + Math.round((Float.parseFloat(amount))));

                                }

                                selectpaymentflag = 0;
                                if (callLayout.getVisibility() == View.VISIBLE) {
                                    callLayout.setVisibility(View.GONE);
                                }
                            }
                        }

                        if (response.body().needToPay.toString().equals("0")) {
                            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + "0" + " " + "إجمالي الطلب");

                            } else {
                                amounttext.setText("Total" + " " + "0" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                            }
                            selectpaymentflag = 0;
                        }

                    } else {
                        // Toast.makeText(getApplicationContext(),"fromoutside",Toast.LENGTH_SHORT).show();
                        walletcheckbox.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                        walletaddflag = false;
                        is_partialpayment = false;

                        leftwalletamount.setVisibility(View.GONE);
                        if (freshwalletAmount <= (Math.round(Float.parseFloat(amount)))) {

                            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()) + " " + "-المحفظة");
                            } else {
                                walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()));

                            }


                            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + subtotal + " " + "إجمالي الطلب");

                            } else {
                                amounttext.setText("Total" + " " + subtotal + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                            }

                            // amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + subtotal+ " " + "إجمالي الطلب");
                        } else if (freshwalletAmount > Integer.parseInt(amount)) {

                            //walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " +  Math.round((Float.parseFloat(amount)* MySharedPreferenceClass.getSelectedCurrencyRate(getContext()))) + " " + "-المحفظة");


                            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + Math.round(freshwalletAmount) + " " + "-المحفظة");
                            } else {
                                walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + Math.round(freshwalletAmount));

                            }


                            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                                amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + subtotal + " " + "إجمالي الطلب");

                            } else {
                                amounttext.setText("Total" + " " + subtotal + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                            }

                            selectpaymentflag = 99;

                        }

                    }

                    if (Math.round(Float.parseFloat(response.body().needToPay.toString())) == 0) {
                        cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                        codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                        stcRadiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                        paypalradiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                        tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
                        tamarainstallmentradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));

                    }

                    /*if (Integer.valueOf(amounttext.getText().toString().split(" ")[1]) < paylatermaxamount &&
                            Integer.valueOf(amounttext.getText().toString().split(" ")[1]) > paylaterminamount) {

                        tamaraCardClcik.setVisibility(View.VISIBLE);


                    }

                    else{

                        tamaraCardClcik.setVisibility(View.GONE);

                    }
                    if (Integer.valueOf(amounttext.getText().toString().split(" ")[1]) < payinstallmentmaxamount && Integer.valueOf(amounttext.getText().toString().split(" ")[1]) > payinstallmentminamount) {

                        tamarainstallmentscard.setVisibility(View.VISIBLE);

                    }
                    else{
                        tamarainstallmentscard.setVisibility(View.GONE);

                    }*/
                    // Toast.makeText(getContext(), "WalletApplied", Toast.LENGTH_SHORT).show();

                } else {

                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            walletaddflag = false;
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
            public void onFailure(Call<ApplyWalletResult> call, Throwable t) {
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

    public void getcodcharges() {

        //parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Map<Object, Object> map1 = new HashMap<>();


        map1.put("name", "cashondelivery");

        Call<String> call;

        if (Locale.equals("en")) {
            call = apiService.getcodecharges(map1);
        } else {
            call = apiService.getcodechargesinarabic(map1);
        }

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null && response.isSuccessful()) {
                    //parentDialogListener.hideProgressDialog();

                    String codjson = response.body().toString();

                    try {

                        String jsonFormattedString = new JSONTokener(codjson).nextValue().toString();
                        JSONObject giftjson = new JSONObject(jsonFormattedString);
                        //JSONObject jsonobj = giftjson.getJSONObject(0);
                        String codfee = giftjson.optString("fee");


                        codamount = String.valueOf(Math.round(Float.parseFloat(codfee) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext())));

                        if (Locale.equals("en")) {
                            codcharges.setText("Cash On Delivery" + " " + "+" + " " + codamount);
                        } else {
                            codcharges.setText("الدفع عند التسليم" + " " + "+" + " " + codamount);
                        }
                        System.out.println("jahsdfgjdsgf" + jsonFormattedString);

                    } catch (Exception e) {
                        System.out.println("jahsdfgjdsgf" + e.getMessage());
                    }

                }
                getUpgradedRememberCardName();
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


   /* public void getTamarapaymentUrl(String orderId) {


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        TamaraInternalOrderId = orderId;
        mainparam.put("order_reference_id", TamaraInternalOrderId);
        mainparam.put("order_number", TamaraInternalOrderId);

        Map<String, String> total_amountdata = new HashMap<>();
        total_amountdata.put("amount", amount);
        total_amountdata.put("currency", MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()));
        mainparam.put("total_amount", total_amountdata*//* amount*//*);
        mainparam.put("description", "string");
        mainparam.put("country_code", MySharedPreferenceClass.getCountryId(getApplicationContext()));


        if (selectpaymentflag == 6) {
            mainparam.put("payment_type", "PAY_BY_LATER");//PAY_BY_INSTALMENTS //PAY_BY_LATER
        } else {
            mainparam.put("payment_type", "PAY_BY_INSTALMENTS");
        }

        if (Locale.equals("en")) {
            mainparam.put("locale", "en_US");
        } else {
            mainparam.put("locale", "ar");
        }


        ArrayList<Object> itemsarray = new ArrayList<>();

        Map<Object, Object> itemsobject = new HashMap<>();

        itemsobject.put("reference_id", TamaraInternalOrderId*//*String.valueOf(ThreadLocalRandom.current().nextInt())*//*);
        itemsobject.put("type", "Digital");
        itemsobject.put("name", " ");
        itemsobject.put("sku", " ");
        itemsobject.put("image_url", " ");
        itemsobject.put("quantity", 1);

        Map<String, String> unitprice = new HashMap<>();
        unitprice.put("amount", amount);
        unitprice.put("currency", MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()));


        Map<Object, Object> discountamount = new HashMap<>();


        discountamount.put("amount", "0");
        discountamount.put("currency", MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()));
        Map<Object, Object> taxamount = new HashMap<>();

        taxamount.put("amount", "0");

        taxamount.put("currency", MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()));
        itemsobject.put("unit_price", unitprice);
        itemsobject.put("discount_amount", discountamount);
        itemsobject.put("tax_amount", taxamount);
        itemsobject.put("total_amount", total_amountdata);
        itemsarray.add(itemsobject);

        mainparam.put("items", itemsarray);


        Map<Object, Object> consumer = new HashMap<>();
        Map<String, Object> consumerdetail = new HashMap<>();
        consumerdetail.put("first_name", MySharedPreferenceClass.getfirstAdressname(getApplicationContext()));
        consumerdetail.put("last_name", MySharedPreferenceClass.getlastAdressname(getApplicationContext()));
        consumerdetail.put("phone_number", MySharedPreferenceClass.getAdressphone(getApplicationContext()));
        consumerdetail.put("email", MySharedPreferenceClass.getEmail(getApplicationContext()));
        consumerdetail.put("national_id", "");
        consumerdetail.put("date_of_birth", "");
        consumerdetail.put("is_first_order", false);


        mainparam.put("consumer", consumerdetail);


        Map<Object, Object> billingaddress = new HashMap<>();
        Map<String, Object> billingaddressdetail = new HashMap<>();
        billingaddressdetail.put("first_name", MySharedPreferenceClass.getfirstAdressname(getApplicationContext()));
        billingaddressdetail.put("last_name", MySharedPreferenceClass.getlastAdressname(getApplicationContext()));
        billingaddressdetail.put("phone_number", MySharedPreferenceClass.getAdressphone(getApplicationContext()));
        billingaddressdetail.put("line1", MySharedPreferenceClass.getStreetname(getApplicationContext()));
        billingaddressdetail.put("line2", MySharedPreferenceClass.getCityName(getApplicationContext()));
        billingaddressdetail.put("region", MySharedPreferenceClass.getCountryId(getApplicationContext()));
        billingaddressdetail.put("postal_code", " ");
        billingaddressdetail.put("city", MySharedPreferenceClass.getCityName(getApplicationContext()));
        billingaddressdetail.put("country_code", MySharedPreferenceClass.getCountryId(getApplicationContext()));

        mainparam.put("billing_address", billingaddressdetail);


        //Map<Object, Object> shippinggaddress = new HashMap<>();
        Map<String, Object> shippinggaddressdetail = new HashMap<>();
        shippinggaddressdetail.put("first_name", MySharedPreferenceClass.getfirstAdressname(getApplicationContext()));
        shippinggaddressdetail.put("last_name", MySharedPreferenceClass.getlastAdressname(getApplicationContext()));
        shippinggaddressdetail.put("phone_number", MySharedPreferenceClass.getAdressphone(getApplicationContext()));
        shippinggaddressdetail.put("line1", MySharedPreferenceClass.getStreetname(getApplicationContext()));
        shippinggaddressdetail.put("line2", MySharedPreferenceClass.getCityName(getApplicationContext()));
        shippinggaddressdetail.put("region", MySharedPreferenceClass.getCountryId(getApplicationContext()));
        shippinggaddressdetail.put("postal_code", " ");
        shippinggaddressdetail.put("city", MySharedPreferenceClass.getCityName(getApplicationContext()));
        shippinggaddressdetail.put("country_code", MySharedPreferenceClass.getCountryId(getApplicationContext()));
        mainparam.put("shipping_address", billingaddressdetail);
        mainparam.put("tax_amount", taxamount);


        Map<String, Object> shipping_amountdetail = new HashMap<>();
        shipping_amountdetail.put("amount", amount);
        shipping_amountdetail.put("currency", MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()));

        mainparam.put("shipping_amount", shipping_amountdetail);
        Map<String, Object> merchanturls = new HashMap<>();


        merchanturls.put("failure", "https://upgrade.eoutlet.com/webservice/index/tamarafailed");
        merchanturls.put("cancel", "https://upgrade.eoutlet.com/webservice/index/tamaracancel");
        merchanturls.put("notification", "https://upgrade.eoutlet.com/webservice/index/tamaranotify");

        merchanturls.put("success", "https://upgrade.eoutlet.com/webservice/index/tamararesponse");

        mainparam.put("merchant_url", merchanturls);

        mainparam.put("platform", "Android");
        mainparam.put("is_mobile", true);


        Map<String, Object> risk_assesmentdetail = new HashMap<>();
        risk_assesmentdetail.put("has_cod_failed", false);
        mainparam.put("risk_assesment", risk_assesmentdetail);


        // mainparam.put("param",map1);

        Call<TamaraPaymentUrlResponse> call = apiService.getTamaraCheckoutUrl("Bearer " + Constants.AUTH_TOKEN, mainparam);
        call.enqueue(new Callback<TamaraPaymentUrlResponse>() {
            @Override
            public void onResponse(Call<TamaraPaymentUrlResponse> call, Response<TamaraPaymentUrlResponse> response) {


                if (response.body() != null && response.isSuccessful()) {
                    parentDialogListener.hideProgressDialog();
                    TamaraOrderId = response.body().orderId;

                    Log.e("TamaraOrderId", TamaraOrderId);

                    TamaraPayment.Companion.startPayment(getActivity(), response.body().checkoutUrl, "https://upgrade.eoutlet.com/webservice/index/tamararesponse", "https://upgrade.eoutlet.com/webservice/index/tamarafailed", "https://upgrade.eoutlet.com/webservice/index/tamaracancel");


                } else {
                    parentDialogListener.hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Your Request is Invalid", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<TamaraPaymentUrlResponse> call, Throwable t) {

                Log.e(TAG, t.toString());
                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
       // Log.e("from webview-->>","from payment webview");
        //Toast.makeText(getContext(),"formOnactivityresult",Toast.LENGTH_SHORT).show();


        if (TamaraPaymentHelper.Companion.shouldHandleActivityResult(requestCode, resultCode, data)) {
            PaymentResult result = TamaraPaymentHelper.Companion.getData(data);

            if (result.getStatus() == PaymentResult.STATUS_CANCEL) {
                getOrderDetailforTamara(TamarathreedigitId, "cancel");

            } else if (result.getStatus() == PaymentResult.STATUS_FAILURE) {

                getOrderDetailforTamara(TamarathreedigitId, "failed");

            } else if (result.getStatus() == PaymentResult.STATUS_SUCCESS) {


                getOrderDetailforTamara(TamarathreedigitId, "success");


            }


            {


            }

        }
    }


    public void sendTamarStatustoWeb(String Status) {
/*{
    "param":{

        "order_id":173
    }
}*/
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Map<Object, Object> map1 = new HashMap<>();

        Map<Object, Object> map2 = new HashMap<>();

        Map<Object, Object> map3 = new HashMap<>();
        map2.put("order_id", TamaraInternalOrderId/*TamarathreedigitId*/);
        map2.put("tamara_order_id", TamaraOrderId);
        map2.put("status", Status);
        map2.put("device", "android");
        map1.put("param", map2);

        Call<TamaraOrderResponse> call = apiService.sendTamarSatustoWeb(map1);


        call.enqueue(new Callback<TamaraOrderResponse>() {
            @Override
            public void onResponse(Call<TamaraOrderResponse> call, Response<TamaraOrderResponse> response) {


                if (response.body() != null && response.isSuccessful()) {
                    parentDialogListener.hideProgressDialog();
                    Intent in = new Intent(getContext(), ThankyouActivity.class);
                    in.putExtra("orderId", response.body().orderId);
                    in.putExtra("statusflag", Status);
                    in.putExtra("totalvalue", amounttext.getText().toString().split(" ")[1]);
                    in.putExtra("coupencode", coupencode);
                    //in.putExtra("coupencode",coupencode);
                    startActivity(in);


                } else {
                    parentDialogListener.hideProgressDialog();
                    Intent in = new Intent(getContext(), ThankyouActivity.class);
                    in.putExtra("orderId", TamaraInternalOrderId);
                    in.putExtra("statusflag", Status);
                    in.putExtra("totalvalue", amounttext.getText().toString().split(" ")[1]);
                    in.putExtra("coupencode", coupencode);
                    //in.putExtra("coupencode",coupencode);
                    startActivity(in);
                }
            }

            @Override
            public void onFailure(Call<TamaraOrderResponse> call, Throwable t) {
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

    public void getUpgradedLatestversionCode() {


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Call<GetCategoryCode> call = apiService.getUpgradedLatestAppVersion();
        call.enqueue(new Callback<GetCategoryCode>() {
            @Override
            public void onResponse(Call<GetCategoryCode> call, Response<GetCategoryCode> response) {


                if (response.body() != null) {

                    if (response.body().response.equals("success")) {

                        paylatermaxamount = Integer.valueOf(response.body().paylaterMaxAmount);
                        paylaterminamount = Integer.valueOf(response.body().paylaterMinAmount);
                        payinstallmentmaxamount = Integer.valueOf(response.body().payinstallmentMaxAmount);
                        payinstallmentminamount  = Integer.valueOf(response.body().payinstallmentMinAmount);

                        if (Integer.valueOf(amounttext.getText().toString().split(" ")[1]) <= Integer.valueOf(response.body().paylaterMaxAmount) &&
                                Integer.valueOf(amounttext.getText().toString().split(" ")[1]) >= Integer.valueOf(response.body().paylaterMinAmount)) {

                               //tamaraCardClcik.setVisibility(View.VISIBLE);


                        }
                        else{
                            tamaraCardClcik.setVisibility(View.GONE);

                        }
                        if (Integer.valueOf(amounttext.getText().toString().split(" ")[1]) <= Integer.valueOf(response.body().payinstallmentMaxAmount) && Integer.valueOf(amounttext.getText().toString().split(" ")[1]) >=Integer.valueOf(response.body().payinstallmentMinAmount)) {

                           tamarainstallmentscard.setVisibility(View.VISIBLE);

                        }

                        else{
                            tamaraCardClcik.setVisibility(View.GONE);

                        }
                        if (!MySharedPreferenceClass.getCountryId(getApplicationContext()).equalsIgnoreCase("SA")) {

                            tamaraCardClcik.setVisibility(View.GONE);
                            tamarainstallmentscard.setVisibility(View.GONE);


                        }
                    }

                }


            }


            @Override
            public void onFailure(Call<GetCategoryCode> call, Throwable t) {

                Log.e(TAG, t.toString());


            }
        });


    }
  public void checkTamaraVisibility()
  {

      if (Integer.valueOf(amounttext.getText().toString().split(" ")[1]) < paylatermaxamount &&
              Integer.valueOf(amounttext.getText().toString().split(" ")[1]) > paylaterminamount) {

       //   tamaraCardClcik.setVisibility(View.VISIBLE);


      }

      else{

          tamaraCardClcik.setVisibility(View.GONE);

      }
      if (Integer.valueOf(amounttext.getText().toString().split(" ")[1]) <= payinstallmentmaxamount && Integer.valueOf(amounttext.getText().toString().split(" ")[1]) >= payinstallmentminamount) {

        tamarainstallmentscard.setVisibility(View.VISIBLE);

      }
      else{
          tamarainstallmentscard.setVisibility(View.GONE);

      }

      if (!MySharedPreferenceClass.getCountryId(getContext()).equalsIgnoreCase("SA")) {

          tamaraCardClcik.setVisibility(View.GONE);
          tamarainstallmentscard.setVisibility(View.GONE);


      }
  }

    public void removeapplycoupen() {

        /*https://upgrade.eoutlet.com/rest/en/V1/carts/426/coupons*/

        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        HashMap<String, String> headers = new HashMap();
        headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getToken(getContext()));
        headers.put("Cookie", MySharedPreferenceClass.getCookies(getContext()));
        Call<Boolean> call = apiService.Upgraderemovecode(headers, Constants.UPGRADED_HOST_LINK + "rest/en/V1/carts/" + String.valueOf(MySharedPreferenceClass.getCartId(getContext())) + "/coupons");
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {


                if (response.body() != null) {

                    if (response.body() && response.isSuccessful()) {
                        parentDialogListener.hideProgressDialog();
                        coupencode = " ";
                        //getPaymentMethod();
                        getShippingMethod();
                        CheckOutConfirmationfragment.getInstance().removeapplycoupen();







                    }


                } else {
                    parentDialogListener.hideProgressDialog();


                }

            }


            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
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

    public void getShippingMethod() {


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();

        //map1.put("region", MySharedPreferenceClass.getCountryId(getApplicationContext()));


        ArrayList<String> street = new ArrayList<>();
        street.add(MySharedPreferenceClass.getStreetname(getApplicationContext()));
        map1.put("street", street);


        map1.put("country_id", MySharedPreferenceClass.getCountryId(getApplicationContext()));
        // map1.put("postcode", "11501");
        map1.put("city", MySharedPreferenceClass.getCityName(getContext()));
        map1.put("firstname", MySharedPreferenceClass.getfirstAdressname(getContext()));
        map1.put("lastname", MySharedPreferenceClass.getlastAdressname(getContext()));
        map1.put("customer_id", Integer.parseInt(MySharedPreferenceClass.getMyUserId(getContext())));
        map1.put("email", MySharedPreferenceClass.getEmail(getContext()));
        map1.put("telephone", MySharedPreferenceClass.getAdressphone(getApplicationContext()));
        map1.put("same_as_billing", 1);
        mainparam.put("address", map1);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getCustomerToken(getContext()));

        headers.put("Cookie", MySharedPreferenceClass.getCookies(getApplicationContext()));


        Call<List<GetShippingMethod>> call;
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            call = apiService.getUpgradedShippingMethod(headers, mainparam);
        } else {
            call = apiService.getUpgradedShippingMethodArabic(headers, mainparam);

        }

        call.enqueue(new Callback<List<GetShippingMethod>>() {
            @Override
            public void onResponse(Call<List<GetShippingMethod>> call, Response<List<GetShippingMethod>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        // parentDialogListener.hideProgressDialog();
                        carrier_code = response.body().get(0).carrierCode;
                        method_code = response.body().get(0).methodCode;
                        getPaymentMethod();
                        //sendcountryselction();


                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetShippingMethod>> call, Throwable t) {
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
    public void getPaymentMethod() {


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> externalmainparam = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> addresses = new HashMap<>();


        map1.put("region", MySharedPreferenceClass.getCountryId(getApplicationContext()));


        ArrayList<String> street = new ArrayList<>();
        street.add(MySharedPreferenceClass.getStreetname(getContext()));
        map1.put("street", street);


        map1.put("country_id", MySharedPreferenceClass.getCountryId(getApplicationContext()));
        // map1.put("postcode", "11501");
        map1.put("city", MySharedPreferenceClass.getCityName(getContext()));
        map1.put("firstname", MySharedPreferenceClass.getfirstAdressname(getContext()));
        map1.put("lastname", MySharedPreferenceClass.getlastAdressname(getContext()));
        map1.put("customer_id", Integer.parseInt(MySharedPreferenceClass.getMyUserId(getContext())));
        map1.put("email", MySharedPreferenceClass.getEmail(getContext()));
        map1.put("telephone", MySharedPreferenceClass.getAdressphone(getContext()));

        addresses.put("shipping_address", map1);


        map2.put("region", "SA");


        map2.put("street", street);


        map2.put("country_id", "SA");
        //  map2.put("postcode", "11501");
        map2.put("city", MySharedPreferenceClass.getCityName(getContext()));
        map2.put("firstname", MySharedPreferenceClass.getfirstAdressname(getContext()));
        map2.put("lastname", MySharedPreferenceClass.getlastAdressname(getContext()));
        map2.put("customer_id", Integer.parseInt(MySharedPreferenceClass.getMyUserId(getContext())));
        map2.put("email", MySharedPreferenceClass.getEmail(getContext()));
        map2.put("telephone", MySharedPreferenceClass.getAdressphone(getContext()));
        addresses.put("billing_address", map2);
        addresses.put("shipping_carrier_code", carrier_code);
        addresses.put("shipping_method_code", method_code);


        mainparam.put("addressInformation", addresses);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getCustomerToken(getContext()));
        headers.put("Cookie", MySharedPreferenceClass.getCookies(getApplicationContext()));

        Call<GetPaymentInfo> call;
        //Call<List<GetShippingMethod>> call = apiService.getUpgradedShippingMethod("Bearer 0fdxvtld18ugpwwo5l0eu5ft4ksu2dte",mainparam);


        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            call = apiService.getUpgradedPaymentMethod(headers, mainparam);
        } else {
            call = apiService.getUpgradedPaymentMethodArabic(headers, mainparam);
        }

        call.enqueue(new Callback<GetPaymentInfo>() {
            @Override
            public void onResponse(Call<GetPaymentInfo> call, Response<GetPaymentInfo> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    amount =  String.valueOf(Math.round(response.body().totals.totalSegments.get(0).value));
                    if(!isinstallationtamaraflag) {
                        onselectTamaraLater();
                    }
                    else{

                        onselectTamaraInstallments();
                    }

                }
            }
            @Override
            public void onFailure(Call<GetPaymentInfo> call, Throwable t) {
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
    public void openCoupenRemoveDialog()
    {
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);


            alertDialogBuilder.setMessage("سيتم إزالة كود الخصم لطريقة الدفع هذه");

        alertDialogBuilder.setPositiveButton("نعم",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        removeapplycoupen();
                    }
                });

        alertDialogBuilder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                //alertDialog.dismiss();

            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    } else {
            final AlertDialog alertDialog;
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

            alertDialogBuilder.setMessage("Coupon Code is going to remove for this payment method");


            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            removeapplycoupen();


                        }
                    });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {
                    //alertDialog.dismiss();

                }
            });
            alertDialog = alertDialogBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();


        }
    }
    public void onselectTamaraLater()
    {

        initSavedCardRecycler();
        selectpaymentflag = 6;


        if (amounttext.getText().toString().split(" ")[1].equals("0")) {

            walletcheckbox.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
            walletaddflag = false;
            is_partialpayment = false;


            int mainwalletamount = Math.round(Float.parseFloat(MySharedPreferenceClass.getWalletamount(getContext())) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()) + " " + "-المحفظة");
            } else {
                walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()));

            }


            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "إجمالي الطلب");

            } else {
                amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

            }


            leftwalletamount.setVisibility(View.GONE);

        } else {
            if (walletaddflag) {
                int balance = Math.round(Float.parseFloat(amount)) - freshwalletAmount;


                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                    amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + balance + " " + "إجمالي الطلب");

                } else {
                    amounttext.setText("Total" + " " + balance + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                }


            } else {

                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                    amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "إجمالي الطلب");

                } else {
                    amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                }

            }

        }
        paypalradiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
        stcRadiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
        cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
        codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
        tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));
        tamarainstallmentradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
        if (edt_card_detail.getVisibility() == View.VISIBLE) {
            edt_card_detail.setVisibility(View.GONE);
        }
        selectedcardposition = 0;

        if (Integer.valueOf(amounttext.getText().toString().split(" ")[1]) < paylatermaxamount &&
                Integer.valueOf(amounttext.getText().toString().split(" ")[1]) > paylaterminamount) {

          //  tamaraCardClcik.setVisibility(View.VISIBLE);


        }

        else{

            tamaraCardClcik.setVisibility(View.GONE);

        }
        if (Integer.valueOf(amounttext.getText().toString().split(" ")[1]) < payinstallmentmaxamount && Integer.valueOf(amounttext.getText().toString().split(" ")[1]) > payinstallmentminamount) {

          //  tamarainstallmentscard.setVisibility(View.VISIBLE);

        }
        else{
            tamarainstallmentscard.setVisibility(View.GONE);

        }


    }

    public void onselectTamaraInstallments()
    {
        initSavedCardRecycler();
        selectpaymentflag = 7;


        if (amounttext.getText().toString().split(" ")[1].equals("0")) {

            walletcheckbox.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
            walletaddflag = false;
            is_partialpayment = false;


            int mainwalletamount = Math.round(Float.parseFloat(MySharedPreferenceClass.getWalletamount(getContext())) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                walletamount.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()) + " " + "-المحفظة");
            } else {
                walletamount.setText("Wallet -" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + MySharedPreferenceClass.getWalletamount(getContext()));

            }


            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "إجمالي الطلب");

            } else {
                amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

            }


            leftwalletamount.setVisibility(View.GONE);

        } else {
            if (walletaddflag) {
                int balance = Math.round(Float.parseFloat(amount)) - freshwalletAmount;

                //int subtotal = Math.round((balance) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                    amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + balance + " " + "إجمالي الطلب");

                } else {
                    amounttext.setText("Total" + " " + balance + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                }


            } else {
                //int subtotal = Math.round(Float.parseFloat(amount) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext()));
                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                    amounttext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + amount + " " + "إجمالي الطلب");

                } else {
                    amounttext.setText("Total" + " " + amount + " " + MySharedPreferenceClass.getSelectedCurrencyName(getContext()));

                }

            }

        }
        paypalradiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
        stcRadiobutton.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
        cardradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
        codradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
        tamararadiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
        tamarainstallmentradiobtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));


        if (edt_card_detail.getVisibility() == View.VISIBLE) {
            edt_card_detail.setVisibility(View.GONE);
        }
        selectedcardposition = 0;

        if (Integer.valueOf(amounttext.getText().toString().split(" ")[1]) < paylatermaxamount &&
                Integer.valueOf(amounttext.getText().toString().split(" ")[1]) > paylaterminamount) {

          //  tamaraCardClcik.setVisibility(View.VISIBLE);


        }

        else{

            tamaraCardClcik.setVisibility(View.GONE);

        }
        if (Integer.valueOf(amounttext.getText().toString().split(" ")[1]) < payinstallmentmaxamount && Integer.valueOf(amounttext.getText().toString().split(" ")[1]) > payinstallmentminamount) {

           // tamarainstallmentscard.setVisibility(View.VISIBLE);

        }
        else{
            tamarainstallmentscard.setVisibility(View.GONE);

        }

    }
}