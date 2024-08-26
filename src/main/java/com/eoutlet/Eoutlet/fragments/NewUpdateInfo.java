package com.eoutlet.Eoutlet.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.ChangePassword;
import com.eoutlet.Eoutlet.pojo.ChangePasswordInside;
import com.eoutlet.Eoutlet.pojo.GetCountryCode;
import com.eoutlet.Eoutlet.pojo.GetUserProfile;
import com.eoutlet.Eoutlet.pojo.GuestUser;
import com.eoutlet.Eoutlet.utility.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.eoutlet.Eoutlet.fragments.HomeFragmentNewDesign.wishListBadgeCount;
import static com.eoutlet.Eoutlet.fragments.NewCategoryDesignFragments.categorywishListBadgeCount;

import static com.eoutlet.Eoutlet.fragments.ProductList.productListBadgeCount;
import static com.facebook.FacebookSdk.getApplicationContext;


public class NewUpdateInfo extends Fragment {
    View view;
    private Spinner countryspinner;
    private int selctedposition = 0;
    private List<String> countryname;
    private List<String> countryCode;
    private List<String> celcode;
    private List<String> placeholder;
    String userName;
    int mobilevalidationlength;
    private String countryName, otp;
    private static int celcodenumber;
    CheckBox newsLetterCheckBox;
    private EditText updatefName, updatelName, updateMail, updateMobile, updatecelcode, changenewpassword, oldPassword, updateoldpassword, updatenewpassword;
    private TextView btnupdatProfile, savenewpassword, fullName, address;
    public ParentDialogsListener parentDialogListener;
    private boolean isAllConditionFulfilled;
    private ScrollView mScrollView;
    private ImageView searchImage, backarrow;
    private Toolbar toolbar1;
    String Locale;
    private LinearLayout changepasswordUi;
    Boolean newsLetterSubscription = false;
    private TextView editfirstname, editlastname, editmail, editmobile, editcelcode, editoldpassword, editnewpassword, editcel;

    public static NewUpdateInfo newInstance(String param1, String param2) {
        NewUpdateInfo fragment = new NewUpdateInfo();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
            Locale = "en";
            view = inflater.inflate(R.layout.fragment_new_update_info_english, container, false);
        } else {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_new_update_info, container, false);
        }


        intiViews();

        getUserProfile();


        //initialzesavedfields();
        getcountryDetail();
        return view;
    }

    public void intiViews() {
        newsLetterCheckBox = view.findViewById(R.id.newsLetterCheckBox);
        updatefName = view.findViewById(R.id.updatefirstName);
        updatelName = view.findViewById(R.id.updatelastName);
        updateMail = view.findViewById(R.id.updateeMail);

        updateoldpassword = view.findViewById(R.id.oldPassword);
        updatenewpassword = view.findViewById(R.id.newPassword);
        updateMobile = view.findViewById(R.id.updatemobile);
        updatecelcode = view.findViewById(R.id.updatecelcode);
        btnupdatProfile = view.findViewById(R.id.updateProfile);
        savenewpassword = view.findViewById(R.id.saveNewPassword);
        changenewpassword = view.findViewById(R.id.newPassword);
        oldPassword = view.findViewById(R.id.oldPassword);
        fullName = view.findViewById(R.id.fullName);
        address = view.findViewById(R.id.address);
        editfirstname = view.findViewById(R.id.editfirstname);
        editlastname = view.findViewById(R.id.editlastname);
        editmail = view.findViewById(R.id.editemail);
        editmobile = view.findViewById(R.id.editmobile);
        editcelcode = view.findViewById(R.id.editcelcode);
        mScrollView = view.findViewById(R.id.scrollContainer);
        toolbar1 = view.findViewById(R.id.toolbar);
        searchImage = toolbar1.findViewById(R.id.serachbar);
        backarrow = toolbar1.findViewById((R.id.backarrow));
        editoldpassword = view.findViewById(R.id.editoldpassword);
        editnewpassword = view.findViewById(R.id.editnewpassword);
        countryspinner = view.findViewById(R.id.countrycodeSpinner);
        changepasswordUi = view.findViewById(R.id.changepasswordUI);

        if (MySharedPreferenceClass.getLoginType(getContext()).equals("google") || MySharedPreferenceClass.getLoginType(getContext()).equals("twitter")) {

            changepasswordUi.setVisibility(View.GONE);


        } else {
            changepasswordUi.setVisibility(View.VISIBLE);
        }
        newsLetterCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    newsLetterSubscription = true;
                } else {
                    newsLetterSubscription = false;
                }
                Log.e("newsLetterSubscription", newsLetterSubscription.toString());
            }
        });

        editoldpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updateoldpassword.setEnabled(true);
                updateoldpassword.requestFocus();


            }
        });

        editnewpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updatenewpassword.setEnabled(true);
                updatenewpassword.requestFocus();


            }
        });


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
                getFragmentManager().popBackStack();
            }
        });


        updatefName.setEnabled(false);
        // updatefName.setFocusable(false);

        updatelName.setEnabled(false);
        // updatelName.setFocusable(false);

        updateMail.setEnabled(false);
        //  updateMail.setFocusable(false);


        updateMobile.setEnabled(false);
        //updateMobile.setFocusable(false);

        updatecelcode.setEnabled(false);

        mScrollView.setSmoothScrollingEnabled(true);
        editfirstname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatefName.setEnabled(true);
                updatefName.requestFocus();
                updatefName.setSelection(updatefName.getText().length());
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            }
        });
        editlastname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatelName.setEnabled(true);
                updatelName.requestFocus();
                updatelName.setSelection(updatelName.getText().length());
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);


            }
        });
        editmobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMobile.setEnabled(true);
                updateMobile.requestFocus();
                updateMobile.setSelection(updateMobile.getText().length());
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            }
        });
        editmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMail.setEnabled(true);
                updateMail.requestFocus();
                updateMail.setSelection(updateMail.getText().length());
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            }
        });

        editcelcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatecelcode.setVisibility(View.GONE);
                initSpinner();
                //getcountryDetail();


            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isAdded()) {
                    // Scroll 1000px down
                    mScrollView.smoothScrollTo(0, 1000);
                }
            }
        }, 150);


        btnupdatProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                validateProfileFields();


            }
        });

        savenewpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Locale.equals("ar")) {
                    isAllConditionFulfilled = Util.checkTextViewValidation(oldPassword, "من فضلك ادخل كلمة المرور")
                            && Util.checkTextViewValidation(changenewpassword, "الرجاء إدخال تأكيد كلمة المرور")
                            && Util.isPasswordMatched(oldPassword, changenewpassword, "كلمة المرور لا تتطابق مع تأكيد كلمة المرور");
                } else {
                    isAllConditionFulfilled = Util.checkTextViewValidation(oldPassword, "Please enter password")
                            && Util.checkTextViewValidation(changenewpassword, "Please enter confirm password")
                            && Util.isPasswordMatched(oldPassword, changenewpassword, "Password doesn't match with confirm password");
                }


                if(!isAllConditionFulfilled)

            {
                if (Util.view_final != null) {

                    Util.view_final.requestFocus();
                }


                return;
            }

            savenewpassword();


        }
    });


}

    public void getUserProfile() {
        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("email", MySharedPreferenceClass.getEmail(getContext()));
        mainparam.put("param", map1);




        Call<GetUserProfile> call = apiService.getUpgradedUserProfile(mainparam);


        call.enqueue(new Callback<GetUserProfile>() {
            @Override
            public void onResponse(Call<GetUserProfile> call, Response<GetUserProfile> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();

                    MySharedPreferenceClass.setMyFirstNamePref(getApplicationContext(), response.body().data.get(0).fname);
                    MySharedPreferenceClass.setMyLastNamePref(getApplicationContext(), response.body().data.get(0).lname);
                    MySharedPreferenceClass.setEmail(getApplicationContext(), response.body().data.get(0).email);
                    MySharedPreferenceClass.setCountryCode(getApplicationContext(), response.body().data.get(0).countryCode);
                    MySharedPreferenceClass.setMobileNumber(getApplicationContext(), response.body().data.get(0).mob);
                    if (response.body().data.get(0).newsletter == true) {
                        newsLetterCheckBox.setChecked(true);
                        newsLetterSubscription = true;
                    } else {
                        newsLetterCheckBox.setChecked(false);
                        newsLetterSubscription = false;
                    }

                    initialzesavedfields();


                }
            }

            @Override
            public void onFailure(Call<GetUserProfile> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getApplicationContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void savenewpassword() {


        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        map1.put("newpassword", changenewpassword.getText().toString());
        mainparam.put("param", map1);

        Call<ChangePasswordInside> call = apiService.saveNewPassword(mainparam);
        call.enqueue(new Callback<ChangePasswordInside>() {
            @Override
            public void onResponse(Call<ChangePasswordInside> call, Response<ChangePasswordInside> response) {


                if (response.body() != null) {
                    //  parentDialogListener.hideProgressDialog();


                    if (response.body().response.equals("success")) {

                        if (Locale.equals("ar")) {
                            Toast.makeText(getContext(), "تم تغيير كلمة السر بنجاح", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "The password has been changed successfully", Toast.LENGTH_LONG).show();
                        }


                        getUpgradedguesttoken();


                    } else {
                        parentDialogListener.hideProgressDialog();


                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<ChangePasswordInside> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getContext(), "Something went wrong. Please try again", Toast.LENGTH_LONG).show();
            }
        });


    }


    public void initialzesavedfields() {
        updatefName.setText(MySharedPreferenceClass.getMyFirstNamePref(getContext()));
        updatelName.setText(MySharedPreferenceClass.getMyLastNamePref(getContext()));
        updateMail.setText(MySharedPreferenceClass.getEmail(getContext()));
        if (!MySharedPreferenceClass.getMobileNumber(getContext()).equals(" ")) {

            updateMobile.setText(MySharedPreferenceClass.getMobileNumber(getContext()));
        }

        if (!MySharedPreferenceClass.getCountryCode(getContext()).equals(" ")) {
            updatecelcode.setText(MySharedPreferenceClass.getCountryCode(getContext()));
        }

        fullName.setText(MySharedPreferenceClass.getMyFirstNamePref(getContext()) + " " + (MySharedPreferenceClass.getMyLastNamePref(getContext())));
        address.setText(MySharedPreferenceClass.getEmail(getContext()));


    }

    public void getcountryDetail() {
        // parentDialogListener.showProgressDialog();
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
                    parentDialogListener.hideProgressDialog();
                    for (int i = 0; i < response.body().data.size(); i++) {
                        String countrycode = response.body().data.get(i).cel_code.replace("+", "");

                        // countryname.add("(" + response.body().data.get(i).cel_code + ")" + " " + response.body().data.get(i).name);
                        countryCode.add(response.body().data.get(i).code);
                        countryname.add("(" + countrycode + "+" + ")" + " " + response.body().data.get(i).name);
                        placeholder.add(response.body().data.get(i).placeholder.replaceAll("\\s", ""));
                        celcode.add(response.body().data.get(i).cel_code);


                    }
                    Collections.reverse(countryname);
                    Collections.reverse(celcode);
                    Collections.reverse(countryCode);
                    Collections.reverse(placeholder);


                    for (int i = 0; i < celcode.size(); i++) {

                        if (MySharedPreferenceClass.getCountryCode(getApplicationContext()).equals(celcode.get(i))) {
                            celcodenumber = i;
                            break;


                        }


                    }


                    //initSpinner();


                }
            }

            @Override
            public void onFailure(Call<GetCountryCode> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();

            }
        });


    }

    public void initSpinner() {

        if (getActivity() != null) {


            //  CustomArrayAdapter adp2 = new CustomArrayAdapter(getContext(),countryname.toArray(new String[countryname.size()]));
            ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(), R.layout.update_info_spinner_layout, countryname);
            adp2.setDropDownViewResource(R.layout.checkedradiotextview);


            // adp2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);


            countryspinner.setAdapter(adp2);
            countryspinner.setSelection(celcodenumber);


            countryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    //Toast.makeText(getApplicationContext(),"Position"+ position, Toast.LENGTH_SHORT).show();


                    countryName = countryspinner.getSelectedItem().toString();
                    selctedposition = position;
                    updateMobile.setHint(placeholder.get(selctedposition));
                    //mobilevalidationlength = placeholder.get(selctedposition).length();
                    // updateMobile.setFilters(new InputFilter[]{new InputFilter.LengthFilter(placeholder.get(selctedposition).length())});
                    updateMobile.setText("");

                    Log.e("Gender Name--------.>>", countryName);

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }

            });

        }
    }

    public void validateProfileFields() {
        isAllConditionFulfilled = Util.checkTextViewValidation(updatefName, "برجاء كتابة الاسم الاول")
                && Util.checkTextViewValidation(updatelName, "برجاء كتابة الاسم الاخير")
                && Util.checkTextViewValidation(updateMail, "الرجاء إدخال بريد إلكتروني صحيح");


        /* && Util.checkTextViewValidation(updateMobile, "من فضلك أدخل رقم الجوال");*/


        if (!isAllConditionFulfilled) {
            if (Util.view_final != null) {

                Util.view_final.requestFocus();
            }


            return;
        }

        saveUpgardeUpdateProfile();

    }


    public void saveUpgardeUpdateProfile() {
   /*{"param":
   {"customer_id":83188,
   "firstname":"asdkjhasdjha","lastname":"sjdjdhaljdh",
     "mobilenumber":"",
     "phone":"",
      "email":"abhishek.srivastava@unyscape.com"}

      }*/



        /*{"param":{"newsLetterSubscription":"false",
        "firstname":"arpan test account",
        "mobilenumber":"",
        "lastname ":"bhatia",
        "customer_id":"74060",
        "email":"arpan@unyscape.com"}}*/
        /*https://upgrade.eoutlet.com/rest/V1/api/updateprofile*/
        https:
//upgrade.eoutlet.com/rest/V1/api/updateprofile
        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("firstname", updatefName.getText().toString());
        map1.put("lastname", updatelName.getText().toString());
        map1.put("email", updateMail.getText().toString());


       /* if(!updateMobile.getText().toString().isEmpty()) {
            map1.put("mobilenumber", updateMobile.getText().toString());
        }
        else{
            map1.put("mobilenumber", " ");
        }*/

        if (!updateMobile.getText().toString().isEmpty() && celcode.get(selctedposition) != null) {
            map1.put("mobilenumber", celcode.get(selctedposition) + updateMobile.getText().toString());
        } else {
            map1.put("mobilenumber", "");

        }
        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        map1.put("newsletter", String.valueOf(newsLetterSubscription));


        mainparam.put("param", map1);

        Call<ChangePassword> call = apiService.saveUpgardeUpdateInfo(mainparam);
        call.enqueue(new Callback<ChangePassword>() {
            @Override
            public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();

                    if (response.body().msg.equals("success")) {
                        if (MySharedPreferenceClass.getChoosenlanguage(view.getContext()).equals("ar")) {
                            Toast.makeText(getContext(), "تم تحديث الملف الشخصي بنجاح", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_LONG).show();
                        }
                        MySharedPreferenceClass.setMyFirstNamePref(getContext(), updatefName.getText().toString());
                        MySharedPreferenceClass.setMyLastNamePref(getContext(), updatelName.getText().toString());
                        MySharedPreferenceClass.setMobileNumber(getContext(), updateMobile.getText().toString());
                        MySharedPreferenceClass.setCountryCode(getContext(), celcode.get(selctedposition));
                        MySharedPreferenceClass.setEmail(getContext(), updateMail.getText().toString());
                        MySharedPreferenceClass.setMyUserName(getContext(), updatefName.getText().toString() + " " + updatelName.getText().toString());
                        Calendar c = Calendar.getInstance();
                        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

                        if (MySharedPreferenceClass.getMyUserName(getContext()) != null) {
                            userName = MySharedPreferenceClass.getMyUserName(getContext());
                        }
                        if (timeOfDay >= 6 && timeOfDay < 12) {
                            if (Locale == "ar") {
                               HomeFragmentNewDesign.greetingMessage.setText(userName != null ? "صباح الخير,  " + userName + "!" : "صباح الخير!");
                            } else {
                                HomeFragmentNewDesign.greetingMessage.setText(userName != null ? "Good Morning, " + userName + "!" : "Good Morning!");
                            }
                        } else if (timeOfDay >= 12 && timeOfDay < 17) {
                            if (Locale == "ar") {
                                HomeFragmentNewDesign.greetingMessage.setText(userName != null ? "مساء الخير,  " + userName + "!" : "مساء الخير!");
                            } else {
                                HomeFragmentNewDesign.greetingMessage.setText(userName != null ? "Good Afternoon, " + userName + "!" : "Good Afternoon!");
                            }
                        } else {
                            if (Locale == "ar") {
                                HomeFragmentNewDesign.greetingMessage.setText(userName != null ? "مساء النور,  " + userName + "!" : "مساء النور!");
                            } else {
                                HomeFragmentNewDesign.greetingMessage.setText(userName != null ? "Good Evening, " + userName + "!" : "Good Evening!");
                            }
                        }

                    } else {
                        parentDialogListener.hideProgressDialog();

                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                }

            }


            @Override
            public void onFailure(Call<ChangePassword> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();

            }
        });


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

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

    @Override
    public void onDetach() {
        super.onDetach();

    }


public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);

}


    public void getUpgradedguesttoken() {


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Call<GuestUser> call = apiService.getUpgradedGuestToken();
        call.enqueue(new Callback<GuestUser>() {
            @Override
            public void onResponse(Call<GuestUser> call, Response<GuestUser> response) {

                if (response.body() != null && response.body().msg != null) {
                    if (response.body().msg.equals("success")) {
                        parentDialogListener.hideProgressDialog();
                        MySharedPreferenceClass.setMaskkey(getContext(), response.body().maskKey);
                        MySharedPreferenceClass.setCartId(getContext(), response.body().quoteId);
                        MainActivity.notificationBadge.setVisibility(View.GONE);
                        MySharedPreferenceClass.setMaskkey(getApplicationContext(), response.body().maskKey);
                        MySharedPreferenceClass.setCartId(getApplicationContext(), response.body().quoteId);
                        MySharedPreferenceClass.setQuoteId(getApplicationContext(), " ");
                        MySharedPreferenceClass.setMyUserId(getApplicationContext(), null);
                        MySharedPreferenceClass.setMyUserName(getApplicationContext(), null);
                        MySharedPreferenceClass.setMyFirstNamePref(getApplicationContext(), null);
                        MySharedPreferenceClass.setMyLastNamePref(getApplicationContext(), null);
                        MySharedPreferenceClass.setBedgeCount(getApplicationContext(), 0);
                        MySharedPreferenceClass.setEmail(getApplicationContext(), null);


                        MySharedPreferenceClass.setAddressname(getApplicationContext(), null);
                        MySharedPreferenceClass.setCityname(getApplicationContext(), null);
                        MySharedPreferenceClass.setStreetName(getApplicationContext(), null);
                        MySharedPreferenceClass.setCountryname(getApplicationContext(), null);

                        MySharedPreferenceClass.setAddressphone(getApplicationContext(), null);
                        MySharedPreferenceClass.setCountryId(getApplicationContext(), null);
                        MySharedPreferenceClass.setfirstAdressname(getApplicationContext(), null);
                        MySharedPreferenceClass.setlastAdressname(getApplicationContext(), null);
                        MySharedPreferenceClass.setSelecteAddressdId(getApplicationContext(), "0");
                        MySharedPreferenceClass.setSelecteTokenName(getApplicationContext(), " ");
                        MySharedPreferenceClass.setMyPassword(getApplicationContext(), null);
                        MySharedPreferenceClass.setCookies(getApplicationContext(), " ");


                        wishListBadgeCount.setVisibility(View.GONE);
                        categorywishListBadgeCount.setVisibility(View.GONE);
                        if (productListBadgeCount != null) {
                            productListBadgeCount.setVisibility(View.GONE);
                        }

                        Calendar c = Calendar.getInstance();
                        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

                        if (MySharedPreferenceClass.getMyUserName(getContext()) != null) {
                            userName = MySharedPreferenceClass.getMyUserName(getContext());
                        }
                        if (timeOfDay >= 6 && timeOfDay < 12) {
                            if (Locale == "ar") {
                                HomeFragmentNewDesign.greetingMessage.setText(userName != null ? "صباح الخير,  " + userName + "!" : "صباح الخير!");
                            } else {
                                HomeFragmentNewDesign.greetingMessage.setText(userName != null ? "Good Morning, " + userName + "!" : "Good Morning!");
                            }
                        } else if (timeOfDay >= 12 && timeOfDay < 17) {
                            if (Locale == "ar") {
                                HomeFragmentNewDesign.greetingMessage.setText(userName != null ? "مساء الخير,  " + userName + "!" : "مساء الخير!");
                            } else {
                                HomeFragmentNewDesign.greetingMessage.setText(userName != null ? "Good Afternoon, " + userName + "!" : "Good Afternoon!");
                            }
                        } else {
                            if (Locale == "ar") {
                                HomeFragmentNewDesign.greetingMessage.setText(userName != null ? "مساء النور,  " + userName + "!" : "مساء النور!");
                            } else {
                                HomeFragmentNewDesign.greetingMessage.setText(userName != null ? "Good Evening, " + userName + "!" : "Good Evening!");
                            }
                        }
                        Fragment prFrag = new NewProfileFragment();

                        Bundle bund = new Bundle();
                        prFrag.setArguments(bund);


                        getFragmentManager()
                                .beginTransaction()
                                .replace(NewUpdateInfo.this.getId(), prFrag).addToBackStack(null)
                                .commit();


                    } else {
                        parentDialogListener.hideProgressDialog();


                    }
                }

            }


            @Override
            public void onFailure(Call<GuestUser> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
                Log.e(TAG, t.toString());

            }
        });


    }

}
