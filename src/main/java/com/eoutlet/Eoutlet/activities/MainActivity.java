package com.eoutlet.Eoutlet.activities;


import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.adpters.BottomBarAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.fragments.AZfragment;
import com.eoutlet.Eoutlet.fragments.CartFragment;
import com.eoutlet.Eoutlet.fragments.HomeFragmentNewDesign;
import com.eoutlet.Eoutlet.fragments.NewCategoryDesignFragments;
import com.eoutlet.Eoutlet.fragments.NewProfileFragment;
import com.eoutlet.Eoutlet.fragments.OrderPaymentWebView;
import com.eoutlet.Eoutlet.fragments.ProductDetail;
import com.eoutlet.Eoutlet.fragments.ProductList;
import com.eoutlet.Eoutlet.fragments.SearchResultFragment;
import com.eoutlet.Eoutlet.fragments.Signup;
import com.eoutlet.Eoutlet.fragments.ThreedpasswordFragments;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.intrface.UpdateBedgeCount;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.AppCurrentVersion;
import com.eoutlet.Eoutlet.pojo.Child;
import com.eoutlet.Eoutlet.pojo.GetCategoryCode;
import com.eoutlet.Eoutlet.pojo.GuestUser;
import com.eoutlet.Eoutlet.pojo.LoginResponse;
import com.eoutlet.Eoutlet.services.MyFirebaseMessagingService;
import com.eoutlet.Eoutlet.utility.Constants;
import com.eoutlet.Eoutlet.utility.Util;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;

import co.tamara.sdk.TamaraPayment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;


public class MainActivity extends ParentActivity implements LocationListener, ExecuteFragment, UpdateBedgeCount, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback {
    public static  BottomNavigationView navigationView;
    Toolbar toolbar;
    private ImageView dotimage;
    private Context mcontext = MainActivity.this;
    public static TextView bedgetext, toolbar_bedgetext;
    public static Toolbar toolbar1;
    int REQUEST_FINE_LOCATION = 101;
    public static boolean isaddressempty = false;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    protected GoogleApiClient mGoogleApiClient;
    //protected LocationRequest locationRequest;
    int REQUEST_CHECK_SETTINGS = 100;
    public static View notificationBadge;
    static int totalcount;
    private String mCurrentTab;
    private HashMap<String, Stack<Fragment>> mStacks;
    public static final String TAB_HOME = "tab_home";
    public static final String TAB_CATOGORY = "tab_catagory";
    public static final String TAB_PROFILE = "tab_profile";
    public static final String TAB_CART = "tab_cart";
    MenuItem lastclickeditem;
    public static String shortlink1 = " ";
    public static String id = " ";
    public static String name = " ";
    public static String children = " ";
    public static String opencartview = " ";
    private String version, latestversion;
    private int versioncode;
    private NoSwipePager viewPager;
    private BottomBarAdapter pagerAdapter;
    private BottomNavigationView navigation;
    private ImageView toolbarsearch;
    private RelativeLayout toolbarbeg;
    private FusedLocationProviderClient fusedLocationClient;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private LocationRequest mLocationRequest;
    public static View mainview;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    public static boolean fromcart = false;
    List<Child> childlist = new ArrayList<>();
    CartFragment frag1 = new CartFragment();
    String languageToLoad;
    NewProfileFragment frag2 = new NewProfileFragment();
    //BrandFragment frag3 = new BrandFragment();


    AZfragment frag3 = new AZfragment();
    //NewCategoryFragment frag4 = new NewCategoryFragment();

    NewCategoryDesignFragments frag4 = new NewCategoryDesignFragments();

    // NewHomeFragmentUpgrade frag5 = new NewHomeFragmentUpgrade();
    HomeFragmentNewDesign frag5 = new HomeFragmentNewDesign();


    public ArrayList<HashMap<Object, Object>> requestItems = new ArrayList<HashMap<Object, Object>>();


    @Override
    public void onLocationChanged(Location location) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLatitude(), 1);
            String cityName = addresses.get(0).getAddressLine(0);
            String stateName = addresses.get(0).getAddressLine(1);
            String countryName = addresses.get(0).getAddressLine(2);

            //Toast.makeText(getApplicationContext(), cityName+stateName+countryName, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), cityName+stateName+countryName, Toast.LENGTH_SHORT).show();
            Log.d("Address---->>>>>>>", cityName + stateName + countryName);
        } catch (Exception e) {


        }

        //Toast.makeText(getApplicationContext(),"Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
                languageToLoad = "ar";
            } else {
                languageToLoad = "en";
            }
        } else {
            languageToLoad = "ar";
        }
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            config.setLocale(locale);
            createConfigurationContext(config);
        } else { //deprecated
            config.locale = locale;
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        }
        Log.e("Locale", Locale.getDefault().getDisplayLanguage());

        /*getBaseContext().getResources().updateConfiguration(config,
        getBaseContext().getResources().getDisplayMetrics());*/

        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {

            setContentView(R.layout.activity_main_english);

        } else {
            setContentView(R.layout.activity_main);
        }
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("success", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("token", token);
//                        MoEFireBaseHelper.getInstance().passPushToken(getApplicationContext(), token);


                    }
                });
        //Fabric.with(this, new Crashlytics());
        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
        TamaraPayment.Companion.initialize(Constants.AUTH_TOKEN, Constants.TAMARA_API_URL, Constants.NOTIFICATION_WEB_HOOK_URL);
        toolbar1 = findViewById(R.id.common_toolbar);

//        dotimage = toolbar1.findViewById(R.id.dotmenu);
       /* dotimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniatializesortDialogue();
            }
        });*/
       /* if (checkPermissions()) {
            requestopenGPS();
        }

        getLastLocation();*/


        AppsFlyerLib.getInstance().sendDeepLinkData(this);


        AppsFlyerLib.getInstance().registerConversionListener(this, new AppsFlyerConversionListener() {


            @Override
            public void onConversionDataSuccess(Map<String, Object> map) {
                for (String attrName : map.keySet()) {
                    Log.d("ConversionData", "conversion_attribute: " + attrName + " = " +
                            map.get(attrName));


                    if (attrName.equals("shortlink")) {
                        shortlink1 = map.get(attrName) + " ";

                        Toast.makeText(getBaseContext(), "" + shortlink1, Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d("Fail", "error onAttributionFailure : " + errorMessage);
            }

            /* for direct deep linking */
            @Override
            public void onAppOpenAttribution(Map<String, String> conversionData) {
                for (String attrName : conversionData.keySet()) {
                    Log.d(" Attribution", "onAppOpen_attribute: " + attrName + " = " +
                            conversionData.get(attrName));
                    String toSplit = conversionData.get(attrName);
                    if(toSplit.contains("eoutlet.onelink.me")) {

                        String result[] = toSplit.split("=");

                        String returnValue = result[result.length - 2];


                        String resultNew[] = returnValue.split("&");
                        String productId = resultNew[0];
                        System.out.println(productId);

                        Log.e("returnValue", returnValue);

                        MySharedPreferenceClass.setSharedProductId(getApplicationContext(), productId);
                        Toast.makeText(getApplicationContext(), productId.toString(), Toast.LENGTH_LONG).show();
                        Log.e("inside attribution", " ");

                        if (attrName.equals("id")) {

                            id = conversionData.get(attrName) + "";
                        } else if (attrName.equals("name")) {


                            name = conversionData.get(attrName) + "";
                        } else if (attrName.equals("children")) {


                            children = conversionData.get(attrName) + "";


                        } else if (attrName.equals("opencartview")) {
                            opencartview = conversionData.get(attrName) + "";

                        }
                    }

                }
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d("Fail2", "error onAttributionFailure : " + errorMessage);
            }
        });

        initViews();

//Log.d("MaskKey---->>>",MySharedPreferenceClass.getMaskkey(getApplicationContext()).toString());
        // Log.d("CArtID---->>>",String.valueOf(MySharedPreferenceClass.getCartId(getApplicationContext())));


        if (MySharedPreferenceClass.getMaskkey(getBaseContext()) == null) {


            //getguesttoken();
            getUpgradedguesttoken();


        }
        getversionname();
        //getLatestversionCode();

        createBroadCastReceiver();


    }

    public void requestopenGPS() {

        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(MainActivity.this)
                .addOnConnectionFailedListener(MainActivity.this).build();
        mGoogleApiClient.connect();
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);


    }

    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)


        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);


        if (checkPermissions()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            requestopenGPS();

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

           /* locationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // GPS location can be null if GPS is switched off
                    if (location != null) {
                        String msg = "Updated Location: " +
                                Double.toString(location.getLatitude()) + "," +
                                Double.toString(location.getLongitude());

                        Toast.makeText(getApplicationContext(),"Loaction is :"+msg,Toast.LENGTH_SHORT).show();
                       Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLatitude(), 1);
                            String cityName = addresses.get(0).getAddressLine(0);
                            String stateName = addresses.get(0).getAddressLine(1);
                            String countryName = addresses.get(0).getAddressLine(2);

                            //Toast.makeText(getApplicationContext(), cityName+stateName+countryName, Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(), cityName+stateName+countryName, Toast.LENGTH_SHORT).show();
                        Log.d("Address---->>>>>>>",cityName+stateName+countryName);
                        }
                        catch (Exception e){


                        }

                        Log.d("Address---->>>>>>>",msg);
                        onLocationChanged(location);
                    }
                    else {

                        Toast.makeText(getApplicationContext(),"Loaction is :"+"null",Toast.LENGTH_SHORT).show();
                        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                                .addApi(LocationServices.API)
                                .addConnectionCallbacks(MainActivity.this)
                                .addOnConnectionFailedListener(MainActivity.this).build();
                        mGoogleApiClient.connect();
                        locationRequest = LocationRequest.create();
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        locationRequest.setInterval(30 * 1000);
                        locationRequest.setFastestInterval(5 * 1000);

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Loaction is :"+"failed",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                                    .addApi(LocationServices.API)
                                    .addConnectionCallbacks(MainActivity.this)
                                    .addOnConnectionFailedListener(MainActivity.this).build();
                            mGoogleApiClient.connect();
                            locationRequest = LocationRequest.create();
                            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                            locationRequest.setInterval(30 * 1000);
                            locationRequest.setFastestInterval(5 * 1000);

                        }
                    });*/
        }
    }

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


            return true;
        } else {
            requestPermissions();
            return false;
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_FINE_LOCATION);
    }
    /*public void onLocationChanged(Location location) {
        // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLatitude());
        Toast.makeText(getApplicationContext(),"Loaction is :"+msg,Toast.LENGTH_SHORT).show();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLatitude(), 1);
            String cityName = addresses.get(0).getAddressLine(0);
            String stateName = addresses.get(0).getAddressLine(1);
            String countryName = addresses.get(0).getAddressLine(2);

           // Toast.makeText(this, cityName+stateName+countryName, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){


        }
        // You can now create a LatLng Object for use with maps
       // LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }*/


    public void initViews() {


        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.common_toolbar);


        viewPager = findViewById(R.id.mainviewPager);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setPagingEnabled(false);


        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());

        pagerAdapter.addFragments(frag1);
        pagerAdapter.addFragments(frag2);
        pagerAdapter.addFragments(frag3);
        pagerAdapter.addFragments(frag4);
        pagerAdapter.addFragments(frag5);

        viewPager.setAdapter(pagerAdapter);

        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {


            BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(0);
            notificationBadge = LayoutInflater.from(this).inflate(R.layout.cart_bedge, menuView, false);
            itemView.addView(notificationBadge);
        } else {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(4);
            notificationBadge = LayoutInflater.from(this).inflate(R.layout.cart_bedge, menuView, false);
            itemView.addView(notificationBadge);


        }


        bedgetext = findViewById(R.id.cart_badge_text);


        navigationView.setItemIconTintList(null);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        if (MySharedPreferenceClass.getDeeplinkingNotification(getApplicationContext()) != null) {
            Log.e("MainActivity", " getDeeplinkingNotification is not null");
            if (MySharedPreferenceClass.getDeeplinkingNotification(getApplicationContext()).equalsIgnoreCase("true")) {
                Log.e("MainActivity", " getDeeplinkingNotification is true");
                if (MySharedPreferenceClass.getDeeplinkingPage(getApplicationContext()) != null) {
                    Log.e("MainActivity", " getDeeplinkingPage is not null");
                    if (MySharedPreferenceClass.getDeeplinkingPage(getApplicationContext()).equalsIgnoreCase("home")) {
                        Log.e("MainActivity", " getDeeplinkingPage is home");
                        navigationView.setSelectedItemId(R.id.home);
                    } else if (MySharedPreferenceClass.getDeeplinkingPage(getApplicationContext()).equalsIgnoreCase("category")) {
                        Log.e("MainActivity", " getDeeplinkingPage is category");
                        navigationView.setSelectedItemId(R.id.catagory);


                    }
                    else if (MySharedPreferenceClass.getDeeplinkingPage(getApplicationContext()).equalsIgnoreCase("brand")) {
                        Log.e("MainActivity", " getDeeplinkingPage is brand");

                        navigationView.setSelectedItemId(R.id.AtoZ);


                    } else {
                        Log.e("MainActivity", " getDeeplinkingPage is detail");
                        navigationView.setSelectedItemId(R.id.home);
                    }
                } else {
                    Log.e("MainActivity", " getDeeplinkingPage is null");
                    navigationView.setSelectedItemId(R.id.home);
                }

            } else {
                Log.e("MainActivity", " getDeeplinkingNotification is false");
                navigationView.setSelectedItemId(R.id.home);
            }
        } else {
            Log.e("MainActivity", " getDeeplinkingNotification is null");
            navigationView.setSelectedItemId(R.id.home);
        }


        toolbar_bedgetext = toolbar.findViewById(R.id.toolbar_cart_badge_text);
        toolbarsearch = toolbar.findViewById(R.id.serachbar);
        toolbarbeg = toolbar.findViewById(R.id.toolbarbag);
        toolbarsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment prFrag = new SearchResultFragment();
                loadFragment(prFrag);


            }
        });


        toolbarbeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(0);

            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        Fragment fragment = null;
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {

            if (lastclickeditem != null) {

                Fragment fragment;
                Fragment manager = getSupportFragmentManager().getFragments().get(viewPager.getCurrentItem());

                if (manager.getFragmentManager() != null) {
                    if (manager.getFragmentManager().getBackStackEntryCount() > 0) {
                        while (manager.getFragmentManager().getBackStackEntryCount() > 0) {

                            manager.getFragmentManager().popBackStackImmediate();
                        }
                    }

                }
            }
            lastclickeditem = item;


            switch (item.getItemId()) {
                case R.id.cart:

                    toolbar.setVisibility(View.VISIBLE);
                    viewPager.setCurrentItem(0);

                    bedgetext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colour_black));
                    bedgetext.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.selected_badgedrawable));
                    toolbar1.setVisibility(View.GONE);
                    // getSupportFragmentManager().popBackStackImmediate();
                    return true;


                       /* fragment = new CartFragment();

                        loadFragment(fragment);*/
                // bedgetext.setVisibility(View.GONE);


                case R.id.profile:
                    viewPager.setCurrentItem(1);
                    bedgetext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                    bedgetext.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.noti_bedge2));
                    toolbar.setVisibility(View.GONE);
                    toolbar1.setVisibility(View.GONE);

                    return true;
                case R.id.home:

                    toolbar.setVisibility(View.GONE);
                    bedgetext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                    bedgetext.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.noti_bedge2));
                    fragment = new HomeFragmentNewDesign();


                    ((HomeFragmentNewDesign) fragment).passData(mcontext);

                    viewPager.setCurrentItem(4);


                    return true;


                case R.id.catagory:
                    toolbar.setVisibility(View.VISIBLE);

                    bedgetext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                    bedgetext.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.noti_bedge2));
                    toolbar1.setVisibility(View.GONE);
                    viewPager.setCurrentItem(3);

                    fragment = /*new NewCategoryFragment();*/ new NewCategoryDesignFragments();

                    ((NewCategoryDesignFragments) fragment).passData(mcontext);


                    return true;

                case R.id.AtoZ:

                    bedgetext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                    bedgetext.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.noti_bedge2));
                    toolbar.setVisibility(View.VISIBLE);
                    toolbar1.setVisibility(View.GONE);
                    viewPager.setCurrentItem(2);
                    return true;


            }
            return false;
        }


    };


    @Override
    public void ExecutFragmentListener(int x) {

        if (x == 0) {

            // Toast.makeText(getApplicationContext(),"fromdetail",Toast.LENGTH_LONG).show();
            viewPager.setCurrentItem(0);


        } else if (x == 1) {
            viewPager.setCurrentItem(1);

            // Fragment fragment = new LoginFragment();
            // ((LoginFragment) fragment).passData(this);
            // loadFragment(fragment);
            Menu menu = navigationView.getMenu();
            MenuItem item = menu.getItem(1);
            item.setChecked(item.getItemId() == R.id.profile);


        } else if (x == 2) {

            Fragment fragment = new Signup();
            ((Signup) fragment).passData(this);
            loadFragment(fragment);


        } else if (x == 3) {

            Fragment fragment = new ProductList();
            ((ProductList) fragment).passData(this);
            loadFragment(fragment);


        } else if (x == 4) {

            Fragment fragment = new ProductDetail();
            ((ProductDetail) fragment).passData(this);
            loadFragment(fragment);


        } else if (x == 5) {

            Menu menu = navigationView.getMenu();
            MenuItem item = menu.getItem(1);
            item.setChecked(item.getItemId() == R.id.profile);
            Fragment fragment = new NewProfileFragment();

            loadFragment(fragment);


        } else if (x == 6) {


            Fragment fragment = new CartFragment();

            loadFragment(fragment);


        }
    }


    public void updateBedgeCount() {

        bedgetext.setText(String.valueOf(MySharedPreferenceClass.getBedgeCount(this)));
        bedgetext.setVisibility(View.VISIBLE);
        toolbar_bedgetext.setVisibility(View.VISIBLE);
        toolbar_bedgetext.setText(String.valueOf(MySharedPreferenceClass.getBedgeCount(this)));


    }


    @Override
    public void onBackPressed() {

        if (OrderPaymentWebView.ispaymentwebview || ThreedpasswordFragments.isthreedpasswordwalletwebview) {
            return;
        }
        if (MainActivity.isaddressempty) {
           /* Fragment mFragment = null;
            mFragment = new CheckOutConfirmationfragment();
           // FragmentManager fragmentManager = getSupportFragmentManager();
         getSupportFragmentManager().beginTransaction().addToBackStack(null)
                    .replace(R.id.cartcontainer, mFragment).commit();
*/
            /*Fragment mFragment = null;
            mFragment = new CheckOutConfirmationfragment();


            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (Build.VERSION.SDK_INT >= 26) {
                transaction.setReorderingAllowed(false);
            }
            transaction.detach( mFragment).attach
                    ( mFragment).commit();
*/

            //getFragmentManager().popBackStack();
            //getFragmentManager().popBackStackImmediate();

            // getFragmentManager().popBackStackImmediate();


          /*  FragmentManager manager = getFragmentManager();
            int count = manager.getBackStackEntryCount();

            if (count == 0) {
                super.onBackPressed();
            } else {
                manager.popBackStack();
            }*/
            // super.onBackPressed();

          /*  Fragment mFragment;
            mFragment = new CheckOutConfirmationfragment();
            getSupportFragmentManager().beginTransaction().detach(mFragment).commitNowAllowingStateLoss();
            getSupportFragmentManager().beginTransaction().attach(mFragment).commitAllowingStateLoss();
            getFragmentManager().popBackStackImmediate();*/

            return;

        }


        super.onBackPressed();


    }



    /*
    @Override
    protected void onRestart() {
        super.onRestart();
        getLatestversionCode();

    }*/


    public void getversionname() {

        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
            versioncode = pInfo.versionCode;
            //Toast.makeText(getApplicationContext(), "version is" + version, Toast.LENGTH_SHORT).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void getLatestversionCode() {


        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Call<AppCurrentVersion> call = apiService.getLatestAppVersion();
        call.enqueue(new Callback<AppCurrentVersion>() {
            @Override
            public void onResponse(Call<AppCurrentVersion> call, Response<AppCurrentVersion> response) {


                if (response.body() != null) {

                    if (response.body().msg.equals("success")) {


                        latestversion = response.body().androidVersion;

                        if (versioncode < Integer.parseInt(latestversion))

                        // if(version.equals(latestversion))
                        {
                            openUpdatedialog();


                        } else {
                            if (viewPager == null) {

                                initViews();

                            }

                        }
                        //Toast.makeText(getApplicationContext(), "version is" + latestversion, Toast.LENGTH_SHORT).show();
                    }


                }


            }


            @Override
            public void onFailure(Call<AppCurrentVersion> call, Throwable t) {

                Log.e(TAG, t.toString());


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
                        MySharedPreferenceClass.setAdminUsername(getApplicationContext(), response.body().apiUsername);
                        MySharedPreferenceClass.setAdminPassword(getApplicationContext(), response.body().apiPassword);


                        //Toast.makeText(MainActivity.this, "Inside version code", Toast.LENGTH_SHORT).show();


                        latestversion = response.body().androidVersion;

                        if (versioncode < Integer.parseInt(latestversion))

                        // if(version.equals(latestversion))
                        {
                            openUpdatedialog();


                        } else {
                            if (viewPager == null) {

                                initViews();

                            }

                        }
                        getTokenID(response.body().apiUsername, response.body().apiPassword);
                        //Toast.makeText(getApplicationContext(), "version is" + latestversion, Toast.LENGTH_SHORT).show();
                    }


                }


            }


            @Override
            public void onFailure(Call<GetCategoryCode> call, Throwable t) {

                Log.e(TAG, t.toString());


            }
        });


    }

    public void getTokenID(String username, String password) {
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        map1.put("username", username);
        map1.put("password", password);

        // mainparam.put("param",map1);

        Call<String> call = apiService.createtokenbyId(map1);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null) {


                    Log.d("Response------->>>>", response.body().toString());

                    MySharedPreferenceClass.setToken(getApplicationContext(), response.body().toString());


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, t.toString());

                Toast.makeText(getApplicationContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void
    loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container2, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
//        transaction.commitNowAllowingStateLoss();
    }

    public void openUpdatedialog() {
        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);


        TextView myMsg = new TextView(this);
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equalsIgnoreCase("en")) {
                myMsg.setText("Alert");

            }


        } else if (Locale.getDefault().getLanguage().equalsIgnoreCase("en")) {
            myMsg.setText("Alert");


        } else {
            myMsg.setText("Alert");
        }
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg.setTextSize(25);
        myMsg.setTextColor(Color.BLACK);
        alertDialogBuilder.setCustomTitle(myMsg);


        TextView myMsg2 = new TextView(this);


        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equalsIgnoreCase("en")) {
                myMsg2.setText("There is a new version of this app available!");

            }


        } else if (Locale.getDefault().getLanguage().equalsIgnoreCase("en")) {
            myMsg2.setText("There is a new version of this app available");


        } else {
            myMsg2.setText("هناك إصدار جديد من هذا التطبيق متاح");
        }

        myMsg2.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg2.setTextSize(25);
        myMsg2.setTextColor(Color.BLACK);
        alertDialogBuilder.setMessage(myMsg2.getText().toString());

        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equalsIgnoreCase("en")) {
                alertDialogBuilder.setPositiveButton(
                        "Update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                if (Util.checkConnection(getApplicationContext())) {

                                    appsflyer_event_update();
                                    navigatetoPlaystore();

                                }

                            }
                        });

            }
        } else if (Locale.getDefault().getLanguage().equalsIgnoreCase("en")) {

            alertDialogBuilder.setPositiveButton(
                    "Update",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            if (Util.checkConnection(getApplicationContext())) {

                                appsflyer_event_update();
                                navigatetoPlaystore();

                            }

                        }
                    });


        } else {
            alertDialogBuilder.setPositiveButton(
                    "تحديث",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            if (Util.checkConnection(getApplicationContext())) {

                                appsflyer_event_update();
                                navigatetoPlaystore();

                            }

                        }
                    });

        }
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.show();


    }


    public void navigatetoPlaystore() {


        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.setAction(Intent.ACTION_MAIN);
            startActivity(intent);


        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }


    }

    public void appsflyer_event_update() {
        Map<String, Object> eventValue = new HashMap<String, Object>();

        eventValue.put(AFInAppEventParameterName.OLD_VERSION, versioncode);
        eventValue.put(AFInAppEventParameterName.NEW_VERSION, latestversion);
        AppsFlyerLib.getInstance().trackEvent(getBaseContext(), AFInAppEventType.UPDATE, eventValue);
    }

    @Override
    public void onResume() {
        super.onResume();
        //getLatestversionCode();


        getUpgradedLatestversionCode();


        //Toast.makeText(getApplicationContext(),"from newResume",Toast.LENGTH_SHORT).show();


        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Constants.PUSH_NOTIFICATION));

    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }


    void createBroadCastReceiver() {


        //Creating broadcast receiver
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String type = intent.getStringExtra("type");
                //  if (intent.getAction().equals(Constants.PUSH_NOTIFICATION)) {
                Toast.makeText(getApplicationContext(), "From BroadCastReceiver", Toast.LENGTH_SHORT).show();


                // }


            }


        };

    }


    protected void onNewIntent(Intent intent) {
        Log.e("Main Activity", "onNewIntent");
        super.onNewIntent(intent);
        Uri data = intent.getData();
       if (data != null && data.toString().contains("eoutlet.onelink.me") ) {
            Log.e("deeplinkingcallback:-", data.toString());
            String toSplit = data.toString();
            String result[] = toSplit.split("=");

            String returnValue = result[result.length - 2];

            String resultNew[] = returnValue.split("&");
            String productId = resultNew[0];
            System.out.println(productId);


            MySharedPreferenceClass.setSharedProductId(getApplicationContext(), productId);
            Log.e("returnValue", returnValue);


        }

        if (MySharedPreferenceClass.getSharedProductId(getApplicationContext()) != null /*|| MySharedPreferenceClass.getDeeplinkingId(getApplicationContext())!=null*/) {
            navigationView.setSelectedItemId(R.id.home);

        }


        if (MyFirebaseMessagingService.id != null) {

            if (!MyFirebaseMessagingService.children.equals(" ")) {
                String childwithoutspace2 = MyFirebaseMessagingService.children.replaceAll("\\s", "");

                String childerenname2[] = childwithoutspace2.split("\\|");

                for (String a : childerenname2) {

                    String name = a.split(",")[1];
                    String id = a.split(",")[0];

                    Child child = new Child();
                    child.id = id;
                    child.name = name;
                    childlist.add(child);

                    System.out.println("Childeren are---->>>>" + a + " " + id + " " + name);
                }

                Fragment prFrag2 = new ProductList();
                Bundle databund2 = new Bundle();

                databund2.putInt("productId", Integer.parseInt(MyFirebaseMessagingService.id));
                databund2.putString("name", MyFirebaseMessagingService.catagoryname);
                databund2.putSerializable("childeren", (Serializable) childlist);

                databund2.putString("fromwhere", "fromhome");
                prFrag2.setArguments(databund2);


                getSupportFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.container2, prFrag2)
                        .commit();

            }


        }

        handleIntent(intent);

        // Toast.makeText(getBaseContext(),"Arpan",Toast.LENGTH_SHORT).show();
    }

    public void DeviceRegister() {
        String serial;
        String android_id;
        String myKey;

        myKey = " ";

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        HashMap<String, String> map1 = new HashMap<>();
        map1.put("devicetype", "android");
        map1.put("fcm_token", FirebaseInstanceId.getInstance().getToken());
        map1.put("device_id", myKey);

        Call<LoginResponse> call = apiService.deviceregister(map1);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


                if (response.body() != null) {


                    if (response.body().msg.equals("success")) {


                    } else {

                        Toast.makeText(getApplicationContext(), "لم تقم بتسجيل الدخول بشكل صحيح أو أن حسابك معطل مؤقتاً.", Toast.LENGTH_SHORT).show();


                    }
                } else {


                }


            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Log.e(TAG, t.toString());
                System.out.println("ErrorApi...." + t.toString());

                Toast.makeText(getApplicationContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        builder.build()
                );

        result.setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHECK_SETTINGS) {

            if (resultCode == RESULT_OK) {
                // getLastLocation();

                //     Toast.makeText(getApplicationContext(), "GPS enabled", Toast.LENGTH_LONG).show();
            } else {

                // Toast.makeText(getApplicationContext(), "GPS is not enabled", Toast.LENGTH_LONG).show();
            }

        }


        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }


    }


    @Override
    public void onResult(@NonNull Result result) {
        final Status status = result.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:

                // NO need to show the dialog;


                break;

            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                //  Location settings are not satisfied. Show the user a dialog

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().

                    status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);

                } catch (IntentSender.SendIntentException e) {
                }
                break;

            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                // Location settings are unavailable so not possible to show any dialog now
                break;
        }
    }


    public void getUpgradedguesttoken() {


        //  showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Call<GuestUser> call = apiService.getUpgradedGuestToken();
        call.enqueue(new Callback<GuestUser>() {
            @Override
            public void onResponse(Call<GuestUser> call, Response<GuestUser> response) {

                if (response.body() != null && response.body().msg != null) {
                    if (response.body().msg.equals("success")) {
                        //  hideProgressDialog();
                        MySharedPreferenceClass.setMaskkey(getBaseContext(), response.body().maskKey);
                        MySharedPreferenceClass.setCartId(getBaseContext(), response.body().quoteId);
                        Log.d("MaskKey---->>>", MySharedPreferenceClass.getMaskkey(getApplicationContext()).toString());
                        Log.d("CartID---->>>", String.valueOf(MySharedPreferenceClass.getCartId(getApplicationContext())));
                    } else {
                        //  hideProgressDialog();


                    }
                }

            }


            @Override
            public void onFailure(Call<GuestUser> call, Throwable t) {
                hideProgressDialog();
                Log.e(TAG, t.toString());

            }
        });


    }

    private void iniatializesortDialogue() {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme);
        View sheetView = this.getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
        mBottomSheetDialog.setContentView(sheetView);

        RadioButton english = sheetView.findViewById(R.id.english);
        RadioButton arabic = sheetView.findViewById(R.id.arabic);

        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {

            english.setChecked(true);
        } else {
            arabic.setChecked(true);

        }

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Language set to English", Toast.LENGTH_SHORT).show();
                MySharedPreferenceClass.setChoosenLanguage(MainActivity.this, "en");
                Intent in = new Intent(MainActivity.this, MainActivity.class);
                startActivity(in);
                finish();

            }
        });
        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "ضبط اللغة على اللغة الإنجليزية", Toast.LENGTH_SHORT).show();
                MySharedPreferenceClass.setChoosenLanguage(MainActivity.this, "ar");
                Intent in = new Intent(MainActivity.this, MainActivity.class);
                startActivity(in);
                finish();

            }
        });


        mBottomSheetDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void handleIntent(Intent intent) {
        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();

        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null) {
            String recipeId = appLinkData.getLastPathSegment();
            Log.e("recepieidis", recipeId);
            Log.e("appLink", appLinkData.toString());
            /*Uri appData = Uri.parse("https://upgrade.eoutlet.com/aspen-polo-clup-suit-boy-misc-ap2104004.html?pid=51772&uniqueid=ap2104004&type=product&seller=eoutlet").buildUpon()
                    .appendPath(recipeId).build();*/

            // Set<String> args = appData.getQueryParameterNames();

            String type = appLinkData.getQueryParameter("type");


            if (type!=null && type.equals("category")) {
                String cid = appLinkData.getQueryParameter("cid");

                Fragment prFrag2 = new ProductList();
                Bundle databund2 = new Bundle();

                databund2.putInt("productId", Integer.parseInt(cid));
                databund2.putString("name",  appLinkData.getQueryParameter("name"));
                databund2.putSerializable("childeren", (Serializable) childlist);
                databund2.putSerializable("onClick", " ");

                databund2.putString("fromwhere", "fromhome");
                prFrag2.setArguments(databund2);


                getSupportFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.container2, prFrag2)
                        .commit();
             getIntent().setAction("");
                Log.e("cid is", cid);
            } else if(type!=null && type.equals("product")) {

                String pid = appLinkData.getQueryParameter("pid");
                Log.e("pid is", pid);
                if(pid!= null) {
                    Fragment productDetail = new ProductDetail();


                    Bundle databund = new Bundle();
                    //put your ArrayList data in bundle
                    databund.putString("fromwhere", "fromhome");
                    databund.putString("pid", pid);
                    productDetail.setArguments(databund);
                    getSupportFragmentManager()
                            .beginTransaction().addToBackStack(null)
                            .replace(R.id.container2, productDetail)
                            .commit();
                    getIntent().setAction("");
                }

            }


        }

    }

}