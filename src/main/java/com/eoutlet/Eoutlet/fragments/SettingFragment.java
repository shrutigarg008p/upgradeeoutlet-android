package com.eoutlet.Eoutlet.fragments;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.adpters.CurrencyListAdapter;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.intrface.ViewlistenerCurrency;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.CurrencysetResponse;
import com.eoutlet.Eoutlet.pojo.ExchangeRate;
import com.eoutlet.Eoutlet.pojo.GetCountryCode;
import com.eoutlet.Eoutlet.pojo.GetCurrencyDetail;
import com.eoutlet.Eoutlet.pojo.countryCodeDetail;
import com.eoutlet.Eoutlet.utility.Constants;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.moe.pushlibrary.MoEHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.FacebookSdk.getApplicationSignature;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<countryCodeDetail> currencycountrynames = new ArrayList();
    View view;
    List<String> availiblecuurrency;
    private List<ExchangeRate> exchangecountrylist = new ArrayList<>();
    private RecyclerView currencyrecycler;
    private int countryselctposition;
    private RelativeLayout changcountry, changelanguage;
    private CurrencyListAdapter currencyadpter;
    private ImageView selctCountryFlag, selectLanguageFlag;
    ParentDialogsListener parentDialogListener;
    private ImageView searchImage, backarrow;
    private Toolbar toolbar1;
    private Switch notificationswitch;
    private TextView selctedcountry;
    int flag;


    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            view = inflater.inflate(R.layout.fragment_setting, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_setting_arabic, container, false);

        }


        changcountry = view.findViewById(R.id.changecountry);
        changelanguage = view.findViewById(R.id.changeLanguage);
        selectLanguageFlag = view.findViewById(R.id.changelanguageflag);
        selctCountryFlag = view.findViewById(R.id.changecountryflag);
        selctedcountry = view.findViewById(R.id.userselectedcountry);

        toolbar1 = view.findViewById(R.id.toolbar);
        searchImage = toolbar1.findViewById(R.id.serachbar);
        backarrow = toolbar1.findViewById((R.id.backarrow));

        notificationswitch = view.findViewById(R.id.notificationSwitch);

        if (areNotificationsEnabled()) {
            notificationswitch.setChecked(true);
            MoEHelper.getInstance(getApplicationContext()).setUserAttribute("optin_status", true);
        } else {
            notificationswitch.setChecked(false);
            MoEHelper.getInstance(getApplicationContext()).setUserAttribute("optin_status", false);
        }


        notificationswitch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Intent settingsIntent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra(Settings.EXTRA_APP_PACKAGE,getContext().getPackageName())
                        .putExtra(Settings.EXTRA_CHANNEL_ID, "MY_CHANNEL_ID");
                startActivity(settingsIntent);
                return true;
            }
        });



        if (!MySharedPreferenceClass.getSelectedCountryName(getApplicationContext()).equals(" ")) {
            selctedcountry.setText(MySharedPreferenceClass.getSelectedCountryName(getApplicationContext()));
        }


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
        changelanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                initLanguageDialog();
            }
        });

        changcountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniatializechangeCountryDialogue();
            }
        });

        getcountryDetail1();

        return view;
    }

    private void initLanguageDialog() {

        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
        View sheetView;
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            sheetView = this.getLayoutInflater().inflate(R.layout.language_selction_from_profile, null);
        } else {
            sheetView = this.getLayoutInflater().inflate(R.layout.language_selection_arabic, null);
        }
        mBottomSheetDialog.setContentView(sheetView);

        ImageView english = sheetView.findViewById(R.id.english);
        ImageView arabic = sheetView.findViewById(R.id.arabic);
        TextView changelang = sheetView.findViewById(R.id.continuetohom);
        LinearLayout continuelangchange = sheetView.findViewById(R.id.continuelanguagechange);


        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            flag = 1;
            english.setImageDrawable(getContext().getDrawable(R.drawable.selected));
            arabic.setImageDrawable(getContext().getDrawable(R.drawable.unselected));
        } else {
            arabic.setImageDrawable(getContext().getDrawable(R.drawable.selected));
            english.setImageDrawable(getContext().getDrawable(R.drawable.unselected));
            flag = 2;
        }
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                english.setImageDrawable(getContext().getDrawable(R.drawable.selected));
                arabic.setImageDrawable(getContext().getDrawable(R.drawable.unselected));


            }
        });

        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 2;
                arabic.setImageDrawable(getContext().getDrawable(R.drawable.selected));
                english.setImageDrawable(getContext().getDrawable(R.drawable.unselected));


            }
        });

        changelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 1) {
                    Toast.makeText(getApplicationContext(), "Language set to English", Toast.LENGTH_SHORT).show();
                    MySharedPreferenceClass.setChoosenLanguage(getActivity(), "en");
                    Locale.setDefault(Locale.forLanguageTag("en"));
                    Intent i = new Intent(getContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {

                    Toast.makeText(getApplicationContext(), "ضبط اللغة على اللغة الإنجليزية", Toast.LENGTH_SHORT).show();
                    MySharedPreferenceClass.setChoosenLanguage(getActivity(), "ar");
                    Locale.setDefault(Locale.forLanguageTag("ar"));
                    Intent i = new Intent(getContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }

            }
        });
        mBottomSheetDialog.show();
    }

    private void iniatializechangeCountryDialogue() {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
        View sheetView;
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            sheetView = this.getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
        } else {
            sheetView = this.getLayoutInflater().inflate(R.layout.change_country_arabic, null);
        }


        mBottomSheetDialog.setContentView(sheetView);


        RelativeLayout done = sheetView.findViewById(R.id.done);
        currencyrecycler = sheetView.findViewById(R.id.currencyListRecycler);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.cancel();
                for (int i = 0; i < exchangecountrylist.size(); i++) {
                    Log.e("Exchange Country List",exchangecountrylist.get(i).currencyTo);
                    Log.e("Currency Country Names",currencycountrynames.get(i).currency);


                    if (currencycountrynames.get(countryselctposition).currency.
                            equalsIgnoreCase(exchangecountrylist.get(i).currencyTo)) {

                        sendcountryselction(currencycountrynames.get(countryselctposition).code,currencycountrynames.get(countryselctposition).name, exchangecountrylist.get(i).currencyTo, exchangecountrylist.get(i).rate);

                        break;

                    }

                }


                //sendcountryselction(exchangecountrylist.get(countryselctposition).currencyTo, exchangecountrylist.get(countryselctposition).rate);

            }
        });


        getCurrency();
        mBottomSheetDialog.show();

        MySharedPreferenceClass.setisAppOpenFirstTime(getApplicationContext(), true);
    }

    public void sendcountryselction(String countrycode, String countryname, String currencycode, Float rate) {
        parentDialogListener.showProgressDialog();
        /*https://upgrade.eoutlet.com/rest/en/V1/api/setquotecurrency
        *
        *
        * "{
    ""param"":{
        ""quote_id"":2016,
        ""currency_code"":""USD""
    }
}"*/


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
        Map<Object, Object> mainparam = new HashMap<>();

        Map<String, String> map1 = new HashMap<>();

        if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {

            map1.put("quote_id", MySharedPreferenceClass.getQuoteId(getContext()));
            map1.put("currency_code", currencycode);

            mainparam.put("param", map1);

        } else {
            map1.put("quote_id", String.valueOf(MySharedPreferenceClass.getCartId(getContext())));
            map1.put("currency_code", currencycode);

            mainparam.put("param", map1);

        }


        HashMap<String, String> headers = new HashMap<>();

        headers.put("Cookie", MySharedPreferenceClass.getCookies(getApplicationContext()));

        Call<CurrencysetResponse> call = apiService.postUserCurrencySelection(headers, mainparam);
        call.enqueue(new Callback<CurrencysetResponse>() {
            @Override
            public void onResponse(Call<CurrencysetResponse> call, Response<CurrencysetResponse> response) {
                parentDialogListener.hideProgressDialog();
                if (response.body() != null && response.isSuccessful()) {
                   // Toast.makeText(getApplicationContext(), countryname, Toast.LENGTH_SHORT).show();

                    Locale.setDefault(Locale.forLanguageTag("en"));

                    if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
                        selctedcountry.setText("Current Selection-" + countryname);
                        MySharedPreferenceClass.setSelectedCountryName(getApplicationContext(), "Current Selection-" + countryname);
                    } else {
                        selctedcountry.setText("الإختيار الحالي-" + countryname);
                        MySharedPreferenceClass.setSelectedCountryName(getApplicationContext(), "الإختيار الحالي-" + countryname);

                    }


                    MySharedPreferenceClass.setSelectedCountryCode(getContext(),countrycode);
                    MySharedPreferenceClass.setSelectedCurrencyName(getApplicationContext(), currencycode);
                    MySharedPreferenceClass.setSelectedCurrencyRate(getApplicationContext(), rate);
                    Intent i = new Intent(getContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);



                } else {

                    initCurrenCyrecycler();
                }

            }


            @Override
            public void onFailure(Call<CurrencysetResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void initCurrenCyrecycler() {
        List<countryCodeDetail> currencycountrynamesafterfilter = new ArrayList<>();
       // availiblecuurrency.add("AED");
        for(int i =0;i<currencycountrynames.size();i++){
            if(availiblecuurrency.contains(currencycountrynames.get(i).currency)){
                currencycountrynamesafterfilter.add(currencycountrynames.get(i));


            }


        }

        currencyadpter = new CurrencyListAdapter(getContext(), currencycountrynamesafterfilter, exchangecountrylist,availiblecuurrency, new ViewlistenerCurrency() {
            @Override
            public void onClick(int position, View view, String currencyname) {
                int id = view.getId();
                countryselctposition = position;

                switch (id) {
                    case R.id.currencyselection://button for message

                        //sendcountryselction(exchangecountrylist.get(countryselctposition).currencyTo,exchangecountrylist.get(countryselctposition).rate);
                        break;
                }
            }
        }


        );
        GridLayoutManager lm;
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            lm = new GridLayoutManager(view.getContext(), 3);
        }
        // LinearLayoutManager categoryGridLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        else {
            lm = new GridLayoutManager(view.getContext(), 3) {
                @Override
                protected boolean isLayoutRTL() {
                    return true;
                }
            };
        }
        currencyrecycler.setLayoutManager(lm);
        currencyrecycler.setItemAnimator(new DefaultItemAnimator());
        currencyrecycler.setAdapter(currencyadpter);
        currencyadpter.notifyDataSetChanged();


    }

    public void getCurrency() {
        //  showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Call<GetCurrencyDetail> call = apiService.getCurrency();
        call.enqueue(new Callback<GetCurrencyDetail>() {
            @Override
            public void onResponse(Call<GetCurrencyDetail> call, Response<GetCurrencyDetail> response) {

                if (response.body() != null && response.isSuccessful()) {

                    exchangecountrylist = response.body().exchangeRates;
                    availiblecuurrency = response.body().availableCurrencyCodes;


                    if (MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()) == null) {
                        MySharedPreferenceClass.setSelectedCurrencyRate(getApplicationContext(), 1.0f);
                        MySharedPreferenceClass.setSelectedCurrencyName(getApplicationContext(), response.body().baseCurrencyCode);
                    }


                    //initCurrenCyrecycler();
                    getcountryDetail2();


                }

            }


            @Override
            public void onFailure(Call<GetCurrencyDetail> call, Throwable t) {
                if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
                Log.e(TAG, t.toString());

            }
        });


    }

    public void getcountryDetail2() {
        //parentDialogListener.showProgressDialog();


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
                    //parentDialogListener.hideProgressDialog();
                    for (int i = 0; i < response.body().data.size(); i++) {

                        currencycountrynames = response.body().data;
                        if (response.body().data.get(i).currency.equalsIgnoreCase(MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()))) {

                            try {
                                Glide.with(selctCountryFlag)
                                        .load(Constants.upgradedmediaSourceURL + response.body().data.get(i).flag).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)


                                        .into(selctCountryFlag);
                            } catch (Exception e) {
                            }
                        }

                    }
                    initCurrenCyrecycler();


                }
            }

            @Override
            public void onFailure(Call<GetCountryCode> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }}
        });


    }

    public void getcountryDetail1() {
        parentDialogListener.showProgressDialog();


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


                if (response.body() != null && response.isSuccessful()) {
                    currencycountrynames = response.body().data;
                    parentDialogListener.hideProgressDialog();

                    for (int i = 0; i < response.body().data.size(); i++) {
                        /*if(response.body().data.size()-1==i){


                            try {
                                Glide.with(selctCountryFlag)
                                        .load(Constants.upgradedmediaSourceURL + "pub/media/mobileapp/usa.png").skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)


                                        .into(selctCountryFlag);
                            } catch (Exception e) {
                                // }

                            }



                        }
                        else{*/


                        response.body().data.get(response.body().data.size() - 1).currency = "USD";

                        if (response.body().data.get(i).currency.equalsIgnoreCase(MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()))) {
                            Log.e("currency----->>", response.body().data.get(i).currency);
                            Log.e("shareprefernce----->>", MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()));

                            if (MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()).equalsIgnoreCase("USD")) {
                                try {
                                    Glide.with(selctCountryFlag)
                                            .load(Constants.upgradedmediaSourceURL + "pub/media/mobileapp/usa.png").skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)


                                            .into(selctCountryFlag);
                                } catch (Exception e) {
                                }

                            } else {
                                try {
                                    Glide.with(selctCountryFlag)
                                            .load(Constants.upgradedmediaSourceURL + response.body().data.get(i).flag).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)


                                            .into(selctCountryFlag);
                                } catch (Exception e) {
                                }
                            }


                            //  }

                        }


                        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals(("ar"))) {
                            if (response.body().data.get(i).currency.equalsIgnoreCase("SAR")) {

                                try {
                                    Glide.with(selectLanguageFlag)
                                            .load(Constants.upgradedmediaSourceURL + response.body().data.get(i).flag).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)


                                            .into(selectLanguageFlag);
                                } catch (Exception e) {
                                }

                            }

                        } else {
                            //Toast.makeText(getContext(),"from others",Toast.LENGTH_SHORT).show();
                           /* if(response.body().data.get(i).currency.equalsIgnoreCase("USD"))
                            {*/

                            try {
                                Glide.with(selectLanguageFlag)
                                        .load(Constants.upgradedmediaSourceURL + "pub/media/mobileapp/usa.png").skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)


                                        .into(selectLanguageFlag);
                            } catch (Exception e) {
                                // }

                            }


                        }

                    }


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

    public void clearFragmentBackStack() {
        FragmentManager fm = getFragmentManager();
        for (int i = 0; i <= fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }


    }

    public boolean areNotificationsEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            if (!manager.areNotificationsEnabled()) {
                return false;
            }
            List<NotificationChannel> channels = manager.getNotificationChannels();
            for (NotificationChannel channel : channels) {
                if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                    return false;
                }
            }
            return true;
        } else {
            return NotificationManagerCompat.from(getApplicationContext()).areNotificationsEnabled();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (notificationswitch != null) {
            if (areNotificationsEnabled()) {
                MoEHelper.getInstance(getApplicationContext()).setUserAttribute("optin_status", true);
                notificationswitch.setChecked(true);
            } else {
                MoEHelper.getInstance(getApplicationContext()).setUserAttribute("optin_status", false);
                notificationswitch.setChecked(false);
            }
        }
    }
}