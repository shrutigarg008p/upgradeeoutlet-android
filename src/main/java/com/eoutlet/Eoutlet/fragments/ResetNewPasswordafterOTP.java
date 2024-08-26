package com.eoutlet.Eoutlet.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.ResetPasswordresponse;
import com.eoutlet.Eoutlet.pojo.universalMessage;
import com.eoutlet.Eoutlet.utility.Util;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class ResetNewPasswordafterOTP extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    View view;
    EditText edtnewpassword, edtconfirmnewpassword;
    boolean isAllConditionFulfilled;
    private TextView btnsubmitnewpassword;
    private String customerId, url;
    public ParentDialogsListener parentDialogListener;
    private ForgotPassword.OnFragmentInteractionListener mListener;
    private String Locale = " ";

    public ResetNewPasswordafterOTP() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ResetNewPasswordafterOTP newInstance(String param1, String param2) {
        ResetNewPasswordafterOTP fragment = new ResetNewPasswordafterOTP();


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
        if(MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_reset_new_passwordafter_ot, container, false);
        }
        else{
            Locale = "en";
            view = inflater.inflate(R.layout.fragment_reset_new_password_english, container, false);
        }
        initViews();
        return view;

    }

    public void initViews() {
        edtnewpassword = view.findViewById(R.id.newpassword);
        edtconfirmnewpassword = view.findViewById(R.id.confirmnewresetpassword);
        btnsubmitnewpassword = view.findViewById(R.id.submitnewpassword);
        customerId = getArguments().getString("customerId");
        url = getArguments().getString("url");


        btnsubmitnewpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Locale.equals("ar")) {
                    isAllConditionFulfilled =

                            Util.checkTextViewValidation(edtnewpassword, "برجاء كتابة كلمة السر")
                                    && Util.checkTextViewValidation(edtconfirmnewpassword, "الرجاء إدخال تأكيد كلمة المرور") && Util.isPasswordLengthCorrect(edtnewpassword, "كلمة السر يجب أن لا تقل عن 7 أرقام")
                                    && Util.isPasswordMatched(edtnewpassword, edtconfirmnewpassword, "كلمة المرور لا تتطابق مع تأكيد كلمة المرور");

                }
                else{
                    isAllConditionFulfilled =      Util.checkTextViewValidation(edtnewpassword, "Please type your password")
                            && Util.checkTextViewValidation(edtconfirmnewpassword, "Please enter the confirm password") && Util.isPasswordLengthCorrect(edtnewpassword, "There must be atleast seven characters in password")
                            && Util.isPasswordMatched(edtnewpassword, edtconfirmnewpassword, "Password doesn't match with confirm password");

                }

                if (!isAllConditionFulfilled) {
                    if (Util.view_final != null) {

                        Util.view_final.requestFocus();
                    }


                    return;
                }
                resetNewPassword();


            }
        });


    }


    public void resetNewPassword() {

/*{"param":{"customer_id":83188,"newpassword":"admin@321"}}*/
        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainmap = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("customer_id", customerId);
        map1.put("newpassword", edtnewpassword.getText().toString());

        mainmap.put("param",map1);



        Call<ResetPasswordresponse> call = apiService.resetnewPassword(mainmap);
        call.enqueue(new Callback<ResetPasswordresponse>() {
            @Override
            public void onResponse(Call<ResetPasswordresponse> call, Response<ResetPasswordresponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().response.equals("success")) {
                        if(Locale.equals("ar")) {
                            Toast.makeText(getContext(), "تم استعادة كلمة السر بنجاح", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(), "Password Reset successfully", Toast.LENGTH_SHORT).show();
                        }

                        Fragment prFrag = new NewProfileFragment();

                        Bundle bund  =  new Bundle();
                        prFrag.setArguments(bund);


                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.profileContainer, prFrag).addToBackStack(null)
                                .commit();


                    } else {
                        parentDialogListener.hideProgressDialog();


                        if(Locale.equals("ar")) {
                            Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<ResetPasswordresponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                if(Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {

        }
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
        mListener = null;
    }



}
