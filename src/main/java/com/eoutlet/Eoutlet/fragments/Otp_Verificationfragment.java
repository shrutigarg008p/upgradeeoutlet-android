package com.eoutlet.Eoutlet.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.pojo.OtpResponse;
import com.eoutlet.Eoutlet.pojo.SignUpResponse;
import com.eoutlet.Eoutlet.pojo.VerificationResponse;
import com.eoutlet.Eoutlet.utility.Util;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;


public class Otp_Verificationfragment extends DialogFragment {
    private View view;
    private  Dialog dialog;
    private EditText edtotp;
    private Button register,resendotp;
    private  ParentDialogsListener parentDialogListener;
    private String firstname, lastname, email, password, mobile,selectedgender;
    private TextView resendtext,maintextview;
    private Handler handler;
    private Runnable handlerTask;
    static  int resend;

    int i;
    private String value;

    private OnFragmentInteractionListener mListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        value = getArguments().getString("flag");
        view = inflater.inflate(R.layout.fragment_otp__verificationfragment, null);
        edtotp = view.findViewById(R.id.edittext_otp);
        register = view.findViewById(R.id.register);
        resendotp = view.findViewById(R.id.resendotp);
        resendtext = view.findViewById(R.id.resendtext);
        maintextview = view.findViewById(R.id.textviewmain);

        builder.setView(view);
        //initViews();
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        firstname = getArguments().getString("firstname");
        lastname = getArguments().getString("lastname");
        email = getArguments().getString("email");
        password = getArguments().getString("password");
        mobile = getArguments().getString("mobile");
        selectedgender = getArguments().getString("gender");


        System.out.println("Selected Gender is---->"+selectedgender);

        maintextview.setText("تم ارسال رسالة نصية لكم بالكود السري مكون من 4 ارقام"/*+" "+mobile*/);
        initTimer();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                registerotp(v);
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
    public  void initTimer()
    {
        i=30;

        resendotp.setEnabled(false);
        resendotp.setBackgroundResource(R.drawable.grey_filled_rectangle);
        resendtext.setVisibility(View.VISIBLE);
        //resendtext.setTextColor(Color.parseColor("#ffffff"));



        handler = new Handler();
        handlerTask = new Runnable()
        {
            @Override
            public void run() {
                // do something

                resendtext.setText("إعادة ارسال الكود بعد"+" "+String.valueOf(i)+" "+" ثانية");
                //resendtext.setText("Resend OTP after"+" "+String.valueOf(i)+" "+"Seconds");
                i--;




                handler.postDelayed(handlerTask, 1000);
                if(i==0) {
                    handler.removeCallbacks(handlerTask);
                    resendotp.setEnabled(true);
                    resendtext.setVisibility(View.INVISIBLE);
                    resendotp.setBackgroundResource(R.drawable.red_rectangle);
                }
            }
        };
        handlerTask.run();
    }

    public void resendotp() {
        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Map<String, String> map1 = new HashMap<>();
        map1.put("mobile", /*"+917906156955"*/mobile   );
        map1.put("resend", String.valueOf(resend));


        Call<OtpResponse> call = apiService.sendotp(map1);
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


    public void registerotp(View v) {
        if (Util.checkTextViewValidation(edtotp, "برجاء كتابة الاسم الاول")) {
            parentDialogListener.showProgressDialog();

            BasicRequest apiService =
                    BasicBuilder.getClient().create(BasicRequest.class);


            Map<String, String> map1 = new HashMap<>();
            map1.put("otp", edtotp.getText().toString()/*countryCode.get(selctedposition) + mobile   */);
            map1.put("mobile", mobile);


            Call<VerificationResponse> call = apiService.otpverification(map1);
            call.enqueue(new Callback<VerificationResponse>() {
                @Override
                public void onResponse(Call<VerificationResponse> call, Response<VerificationResponse> response) {


                    if (response.body() != null) {
                        parentDialogListener.hideProgressDialog();


                        if (response.body().success) {
                            parentDialogListener.hideProgressDialog();
                            //Toast.makeText(getContext(), "OTP verified Successfully", Toast.LENGTH_SHORT).show();
                             makeSignUp();


                        } else {
                            parentDialogListener.hideProgressDialog();


                        }
                    } else {
                        parentDialogListener.hideProgressDialog();


                    }


                }


                @Override
                public void onFailure(Call<VerificationResponse> call, Throwable t) {
                    parentDialogListener.hideProgressDialog();
                    Log.e(TAG, t.toString());

                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                }
            });
        } else {

            if (Util.view_final != null) {

                Util.view_final.requestFocus();
            }
        }
    }


    public void makeSignUp()
    {
        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Map<String, String> map1 = new HashMap<>();
        map1.put("firstname", firstname);
        map1.put("lastname", lastname);
        map1.put("email", email);
        map1.put("mobilenumber", mobile);
        map1.put("password", password);
        map1.put("gender",selectedgender);


        Call<SignUpResponse> call = apiService.getSignUp(map1);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if ((response.body().response.equals("success") )) {


                       // Toast.makeText(getContext(), "اشترك بنجاح", Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        Fragment prFrag = new LoginNewFragment();

                        Bundle bundle = new Bundle();
                        bundle.putString("email",email);
                        bundle.putString("password", password);
                        bundle.putString("flag", value);


                        prFrag.setArguments(bundle);




                        getFragmentManager()
                                .beginTransaction()/*.addToBackStack(null)*/
                                .replace(R.id.profileContainer, prFrag)
                                .commit();


                    } else {
                        parentDialogListener.hideProgressDialog();
                        //Toast.makeText(getContext(), response.body().message, Toast.LENGTH_SHORT).show();


                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                parentDialogListener.hideProgressDialog();
               // Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });







    }
}
