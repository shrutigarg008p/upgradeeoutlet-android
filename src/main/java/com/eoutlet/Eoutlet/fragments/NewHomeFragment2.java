/*
package com.eoutlet.Eoutlet.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaDrm;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.eoutlet.Eoutlet.adpters.HomeAdapter1;
import com.eoutlet.Eoutlet.adpters.HomeAdapter3;
import com.eoutlet.Eoutlet.adpters.HomeAdapter5;
import com.eoutlet.Eoutlet.adpters.HomeAdapter6;
import com.eoutlet.Eoutlet.adpters.HomeAdapter7;
import com.eoutlet.Eoutlet.adpters.HomeSliderAdapter;
import com.eoutlet.Eoutlet.adpters.HorizantalProductListAdapter;
import com.eoutlet.Eoutlet.adpters.NewArrivalAdapter;
import com.eoutlet.Eoutlet.adpters.TrendingAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.ListItems;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.intrface.UpdateBedgeCount;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.CatagoryName;
import com.eoutlet.Eoutlet.pojo.Child;
import com.eoutlet.Eoutlet.pojo.Datum2;
import com.eoutlet.Eoutlet.pojo.FeatureandRecommandedResponse;
import com.eoutlet.Eoutlet.pojo.Featured;
import com.eoutlet.Eoutlet.pojo.GetBannners;
import com.eoutlet.Eoutlet.pojo.GetHorizantalCategory;
import com.eoutlet.Eoutlet.pojo.GetnewArrivals;
import com.eoutlet.Eoutlet.pojo.LoginResponse;
import com.eoutlet.Eoutlet.pojo.MainList;
import com.eoutlet.Eoutlet.pojo.MostDemanded;
import com.eoutlet.Eoutlet.pojo.OneHomeAPI;
import com.eoutlet.Eoutlet.pojo.Recommended;
import com.eoutlet.Eoutlet.pojo.Recommendedlist;
import com.eoutlet.Eoutlet.pojo.Trending;
import com.eoutlet.Eoutlet.pojo.ViewCart1;
import com.eoutlet.Eoutlet.services.MyFirebaseMessagingService;
import com.eoutlet.Eoutlet.utility.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.payfort.fort.android.sdk.base.FortSdk;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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


*/
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewHomeFragment2#newInstance} factory method to
 * create an instance of this fragment.
 *//*

public class NewHomeFragment2 extends Fragment implements View.OnClickListener {
    public ParentDialogsListener parentDialogListener;
    private LinearLayout offersLayout, linhomeMainLayout;
    private Context mContext;
    String version, latestversion;
    private ViewPager viewPager;
    private TextView toolText;
    private ImageView searchImage,dotimage;
    public static TextView toolbar_bedgetexthome;
    private ExecuteFragment execute;
    private int page = 0;
    private List<ListItems> productListmain = new ArrayList<>();
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private ArrayList<String> sliderImage = new ArrayList<String>();
    private ArrayList<String> sliderId = new ArrayList<String>();
    private ArrayList<String> sliderName = new ArrayList<String>();
    private List<List<Child>> childerenofslider = new ArrayList<>();
    private List<List<Child>> childerenofoffers = new ArrayList<>();

    private ArrayList<String> maincatelistname = new ArrayList<>();
    private ArrayList<String> maincatelistId = new ArrayList<>();
    private List<CatagoryName> mainhorizantallist = new ArrayList<>();
    private List<MainList> maincatelist = new ArrayList();
    String boximage, allnewarrivalurl, allnewarrivaid = " ";
    private List<ListItems> new_arrival = new ArrayList<>();


    private List<List<ListItems>> maincatitems = new ArrayList<>();


    private ImageView fetureImage1, featureImage2, featureImage3, boxImage;
    private int viewpagerposition;
    private List<Featured> featured;
    public List<Recommended> recommendedlist;

    private int current_position = 0;
    int currentPage = 0;
    Timer timer;
    private UpdateBedgeCount updatefix;
    private boolean _hasLoadedOnce = false;
    private ImageView zoomoutImage, kids, childeren, women, men, shoes, accessories, offres, allnewarrival;
    private TextView maincat1, maincat4, maincat3, womencat, mancat, shoescat, accessoriescat;

    private ArrayList<String> bannerslist = new ArrayList<>();
    private ArrayList<String> bannertext = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();


    private ArrayList<String> newCatagoryImage = new ArrayList<>();


    private List<List<Child>> childerenofcatagory = new ArrayList<>();
    private RecyclerView newarrivalrecycler;

    private FrameLayout serchbarhome;


    List<Child> childlist = new ArrayList<>();
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


    private NewHomeFragment2.OnFragmentInteractionListener mListener;


    public NewHomeFragment2() {

    }


    public static NewHomeFragment2 newInstance(String param1, String param2) {
        NewHomeFragment2 fragment = new NewHomeFragment2();
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

        if (view == null) {

            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.home_fragment_latest_new, container, false);
            if(MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")){
               // execute = (MainActivityEnglish) getActivity();
            }
            else{

            }

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

            // getHomeCatagory();
            getCatagoryDetailHome();


        }

        return view;

    }

    public void initOnclickListener() {
        //searchBar.setOnClickListener(this);
        toolbarbeg.setOnClickListener(this);
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
                execute.
                        ExecutFragmentListener(0);

                break;


        }
    }


    public void intiViewPager(final ArrayList<String> arr) {


        viewPager = view.findViewById(R.id.homeviewPager);


        viewPager.setOffscreenPageLimit(arr.size());
        viewPager.setRotation(180);


        final HomeSliderAdapter adapter = new HomeSliderAdapter(view.getContext(), arr,

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

                                databund.putInt("productId", Integer.parseInt(sliderId.get(position)));
                                databund.putString("name", sliderName.get(position));
                                databund.putSerializable("childeren", (Serializable) childerenofslider.get(position));
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

        homerecycler1 = v.findViewById(R.id.home_recycler1);
        homereyccler3 = v.findViewById(R.id.home_recycler3);
        homereyccler5 = v.findViewById(R.id.home_recycler5);
        homereyccler6 = v.findViewById(R.id.home_recycler6);
        homereyccler7 = v.findViewById(R.id.home_recycler7);

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

        //toolbarbeg=v.findViewById(R.id.toolbarbag);
        toolbar = getActivity().findViewById(R.id.common_toolbar);
        toolbar.setVisibility(View.VISIBLE);
        toolbar1 = v.findViewById(R.id.common_toolbar);
        searchImage = toolbar1.findViewById(R.id.serachbar);
        dotimage = toolbar1.findViewById(R.id.dotmenu);
        toolbarbeg = toolbar1.findViewById(R.id.toolbarbag);
        toolbar_bedgetexthome = toolbar1.findViewById(R.id.toolbar_cart_badge_text);
        updatefix = (MainActivity) getActivity();
        toolText = (toolbar1).findViewById(R.id.tooltext);
        productListRecycler = v.findViewById(R.id.product_listhome_recycler);
        fetureImage1 = v.findViewById(R.id.featurproduct1);
        featureImage2 = v.findViewById(R.id.featurproduct2);
        featureImage3 = v.findViewById(R.id.featurproduct3);
        boxImage = v.findViewById(R.id.boximage);
        serchbarhome = v.findViewById(R.id.serachbarhome);
        allnewarrival = v.findViewById(R.id.allewarrival);
        newarrivalrecycler = v.findViewById(R.id.new_arrival_recycler);


        */
/*public void onRadioButtonClicked(View view) {
            // Is the button now checked?
            boolean checked = ((RadioButton) view).isChecked();

            // Check which radio button was clicked
            switch(view.getId()) {
                case R.id.radio_pirates:
                    if (checked)
                        // Pirates are the best
                        break;
                case R.id.radio_ninjas:
                    if (checked)
                        // Ninjas rule
                        break;
            }
        }*//*

        dotimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniatializesortDialogue();
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

                    if (new_arrival.size() > 0) {
                        databund.putString("name", new_arrival.get(1).name);
                    }

                    //databund.putSerializable("childeren", (Serializable) new_arrival);

                    prFrag.setArguments(databund);


                    getFragmentManager()
                            .beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();
                }

            }
        });


        if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {
            toolText.setText(MySharedPreferenceClass.getMyFirstNamePref(getContext()) */
/*+ " " + "مرحبا"*//*
);
        }


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


        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment prFrag = new SearchResultFragment();
                Bundle databund = new Bundle();


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.container2, prFrag)
                        .commit();


                //Toast.makeText(getContext(),"dsfdsfdsfdsf",Toast.LENGTH_LONG).show();
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

    //Initializing differentRecycler from here
    public void initRecycler1(final List<String> cat1) {
        this.cat1 = cat1;
        homerecycler1.setHasFixedSize(true);
        homerecycler1.setNestedScrollingEnabled(false);
        homerecycler1.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, true));
        //homerecycler1.setLayoutManager(new GridLayoutManager(view.getContext(), 3));

        mAdapter = new HomeAdapter1(mContext, cat1, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();


                switch (id) {

                    case R.id.selectCatagory1://button for message

                        Fragment prFrag = new ProductList();
                        Bundle databund = new Bundle();
                        databund.putString("fromwhere", "fromcatagory");

                        databund.putInt("productId", Integer.parseInt(maincatelistId.get(position)));
                        databund.putString("name", maincatelistname.get(position));
                        databund.putSerializable("childeren", (Serializable) mainhorizantallist.get(position).data);
                        prFrag.setArguments(databund);


                        getFragmentManager()
                                .beginTransaction().addToBackStack(null)
                                .add(R.id.container2, prFrag)
                                .commit();

                        break;


                    //initRecycler(view,maincatitems.get(position));


                }


            }
        }
        );
        homerecycler1.setAdapter(mAdapter);


    }


    public void initrendingrecycler() {


        //  this.trendinglist = trendinglist;
        trendingrecycler.setHasFixedSize(true);
        trendingrecycler.setNestedScrollingEnabled(false);
        trendingrecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));


        tAdapter = new TrendingAdapter(mContext, trendinglist, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();


                switch (id) {

                    case R.id.selectCatagory1://button for message
                        Fragment prFrag = new ProductList();
                        Bundle databund = new Bundle();
                        databund.putString("fromwhere", "fromhome");

                        databund.putInt("productId", Integer.parseInt(trendinglist.get(position).id));
                        databund.putString("name", trendinglist.get(position).name);
                        databund.putSerializable("childeren", (Serializable) trendinglist.get(position).children);
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
        trendingrecycler.setAdapter(tAdapter);


    }

    public void initRecycler7(final List<Recommendedlist> cat7) {

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

                                databund.putInt("productId", Integer.parseInt(recommendedlist.get(position).id));
                                databund.putString("name", recommendedlist.get(position).name);
                                databund.putSerializable("childeren", (Serializable) recommendedlist.get(position).children);
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

        newarrivalrecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        newArrivalAdapter = new NewArrivalAdapter(mContext, cat7,


                new ViewListener() {
                    @Override
                    public void onClick(int position, View view) {
                        int id = view.getId();


                        switch (id) {

                            case R.id.singleproduct://button for message
                                Fragment prFrag = new ProductDetail();
                                Bundle databund = new Bundle();
                                databund.putString("fromwhere", "fromhome");
                                databund.putString("sku", cat7.get(position).sku);
                                databund.putString("type", cat7.get(position).type);
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

    public void initRecycler5(final List<ListItems> cat5) {

        // use a linear layout manager

        homereyccler5.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        // specify an adapter (see also next example)
        mAdapter5 = new HomeAdapter5(mContext, cat5,
                new ViewListener() {
                    @Override
                    public void onClick(int position, View view) {
                        int id = view.getId();


                        switch (id) {

                            case R.id.mainimage://button for message
                                Fragment prFrag = new ProductDetail();
                                Bundle databund = new Bundle();
                                databund.putString("sku", cat5.get(position).sku);
                                databund.putString("type", cat5.get(position).type);
                                databund.putSerializable("catagoryobject", cat5.get(position));
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
        homereyccler5.setAdapter(mAdapter5);
        homereyccler5.setNestedScrollingEnabled(false);


    }


    public void initwomenRecycler(final List<ListItems> cat_women) {


        womenreyccler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        // specify an adapter (see also next example)
        mAdapter5 = new HomeAdapter5(mContext, cat_women,
                new ViewListener() {
                    @Override
                    public void onClick(int position, View view) {
                        int id = view.getId();


                        switch (id) {

                            case R.id.mainimage://button for message
                                Fragment prFrag = new ProductDetail();
                                Bundle databund = new Bundle();
                                databund.putString("sku", cat_women.get(position).sku);
                                databund.putString("type", cat_women.get(position).type);
                                databund.putSerializable("catagoryobject", cat_women.get(position));
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
        womenreyccler.setAdapter(mAdapter5);
        womenreyccler.setNestedScrollingEnabled(false);


    }

    public void initMenRecycler(final List<ListItems> cat_men) {


        menreyccler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        // specify an adapter (see also next example)
        mAdapter5 = new HomeAdapter5(mContext, cat_men,
                new ViewListener() {
                    @Override
                    public void onClick(int position, View view) {
                        int id = view.getId();


                        switch (id) {

                            case R.id.mainimage://button for message
                                Fragment prFrag = new ProductDetail();
                                Bundle databund = new Bundle();
                                databund.putString("sku", cat_men.get(position).sku);
                                databund.putString("type", cat_men.get(position).type);
                                databund.putSerializable("catagoryobject", cat_men.get(position));
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
        menreyccler.setAdapter(mAdapter5);
        menreyccler.setNestedScrollingEnabled(false);


    }

    public void initShoesRecycler(final List<ListItems> cat_shoes) {


        shoesreyccler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        // specify an adapter (see also next example)
        mAdapter5 = new HomeAdapter5(mContext, cat_shoes,
                new ViewListener() {
                    @Override
                    public void onClick(int position, View view) {
                        int id = view.getId();


                        switch (id) {

                            case R.id.mainimage://button for message
                                Fragment prFrag = new ProductDetail();
                                Bundle databund = new Bundle();
                                databund.putString("sku", cat_shoes.get(position).sku);
                                databund.putString("type", cat_shoes.get(position).type);
                                databund.putSerializable("catagoryobject", cat_shoes.get(position));
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
        shoesreyccler.setAdapter(mAdapter5);
        shoesreyccler.setNestedScrollingEnabled(false);


    }


    public void initAccessoriesRecycler(final List<ListItems> cat_shoes) {


        accessoriesrecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        // specify an adapter (see also next example)
        mAdapter5 = new HomeAdapter5(mContext, cat_shoes,
                new ViewListener() {
                    @Override
                    public void onClick(int position, View view) {
                        int id = view.getId();


                        switch (id) {

                            case R.id.mainimage://button for message
                                Fragment prFrag = new ProductDetail();
                                Bundle databund = new Bundle();
                                databund.putString("sku", cat_shoes.get(position).sku);
                                databund.putString("type", cat_shoes.get(position).type);
                                databund.putSerializable("catagoryobject", cat_shoes.get(position));
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
        accessoriesrecycler.setAdapter(mAdapter5);
        accessoriesrecycler.setNestedScrollingEnabled(false);


    }

    public void initrecycler3(final List<ListItems> cat3) {
        homereyccler3.setHasFixedSize(true);


        homereyccler3.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        homereyccler3.setNestedScrollingEnabled(false);

        mAdapter3 = new HomeAdapter3(mContext, cat3
                , new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();


                switch (id) {

                    case R.id.mostwantedImage://button for message
                        Fragment prFrag = new ProductDetail();
                        Bundle databund = new Bundle();

                        databund.putString("sku", cat3.get(position).sku);
                        databund.putString("type", cat3.get(position).type);
                        databund.putSerializable("catagoryobject", cat3.get(position));
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
        homereyccler3.setAdapter(mAdapter3);
        homereyccler3.setNestedScrollingEnabled(true);


    }

    public void getHomeCatagory() {

        parentDialogListener.showProgressDialog();


        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Call<OneHomeAPI> call = apiService.getHomeCataGoryDetail3(Constants.HOST_LINK + Constants.BASE_LINK + "gethomehorizontalcategory.php");
        call.enqueue(new Callback<OneHomeAPI>() {
            @Override
            public void onResponse(Call<OneHomeAPI> call, Response<OneHomeAPI> response) {


                if (response.body() != null) {
                    //parentDialogListener.hideProgressDialog();

                    if (response.body().data.size() > 1) {

                        for (int i = 0; i < response.body().data.size(); i++) {

                            maincatelistname.add(response.body().data.get(i).cate);


                        }
                        initRecycler1(maincatelistname);


                        maincatelist = response.body().data;


                        for (int i = 0; i < maincatelist.size(); i++) {

                            // for(int j=0;j<maincatelist.get(i).data.size();j++){
                            maincatitems.add(maincatelist.get(i).data);

                            // }

                        }


                        initRecycler(view, maincatitems.get(0));

                        for (int i = 0; i < response.body().slidingbanners.size(); i++) {

                            sliderImage.add(response.body().slidingbanners.get(i).image);
                            sliderId.add(response.body().slidingbanners.get(i).id);
                            sliderName.add(response.body().slidingbanners.get(i).name);
                            childerenofslider.add(response.body().slidingbanners.get(i).children);


                        }
                        intiViewPager(sliderImage);


                        getfeturandrecommanded();


                        //getPoularNewSessionData("473");

                    } else {


                    }
                } else {


                }


            }


            @Override
            public void onFailure(Call<OneHomeAPI> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }


    public void getfeturandrecommanded() {


        // parentDialogListener.showProgressDialog();


        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Call<FeatureandRecommandedResponse> call = apiService.getfeatureandrecommanded(Constants.HOST_LINK + Constants.BASE_LINK + "getrecommended.php");
        call.enqueue(new Callback<FeatureandRecommandedResponse>() {
            @Override
            public void onResponse(Call<FeatureandRecommandedResponse> call, Response<FeatureandRecommandedResponse> response) {
                //parentDialogListener.hideProgressDialog();


                if (response.body() != null) {
                    //parentDialogListener.hideProgressDialog();
                    featured = response.body().featured;
                    recommendedlist = response.body().recommendedlist;
                    if (featured.size() > 0) {
                        setfetureviews();
                    }

                    getnewarrivals();


                }

            }


            @Override
            public void onFailure(Call<FeatureandRecommandedResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void getnewarrivals() {


        // parentDialogListener.showProgressDialog();


        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Call<GetnewArrivals> call = apiService.getnewArrival(Constants.HOST_LINK + Constants.BASE_LINK + "getnewarrival.php");
        call.enqueue(new Callback<GetnewArrivals>() {
            @Override
            public void onResponse(Call<GetnewArrivals> call, Response<GetnewArrivals> response) {
                parentDialogListener.hideProgressDialog();


                if (response.body() != null) {
                    boximage = response.body().banners.get(0).img;
                    allnewarrivalurl = response.body().banners.get(1).img;
                    allnewarrivaid = response.body().banners.get(1).id;
                    new_arrival = response.body().data;


                    try {
                        Glide.with(boxImage)
                                .load(boximage)*/
/*.apply(RequestOptions.bitmapTransform(new RoundedCorners(70)))*//*
.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .error(R.drawable.progress_animation)
                                .placeholder(R.drawable.progress_animation).override(1000, 1500)

                                .into(boxImage);
                    } catch (Exception e) {
                    }

                    Picasso.get().load(allnewarrivalurl).placeholder(R.drawable.progress_animation).into(allnewarrival);
                   */
/* try {
                        Glide.with(allnewarrival)
                                .load(allnewarrivalurl)*//*
*/
/*.apply(RequestOptions.bitmapTransform(new RoundedCorners(70)))*//*
*/
/*.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                     *//*
*/
/* .override(400, 1500)*//*
*/
/*

                                .into(allnewarrival);
                    } catch (Exception e) {
                    }*//*

                    linhomeMainLayout.setVisibility(View.VISIBLE);
                    newarrivalrecycler(new_arrival);


                }

            }


            @Override
            public void onFailure(Call<GetnewArrivals> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });
//340,subcat1 | 341,subcat2


        // Toast.makeText(getContext(),MainActivity.id,Toast.LENGTH_SHORT).show();
        if (!MainActivity.id.equals(" ")) {
            String childwithoutspace = MainActivity.children.replaceAll("\\s", "");

            String childerenname[] = childwithoutspace.split("\\|");

            for (String a : childerenname) {

                String name = a.split(",")[1];
                String id = a.split(",")[0];

                Child child = new Child();
                child.id = id;
                child.name = name;
                childlist.add(child);
                System.out.println("Childeren are---->>>>" + a + " " + id + " " + name);


            }

            //  List<List<Child>>  childrenarraylist = new ArrayList<>();

            // childrenarraylist.add(childlist);


            Fragment prFrag = new ProductList();
            Bundle databund = new Bundle();

            databund.putInt("productId", Integer.parseInt(MainActivity.id)*/
/*Integer.parseInt(cat1.get(3).id)*//*
);
            databund.putSerializable("childeren", (Serializable) childlist);
            databund.putString("name", MainActivity.name);
            databund.putString("adapterposition", "0");
            databund.putString("fromwhere", "fromhome");
            prFrag.setArguments(databund);


            getFragmentManager()
                    .beginTransaction().addToBackStack(null)
                    .replace(R.id.container2, prFrag)
                    .commit();

            MainActivity.id = " ";
            MainActivity.name = " ";
            MainActivity.children = " ";


        } else if (!MainActivity.opencartview.equals(" ")) {


            execute.ExecutFragmentListener(0);
            MainActivity.opencartview = " ";


        }
    }

    public void setfetureviews() {

        if (view.getContext() != null) {

            Glide.with(view.getContext())
                    .load(featured.get(0).img)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).placeholder(R.drawable.rectangular_border3)
                    .into(fetureImage1);
            Glide.with(view.getContext())
                    .load(featured.get(1).img)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).placeholder(R.drawable.rectangular_border3)
                    .into(featureImage2);
            Glide.with(view.getContext())
                    .load(featured.get(2).img)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).placeholder(R.drawable.rectangular_border3)
                    .into(featureImage3);
        }


        fetureImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Fragment prFrag = new ProductList();
                Bundle databund = new Bundle();
                databund.putString("fromwhere", "fromhome");

                databund.putInt("productId", Integer.parseInt(featured.get(0).id));
                databund.putString("name", featured.get(0).name);
                databund.putSerializable("childeren", (Serializable) featured.get(0).children);
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

                databund.putInt("productId", Integer.parseInt(featured.get(1).id));
                databund.putString("name", featured.get(1).name);
                databund.putSerializable("childeren", (Serializable) featured.get(1).children);
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

                databund.putInt("productId", Integer.parseInt(featured.get(2).id));
                databund.putString("name", featured.get(2).name);
                databund.putSerializable("childeren", (Serializable) featured.get(2).children);
                prFrag.setArguments(databund);


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .add(R.id.container2, prFrag)
                        .commit();


            }
        });


        initRecycler7(recommendedlist);


        if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {
            getCartData();
        } else {

            getCartDataforGuestUser();
        }
    }


    public void gettrending() {
        // parentDialogListener.showProgressDialog();
        BasicRequest apiService = BasicBuilder.getClient().create(BasicRequest.class);


        Call<Trending> call = apiService.getTrending(Constants.HOST_LINK + Constants.BASE_LINK + "getnewarrivalsandtrending.php");
        call.enqueue(new Callback<Trending>() {
            @Override
            public void onResponse(Call<Trending> call, Response<Trending> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();

                    if (response.body().data.size() > 1) {

                        trendinglist = response.body().data;


                        if (!response.body().couponbanner.equals(" ")) {
                            //  offres.setVisibility(View.VISIBLE);
                            Glide.with(offres)
                                    .load(response.body().couponbanner)
                                    .into(offres);

                        } else {

                            offres.setVisibility(View.GONE);


                        }
                        initrendingrecycler();

                        getBannersDetail();


                    } else {


                    }
                } else {


                }


            }


            @Override
            public void onFailure(Call<Trending> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


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

    public void getPoularNewSessionData(final String cat_id) {
        //   parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Call<MostDemanded> call = apiService.getpopularNewSesionData(Constants.HOST_LINK + Constants.BASE_LINK + "productlistlimitapi.php"*/
/*?cat_id=" + cat_id*//*
);
        call.enqueue(new Callback<MostDemanded>() {
            @Override
            public void onResponse(Call<MostDemanded> call, Response<MostDemanded> response) {


                if (response.body() != null) {
                    // parentDialogListener.hideProgressDialog();


                    if (response.body().arr.size() > 1) {
                        if (cat_id.equals("473")) {
                            //initRecycler7(response.body().arr);
                            //gettrending();


                            //getBannersDetail();

                            //getPoularNewSessionData("475");


                        } else if (cat_id.equals("475")) {


                            initrecycler3(response.body().arr);
                            // getPoularNewSessionData("467");
                            getPoularNewSessionData("476");


                        } else if (cat_id.equals("476")) {
//                            parentDialogListener.hideProgressDialog();
                            initRecycler5(response.body().arr);
                            getPoularNewSessionData("477");


                        } else if (cat_id.equals("477")) {
//                            parentDialogListener.hideProgressDialog();
                            initwomenRecycler(response.body().arr);
                            getPoularNewSessionData("478");


                        } else if (cat_id.equals("478")) {
//                            parentDialogListener.hideProgressDialog();
                            initMenRecycler(response.body().arr);
                            getPoularNewSessionData("479");


                        } else if (cat_id.equals("479")) {
//                            parentDialogListener.hideProgressDialog();
                            initShoesRecycler(response.body().arr);
                            getPoularNewSessionData("480");


                        } else if (cat_id.equals("480")) {
                            initAccessoriesRecycler(response.body().arr);

                            linhomeMainLayout.setVisibility(View.VISIBLE);


                        }

                    } else {
                        // parentDialogListener.hideProgressDialog();


                    }
                } else {
                    // parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<MostDemanded> call, Throwable t) {
                //  parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


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

    public void getBannersDetail() {

//parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();


        Call<GetBannners> call = apiService.getBanners();
        call.enqueue(new Callback<GetBannners>() {
            @Override
            public void onResponse(Call<GetBannners> call, Response<GetBannners> response) {
                parentDialogListener.hideProgressDialog();

                if (response.body().msg.equals("success")) {


                    if (response.body().data.size() > 0) {
                        parentDialogListener.hideProgressDialog();
                        for (int i = 0; i < response.body().data.size(); i++) {

                            bannerslist.add(response.body().data.get(i).image);
                            bannertext.add(response.body().data.get(i).name);
                            ids.add(response.body().data.get(i).id);

                            childerenofcatagory.add(response.body().data.get(i).children);


                        }


                    }
                    kidstext.setText(bannertext.get(0));
                    childerentext.setText(bannertext.get(1));
                    womentext.setText(bannertext.get(4));
                    mentext.setText(bannertext.get(3));
                    shoestext.setText(bannertext.get(4));
                    accessoriestext.setText(bannertext.get(5));
                    Picasso.get().load(bannerslist.get(0))*/
/*.placeholder(R.drawable.placeholder)*//*
.into(kids);
                    Picasso.get().load(bannerslist.get(1))*/
/*.placeholder(R.drawable.placeholder)*//*
.into(childeren);
                    Picasso.get().load(bannerslist.get(2))*/
/*.placeholder(R.drawable.placeholder)*//*
.into(women);
                    Picasso.get().load(bannerslist.get(3))*/
/*.placeholder(R.drawable.placeholder)*//*
.into(men);
                    Picasso.get().load(bannerslist.get(4))*/
/*.placeholder(R.drawable.placeholder)*//*
.into(shoes);
                    Picasso.get().load(bannerslist.get(5))*/
/*.placeholder(R.drawable.placeholder)*//*
.into(accessories);

                    linhomeMainLayout.setVisibility(View.VISIBLE);
                    if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {
                        getCartData();
                    } else {

                        getCartDataforGuestUser();
                    }

                } else {
                    parentDialogListener.hideProgressDialog();
                }


                Log.e("FromAppsFlyer---->", MainActivity.name + MainActivity.id);

                if (!MainActivity.id.equals(" ")) {


                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();

                    databund.putInt("productId", Integer.parseInt(MainActivity.id)*/
/*Integer.parseInt(cat1.get(3).id)*//*
);

                    databund.putString("name", MainActivity.name);
                    databund.putString("fromwhere", "fromhome");
                    prFrag.setArguments(databund);


                    getFragmentManager()
                            .beginTransaction().addToBackStack(null)
                            .replace(R.id.container2, prFrag)
                            .commit();

                    MainActivity.id = " ";

                } else if (!MainActivity.opencartview.equals(" ")) {


                    execute.ExecutFragmentListener(0);
                    MainActivity.opencartview = " ";


                }


                System.out.println(MyFirebaseMessagingService.id + "-------->>>>>>>>>>>");


            }

            @Override
            public void onFailure(Call<GetBannners> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

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




    public void DeviceRegister() {
        String serial;
        String android_id;
        String myKey = " ";

        myKey = getUniqueID2();


        //myKey = FortSdk.getDeviceId(getContext());

        */
/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Todo Don't forget to ask the permission
            if(getContext()!= null) {

                android_id = Settings.Secure.getString(getContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                myKey = android_id;
            }
        } else {
            if(getContext()!= null) {
                serial = Build.SERIAL;

                android_id = Settings.Secure.getString(getContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);

                myKey = serial + android_id;
            }
        }


        String uudid = UUID.randomUUID().toString();
        if(myKey.equals(" ")){
            myKey=uudid;
        }
        Log.e("UUID--->>>>",uudid);*//*



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




*/
/*  This code used to start  grid layout from right side of the screen as comfortable for

arabic Views*//*



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


    public void getCatagoryDetailHome() {
        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Call<GetHorizantalCategory> call = apiService.getcatagoryDetailHome();
        call.enqueue(new Callback<GetHorizantalCategory>() {
            @Override
            public void onResponse(Call<GetHorizantalCategory> call, Response<GetHorizantalCategory> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();

                    if (response.body().categorydata.size() > 0) {

                        if (response.body().categorydata.size() > 1) {
                            mainhorizantallist = response.body().categorydata;
                            for (int i = 0; i < response.body().categorydata.size(); i++) {

                                maincatelistname.add(response.body().categorydata.get(i).name);
                                maincatelistId.add(response.body().categorydata.get(i).id);


                            }
                            initRecycler1(maincatelistname);


                            for (int i = 0; i < response.body().slidingbanners.size(); i++) {

                                sliderImage.add(response.body().slidingbanners.get(i).image);
                                sliderId.add(response.body().slidingbanners.get(i).id);
                                sliderName.add(response.body().slidingbanners.get(i).name);
                                childerenofslider.add(response.body().slidingbanners.get(i).children);


                            }
                            intiViewPager(sliderImage);


                            getfeturandrecommanded();


                        } else {
                            parentDialogListener.hideProgressDialog();


                        }
                    } else {
                        parentDialogListener.hideProgressDialog();


                    }
                }
            }

            @Override
            public void onFailure(Call<GetHorizantalCategory> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void iniatializesortDialogue() {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity(),R.style.AppBottomSheetDialogTheme);
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
        mBottomSheetDialog.setContentView(sheetView);

        RadioButton english = sheetView.findViewById(R.id.english);
        RadioButton arabic  = sheetView.findViewById(R.id.arabic);

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Language set to English",Toast.LENGTH_SHORT).show();
                MySharedPreferenceClass.setChoosenLanguage(getContext(),"en");

            }
        });
     arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"ضبط اللغة على اللغة الإنجليزية",Toast.LENGTH_SHORT).show();
                MySharedPreferenceClass.setChoosenLanguage(getContext(),"ar");
            }
        });


        mBottomSheetDialog.show();
    }

}


*/
