package com.eoutlet.Eoutlet.fragments;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.activities.SplashActivity;
import com.eoutlet.Eoutlet.adpters.CurrencyListAdapter;
import com.eoutlet.Eoutlet.adpters.EditorChoiceAdapter;
import com.eoutlet.Eoutlet.adpters.FeaturedChoiceAdapter;
import com.eoutlet.Eoutlet.adpters.HomeAdapter1;
import com.eoutlet.Eoutlet.adpters.HomeAdapter3;
import com.eoutlet.Eoutlet.adpters.HomeAdapter5;
import com.eoutlet.Eoutlet.adpters.HomeAdapter6;
import com.eoutlet.Eoutlet.adpters.HomeAdapter7;
import com.eoutlet.Eoutlet.adpters.HomeSliderAdapter;
import com.eoutlet.Eoutlet.adpters.HorizantalProductListAdapter;
import com.eoutlet.Eoutlet.adpters.NewArrivalAdapter;
import com.eoutlet.Eoutlet.adpters.NewArrivalHomeAdapter;
import com.eoutlet.Eoutlet.adpters.NewArrivalViewPagerAdapter;
import com.eoutlet.Eoutlet.adpters.TopTabsCategoryAdapter;
import com.eoutlet.Eoutlet.adpters.TrendingAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.ListItems;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.intrface.UpdateBedgeCount;
import com.eoutlet.Eoutlet.intrface.ViewlistenerCurrency;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.CatagoryName;
import com.eoutlet.Eoutlet.pojo.Child;
import com.eoutlet.Eoutlet.pojo.CurrencysetResponse;
import com.eoutlet.Eoutlet.pojo.Datum;
import com.eoutlet.Eoutlet.pojo.Datum2;
import com.eoutlet.Eoutlet.pojo.EditorChoice;
import com.eoutlet.Eoutlet.pojo.ExchangeRate;
import com.eoutlet.Eoutlet.pojo.Featured;
import com.eoutlet.Eoutlet.pojo.GetCategoryCode;
import com.eoutlet.Eoutlet.pojo.GetCountryCode;
import com.eoutlet.Eoutlet.pojo.GetCurrencyDetail;
import com.eoutlet.Eoutlet.pojo.GetNewArrivalHomeData;
import com.eoutlet.Eoutlet.pojo.GetPopUpData;
import com.eoutlet.Eoutlet.pojo.LoginResponse;
import com.eoutlet.Eoutlet.pojo.MainList;
import com.eoutlet.Eoutlet.pojo.NewSingleScreenData;
import com.eoutlet.Eoutlet.pojo.Recommended;
import com.eoutlet.Eoutlet.pojo.Recommendedlist;
import com.eoutlet.Eoutlet.pojo.UpgradeWishListResponse;
import com.eoutlet.Eoutlet.pojo.UpgradedCartItems;
import com.eoutlet.Eoutlet.pojo.UpgradedItem;
import com.eoutlet.Eoutlet.pojo.ViewCart1;
import com.eoutlet.Eoutlet.pojo.countryCodeDetail;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.NewArrival;
import com.eoutlet.Eoutlet.utility.Constants;
import com.eoutlet.Eoutlet.utility.CustomDialogClassforNotification;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.moe.pushlibrary.MoEHelper;
import com.moengage.core.Properties;
import com.moengage.inapp.MoEInAppHelper;
import com.moengage.widgets.NudgeView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;


public class NewHomeFragmentUpgrade extends Fragment implements View.OnClickListener {
    public ParentDialogsListener parentDialogListener;
    private LinearLayout offersLayout, linhomeMainLayout;
    LinearLayout selectLanguage, sliderDotspanel, newArrivalSliderDots, recommendedSliderDots;
    private Context mContext;
    List<Child> childlist = new ArrayList<>();
    String userName;
    List<String> availiblecuurrency;
    private ViewPager viewPager, newArrivalViewPager, recommendedViewPager;
    private RecyclerView topcategoryrecycler;
    private TopTabsCategoryAdapter madapter;
    private TextView toolText;
    private ImageView searchImage;
    public static TextView toolbar_bedgetexthome;
    private ExecuteFragment execute;
    private int dotscount, newarrivaldotscount, recommenddotscount;
    private ImageView[] dots, newarrivaldots, recommenddots;
    private int page = 0;
    private List<ListItems> productListmain = new ArrayList<>();
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private ArrayList<String> sliderImage = new ArrayList<String>();
    private ArrayList<String> sliderId = new ArrayList<String>();
    private ArrayList<String> sliderName = new ArrayList<String>();
    private List<List<Child>> childerenofslider = new ArrayList<>();
    private List<List<Child>> childerenofoffers = new ArrayList<>();
    private ProgressDialog progressDialog1;
    private ArrayList<String> maincatelistname = new ArrayList<>();
    private ArrayList<String> maincatelistId = new ArrayList<>();
    private List<CatagoryName> mainhorizantallist = new ArrayList<>();
    private List<MainList> maincatelist = new ArrayList();
    String boximage, allnewarrivalurl, allnewarrivaid = " ", allnewarrivalname;
    private List<ListItems> new_arrival = new ArrayList<>();
    private List<NewArrival> newarrivalitems = new ArrayList<>();

    private List<EditorChoice> editorchoice = new ArrayList<>();
    NewArrivalViewPagerAdapter newArrivalViewPagerAdapter;
    private List<UpgradedItem> upgradedcartData;

    private List<List<ListItems>> maincatitems = new ArrayList<>();
    private List<Recommendedlist> editorChoiceList = new ArrayList<>();

    private ImageView fetureImage1, featureImage2, featureImage3, boxImage, featureImage7, featureImage8, featureImage9;
    private int viewpagerposition;

    private List<Featured> featured;
    public List<Recommended> recommendedlist = new ArrayList<>();
    TextView wishListBadgeCount;
    private int current_position = 0;
    int currentPage = 0;
    Timer timer;
    int catselctionposition = 1;
    private UpdateBedgeCount updatefix;
    private boolean _hasLoadedOnce = false;
    Boolean isCategoryContainerVisible = false;
    LinearLayout searchCardView, wishListCardView, notificationCardView;
    CardView categorySelectionContainerCardView;
    private ImageView zoomoutImage, kids, childeren, women, men, shoes, accessories, offres, allnewarrival;
    private TextView maincat1, maincat4, maincat3, womencat, mancat, shoescat, accessoriescat;

    private ArrayList<String> bannerslist = new ArrayList<>();
    private ArrayList<String> bannertext = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();

    private ArrayList<String> alphabetic = new ArrayList<>();


    private ArrayList<String> newCatagoryImage = new ArrayList<>();


    private List<List<Child>> childerenofcatagory = new ArrayList<>();
    private RecyclerView newarrivalrecycler, editorChoiceRecyclerView, featuredrecyclerview, newarrivalhomerecycler;

    private FrameLayout serchbarhome;

    private EditorChoiceAdapter eAdapter;
    private NewArrivalHomeAdapter newarrivalAdapter;

    private FeaturedChoiceAdapter fAdapter;
    String Locale;
    private List<Featured> featuredrecyclerlist = new ArrayList<>();
    private List<ExchangeRate> exchangecountrylist = new ArrayList<>();
    private int countryselctposition;
    private RecyclerView currencyrecycler;
    private LinearLayout categorySelectionContainerHome, searchcontainer;
// private ImageView searchBar/*;

    private View toolbarbeg;
    private TextView kidstext, childerentext, womentext, mentext, shoestext, accessoriestext;
    private TextView kidsshop, childerenshop, womenshop, menshop, shoesshop, accessoriesshop;
    private int totalcount;
    private HorizantalProductListAdapter mAdapter2;
    private int[] myImageList = new int[]{R.drawable.pro1, R.drawable.pro4, R.drawable.pro3, R.drawable.pro4, R.drawable.pro5, R.drawable.pro6,
            R.drawable.pro7, R.drawable.pro8, R.drawable.pro9, R.drawable.pro10};
    // TODO: Rename and change types of parameters
    private RecyclerView homerecycler1, homereyccler3, homereyccler4, homereyccler5, homereyccler6, homereyccler7, womenreyccler, menreyccler, shoesreyccler, accessoriesrecycler, trendingrecycler;
    private HomeAdapter1 mAdapter;

    private TrendingAdapter tAdapter;

    private HomeAdapter3 mAdapter3;

    private HomeAdapter5 mAdapter5;
    private HomeAdapter6 mAdapter6;
    private RecyclerView productListRecycler;
    int flag = 3;
    private HomeAdapter7 mAdapter7;
    private NewArrivalAdapter newArrivalAdapter;
    private View view;
    private Toolbar toolbar, toolbar1;
    String deviceToken = " ";
    private ArrayList<Integer> itemlidt = new ArrayList<>();

    private List<String> cat1 = new ArrayList<>();
    private List<Datum2> trendinglist = new ArrayList<>();

    private List<countryCodeDetail> currencycountrynames = new ArrayList();

    private CurrencyListAdapter currencyadpter;

    private NewHomeFragmentUpgrade.OnFragmentInteractionListener mListener;
    private TextView selectedcountry;
    private ImageView flagIamge;
    public static TextView greetingMessage;
    private TextView selectedCategoryName;
    private ImageView wishList, women_tick_image, men_tick_image,
            child_tick_image, kid_tick_image, selectedCategoryIcon;
    private RelativeLayout newArrivalContainer, shopWomenContainer, shopMenContainer, shopChildrenContainer, shopKidsContainer;
    private String newArrivalId, menId, womenId, babiesId, childerenId;
    private static NewHomeFragmentUpgrade instance = null;
    private FrameLayout maincontainer;

    int templangselectionflag;

    private boolean dataloaded = false;

    HomeSliderAdapter adapter;


    private List<Datum> newarrivalItem = new ArrayList<>();

    LinearLayout newarrivallayout;
    NestedScrollView mainnestedhomelayout;
    String coupencatId, coupencatName;

    public NewHomeFragmentUpgrade() {

    }

    public static NewHomeFragmentUpgrade getInstance() {
        return instance;
    }

    public static NewHomeFragmentUpgrade newInstance(String param1, String param2) {
        NewHomeFragmentUpgrade fragment = new NewHomeFragmentUpgrade();
        Bundle args = new Bundle();

        return fragment;
    }

    public void passData(Context context) {
        mContext = context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //clearFragmentBackStack();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        instance = this;
        if (view == null) {

            // Inflate the layout for this fragment
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                Locale = "ar";
                view = inflater.inflate(R.layout.home_fragment_latest_new_upgrade, container, false);
            } else {
                Locale = "en";
                view = inflater.inflate(R.layout.home_fragment_latest_new_english, container, false);
            }

            // view = inflater.inflate(R.layout.home_fragment_latest_new_english, container, false);
            execute = (MainActivity) getActivity();
            initViews(view);
            initOnclickListener();
            appsflyer_content_view();

            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    deviceToken = instanceIdResult.getToken();

                    if (deviceToken != null) {

                        Log.e("FcmToken---->>", deviceToken);
                    }
                }
            });


            getUpgradedLatestversionCode();


        }

        return view;

    }

    public void initOnclickListener() {
        //searchBar.setOnClickListener(this);
//        toolbarbeg.setOnClickListener(this);
        womenshop.setOnClickListener(this);
        menshop.setOnClickListener(this);
        shoesshop.setOnClickListener(this);
        accessoriesshop.setOnClickListener(this);
        maincat1.setOnClickListener(this);
        kidsshop.setOnClickListener(this);
        childerenshop.setOnClickListener(this);
        offersLayout.setOnClickListener(this);
        kids.setOnClickListener(this);
        childeren.setOnClickListener(this);
        women.setOnClickListener(this);
        men.setOnClickListener(this);
        shoes.setOnClickListener(this);
        accessories.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.women:
                Fragment prFrag = new ProductList();
                Bundle databund = new Bundle();

                databund.putInt("productId", Integer.parseInt(ids.get(2)));

                databund.putSerializable("childeren", (Serializable) childerenofcatagory.get(2));
                databund.putString("fromwhere", "fromhome");
                prFrag.setArguments(databund);


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.container2, prFrag)
                        .commit();

                break;

            case R.id.men:

                prFrag = new ProductList();
                databund = new Bundle();

                databund.putInt("productId", Integer.parseInt(ids.get(3)));
                databund.putSerializable("childeren", (Serializable) childerenofcatagory.get(3));

                databund.putString("fromwhere", "fromhome");
                prFrag.setArguments(databund);


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.container2, prFrag)
                        .commit();

                break;

            case R.id.shoes:

                prFrag = new ProductList();
                databund = new Bundle();

                databund.putInt("productId", Integer.parseInt(ids.get(2)));
                databund.putSerializable("childeren", (Serializable) childerenofcatagory.get(2));

                databund.putString("fromwhere", "fromhome");
                prFrag.setArguments(databund);


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.container2, prFrag)
                        .commit();


                break;


            case R.id.accessories:

                prFrag = new ProductList();
                databund = new Bundle();

                databund.putInt("productId", Integer.parseInt(ids.get(5)));
                databund.putSerializable("childeren", (Serializable) childerenofcatagory.get(5));

                databund.putString("fromwhere", "fromhome");
                prFrag.setArguments(databund);


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.container2, prFrag)
                        .commit();


                break;


            case R.id.cat1:
                prFrag = new ProductList();
                databund = new Bundle();

                databund.putString("fromwhere", "fromhome");
                prFrag.setArguments(databund);


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.container2, prFrag)
                        .commit();


                break;


            case R.id.kids:
                prFrag = new ProductList();
                databund = new Bundle();

                databund.putInt("productId", Integer.parseInt(ids.get(0)));
                databund.putSerializable("childeren", (Serializable) childerenofcatagory.get(0));

                databund.putString("fromwhere", "fromhome");
                prFrag.setArguments(databund);


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.container2, prFrag)
                        .commit();


                break;


            case R.id.childeren:


                prFrag = new ProductList();
                databund = new Bundle();

                databund.putInt("productId", Integer.parseInt(ids.get(1)));
                databund.putSerializable("childeren", (Serializable) childerenofcatagory.get(1));

                databund.putString("fromwhere", "fromhome");
                prFrag.setArguments(databund);


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.container2
                                , prFrag)
                        .commit();

                break;


            case R.id.offersdetail:
                prFrag = new ProductList();
                databund = new Bundle();
                databund.putInt("productId", 266);
                databund.putString("name", "Offers");
                databund.putString("fromwhere", "fromhome");
                prFrag.setArguments(databund);


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.container, prFrag)
                        .commit();


                break;

            case R.id.toolbarbag:
                execute.ExecutFragmentListener(0);

                break;


        }
    }


    public void intiViewPager(final ArrayList<String> arr) {


        viewPager = view.findViewById(R.id.homeviewPager);


        viewPager.setOffscreenPageLimit(arr.size());
        viewPager.setRotation(180);


        adapter = new HomeSliderAdapter(view.getContext(), arr,

                new ViewListener() {
                    @Override
                    public void onClick(int position, View view) {
                        int id = view.getId();


                        switch (id) {

                            case R.id.home_slider_item:


                                //  Toast.makeText(getContext(),"selection is"+position,Toast.LENGTH_SHORT).show();
                                Fragment prFrag = new ProductList();
                                Bundle databund = new Bundle();
                                databund.putString("fromwhere", "fromhome");
                                databund.putString("onClick", "mainBanner");

                                databund.putInt("productId", Integer.parseInt(sliderId.get(position)));
                                databund.putString("name", sliderName.get(position));
                                // databund.putSerializable("childeren", (Serializable) childerenofslider.get(position));
                                prFrag.setArguments(databund);


                                getFragmentManager()
                                        .beginTransaction().addToBackStack(null)
                                        .add(R.id.container2, prFrag)
                                        .commit();


                                break;
                        }
                    }
                }


        );
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Toast.makeText(view.getContext(),position+"scroll",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(view.getContext(),position+"select",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Toast.makeText(view.getContext(),state+"state",Toast.LENGTH_SHORT).show();
            }
        });

        if (arr.size() > 1) {
            createSlide();
        }


    }

    public void initViews(View v) {

        // get instance of the view
        NudgeView nv = (NudgeView) view.findViewById(R.id.nudge);
        // initialize
        nv.initialiseNudgeView(getActivity());
        editorChoiceRecyclerView = (RecyclerView) view.findViewById(R.id.editorChoiceRecyclerView);
        newArrivalSliderDots = (LinearLayout) view.findViewById(R.id.newArrivalSliderDots);
        newArrivalViewPager = (ViewPager) view.findViewById(R.id.newArrivalViewPager);
        topcategoryrecycler = view.findViewById(R.id.toptabscategory);
        homerecycler1 = v.findViewById(R.id.home_recycler1);
        homereyccler3 = v.findViewById(R.id.home_recycler3);
        homereyccler5 = v.findViewById(R.id.home_recycler5);
        homereyccler6 = v.findViewById(R.id.home_recycler6);
        homereyccler7 = v.findViewById(R.id.home_recycler7);
        featuredrecyclerview = v.findViewById(R.id.featuredrecyclerview);

        womenreyccler = v.findViewById(R.id.women_recycler);
        menreyccler = v.findViewById(R.id.men_recycler);
        shoesreyccler = v.findViewById(R.id.shoes_recycler);
        accessoriesrecycler = v.findViewById(R.id.accessories_recycler);

        kids = v.findViewById(R.id.kids);
        childeren = v.findViewById(R.id.childeren);
        women = v.findViewById(R.id.women);
        men = v.findViewById(R.id.men);
        shoes = v.findViewById(R.id.shoes);
        accessories = v.findViewById(R.id.accessories);

        womentext = v.findViewById(R.id.womentext);
        mentext = v.findViewById(R.id.mentext);
        kidstext = v.findViewById(R.id.kidstext);
        childerentext = v.findViewById(R.id.childerentext);
        accessoriestext = v.findViewById(R.id.accessoriestext);
        shoestext = v.findViewById(R.id.shoestext);

        womenshop = v.findViewById(R.id.womenshopnow);
        menshop = v.findViewById(R.id.menshopnow);
        kidsshop = v.findViewById(R.id.kidsshopnow);
        childerenshop = v.findViewById(R.id.childerenshopnow);
        accessoriesshop = v.findViewById(R.id.accessoriesshopnow);
        shoesshop = v.findViewById(R.id.shoesshopnow);
        trendingrecycler = v.findViewById(R.id.tending_recycler1);
        offres = v.findViewById(R.id.offres);
        categorySelectionContainerHome = view.findViewById(R.id.categorySelectionContainerHome);
        searchcontainer = view.findViewById(R.id.searchcontainer);
        selectedCategoryName = view.findViewById(R.id.selectedCategoryName);
        greetingMessage = view.findViewById(R.id.greetingMessage);


        updatefix = (MainActivity) getActivity();

        productListRecycler = v.findViewById(R.id.product_listhome_recycler);
        fetureImage1 = v.findViewById(R.id.featurproduct1);
        featureImage2 = v.findViewById(R.id.featurproduct2);
        featureImage3 = v.findViewById(R.id.featurproduct3);
        featureImage7 = v.findViewById(R.id.featurproduct7);
        featureImage8 = v.findViewById(R.id.featurproduct8);
        featureImage9 = v.findViewById(R.id.featurproduct9);
        maincontainer = v.findViewById(R.id.container2);
        newarrivalhomerecycler = v.findViewById(R.id.new_arrival_recycler);

        boxImage = v.findViewById(R.id.boximage);
        serchbarhome = v.findViewById(R.id.serachbarhome);
        allnewarrival = v.findViewById(R.id.allewarrival);
        newarrivalrecycler = v.findViewById(R.id.new_arrival_recycler_upgrade);
        categorySelectionContainerCardView = view.findViewById(R.id.categorySelectionContainerCardView);
        searchCardView = view.findViewById(R.id.searchCardView);
        wishListCardView = view.findViewById(R.id.wishListCardView);
        notificationCardView = view.findViewById(R.id.notificationCardView);
        selectedCategoryIcon = view.findViewById(R.id.selectedCategoryIcon);
        shopWomenContainer = view.findViewById(R.id.shopWomenContainer);
        newArrivalContainer = view.findViewById(R.id.newArrivalContainer);
        shopMenContainer = view.findViewById(R.id.shopMenContainer);
        shopChildrenContainer = view.findViewById(R.id.shopChildrenContainer);
        shopKidsContainer = view.findViewById(R.id.shopKidsContainer);
        women_tick_image = view.findViewById(R.id.women_tick_image);
        men_tick_image = view.findViewById(R.id.men_tick_image);
        child_tick_image = view.findViewById(R.id.child_tick_image);
        kid_tick_image = view.findViewById(R.id.kid_tick_image);
        wishList = (ImageView) view.findViewById(R.id.wishList);
        wishListBadgeCount = v.findViewById(R.id.wishListBadgeCount);
        newarrivallayout = v.findViewById(R.id.new_arrival_home_recycler);
        mainnestedhomelayout = v.findViewById(R.id.mainnestedlayout);


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
        notificationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new NotificationInboxFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.container2, fragment);
                ft.commit();
            }
        });

        alphabetic.add(Locale == "ar" ? "كل ماوصل حديثًا" : "All New Arrivals");
        alphabetic.add(Locale == "ar" ? "رجال" : "Men");
        alphabetic.add(Locale == "ar" ? "نساء" : "Women");
        alphabetic.add(Locale == "ar" ? "أطفال" : "Children");
        alphabetic.add(Locale == "ar" ? "مواليد" : "Babies");


        wishListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (MySharedPreferenceClass.getEmail(getContext()) != null) {*/
                if (!MySharedPreferenceClass.getMyUserId(getContext()).equals(" ")) {
                    Log.e("UserId", MySharedPreferenceClass.getMyUserId(getContext()));
                    Fragment fragment = new UpgradeWishListFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.container2, fragment);
                    ft.commit();
                } else {
                    Log.e("UserId", "null");
                    Fragment fragment = new UpgradeWishListFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.container2, fragment);
                    ft.commit();
                    //openSignInDialog(R.id.container2);
                }
            }
        });
        searchCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment prFrag = new SearchResultFragment();
                Bundle databund = new Bundle();
                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.container2, prFrag)
                        .commit();
            }
        });

        newArrivalContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment prFrag = new ProductList();
                Bundle databund = new Bundle();
                databund.putString("fromwhere", "fromcatagory");
                databund.putInt("productId", Integer.parseInt(newArrivalId));
                databund.putString("name", Locale == "ar" ? "كل ماوصل حديثًا" : "All New Arrivals");
//                        databund.putSerializable("childeren", (Serializable) mainhorizantallist.get(position).data);
                prFrag.setArguments(databund);
                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .add(R.id.container2, prFrag)
                        .commit();
                isCategoryContainerVisible = false;
                selectedCategoryIcon.setRotation(0);
                categorySelectionContainerCardView.setVisibility(View.GONE);
                searchcontainer.setVisibility(View.VISIBLE);
            }
        });
        shopWomenContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MySharedPreferenceClass.getCatId(getContext()).equals(womenId)) {

                } else {
                    selectedCategoryName.setText(Locale == "ar" ? "تسوق نساء" : "Shop Women");
                    women_tick_image.setVisibility(View.VISIBLE);
                    men_tick_image.setVisibility(View.GONE);
                    child_tick_image.setVisibility(View.GONE);
                    kid_tick_image.setVisibility(View.GONE);
                    MySharedPreferenceClass.setCatId(getApplicationContext(), womenId);
                    Intent in = new Intent(getActivity(), MainActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(in);
                    getActivity().finish();


                    // getActivity().recreate();
                }

            }
        });
        shopMenContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MySharedPreferenceClass.getCatId(getContext()).equals(menId)) {

                } else {
                    selectedCategoryName.setText(Locale == "ar" ? "تسوق رجال" : "Shop Men");
                    women_tick_image.setVisibility(View.GONE);
                    men_tick_image.setVisibility(View.VISIBLE);
                    child_tick_image.setVisibility(View.GONE);
                    kid_tick_image.setVisibility(View.GONE);
                    MySharedPreferenceClass.setCatId(getApplicationContext(), menId);

                    Intent in = new Intent(getActivity(), MainActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(in);
                    getActivity().finish();
                    // getActivity().recreate();
                }
            }
        });
        shopChildrenContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MySharedPreferenceClass.getCatId(getContext()).equals(childerenId)) {

                } else {
                    selectedCategoryName.setText(Locale == "ar" ? "تسوق أطفال" : "Shop Children");
                    men_tick_image.setVisibility(View.GONE);
                    women_tick_image.setVisibility(View.GONE);
                    child_tick_image.setVisibility(View.VISIBLE);
                    kid_tick_image.setVisibility(View.GONE);
                    MySharedPreferenceClass.setCatId(getApplicationContext(), childerenId);

                    Intent in = new Intent(getActivity(), MainActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(in);
                    getActivity().finish();
                    // getActivity().recreate();
                }
            }
        });
        shopKidsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MySharedPreferenceClass.getCatId(getContext()).equals(babiesId)) {

                } else {
                    selectedCategoryName.setText(Locale == "ar" ? "تسوق مواليد" : "Shop Babies");
                    men_tick_image.setVisibility(View.GONE);
                    women_tick_image.setVisibility(View.GONE);
                    child_tick_image.setVisibility(View.GONE);
                    kid_tick_image.setVisibility(View.VISIBLE);
                    MySharedPreferenceClass.setCatId(getApplicationContext(), babiesId);

                    Intent in = new Intent(getActivity(), MainActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(in);
                    getActivity().finish();
                    //getActivity().recreate();
                }
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

        serchbarhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Fragment prFrag = new SearchResultFragment();
                Bundle databund = new Bundle();


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.container2, prFrag)
                        .commit();


            }
        });
        allnewarrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allnewarrivaid != null && allnewarrivaid != " ") {
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();
                    databund.putString("fromwhere", "fromhome");


                    databund.putInt("productId", Integer.parseInt(allnewarrivaid));
                    databund.putString("name", allnewarrivalname);

                    if (new_arrival.size() > 0) {

                        databund.putString("name", new_arrival.get(1).name);
                        if (new_arrival.get(1).category_name != null) {
                            databund.putString("name", new_arrival.get(1).category_name);
                        }

                    }


                    prFrag.setArguments(databund);


                    getFragmentManager()
                            .beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();
                }

            }
        });


        offersLayout = v.findViewById(R.id.offersdetail);
        zoomoutImage = v.findViewById(R.id.zoomoutImage);
        linhomeMainLayout = v.findViewById(R.id.homeMainLayout);
        maincat1 = v.findViewById(R.id.cat1);
        maincat4 = v.findViewById(R.id.cat2);
        maincat3 = v.findViewById(R.id.cat3);
        womencat = v.findViewById(R.id.catwomen);
        mancat = v.findViewById(R.id.catmen);
        shoescat = v.findViewById(R.id.catshoes);
        accessoriescat = v.findViewById(R.id.cataccessories);


        offres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment prFrag = new ProductList();
                Bundle databund = new Bundle();
                databund.putString("fromwhere", "fromhome");

                databund.putInt("productId", 492);
                databund.putString("name", " ");
                databund.putSerializable("childeren", (Serializable) childerenofoffers);
                prFrag.setArguments(databund);


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .add(R.id.container2, prFrag)
                        .commit();


            }
        });
        if (MySharedPreferenceClass.getisAppOpenFirstTime(getApplicationContext()) && SplashActivity.ispopupshow)

            getfirsttimepopupData();
        SplashActivity.ispopupshow = false;


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

    //Initializing differentRecycler from here
    public void initRecycler1(final List<String> cat1) {
        this.cat1 = cat1;
        homerecycler1.setHasFixedSize(true);
        homerecycler1.setNestedScrollingEnabled(false);


        GridLayoutManager categoryGridLayoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false) {
            @Override
            protected boolean isLayoutRTL() {
                if (Locale == "ar") {
                    return true;
                } else {
                    return false;
                }

            }

        };
        //homerecycler1.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, true));
        homerecycler1.setLayoutManager(categoryGridLayoutManager);

        mAdapter = new HomeAdapter1(mContext, cat1, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();


                switch (id) {

                    case R.id.categoryButton://button for message

                        Fragment prFrag = new ProductList();
                        Bundle databund = new Bundle();
                        databund.putString("fromwhere", "fromcatagory");

                        databund.putInt("productId", Integer.parseInt(maincatelistId.get(position)));
                        databund.putString("name", maincatelistname.get(position));
//                        databund.putSerializable("childeren", (Serializable) mainhorizantallist.get(position).data);
                        prFrag.setArguments(databund);


                        getFragmentManager()
                                .beginTransaction().addToBackStack(null)
                                .add(R.id.container2, prFrag)
                                .commit();

                        break;


                }


            }
        }
        );
        homerecycler1.setAdapter(mAdapter);


    }


    public void initRecycler7(final List<Recommended> cat7) {

        homereyccler7.setHasFixedSize(true);
        homereyccler7.setNestedScrollingEnabled(false);

        homereyccler7.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        mAdapter7 = new HomeAdapter7(mContext, recommendedlist,


                new ViewListener() {
                    @Override
                    public void onClick(int position, View view) {
                        int id = view.getId();


                        switch (id) {

                            case R.id.man_image://button for message


                                Fragment prFrag = new ProductList();
                                Bundle databund = new Bundle();
                                databund.putString("fromwhere", "fromhome");
                                databund.putString("onClick", "mainBanner");
                                databund.putInt("productId", Integer.parseInt(recommendedlist.get(position).getId()));
                                databund.putString("name", recommendedlist.get(position).getName());
                                //databund.putSerializable("childeren", (Serializable) recommendedlist.get(position));
                                prFrag.setArguments(databund);


                                getFragmentManager()
                                        .beginTransaction().addToBackStack(null)
                                        .add(R.id.container2, prFrag)
                                        .commit();


                                break;
                        }
                    }
                }


        );
        homereyccler7.setAdapter(mAdapter7);


    }

    public void newarrivalrecycler(final List<ListItems> cat7) {

        newarrivalrecycler.setHasFixedSize(true);
        newarrivalrecycler.setNestedScrollingEnabled(false);

        newarrivalrecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, Locale == "ar" ? true : false));


        newArrivalAdapter = new NewArrivalAdapter(view.getContext(), cat7, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();
                //   Toast.makeText(getApplicationContext(),"click",Toast.LENGTH_SHORT).show();

                switch (id) {

                    case R.id.product_list_image://button for message
                        Fragment prFrag = new ProductDetail();
                        Bundle databund = new Bundle();
                        databund.putString("fromwhere", "fromhome");
                        databund.putString("onClick", "mainBanner");
                        databund.putString("sku", cat7.get(position).sku);
                        databund.putString("type", cat7.get(position).type);
                        databund.putString("name", cat7.get(position).name);


                        if (cat7.get(position).category_name != null) {
                            databund.putString("name", cat7.get(position).category_name);
                        }

                        databund.putSerializable("catagoryobject", cat7.get(position));

                        databund.putString("pid", cat7.get(position).id);
                        databund.putString("size", cat7.get(position).size);
                        databund.putString("color", cat7.get(position).color);
                        databund.putString("color_name", cat7.get(position).color_name);

                        prFrag.setArguments(databund);


                        getFragmentManager()
                                .beginTransaction().addToBackStack(null)
                                .replace(R.id.container2, prFrag)
                                .commit();

                        break;
                }
            }
        }


        );

        newarrivalrecycler.setAdapter(newArrivalAdapter);


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
                                isCategoryContainerVisible = false;
                                selectedCategoryIcon.setRotation(0);
                                categorySelectionContainerCardView.setVisibility(View.GONE);
                                searchcontainer.setVisibility(View.VISIBLE);
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
                    databund.putString("pid", pid);
                    productDetail.setArguments(databund);
                    getActivity().getIntent().setAction("");
                }


            }


        }

    }

    public void setfetureviews() {

        if (view.getContext() != null) {

            Glide.with(view.getContext())
                    .load(featured.get(0).image)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).placeholder(R.drawable.rectangular_border3)
                    .into(fetureImage1);
            Glide.with(view.getContext())
                    .load(featured.get(1).image)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).placeholder(R.drawable.rectangular_border3)
                    .into(featureImage2);
            Glide.with(view.getContext())
                    .load(featured.get(2).image)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).placeholder(R.drawable.rectangular_border3)
                    .into(featureImage3);
            Glide.with(view.getContext())
                    .load(featured.get(7).image)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).placeholder(R.drawable.rectangular_border3)
                    .into(featureImage7);
            Glide.with(view.getContext())
                    .load(featured.get(8).image)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).placeholder(R.drawable.rectangular_border3)
                    .into(featureImage8);
            Glide.with(view.getContext())
                    .load(featured.get(9).image)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).placeholder(R.drawable.rectangular_border3)
                    .into(featureImage9);
        }

        for (int i = 3; i <= 6; i++) {

            featuredrecyclerlist.add(featured.get(i));


            System.out.println("URL is" + featured.get(i).image);
        }


        fetureImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Fragment prFrag = new ProductList();
                Bundle databund = new Bundle();
                databund.putString("fromwhere", "fromhome");
                databund.putString("onClick", "mainBanner");
                databund.putInt("productId", Integer.parseInt(featured.get(0).id));
                databund.putString("name", featured.get(0).name);
                //databund.putSerializable("childeren", (Serializable) featured.get(0).children);
                prFrag.setArguments(databund);


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .add(R.id.container2, prFrag)
                        .commit();


            }
        });

        featureImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Fragment prFrag = new ProductList();
                Bundle databund = new Bundle();
                databund.putString("fromwhere", "fromhome");
                databund.putString("onClick", "mainBanner");
                databund.putInt("productId", Integer.parseInt(featured.get(1).id));
                databund.putString("name", featured.get(1).name);
                //databund.putSerializable("childeren", (Serializable) featured.get(1).children);
                prFrag.setArguments(databund);


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .add(R.id.container2, prFrag)
                        .commit();


            }
        });
        featureImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment prFrag = new ProductList();
                Bundle databund = new Bundle();
                databund.putString("fromwhere", "fromhome");
                databund.putString("onClick", "mainBanner");
                databund.putInt("productId", Integer.parseInt(featured.get(2).id));
                databund.putString("name", featured.get(2).name);
                //databund.putSerializable("childeren", (Serializable) featured.get(2).children);
                prFrag.setArguments(databund);


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .add(R.id.container2, prFrag)
                        .commit();


            }
        });
        featureImage7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment prFrag = new ProductList();
                Bundle databund = new Bundle();
                databund.putString("fromwhere", "fromhome");
                databund.putString("onClick", "mainBanner");
                databund.putInt("productId", Integer.parseInt(featured.get(7).id));
                databund.putString("name", featured.get(7).name);
                //databund.putSerializable("childeren", (Serializable) featured.get(7).children);
                prFrag.setArguments(databund);


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .add(R.id.container2, prFrag)
                        .commit();


            }
        });
        featureImage8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment prFrag = new ProductList();
                Bundle databund = new Bundle();
                databund.putString("fromwhere", "fromhome");
                databund.putString("onClick", "mainBanner");
                databund.putInt("productId", Integer.parseInt(featured.get(8).id));
                databund.putString("name", featured.get(8).name);
                //databund.putSerializable("childeren", (Serializable) featured.get(8).children);
                prFrag.setArguments(databund);


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .add(R.id.container2, prFrag)
                        .commit();


            }
        });
        featureImage9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment prFrag = new ProductList();
                Bundle databund = new Bundle();
                databund.putString("fromwhere", "fromhome");
                databund.putString("onClick", "mainBanner");
                databund.putInt("productId", Integer.parseInt(featured.get(9).id));
                databund.putString("name", featured.get(9).name);
                // databund.putSerializable("childeren", (Serializable) featured.get(9).children);
                prFrag.setArguments(databund);


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .add(R.id.container2, prFrag)
                        .commit();


            }
        });


        initRecycler7(recommendedlist);


    }


    //Api Request Methods Starts from Here


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

    public void clearFragmentBackStack() {
        FragmentManager fm = getFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount() - 1; i++) {
            fm.popBackStack();
        }
    }


    public void getCartDataforGuestUser() {


        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", "");
        query.put("mask_key", MySharedPreferenceClass.getMaskkey(getContext()));
        query.put("cart_id", String.valueOf(MySharedPreferenceClass.getCartId(getContext())));

        Call<ViewCart1> call = apiService.getCartDetail(query);
        call.enqueue(new Callback<ViewCart1>() {
            @Override
            public void onResponse(Call<ViewCart1> call, Response<ViewCart1> response) {
                parentDialogListener.hideProgressDialog();

                if (response.body() != null) {

                    if (response.body().msg.equals("success")) {
                        linhomeMainLayout.setVisibility(View.VISIBLE);
                        parentDialogListener.hideProgressDialog();


                        if (response.body().data.size() > 0) {
//                        parentDialogListener.hideProgressDialog();
                            for (int i = 0; i < response.body().data.size(); i++) {
                                if (response.body().data.get(i).qty instanceof String) {

                                    totalcount = totalcount + (int) Float.parseFloat((String) response.body().data.get(i).qty);

                                } else {

                                    parentDialogListener.hideProgressDialog();
                                    totalcount = totalcount + (int) (response.body().data.get(i).qty);


                                }


                            }

                            try {
                                MySharedPreferenceClass.setBedgeCount(getContext(), totalcount);
                            } catch (Exception e) {


                            }


                            updatefix.updateBedgeCount();
                            MainActivity.notificationBadge.setVisibility(View.VISIBLE);


                            Log.e("Total Value in Cart--->", String.valueOf(totalcount));


                        }


                    }
                } else {
                    MainActivity.notificationBadge.setVisibility(View.GONE);

                    // parentDialogListener.hideProgressDialog();


                }
                DeviceRegister();

            }


            @Override
            public void onFailure(Call<ViewCart1> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                MainActivity.notificationBadge.setVisibility(View.GONE);
                //Toast.makeText(getContext(), "Ts Found", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void getCartData() {
        totalcount = 0;

        // parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));

        Call<ViewCart1> call = apiService.getCartDetail(query);
        call.enqueue(new Callback<ViewCart1>() {
            @Override
            public void onResponse(Call<ViewCart1> call, Response<ViewCart1> response) {

                if (response.body() != null) {

                    if (response.body().msg.equals("success")) {
                        linhomeMainLayout.setVisibility(View.VISIBLE);
                        parentDialogListener.hideProgressDialog();


                        if (response.body().data.size() > 0) {
//                        parentDialogListener.hideProgressDialog();
                            for (int i = 0; i < response.body().data.size(); i++) {
                                if (response.body().data.get(i).qty instanceof String) {

                                    totalcount = totalcount + (int) Float.parseFloat((String) response.body().data.get(i).qty);

                                } else {

                                    parentDialogListener.hideProgressDialog();
                                    totalcount = totalcount + (int) (response.body().data.get(i).qty);


                                }


                            }


                            MySharedPreferenceClass.setBedgeCount(getContext(), totalcount);


                            updatefix.updateBedgeCount();
                            MainActivity.notificationBadge.setVisibility(View.VISIBLE);
                            Log.e("Total Value in Cart--->", String.valueOf(totalcount));


                        }


                    } else {
                        MainActivity.notificationBadge.setVisibility(View.GONE);
                        // parentDialogListener.hideProgressDialog();


                    }


                }
                DeviceRegister();
            }

            @Override
            public void onFailure(Call<ViewCart1> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                //Toast.makeText(getContext(), "Ts Found", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);


        if (visible && isResumed()) {


        }
    }


    private void createSlide() {

        if (timer == null && current_position == 0) {
            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (current_position == Integer.MAX_VALUE) {
                        current_position = 0;


                    }
                    viewPager.setCurrentItem(current_position++, true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(runnable);
                }
            }, 300, 8000);
        }
    }

    public void navigatetoPlaystore() {


        final String appPackageName = getContext().getPackageName(); // getPackageName() from Context or Activity object
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.setAction(Intent.ACTION_MAIN);
            startActivity(intent);


        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }


    }

    public void appsflyer_content_view() {


        Map<String, Object> eventValue = new HashMap<String, Object>();


        eventValue.put(AFInAppEventParameterName.CUSTOMER_USER_ID, MySharedPreferenceClass.getMyUserId(getContext()));


        AppsFlyerLib.getInstance().trackEvent(getContext(), AFInAppEventType.CONTENT_VIEW, eventValue);


    }

    @Nullable
    String getUniqueID() {
        UUID wideVineUuid = new UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L);
        try {
            MediaDrm wvDrm = new MediaDrm(wideVineUuid);
            byte[] wideVineId = wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID);
            return Arrays.toString(wideVineId);
        } catch (Exception e) {
            // Inspect exception
            return null;
        }
        // Close resources with close() or release() depending on platform API
        // Use ARM on Android P platform or higher, where MediaDrm has the close() method
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

    public void DeviceRegister() {
        String serial;
        String android_id;
        String myKey = " ";

        myKey = getUniqueID2().replaceAll("[^A-Za-z0-9]","");


        //myKey = FortSdk.getDeviceId(getContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Todo Don't forget to ask the permission
            if (getContext() != null) {

                android_id = Settings.Secure.getString(getContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                myKey = android_id;
            }
        } else {
            if (getContext() != null) {
                serial = Build.SERIAL;

                android_id = Settings.Secure.getString(getContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);

                myKey = serial + android_id;
            }
        }


        String uudid = UUID.randomUUID().toString();
        if (myKey.equals(" ")) {
            myKey = uudid;
        }
        Log.e("UUID--->>>>", uudid);


        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        HashMap<String, String> map1 = new HashMap<>();
        map1.put("devicetype", "android");
        map1.put("fcm_token", deviceToken);
        map1.put("device_id", myKey);

        Call<LoginResponse> call = apiService.deviceregister(map1);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().msg.equals("success")) {

                        parentDialogListener.hideProgressDialog();


                    } else {
                        parentDialogListener.hideProgressDialog();
                        Toast.makeText(getContext(), "لم تقم بتسجيل الدخول بشكل صحيح أو أن حسابك معطل مؤقتاً.", Toast.LENGTH_SHORT).show();


                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                System.out.println("ErrorApi...." + t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void initRecycler(View v, final List<ListItems> catagoryList) {
        productListRecycler.setHasFixedSize(true);


        productListRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        // specify an adapter (see also next example)


        mAdapter2 = new HorizantalProductListAdapter(mContext, catagoryList, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();


                switch (id) {

                    case R.id.product_list_image://button for message
                        Fragment prFrag = new ProductDetail();
                        Bundle databund = new Bundle();
                        databund.putString("sku", catagoryList.get(position).sku);
                        databund.putString("type", catagoryList.get(position).type);
                        databund.putString("pid", catagoryList.get(position).id);
                        databund.putString("size", catagoryList.get(position).size);
                        databund.putString("color", catagoryList.get(position).color);
                        databund.putString("color_name", catagoryList.get(position).color_name);
                        databund.putString("name", catagoryList.get(position).name);
                        if (catagoryList.get(position).category_name != null) {
                            databund.putString("name", catagoryList.get(position).category_name);
                        }
                        databund.putSerializable("catagoryobject", catagoryList.get(position));
                        prFrag.setArguments(databund);
                        getFragmentManager()
                                .beginTransaction().addToBackStack(null)
                                .replace(R.id.container2, prFrag)
                                .commit();


                        break;
                }
            }
        });

        productListRecycler.setAdapter(mAdapter2);


    }


    private void getScrollHomeScreenData() {
        sliderId.clear();

        sliderName.clear();
        maincatelistname.clear();
        maincatelistId.clear();
        recommendedlist.clear();
        newarrivalitems.clear();
        editorchoice.clear();
        featuredrecyclerlist.clear();

        //showProgressDialog();

        /*secont hit : https://upgrade.eoutlet.com/rest/ar/V1/api/getnexthomepage/11*/

        Log.e("getHomeScreenData", "getHomeScreenData");

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<NewSingleScreenData> call;
        call = Locale == "ar" ? apiService.getUpgradedSingleHomeScreenData(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "getnexthomepage/" + MySharedPreferenceClass.getCatId(getContext())) : apiService.getUpgradedSingleHomeScreenDataEnglish(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "getnexthomepage/" + MySharedPreferenceClass.getCatId(getContext()));
        call.enqueue(new Callback<NewSingleScreenData>() {
            @Override
            public void onResponse(Call<NewSingleScreenData> call, Response<NewSingleScreenData> response) {
                if (response.body() != null) {
                    if (response.body().response.equalsIgnoreCase("success")) {

                        if (!MySharedPreferenceClass.getisAppOpenFirstTime(getApplicationContext())) {
                            initLanguageDialog();


                        }


                        //hideProgressDialog();
                        sliderImage = new ArrayList<String>();
                        for (int i = 0; i < response.body().data.mainBanner.size(); i++) {

                            sliderImage.add(response.body().data.mainBanner.get(i).image);
                            sliderId.add(response.body().data.mainBanner.get(i).id);
                            sliderName.add(response.body().data.mainBanner.get(i).name);
                            //childerenofslider.add(response.body().data.mainBanner.get(i).children);


                        }

                        if (adapter == null) {
                            intiViewPager(sliderImage);
                        } else {

                            adapter.notifyDataSetChanged();
                            intiViewPager(sliderImage);
                        }

                        /*-----------------------------------------------*/
                        for (int i = 0; i < response.body().data.category.size(); i++) {

                            maincatelistname.add(response.body().data.category.get(i).name);
                            maincatelistId.add(response.body().data.category.get(i).id);


                        }
                        initRecycler1(maincatelistname);
                        /*-----------------------------------------------*/

                        boximage = response.body().data.salesBanners.get(0).image;
                        try {
                            Glide.with(boxImage)
                                    .load(boximage)
                                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(70)))
                                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .error(R.drawable.progress_animation)
                                    .placeholder(R.drawable.progress_animation).override(1000, 100)

                                    .into(boxImage);
                        } catch (Exception e) {
                        }
                        boxImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Fragment prFrag = new ProductList();
                                Bundle databund = new Bundle();
                                databund.putString("fromwhere", "fromcatagory");
                                databund.putInt("productId", Integer.parseInt(newArrivalId));


                                databund.putString("name", Locale == "ar" ? "كل ماوصل حديثًا" : "All New Arrivals");
//                        databund.putSerializable("childeren", (Serializable) mainhorizantallist.get(position).data);
                                prFrag.setArguments(databund);
                                getFragmentManager()
                                        .beginTransaction().addToBackStack(null)
                                        .add(R.id.container2, prFrag)
                                        .commit();
                                isCategoryContainerVisible = false;
                                selectedCategoryIcon.setRotation(0);
                                categorySelectionContainerCardView.setVisibility(View.GONE);
                                searchcontainer.setVisibility(View.VISIBLE);
                            }
                        });

                        /*-----------------------------------------------*/
                        allnewarrivalurl = response.body().data.salesBanners.get(1).image;
                        allnewarrivaid = response.body().data.salesBanners.get(1).id;
                        allnewarrivalname = response.body().data.salesBanners.get(1).name;
                        Picasso.get().load(allnewarrivalurl).placeholder(R.drawable.progress_animation).into(allnewarrival);
                        allnewarrival.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (allnewarrivaid != null && allnewarrivaid != " ") {
                                    Fragment prFrag = new ProductList();
                                    Bundle databund = new Bundle();
                                    databund.putString("fromwhere", "frombanner");
                                    databund.putString("onClick", "mainBanner");

                                    databund.putInt("productId", Integer.parseInt(allnewarrivaid));
                                    databund.putString("name", allnewarrivalname);


                                    prFrag.setArguments(databund);


                                    getFragmentManager()
                                            .beginTransaction().addToBackStack(null)
                                            .add(R.id.container2, prFrag)
                                            .commit();
                                }
                            }
                        });
                        new_arrival.clear();
                        /*-----------------------------------------------*/
                        for (int i = 0; i < response.body().data.flashSale.size(); i++) {
                            ListItems listitem = new ListItems();
                            listitem.name = response.body().data.flashSale.get(i).name;
                            listitem.id = response.body().data.flashSale.get(i).id;
                            listitem.img = response.body().data.flashSale.get(i).image;
                            listitem.oldPrice = String.valueOf(response.body().data.flashSale.get(i).oldPrice);
                            listitem.price = String.valueOf(response.body().data.flashSale.get(i).price);
                            //listitem.type = response.body().data.flashSale.get(i).;
                            new_arrival.add(listitem);
                        }

                        newarrivalrecycler(new_arrival);
                        /*-----------------------------------------------*/

                        for (int i = 0; i < response.body().data.editorChoice.size(); i++) {
                            editorchoice.add(response.body().data.editorChoice.get(i));
                        }


//                        eAdapter = new EditorChoiceAdapter(getContext(), editorchoice);
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
                        /*-----------------------------------------------*/

                        for (int i = 0; i < response.body().data.newArrivals.size(); i++) {
//                            newarrivalitems.add(response.body().data.newArrivals.get(i));


                        }
                        newArrivalViewPagerAdapter = new NewArrivalViewPagerAdapter(getContext(), newarrivalitems);
                        newArrivalViewPager.setAdapter(newArrivalViewPagerAdapter);
                        newarrivaldotscount = newArrivalViewPagerAdapter.getCount();
                        newarrivaldots = new ImageView[newarrivaldotscount];
                        if (newarrivaldotscount > 1) {
                            for (int i = 0; i < newarrivaldotscount; i++) {
                                newarrivaldots[i] = new ImageView(getContext());
                                newarrivaldots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                params.setMargins(8, 0, 8, 0);
                                newArrivalSliderDots.addView(newarrivaldots[i], params);
                            }
                            newarrivaldots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
                            newArrivalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    for (int i = 0; i < newarrivaldotscount; i++) {
                                        newarrivaldots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));
                                    }
                                    newarrivaldots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {
                                }
                            });
                        }
                        /*-----------------------------------------------*/

                        for (int i = 0; i < response.body().data.recommended.size(); i++) {
                            recommendedlist.add(response.body().data.recommended.get(i));

                        }
                        initRecycler7(recommendedlist);
                        /*-----------------------------------------------*/
                        featured = response.body().data.featured;
                        if (featured.size() > 0) {
                            setfetureviews();
                        }
                        /*-----------------------------------------------*/

                        fAdapter = new FeaturedChoiceAdapter(getContext(), featuredrecyclerlist, NewHomeFragmentUpgrade.this);
                        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false) {
                            @Override
                            protected boolean isLayoutRTL() {
                                if (Locale.equals("ar")) {
                                    return true;
                                } else {
                                    return false;
                                }

                            }
                        };
                        featuredrecyclerview.setLayoutManager(gridLayoutManager2);
                        featuredrecyclerview.setItemAnimator(new DefaultItemAnimator());
                        featuredrecyclerview.setAdapter(fAdapter);
                        fAdapter.notifyDataSetChanged();

                        /*-----------------------------------------------*/


                    }
                }


                /*if (!MySharedPreferenceClass.getisAppOpenFirstTime(getApplicationContext())) {
                    initLanguageDialog();


                }*/
                maincontainer.setVisibility(View.VISIBLE);
                dataloaded = true;

                if (MySharedPreferenceClass.getSharedProductId(getContext()) != null) {
                    Log.e("sharedProductId", MySharedPreferenceClass.getSharedProductId(getContext()));
                    Fragment productDetail = new ProductDetail();
                    FragmentTransaction mFragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.container2, productDetail).addToBackStack(null).commit();
                    Bundle databund = new Bundle();
                    //put your ArrayList data in bundle
                    databund.putString("fromwhere", "fromhome");
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
                                    isCategoryContainerVisible = false;
                                    selectedCategoryIcon.setRotation(0);
                                    categorySelectionContainerCardView.setVisibility(View.GONE);
                                    searchcontainer.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<NewSingleScreenData> call, Throwable t) {
                hideProgressDialog();
                Log.e("UpgradedHome fail", t.getMessage());
            }
        });

        if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {
            //   getCartData();
            getUpgradeCartData();
        } else {
            getUpgradedCartDataStatusforGuestUser();

            // getCartDataforGuestUser();
        }
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
    public void moengageNewInstallEvent() {

        Properties properties = new Properties();


        MoEHelper.getInstance(getApplicationContext()).trackEvent("New Install", properties);


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


                switch (id) {

                    case R.id.parentLayout://button for message
                        if (position == 0) {


                            getNewArrivalData();
                            newarrivallayout.setVisibility(View.VISIBLE);

                            mainnestedhomelayout.setVisibility(View.GONE);
                            MySharedPreferenceClass.setCatId(getApplicationContext(), newArrivalId);

/*

                            Fragment prFrag = new ProductList();
                            Bundle databund = new Bundle();
                            databund.putString("fromwhere", "fromcatagory");
                            databund.putInt("productId", Integer.parseInt(newArrivalId));
                            databund.putString("name", Locale == "ar" ? "كل ماوصل حديثًا" : "All New Arrivals");
//                        databund.putSerializable("childeren", (Serializable) mainhorizantallist.get(position).data);
                            prFrag.setArguments(databund);
                            getFragmentManager()
                                    .beginTransaction().addToBackStack(null)
                                    .add(R.id.container2, prFrag)
                                    .commit();
                            isCategoryContainerVisible = false;
                            selectedCategoryIcon.setRotation(0);
                            categorySelectionContainerCardView.setVisibility(View.GONE);
                            searchcontainer.setVisibility(View.VISIBLE);
*/

                        } else if (position == 1) {
                            if (MySharedPreferenceClass.getCatId(getContext()).equals(menId)) {

                            } else {
                                newarrivallayout.setVisibility(View.GONE);
                                mainnestedhomelayout.setVisibility(View.VISIBLE);
                                Thread.currentThread().interrupt();
                                MySharedPreferenceClass.setCatId(getApplicationContext(), menId);

                                getUpgradedLatestversionCode();

                               /* selectedCategoryName.setText(Locale == "ar" ? "رجال" : "Men");
                                women_tick_image.setVisibility(View.VISIBLE);
                                men_tick_image.setVisibility(View.GONE);
                                child_tick_image.setVisibility(View.GONE);
                                kid_tick_image.setVisibility(View.GONE);

                                Intent in = new Intent(getActivity(), MainActivity.class);
                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(in);
                                getActivity().finish();*/


                            }

                        } else if (position == 2) {
                            if (MySharedPreferenceClass.getCatId(getContext()).equals(womenId)) {

                            } else {
                                newarrivallayout.setVisibility(View.GONE);
                                mainnestedhomelayout.setVisibility(View.VISIBLE);
                                Thread.currentThread().interrupt();

                                MySharedPreferenceClass.setCatId(getApplicationContext(), womenId);

                                getUpgradedLatestversionCode();



                               /* selectedCategoryName.setText(Locale == "ar" ? "نساء" : "Women");
                                women_tick_image.setVisibility(View.GONE);
                                men_tick_image.setVisibility(View.VISIBLE);
                                child_tick_image.setVisibility(View.GONE);
                                kid_tick_image.setVisibility(View.GONE);
                                MySharedPreferenceClass.setCatId(getApplicationContext(), womenId);

                                Intent in = new Intent(getActivity(), MainActivity.class);
                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(in);
                                getActivity().finish();*/

                            }

                        } else if (position == 3) {
                            if (MySharedPreferenceClass.getCatId(getContext()).equals(childerenId)) {

                            } else {
                                newarrivallayout.setVisibility(View.GONE);
                                mainnestedhomelayout.setVisibility(View.VISIBLE);
                                Thread.currentThread().interrupt();
                                MySharedPreferenceClass.setCatId(getApplicationContext(), childerenId);

                                getUpgradedLatestversionCode();



                                /*selectedCategoryName.setText(Locale == "ar" ? "تسوق أطفال" : "Children");
                                men_tick_image.setVisibility(View.GONE);
                                women_tick_image.setVisibility(View.GONE);
                                child_tick_image.setVisibility(View.VISIBLE);
                                kid_tick_image.setVisibility(View.GONE);
                                MySharedPreferenceClass.setCatId(getApplicationContext(), childerenId);

                                Intent in = new Intent(getActivity(), MainActivity.class);
                               in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(in);
                                getActivity().finish();*/

                            }


                        } else if (position == 4) {

                            if (MySharedPreferenceClass.getCatId(getContext()).equals(babiesId)) {

                            } else {
                                newarrivallayout.setVisibility(View.GONE);
                                mainnestedhomelayout.setVisibility(View.VISIBLE);
                                Thread.currentThread().interrupt();
                                MySharedPreferenceClass.setCatId(getApplicationContext(), babiesId);

                                getUpgradedLatestversionCode();

                              /*  selectedCategoryName.setText(Locale == "ar" ? "تسوق مواليد" : "Babies");
                                men_tick_image.setVisibility(View.GONE);
                                women_tick_image.setVisibility(View.GONE);
                                child_tick_image.setVisibility(View.GONE);
                                kid_tick_image.setVisibility(View.VISIBLE);
                                MySharedPreferenceClass.setCatId(getApplicationContext(), babiesId);

                                Intent in = new Intent(getActivity(), MainActivity.class);
                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(in);
                                getActivity().finish();*/

                            }


                        }
                        break;
                }
            }
        }


        );
        topcategoryrecycler.setAdapter(madapter);
        topcategoryrecycler.setNestedScrollingEnabled(false);
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

    private void getUpgradedLatestversionCode() {
        showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Call<GetCategoryCode> call = apiService.getUpgradedLatestAppVersion();
        call.enqueue(new Callback<GetCategoryCode>() {
            @Override
            public void onResponse(Call<GetCategoryCode> call, Response<GetCategoryCode> response) {
                if (response.body() != null) {
                    //hideProgressDialog();
                    if (response.body().response.equals("success")) {
                        menId = response.body().menId;
                        womenId = response.body().womenId;
                        childerenId = response.body().childrenId;
                        babiesId = response.body().babiesId;
                        newArrivalId = response.body().newarrivalId;
                        if (MySharedPreferenceClass.getCatId(getContext()) != null) {
                            Log.e("getCatId", MySharedPreferenceClass.getCatId(getContext()));
                            if (MySharedPreferenceClass.getCatId(getContext()).equals(menId)) {
                                selectedCategoryName.setText(Locale == "ar" ? "رجال" : "Men");//id = 11
                                catselctionposition = 1;
                                getScrollHomeScreenData();
                            } else if (MySharedPreferenceClass.getCatId(getContext()).equals(womenId)) {
                                catselctionposition = 2;
                                getScrollHomeScreenData();
                                selectedCategoryName.setText(Locale == "ar" ? "نساء" : "Women"); //id=10
                            } else if (MySharedPreferenceClass.getCatId(getContext()).equals(babiesId)) {
                                catselctionposition = 4;
                                selectedCategoryName.setText(Locale == "ar" ? "مواليد" : "Babies");//id= 7
                                getScrollHomeScreenData();
                            } else if (MySharedPreferenceClass.getCatId(getContext()).equals(childerenId)) {
                                catselctionposition = 3;
                                selectedCategoryName.setText(Locale == "ar" ? "أطفال" : "Children");//id =8
                                getScrollHomeScreenData();
                            } else {
                                catselctionposition = 0;
                                hideProgressDialog();
                                getNewArrivalData();

                            }
                        }
                        initTopTabsRecycler(alphabetic);
                        //getScrollHomeScreenData();


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

    public void getUpgradedCartDataStatusforGuestUser() {

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
                int quant;
                if (response.body().response.equals("success")) {

                    upgradedcartData = response.body().data.items;

                    /*if(response.body().gift_wrap_fee!=null) {
                        gift_wrap_fees = Integer.parseInt(response.body().gift_wrap_fee);
                    }*/
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

            }


            @Override
            public void onFailure(Call<UpgradedCartItems> call, Throwable t) {
                hideProgressDialog();
                Log.e(TAG, t.toString());
                MainActivity.notificationBadge.setVisibility(View.GONE);
                MainActivity.bedgetext.setVisibility(View.GONE);
                MainActivity.toolbar_bedgetext.setVisibility(View.GONE);
            }
        });
        //getfirsttimepopupData();
        getWishListItemCount();
    }

    public void getUpgradeCartData() {
        totalcount = 0;


        // parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));


        /*https://upgrade.eoutlet.com/rest/en/V1/api/customercart/83188*/


        Call<UpgradedCartItems> call = apiService.getUpgradedCartDetail(Constants.UPGRADED_HOST_LINK + "rest/en/V1/api/customercart/" + MySharedPreferenceClass.getMyUserId(getContext()));
        call.enqueue(new Callback<UpgradedCartItems>() {
            @Override
            public void onResponse(Call<UpgradedCartItems> call, Response<UpgradedCartItems> response) {
                hideProgressDialog();
                int quant;
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().response.equals("success")) {
                        upgradedcartData = response.body().data.items;

                    }
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
                hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });

        getWishListItemCount();
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
        myKey = getUniqueID2().replaceAll("[^A-Za-z0-9]","");
        Log.e("mykey is",myKey);
        /*https://upgrade.eoutlet.com/rest/V1/api/getguestwishlist/23476242784b24b2646v*/
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<UpgradeWishListResponse> call = Locale == "ar" ? apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "getguestwishlist/" + "1" + myKey.toUpperCase() + "2") : apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "getguestwishlist/" + "1" + myKey.toUpperCase() + "2");
        call.enqueue(new Callback<UpgradeWishListResponse>() {
            @Override
            public void onResponse(Call<UpgradeWishListResponse> call, Response<UpgradeWishListResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResponse().equals("success")) {
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
    //    function to open signInDialog (for Guest User)
    public void openSignInDialog(int containerLayout) {
        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
            alertDialogBuilder.setMessage("You must login or register to add items to your wishlist");

            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Fragment prFrag = new LoginNewFragment();
                            Bundle databund = new Bundle();
                            databund.putString("flag", "fromWishList");
                            prFrag.setArguments(databund);
                            getFragmentManager()
                                    .beginTransaction().addToBackStack(null)
                                    .replace(containerLayout, prFrag)
                                    .commit();
                        }
                    });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        } else {
            alertDialogBuilder.setMessage("يجب عليك تسجيل الدخول أو التسجيل لإضافة عناصر إلى قائمة الامنيات الخاصة بك");
            alertDialogBuilder.setPositiveButton("نعم",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Fragment prFrag = new LoginNewFragment();
                            Bundle databund = new Bundle();
                            databund.putString("flag", "fromWishList");
                            prFrag.setArguments(databund);
                            getFragmentManager()
                                    .beginTransaction().addToBackStack(null)
                                    .replace(containerLayout, prFrag)
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
                            initnewArrivalRecycler();
                            maincontainer.setVisibility(View.VISIBLE);

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
                mFragmentTransaction.replace(R.id.container2, productList).addToBackStack(null).commit();
                Bundle databund = new Bundle();
                //put your ArrayList data in bundle
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
        prFrag.setTargetFragment(NewHomeFragmentUpgrade.this, 1002);
        prFrag.show(getFragmentManager(), "signup");


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
}


