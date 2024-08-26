package com.eoutlet.Eoutlet.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.SendOtpResponse;
import com.eoutlet.Eoutlet.pojo.VerifyOtpResponse;
import com.eoutlet.Eoutlet.utility.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SaveAddressVerifyOtp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaveAddressVerifyOtp extends DialogFragment {
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
    private LoginVerifyOtp.OnFragmentInteractionListener mListener;
    String Locale;

    public SaveAddressVerifyOtp() {
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
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                view = inflater.inflate(R.layout.fragment_re_password_verify_otp_arabic, null);
                Locale = "ar";
            } else {
                view = inflater.inflate(R.layout.fragment_re_password_verify_otp, null);
                Locale = "en";
            }
        } else {
            view = inflater.inflate(R.layout.fragment_re_password_verify_otp_arabic, null);
            Locale = "ar";
        }
        edtotp = view.findViewById(R.id.edittext_otp);
        verifyOtp = view.findViewById(R.id.verifyOtp);
        resendotp = view.findViewById(R.id.resendotp);
        resendtext = view.findViewById(R.id.resendtext);
        maintextview = view.findViewById(R.id.textviewmain);
        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        mobile = getArguments().getString("mobile");
        initTimer();
        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOtp(v);
            }
        });
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend++;
                resendotp();
            }
        });

        return dialog;
    }

    // function to resend OTP
    public void resendotp() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<SendOtpResponse> call = apiService.reSendOtp("https://upgrade.eoutlet.com/vsms/app/send?mobile=" + mobile + "&resend=" + String.valueOf(1)+ "&customer_id=" + MySharedPreferenceClass.getMyUserId(getContext()));

        call.enqueue(new Callback<SendOtpResponse>() {
            @Override
            public void onResponse(Call<SendOtpResponse> call, Response<SendOtpResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().success == true) {
                        Toast.makeText(getContext(), Locale == "ar" ? "تم ارسال الكود بنجاح" :
                                "The code has been sent successfully", Toast.LENGTH_SHORT).show();
                        initTimer();
                    } else {
                        parentDialogListener.hideProgressDialog();
                        Toast.makeText(getContext(), Locale == "ar" ? "فشل الارسال -اعادة المحاولة" :
                                "Send Failed - Retry", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                            "An error occurred - please try again", Toast.LENGTH_LONG).show();
                    parentDialogListener.hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<SendOtpResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });

    }

    // function to verify OTP
    public void verifyOtp(View v) {
        if (Util.checkTextViewValidation(edtotp, Locale == "ar" ? "برجاء كتابة الاسم الاول" : "Please Enter Otp")) {
            parentDialogListener.showProgressDialog();
            BasicRequest apiService =
                    BasicBuilder.getClient().create(BasicRequest.class);

            Call<VerifyOtpResponse> call = apiService.verifyOtp("https://upgrade.eoutlet.com/vsms/app/verify?mobile=" + mobile + "&otp=" + edtotp.getText().toString());
            call.enqueue(new Callback<VerifyOtpResponse>() {
                @Override
                public void onResponse(Call<VerifyOtpResponse> call, Response<VerifyOtpResponse> response) {
                    if (response.body() != null) {
                        parentDialogListener.hideProgressDialog();
                        if (response.body().success == true) {
                            Log.e("verify otp", "success");
                            parentDialogListener.hideProgressDialog();
                            if (getTargetFragment() != null) {
                                Bundle bundle = new Bundle();
                                bundle.putString("otp", response.body().otp);
                                Intent mIntent = new Intent();
                                mIntent.putExtras(bundle);
                                getTargetFragment().onActivityResult(1002,
                                        Activity.RESULT_OK, mIntent);
                            }
                            getDialog().cancel();

                        } else {
                            Log.e("verify otp", "fail");
                            Toast.makeText(getContext(), response.body().msg, Toast.LENGTH_LONG).show();
                            parentDialogListener.hideProgressDialog();
                        }
                    } else {
                        Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                                "An error occurred - please try again", Toast.LENGTH_LONG).show();
                        parentDialogListener.hideProgressDialog();
                    }
                }

                @Override
                public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                            "An error occurred - please try again", Toast.LENGTH_LONG).show();
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

    // function to init timer to resend verification code
    public void initTimer() {
        i = 30;
        resendotp.setEnabled(false);
        resendotp.setBackgroundResource(R.drawable.grey_rectangle_shape);
        resendtext.setVisibility(View.VISIBLE);
        resendtext.setTextColor(Color.BLACK);
        handler = new Handler();
        handlerTask = new Runnable() {
            @Override
            public void run() {
                // set Resend text message
                resendtext.setText(Locale == "ar" ? "إعادة ارسال الكود بعد" + " " + String.valueOf(i) + " " + " ثانية" : "Resend the code after " + i + " seconds.");
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
