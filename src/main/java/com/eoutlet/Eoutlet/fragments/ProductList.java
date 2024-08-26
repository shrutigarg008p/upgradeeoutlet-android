package com.eoutlet.Eoutlet.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaDrm;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.adpters.FilterListadapeter;
import com.eoutlet.Eoutlet.adpters.ProductListAdapter;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.CatagoryList;
import com.eoutlet.Eoutlet.api.request.ListItems;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.listener.PaginationListener;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.AddtoWishList;
import com.eoutlet.Eoutlet.pojo.Child;
import com.eoutlet.Eoutlet.pojo.GetCategoryCode;
import com.eoutlet.Eoutlet.pojo.HomeCatagory1Param;
import com.eoutlet.Eoutlet.pojo.UpgradeFilterListData;
import com.eoutlet.Eoutlet.pojo.UpgradeSubcategory;
import com.eoutlet.Eoutlet.pojo.UpgradedHomeScreenCategory;
import com.eoutlet.Eoutlet.pojo.UpgradedHomeScreenHighlightedSlider;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.EditorChoice;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.FeatureBrand;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.LimitedStock;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.MainBanner;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.MonthDeal;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.NewArrival;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.Recommended;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.StealDeal;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.StealDealOffer;
import com.eoutlet.Eoutlet.viewmodels.ProductListViewModel;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.moe.pushlibrary.MoEHelper;
import com.moengage.core.Properties;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

public class ProductList extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    Context mContext;
    private ExecuteFragment execute;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView productListRecycler, filterlstrecycler;
    private ProductListAdapter mAdapter;
    private int itemcount = 0;
    public static boolean isfromcategoryfilterply = false;
    boolean isfirsttime = true;
    GridLayoutManager lm;

    private String name, category, fromwhere, onClick = " ";
    private int currentpage = 1;
    boolean view_items_flag = true;
    private Dialog dialog;
    private String item_catagory_name;
    private FirebaseAnalytics mFirebaseAnalytics;
    private int filterpage = 1;

    private HomeCatagory1Param filterlist = new HomeCatagory1Param();
    private List<Child> childlist = new ArrayList<>();
    List<MainBanner> mainBannerList;
    private List<UpgradedHomeScreenCategory> categoryList = new ArrayList<>();
    private List<UpgradedHomeScreenHighlightedSlider> highlightedSliderList;
    private List<NewArrival> newArrivalList;
    private List<EditorChoice> editorChoiceList;
    private List<MonthDeal> monthDealList;
    private List<FeatureBrand> recommendedList;
    private List<LimitedStock> limitedStockList;
    private List<StealDealOffer> limitedStockList2;
    private List<UpgradeSubcategory> childcatagorylist = new ArrayList<>();
    private List<String> childtitle = new ArrayList<>();
    private List<String> childid = new ArrayList<>();
    private SearchView searchView;
    List<List<String>> mainCatList = new ArrayList<>();
    List<List<String>> mainCatListclone = new ArrayList<>();
    private PaginationListener scrollListener;
    private List<UpgradeFilterListData> fullList = new ArrayList<>();
    private FilterListadapeter madapter;
    private boolean scrollflag = false;
    View toolbarbeg;
    private int counter = 0;

    boolean showappprogress = true;

    public int sortingflag = 0;
    String sortOrder = "desc";
    String sortBy = "created_at";
    private ImageView tick_image, tick_image2, tick_image3, tick_image4;


    public ParentDialogsListener parentDialogListener;
    private List<ListItems> catagoryList = new ArrayList<>();
    private List<ListItems> filtercatagoryList = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private LinearLayout filterbutton, sortbutton;
    private ProductListViewModel model;
    private List<ListItems> productListmain = new ArrayList<>();
    private List<ListItems> recentlyarrivedList = new ArrayList<>();
    private List<ListItems> higtolowlist = new ArrayList<>();
    private List<ListItems> lowtohighlist = new ArrayList<>();

    private List<ListItems> filterproductList = new ArrayList<>();
    private View v;
    private TextView recentlyarrived, hightolow, lowtohigh, defaultlist, toolbarname, toolBarCategoryName, itemcounttext;
    static int searchflage = 0;
    private int value;
    private String attributvalue, attributdisplay, attributcount;
    private String adapterposition = "";
    String subcatagoryid = " ";
    String subcatagoryName = "";
    int subcatagorypage = 1;
    TextView sorttext, filtertitle, filtercounter;
    private int isGetAllProductlist = 1;
    private LinearLayout filteapplybtn, sortapplybbtn;

    int selectedtopcategory = 99;
    private ArrayList svalue = new ArrayList();

    private HashMap<String, List<String>> selectedvalue = new HashMap();
    // private List<Integer> countlist = new ArrayList<>();
    private HashMap<String, Integer> countlist = new HashMap<>();
    private String selectmanufec, selectesize, selectitemtype;

    private ImageView searchImage, backarrow, sortimg, filterimg;
    private Toolbar toolbar1;
    private String Locale;
    private int brandclickpage = 1;
    private int bannerclickpage = 1;
    private int subcategoriesclickpage = 1;
    private int sizeclickpage = 1;
    public static TextView productListBadgeCount;
    private ProgressDialog progressDialog;
    String newarrivaidforsort = " ";
    FrameLayout wishlist;
    private String previouspage = " ";

    int lastclckedposition;

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


    private OnFragmentInteractionListener mListener;

    public ProductList() {

    }

    public void passData(Context context) {
        mContext = context;
        execute = (MainActivity) mContext;
    }


   /* // TODO: Rename and change types and number of parameters
    public static ProductList newInstance(String param1, String param2) {
        ProductList fragment = new ProductList();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (v == null) {


            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                Locale = "en";
                v = inflater.inflate(R.layout.fragment_product_list_english, container, false);
            } else {
                Locale = "ar";
                v = inflater.inflate(R.layout.fragment_product_list, container, false);
            }

            if(MainActivity.isaddressempty==true){
                MainActivity.isaddressempty = false;

            }
            value = getArguments().getInt("productId");
            name = getArguments().getString("name");
            category = getArguments().getString("category");
            adapterposition = getArguments().getString("adapterposition");
            attributvalue = getArguments().getString("attributvalue");
            attributdisplay = getArguments().getString("attributdisplay");
            attributcount = getArguments().getString("attributcount");
            previouspage = getArguments().getString("previouspage");
            //isfirsttime = false;



            if (adapterposition != null) {
                selectedtopcategory = Integer.valueOf(adapterposition);
            }

            fromwhere = getArguments().getString("fromwhere");
            onClick = getArguments().getString("onClick");
            initViews(v);


            lm = new GridLayoutManager(v.getContext(), 2) {
                @Override
                protected boolean isLayoutRTL() {
                    if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                        return false;
                    } else {
                        return true;
                    }

                }
            };
            getUpgradedLatestversionCode();





        /*This is pagination part used to load limited items at a time and load more items on page

        scroll  */
            scrollListener = new PaginationListener(
                    lm) {
                @Override
                public void onLoadMore(int current_page) {




                    if (isGetAllProductlist == 1) {
                        currentpage++;
                        if(currentpage == 1){
                            itemcount=0;
                        }


                        getUpgradedProductList(String.valueOf(value), currentpage);

                    } else if (isGetAllProductlist == 2) {


                        filterpage++;
                        if ((mainCatList.size() > 0 && fullList.size() > 0)) {

                            if(filterpage==1){
                                itemcount=0;
                            }

                            getUpgradedfilterdata(filterpage);
                        }


                    } else if (isGetAllProductlist == 3) {
                        subcatagorypage++;
                        if(subcatagorypage==1){
                            itemcount=0;
                        }


                        getUpgradedProductListforsubCatagory(subcatagoryid, subcatagorypage);


                    }
                    else if(isGetAllProductlist == 4){
                        brandclickpage++;
                        if(brandclickpage==1){
                            itemcount=0;
                        }
                        getUpgradedfilterdataforBrand(brandclickpage);

                    }
                    else if(isGetAllProductlist == 7){
                        sizeclickpage++;
                        if(sizeclickpage==1){
                            itemcount=0;
                        }
                        getUpgradedfilterdataforSize(sizeclickpage);

                    }
                    else if(isGetAllProductlist == 5){
                        if(bannerclickpage==1){
                            itemcount=0;
                        }
                        currentpage++;
                        getUpgradedProductList(String.valueOf(value),  currentpage);
                        //getUpgradedfilterdataforBanners(bannerclickpage);
                    }
                    else if(isGetAllProductlist == 6){
                        if(subcategoriesclickpage==1){
                            itemcount=0;
                        }
                        subcategoriesclickpage++;
                        getUpgradedfilterdataforSubcategories(subcategoriesclickpage);
                    }
                }

            };

            productListRecycler.addOnScrollListener(scrollListener);
            scrollListener.resetState();






            filterbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    DialogFragment prFrag = new FilterFragmentNew();
                    Bundle bund = new Bundle();
                    //bund.putString("previoussize", selectesize);
                    //bunde.putString("previousitemtype", selectitemtype);
                    //bundle.putString("previousmanufecturer", selectmanufec);
                    bund.putInt("catId", value);
                    prFrag.setArguments(bund);


                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);


                    prFrag.setTargetFragment(ProductList.this, 101);
                    prFrag.show(ft, "dialog  ");


                }
            });
            backarrow = toolbar1.findViewById((R.id.backarrow));

            searchImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Fragment prFrag = new SearchResultFragment();
                    Bundle databund = new Bundle();


                    getFragmentManager()
                            .beginTransaction().addToBackStack(null)
                            .replace(R.id.containerdetail, prFrag)
                            .commit();


                }
            });

            backarrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().popBackStack();
                }
            });


        }
        //iniatializesortDialogue();

        return v;
    }

    private void iniatializesortDialogue() {
        Handler mHandler;
        dialog = new Dialog(getContext(), R.style.CustomDialogAnimation);

        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
            dialog.setContentView(R.layout.sort_design_english);
        } else {

            dialog.setContentView(R.layout.sort_design);
        }

        mHandler = new Handler();
        /*DisplayMetrics displayMetrics = new DisplayMetrics();

      int  height = displayMetrics.heightPixels;
      int  width = displayMetrics.widthPixels;
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, height / 2);*/
        if (dialog != null) {
            Window window = dialog.getWindow();

            //  dialog.getWindow().setBackgroundDrawableResource(android.R.color.black);
            if (window != null) {

                // dialog.getWindow().setDimAmount(0.5f);


                WindowManager.LayoutParams params = window.getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;

                params.horizontalMargin = 10f;

                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);

                params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                params.y = 380;

                params.dimAmount = 0;
                params.dimAmount = 0.2f;
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.background_drawable);
                // params.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                window.setAttributes(params);
            }

            recentlyarrived = dialog.findViewById(R.id.recentlyarrived);
            hightolow = dialog.findViewById(R.id.hightolow);
            lowtohigh = dialog.findViewById(R.id.lowtohigh);
            defaultlist = dialog.findViewById(R.id.defaultlist);
            tick_image = dialog.findViewById(R.id.tick_image);
            tick_image2 = dialog.findViewById(R.id.tick_image2);
            tick_image3 = dialog.findViewById(R.id.tick_image3);
            tick_image4 = dialog.findViewById(R.id.tick_image4);



            if (sortingflag == 0) {
                tick_image.setVisibility(View.VISIBLE);
                tick_image2.setVisibility(View.GONE);
                tick_image3.setVisibility(View.GONE);
                tick_image4.setVisibility(View.GONE);
            }
            else if(sortingflag==1){
                tick_image.setVisibility(View.GONE);
                tick_image2.setVisibility(View.VISIBLE);
                tick_image3.setVisibility(View.GONE);
                tick_image4.setVisibility(View.GONE);


            }


            defaultlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                   /* try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //Update the value background thread to UI thread
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });*/

                    tick_image.setVisibility(View.VISIBLE);
                    tick_image2.setVisibility(View.GONE);
                    tick_image3.setVisibility(View.GONE);
                    tick_image4.setVisibility(View.GONE);
                    sortingflag = 0;
                    currentpage = 1;
                    filterpage = 1;
                    subcatagorypage = 1;
                    subcatagorypage =1;
                    brandclickpage = 1;
                    bannerclickpage =1;
                    if(currentpage == 1){
                        itemcount=0;
                    }


                    if (isGetAllProductlist == 1) {


//                        getProductList(String.valueOf(value), currentpage);
                        getUpgradedProductList(String.valueOf(value), currentpage);

                    } else if (isGetAllProductlist == 2) {


                        if ((mainCatList.size() > 0 && fullList.size() > 0)) {
                         //   getfilterdata(filterpage);
                            if(filterpage == 1){
                                itemcount=0;
                            }

                            getUpgradedfilterdata(filterpage);
                        }


                    } else if (isGetAllProductlist == 3) {
                        if(subcatagorypage == 1){
                            itemcount=0;
                        }

                        //getProductListforsubCatagory(subcatagoryid, subcatagorypage);
                        getUpgradedProductListforsubCatagory(subcatagoryid, subcatagorypage);


                    }
                   else if(isGetAllProductlist ==4){

                        getUpgradedfilterdataforBrand(brandclickpage);

                    }
                    else if(isGetAllProductlist ==7){

                        getUpgradedfilterdataforSize(brandclickpage);

                    }
                    else if(isGetAllProductlist ==5){
                        getUpgradedProductList(String.valueOf(value), currentpage);

                        //getUpgradedfilterdataforBanners(bannerclickpage);

                    }

                    else if(isGetAllProductlist ==6){

                        getUpgradedfilterdataforSubcategories(bannerclickpage);

                    }
                    else{
                        getUpgradedProductList(String.valueOf(value), currentpage);
                    }


                    dialog.cancel();


                }
            });


            recentlyarrived.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tick_image.setVisibility(View.GONE);
                    tick_image2.setVisibility(View.VISIBLE);
                    tick_image3.setVisibility(View.GONE);
                    tick_image4.setVisibility(View.GONE);

                    sortingflag = 1;
                    currentpage = 1;
                    filterpage = 1;
                    subcatagorypage = 1;

                    subcatagorypage =1;
                    brandclickpage = 1;
                    bannerclickpage =1;
                    if(currentpage == 1){
                        itemcount=0;
                    }


                    if (isGetAllProductlist == 1) {
//                        getProductList(String.valueOf(value), currentpage);
                        getUpgradedProductList(String.valueOf(value), currentpage);

                    } else if (isGetAllProductlist == 2) {

                        if ((mainCatList.size() > 0 && fullList.size() > 0)) {
                        //    getfilterdata(filterpage);
                            getUpgradedfilterdata(filterpage);
                        }


                    } else if (isGetAllProductlist == 3) {

                        //  getProductListforsubCatagory(subcatagoryid, subcatagorypage);

                        getUpgradedProductListforsubCatagory(subcatagoryid, subcatagorypage);


                    }
                    if(isGetAllProductlist ==4){

                        getUpgradedfilterdataforBrand(brandclickpage);

                    }
                    if(isGetAllProductlist ==7){

                        getUpgradedfilterdataforSize(sizeclickpage);

                    }
                    else if(isGetAllProductlist ==5){
                        getUpgradedProductList(String.valueOf(value), currentpage);
                        //getUpgradedfilterdataforBanners(bannerclickpage);

                    }

                    else if(isGetAllProductlist ==6){

                        getUpgradedfilterdataforSubcategories(bannerclickpage);

                    }

                    dialog.cancel();


                }
            });

            lowtohigh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tick_image.setVisibility(View.GONE);
                    tick_image2.setVisibility(View.GONE);
                    tick_image3.setVisibility(View.VISIBLE);
                    tick_image4.setVisibility(View.GONE);
                    sortingflag = 2;
                    currentpage = 1;

                    filterpage = 1;
                    subcatagorypage = 1;
                    subcatagorypage =1;
                    brandclickpage = 1;
                    bannerclickpage =1;

                    if(currentpage == 1){
                        itemcount=0;
                    }


                    if (isGetAllProductlist == 1) {

//                        getProductList(String.valueOf(value), currentpage);
                        getUpgradedProductList(String.valueOf(value), currentpage);


                    } else if (isGetAllProductlist == 2) {


                        if ((mainCatList.size() > 0 && fullList.size() > 0)) {
                            getUpgradedfilterdata(filterpage);
                        }


                    } else if (isGetAllProductlist == 3) {

                        // getProductListforsubCatagory(subcatagoryid, subcatagorypage);

                        getUpgradedProductListforsubCatagory(subcatagoryid, subcatagorypage);


                    }
                    if(isGetAllProductlist ==4){

                        getUpgradedfilterdataforBrand(brandclickpage);

                    }
                    if(isGetAllProductlist ==7){

                        getUpgradedfilterdataforSize(sizeclickpage);

                    }
                    else if(isGetAllProductlist ==5){
                        getUpgradedProductList(String.valueOf(value), currentpage);

                        //getUpgradedfilterdataforBanners(bannerclickpage);

                    }

                    else if(isGetAllProductlist ==6){
                        //getUpgradedProductList(String.valueOf(value), currentpage);
                        getUpgradedfilterdataforSubcategories(bannerclickpage);

                    }

                    dialog.cancel();


                }
            });

            hightolow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tick_image.setVisibility(View.GONE);
                    tick_image2.setVisibility(View.GONE);
                    tick_image3.setVisibility(View.GONE);
                    tick_image4.setVisibility(View.VISIBLE);

                    sortingflag = 3;
                    currentpage = 1;
                    filterpage = 1;
                    subcatagorypage =1;
                    brandclickpage = 1;
                    bannerclickpage =1;
                    subcatagorypage = 1;
                    if(currentpage == 1){
                        itemcount=0;
                    }


                    if (isGetAllProductlist == 1) {

//                        getProductList(String.valueOf(value), currentpage);
                        getUpgradedProductList(String.valueOf(value), currentpage);

                    } else if (isGetAllProductlist == 2) {


                        if ((mainCatList.size() > 0 && fullList.size() > 0)) {
                            getUpgradedfilterdata(filterpage);
                        }


                    } else if (isGetAllProductlist == 3) {

                        // getProductListforsubCatagory(subcatagoryid, subcatagorypage);

                        getUpgradedProductListforsubCatagory(subcatagoryid, subcatagorypage);


                    }
                    if(isGetAllProductlist ==4){

                        getUpgradedfilterdataforBrand(brandclickpage);

                    }
                    if(isGetAllProductlist ==7){

                        getUpgradedfilterdataforSize(sizeclickpage);

                    }
                    else if(isGetAllProductlist ==5){
                        getUpgradedProductList(String.valueOf(value), currentpage);
                        //getUpgradedfilterdataforBanners(bannerclickpage);

                    }

                    else if(isGetAllProductlist ==6){

                        getUpgradedfilterdataforSubcategories(bannerclickpage);

                    }

                    dialog.cancel();


                }
            });


        }

    }


    public void initViews(View v) {
        productListRecycler = v.findViewById(R.id.product_list_Recycler);
        filterlstrecycler = v.findViewById(R.id.filterlistrecycler);
        filterbutton = v.findViewById(R.id.filrterbtn);
        sortbutton = v.findViewById(R.id.sort);
        sorttext = v.findViewById(R.id.sorttitle);
        filtertitle = v.findViewById(R.id.filtertitle);
        filteapplybtn = v.findViewById(R.id.filterapplybtn);
        sortapplybbtn = v.findViewById(R.id.sortapplybtn);
        sortimg = v.findViewById(R.id.sortimg);
        filterimg = v.findViewById(R.id.filterimg);
        filtercounter = v.findViewById(R.id.filtercounter);
        toolbarname = v.findViewById(R.id.toolname);
        toolBarCategoryName = v.findViewById(R.id.tool_bar_category_name);
        toolbar1 = v.findViewById(R.id.toolbar);
        searchImage = toolbar1.findViewById(R.id.serachbar);
        itemcounttext = v.findViewById(R.id.itemsshowingtext);
        productListBadgeCount = toolbar1.findViewById(R.id.productListBadgeCount);
        wishlist = toolbar1.findViewById(R.id.wishList);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        execute = (MainActivity) getActivity();
        HomeFragmentNewDesign.getInstance().getWishListItemCount();
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySharedPreferenceClass.getMyUserId(getContext()).equals(" ")) {
                    Fragment fragment = new UpgradeWishListFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(ProductList.this.getId(), fragment);
                    ft.commit();
                } else {
                    Fragment fragment = new UpgradeWishListFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(ProductList.this.getId(), fragment);
                    ft.commit();

                }
            }
        });


        if (category != null && category != " ") {
            toolBarCategoryName.setVisibility(View.VISIBLE);
            if (name != null) {
                if (name.length() > 20) {
                    toolbarname.setTextSize(10f);
                    toolBarCategoryName.setTextSize(10f);
                }
                String trimmedname = name.trim();
                if (Locale == "en") {
                    String result[] = trimmedname.split(" ");
                    StringBuffer sb = new StringBuffer();

                    for (int i = 0; i < result.length; i++) {
                        sb.append(Character.toUpperCase(result[i].charAt(0)))
                                .append(result[i].substring(1)).append(" ");
                    }
                    toolbarname.setText(sb.toString().trim() + " - ");
                    toolBarCategoryName.setText(category);
                    if(sb.toString().trim().equalsIgnoreCase(category)) {
                        toolbarname.setText(" ");
                    }
                    moengageCategoryViewEvent(String.valueOf(value),sb.toString().trim() + " - "+category);

                } else {
                    toolbarname.setText(trimmedname.substring(0, 1).toUpperCase() + trimmedname.substring(1) + " - ");
                    toolBarCategoryName.setText(category);
                    if((trimmedname.substring(0, 1).toUpperCase() + trimmedname.substring(1).toString().trim()).equalsIgnoreCase(category)) {
                        toolbarname.setText(" ");
                    }
                    moengageCategoryViewEvent(String.valueOf(value),trimmedname.substring(0, 1).toUpperCase() + trimmedname.substring(1) + " - "+category);

                }
            }
        } else {
            toolbarname.setTextColor(Color.parseColor("#D8A664"));
            toolBarCategoryName.setVisibility(View.GONE);
            if (name != null) {
                if (name.length() > 20) {
                    toolbarname.setTextSize(10f);
                    toolBarCategoryName.setTextSize(10f);
                }
                String trimmedname = name.trim();
                if (Locale == "en") {
                    String result[] = trimmedname.split(" ");
                    StringBuffer sb = new StringBuffer();

                    for (int i = 0; i < result.length; i++) {
                        sb.append(Character.toUpperCase(result[i].charAt(0)))
                                .append(result[i].substring(1)).append(" ");
                    }
                    toolbarname.setText(sb.toString().trim());
                    moengageCategoryViewEvent(String.valueOf(value),sb.toString().trim() );

                } else {
                    toolbarname.setText(trimmedname.substring(0, 1).toUpperCase() + trimmedname.substring(1));

                    moengageCategoryViewEvent(String.valueOf(value),trimmedname.substring(0, 1).toUpperCase() + trimmedname.substring(1));

                }
            }

        }


        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);


        filteapplybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedtopcategory = 99;


                initfilterListRecycler(childtitle, counter, selectedtopcategory);

                filteapplybtn.setBackgroundResource(R.drawable.rectangular_border_black2);
                filtertitle.setTextColor(Color.parseColor("#FFFFFF"));
                filterimg.setImageResource(R.drawable.new_filter_white);
                filtercounter.setTextColor(Color.parseColor("#ffffff"));

                sortimg.setImageResource(R.drawable.ic_sort_icon_black);
                sortapplybbtn.setBackgroundResource(R.drawable.rectangular_border_corner2);
                sorttext.setTextColor(Color.parseColor("#000000"));


                DialogFragment prFrag = new FilterFragmentNew();
                Bundle bundle = new Bundle();
                Log.e("maincatList-->>", String.valueOf(mainCatList.size()));

                Log.e("countlist-->>>", String.valueOf(countlist.size()));

                bundle.putInt("catId", value);
                bundle.putString("previoussize", selectesize);
                bundle.putString("previousitemtype", selectitemtype);
                bundle.putString("previousmanufecturer", selectmanufec);
                bundle.putSerializable("prevselectedvalue", (Serializable) new HashMap<>(selectedvalue));


                bundle.putSerializable("selctecatagory", ((Serializable) new ArrayList<>(mainCatListclone)));


                bundle.putSerializable("countlist", (Serializable) new HashMap<String, Integer>(countlist));


                prFrag.setArguments(bundle);

                prFrag.setTargetFragment(ProductList.this, 101);


                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }


                prFrag.setTargetFragment(ProductList.this, 101);
                prFrag.show(ft, "dialog");


            }
        });

        sortapplybbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                selectedtopcategory = 99;
                initfilterListRecycler(childtitle, counter, selectedtopcategory);
                sortimg.setImageResource(R.drawable.ic_sort_icon_white);
                sortapplybbtn.setBackgroundResource(R.drawable.rectangular_border_black2);
                sorttext.setTextColor(Color.parseColor("#FFFFFF"));
                filteapplybtn.setBackgroundResource(R.drawable.rectangular_border_corner2);
                filtertitle.setTextColor(Color.parseColor("#000000"));
                filterimg.setImageResource(R.drawable.new_filter_black);
                filtercounter.setTextColor(Color.parseColor("#000000"));
            }
        });


    }










    public void initRecycler(View v, final List<ListItems> catagoryList) {
        productListRecycler.setHasFixedSize(true);




/*  This code used to start  grid layout from right side of the screen as comfortable for

arabic Views*/


        productListRecycler.setLayoutManager(lm/*new GridLayoutManager(v.getContext(), 2)*/);

        // specify an adapter (see also next example)


        mAdapter = new ProductListAdapter(mContext, catagoryList, new ViewListener() {
            @Override
            public void onClick(int position, View view) {


                int id = view.getId();
                switch (id) {

                    case R.id.product_list_image://button for message

                        lastclckedposition = position;
                        Fragment prFrag = new ProductDetail();


                        FragmentTransaction mFragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                        mFragmentTransaction.replace(ProductList.this.getId(), prFrag).addToBackStack(null).commit();

                        Bundle databund = new Bundle();
                        databund.putString("previouspage","FromProductListScreen");
                        databund.putString("sku", catagoryList.get(position).sku);
                        databund.putString("type", catagoryList.get(position).type);
                        databund.putString("pid", catagoryList.get(position).id);
                        databund.putString("size", catagoryList.get(position).size);
                        databund.putString("color", catagoryList.get(position).color);
                        databund.putString("color_name", catagoryList.get(position).color_name);
                        databund.putSerializable("catagoryobject", catagoryList.get(position));
                        prFrag.setArguments(databund);

                        /* getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.containerdetail, prFrag).addToBackStack(null)
                                .commit();*/
                        break;

                    case R.id.productListAddToWishlist:

                        if (!MySharedPreferenceClass.getMyUserId(getContext()).equals(" ")) {
                            //addToWishList(catagoryList.get(position).id);

                        } else {


                           // addtowishlistforGuestUser(catagoryList.get(position).id);
                        }

                        //addToWishList(catagoryList.get(position).id);

                        break;
                }
            }
        });

        productListRecycler.setAdapter(mAdapter);


    }

    public void addtowishlistforGuestUser(String id) {


        String myKey = getUniqueID2().toString().replaceAll("[^A-Za-z0-9]", "");


        Log.e("mykey is", myKey);
/*{
      "param": {
          "device_id":"23476242784b24b2646v",
          "product_id": "52862"
      }
}
*/

        showProgressDialog();


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("device_id", "121");
        map1.put("product_id", id);
        mainparam.put("param", map1);

        Call<AddtoWishList> call = apiService.addtowishlistforGuestUser(mainparam);
        call.enqueue(new Callback<AddtoWishList>() {
            @Override
            public void onResponse(Call<AddtoWishList> call, Response<AddtoWishList> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        if (response.body().msg.equals("success")) {
                            // Update Wishlist Badge Count
                            HomeFragmentNewDesign.getInstance().getWishListItemCountforguestUser();

                            if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
                                Toast.makeText(getContext(), "Product added to Wishlist", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "تم اضافة المنتج إلى قائمة الأمنيات..", Toast.LENGTH_LONG).show();
                            }


                        }
                        hideProgressDialog();
                    } else {
                        hideProgressDialog();


                    }
                } else {

                    if (response.code() == 400) {
                        hideProgressDialog();
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
                        hideProgressDialog();
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
            public void onFailure(Call<AddtoWishList> call, Throwable t) {
                hideProgressDialog();
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();

            }
        });


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    public void getUpgradedProductList(String productId, final int current_page) {
        //catagoryList = new ArrayList<>();

        if (showappprogress) {
            try {
//                if (!parentDialogListener.isProgressBarRunning())
                showProgressDialog();
            } catch (Exception e) {
            }
            ;
        }

        showappprogress = true;

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> map = new HashMap<>();
        map.put("category_id", productId);
        //map.put("page", String.valueOf(current_page));

        // map.put("sort_by", String.valueOf(sortingflag));

        Call<CatagoryList> call;
        sortOrder = sortingflag == 0 ? "asc" : sortingflag == 1 ? "desc" : sortingflag == 2 ? "asc" : "desc";
        sortBy = sortingflag == 0 ? "position" : sortingflag == 1 ? "created_at" : sortingflag == 2 ? "price" : "price";

        Log.e("sortOrder", sortOrder);
        Log.e("sortBy", sortBy);

        if (!MySharedPreferenceClass.getMyUserId(getApplicationContext()).equals(" ")) {
            if (Locale.equals("en")) {
//            call = apiService.getupgradedCataGoryListEnglish("https://upgrade.eoutlet.com/rest/en/V1/api/productlist/" + productId + "/" + current_page);
                call = apiService.getupgradedCataGoryListEnglish("https://upgrade.eoutlet.com/rest/en/V1/api/productlist/" + productId + "/" + current_page + "/" + sortBy + "/" + sortOrder + "/0/" + MySharedPreferenceClass.getMyUserId(getApplicationContext()));
            } else {
                call = apiService.getupgradedCataGoryListArabic("https://upgrade.eoutlet.com/rest/ar/V1/api/productlist/" + productId + "/" + current_page + "/" + sortBy + "/" + sortOrder + "/0/" + MySharedPreferenceClass.getMyUserId(getApplicationContext()));
//            call = apiService.getupgradedCataGoryListArabic("https://upgrade.eoutlet.com/rest/ar/V1/api/productlist/" + productId + "/" + current_page);
            }
        } else {
            if (Locale.equals("en")) {
//            call = apiService.getupgradedCataGoryListEnglish("https://upgrade.eoutlet.com/rest/en/V1/api/productlist/" + productId + "/" + current_page);
                call = apiService.getupgradedCataGoryListEnglish("https://upgrade.eoutlet.com/rest/en/V1/api/productlist/" + productId + "/" + current_page + "/" + sortBy + "/" + sortOrder + "/1" + getUniqueID2().replaceAll("[^A-Za-z0-9]", "").toUpperCase() + "2/0");
            } else {
                call = apiService.getupgradedCataGoryListArabic("https://upgrade.eoutlet.com/rest/ar/V1/api/productlist/" + productId + "/" + current_page + "/" + sortBy + "/" + sortOrder + "/" + getUniqueID2().replaceAll("[^A-Za-z0-9]", "") + "/0");
//            call = apiService.getupgradedCataGoryListArabic("https://upgrade.eoutlet.com/rest/ar/V1/api/productlist/" + productId + "/" + current_page);
            }
        }


        call.enqueue(new Callback<CatagoryList>() {
            @Override
            public void onResponse(Call<CatagoryList> call, Response<CatagoryList> response) {
                if (response.body().data != null) {
                    productListmain.addAll(response.body().data);
                }

                if (response.body() != null) {
                    hideProgressDialog();
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.body().data.size() > 0) {
                        item_catagory_name = response.body().data.get(0).category_name;

                        if (view_items_flag) {
                            view_items_flag = false;
                            firebase_view_item_list();
                        }


                        if (currentpage == 1) {
                            catagoryList.clear();


                        }


                        for (int i = 0; i < response.body().data.size(); i++) {

                            catagoryList.add(response.body().data.get(i));
                        }
                       // itemcount  = response.body().data.size()+itemcount;
                        itemcount  = Integer.valueOf(response.body().product_count);

                        if(Locale.equalsIgnoreCase("en")) {
                            itemcounttext.setText("Showing " + itemcount + " results");
                        }
                        else
                            {
                                itemcounttext.setText(" إظهار " + itemcount + " نتائج");
                            }

                        if (catagoryList.size() > 0) {

                            if (currentpage == 1) {
                                scrollListener.resetState();

                                initRecycler(v, catagoryList);

                            } else {
                                if (madapter != null) {
                                    mAdapter.notifyDataSetChanged();




                                }


                            }
                        } else {
                            //initRecycler(v, catagoryList);
                            Toast.makeText(getContext(), "لا يوجد سجلات", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        mSwipeRefreshLayout.setRefreshing(false);


                    }
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);

                    hideProgressDialog();

                }


            }


            @Override
            public void onFailure(Call<CatagoryList> call, Throwable t) {

                mSwipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, t.toString());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
                hideProgressDialog();
                //Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRefresh() {


        currentpage = 0;
       filterpage = 0;
       subcatagorypage =0;
       brandclickpage = 0;
       bannerclickpage =0;



        PaginationListener.current_page = 1;

        scrollListener.resetState();

        catagoryList = new ArrayList<>();


        showappprogress = false;




        if (isGetAllProductlist == 1) {


            currentpage++;
            if(currentpage ==1){
                itemcount = 0;

            }

            getUpgradedProductList(String.valueOf(value), currentpage);

        } else if (isGetAllProductlist == 2) {


            filterpage++;
            if(filterpage ==1){
                itemcount = 0;

            }
            if ((mainCatList.size() > 0 && fullList.size() > 0)) {

                getUpgradedfilterdata(filterpage);
            }


        } else if (isGetAllProductlist == 3) {

            subcatagorypage++;
            if(subcatagorypage ==1){
                itemcount = 0;

            }

            getUpgradedProductListforsubCatagory(subcatagoryid, subcatagorypage);


        }
        else if (isGetAllProductlist == 4) {

          brandclickpage++;
            if(brandclickpage ==1){
                itemcount = 0;

            }

           getUpgradedfilterdataforBrand(brandclickpage);


        }


        else if (isGetAllProductlist == 7) {

          sizeclickpage++;
            if(sizeclickpage ==1){
                itemcount = 0;

            }

            getUpgradedfilterdataforSize(sizeclickpage);


        }
        else if (isGetAllProductlist == 5) {

           bannerclickpage++;
            if(currentpage ==1){
                itemcount = 0;

            }
            getUpgradedProductList(String.valueOf(value), currentpage);
            //getUpgradedProductList(String.valueOf(value),  bannerclickpage);
          //getUpgradedfilterdataforBanners(bannerclickpage);


        }
        else if (isGetAllProductlist == 6) {

           subcategoriesclickpage++;

           if(subcategoriesclickpage==1){
                itemcount = 0;

            }

            getUpgradedfilterdataforSubcategories(subcategoriesclickpage);


        }

     //   getUpgradedProductList(String.valueOf(value), currentpage);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        //Toast.makeText(getContext(), "From on Result", Toast.LENGTH_SHORT).show();


        if (requestCode == 101) {
            if (resultCode == 100) {


                Bundle args = data.getBundleExtra("BUNDLE");

               // mainCatList.clear();
                //mainCatListclone.clear();
                mainCatList = new ArrayList<>();
                mainCatListclone = new ArrayList<>();
                itemcount = 0;


                //countlist.clear(); //New Add


                selectesize = args.getString("size");
                selectitemtype = args.getString("itemtype");
                selectmanufec = args.getString("manufecturer");


                mainCatList = new ArrayList<>((List<List<String>>) args.getSerializable("selctecatagory"));
                Log.e("mainCatList fromList", mainCatList.get(0).size()+" " );
             //   Log.e("mainCatList sizeList", mainCatList.size()+" " );


                for (int i = 0; i < mainCatList.size(); i++) {
                    List<String> l1 = new ArrayList<>(mainCatList.get(i));
                    mainCatListclone.add(l1);
                }


                fullList = new ArrayList<>((List<UpgradeFilterListData>) args.getSerializable("maincatagory"));
                //countlist = new ArrayList<>((List<Integer>) args.getSerializable("countlist"));
                countlist = new HashMap<String, Integer>((HashMap<String, Integer>) args.getSerializable("countlist"));
                selectedvalue = new HashMap<>((HashMap<String, List<String>>) args.getSerializable("selectedvalue"));
                counter = 0;


                Collection<Integer> values = countlist.values();
                ArrayList<Integer> val = new ArrayList<>(values);


                for (int i = 0; i < val.size(); i++) {

                    counter = counter + val.get(i);
                    initfilterListRecycler(childtitle, counter, selectedtopcategory);
                    if (counter != 0) {

                        filtercounter.setVisibility(View.VISIBLE);
                        filtercounter.setText(String.valueOf("(" + counter + ")"));
                        filterimg.setImageResource(R.drawable.new_filter_white);


                    } else {
                        filtercounter.setVisibility(View.GONE);


                    }
                }

                if (mainCatList.size() > 0 && fullList.size() > 0) {

                    isGetAllProductlist = 2;

                    filterpage = 1;

                    //getfilterdata(filterpage);
                    getUpgradedfilterdata(1);
                }
            }


        }


    }


    /*Applying filter in below method after apply button click in filter class*/



    public void getUpgradedfilterdata(final int filterpage) {
        /*https://upgrade.eoutlet.com/rest/en/V1/api/getfilterdata*/

        /*{
    "param": {
        "category_id": 4,
        "order_by": "position",
        "sort_order": "ASC",
        "page": 1,
        "product_count": 10,
        "data": [
            {
                "code": "item_type",
                "name": "Item Type",
                "data": [{"display": "Berbatose","value": "5459","count": "3"},{"display": "Baby Kits","value": "5460","count": "4"}]
            },
            {
                "code": "manufacturer",
                "name": "Brand",
                "data": [{"display":"ARMANI BABY","value":"5484","count":"7"}]
            }
            ]
        }
    }   */


        showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        sortOrder = sortingflag == 0 ? "asc" : sortingflag == 1 ? "desc" : sortingflag == 2 ? "asc" : "desc";
        sortBy = sortingflag == 0 ? "position" : sortingflag == 1 ? "created_at" : sortingflag == 2 ? "price" : "price";

        map1.put("category_id", value);
        map1.put("order_by", String.valueOf(sortBy));
        map1.put("sort_order", sortOrder);
        map1.put("product_count", 10);
        map1.put("page", String.valueOf(filterpage));



    /*    HashMap<Object, Object> map = new HashMap<>();

        map.put("cat_id", value);
        map.put("page", String.valueOf(filterpage));
        map.put("sort_by", String.valueOf(sortingflag));
*/

        ArrayList<Object> data = new ArrayList<>();

        if (fullList.size() > 0) {
            for (int i = 0; i < fullList.size(); i++) {
                HashMap<Object, Object> catagorydetailobject = new HashMap<>();

                catagorydetailobject.put("name", fullList.get(i).name);
                catagorydetailobject.put("code", fullList.get(i).code);

                ArrayList<Object> detaildata = new ArrayList<>();


                for (int j = 0; j < fullList.get(i).data.size(); j++) {
                    if (mainCatList.get(i).size() > 0) {

                        if (mainCatList.get(i).get(j).equals("true")) {


                            HashMap<Object, Object> internalobject = new HashMap();

                            internalobject.put("display", fullList.get(i).data.get(j).display);
                            internalobject.put("value", fullList.get(i).data.get(j).value);
                            internalobject.put("count", fullList.get(i).data.get(j).count);

                            detaildata.add(internalobject);

                        }
                    }


                }

                catagorydetailobject.put("data", detaildata);
                data.add(catagorydetailobject);


            }
        }
        //map.put("data", data);
        map1.put("data", data);

        mainparam.put("param", map1);


        Call<CatagoryList> call = Locale == "ar" ? apiService.getUpgradedFilterProductListArabic(mainparam) : apiService.getUpgradedFilterProductList(mainparam);
        // Call<CatagoryList> call =  apiService.getUpgradedFilterProductList(mainparam);


        call.enqueue(new Callback<CatagoryList>() {
            @Override
            public void onResponse(Call<CatagoryList> call, Response<CatagoryList> response) {
                if (filterpage == 1) {
                    catagoryList = new ArrayList<>();
                    scrollListener.resetState();
                }
                productListmain.clear();
                productListmain.addAll(response.body().data); //added  to get latest data in sorting

                if (response.body() != null && response.body().msg.equals("success")) {
                    hideProgressDialog();
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.body().data.size() > 0) {
                     //   itemcount  = response.body().data.size()+itemcount;

                        itemcount  = Integer.valueOf(response.body().product_count);
                        if(Locale.equalsIgnoreCase("en")) {
                            itemcounttext.setText("Showing " + itemcount + " results");
                        }
                        else
                        {
                            itemcounttext.setText("إظهار" + itemcount + " نتائج");
                        }
                        for (int i = 0; i < response.body().data.size(); i++) {
                            catagoryList.add(response.body().data.get(i));
                        }

                        if (catagoryList.size() > 0) {
                            if (filterpage == 1) {

                                initRecycler(v, catagoryList);
                            } else {

                                mAdapter.notifyDataSetChanged();

                            }
                        } else {
                            initRecycler(v, catagoryList);

                        }

                    } else {


                    }
                } else {
                    hideProgressDialog();
                    if (filterpage == 1) {
                        catagoryList.clear();
                        initRecycler(v, catagoryList);
                        //mAdapter.notifyDataSetChanged();
                        if(Locale.equalsIgnoreCase("en")) {
                            itemcounttext.setText("Showing " + "0" + " results");
                        }
                        else
                        {
                            itemcounttext.setText("إظهار" + "0"+ " نتائج");
                        }
                    }

                    mSwipeRefreshLayout.setRefreshing(false);


                }


            }


            @Override
            public void onFailure(Call<CatagoryList> call, Throwable t) {
                hideProgressDialog();
                mSwipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, t.toString());
                if (filterpage == 1) {
                    catagoryList.clear();
                    initRecycler(v, catagoryList);
                    //mAdapter.notifyDataSetChanged();
                }


            }
        });
    }
    public void getUpgradedfilterdataforBrand(final int filterpage) {
        /*https://upgrade.eoutlet.com/rest/en/V1/api/getfilterdata*/
       /* {"param":{"category_id":22,"data":[],"product_count":10,"order_by":"position","page":"1","sort_order":"asc"}}*/
        /*{
    "param": {
        "category_id": 4,
        "order_by": "position",
        "sort_order": "ASC",
        "page": 1,
        "product_count": 10,
        "data": [
            {
                "code": "item_type",
                "name": "Item Type",
                "data": [{"display": "Berbatose","value": "5459","count": "3"},{"display": "Baby Kits","value": "5460","count": "4"}]
            },
            {
                "code": "manufacturer",
                "name": "Brand",
                "data": [{"display":"ARMANI BABY","value":"5484","count":"7"}]
            }
            ]
        }
    }   */


        showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        sortOrder = sortingflag == 0 ? "asc" : sortingflag == 1 ? "desc" : sortingflag == 2 ? "asc" : "desc";
        sortBy = sortingflag == 0 ? "position" : sortingflag == 1 ? "created_at" : sortingflag == 2 ? "price" : "price";

        map1.put("category_id", value);
        map1.put("order_by", String.valueOf(sortBy));
        map1.put("sort_order", sortOrder);
        map1.put("product_count", 10);
        map1.put("page", String.valueOf(filterpage));



    /*    HashMap<Object, Object> map = new HashMap<>();

        map.put("cat_id", value);
        map.put("page", String.valueOf(filterpage));
        map.put("sort_by", String.valueOf(sortingflag));
*/

        ArrayList<Object> data = new ArrayList<>();


                HashMap<Object, Object> catagorydetailobject = new HashMap<>();

                catagorydetailobject.put("name", "Brand");
                catagorydetailobject.put("code", "manufacturer");
                ArrayList<Object> detaildata = new ArrayList<>();
                HashMap<Object, Object> internalobject = new HashMap();
                internalobject.put("display", attributdisplay);
                internalobject.put("value", attributvalue);
                internalobject.put("count",attributcount);

                            detaildata.add(internalobject);






                catagorydetailobject.put("data", detaildata);
                data.add(catagorydetailobject);




        //map.put("data", data);
        map1.put("data", data);

        mainparam.put("param", map1);


        Call<CatagoryList> call = Locale == "ar" ? apiService.getUpgradedFilterProductListArabic(mainparam) : apiService.getUpgradedFilterProductList(mainparam);
        // Call<CatagoryList> call =  apiService.getUpgradedFilterProductList(mainparam);


        call.enqueue(new Callback<CatagoryList>() {
            @Override
            public void onResponse(Call<CatagoryList> call, Response<CatagoryList> response) {
                if (filterpage == 1) {
                    catagoryList = new ArrayList<>();
                    scrollListener.resetState();
                }
                productListmain.clear();
                productListmain.addAll(response.body().data); //added  to get latest data in sorting

                if (response.body() != null && response.body().msg.equals("success")) {
                    hideProgressDialog();
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.body().data.size() > 0) {
                       // itemcount  = response.body().data.size()+itemcount;

                        itemcount  = Integer.valueOf(response.body().product_count);
                        if(Locale.equalsIgnoreCase("en")) {
                            itemcounttext.setText("Showing " + itemcount + " results");
                        }
                        else
                        {
                            itemcounttext.setText("إظهار" + itemcount + " نتائج");
                        }
                        for (int i = 0; i < response.body().data.size(); i++) {
                            catagoryList.add(response.body().data.get(i));
                        }

                        if (catagoryList.size() > 0) {
                            if (filterpage == 1) {

                                initRecycler(v, catagoryList);
                            } else {

                                mAdapter.notifyDataSetChanged();

                            }
                        } else {
                            initRecycler(v, catagoryList);

                        }

                    } else {


                    }
                } else {
                    hideProgressDialog();
                    if (filterpage == 1) {
                        catagoryList.clear();
                        initRecycler(v, catagoryList);
                        //mAdapter.notifyDataSetChanged();
                    }

                    mSwipeRefreshLayout.setRefreshing(false);


                }


            }


            @Override
            public void onFailure(Call<CatagoryList> call, Throwable t) {
                hideProgressDialog();
                mSwipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, t.toString());
                if (filterpage == 1) {
                    catagoryList.clear();
                    initRecycler(v, catagoryList);
                    //mAdapter.notifyDataSetChanged();
                }


            }
        });
    }
    public void getUpgradedfilterdataforSize(final int filterpage) {
        /*https://upgrade.eoutlet.com/rest/en/V1/api/getfilterdata*/
        /* {"param":{"category_id":22,"data":[],"product_count":10,"order_by":"position","page":"1","sort_order":"asc"}}*/
        /*{
    "param": {
        "category_id": 4,
        "order_by": "position",
        "sort_order": "ASC",
        "page": 1,
        "product_count": 10,
        "data": [
            {
                "code": "item_type",
                "name": "Item Type",
                "data": [{"display": "Berbatose","value": "5459","count": "3"},{"display": "Baby Kits","value": "5460","count": "4"}]
            },
            {
                "code": "manufacturer",
                "name": "Brand",
                "data": [{"display":"ARMANI BABY","value":"5484","count":"7"}]
            }
            ]
        }
    }   */


        showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        sortOrder = sortingflag == 0 ? "asc" : sortingflag == 1 ? "desc" : sortingflag == 2 ? "asc" : "desc";
        sortBy = sortingflag == 0 ? "position" : sortingflag == 1 ? "created_at" : sortingflag == 2 ? "price" : "price";

        map1.put("category_id", value);
        map1.put("order_by", String.valueOf(sortBy));
        map1.put("sort_order", sortOrder);
        map1.put("product_count", 10);
        map1.put("page", String.valueOf(filterpage));



    /*    HashMap<Object, Object> map = new HashMap<>();

        map.put("cat_id", value);
        map.put("page", String.valueOf(filterpage));
        map.put("sort_by", String.valueOf(sortingflag));
*/

        ArrayList<Object> data = new ArrayList<>();


        HashMap<Object, Object> catagorydetailobject = new HashMap<>();

        catagorydetailobject.put("name", "Size");
        catagorydetailobject.put("code", "size");
        ArrayList<Object> detaildata = new ArrayList<>();
        HashMap<Object, Object> internalobject = new HashMap();
        internalobject.put("display", attributdisplay);
        internalobject.put("value", attributvalue);
        internalobject.put("count",attributcount);

        detaildata.add(internalobject);






        catagorydetailobject.put("data", detaildata);
        data.add(catagorydetailobject);




        //map.put("data", data);
        map1.put("data", data);

        mainparam.put("param", map1);


        Call<CatagoryList> call = Locale == "ar" ? apiService.getUpgradedFilterProductListArabic(mainparam) : apiService.getUpgradedFilterProductList(mainparam);
        // Call<CatagoryList> call =  apiService.getUpgradedFilterProductList(mainparam);


        call.enqueue(new Callback<CatagoryList>() {
            @Override
            public void onResponse(Call<CatagoryList> call, Response<CatagoryList> response) {
                if (filterpage == 1) {
                    catagoryList = new ArrayList<>();
                    scrollListener.resetState();
                }
                productListmain.clear();
                productListmain.addAll(response.body().data); //added  to get latest data in sorting

                if (response.body() != null && response.body().msg.equals("success")) {
                    hideProgressDialog();
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.body().data.size() > 0) {
                        // itemcount  = response.body().data.size()+itemcount;

                        itemcount  = Integer.valueOf(response.body().product_count);
                        if(Locale.equalsIgnoreCase("en")) {
                            itemcounttext.setText("Showing " + itemcount + " results");
                        }
                        else
                        {
                            itemcounttext.setText("إظهار" + itemcount + " نتائج");
                        }
                        for (int i = 0; i < response.body().data.size(); i++) {
                            catagoryList.add(response.body().data.get(i));
                        }

                        if (catagoryList.size() > 0) {
                            if (filterpage == 1) {

                                initRecycler(v, catagoryList);
                            } else {

                                mAdapter.notifyDataSetChanged();

                            }
                        } else {
                            initRecycler(v, catagoryList);

                        }

                    } else {


                    }
                } else {
                    hideProgressDialog();
                    if (filterpage == 1) {
                        catagoryList.clear();
                        initRecycler(v, catagoryList);
                        //mAdapter.notifyDataSetChanged();
                    }

                    mSwipeRefreshLayout.setRefreshing(false);


                }


            }


            @Override
            public void onFailure(Call<CatagoryList> call, Throwable t) {
                hideProgressDialog();
                mSwipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, t.toString());
                if (filterpage == 1) {
                    catagoryList.clear();
                    initRecycler(v, catagoryList);
                    //mAdapter.notifyDataSetChanged();
                }


            }
        });
    }
    public void getUpgradedfilterdataforBanners(final int filterpage) {
        /*https://upgrade.eoutlet.com/rest/en/V1/api/getfilterdata*/
        /* {"param":{"category_id":22,"data":[],"product_count":10,"order_by":"position","page":"1","sort_order":"asc"}}*/
        /*{
    "param": {
        "category_id": 4,
        "order_by": "position",
        "sort_order": "ASC",
        "page": 1,
        "product_count": 10,
        "data": [
            {
                "code": "item_type",
                "name": "Item Type",
                "data": [{"display": "Berbatose","value": "5459","count": "3"},{"display": "Baby Kits","value": "5460","count": "4"}]
            },
            {
                "code": "manufacturer",
                "name": "Brand",
                "data": [{"display":"ARMANI BABY","value":"5484","count":"7"}]
            }
            ]
        }
    }   */


        showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        sortOrder = sortingflag == 0 ? "asc" : sortingflag == 1 ? "desc" : sortingflag == 2 ? "asc" : "desc";
        sortBy = sortingflag == 0 ? "position" : sortingflag == 1 ? "created_at" : sortingflag == 2 ? "price" : "price";

        map1.put("category_id", value);
        map1.put("order_by", String.valueOf(sortBy));
        map1.put("sort_order", sortOrder);
        map1.put("product_count", 10);
        map1.put("page", String.valueOf(filterpage));



    /*    HashMap<Object, Object> map = new HashMap<>();

        map.put("cat_id", value);
        map.put("page", String.valueOf(filterpage));
        map.put("sort_by", String.valueOf(sortingflag));
*/

        ArrayList<Object> data = new ArrayList<>();


        HashMap<Object, Object> catagorydetailobject = new HashMap<>();

        catagorydetailobject.put("name", "item_type");
        catagorydetailobject.put("code", "item_type");
        ArrayList<Object> detaildata = new ArrayList<>();
        HashMap<Object, Object> internalobject = new HashMap();
        internalobject.put("display", attributdisplay);
        internalobject.put("value", attributvalue);
        internalobject.put("count",attributcount);

        detaildata.add(internalobject);






        catagorydetailobject.put("data", detaildata);
        data.add(catagorydetailobject);




        //map.put("data", data);
        map1.put("data", data);

        mainparam.put("param", map1);


        Call<CatagoryList> call = Locale == "ar" ? apiService.getUpgradedFilterProductListArabic(mainparam) : apiService.getUpgradedFilterProductList(mainparam);
        // Call<CatagoryList> call =  apiService.getUpgradedFilterProductList(mainparam);


        call.enqueue(new Callback<CatagoryList>() {
            @Override
            public void onResponse(Call<CatagoryList> call, Response<CatagoryList> response) {
                if (filterpage == 1) {
                    catagoryList = new ArrayList<>();
                    scrollListener.resetState();
                }
                productListmain.clear();
                productListmain.addAll(response.body().data); //added  to get latest data in sorting

                if (response.body() != null && response.body().msg.equals("success")) {
                    hideProgressDialog();
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.body().data.size() > 0) {
                        // itemcount  = response.body().data.size()+itemcount;

                        itemcount  = Integer.valueOf(response.body().product_count);
                        if(Locale.equalsIgnoreCase("en")) {
                            itemcounttext.setText("Showing " + itemcount + " results");
                        }
                        else
                        {
                            itemcounttext.setText("إظهار" + itemcount + " نتائج");
                        }
                        for (int i = 0; i < response.body().data.size(); i++) {
                            catagoryList.add(response.body().data.get(i));
                        }

                        if (catagoryList.size() > 0) {
                            if (filterpage == 1) {

                                initRecycler(v, catagoryList);
                            } else {

                                mAdapter.notifyDataSetChanged();

                            }
                        } else {
                            initRecycler(v, catagoryList);

                        }

                    } else {


                    }
                } else {
                    hideProgressDialog();
                    if (filterpage == 1) {
                        catagoryList.clear();
                        initRecycler(v, catagoryList);
                        //mAdapter.notifyDataSetChanged();
                    }

                    mSwipeRefreshLayout.setRefreshing(false);


                }


            }


            @Override
            public void onFailure(Call<CatagoryList> call, Throwable t) {
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
                hideProgressDialog();
                mSwipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, t.toString());
                if (filterpage == 1) {
                    catagoryList.clear();
                    initRecycler(v, catagoryList);
                    //mAdapter.notifyDataSetChanged();
                }


            }
        });
    }

    public void getUpgradedfilterdataforSubcategories(final int filterpage) {
        /*https://upgrade.eoutlet.com/rest/en/V1/api/getfilterdata*/
        /* {"param":{"category_id":22,"data":[],"product_count":10,"order_by":"position","page":"1","sort_order":"asc"}}*/
        /*{
    "param": {
        "category_id": 4,
        "order_by": "position",
        "sort_order": "ASC",
        "page": 1,
        "product_count": 10,
        "data": [
            {
                "code": "item_type",
                "name": "Item Type",
                "data": [{"display": "Berbatose","value": "5459","count": "3"},{"display": "Baby Kits","value": "5460","count": "4"}]
            },
            {
                "code": "manufacturer",
                "name": "Brand",
                "data": [{"display":"ARMANI BABY","value":"5484","count":"7"}]
            }
            ]
        }
    }   */


        showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        sortOrder = sortingflag == 0 ? "asc" : sortingflag == 1 ? "desc" : sortingflag == 2 ? "asc" : "desc";
        sortBy = sortingflag == 0 ? "position" : sortingflag == 1 ? "created_at" : sortingflag == 2 ? "price" : "price";

        map1.put("category_id", value);
        map1.put("order_by", String.valueOf(sortBy));
        map1.put("sort_order", sortOrder);
        map1.put("product_count", 10);
        map1.put("page", String.valueOf(filterpage));



    /*    HashMap<Object, Object> map = new HashMap<>();

        map.put("cat_id", value);
        map.put("page", String.valueOf(filterpage));
        map.put("sort_by", String.valueOf(sortingflag));
*/

        ArrayList<Object> data = new ArrayList<>();


        HashMap<Object, Object> catagorydetailobject = new HashMap<>();

        catagorydetailobject.put("name", "item_type");
        catagorydetailobject.put("code", "item_type");

        ArrayList<Object> detaildata = new ArrayList<>();
        HashMap<Object, Object> internalobject = new HashMap();
        internalobject.put("display", attributdisplay);
        internalobject.put("value", attributvalue);
        internalobject.put("count",attributcount);

        detaildata.add(internalobject);






        catagorydetailobject.put("data", detaildata);
        data.add(catagorydetailobject);




        //map.put("data", data);
        map1.put("data", data);

        mainparam.put("param", map1);


        Call<CatagoryList> call = Locale == "ar" ? apiService.getUpgradedFilterProductListArabic(mainparam) : apiService.getUpgradedFilterProductList(mainparam);
        // Call<CatagoryList> call =  apiService.getUpgradedFilterProductList(mainparam);


        call.enqueue(new Callback<CatagoryList>() {
            @Override
            public void onResponse(Call<CatagoryList> call, Response<CatagoryList> response) {
                if (filterpage == 1) {
                    catagoryList = new ArrayList<>();
                    scrollListener.resetState();
                }
                productListmain.clear();
                productListmain.addAll(response.body().data); //added  to get latest data in sorting

                if (response.body() != null && response.body().msg.equals("success")) {
                    hideProgressDialog();
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.body().data.size() > 0) {
                        // itemcount  = response.body().data.size()+itemcount;

                        itemcount  = Integer.valueOf(response.body().product_count);
                        if(Locale.equalsIgnoreCase("en")) {
                            itemcounttext.setText("Showing " + itemcount + " results");
                        }
                        else
                        {
                            itemcounttext.setText("إظهار" + itemcount + " نتائج");
                        }
                        for (int i = 0; i < response.body().data.size(); i++) {
                            catagoryList.add(response.body().data.get(i));
                        }

                        if (catagoryList.size() > 0) {
                            if (filterpage == 1) {

                                initRecycler(v, catagoryList);
                            } else {

                                mAdapter.notifyDataSetChanged();

                            }
                        } else {
                            initRecycler(v, catagoryList);

                        }

                    } else {


                    }
                } else {
                    hideProgressDialog();
                    if (filterpage == 1) {
                        catagoryList.clear();
                        initRecycler(v, catagoryList);
                        //mAdapter.notifyDataSetChanged();
                    }

                    mSwipeRefreshLayout.setRefreshing(false);


                }


            }


            @Override
            public void onFailure(Call<CatagoryList> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();

                mSwipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, t.toString());
                if (filterpage == 1) {
                    catagoryList.clear();
                    initRecycler(v, catagoryList);
                    //mAdapter.notifyDataSetChanged();
                }


            }
        });
    }
    public void initfilterListRecycler(final List<String> cat6, int counter, int selectedtopcategory2) {


        filterlstrecycler.setHasFixedSize(true);
        LinearLayoutManager lm;
        // use a linear layout manager
        if (MySharedPreferenceClass.getChoosenlanguage(v.getContext()).equals("en")) {

            lm = new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false);
        } else {

            lm = new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, true);
        }

        filterlstrecycler.setLayoutManager(lm);

        // specify an adapter (see also next example)
        madapter = new FilterListadapeter(getContext(), cat6, counter, selectedtopcategory2


                , new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();
                selectedtopcategory = position;

                switch (id) {

                    case R.id.filterlistclick://button for message


                        sortimg.setImageResource(R.drawable.ic_sort_icon_black);
                        sortapplybbtn.setBackgroundResource(R.drawable.rectangular_border_corner2);
                        sorttext.setTextColor(Color.parseColor("#000000"));


                        filteapplybtn.setBackgroundResource(R.drawable.rectangular_border_corner2);
                        filtertitle.setTextColor(Color.parseColor("#000000"));
                        filterimg.setImageResource(R.drawable.new_filter_black);
                        filtercounter.setTextColor(Color.parseColor("#000000"));


                        tick_image.setVisibility(View.GONE);
                        tick_image2.setVisibility(View.GONE);
                        tick_image3.setVisibility(View.GONE);
                        tick_image4.setVisibility(View.GONE);

                        countlist.clear();
                        mainCatList.clear();
                        subcatagorypage = 1;
                        isGetAllProductlist = 3;

                        int finalposition = position /*- 2*/;
                        catagoryList = new ArrayList<>();
                        if (fromwhere.equals("fromhome")) {
                            subcatagoryid = childid.get(finalposition);
                            subcatagoryName = childtitle.get(finalposition);


                        } else if (fromwhere.equals("fromcatagory")) {
                            subcatagoryid = childid.get(finalposition);
                            subcatagoryName = childtitle.get(finalposition);

                        }

                        value = Integer.parseInt(subcatagoryid);


                        //tooltext.setText(childtitle.get(position));

                        sortingflag = 0;
                        subcatagorypage = 1;
                        if(subcatagorypage==1){
                            itemcount=0;
                        }
                        //  getProductListforsubCatagory(subcatagoryid, subcatagorypage);
                        getUpgradedProductListforsubCatagory(subcatagoryid, subcatagorypage);


                        //  }


                        break;
                }
            }
        }


        );
        filterlstrecycler.setAdapter(madapter);
        filterlstrecycler.setNestedScrollingEnabled(false);


        RecyclerView.LayoutManager layoutManager = filterlstrecycler.getLayoutManager();


        if (adapterposition != null)
            filterlstrecycler.getLayoutManager().scrollToPosition(Integer.parseInt(adapterposition));


        filterlstrecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);


                RecyclerView.LayoutManager layoutManager = filterlstrecycler.getLayoutManager();

                int firstCompleteVisibleItemPosition = -1;
                int lastCompleteVisibleItemPosition = -1;
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                    firstCompleteVisibleItemPosition = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                    lastCompleteVisibleItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                } else if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    firstCompleteVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    lastCompleteVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                }

                if (dy > 0) {
                    // Scrolling up
                } else {
                    // Scrolling down
                }

                if (firstCompleteVisibleItemPosition == 2) {
                    // dy < 0 means scroll to bottom, dx < 0 means scroll to right at beginning.
                    if (dy < 0 || dx < 0) {
                        // Means scroll to bottom.
                        if (dy < 0) {

                        }

                        // Means scroll to right.
                        if (dx < 0) {
                            //childtext.setVisibility(View.GONE);
                            System.out.println("Scrolled to Right");
                        }
                    }
                }
                // Means scroll at ending ( bottom to top or right to left )
                else if (lastCompleteVisibleItemPosition == (totalItemCount - 1)) {
                    // dy > 0 means scroll to up, dx > 0 means scroll to left at ending.
                    if (dy > 0 || dx > 0) {
                        // Scroll to top
                        if (dy > 0) {

                        }

                        // Scroll to left
                        if (dx > 0) {
                            //childtext.setVisibility(View.VISIBLE);
                            System.out.println("Scrolled to Left");
                        }
                    }
                }


            }


        });


    }




    public void getUpgradedProductListforsubCatagory(String productId, final int current_page) {
        if (subcatagoryName != null) {
            String trimmedname = subcatagoryName.trim();
            if (Locale == "en") {
                String result[] = trimmedname.split(" ");
                StringBuffer sb = new StringBuffer();

                for (int i = 0; i < result.length; i++) {
                    sb.append(Character.toUpperCase(result[i].charAt(0)))
                            .append(result[i].substring(1)).append(" ");
                }
                toolbarname.setText(sb.toString().trim());
            } else {
                toolbarname.setText(trimmedname.substring(0, 1).toUpperCase() + trimmedname.substring(1));
            }
        }

        //catagoryList = new ArrayList<>();

        if (showappprogress) {
            showProgressDialog();
        }

        showappprogress = true;

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> map = new HashMap<>();
        map.put("category_id", productId);
        //map.put("page", String.valueOf(current_page));

        // map.put("sort_by", String.valueOf(sortingflag));

        Call<CatagoryList> call;
        sortOrder = sortingflag == 0 ? "asc" : sortingflag == 1 ? "desc" : sortingflag == 2 ? "asc" : "desc";
        sortBy = sortingflag == 0 ? "position" : sortingflag == 1 ? "created_at" : sortingflag == 2 ? "price" : "price";
        Log.e("sortOrder", sortOrder);
        Log.e("sortBy", sortBy);

        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
//            call = apiService.getupgradedCataGoryListEnglish("https://upgrade.eoutlet.com/rest/en/V1/api/productlist/" + productId + "/" + current_page);
            call = apiService.getupgradedCataGoryListEnglish("https://upgrade.eoutlet.com/rest/en/V1/api/productlist/" + productId + "/" + current_page + "/" + sortBy + "/" + sortOrder);
        } else {
            call = apiService.getupgradedCataGoryListArabic("https://upgrade.eoutlet.com/rest/ar/V1/api/productlist/" + productId + "/" + current_page + "/" + sortBy + "/" + sortOrder);
//            call = apiService.getupgradedCataGoryListArabic("https://upgrade.eoutlet.com/rest/ar/V1/api/productlist/" + productId + "/" + current_page);
        }


        call.enqueue(new Callback<CatagoryList>() {
            @Override
            public void onResponse(Call<CatagoryList> call, Response<CatagoryList> response) {
                if (response.body().data != null) {
                    productListmain.addAll(response.body().data);
                }

                if (response.body() != null) {
                    hideProgressDialog();
                    mSwipeRefreshLayout.setRefreshing(false);
                    productListmain.clear();

                    productListmain.addAll(response.body().data);
                    if (response.body().data.size() > 0) {
                        if (subcatagorypage == 1) {
                            catagoryList = new ArrayList<>();
                            scrollListener.resetState();
                        }


                        if (subcatagorypage == 1) {


                            catagoryList.clear();

                        }


                        for (int i = 0; i < response.body().data.size(); i++) {
                            catagoryList.add(response.body().data.get(i));
                        }

                      //  itemcount  = response.body().data.size()+itemcount;
                        itemcount  = Integer.valueOf(response.body().product_count);
                        if(Locale.equalsIgnoreCase("en")) {
                            itemcounttext.setText("Showing " + itemcount + " results");
                        }
                        else
                        {
                            itemcounttext.setText("إظهار" + itemcount + " نتائج");
                        }

                        if (catagoryList.size() > 0) {
                            if (subcatagorypage == 1) {


                                initRecycler(v, catagoryList);
                            } else {

                                mAdapter.notifyDataSetChanged();


                            }
                        } else {

                        }

                    } else {
                        mSwipeRefreshLayout.setRefreshing(false);


                    }
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    hideProgressDialog();

                }


            }


            @Override
            public void onFailure(Call<CatagoryList> call, Throwable t) {
                hideProgressDialog();
                mSwipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, t.toString());

                if(Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isfirsttime)

            if (mainCatList.size() > 0 && fullList.size() > 0) {

                isGetAllProductlist = 2;

                filterpage = 1;


                getUpgradedfilterdata(1);
            }
        else {

            if(categoryList.size()>0)
            getWishListFlag();


               // getUpgradedLatestversionCode();


            }

    }


    @Override
    public void onStart() {
        super.onStart();
        //Toast.makeText(getContext(),"on Start",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        isfirsttime = false;
        //Toast.makeText(getContext(),"on Pau",Toast.LENGTH_SHORT).show();
    }

    public void firebase_view_item_list() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, item_catagory_name);
        bundle.putString("isFrom",previouspage);


        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, bundle);

    }

    //    add To wishlist function
    public void addToWishList(String id) {
        showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        map1.put("product_id", id);
        mainparam.put("param", map1);

        Call<AddtoWishList> call = apiService.addtowishlist(mainparam);
        call.enqueue(new Callback<AddtoWishList>() {
            @Override
            public void onResponse(Call<AddtoWishList> call, Response<AddtoWishList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        hideProgressDialog();
                        if (response.body().response.equals("success")) {
                            Toast.makeText(getContext(), "Added to Wishlist", Toast.LENGTH_LONG).show();
                            // Update Wishlist Badge Count
                            HomeFragmentNewDesign.getInstance().getWishListItemCount();
                        }
                    } else {
                        hideProgressDialog();
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
            public void onFailure(Call<AddtoWishList> call, Throwable t) {
                hideProgressDialog();
                Log.e(TAG, t.toString());
                if(Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                }
                else{
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
                        newarrivaidforsort = response.body().sortby_newarrival;

                        String[] newarrivalSplit = newarrivaidforsort.split(",");
                        ArrayList<String> newarrivalList = new ArrayList<String>(
                                Arrays.asList(newarrivalSplit));
                        if(newarrivalList.contains(String.valueOf(value)))
                        {

                            sortingflag=1;
                            Log.e("newarrivalsort",String.valueOf(newarrivaidforsort));
                            Log.e("sortingflag",String.valueOf(value));

                        }
                     Log.e("sortingflag",String.valueOf(sortingflag));
                        if (fromwhere.equals("fromhome")) {
//                mainBanner list item clicked
                            if (onClick.equals("mainBanner")) {
                                mainBannerList = (List) getArguments().getSerializable("childeren");
                                if (mainBannerList != null && mainBannerList.size() > 0) {
                                    for (int i = 0; i < mainBannerList.size(); i++) {
                                        childid.add(mainBannerList.get(i).id);
                                        //childtitle.add(mainBannerList.get(i).name);
                                        childtitle.add(mainBannerList.get(i).name);
                                    }
                                }
                            }
//                category list item clicked
                            if (onClick.equals("category")) {
                                categoryList = (List) getArguments().getSerializable("childeren");
                                if (categoryList != null && categoryList.size() > 0) {
                                    for (int i = 0; i < categoryList.size(); i++) {
                                        childid.add(categoryList.get(i).getId());
                                        childtitle.add(categoryList.get(i).getName());
                                    }
                                }
                            }
//                highlighted slider list item clicked
                            else if (onClick.equals("highlightedSlider")) {
                                highlightedSliderList = (List) getArguments().getSerializable("childeren");
                                if (highlightedSliderList != null && highlightedSliderList.size() > 0) {
                                    for (int i = 0; i < highlightedSliderList.size(); i++) {
                                        childid.add(highlightedSliderList.get(i).getId());
                                        childtitle.add(highlightedSliderList.get(i).getCaption());
                                    }
                                }
                            }
//                new Arrival list item clicked
                            else if (onClick.equals("newArrival")) {
                                newArrivalList = (List) getArguments().getSerializable("childeren");
                                if (newArrivalList != null && newArrivalList.size() > 0) {
                                    for (int i = 0; i < newArrivalList.size(); i++) {
                                        childid.add(newArrivalList.get(i).id);
                                        childtitle.add(newArrivalList.get(i).name);
                                    }
                                }
                            }
//                editor choice list item clicked
                            else if (onClick.equals("editorChoice")) {
                                editorChoiceList = (List) getArguments().getSerializable("childeren");
                                if (editorChoiceList != null && editorChoiceList.size() > 0) {
                                    for (int i = 0; i < editorChoiceList.size(); i++) {
                                        childid.add(editorChoiceList.get(i).id);
                                        childtitle.add(editorChoiceList.get(i).name);
                                    }
                                }
                            }
//                deal of the month list item clicked
                            else if (onClick.equals("dealOfTheMonth")) {
                                monthDealList = (List) getArguments().getSerializable("childeren");
                                if (monthDealList != null && monthDealList.size() > 0) {
                                    for (int i = 0; i < monthDealList.size(); i++) {
                                        childid.add(monthDealList.get(i).id);
                                        childtitle.add(monthDealList.get(i).name);
                                    }
                                }
                            }
//                recommended list item clicked
                            else if (onClick.equals("recommended")) {
                                recommendedList = (List) getArguments().getSerializable("childeren");
                                if (recommendedList != null && recommendedList.size() > 0) {
                                    for (int i = 0; i < recommendedList.size(); i++) {
                                        childid.add(recommendedList.get(i).id);
                                        childtitle.add(recommendedList.get(i).name);
                                    }
                                }
                            }
//               limited stock list item clicked
                            else if (onClick.equals("limitedStock")) {
                                limitedStockList = (List) getArguments().getSerializable("childeren");
                                if (limitedStockList != null && limitedStockList.size() > 0) {
                                    for (int i = 0; i < limitedStockList.size(); i++) {
                                        childid.add(limitedStockList.get(i).id);
                                        childtitle.add(limitedStockList.get(i).name);
                                    }
                                }
                            }
//               limited stock list item clicked
                            else if (onClick.equals("stealdeal2")) {
                                limitedStockList2 = (List) getArguments().getSerializable("childeren");
                                if (limitedStockList2 != null && limitedStockList2.size() > 0) {
                                    for (int i = 0; i < limitedStockList2.size(); i++) {
                                        childid.add(limitedStockList2.get(i).id);
                                        childtitle.add(limitedStockList2.get(i).name);
                                    }
                                }
                            }
                            childlist = (List) getArguments().getSerializable("childeren");


                            initfilterListRecycler(childtitle, counter, selectedtopcategory);


                        } else if (fromwhere.equals("fromcatagory")) {
                            childcatagorylist = (List) getArguments().getSerializable("childeren");

                            if (childcatagorylist != null) {


                                for (int i = 0; i < childcatagorylist.size(); i++) {


                                    childtitle.add(childcatagorylist.get(i).name);
                                    childid.add(childcatagorylist.get(i).id);
                                    /*childid.add(childlist.get(i).id);*/


                                }
                            }

                            initfilterListRecycler(childtitle, counter, selectedtopcategory);


                        }
                        else if (fromwhere.equals("frombanner")) {

                            for (int i = 0; i < childcatagorylist.size(); i++) {


                                childtitle.add(childcatagorylist.get(i).name);
                                childid.add(childcatagorylist.get(i).id);
                                /*childid.add(childlist.get(i).id);*/


                            }


                            initfilterListRecycler(childtitle, counter, selectedtopcategory);




                        }


                        else {

                            initfilterListRecycler(childtitle, counter, selectedtopcategory);


                        }



                        currentpage = 1;
                        scrollListener.resetState();
                        PaginationListener.current_page = 1;
                        //getProductList(String.valueOf(value), 1);

                        if(fromwhere.equals("fromcatagorybrands")){
                            isGetAllProductlist =4;

                            selectmanufec =  attributvalue;
                            svalue.add(selectmanufec.toString());
                            selectedvalue.put("manufacturer",svalue);
                            filteapplybtn.setBackgroundResource(R.drawable.rectangular_border_black2);
                            filtertitle.setTextColor(Color.parseColor("#FFFFFF"));
                            filterimg.setImageResource(R.drawable.new_filter_white);
                            filtercounter.setTextColor(Color.parseColor("#ffffff"));
                            counter =1;
                            filtercounter.setVisibility(View.VISIBLE);
                            filtercounter.setText(String.valueOf("(" + counter + ")"));
                            countlist.put("manufacturer",1);
                            countlist.put("item_type",0);
                            countlist.put("size",0);
                            isfromcategoryfilterply  = true;

                            getUpgradedfilterdataforBrand(brandclickpage);

                        }
                        else if(fromwhere.equals("fromcatagorybanners")){
                            isGetAllProductlist=5;
                            // selectitemtype =  attributvalue;
                            //svalue.add(selectitemtype.toString());
                            //selectedvalue.put("item_type",svalue);

                            //getUpgradedfilterdataforBanners(bannerclickpage);
                            getUpgradedProductList(String.valueOf(value), 1);
                        }

                        else if(fromwhere.equals("fromsubcategories")){
                            isGetAllProductlist=6;
                            selectitemtype =  attributvalue;

                            svalue.add(selectitemtype.toString());
                            selectedvalue.put("item_type",svalue);
                            filteapplybtn.setBackgroundResource(R.drawable.rectangular_border_black2);
                            filtertitle.setTextColor(Color.parseColor("#FFFFFF"));
                            filterimg.setImageResource(R.drawable.new_filter_white);
                            filtercounter.setTextColor(Color.parseColor("#ffffff"));
                            counter =1;
                            filtercounter.setVisibility(View.VISIBLE);
                            filtercounter.setText(String.valueOf("(" + counter + ")"));

                            countlist.put("manufacturer",0);
                            countlist.put("item_type",1);
                            countlist.put("size",0);

                            isfromcategoryfilterply  = true;

                            getUpgradedfilterdataforSubcategories(bannerclickpage);

                        }
                       else if(fromwhere.equals("fromcatagorysize")){
                            isGetAllProductlist =7;

                            selectesize =  attributvalue;
                            svalue.add(selectesize.toString());

                            selectedvalue.put("size",svalue);
                            filteapplybtn.setBackgroundResource(R.drawable.rectangular_border_black2);
                            filtertitle.setTextColor(Color.parseColor("#FFFFFF"));
                            filterimg.setImageResource(R.drawable.new_filter_white);
                            filtercounter.setTextColor(Color.parseColor("#ffffff"));
                            counter = 1;
                            filtercounter.setVisibility(View.VISIBLE);
                            filtercounter.setText(String.valueOf("(" + counter + ")"));
                            countlist.put("manufacturer",0);
                            countlist.put("item_type",0);
                            countlist.put("size",1);
                            isfromcategoryfilterply  = true;

                            getUpgradedfilterdataforSize(brandclickpage);

                        }
                        else
                        {
                            getUpgradedProductList(String.valueOf(value), 1);
                        }

                        sortbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


//                    initSortDialogue();


                            }
                        });
                        if (getActivity()!=null) {
                            iniatializesortDialogue();
                        }
                        //Toast.makeText(MainActivity.this, "Inside version code", Toast.LENGTH_SHORT).show();

                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<GetCategoryCode> call, Throwable t) {

                Log.e(TAG, t.toString());
            }
        });
    }

    @Nullable
    String getUniqueID2() {
        UUID wideVineUuid = new UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L);
        try {
            MediaDrm wvDrm = new MediaDrm(wideVineUuid);
            byte[] wideVineId = wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID);


            String encodedString = Base64.encodeToString(wideVineId, Base64.DEFAULT);
            Log.i("Uniqueid", "Uniqueid" + encodedString);
            return encodedString;
        } catch (Exception e) {
            // Inspect exception
            return null;
        }

    }

    private void showProgressDialog() {
        if (getActivity() != null) {
            progressDialog = ProgressDialog.show(getActivity(), "Null", "Null", false, false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.progress_bar);
            progressDialog.setCanceledOnTouchOutside(false);
        }
    }


    private void hideProgressDialog() {
        try {
            progressDialog.dismiss();
        } catch (Exception e) {
        }
    }
    public void getWishListFlag() {

        //parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();

        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        String myKey = getUniqueID2().toString().replaceAll("[^A-Za-z0-9]", "");

        Call<String> call;
        if (MySharedPreferenceClass.getMyUserId(getApplicationContext()).equals(" ")) {
            call = apiService.getWishListFlag("https://upgrade.eoutlet.com/rest/en/V1/api/checkwishlist/" + "1" + myKey.toUpperCase() + "2" + "/0/" + catagoryList.get(lastclckedposition).id);
        } else {
            call = apiService.getWishListFlag("https://upgrade.eoutlet.com/rest/en/V1/api/checkwishlist/" + "0/" + MySharedPreferenceClass.getMyUserId(getContext()) + "/" + catagoryList.get(lastclckedposition).id);

        }

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null) {

                    if (response.body().toString().equals("0")) {

                      catagoryList.get(lastclckedposition).iswishlist = false;


                      mAdapter.notifyItemChanged(lastclckedposition);

                    } else if((response.body().toString().equals("1"))){
                        catagoryList.get(lastclckedposition).iswishlist = true;

                        mAdapter.notifyItemChanged(lastclckedposition);

                    }




                  //  Log.d("Wish List Response is ------->>>>", response.body().toString());


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
                parentDialogListener.hideProgressDialog();
            }
        });


    }
    void refreshRecyclerView(RecyclerView recyclerView){
        RecyclerView.Adapter adapterRef=recyclerView.getAdapter();
        recyclerView.setAdapter(null);
        recyclerView.setAdapter(adapterRef);
    }

    public void moengageCategoryViewEvent(String categoryId,String categoryname)
    {


        Properties properties = new Properties();


        properties.addAttribute("category Id", categoryId);
        properties.addAttribute("category name",categoryname);




        MoEHelper.getInstance(getApplicationContext()).trackEvent("Category View", properties);




    }

}
