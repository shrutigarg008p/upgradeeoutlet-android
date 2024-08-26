package com.eoutlet.Eoutlet.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
//import com.crashlytics.android.Crashlytics;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.AddressList;
import com.eoutlet.Eoutlet.pojo.LoginResponse;
import com.eoutlet.Eoutlet.pojo.OtpResponse;
import com.eoutlet.Eoutlet.pojo.Verifyforgetpasswordresponse;
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
 * {@link RePasswordVerifyOtp.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RePasswordVerifyOtp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginVerifyOtp extends DialogFragment {
    private View view;
    private Dialog dialog;
    private EditText edtotp;
    private Button verifyOtp, resendotp;
    private TextView resendtext, maintextview;
    private Handler handler;
    private ParentDialogsListener parentDialogListener;
    private String mobile;
    private Runnable handlerTask;
    private int i;
    static int resend;
    private String value;
    private OnFragmentInteractionListener mListener;

    public LoginVerifyOtp() {
        // Required empty public constructor
    }


    public static RePasswordVerifyOtp newInstance(String param1, String param2) {
        RePasswordVerifyOtp fragment = new RePasswordVerifyOtp();
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
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
            view = inflater.inflate(R.layout.fragment_re_password_verify_otp_english, null);
        } else {
            view = inflater.inflate(R.layout.fragment_re_password_verify_otp, null);
        }

        edtotp = view.findViewById(R.id.edittext_otp);
        verifyOtp = view.findViewById(R.id.verifyOtp);
        resendotp = view.findViewById(R.id.resendotp);
        resendtext = view.findViewById(R.id.resendtext);
        maintextview = view.findViewById(R.id.textviewmain);
        builder.setView(view);
        //initViews();
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        mobile = getArguments().getString("mobile");
        //  maintextview.setText("A text message with a 4-digit Verification code has been sent to"+" "+mobile);
        if (MySharedPreferenceClass.getChoosenlanguage(view.getContext()).equals("ar")) {
            maintextview.setText("تم ارسال رسالة نصية لكم بالكود السري مكون من 4 ارقام");
        }
        initTimer();
        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upgradeverifyOtp(v);
                // verifyOtp(v);
            }
        });
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend++;
                //resendotp();
                upgraderesendotp();


            }
        });

        return dialog;
    }

    public void resendotp() {
        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Map<String, String> map1 = new HashMap<>();
        map1.put("mobile", /*"+917906156955"*/mobile);
        map1.put("resend", String.valueOf(resend));


        Call<OtpResponse> call = apiService.sendotpforLogin(map1);
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().success) {
                        Toast.makeText(getContext(), "تم ارسال الكود بنجاح", Toast.LENGTH_SHORT).show();
                        initTimer();


                    } else {
                        parentDialogListener.hideProgressDialog();
                        Toast.makeText(getContext(), "فشل الارسال -اعادة المحاولة", Toast.LENGTH_SHORT).show();


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

    public void upgraderesendotp() {
        parentDialogListener.showProgressDialog();

       /* BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);*/

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
        Map<String, String> map1 = new HashMap<>();
        map1.put("mobile", /*"+917906156955"*/mobile);
        map1.put("resend", String.valueOf(resend));


        Call<OtpResponse> call = apiService.upgradesendotpforLogin("https://upgrade.eoutlet.com/vsms/app/sendLogin?" + mobile + "&resend=" + String.valueOf(resend) + "&application=1"/*map1*/);
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().success) {
                        Toast.makeText(getContext(), "تم ارسال الكود بنجاح", Toast.LENGTH_SHORT).show();
                        initTimer();


                    } else {
                        parentDialogListener.hideProgressDialog();
                        Toast.makeText(getContext(), "فشل الارسال -اعادة المحاولة", Toast.LENGTH_SHORT).show();


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


    public void upgradeverifyOtp(View v) {
        if (Util.checkTextViewValidation(edtotp, "برجاء كتابة الاسم الاول")) {
            parentDialogListener.showProgressDialog();

            /*BasicRequest apiService =
                    BasicBuilder.getClient().create(BasicRequest.class);*/
            BasicRequest apiService =
                    UpgradedBasicBuilder.getClient().create(BasicRequest.class);


            Map<String, String> map1 = new HashMap<>();
            map1.put("otp", edtotp.getText().toString()/*countryCode.get(selctedposition) + mobile   */);
            map1.put("mobile", mobile);

            /*https://upgrade.eoutlet.com/vsms/app/verifylogin/*/
            Call<Verifyforgetpasswordresponse> call = apiService.otpverificationforLogin("https://upgrade.eoutlet.com/vsms/app/verifylogin/?" + "mobile=" + mobile + "&otp=" + edtotp.getText().toString() + "&application=1");
            call.enqueue(new Callback<Verifyforgetpasswordresponse>() {
                @Override
                public void onResponse(Call<Verifyforgetpasswordresponse> call, Response<Verifyforgetpasswordresponse> response) {


                    if (response.body() != null) {
                        parentDialogListener.hideProgressDialog();


                        if (response.body().success) {
                            parentDialogListener.hideProgressDialog();
                            //Toast.makeText(getContext(), "OTP verified Successfully", Toast.LENGTH_SHORT).show();


                            if (getTargetFragment() != null) {
                                Bundle bundle = new Bundle();
                                bundle.putString("otp", edtotp.getText().toString());
                                Intent mIntent = new Intent();
                                mIntent.putExtras(bundle);
                                getTargetFragment().onActivityResult(1002,
                                        Activity.RESULT_OK, mIntent);
                            }

                            getDialog().cancel();


                        } else {

                            Toast.makeText(getContext(), response.body().msg.toString(), Toast.LENGTH_LONG).show();
                            parentDialogListener.hideProgressDialog();


                        }
                    } else {
                        parentDialogListener.hideProgressDialog();


                    }


                }


                @Override
                public void onFailure(Call<Verifyforgetpasswordresponse> call, Throwable t) {
                    Toast.makeText(getContext(), "The OTP code is not valid", Toast.LENGTH_LONG).show();
                    parentDialogListener.hideProgressDialog();
                    Log.e(TAG, t.toString());


                }
            });
        } else {

            if (Util.view_final != null) {

                Util.view_final.requestFocus();
            }
        }
    }


    public void initTimer() {
        i = 30;

        resendotp.setEnabled(false);
        resendotp.setBackgroundResource(R.drawable.grey_filled_rectangle);
        resendtext.setVisibility(View.VISIBLE);


        handler = new Handler();
        handlerTask = new Runnable() {
            @Override
            public void run() {
                // do something

                if (MySharedPreferenceClass.getChoosenlanguage(view.getContext()).equals("ar")) {
                    resendtext.setText("إعادة ارسال الكود بعد" + " " + String.valueOf(i) + " " + " ثانية");
                }
                else{
                    resendtext.setText("Resend the code after" + " " + String.valueOf(i) + " " + "Seconds");

                }


                i--;


                handler.postDelayed(handlerTask, 1000);
                if (i == 0) {
                    handler.removeCallbacks(handlerTask);
                    resendotp.setEnabled(true);
                    resendotp.setTextColor(Color.parseColor("#ffffff"));
                    resendtext.setVisibility(View.INVISIBLE);
                    resendotp.setBackgroundResource(R.drawable.red_rectangle);
                }
            }
        };
        handlerTask.run();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            parentDialogListener = (ParentDialogsListener) context;

        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " Activity's Parent should be Parent Activity");
        }
        super.onAttach(context);
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
}
