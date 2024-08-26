package com.eoutlet.Eoutlet.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.adpters.PlacesAutoCompleteAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.listener.CartListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.EditAddressResponse;
import com.eoutlet.Eoutlet.pojo.GetCountryCode;
import com.eoutlet.Eoutlet.pojo.SendOtpResponse;
import com.eoutlet.Eoutlet.pojo.UpgradeAddAddressResponse;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

public class LocationMapFragment extends Fragment implements PlacesAutoCompleteAdapter.ClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback {
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
    String CountryCodeSelected = "";
    String CelCodeSelected = "";
    private TextView firstName, lastName, city, street, mobileNumber;
    private TextInputLayout firstNameTextInputLayout, lastNameTextInputLayout, streetNameTextInputLayout, cityTextInputLayout, mobileNumberInputLayout, countryNameInputLayout;
    private FrameLayout upgradeAddAddressSubmitButton;
    View view;
    String longitude = "", latitude = "";
    Geocoder geocoder, geocoder1;
    ImageView clear_text, backarrow;
    boolean placeSelected = false;
    boolean placeSearch = false;
    String languageToLoad;
    private Spinner countryspinner;
    private List<String> countryname;
    private List<String> countryCode;
    private List<String> celcode;
    private List<String> placeholder;
    private String apikey = "AIzaSyDBfKVOx5k6qGpWO2ZyeVPP8Rnpb1RLjyo";
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    List<Place.Field> fields;
    GetCountryCode getCountryCodeResponse;
    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;
    private RecyclerView recyclerView;
    AutoCompleteTextView place_search;
    private String countryName;
    private int selectedposition = 0;
    boolean setCountryId = false;
    boolean isCurrentCountryId = false;
    String currentCountryId;
    NestedScrollView locationMapFragmentView;


    private String flag, addressId, editFirstName, editLastName, editStreet, editCity, editPhone, editCountryId;


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

//           Marker drag function
            googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker arg0) {
                    // TODO Auto-generated method stub
                    Log.d("System out", "onMarkerDragStart..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);
                }

                @SuppressWarnings("unchecked")
                @Override
                public void onMarkerDragEnd(Marker arg0) {
                    // TODO Auto-generated method stub
                    Log.d("System out", "onMarkerDragEnd..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);

                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
                }

                @Override
                public void onMarkerDrag(Marker arg0) {
                    // TODO Auto-generated method stub
                    Log.i("System out", "onMarkerDrag...");
                }
            });
//            Marker drag function


            final LocationManager manager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                requestopenGPS();
                Toast.makeText(getContext(), "GPS is not enabled! ", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getContext(), "GPS is enabled!", Toast.LENGTH_LONG).show();
            }
            mGoogleMap = googleMap;
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            mLocationRequest = new LocationRequest();
            int minimumDistanceBetweenUpdates = 20;
            mLocationRequest.setSmallestDisplacement(minimumDistanceBetweenUpdates);
//            mLocationRequest.setInterval(120000); // two minute interval
//            mLocationRequest.setFastestInterval(120000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    //Location Permission already granted

                    if (placeSearch == false) {
                        if (flag.equals("editAddress")) {
                            Log.e("getCurrentLocation", "flag editAddress");
                        } else {
                            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                            mGoogleMap.setMyLocationEnabled(true);
                        }
                    }
                } else {
                    //Request Location Permission
                    checkLocationPermission();
                }
            } else {
                if (placeSearch == false) {
                    if (flag.equals("editAddress")) {
                        Log.e("getCurrentLocation", "flag editAddress");
                    } else {
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mGoogleMap.setMyLocationEnabled(true);
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
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //Place current location marker
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.draggable(true);
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

                //move map camera
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
                mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
                geocoder = new Geocoder(getContext(), Locale.getDefault());
                Log.e("geocoder Locale", Locale.getDefault().getDisplayLanguage());
                String result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            location.getLatitude(), location.getLongitude(), 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)).append("\n");
                        }
                        sb.append(address.getPremises()).append(", ");

                        //sb.append(address.getSubAdminArea()).append(", ");

                        sb.append(address.getSubLocality()).append(", ");
                        sb.append(address.getLocality()).append(", ");
                        sb.append(address.getAdminArea()).append(", ");
                        sb.append(address.getPostalCode()).append(", ");
                        sb.append(address.getCountryName());
                        //sb.append(address.getPhone()).append(", ");


                        result = sb.toString();
                        //Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();

                        city.setText(address.getLocality());
                        street.setText(address.getSubLocality());
                        currentCountryId = address.getCountryCode();
                        isCurrentCountryId = true;

                        Log.d("Full Address is", result);
                    }
                } catch (IOException e) {
                    Toast.makeText(getContext(), "fromexception", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "Unable connect to Geocoder", e);
                }
            }
        }
    };

    public LocationMapFragment(NewAddressFragmentUpgrade cl) {
        cartreference = cl;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
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
        config.setLocale(locale);
        getContext().createConfigurationContext(config);

        if (languageToLoad == "ar") {
            view = inflater.inflate(R.layout.fragment_location_map_arabic, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_location_map, container, false);
        }

        locationMapFragmentView = view.findViewById(R.id.locationMapFragmentView);
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        city = view.findViewById(R.id.city);
        street = view.findViewById(R.id.streetname);
        mobileNumber = view.findViewById(R.id.mobileNumber);
        firstNameTextInputLayout = view.findViewById(R.id.firstNameTextInputLayout);
        lastNameTextInputLayout = view.findViewById(R.id.lastNameTextInputLayout);
        streetNameTextInputLayout = view.findViewById(R.id.streetNameTextInputLayout);
        cityTextInputLayout = view.findViewById(R.id.cityTextInputLayout);
        mobileNumberInputLayout = view.findViewById(R.id.mobileNumberInputLayout);
        countryNameInputLayout = view.findViewById(R.id.countryNameInputLayout);
        countryNameInputLayout = view.findViewById(R.id.countryNameInputLayout);
        backarrow = view.findViewById(R.id.backarrow);
        clear_text = view.findViewById(R.id.clear_text);
        upgradeAddAddressSubmitButton = view.findViewById(R.id.upgradeAddAddressSubmitButton);
        countryspinner = view.findViewById(R.id.countrycodeSpinner);
        recyclerView = view.findViewById(R.id.autoCompleteRecyclerView);
        place_search = view.findViewById(R.id.place_search);
        place_search.addTextChangedListener(filterTextWatcher);
        firstName.addTextChangedListener(mTextWatcher);
        lastName.addTextChangedListener(mTextWatcher);
        street.addTextChangedListener(mTextWatcher);
        city.addTextChangedListener(mTextWatcher);
        mobileNumber.addTextChangedListener(mTextWatcher);
        flag = getArguments().getString("flag");
        addressId = getArguments().getString("addressId");
        editFirstName = getArguments().getString("fname");
        editLastName = getArguments().getString("lname");
        editStreet = getArguments().getString("street");
        editCity = getArguments().getString("city");
        editPhone = getArguments().getString("phone");
        editCountryId = getArguments().getString("countryId");

        firstNameTextInputLayout.setHelperText(languageToLoad == "ar" ? "على سبيل المثال عبد" : "e.g. Abdul");
        lastNameTextInputLayout.setHelperText(languageToLoad == "ar" ? "على سبيل المثال خان" : "e.g. Khan");
        streetNameTextInputLayout.setHelperText(languageToLoad == "ar" ? "على سبيل المثال شارع" : "e.g. Street");
        cityTextInputLayout.setHelperText(languageToLoad == "ar" ? "على سبيل المثال مدينة" : "e.g. City");
        mobileNumberInputLayout.setHelperText(languageToLoad == "ar" ? "على سبيل المثال 9898989898" : "e.g. 9898989898");
        countryNameInputLayout.setHelperText(languageToLoad == "ar" ? "على سبيل المثال المملكة العربية السعودية" : "e.g. Saudi Arabia");

        if (flag.equals("editAddress")) {
            Log.e("editAddress", editFirstName + " " + editLastName + " " + editStreet + " " + editCity + " " + editPhone);
            firstName.setText(editFirstName);
            lastName.setText(editLastName);
            street.setText(editStreet);
            city.setText(editCity);
            mobileNumber.setText(editPhone);
            setCountryId = true;

        } else {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
            if (MySharedPreferenceClass.getMyFirstNamePref(getContext()) != null) {
                firstName.setText(MySharedPreferenceClass.getMyFirstNamePref(getContext()));
            }
            if (MySharedPreferenceClass.getMyLastNamePref(getContext()) != null) {
                lastName.setText(MySharedPreferenceClass.getMyLastNamePref(getContext()));
            }
        }

        checkFieldsForEmptyValues();
        getcountryDetail();
        if (!Places.isInitialized()) {
            Places.initialize(getContext(), apikey);
        }

//        AutoComplete place google api starts

        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(getContext());
        clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place_search.setText("");
            }
        });
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAutoCompleteAdapter.setClickListener(this);
        recyclerView.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.notifyDataSetChanged();
        recyclerView.setNestedScrollingEnabled(false);

//            AutoComplete place google api ends
        return view;
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
                        // countryname.add("(" + response.body().data.get(i).cel_code + ")" + " " + response.body().data.get(i).name);
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
                    initSpinner();
                }
            }

            @Override
            public void onFailure(Call<GetCountryCode> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    //   Initialize Spinner
    public void initSpinner() {
        if (getActivity() != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(), R.layout.spinnertextviewenglish, countryname);
                adp2.setDropDownViewResource(R.layout.checkedradiotextviewenglish);
                countryspinner.setAdapter(adp2);
            } else {
                ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(), R.layout.spinnertextviewarabic, countryname);
                adp2.setDropDownViewResource(R.layout.checkedradiotextview);
                countryspinner.setAdapter(adp2);
            }
            countryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    //Toast.makeText(getApplicationContext(),"Position"+ position, Toast.LENGTH_SHORT).show();

                    if (setCountryId == true) {
                        if (getCountryCodeResponse != null) {
                            for (int i = 0; i < getCountryCodeResponse.data.size(); i++) {
                                Log.e("AddressCountryCode: ", "" + countryCode);
                                Log.e("code", editCountryId);
                                Log.e("i", String.valueOf(i));
                                if (editCountryId.equals(getCountryCodeResponse.data.get(i).code)) {
                                    countryspinner.setSelection(getCountryCodeResponse.data.size() - 1 - i);
                                    CountryCodeSelected = editCountryId;
                                    CelCodeSelected = getCountryCodeResponse.data.get(i).cel_code;
                                    break;
                                }
                            }
                        } else {
                            Log.e("getCountryCodeResponse", "null");
                        }
                        setCountryId = false;
                    } else if (isCurrentCountryId == true) {
                        if (getCountryCodeResponse != null) {
                            for (int i = 0; i < getCountryCodeResponse.data.size(); i++) {
                                Log.e("AddressCountryCode: ", "" + countryCode);
                                Log.e("code", getCountryCodeResponse.data.get(i).code);
                                Log.e("i", String.valueOf(i));
                                if (currentCountryId.equals(getCountryCodeResponse.data.get(i).code)) {
                                    countryspinner.setSelection(getCountryCodeResponse.data.size() - 1 - i);
                                    CountryCodeSelected = currentCountryId;
                                    CelCodeSelected = getCountryCodeResponse.data.get(i).cel_code;
                                    break;
                                }
                            }
                        } else {
                            Log.e("getCountryCodeResponse", "null");
                        }
                        isCurrentCountryId = false;
                    } else {
                        countryName = countryspinner.getSelectedItem().toString();
                        selectedposition = position;
                        CountryCodeSelected = getCountryCodeResponse.data.get(getCountryCodeResponse.data.size() - 1 - position).code;
                        CelCodeSelected = getCountryCodeResponse.data.get(getCountryCodeResponse.data.size() - 1 - position).cel_code;
                        Log.e("Country Name--------.>>", countryName);
                        Log.e("Country Code--------.>>", String.valueOf(countryspinner.getSelectedItemId()));
                        Log.e("CountryCodeSelected", CountryCodeSelected);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }

            });

        }
    }
//    Initialize Spinner


    //      function to save address
    private void saveAddress() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        JsonObject jsonObject = new JsonObject();
        JsonObject param = new JsonObject();

        param.addProperty("customer_id", Integer.parseInt(MySharedPreferenceClass.getMyUserId(getContext())));
        param.addProperty("firstname", firstName.getText().toString());
        param.addProperty("lastname", lastName.getText().toString());
        param.addProperty("country_id", CountryCodeSelected);
        param.addProperty("city", city.getText().toString());
        param.addProperty("mobilenumber", mobileNumber.getText().toString());
        param.addProperty("street", street.getText().toString());
        param.addProperty("latitude","");
        param.addProperty("longitude", " ");
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
                        Toast.makeText(getContext(), languageToLoad == "ar" ? "تم إضافة العنوان بنجاح" :
                                "Address Added Successfully", Toast.LENGTH_LONG).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    } else {
                        Log.e("add address", "failed");
                    }
                }
                else {


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
        param.addProperty("city", city.getText().toString());
        param.addProperty("mobilenumber", mobileNumber.getText().toString());
        param.addProperty("street", street.getText().toString());
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

    public void initiViews() {
        city = view.findViewById(R.id.city);
        street = view.findViewById(R.id.streetname);
        firstName = view.findViewById(R.id.firstName);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            Log.e("onViewCreated", "mapFragment");
            mapFragment.getMapAsync(callback);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
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

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
               /* new AlertDialog.Builder(getContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown

                            }
                        })
                        .create()
                        .show();*/


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    public void requestopenGPS() {

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(LocationMapFragment.this)
                .addOnConnectionFailedListener(LocationMapFragment.this).build();
        mGoogleApiClient.connect();
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);


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

        result.setResultCallback(LocationMapFragment.this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
            Log.e("mTextWatcher", "beforeTextChanged");
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            Log.e("mTextWatcher", "onTextChanged");
            checkFieldsForEmptyValues();

        }

        @Override
        public void afterTextChanged(Editable editable) {
            Log.e("mTextWatcher", "afterTextChanged");
            // check Fields For Empty Values
        }
    };
//    Edit Text field watcher

    //    function to check if values are empty
    void checkFieldsForEmptyValues() {
        if (firstName.getText().toString().equals("") || lastName.getText().toString().equals("") || street.getText().toString().equals("") || city.getText().toString().equals("") || mobileNumber.getText().toString().equals("")) {
            Log.e("checkFields", "Empty Values");
            upgradeAddAddressSubmitButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_mapgreybutton));

        } else {
            Log.e("checkFields", "Filled Values");
            upgradeAddAddressSubmitButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_mapbutton));
            upgradeAddAddressSubmitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendOtpForVerification();
                }
            });
        }
    }

    private void sendOtpForVerification() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<SendOtpResponse> call = apiService.sendOtp("https://upgrade.eoutlet.com/vsms/app/send?mobile=" + CelCodeSelected + mobileNumber.getText().toString() + "&resend=0");
        call.enqueue(new Callback<SendOtpResponse>() {
            @Override
            public void onResponse(Call<SendOtpResponse> call, Response<SendOtpResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().success == true) {
                        Log.e("OTP send", "success");
                        DialogFragment prFrag = new SaveAddressVerifyOtp();
                        Bundle bund = new Bundle();
                        bund.putString("mobile", CelCodeSelected + mobileNumber.getText().toString());
                        prFrag.setArguments(bund);
                        prFrag.setTargetFragment(LocationMapFragment.this, 1002);
                        prFrag.show(getFragmentManager(), "signup");
                    } else {
                        parentDialogListener.hideProgressDialog();
                        Log.e("OTP send", "failed");
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
        if (requestCode == 1002) {
            if (flag.equals("editAddress")) {
                saveEdittedAddress();
            } else {
                saveAddress();
            }
        }

    }


    //    function to check if values are empty
    //   Autocomplete place google text field watcher
    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            if (placeSelected == true) {
                if (recyclerView.getVisibility() == View.VISIBLE) {
                    recyclerView.setVisibility(View.GONE);
                    placeSelected = false;
                }
            } else {
                if (!s.toString().equals("")) {
                    mAutoCompleteAdapter.getFilter().filter(s.toString());
                    if (recyclerView.getVisibility() == View.GONE) {
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (recyclerView.getVisibility() == View.VISIBLE) {
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };
//    Autocomplete place google text field watcher

    //    On click action of selecting place in autocomplete place google api
    @Override
    public void click(Place place) {
        placeSelected = true;
        placeSearch = true;
        place_search.setText(place.getAddress());
        longitude = String.valueOf(place.getLatLng().longitude);
        latitude = String.valueOf(place.getLatLng().latitude);
        mGoogleMap.clear();
        mGoogleMap.addMarker(new MarkerOptions().position(place.getLatLng()).draggable(true).title(place.getName()));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 11));
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        try {
            List<Address> addresses;
            geocoder1 = new Geocoder(getContext(), Locale.getDefault());
            Log.e("geocoder1 Locale", Locale.getDefault().getDisplayLanguage());
            try {
                addresses = geocoder1.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address1 = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String address2 = addresses.get(0).getAddressLine(1); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String cityName = addresses.get(0).getLocality();
                String streetName = addresses.get(0).getSubLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String countryCode = addresses.get(0).getCountryCode();
                city.setText(cityName);
                street.setText(streetName);
                for (int i = 0; i < getCountryCodeResponse.data.size(); i++) {
                    Log.e("AddressCountryCode: ", "" + countryCode);
                    Log.e("code", getCountryCodeResponse.data.get(i).code);
                    Log.e("i", String.valueOf(i));
                    if (countryCode.equals(getCountryCodeResponse.data.get(i).code)) {
                        countryspinner.setSelection(getCountryCodeResponse.data.size() - 1 - i);
                        CountryCodeSelected = countryCode;
                        Log.e("CountryCodeSelected", CountryCodeSelected);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //setMarker(latLng);
        }
    }
//    On click action of selecting place in autocomplete place google api

}
