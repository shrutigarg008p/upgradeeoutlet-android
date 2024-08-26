package com.eoutlet.Eoutlet.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.ContactMessage;
import com.eoutlet.Eoutlet.utility.Util;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nispok.snackbar.SnackbarManager.dismiss;


public class ContactUsFragment extends Fragment {
    private LinearLayout linearwhatsapp, linearcall;
    public ParentDialogsListener parentDialogListener;

    private View view;
    boolean isAllConditionFulfilled;
    TextView tvsignUp;
    EditText edt_name, edt_email, edt_mobile, contactissue;
    private ImageView searchImage, backarrow;
    private Toolbar toolbar1;
    String Locale;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                Locale = "en";
                view = inflater.inflate(R.layout.fragment_contact_us_english, container, false);
            } else {
                Locale = "ar";
                view = inflater.inflate(R.layout.fragment_contact_us, container, false);
            }
        } else {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        }
        linearcall = view.findViewById(R.id.linearCall);
        linearwhatsapp = view.findViewById(R.id.linearWhatsapp);
        tvsignUp = view.findViewById(R.id.signUp);
        edt_name = view.findViewById(R.id.edtname);
        edt_email = view.findViewById(R.id.edt_email);
        edt_mobile = view.findViewById(R.id.mobile);
        contactissue = view.findViewById(R.id.contactissue);
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
                getFragmentManager().popBackStack();
            }
        });
        registerListener();
        return view;
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

    private void registerListener() {


        linearwhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text;
                if(Locale.equalsIgnoreCase("ar")) {
                 text = "مرحبا فريق اي اوتلت ";// Replace with your message.
             }
             else{
                text = "Hello eoutlet team ";// Replace with your message.
             }

                String toNumber = "+966532631188"; // Replace with mobile phone number without +Sign or leading zeros, but with country code
                //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.
                PackageManager pm = getActivity().getPackageManager();
                try {

                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);

                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text=" + text));
                    startActivity(intent);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getActivity(), Locale == "ar" ? "آسف ، لم يتم تثبيت واتس اب." :
                            "Sorry, Whatsapp is not installed.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }

        });

        linearcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, 101);
                    return;
                }
                makeCall();

            }
        });

        tvsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                isAllConditionFulfilled = Util.checkTextViewValidation(edt_name, Locale == "ar" ? "اكتب اسمك الاول" : "Please enter your name.") &&
                        Util.checkTextViewValidation(edt_email, Locale == "ar" ? "الرجاء إدخال بريد إلكتروني صحيح" : "Please enter valid email.")
                        && Util.isEmailValidContact(edt_email, Locale == "ar" ? "الرجاء إدخال بريد إلكتروني صحيح" : "Please enter valid email.") &&
                        Util.checkTextViewValidation(edt_mobile, Locale == "ar" ? "الرجاء ادخال رقم الهاتف" : "Please enter mobile number");
                if (!isAllConditionFulfilled) {
                    if (Util.view_final != null) {

                        Util.view_final.requestFocus();
                    }
                    return;
                }
                getContactdETAILS();


            }
        });
    }

    @SuppressLint("MissingPermission")
    private void makeCall() {
        String uri = "tel:" + "+966920017422";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Important Permission for make call..");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                        ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, 101);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        }
    }

    private void getContactdETAILS() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("name", edt_name.getText().toString().trim());
        map1.put("email", edt_email.getText().toString().trim());
        map1.put("telephone", edt_mobile.getText().toString().trim());
        map1.put("comment", contactissue.getText().toString().trim());
        mainparam.put("param", map1);
        Call<ContactMessage> call = apiService.upgradeContactUs(mainparam);
        call.enqueue(new Callback<ContactMessage>() {
            @Override
            public void onResponse(Call<ContactMessage> call, Response<ContactMessage> response) {
                parentDialogListener.hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().getMsg().equalsIgnoreCase("success")) {
                        Toast.makeText(getContext(), response.body().getData(), Toast.LENGTH_LONG).show();
                        edt_name.setText("");
                        edt_email.setText("");
                        edt_mobile.setText("");
                        contactissue.setText("");
                    } else {
                        Log.e("contact Us ", "not successful");
                        Toast.makeText(getContext(), response.body().getData(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ContactMessage> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("contact Us ", t.toString());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });

    }
    private boolean isAppInstalled(Context ctx, String packageName) {
        PackageManager pm = ctx.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}
