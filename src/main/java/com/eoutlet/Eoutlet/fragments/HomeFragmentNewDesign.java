package com.eoutlet.Eoutlet.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaDrm;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.activities.SplashActivity;
import com.eoutlet.Eoutlet.adpters.CurrencyListAdapter;
import com.eoutlet.Eoutlet.adpters.DealsOfTheMonthAdapter;
import com.eoutlet.Eoutlet.adpters.EditorChoiceAdapter;
import com.eoutlet.Eoutlet.adpters.HomeCategorySelectionAdapter;
import com.eoutlet.Eoutlet.adpters.LimitedStockAdapter;
import com.eoutlet.Eoutlet.adpters.LimitedStockAdapter2;
import com.eoutlet.Eoutlet.adpters.NewArrivalViewPagerAdapter;
import com.eoutlet.Eoutlet.adpters.RecommendedViewPagerAdapter;
import com.eoutlet.Eoutlet.adpters.StealDealViewPagerAdapter;
import com.eoutlet.Eoutlet.adpters.TopTabsCategoryAdapter;
import com.eoutlet.Eoutlet.adpters.TopTabsHomeAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.UpdateBedgeCount;
import com.eoutlet.Eoutlet.intrface.ViewlistenerCurrency;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.Child;
import com.eoutlet.Eoutlet.pojo.CurrencysetResponse;
import com.eoutlet.Eoutlet.pojo.ExchangeRate;
import com.eoutlet.Eoutlet.pojo.GetCategoryCode;
import com.eoutlet.Eoutlet.pojo.GetCountryCode;
import com.eoutlet.Eoutlet.pojo.GetCurrencyDetail;
import com.eoutlet.Eoutlet.pojo.GetPopUpData;

import com.eoutlet.Eoutlet.pojo.UpgradeWishListResponse;
import com.eoutlet.Eoutlet.pojo.UpgradedCartItems;
import com.eoutlet.Eoutlet.pojo.countryCodeDetail;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.EditorChoice;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.FeatureBrand;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.GetNewHomeScreenData;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.LimitedStock;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.MainBanner;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.MonthDeal;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.NewArrival;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.Recommended;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.ShopByCategory;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.StealDeal;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.StealDealOffer;
import com.eoutlet.Eoutlet.utility.Constants;
import com.eoutlet.Eoutlet.utility.CustomDialogClassforNotification;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.moe.pushlibrary.MoEHelper;
import com.moengage.core.Properties;
import com.moengage.inapp.MoEInAppHelper;
import com.moengage.widgets.NudgeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.eoutlet.Eoutlet.activities.MainActivity.navigationView;
import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeFragmentNewDesign extends Fragment {
    private HomeCategorySelectionAdapter categoryAdapter;

    private ViewPagerAdapter viewPagerAdapter;
    private RecyclerView topcategoryrecycler;
    int catselctionposition = 0;
    private TopTabsHomeAdapter madapter;
    private String menId, womenId, babiesId, childerenId;
    ArrayList<String> topcatname = new ArrayList<>();

    private FirebaseAnalytics mFirebaseAnalytics;
    private NewArrivalViewPagerAdapter newArrivalViewPagerAdapter;
    private StealDealViewPagerAdapter stealDealViewPagerAdapter;
    private EditorChoiceAdapter eAdapter;
    private DealsOfTheMonthAdapter dAdapter;
    List<Child> childlist = new ArrayList<>();
    List<String> availiblecuurrency;
    private CurrencyListAdapter currencyadpter;
    RecommendedViewPagerAdapter recommendedViewPagerAdapter;
    private LimitedStockAdapter lAdapter;
    private LimitedStockAdapter2 lAdapter2;
    private List<ExchangeRate> exchangecountrylist = new ArrayList<>();
    private List<countryCodeDetail> currencycountrynames = new ArrayList();
    private ViewPager mainBannerViewPager, newArrivalViewPager, recommendedViewPager,stealdealviewpager;
    private LinearLayout mainBannerSliderDots, newArrivalSliderDots, recommendedSliderDots;
    private int mainBannerDotsCount, newArrivalDotsCount, recommendDotsCount;
    private ImageView[] mainBannerDots, newArrivalDots, recommendDots;
    private RecyclerView shopByCategoryRecyclerView, editorChoiceRecyclerView, domRecyclerView, limitedStockRecyclerView,limitedStockRecyclerView2;
    LinearLayoutManager dealOfTheMonthLayoutManager, limitedStockLayoutManager,limitedStockLayoutManager2;
    CardView searchCardView, wishListCardView, notificationCardView;
    GridLayoutManager categoryGridLayoutManager, EditorChoiceGridLayoutManager;
    private List<MainBanner> mainBannerList;
    private List<ShopByCategory> shopByCategoryList;
    private List<NewArrival> newArrivalList;
    private List<Recommended> editorChoiceList;
    private List<MonthDeal> monthDealList;
    private List<FeatureBrand> recommendedList;
    private List<LimitedStock> limitedStockList;
    private List<StealDeal> stealdeal;
    private List<StealDealOffer> stealdealoffer;

    String Locale;
    private RecyclerView currencyrecycler;
    private Context mContext;
    private View view;
    private ProgressDialog progressDialog1;
    @SuppressLint("StaticFieldLeak")
    public static TextView wishListBadgeCount;
    @SuppressLint("StaticFieldLeak")
    private static HomeFragmentNewDesign instance = null;
    String userName;
    public static TextView greetingMessage;
    private int countryselctposition;
    int templangselectionflag;
    private ImageView flagIamge;
    private TextView selectedcountry;
    String coupencatId, coupencatName;
    private boolean dataloaded = false;
    private int totalcount;
    private UpdateBedgeCount updatefix;


    public void passData(Context context) {
        mContext = context;
    }

    public static HomeFragmentNewDesign getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        instance = this;
        // Inflate the layout for this fragment
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_new_home_design_arabic, container, false);
        } else {
            Locale = "en";
            view = inflater.inflate(R.layout.fragment_new_home_design, container, false);
        }
        initViews();
        return view;
    }

    public void initViews() {

        topcategoryrecycler = view.findViewById(R.id.toptabscategory);
        NudgeView nv = (NudgeView) view.findViewById(R.id.nudge);
        // initialize
        nv.initialiseNudgeView(getActivity());

        updatefix = (MainActivity) getActivity();
        mainBannerViewPager = (ViewPager) view.findViewById(R.id.main_banner_view_pager);
        newArrivalViewPager = (ViewPager) view.findViewById(R.id.new_arrival_view_pager);
        recommendedViewPager = (ViewPager) view.findViewById(R.id.recommended_view_pager);
        stealdealviewpager = (ViewPager)view.findViewById(R.id.steal_deals_view_pager);

        mainBannerSliderDots = (LinearLayout) view.findViewById(R.id.main_banner_slider_dots);
        newArrivalSliderDots = (LinearLayout) view.findViewById(R.id.new_arrival_slider_dots);
        recommendedSliderDots = (LinearLayout) view.findViewById(R.id.recommended_slider_dots);


        shopByCategoryRecyclerView = view.findViewById(R.id.shop_by_category_recycler_view);
        editorChoiceRecyclerView = view.findViewById(R.id.editor_choice_recycler_view);
        domRecyclerView = view.findViewById(R.id.dom_recycler_view);
        limitedStockRecyclerView = view.findViewById(R.id.limited_stock_recycler_view);
        limitedStockRecyclerView2 = view.findViewById(R.id.steal_deals_recycler_view);
        searchCardView = view.findViewById(R.id.searchCardView);
        wishListCardView = view.findViewById(R.id.wishListCardView);
        notificationCardView = view.findViewById(R.id.notificationCardView);
        wishListBadgeCount = view.findViewById(R.id.wishListBadgeCount);
        greetingMessage = view.findViewById(R.id.greetingMessage);
        topcatname.add("Women");
        topcatname.add("Men");
        topcatname.add("Kids");
        topcatname.add("Babies");
        initTopTabsRecycler(topcatname);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        wishListCardView.setOnClickListener(v -> {
            Fragment fragment = new UpgradeWishListFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.container2, fragment);
            ft.commit();
        });
        searchCardView.setOnClickListener(v -> {
            Fragment prFrag = new SearchResultFragment();
            getFragmentManager()
                    .beginTransaction().addToBackStack(null)
                    .replace(R.id.container2, prFrag)
                    .commit();
        });
        notificationCardView.setOnClickListener(v -> {
            Fragment fragment = new NotificationInboxFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.container2, fragment);
            ft.commit();
        });

        setMessageOnHeader();

//        category adapter set
        categoryGridLayoutManager = new GridLayoutManager(getApplicationContext(), 4, LinearLayoutManager.VERTICAL, false) {
            @Override
            protected boolean isLayoutRTL() {
                if (Locale == "ar") {
                    return true;
                } else {
                    return false;
                }
            }
        };
        shopByCategoryRecyclerView.setLayoutManager(categoryGridLayoutManager);
        shopByCategoryRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        editor choice adapter set
        EditorChoiceGridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false) {
            @Override
            protected boolean isLayoutRTL() {
                if (Locale == "ar") {
                    return true;
                } else {
                    return false;
                }
            }
        };
        editorChoiceRecyclerView.setLayoutManager(EditorChoiceGridLayoutManager);
        editorChoiceRecyclerView.setItemAnimator(new DefaultItemAnimator());

//       deal of the month layout manager
        dealOfTheMonthLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        domRecyclerView.setLayoutManager(dealOfTheMonthLayoutManager);
        domRecyclerView.setItemAnimator(new DefaultItemAnimator());

//       limited stock layout manager
        limitedStockLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            protected boolean isLayoutRTL() {
                if (Locale == "ar") {
                    return true;
                } else {
                    return false;
                }
            }
        };
        limitedStockRecyclerView.setLayoutManager(limitedStockLayoutManager);
        limitedStockRecyclerView.setItemAnimator(new DefaultItemAnimator());

        limitedStockLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            protected boolean isLayoutRTL() {
                if (Locale == "ar") {
                    return true;
                } else {
                    return false;
                }
            }
        };
        limitedStockRecyclerView2.setLayoutManager(limitedStockLayoutManager2);
        limitedStockRecyclerView2.setItemAnimator(new DefaultItemAnimator());
        getHomeScreenData();
        if (MySharedPreferenceClass.getisAppOpenFirstTime(getApplicationContext()) && SplashActivity.ispopupshow) {
            getfirsttimepopupData();
            SplashActivity.ispopupshow = false;
        }

    }


    private void setMessageOnHeader() {
        if (MySharedPreferenceClass.getMyUserName(getContext()) != null) {
            userName = MySharedPreferenceClass.getMyUserName(getContext());
        }
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 6 && timeOfDay < 12) {
            if (Locale == "ar") {
                greetingMessage.setText(userName != null ? "صباح الخير,  " + userName + "!" : "صباح الخير!");
            } else {
                greetingMessage.setText(userName != null ? "Good Morning, " + userName + "!" : "Good Morning!");
            }
        } else if (timeOfDay >= 12 && timeOfDay < 17) {
            if (Locale == "ar") {
                greetingMessage.setText(userName != null ? "مساء الخير,  " + userName + "!" : "مساء الخير!");
            } else {
                greetingMessage.setText(userName != null ? "Good Afternoon, " + userName + "!" : "Good Afternoon!");
            }
        } else {
            if (Locale == "ar") {
                greetingMessage.setText(userName != null ? "مساء النور,  " + userName + "!" : "مساء النور!");
            } else {
                greetingMessage.setText(userName != null ? "Good Evening, " + userName + "!" : "Good Evening!");
            }
        }
    }
    public void firebase_view_banner(String promotionname,String promotionId) {
        Bundle bundle = new Bundle();

        bundle.putString(FirebaseAnalytics.Param.PROMOTION_NAME, promotionname);
        bundle.putString(FirebaseAnalytics.Param.PROMOTION_ID, promotionId);
        //bundle.putString(FirebaseAnalytics.Param.ITEM_LOCATION_ID, Locale.getDefault().getCountry());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, bundle);


    }

    private void getHomeScreenData() {
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<GetNewHomeScreenData> call;
        call = Locale == "ar" ? apiService.getNewHomeScreenData(Constants.upgradedmediaSourceURL +
                Constants.UPGRADED_BASE_LINK_ARABIC + /*"getnexthomepage/"+MySharedPreferenceClass.getCatId(getApplicationContext())*/"eoutlethome")
                : apiService.getNewHomeScreenData(Constants.upgradedmediaSourceURL +
                Constants.UPGRADED_BASE_LINK_ENGLISH + /*"getnexthomepage/"+MySharedPreferenceClass.getCatId(getApplicationContext())*/"eoutlethome");
        call.enqueue(new Callback<GetNewHomeScreenData>() {

            @Override
            public void onResponse(Call<GetNewHomeScreenData> call, Response<GetNewHomeScreenData> response) {
                if (response.body() != null) {
                    if (response.body().response.equalsIgnoreCase("success")) {
                        dataloaded = true;
                        if (!MySharedPreferenceClass.getisAppOpenFirstTime(getApplicationContext())) {
                            initLanguageDialog();
                        }

                        mainBannerList = response.body().data.mainBanner;
                        shopByCategoryList = response.body().data.shopByCategory;
                        newArrivalList = response.body().data.newArrivals;
                        editorChoiceList = response.body().data.recommended;
                        monthDealList = response.body().data.monthDeals;
                        recommendedList = response.body().data.featured_brand;
                        limitedStockList = response.body().data.limitedStock;
                        stealdealoffer  =response.body().data.stealdealoffer;
                        stealdeal = response.body().data.stealdeal;


                        //   main banner adapter


                        viewPagerAdapter = new ViewPagerAdapter(getContext(), mainBannerList);

                        mainBannerViewPager.setAdapter(viewPagerAdapter);
                        viewPagerAdapter.notifyDataSetChanged();
                        for(int i =0;i< mainBannerDotsCount; i++) {
                            mainBannerSliderDots.removeView(mainBannerDots[i]);
                        }
                        mainBannerDotsCount =  viewPagerAdapter.getCount();

                        if (mainBannerDotsCount > 0) {
                            mainBannerDots = new ImageView[mainBannerDotsCount];
                            for (int i = 0; i < mainBannerDotsCount; i++) {
                              
                                mainBannerDots[i] = new ImageView(getApplicationContext());

                                mainBannerDots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                params.setMargins(8, 0, 8, 0);
                                mainBannerSliderDots.addView(mainBannerDots[i], params);
                            }
                            mainBannerDots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                            mainBannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                //Log.e("pageScrolled",String.valueOf(position));
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    Log.e("pageSelected",String.valueOf(position));
                                    firebase_view_banner(mainBannerList.get(position).name,mainBannerList.get(position).id);
                                    for (int i = 0; i < mainBannerDotsCount; i++) {
                                        mainBannerDots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                    }
                                    mainBannerDots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {
                                }
                            });
                        }

//                         category adapter
                        categoryAdapter = new HomeCategorySelectionAdapter(getContext(), shopByCategoryList, new ViewListener() {
                            @Override
                            public void onClick(int position, View view) {

                                Log.e("onClick", shopByCategoryList.get(position).name);

                                if(shopByCategoryList.get(position).caption.equalsIgnoreCase("product_list")) {
                                    Fragment productList = new ProductList();
                                    FragmentTransaction mFragmentTransaction = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                                    mFragmentTransaction.replace(R.id.container2, productList).addToBackStack(null).commit();
                                    Bundle databund = new Bundle();
                                    databund.putString("previouspage","FromHomeScreen");
                                    //put your ArrayList data in bundle
                                    databund.putInt("productId", Integer.parseInt(shopByCategoryList.get(position).id));
                                    databund.putString("name", shopByCategoryList.get(position).name);
                                    databund.putSerializable("childeren", (Serializable) mainBannerList );
                                    databund.putString("fromwhere", "fromhome");
                                    databund.putString("previouspage","HomeScreen");
                                    databund.putString("onClick", "mainBanner");
                                    productList.setArguments(databund);

                                }
                                else if(shopByCategoryList.get(position).caption.equalsIgnoreCase("product_link")){
                                    Fragment productList = new ProductDetail();

                                    FragmentTransaction mFragmentTransaction = ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                                    mFragmentTransaction.replace(R.id.container2, productList).addToBackStack(null).commit();
                                    Bundle databund = new Bundle();
                                    //put your ArrayList data in bundle
                                    databund.putString("previouspage","FromHomeScreen");
                                    databund.putString("pid",shopByCategoryList.get(position).id);
                                    databund.putString("name", shopByCategoryList.get(position).name);
                                    databund.putSerializable("childeren", (Serializable) mainBannerList);
                                    databund.putString("fromwhere", "fromhome");
                                    databund.putString("previouspage","FromHomeScreen");
                                    databund.putString("onClick", "mainBanner");
                                    productList.setArguments(databund);
                                    Log.e("pid is--->>>",mainBannerList.get(position).id);
                                }
                                else if(shopByCategoryList.get(position).caption.equalsIgnoreCase("category")){
                                    MySharedPreferenceClass.setCatId(getApplicationContext(), shopByCategoryList.get(position).id);
                                    MySharedPreferenceClass.setsubCatId(getApplicationContext()," ");
                                    navigationView.setSelectedItemId(R.id.catagory);}

                            }






                        });
                        shopByCategoryRecyclerView.setAdapter(categoryAdapter);
                        categoryAdapter.notifyDataSetChanged();

                        //   NewArrivalViewPagerAdapter starts
                        newArrivalViewPagerAdapter = new NewArrivalViewPagerAdapter(getContext(), newArrivalList);
                        newArrivalViewPager.setAdapter(newArrivalViewPagerAdapter);
                        newArrivalDotsCount = newArrivalViewPagerAdapter.getCount();
                        if (newArrivalDotsCount > 1) {
                            newArrivalDots = new ImageView[newArrivalDotsCount];
                            for (int i = 0; i < newArrivalDotsCount; i++) {
                                newArrivalDots[i] = new ImageView(getContext());
                                newArrivalDots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                params.setMargins(8, 0, 8, 0);
                                newArrivalSliderDots.addView(newArrivalDots[i], params);
                            }
                            newArrivalDots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                            newArrivalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    for (int i = 0; i < newArrivalDotsCount; i++) {
                                        newArrivalDots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                    }
                                    newArrivalDots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {
                                }
                            });
                        }


                        //   Steal Deal starts

                      stealDealViewPagerAdapter = new StealDealViewPagerAdapter(getContext(), stealdeal);
                        stealdealviewpager.setAdapter(  stealDealViewPagerAdapter);
                       /* newArrivalDotsCount =   stealDealViewPagerAdapter.getCount();
                        if (newArrivalDotsCount > 1) {
                            newArrivalDots = new ImageView[newArrivalDotsCount];
                            for (int i = 0; i < newArrivalDotsCount; i++) {
                                newArrivalDots[i] = new ImageView(getContext());
                                newArrivalDots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                params.setMargins(8, 0, 8, 0);
                                newArrivalSliderDots.addView(newArrivalDots[i], params);
                            }
                            newArrivalDots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                            newArrivalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    for (int i = 0; i < newArrivalDotsCount; i++) {
                                        newArrivalDots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                    }
                                    newArrivalDots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {
                                }
                            });
                        }*/
//                      editor choice adapter
                        eAdapter = new EditorChoiceAdapter(getContext(), editorChoiceList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false) {
                            @Override
                            protected boolean isLayoutRTL() {
                                if (Locale == "ar") {
                                    return true;
                                } else {
                                    return false;
                                }

                            }
                        };
                        editorChoiceRecyclerView.setLayoutManager(gridLayoutManager);
                        editorChoiceRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        editorChoiceRecyclerView.setAdapter(eAdapter);
                        eAdapter.notifyDataSetChanged();

//                       deal of the month  adapter
                        dAdapter = new DealsOfTheMonthAdapter(getContext(), monthDealList);
                        domRecyclerView.setAdapter(dAdapter);
                        dAdapter.notifyDataSetChanged();

//                       recommendedViewPager starts
                        recommendedViewPagerAdapter = new RecommendedViewPagerAdapter(getContext(), recommendedList);
                        recommendedViewPager.setAdapter(recommendedViewPagerAdapter);
                        recommendDotsCount = recommendedViewPagerAdapter.getCount();
                        if (recommendDotsCount > 1) {
                            recommendDots = new ImageView[recommendDotsCount];
                            for (int i = 0; i < recommendDotsCount; i++) {
                                recommendDots[i] = new ImageView(getContext());
                                recommendDots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                params.setMargins(8, 0, 8, 0);
                                recommendedSliderDots.addView(recommendDots[i], params);
                            }
                            recommendDots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                            recommendedViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    for (int i = 0; i < recommendDotsCount; i++) {
                                        recommendDots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                    }
                                    recommendDots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {
                                }
                            });
                        }

//                        limitedStock adapter start
                        lAdapter = new LimitedStockAdapter(getContext(), limitedStockList);
                        limitedStockRecyclerView.setAdapter(lAdapter);
                        lAdapter.notifyDataSetChanged();


                        lAdapter2 = new LimitedStockAdapter2(getContext(), stealdealoffer);
                        limitedStockRecyclerView2.setAdapter(lAdapter2);
                        lAdapter2.notifyDataSetChanged();

                    } else {
                    }
                } else {
                }

                if (MySharedPreferenceClass.getSharedProductId(getContext()) != null) {
                    Log.e("sharedProductId", MySharedPreferenceClass.getSharedProductId(getContext()));
                    Fragment productDetail = new ProductDetail();
                    FragmentTransaction mFragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.container2, productDetail).addToBackStack(null).commit();
                    Bundle databund = new Bundle();
                    //put your ArrayList data in bundle
                    databund.putString("fromwhere", "fromhome");
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putString("pid", MySharedPreferenceClass.getSharedProductId(getContext()));
                    productDetail.setArguments(databund);
                    MySharedPreferenceClass.setSharedProductId(getContext(), null);
                } else if (MySharedPreferenceClass.getDeeplinkingNotification(getContext()) != null) {
                    if (MySharedPreferenceClass.getDeeplinkingNotification(getContext()).equalsIgnoreCase("true")) {
                        if (MySharedPreferenceClass.getDeeplinkingPage(getContext()) != null) {
                            if (MySharedPreferenceClass.getDeeplinkingPage(getContext()).equalsIgnoreCase("home")
//                                    || MySharedPreferenceClass.getDeeplinkingPage(getContext()).equalsIgnoreCase("category")
//                                    || MySharedPreferenceClass.getDeeplinkingPage(getContext()).equalsIgnoreCase("brand")
                            ) {

                                if (MySharedPreferenceClass.getDeeplinkingId(getContext()) != null
                                        || MySharedPreferenceClass.getDeeplinkingName(getContext()) != null) {


                                    Fragment prFrag = new ProductList();

                                    Bundle databund = new Bundle();
                                    databund.putString("fromwhere", "fromcatagory");
                                    databund.putString("previouspage","FromHomeScreen");
                                    Log.e("Id->>>>>", MySharedPreferenceClass.getDeeplinkingId(getContext()));
                                    Log.e("Name->>>>>", MySharedPreferenceClass.getDeeplinkingName(getContext()));
                                    databund.putInt("productId", Integer.parseInt(MySharedPreferenceClass.getDeeplinkingId(getContext())));
                                    databund.putString("name", MySharedPreferenceClass.getDeeplinkingName(getContext()));
//                        databund.putSerializable("childeren", (Serializable) mainhorizantallist.get(position).data);
                                    prFrag.setArguments(databund);
                                    getFragmentManager()
                                            .beginTransaction().addToBackStack(null)
                                            .add(R.id.container2, prFrag)
                                            .commit();

                                    /*MySharedPreferenceClass.setDeeplinkingNotification(getContext(), null);
                                    MySharedPreferenceClass.setDeeplinkingId(getContext(), null);
                                    MySharedPreferenceClass.setDeeplinkingName(getContext(), null);
                                    MySharedPreferenceClass.setDeeplinkingPage(getContext(), null);*/
                                }

                            } else if (MySharedPreferenceClass.getDeeplinkingPage(getContext()).equalsIgnoreCase("detail")) {
                                if (MySharedPreferenceClass.getDeeplinkingId(getContext()) != null
                                        || MySharedPreferenceClass.getDeeplinkingName(getContext()) != null) {
                                    //  Log.e("pid", MySharedPreferenceClass.getDeeplinkingId(getContext()));
                                    Fragment productDetail = new ProductDetail();
                                    FragmentTransaction mFragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                                    mFragmentTransaction.add(R.id.container2, productDetail).addToBackStack(null).commit();
                                    Bundle databund = new Bundle();
                                    //put your ArrayList data in bundle
                                    databund.putString("fromwhere", "fromhome");
                                    databund.putString("previouspage","FromHomeScreen");
                                    databund.putString("pid", MySharedPreferenceClass.getDeeplinkingId(getContext()));
                                    productDetail.setArguments(databund);

                                    Log.e("pid", MySharedPreferenceClass.getDeeplinkingId(getContext()));
                                    /*MySharedPreferenceClass.setDeeplinkingNotification(getContext(), null);
                                    MySharedPreferenceClass.setDeeplinkingId(getContext(), null);
                                    MySharedPreferenceClass.setDeeplinkingName(getContext(), null);
                                    MySharedPreferenceClass.setDeeplinkingPage(getContext(), null);*/
                                }

                            }
                        }

                    } else {
                        Log.e("getNotification", "false");
                    }
                } else {


                    // getUpgradedLatestversionCode();

                    Log.e("sharedProductId", "null");


                }


            }

            @Override
            public void onFailure(Call<GetNewHomeScreenData> call, Throwable t) {
                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });

        getWishListItemCount();
        if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {
            getUpgradeCartData();
        } else {
            getUpgradedCartDataStatusforGuestUser();
        }
        getUpgradedLatestversionCode();
    }

    private void getUpgradedCartDataStatusforGuestUser() {
        totalcount = 0;
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
                if (response.body().data.items.size() > 0) {
                    for (int i = 0; i < response.body().data.items.size(); i++) {
                        totalcount = totalcount + (int) (response.body().data.items.get(i).qty);
                    }
                    MySharedPreferenceClass.setBedgeCount(getContext(), totalcount);
                    updatefix.updateBedgeCount();
                    MainActivity.notificationBadge.setVisibility(View.VISIBLE);
                } else {
                    MainActivity.bedgetext.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UpgradedCartItems> call, Throwable t) {
                Log.e(TAG, t.toString());
                MainActivity.notificationBadge.setVisibility(View.GONE);
                MainActivity.bedgetext.setVisibility(View.GONE);
                MainActivity.toolbar_bedgetext.setVisibility(View.GONE);
            }
        });
    }

    private void getUpgradeCartData() {
        totalcount = 0;
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        Call<UpgradedCartItems> call = apiService.getUpgradedCartDetail(Constants.UPGRADED_HOST_LINK + "rest/en/V1/api/customercart/" + MySharedPreferenceClass.getMyUserId(getContext()));
        call.enqueue(new Callback<UpgradedCartItems>() {
            @Override
            public void onResponse(Call<UpgradedCartItems> call, Response<UpgradedCartItems> response) {
                hideProgressDialog();
                int quant;
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().data.items.size() > 0) {
                        for (int i = 0; i < response.body().data.items.size(); i++) {
                            totalcount = totalcount + (int) (response.body().data.items.get(i).qty);
                        }

                        MySharedPreferenceClass.setBedgeCount(getApplicationContext(), totalcount);
                        updatefix.updateBedgeCount();
                        MainActivity.notificationBadge.setVisibility(View.VISIBLE);

                    }
                } else {
                    MainActivity.bedgetext.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UpgradedCartItems> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void initLanguageDialog() {

        moengageNewInstallEvent();
        MySharedPreferenceClass.setisAppOpenFirstTime(getApplicationContext(), true);
        if (Locale.equals("en")) {
            templangselectionflag = 1;
        } else {
            templangselectionflag = 2;
        }
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
        View sheetView;
        if (Locale.equals("ar")) {
            sheetView = this.getLayoutInflater().inflate(R.layout.language_selction_layout_arabic, null);
        } else {
            sheetView = this.getLayoutInflater().inflate(R.layout.language_selction_layout, null);
        }
        mBottomSheetDialog.setContentView(sheetView);

        ImageView english = sheetView.findViewById(R.id.english);
        ImageView arabic = sheetView.findViewById(R.id.arabic);
        selectedcountry = sheetView.findViewById(R.id.selectecountry);
        flagIamge = sheetView.findViewById(R.id.flagimage);

        TextView changecountry = sheetView.findViewById(R.id.changecountry);

        TextView continuetohome = sheetView.findViewById(R.id.continuetohom);

        changecountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iniatializechangeCountryDialogue();
                // mBottomSheetDialog.cancel();


            }
        });
        continuetohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (templangselectionflag == 1) {
                    java.util.Locale.setDefault(java.util.Locale.forLanguageTag("en"));
                    Toast.makeText(getApplicationContext(), "Language set to English", Toast.LENGTH_SHORT).show();
                    MySharedPreferenceClass.setChoosenLanguage(getActivity(), "en");

                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(in);
                    Activity activity = getActivity();
                    if (isAdded() && activity != null) {
                        getActivity().finish();

                    }
                } else {
                    java.util.Locale.setDefault(java.util.Locale.forLanguageTag("ar"));
                    Toast.makeText(getApplicationContext(), "ضبط اللغة على اللغة الإنجليزية", Toast.LENGTH_SHORT).show();
                    MySharedPreferenceClass.setChoosenLanguage(getActivity(), "ar");


                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(in);
                    Activity activity = getActivity();
                    if (isAdded() && activity != null) {
                        getActivity().finish();

                    }
                }
                //mBottomSheetDialog.cancel();

            }
        });


        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {

            english.setImageDrawable(getContext().getDrawable(R.drawable.selected));
            arabic.setImageDrawable(getContext().getDrawable(R.drawable.unselected));
        } else {
            arabic.setImageDrawable(getContext().getDrawable(R.drawable.selected));
            english.setImageDrawable(getContext().getDrawable(R.drawable.unselected));
        }
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                english.setImageDrawable(getContext().getDrawable(R.drawable.selected));
                arabic.setImageDrawable(getContext().getDrawable(R.drawable.unselected));
                templangselectionflag = 1;


            }
        });

        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arabic.setImageDrawable(getContext().getDrawable(R.drawable.selected));
                english.setImageDrawable(getContext().getDrawable(R.drawable.unselected));
                templangselectionflag = 2;

            }
        });
        getCurrencyfromLanguageDialog();
        mBottomSheetDialog.show();


        mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {


                getfirsttimepopupData();


            }
        });
    }

    public void getfirsttimepopupData() {


        //showProgressDialog();

        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
            Locale = "ar";
        } else {
            Locale = "en";
        }
        String userID = "0";

        if (!MySharedPreferenceClass.getMyUserId(getApplicationContext()).equals(" ")) {

            userID = MySharedPreferenceClass.getMyUserId(getApplicationContext());
        } else {


        }


        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<GetPopUpData> call = Locale == "ar" ? apiService.getPopupData(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "getpopupdata/" + userID) : apiService.getPopupData(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "getpopupdata/" + userID);
        call.enqueue(new Callback<GetPopUpData>() {
            @Override
            public void onResponse(Call<GetPopUpData> call, Response<GetPopUpData> response) {
                if (response.body() != null) {
                    hideProgressDialog();
                    if (response.body().response.equals("success")) {
                        if (response.body().data.size() > 0) {


                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {


                                    showfirstTimePopup(response.body().data.get(0).image);


                                    if (!areNotificationsEnabled()) {

                                        openNotificationShowDialog();


                                    }

                                }
                            }, 1000);
                        }

                        coupencatId = response.body().data.get(0).id;
                        coupencatName = response.body().data.get(0).name;


                    } else {
                        Log.e("get wishList", "failed");
                    }
                }
            }

            @Override
            public void onFailure(Call<GetPopUpData> call, Throwable t) {
                hideProgressDialog();
                Log.e("fail", t.getMessage());
            }
        });


    }

    public void showfirstTimePopup(String imgurl) {
        Dialog settingsDialog = new Dialog(getActivity(),
                R.style.CustomDialogAnimationforhomepopup);


        settingsDialog.getWindow().setFormat(PixelFormat.TRANSPARENT);
        settingsDialog.setCancelable(true);


        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.image_layout
                , null));
        ImageView iv = settingsDialog.findViewById(R.id.popupimage);
        ImageView closeImage = settingsDialog.findViewById(R.id.crossimage);
        FrameLayout imagearea = settingsDialog.findViewById(R.id.imagearaea);
        RelativeLayout mainlayout = settingsDialog.findViewById(R.id.mainlayout);
        Glide.with(iv)
                .load(imgurl).apply(RequestOptions.bitmapTransform(new RoundedCorners(80))).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(iv);


        settingsDialog.show();
        mainlayout.setVisibility(View.VISIBLE);

        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog.cancel();
            }
        });


        imagearea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (coupencatId != null && coupencatId != "") {
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();
                    databund.putString("fromwhere", "fromcatagory");
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putInt("productId", Integer.parseInt(coupencatId));
                    databund.putString("name", coupencatName);
//                        databund.putSerializable("childeren", (Serializable) mainhorizantallist.get(position).data);
                    prFrag.setArguments(databund);
                    getFragmentManager()
                            .beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();
                    settingsDialog.cancel();
                } else {

                    settingsDialog.cancel();
                }


            }
        });


    }

    public void getWishListItemCount() {
        String mykey = getUniqueID2().replaceAll("[^A-Za-z0-9]", "");
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
            Locale = "ar";
        } else {
            Locale = "en";
        }
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Call<UpgradeWishListResponse> call;
        if (!MySharedPreferenceClass.getMyUserId(getContext()).equals(" ")) {
            call = Locale == "ar" ? apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "mywishlist/" + MySharedPreferenceClass.getMyUserId(getContext())) : apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "mywishlist/" + MySharedPreferenceClass.getMyUserId(getContext()));
        } else {
            call = Locale == "ar" ? apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "getguestwishlist/" + "1" + mykey.toUpperCase() + "2/") : apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "getguestwishlist/" + "1" + mykey.toUpperCase() + "2/");
        }

        //  Call<UpgradeWishListResponse> call = Locale == "ar" ? apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "mywishlist/" + MySharedPreferenceClass.getMyUserId(getContext())) : apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "mywishlist/" + MySharedPreferenceClass.getMyUserId(getContext()));


        call.enqueue(new Callback<UpgradeWishListResponse>() {
            @Override
            public void onResponse(Call<UpgradeWishListResponse> call, Response<UpgradeWishListResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResponse().equals("success") && response.body().getData() != null) {
                        if (response.body().getData().size() > 0) {
                            NewCategoryDesignFragments.categorywishListBadgeCount.setVisibility(View.VISIBLE);
                            NewCategoryDesignFragments.categorywishListBadgeCount.setText(String.valueOf(response.body().getData().size()));
                            if (ProductList.productListBadgeCount != null) {
                                ProductList.productListBadgeCount.setVisibility(View.VISIBLE);
                                ProductList.productListBadgeCount.setText(String.valueOf(response.body().getData().size()));
                            }
                            wishListBadgeCount.setVisibility(View.VISIBLE);
                            wishListBadgeCount.setText(String.valueOf(response.body().getData().size()));
                        } else {
                            NewCategoryDesignFragments.categorywishListBadgeCount.setVisibility(View.GONE);

                            if (ProductList.productListBadgeCount != null) {
                                ProductList.productListBadgeCount.setVisibility(View.GONE);
                            }

                            wishListBadgeCount.setVisibility(View.GONE);


                        }
                    } else {
                        Log.e("get wishList", "failed");
                    }
                }
                //getfirsttimepopupData();
            }

            @Override
            public void onFailure(Call<UpgradeWishListResponse> call, Throwable t) {
                Log.e("fail", t.getMessage());
            }
        });
    }

    public void getWishListItemCountforguestUser() {
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
            Locale = "ar";
        } else {
            Locale = "en";
        }
        String myKey;
        myKey = getUniqueID2().replaceAll("[^A-Za-z0-9]", "");
        Log.e("mykey is", myKey);
        /*https://upgrade.eoutlet.com/rest/V1/api/getguestwishlist/23476242784b24b2646v*/
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<UpgradeWishListResponse> call = Locale == "ar" ? apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "getguestwishlist/" + "1" + myKey.toUpperCase() + "2") : apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "getguestwishlist/" + "1" + myKey.toUpperCase() + "2");
        call.enqueue(new Callback<UpgradeWishListResponse>() {
            @Override
            public void onResponse(Call<UpgradeWishListResponse> call, Response<UpgradeWishListResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResponse().equals("success")) {
                        if (response.body().getData() != null && response.body().getData().size() > 0) {
                            NewCategoryDesignFragments.categorywishListBadgeCount.setVisibility(View.VISIBLE);
                            NewCategoryDesignFragments.categorywishListBadgeCount.setText(String.valueOf(response.body().getData().size()));
                            if (ProductList.productListBadgeCount != null) {
                                ProductList.productListBadgeCount.setVisibility(View.VISIBLE);
                                ProductList.productListBadgeCount.setText(String.valueOf(response.body().getData().size()));
                            }
                            wishListBadgeCount.setVisibility(View.VISIBLE);
                            wishListBadgeCount.setText(String.valueOf(response.body().getData().size()));
                        } else {
                            NewCategoryDesignFragments.categorywishListBadgeCount.setVisibility(View.GONE);

                            if (ProductList.productListBadgeCount != null) {
                                ProductList.productListBadgeCount.setVisibility(View.GONE);
                            }

                            wishListBadgeCount.setVisibility(View.GONE);


                        }
                    } else {
                        Log.e("get wishList", "failed");
                    }
                }
                //getfirsttimepopupData();
            }

            @Override
            public void onFailure(Call<UpgradeWishListResponse> call, Throwable t) {
                Log.e("fail", t.getMessage());
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


    public void moengageNewInstallEvent() {

        Properties properties = new Properties();


        MoEHelper.getInstance(getApplicationContext()).trackEvent("New Install", properties);


    }

    private void iniatializechangeCountryDialogue() {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
        View sheetView;
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            sheetView = this.getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
        } else {
            sheetView = this.getLayoutInflater().inflate(R.layout.change_country_arabic, null);
        }

        mBottomSheetDialog.setContentView(sheetView);


        RelativeLayout done = sheetView.findViewById(R.id.done);
        currencyrecycler = sheetView.findViewById(R.id.currencyListRecycler);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.cancel();


                for (int i = 0; i < exchangecountrylist.size(); i++) {

                    if (currencycountrynames.get(countryselctposition).currency.
                            equalsIgnoreCase(exchangecountrylist.get(i).currencyTo)) {

                        sendcountryselction(currencycountrynames.get(countryselctposition).code, exchangecountrylist.get(i).currencyTo, exchangecountrylist.get(i).rate);

                        break;

                    }

                }


            }
        });


        getCurrency();
        mBottomSheetDialog.show();


    }


    public void getCurrencyfromLanguageDialog() {
        //  showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Call<GetCurrencyDetail> call = apiService.getCurrency();
        call.enqueue(new Callback<GetCurrencyDetail>() {
            @Override
            public void onResponse(Call<GetCurrencyDetail> call, Response<GetCurrencyDetail> response) {

                if (response.body() != null && response.isSuccessful()) {

                    exchangecountrylist = response.body().exchangeRates;


                    if (MySharedPreferenceClass.getSelectedCurrencyName(getContext()) == null) {


                        MySharedPreferenceClass.setSelectedCurrencyRate(getApplicationContext(), 1.0f);
                        MySharedPreferenceClass.setSelectedCurrencyName(getApplicationContext(), response.body().baseCurrencyCode);

                        for (int i = 0; i < currencycountrynames.size(); i++) {
                            if (MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()).equalsIgnoreCase(currencycountrynames.get(i).currency)) {
                                MySharedPreferenceClass.setSelectedCountryCode(getApplicationContext(), currencycountrynames.get(i).code);
                            }
                        }
                    }


                    getcountryDetailforLanguageDialog();


                }

            }


            @Override
            public void onFailure(Call<GetCurrencyDetail> call, Throwable t) {

                Log.e(TAG, t.toString());

            }
        });


    }

    public void getcountryDetailforLanguageDialog() {
        //parentDialogListener.showProgressDialog();


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Call<GetCountryCode> call;


        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {

            call = apiService.getCountryDetailEnglish();
        } else {

            call = apiService.getCountryDetailArabic();
        }


        call.enqueue(new Callback<GetCountryCode>() {
            @Override
            public void onResponse(Call<GetCountryCode> call, Response<GetCountryCode> response) {


                if (response.body() != null) {
                    //parentDialogListener.hideProgressDialog();
                    for (int i = 0; i < response.body().data.size(); i++) {

                        if (response.body().data.get(i).currency.equalsIgnoreCase(MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()))) {


                            selectedcountry.setText(response.body().data.get(i).name + " " + "(" + MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()) + ")");
                            try {
                                Glide.with(flagIamge)
                                        .load(Constants.upgradedmediaSourceURL + response.body().data.get(i).flag).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)


                                        .into(flagIamge);
                            } catch (Exception e) {
                            }


                            break;

                        }


                    }
                    currencycountrynames = response.body().data;


                }
            }

            @Override
            public void onFailure(Call<GetCountryCode> call, Throwable t) {
                //parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void showProgressDialog() {
        progressDialog1 = ProgressDialog.show(getActivity(), "Null", "Null", false, false);
        progressDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog1.setContentView(R.layout.progress_bar);
        progressDialog1.setCanceledOnTouchOutside(false);
    }


    public void hideProgressDialog() {

        try {


            //progressDialog.hide();
            progressDialog1.dismiss();
        } catch (Exception e) {

        }

    }

    public boolean areNotificationsEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            if (!manager.areNotificationsEnabled()) {
                return false;
            }
            List<NotificationChannel> channels = manager.getNotificationChannels();
            for (NotificationChannel channel : channels) {
                if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                    return false;
                }
            }
            return true;
        } else {
            return NotificationManagerCompat.from(getApplicationContext()).areNotificationsEnabled();
        }
    }

    public void openNotificationShowDialog() {

        DialogFragment prFrag = new CustomDialogClassforNotification(getActivity());
        Bundle bund = new Bundle();
        prFrag.setArguments(bund);
        prFrag.setTargetFragment(HomeFragmentNewDesign.this, 1002);
        prFrag.show(getFragmentManager(), "signup");


    }

    public void sendcountryselction(String countrycode, String currencycode, Float rate) {
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

                if (response.body() != null && response.isSuccessful()) {
                    if (templangselectionflag == 1) {
                        java.util.Locale.setDefault(java.util.Locale.forLanguageTag("en"));
                        // Toast.makeText(getApplicationContext(), "Language set to English", Toast.LENGTH_SHORT).show();
                        MySharedPreferenceClass.setChoosenLanguage(getActivity(), "en");
                    } else {
                        java.util.Locale.setDefault(java.util.Locale.forLanguageTag("ar"));
                        // Toast.makeText(getApplicationContext(), "ضبط اللغة على اللغة الإنجليزية", Toast.LENGTH_SHORT).show();
                        MySharedPreferenceClass.setChoosenLanguage(getActivity(), "ar");


                    }

                    MySharedPreferenceClass.setSelectedCountryCode(getContext(), countrycode);
                    MySharedPreferenceClass.setSelectedCurrencyName(getContext(), currencycode);
                    MySharedPreferenceClass.setSelectedCurrencyRate(getContext(), rate);
                    MySharedPreferenceClass.setSelectedCurrencyName(getApplicationContext(), currencycode);
                    MySharedPreferenceClass.setSelectedCurrencyRate(getApplicationContext(), rate);
                    Intent i = new Intent(getContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);

                } else {

                    initCurrenCyrecycler();
                }

            }


            @Override
            public void onFailure(Call<CurrencysetResponse> call, Throwable t) {

                Log.e(TAG, t.toString());

            }
        });


    }

    public void getCurrency() {
        //  showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Call<GetCurrencyDetail> call = apiService.getCurrency();
        call.enqueue(new Callback<GetCurrencyDetail>() {
            @Override
            public void onResponse(Call<GetCurrencyDetail> call, Response<GetCurrencyDetail> response) {

                if (response.body() != null && response.isSuccessful()) {

                    exchangecountrylist = response.body().exchangeRates;
                    availiblecuurrency = response.body().availableCurrencyCodes;

                    if (MySharedPreferenceClass.getSelectedCurrencyName(getContext()) == null) {
                        MySharedPreferenceClass.setSelectedCurrencyRate(getApplicationContext(), 1.0f);
                        MySharedPreferenceClass.setSelectedCurrencyName(getApplicationContext(), response.body().baseCurrencyCode);


                    }


                    // initCurrenCyrecycler();

                    getcountryDetail2();


                }

            }


            @Override
            public void onFailure(Call<GetCurrencyDetail> call, Throwable t) {

                Log.e(TAG, t.toString());

            }
        });


    }

    public void initCurrenCyrecycler() {
        List<countryCodeDetail> currencycountrynamesafterfilter = new ArrayList<>();

        // availiblecuurrency.add("AED");
        for (int i = 0; i < currencycountrynames.size(); i++) {
            if (availiblecuurrency.contains(currencycountrynames.get(i).currency)) {
                currencycountrynamesafterfilter.add(currencycountrynames.get(i));


            }


        }
        currencyadpter = new CurrencyListAdapter(getContext(), currencycountrynamesafterfilter, exchangecountrylist, availiblecuurrency, new ViewlistenerCurrency() {
            @Override
            public void onClick(int position, View view, String currencyname) {
                int id = view.getId();
                countryselctposition = position;

                switch (id) {
                    case R.id.currencyselection://button for message


                        //sendcountryselction(exchangecountrylist.get(countryselctposition).currencyTo,exchangecountrylist.get(countryselctposition).rate);
                        break;
                }
            }
        }


        );
        GridLayoutManager lm;
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            lm = new GridLayoutManager(view.getContext(), 3);
        }
        // LinearLayoutManager categoryGridLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        else {
            lm = new GridLayoutManager(view.getContext(), 3) {
                @Override
                protected boolean isLayoutRTL() {
                    return true;
                }
            };
        }
        currencyrecycler.setLayoutManager(lm);
        currencyrecycler.setItemAnimator(new DefaultItemAnimator());
        currencyrecycler.setAdapter(currencyadpter);
        currencyadpter.notifyDataSetChanged();


    }

    public void getcountryDetail2() {
        //parentDialogListener.showProgressDialog();


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Call<GetCountryCode> call;


        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {

            call = apiService.getCountryDetailEnglish();
        } else {

            call = apiService.getCountryDetailArabic();
        }


        call.enqueue(new Callback<GetCountryCode>() {
            @Override
            public void onResponse(Call<GetCountryCode> call, Response<GetCountryCode> response) {


                if (response.body() != null) {
                    //parentDialogListener.hideProgressDialog();


                    for (int i = 0; i < response.body().data.size(); i++) {

                        if (response.body().data.get(i).currency.equalsIgnoreCase(MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()))) {


                            selectedcountry.setText(response.body().data.get(i).currency + " " + MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()));
                            break;

                        }


                    }
                    currencycountrynames = response.body().data;


                    initCurrenCyrecycler();


                }
            }

            @Override
            public void onFailure(Call<GetCountryCode> call, Throwable t) {
                //parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1002) {

            Intent settingsIntent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName())
                    .putExtra(Settings.EXTRA_CHANNEL_ID, "MY_CHANNEL_ID");
            startActivity(settingsIntent);


        }


    }


    @Override
    public void onResume() {
        super.onResume();
        MoEInAppHelper.getInstance().showInApp(getApplicationContext());
        if (dataloaded) {

            //Toast.makeText(getContext(),"fromdataloaded",Toast.LENGTH_LONG).show();
            Log.e("fromdataloaded", "fromdataloaded");
            if (MySharedPreferenceClass.getSharedProductId(getContext()) != null) {
                Log.e("sharedProductId", MySharedPreferenceClass.getSharedProductId(getContext()) + "  " + "fromonresume");

                Fragment productDetail = new ProductDetail();
                FragmentTransaction mFragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.container2, productDetail).addToBackStack(null).commit();
                Bundle databund = new Bundle();
                //put your ArrayList data in bundle
                databund.putString("fromwhere", "fromhome");
                databund.putString("previouspage","FromHomeScreen");
                databund.putString("pid", MySharedPreferenceClass.getSharedProductId(getContext()));
                productDetail.setArguments(databund);
                MySharedPreferenceClass.setSharedProductId(getContext(), null);

                Log.e("sharedproductId", "sharedproductId");

            } else if (MySharedPreferenceClass.getDeeplinkingNotification(getContext()) != null) {

                //Toast.makeText(getContext(),"fromdeeplink",Toast.LENGTH_LONG).show();
                Log.e("fromdataloaded", "fromdataloaded");
                if (MySharedPreferenceClass.getDeeplinkingNotification(getContext()).equalsIgnoreCase("true")) {
                    if (MySharedPreferenceClass.getDeeplinkingPage(getContext()) != null) {
                        if (MySharedPreferenceClass.getDeeplinkingPage(getContext()).equalsIgnoreCase("home")
                              /*  || MySharedPreferenceClass.getDeeplinkingPage(getContext()).equalsIgnoreCase("category")
                                || MySharedPreferenceClass.getDeeplinkingPage(getContext()).equalsIgnoreCase("brand")*/) {

                            if (MySharedPreferenceClass.getDeeplinkingId(getContext()) != null
                                    || MySharedPreferenceClass.getDeeplinkingName(getContext()) != null) {


                                Fragment prFrag = new ProductList();

                                Bundle databund = new Bundle();
                                databund.putString("fromwhere", "fromcatagory");
                                databund.putString("previouspage","HomeScreen");
                                Log.e("Id->>>>>", MySharedPreferenceClass.getDeeplinkingId(getContext()));
                                Log.e("Name->>>>>", MySharedPreferenceClass.getDeeplinkingName(getContext()));
                                databund.putInt("productId", Integer.parseInt(MySharedPreferenceClass.getDeeplinkingId(getContext())));
                                databund.putString("name", MySharedPreferenceClass.getDeeplinkingName(getContext()));
//                        databund.putSerializable("childeren", (Serializable) mainhorizantallist.get(position).data);
                                prFrag.setArguments(databund);
                                getFragmentManager()
                                        .beginTransaction().addToBackStack(null)
                                        .add(R.id.container2, prFrag)
                                        .commit();
                                //  isCategoryContainerVisible = false;
                                // selectedCategoryIcon.setRotation(0);
                                //  categorySelectionContainerCardView.setVisibility(View.GONE);
                                /// searchcontainer.setVisibility(View.VISIBLE);
                               /* MySharedPreferenceClass.setDeeplinkingNotification(getContext(), null);
                                MySharedPreferenceClass.setDeeplinkingId(getContext(), null);
                                MySharedPreferenceClass.setDeeplinkingName(getContext(), null);
                                MySharedPreferenceClass.setDeeplinkingPage(getContext(), null);*/
                            }

                        } else if (MySharedPreferenceClass.getDeeplinkingPage(getContext()).equalsIgnoreCase("detail")) {
                            if (MySharedPreferenceClass.getDeeplinkingId(getContext()) != null
                                    || MySharedPreferenceClass.getDeeplinkingName(getContext()) != null) {


                                //  Log.e("pid", MySharedPreferenceClass.getDeeplinkingId(getContext()));
                                Fragment productDetail = new ProductDetail();
                                FragmentTransaction mFragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                                mFragmentTransaction.add(R.id.container2, productDetail).addToBackStack(null).commit();
                                Bundle databund = new Bundle();
                                //put your ArrayList data in bundle
                                databund.putString("fromwhere", "fromhome");
                                databund.putString("previouspage","FromHomeScreen");
                                databund.putString("pid", MySharedPreferenceClass.getDeeplinkingId(getContext()));
                                productDetail.setArguments(databund);

                                Log.e("pid", MySharedPreferenceClass.getDeeplinkingId(getContext()) + "fromonresume");
                                // MySharedPreferenceClass.setDeeplinkingNotification(getContext(), null);
                               /* MySharedPreferenceClass.setDeeplinkingId(getContext(), null);
                                MySharedPreferenceClass.setDeeplinkingName(getContext(), null);
                                MySharedPreferenceClass.setDeeplinkingPage(getContext(), null);*/
                            }

                        }

                    }

                } else {
                    Log.e("getNotification", "false");
                }
            } else {
                // getUpgradedLatestversionCode();

                Log.e("sharedProductId", "null");


            }
        }
        if (!MySharedPreferenceClass.getCatId(getApplicationContext()).equals(" ")) {
            handleIntent(getActivity().getIntent());

        } else {

            Intent in = new Intent(getApplicationContext(), SplashActivity.class);
            //Intent in = new Intent(SplashActivity.this, EoutletSplash.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(in);

            // overridePendingTransition(R.anim.slide_to_left,R.anim.slide_to_left);
            MySharedPreferenceClass.clear(getApplicationContext());
            getActivity().finish();
        }
    }

    private void handleIntent(Intent intent) {
        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();


        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null) {
            String recipeId = appLinkData.getLastPathSegment();
            Log.e("recepieidis", recipeId);
            Log.e("appLink", appLinkData.toString());


            String type = appLinkData.getQueryParameter("type");


            if (type != null && type.equals("category")) {
                String cid = appLinkData.getQueryParameter("cid");
                String name = appLinkData.getQueryParameter("name");

                if (cid != null && name != null) {
                    Fragment prFrag2 = new ProductList();
                    Bundle databund2 = new Bundle();

                    databund2.putInt("productId", Integer.parseInt(cid));
                    databund2.putString("name", name);
                    databund2.putSerializable("childeren", (Serializable) childlist);
                    databund2.putSerializable("onClick", " ");

                    databund2.putString("fromwhere", "fromhome");
                    databund2.putString("previouspage","FromHomeScreen");
                    prFrag2.setArguments(databund2);


                    getFragmentManager()
                            .beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag2)
                            .commit();
                    getActivity().getIntent().setAction("");
                    Log.e("cid is", cid);
                }
            } else if (type != null && type.equals("product")) {

                String pid = appLinkData.getQueryParameter("pid");
                Log.e("pid is", pid);
                if (pid != null) {
                    Fragment productDetail = new ProductDetail();
                    FragmentTransaction mFragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.add(R.id.container2, productDetail).addToBackStack(null).commit();
                    Bundle databund = new Bundle();
                    //put your ArrayList data in bundle
                    databund.putString("fromwhere", "fromhome");
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putString("pid", pid);
                    productDetail.setArguments(databund);
                    getActivity().getIntent().setAction("");
                }


            }


        }

    }
    public void initTopTabsRecycler(ArrayList<String> alphabetic) {

        topcategoryrecycler.setHasFixedSize(true);
        topcategoryrecycler.setNestedScrollingEnabled(false);

        GridLayoutManager lm;
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            lm = new GridLayoutManager(view.getContext(), 4);

        }
        // LinearLayoutManager categoryGridLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        else {
            lm = new GridLayoutManager(view.getContext(), 4) {
                @Override
                protected boolean isLayoutRTL() {
                    return true;
                }
            };
        }
        topcategoryrecycler.setLayoutManager(lm);


        topcategoryrecycler.setItemAnimator(new DefaultItemAnimator());
        // use a linear layout manager



        // specify an adapter (see also next example)
        madapter = new TopTabsHomeAdapter(getContext(), catselctionposition, alphabetic, new ViewListener() {
            @Override
            public void onClick(int position, View view) {

                int id = view.getId();


                switch (id) {

                    case R.id.parentLayout://button for message
                      if(position==0){
                          MySharedPreferenceClass.setCatId(getApplicationContext(),womenId);
                      }
                      else if(position==1){
                          MySharedPreferenceClass.setCatId(getApplicationContext(),menId);
                      }
                      else if(position==2){
                          MySharedPreferenceClass.setCatId(getApplicationContext(),childerenId);
                      }
                      else if(position==3){
                          MySharedPreferenceClass.setCatId(getApplicationContext(),babiesId);
                      }
                        getHomeScreenData();




                        }


                }

        }


        );
        topcategoryrecycler.setAdapter(madapter);
        topcategoryrecycler.setNestedScrollingEnabled(false);
    }
    public void getUpgradedLatestversionCode() {
        showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Call<GetCategoryCode> call = apiService.getUpgradedLatestAppVersion();
        call.enqueue(new Callback<GetCategoryCode>() {
            @Override
            public void onResponse(Call<GetCategoryCode> call, Response<GetCategoryCode> response) {
                if (response.body() != null) {
                    if (response.body().response.equals("success")) {

                        menId = response.body().menId;
                        womenId = response.body().womenId;
                        childerenId = response.body().childrenId;
                        babiesId = response.body().babiesId;


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

}