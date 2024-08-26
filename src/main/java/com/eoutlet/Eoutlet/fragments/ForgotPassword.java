package com.eoutlet.Eoutlet.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.adpters.CustomArrayAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.GetCountryCode;
import com.eoutlet.Eoutlet.pojo.OtpResponse;
import com.eoutlet.Eoutlet.pojo.ResetEmailPasswordresponse;
import com.eoutlet.Eoutlet.pojo.universalMessage;
import com.eoutlet.Eoutlet.utility.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ForgotPassword.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ForgotPassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPassword extends Fragment {
    View view;
    EditText email, edtmobile;
    TextView forgotPassword;
    RadioButton btnmobile, btnemail;
    boolean isAllConditionFulfilled;
    LinearLayout mobileverify, emailview;
    private List<String> countryname;
    private List<String> countryCode;
    private List<String> celcode;
    private List<String> placeholder;
    private String countryName;
    private int selctedposition = 0;
    String selectedmethod = "mobile";
    private int mobilevalidationlength;
    Spinner countryspinner;
    String Local = " ";
    public ParentDialogsListener parentDialogListener;

    // TODO: Rename and change types and number of parameters
    public static ForgotPassword newInstance(String param1, String param2) {
        ForgotPassword fragment = new ForgotPassword();
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
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
            Local = "ar";
            view = inflater.inflate(R.layout.activity_forgot_password, container, false);
        } else if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
            Local = "en";
            view = inflater.inflate(R.layout.activity_forgot_password_english, container, false);

        } else {


            view = inflater.inflate(R.layout.activity_forgot_password, container, false);
        }

        email = view.findViewById(R.id.eMail);
        forgotPassword = view.findViewById(R.id.forgotPasswordClick);

        btnmobile = view.findViewById(R.id.btnmobile);
        btnemail = view.findViewById(R.id.btnemail);
        mobileverify = view.findViewById(R.id.veryfybymobile);
        countryspinner = view.findViewById(R.id.countrycodeSpinner);
        edtmobile = view.findViewById(R.id.edtmobilrnumber);
        emailview = view.findViewById(R.id.emailView);


        btnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnemail.setTextColor(getResources().getColor(R.color.white));

                btnmobile.setTextColor(getResources().getColor(R.color.colour_black))
                ;
                if(MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                    btnmobile.setText("Mobile Number");
                }
                else{
                    btnmobile.setText("رقم الجوال");

                }

                selectedmethod = "email";
                emailview.setVisibility(View.VISIBLE);
                mobileverify.setVisibility(View.GONE);


            }
        });


        btnmobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                btnemail.setTextColor(getResources().getColor(R.color.colour_black));

                btnmobile.setTextColor(getResources().getColor(R.color.white));


                emailview.setVisibility(View.GONE);
                mobileverify.setVisibility(View.VISIBLE);

                selectedmethod = "mobile";

            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (selectedmethod.equals("email")) {
                    if (Local.equals("ar")) {
                        isAllConditionFulfilled = Util.isEmailValid(email) && Util.checkTextViewValidation(email, "يرجى ملء معرف البريد الإلكتروني المسجل");
                    } else {
                        isAllConditionFulfilled = Util.isEmailValid(email) && Util.checkTextViewValidation(email, "Please fill in the registered email id");
                    }

                    if (isAllConditionFulfilled) {


                        forgotupgradePassword();


                    } else {

                        if (Util.view_final != null) {

                            Util.view_final.requestFocus();
                        }

                        return;
                    }


                } else {

                    if (selectedmethod.equals("mobile")) {

                        if (Local.equals("ar")) {
                            isAllConditionFulfilled = Util.checkTextViewValidation(edtmobile, "من فضلك أدخل رقم الجوال")
                                    && Util.checkMobileValidation(edtmobile, mobilevalidationlength, "الرجاء إدخال بريد إلكتروني صحيح");
                        } else {
                            isAllConditionFulfilled = Util.checkTextViewValidation(edtmobile, "Please enter the mobile number")
                                    && Util.checkMobileValidation(edtmobile, mobilevalidationlength, "Please enter the valid mobile number");
                        }


                        if (isAllConditionFulfilled) {

                            opengetOtpDialog();


                        } else {

                            if (Util.view_final != null) {

                                Util.view_final.requestFocus();
                            }

                            return;
                        }


                    }


                }

            }
        });

        getcountryDetail();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void forgotupgradePassword() {

        parentDialogListener.showProgressDialog();

      /*  BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);*/


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        HashMap<Object, Object> mainparam = new HashMap<>();
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("email", email.getText().toString());
        mainparam.put("param", map1);
        Call<ResetEmailPasswordresponse> call;
        if (Local.equals("en")) {
            call = apiService.upgradedforGotPassword(mainparam);
        } else {
            call = apiService.upgradedforGotPasswordarabic(mainparam);
        }

        call.enqueue(new Callback<ResetEmailPasswordresponse>() {
            @Override
            public void onResponse(Call<ResetEmailPasswordresponse> call, Response<ResetEmailPasswordresponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().response.equals("success")) {

                        if (Local.equals("ar")) {
                            Toast.makeText(getContext(), "تم إرسال بريد إلكتروني لإعادة تعين كلمة السر", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Reset Password sent to your email ", Toast.LENGTH_SHORT).show();
                        }

                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction trans = manager.beginTransaction();
                        trans.remove(new ForgotPassword());
                        trans.commit();
                        manager.popBackStack();
                    } else {
                        parentDialogListener.hideProgressDialog();
                        if (Local.equals("ar")) {
                            Toast.makeText(getContext(), response.body().message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), response.body().message, Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<ResetEmailPasswordresponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                if (Local.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

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


    public void getcountryDetail() {
        parentDialogListener.showProgressDialog();
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

                if (Local.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    public void initSpinner() {

        if (getActivity() != null) {


            //  CustomArrayAdapter adp2 = new CustomArrayAdapter(getContext(),countryname.toArray(new String[countryname.size()]));
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(), R.layout.spinnertextviewenglish, countryname);
                adp2.setDropDownViewResource(R.layout.checkedradiotextviewenglish);

                countryspinner.setAdapter(adp2);
            } else {
                ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(), R.layout.spinnertextview2, countryname);
                adp2.setDropDownViewResource(R.layout.checkedradiotextview);


                countryspinner.setAdapter(adp2);

            }


            countryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {


                    countryName = countryspinner.getSelectedItem().toString();
                    selctedposition = position;
                    edtmobile.setHint(placeholder.get(selctedposition));
                    mobilevalidationlength = placeholder.get(selctedposition).length();
                    //edtmobile.setFilters(new InputFilter[]{new InputFilter.LengthFilter(placeholder.get(selctedposition).length())});
                    edtmobile.setText("");

                    Log.e("Country Name--------.>>", countryName);

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }

            });

        }
    }


    public void sendupgraderesetPasswordOtp() {

        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Map<String, String> map1 = new HashMap<>();
        map1.put("mobile", celcode.get(selctedposition) + edtmobile.getText().toString());
        map1.put("resend", "0");


        Call<OtpResponse> call = apiService.sendupgradeotpforresetPassword("https://upgrade.eoutlet.com/vsms/app/sendforgotpassword?mobile=" + celcode.get(selctedposition) + edtmobile.getText().toString() + "&resend=0" +
                "&application=1");
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().success) {
                        if (Local.equals("ar")) {
                            Toast.makeText(getContext(), "تم ارسال رمز التحقق", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "OTP sent successfully.", Toast.LENGTH_SHORT).show();
                        }


                        DialogFragment prFrag = new RePasswordVerifyOtp();
                        Bundle bund = new Bundle();
                        bund.putString("mobile", celcode.get(selctedposition) + edtmobile.getText().toString());

                        prFrag.setArguments(bund);

                        prFrag.setTargetFragment(ForgotPassword.this, 1001);
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
                if (Local.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void opengetOtpDialog() {

        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        if (Local.equals("ar")) {
            alertDialogBuilder.setMessage("التأكد من صحة رقم الجوال");
            alertDialogBuilder.setPositiveButton("أدخل الكود",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //sendresetPasswordOtp();
                            sendupgraderesetPasswordOtp();

                        }
                    });

            alertDialogBuilder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {


                }
            });
        } else {
            alertDialogBuilder.setMessage("Ensure that the mobile number is correct");
            alertDialogBuilder.setPositiveButton("Get the code",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //sendresetPasswordOtp();
                            sendupgraderesetPasswordOtp();

                        }
                    });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {


                }
            });

        }


        alertDialogBuilder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {


            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();

        TextView messageView = (TextView) alertDialog.findViewById(android.R.id.message);
        messageView.setTypeface(null, Typeface.BOLD);
        messageView.setGravity(Gravity.CENTER);


        final Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

        positiveButton.setGravity(Gravity.CENTER);

        final Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setVisibility(View.GONE);


    }


}
