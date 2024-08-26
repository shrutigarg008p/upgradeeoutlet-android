package com.eoutlet.Eoutlet.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.adpters.NewProfileAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.AddressList;
import com.eoutlet.Eoutlet.pojo.DeleteCustomerAccount;
import com.eoutlet.Eoutlet.pojo.GuestUser;
import com.eoutlet.Eoutlet.utility.CustomDialogClass;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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


public class NewProfileFragment extends Fragment {
    private ImageView searchImage;
    RecyclerView profilerecycler;
    NewProfileAdapter profileadapter;
    ParentDialogsListener parentDialogListener;
    private boolean _hasLoadedOnce = false;
    String userName;
    private View v;

    private int menuicon[] = {
            R.drawable.ic_upgrade_profile,
            R.drawable.ic_upgrade_notifications,
            R.drawable.ic_upgrade_myorders,
            R.drawable.ic_my_wallet,
            R.drawable.ic_track_order,
            R.drawable.ic_my_returns,
            R.drawable.ic_address_book,
            R.drawable.ic_saved_card,
            R.drawable.ic_upgrade_setting,
            R.drawable.ic_upgrade_helpandsupport,
            R.drawable.profile_eoutlet,

            /* R.drawable.ic_upgrade_notifications,

             R.drawable.ic_upgrade_notifications,*/
            R.drawable.ic_upgrade_follow_us,
            R.drawable.ic_term_condition,
            R.drawable.ic_delete_account_icon,
            R.drawable.ic_upgrade_logout};


    private List<String> profileString;
    private List<String> profilehintString;
    private Toolbar toolbar;
    private List<String> street = new ArrayList<>();
    private List<String> city = new ArrayList<>();
    private List<String> country = new ArrayList<>();
    private List<String> phone = new ArrayList<>();
    private List<String> address_id = new ArrayList<>();
    private List<String> countryId = new ArrayList<>();
    private List<String> name = new ArrayList<>();
    private List<String> addressfirstname = new ArrayList<>();
    private List<String> addresslastname = new ArrayList<>();
    private GoogleSignInClient mGoogleSignInClient;
    private Toolbar toolbar1;
    String Locale;
    private OnFragmentInteractionListener mListener;

    public NewProfileFragment() {
        // Required empty public constructor
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

    // TODO: Rename and change types and number of parameters
    public static NewProfileFragment newInstance(String param1, String param2) {
        NewProfileFragment fragment = new NewProfileFragment();
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

        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            Locale = "en";
            v = inflater.inflate(R.layout.fragment_new_profile_english, container, false);

            profileString = new ArrayList<>();
            if (MySharedPreferenceClass.getMyFirstNamePref(getApplicationContext()) != null) {
                profileString.add("Hello" + " " + MySharedPreferenceClass.getMyFirstNamePref(getApplicationContext()));
            } else {
                profileString.add("Log in / Register");
            }
            profileString.add("Notifications");
            profileString.add("My Orders");
            profileString.add("My Wallet");
            profileString.add("Track My Orders");
            profileString.add("My Returns");

            profileString.add("Address Book");
            profileString.add("Saved Cards");
            profileString.add("Settings");
            profileString.add("Help & Support");
            profileString.add("eOutlet");
            //profileString.add("Contact Us");
            // profileString.add("Shop By");
            profileString.add("Follow us on");
            profileString.add("Privacy Policy");
            profileString.add("Delete My Account");
            profileString.add("Log Out");
        } else {
            v = inflater.inflate(R.layout.fragment_new_profile, container, false);
            profileString = new ArrayList<>();

            if (MySharedPreferenceClass.getMyFirstNamePref(getApplicationContext()) != null) {
                profileString.add("مرحبا" + " " + MySharedPreferenceClass.getMyFirstNamePref(getApplicationContext()));
            } else {
                profileString.add("تسجيل الدخول/ تسجيل");
            }
            profileString.add("الاشعارات ");
            profileString.add("طلباتي");
            profileString.add("محفظتى");
            profileString.add("تعقب الطلبات");
            profileString.add("الاسترجاع");
            profileString.add("عناوين الشحن");
            profileString.add("البطاقات المحفوظة");
            profileString.add("الاعدادات");
            profileString.add("الدعم والمساعدة");//المساعدة والدعم
            profileString.add(" إي أوتلت");


            profileString.add("تابعونا على");
            profileString.add("سياسة الخصوصية");
            profileString.add("حذف الحساب");
            profileString.add("تسجيل الخروج");
        }

        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {

            profilehintString = new ArrayList<>();
            if (MySharedPreferenceClass.getMyFirstNamePref(getApplicationContext()) != null) {
                profilehintString.add(MySharedPreferenceClass.getEmail(getApplicationContext()));
            } else {
                profilehintString.add("For faster checkout");
            }
            profilehintString.add("Stay up to date on latest offers and more");
            profilehintString.add("Find all orders");
            profilehintString.add("Review and add amount");
            profilehintString.add("Track your Order");
            profilehintString.add("Return your Order");


            profilehintString.add("Add and Edit");
            profilehintString.add("Review and remove Cards");
            profilehintString.add("Language, Country and Notifications");
            profilehintString.add("Contact us, Shipping, Return, FAQ..");

            profilehintString.add("Learn more about us");
            // profileString.add("Shop By");
            profilehintString.add(" ");
            profilehintString.add(" ");
            profilehintString.add(" ");
            profilehintString.add(" ");
        } else {
            Locale = "ar";

            profilehintString = new ArrayList<>();
            if (MySharedPreferenceClass.getMyFirstNamePref(getApplicationContext()) != null) {
                profilehintString.add(MySharedPreferenceClass.getEmail(getApplicationContext()));
            } else {
                profilehintString.add("لخروج أسرع");
            }
            profilehintString.add("كن على اطلاع على أحدث العروض وأكثر");
            profilehintString.add("إبحث عن جميع الطلبات");
            profilehintString.add("مراجعة و إزضافة المبلغ");
            profilehintString.add("التعقب على طلبك");
            profilehintString.add("إعادة طلبك");

            profilehintString.add("اضافة وتعديل ");
            profilehintString.add("عرض وحذف البطاقات");
            profilehintString.add("اللغات, البلاد و الإشعارات");
            profilehintString.add("اتصل بنا ، الشحن ، الاسترجاع، الأسئلة الشائعة ..");

            profilehintString.add("تعلم المزيد عنا");
            // profileString.add("Shop By");
            profilehintString.add(" ");
            profilehintString.add(" ");
            profilehintString.add(" ");
            profilehintString.add(" ");
        }


        profilerecycler = v.findViewById(R.id.profileRecycler);


        initProfilerecycler();
        toolbar1 = v.findViewById(R.id.toolbar);

        searchImage = toolbar1.findViewById(R.id.serachbar);

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


        if (MySharedPreferenceClass.getMyUserId(getApplicationContext()) != " ") {


        } else {


        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        return v;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {

        }
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

    @SuppressLint("WrongConstant")
    public void initProfilerecycler() {

        // use a linear layout manager

        profilerecycler.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false));

        // specify an adapter (see also next example)
        profileadapter = new NewProfileAdapter(getContext(),
                profileString, profilehintString, menuicon,
                new ViewListener() {
                    @Override
                    public void onClick(int position, View view) {
                        int id = view.getId();


                        switch (id) {


                            case R.id.profileItemClick://button for message

                                if (position == 0) {

                                    if (MySharedPreferenceClass.getMyUserId(getApplicationContext()).equals(" ")) {
                                        Fragment prFrag = new LoginNewFragment();
                                        Bundle bundle = new Bundle();
                                        prFrag.setArguments(bundle);
                                        getFragmentManager()
                                                .beginTransaction().addToBackStack(null)
                                                .replace(R.id.profileContainer, prFrag)
                                                .commit();
                                    } else {

                                        Fragment prFrag = new NewUpdateInfo();

                                        getFragmentManager()
                                                .beginTransaction().addToBackStack(null)
                                                .replace(R.id.profileContainer, prFrag)
                                                .commit();

                                    }


                                } else if (position == 1) {

                                    Fragment newWalletFragment = new NotificationInboxFragment();
                                    getFragmentManager()
                                            .beginTransaction().addToBackStack(null)
                                            .replace(R.id.profileContainer, newWalletFragment)
                                            .commit();


                                } else if (position == 2) {
                                    Fragment orderListFragment = new NewOrderListFragment();

                                    getFragmentManager()
                                            .beginTransaction().addToBackStack(null)
                                            .replace(R.id.profileContainer, orderListFragment)
                                            .commit();


                                } else if (position == 3) {

                                    Fragment newWalletFragment = new NewWalletFragment();
                                    getFragmentManager()
                                            .beginTransaction().addToBackStack(null)
                                            .replace(R.id.profileContainer, newWalletFragment)
                                            .commit();

                                } else if (position == 4) {

                                    Fragment prFrag = new TrackOrderFragment();

                                    getFragmentManager()
                                            .beginTransaction().addToBackStack(null)
                                            .replace(R.id.profileContainer, prFrag)
                                            .commit();
                                } else if (position == 5) {

                                    Fragment prFrag = new UpgradeReturnItemListFragment();

                                    getFragmentManager()
                                            .beginTransaction().addToBackStack(null)
                                            .replace(R.id.profileContainer, prFrag)
                                            .commit();
                                } else if (position == 6) {
                                    Fragment prFrag = new NewAddressFragmentUpgrade();
                                    Bundle databund = new Bundle();
                                    databund.putString("flag", "mainprofile");
                                    prFrag.setArguments(databund);
                                    getFragmentManager()
                                            .beginTransaction().addToBackStack(null)
                                            .replace(R.id.profileContainer, prFrag)
                                            .commit();
                                } else if (position == 7) {
                                    Fragment prFrag = new SavedCardFragment();
                                    Bundle databund = new Bundle();
                                    databund.putString("flag", "mainprofile");
                                    prFrag.setArguments(databund);
                                    getFragmentManager()
                                            .beginTransaction().addToBackStack(null)
                                            .replace(R.id.profileContainer, prFrag)
                                            .commit();


                                } else if (position == 8) {
                                    Fragment prFrag = new SettingFragment();

                                    getFragmentManager()
                                            .beginTransaction().addToBackStack(null)
                                            .replace(R.id.profileContainer, prFrag)
                                            .commit();

                                } else if (position == 9) {
                                    Fragment prFrag = new HelpSupportFragment();

                                    getFragmentManager()
                                            .beginTransaction().addToBackStack(null)
                                            .replace(R.id.profileContainer, prFrag)
                                            .commit();


                                } else if (position == 10) {

                                    Fragment prFrag = new AboutUsFragment();

                                    getFragmentManager()
                                            .beginTransaction().addToBackStack(null)
                                            .replace(R.id.profileContainer, prFrag)
                                            .commit();


                                } else if (position == 11) {
                                    //follow us on tab

                                } else if (position == 12) {
                                    Fragment prFrag = new TermsandConditionsFragment();
                                    getFragmentManager()
                                            .beginTransaction().addToBackStack(null)
                                            .replace(R.id.profileContainer, prFrag)
                                            .commit();
                                } else if (position == 13) {
                                    openDeletedialog();

                                } else if (position == 14) {
                                    openlogoutdialog();
                                }

                                break;
                        }
                    }
                }


        );
        profilerecycler.setAdapter(profileadapter);
        profilerecycler.setNestedScrollingEnabled(false);


    }

    public void openDeletedialog() {


        DialogFragment prFrag = new CustomDialogClass(getActivity());
        Bundle bund = new Bundle();


        prFrag.setArguments(bund);


        prFrag.setTargetFragment(NewProfileFragment.this, 1003);
        prFrag.show(getFragmentManager(), "signup");


    }

    public void openlogoutdialog() {


        DialogFragment prFrag = new CustomDialogClass(getActivity());
        Bundle bund = new Bundle();


        prFrag.setArguments(bund);

        prFrag.setTargetFragment(NewProfileFragment.this, 1002);
        prFrag.show(getFragmentManager(), "signup");

        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
            /*final AlertDialog alertDialog;
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

            alertDialogBuilder.setMessage("هل أنت متأكد من تسجيل الخروج؟");


            alertDialogBuilder.setPositiveButton("نعم",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // getguesttoken();
                            getUpgradedguesttoken();

                            //doLogout();

                        }
                    });

            alertDialogBuilder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {
                    //alertDialog.dismiss();

                }
            });
            alertDialog = alertDialogBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();*/
        } else {
            /*final AlertDialog alertDialog;
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

            alertDialogBuilder.setMessage("Are you sure to log out?");


            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // getguesttoken();
                            getUpgradedguesttoken();

                            //doLogout();

                        }
                    });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {
                    //alertDialog.dismiss();

                }
            });
            alertDialog = alertDialogBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();*/


        }
    }


    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Toast.makeText(getApplicationContext(), "You have been Logout", Toast.LENGTH_SHORT).show();
                    }
                });
        LoginManager.getInstance().logOut();
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed() && _hasLoadedOnce) {
            if (MySharedPreferenceClass.getMyUserId(getApplicationContext()) != " ") {
                Fragment prFrag = new NewProfileFragment();
                Bundle databund = new Bundle();
                databund.putString("flag", "fromProfile");
                prFrag.setArguments(databund);
                //  ((SaveAddressFragment) prFrag).passData(getContext());
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.profileContainer, prFrag)
                        .commit();

            } else {

             /*   Fragment prFrag = new LoginNewFragment();
                Bundle databund = new Bundle();
                databund.putString("flag", "fromProfile");
                prFrag.setArguments(databund);
                //  ((SaveAddressFragment) prFrag).passData(getContext());
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.profileContainer, prFrag)
                        .commit();
*/

                //Toast.makeText(getContext(), "You must be logged in first", Toast.LENGTH_SHORT).show();
            }
            // System.out.println("Visible User Method Called.....");
        }
        _hasLoadedOnce = true;
    }

    public void clearFragmentBackStack() {
        FragmentManager fm = getFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }


    public void clearStack() {
        //Here we are clearing back stack fragment entries
        int backStackEntry = getFragmentManager().getBackStackEntryCount();
        if (backStackEntry > 0) {
            for (int i = 0; i < backStackEntry; i++) {
                getFragmentManager().popBackStackImmediate();
            }
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    public void getUpgradedguesttoken() {


        parentDialogListener.showProgressDialog();

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
                        MySharedPreferenceClass.setCartId(getApplicationContext(), response.body().quoteId);
                        parentDialogListener.hideProgressDialog();
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


                        if (MySharedPreferenceClass.getLoginType(getApplicationContext()).equals("facebook")) {
                            LoginManager.getInstance().logOut();

                            MySharedPreferenceClass.setLoginType(getApplicationContext(), " ");

                        } else if (MySharedPreferenceClass.getLoginType(getApplicationContext()).equals("google")) {
                            signOut();

                            MySharedPreferenceClass.setLoginType(getApplicationContext(), " ");

                        } else if (MySharedPreferenceClass.getLoginType(getApplicationContext()).equals("twitter")) {
                            signOut();

                            MySharedPreferenceClass.setLoginType(getApplicationContext(), " ");

                        }

                        Fragment prFrag = new NewProfileFragment();
                        Bundle databund = new Bundle();
                        databund.putString("flag", "fromlogout");
                        prFrag.setArguments(databund);

                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.profileContainer, prFrag)
                                .commit();

                    } else {
                        parentDialogListener.hideProgressDialog();


                    }

                } else {
                    //  hideProgressDialog();


                }
            }


            @Override
            public void onFailure(Call<GuestUser> call, Throwable t) {
                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
                Log.e(TAG, t.toString());

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1002) {


            getUpgradedguesttoken();
        }
        if(requestCode == 1003){

      deletecustomerAccount();

        }

    }

    public void deletecustomerAccount()
    {

        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
    /*    {    "param":{   "customer_id":"180442"    }}*/
        Map<Object, Object> map1 = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        map2.put("customer_id",MySharedPreferenceClass.getMyUserId(getApplicationContext()));
        map1.put("param",map2);
        Call<DeleteCustomerAccount> call = apiService.deleteUserAccount("Bearer "+
                MySharedPreferenceClass.getToken(getApplicationContext()),map1
                );
        call.enqueue(new Callback<DeleteCustomerAccount>() {
            @Override
            public void onResponse(Call<DeleteCustomerAccount> call, Response<DeleteCustomerAccount> response) {

                if (response.body() != null && response.body().message != null) {
                    if (response.body().message.equals("success")) {
                        parentDialogListener.hideProgressDialog();
                        Toast.makeText(getApplicationContext(),response.body().data,Toast.LENGTH_SHORT).show();

                        getUpgradedguesttoken();

                    }

                }
            }


            @Override
            public void onFailure(Call<DeleteCustomerAccount> call, Throwable t) {
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
