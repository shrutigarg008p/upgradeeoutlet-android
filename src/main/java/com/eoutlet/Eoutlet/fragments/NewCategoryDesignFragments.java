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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.adpters.LeftCategoryAdapter;
import com.eoutlet.Eoutlet.adpters.NewArrivalHomeAdapter;
import com.eoutlet.Eoutlet.adpters.RightCategoryAdapter;
import com.eoutlet.Eoutlet.adpters.RightCategoryAdapterforBrands;
import com.eoutlet.Eoutlet.adpters.RightCategoryBannerAdapter;
import com.eoutlet.Eoutlet.adpters.TopTabsCategoryAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.CategoryData;
import com.eoutlet.Eoutlet.pojo.Datum;
import com.eoutlet.Eoutlet.pojo.GetCategoryCode;
import com.eoutlet.Eoutlet.pojo.GetNewArrivalHomeData;
import com.eoutlet.Eoutlet.pojo.NewCategoryDatum;
import com.eoutlet.Eoutlet.pojo.UpgradeCatgoryresponse;
import com.eoutlet.Eoutlet.utility.Constants;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.moe.pushlibrary.MoEHelper;
import com.moengage.core.Properties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

public class NewCategoryDesignFragments extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean _hasLoadedOnce = false;
    private Toolbar toolbar1;
    private Context mContext;
    private ImageView searchImage;
    String Locale;
    LinearLayout newarrivallayout;
    public ParentDialogsListener parentDialogListener;
    private RecyclerView topcategoryrecycler;
    private RecyclerView newarrivalhomerecycler, leftcategoryrecycler;
    private RecyclerView rightcategoryrecycler, rightsidebannercategory;
    private View view;
    private Boolean gridViewVisible = false;

    private TopTabsCategoryAdapter madapter;
    private LeftCategoryAdapter madapter2;
    private RightCategoryAdapter madapter3;
    private RightCategoryAdapterforBrands madapterforbrands;
    public static TextView categorywishListBadgeCount;
    private RightCategoryBannerAdapter madapter4;
    private ArrayList<String> topcatname = new ArrayList<>();

    private ArrayList<Integer> topcatId = new ArrayList<>();
    private ArrayList<String> leftcatname = new ArrayList<>();
    private ArrayList<String> rightcatname = new ArrayList<>();
    private ArrayList<String> rightcatbannername = new ArrayList<>();
    private ArrayList<String> rightbannercatname = new ArrayList<>();

    private List<NewCategoryDatum> alldata = new ArrayList<>();
    private List<CategoryData> newalldata = new ArrayList<>();
    private LinearLayout brandsclick;
    private int toplistposition = 1;
    private TextView brandtext, toolBarTitle, toolBarCategory;
    private ImageView checkImage;
    private FrameLayout wishlist;
    public static boolean isbrandselected = false;
    private FrameLayout categorycontainer;
    private String newArrivalId;
    private int leftlistposition;
    private int toplistmainposition;
    private int leftlistmainposition;
    private ProgressDialog progressDialog1;
    int catselctionposition = 1;
    private List<Datum> newarrivalItem = new ArrayList<>();
    private NewArrivalHomeAdapter newarrivalAdapter;

    static int leftnavigatingposition;

    @Override
    public void onPause() {
        super.onPause();
        _hasLoadedOnce = false;
    }

    public NewCategoryDesignFragments() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewCategoryDesignFragments.
     */
    // TODO: Rename and change types and number of parameters
    public static NewCategoryDesignFragments newInstance(String param1, String param2) {
        NewCategoryDesignFragments fragment = new NewCategoryDesignFragments();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void passData(Context context) {
        mContext = context;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equalsIgnoreCase("en")) {
            view = inflater.inflate(R.layout.fragment_new_category_design_fragments, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_new_category_design_fragments_arabic, container, false);
        }
        Locale = MySharedPreferenceClass.getChoosenlanguage(getApplicationContext());
        initViews();
        /*if (!MainActivity.isaddressempty) {


        }*/
        // getUpgradedLatestversionCode();

      /*  if (MySharedPreferenceClass.getDeeplinkingPage(getApplicationContext()) != null) {
            if (MySharedPreferenceClass.getDeeplinkingPage(getApplicationContext()).equalsIgnoreCase("category")){
                MySharedPreferenceClass.setDeeplinkingNotification(getContext(), null);
                MySharedPreferenceClass.setDeeplinkingId(getContext(), null);
                MySharedPreferenceClass.setDeeplinkingName(getContext(), null);
                MySharedPreferenceClass.setDeeplinkingPage(getContext(), null);
            }

        }*/

        Log.e("from createview-->", "inside createview");

        return view;

    }

    private void initViews() {

        topcategoryrecycler = view.findViewById(R.id.toptabscategory);
        leftcategoryrecycler = view.findViewById(R.id.leftsidecategory);
        rightcategoryrecycler = view.findViewById(R.id.rightsidecategory);
        rightsidebannercategory = view.findViewById(R.id.rightsidebannercategory);
        newarrivalhomerecycler = view.findViewById(R.id.new_arrival_recycler);
        toolbar1 = view.findViewById(R.id.toolbar);
        brandsclick = view.findViewById(R.id.categorybrands);
        searchImage = toolbar1.findViewById(R.id.serachbar);
        wishlist = toolbar1.findViewById(R.id.wishList);
        categorywishListBadgeCount = toolbar1.findViewById(R.id.categorywishListBadgeCount);
        brandtext = view.findViewById(R.id.topcatname);
        checkImage = view.findViewById(R.id.bottomline);
        categorycontainer = view.findViewById(R.id.categoryContainer);
        newarrivallayout = view.findViewById(R.id.new_arrival_home_recycler);
        toolBarTitle = view.findViewById(R.id.toolbar_title);
        toolBarCategory = view.findViewById(R.id.toolbar_category);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());

        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment prFrag = new SearchResultFragment();
                Bundle databund = new Bundle();


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.categoryContainer, prFrag)
                        .commit();


            }
        });

        if (Locale.equals("ar")) {
            brandtext.setText("الماركات ");
        } else {

            brandtext.setText("Brands");

        }
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new UpgradeWishListFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.categoryContainer, fragment);
                ft.commit();


            }
        });
        brandsclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightcatname.clear();
                rightbannercatname.clear();
                //rightcatbannername.clear();
                brandtext.setTextColor(Color.parseColor("#D8A664"));
                checkImage.setImageResource(R.drawable.ic_selectcategorybackground);
                isbrandselected = true;
                initLeftTabsRecycler(leftcatname, 0);


                for (int j = 0; j < newalldata.get(toplistposition).brands.size(); j++) {

                    rightcatname.add(newalldata.get(toplistposition).brands.get(j).banners);

                    rightcatbannername.add(newalldata.get(toplistposition).brands.get(j).name);


                }

                rightbannercatname.add(rightcatname.get(0));
                initRightTabsBannersRecycler(rightbannercatname, 0);

                initRightTabsRecyclerforBrands(rightcatname, rightcatbannername, toplistposition);

            }
        });

    }


    public void initTopTabsRecycler(ArrayList<String> alphabetic) {

        topcategoryrecycler.setHasFixedSize(true);
        topcategoryrecycler.setNestedScrollingEnabled(false);

        GridLayoutManager lm;
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            lm = new GridLayoutManager(view.getContext(), 5);

        }
        // LinearLayoutManager categoryGridLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        else {
            lm = new GridLayoutManager(view.getContext(), 5) {
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
        madapter = new TopTabsCategoryAdapter(getContext(), catselctionposition, alphabetic, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();

                leftnavigatingposition = 0;
                MySharedPreferenceClass.setsubCatId(getApplicationContext(), " ");
                switch (id) {

                    case R.id.parentLayout://button for message
                        if (position != 0) {
                            leftcatname.clear();
                            rightcatname.clear();
                            rightbannercatname.clear();
                            rightcatbannername.clear();
                            isbrandselected = false;
                            newarrivallayout.setVisibility(View.GONE);
                            checkImage.setImageResource(R.drawable.ic_greycheck);
                            brandtext.setTextColor(Color.parseColor("#222B45"));
                            if (Locale.equals("en")) {
                                leftcatname.add("Offers");
                            } else {
                                leftcatname.add("عروض");
                            }

                            toplistposition = position;

                            for (int j = 0; j < newalldata.get(position - 1).subcategory.size(); j++) {
                                leftcatname.add(newalldata.get(position - 1).subcategory.get(j).name);
                            }
                            if (Locale.equals("ar")) {
                                leftcatname.add("الماركات ");
                            } else {
                                leftcatname.add("Brands");
                            }
                            initLeftTabsRecycler(leftcatname, position - 1);
                            Log.e("top position", position + " ");
                            toplistmainposition = position - 1;
                            toolBarCategory.setVisibility(View.VISIBLE);
                            toolBarTitle.setText(Locale == "ar" ? "عروض - " : "Offers - ");
                            toolBarCategory.setText(newalldata.get(toplistmainposition).name);
                            for (int j = 0; j < newalldata.get(position - 1).promotionals.size(); j++) {
                                rightbannercatname.add(newalldata.get(position - 1).promotionals.get(j).image);
                            }
                            if (newalldata.get(position - 1).promotionals.size() < 3) {
                                gridViewVisible = false;
                                rightcategoryrecycler.setVisibility(View.GONE);
                            } else {
                                gridViewVisible = true;
                                rightcategoryrecycler.setVisibility(View.VISIBLE);
                            }

                            initRightTabsBannersRecycler(rightbannercatname, 0);
                            initRightTabsRecycler(rightcatname, rightcatbannername, position - 1);
                        } else {
                            //getNewArrivalData();
                            toolBarCategory.setVisibility(View.GONE);
                            toolBarTitle.setText(Locale == "ar" ? "جميع ما وصل حديثا" : "All New Arrivals");
                            initnewArrivalRecycler();
                            newarrivallayout.setVisibility(View.VISIBLE);



                        }

                        break;
                }
            }
        }


        );
        topcategoryrecycler.setAdapter(madapter);
        topcategoryrecycler.setNestedScrollingEnabled(false);
    }

    public void getNewArrivalData() {
        showProgressDialog();

        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
            Locale = "ar";
        } else {
            Locale = "en";
        }
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<GetNewArrivalHomeData> call = Locale == "ar" ? apiService.getNewArrivalData(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "getallnewarrivals/category") : apiService.getNewArrivalData(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "getallnewarrivals/category");
        call.enqueue(new Callback<GetNewArrivalHomeData>() {
            @Override
            public void onResponse(Call<GetNewArrivalHomeData> call, Response<GetNewArrivalHomeData> response) {
                if (response.body() != null) {
                    hideProgressDialog();
                    if (response.body().response.equals("success")) {
                        if (response.body().data.size() > 0) {
                            newarrivalItem = response.body().data;
                            if (MySharedPreferenceClass.getCatId(getApplicationContext()).equals(newArrivalId)) {
                                newarrivallayout.setVisibility(View.VISIBLE);
                                initnewArrivalRecycler();
                            } else {
                                newarrivallayout.setVisibility(View.GONE);
                            }

                            getNewUpgradedCatagoryDetail(view);

                        }
                    } else {
                        Log.e("get wishList", "failed");
                    }
                }
            }

            @Override
            public void onFailure(Call<GetNewArrivalHomeData> call, Throwable t) {
                hideProgressDialog();
                Log.e("fail", t.getMessage());
            }
        });


    }

    public void initnewArrivalRecycler() {

        newarrivalAdapter = new NewArrivalHomeAdapter(getContext(), newarrivalItem, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                Fragment productList = new ProductList();
                FragmentTransaction mFragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.categoryContainer, productList).addToBackStack(null).commit();
                Bundle databund = new Bundle();
                //put your ArrayList data in bundle
                databund.putString("previouspage","FromCategoryScreen");
                databund.putInt("productId", Integer.parseInt(newarrivalItem.get(position).id));
                databund.putString("name", newarrivalItem.get(position).name);
                //databund.putSerializable("childeren", (Serializable) editorChoiceList);
                databund.putString("fromwhere", "fromhome");
                databund.putString("onClick", "editorChoice");
                productList.setArguments(databund);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        newarrivalhomerecycler.setLayoutManager(linearLayoutManager);
        newarrivalhomerecycler.setItemAnimator(new DefaultItemAnimator());
        newarrivalhomerecycler.setAdapter(newarrivalAdapter);


    }

    public void initLeftTabsRecycler(ArrayList<String> alphabetic, int mainlistposition) {

        leftlistmainposition = 0;
        leftcategoryrecycler.setHasFixedSize(true);
        leftcategoryrecycler.setNestedScrollingEnabled(false);
        leftcategoryrecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));


        leftcategoryrecycler.setItemAnimator(new DefaultItemAnimator());
        // use a linear layout manager


        // specify an adapter (see also next example)
        madapter2 = new LeftCategoryAdapter(getContext(), alphabetic, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();
                isbrandselected = false;
                checkImage.setImageResource(R.drawable.ic_greycheck);
                brandtext.setTextColor(Color.parseColor("#222B45"));
                leftlistmainposition = position;

                switch (id) {

                    case R.id.parentLayout://button for message

                        if (position != 0 && position != alphabetic.size() - 1) {
                            leftlistposition = position - 1;

                            rightcatname.clear();
                            rightbannercatname.clear();
                            rightcatbannername.clear();

                            if (newalldata.get(mainlistposition).subcategory.get(position - 1).banners != null) {
                                if (newalldata.get(mainlistposition).subcategory.get(position - 1).banners.size() < 3) {
                                    gridViewVisible = false;
                                    rightcategoryrecycler.setVisibility(View.GONE);
                                } else {
                                    gridViewVisible = true;
                                    rightcategoryrecycler.setVisibility(View.VISIBLE);
                                }
                                for (int j = 1; j < newalldata.get(mainlistposition).subcategory.get(position - 1).banners.size(); j++) {


                                    rightcatname.add(newalldata.get(mainlistposition).subcategory.get(position - 1).banners.get(j).image);
                                    rightcatbannername.add(newalldata.get(mainlistposition).subcategory.get(position - 1).banners.get(j).caption);
                                }

                                initRightTabsRecycler(rightcatname, rightcatbannername, mainlistposition);

                                rightbannercatname.add(newalldata.get(mainlistposition).subcategory.get(position - 1).banners.get(0).image);

                                initRightTabsBannersRecycler(rightbannercatname, mainlistposition);


                                toolBarCategory.setVisibility(View.VISIBLE);
                                toolBarTitle.setText(newalldata.get(mainlistposition).subcategory.get(position - 1).name + " - ");
                                toolBarCategory.setText(newalldata.get(mainlistposition).name);
                            } else {
                                Fragment prFrag = new ProductList();
                                Bundle databund = new Bundle();
                                databund.putString("previouspage","FromCategoryScreen");
                                databund.putInt("productId", Integer.valueOf(newalldata.get(mainlistposition).subcategory.get(position - 1).id));
                                databund.putString("name", newalldata.get(mainlistposition).subcategory.get(position - 1).name); /*alphabetic.get(position)*/
                                databund.putString("category", newalldata.get(mainlistposition).name); /*alphabetic.get(position)*/
                                databund.putSerializable("childeren", (Serializable) newalldata.get(mainlistposition).subcategory);
                                databund.putString("fromwhere", "fromcatagory");
                                databund.putString("adapterposition", String.valueOf(position));


                                prFrag.setArguments(databund);


                                getFragmentManager()
                                        .beginTransaction().addToBackStack(null)
                                        .add(R.id.categoryContainer, prFrag)
                                        .commit();


                            }


                        } else if (position == alphabetic.size() - 1) {
                            toolBarCategory.setVisibility(View.VISIBLE);
                            toolBarTitle.setText(Locale == "ar" ? "الماركات - " : "Brands - ");
                            toolBarCategory.setText(newalldata.get(mainlistposition).name);

                            rightcatname.clear();
                            rightbannercatname.clear();
                            rightcatbannername.clear();
                            brandtext.setTextColor(Color.parseColor("#D8A664"));
                            checkImage.setImageResource(R.drawable.ic_selectcategorybackground);
                            isbrandselected = true;

                            if (newalldata.get(toplistposition - 1).brands != null) {
                                for (int j = 0; j < newalldata.get(toplistposition - 1).brands.size(); j++) {

                                    rightcatname.add(newalldata.get(toplistposition - 1).brands.get(j).banners);

                                    rightcatbannername.add(newalldata.get(toplistposition - 1).brands.get(j).name);


                                }
                            }
                            for (int j = 0; j < newalldata.get(toplistposition - 1).brands_banner.size(); j++) {


                                rightbannercatname.add(newalldata.get(toplistposition - 1).brands_banner.get(j).image);


                            }

                            initRightTabsBannersRecycler(rightbannercatname, mainlistposition);
                            initRightTabsRecyclerforBrands(rightcatname, rightcatbannername, toplistposition);


                        } else {
                            toolBarCategory.setVisibility(View.VISIBLE);
                            toolBarTitle.setText(Locale == "ar" ? "عروض - " : "Offers - ");
                            toolBarCategory.setText(newalldata.get(mainlistposition).name);
                            rightcatname.clear();
                            rightbannercatname.clear();
                            rightcatbannername.clear();


                            for (int j = 0; j < newalldata.get(mainlistposition).promotionals.size(); j++) {

                                rightbannercatname.add(newalldata.get(mainlistposition).promotionals.get(j).image);


                            }
                            if (newalldata.get(mainlistposition).promotionals.size() < 3) {
                                gridViewVisible = false;
                                rightcategoryrecycler.setVisibility(View.GONE);
                            } else {
                                gridViewVisible = true;
                                rightcategoryrecycler.setVisibility(View.VISIBLE);
                            }
                            Log.e("fromlefttan", mainlistposition + " ");

                            initRightTabsBannersRecycler(rightbannercatname, 0);
                            initRightTabsRecycler(rightcatname, rightcatbannername, position);
                        }
                        break;
                }
            }
        }, leftnavigatingposition


        );
        leftcategoryrecycler.setAdapter(madapter2);
        leftcategoryrecycler.setNestedScrollingEnabled(false);
    }


    public void initRightTabsBannersRecycler(ArrayList<String> alphabetic, int mainlistposition) {

        rightsidebannercategory.setHasFixedSize(true);
        rightsidebannercategory.setNestedScrollingEnabled(false);


        rightsidebannercategory.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));


        rightsidebannercategory.setItemAnimator(new DefaultItemAnimator());
        // use a linear layout manager


        // specify an adapter (see also next example)
        madapter4 = new RightCategoryBannerAdapter(getContext(), alphabetic, isbrandselected, gridViewVisible, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();
                Log.e("leftlistposition", leftlistposition + " ");
                Log.e("mainlistposition", mainlistposition + " ");
                Log.e("toplismainposition", toplistmainposition + " ");

                switch (id) {

                    case R.id.parentLayout://button for message
                        if (!isbrandselected) {
                            if (leftlistmainposition == 0) {
                                Log.e("firebase event",
                                        newalldata.get(mainlistposition).name + "-" +
                                                newalldata.get(toplistmainposition).promotionals.get(position).caption + " ," +
                                                newalldata.get(toplistmainposition).promotionals.get(position).id);



                                firebase_click_banner(newalldata.get(toplistmainposition).name + "-" + newalldata.get(toplistmainposition).promotionals.get(position).caption,

                                        newalldata.get(toplistmainposition).promotionals.get(position).id);


                                Fragment prFrag = new ProductList();
                                Bundle databund = new Bundle();
                                databund.putString("previouspage","FromCategoryScreen");
                                //databund.putInt("productId", Integer.valueOf(newalldata.get(toplistmainposition).id));
                                databund.putInt("productId", Integer.valueOf(newalldata.get(toplistmainposition).promotionals.get(position).attribute.value));
                                databund.putString("name", newalldata.get(toplistmainposition).promotionals.get(position).caption);
                                databund.putString("category", newalldata.get(toplistmainposition).name);
                                databund.putString("fromwhere", "frompromtionalbanners");
                                //databund.putString("fromwhere", "fromcatagorybanners");
                                databund.putString("adapterposition", String.valueOf(leftlistposition));
                                databund.putString("attributvalue", newalldata.get(toplistmainposition).promotionals.get(position).attribute.value);
                                databund.putString("attributdisplay", newalldata.get(toplistmainposition).promotionals.get(position).attribute.display);
                                databund.putString("attributcount", newalldata.get(toplistmainposition).promotionals.get(position).attribute.count.toString());


                                prFrag.setArguments(databund);


                                getFragmentManager()
                                        .beginTransaction().addToBackStack(null)
                                        .add(R.id.categoryContainer, prFrag)
                                        .commit();

                            } else {
                                Log.e("firebas event",
                                        newalldata.get(mainlistposition).name + "-" +
                                                newalldata.get(mainlistposition).subcategory.get(leftlistposition).banners.get(position).caption + " ," +
                                                newalldata.get(toplistmainposition).subcategory.get(leftlistposition).banners.get(position).id);

                                firebase_click_banner(newalldata.get(mainlistposition).name + "-" + newalldata.get(mainlistposition).subcategory.get(leftlistposition).banners.get(position).caption,
                                        newalldata.get(toplistmainposition).subcategory.get(leftlistposition).banners.get(position).id);




                                Fragment prFrag = new ProductList();
                                Bundle databund = new Bundle();
                                databund.putString("previouspage","FromCategoryScreen");
                                // databund.putInt("productId", Integer.valueOf(newalldata.get(toplistmainposition).id));
                                databund.putInt("productId", Integer.valueOf(newalldata.get(toplistmainposition).subcategory.get(leftlistposition).banners.get(position).id));
                                databund.putString("name", newalldata.get(mainlistposition).subcategory.get(leftlistposition).banners.get(position).caption);
                                databund.putString("category", newalldata.get(mainlistposition).name);
                                databund.putString("fromwhere", "fromcatagorybanners");
                                databund.putString("adapterposition", String.valueOf(leftlistposition));
                                databund.putString("attributvalue", newalldata.get(toplistmainposition).subcategory.get(leftlistposition).banners.get(position).attribute.value);
                                databund.putString("attributdisplay", newalldata.get(toplistmainposition).subcategory.get(leftlistposition).banners.get(position).attribute.display);
                                databund.putString("attributcount", newalldata.get(toplistmainposition).subcategory.get(leftlistposition).banners.get(position).attribute.count.toString());


                                prFrag.setArguments(databund);


                                getFragmentManager()
                                        .beginTransaction().addToBackStack(null)
                                        .add(R.id.categoryContainer, prFrag)
                                        .commit();
                            }


                        } else {
                            Log.e("firebas event",
                                    newalldata.get(mainlistposition).name + "-" +
                                            newalldata.get(mainlistposition).brands_banner.get(position).caption + " ," +
                                            newalldata.get(mainlistposition).brands_banner.get(position).id);

                            firebase_click_banner(newalldata.get(mainlistposition).name + "-" + newalldata.get(mainlistposition).brands_banner.get(position).caption,
                                    newalldata.get(mainlistposition).brands_banner.get(position).id);


                            leftlistposition = position;
                            Fragment prFrag = new ProductList();
                            Bundle databund = new Bundle();
                            databund.putString("previouspage","FromCategoryScreen");
                            databund.putInt("productId", Integer.valueOf(newalldata.get(mainlistposition).id));
                            databund.putString("name", newalldata.get(mainlistposition).brands_banner.get(position).attribute.display);
                            databund.putString("category", newalldata.get(mainlistposition).name);
                            //databund.putSerializable("childeren", (Serializable) newalldata.get(mainlistposition).subcategory);

                            // databund.putString("fromwhere", "fromcatagorybrands");
                            databund.putString("fromwhere", "fromcatagorybanners");
                            databund.putString("adapterposition", String.valueOf(leftlistposition));
                            databund.putString("attributvalue", newalldata.get(mainlistposition).brands_banner.get(leftlistposition).attribute.value);
                            databund.putString("attributdisplay", newalldata.get(mainlistposition).brands_banner.get(leftlistposition).attribute.display);
                            databund.putString("attributcount", newalldata.get(mainlistposition).brands_banner.get(leftlistposition).attribute.count.toString());


                            prFrag.setArguments(databund);


                            getFragmentManager()
                                    .beginTransaction().addToBackStack(null)
                                    .add(R.id.categoryContainer, prFrag)
                                    .commit();
                        }
                        break;


                }
            }
        }


        );
        rightsidebannercategory.setAdapter(madapter4);
        rightsidebannercategory.setNestedScrollingEnabled(false);
    }

    public void initRightTabsRecyclerforBrands(ArrayList<String> alphabetic, ArrayList<String> bannername, int mainlistposition) {
        rightcategoryrecycler.setHasFixedSize(true);
        rightcategoryrecycler.setNestedScrollingEnabled(false);
        rightcategoryrecycler.setVisibility(View.VISIBLE); //This code line has been not live


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false) {
            @Override
            protected boolean isLayoutRTL() {
                if (Locale.equalsIgnoreCase("ar")) {
                    return true;
                } else {
                    return false;
                }

            }
        };
        // rightcategoryrecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        rightcategoryrecycler.setLayoutManager(gridLayoutManager);
        rightcategoryrecycler.setItemAnimator(new DefaultItemAnimator());
        // use a linear layout manager


        // specify an adapter (see also next example)
        madapterforbrands = new RightCategoryAdapterforBrands(getContext(), alphabetic, bannername, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();


                switch (id) {
                    case R.id.parentLayout://button for message
                        if (!isbrandselected) {


                            Log.e("firebas event",
                                    newalldata.get(mainlistposition).name + "-" +
                                            newalldata.get(mainlistposition).subcategory.get(leftlistposition).banners.get(position).caption + " ," +
                                            newalldata.get(mainlistposition).subcategory.get(leftlistposition).banners.get(position).id);


                            firebase_click_banner(newalldata.get(mainlistposition).name + "-" + newalldata.get(mainlistposition).subcategory.get(leftlistposition).banners.get(position).caption,
                                    newalldata.get(mainlistposition).subcategory.get(leftlistposition).banners.get(position).id);


                            Fragment prFrag = new ProductList();
                            Bundle databund = new Bundle();
                            databund.putString("previouspage","FromCategoryScreen");
                            // databund.putInt("productId", Integer.valueOf(newalldata.get(mainlistposition).subcategory.get(leftlistposition).banners.get(position).id));
                            databund.putInt("productId", Integer.valueOf(newalldata.get(mainlistposition).id));

                            databund.putString("name", newalldata.get(mainlistposition).subcategory.get(leftlistposition).banners.get(position).name);
                            databund.putString("category", newalldata.get(mainlistposition).name);
                            //databund.putSerializable("childeren", (Serializable) newalldata.get(mainlistposition).subcategory);

                            //databund.putString("fromwhere", "fromcatagory");
                            databund.putString("fromwhere", "fromsubcategories");
                            databund.putString("adapterposition", String.valueOf(leftlistposition));
                            databund.putString("attributvalue", newalldata.get(mainlistposition).subcategory.get(leftlistposition).banners.get(position).attribute.value);
                            databund.putString("attributdisplay", newalldata.get(mainlistposition).subcategory.get(leftlistposition).banners.get(position).attribute.display);
                            databund.putString("attributcount", newalldata.get(mainlistposition).subcategory.get(leftlistposition).banners.get(position).attribute.count.toString());


                            prFrag.setArguments(databund);


                            getFragmentManager()
                                    .beginTransaction().addToBackStack(null)
                                    .add(R.id.categoryContainer, prFrag)
                                    .commit();
                        } else {
                            Log.e("firebase event",
                                    newalldata.get(mainlistposition - 1).name + "-" +
                                            newalldata.get(mainlistposition - 1).brands.get(position).attribute.display + " ," +
                                            newalldata.get(mainlistposition - 1).brands.get(position).attribute.value);

                            firebase_click_banner(newalldata.get(mainlistposition - 1).name + "-" +  newalldata.get(mainlistposition - 1).brands.get(position).attribute.display,
                                    newalldata.get(mainlistposition - 1).brands.get(position).attribute.value);

                            Fragment prFrag = new ProductList();
                            Bundle databund = new Bundle();
                            databund.putString("previouspage","FromCategoryScreen");
                            databund.putInt("productId", Integer.valueOf(newalldata.get(mainlistposition - 1).id));
                            databund.putString("name", newalldata.get(mainlistposition - 1).brands.get(position).name);
                            databund.putString("category", newalldata.get(mainlistposition - 1).name);
                            //databund.putSerializable("childeren", (Serializable) newalldata.get(mainlistposition).subcategory);

                            databund.putString("fromwhere", "fromcatagorybrands");
                            databund.putString("adapterposition", String.valueOf(leftlistposition));
                            databund.putString("attributvalue", newalldata.get(mainlistposition - 1).brands.get(position).attribute.value);
                            databund.putString("attributdisplay", newalldata.get(mainlistposition - 1).brands.get(position).attribute.display);
                            databund.putString("attributcount", newalldata.get(mainlistposition - 1).brands.get(position).attribute.count);


                            prFrag.setArguments(databund);


                            getFragmentManager()
                                    .beginTransaction().addToBackStack(null)
                                    .add(R.id.categoryContainer, prFrag)
                                    .commit();


                        }

                        break;


                }
            }
        }


        );
        rightcategoryrecycler.setAdapter(madapterforbrands);
        rightcategoryrecycler.setNestedScrollingEnabled(false);
    }

    public void initRightTabsRecycler(ArrayList<String> alphabetic, ArrayList<String> bannername, int mainlistposition) {

        rightcategoryrecycler.setHasFixedSize(true);
        rightcategoryrecycler.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager;
        if (bannername.size() == 1) {
            gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false) {
                @Override
                protected boolean isLayoutRTL() {
                    if (Locale.equalsIgnoreCase("ar")) {
                        return true;
                    } else {
                        return false;
                    }

                }
            };
        } else {
            gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false) {
                @Override
                protected boolean isLayoutRTL() {
                    if (Locale.equalsIgnoreCase("ar")) {
                        return true;
                    } else {
                        return false;
                    }

                }
            };

        }
        // rightcategoryrecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        rightcategoryrecycler.setLayoutManager(gridLayoutManager);
        rightcategoryrecycler.setItemAnimator(new DefaultItemAnimator());

        // use a linear layout manager


        // specify an adapter (see also next example)
        madapter3 = new RightCategoryAdapter(getContext(), alphabetic, bannername, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();


                switch (id) {
                    case R.id.parentLayout://button for message
                        if (!isbrandselected) {
                            Log.e("firebase event",
                                    newalldata.get(mainlistposition).name + "-" +
                                            newalldata.get(toplistmainposition).subcategory.get(leftlistmainposition - 1).banners.get(position + 1).caption + " ," +
                                            newalldata.get(toplistmainposition).subcategory.get(leftlistmainposition - 1).banners.get(position + 1).id);

                            firebase_click_banner(newalldata.get(mainlistposition).name + "-" +  newalldata.get(toplistmainposition).subcategory.get(leftlistmainposition - 1).banners.get(position + 1).caption,
                                    newalldata.get(toplistmainposition).subcategory.get(leftlistmainposition - 1).banners.get(position + 1).id);

                            Log.e("toplistmainposition", toplistmainposition + " ");
                            Log.e("toplistmainposition", leftlistmainposition + " ");
                            Log.e("toplistmainposition", position + " ");

                            Fragment prFrag = new ProductList();
                            Bundle databund = new Bundle();
                            databund.putString("previouspage","FromCategoryScreen");
                            //databund.putInt("productId", Integer.valueOf(newalldata.get(toplistmainposition).id));
                            databund.putInt("productId", Integer.valueOf(newalldata.get(toplistmainposition).subcategory.get(leftlistmainposition - 1).id));
                            databund.putString("name", newalldata.get(toplistmainposition).subcategory.get(leftlistmainposition - 1).banners.get(position + 1).caption);
                            databund.putString("category", newalldata.get(toplistmainposition).name);

                            databund.putString("fromwhere", "fromsubcategories");
                            databund.putString("adapterposition", String.valueOf(leftlistmainposition - 1));
                            databund.putString("attributvalue", newalldata.get(toplistmainposition).subcategory.get(leftlistmainposition - 1).banners.get(position + 1).attribute.value);
                            databund.putString("attributdisplay", newalldata.get(toplistmainposition).subcategory.get(leftlistmainposition - 1).banners.get(position + 1).attribute.display);
                            databund.putString("attributcount", newalldata.get(toplistmainposition).subcategory.get(leftlistmainposition - 1).banners.get(position + 1).attribute.count.toString());


                            prFrag.setArguments(databund);


                            getFragmentManager()
                                    .beginTransaction().addToBackStack(null)
                                    .add(R.id.categoryContainer, prFrag)
                                    .commit();
                        } else {
                            Log.e("firebase event",
                                    newalldata.get(mainlistposition).name + "-" +
                                            newalldata.get(mainlistposition).brands.get(position).attribute.display+ " ," +
                                            newalldata.get(mainlistposition).brands.get(position).attribute.value);

                            firebase_click_banner(newalldata.get(mainlistposition).name + "-" +  newalldata.get(mainlistposition).brands.get(position).attribute.display,
                                    newalldata.get(mainlistposition).brands.get(position).attribute.value);

                            Fragment prFrag = new ProductList();
                            Bundle databund = new Bundle();

                            databund.putInt("productId", Integer.valueOf(newalldata.get(mainlistposition).id));
                            databund.putString("name", newalldata.get(mainlistposition).brands.get(position).name);
                            databund.putString("category", newalldata.get(mainlistposition).name);
                            //databund.putSerializable("childeren", (Serializable) newalldata.get(mainlistposition).subcategory);

                            databund.putString("previouspage","FromCategoryScreen");
                            databund.putString("fromwhere", "fromcatagorybrands");
                            databund.putString("adapterposition", String.valueOf(leftlistposition));
                            databund.putString("attributvalue", newalldata.get(mainlistposition).brands.get(position).attribute.value);
                            databund.putString("attributdisplay", newalldata.get(mainlistposition).brands.get(position).attribute.display);
                            databund.putString("attributcount", newalldata.get(mainlistposition).brands.get(position).attribute.count);


                            prFrag.setArguments(databund);


                            getFragmentManager()
                                    .beginTransaction().addToBackStack(null)
                                    .add(R.id.categoryContainer, prFrag)
                                    .commit();


                        }

                        break;


                }
            }
        }


        );
        rightcategoryrecycler.setAdapter(madapter3);
        rightcategoryrecycler.setNestedScrollingEnabled(false);
    }


    public void getNewUpgradedCatagoryDetail(final View v) {

        showProgressDialog();

        alldata.clear();
        leftcatname.clear();
        rightcatname.clear();
        topcatname.clear();
        newalldata.clear();
        rightbannercatname.clear();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Call<UpgradeCatgoryresponse> call;

        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {

            call = apiService.getNewupgradedcatagoryDetailEnglish();
        } else {

            call = apiService.getNewupgradedcatagoryDetailArabic();
        }


        call.enqueue(new Callback<UpgradeCatgoryresponse>() {
            @Override
            public void onResponse(Call<UpgradeCatgoryresponse> call, Response<UpgradeCatgoryresponse> response) {


                if (response.body() != null && response.isSuccessful()) {
                    categorycontainer.setVisibility(View.VISIBLE);
                    hideProgressDialog();
                    newalldata = response.body().data;


                    if (response.body().data.size() > 0) {
                        topcatname.add(Locale.equalsIgnoreCase("en") ? "All New Arrivals" : "جميع ما وصل حديثا");
                        // topcatId.add(Integer.valueOf(newArrivalId));
                        for (int j = 0; j < response.body().data.size(); j++) {

                            topcatname.add(response.body().data.get(j).name);
                            topcatId.add(Integer.parseInt(response.body().data.get(j).id));

                        }


                        for (int i = 0; i < topcatId.size(); i++) {
                            Log.e("id=", topcatId.get(i).toString());
                            Log.e("userselectedid=", MySharedPreferenceClass.getCatId(getApplicationContext()));
                            if (Integer.parseInt(MySharedPreferenceClass.getCatId(getApplicationContext())) == (topcatId.get(i))) {
                                catselctionposition = i + 1;
                                toplistposition = catselctionposition;
                                toplistmainposition = i;
                                break;


                            }

                        }


                        for (int i = 0; i < response.body().data.get(catselctionposition - 1).subcategory.size(); i++) {
                            Log.e("subid=", response.body().data.get(catselctionposition - 1).subcategory.get(i).id);
                            if (!MySharedPreferenceClass.getsubCatId(getApplicationContext()).equalsIgnoreCase(" ") && Integer.parseInt(MySharedPreferenceClass.getsubCatId(getApplicationContext())) == Integer.parseInt(response.body().data.get(catselctionposition - 1).subcategory.get(i).id)) {

                                Log.e("selsubid=", response.body().data.get(catselctionposition - 1).subcategory.get(i).id.toString());

                                leftnavigatingposition = i + 1;
                                Log.e("selsubid position=", String.valueOf(i));

                                break;


                            }

                        }

                        if (MySharedPreferenceClass.getsubCatId(getApplicationContext()).equalsIgnoreCase(" ")) {


                            leftnavigatingposition = 0;
                            Log.e("selsubpromotional position=", String.valueOf(leftnavigatingposition));


                        }
                      /*else if(leftnavigatingposition == 0)
                        {
                            leftnavigatingposition = response.body().data.
                                    get(catselctionposition - 1).subcategory.size()+1;

                            Log.e("leftnavigatingbrandposition=", String.valueOf(leftnavigatingposition));
                        }*/


                        leftcatname.clear();
                        if (Locale.equals("en")) {
                            leftcatname.add("Offers");
                        } else {
                            leftcatname.add("عروض");
                        }
                        Log.e("catselectiopos", String.valueOf(catselctionposition));

                        for (int j = 0; j < response.body().data.get(catselctionposition - 1).subcategory.size(); j++) {

                            leftcatname.add(response.body().data.get(catselctionposition - 1).subcategory.get(j).name);


                        }


                        if (Locale.equals("ar")) {
                            leftcatname.add("الماركات ");
                        } else {

                            leftcatname.add("Brands");

                        }
                        toolBarCategory.setVisibility(View.VISIBLE);
                        toolBarTitle.setText(Locale == "ar" ? "عروض - " : "Offers - ");
                        toolBarCategory.setText(newalldata.get(catselctionposition - 1).name);
                        for (int j = 0; j < response.body().data.get(catselctionposition - 1).promotionals.size(); j++) {

                            rightbannercatname.add(response.body().data.get(catselctionposition - 1).promotionals.get(j).image);

                        }
                        if (newalldata.get(catselctionposition - 1).promotionals.size() < 3) {
                            gridViewVisible = false;
                            rightcategoryrecycler.setVisibility(View.GONE);
                        } else {
                            gridViewVisible = true;
                            rightcategoryrecycler.setVisibility(View.VISIBLE);
                        }
                        if (MySharedPreferenceClass.getCatId(getApplicationContext()).equals(newArrivalId)) {

                            catselctionposition = 0;

                        }
                        initTopTabsRecycler(topcatname);
                        initLeftTabsRecycler(leftcatname, catselctionposition - 1);
                        initRightTabsBannersRecycler(rightbannercatname, catselctionposition - 1);


                        for (int i = 0; i < response.body().data.size(); i++) {

                        }


                    } else {
                        hideProgressDialog();


                    }
                    hideProgressDialog();
                } else {
                    hideProgressDialog();

                }
            }

            @Override
            public void onFailure(Call<UpgradeCatgoryresponse> call, Throwable t) {
                hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        Log.e("from visibility", "inside visible" + visible);


        if (MainActivity.isaddressempty == true) {
            MainActivity.isaddressempty = false;

        }

       /* if(MySharedPreferenceClass.getDeeplinkingNotification(getApplicationContext())!=null && MySharedPreferenceClass.getDeeplinkingNotification(getApplicationContext()).equalsIgnoreCase("true")&& !_hasLoadedOnce){
            getUpgradedLatestversionCode();
            Log.e("from visibility","inside shared"+visible);
            _hasLoadedOnce = true;
        }*/

        if (visible /*&& isResumed()*/ && !_hasLoadedOnce) {
            getUpgradedLatestversionCode();

            //getNewUpgradedCatagoryDetail(view);
            Log.e("from visibility", "inside visible22" + visible);
            _hasLoadedOnce = true;

        } else if (visible) {
            for (int i = 0; i < topcatId.size(); i++) {
                Log.e("id from bottom=", topcatId.get(i).toString());
                if (Integer.parseInt(MySharedPreferenceClass.getCatId(getApplicationContext())) == (topcatId.get(i))) {
                    newarrivallayout.setVisibility(View.GONE);
                    catselctionposition = i + 1;
                    toplistposition = catselctionposition;
                    toplistmainposition = i;
                    break;


                }

            }
            for (int i = 0; i < newalldata.get(catselctionposition - 1).subcategory.size(); i++) {
                Log.e("subid frm bottom=", newalldata.get(catselctionposition - 1).subcategory.get(i).id);
                if (!MySharedPreferenceClass.getsubCatId(getApplicationContext()).equalsIgnoreCase(" ") && Integer.parseInt(MySharedPreferenceClass.getsubCatId(getApplicationContext())) == Integer.parseInt(newalldata.get(catselctionposition - 1).subcategory.get(i).id)) {

                    Log.e("selsubid=", newalldata.get(catselctionposition - 1).subcategory.get(i).id.toString());

                    leftnavigatingposition = i + 1;
                    Log.e("selsubid positionfrom bottom=", String.valueOf(i));

                    break;


                }

            }

            if (MySharedPreferenceClass.getsubCatId(getApplicationContext()).equalsIgnoreCase(" ")) {


                leftnavigatingposition = 0;
                Log.e("selsubpromotional position=", String.valueOf(leftnavigatingposition));


            }
       /*  else if(leftnavigatingposition == 0 &&  MySharedPreferenceClass.getsubCatId(getApplicationContext()).equals(" "))
            {
                leftnavigatingposition = newalldata.get(catselctionposition - 1).subcategory.size()+1;

                Log.e("leftnavigatingbrandposition=", String.valueOf(leftnavigatingposition));
            }*/


            leftcatname.clear();

            if (Locale.equals("en")) {
                leftcatname.add("Offers");
            } else {
                leftcatname.add("عروض");
            }

            for (int j = 0; j < newalldata.get(catselctionposition - 1).subcategory.size(); j++) {

                leftcatname.add(newalldata.get(catselctionposition - 1).subcategory.get(j).name);

            }


            if (Locale.equals("ar")) {
                leftcatname.add("الماركات ");
            } else {

                leftcatname.add("Brands");

            }
            rightcatname.clear();
            rightbannercatname.clear();
            for (int j = 0; j < newalldata.get(catselctionposition - 1).promotionals.size(); j++) {

                rightbannercatname.add(newalldata.get(catselctionposition - 1).promotionals.get(j).image);

            }
            if (newalldata.get(catselctionposition - 1).promotionals.size() < 3) {
                gridViewVisible = false;
                rightcategoryrecycler.setVisibility(View.GONE);
            } else {
                gridViewVisible = true;
                rightcategoryrecycler.setVisibility(View.VISIBLE);
            }
            toolBarCategory.setVisibility(View.VISIBLE);
            toolBarTitle.setText(Locale == "ar" ? "عروض - " : "Offers - ");
            toolBarCategory.setText(newalldata.get(catselctionposition - 1).name);
            initTopTabsRecycler(topcatname);
            initLeftTabsRecycler(leftcatname, catselctionposition - 1);
            initRightTabsBannersRecycler(rightbannercatname, catselctionposition - 1);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentDialogListener = (ParentDialogsListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private void getUpgradedLatestversionCode() {
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Call<GetCategoryCode> call = apiService.getUpgradedLatestAppVersion();
        call.enqueue(new Callback<GetCategoryCode>() {
            @Override
            public void onResponse(Call<GetCategoryCode> call, Response<GetCategoryCode> response) {
                if (response.body() != null) {

                    if (response.body().response.equals("success")) {

                        newArrivalId = response.body().newarrivalId;

                        getNewArrivalData();

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

    private void showProgressDialog() {
        progressDialog1 = ProgressDialog.show(getActivity(), "Null", "Null", false, false);
        progressDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog1.setContentView(R.layout.progress_bar);
        progressDialog1.setCanceledOnTouchOutside(false);
    }

    private void hideProgressDialog() {
        try {
            progressDialog1.dismiss();
        } catch (Exception e) {
        }

    }


    public void firebase_click_banner(String promotionname, String promotionid) {
        Bundle bundle = new Bundle();

        bundle.putString(FirebaseAnalytics.Param.PROMOTION_NAME, promotionname);
        bundle.putString(FirebaseAnalytics.Param.PROMOTION_ID, promotionid);
        //bundle.putString(FirebaseAnalytics.Param.ITEM_LOCATION_ID, Locale.getDefault().getCountry());
        mFirebaseAnalytics.logEvent("category_banner_selection", bundle);


    }
}