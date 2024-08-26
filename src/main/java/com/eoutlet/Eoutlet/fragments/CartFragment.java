package com.eoutlet.Eoutlet.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.adpters.CartAdpter;
import com.eoutlet.Eoutlet.adpters.ProductListAdapter;
import com.eoutlet.Eoutlet.adpters.UpgradedCartAdpter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.ListItems;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.intrface.UpdateBedgeCount;
import com.eoutlet.Eoutlet.listener.CartListener;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.CancelOrderListResponse;
import com.eoutlet.Eoutlet.pojo.CurrencysetResponse;
import com.eoutlet.Eoutlet.pojo.ItemGift;
import com.eoutlet.Eoutlet.pojo.Size;
import com.eoutlet.Eoutlet.pojo.UpgradeQuantity;
import com.eoutlet.Eoutlet.pojo.UpgradedCartItems;
import com.eoutlet.Eoutlet.pojo.UpgradedItem;
import com.eoutlet.Eoutlet.pojo.ViewCartData;
import com.eoutlet.Eoutlet.utility.Constants;
import com.eoutlet.Eoutlet.utility.Util;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;


public class
CartFragment extends Fragment implements CartListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    View v;
    UpdateBedgeCount updatefix;

    public ParentDialogsListener parentDialogListener;
    RecyclerView cartRecycler;
    CartAdpter mAdapter;
    UpgradedCartAdpter mAdapter2;
    private float totalprice;
    private float totalwithtax;
    private Toolbar toolbar, toolbar1;
    private int tax_rate_in_percentage, gift_wrap_fees = 0, codcharges;
    private LinearLayout removealllayout;
    private RelativeLayout removeall;
    int totalcount;
    private ProgressDialog progressDialog;

    private List<ItemGift> giftarray = new ArrayList<>();

    private ImageView searchImage, backarrow;

    public static String seledtedItemid;

    public static int selcteditemquantity;
    private FrameLayout proceedtoCheckout;
    private TextView tvTotalprice, tvTotalPricewithTax, totalPricewithoutTax;
    private List<ViewCartData> cartData;

    private List<UpgradedItem> upgradedcartData;
    private String cartId = " ";

    String cookiefromheader;
    public TextView plusbutton;
    private View toolbarbeg;
    private ReviewManager reviewManager;
    private TextView noitemavail, tooltext;
    private TextView shippingcharges, totaltax;
    private List<String> itemId = new ArrayList<>();
    private List<String> skuList = new ArrayList<>();
    private List<Integer> quantityList = new ArrayList<>();
    private List<Size> options = new ArrayList<>();
    boolean islayoutvisible = false;
    private LinearLayout coupenlayout;
    boolean coupenflag = false;
    static int dicountamount = 0;
    private TextView coupantext, coupenAmount;
    private LinearLayout linCartMain, giftwrapbtn;
    ExecuteFragment execute;
    private LinearLayout coupanmainlayout;
    private TextView applycode;
    private TextView viewGift, descriptiontext, giftwrappingcharge;
    private EditText edtcoupencode, giftwrapmessage;
    private OnFragmentInteractionListener mListener;
    private ImageView closeimage, checkbox;
    private RelativeLayout giftboxadd;
    private FrameLayout giftwrapsubmit, giftwrapcancel;

    public static boolean checkboxflag = false;

    private FrameLayout giftimage;

    public String Locale = " ";

    public CartFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
            Locale = "en";
            v = inflater.inflate(R.layout.fragment_cart_new_english, container, false);
        } else {
            Locale = "ar";
            v = inflater.inflate(R.layout.fragment_cart_new, container, false);
        }
        MainActivity.fromcart = true;
        execute = (MainActivity) getActivity();
        initViews(v);
        checkboxflag = MySharedPreferenceClass.getCheckboxflag(getContext());


        toolbar1 = v.findViewById(R.id.toolbar);
        searchImage = toolbar1.findViewById(R.id.serachbar);


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



      /*  if (MySharedPreferenceClass.getBedgeCount(getContext()) > 0) {
            toolbar_bedgetextcart.setVisibility(View.VISIBLE);
            toolbar_bedgetextcart.setText(String.valueOf(MySharedPreferenceClass.getBedgeCount(getContext())));
        }
*/

        return v;

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void setUserVisibleHint(boolean visible) {


        super.setUserVisibleHint(visible);

        if (visible && isResumed()) {

            checkboxflag = MySharedPreferenceClass.getCheckboxflag(getContext());


         /*   if (MySharedPreferenceClass.getBedgeCount(getContext()) > 0) {
                toolbar_bedgetextcart.setVisibility(View.VISIBLE);
                toolbar_bedgetextcart.setText(String.valueOf(MySharedPreferenceClass.getBedgeCount(getContext())));
            } else {
                toolbar_bedgetextcart.setVisibility(View.GONE);

            }
*/
            searchImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment prFrag = new SearchResultFragment();
                    getFragmentManager()
                            .beginTransaction().addToBackStack(null)
                            .replace(R.id.cartcontainer, prFrag)
                            .commit();
                }
            });
            sendcountryselction();
           /* if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {

                getUpgradeCartData();
       //sendcountryselction();


            } else {
                //getCartDataforGuestUser();
                getUpgradedCartDataforGuestUser();
            }
*/
        }

        if(MainActivity.isaddressempty==true){
            MainActivity.isaddressempty = false;

        }
    }

    public void sendcountryselction() {
        /*https://upgrade.eoutlet.com/rest/en/V1/api/setquotecurrency
        *
        *
        * "{
    ""param"":{
        ""quote_id"":2016,
        ""currency_code"":""USD""
    }
}"*/
//        if(!parentDialogListener.isProgressBarRunning())
        showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
        Map<Object, Object> mainparam = new HashMap<>();

        Map<String, String> map1 = new HashMap<>();

        if (MySharedPreferenceClass.getMyUserId(getApplicationContext()) != " ") {

            map1.put("quote_id", MySharedPreferenceClass.getQuoteId(getContext()));
            map1.put("currency_code", MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()));

            mainparam.put("param", map1);

        } else {
            map1.put("quote_id", String.valueOf(MySharedPreferenceClass.getCartId(getApplicationContext())));
            map1.put("currency_code", MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()));

            mainparam.put("param", map1);

        }

        HashMap<String, String> headers = new HashMap<>();

        headers.put("Cookie", MySharedPreferenceClass.getCookies(getApplicationContext()));
        Call<CurrencysetResponse> call;

                if(Locale.equals("en")) {

                    call = apiService.postUserCurrencySelection(headers, mainparam);
                }
                else{
                    call = apiService.postUserCurrencySelectionforarabic(headers, mainparam);
                }


        call.enqueue(new Callback<CurrencysetResponse>() {
            @Override
            public void onResponse(Call<CurrencysetResponse> call, Response<CurrencysetResponse> response) {

                if (response.body() != null && response.isSuccessful()) {
                    if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {

                        getUpgradeCartData();


                    } else {
                        getUpgradedCartDataforGuestUser();

                    }


                } else {


                }

            }


            @Override
            public void onFailure(Call<CurrencysetResponse> call, Throwable t) {
                hideProgressDialog();

                if (Locale.equals("ar")) {


                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "يرجى التحقق من اتصالك بالإنترنت وحاول مرة أخرى", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                    }
                } else {

                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                        // logging probably not necessary
                    }
                    else{
                        Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


    }

    public void initViews(View v) {
        cartRecycler = v.findViewById(R.id.cart_recycler);
        tvTotalprice = v.findViewById(R.id.totalprice);
        tvTotalPricewithTax = v.findViewById(R.id.totalpricewithTax);

        proceedtoCheckout = v.findViewById(R.id.proceedtoCheckout);
        linCartMain = v.findViewById(R.id.cartmainlayout);
        noitemavail = v.findViewById(R.id.notItemAvail);
        shippingcharges = v.findViewById(R.id.shippingcharges);
        totaltax = v.findViewById(R.id.totalTax);
        totalPricewithoutTax = v.findViewById(R.id.totalpricewithouttax);
        coupanmainlayout = v.findViewById(R.id.coupanmainlayout);
        removealllayout = v.findViewById(R.id.removealllayout);
        removeall = v.findViewById(R.id.removeall);
        coupenlayout = v.findViewById(R.id.coupenlayout);
        applycode = v.findViewById(R.id.applycode);
        edtcoupencode = v.findViewById(R.id.edt_coupen);
        coupantext = v.findViewById(R.id.coupenname);
        coupenAmount = v.findViewById(R.id.discoutnamount);
        giftwrapmessage = v.findViewById(R.id.giftboxmsg);
        giftwrapbtn = v.findViewById(R.id.giftwrapbtn);


        giftimage = v.findViewById(R.id.giftImage);
        closeimage = v.findViewById(R.id.clooseImage);
        viewGift = v.findViewById(R.id.btnviewgift);
        descriptiontext = v.findViewById(R.id.destext);
        checkbox = v.findViewById(R.id.checkbox);
        plusbutton = v.findViewById(R.id.plusbutton);
        giftwrappingcharge = v.findViewById(R.id.giftwrappingcharge);
        giftboxadd = v.findViewById(R.id.giftboxadd);
        giftwrapsubmit = v.findViewById(R.id.giftwrapsubmit);
        giftwrapcancel = v.findViewById(R.id.giftwrapcancel);


      /*  giftwrapsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getTokenID();
            }
        });

        giftwrapcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                giftwrapbtn.setVisibility(View.GONE);
                giftwrapmessage.setVisibility(View.GONE);
              if(!MySharedPreferenceClass.getCheckboxflag(getContext())) {
                  checkbox.setImageDrawable(getContext().getDrawable(R.drawable.ic_circle));
              }
            }
        });
*/


     /* giftboxadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!MySharedPreferenceClass.getCheckboxflag(getContext())) {
                    checkbox.setImageDrawable(getContext().getDrawable(R.drawable.ic_yellow_check));
                    giftwrapbtn.setVisibility(View.VISIBLE);
                    giftwrapmessage.setVisibility(View.VISIBLE);


                }
                else
                    {



                        getTokenIDtoRemoveGiftBox();

                }


            }
        });*/

        closeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewGift.setVisibility(View.VISIBLE);
                giftimage.setVisibility(View.GONE);
            }
        });

        viewGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                reviewManager = ReviewManagerFactory.create(getContext());
                //showRateApp();

                DialogFragment prFrag = new GiftboxFragment();
                //DialogFragment prFrag = new SuccessThankyouFragment();
                Bundle bund = new Bundle();


                prFrag.setArguments(bund);

                prFrag.setTargetFragment(CartFragment.this, 1002);
                prFrag.show(getFragmentManager(), "signup");


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

                    plusbutton.setText("-");


                } else {


                    Animation animHide = AnimationUtils.loadAnimation(getContext(), R.anim.view_hide);
                    coupenlayout.setVisibility(View.GONE);


                    islayoutvisible = false;
                    plusbutton.setText("+");

                }
            }
        });


        removeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openRemovealldialog();


            }
        });


        updatefix = (MainActivity) getActivity();
        noitemavail.setVisibility(View.GONE);


        proceedtoCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Util.checkConnection(getApplicationContext())) {
                    if (MySharedPreferenceClass.getMyUserId(getContext()) == " ") {
                   /* getCartDataStatusforGuestUser();

                    getUpgradedCartDataforGuestUser();*/

                        getUpgradedCartDataStatusforGuestUser();


                    } else if (MySharedPreferenceClass.getEmail(getContext()) == null) {
                        openSignindialog();
                    } else {

                        getUpgradedStatusofCartItems();

                    }


                }
                else
                    {
                        if (Locale.equals("en")) {
                            Toast.makeText(getApplicationContext(), "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "يرجى التحقق من اتصالك بالإنترنت وحاول مرة أخرى", Toast.LENGTH_SHORT).show();
                        }

                    }
            }

        });


    }

    public void initUpgardedRecycler(View v) {
        cartRecycler.setHasFixedSize(true);

        if (upgradedcartData.size() == 1) {

            final float scale = getResources().getDisplayMetrics().density;

            int dpHeightInPx = (int) (R.dimen._50sdp * scale);


            LinearLayout.LayoutParams lp =
                    new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, dpHeightInPx);
            lp.leftMargin = 0;


            cartRecycler.setPadding(6, 0, 6, 0);

            cartRecycler.setLayoutParams(lp);


            cartRecycler.getLayoutParams().height = (int) getResources().getDimension(R.dimen._120sdp);


            cartRecycler.setVerticalScrollBarEnabled(false);


        } else if (upgradedcartData.size() == 2) {

            final float scale = getResources().getDisplayMetrics().density;

            int dpHeightInPx = (int) (R.dimen.product_height2 * scale);

            LinearLayout.LayoutParams lp =
                    new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 2 * dpHeightInPx);
            lp.leftMargin = 0;
            cartRecycler.setPadding(6, 0, 6, 0);
            cartRecycler.setLayoutParams(lp);

            cartRecycler.getLayoutParams().height = (int) getResources().getDimension(R.dimen.twoitemheight);
            cartRecycler.setVerticalScrollBarEnabled(false);
        } else if (upgradedcartData.size() == 3) {

            final float scale = getResources().getDisplayMetrics().density;

            int dpHeightInPx = (int) (R.dimen.product_height2 * scale);

            LinearLayout.LayoutParams lp =
                    new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 3 * dpHeightInPx);

            cartRecycler.setPadding(0, 0, 6, 0);
            lp.leftMargin = 0;
            cartRecycler.setLayoutParams(lp);
            cartRecycler.getLayoutParams().height = (int) getResources().getDimension(R.dimen.product_height3);
            cartRecycler.setVerticalScrollBarEnabled(false);


        } else {

            final float scale = getResources().getDisplayMetrics().density;

            int dpHeightInPx = (int) (R.dimen.product_height2 * scale);

            LinearLayout.LayoutParams lp =
                    new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 3 * dpHeightInPx + 20);

            cartRecycler.setPadding(20, 0, 6, 0);
            lp.leftMargin = 0;
            cartRecycler.setLayoutParams(lp);
            cartRecycler.getLayoutParams().height = (int) getResources().getDimension(R.dimen.product_height3);
            cartRecycler.setVerticalScrollBarEnabled(true);


        }
        cartRecycler.setLayoutManager(new LinearLayoutManager(v.getContext()));

        // specify an adapter (see also next example)
        mAdapter2 = new UpgradedCartAdpter(v.getContext(), upgradedcartData

                , new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();


                switch (id) {

                    case R.id.deleteItemfromCart://button for message

                        seledtedItemid = itemId.get(position);
                        selcteditemquantity = quantityList.get(position);
                        dicountamount = 0;

                        openDeletedialog();


                        break;


                    case R.id.cart_plus_button:
                        dicountamount = 0;
                        seledtedItemid = itemId.get(position);
                        edtcoupencode.setText("");
                        coupantext.setText("الخصم");
                        coupenAmount.setText("- SAR 0");
                        edtcoupencode.setEnabled(true);


                        if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {


                            //changequantity(CartAdpter.qtyarray.get(position).toString(), position, (Integer) CartAdpter.qtyarray.get(position) + 1);
                            Upgradechangequantity(UpgradedCartAdpter.qtyarray.get(position).toString(), position, (Integer) UpgradedCartAdpter.qtyarray.get(position) + 1);

                        } else {


                            Upgradechangequantityforguestuser(UpgradedCartAdpter.qtyarray.get(position).toString(), position, (Integer) UpgradedCartAdpter.qtyarray.get(position) + 1);

                        }
                        break;
                    case R.id.mainitemclick:
                        Fragment prFrag = new ProductDetail();
                        Bundle databund = new Bundle();
                        databund.putString("previouspage","FromCartScreen");
                        databund.putString("sku", upgradedcartData.get(position).sku);
                        //databund.putString("type", upgradedcartData.get(position).type);
                        databund.putString("pid", upgradedcartData.get(position).productId);

                        //databund.putString("size", upgradedcartData.get(position).productData.size);
                        //databund.putString("color",upgradedcartData.get(position).);
                        //databund.putString("color_name",  cartData.get(position).productData.color_name);

                        ListItems listItems = new ListItems();

                        listItems.name = upgradedcartData.get(position).name;
                        listItems.id = String.valueOf(upgradedcartData.get(position).id);
                        listItems.price = String.valueOf(upgradedcartData.get(position).price);
                        listItems.oldPrice = String.valueOf(upgradedcartData.get(position).price);
                        listItems.sku = String.valueOf(upgradedcartData.get(position).sku);

                        databund.putSerializable("catagoryobject", listItems);

                        // databund.putSerializable("catagoryobject",upgradedcartData.get(position).productData);

                        prFrag.setArguments(databund);
                        getFragmentManager()
                                .beginTransaction().addToBackStack(null)
                                .replace(R.id.cartcontainer, prFrag)
                                .commit();


                        break;

                    case R.id.cart_minace_button:
                        seledtedItemid = itemId.get(position);
                        seledtedItemid = itemId.get(position);
                        edtcoupencode.setText("");
                        coupantext.setText("الخصم");
                        coupenAmount.setText("- SAR 0");
                        edtcoupencode.setEnabled(true);
                        dicountamount = 0;

                        if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {


                            if ((Integer) UpgradedCartAdpter.qtyarray.get(position) > 1) {
                                Upgradechangequantity(UpgradedCartAdpter.qtyarray.get(position).toString(), position, (Integer) UpgradedCartAdpter.qtyarray.get(position) - 1);

                                //changequantity(CartAdpter.qtyarray.get(position).toString(), position, (Integer) CartAdpter.qtyarray.get(position) - 1);
                            } else {


                                //deleteItemfromCart();
                                upgradeddeleteItemfromCart();


                            }


                        } else {
                            if ((Integer) UpgradedCartAdpter.qtyarray.get(position) > 1) {
                                Upgradechangequantityforguestuser(UpgradedCartAdpter.qtyarray.get(position).toString(), position, (Integer) UpgradedCartAdpter.qtyarray.get(position) - 1);
                            } else {

                                //deleteItemfromCartforguestUser();
                                upgradeddeleteItemfromCartforguestUser();


                            }
                        }
                        break;


                }
            }
        }


        );
        cartRecycler.setAdapter(mAdapter2);


    }


    public void Upgradechangequantity(String quantity, final int position, final int changequnatity) {

        showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


       /* {"cart_item" :{
            "quote_id" : "weweqeqe1233123",
                    "sku": "hffytr658",
                    "qty" :"1"
        }}*/
        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        //map1.put("quote_id",MySharedPreferenceClass.getQuoteId(getContext()));


        map1.put("quote_id", MySharedPreferenceClass.getQuoteId(getContext()));

        map1.put("qty", changequnatity);

        map1.put("sku", skuList.get(position));
        map1.put("item_id", seledtedItemid);

        mainparam.put("cartItem", map1);

       /* Map<String, String> query = new HashMap<>();
        query.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        query.put("item_id", seledtedItemid);
        query.put("qty", String.valueOf(changequnatity));
        query.put("sku", skuList.get(position));*/
        /*https://upgrade.eoutlet.com/rest/V1/carts/mine/items/305*/
        Call<UpgradeQuantity> call = apiService.UpgradedchangequantityfromCart("Bearer" + " " + MySharedPreferenceClass.getToken(getContext()), mainparam, "https://upgrade.eoutlet.com/rest/V1/carts/mine/items/" + seledtedItemid);
        call.enqueue(new Callback<UpgradeQuantity>() {
            @Override
            public void onResponse(Call<UpgradeQuantity> call, Response<UpgradeQuantity> response) {

                hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {


                    if (response.body().qty == changequnatity) {
                        UpgradedCartAdpter.qtyarray.set(position, changequnatity);
                        hideProgressDialog();
                        //getCartData();
                        getUpgradeCartData();

                    } else {
                        hideProgressDialog();
                        //Toast.makeText(getContext(), response.body()., Toast.LENGTH_LONG).show();


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
                hideProgressDialog();
                Log.e(TAG, t.toString());

                if (Locale.equals("ar")) {


                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "يرجى التحقق من اتصالك بالإنترنت وحاول مرة أخرى", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                    }
                } else {

                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                        // logging probably not necessary
                    }
                    else{
                        Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


    }

    public void Upgradechangequantityforguestuser(String quantity, final int position, final int changequnatity) {

        //showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        /*https://upgrade.eoutlet.com/rest/V1/guest-carts/678/items/305*/ //https://upgrade.eoutlet.com/rest/V1/guest-carts/951/items/444
       /* {"cart_item" :{
            "quote_id" : "weweqeqe1233123",
                    "sku": "hffytr658",
                    "qty" :"1"
        }}*/
        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        //map1.put("quote_id",MySharedPreferenceClass.getQuoteId(getContext()));


        map1.put("quote_id", MySharedPreferenceClass.getMaskkey(getContext())/*String.valueOf(MySharedPreferenceClass.getCartId(getContext()))*/);

        map1.put("qty", String.valueOf(changequnatity));

        map1.put("sku", skuList.get(position));
        map1.put("item_id", seledtedItemid);

        mainparam.put("cart_item", map1);

       /* Map<String, String> query = new HashMap<>();
        query.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        query.put("item_id", seledtedItemid);
        query.put("qty", String.valueOf(changequnatity));
        query.put("sku", skuList.get(position));*/
        /*https://upgrade.eoutlet.com/rest/V1/carts/mine/items/305*/
        Call<UpgradeQuantity> call = apiService.UpgradedchangequantityfromCartforguestUser("Bearer" + " " + MySharedPreferenceClass.getToken(getContext()), mainparam, "https://upgrade.eoutlet.com/rest/V1/guest-carts/" + MySharedPreferenceClass.getCartId(getContext()) + "/items/" + seledtedItemid);
        call.enqueue(new Callback<UpgradeQuantity>() {
            @Override
            public void onResponse(Call<UpgradeQuantity> call, Response<UpgradeQuantity> response) {

                hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {


                    if (response.body().qty == changequnatity) {
                        UpgradedCartAdpter.qtyarray.set(position, changequnatity);
                        hideProgressDialog();
                        //getCartData();
                        getUpgradedCartDataforGuestUser();

                    } else {
                        hideProgressDialog();
                        //Toast.makeText(getContext(), response.body()., Toast.LENGTH_LONG).show();


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
                hideProgressDialog();
                Log.e(TAG, t.toString());

                if (Locale.equals("ar")) {


                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "يرجى التحقق من اتصالك بالإنترنت وحاول مرة أخرى", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                    }
                } else {

                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                        // logging probably not necessary
                    }
                    else{
                        Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {

        }
    }

    public void upgradeddeleteItemfromCart() {

        edtcoupencode.setText("");
        coupantext.setText("الخصم");
        coupenAmount.setText("- SAR 0");
        edtcoupencode.setEnabled(true);

        showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", String.valueOf(MySharedPreferenceClass.getCartId(getContext())));
        query.put("item_id", seledtedItemid);
        /*https://upgrade.eoutlet.com/rest/V1/carts/678/items/543*/


        Call<Boolean> call = apiService.upgradedeleteItemfromCart("Bearer" + " " + MySharedPreferenceClass.getToken(getContext()), "https://upgrade.eoutlet.com/rest/V1/carts/" + cartId + "/items/" + seledtedItemid);//739
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {


                if (response.body() != null) {


                    if (response.isSuccessful()) {
                        hideProgressDialog();
                        upgradedcartData.clear();
                        getUpgradeCartData();


                    } else {
                        hideProgressDialog();

                        if (Locale.equals("ar")) {

                            Toast.makeText(getContext(), "لا يوجد لديك منتجات في حقيبة التسوق الخاصة بك", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "There is no items in your cart", Toast.LENGTH_LONG).show();
                        }


                        linCartMain.setVisibility(View.GONE);
                        proceedtoCheckout.setVisibility(View.GONE);
                        removealllayout.setVisibility(View.GONE);


                    }


                }


            }


            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                hideProgressDialog();
                Log.e(TAG, t.toString());
                if (Locale.equals("ar")) {


                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "يرجى التحقق من اتصالك بالإنترنت وحاول مرة أخرى", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                    }
                } else {

                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                        // logging probably not necessary
                    }
                    else{
                        Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                    }
                }

                // Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                linCartMain.setVisibility(View.GONE);
                proceedtoCheckout.setVisibility(View.GONE);
                removealllayout.setVisibility(View.GONE);
            }
        });


    }

    public void upgradeddeleteItemfromCartforguestUser() {
        /*https://upgrade.eoutlet.com/rest/V1/guest-carts/:cartId/items/:itemId*/

        edtcoupencode.setText("");
        coupantext.setText("الخصم");
        coupenAmount.setText("- SAR 0");
        edtcoupencode.setEnabled(true);

        showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", String.valueOf(MySharedPreferenceClass.getCartId(getContext())));
        query.put("item_id", seledtedItemid);
        /*https://upgrade.eoutlet.com/rest/V1/carts/678/items/543*/


        Call<Boolean> call = apiService.upgradedeleteItemfromCart("Bearer" + " " + MySharedPreferenceClass.getToken(getContext()), "https://upgrade.eoutlet.com/rest/V1/guest-carts/" + MySharedPreferenceClass.getMaskkey(getContext()) + "/items/" + seledtedItemid);//739
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {


                if (response.body() != null) {


                    if (response.body()) {
                        hideProgressDialog();
                        //getCartData();
                        getUpgradedCartDataforGuestUser();


                    } else {
                        hideProgressDialog();

                        if (Locale.equals("ar")) {
                            Toast.makeText(getContext(), "لا يوجد لديك منتجات في حقيبة التسوق الخاصة بك", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "There is no items in your cart", Toast.LENGTH_LONG).show();
                        }

                        // Toast.makeText(getContext(), "لا يوجد لديك منتجات في حقيبة التسوق الخاصة بك", Toast.LENGTH_LONG).show();
                        linCartMain.setVisibility(View.GONE);
                        proceedtoCheckout.setVisibility(View.GONE);
                        removealllayout.setVisibility(View.GONE);


                    }


                }


            }


            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                hideProgressDialog();
                Log.e(TAG, t.toString());

                if (Locale.equals("ar")) {


                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "يرجى التحقق من اتصالك بالإنترنت وحاول مرة أخرى", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                    }
                } else {

                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                        // logging probably not necessary
                    }
                    else{
                        Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                    }
                }
                linCartMain.setVisibility(View.GONE);
                proceedtoCheckout.setVisibility(View.GONE);
                removealllayout.setVisibility(View.GONE);
            }
        });


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {

            parentDialogListener = (ParentDialogsListener) activity;

        } catch (ClassCastException e) {

            throw new ClassCastException(activity.toString()
                    + " Activity's Parent should be Parent Activity");
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void getUpgradedStatusofCartItems() {
        showProgressDialog();
        totalcount = 0;
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));


        /*https://upgrade.eoutlet.com/rest/en/V1/api/customercart/83188*/

        Call<UpgradedCartItems> call;
    if(Locale.equals("en")) {
        call = apiService.getUpgradedCartDetail(Constants.UPGRADED_HOST_LINK + "rest/en/V1/api/customercart/" + MySharedPreferenceClass.getMyUserId(getContext()));

    }
    else{
        call = apiService.getUpgradedCartDetail(Constants.UPGRADED_HOST_LINK + "rest/ar/V1/api/customercart/" + MySharedPreferenceClass.getMyUserId(getContext()));

    }


        call.enqueue(new Callback<UpgradedCartItems>() {
            @Override
            public void onResponse(Call<UpgradedCartItems> call, Response<UpgradedCartItems> response) {
                hideProgressDialog();
                int quant;
                if (response.body().response.equals("success")) {

                    upgradedcartData = response.body().data.items;
                    cartId = response.body().data.cart_id;

                    if (response.body().data.items.size() > 0) {
                        for (int i = 0; i < response.body().data.items.size(); i++) {

                            totalcount = totalcount + (int) (response.body().data.items.get(i).qty);

                            quant = (int) response.body().data.items.get(i).qty;

                            quantityList.add(quant);


                            if (quant == 1) {

                                totalprice = totalprice + Float.parseFloat(response.body().data.items.get(i).price);
                            } else {

                                totalprice = totalprice + Float.parseFloat(response.body().data.items.get(i).price) * quant;


                            }


                            itemId.add(response.body().data.items.get(i).id);
                            skuList.add(response.body().data.items.get(i).sku);


                        }

                        try {
                            linCartMain.setVisibility(View.VISIBLE);
                            proceedtoCheckout.setVisibility(View.VISIBLE);
                            removealllayout.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        initUpgardedRecycler(v);
                        int subtotal = Math.round((Float.parseFloat(response.body().data.subtotal) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext())));

                        totalPricewithoutTax.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + subtotal);
                        int giftwracharge = Math.round((Float.parseFloat(response.body().data.gift_wrap_fee) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext())));
                        giftwrappingcharge.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + +giftwracharge);


                        MySharedPreferenceClass.setBedgeCount(getContext(), totalcount);
                        updatefix.updateBedgeCount();
                        MainActivity.notificationBadge.setVisibility(View.VISIBLE);

                        hideProgressDialog();
                    } else {
                        MainActivity.bedgetext.setVisibility(View.GONE);

                    }

                } else {

                    hideProgressDialog();
                    MainActivity.notificationBadge.setVisibility(View.GONE);
                    MainActivity.bedgetext.setVisibility(View.GONE);
                    MainActivity.toolbar_bedgetext.setVisibility(View.GONE);
                    linCartMain.setVisibility(View.GONE);
                    removealllayout.setVisibility(View.GONE);
                    noitemavail.setVisibility(View.VISIBLE);

                    proceedtoCheckout.setVisibility(View.GONE);

                    MySharedPreferenceClass.setBedgeCount(getContext(), 0);

                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                        Toast.makeText(getContext(), "There is no items in your cart"
                                , Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "لا يوجد لديك منتجات في حقيبة التسوق الخاصة بك"
                                , Toast.LENGTH_LONG).show();
                    }

                }
                for (int i = 0; i < response.body().data.items.size(); i++) {

                    if (response.body().data.items.get(i).instock == 0) {

                        Toast.makeText(getContext(), "بعض المنتجات غير متوفرة بالمخزون", Toast.LENGTH_SHORT).show();

                        return;

                    }


                }
               // if (!MySharedPreferenceClass.getSelectedAddressId(getApplicationContext()).equals("0")) {
                    Fragment prFrag = new CheckOutConfirmationfragment(CartFragment.this);
                    Bundle bundle = new Bundle();
                    bundle.putString("coupencode", edtcoupencode.getText().toString().trim());
                    System.out.println("coupencode" + edtcoupencode.getText().toString().trim());
                    bundle.putString("discounntamount", String.valueOf(dicountamount));
                    System.out.println("discounntamount" + String.valueOf(dicountamount));

                    bundle.putString("giftwrapping", giftwrappingcharge.getText().toString());


                    prFrag.setArguments(bundle);

                    getFragmentManager()
                            .beginTransaction().
                            /*setCustomAnimations( R.anim.slide_to_left, R.anim.slide_to_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right )
                            .*/addToBackStack(null)
                            .replace(R.id.cartcontainer, prFrag)
                            .commit();
               // }
               /* else {
                    Fragment prFrag = new NewAddressFragmentUpgrade();
                    Bundle bundle = new Bundle();
                    bundle.putString("flag", "fromCart");
                    prFrag.setArguments(bundle);

                    getFragmentManager()
                            .beginTransaction().
                            *//*setCustomAnimations( R.anim.slide_to_left, R.anim.slide_to_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right )
                            .*//*addToBackStack(null)
                            .replace(R.id.cartcontainer, prFrag)
                            .commit();


                }*/
            }


            @Override
            public void onFailure(Call<UpgradedCartItems> call, Throwable t) {
                hideProgressDialog();
                Log.e(TAG, t.toString());
                if (Locale.equals("ar")) {


                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "يرجى التحقق من اتصالك بالإنترنت وحاول مرة أخرى", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                    }
                } else {

                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                        // logging probably not necessary
                    }
                    else{
                        Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        System.out.println("I am on attach Fragment....");
    }

    @Override
    public void setUserHintcall() {
        sendcountryselction();
        /*if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {




            getUpgradeCartData();


        } else {


            getUpgradedCartDataforGuestUser();


        }*/
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void openRemovealldialog() {


        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
            alertDialogBuilder.setMessage("\n" +
                    "Are you sure to delete all items in the basket?");
        } else {
            alertDialogBuilder.setMessage("هل انت متأكد من حذف كل القطع بالسلة؟");
        }


        alertDialogBuilder.setPositiveButton("موافق",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        removeallitems();

                    }
                });

        alertDialogBuilder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {


            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    public void openDeletedialog() {
        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
            alertDialogBuilder.setMessage("Are you sure to cancel this item from your cart?");
            alertDialogBuilder.setPositiveButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
            alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {
                    if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {
                        //deleteItemfromCart();
                        upgradeddeleteItemfromCart();
                    } else {
                        //deleteItemfromCartforguestUser();
                        upgradeddeleteItemfromCartforguestUser();
                    }

                }
            });
        } else {
            alertDialogBuilder.setMessage("هل أنت متأكد من إلغاء هذا العنصر من سلة التسوق؟");
            alertDialogBuilder.setPositiveButton("موافق",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {
                                //deleteItemfromCart();
                                upgradeddeleteItemfromCart();
                            } else {
                                upgradeddeleteItemfromCartforguestUser();
                                //deleteItemfromCartforguestUser();
                            }
                        }
                    });
            alertDialogBuilder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {


                }
            });
        }


        alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    public void getUpgradedCartDataStatusforGuestUser() {
        noitemavail.setVisibility(View.GONE);
        totalcount = 0;
        cartData = new ArrayList<>();
        itemId.clear();
        cartData.clear();
        totalprice = 0;
        totalwithtax = 0;
        quantityList.clear();
        skuList.clear();


        showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", " ");
        query.put("mask_key", MySharedPreferenceClass.getMaskkey(getContext()));
        query.put("cart_id", String.valueOf(MySharedPreferenceClass.getCartId(getContext())));
        Call<UpgradedCartItems> call;
        call = Locale == "ar" ? apiService.getUpgradedCartDetailforguestuser(Constants.UPGRADED_HOST_LINK + "rest/ar/V1/api/guestcart/" + MySharedPreferenceClass.getCartId(getContext())) : apiService.getUpgradedCartDetailforguestuser(Constants.UPGRADED_HOST_LINK + "rest/en/V1/api/guestcart/" + MySharedPreferenceClass.getCartId(getContext()));
        call.enqueue(new Callback<UpgradedCartItems>() {
            @Override
            public void onResponse(Call<UpgradedCartItems> call, Response<UpgradedCartItems> response) {

                hideProgressDialog();
                int quant;
                if (response.body().response.equals("success")) {

                    upgradedcartData = response.body().data.items;

                    /*if(response.body().gift_wrap_fee!=null) {
                        gift_wrap_fees = Integer.parseInt(response.body().gift_wrap_fee);
                    }*/

                    for (int i = 0; i < response.body().data.items.size(); i++) {

                        totalcount = totalcount + (int) (response.body().data.items.get(i).qty);

                        quant = (int) response.body().data.items.get(i).qty;

                        quantityList.add(quant);
                        //}


                        if (quant == 1) {

                            totalprice = totalprice + Float.parseFloat(response.body().data.items.get(i).price);
                        } else {

                            totalprice = totalprice + Float.parseFloat(response.body().data.items.get(i).price) * quant;


                        }


                        itemId.add(response.body().data.items.get(i).id);
                        skuList.add(response.body().data.items.get(i).sku);
                        // tax_rate_in_percentage = response.body().items.goodstax_rate;


                    }


                    if (response.body().data.items.size() > 0) {

                        try {
                            linCartMain.setVisibility(View.VISIBLE);
                            proceedtoCheckout.setVisibility(View.VISIBLE);
                            removealllayout.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        initUpgardedRecycler(v);
                    } else {


                        hideProgressDialog();
                        MainActivity.notificationBadge.setVisibility(View.GONE);
                        MainActivity.bedgetext.setVisibility(View.GONE);
                        MainActivity.toolbar_bedgetext.setVisibility(View.GONE);
                        linCartMain.setVisibility(View.GONE);
                        removealllayout.setVisibility(View.GONE);
                        noitemavail.setVisibility(View.VISIBLE);

                        proceedtoCheckout.setVisibility(View.GONE);

                        MySharedPreferenceClass.setBedgeCount(getContext(), 0);

                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                            Toast.makeText(getContext(), "There is no items in your cart"
                                    , Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "لا يوجد لديك منتجات في حقيبة التسوق الخاصة بك"
                                    , Toast.LENGTH_LONG).show();
                        }


                    }


                    int subtotal = Math.round((Float.parseFloat(response.body().data.subtotal) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext())));


                    totalPricewithoutTax.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + subtotal);


                    MySharedPreferenceClass.setBedgeCount(getContext(), totalcount);
                    updatefix.updateBedgeCount();
                    MainActivity.notificationBadge.setVisibility(View.VISIBLE);

                    hideProgressDialog();
                    int giftwracharge = Math.round((Float.parseFloat(response.body().data.gift_wrap_fee) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext())));
                    giftwrappingcharge.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + giftwracharge);


                } else {

                    hideProgressDialog();
                    MainActivity.notificationBadge.setVisibility(View.GONE);
                    MainActivity.bedgetext.setVisibility(View.GONE);
                    MainActivity.toolbar_bedgetext.setVisibility(View.GONE);
                    linCartMain.setVisibility(View.GONE);
                    removealllayout.setVisibility(View.GONE);
                    noitemavail.setVisibility(View.VISIBLE);

                    proceedtoCheckout.setVisibility(View.GONE);

                    MySharedPreferenceClass.setBedgeCount(getContext(), 0);

                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                        Toast.makeText(getContext(), "There is no items in your cart"
                                , Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "لا يوجد لديك منتجات في حقيبة التسوق الخاصة بك"
                                , Toast.LENGTH_LONG).show();
                    }

                }

                for (int i = 0; i < response.body().data.items.size(); i++) {

                    if (response.body().data.items.get(i).instock == 0) {

                        Toast.makeText(getContext(), "بعض المنتجات غير متوفرة بالمخزون", Toast.LENGTH_SHORT).show();

                        return;

                    }


                }
                openSignindialog();

            }


            @Override
            public void onFailure(Call<UpgradedCartItems> call, Throwable t) {
                hideProgressDialog();
                if (Locale.equals("ar")) {


                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "يرجى التحقق من اتصالك بالإنترنت وحاول مرة أخرى", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                    }
                } else {

                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                        // logging probably not necessary
                    }
                    else{
                        Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                    }
                }
                Log.e(TAG, t.toString());
                MainActivity.notificationBadge.setVisibility(View.GONE);
                MainActivity.bedgetext.setVisibility(View.GONE);
                MainActivity.toolbar_bedgetext.setVisibility(View.GONE);
            }
        });


    }


    public void getUpgradedCartDataforGuestUser() {
        noitemavail.setVisibility(View.GONE);
        totalcount = 0;
        cartData = new ArrayList<>();
        itemId.clear();
        cartData.clear();
        totalprice = 0;
        totalwithtax = 0;
        quantityList.clear();
        skuList.clear();


        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", " ");
        query.put("mask_key", MySharedPreferenceClass.getMaskkey(getContext()));
        query.put("cart_id", String.valueOf(MySharedPreferenceClass.getCartId(getContext())));

        Call<UpgradedCartItems> call;
        call = Locale == "ar" ? apiService.getUpgradedCartDetailforguestuser(Constants.UPGRADED_HOST_LINK + "rest/ar/V1/api/guestcart/" + MySharedPreferenceClass.getCartId(getContext())) : apiService.getUpgradedCartDetailforguestuser(Constants.UPGRADED_HOST_LINK + "rest/en/V1/api/guestcart/" + MySharedPreferenceClass.getCartId(getContext()));

        call.enqueue(new Callback<UpgradedCartItems>() {
            @Override
            public void onResponse(Call<UpgradedCartItems> call, Response<UpgradedCartItems> response) {

                hideProgressDialog();
                int quant;
                if (response.isSuccessful()) {
                    if (response.body().response.equals("success")) {

                        upgradedcartData = response.body().data.items;


                    /*if(response.body().gift_wrap_fee!=null) {
                        gift_wrap_fees = Integer.parseInt(response.body().gift_wrap_fee);
                    }*/

                        for (int i = 0; i < response.body().data.items.size(); i++) {
                        /*if (response.body().data.items.get(i).qty instanceof String) {
                            totalcount = totalcount + (int) Float.parseFloat((String) response.body().data.get(i).qty);
                            quant = (int) Float.parseFloat((String) response.body().data.get(i).qty);
                            quantityList.add(quant);
                        } else {*/
                            totalcount = totalcount + (int) (response.body().data.items.get(i).qty);

                            quant = (int) response.body().data.items.get(i).qty;

                            quantityList.add(quant);
                            //}


                            if (quant == 1) {

                                totalprice = totalprice + Float.parseFloat(response.body().data.items.get(i).price);
                            } else {

                                totalprice = totalprice + Float.parseFloat(response.body().data.items.get(i).price) * quant;


                            }


                            itemId.add(response.body().data.items.get(i).id);
                            skuList.add(response.body().data.items.get(i).sku);
                            // tax_rate_in_percentage = response.body().items.goodstax_rate;


                        }


                        if (response.body().data.items.size() > 0) {

                            try {
                                linCartMain.setVisibility(View.VISIBLE);
                                proceedtoCheckout.setVisibility(View.VISIBLE);
                                removealllayout.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            initUpgardedRecycler(v);
                        } else {


                            hideProgressDialog();
                            MainActivity.notificationBadge.setVisibility(View.GONE);
                            MainActivity.bedgetext.setVisibility(View.GONE);
                            MainActivity.toolbar_bedgetext.setVisibility(View.GONE);
                            linCartMain.setVisibility(View.GONE);
                            removealllayout.setVisibility(View.GONE);
                            noitemavail.setVisibility(View.VISIBLE);

                            proceedtoCheckout.setVisibility(View.GONE);

                            MySharedPreferenceClass.setBedgeCount(getContext(), 0);

                            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                                Toast.makeText(getContext(), "There is no items in your cart"
                                        , Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "لا يوجد لديك منتجات في حقيبة التسوق الخاصة بك"
                                        , Toast.LENGTH_LONG).show();
                            }


                        }


                        int subtotal = Math.round((Float.parseFloat(response.body().data.subtotal) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext())));
                        int grandtotal = Math.round((Float.parseFloat(response.body().data.grandtotal) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext())));


                        totalPricewithoutTax.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + subtotal);


                        tvTotalPricewithTax.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + grandtotal);

                        MySharedPreferenceClass.setBedgeCount(getContext(), totalcount);
                        updatefix.updateBedgeCount();
                        MainActivity.notificationBadge.setVisibility(View.VISIBLE);

                        hideProgressDialog();
                        int giftwracharge;
                       if( response.body().data.gift_wrap_fee!=null) {
                           giftwracharge = Math.round((Float.parseFloat(response.body().data.gift_wrap_fee) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext())));
                           giftwrappingcharge.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + giftwracharge);

                       }


                        if (response.body().data.items.size() == 0) {
                            MainActivity.bedgetext.setVisibility(View.GONE);
                        }

                    } else {

                        hideProgressDialog();
                        MainActivity.notificationBadge.setVisibility(View.GONE);
                        MainActivity.bedgetext.setVisibility(View.GONE);
                        MainActivity.toolbar_bedgetext.setVisibility(View.GONE);
                        linCartMain.setVisibility(View.GONE);
                        removealllayout.setVisibility(View.GONE);
                        noitemavail.setVisibility(View.VISIBLE);

                        proceedtoCheckout.setVisibility(View.GONE);

                        MySharedPreferenceClass.setBedgeCount(getContext(), 0);

                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                            Toast.makeText(getContext(), "There is no items in your cart"
                                    , Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "لا يوجد لديك منتجات في حقيبة التسوق الخاصة بك"
                                    , Toast.LENGTH_LONG).show();
                        }

                    }


                }
            }

            @Override
            public void onFailure(Call<UpgradedCartItems> call, Throwable t) {
                hideProgressDialog();
                if (Locale.equals("ar")) {


                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "يرجى التحقق من اتصالك بالإنترنت وحاول مرة أخرى", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                    }
                } else {

                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                        // logging probably not necessary
                    }
                    else{
                        Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                    }
                }
                Log.e(TAG, t.toString());
                MainActivity.notificationBadge.setVisibility(View.GONE);
                MainActivity.bedgetext.setVisibility(View.GONE);
                MainActivity.toolbar_bedgetext.setVisibility(View.GONE);
            }
        });


    }


    public void openSignindialog() {
        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
            alertDialogBuilder.setMessage("You must be logged in first");


            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {


                            Fragment prFrag = new LoginNewFragment(CartFragment.this);
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


                }
            });
        } else {
            alertDialogBuilder.setMessage("يجب تسجيل الدخول أولا");


            alertDialogBuilder.setPositiveButton("نعم",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {


                            Fragment prFrag = new LoginNewFragment(CartFragment.this);
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


                }
            });
        }
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void removeallitems() {


        showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Map<String, String> map1 = new HashMap<>();


        if (!MySharedPreferenceClass.getMyUserId(getContext()).equals(" ")) {

            map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));


        } else {
            map1.put("cart_id", String.valueOf(MySharedPreferenceClass.getCartId(getContext())));
        }


        Call<CancelOrderListResponse> call = apiService.clearAllItems(map1);
        call.enqueue(new Callback<CancelOrderListResponse>() {
            @Override
            public void onResponse(Call<CancelOrderListResponse> call, Response<CancelOrderListResponse> response) {


                if (response.body() != null) {
                    hideProgressDialog();
                    if (response.body().getMsg().equals("success")) {


                        if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {


                            //  getCartData();
                            getUpgradeCartData();


                        } else {

                            //getCartDataforGuestUser();
                            getUpgradedCartDataforGuestUser();


                        }
                    }

                } else {
                    hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<CancelOrderListResponse> call, Throwable t) {
                hideProgressDialog();
                Log.e(TAG, t.toString());

                if (Locale.equals("ar")) {


                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "يرجى التحقق من اتصالك بالإنترنت وحاول مرة أخرى", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                    }
                } else {

                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                        // logging probably not necessary
                    }
                    else{
                        Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void showRateApp() {
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();

                Task<Void> flow = reviewManager.launchReviewFlow(getActivity(), reviewInfo);
                flow.addOnCompleteListener(task1 -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                });
            } else {
                // There was some problem, continue regardless of the result.
                // show native rate app dialog on error
                // showRateAppFallbackDialog();
            }
        });
    }

    private void showRateAppFallbackDialog() {
        new MaterialAlertDialogBuilder(getContext(), R.style.AppTheme)
                .setTitle(R.string.app_name)
                .setMessage("Review")
                .setPositiveButton("Submit", (dialog, which) -> {

                })
                .setNegativeButton("Cancel",
                        (dialog, which) -> {
                        })
                .setNeutralButton("Do it Later",
                        (dialog, which) -> {
                        })
                .setOnDismissListener(dialog -> {
                })
                .show();
    }


    public void getUpgradeCartData() {

        StringBuilder catid = new StringBuilder();
        StringBuilder catename = new StringBuilder();
        StringBuilder brandname = new StringBuilder();


        totalcount = 0;


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

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
                hideProgressDialog();
                int quant;
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().response.equals("success")) {
                        itemId.clear();
                        skuList.clear();
                        quantityList.clear();

                        upgradedcartData = response.body().data.items;
                        cartId = response.body().data.cart_id;

                        if (cartId != null) {
                            MySharedPreferenceClass.setCartId(getContext(), Integer.parseInt(cartId));
                        }

                        if (response.body().data.gift_wrap_fee != null && (int) Float.parseFloat(response.body().data.gift_wrap_fee) > 0) {
                            MySharedPreferenceClass.setCheckbofflag(getContext(), true);
                        } else {
                            MySharedPreferenceClass.setCheckbofflag(getContext(), false);

                        }

                        for (int i = 0; i < response.body().data.items.size(); i++) {
                            catename.append(response.body().data.items.get(i).category_name+","+" ");
                            catid.append(response.body().data.items.get(i).category_id+","+" ");
                            brandname.append(response.body().data.items.get(i).brand_name+","+" ");


                            totalcount = totalcount + (int) (response.body().data.items.get(i).qty);

                            quant = (int) response.body().data.items.get(i).qty;
                            itemId.add(response.body().data.items.get(i).id);
                            skuList.add(response.body().data.items.get(i).sku);
                            quantityList.add(quant);

                        }

                        Log.e("catename",catename.toString());
                        Log.e("cateid",catid.toString());
                        Log.e("brandname",brandname.toString());

                        MySharedPreferenceClass.setPurchaseCategoryNames(getApplicationContext(),catename.toString());
                        MySharedPreferenceClass.setPurchaseCategoryIds(getApplicationContext(),catid.toString());
                        MySharedPreferenceClass.setPurchaseBrandname(getApplicationContext(),brandname.toString());

                        if (response.body().data.items.size() > 0) {
                            try {
                                linCartMain.setVisibility(View.VISIBLE);
                                proceedtoCheckout.setVisibility(View.VISIBLE);
                                removealllayout.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            initUpgardedRecycler(v);
                            noitemavail.setVisibility(View.GONE);
                        } else {


                            hideProgressDialog();
                            MainActivity.notificationBadge.setVisibility(View.GONE);
                            MainActivity.bedgetext.setVisibility(View.GONE);
                            MainActivity.toolbar_bedgetext.setVisibility(View.GONE);
                            linCartMain.setVisibility(View.GONE);
                            removealllayout.setVisibility(View.GONE);
                            noitemavail.setVisibility(View.VISIBLE);

                            proceedtoCheckout.setVisibility(View.GONE);

                            MySharedPreferenceClass.setBedgeCount(getContext(), 0);

                            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                                Toast.makeText(getContext(), "There is no items in your cart"
                                        , Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "لا يوجد لديك منتجات في حقيبة التسوق الخاصة بك"
                                        , Toast.LENGTH_LONG).show();
                            }
                        }

                        int finalpricewithouttax = Math.round((Float.parseFloat(response.body().data.subtotal) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext())));
                        int finaltotalprice = Math.round((Float.parseFloat(response.body().data.grandtotal) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext())));
                        int giftwracharge = Math.round((Float.parseFloat(response.body().data.gift_wrap_fee) * MySharedPreferenceClass.getSelectedCurrencyRate(getContext())));

                        totalPricewithoutTax.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + finalpricewithouttax);

                        tvTotalPricewithTax.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + finaltotalprice);

                        giftwrappingcharge.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext()) + " " + giftwracharge);


                        MySharedPreferenceClass.setBedgeCount(getContext(), totalcount);
                        updatefix.updateBedgeCount();
                        MainActivity.notificationBadge.setVisibility(View.VISIBLE);

                        hideProgressDialog();
                        if (response.body().data.items.size() == 0) {
                            MainActivity.bedgetext.setVisibility(View.GONE);
                        }

                    }
                } else {

                    hideProgressDialog();
                    MainActivity.notificationBadge.setVisibility(View.GONE);
                    MainActivity.bedgetext.setVisibility(View.GONE);
                    MainActivity.toolbar_bedgetext.setVisibility(View.GONE);
                    linCartMain.setVisibility(View.GONE);
                    removealllayout.setVisibility(View.GONE);
                    noitemavail.setVisibility(View.VISIBLE);

                    proceedtoCheckout.setVisibility(View.GONE);

                    MySharedPreferenceClass.setBedgeCount(getContext(), 0);

                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                        Toast.makeText(getContext(), "There is no items in your cart"
                                , Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "لا يوجد لديك منتجات في حقيبة التسوق الخاصة بك"
                                , Toast.LENGTH_LONG).show();
                    }

                }

            }


            @Override
            public void onFailure(Call<UpgradedCartItems> call, Throwable t) {
                hideProgressDialog();
                Log.e(TAG, t.toString());
                if (Locale.equals("ar")) {


                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "يرجى التحقق من اتصالك بالإنترنت وحاول مرة أخرى", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                    }
                } else {

                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                        // logging probably not necessary
                    }
                    else{
                        Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //  getGiftwrapdetails();
    }


    public void sendcountryselction(String currencycode, Float rate) {
//        if(!parentDialogListener.isProgressBarRunning())
        showProgressDialog();
        /*https://upgrade.eoutlet.com/rest/en/V1/api/setquotecurrency
        *
        *
        * "{
    ""param"":{
        ""quote_id"":2016,
        ""currency_code"":""USD""
    }
}"*/


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
        Map<Object, Object> mainparam = new HashMap<>();

        Map<String, String> map1 = new HashMap<>();

        if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {

            map1.put("quote_id", MySharedPreferenceClass.getQuoteId(getContext()));
            map1.put("currency_code", currencycode);

            mainparam.put("param", map1);

        } else {
            map1.put("quote_id", String.valueOf(MySharedPreferenceClass.getCartId(getContext())));
            map1.put("currency_code", currencycode);

            mainparam.put("param", map1);

        }


        HashMap<String, String> headers = new HashMap<>();

        headers.put("Cookie", MySharedPreferenceClass.getCookies(getApplicationContext()));

        Call<CurrencysetResponse> call = apiService.postUserCurrencySelection(headers, mainparam);
        call.enqueue(new Callback<CurrencysetResponse>() {
            @Override
            public void onResponse(Call<CurrencysetResponse> call, Response<CurrencysetResponse> response) {
                hideProgressDialog();
                if (response.body() != null && response.isSuccessful()) {

                    MySharedPreferenceClass.setSelectedCurrencyName(getApplicationContext(), currencycode);
                    MySharedPreferenceClass.setSelectedCurrencyRate(getApplicationContext(), rate);


                } else {


                }

            }


            @Override
            public void onFailure(Call<CurrencysetResponse> call, Throwable t) {
                hideProgressDialog();
                Log.e(TAG, t.toString());
                if (Locale.equals("ar")) {


                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "يرجى التحقق من اتصالك بالإنترنت وحاول مرة أخرى", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                    }
                } else {

                    if  (!Util.checkConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                        // logging probably not necessary
                    }
                    else{
                        Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }

    private void showProgressDialog() {
        progressDialog = ProgressDialog.show(getActivity(), "Null", "Null", false, false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progress_bar);
        progressDialog.setCanceledOnTouchOutside(false);
    }


    private void hideProgressDialog() {
        try {
            progressDialog.dismiss();
        } catch (Exception e) {

        }

    }


}






