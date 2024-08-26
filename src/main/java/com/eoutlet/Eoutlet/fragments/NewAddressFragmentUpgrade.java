package com.eoutlet.Eoutlet.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.adpters.NewAddressListAdapter;
import com.eoutlet.Eoutlet.adpters.UpgradeNewAddressListAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.listener.CartListener;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.AddressList;
import com.eoutlet.Eoutlet.pojo.GetCountryCode;
import com.eoutlet.Eoutlet.pojo.GetUserProfile;
import com.eoutlet.Eoutlet.pojo.UpgradeDeleteAddress;
import com.eoutlet.Eoutlet.pojo.UpgradeGetAddressResponse;
import com.eoutlet.Eoutlet.pojo.UpgradedGetAddressList;
import com.eoutlet.Eoutlet.utility.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class NewAddressFragmentUpgrade extends Fragment implements View.OnClickListener, CartListener {
    View view;
    private ParentDialogsListener parentDialogListener;
    private String value = " ";
    private CartListener cartreference;
    private List<String> name = new ArrayList<>();
    private List<String> addressfirstname = new ArrayList<>();
    private List<String> addresslastname = new ArrayList<>();
    private List<String> street = new ArrayList<>();
    private List<String> city = new ArrayList<>();
    private List<String> country = new ArrayList<>();
    private List<String> phone = new ArrayList<>();
    private List<String> address_id = new ArrayList<>();
    private List<String> countryId = new ArrayList<>();
    private TextView btnaddAddress, setbymap;
    private RecyclerView addressRecycler;
    private NewAddressListAdapter mAdapter;
    private UpgradeNewAddressListAdapter upgradeNewAddressListAdapter;
    private ImageView searchImage, backarrow;
    private Toolbar toolbar1;
    private List<UpgradedGetAddressList> upgradedGetAddressLists = new ArrayList<>();
    LinearLayout addressContainer;
    GetCountryCode getCountryCodeResponse;
    private ArrayList<String >   countrycode = new ArrayList<>();

    String Locale;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public NewAddressFragmentUpgrade(CheckOutConfirmationfragment cl) {
        cartreference = cl;
    }


    public NewAddressFragmentUpgrade() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                Locale = "ar";
                view = inflater.inflate(R.layout.fragment_new_address_arabic, container, false);
            } else {
                Locale = "en";
                view = inflater.inflate(R.layout.upgradefragment_new_address, container, false);
            }
        } else {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_new_address_arabic, container, false);
        }
        super.onCreateView(inflater, container, savedInstanceState);
        try {
            value = getArguments().getString("flag");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initViews();
        initonClickListener();
        toolbar1 = view.findViewById(R.id.toolbar);
        searchImage = toolbar1.findViewById(R.id.serachbar);
        backarrow = toolbar1.findViewById((R.id.backarrow));


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
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (value.equals("fromCheckout")) {

                    cartreference.setUserHintcall();
                    getFragmentManager().popBackStackImmediate();

                } else {
                    getFragmentManager().popBackStack();
                }


            }
        });

        //getAddressDetails();
//        getAddressList();
        return view;
    }

    //    function to get list of address in eoutlet upgrade
    private void getAddressList() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<UpgradeGetAddressResponse> call = Locale == "ar" ? apiService.getUpgradedAddress(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "addresslist/" + MySharedPreferenceClass.getMyUserId(view.getContext()))
                : apiService.getUpgradedAddress(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "addresslist/" + MySharedPreferenceClass.getMyUserId(view.getContext()));
        call.enqueue(new Callback<UpgradeGetAddressResponse>() {
            @Override
            public void onResponse(Call<UpgradeGetAddressResponse> call, Response<UpgradeGetAddressResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().getResponse().equals("success")) {
                        addressContainer.setVisibility(View.VISIBLE);
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
                        }


                        Collections.reverse(upgradedGetAddressLists);
                        upgradeNewAddressListAdapter = new UpgradeNewAddressListAdapter(getContext(), countrycode,NewAddressFragmentUpgrade.this, upgradedGetAddressLists, value, new ViewListener() {
                            @Override
                            public void onClick(int position, View view) {
                                int id = view.getId();
                                switch (id) {
                                    case R.id.deleteCart:
                                        deleteAddress(upgradedGetAddressLists.get(position).getAddress_id(), position);
                                        Log.e("Upgrade Address ID", String.valueOf(position));
                                        break;


                                    case R.id.addressSelected:
                                        MySharedPreferenceClass.setfirstAdressname(view.getContext(), upgradedGetAddressLists.get(position).getFirstname());
                                        MySharedPreferenceClass.setlastAdressname(view.getContext(), upgradedGetAddressLists.get(position).getLastname());
                                        MySharedPreferenceClass.setAddressname(view.getContext(), upgradedGetAddressLists.get(position).getFirstname() + " " + upgradedGetAddressLists.get(position).getLastname());
                                        MySharedPreferenceClass.setCityname(view.getContext(), upgradedGetAddressLists.get(position).getCity());
                                        MySharedPreferenceClass.setStreetName(view.getContext(), upgradedGetAddressLists.get(position).getStreet());
                                        MySharedPreferenceClass.setCountryname(view.getContext(), upgradedGetAddressLists.get(position).getCountry());
                                        MySharedPreferenceClass.setAddressphone(view.getContext(), upgradedGetAddressLists.get(position).getTelephone());
                                        MySharedPreferenceClass.setCountryId(view.getContext(), upgradedGetAddressLists.get(position).getCountry_id());


                                        if (value.equals("fromCheckout")) {
                                            if (cartreference != null) {
                                                cartreference.setUserHintcall();
                                            }
                                            getFragmentManager().popBackStackImmediate();
                                        }
                                        Log.e("addressSelected", String.valueOf(position));
                                        break;
                                }
                            }
                        }
                        );
                        LinearLayoutManager upgradedAddressLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        addressRecycler.setLayoutManager(upgradedAddressLayout);
                        addressRecycler.setItemAnimator(new DefaultItemAnimator());
                        addressRecycler.setAdapter(upgradeNewAddressListAdapter);
                        upgradeNewAddressListAdapter.notifyDataSetChanged();
                    } else {


                        Log.e("get address", "failed");
                    }
                } else {
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                            "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
                /*if (value.equals("fromCheckout")) {

                    cartreference.setUserHintcall();
                    getFragmentManager().popBackStackImmediate();

                }*/
            }

            @Override
            public void onFailure(Call<UpgradeGetAddressResponse> call, Throwable t) {
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
            }
        });
    }

    public void initonClickListener() {
        btnaddAddress.setOnClickListener(this);
        setbymap.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addnewAddress:
                addanewAddress();
                break;

            case R.id.setbymap:

                //checkLocationPermission();
                Fragment prFrag = new UpgradeMapFragment();
                if (value.equals("fromCheckout")) {

                    Bundle databund = new Bundle();
                    databund.putString("flag", "fromCheckout");
                    prFrag.setArguments(databund);
                    getFragmentManager()
                            .beginTransaction().addToBackStack(null)
                            .replace(R.id.upgradeNewAddressListLayout, prFrag)
                            .detach(this).attach(this)
                            .commit();
                } else {
                    Bundle databund = new Bundle();
                    databund.putString("flag", " ");
                    prFrag.setArguments(databund);

                    getFragmentManager()
                            .beginTransaction().addToBackStack(null)
                            .replace(R.id.upgradeNewAddressListLayout, prFrag).detach(this).attach(this)
                            .commit();
                }

                break;
        }
    }


    public void addanewAddress() {
        Fragment prFrag = new SaveAddressFragment(NewAddressFragmentUpgrade.this);
        if (value.equals("fromCheckout")) {
            Bundle databund = new Bundle();
            databund.putString("flag", "fromCheckout");
            prFrag.setArguments(databund);

            getFragmentManager()
                    .beginTransaction().addToBackStack(null)
                    .replace(R.id.checkoutContainer, prFrag)
                    .commit();
        } else {
            Bundle databund = new Bundle();
            databund.putString("flag", " ");
            prFrag.setArguments(databund);

            getFragmentManager()
                    .beginTransaction().addToBackStack(null)
                    .replace(R.id.addressContainer, prFrag)
                    .commit();
        }
    }

    public void onButtonPressed(Uri uri) {
    }

    public void initViews() {
        addressRecycler = view.findViewById(R.id.address_list_Recycler);
        addressContainer = view.findViewById(R.id.addressContainer);
        btnaddAddress = view.findViewById(R.id.addnewAddress);
        setbymap = view.findViewById(R.id.setbymap);
        addressContainer.setVisibility(View.GONE);

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

    @Override
    public void setUserHintcall() {
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




    //    function to delete address
    public void deleteAddress(final String addressId, final int position) {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<UpgradeDeleteAddress> call = apiService.upgradeDeleteAddress(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK + "deleteaddress/" + addressId);
        call.enqueue(new Callback<UpgradeDeleteAddress>() {
            @Override
            public void onResponse(Call<UpgradeDeleteAddress> call, Response<UpgradeDeleteAddress> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().getResponse().equals("success")) {
                        if (MySharedPreferenceClass.getSelectedAddressId(getContext()).equals(addressId)) {
                            MySharedPreferenceClass.setAddressname(getContext(), null);
                            MySharedPreferenceClass.setCityname(getContext(), null);
                            MySharedPreferenceClass.setStreetName(getContext(), null);
                            MySharedPreferenceClass.setCountryname(getContext(), null);
                            MySharedPreferenceClass.setAddressphone(getContext(), null);
                            MySharedPreferenceClass.setCountryId(getContext(), null);
                            MySharedPreferenceClass.setfirstAdressname(getContext(), null);
                            MySharedPreferenceClass.setlastAdressname(getContext(), null);
                            MySharedPreferenceClass.setSelecteAddressdId(getContext(), "0");
                            if (value.equals("fromCheckout"))
                                MainActivity.isaddressempty = true;


                        }


                        Log.e("delete address", "success");
                        Toast.makeText(getContext(), Locale == "ar" ? "تم حذف العنوان بنجاح" :
                                "Address deleted successfully", Toast.LENGTH_LONG).show();
                        getAddressList();
                    } else {
                        Log.e("delete address", "failed");
                    }
                } else {
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                            "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpgradeDeleteAddress> call, Throwable t) {
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
            }
        });
    }
//    function to delete address


    @Override
    public void onResume() {
        Log.e("getAddressList", "on Resume");
        getcountryDetail();

       // getAddressList();
        super.onResume();
//        getAddressDetails();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.e("onRequestPermissions", String.valueOf(requestCode));
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {


            }
        }


    }

    public void getcountryDetail() {

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
                    countrycode.clear();

                    parentDialogListener.hideProgressDialog();
                    for (int i = 0; i < response.body().data.size(); i++) {
                       countrycode.add(response.body().data.get(i).cel_code);

                }
                    getAddressList();

            }}

            @Override
            public void onFailure(Call<GetCountryCode> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

}