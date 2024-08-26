/*
package com.eoutlet.Eoutlet.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.adpters.CategoryAdapter;
import com.eoutlet.Eoutlet.adpters.CurrencyListAdapter;
import com.eoutlet.Eoutlet.adpters.DealsOfTheMonthAdapter;
import com.eoutlet.Eoutlet.adpters.EditorChoiceAdapter;
import com.eoutlet.Eoutlet.adpters.FlashSaleAdapter;
import com.eoutlet.Eoutlet.adpters.LimitedStockAdapter;
import com.eoutlet.Eoutlet.adpters.MyAdapter;
import com.eoutlet.Eoutlet.adpters.NewArrivalViewPagerAdapter;
import com.eoutlet.Eoutlet.adpters.RecommendedViewPagerAdapter;
import com.eoutlet.Eoutlet.adpters.ViewPagerAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.CurrencysetResponse;
import com.eoutlet.Eoutlet.pojo.EditorChoice;
import com.eoutlet.Eoutlet.pojo.ExchangeRate;
import com.eoutlet.Eoutlet.pojo.GetCategoryCode;
import com.eoutlet.Eoutlet.pojo.GetCountryCode;
import com.eoutlet.Eoutlet.pojo.GetCurrencyDetail;
import com.eoutlet.Eoutlet.pojo.GetScreolledHomePageData;
import com.eoutlet.Eoutlet.pojo.LimitedStock;
import com.eoutlet.Eoutlet.pojo.MonthDeal;
import com.eoutlet.Eoutlet.pojo.NewArrival;
import com.eoutlet.Eoutlet.pojo.Recommended;
import com.eoutlet.Eoutlet.pojo.UpgradeHomeScreenFlashSale;
import com.eoutlet.Eoutlet.pojo.UpgradedHomeScreenCategory;
import com.eoutlet.Eoutlet.pojo.UpgradedHomeScreenEditorChoice;
import com.eoutlet.Eoutlet.pojo.UpgradedHomeScreenHighlightedSlider;
import com.eoutlet.Eoutlet.pojo.UpgradedHomeScreenLimitedStock;
import com.eoutlet.Eoutlet.pojo.UpgradedHomeScreenMainBanner;
import com.eoutlet.Eoutlet.pojo.UpgradedHomeScreenMonthDeal;
import com.eoutlet.Eoutlet.pojo.UpgradedHomeScreenNewArrival;
import com.eoutlet.Eoutlet.pojo.UpgradedHomeScreenRecommended;
import com.eoutlet.Eoutlet.pojo.UpgradedHomeScreenResponse;
import com.eoutlet.Eoutlet.pojo.countryCodeDetail;
import com.eoutlet.Eoutlet.utility.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

public class UpgradeHomeScreen extends Fragment {
    private Context mContext;
    //public ParentDialogsListener parentDialogListener;
    ViewPager viewPager, newArrivalViewPager, recommendedViewPager;
    private NestedScrollView homescroller;
    private LinearLayout topbar, categorySelectionContainerHome, header, searchcontainer;
    RelativeLayout shopWomenContainer, shopMenContainer, shopChildrenContainer, shopKidsContainer;
    FrameLayout upgradeHomeLayout;
    CardView categorySelectionContainerCardView, searchCardView, wishListCardView;

    ProgressDialog progressDialog1;
    ImageView wishList, women_tick_image, men_tick_image, child_tick_image, kid_tick_image, selectedCategoryIcon;
    ViewPager2 centerViewPager;
    Boolean dataLoaded = false;
    private String menId, womenId, babiesId, childerenId;
    String userName;
    Boolean isCategoryContainerVisible = false;
    GridLayoutManager categoryGridLayoutManager, EditorChoiceGridLayoutManager;
    LinearLayoutManager flashSaleLayoutManager, dealOfTheMonthLayoutManager, recommendedLayoutManager;
    LinearLayout selectLanguage, sliderDotspanel, newArrivalSliderDots, recommendedSliderDots;
    private int dotscount, newarrivaldotscount, recommenddotscount;
    private ImageView[] dots, newarrivaldots, recommenddots;
    com.eoutlet.Eoutlet.adpters.MyAdapter MyAdapter;
    private RecyclerView categoryRecyclerView, flashSaleRecyclerView, editorChoiceRecyclerView, domRecyclerView, limitedStockRecyclerView;
    private List<UpgradedHomeScreenCategory> categoryList = new ArrayList<>();
    private List<UpgradeHomeScreenFlashSale> flashSaleList = new ArrayList<>();
    private List<EditorChoice> editorChoiceList = new ArrayList<>();
    private List<LimitedStock> limitedStockList = new ArrayList<>();
    private List<MonthDeal> monthDealList = new ArrayList<>();
    private List<UpgradedHomeScreenHighlightedSlider> highlightedSliderList = new ArrayList<>();
    private CategoryAdapter cAdapter;
    private CurrencyListAdapter currencyadpter;
    private FlashSaleAdapter mAdapter;
    private EditorChoiceAdapter eAdapter;
    private DealsOfTheMonthAdapter dAdapter;
    private LimitedStockAdapter lAdapter;
    RecommendedViewPagerAdapter recommendedViewPagerAdapter;
    private View view;
    private int countryselctposition;
    ViewPagerAdapter viewPagerAdapter;
    private List<UpgradedHomeScreenMainBanner> mainBannerList;
    private List<NewArrival> newArrivalList;
    NewArrivalViewPagerAdapter newArrivalViewPagerAdapter;
    private List<Recommended> recommendedList;
    private Call<UpgradedHomeScreenResponse> call;
    private List<ExchangeRate> exchangecountrylist = new ArrayList<>();
    private TextView  selectedCategoryName, shopWomenText, shopMenText, shopChildText, shopKidText, greetingMessage;
    String Locale;

    private List<countryCodeDetail> currencycountrynames = new ArrayList();

    private RecyclerView currencyrecycler;
    private ImageView searchIcon;

    @Override
    public void setUserVisibleHint(boolean visible) {


        super.setUserVisibleHint(visible);

        if (visible && isResumed()) {

            if (MySharedPreferenceClass.getMyUserId(getContext()).equals(" ")) {


                wishList.setVisibility(View.GONE);

            } else {

                wishList.setVisibility(View.VISIBLE);
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                Locale = "ar";
                view = inflater.inflate(R.layout.fragment_upgrade_home_screen_arabic, container, false);
            } else {
                Locale = "en";
                view = inflater.inflate(R.layout.fragment_upgrade_home_screen, container, false);
            }
        } else {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_upgrade_home_screen_arabic, container, false);
        }

        upgradeHomeLayout = (FrameLayout) view.findViewById(R.id.upgradeHomeLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        newArrivalViewPager = (ViewPager) view.findViewById(R.id.newArrivalViewPager);
        recommendedViewPager = (ViewPager) view.findViewById(R.id.recommendedViewPager);
        centerViewPager = (ViewPager2) view.findViewById(R.id.centerViewPager);
        selectLanguage = (LinearLayout) view.findViewById(R.id.selectLanguage);
        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);
        newArrivalSliderDots = (LinearLayout) view.findViewById(R.id.newArrivalSliderDots);
        recommendedSliderDots = (LinearLayout) view.findViewById(R.id.recommendedSliderDots);
        categoryRecyclerView = (RecyclerView) view.findViewById(R.id.categoryRecyclerView);
        flashSaleRecyclerView = (RecyclerView) view.findViewById(R.id.flashSaleRecyclerView);
        editorChoiceRecyclerView = (RecyclerView) view.findViewById(R.id.editorChoiceRecyclerView);
        limitedStockRecyclerView = (RecyclerView) view.findViewById(R.id.limitedStockRecyclerView);
        domRecyclerView = (RecyclerView) view.findViewById(R.id.domRecyclerView);
        searchIcon = (ImageView) view.findViewById(R.id.searchIcon);
        homescroller = view.findViewById(R.id.homescroller);
        topbar = view.findViewById(R.id.topBar);
        categorySelectionContainerHome = view.findViewById(R.id.categorySelectionContainerHome);
        searchcontainer = view.findViewById(R.id.searchcontainer);
        header = view.findViewById(R.id.header);
        selectedCategoryName = view.findViewById(R.id.selectedCategoryName);
        greetingMessage = view.findViewById(R.id.greetingMessage);
        categorySelectionContainerCardView = view.findViewById(R.id.categorySelectionContainerCardView);
        searchCardView = view.findViewById(R.id.searchCardView);
        wishListCardView = view.findViewById(R.id.wishListCardView);
        selectedCategoryIcon = view.findViewById(R.id.selectedCategoryIcon);
        shopWomenContainer = view.findViewById(R.id.shopWomenContainer);
        shopMenContainer = view.findViewById(R.id.shopMenContainer);
        shopChildrenContainer = view.findViewById(R.id.shopChildrenContainer);
        shopKidsContainer = view.findViewById(R.id.shopKidsContainer);
        women_tick_image = view.findViewById(R.id.women_tick_image);
        men_tick_image = view.findViewById(R.id.men_tick_image);
        child_tick_image = view.findViewById(R.id.child_tick_image);
        kid_tick_image = view.findViewById(R.id.kid_tick_image);

        getUpgradedLatestversionCode();

        if (MySharedPreferenceClass.getMyUserName(getContext()) != null) {
            userName = MySharedPreferenceClass.getMyUserName(getContext());
        }
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 6 && timeOfDay < 12) {
            greetingMessage.setText(userName != null ? "Good Morning, " + userName + "!" : "Good Morning!");
        } else if (timeOfDay >= 12 && timeOfDay < 17) {
            greetingMessage.setText(userName != null ? "Good Afternoon, " + userName + "!" : "Good Afternoon!");
        } else {
            greetingMessage.setText(userName != null ? "Good Evening, " + userName + "!" : "Good Evening!");
        }

        // upgradeHomeLayout.setVisibility(View.GONE);
        homescroller.setVisibility(View.GONE);
        selectLanguage.setVisibility(View.GONE);

        homescroller.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == 0) {
                    topbar.setBackgroundColor(0);
                    // Toast.makeText(getApplicationContext(),"Scroll End",Toast.LENGTH_SHORT).show();
                } else if (scrollY > 100) {

                    topbar.setBackgroundColor(Color.parseColor("#45000000"));
                }
            }
        });

        wishList = (ImageView) view.findViewById(R.id.wishList);
        if (MySharedPreferenceClass.getMyUserId(getContext()).equals(" ")) {
            wishList.setVisibility(View.GONE);
        } else {
            wishList.setVisibility(View.VISIBLE);
        }

        selectLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniatializesortDialogue();
            }
        });
        wishListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new UpgradeWishListFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.upgradeHomeLayout, fragment);
                ft.commit();
            }
        });
        searchCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment prFrag = new SearchResultFragment();
                Bundle databund = new Bundle();
                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.upgradeHomeLayout, prFrag)
                        .commit();
            }
        });
        categorySelectionContainerHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCategoryContainerVisible == false) {
                    if (MySharedPreferenceClass.getCatId(getContext()) != null) {
                        women_tick_image.setVisibility(MySharedPreferenceClass.getCatId(getContext()).equals(womenId) ? View.VISIBLE : View.GONE);
                        men_tick_image.setVisibility(MySharedPreferenceClass.getCatId(getContext()).equals(menId) ? View.VISIBLE : View.GONE);
                        child_tick_image.setVisibility(MySharedPreferenceClass.getCatId(getContext()).equals(childerenId) ? View.VISIBLE : View.GONE);
                        kid_tick_image.setVisibility(MySharedPreferenceClass.getCatId(getContext()).equals(babiesId) ? View.VISIBLE : View.GONE);
                    }
                    selectedCategoryIcon.setRotation(180);
                    isCategoryContainerVisible = true;
                    categorySelectionContainerCardView.setVisibility(View.VISIBLE);
                    searchcontainer.setVisibility(View.GONE);
                } else {
                    isCategoryContainerVisible = false;
                    selectedCategoryIcon.setRotation(0);
                    categorySelectionContainerCardView.setVisibility(View.GONE);
                    searchcontainer.setVisibility(View.VISIBLE);
                }

            }
        });
        shopWomenContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                women_tick_image.setVisibility(View.VISIBLE);
                men_tick_image.setVisibility(View.GONE);
                child_tick_image.setVisibility(View.GONE);
                kid_tick_image.setVisibility(View.GONE);
                MySharedPreferenceClass.setCatId(getApplicationContext(), womenId);
                getActivity().recreate();
            }
        });
        shopMenContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                women_tick_image.setVisibility(View.GONE);
                men_tick_image.setVisibility(View.VISIBLE);
                child_tick_image.setVisibility(View.GONE);
                kid_tick_image.setVisibility(View.GONE);
                MySharedPreferenceClass.setCatId(getApplicationContext(), menId);
                getActivity().recreate();
            }
        });
        shopChildrenContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                men_tick_image.setVisibility(View.GONE);
                women_tick_image.setVisibility(View.GONE);
                child_tick_image.setVisibility(View.VISIBLE);
                kid_tick_image.setVisibility(View.GONE);
                MySharedPreferenceClass.setCatId(getApplicationContext(), childerenId);
                getActivity().recreate();
            }
        });
        shopKidsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                men_tick_image.setVisibility(View.GONE);
                women_tick_image.setVisibility(View.GONE);
                child_tick_image.setVisibility(View.GONE);
                kid_tick_image.setVisibility(View.VISIBLE);
                MySharedPreferenceClass.setCatId(getApplicationContext(), babiesId);
                getActivity().recreate();
            }
        });

        //    category grid layout manager
        categoryGridLayoutManager = new GridLayoutManager(getApplicationContext(), 3, LinearLayoutManager.VERTICAL, false) {
            @Override
            protected boolean isLayoutRTL() {
                if (Locale == "ar") {
                    return true;
                } else {
                    return false;
                }
            }
        };
        categoryRecyclerView.setLayoutManager(categoryGridLayoutManager);
        categoryRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //    flash sale layout manager
        flashSaleLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            protected boolean isLayoutRTL() {
                if (Locale == "ar") {
                    return true;
                } else {
                    return false;
                }
            }
        };
        flashSaleRecyclerView.setLayoutManager(flashSaleLayoutManager);
        flashSaleRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //    editor choice grid layout manager
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

        //        deal of the month layout manager
        dealOfTheMonthLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        domRecyclerView.setLayoutManager(dealOfTheMonthLayoutManager);
        domRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //        Recommended Layout manager
        recommendedLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            protected boolean isLayoutRTL() {
                if (Locale == "ar") {
                    return true;
                } else {
                    return false;
                }
            }
        };
        limitedStockRecyclerView.setLayoutManager(recommendedLayoutManager);
        limitedStockRecyclerView.setItemAnimator(new DefaultItemAnimator());

        homescroller.setVisibility(View.VISIBLE);

        getHomeScreenData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dataLoaded == true) {
            if (MySharedPreferenceClass.getSharedProductId(getContext()) != null) {
                Log.e("sharedProductId", MySharedPreferenceClass.getSharedProductId(getContext()));
                Fragment productDetail = new ProductDetail();
                FragmentTransaction mFragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.upgradeHomeLayout, productDetail).addToBackStack(null).commit();
                Bundle databund = new Bundle();
                //put your ArrayList data in bundle
                databund.putString("fromwhere", "fromhome");
                databund.putString("pid", MySharedPreferenceClass.getSharedProductId(getContext()));
                productDetail.setArguments(databund);
                MySharedPreferenceClass.setSharedProductId(getContext(), null);
            } else {
                Log.e("sharedProductId", "null");
            }
        }
    }
    private void getUpgradedLatestversionCode() {
        showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Call<GetCategoryCode> call = apiService.getUpgradedLatestAppVersion();
        call.enqueue(new Callback<GetCategoryCode>() {
            @Override
            public void onResponse(Call<GetCategoryCode> call, Response<GetCategoryCode> response) {
                if (response.body() != null) {
                    hideProgressDialog();
                    if (response.body().response.equals("success")) {
                        menId = response.body().menId;
                        womenId = response.body().womenId;
                        childerenId = response.body().childrenId;
                        babiesId = response.body().babiesId;
                        if (MySharedPreferenceClass.getCatId(getContext()) != null) {
                            Log.e("getCatId", MySharedPreferenceClass.getCatId(getContext()));
                            if (MySharedPreferenceClass.getCatId(getContext()).equals(womenId)) {
                                selectedCategoryName.setText("Shop Women");
                            } else if (MySharedPreferenceClass.getCatId(getContext()).equals(menId)) {
                                selectedCategoryName.setText("Shop Men");
                            } else if (MySharedPreferenceClass.getCatId(getContext()).equals(babiesId)) {
                                selectedCategoryName.setText("Shop Kids");
                            } else {
                                selectedCategoryName.setText("Shop Children");
                            }
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<GetCategoryCode> call, Throwable t) {
                hideProgressDialog();
                Log.e(TAG, t.toString());
            }
        });
    }
    private void iniatializesortDialogue() {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
        View sheetView = this.getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
        mBottomSheetDialog.setContentView(sheetView);


        RelativeLayout done = sheetView.findViewById(R.id.done);
        currencyrecycler = sheetView.findViewById(R.id.currencyListRecycler);



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.cancel();
                sendcountryselction(exchangecountrylist.get(countryselctposition).currencyTo, exchangecountrylist.get(countryselctposition).rate);

            }
        });


        getCurrency();
        mBottomSheetDialog.show();

        MySharedPreferenceClass.setisAppOpenFirstTime(getApplicationContext(),true);
    }


    public void initCurrenCyrecycler() {
        currencyadpter = new CurrencyListAdapter(getContext(), currencycountrynames, exchangecountrylist, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
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
        //LinearLayoutManager categoryGridLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        GridLayoutManager lm;
        if(MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")){
            lm = new GridLayoutManager(view.getContext(), 3);
        }
        // LinearLayoutManager categoryGridLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        else{
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {

           // parentDialogListener = (ParentDialogsListener) context;

        } catch (ClassCastException e) {

            throw new ClassCastException(context.toString()
                    + " Activity's Parent should be Parent Activity");
        }
    }


    private void getHomeScreenData() {
        //parentDialogListener.showProgressDialog();
        showProgressDialog();
        Log.e("getHomeScreenData", "getHomeScreenData");

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        call = Locale == "ar" ? apiService.getUpgradedHomeScreenData(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "gethomepage/" + MySharedPreferenceClass.getCatId(getContext())) : apiService.getUpgradedHomeScreenData(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "gethomepage/" + MySharedPreferenceClass.getCatId(getContext()));
        call.enqueue(new Callback<UpgradedHomeScreenResponse>() {
            @Override
            public void onResponse(Call<UpgradedHomeScreenResponse> call, Response<UpgradedHomeScreenResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResponse().equalsIgnoreCase("success")) {

                        mainBannerList = response.body().getData().getMain_banner();
                        categoryList = response.body().getData().getCategory();
                        highlightedSliderList = response.body().getData().getHighlighted_slider();
                        flashSaleList = response.body().getData().getFlash_sale();
                        //newArrivalList = response.body().getData().getNew_arrivals();
                       // editorChoiceList = response.body().getData().getEditor_choice();
                       // monthDealList = response.body().getData().getMonth_deals();
                        //recommendedList = response.body().getData().getRecommended();
                       // limitedStockList = response.body().getData().getLimited_stock();
                        homescroller.setVisibility(View.VISIBLE);
                        //selectLanguage.setVisibility(View.VISIBLE);
                        //upgradeHomeLayout.setVisibility(View.VISIBLE);
                        //parentDialogListener.hideProgressDialog();
                        //hideProgressDialog();
                        getScrollHomeScreenData();

                        dataLoaded = true;
                        if (MySharedPreferenceClass.getSharedProductId(getApplicationContext()) != null) {
                            Log.e("sharedProductId", MySharedPreferenceClass.getSharedProductId(getContext()));
                            Fragment productDetail = new ProductDetail();
                            FragmentTransaction mFragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                            mFragmentTransaction.replace(R.id.upgradeHomeLayout, productDetail).addToBackStack(null).commit();
                            Bundle databund = new Bundle();
                            //put your ArrayList data in bundle
                            databund.putString("fromwhere", "fromhome");
                            databund.putString("pid", MySharedPreferenceClass.getSharedProductId(getContext()));
                            productDetail.setArguments(databund);
                            MySharedPreferenceClass.setSharedProductId(getContext(), null);
                        } else {
                            Log.e("sharedProductId", "null");
                        }


                        //   main banner adapter start
                        viewPagerAdapter = new ViewPagerAdapter(getContext(), mainBannerList, UpgradeHomeScreen.this);
                        viewPager.setAdapter(viewPagerAdapter);
                        viewPagerAdapter.notifyDataSetChanged();
                        dotscount = viewPagerAdapter.getCount();
                        if (dotscount > 0) {
                            dots = new ImageView[dotscount];
                            for (int i = 0; i < dotscount; i++) {
                                dots[i] = new ImageView(getApplicationContext());
                                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                params.setMargins(8, 0, 8, 0);
                                sliderDotspanel.addView(dots[i], params);
                            }
                            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    for (int i = 0; i < dotscount; i++) {
                                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                    }
                                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {
                                }
                            });
                        }
                        //   main banner adapter ends

                        //  category adapter start
                        cAdapter = new CategoryAdapter(categoryList, getContext(), UpgradeHomeScreen.this);
                        categoryRecyclerView.setAdapter(cAdapter);
                        cAdapter.notifyDataSetChanged();
                        //  category adapter stop

                        //  highlighted_slider ViewPager start
                        MyAdapter = new MyAdapter(getContext(), highlightedSliderList, UpgradeHomeScreen.this);
                        centerViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                        centerViewPager.setAdapter(MyAdapter);
                        centerViewPager.setOffscreenPageLimit(3);

                        if (MyAdapter.getItemCount() > 1) {
                            Log.e("getItemCount", String.valueOf(MyAdapter.getItemCount()));
                            centerViewPager.setCurrentItem(1, false);
                        } else {
                            Log.e("getItemCount", "1");
                        }

                        MyAdapter.notifyDataSetChanged();
                        float pageMargin = getResources().getDimensionPixelOffset(R.dimen.brand_height);
                        float pageOffset = getResources().getDimensionPixelOffset(R.dimen.brand_height);
                        centerViewPager.setPageTransformer((page, position) -> {
                            float myOffset = position * -(2 * pageOffset + pageMargin);
                            if (position < -1) {
                                page.setTranslationX(-myOffset);
                            } else if (position <= 1) {
                                float scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.04285715f));
                                page.setTranslationX(myOffset);
                                page.setScaleY(scaleFactor);
                                page.setAlpha(scaleFactor);
                            } else {
                                page.setAlpha(0);
                                page.setTranslationX(myOffset);
                            }
                        });
                        //   highlighted_slider ViewPager end

                        //   flash sale adapter start
                        mAdapter = new FlashSaleAdapter(getContext(), flashSaleList, UpgradeHomeScreen.this);
                        flashSaleRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                        //   flash sale adapter stop

                        //   NewArrivalViewPagerAdapter starts
newArrivalViewPagerAdapter = new NewArrivalViewPagerAdapter(getContext(), newArrivalList, UpgradeHomeScreen.this);
                        newArrivalViewPager.setAdapter(newArrivalViewPagerAdapter);
                        newarrivaldotscount = newArrivalViewPagerAdapter.getCount();
                        if (newarrivaldotscount > 0) {
                            newarrivaldots = new ImageView[newarrivaldotscount];
                            for (int i = 0; i < newarrivaldotscount; i++) {
                                newarrivaldots[i] = new ImageView(getContext());
                                newarrivaldots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                params.setMargins(8, 0, 8, 0);
                                newArrivalSliderDots.addView(newarrivaldots[i], params);
                            }
                            newarrivaldots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                            newArrivalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    for (int i = 0; i < newarrivaldotscount; i++) {
                                        newarrivaldots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                    }
                                    newarrivaldots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {
                                }
                            });
                        }
                        //    NewArrivalViewPagerAdapter end

                        //   editor choice adapter start
                        eAdapter = new EditorChoiceAdapter(getContext(), editorChoiceList, UpgradeHomeScreen.this);
                        editorChoiceRecyclerView.setAdapter(eAdapter);
                        eAdapter.notifyDataSetChanged();
                        //   editor choice adapter stop

                        //    deal of the month  adapter start
                        dAdapter = new DealsOfTheMonthAdapter(getContext(), monthDealList, UpgradeHomeScreen.this);
                        domRecyclerView.setAdapter(dAdapter);
                        dAdapter.notifyDataSetChanged();
                        //   deal of the month adapter stop

                        //   recommendedViewPager starts
                        recommendedViewPagerAdapter = new RecommendedViewPagerAdapter(getContext(), recommendedList, UpgradeHomeScreen.this);
                        recommendedViewPager.setAdapter(recommendedViewPagerAdapter);
                        recommenddotscount = recommendedViewPagerAdapter.getCount();
                        recommenddots = new ImageView[recommenddotscount];
                        for (int i = 0; i < recommenddotscount; i++) {
                            recommenddots[i] = new ImageView(getContext());
                            recommenddots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(8, 0, 8, 0);
                            recommendedSliderDots.addView(recommenddots[i], params);
                        }
                        recommenddots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                        recommendedViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            }

                            @Override
                            public void onPageSelected(int position) {
                                for (int i = 0; i < recommenddotscount; i++) {
                                    recommenddots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                }
                                recommenddots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {
                            }
                        });
                        //   recommendedViewPager end

                        //   limitedStock adapter start
                        lAdapter = new LimitedStockAdapter(getContext(), limitedStockList, UpgradeHomeScreen.this);
                        limitedStockRecyclerView.setAdapter(lAdapter);
                        lAdapter.notifyDataSetChanged();
                        //   limitedStock adapter stop



                        if(!MySharedPreferenceClass.getisAppOpenFirstTime(getApplicationContext())) {

                            iniatializesortDialogue();
                        }


                    } else {
                       // parentDialogListener.hideProgressDialog();
                        hideProgressDialog();

                        Log.e("Homescreen api", " data not received");

                    }
                } else {
                    hideProgressDialog();

                   // parentDialogListener.hideProgressDialog();
                    Log.e("Homescreen api", " data null");

                }
            }

            @Override
            public void onFailure(Call<UpgradedHomeScreenResponse> call, Throwable t) {
               // parentDialogListener.hideProgressDialog();
                hideProgressDialog();

                Log.e("UpgradedHome fail", t.getMessage());
            }
        });


    }

    private void getScrollHomeScreenData() {
        //parentDialogListener.showProgressDialog();




        Log.e("getHomeScreenData", "getHomeScreenData");

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<GetScreolledHomePageData> call;
        call = Locale == "ar" ? apiService.getUpgradedScrolledHomeScreenData(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "getnexthomepage/" + MySharedPreferenceClass.getCatId(getContext())) : apiService.getUpgradedScrolledHomeScreenData(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "getnexthomepage/" + MySharedPreferenceClass.getCatId(getContext()));
        call.enqueue(new Callback<GetScreolledHomePageData>() {
            @Override
            public void onResponse(Call<GetScreolledHomePageData> call, Response<GetScreolledHomePageData> response) {
                if (response.body() != null) {
                    if (response.body().getResponse().equalsIgnoreCase("success")) {

                       // mainBannerList = response.body().getData().getMain_banner();
                       // categoryList = response.body().getData().getCategory();
                       // highlightedSliderList = response.body().getData().getHighlighted_slider();
                       // flashSaleList = response.body().getData().getFlash_sale();
                        newArrivalList = response.body().getData().getNewArrivals();
                        editorChoiceList = response.body().getData().getEditorChoice();
                        monthDealList = response.body().getData().getMonthDeals();
                        recommendedList = response.body().getData().getRecommended();
                        limitedStockList = response.body().getData().getLimitedStock();
                        homescroller.setVisibility(View.VISIBLE);
                        //selectLanguage.setVisibility(View.VISIBLE);
                        //upgradeHomeLayout.setVisibility(View.VISIBLE);
                        //parentDialogListener.hideProgressDialog();
                        hideProgressDialog();

                        dataLoaded = true;
                        if (MySharedPreferenceClass.getSharedProductId(getApplicationContext()) != null) {
                            Log.e("sharedProductId", MySharedPreferenceClass.getSharedProductId(getContext()));
                            Fragment productDetail = new ProductDetail();
                            FragmentTransaction mFragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                            mFragmentTransaction.replace(R.id.upgradeHomeLayout, productDetail).addToBackStack(null).commit();
                            Bundle databund = new Bundle();
                            //put your ArrayList data in bundle
                            databund.putString("fromwhere", "fromhome");
                            databund.putString("pid", MySharedPreferenceClass.getSharedProductId(getContext()));
                            productDetail.setArguments(databund);
                            MySharedPreferenceClass.setSharedProductId(getContext(), null);
                        } else {
                            Log.e("sharedProductId", "null");
                        }


  //   main banner adapter start
                        viewPagerAdapter = new ViewPagerAdapter(getContext(), mainBannerList, UpgradeHomeScreen.this);
                        viewPager.setAdapter(viewPagerAdapter);
                        viewPagerAdapter.notifyDataSetChanged();
                        dotscount = viewPagerAdapter.getCount();
                        if (dotscount > 0) {
                            dots = new ImageView[dotscount];
                            for (int i = 0; i < dotscount; i++) {
                                dots[i] = new ImageView(getApplicationContext());
                                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                params.setMargins(8, 0, 8, 0);
                                sliderDotspanel.addView(dots[i], params);
                            }
                            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    for (int i = 0; i < dotscount; i++) {
                                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                    }
                                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {
                                }
                            });
                        }
                        //   main banner adapter ends

                        //  category adapter start
                        cAdapter = new CategoryAdapter(categoryList, getContext(), UpgradeHomeScreen.this);
                        categoryRecyclerView.setAdapter(cAdapter);
                        cAdapter.notifyDataSetChanged();
                        //  category adapter stop

                        //  highlighted_slider ViewPager start
                        MyAdapter = new MyAdapter(getContext(), highlightedSliderList, UpgradeHomeScreen.this);
                        centerViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                        centerViewPager.setAdapter(MyAdapter);
                        centerViewPager.setOffscreenPageLimit(3);

                        if (MyAdapter.getItemCount() > 1) {
                            Log.e("getItemCount", String.valueOf(MyAdapter.getItemCount()));
                            centerViewPager.setCurrentItem(1, false);
                        } else {
                            Log.e("getItemCount", "1");
                        }

                        MyAdapter.notifyDataSetChanged();
                        float pageMargin = getResources().getDimensionPixelOffset(R.dimen.brand_height);
                        float pageOffset = getResources().getDimensionPixelOffset(R.dimen.brand_height);
                        centerViewPager.setPageTransformer((page, position) -> {
                            float myOffset = position * -(2 * pageOffset + pageMargin);
                            if (position < -1) {
                                page.setTranslationX(-myOffset);
                            } else if (position <= 1) {
                                float scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.04285715f));
                                page.setTranslationX(myOffset);
                                page.setScaleY(scaleFactor);
                                page.setAlpha(scaleFactor);
                            } else {
                                page.setAlpha(0);
                                page.setTranslationX(myOffset);
                            }
                        });
                        //   highlighted_slider ViewPager end

                        //   flash sale adapter start
                        mAdapter = new FlashSaleAdapter(getContext(), flashSaleList, UpgradeHomeScreen.this);
                        flashSaleRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();


                        //   flash sale adapter stop

                        //   NewArrivalViewPagerAdapter starts
                        newArrivalViewPagerAdapter = new NewArrivalViewPagerAdapter(getContext(), newArrivalList, UpgradeHomeScreen.this);
                        newArrivalViewPager.setAdapter(newArrivalViewPagerAdapter);
                        newarrivaldotscount = newArrivalViewPagerAdapter.getCount();
                        if (newarrivaldotscount > 0) {
                            newarrivaldots = new ImageView[newarrivaldotscount];
                            for (int i = 0; i < newarrivaldotscount; i++) {
                                newarrivaldots[i] = new ImageView(getContext());
                                newarrivaldots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                params.setMargins(8, 0, 8, 0);
                                newArrivalSliderDots.addView(newarrivaldots[i], params);
                            }
                            newarrivaldots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                            newArrivalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    for (int i = 0; i < newarrivaldotscount; i++) {
                                        newarrivaldots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                    }
                                    newarrivaldots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {
                                }
                            });
                        }
                        //    NewArrivalViewPagerAdapter end

                        //   editor choice adapter start
                        eAdapter = new EditorChoiceAdapter(getContext(), editorChoiceList, UpgradeHomeScreen.this);
                        editorChoiceRecyclerView.setAdapter(eAdapter);
                        eAdapter.notifyDataSetChanged();
                        //   editor choice adapter stop

                        //    deal of the month  adapter start
                        dAdapter = new DealsOfTheMonthAdapter(getContext(), monthDealList, UpgradeHomeScreen.this);
                        domRecyclerView.setAdapter(dAdapter);
                        dAdapter.notifyDataSetChanged();
                        //   deal of the month adapter stop

                        //   recommendedViewPager starts
                        recommendedViewPagerAdapter = new RecommendedViewPagerAdapter(getContext(), recommendedList, UpgradeHomeScreen.this);
                        recommendedViewPager.setAdapter(recommendedViewPagerAdapter);
                        recommenddotscount = recommendedViewPagerAdapter.getCount();
                        recommenddots = new ImageView[recommenddotscount];
                        for (int i = 0; i < recommenddotscount; i++) {
                            recommenddots[i] = new ImageView(getContext());
                            recommenddots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(8, 0, 8, 0);
                            recommendedSliderDots.addView(recommenddots[i], params);
                        }
                        recommenddots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                        recommendedViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            }

                            @Override
                            public void onPageSelected(int position) {
                                for (int i = 0; i < recommenddotscount; i++) {
                                    recommenddots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                }
                                recommenddots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {
                            }
                        });
                        //   recommendedViewPager end

                        //   limitedStock adapter start
                        lAdapter = new LimitedStockAdapter(getContext(), limitedStockList, UpgradeHomeScreen.this);
                        limitedStockRecyclerView.setAdapter(lAdapter);
                        lAdapter.notifyDataSetChanged();
                        //   limitedStock adapter stop


                        if(!MySharedPreferenceClass.getisAppOpenFirstTime(getApplicationContext())) {

                            iniatializesortDialogue();
                        }


                    } else {
                        // parentDialogListener.hideProgressDialog();
                        hideProgressDialog();

                        Log.e("Homescreen api", " data not received");

                    }
                } else {
                    hideProgressDialog();

                    // parentDialogListener.hideProgressDialog();
                    Log.e("Homescreen api", " data null");

                }
            }

            @Override
            public void onFailure(Call<GetScreolledHomePageData> call, Throwable t) {
                // parentDialogListener.hideProgressDialog();
                hideProgressDialog();

                Log.e("UpgradedHome fail", t.getMessage());
            }
        });


    }
    public void passData(Context mcontext) {
        mContext = mcontext;
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


                    if (MySharedPreferenceClass.getSelectedCurrencyName(getContext()) == null) {
                        MySharedPreferenceClass.setSelectedCurrencyRate(getApplicationContext(), 1.0f);
                        MySharedPreferenceClass.setSelectedCurrencyName(getApplicationContext(), response.body().baseCurrencyCode);
                    }
                    initCurrenCyrecycler();

                    getcountryDetail();


                }

            }


            @Override
            public void onFailure(Call<GetCurrencyDetail> call, Throwable t) {

                Log.e(TAG, t.toString());

            }
        });


    }

    public void sendcountryselction(String currencycode, Float rate) {
*/
/*
https://upgrade.eoutlet.com/rest/en/V1/api/setquotecurrency
        *
        *
        * "{
    ""param"":{
        ""quote_id"":2016,
        ""currency_code"":""USD""
    }
}"
*//*


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


        Call<CurrencysetResponse> call = apiService.postUserCurrencySelection(mainparam);
        call.enqueue(new Callback<CurrencysetResponse>() {
            @Override
            public void onResponse(Call<CurrencysetResponse> call, Response<CurrencysetResponse> response) {

                if (response.body() != null && response.isSuccessful()) {

                    MySharedPreferenceClass.setSelectedCurrencyName(getContext(), currencycode);
                    MySharedPreferenceClass.setSelectedCurrencyRate(getContext(), rate);


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

    public void getcountryDetail() {
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

                        currencycountrynames = response.body().data;


                        initCurrenCyrecycler();

                    }


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
        progressDialog1 = ProgressDialog.show( getActivity(), "Null", "Null", false, false);
        progressDialog1.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        progressDialog1.setContentView( R.layout.progress_bar );
        progressDialog1.setCanceledOnTouchOutside(false);
    }


    public void hideProgressDialog() {

        try {



            //progressDialog.hide();
            progressDialog1.dismiss();
        } catch (Exception e) {

        }

    }

}
*/
