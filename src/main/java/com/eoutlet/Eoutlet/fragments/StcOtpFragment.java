package com.eoutlet.Eoutlet.fragments;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.ThankyouActivity;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.AddtoCartResponse;
import com.eoutlet.Eoutlet.pojo.Message;
import com.eoutlet.Eoutlet.pojo.OrderResponseStc;
import com.eoutlet.Eoutlet.pojo.StcOrderResponse;
import com.eoutlet.Eoutlet.pojo.StcResendOtpResponse;
import com.eoutlet.Eoutlet.pojo.StcRetainApi;
import com.eoutlet.Eoutlet.pojo.Verifyforgetpasswordresponse;
import com.eoutlet.Eoutlet.utility.Util;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eoutlet.Eoutlet.listener.PaginationListener.TAG;

//import static com.crashlytics.android.beta.Beta.TAG;


public class StcOtpFragment extends DialogFragment {

    private Otp_Verificationfragment.OnFragmentInteractionListener mListener;
    private View view;
    private EditText edtotp;
    private Button register, resendotp;
    private TextView resendtext, maintextview;
    private Dialog dialog;
    private String orderId, otpRefernce, sTCPayPmtReference, amount;
    private ParentDialogsListener parentDialogListener;
    private String mobilenumber;
    int i;
    private Handler handler;
    private Runnable handlerTask;
    static int resend;
    String Locale;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_otp__verificationfragment, null);
        } else {
            Locale = "en";
            view = inflater.inflate(R.layout.fragment_otp__verificationenglish, null);

        }


        orderId = getArguments().getString("orderId");
        edtotp = view.findViewById(R.id.edittext_otp);
        register = view.findViewById(R.id.register);
        resendotp = view.findViewById(R.id.resendotp);
        resendtext = view.findViewById(R.id.resendtext);
        maintextview = view.findViewById(R.id.textviewmain);

        builder.setView(view);


        orderId = getArguments().getString("orderId");
        otpRefernce = getArguments().getString("otp_refernce");
        sTCPayPmtReference = getArguments().getString("sTCPayPmtReference");
        amount = getArguments().getString("amount");
        mobilenumber = getArguments().getString("mobileNumber");


        //initViews();
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        //System.out.println("bdfvjhdsgfjhdsgfjgdsjfgdsjhfgdjku");

                        // Toast.makeText(getContext(),"APi Hit",Toast.LENGTH_SHORT).show();
                        //When you touch outside of dialog bounds,
                        //the dialog gets canceled and this method executes.
                    }
                }
        );
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {

            maintextview.setText("تم ارسال رسالة نصية لكم بالكود السري مكون من 6 ارقام"/*+" "+mobile*/);
        } else {
            maintextview.setText("A text message has been sent to you with a secret code consisting of 6 digits"/*+" "+mobile*/);


        }


        initTimer();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Upgradedostcpayment(v);
                //dostcpayment(v);
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

    public void initTimer() {
        i = 30;

        resendotp.setEnabled(false);
        // resendotp.setBackgroundResource(R.drawable.grey_filled_rectangle);
        resendtext.setVisibility(View.VISIBLE);
        //resendtext.setTextColor(Color.parseColor("#ffffff"));


        handler = new Handler();
        handlerTask = new Runnable() {
            @Override
            public void run() {
                // do something


                if (Locale.equals("ar")) {
                    resendtext.setText("إعادة إرسال بعد 30 ثانية" + " " + String.valueOf(i) + " " + " ثانية");
                } else {
                    resendtext.setText("Resend After" + " " + String.valueOf(i) + " " + " Seconds");
                }


                //resendtext.setText("Resend OTP after"+" "+String.valueOf(i)+" "+"Seconds");
                i--;


                handler.postDelayed(handlerTask, 1000);
                if (i == 0) {
                    handler.removeCallbacks(handlerTask);
                    resendotp.setEnabled(true);
                    resendtext.setVisibility(View.INVISIBLE);
                    resendotp.setBackgroundResource(R.drawable.red_rectangle);
                    resendotp.setTextColor(Color.WHITE);
                }
            }
        };
        handlerTask.run();
    }

    public void resendotp() {
        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();

        map1.put("order_id", orderId);
        map1.put("amount", amount);
        map1.put("stcmobile", mobilenumber);

        mainparam.put("param", map1);
        Call<OrderResponseStc> call = apiService.initiateStcOrder(mainparam);
        call.enqueue(new Callback<OrderResponseStc>() {
            @Override
            public void onResponse(Call<OrderResponseStc> call, Response<OrderResponseStc> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().msg != null && response.body().msg.equals("success")) {

                        if (Locale.equals("ar")) {
                            Toast.makeText(getContext(), "تم ارسال رمز التحقق", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(getContext(), "OTP sent successfully.", Toast.LENGTH_SHORT).show();


                        }

                        resendotp.setEnabled(false);
                        //  resendotp.setBackgroundResource(R.drawable.grey_filled_rectangle);
                        resendotp.setBackgroundResource(R.drawable.grey_rectangle_shape);
                        resendotp.setTextColor(Color.BLACK);
                        resendtext.setVisibility(View.VISIBLE);


                        initTimer();


                    } else {
                        parentDialogListener.hideProgressDialog();
                        if (Locale.equals("ar")) {
                            Toast.makeText(getContext(), "حدث خطأ- يرجى اعادة المحاولة ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Something Went wrong- please try again", Toast.LENGTH_SHORT).show();

                        }
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                }


            }


            @Override
            public void onFailure(Call<OrderResponseStc> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        retainstcorder();
        //System.out.println("bdfvjhdsgfjhdsgfjgdsjfgdsjhfgdjku");
        super.onCancel(dialog);


    }


    public void retainstcorder() {


/*"{
    ""param"": {
        ""order_id"": ""000000028"",
        ""devicetype"":""android"",
         ""cart_id"" :""4564""
    }
}"*/
        /*https://upgrade.eoutlet.com/rest/V1/api/stccartretain*/

        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> query = new HashMap<>();


        query.put("order_id", orderId);
        query.put("devicetype", "android");
        query.put("cart_id", MySharedPreferenceClass.getQuoteId(getContext()));

        mainparam.put("param", query);


        Call<StcRetainApi> call = apiService.getRetainorderfromStc(mainparam);
        call.enqueue(new Callback<StcRetainApi>() {
            @Override
            public void onResponse(Call<StcRetainApi> call, Response<StcRetainApi> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();

                    if (response.body().msg.equals("success")) {


                    } else {
                        //Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_SHORT).show();
                        parentDialogListener.hideProgressDialog();


                    }
                }


            }


            @Override
            public void onFailure(Call<StcRetainApi> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(ContentValues.TAG, t.toString());

                //Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void Upgradedostcpayment(View v) {

        https:
//upgrade.eoutlet.com/rest/V1/api/getstcresponse
        /* https://upgrade.eoutlet.com/rest/V1/api/getstcresponse*/

       /* {
            "param": {
            "OtpReference": "IbBNeCEzEOMwEYzWOoOP",
                    "OtpValue":"1090",
                    "STCPayPmtReference":"4678220561",
                    "order_id":"000000028",
                    "devicetype":"ios"
        }
        }*/


        if (Util.checkTextViewValidation(edtotp, "برجاء كتابة الاسم الاول")) {
            parentDialogListener.showProgressDialog();

            BasicRequest apiService =
                    UpgradedBasicBuilder.getClient().create(BasicRequest.class);

            Map<Object, Object> mainparam = new HashMap<>();
            Map<String, String> map1 = new HashMap<>();
            map1.put("OtpValue", edtotp.getText().toString()/*countryCode.get(selctedposition) + mobile   */);
            map1.put("OtpReference", otpRefernce);
            map1.put("STCPayPmtReference", sTCPayPmtReference);
            map1.put("order_id", orderId);
            map1.put("devicetype", "android");

            mainparam.put("param", map1);


            Call<StcOrderResponse> call = apiService.Upgradedopaymentwithstc(mainparam);
            call.enqueue(new Callback<StcOrderResponse>() {
                @Override
                public void onResponse(Call<StcOrderResponse> call, Response<StcOrderResponse> response) {


                    if (response.body() != null) {
                        parentDialogListener.hideProgressDialog();


                        if (response.body().msg.equals("success")) {
                            parentDialogListener.hideProgressDialog();
                            //Toast.makeText(getContext(), "OTP verified Successfully", Toast.LENGTH_SHORT).show();

                            dismiss();
                            Intent in = new Intent(view.getContext(), ThankyouActivity.class);
                            in.putExtra("orderId", orderId);
                            in.putExtra("statusflag", response.body().msg);
                            in.putExtra("totalvalue", amount);


                            if (in != null) {

                                startActivity(in);
                            }


                        } else {
                            Toast.makeText(getContext(), response.body().data.toString(), Toast.LENGTH_LONG).show();
                            dialog.cancel();
                            parentDialogListener.hideProgressDialog();


                        }
                    } else {
                        parentDialogListener.hideProgressDialog();


                    }


                }


                @Override
                public void onFailure(Call<StcOrderResponse> call, Throwable t) {
                    parentDialogListener.hideProgressDialog();
                    Log.e(ContentValues.TAG, t.toString());


                }
            });
        } else {

            if (Util.view_final != null) {

                Util.view_final.requestFocus();
            }
        }
    }
}
