package com.eoutlet.Eoutlet.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.adpters.CitySearchAdapter;
import com.eoutlet.Eoutlet.adpters.PlacesAutoCompleteAdapter;
import com.eoutlet.Eoutlet.adpters.UpgradeNewAddressListAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.listener.CartListener;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.EditAddressResponse;
import com.eoutlet.Eoutlet.pojo.GetCitiesResponse;
import com.eoutlet.Eoutlet.pojo.GetCountryCode;
import com.eoutlet.Eoutlet.pojo.NumberVerificationResponse;
import com.eoutlet.Eoutlet.pojo.SendOtpResponse;
import com.eoutlet.Eoutlet.pojo.UpgradeAddAddressResponse;
import com.eoutlet.Eoutlet.pojo.UpgradeGetAddressResponse;
import com.eoutlet.Eoutlet.pojo.UpgradedGetAddressList;
import com.eoutlet.Eoutlet.utility.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.moe.pushlibrary.MoEHelper;
import com.moengage.core.Properties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class UpgradeMapFragment extends Fragment implements PlacesAutoCompleteAdapter.ClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback {
    private CartListener cartreference;
    public ParentDialogsListener parentDialogListener;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    int REQUEST_CHECK_SETTINGS = 100;
    FusedLocationProviderClient mFusedLocationClient;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest locationRequest;
    String CelCodeSelected = "";
    private TextView firstName, lastName, city, area, street, mobileNumber, country;
    private TextInputLayout firstNameTextInputLayout, lastNameTextInputLayout, streetNameTextInputLayout, areaTextInputLayout, cityTextInputLayout, mobileNumberInputLayout, countryNameInputLayout;
    private FrameLayout upgradeAddAddressSubmitButton, upgradeFindMeOnMap;
    View view;
    Geocoder geocoder, geocoder1;
    ImageView clear_text, backarrow, imgLocationIcon;
    boolean placeSelected = false;
    boolean placeSearch = false;
    String languageToLoad;
    private List<String> cityList;
    private List<String> cityListBackend;
    private List<String> countryname;
    private List<String> countryCode;
    private List<String> celcode;
    private List<String> placeholder;
    private String apikey = "AIzaSyDBfKVOx5k6qGpWO2ZyeVPP8Rnpb1RLjyo";
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    GetCountryCode getCountryCodeResponse;
    GetCitiesResponse getCitiesResponse;
    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;
    private RecyclerView recyclerView;
    AutoCompleteTextView place_search;
    boolean isCurrentCountryId = false;
    String currentCountryId;
    LinearLayout locationMapFragmentView;
    Boolean showSuggestions = false;
    FrameLayout upgradeConfirmAddressSubmitButton, autoCompleteFrameLayout;
    LinearLayout mapHeader, mapContainer, addressFields, addressConfirmContainer, editAddressButton, notDeliveredText, enterAddressManuallyLayout;
    boolean showAddressFields = false;
    boolean locationSelected = false;
    boolean isAddressEnteredManually = false;
    String cityValueFromGoogle = "";
    String Latitude = "";
    String Longitude = "";
    private String permissionflag;
    String CountryCodeSelected = "";
    private Dialog dialog;
    String manualFirstName = "", manualLastName = "", manualStreet = "", manualArea = "", manualCity = "", manualPhone = "", manualCountry = "";
    private String flag, addressId, editFirstName, editLastName, editStreet, editArea, editCity, editPhone, editCountryId, editLatitude, editLongitude;
    Boolean editAddressFields = false;
    Boolean editAddressOnMapMove = false;
    private String selectedCityName = "";

    private List<UpgradedGetAddressList> upgradedGetAddressLists = new ArrayList<>();
    private ImageView backbtn;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            // marker get location on tap
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Log.e("onMapClick", "function called");
                    editStreet = "";
                    if (googleMap != null) {
                        if (showAddressFields == true) {
                            manualFirstName = firstName.getText().toString();
                            manualLastName = lastName.getText().toString();
                            manualStreet = street.getText().toString();
                            manualArea = area.getText().toString();
                            manualCity = city.getText().toString();
                            manualPhone = mobileNumber.getText().toString();
                            manualCountry = CountryCodeSelected;
                            mGoogleMap.getUiSettings().setZoomGesturesEnabled(showAddressFields == true ? false : true);
                            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(showAddressFields == true ? false : true);
                            mGoogleMap.getUiSettings().setScrollGesturesEnabled(showAddressFields == true ? false : true);
                            ((LinearLayout.LayoutParams) mapContainer.getLayoutParams()).weight = (float) 1.75;
                            mapHeader.setVisibility(View.GONE);
                            addressFields.setVisibility(View.GONE);
                            editAddressButton.setVisibility(View.GONE);
                            addressConfirmContainer.setVisibility(View.VISIBLE);
                            autoCompleteFrameLayout.setVisibility(View.VISIBLE);
                            imgLocationIcon.setVisibility(View.VISIBLE);
                            showAddressFields = false;
                            mGoogleMap.getUiSettings().setZoomGesturesEnabled(showAddressFields == true ? false : true);
                            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(showAddressFields == true ? false : true);
                            mGoogleMap.getUiSettings().setScrollGesturesEnabled(showAddressFields == true ? false : true);
                            if (isAddressEnteredManually == true) {
                                isAddressEnteredManually = false;
                            }

                        } else {
                            //move map camera
                            mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                                @Override
                                public void onCameraMove() {
                                    Log.e("onMapClick", "onCameraMove");
                                    // Get the center of the map
                                    mGoogleMap.clear();
                                    // display imageView
                                    imgLocationIcon.setVisibility(View.VISIBLE);
                                    notDeliveredText.setVisibility(View.GONE);
                                    upgradeConfirmAddressSubmitButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_mapgreybutton));
                                    upgradeConfirmAddressSubmitButton.setClickable(false);
                                }
                            });
                            mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                                @Override
                                public void onCameraIdle() {
                                    Log.e("onMapClick", "onCameraIdle");
                                    imgLocationIcon.setVisibility(View.VISIBLE);
                                    // customizing map marker with a custom icon
                                    // and place it on the current map camera position
                                    MarkerOptions markerOptions = new MarkerOptions().position(mGoogleMap.getCameraPosition().target);
                                    mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
                                    mCurrLocationMarker.setVisible(false);
                                    getGeoCoderLocationDetails(mGoogleMap.getCameraPosition().target);
                                }
                            });
                            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));
                            mGoogleMap.getUiSettings().setZoomGesturesEnabled(showAddressFields == true ? false : true);
                            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(showAddressFields == true ? false : true);
                            mGoogleMap.getUiSettings().setScrollGesturesEnabled(showAddressFields == true ? false : true);
                            Log.e("map move auto", "outSide");
                            getGeoCoderLocationDetails(latLng);
                        }
                    }
                }

            });

            final LocationManager manager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
            mGoogleMap = googleMap;
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                //requestopenGPS();
                // Toast.makeText(getContext(), "GPS is not enabled! ", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(getContext(), "GPS is enabled!", Toast.LENGTH_LONG).show();

                mLocationRequest = new LocationRequest();
                int minimumDistanceBetweenUpdates = 20;
                mLocationRequest.setSmallestDisplacement(minimumDistanceBetweenUpdates);
//            mLocationRequest.setInterval(120000); // two minute interval
//            mLocationRequest.setFastestInterval(120000);
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        //Location Permission already granted

                        if (placeSearch == false) {
                            if (flag.equals("editAddress")) {
                                Log.e("getCurrentLocation", "flag editAddress");
                                if (Latitude.length() == 0 || Longitude.length() == 0) {
                                    Log.e("flag editAddress", "editLatitude or editLongitude is empty");
                                    showAddressFields = true;
                                    mGoogleMap.getUiSettings().setZoomGesturesEnabled(showAddressFields == true ? false : true);
                                    mGoogleMap.getUiSettings().setMyLocationButtonEnabled(showAddressFields == true ? false : true);
                                    mGoogleMap.getUiSettings().setScrollGesturesEnabled(showAddressFields == true ? false : true);
                                    ((LinearLayout.LayoutParams) mapContainer.getLayoutParams()).weight = (float) 0.45;
                                    mapHeader.setVisibility(View.VISIBLE);
                                    addressFields.setVisibility(View.VISIBLE);
                                    editAddressButton.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                    addressConfirmContainer.setVisibility(View.GONE);
                                    autoCompleteFrameLayout.setVisibility(View.GONE);
//                imgLocationIcon.setVisibility(View.GONE);
                                    imgLocationIcon.setVisibility(View.VISIBLE);
                                    confirmAddressColorChange();
                                } else {
                                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                                    mGoogleMap.setMyLocationEnabled(true);
                                }
                            } else {
                                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                                mGoogleMap.setMyLocationEnabled(true);
                            }
                        }
                    } else {
                        //Request Location Permission
                        //checkLocationPermission();
                    }
                } else {
                    if (placeSearch == false) {
                        if (flag.equals("editAddress")) {
                            Log.e("getCurrentLocation", "flag editAddress");
                            if (Latitude.matches("") || Longitude.matches("")) {
                                Log.e("flag editAddress", "editLatitude or editLongitude is empty");
                                showAddressFields = true;
                                mGoogleMap.getUiSettings().setZoomGesturesEnabled(showAddressFields == true ? false : true);
                                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(showAddressFields == true ? false : true);
                                mGoogleMap.getUiSettings().setScrollGesturesEnabled(showAddressFields == true ? false : true);
                                ((LinearLayout.LayoutParams) mapContainer.getLayoutParams()).weight = (float) 0.45;
                                mapHeader.setVisibility(View.VISIBLE);
                                addressFields.setVisibility(View.VISIBLE);
                                editAddressButton.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                                addressConfirmContainer.setVisibility(View.GONE);
                                autoCompleteFrameLayout.setVisibility(View.GONE);
//                imgLocationIcon.setVisibility(View.GONE);
                                imgLocationIcon.setVisibility(View.VISIBLE);
                                confirmAddressColorChange();
                            } else {
                                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                                mGoogleMap.setMyLocationEnabled(true);
                            }
                        } else {
                            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                            mGoogleMap.setMyLocationEnabled(true);
                        }
                    }
                }
            }
        }
    };


    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
                LatLng latLng;
                //Place current location marker
                if (flag.equals("editAddress")) {

                    latLng = new LatLng(Double.parseDouble(Latitude), Double.parseDouble(Longitude));
                } else {
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                }

                //move map camera
                mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                    @Override
                    public void onCameraMove() {
                        // Get the center of the map
                        mGoogleMap.clear();
                        // display imageView
                        imgLocationIcon.setVisibility(View.VISIBLE);
                        notDeliveredText.setVisibility(View.GONE);
                        upgradeConfirmAddressSubmitButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_mapgreybutton));
                        upgradeConfirmAddressSubmitButton.setClickable(false);
                    }
                });
                mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        Log.e("map move auto", "onCameraIdle");
                        if (editAddressFields == true) {
                            editAddressOnMapMove = true;
                        }
                        imgLocationIcon.setVisibility(View.VISIBLE);
                        MarkerOptions markerOptions = new MarkerOptions().position(mGoogleMap.getCameraPosition().target);
                        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
                        mCurrLocationMarker.setVisible(false);
                        getGeoCoderLocationDetails(mGoogleMap.getCameraPosition().target);
                    }
                });
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));
                mGoogleMap.getUiSettings().setZoomGesturesEnabled(showAddressFields == true ? false : true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(showAddressFields == true ? false : true);
                mGoogleMap.getUiSettings().setScrollGesturesEnabled(showAddressFields == true ? false : true);
                Log.e("map move auto", "outSide");
                getGeoCoderLocationDetails(latLng);
            }
        }
    };

    private void getGeoCoderLocationDetails(LatLng GeoCoder) {
        Longitude = String.valueOf(GeoCoder.longitude);
        Latitude = String.valueOf(GeoCoder.latitude);
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String result = null;
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    GeoCoder.latitude, GeoCoder.longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                locationSelected = true;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }
                sb.append(address.getPremises()).append(", ");
                sb.append(address.getFeatureName()).append(", ");
                sb.append(address.getSubLocality()).append(", ");
                sb.append(address.getLocality()).append(", ");
                sb.append(address.getSubAdminArea()).append(", ");
                sb.append(address.getAdminArea()).append(", ");
                sb.append(address.getPostalCode()).append(", ");
                sb.append(address.getCountryName());
                result = sb.toString();
                //                currentCountryId = address.getCountryCode();
//                isCurrentCountryId = true;
                Log.e("Full Address map", result);
                if (editAddressFields == true) {
                    Log.e("editAddressFields", "true");
                    if (editStreet != "") {
                        Log.e("editStreet", "not null");
                        place_search.setText(editStreet);
                    } else {
                        Log.e("editStreet", " null");
                        place_search.setText(address.getAddressLine(0));
                    }
                } else {
                    Log.e("editAddressFields", "false");
                    place_search.setText(address.getAddressLine(0));
                }
                showSuggestions = false;
                if (countryCode.size() != 0) {
                    if (countryCode.contains(address.getCountryCode())) {
                        notDeliveredText.setVisibility(View.GONE);
                        if (getCountryCodeResponse != null) {
                            for (int i = 0; i < getCountryCodeResponse.data.size(); i++) {
                                if (address.getCountryCode() != null) {
                                    if (address.getCountryCode().equals(getCountryCodeResponse.data.get(i).code)) {
                                        CountryCodeSelected = address.getCountryCode();
                                        CelCodeSelected = getCountryCodeResponse.data.get(i).cel_code;
                                        country.setText(countryname.get(countryname.size() - 1 - i));
//                                        cityValueFromGoogle = address.getAddressLine(0) != null ? address.getAddressLine(0) : "";

                                        if (editAddressFields == true) {
                                            if (editStreet != "") {
                                                Log.e("editAddress", "area and street already filled");
                                                if (editAddressOnMapMove == true) {
                                                    editStreet = "";
                                                    editAddressOnMapMove = false;
                                                }
                                            } else {
                                                cityValueFromGoogle = address.getLocality() != null ? address.getLocality() : "";
                                                area.setText(address.getSubLocality() != null ? address.getSubLocality() : "");
                                                street.setText(address.getAddressLine(0) != null ? address.getAddressLine(0) + (address.getAddressLine(1) != null ? address.getAddressLine(1) : "") : "");
                                                editAddressFields = false;
                                                editStreet = "";
                                            }

                                        } else {
                                            cityValueFromGoogle = address.getLocality() != null ? address.getLocality() : "";
                                            area.setText(address.getSubLocality() != null ? address.getSubLocality() : "");
                                            street.setText(address.getAddressLine(0) != null ? address.getAddressLine(0) + (address.getAddressLine(1) != null ? address.getAddressLine(1) : "") : "");
                                        }
                                        getCityList(address.getCountryCode());
                                        break;
                                    }
                                }
                            }
                        }
                        confirmAddressColorChange();

                    } else {
                        Log.e(address.getCountryCode(), "value does not exist in array");
                        country.setText("");
                        city.setText("");
                        area.setText("");
                        street.setText("");
                        notDeliveredText.setVisibility(View.VISIBLE);
                        upgradeConfirmAddressSubmitButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_mapgreybutton));
                        upgradeConfirmAddressSubmitButton.setClickable(false);
                    }
                }
            }
        } catch (IOException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("TAG", "Unable connect to Geocoder", e);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                languageToLoad = "ar";
            } else {
                languageToLoad = "en";
            }
        } else {
            languageToLoad = "ar";
        }
        //  checkLocationPermission();
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            config.setLocale(locale);
            getContext().createConfigurationContext(config);
        } else { //deprecated
            config.locale = locale;
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        }
        Log.e("Locale", Locale.getDefault().getDisplayLanguage());

        if (languageToLoad == "ar") {

            // view = inflater.inflate(R.layout.fragment_upgrade_map_arabic, container, false);
            view = inflater.inflate(R.layout.fragment_upgrade_map_arabic_modify, container, false);
        } else {
            // view = inflater.inflate(R.layout.fragment_upgrade_map, container, false);
            view = inflater.inflate(R.layout.fragment_upgrade_modify, container, false);
        }

        locationMapFragmentView = view.findViewById(R.id.locationMapFragmentView);
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        city = view.findViewById(R.id.city);
        area = view.findViewById(R.id.area);
        street = view.findViewById(R.id.streetname);
        country = view.findViewById(R.id.country);
        mobileNumber = view.findViewById(R.id.mobileNumber);
        firstNameTextInputLayout = view.findViewById(R.id.firstNameTextInputLayout);
        lastNameTextInputLayout = view.findViewById(R.id.lastNameTextInputLayout);
        streetNameTextInputLayout = view.findViewById(R.id.streetNameTextInputLayout);
        areaTextInputLayout = view.findViewById(R.id.areaTextInputLayout);
        cityTextInputLayout = view.findViewById(R.id.cityTextInputLayout);
        mobileNumberInputLayout = view.findViewById(R.id.mobileNumberInputLayout);
        countryNameInputLayout = view.findViewById(R.id.countryNameInputLayout);
        countryNameInputLayout = view.findViewById(R.id.countryNameInputLayout);
        backarrow = view.findViewById(R.id.backarrow);
        backbtn = view.findViewById(R.id.backButton);
        imgLocationIcon = view.findViewById(R.id.imgLocationIcon);
        clear_text = view.findViewById(R.id.clear_text);
        upgradeAddAddressSubmitButton = view.findViewById(R.id.upgradeAddAddressSubmitButton);
        recyclerView = view.findViewById(R.id.autoCompleteRecyclerView);
        place_search = view.findViewById(R.id.place_search);
        upgradeConfirmAddressSubmitButton = view.findViewById(R.id.upgradeConfirmAddressSubmitButton);
        enterAddressManuallyLayout = view.findViewById(R.id.enterAddressManuallyLayout);
        upgradeFindMeOnMap = view.findViewById(R.id.upgradeFindMeOnMap);
        mapHeader = view.findViewById(R.id.mapHeader);
        mapContainer = view.findViewById(R.id.mapContainer);
        addressFields = view.findViewById(R.id.addressFields);
        addressConfirmContainer = view.findViewById(R.id.addressConfirmContainer);
        autoCompleteFrameLayout = view.findViewById(R.id.autoCompleteFrameLayout);
        editAddressButton = view.findViewById(R.id.editAddressButton);
        notDeliveredText = view.findViewById(R.id.notDeliveredText);
        firstName.addTextChangedListener(mTextWatcher);
        lastName.addTextChangedListener(mTextWatcher);
        street.addTextChangedListener(mTextWatcher);
        area.addTextChangedListener(mTextWatcher);
        mobileNumber.addTextChangedListener(mTextWatcher);
        city.addTextChangedListener(mTextWatcher);
        country.addTextChangedListener(mTextWatcher);
        place_search.addTextChangedListener(filterTextWatcher);
        flag = getArguments().getString("flag");
        addressId = getArguments().getString("addressId");
        editFirstName = getArguments().getString("fname");
        editLastName = getArguments().getString("lname");
        editStreet = getArguments().getString("street");
        editArea = getArguments().getString("area");
        editCity = getArguments().getString("city");
        editPhone = getArguments().getString("phone");
        editCountryId = getArguments().getString("countryId");
        editLatitude = getArguments().getString("latitude");
        editLongitude = getArguments().getString("longitude");

        firstNameTextInputLayout.setHelperText(" ");
        lastNameTextInputLayout.setHelperText(" ");
        streetNameTextInputLayout.setHelperText(" ");
        areaTextInputLayout.setHelperText(" ");
        cityTextInputLayout.setHelperText(" ");
        mobileNumberInputLayout.setHelperText(" ");
        countryNameInputLayout.setHelperText(" ");


       /* firstNameTextInputLayout.setHelperText(languageToLoad == "ar" ? "مثلا عبدلله" : "e.g. Abdul");
        lastNameTextInputLayout.setHelperText(languageToLoad == "ar" ? "مثلا خان" : "e.g. Khan");
        streetNameTextInputLayout.setHelperText(languageToLoad == "ar" ? "مثلا عنوان" : "e.g. Address");
        areaTextInputLayout.setHelperText(languageToLoad == "ar" ? "مثلا منطقة" : "e.g. Area");
        cityTextInputLayout.setHelperText(languageToLoad == "ar" ? "مثلا مدينة" : "e.g. City");
        mobileNumberInputLayout.setHelperText(languageToLoad == "ar" ? "مثلا 9898989898" : "e.g. 9898989898");
        countryNameInputLayout.setHelperText(languageToLoad == "ar" ? "مثلا السعودية العربية" : "e.g. Saudi Arabia");*/
        checkFieldsForEmptyValues();
        getcountryDetail();
        //    AutoComplete place google api starts
        // Create a new PlacesClient instance
        if (!Places.isInitialized()) {
            Places.initialize(getContext(), apikey);
        }
        PlacesClient placesClient = Places.createClient(getContext());
        mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAutoCompleteAdapter.setClickListener(this);
        recyclerView.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.notifyDataSetChanged();
        recyclerView.setNestedScrollingEnabled(false);


//            AutoComplete place google api ends
        clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place_search.setText("");
                notDeliveredText.setVisibility(View.GONE);
                upgradeConfirmAddressSubmitButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_mapgreybutton));
                upgradeConfirmAddressSubmitButton.setClickable(false);
            }
        });
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        editAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showAddressFields == true) {
                    manualFirstName = firstName.getText().toString();
                    manualLastName = lastName.getText().toString();
                    manualStreet = street.getText().toString();
                    manualArea = area.getText().toString();
                    manualCity = city.getText().toString();
                    manualPhone = mobileNumber.getText().toString();
                    manualCountry = CountryCodeSelected;
                    mGoogleMap.getUiSettings().setZoomGesturesEnabled(showAddressFields == true ? false : true);
                    mGoogleMap.getUiSettings().setMyLocationButtonEnabled(showAddressFields == true ? false : true);
                    mGoogleMap.getUiSettings().setScrollGesturesEnabled(showAddressFields == true ? false : true);
                    ((LinearLayout.LayoutParams) mapContainer.getLayoutParams()).weight = (float) 1.75;
                    mapHeader.setVisibility(View.GONE);
                    addressFields.setVisibility(View.GONE);
                    editAddressButton.setVisibility(View.GONE);
                    addressConfirmContainer.setVisibility(View.VISIBLE);
                    autoCompleteFrameLayout.setVisibility(View.VISIBLE);
                    imgLocationIcon.setVisibility(View.VISIBLE);
                    showAddressFields = false;
                    mGoogleMap.getUiSettings().setZoomGesturesEnabled(showAddressFields == true ? false : true);
                    mGoogleMap.getUiSettings().setMyLocationButtonEnabled(showAddressFields == true ? false : true);
                    mGoogleMap.getUiSettings().setScrollGesturesEnabled(showAddressFields == true ? false : true);
                }
            }
        });
        enterAddressManuallyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

    openAddressManually();
            }
        });


        upgradeFindMeOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editAddressFields == true) {
                    editStreet = "";
                } else {
                }
                manualFirstName = firstName.getText().toString();
                manualLastName = lastName.getText().toString();
                manualStreet = street.getText().toString();
                manualArea = area.getText().toString();
                manualCity = city.getText().toString();
                manualPhone = mobileNumber.getText().toString();
                manualCountry = CountryCodeSelected;
                mGoogleMap.getUiSettings().setZoomGesturesEnabled(showAddressFields == true ? false : true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(showAddressFields == true ? false : true);
                mGoogleMap.getUiSettings().setScrollGesturesEnabled(showAddressFields == true ? false : true);
                mapContainer.setVisibility(View.VISIBLE);
                ((LinearLayout.LayoutParams) mapContainer.getLayoutParams()).weight = (float) 1.75;
                mapHeader.setVisibility(View.GONE);
                addressFields.setVisibility(View.GONE);
                upgradeFindMeOnMap.setVisibility(View.GONE);
                editAddressButton.setVisibility(View.GONE);
                addressConfirmContainer.setVisibility(View.VISIBLE);
                autoCompleteFrameLayout.setVisibility(View.VISIBLE);
                imgLocationIcon.setVisibility(View.VISIBLE);
                showAddressFields = false;
                mGoogleMap.getUiSettings().setZoomGesturesEnabled(showAddressFields == true ? false : true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(showAddressFields == true ? false : true);
                mGoogleMap.getUiSettings().setScrollGesturesEnabled(showAddressFields == true ? false : true);
                if (isAddressEnteredManually == true) {
                    isAddressEnteredManually = false;
                }
            }
        });
        place_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSuggestions = true;
            }
        });
        country.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (getCountryCodeResponse != null) {
                        countryname = new ArrayList<>();
                        countryCode = new ArrayList<>();
                        placeholder = new ArrayList<>();
                        celcode = new ArrayList<>();
                        for (int i = 0; i < getCountryCodeResponse.data.size(); i++) {
                            String countrycode = getCountryCodeResponse.data.get(i).cel_code/*.replace("+","")*/;
                            countryCode.add(getCountryCodeResponse.data.get(i).code);
                            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                                countryname.add(getCountryCodeResponse.data.get(i).name + " " + "(" + countrycode /*+ "+"*/ + ")");
                            } else {
                                countryname.add("(" + countrycode /*+ "+" */ + ")" + " " + getCountryCodeResponse.data.get(i).name);
                            }
                            placeholder.add(getCountryCodeResponse.data.get(i).placeholder.replaceAll("\\s", ""));
                            celcode.add(getCountryCodeResponse.data.get(i).cel_code);
                        }
                        Collections.reverse(countryname);
                        Collections.reverse(celcode);
                        Collections.reverse(countryCode);
                        Collections.reverse(placeholder);
                        PopupWindow popUp = popupWindowsCountry();
                        popUp.showAtLocation(locationMapFragmentView, Gravity.CENTER, 10, 10);
                    } else {
                        Toast.makeText(getContext(), "PLease select country first", Toast.LENGTH_SHORT).show();
                    }


                }
                return false;
            }
        });
        city.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("country.getText()", country.getText().toString());
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (country.getText().toString().length() > 0) {
                        if (getCitiesResponse != null) {
                            if (cityList.size() > 0) {
                                if (languageToLoad == "ar") {
                                    city.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_down_arrow, 0, 0, 0);
                                } else {
                                    city.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0);
                                }
                                iniatializeCitySelectionDialogue();
                            } else {
                                city.setEnabled(true);
                                city.setFocusable(true);
                                city.setFocusableInTouchMode(true);
                                city.setClickable(true);
                                city.setCompoundDrawables(null, null, null, null);
//                                Toast.makeText(getContext(), "PLease select different country", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "PLease select country first", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        if (flag.equals("editAddress")) {
            editAddressFields = true;
            Log.e("editAddress", editFirstName + " " + editLastName + " " + editStreet + " " + editArea + " " + editCity + " " + editPhone);
            Log.e("editArea", editArea != null ? editArea : "editArea is null");
            firstName.setText(editFirstName);
            lastName.setText(editLastName);
            street.setText(editStreet);
            area.setText(editArea);
            city.setText(editCity);
            mobileNumber.setText(editPhone);
            cityValueFromGoogle = editCity;
            Longitude = editLongitude != null ? editLongitude : "";
            Latitude = editLatitude != null ? editLatitude : "";
            imgLocationIcon.setVisibility(View.VISIBLE);

        } else {
            if (MySharedPreferenceClass.getMyFirstNamePref(getContext()) != null) {
                firstName.setText(MySharedPreferenceClass.getMyFirstNamePref(getContext()));
            }
            if (MySharedPreferenceClass.getMyLastNamePref(getContext()) != null) {
                lastName.setText(MySharedPreferenceClass.getMyLastNamePref(getContext()));
            }
        }

        return view;
    }

    private void iniatializeCitySelectionDialogue() {
        Handler mHandler;
        dialog = new Dialog(getContext());

        if (languageToLoad == "ar") {
            dialog.setContentView(R.layout.city_selection_layout_arabic);
        } else {
            dialog.setContentView(R.layout.city_selection_layout);
        }
        dialog.getWindow();
        mHandler = new Handler();
        if (dialog != null) {
            Window window = dialog.getWindow();

            //  dialog.getWindow().setBackgroundDrawableResource(android.R.color.black);
            if (window != null) {
                WindowManager.LayoutParams params = window.getAttributes();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int maxheight = (displayMetrics.heightPixels * 3) / 4;
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = maxheight;
//                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                params.horizontalMargin = 10f;
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                params.y = 200;
                params.dimAmount = 0;
                params.dimAmount = 0.2f;
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.city_selector_background);
                window.setAttributes(params);
            }

            EditText citySearch = (EditText) dialog.findViewById(R.id.citySearch);
            RecyclerView cityRecyclerView = (RecyclerView) dialog.findViewById(R.id.cityRecyclerView);
            cityRecyclerView.setHasFixedSize(true);
            cityRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            CitySearchAdapter citySearchAdapter = new CitySearchAdapter(getContext(), cityList, cityListBackend, UpgradeMapFragment.this);
            cityRecyclerView.setAdapter(citySearchAdapter);
            citySearchAdapter.notifyDataSetChanged();
            citySearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    filter(cs.toString(), citySearchAdapter);
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                }
            });
        }
        dialog.show();
    }

    public void setCityName(String cityName, String cityNameBackend) {

        selectedCityName = cityNameBackend;
        city.setText(cityName);
        dialog.cancel();
    }

    private void filter(String searchWord, CitySearchAdapter citySearchAdapter) {
        ArrayList<String> filteredList = new ArrayList<>();
        for (String city : cityList) {
            if (city.toLowerCase().contains(searchWord.toLowerCase())) {
                filteredList.add(city);
            }
        }
        citySearchAdapter.filterList(filteredList);
    }

    private PopupWindow popupWindowsCountry() {
        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(getContext());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), languageToLoad == "ar" ? R.layout.checkedradiotextview : R.layout.checkedradiotextviewenglish,
                countryname);
        // the drop down list is a list view
        adapter.setDropDownViewResource(R.layout.popup_layout_english);
        ListView listViewSort = new ListView(getContext());

        // set our adapter and pass our pop up window contents
        listViewSort.setAdapter(adapter);

        // set on item selected
        listViewSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                country.setText(countryname.get(position));
                CelCodeSelected = getCountryCodeResponse.data.get(getCountryCodeResponse.data.size() - 1 - position).cel_code;
                CountryCodeSelected = getCountryCodeResponse.data.get(getCountryCodeResponse.data.size() - 1 - position).code;
                Log.e("CelCodeSelected", CelCodeSelected);
                Log.e("CountryCodeSelected", CountryCodeSelected);
                city.setText("");
                cityValueFromGoogle = "";
                getCityList(CountryCodeSelected);
                popupWindow.dismiss();
            }
        });

        // some other visual settings for popup window
        int popUpTopMarginDp = 100;

        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(locationMapFragmentView, Gravity.CENTER, 10, 200);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int maxheight = (displayMetrics.heightPixels * 3) / 4;
        int width = displayMetrics.widthPixels;
        int heightNow = View.MeasureSpec.makeMeasureSpec(maxheight, View.MeasureSpec.AT_MOST);

        Log.e("width", String.valueOf(width));
        Log.e("maxheight", String.valueOf(maxheight));
        Log.e("height", String.valueOf(height));
        Log.e("heightNow", String.valueOf(heightNow));

        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth((width * 3) / 4);
        // set the listview as popup content
        popupWindow.setContentView(listViewSort);

        return popupWindow;
    }

    private void getCityList(String countryCodeValue) {
//        parentDialogListener.showProgressDialog();

        Log.e("getCityName", cityValueFromGoogle);
        cityList = new ArrayList<>();
        cityList.clear();
        cityListBackend = new ArrayList<>();
        cityListBackend.clear();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
        Call<GetCitiesResponse> call = languageToLoad == "ar" ? apiService.getCitiesList(countryCodeValue != null ? "https://upgrade.eoutlet.com/rest/ar/V1/api/getcities/" + countryCodeValue : "https://upgrade.eoutlet.com/rest/ar/V1/api/getcities/IN") : apiService.getCitiesList(countryCodeValue != null ? "https://upgrade.eoutlet.com/rest/en/V1/api/getcities/" + countryCodeValue : "https://upgrade.eoutlet.com/rest/en/V1/api/getcities/IN");
        call.enqueue(new Callback<GetCitiesResponse>() {
            @Override
            public void onResponse(Call<GetCitiesResponse> call, Response<GetCitiesResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    getCitiesResponse = response.body();
                    if (getCitiesResponse.response.equalsIgnoreCase("success")) {
                        for (int i = 0; i < response.body().data.size(); i++) {
                            cityList.add(response.body().data.get(i).frontend);
                            cityListBackend.add(response.body().data.get(i).backend);
                        }

                       // if (isAddressEnteredManually == false) {
                            if (cityList.size() > 0) {

                                if (languageToLoad == "ar") {
                                    city.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_down_arrow, 0, 0, 0);
                                } else {
                                    city.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0);
                                }

                                for (int i = 0; i < cityList.size(); i++) {
                                    if (cityList.get(i).toLowerCase().equalsIgnoreCase(cityValueFromGoogle)) {
//                                    if (cityValueFromGoogle.toLowerCase().contains(cityList.get(i).toLowerCase())) {
                                        city.setText(getCitiesResponse.data.get(i).frontend);
                                        selectedCityName = getCitiesResponse.data.get(i).backend;
//                                        confirmAddressColorChange();
//                                        notDeliveredText.setVisibility(View.GONE);
                                        break;
                                    } else {
                                        city.setText("");
//                                        notDeliveredText.setVisibility(View.VISIBLE);
//                                        upgradeConfirmAddressSubmitButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_mapgreybutton));
//                                        upgradeConfirmAddressSubmitButton.setClickable(false);
                                    }
                                }
                            } else {
                                city.setCompoundDrawables(null, null, null, null);
                                city.setText(cityValueFromGoogle);
                            }
                       // }
                        //else {
//                            if (manualCity != "") {
//                                Log.e("manualCity", "not null");
//                                city.setText(manualCity);
//                            } else {
//                                Log.e("manualCity", " null");
//                                city.setText("");
//                            }
                           if( !flag.equalsIgnoreCase("editAddress"))
                            city.setText("");
                       // }

                    } else {
                        city.setCompoundDrawables(null, null, null, null);
                        if( !flag.equalsIgnoreCase("editAddress"))


                        city.setText("");
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                    city.setText("");
                    Toast.makeText(getContext(), languageToLoad == "ar" ? "حدث خطأ - يرجي اعادة المحاولة"
                            : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetCitiesResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                Toast.makeText(getContext(), languageToLoad == "ar" ? "حدث خطأ - يرجي اعادة المحاولة"
                        : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            parentDialogListener = (ParentDialogsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " Activity's Parent should be Parent Activity");
        }
    }

    public void getcountryDetail() {
//        parentDialogListener.showProgressDialog();
        countryname = new ArrayList<>();
        countryCode = new ArrayList<>();
        placeholder = new ArrayList<>();
        celcode = new ArrayList<>();
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
                    getCountryCodeResponse = response.body();
                    parentDialogListener.hideProgressDialog();
                    for (int i = 0; i < response.body().data.size(); i++) {
                        String countrycode = response.body().data.get(i).cel_code/*.replace("+","")*/;
                        countryCode.add(response.body().data.get(i).code);
                        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                            countryname.add(response.body().data.get(i).name + " " + "(" + countrycode /*+ "+"*/ + ")");
                        } else {
                            countryname.add("(" + countrycode /*+ "+" */ + ")" + " " + response.body().data.get(i).name);
                        }
                        placeholder.add(response.body().data.get(i).placeholder.replaceAll("\\s", ""));
                        celcode.add(response.body().data.get(i).cel_code);
                    }
                    Collections.reverse(countryname);
                    Collections.reverse(celcode);
                    Collections.reverse(countryCode);
                    Collections.reverse(placeholder);
                    if (flag.equals("editAddress")) {
                        if (countryCode.size() > 0) {
                            for (int i = 0; i < countryCode.size(); i++) {
                                if (editCountryId.equalsIgnoreCase(countryCode.get(i))) {
                                    country.setText(countryname.get(i));
                                    CelCodeSelected = getCountryCodeResponse.data.get(getCountryCodeResponse.data.size() - 1 - i).cel_code;
                                    CountryCodeSelected = getCountryCodeResponse.data.get(getCountryCodeResponse.data.size() - 1 - i).code;
                                    Log.e("CountryCodeSelected", CountryCodeSelected + " for editAddress");
                                    getCityList(CountryCodeSelected);
                                }
                            }
                        }
                    }

                }

                openAddressManually();
            }

            @Override
            public void onFailure(Call<GetCountryCode> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });
    }

    //      function to save address
    private void saveAddress() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        JsonObject jsonObject = new JsonObject();
        JsonObject param = new JsonObject();
        if(!city.getText().toString().equals("")){
            selectedCityName  = city.getText().toString();

        }


        param.addProperty("customer_id", Integer.parseInt(MySharedPreferenceClass.getMyUserId(getContext())));
        param.addProperty("firstname", firstName.getText().toString());
        param.addProperty("lastname", lastName.getText().toString());
        param.addProperty("country_id",CountryCodeSelected);
        param.addProperty("city", selectedCityName);
        param.addProperty("mobilenumber", CelCodeSelected + mobileNumber.getText().toString());
        param.addProperty("street", street.getText().toString());
        param.addProperty("area", area.getText().toString());
        param.addProperty("latitude", Latitude);
        param.addProperty("longitude", Longitude);
        jsonObject.add("param", param);
        Gson gson = new Gson();
        String bodyInStringFormat = gson.toJson(jsonObject);
        Log.e("bodyInStringFormat", bodyInStringFormat);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (bodyInStringFormat));
        Call<UpgradeAddAddressResponse> call = languageToLoad == "ar" ? apiService.getUpgradeAddAddressArabic(body) : apiService.getUpgradeAddAddress(body);
        call.enqueue(new Callback<UpgradeAddAddressResponse>() {
            @Override
            public void onResponse(Call<UpgradeAddAddressResponse> call, Response<UpgradeAddAddressResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().getResponse().equals("success")) {
                        Log.e("add address", "success");
                        moengageAddAddressEvent(Latitude, Longitude, CelCodeSelected + mobileNumber.getText().toString());
                        Toast.makeText(getContext(), languageToLoad == "ar" ? "تم إضافة العنوان بنجاح" :
                                "Address Added Successfully", Toast.LENGTH_LONG).show();


                        if (flag.equalsIgnoreCase("fromCheckout")) {
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                                fm.popBackStack();
                            }
                            MySharedPreferenceClass.setfirstAdressname(view.getContext(), response.body().getData().getFirstname());
                            MySharedPreferenceClass.setlastAdressname(view.getContext(), response.body().getData().getLastname());
                            MySharedPreferenceClass.setAddressname(view.getContext(), response.body().getData().getFirstname() + " " + response.body().getData().getLastname());
                            MySharedPreferenceClass.setCityname(view.getContext(), response.body().getData().getCity());
                            MySharedPreferenceClass.setStreetName(view.getContext(), response.body().getData().getStreet());
                            MySharedPreferenceClass.setCountryname(view.getContext(), response.body().getData().getCountry());
                            MySharedPreferenceClass.setAddressphone(view.getContext(), response.body().getData().getTelephone());
                            MySharedPreferenceClass.setCountryId(view.getContext(), response.body().getData().getCountry_id());
                            MySharedPreferenceClass.setSelecteAddressdId(view.getContext(), response.body().getData().getAddress_id());

                            Fragment prFrag = new CheckOutConfirmationfragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("coupencode", " ");

                            bundle.putString("discounntamount", " ");
                            System.out.println("discounntamount" + " ");

                            bundle.putString("giftwrapping", " ");


                            prFrag.setArguments(bundle);

                            getFragmentManager()
                                    .beginTransaction().
                                    /*setCustomAnimations( R.anim.slide_to_left, R.anim.slide_to_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right )
                                    .*/addToBackStack(null)
                                    .replace(R.id.cartcontainer, prFrag)
                                    .commit();

                            // getAddressList();


                        } else {
                            /*MySharedPreferenceClass.setfirstAdressname(view.getContext(), response.body().getData().getFirstname());
                            MySharedPreferenceClass.setlastAdressname(view.getContext(), response.body().getData().getLastname());
                            MySharedPreferenceClass.setAddressname(view.getContext(), response.body().getData().getFirstname() + " " + response.body().getData().getLastname());
                            MySharedPreferenceClass.setCityname(view.getContext(), response.body().getData().getCity());
                            MySharedPreferenceClass.setStreetName(view.getContext(), response.body().getData().getStreet());
                            MySharedPreferenceClass.setCountryname(view.getContext(), response.body().getData().getCountry());
                            MySharedPreferenceClass.setAddressphone(view.getContext(), response.body().getData().getTelephone());
                            MySharedPreferenceClass.setCountryId(view.getContext(), response.body().getData().getCountry_id());
                            MySharedPreferenceClass.setSelecteAddressdId(view.getContext(), response.body().getData().getAddress_id());*/

                            getActivity().getSupportFragmentManager().popBackStack();
                        }


                    } else {
                        Log.e("add address", "failed");
                    }
                } else {
                    Toast.makeText(getContext(), languageToLoad == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                            "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpgradeAddAddressResponse> call, Throwable t) {
                Toast.makeText(getContext(), languageToLoad == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
            }
        });
    }


    //      function to save editted address
    private void saveEdittedAddress() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        JsonObject jsonObject = new JsonObject();
        JsonObject param = new JsonObject();
        param.addProperty("address_id", Integer.parseInt(addressId));
        param.addProperty("firstname", firstName.getText().toString());
        param.addProperty("lastname", lastName.getText().toString());
        param.addProperty("country_id", CountryCodeSelected);
        if(!city.getText().toString().equals("")){
            selectedCityName  = city.getText().toString();

        }

        param.addProperty("city", selectedCityName);
        param.addProperty("mobilenumber", CelCodeSelected + mobileNumber.getText().toString());
        param.addProperty("street", street.getText().toString());
        param.addProperty("area", area.getText().toString());
        param.addProperty("latitude", Latitude);
        param.addProperty("longitude", Longitude);
        jsonObject.add("param", param);
        Gson gson = new Gson();
        String bodyInStringFormat = gson.toJson(jsonObject);
        Log.e("bodyInStringFormat", bodyInStringFormat);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (bodyInStringFormat));
        Call<EditAddressResponse> call = languageToLoad == "ar" ? apiService.editAddressArabic(body) : apiService.editAddress(body);
        call.enqueue(new Callback<EditAddressResponse>() {
            @Override
            public void onResponse(Call<EditAddressResponse> call, Response<EditAddressResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().response.equals("success")) {
                        Log.e("change address", "success");
                        moengageAddAddressEvent(Latitude, Longitude, CelCodeSelected + mobileNumber.getText().toString());
                        Toast.makeText(getContext(), languageToLoad == "ar" ? "تم تبديل العنوان بنجاح" : "Address Changed Successfully", Toast.LENGTH_LONG).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    } else {
                        Log.e("change address", "failed");
                    }
                } else {

                    Toast.makeText(getContext(), languageToLoad == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                            "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<EditAddressResponse> call, Throwable t) {
                Toast.makeText(getContext(), languageToLoad == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
            }
        });
    }

    public void moengageAddAddressEvent(String lat, String longitude, String phone) {
        if (lat != "" || longitude != "") {
            MoEHelper.getInstance(getApplicationContext()).setUserLocation(Double.parseDouble(lat), Double.parseDouble(longitude));
        }
        MoEHelper.getInstance(getApplicationContext()).setNumber(phone);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            Log.e("onViewCreated", "mapFragment");
            mapFragment.getMapAsync(callback);
            View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            rlp.setMargins(0, 0, 30, 30);
        }
    }

    public static void MyOnRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: true");
        } else {
            Log.d(TAG, "permission: false");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.e("onRequestPermissions", String.valueOf(requestCode));
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        permissionflag = "frompermission";

                        if (!isGPSEnabled(getContext())) {

                            // requestopenGPS();
                        } else {
                            if (flag.equals("editAddress")) {
                                Log.e("getCurrentLocation", "flag editAddress");
                                if (Latitude.matches("") || Longitude.matches("")) {
                                    Log.e("flag editAddress", "editLatitude or editLongitude is empty");
                                    showAddressFields = true;
                                    if (mGoogleMap != null) {
                                        mGoogleMap.getUiSettings().setZoomGesturesEnabled(showAddressFields == true ? false : true);
                                        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(showAddressFields == true ? false : true);
                                        mGoogleMap.getUiSettings().setScrollGesturesEnabled(showAddressFields == true ? false : true);
                                        ((LinearLayout.LayoutParams) mapContainer.getLayoutParams()).weight = (float) 0.45;
                                    }
                                    mapHeader.setVisibility(View.VISIBLE);
                                    addressFields.setVisibility(View.VISIBLE);
                                    editAddressButton.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                    addressConfirmContainer.setVisibility(View.GONE);
                                    autoCompleteFrameLayout.setVisibility(View.GONE);
//                imgLocationIcon.setVisibility(View.GONE);
                                    imgLocationIcon.setVisibility(View.VISIBLE);
                                    confirmAddressColorChange();
                                } else {
                                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.

                                        return;
                                    }

                                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

                                    if (mGoogleMap != null) {
                                        mGoogleMap.setMyLocationEnabled(true);
                                    }
                                }
                            } else {
                                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

                                if (mGoogleMap != null) {
                                    mGoogleMap.setMyLocationEnabled(true);
                                }
                            }
                        }

                        //mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        // mGoogleMap.setMyLocationEnabled(true);


                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getContext(), "Please provide location permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void checkLocationPermission() {
        Log.e("checkLocationPermission", "checkLocationPermission");
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
           /* if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {*/
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
//                ActivityCompat.requestPermissions(getActivity(), new String[]{
//                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
//                new AlertDialog.Builder(getContext())
//                        .setTitle("Location Permission Needed")
//                        .setMessage("This app needs the Location permission, please accept to use location functionality")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //Prompt the user once explanation has been shown
//                                ActivityCompat.requestPermissions(getActivity(), new String[]{
//                                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                                        Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
//                            }
//                        })
//                        .setCancelable(false)
//                        .create()
//                        .show();
        } else {
            // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(getActivity(), new String[]{
//                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
        //}
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

    }


    public void requestopenGPS() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(UpgradeMapFragment.this)
                .addOnConnectionFailedListener(UpgradeMapFragment.this).build();
        mGoogleApiClient.connect();
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(20);


        //        locationRequest.setInterval(30 * 1000);
//        locationRequest.setFastestInterval(5 * 1000);

        /*LocationManager lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            getApplicationContext().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        }*/


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

        result.setResultCallback(UpgradeMapFragment.this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResume() {
        super.onResume();

        /*if (permissionflag.equals("frompermission")) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mGoogleMap.setMyLocationEnabled(true);



        } else {
            requestopenGPS();
        }
    }*/
    }

    public boolean isGPSEnabled(Context mContext) {
        LocationManager locationManager = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onResult(@NonNull Result result) {
        Log.e("onResult", result.toString());
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

                    status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);

                } catch (IntentSender.SendIntentException e) {
                }
                break;

            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                // Location settings are unavailable so not possible to show any dialog now
                break;
        }

    }


    //    Edit Text field watcher
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldsForEmptyValues();

        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
        }
    };
//    Edit Text field watcher

    //    function to check if values are empty
    void checkFieldsForEmptyValues() {
        if (firstName.getText().toString().equals("") || lastName.getText().toString().equals("") || street.getText().toString().equals("") || area.getText().toString().equals("") || city.getText().toString().equals("") || mobileNumber.getText().toString().equals("")) {
            upgradeAddAddressSubmitButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_mapgreybutton));
            upgradeAddAddressSubmitButton.setClickable(false);


        } else {
           upgradeAddAddressSubmitButton.setClickable(true);
            upgradeAddAddressSubmitButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_mapbutton));
             upgradeAddAddressSubmitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isNumberVerified();
                }
            });
        }
    }

    private void isNumberVerified() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("customer_id", Integer.parseInt(MySharedPreferenceClass.getMyUserId(getContext())));
        map1.put("mobilenumber", CelCodeSelected + mobileNumber.getText().toString());

        mainparam.put("param", map1);
        Call<NumberVerificationResponse> call = languageToLoad == "ar" ? apiService.verifyNumberArabic(mainparam) : apiService.verifyNumber(mainparam);
        call.enqueue(new Callback<NumberVerificationResponse>() {
            @Override
            public void onResponse(Call<NumberVerificationResponse> call, Response<NumberVerificationResponse> response) {
                parentDialogListener.hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().getMessage().equalsIgnoreCase("success")) {
                        if (response.body().getData() == 0) {
                            sendOtpForVerification();
                        } else {
                            if (flag.equals("editAddress")) {
                                saveEdittedAddress();
                            } else {
                                saveAddress();
                            }
                        }

                    } else {
                        Log.e("Num Verification ", "not successful");
                        Toast.makeText(getContext(), response.body().getData(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                    Toast.makeText(getContext(), languageToLoad == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NumberVerificationResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("Num Verification ", t.toString());
                Toast.makeText(getContext(), languageToLoad == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void sendOtpForVerification() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<SendOtpResponse> call = apiService.sendOtp("https://upgrade.eoutlet.com/vsms/app/send?mobile=" + CelCodeSelected + mobileNumber.getText().toString() + "&resend=0" + "&customer_id=" + MySharedPreferenceClass.getMyUserId(getContext()));
        call.enqueue(new Callback<SendOtpResponse>() {
            @Override
            public void onResponse(Call<SendOtpResponse> call, Response<SendOtpResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().success == true) {
                        Toast.makeText(getContext(), languageToLoad == "ar" ? "تم ارسال الكود بنجاح" :
                                "The code has been sent successfully", Toast.LENGTH_SHORT).show();
                        Log.e("OTP send", "success");
                        DialogFragment prFrag = new SaveAddressVerifyOtp();
                        Bundle bund = new Bundle();
                        bund.putString("mobile", CelCodeSelected + mobileNumber.getText().toString());
                        prFrag.setArguments(bund);
                        prFrag.setTargetFragment(UpgradeMapFragment.this, 1002);
                        prFrag.show(getFragmentManager(), "signup");
                    } else {
                        parentDialogListener.hideProgressDialog();
                        Log.e("OTP send", "failed");
                        Toast.makeText(getContext(), languageToLoad == "ar" ? "فشل الارسال -اعادة المحاولة" :
                                "Send Failed - Retry", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                    Toast.makeText(getContext(), languageToLoad == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SendOtpResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
                Toast.makeText(getContext(), languageToLoad == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQUEST_CHECK_SETTINGS) {

            if (resultCode == RESULT_OK) {

                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    if (Build.VERSION.SDK_INT >= 26) {
                        ft.setReorderingAllowed(false);
                    }
                    getFragmentManager()
                            .beginTransaction()
                            .detach(this)
                            .attach(this)
                            .commit();
                    // Toast.makeText(getApplicationContext(), "GPS enabled", Toast.LENGTH_LONG).show();

                } else {

                    // Toast.makeText(getApplicationContext(), "GPS is not enabled", Toast.LENGTH_LONG).show();
                }

            }
        } else if (requestCode == 1002) {
            if (flag.equals("editAddress")) {
                saveEdittedAddress();
            } else {
                saveAddress();
            }
        }
    }

    /* @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {


         Toast.makeText(getApplicationContext(), "GPS enabled from Fragment", Toast.LENGTH_LONG).show();
         if (requestCode == REQUEST_CHECK_SETTINGS) {

             if (resultCode == RESULT_OK) {

                 if (ContextCompat.checkSelfPermission(getContext(),
                         Manifest.permission.ACCESS_FINE_LOCATION)
                         == PackageManager.PERMISSION_GRANTED) {
                      *//* FragmentTransaction ft = getFragmentManager().beginTransaction();
                    if (Build.VERSION.SDK_INT >= 26) {
                        ft.setReorderingAllowed(false);
                    }
                    ft.detach(this).attach(this).commit();*//*
                   Toast.makeText(getApplicationContext(), "GPS enabled", Toast.LENGTH_LONG).show();
                   LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                   if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {

                       mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                           @Override
                           public void onComplete(@NonNull Task<Location> task) {

                               Location location = task.getResult();
                               if (location != null) {
                                   Log.e("Location-->>", " not null");
                                   LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                   Toast.makeText(getApplicationContext(), latLng + "map", Toast.LENGTH_SHORT).show();
                                   getGeoCoderLocationDetails(latLng);


                               } else {
                                   Log.e("Location-->>", "  null");
                                   mLocationRequest = new LocationRequest();
                                   int minimumDistanceBetweenUpdates = 20;
                                   mLocationRequest.setSmallestDisplacement(minimumDistanceBetweenUpdates);
                                   mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                   if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                       // TODO: Consider calling
                                       //    ActivityCompat#requestPermissions
                                       // here to request the missing permissions, and then overriding
                                       //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                       //                                          int[] grantResults)
                                       // to handle the case where the user grants the permission. See the documentation
                                       // for ActivityCompat#requestPermissions for more details.
                                       return;
                                   }
                                   mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                                   mGoogleMap.setMyLocationEnabled(true);
                               }

                           }
                       });
                   } else {

                   }

               } else {

                   Toast.makeText(getApplicationContext(), "GPS is not enabled", Toast.LENGTH_LONG).show();
               }

           } else if (requestCode == 1002) {
               if (flag.equals("editAddress")) {
                   saveEdittedAddress();
               } else {
                   saveAddress();
               }
           }
       }
   }*/
    //    function to check if values are empty
    //   Autocomplete place google text field watcher
    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            if (placeSelected == true) {
                if (recyclerView.getVisibility() == View.VISIBLE) {
                    recyclerView.setVisibility(View.GONE);
                    placeSelected = false;
//                    confirmAddressColorChange();
                }
            } else {
                if (showSuggestions == false) {
                    recyclerView.setVisibility(View.GONE);
                    showSuggestions = true;
//                    confirmAddressColorChange();
                } else {
                    if (!s.toString().equals("")) {
                        mAutoCompleteAdapter.getFilter().filter(s.toString());
                        if (recyclerView.getVisibility() == View.GONE) {
                            recyclerView.setVisibility(View.VISIBLE);
//                            confirmAddressColorChange();
//                            upgradeConfirmAddressSubmitButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_mapgreybutton));
                            upgradeConfirmAddressSubmitButton.setClickable(false);
                        }
                    } else {
                        if (recyclerView.getVisibility() == View.VISIBLE) {
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                }
            }

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };

    private void confirmAddressColorChange() {
        upgradeConfirmAddressSubmitButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_mapbutton));
        upgradeConfirmAddressSubmitButton.setClickable(true);
        upgradeConfirmAddressSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddressFields = true;
                mGoogleMap.getUiSettings().setZoomGesturesEnabled(showAddressFields == true ? false : true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(showAddressFields == true ? false : true);
                mGoogleMap.getUiSettings().setScrollGesturesEnabled(showAddressFields == true ? false : true);
                ((LinearLayout.LayoutParams) mapContainer.getLayoutParams()).weight = (float) 0.45;
                mapHeader.setVisibility(View.VISIBLE);
                addressFields.setVisibility(View.VISIBLE);
                editAddressButton.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                addressConfirmContainer.setVisibility(View.GONE);
                autoCompleteFrameLayout.setVisibility(View.GONE);
//                imgLocationIcon.setVisibility(View.GONE);
                imgLocationIcon.setVisibility(View.VISIBLE);
            }
        });
    }
//    Autocomplete place google text field watcher

    //    On click action of selecting place in autocomplete place google api
    @Override
    public void click(Place place) {
        try {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }
        placeSelected = true;
        placeSearch = true;
        locationSelected = true;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 18.0f));
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(showAddressFields == true ? false : true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(showAddressFields == true ? false : true);
        LatLng latLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
        getGeoCoderLocationDetails(latLng);

    }

    //    function to get list of address in eoutlet upgrade
    private void getAddressList() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<UpgradeGetAddressResponse> call = languageToLoad == "ar" ? apiService.getUpgradedAddress(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "addresslist/" + MySharedPreferenceClass.getMyUserId(view.getContext()))
                : apiService.getUpgradedAddress(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "addresslist/" + MySharedPreferenceClass.getMyUserId(view.getContext()));
        call.enqueue(new Callback<UpgradeGetAddressResponse>() {
            @Override
            public void onResponse(Call<UpgradeGetAddressResponse> call, Response<UpgradeGetAddressResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().getResponse().equals("success")) {

                        Log.e("get address", "success");
                        upgradedGetAddressLists.clear();

                        upgradedGetAddressLists = response.body().getData();
                        if (upgradedGetAddressLists.size() == 1) {
                            MySharedPreferenceClass.setfirstAdressname(view.getContext(), upgradedGetAddressLists.get(0).getFirstname());
                            MySharedPreferenceClass.setlastAdressname(view.getContext(), upgradedGetAddressLists.get(0).getLastname());
                            MySharedPreferenceClass.setAddressname(view.getContext(), upgradedGetAddressLists.get(0).getFirstname() + " " + upgradedGetAddressLists.get(0).getLastname());
                            MySharedPreferenceClass.setCityname(view.getContext(), upgradedGetAddressLists.get(0).getCity());
                            MySharedPreferenceClass.setStreetName(view.getContext(), upgradedGetAddressLists.get(0).getStreet());
                            MySharedPreferenceClass.setCountryname(view.getContext(), upgradedGetAddressLists.get(0).getCountry());
                            MySharedPreferenceClass.setAddressphone(view.getContext(), upgradedGetAddressLists.get(0).getTelephone());
                            MySharedPreferenceClass.setCountryId(view.getContext(), upgradedGetAddressLists.get(0).getCountry_id());
                            MySharedPreferenceClass.setSelecteAddressdId(view.getContext(), upgradedGetAddressLists.get(0).getAddress_id());

                        } else {
                            MySharedPreferenceClass.setfirstAdressname(view.getContext(), upgradedGetAddressLists.get(upgradedGetAddressLists.size() - 1).getFirstname());
                            MySharedPreferenceClass.setlastAdressname(view.getContext(), upgradedGetAddressLists.get(upgradedGetAddressLists.size() - 1).getLastname());
                            MySharedPreferenceClass.setAddressname(view.getContext(), upgradedGetAddressLists.get(upgradedGetAddressLists.size() - 1).getFirstname() + " " + upgradedGetAddressLists.get(upgradedGetAddressLists.size() - 1).getLastname());
                            MySharedPreferenceClass.setCityname(view.getContext(), upgradedGetAddressLists.get(upgradedGetAddressLists.size() - 1).getCity());
                            MySharedPreferenceClass.setStreetName(view.getContext(), upgradedGetAddressLists.get(upgradedGetAddressLists.size() - 1).getStreet());
                            MySharedPreferenceClass.setCountryname(view.getContext(), upgradedGetAddressLists.get(upgradedGetAddressLists.size() - 1).getCountry());
                            MySharedPreferenceClass.setAddressphone(view.getContext(), upgradedGetAddressLists.get(upgradedGetAddressLists.size() - 1).getTelephone());
                            MySharedPreferenceClass.setCountryId(view.getContext(), upgradedGetAddressLists.get(upgradedGetAddressLists.size() - 1).getCountry_id());
                            MySharedPreferenceClass.setSelecteAddressdId(view.getContext(), upgradedGetAddressLists.get(upgradedGetAddressLists.size() - 1).getAddress_id());

                        }
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                            fm.popBackStack();
                        }
/*
                        if (getActivity().getFragmentManager() != null) {
                            if (getActivity().getFragmentManager().getBackStackEntryCount() > 0) {
                                while (getActivity().getFragmentManager().getBackStackEntryCount() > 1) {

                                    getActivity().getFragmentManager().popBackStackImmediate();
                                }
                            }

                        }*/
                        Fragment prFrag = new CheckOutConfirmationfragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("coupencode", " ");

                        bundle.putString("discounntamount", " ");
                        System.out.println("discounntamount" + " ");

                        bundle.putString("giftwrapping", " ");


                        prFrag.setArguments(bundle);

                        getFragmentManager()
                                .beginTransaction().
                                /*setCustomAnimations( R.anim.slide_to_left, R.anim.slide_to_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right )
                                .*/addToBackStack(null)
                                .replace(R.id.cartcontainer, prFrag)
                                .commit();


                    } else {


                        Log.e("get address", "failed");
                    }
                } else {
                    Toast.makeText(getContext(), languageToLoad == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                            "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
                /*if (value.equals("fromCheckout")) {

                    cartreference.setUserHintcall();
                    getFragmentManager().popBackStackImmediate();

                }*/
            }

            @Override
            public void onFailure(Call<UpgradeGetAddressResponse> call, Throwable t) {
                Toast.makeText(getContext(), languageToLoad == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
            }
        });
    }

    //    On click action of selecting place in autocomplete place google api
    public void openAddressManually() {


        isAddressEnteredManually = true;
        showAddressFields = true;
        /*mGoogleMap.getUiSettings().setZoomGesturesEnabled(showAddressFields == true ? false : true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(showAddressFields == true ? false : true);
        mGoogleMap.getUiSettings().setScrollGesturesEnabled(showAddressFields == true ? false : true);*/


//                ((LinearLayout.LayoutParams) mapContainer.getLayoutParams()).weight = 1;
        mapContainer.setVisibility(View.GONE);
        ((LinearLayout.LayoutParams) addressFields.getLayoutParams()).weight = (float) 2;
       // upgradeFindMeOnMap.setVisibility(View.VISIBLE);
        addressFields.setVisibility(View.VISIBLE);
        editAddressButton.setVisibility(View.VISIBLE);
        addressConfirmContainer.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        autoCompleteFrameLayout.setVisibility(View.GONE);
//                imgLocationIcon.setVisibility(View.GONE);
        imgLocationIcon.setVisibility(View.VISIBLE);
        if(!flag.equalsIgnoreCase("editAddress")) {
            firstName.setText(MySharedPreferenceClass.getMyFirstNamePref(getApplicationContext()));
            lastName.setText(MySharedPreferenceClass.getMyLastNamePref(getApplicationContext()));
            street.setText("");
            area.setText("");
            mobileNumber.setText("");
            city.setText("");


            Log.e("Electecountry", MySharedPreferenceClass.getSelectedCountryCode(getApplicationContext()));
            if (MySharedPreferenceClass.getSelectedCountryCode(getApplicationContext()) != null) {
                for (int i = 0; i < getCountryCodeResponse.data.size(); i++) {
                    if (getCountryCodeResponse.data.get(i).code.equalsIgnoreCase(MySharedPreferenceClass.getSelectedCountryCode(getApplicationContext()))) {
                        CountryCodeSelected = getCountryCodeResponse.data.get(i).code;
                        CelCodeSelected = getCountryCodeResponse.data.get(i).cel_code;
                        Log.e("CelCodeSelectedManual", CelCodeSelected);
                        country.setText(countryname.get(countryname.size() - 1 - i));

                        getCityList(getCountryCodeResponse.data.get(i).code);

                    }


                }


            }

        }
        //country.setText("");

        Longitude = "";
        Latitude = "";
    }
}
