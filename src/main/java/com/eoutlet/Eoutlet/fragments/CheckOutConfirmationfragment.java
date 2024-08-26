package com.eoutlet.Eoutlet.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.PaymentGateway.PaymentActivity;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.activities.ThankyouActivity;
import com.eoutlet.Eoutlet.adpters.AZAdapter;
import com.eoutlet.Eoutlet.adpters.CheckOutAdapter;
import com.eoutlet.Eoutlet.adpters.CheckOutCalculationAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;

import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.listener.CartListener;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.listener.ViewListener4;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.ApplyCoupenResult;
import com.eoutlet.Eoutlet.pojo.ClearCodSelection;
import com.eoutlet.Eoutlet.pojo.CurrencysetResponse;
import com.eoutlet.Eoutlet.pojo.GetPaymentInfo;
import com.eoutlet.Eoutlet.pojo.GetShippingCharge;
import com.eoutlet.Eoutlet.pojo.GetShippingMethod;
import com.eoutlet.Eoutlet.pojo.GetToken;
import com.eoutlet.Eoutlet.pojo.GetUserProfile;
import com.eoutlet.Eoutlet.pojo.Giftwrapdetail;
import com.eoutlet.Eoutlet.pojo.ItemGift;
import com.eoutlet.Eoutlet.pojo.OrderResponse;
import com.eoutlet.Eoutlet.pojo.OrderResponseCod;
import com.eoutlet.Eoutlet.pojo.PayFortData;
import com.eoutlet.Eoutlet.pojo.PaymentMethod;
import com.eoutlet.Eoutlet.pojo.SignUpResponse;
import com.eoutlet.Eoutlet.pojo.TotalSegment;
import com.eoutlet.Eoutlet.pojo.UpgradeQuantity;
import com.eoutlet.Eoutlet.pojo.UpgradedCartData;
import com.eoutlet.Eoutlet.pojo.UpgradedCartItems;
import com.eoutlet.Eoutlet.pojo.UpgradedItem;
import com.eoutlet.Eoutlet.pojo.ViewCart1;
import com.eoutlet.Eoutlet.pojo.ViewCartData;
import com.eoutlet.Eoutlet.utility.Constants;
import com.eoutlet.Eoutlet.utility.Util;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.moe.pushlibrary.MoEHelper;
import com.moengage.core.Properties;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HeaderMap;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;


public class CheckOutConfirmationfragment extends Fragment implements CartListener {


    View view;
    private RecyclerView recyclerViewCheckOut;
    private CheckOutAdapter checkOutAdapter;
    private CheckOutCalculationAdapter calculationadapter;
    private ExecuteFragment execute;
    View sheetView;
    private String carrier_code, method_code;
    boolean islayoutvisible = false;
    private String orderId;
    private CartListener cartreference;
    public ParentDialogsListener parentDialogListener;
    private String deviceId = "", sdkToken = "";
    private FirebaseAnalytics mFirebaseAnalytics;
    public boolean stockflag = true;
    private ImageView crossimage;
    private TextView cityName;
    private UpgradedCartItems upgradedcartData;
    private String cartId = " ";
    private String Currency = " ";
    private LinearLayout addgiftbox;
    private TextView addgiftBoxText;
    private List<TotalSegment> totalSegments = new ArrayList<>();
    private List<ItemGift> giftarray = new ArrayList<>();
    boolean giftcheckflag = false;
    private String giftmessage = " ";
    private FrameLayout checkoutcontainer;
    private String Locale = " ";

    private List<PaymentMethod> paymentmethods = new ArrayList<>();
    private boolean is_giftboxadded;
    private static CheckOutConfirmationfragment instance = null;

    /*String ACCESS_CODE = "Se8GBcDzHUIqpRvyzkmT";
    private final static String MERCHANT_IDENTIFIER = "uRelyHKU";
    private final static String SHA_REQUEST_PHRASE = "TESTSHAIN";
    public final static String SHA_RESPONSE_PHRASE = "asdadseeerg";
*/

    private final static String MERCHANT_IDENTIFIER = "VwaZsGPf";  //Live
    private final static String SHA_REQUEST_PHRASE = "$2y$10$RiIngXYpb"; //Live
    public final static String SHA_RESPONSE_PHRASE = "$2y$10$CaO9SBtke";//Live


    private ImageView radio1, radio2;

    private static String totaltax/* shippingcharges*/;
    private static TextView tvshippingcharge, tvtotaltax, giftwrappingcharge;
    int codcharges;
    boolean coupenflag = false;
    static int shippingchargebycountry = 0;
    String shippingtitle = "الشحن", shippingmethod;
    static int dicountamount;
    private String tax_apply = " ";
    private final Gson gson = new Gson();

    int radioflag = 0;

    private ImageView searchImage, backarrow;
    private Toolbar toolbar1;

    private ImageView giftboxImage, giftadd, giftmessagechceck;

    private RelativeLayout addgiftboxmsg;

    private TextView title, giftboxmessage, giftmsgtext;

    // private final static String TEST_TOKEN_URL = "http://dev.eoutlet.net/webservice/sbpaymentservices.php ";

    // private final static String TEST_TOKEN_URL = "https://sbpaymentservices.payfort.com/FortAPI/paymentApi";

    //private final static String TEST_TOKEN_URL = "https://paymentservices.payfort.com/FortAPI/paymentApi";
    private RelativeLayout confirmOrder;

    private TextView tvshippingtitle, applycode, tvtotalprice, tvtotalwithTax, newaddress;

    public static TextView tvaddressName, tvstreet, tvCity, tvCountry, tvPhone;

    private List<ViewCartData> getdata = new ArrayList<>();
    private ArrayList<String> productPics = new ArrayList<>();
    private ArrayList<String> productname = new ArrayList<>();
    private ArrayList<String> productprice = new ArrayList<>();
    private ArrayList<String> productsize = new ArrayList<>();
    private ArrayList<String> productquant = new ArrayList<>();
    LinearLayout firstRadiobuttonlayout, secondRadioButtonlayout;
    String totalprice, totatlpricewithtax, giftwrapping = " ", code_label;
    private EditText edtcoupencode;
    private TextView codcharge, plusbutton, codtext;
    private String coupencode = " ", discounntamount = " ";

    private TextView coupenAmount, coupentext;
    private RelativeLayout coupenlayout/*, addresslayotut*/;

    public static RelativeLayout addresslayotut;
    private NestedScrollView scroll;

    private LinearLayout coupanmainlayout, checkoutmainlayout;
    private RecyclerView checkoutcalculationrecycler;

    private boolean is_giftwrap = false;
    private int priceaftercoupencodeappliy;
    private FrameLayout giftwrapsubmit, giftwrapcancel, giftwrapremove;
    private BottomSheetDialog mBottomSheetDialog;
    private OnFragmentInteractionListener mListener;

    public CheckOutConfirmationfragment() {

    }

    public CheckOutConfirmationfragment(CartFragment cl) {

        cartreference = cl;


    }

    public static CheckOutConfirmationfragment newInstance(String param1, String param2) {
        CheckOutConfirmationfragment fragment = new CheckOutConfirmationfragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);


        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();

            coupencode = b.getString("coupencode");
            System.out.println("coupancodecheckout" + coupencode);
            discounntamount = b.getString("discounntamount");
            giftwrapping = b.getString("giftwrapping");
            code_label = b.getString("cod_label");


            System.out.println("discountamoutcheckout" + discounntamount);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        instance = this;
        productPics = new ArrayList<>();
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            Locale = "en";
            view = inflater.inflate(R.layout.fragment_check_out_confirmationfragment_english, container, false);
        } else {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_check_out_confirmationfragment, container, false);
        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());

        tvtotaltax = view.findViewById(R.id.cototaltax);
        giftwrappingcharge = view.findViewById(R.id.checkoutgiftwrappingcharge);

        tvshippingcharge = view.findViewById(R.id.shippingcharges1);
        coupenAmount = view.findViewById(R.id.discoutnamount);
        coupentext = view.findViewById(R.id.coupenname);
        tvshippingtitle = view.findViewById(R.id.shippingtitle);
        codcharge = view.findViewById(R.id.codcharge);
        codtext = view.findViewById(R.id.codtext);


        coupenlayout = view.findViewById(R.id.coupan_layout);
        addresslayotut = view.findViewById(R.id.addresslayout);
        scroll = view.findViewById(R.id.scroll);
        plusbutton = view.findViewById(R.id.plus_button);

        coupanmainlayout = view.findViewById(R.id.coupan_mainLayout);
        applycode = view.findViewById(R.id.coupansubmit);
        crossimage = view.findViewById(R.id.crossimage);
        checkoutcalculationrecycler = view.findViewById(R.id.checkoutcalcultionrecycler);
        addgiftbox = view.findViewById(R.id.addgiftbox);
        addgiftBoxText = view.findViewById(R.id.giftboxtext);
        checkoutcontainer = view.findViewById(R.id.checkoutContainer);
        checkoutmainlayout = view.findViewById(R.id.checkoutmainlayout);


        if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {


            //getTokenID();
            clearCodSelection();


        }
        crossimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                removeapplycoupen();


            }
        });
        coupanmainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!islayoutvisible) {


                    Animation animShow = AnimationUtils.loadAnimation(getContext(), R.anim.view_show);


                    //coupenlayout.startAnimation( animShow );
                    coupenlayout.setVisibility(View.VISIBLE);
                    coupenlayout.requestFocus();


// Start the animation

                    islayoutvisible = true;


                } else {


                    Animation animHide = AnimationUtils.loadAnimation(getContext(), R.anim.view_hide);
                    coupenlayout.setVisibility(View.GONE);


                    islayoutvisible = false;


                }
            }
        });
        plusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!islayoutvisible) {


                    Animation animShow = AnimationUtils.loadAnimation(getContext(), R.anim.view_show);


                    //coupenlayout.startAnimation( animShow );
                    coupenlayout.setVisibility(View.VISIBLE);
                    coupenlayout.requestFocus();

                    if (Locale.equals("ar")) {
                        plusbutton.setText(" رمز الخصم - ");
                    } else {
                        plusbutton.setText("Discount Code - ");
                    }


// Start the animation

                    islayoutvisible = true;


                } else {
                    if (Locale.equals("ar")) {
                        plusbutton.setText(" رمز الخصم + ");
                    } else {
                        plusbutton.setText("Discount Code + ");
                    }
                    Animation animHide = AnimationUtils.loadAnimation(getContext(), R.anim.view_hide);
                    coupenlayout.setVisibility(View.GONE);


                    islayoutvisible = false;

                }


            }
        });
        applycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtcoupencode.getText().toString().equals("")) {
                    if (coupenflag) {
                        return;
                    }
                    //setApplycode2();
                    upgradedsetApplycode();


                } else {
                    if (Locale.equals("ar")) {
                        Toast.makeText(getContext(), "من فضلك ادخل كود الخصم لتطبيق", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Please fill coupon code to apply", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        execute = (MainActivity) getActivity();

        // initUi();
//        registerListener();


        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setUserHintcall() {


        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            transaction.setReorderingAllowed(false);
        }
        transaction.detach(this).attach
                (this).commit();
        radioflag = 0;
        if (edtcoupencode != null) {
            edtcoupencode.setText(" ");
        }

        coupenflag = false;
        islayoutvisible = false;


    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void initUi() {
        firstRadiobuttonlayout = view.findViewById(R.id.firstradioButton);
        secondRadioButtonlayout = view.findViewById(R.id.secondRadioButton);
        newaddress = view.findViewById(R.id.newaddress);
        recyclerViewCheckOut = view.findViewById(R.id.recyclerViewCheckOut);
        radio1 = view.findViewById(R.id.radio1);
        radio2 = view.findViewById(R.id.radio2);
        tvaddressName = view.findViewById(R.id.addressName);
        tvstreet = view.findViewById(R.id.street);
        tvCity = view.findViewById(R.id.city);
        tvCountry = view.findViewById(R.id.coutnryName);
        tvPhone = view.findViewById(R.id.mobileNumber);
        tvtotalprice = view.findViewById(R.id.totalprice);
        tvtotalwithTax = view.findViewById(R.id.totalpricewithTax);
        confirmOrder = view.findViewById(R.id.confirmOrder);
        applycode = view.findViewById(R.id.applycode);
        edtcoupencode = view.findViewById(R.id.edtcoupen);
        coupenAmount.setText("" + "-" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()) + " " + discounntamount);

        toolbar1 = view.findViewById(R.id.toolbar);
        searchImage = toolbar1.findViewById(R.id.serachbar);
        backarrow = toolbar1.findViewById((R.id.backarrow));


    }

    private void initSetonclickListener() {


        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment prFrag = new SearchResultFragment();
                Bundle databund = new Bundle();


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.profileContainer, prFrag)
                        .commit();


            }
        });
        addgiftbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_giftboxadded) {
                    iniatializeGiftBoxDialogue();
                } else {
                    removeGiftBox();

                }


            }
        });

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });


        if (!coupencode.equals("")) {
            coupentext.setText("(" + coupencode + ")" + " " + "القسيمة");
        }


        newaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.isaddressempty = true;

                Fragment prFrag = new NewAddressFragmentUpgrade(CheckOutConfirmationfragment.this);
                Bundle databund = new Bundle();
                databund.putString("flag", "fromCheckout");
                prFrag.setArguments(databund);
                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.checkoutContainer, prFrag)
                        .commit();
                islayoutvisible = false;
                coupenlayout.setVisibility(View.GONE);
                if (Locale.equals("ar")) {
                    plusbutton.setText(" رمز الخصم + ");
                } else {
                    plusbutton.setText("Discount Code + ");
                }

            }
        });


        if (MySharedPreferenceClass.getAdressname(getContext()) != null) {

            if(MySharedPreferenceClass.getAdressname(getContext())!=null && !MySharedPreferenceClass.getAdressname(getContext()).equalsIgnoreCase("") ) {

                tvaddressName.setText(MySharedPreferenceClass.getAdressname(getContext()));

            }
            else{
                tvaddressName.setVisibility(View.GONE);
            }
            if(MySharedPreferenceClass.getStreetname(getContext())!=null && !MySharedPreferenceClass.getStreetname(getContext()).equalsIgnoreCase("") ) {

                tvstreet.setText(MySharedPreferenceClass.getStreetname(getContext())) /*+" "+MySharedPreferenceClass.getCityName(getContext())+" " +MySharedPreferenceClass.getCountryName(getContext()))*/;
            }

            else{
                tvstreet.setVisibility(View.GONE);

            }
            if(MySharedPreferenceClass.getCityName(getContext())!=null && !MySharedPreferenceClass.getCityName(getContext()).equalsIgnoreCase("") ) {

                tvCity.setText(MySharedPreferenceClass.getCityName(getContext()));

            }

            else
                {
                tvCity.setVisibility(View.GONE);
            }
            if(MySharedPreferenceClass.getCountryName(getContext())!=null && !MySharedPreferenceClass.getCountryName(getContext()).equalsIgnoreCase("") ) {


                tvCountry.setText(MySharedPreferenceClass.getCountryName(getContext()));
            }
            else{
                tvCountry.setVisibility(View.GONE);

            }

            if(MySharedPreferenceClass.getAdressphone(getContext())!=null && !MySharedPreferenceClass.getAdressphone(getContext()).equalsIgnoreCase("") ) {

                tvPhone.setText(MySharedPreferenceClass.getAdressphone(getContext()));
            }
            else{
                tvPhone.setVisibility(View.GONE);
            }

            addresslayotut.setVisibility(View.VISIBLE);


            System.out.println("mobile../" + MySharedPreferenceClass.getAdressphone(getContext()));

        } else {
            addresslayotut.setVisibility(View.GONE);
        }


        applycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtcoupencode.getText().toString().equals("")) {
                    if (!coupenflag) {


                        //setApplycode2();
                        upgradedsetApplycode();


                    } else {
                        int finaltotalvalue = 0;
                        coupenflag = false;
                        if (Locale.equals("ar")) {

                            applycode.setText("تطبيق الرصيد");
                        } else {
                            applycode.setText(" ");

                        }

                        edtcoupencode.setEnabled(true);
                        edtcoupencode.setText(null);

                        if (!MySharedPreferenceClass.getCheckboxflag(getContext())) {
                            int tvtotalpriceafterremovecoupen = Integer.parseInt(tvtotalprice.getText().toString().split(" ")[1]) + Integer.parseInt(tvshippingcharge.getText().toString().split(" ")[1]);
                            tvtotalwithTax.setText(MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()) + " " + String.valueOf(tvtotalpriceafterremovecoupen) + " " + " الاجمالي الكلي");

                        } else {

                            int tvtotalpriceafterremovecoupen = Integer.parseInt(tvtotalprice.getText().toString().split(" ")[1]) + Integer.parseInt(tvshippingcharge.getText().toString().split(" ")[1]) + Integer.parseInt(giftwrapping);
                            tvtotalwithTax.setText(MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()) + " " + String.valueOf(tvtotalpriceafterremovecoupen) + " " + " الاجمالي الكلي");

                        }

                        if (Locale.equals("ar")) {
                            coupentext.setText("القسيمة");
                        } else {


                        }


                    }
                } else {
                    if (Locale.equals("ar")) {
                        Toast.makeText(getContext(), "من فضلك ادخل كود الرصيد بشكل صحيح", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Please enter the correct coupon code", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                if (Util.checkConnection(getApplicationContext())) {
                    if (MySharedPreferenceClass.getMyUserId(getContext()) == " " || MySharedPreferenceClass.getEmail(getContext()) == null) {
                        if (Locale.equals("ar")) {
                            openSignindialog();
                        } else {
                            openSignindialogEnglis();
                        }

                        parentDialogListener.hideProgressDialog();
                        MainActivity.notificationBadge.setVisibility(View.GONE);
                        MySharedPreferenceClass.setMyUserId(getContext(), null);
                        MySharedPreferenceClass.setMyUserName(getContext(), null);
                        MySharedPreferenceClass.setMyFirstNamePref(getContext(), null);
                        MySharedPreferenceClass.setMyLastNamePref(getContext(), null);
                        MySharedPreferenceClass.setBedgeCount(getContext(), 0);
                        MySharedPreferenceClass.setEmail(getContext(), null);


                        MySharedPreferenceClass.setAddressname(getContext(), null);
                        MySharedPreferenceClass.setCityname(getContext(), null);
                        MySharedPreferenceClass.setStreetName(getContext(), null);
                        MySharedPreferenceClass.setCountryname(getContext(), null);

                        MySharedPreferenceClass.setAddressphone(getContext(), null);
                        MySharedPreferenceClass.setCountryId(getContext(), null);
                        MySharedPreferenceClass.setfirstAdressname(getContext(), null);
                        MySharedPreferenceClass.setlastAdressname(getContext(), null);
                        MySharedPreferenceClass.setSelecteAddressdId(getContext(), "0");

                    } else {
                        moengageCheckoutEvent();
                        //placeorderforpayfort();


                        if (MySharedPreferenceClass.getAdressname(getContext()) != null) {
                            if (Math.round(Float.parseFloat(tvtotalwithTax.getText().toString().split(" ")[1])) > 0) {
                                Fragment prFrag = new PaymentOptionsFragment();
                                Bundle bundle = new Bundle();

                                bundle.putSerializable("paymentmethods", (Serializable) paymentmethods);


                                bundle.putString("shippingmethod", shippingmethod);
                                bundle.putString("amount", tvtotalwithTax.getText().toString().split(" ")[1]);
                                bundle.putString("codcharges", String.valueOf(codcharges));
                                bundle.putString("codelabel", code_label);
//                            bundle.putInt("shippingcharge", Integer.parseInt(tvshippingcharge.getText().toString().split(" ")[1]));
                                bundle.putBoolean("wrappingflag", is_giftwrap);


                                if (coupenflag) {
                                    bundle.putString("coupencode", edtcoupencode.getText().toString().trim());
                                } else {
                                    bundle.putString("coupencode", " ");


                                }

                                prFrag.setArguments(bundle);

                                getFragmentManager()
                                        .beginTransaction().addToBackStack(null)
                                        .add(R.id.cartcontainer, prFrag)
                                        .commit();

                            } else {

                                placeorderforfree();


                            }


                        } else {

                            if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {

                                Toast.makeText(getContext(), "الرجاء إضافة عنوان واحد على الأقل في دفتر العناوين", Toast.LENGTH_LONG).show();
                            } else {


                                Toast.makeText(getContext(), "Please add at least one address in the address book", Toast.LENGTH_LONG).show();
                            }

                        }


                    }
                }
                else{
                    if (Locale.equals("en")) {
                        Toast.makeText(getApplicationContext(), "Please Check your Internet Connection and try again", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "يرجى التحقق من اتصالك بالإنترنت وحاول مرة أخرى", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });


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


    public void openSignindialog() {
        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        alertDialogBuilder.setMessage("يجب تسجيل الدخول أولا");


        alertDialogBuilder.setPositiveButton("نعم",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //  execute.ExecutFragmentListener(1);
                        Fragment prFrag = new LoginNewFragment();
                        Bundle databund = new Bundle();
                        databund.putString("flag", "fromcart");

                        prFrag.setArguments(databund);


                        getFragmentManager()
                                .beginTransaction().addToBackStack(null)
                                .replace(R.id.cartcontainer, prFrag)
                                .commit();
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
    }

    public void openSignindialogEnglis() {
        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        alertDialogBuilder.setMessage("You must log in first");


        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //  execute.ExecutFragmentListener(1);
                        Fragment prFrag = new LoginNewFragment();
                        Bundle databund = new Bundle();
                        databund.putString("flag", "fromcart");

                        prFrag.setArguments(databund);


                        getFragmentManager()
                                .beginTransaction().addToBackStack(null)
                                .replace(R.id.cartcontainer, prFrag)
                                .commit();
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

    public void upgradedsetApplycode() {

        /*https://upgrade.eoutlet.com/rest/en/V1/carts/{cart-id}/coupons/{coupon-code}*/


        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getToken(getContext()));
        headers.put("Cookie", MySharedPreferenceClass.getCookies(getContext()));
        Call<Boolean> call = apiService.Upgradeapplycode(headers/*"Bearer" + " " + MySharedPreferenceClass.getToken(getContext())*/, Constants.UPGRADED_HOST_LINK + "rest/en/V1/carts/" + String.valueOf(MySharedPreferenceClass.getCartId(getContext())) + "/coupons/" + edtcoupencode.getText().toString());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {


                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        //parentDialogListener.hideProgressDialog();
                        //coupenlayout.setVisibility(View.GONE);
                        if (Locale.equals("ar")) {
                            plusbutton.setText(" رمز الخصم + ");
                        } else {
                            plusbutton.setText("Discount Code + ");
                        }
                        getShippingMethod();

                        edtcoupencode.setEnabled(false);
                        coupenflag = true;
                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                            applycode.setText("إلغاء القسيمة");
                        } else {
                            applycode.setText("Cancel the coupon");

                        }


                    } else {


                    }


                } else {

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.errorBody().string());
                        String userMessage = jsonObject.getString("message");
                        Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    public void openNavigatetoCartdialog(String message) {
        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        alertDialogBuilder.setMessage(message);


        alertDialogBuilder.setPositiveButton("موافق",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
//if(cartreference!=null){
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

    public void placeorderforfree() {


        HashMap<Object, Object> initialorder = new HashMap<>();
        initialorder.put("user_id", MySharedPreferenceClass.getMyUserId(getContext()));
        initialorder.put("devicetype", "android");

        initialorder.put("payment_method", "free");
        initialorder.put("shipping_method", shippingmethod);
        if (coupenflag) {
            initialorder.put("coupon", edtcoupencode.getText().toString());
        } else {

            initialorder.put("coupon", " ");


        }

        HashMap<Object, Object> payment = new HashMap<>();
        payment.put("firstname", MySharedPreferenceClass.getMyFirstNamePref(getContext()));
        payment.put("lastname", MySharedPreferenceClass.getMyLastNamePref(getContext()));
        payment.put("city", tvCity.getText().toString());

        payment.put("country_id", MySharedPreferenceClass.getCountryId(getContext()));
        payment.put("region", MySharedPreferenceClass.getCountryId(getContext()));
        payment.put("telephone", MySharedPreferenceClass.getMobileNumber(getContext()));

        HashMap<String, String> street = new HashMap<>();
        street.put("0", tvstreet.getText().toString());
        street.put("1", " ");
        payment.put("street", street);

        initialorder.put("payment", payment);

        HashMap<Object, Object> shipping = new HashMap<>();

        shipping.put("firstname", MySharedPreferenceClass.getfirstAdressname(getContext()));
        shipping.put("lastname", MySharedPreferenceClass.getlastAdressname(getContext()));
        shipping.put("street", street);
        shipping.put("city", tvCity.getText().toString());
        shipping.put("country_id", MySharedPreferenceClass.getCountryId(getContext()));
        shipping.put("region", MySharedPreferenceClass.getCountryId(getContext()));
        shipping.put("telephone", tvPhone.getText().toString());

        initialorder.put("shipping", shipping);


        HashMap<String, String> products = new HashMap<>();


        ArrayList<Object> mainarraylist = new ArrayList<>();


        for (int i = 0; i < getdata.size(); i++) {

            HashMap<String, Object> superattribute = new HashMap<>();

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


        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));


        Call<OrderResponseCod> call = apiService.createorderCod(initialorder);
        call.enqueue(new Callback<OrderResponseCod>() {
            @Override
            public void onResponse(Call<OrderResponseCod> call, Response<OrderResponseCod> response) {


                if (response.body() != null) {


                    if (response.body().msg.equals("success")) {
                        parentDialogListener.hideProgressDialog();


                        MySharedPreferenceClass.setBedgeCount(getContext(), 0);
                        //Toast.makeText(getContext(), "Order Placed Successful", Toast.LENGTH_LONG).show();

                        Intent in = new Intent(getContext(), ThankyouActivity.class);
                        in.putExtra("orderId", response.body().orderId);
                        in.putExtra("totalvalue", tvtotalwithTax.getText().toString().split(" ")[1]);
                        in.putExtra("coupencode", edtcoupencode.getText().toString());


                        getContext().startActivity(in);

                        getFragmentManager().popBackStack();


                    } else {
                        openNavigatetoCartdialog(response.body().data.toString());
                        parentDialogListener.hideProgressDialog();
                        //Toast.makeText(getContext(), response.body().msg, Toast.LENGTH_LONG).show();

                    }


                }


            }


            @Override
            public void onFailure(Call<OrderResponseCod> call, Throwable t) {
                //  openNavigatetoCartdialog("بعض المنتجات غير متوفرة بالمخزون");
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString() + call.toString());


                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    public void getShippingMethod() {

        /*{
    "address": {
        "region": "New York",
        "region_id": 43,
        "region_code": "NY",
        "country_id": "SA",
        "street": [
            "160 1st St."
        ],
        "postcode": "11501",
        "city": "Mineola",
        "firstname": "Jane",
        "lastname": "Doe",
        "customer_id": 83188,
        "email": "jdoe@example.com",
        "telephone": "(516) 555-1111",
        "same_as_billing": 1
    }
}*/
       /* {"address":
       {"firstname":"Yasmine","city":"Dubai","region_id":43,
       "postcode":"11501","telephone":"(516) 555-1111",
       "same_as_billing":1,
       "lastname":"Tibi",
       "street":[
       "Dso"
       ],"region":"New York",
       "customer_id":83188,
       "country_id":"SA",
       "email":"arpan@unyscape.com",
       "region_code":"NY"}}*/

        /*{
    "address": {
        "region": "New York",
        "region_id": 43,
        "region_code": "NY",
        "country_id": "SA",
        "street": [
            "160 1st St."
        ],
        "postcode": "11501",
        "city": "Mineola",
        "firstname": "Jane",
        "lastname": "Doe",
        "customer_id": 83188,
        "email": "jdoe@example.com",
        "telephone": "(516) 555-1111",
        "same_as_billing": 1
    }
}*/


        /*https://upgrade.eoutlet.com/rest/en/V1/carts/mine/estimate-shipping-methods*/

 /*  map1.put("region", MySharedPreferenceClass.getCityName(getContext()));

        map1.put("region_id", "43");

        ArrayList<String> street = new ArrayList<>();
        street.add(MySharedPreferenceClass.getStreetname(getContext()));
        map1.put("street", street);

        map1.put("region_code", "NY");
        map1.put("country_id", MySharedPreferenceClass.getCountryId(getContext()));
        map1.put("postcode", "11501");
        map1.put("city", MySharedPreferenceClass.getCityName(getContext()));
        map1.put("firstname", MySharedPreferenceClass.getfirstAdressname(getContext()));
        map1.put("lastname", MySharedPreferenceClass.getlastAdressname(getContext()));
        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        map1.put("email", MySharedPreferenceClass.getEmail(getContext()));
        map1.put("telephone", MySharedPreferenceClass.getAdressphone(getContext()));
        map1.put("same_as_billing", 1);*/
        // parentDialogListener.showProgressDialog();
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
/*{
"addressInformation": {
    "shipping_address": {
        "region": "New York",
        "region_id": 43,
        "region_code": "NY",
        "country_id": "SA",
        "street": [
            "160 1st St."
        ],
        "postcode": "11501",
        "city": "Mineola",
        "firstname": "Jane",
        "lastname": "Doe",
        "email": "jdoe@example.com",
        "telephone": "516-555-1111"
    },
    "billing_address": {
        "region": "New York",
        "region_id": 43,
        "region_code": "NY",
        "country_id": "SA",
        "street": [
            "160 1st St."
        ],
        "postcode": "11501",
        "city": "Mineola",
        "firstname": "Jane",
        "lastname": "Doe",
        "email": "jdoe@example.com",
        "telephone": "516-555-1111"
    },
    "shipping_carrier_code": "freeshipping",
    "shipping_method_code": "freeshipping"
    }
}
*/


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
                    initUi();
                    int grandtotal = Math.round(response.body().totals.baseGrandTotal * MySharedPreferenceClass.getSelectedCurrencyRate(view.getContext()));


                    tvtotalwithTax.setText(MySharedPreferenceClass.getSelectedCurrencyName(view.getContext()) + " " + +grandtotal + " الاجمالي الكلي");

                    coupenAmount.setText(response.body().totals.discountAmount.toString());
                    tvtotalprice.setText(response.body().totals.subtotal.toString());
                    tvshippingcharge.setText(response.body().totals.shippingAmount.toString());
                    tvshippingtitle.setText(response.body().totals.totalSegments.get(3).title);


                    totalSegments = response.body().totals.totalSegments;

                    if(response.body().totals.coupon_code !=null){
                        edtcoupencode.setText(response.body().totals.coupon_code);
                        edtcoupencode.setEnabled(false);
                        coupenlayout.setVisibility(View.VISIBLE);
                        coupenlayout.requestFocus();

                        if (Locale.equals("ar")) {
                            plusbutton.setText(" رمز الخصم - ");
                        } else {
                            plusbutton.setText("Discount Code - ");
                        }
                        coupenflag =true;

                    }
                    else{
                        coupenflag = false;
                    }


                    for (int i = 0; i < totalSegments.size(); i++) {

                        if (totalSegments.get(i).code.equalsIgnoreCase("mp_gift_wrap_amount")) {
                            is_giftboxadded = true;
                            if (Locale.equalsIgnoreCase("en")) {
                                addgiftBoxText.setText("Remove Gift Box");


                            } else {

                                addgiftBoxText.setText("حذف بوكس الهدية");
                            }


                            break;


                        } else {
                            is_giftboxadded = false;
                            if (Locale.equalsIgnoreCase("en")) {
                                addgiftBoxText.setText("Add Gift Box");
                            } else {

                                addgiftBoxText.setText("إضافة بوكس الهدية");
                            }
                        }

                    }


                    String giftwrapdetailes = response.body().totals.extensionAttributes.mpGiftWrap;

                    paymentmethods = response.body().paymentMethods;


                    for (int i = 0; i < response.body().paymentMethods.size(); i++) {
                        if (response.body().paymentMethods.get(i).code.equals("cashondelivery")) {

                            code_label = response.body().paymentMethods.get(i).title;
                        }
                    }
                    //  Toast.makeText(getContext(), code_label, Toast.LENGTH_SHORT).show();

                    try {

                        String jsonFormattedString = new JSONTokener(giftwrapdetailes).nextValue().toString();
                        JSONArray giftjson = new JSONArray(jsonFormattedString);
                        JSONObject jsonobj = giftjson.getJSONObject(0);
                        String json2 = jsonobj.getString("mp_gift_wrap_data");
                        JSONObject jsonobj3 = new JSONObject(json2);
                        giftmessage = jsonobj3.getString("gift_message");
                        Log.e(" giftmessage", giftmessage + "");
                        Log.e("json", giftwrapdetailes + "");


                        if (giftboxmessage != null) {

                            giftboxmessage.setText(giftmessage);
                        }


                        System.out.println("jahsdfgjdsgf" + jsonFormattedString);

                    } catch (Exception e) {
                        System.out.println("jahsdfgjdsgf" + e.getMessage());
                    }

                    initSetonclickListener();
                    initChckoutcalculationRecycler(totalSegments);


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


                if (response.body() != null && response.isSuccessful()) {

                    MySharedPreferenceClass.setCustomerToken(getApplicationContext(), response.body().toString());


                    if (!MySharedPreferenceClass.getAdressphone(getApplicationContext()).equals(" ")) {
                        MainActivity.isaddressempty = false;
                        getShippingMethod();

                    } else {
                        MainActivity.isaddressempty = true;
                        parentDialogListener.hideProgressDialog();
                        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
                            Toast.makeText(getApplicationContext(), "Shipping address is missing to proceed, please add shipping address first", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "عنوان الشحن، غائب للمتابعة يرجى إضافة عنوان الشحن أولا", Toast.LENGTH_SHORT).show();
                        }
                        Fragment prFrag = new NewAddressFragmentUpgrade(CheckOutConfirmationfragment.this);
                        if (!prFrag.isAdded()) {
                            Bundle databund = new Bundle();
                            databund.putString("flag", "fromCheckout");
                            prFrag.setArguments(databund);
                            getParentFragmentManager()
                                    .beginTransaction().addToBackStack(null)
                                    .replace(R.id.checkoutContainer, prFrag).setReorderingAllowed(true)
                                    .commit();
                        }

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
                    Toast.makeText(getApplicationContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public void clearCodSelection() {


        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        map1.put("payment_method", "checkoutcom_card_payment");
        map1.put("is_application", "true");
        map1.put("cart_id", MySharedPreferenceClass.getQuoteId(getApplicationContext()));



        Call<ClearCodSelection> call = apiService.clearcodselection(map1);


        call.enqueue(new Callback<ClearCodSelection>() {
            @Override
            public void onResponse(Call<ClearCodSelection> call, Response<ClearCodSelection> response) {


                if (response.body() != null && response.isSuccessful()) {
                    getTokenID();



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
                            JSONObject jsonObject = null;
                            parentDialogListener.hideProgressDialog();
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
            public void onFailure(Call<ClearCodSelection> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                if (Locale.equals("ar")) {
                    Toast.makeText(getApplicationContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });


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
                        getShippingMethod();
                        edtcoupencode.setEnabled(true);
                        edtcoupencode.setText("");
                        coupenflag = false;

                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                            coupentext.setText("Apply Coupon Code");
                        } else {
                            coupentext.setText("تطبيق رمز القسيمة");

                        }


                        coupenAmount.setText("-" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()) + "0");
                        coupenlayout.setVisibility(View.GONE);
                        if (Locale.equals("ar")) {
                            plusbutton.setText(" رمز الخصم + ");
                        } else {
                            plusbutton.setText("Discount Code + ");
                        }
                        islayoutvisible =false;
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

    public void initChckoutcalculationRecycler(List<TotalSegment> totalsegments) {
        checkoutcalculationrecycler.setHasFixedSize(true);
        checkoutcalculationrecycler.setNestedScrollingEnabled(false);
        checkoutcalculationrecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));


        checkoutcalculationrecycler.setItemAnimator(new DefaultItemAnimator());
        // use a linear layout manager


        // specify an adapter (see also next example)
        calculationadapter = new CheckOutCalculationAdapter(getContext(), totalsegments, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();


                switch (id) {


                }
            }
        }


        );
        checkoutcalculationrecycler.setAdapter(calculationadapter);
        checkoutcalculationrecycler.setNestedScrollingEnabled(false);
        parentDialogListener.hideProgressDialog();
        checkoutcontainer.setVisibility(View.VISIBLE);
        checkoutmainlayout.setVisibility(View.VISIBLE);

    }


    private void iniatializeGiftBoxDialogue() {
        BottomSheetBehavior bottomSheetBehavior;
        mBottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);

        if (Locale.equals("en")) {
            sheetView = this.getLayoutInflater().inflate(R.layout.gift_box_dialog, null);
        }
        if (Locale.equals("ar")) {
            sheetView = this.getLayoutInflater().inflate(R.layout.gift_box_dialog_arabic, null);
        }
        mBottomSheetDialog.setContentView(sheetView);


        giftboxImage = sheetView.findViewById(R.id.giftboxImage);
        giftmessagechceck = sheetView.findViewById(R.id.giftmsgcheck);
        addgiftboxmsg = sheetView.findViewById(R.id.addgiftboxmsg);

        giftadd = sheetView.findViewById(R.id.giftcheckbox);
        title = sheetView.findViewById(R.id.title);
        giftboxmessage = sheetView.findViewById(R.id.giftboxmsg);

        giftmsgtext = sheetView.findViewById(R.id.giftmsgtext);
        giftwrapsubmit = sheetView.findViewById(R.id.giftwrapsubmit);
        giftwrapremove = sheetView.findViewById(R.id.giftwrapremove);
        giftwrapcancel = sheetView.findViewById(R.id.giftwrapcancel);
        giftboxmessage.setText(giftmessage);

        addgiftboxmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!giftcheckflag) {
                    giftmessagechceck.setImageDrawable(getContext().getDrawable(R.drawable.tickmsg));
                    giftcheckflag = true;
                    giftboxmessage.setVisibility(View.VISIBLE);
                    giftmsgtext.setVisibility(View.VISIBLE);


                } else {
                    giftmessagechceck.setImageDrawable(getContext().getDrawable(R.drawable.untickmsg));
                    giftcheckflag = false;
                    giftboxmessage.setVisibility(View.GONE);
                    giftmsgtext.setVisibility(View.GONE);

                }


            }
        });


        giftwrapsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTokenIDforGiftBox();
            }
        });

        giftwrapremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mBottomSheetDialog.cancel();
               // removeGiftBox();

            }
        });

        giftwrapcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBottomSheetDialog.cancel();


            }
        });


        getGiftwrapdetails();


        mBottomSheetDialog.show();
    }

    public void getGiftwrapdetails() {

        /*https://upgrade.eoutlet.com/rest/en/V1/mpGiftWrap/wrappers*/


        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", String.valueOf(MySharedPreferenceClass.getCartId(getContext())));

        /*https://upgrade.eoutlet.com/rest/V1/carts/678/items/543*/
        Call<Giftwrapdetail> call;
        if (Locale.equals("en")) {
            call = apiService.getGiftWraDetails("Bearer" + " " + MySharedPreferenceClass.getToken(getContext()), "https://upgrade.eoutlet.com/rest/en/V1/mpGiftWrap/wrappers");//739
        } else {
            call = apiService.getGiftWraDetails("Bearer" + " " + MySharedPreferenceClass.getToken(getContext()), "https://upgrade.eoutlet.com/rest/ar/V1/mpGiftWrap/wrappers");//739
        }


        call.enqueue(new Callback<Giftwrapdetail>() {
            @Override
            public void onResponse(Call<Giftwrapdetail> call, Response<Giftwrapdetail> response) {


                if (response.body() != null) {


                    if (response.isSuccessful() && response.body() != null) {
                        parentDialogListener.hideProgressDialog();
                        giftarray = response.body().items;


                        if (Locale.equals("en")) {
                            int amount = Math.round(response.body().items.get(0).amount * MySharedPreferenceClass.getSelectedCurrencyRate(view.getContext()));

                            title.setText(response.body().items.get(0).name + " " + "(" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()) + " " +
                                    amount + " )");

                        } else {
                            int amount = Math.round(response.body().items.get(1).amount * MySharedPreferenceClass.getSelectedCurrencyRate(view.getContext()));

                            title.setText(response.body().items.get(1).name + " " + "(" + " " + MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()) + " " +
                                    amount + " )");
                        }


                        Glide.with(giftboxImage)
                                .load("https://upgrade.eoutlet.com/pub/media/" + response.body().items.get(0).image).apply(RequestOptions.bitmapTransform(new RoundedCorners(80))).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)/*error(R.drawable.progress_animation)*/
                                /* .placeholder(R.drawable.progress_animation)*/.override(1000, 800)
                                .into(giftboxImage);


                    } else {


                    }


                }


            }


            @Override
            public void onFailure(Call<Giftwrapdetail> call, Throwable t) {
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


    public void removeGiftBox() {

        /*{"itemId":"all_cart","wrap":false}*/
        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();

        mainparam.put("itemId", "all_cart");
        mainparam.put("wrap", false);

        Map<String, String> headers = new HashMap<>();

        headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getCustomerToken(getContext()));
        headers.put("Cookie", MySharedPreferenceClass.getCookies(getContext()));


        Call<UpgradeQuantity> call = apiService.UpgradedGiftWrap(headers, mainparam);
        call.enqueue(new Callback<UpgradeQuantity>() {
            @Override
            public void onResponse(Call<UpgradeQuantity> call, Response<UpgradeQuantity> response) {


                if (response.isSuccessful() && response.body() != null) {
                    //parentDialogListener.hideProgressDialog();
                    is_giftwrap = false;
                    getShippingMethod();
                    giftmessage = " ";

                    if(giftboxmessage!=null) {
                        giftboxmessage.setText(giftmessage);
                    }

                   if(mBottomSheetDialog!=null) {
                       mBottomSheetDialog.cancel();
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

                    }


                }
            }


            @Override
            public void onFailure(Call<UpgradeQuantity> call, Throwable t) {
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

    public void getTokenIDforGiftBox() {


        //parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        map1.put("email", MySharedPreferenceClass.getEmail(getContext()));
        map1.put("password", MySharedPreferenceClass.getMyPassword(getContext()));

        mainparam.put("param", map1);

        Call<String> call;
        if (Locale.equals("en")) {
            call = apiService.createcustomertoken(mainparam);
        } else {
            call = apiService.createcustomertokeninarabic(mainparam);
        }


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    MySharedPreferenceClass.setCustomerToken(getContext(), response.body().toString());


                    addGiftBox();
                } else {

                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                //  Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();

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

    public void addGiftBox() {
        /*https://upgrade.eoutlet.com/rest/en/V1/carts/mine/mpgiftwrap-update-wrap*/

/* {"itemId":"all_cart",
"wrap":
{"image":"mageplaza/giftwrap/giftbox.jpg",
"amount":10,
"wrap_id":1,
"description":null,
"created_at":"2021-01-12 10:19:02",
"use_gift_message":true,
"updated_at":"2021-02-03 10:06:46",
"price":10,
"name":"Standard Gift Wrap",
"price_type":1,
"all_cart":true,
"category":"1",
"gift_message":"arararar",
"status":1}}
    */
            /*{
    "itemId": "all_cart",
    "wrap": {
        "wrap_id": "1",
        "name": "Standard Gift Wrap",
        "status": "1",
        "price_type": "1",
        "amount": "10.0000",
        "description": null,
        "image": "https://upgrade.eoutlet.com/pub/media/mageplaza/giftwrap/giftbox.jpg",
        "category": "1",
        "sort_order": "0",
        "created_at": "2021-01-12 07:19:02",
        "updated_at": "2021-02-03 07:06:46",
        "price": "$10.00",
        "all_cart": true,
        "use_gift_message": true,
        "gift_message": "sefs f;eiruw;eyr q"
    }
}*/


        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> mainparam2 = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();

        if (Locale.equals("en")) {
            map1.put("wrap_id", giftarray.get(0).wrapId.toString());

            map1.put("name", giftarray.get(0).name);

            map1.put("status", giftarray.get(0).status.toString());
            map1.put("price_type", giftarray.get(0).priceType.toString());
            map1.put("amount", giftarray.get(0).amount.toString());
            map1.put("description", giftarray.get(0).description);
            map1.put("image", "https://upgrade.eoutlet.com/pub/media/" + giftarray.get(0).image);
            map1.put("category", giftarray.get(0).category);
            map1.put("sort_order", giftarray.get(0).sortOrder.toString());
            map1.put("created_at", giftarray.get(0).createdAt);
            map1.put("updated_at", giftarray.get(0).updatedAt);
            map1.put("price", giftarray.get(0).amount.toString());
            map1.put("all_cart", true);
        } else {
            map1.put("wrap_id", giftarray.get(1).wrapId.toString());

            map1.put("name", giftarray.get(1).name);

            map1.put("status", giftarray.get(1).status.toString());
            map1.put("price_type", giftarray.get(1).priceType.toString());
            map1.put("amount", giftarray.get(1).amount.toString());
            map1.put("description", giftarray.get(1).description);
            map1.put("image", "https://upgrade.eoutlet.com/pub/media/" + giftarray.get(1).image);
            map1.put("category", giftarray.get(1).category);
            map1.put("sort_order", giftarray.get(1).sortOrder.toString());
            map1.put("created_at", giftarray.get(1).createdAt);
            map1.put("updated_at", giftarray.get(1).updatedAt);
            map1.put("price", giftarray.get(1).amount.toString());
            map1.put("all_cart", true);

        }


        if (!giftboxmessage.getText().toString().equals("")) {
            map1.put("use_gift_message", true);
        } else {
            map1.put("use_gift_message", false);

        }

        map1.put("gift_message", giftboxmessage.getText().toString());

        mainparam.put("wrap", map1);
        mainparam.put("itemId", "all_cart");


        HashMap<String, String> headers = new HashMap();
        headers.put("Authorization", "Bearer" + " " + MySharedPreferenceClass.getCustomerToken(getContext()));
        headers.put("Cookie", MySharedPreferenceClass.getCookies(getContext()));


        Call<UpgradeQuantity> call;


        if (Locale.equals("en")) {
            call = apiService.UpgradedGiftWrap(headers, mainparam);
        } else {
            call = apiService.UpgradedGiftWrapArabic(headers, mainparam);
        }


        call.enqueue(new Callback<UpgradeQuantity>() {
            @Override
            public void onResponse(Call<UpgradeQuantity> call, Response<UpgradeQuantity> response) {

                //parentDialogListener.hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    is_giftwrap = true;
                    getShippingMethod();
                    mBottomSheetDialog.cancel();


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

                    }


                }
            }


            @Override
            public void onFailure(Call<UpgradeQuantity> call, Throwable t) {
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
    public void onStart() {
        super.onStart();
        Log.e("ONSTART--->>", "");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.e("ONPAUSE--->>", "");

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("ONRESUME", "");


    }
    public static CheckOutConfirmationfragment getInstance() {
        return instance;
    }
    public void moengageCheckoutEvent()
    {


        Properties properties = new Properties();


        properties.addAttribute("currency", MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()));
            properties.addAttribute("price",tvtotalwithTax.getText().toString().split(" ")[1]);
        properties.addAttribute("user_id",MySharedPreferenceClass.getMyUserId(getApplicationContext()));

        properties.addAttribute("coupon",coupencode)
        .addAttribute("category_name", MySharedPreferenceClass.getPurchaseCategorynames(getApplicationContext()))
            .addAttribute("category_id", MySharedPreferenceClass.getPurchaseCategoryIds(getApplicationContext()))
            .addAttribute("brand_name", MySharedPreferenceClass.getPurchaseBrandname(getApplicationContext()));



        MoEHelper.getInstance(getApplicationContext()).trackEvent("Checkout", properties);
        Log.e("coupon",coupencode);
        Log.e("pricemoenagageevent",tvtotalwithTax.getText().toString().split(" ")[1]);



    }
}






