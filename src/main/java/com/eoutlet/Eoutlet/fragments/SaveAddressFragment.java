package com.eoutlet.Eoutlet.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.listener.CartListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.ChangePassword;
import com.eoutlet.Eoutlet.pojo.GetCountryCode;
import com.eoutlet.Eoutlet.pojo.OtpResponse;
import com.eoutlet.Eoutlet.pojo.SaveAddress;
import com.eoutlet.Eoutlet.utility.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SaveAddressFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SaveAddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaveAddressFragment extends Fragment  {
    View view;
    private EditText firstName, lastname, mobile, street, city, country;
    private TextView btntoSaveDeliveryAddress;
    public ParentDialogsListener parentDialogListener;
    private OnFragmentInteractionListener mListener;
    private List<String> countryname;
    private List<String> countryCode;
    private List<String> cel_code;
    private List<String> placeholder;
    Spinner countryspinner;
    String value = " ";
    String finalnumber =" ";
    String userselected_celcode =" ";
    private String countryName;
    private int selctedposition = 0;
    private int initialseletedposition = 0;
    private int mobilevalidationlength;
    boolean initialflage = true;
    private boolean isAllConditionFulfilled;

    private CartListener cartreference;


    public SaveAddressFragment(NewAddressFragmentUpgrade cl) {
       cartreference = cl;
    }

    public SaveAddressFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SaveAddressFragment newInstance(String param1, String param2) {
        SaveAddressFragment fragment = new SaveAddressFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_save_address, container, false);

        value = getArguments().getString("flag");
        initViews();

        gecountryDetail();


        return view;


    }

    public void initViews() {

        firstName = view.findViewById(R.id.savedfirstName);
        lastname = view.findViewById(R.id.savedlastName);

        street = view.findViewById(R.id.savedstreet);
        city = view.findViewById(R.id.savedcity);
        country = view.findViewById(R.id.savedcountry);
        btntoSaveDeliveryAddress = view.findViewById(R.id.btnsaveDelieveryAddress);
        countryspinner = view.findViewById(R.id.countrySpinner);


        btntoSaveDeliveryAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateFields();


            }
        });


    }

    public void validateFields() {
        isAllConditionFulfilled = Util.checkTextViewValidation(firstName, "برجاء كتابة الاسم الاول")
                && Util.checkTextViewValidation(lastname, "برجاء كتابة الاسم الاخير")


                && Util.checkTextViewValidation(street, "برجاء كتابة اسم الشارع")

                && Util.checkTextViewValidation(city, "برجاء كتابة المدينة")

                && Util.checkTextViewValidation(mobile, "من فضلك أدخل رقم الجوال") && Util.checkMobileValidation(mobile, mobilevalidationlength, "من فضلك أدخل رقم الجوال");


        if (!isAllConditionFulfilled) {
            if (Util.view_final != null) {

                Util.view_final.requestFocus();
            }


            return;
        }



            send_address_change_otp();




    }


    public void send_address_change_otp()
    {



        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Map<String, String> map1 = new HashMap<>();
        map1.put("mobile", /*"+917906156955"*/cel_code.get(selctedposition) + mobile.getText().toString()   );
        map1.put("resend", "0");


        Call<OtpResponse> call = apiService.otp_for_address_mob_change(map1);
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().success) {
                        Toast.makeText(getContext(), "تم ارسال الكود بنجاح", Toast.LENGTH_SHORT).show();

                        DialogFragment prFrag = new SaveAddressVerifyOtp();
                        Bundle bund = new Bundle();
                        bund.putString("mobile", cel_code.get(selctedposition) + mobile.getText().toString());

                        prFrag.setArguments(bund);

                        prFrag.setTargetFragment(SaveAddressFragment.this, 1002);
                        prFrag.show(getFragmentManager(), "signup");




                    } else {
                        parentDialogListener.hideProgressDialog();
                        Toast.makeText(getContext(), response.body().msg.toString(), Toast.LENGTH_SHORT).show();



                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });











    }


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

    public void saveLatestAddress() {
        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> map1 = new HashMap<>();
        map1.put("firstname", firstName.getText().toString());
        map1.put("lastname", lastname.getText().toString());
        map1.put("street", street.getText().toString());
        map1.put("city", city.getText().toString());
        map1.put("country_id", countryCode.get(selctedposition));

        map1.put("mobilenumber", cel_code.get(selctedposition) + mobile.getText().toString());
        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));


        Call<SaveAddress> call = apiService.saveAddress(map1);
        call.enqueue(new Callback<SaveAddress>() {
            @Override
            public void onResponse(Call<SaveAddress> call, Response<SaveAddress> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().msg.equals("success")) {





                        if (value.equals("fromCheckout")) {



                            getFragmentManager().popBackStackImmediate();


                            /*Fragment prFrag = new Addressfragment();
                            Bundle databund = new Bundle();
                            databund.putString("flag", "fromCheckout");
                            prFrag.setArguments(databund);

                            getFragmentManager()
                                    .beginTransaction()*//*.addToBackStack(null)*//*
                                    .replace(SaveAddressFragment.this.getId(), prFrag)
                                    .commit();
                            System.out.println(value + "from saveaddress---->");*/


                        } else {

                            Fragment prFrag = new NewAddressFragmentUpgrade();

                            getFragmentManager()
                                    .beginTransaction()/*.addToBackStack(null)*/
                                    .replace(SaveAddressFragment.this.getId(), prFrag)
                                    .commit();
                        }


                    } else {
                        parentDialogListener.hideProgressDialog();


                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<SaveAddress> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();
            }
        });


    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

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

    public void initSpinner() {


        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this.getActivity(), R.layout.spinnertextview3, countryname);
        adp2.setDropDownViewResource(R.layout.spinnertextview3);


        countryspinner.setAdapter(adp2);


        countryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //Toast.makeText(getApplicationContext(),"Position"+ position, Toast.LENGTH_SHORT).show();

                Log.e("position is",String.valueOf(position));







                countryName = countryspinner.getSelectedItem().toString();
                selctedposition = position;
                //mobile.setText("");
                if(position!=initialseletedposition) {
                    mobile.getText().clear();
                    mobile.setHint(placeholder.get(selctedposition));
                    initialseletedposition = 101;
                }
                mobilevalidationlength = placeholder.get(selctedposition).length();
                mobile.setFilters(new InputFilter[]{new InputFilter.LengthFilter(placeholder.get(selctedposition).length())});


                Log.e("Gender Name--------.>>", countryName);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });


        initSavedinformation();

    }

    public void gecountryDetail() {
        // parentDialogListener.showProgressDialog();
        countryname = new ArrayList<>();
        countryCode = new ArrayList<>();
        cel_code = new ArrayList<>();
        placeholder = new ArrayList<>();


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Call<GetCountryCode> call;


        if(MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")){

            call = apiService.getCountryDetailEnglish();
        }

        else {

            call = apiService.getCountryDetailArabic();
        }

        call.enqueue(new Callback<GetCountryCode>() {
            @Override
            public void onResponse(Call<GetCountryCode> call, Response<GetCountryCode> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    for (int i = 0; i < response.body().data.size(); i++) {
                        countryname.add("(" + response.body().data.get(i).cel_code + ")" + " " + response.body().data.get(i).name);
                        countryCode.add(response.body().data.get(i).code);
                        cel_code.add(response.body().data.get(i).cel_code);

                        placeholder.add(response.body().data.get(i).placeholder.replaceAll("\\s", ""));


                        mobilevalidationlength = placeholder.get(0).length();


                    }

                    Collections.reverse(countryname);
                    Collections.reverse(countryCode);
                    Collections.reverse(cel_code);
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

    public void initSavedinformation() {
        firstName.setText(MySharedPreferenceClass.getMyFirstNamePref(getContext()));
        lastname.setText(MySharedPreferenceClass.getMyLastNamePref(getContext()));
        mobile.setText(MySharedPreferenceClass.getMobileNumber(getContext()));

        /*for(int i = 0;i<cel_code.size();i++) {


            if (MySharedPreferenceClass.getMobileNumber(getContext()).contains(cel_code.get(i))){

                userselected_celcode = cel_code.get(i);

                finalnumber =MySharedPreferenceClass.getMobileNumber(getContext()).replace(cel_code.get(i),"");

                mobile.setText(finalnumber);
                Log.e("finalnumber is--->",finalnumber);


                break;



            }

        }*/
      /*for (int i = 0; i < cel_code.size(); i++) {

            if (userselected_celcode.equals(cel_code.get(i))) {


                initialseletedposition = i;
                Log.e("fposition is is--->",finalnumber);
                countryspinner.setSelection(i);




                break;


            }


        }*/
        for (int i = 0; i < cel_code.size(); i++) {

            if (MySharedPreferenceClass.getCountryCode(getContext()).equals(cel_code.get(i))) {


                initialseletedposition = i;
                Log.e("fposition is is--->",finalnumber);
                countryspinner.setSelection(i);




                break;


            }


        }


        initialflage=false;

        mobile.setHint("");







    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        saveLatestAddress();




    }

}
