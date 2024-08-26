package com.eoutlet.Eoutlet.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.MediaDrm;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.adpters.CurrencyListAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.intrface.UpdateBedgeCount;
import com.eoutlet.Eoutlet.listener.CartListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.AboutUsResponse;
import com.eoutlet.Eoutlet.pojo.CurrencysetResponse;
import com.eoutlet.Eoutlet.pojo.ExchangeRate;
import com.eoutlet.Eoutlet.pojo.GetCountryCode;
import com.eoutlet.Eoutlet.pojo.GetCurrencyDetail;
import com.eoutlet.Eoutlet.pojo.LoginResponse;
import com.eoutlet.Eoutlet.pojo.OtpResponse;
import com.eoutlet.Eoutlet.pojo.SignUpResponse;
import com.eoutlet.Eoutlet.pojo.UpgradeGetAddressResponse;
import com.eoutlet.Eoutlet.pojo.UpgradeLoginResponse;
import com.eoutlet.Eoutlet.pojo.UpgradeWishListResponse;
import com.eoutlet.Eoutlet.pojo.UpgradedCartItems;
import com.eoutlet.Eoutlet.pojo.UpgradedGetAddressList;
import com.eoutlet.Eoutlet.pojo.UpgradedItem;
import com.eoutlet.Eoutlet.pojo.ViewCartData;
import com.eoutlet.Eoutlet.pojo.countryCodeDetail;
import com.eoutlet.Eoutlet.utility.Constants;
import com.eoutlet.Eoutlet.utility.Util;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.moe.pushlibrary.MoEHelper;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.AccountService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.eoutlet.Eoutlet.fragments.HomeFragmentNewDesign.wishListBadgeCount;
import static com.facebook.FacebookSdk.getApplicationContext;

//import com.crashlytics.android.Crashlytics;


public class LoginNewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match


    Context mContext;
    private FirebaseAuth mAuth;
    static TwitterLoginButton mTwitterBtn;
    private LinearLayout signUp, getotpui;
    private Spinner countryspinner;
    private ExecuteFragment execute;
    private String userName, passWord;
    private TextView login, tabsiggnup, forgotpassword;
    private EditText edtpassword, edtmobile;
    public static CallbackManager callbackManager;
    private View passwordivider;
    private AppCompatRadioButton btnmobile, btnemail, getotp;
    private boolean isAllConditionFulfilled;
    private List<UpgradedItem> upgradedcartData;
    private Toolbar toolbar;
    int totalcount;
    private FirebaseAuth fbAuth;
    private FirebaseAuth.AuthStateListener authListener;
    UpdateBedgeCount updatefix;
    private int mobilevalidationlength;
    private int selctedposition = 0;
    LinearLayout mobileverify, emailview;
    private List<ViewCartData> cartData;

    private String rgistereduseremail = " ";
    private String registereduserpassword = " ";
    private List<String> name = new ArrayList<>();

    private List<String> street = new ArrayList<>();
    private List<String> city = new ArrayList<>();
    private List<String> country = new ArrayList<>();
    private List<String> phone = new ArrayList<>();
    private List<String> address_id = new ArrayList<>();
    private List<String> countryId = new ArrayList<>();
    private List<String> addressfirstname = new ArrayList<>();
    private List<String> addresslastname = new ArrayList<>();
    private String value;
    private LoginButton fbloginbutton;
    private String Locale = " ";
    private static final String EMAIL = "email";
    private List<String> countryname;
    private List<String> countryCode;
    private List<String> currencyCode;
    private List<String> celcode;
    private List<String> placeholder;
    private String countryName, otp;
    private List<countryCodeDetail> currencycountrynames = new ArrayList();

    private CartListener cartreference;
    String selectedmethod = "mobilewithotp";
    private TextView HelpSupport;
    public ParentDialogsListener parentDialogListener;
    public static final String PHONE_STATE_PERMISSION =
            Manifest.permission.READ_PHONE_STATE;
    private LinearLayout loginLayout;
    private EditText userPassword, edtusername;
    int RC_SIGN_IN = 100;
    private Button fb, applesigin, googlelogin;
    SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private View v;
    TwitterAuthConfig mTwitterAuthConfig;
    private String twitterId, twitteremail, twitterfirstname, twitterlastname, twitterusername;

    private String googlefnama, googlelname, googlefullname, googleemail, googleid;

    private List<ExchangeRate> exchangecountrylist = new ArrayList<>();
    private RecyclerView currencyrecycler;
    private int countryselctposition;
    private ImageView passwordshowbtn;
    private boolean isPasswordHide = false;
    private FrameLayout passwordfullframe;

    private CurrencyListAdapter currencyadpter;

    public LoginNewFragment() {
        // Required empty public constructor
    }

    public void passData(Context context) {
        mContext = context;
        execute = (MainActivity) mContext;
    }

    // TODO: Rename and change types and number of parameters
    public static LoginNewFragment newInstance(String param1, String param2) {
        LoginNewFragment fragment = new LoginNewFragment();
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
        //View v = inflater.inflate(R.layout.fragment_login_new, container, false);


        fbAuth = FirebaseAuth.getInstance();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(
                    @NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {


                } else {


                }
            }
        };


        mTwitterAuthConfig = new TwitterAuthConfig(getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret));
        TwitterConfig twitterConfig = new TwitterConfig.Builder(getContext())
                .twitterAuthConfig(mTwitterAuthConfig).debug(true)
                .build();
        Twitter.initialize(twitterConfig);


        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
            Locale = "ar";
            v = inflater.inflate(R.layout.fragment_login_new2, container, false);
        } else if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
            Locale = "en";
            v = inflater.inflate(R.layout.fragment_login_new2_english, container, false);

        } else {
            Locale = "ar";
            v = inflater.inflate(R.layout.fragment_login_new2, container, false);
        }


        signInButton = v.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        /*signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                signIn();
            }
        });*/
        Button signout = v.findViewById(R.id.googlesignout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();

            }
        });
        System.out.println("LoginNewFragment");

        /*TextView crash = v.findViewById(R.id.crash);

        crash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new RuntimeException("Test Crash");
            }
        });
*/


        intiViews(v);


        getcountryDetail();


        Bundle bundle = getArguments();


        if (bundle.containsKey("flag")) {
            try {

                value = getArguments().getString("flag");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (bundle != null) {
            if (bundle.containsKey("email") && bundle.containsKey("password")) {

                rgistereduseremail = getArguments().getString("email");
                registereduserpassword = getArguments().getString("password");
                //loginProceed(rgistereduseremail, registereduserpassword, "0");

                if (getArguments().getString("flag") != null) {
                    if (getArguments().getString("flag").equals("fromfacebook")) {

                        loginUpgradedProceedwithfacebook(getArguments().getString("email"), "", "by_facebook");


                    } else if (getArguments().getString("flag").equals("fromgoogle")) {
                        loginUpgradedProceedwithgoogle(getArguments().getString("email"), "", "google", getArguments().getString("email"));

                    } else if (getArguments().getString("flag").equals("fromtwitter")) {
                        loginUpgradedProceedwithtwitter(getArguments().getString("email"), " ", "twitter", getArguments().getString("email"));


                    }
                } else {

                    loginUpgradedProceed(rgistereduseremail, registereduserpassword, "0");
                }
            }


        }


        return v;
    }

    public LoginNewFragment(CartFragment cl) {

        cartreference = cl;


    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void intiViews(final View v) {
        signUp = v.findViewById(R.id.signup);
        edtusername = v.findViewById(R.id.userName);
        edtpassword = v.findViewById(R.id.password);
        login = v.findViewById(R.id.loginClick);
        forgotpassword = v.findViewById(R.id.forgotPassword);
        tabsiggnup = v.findViewById(R.id.btn_signup);
        HelpSupport = v.findViewById(R.id.HelpSupport);
        mobileverify = v.findViewById(R.id.veryfybymobile);
        emailview = v.findViewById(R.id.emailView);

        btnmobile = v.findViewById(R.id.btnmobile);
        btnemail = v.findViewById(R.id.btnemail);

        getotpui = v.findViewById(R.id.getotpui);
        getotp = v.findViewById(R.id.getotp);

        countryspinner = v.findViewById(R.id.countrycodeSpinner);
        edtmobile = v.findViewById(R.id.edtmobilrnumber);
        passwordivider = v.findViewById(R.id.passworddivider);

        updatefix = (MainActivity) getActivity();
        View view = getActivity().findViewById(R.id.common_toolbar);
        loginLayout = v.findViewById(R.id.loginLayout);
        userPassword = v.findViewById(R.id.userPassword);
        mTwitterBtn = v.findViewById(R.id.twitter_login_button);
        fb = (Button) v.findViewById(R.id.fb);
        googlelogin = (Button) v.findViewById(R.id.googlelogin);
        applesigin = (Button) v.findViewById(R.id.applesignin);
        passwordshowbtn = (ImageView) v.findViewById(R.id.passwordshowimg);
        passwordfullframe = (FrameLayout) v.findViewById(R.id.passwordfullframe);

        mTwitterBtn.setTextSize(14);
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
            mTwitterBtn.setText("المتابعة مع تويتر ");
            mTwitterBtn.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "HelveticaLight.ttf"));
            final Resources res = getResources();
            mTwitterBtn.setPadding((int) getResources().getDimension(R.dimen._70sdp), 0, (int) getResources().getDimension(R.dimen._70sdp), 0);
            mTwitterBtn.setCompoundDrawablesWithIntrinsicBounds(null
                    , null, res.getDrawable(R.drawable.tw__ic_logo_default), null);

        } else {
            mTwitterBtn.setText("Continue with Twitter");
            mTwitterBtn.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Mulish_Regular.ttf"));
            mTwitterBtn.setPadding((int) getResources().getDimension(R.dimen._50sdp), 0, (int) getResources().getDimension(R.dimen._50sdp), 0);
        }

        applesigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "from inside", Toast.LENGTH_SHORT).show();


                OAuthProvider.Builder provider = OAuthProvider.newBuilder("apple.com");

             /*   List<String> scopes =
                        new ArrayList<String>() {
                            {
                                add("email");
                                add("name");
                            }
                        };
                provider.setScopes(scopes);
*/


                mAuth = FirebaseAuth.getInstance();
                Task<AuthResult> pending = mAuth.getPendingAuthResult();
                if (pending != null) {
                    pending.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Log.d(TAG, "checkPending:onSuccess:" + authResult);
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            // Get the user profile with authResult.getUser() and
                            // authResult.getAdditionalUserInfo(), and the ID
                            // token from Apple with authResult.getCredential().
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "checkPending:onFailure", e);
                            Toast.makeText(getContext(), "checkPending:onFailure", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.d(TAG, "pending: null");
                    mAuth.startActivityForSignInWithProvider(getActivity(), provider.build())
                            .addOnSuccessListener(
                                    new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            // Sign-in successful!
                                            Log.d(TAG, "activitySignIn:onSuccess:" + authResult.getUser());
                                            FirebaseUser user = authResult.getUser();
                                            // ...
                                        }
                                    })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "activitySignIn:onFailure", e);
                                        }
                                    });

                }


            }
        });


        callbackManager = CallbackManager.Factory.create();

        fbloginbutton = (LoginButton) v.findViewById(R.id.fbsignin);
        fbloginbutton.setLoginBehavior(LoginBehavior.WEB_ONLY);
        //fbloginbutton .setReadPermissions(Arrays.asList(EMAIL));

        getotp.setSelected(true);
        getotp.setChecked(true);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbloginbutton.performClick();
            }
        });

        googlelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPassword.setVisibility(View.GONE);
                passwordfullframe.setVisibility(View.GONE);
                edtusername.setVisibility(View.GONE);

                edtpassword.setVisibility(View.GONE);
                passwordivider.setVisibility(View.GONE);
                mobileverify.setVisibility(View.VISIBLE);
                emailview.setVisibility(View.GONE);

                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                    login.setText("الدخول برسالة");
                } else {

                    login.setText("Login With Otp");
                }

                selectedmethod = "mobilewithotp";


            }
        });
        fbloginbutton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("TAG", "AccessToken" + loginResult.getAccessToken());
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                Log.e("TAG", "AccessToken" + loginResult.getAccessToken());
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                setFacebookData(loginResult);
                // App code
            }

            @Override
            public void onCancel() {
                Log.e("TAG", "AccessToken" + "Cancel");
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("TAG", "AccessToken" + "Error");
                // App code
            }
        });

        passwordshowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isPasswordHide) {
                    //Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_SHORT).show();
                    isPasswordHide = true;
                    userPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //for show the password
                    passwordshowbtn.setImageDrawable(getResources().getDrawable(R.drawable.hidepass));
                } else {
                    userPassword.setTransformationMethod(PasswordTransformationMethod.getInstance()); // for hide the password
                    isPasswordHide = false;
                    passwordshowbtn.setImageDrawable(getResources().getDrawable(R.drawable.showpass));
                }

            }
        });

        tabsiggnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment prFrag = new Signup();
                Bundle bund = new Bundle();
                bund.putString("flag", value);
                prFrag.setArguments(bund);
                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(LoginNewFragment.this.getId(), prFrag)
                        .commit();


            }
        });
        btnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                    btnmobile.setText(R.string.mobilewithoutunderline);
                }

                selectedmethod = "email";
                emailview.setVisibility(View.GONE);
                mobileverify.setVisibility(View.GONE);
                getotpui.setVisibility(View.GONE);
                passwordivider.setVisibility(View.GONE);
                edtpassword.setVisibility(View.GONE);
                loginLayout.setVisibility(View.VISIBLE);
                userPassword.setVisibility(View.VISIBLE);
                passwordfullframe.setVisibility(View.VISIBLE);
                edtusername.setVisibility(View.VISIBLE);

                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                    login.setText("الدخول بالايميل");
                } else if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {

                    login.setText("Login With Email");


                }


            }
        });


        btnmobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailview.setVisibility(View.GONE);
                mobileverify.setVisibility(View.VISIBLE);
              /*  userPassword.setVisibility(View.VISIBLE);
                getotpui.setVisibility(View.VISIBLE);*/
                /*passwordivider.setVisibility(View.VISIBLE);*/
                userPassword.setVisibility(View.VISIBLE);
                passwordfullframe.setVisibility(View.VISIBLE);
                edtusername.setVisibility(View.GONE);
                selectedmethod = "mobile";
                if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                    login.setText("الدخول بالرقم");
                } else if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {

                    login.setText("Login With Mobile");


                }

            }
        });


        HelpSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment helpSupportFragment = new HelpSupportFragment();
                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(LoginNewFragment.this.getId(), helpSupportFragment)
                        .commit();
            }
        });


        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment prFrag = new ForgotPassword();
                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(LoginNewFragment.this.getId(), prFrag)
                        .commit();


            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginValidation();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment prFrag = new Signup();
                Bundle bund = new Bundle();
                bund.putString("flag", value);
                prFrag.setArguments(bund);
                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(LoginNewFragment.this.getId(), prFrag)
                        .commit();


            }
        });
        mTwitterBtn.setCallback(new com.twitter.sdk.android.core.Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

                System.out.println("gdfdsgfjdsgfjdgfjdgfj" + " " + result.data);
                //Toast.makeText(getContext(), "Signed in to twitter successful", Toast.LENGTH_LONG).show();

                // Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();


                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
                AccountService service = twitterApiClient.getAccountService();


                service.verifyCredentials(false, false, true).enqueue(new com.twitter.sdk.android.core.Callback<User>() {
                    @Override
                    public void success(Result<User> result) {
                        Log.e("FrominsideDataSuccess", "--->>>.");
                        // I converted the user object into json for readability purposes
                        User user = result.data;
                        twitteremail = result.data.email;
                        String profileImageUrl = result.data.profileImageUrl;
                        twitterusername = result.data.name;


                        twitterId = String.valueOf(result.data.id);

                        if (twitteremail.isEmpty()  ) {
                            DialogFragment prFrag = new EmailAddressDialog();
                            Bundle bund = new Bundle();

                            prFrag.setArguments(bund);

                            prFrag.setTargetFragment(LoginNewFragment.this, 1003);
                            prFrag.show(getFragmentManager(), "signup");


                        } else {

                            makeupgradeSignUpwithtwitter(twitterusername, twitterusername, twitteremail, twitterId);


                        }


                        Gson gson = new Gson();
                        String jsonData = gson.toJson(user);
                        Log.i("Json> User  ", jsonData + "");

                        System.out.println("gdfdsgfjdsgfjdgfjdgfj" + " " + jsonData);

                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Toast.makeText(getContext(), "From exception", Toast.LENGTH_SHORT).show();


                        Log.e("From exception", "From exception");


                    }
                });
            }


            @Override
            public void failure(TwitterException exception) {
                // Toast.makeText(getContext(),"From failure",Toast.LENGTH_SHORT).show();
                Log.e("From failure", "From failure");

            }
        });


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


    private void setFacebookData(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Response", response.toString());

                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");

                            String profileURL = "";

                            Log.d("email", email + "----->>>>");
                            Log.d("email", firstName + "----->>>>");
                            Log.d("email", lastName + "----->>>>");
                            //   Toast.makeText(getContext(),email+" "+firstName+" "+lastName,Toast.LENGTH_LONG).show();

                            loginUpgradedProceedwithfacebook(email, " ", "by_facebook");


                            if (Profile.getCurrentProfile() != null) {
                                profileURL = ImageRequest.getProfilePictureUri(Profile.getCurrentProfile().getId(), 400, 400).toString();
                            }

                            //TODO put your code here
                        } catch (JSONException e) {
                            // Toast.makeText(ActivitySignIn.this, R.string.error_occurred_try_again, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void sendotp() {


        if (selectedmethod.equals("mobilewithotp")) {


            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                {
                    isAllConditionFulfilled = Util.checkTextViewValidation(edtmobile, "Please enter the mobile number")
                       /* && Util.checkMobileValidation(edtmobile, mobilevalidationlength,
                        "Please enter the valid mobile number")*/;

                }
            } else {
                isAllConditionFulfilled = Util.checkTextViewValidation(edtmobile, "من فضلك أدخل رقم الجوال")
                /* && Util.checkMobileValidation(edtmobile, mobilevalidationlength, "من فضلك أدخل رقم الجوال")*/;
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

    public void opengetOtpDialog() {

        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
            alertDialogBuilder.setMessage("Ensure that the mobile number is correct");
            alertDialogBuilder.setPositiveButton("\n" +
                            "Get the code",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {


                            //sendLoginOtp();
                            upgradesendLoginOtp();


                        }
                    });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {


                }
            });

        } else {
            alertDialogBuilder.setMessage("التأكد من صحة رقم الجوال");
            alertDialogBuilder.setPositiveButton("أدخل الكود",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {


                            //sendLoginOtp();
                            upgradesendLoginOtp();


                        }
                    });

            alertDialogBuilder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {


                }
            });
        }
        //alertDialogBuilder.setTitle("التأكد من صحة رقم الجوال");


        alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();

        TextView messageView = (TextView) alertDialog.findViewById(android.R.id.message);
        messageView.setTypeface(null, Typeface.BOLD);
        messageView.setGravity(Gravity.CENTER);


        final Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        // LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        // positiveButtonLL.gravity = Gravity.CENTER;
        //positiveButton.setLayoutParams(positiveButtonLL);
        positiveButton.setGravity(Gravity.CENTER);

        final Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setVisibility(View.GONE);


    }

    public void upgradesendLoginOtp() {

        parentDialogListener.showProgressDialog();

        /*BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);*/

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Map<String, String> map1 = new HashMap<>();
        map1.put("mobile", /*"+917906156955"*/celcode.get(selctedposition) + edtmobile.getText().toString());

        map1.put("resend", "0");
        map1.put("application", "1");


        Call<OtpResponse> call = apiService.upgradesendotpforLogin("https://upgrade.eoutlet.com/vsms/app/sendLogin?" + "mobile=" + celcode.get(selctedposition) + edtmobile.getText().toString() + "&resend=0&application=1"/*map1*/);
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().success) {
                        if (MySharedPreferenceClass.getChoosenlanguage(v.getContext()).equals("ar")) {
                            Toast.makeText(getContext(), "تم ارسال الكود بنجاح", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "The code has been sent successfully", Toast.LENGTH_SHORT).show();

                        }

                        DialogFragment prFrag = new LoginVerifyOtp();
                        Bundle bund = new Bundle();
                        bund.putString("mobile", celcode.get(selctedposition) + edtmobile.getText().toString());

                        prFrag.setArguments(bund);

                        prFrag.setTargetFragment(LoginNewFragment.this, 1002);
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
                if (MySharedPreferenceClass.getChoosenlanguage(v.getContext()).equals("ar")) {

                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Something went wrong - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void sendLoginOtp() {

        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Map<String, String> map1 = new HashMap<>();
        map1.put("mobile", /*"+917906156955"*/celcode.get(selctedposition) + edtmobile.getText().toString());

        map1.put("resend", "0");


        Call<OtpResponse> call = apiService.sendotpforLogin(map1);
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().success) {
                        Toast.makeText(getContext(), "تم ارسال الكود بنجاح", Toast.LENGTH_SHORT).show();


                        DialogFragment prFrag = new LoginVerifyOtp();
                        Bundle bund = new Bundle();
                        bund.putString("mobile", celcode.get(selctedposition) + edtmobile.getText().toString());

                        prFrag.setArguments(bund);

                        prFrag.setTargetFragment(LoginNewFragment.this, 1002);
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

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void loginValidation() {


        if (selectedmethod.equals("email")) {

            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                isAllConditionFulfilled = Util.checkTextViewValidation(edtusername,
                        "Please enter your username")
                        && Util.checkTextViewValidation(userPassword, "Please enter your password")
                       /* && Util.checkTextViewlengthValidation(userPassword, "", 16)

                        && Util.checkTextViewlengthValidation(userPassword, " ", 3)*/
                ;

            } else {
                isAllConditionFulfilled = Util.checkTextViewValidation(edtusername, "الرجاء ادخال اسم المستخدم ")
                        && Util.checkTextViewValidation(userPassword, "من فضلك ادخل كلمة المرور")
                        /*&& Util.checkTextViewlengthValidation(userPassword, "", 16)

                        && Util.checkTextViewlengthValidation(userPassword, " ", 3)*/
                ;

            }


            if (!isAllConditionFulfilled) {
                if (Util.view_final != null) {

                    Util.view_final.requestFocus();
                }


                return;
            }
            userName = edtusername.getText().toString();
            passWord = userPassword.getText().toString();

            //loginProceed(userName, passWord, "0");

            loginUpgradedProceed(userName, passWord, "email");

        } else if (selectedmethod.equals("mobile")) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                isAllConditionFulfilled = Util.checkTextViewValidation(edtmobile,
                        "Please enter the mobile number");
                /* && Util.checkMobileValidation(edtmobile, mobilevalidationlength, "Please enter the valid mobile number")*/
                /*&& Util.checkTextViewValidation(userPassword, "Please enter your password");*/
            } else {

                isAllConditionFulfilled = Util.checkTextViewValidation(edtmobile, "من فضلك أدخل رقم الجوال")
                       /* && Util.checkMobileValidation(edtmobile, mobilevalidationlength, "من فضلك أدخل رقم الجوال")
                        && Util.checkTextViewValidation(userPassword, "برجاء كتابة كلمة السر")*/;
            }
            if (isAllConditionFulfilled) {

                //loginProceed(" ", userPassword.getText().toString(), "1");
                loginUpgradedProceed(" ", userPassword.getText().toString(), "mobile");

            } else {

                if (Util.view_final != null) {

                    Util.view_final.requestFocus();
                }

                return;
            }


        } else if (selectedmethod.equals("mobilewithotp")) {


            sendotp();


        }


    }


    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice;
        String tmSerial;
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE") != 0) {
                if (context instanceof Activity && !ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, "android.permission.READ_PHONE_STATE")) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{"android.permission.READ_PHONE_STATE"}, 222);
                }

                tmDevice = "";
                tmSerial = "";
            } else {
                if (Build.VERSION.SDK_INT >= 26) {
                    tmDevice = "" + tm.getImei();
                } else {
                    tmDevice = "" + tm.getDeviceId();
                }

                tmDevice = "" + tm.getDeviceId();
                tmSerial = "" + tm.getSimSerialNumber();
            }
        } else {
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
        }

        String androidId = "" + Settings.Secure.getString(context.getContentResolver(), "android_id");
        UUID deviceUuid = new UUID((long) androidId.hashCode(), (long) tmDevice.hashCode() << 32 | (long) tmSerial.hashCode());
        return deviceUuid.toString();
    }


    @Nullable
    String getUniqueID() {
        UUID wideVineUuid = new UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L);
        try {
            MediaDrm wvDrm = new MediaDrm(wideVineUuid);
            byte[] wideVineId = wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID);
            return Arrays.toString(wideVineId);
        } catch (Exception e) {
            // Inspect exception
            return null;
        }
        // Close resources with close() or release() depending on platform API
        // Use ARM on Android P platform or higher, where MediaDrm has the close() method
    }

    @Nullable
    String getUniqueID2() {
        UUID wideVineUuid = new UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L);
        try {
            MediaDrm wvDrm = new MediaDrm(wideVineUuid);
            byte[] wideVineId = wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID);


            String encodedString = Base64.encodeToString(wideVineId, Base64.DEFAULT);
            Log.i("Uniqueid", "Uniqueid" + encodedString);
            return encodedString;
        } catch (Exception e) {
            // Inspect exception
            return null;
        }

    }


    public void loginUpgradedProceed(String username, String password, String logintype) {
        String serial;
        String android_id;
        String myKey;


        /*{"param":{"email":"aksypopoyt@unsgmail.com","mobile_number":"",
        "password":"ghfkfhkf","mask_key":"","cart_id":"","login_type":"google","device_id":""}}*/


        myKey = getUniqueID2().replaceAll("[^A-Za-z0-9]","");

        parentDialogListener.showProgressDialog();


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Map<Object, Object> mainparam = new HashMap<>();

        Map<String, String> map1 = new HashMap<>();


        map1.put("email", username);
        map1.put("type", logintype);
        map1.put("password", password);
        map1.put("device_type", "android");
        map1.put("device_id", myKey);
        StringBuilder stringnumber = new StringBuilder(edtmobile.getText().toString());
        if (celcode.size() > 0 && edtmobile.getText().toString().length() > 0) {
            if (edtmobile.getText().toString().charAt(0) == '0') {

                stringnumber.deleteCharAt(0);

            }
            map1.put("mobile_number", celcode.get(selctedposition) + stringnumber);


        } else {

            map1.put("mobile_number", " ");

        }
        map1.put("cart_id", String.valueOf(MySharedPreferenceClass.getCartId(getContext())));
        map1.put("mask_key", MySharedPreferenceClass.getMaskkey(getContext()));
        map1.put("account_id", "");
        mainparam.put("param", map1);
        Call<UpgradeLoginResponse> call;
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
            call = apiService.doupgradeLogin(mainparam);
        } else {
            call = apiService.doupgradeLoginforEnglish(mainparam);
        }


        call.enqueue(new Callback<UpgradeLoginResponse>() {
            @Override
            public void onResponse(Call<UpgradeLoginResponse> call, Response<UpgradeLoginResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().response.equals("success")) {

                        hideSoftKeyboard(edtpassword);

                        String cookieset = response.headers().get("Set-Cookie");
                        MySharedPreferenceClass.setCookies(getApplicationContext(), cookieset);


                        MySharedPreferenceClass.setMyFirstNamePref(getContext(), response.body().data.get(0).fname);
                        MySharedPreferenceClass.setMyLastNamePref(getContext(), response.body().data.get(0).lname);
                        MySharedPreferenceClass.setMobileNumber(getContext(), response.body().data.get(0).mob);
                        MySharedPreferenceClass.setCountryCode(getContext(), response.body().data.get(0).country_code);
                        MySharedPreferenceClass.setEmail(getContext(), response.body().data.get(0).email);
                        MySharedPreferenceClass.setMyUserId(getContext(), response.body().data.get(0).id);
                        MySharedPreferenceClass.setMyUserName(getContext(), response.body().data.get(0).fname + " " + response.body().data.get(0).lname);
                        MySharedPreferenceClass.setMyPassword(getContext(), password);
                        //getAddressDetails();

                        logUser(response.body().data.get(0).id, response.body().data.get(0).email, response.body().data.get(0).fname);


                        appsflyer_event_login(response.body().data.get(0).id);

                        getTokenID();

                        MoEHelper.getInstance(getApplicationContext()).setFirstName(response.body().data.get(0).fname);
                        MoEHelper.getInstance(getApplicationContext()).setLastName(response.body().data.get(0).lname);
                        MoEHelper.getInstance(getApplicationContext()).setFullName(response.body().data.get(0).fname + " " + response.body().data.get(0).lname);
                        MoEHelper.getInstance(getApplicationContext()).setEmail(response.body().data.get(0).email);
                        MoEHelper.getInstance(getApplicationContext()).setUniqueId(response.body().data.get(0).id);

                        if (response.body().data.get(0).country_code != null && response.body().data.get(0).mob != null) {
                            MoEHelper.getInstance(getApplicationContext()).setNumber(response.body().data.get(0).country_code + response.body().data.get(0).mob);
                        } else {

                            MoEHelper.getInstance(getApplicationContext()).setNumber("");
                        }

                        mergerWishlist();
                        if (value != null && value.equals("fromdetail")) {


                            getActivity().onBackPressed();


                        } else {


                        }


                    } else {
                        parentDialogListener.hideProgressDialog();
                        Toast.makeText(getView().getContext(), response.body().message.toString() /*"لم تقم بتسجيل الدخول بشكل صحيح أو أن حسابك معطل مؤقتاً."*/, Toast.LENGTH_SHORT).show();


                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<UpgradeLoginResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                System.out.println("ErrorApi...." + t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void mergerWishlist() {
        String mykey = getUniqueID2().replaceAll("[^A-Za-z0-9]", "");
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<AboutUsResponse> call = Locale == "ar" ? apiService.getMergeWishList("https://upgrade.eoutlet.com/rest/ar/V1/api/mergewishlist/" + "1" + mykey.toUpperCase() + "2/" + MySharedPreferenceClass.getMyUserId(getApplicationContext())) : apiService.getMergeWishList("https://upgrade.eoutlet.com/rest/ar/V1/api/mergewishlist/" + "1" + mykey.toUpperCase() + "2/" + MySharedPreferenceClass.getMyUserId(getApplicationContext()));
        call.enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {
                if (response.body() != null) {

                    if (response.body().msg.equals("success")) {

                        parentDialogListener.hideProgressDialog();
                    } else {
                        parentDialogListener.hideProgressDialog();
                        Log.e("Faq request", "failed");
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AboutUsResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void loginUpgradedProceedwithfacebook(String username, String password, String logintype) {
        String serial;
        String android_id;
        String myKey;


        /*{"param":{"email":"aksypopoyt@unsgmail.com","mobile_number":"",
        "password":"ghfkfhkf","mask_key":"","cart_id":"","login_type":"google","device_id":""}}*/


        myKey = getUniqueID2().replaceAll("[^A-Za-z0-9]","");

        parentDialogListener.showProgressDialog();

       /* BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);*/


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Map<Object, Object> mainparam = new HashMap<>();

        Map<String, String> map1 = new HashMap<>();
        map1.put("email", username);
        map1.put("login_type", logintype);
        map1.put("password", password);
        map1.put("device_type", "android");
        map1.put("device_id", myKey);


        StringBuilder stringnumber = new StringBuilder(edtmobile.getText().toString());
        if (celcode.size() > 0 && edtmobile.getText().toString().length() > 0) {
            if (edtmobile.getText().toString().charAt(0) == '0') {

                stringnumber.deleteCharAt(0);

            }
            map1.put("mobile_number", celcode.get(selctedposition) + stringnumber);

            //map1.put("mobile_number", celcode.get(selctedposition) + edtmobile.getText().toString());
        } else {

            map1.put("mobile_number", " ");

        }
        map1.put("cart_id", String.valueOf(MySharedPreferenceClass.getCartId(getContext())));
        map1.put("mask_key", MySharedPreferenceClass.getMaskkey(getContext()));

        mainparam.put("param", map1);

        Call<UpgradeLoginResponse> call;
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
            call = apiService.doupgradeLogin(mainparam);
        } else {
            call = apiService.doupgradeLoginforEnglish(mainparam);
        }
        call.enqueue(new Callback<UpgradeLoginResponse>() {
            @Override
            public void onResponse(Call<UpgradeLoginResponse> call, Response<UpgradeLoginResponse> response) {


                if (response.body() != null) {
                    //parentDialogListener.hideProgressDialog();


                    if (response.body().response.equals("success")) {
                        hideSoftKeyboard(edtpassword);

                        MySharedPreferenceClass.setLoginType(getContext(), "by_facebook");
                        MySharedPreferenceClass.setMyFirstNamePref(getContext(), response.body().data.get(0).fname);
                        MySharedPreferenceClass.setMyLastNamePref(getContext(), response.body().data.get(0).lname);
                        MySharedPreferenceClass.setMobileNumber(getContext(), response.body().data.get(0).mob);
                        MySharedPreferenceClass.setCountryCode(getContext(), response.body().data.get(0).country_code);
                        MySharedPreferenceClass.setEmail(getContext(), response.body().data.get(0).email);
                        MySharedPreferenceClass.setMyUserId(getContext(), response.body().data.get(0).id);
                        MySharedPreferenceClass.setMyUserName(getContext(), response.body().data.get(0).fname + " " + response.body().data.get(0).lname);
                        //getAddressDetails();

                        logUser(response.body().data.get(0).id, response.body().data.get(0).email, response.body().data.get(0).fname);


                        appsflyer_event_login(response.body().data.get(0).id);


                        //  Crashlytics.getInstance().crash(); //Used to check the Crashlaytics


                        if (value != null && value.equals("fromdetail")) {

                            getActivity().onBackPressed();


                        } else {


                            getAddressList();


                        }


                    } else {
                        parentDialogListener.hideProgressDialog();
                        Toast.makeText(getView().getContext(), response.body().message.toString() /*"لم تقم بتسجيل الدخول بشكل صحيح أو أن حسابك معطل مؤقتاً."*/, Toast.LENGTH_SHORT).show();


                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<UpgradeLoginResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                System.out.println("ErrorApi...." + t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void loginUpgradedProceedwithgoogle(String username, String password, String logintype, String id) {
        String serial;
        String android_id;
        String myKey;


        /*{"param":{"email":"aksypopoyt@unsgmail.com","mobile_number":"",
        "password":"ghfkfhkf","mask_key":"","cart_id":"","login_type":"google","device_id":""}}*/


        myKey = getUniqueID2().replaceAll("[^A-Za-z0-9]","");

        parentDialogListener.showProgressDialog();

       /* BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);*/


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Map<Object, Object> mainparam = new HashMap<>();

        Map<String, String> map1 = new HashMap<>();
        map1.put("email", username);
        map1.put("type", logintype);
        map1.put("password", password);
        map1.put("device_type", "android");
        map1.put("device_id", myKey);
        map1.put("mobile_number", " ");
        map1.put("cart_id", String.valueOf(MySharedPreferenceClass.getCartId(getApplicationContext())));
        map1.put("mask_key", MySharedPreferenceClass.getMaskkey(getApplicationContext()));
        map1.put("account_id", id);

        mainparam.put("param", map1);

        Call<UpgradeLoginResponse> call;
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
            call = apiService.doupgradeLogin(mainparam);
        } else {
            call = apiService.doupgradeLoginforEnglish(mainparam);
        }
        call.enqueue(new Callback<UpgradeLoginResponse>() {
            @Override
            public void onResponse(Call<UpgradeLoginResponse> call, Response<UpgradeLoginResponse> response) {


                if (response.body() != null) {
                    //parentDialogListener.hideProgressDialog();


                    if (response.body().response.equals("success")) {

                        String cookieset = response.headers().get("Set-Cookie");

                        MySharedPreferenceClass.setCookies(getApplicationContext(), cookieset);


                        MySharedPreferenceClass.setLoginType(getApplicationContext(), "google");
                        MySharedPreferenceClass.setMyFirstNamePref(getApplicationContext(), response.body().data.get(0).fname);
                        MySharedPreferenceClass.setMyLastNamePref(getApplicationContext(), response.body().data.get(0).lname);
                        MySharedPreferenceClass.setMobileNumber(getApplicationContext(), response.body().data.get(0).mob);
                        MySharedPreferenceClass.setCountryCode(getApplicationContext(), response.body().data.get(0).country_code);
                        MySharedPreferenceClass.setEmail(getApplicationContext(), response.body().data.get(0).email);
                        MySharedPreferenceClass.setMyUserId(getApplicationContext(), response.body().data.get(0).id);
                        MySharedPreferenceClass.setMyUserName(getApplicationContext(), response.body().data.get(0).fname + " " + response.body().data.get(0).lname);

                        MoEHelper.getInstance(getApplicationContext()).setFirstName(response.body().data.get(0).fname);
                        MoEHelper.getInstance(getApplicationContext()).setLastName(response.body().data.get(0).lname);
                        MoEHelper.getInstance(getApplicationContext()).setFullName(response.body().data.get(0).fname + " " + response.body().data.get(0).lname);
                        MoEHelper.getInstance(getApplicationContext()).setEmail(response.body().data.get(0).email);
                        MoEHelper.getInstance(getApplicationContext()).setUniqueId(response.body().data.get(0).id);

                        if (response.body().data.get(0).country_code != null && response.body().data.get(0).mob != null) {
                            MoEHelper.getInstance(getApplicationContext()).setNumber(response.body().data.get(0).country_code + response.body().data.get(0).mob);
                        } else {

                            MoEHelper.getInstance(getApplicationContext()).setNumber("");
                        }

                        logUser(response.body().data.get(0).id, response.body().data.get(0).email, response.body().data.get(0).fname);


                        appsflyer_event_login(response.body().data.get(0).id);


                        getTokenID();


                    } else {
                        parentDialogListener.hideProgressDialog();
                        Toast.makeText(getView().getContext(), response.message().toString() /*"لم تقم بتسجيل الدخول بشكل صحيح أو أن حسابك معطل مؤقتاً."*/, Toast.LENGTH_SHORT).show();
                        signOut();


                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<UpgradeLoginResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                System.out.println("ErrorApi...." + t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }


    public void loginUpgradedProceedwithtwitter(String username, String password, String logintype, String id) {
        String serial;
        String android_id;
        String myKey;


        /*{"param":{"email":"aksypopoyt@unsgmail.com","mobile_number":"",
        "password":"ghfkfhkf","mask_key":"","cart_id":"","login_type":"google","device_id":""}}*/


        myKey = getUniqueID2().replaceAll("[^A-Za-z0-9]","");

        //parentDialogListener.showProgressDialog();

       /* BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);*/


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Map<Object, Object> mainparam = new HashMap<>();

        Map<String, String> map1 = new HashMap<>();
        map1.put("email", username);
        map1.put("type", logintype);
        map1.put("password", password);
        map1.put("device_type", "android");
        map1.put("device_id", myKey);
        map1.put("mobile_number", " ");
        map1.put("cart_id", String.valueOf(MySharedPreferenceClass.getCartId(getContext())));
        map1.put("mask_key", MySharedPreferenceClass.getMaskkey(getContext()));
        map1.put("account_id", id);

        mainparam.put("param", map1);

        Call<UpgradeLoginResponse> call;
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
            call = apiService.doupgradeLogin(mainparam);
        } else {
            call = apiService.doupgradeLoginforEnglish(mainparam);
        }
        call.enqueue(new Callback<UpgradeLoginResponse>() {
            @Override
            public void onResponse(Call<UpgradeLoginResponse> call, Response<UpgradeLoginResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().response.equals("success")) {
                        hideSoftKeyboard(edtpassword);

                        String cookieset = response.headers().get("Set-Cookie");

                        MySharedPreferenceClass.setCookies(getApplicationContext(), cookieset);

                        MySharedPreferenceClass.setLoginType(getContext(), "twitter");
                        MySharedPreferenceClass.setMyFirstNamePref(getContext(), response.body().data.get(0).fname);
                        MySharedPreferenceClass.setMyLastNamePref(getContext(), response.body().data.get(0).lname);
                        MySharedPreferenceClass.setMobileNumber(getContext(), response.body().data.get(0).mob);
                        MySharedPreferenceClass.setCountryCode(getContext(), response.body().data.get(0).country_code);
                        MySharedPreferenceClass.setEmail(getContext(), response.body().data.get(0).email);
                        MySharedPreferenceClass.setMyUserId(getContext(), response.body().data.get(0).id);
                        MySharedPreferenceClass.setMyUserName(getContext(), response.body().data.get(0).fname + " " + response.body().data.get(0).lname);
                        //getAddressDetails();


                        logUser(response.body().data.get(0).id, response.body().data.get(0).email, response.body().data.get(0).fname);


                        appsflyer_event_login(response.body().data.get(0).id);


                        MoEHelper.getInstance(getApplicationContext()).setFirstName(response.body().data.get(0).fname);
                        MoEHelper.getInstance(getApplicationContext()).setLastName(response.body().data.get(0).lname);
                        MoEHelper.getInstance(getApplicationContext()).setFullName(response.body().data.get(0).fname + " " + response.body().data.get(0).lname);
                        MoEHelper.getInstance(getApplicationContext()).setEmail(response.body().data.get(0).email);
                        MoEHelper.getInstance(getApplicationContext()).setUniqueId(response.body().data.get(0).id);
                        MoEHelper.getInstance(getApplicationContext()).trackDeviceLocale();
                        if (response.body().data.get(0).country_code != null && response.body().data.get(0).mob != null) {
                            MoEHelper.getInstance(getApplicationContext()).setNumber(response.body().data.get(0).country_code + response.body().data.get(0).mob);
                        } else {

                            MoEHelper.getInstance(getApplicationContext()).setNumber("");
                        }


                        getTokenID();


                    } else {
                        parentDialogListener.hideProgressDialog();
                        Toast.makeText(getView().getContext(), response.message().toString() /*"لم تقم بتسجيل الدخول بشكل صحيح أو أن حسابك معطل مؤقتاً."*/, Toast.LENGTH_SHORT).show();


                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<UpgradeLoginResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                System.out.println("ErrorApi...." + t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void getTokenID() {
        //parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        map1.put("username", MySharedPreferenceClass.getAdminUsername(getApplicationContext()));
        map1.put("password", MySharedPreferenceClass.getAdminPassword(getApplicationContext()));

        Call<String> call;
        if (Locale.equals("en")) {
            call = apiService.createtokenbyId(map1);

        } else {
            call = apiService.createtokenbyIdArabic(map1);
        }


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null && response.isSuccessful()) {
                    parentDialogListener.hideProgressDialog();


                    if (MySharedPreferenceClass.getMyUserId(getApplicationContext()) != " ") {
                        getQuoteID(response.body().toString());

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


                } else {

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.errorBody().string());
                        String userMessage = jsonObject.getString("message");
                        Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    parentDialogListener.hideProgressDialog();


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void getQuoteID(String authtoken) {
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        map1.put("customer_id",/*"83188" */MySharedPreferenceClass.getMyUserId(getApplicationContext()));


        // mainparam.put("param",map1);

        Call<String> call = apiService.getQuotebyId("Bearer" + " " + authtoken, map1);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null) {
                    MySharedPreferenceClass.setQuoteId(getContext(), response.body().toString());

                    //  Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_SHORT).show();


                    if (value != null && value.equals("fromdetail")) {
                        getActivity().onBackPressed();


                    } else {
                        //getAddressDetails2();
                        getAddressList();
                        getWishListItemCount();

                    }


                    Log.d("Response------->>>>", response.body().toString());


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void getWishListItemCount() {
        String mykey = getUniqueID2().replaceAll("[^A-Za-z0-9]", "");
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
            Locale = "ar";
        } else {
            Locale = "en";
        }
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Call<UpgradeWishListResponse> call;
        if (!MySharedPreferenceClass.getMyUserId(getContext()).equals(" ")) {
            call = Locale == "ar" ? apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "mywishlist/" + MySharedPreferenceClass.getMyUserId(getContext())) : apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "mywishlist/" + MySharedPreferenceClass.getMyUserId(getContext()));
        } else {
            call = Locale == "ar" ? apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "getguestwishlist/" + "1" + mykey.toUpperCase() + "2/") : apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "getguestwishlist/" + "1" + mykey.toUpperCase() + "2/");
        }

        //  Call<UpgradeWishListResponse> call = Locale == "ar" ? apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "mywishlist/" + MySharedPreferenceClass.getMyUserId(getContext())) : apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "mywishlist/" + MySharedPreferenceClass.getMyUserId(getContext()));


        call.enqueue(new Callback<UpgradeWishListResponse>() {
            @Override
            public void onResponse(Call<UpgradeWishListResponse> call, Response<UpgradeWishListResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResponse().equals("success") && response.body().getData() != null) {
                        if (response.body().getData().size() > 0) {
                            NewCategoryDesignFragments.categorywishListBadgeCount.setVisibility(View.VISIBLE);
                            NewCategoryDesignFragments.categorywishListBadgeCount.setText(String.valueOf(response.body().getData().size()));
                            if (ProductList.productListBadgeCount != null) {
                                ProductList.productListBadgeCount.setVisibility(View.VISIBLE);
                                ProductList.productListBadgeCount.setText(String.valueOf(response.body().getData().size()));
                            }
                            wishListBadgeCount.setVisibility(View.VISIBLE);
                            wishListBadgeCount.setText(String.valueOf(response.body().getData().size()));
                        } else {
                            NewCategoryDesignFragments.categorywishListBadgeCount.setVisibility(View.GONE);

                            if (ProductList.productListBadgeCount != null) {
                                ProductList.productListBadgeCount.setVisibility(View.GONE);
                            }

                            wishListBadgeCount.setVisibility(View.GONE);


                        }
                    } else {
                        Log.e("get wishList", "failed");
                    }
                }
                //getfirsttimepopupData();
            }

            @Override
            public void onFailure(Call<UpgradeWishListResponse> call, Throwable t) {
                Log.e("fail", t.getMessage());
            }
        });
    }

    public void DeviceRegister() {
        String serial;
        String android_id;
        String myKey;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Todo Don't forget to ask the permission

            android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            myKey = android_id;
        } else {
            serial = Build.SERIAL;
            android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            myKey = serial + android_id;
        }


        // String serial = Build.SERIAL;
           /*String android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            String myKey = serial + android_id;
*/
        //  parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        HashMap<String, String> map1 = new HashMap<>();
        map1.put("devicetype", "android");
        map1.put("fcm_token", FirebaseInstanceId.getInstance().getToken());
        map1.put("device_id", myKey);

        Call<LoginResponse> call = apiService.deviceregister(map1);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().msg.equals("success")) {

                        parentDialogListener.hideProgressDialog();


                    } else {
                        parentDialogListener.hideProgressDialog();
                        Toast.makeText(getContext(), "لم تقم بتسجيل الدخول بشكل صحيح أو أن حسابك معطل مؤقتاً.", Toast.LENGTH_SHORT).show();


                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                System.out.println("ErrorApi...." + t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }


    public void initProfileFragment() {

        //Log.e("From inside init profile method"," d;jfdlshjfkjsdfhk");


        if (!registereduserpassword.equals(" ") && !registereduserpassword.equals(" ")) {

            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        Intent i = new Intent(getContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);


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

    //    function to get list of address
    private void getAddressList() {
//        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<UpgradeGetAddressResponse> call = apiService.getUpgradedAddress(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK + "addresslist/" + MySharedPreferenceClass.getMyUserId(getApplicationContext()));
        call.enqueue(new Callback<UpgradeGetAddressResponse>() {
            @Override
            public void onResponse(Call<UpgradeGetAddressResponse> call, Response<UpgradeGetAddressResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().getResponse().equals("success")) {
                        List<UpgradedGetAddressList> data = response.body().getData();

                        Collections.reverse(data);
                        if (data.size() > 0) {

                            MySharedPreferenceClass.setfirstAdressname(getApplicationContext(), data.get(0).getFirstname());
                            MySharedPreferenceClass.setlastAdressname(getApplicationContext(), data.get(0).getLastname());
                            MySharedPreferenceClass.setCountryCode(getApplicationContext(), data.get(0).getCountry_id());
                            MySharedPreferenceClass.setCityname(getContext(), data.get(0).getCity());

                            MySharedPreferenceClass.setAddressname(getApplicationContext(), data.get(0).getFirstname() + " " + response.body().getData().get(0).getLastname());

                            MySharedPreferenceClass.setStreetName(getContext(), data.get(0).getStreet());
                            MySharedPreferenceClass.setCountryname(getContext(), data.get(0).getCountry());

                            MySharedPreferenceClass.setAddressphone(getContext(), data.get(0).getTelephone());
                            MySharedPreferenceClass.setCountryId(getContext(), data.get(0).getCountry_id());


                            MySharedPreferenceClass.setSelecteAddressdId(getContext(), data.get(0).getAddress_id());
                        }

                        //  Crashlytics.getInstance().crash(); //Used to check the Crashlaytics


                    } else {
                        Log.e("get address", "failed");
                    }
                    //getCartData();

                    getUpgradeCartData();
                    //initProfileFragment();
                }
            }

            @Override
            public void onFailure(Call<UpgradeGetAddressResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
            }
        });
    }


    public void getUpgradeCartData() {
        totalcount = 0;


        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", MySharedPreferenceClass.getMyUserId(getApplicationContext()));


        /*https://upgrade.eoutlet.com/rest/en/V1/api/customercart/83188*/


        Call<UpgradedCartItems> call = apiService.getUpgradedCartDetail(Constants.UPGRADED_HOST_LINK + "rest/en/V1/api/customercart/" + MySharedPreferenceClass.getMyUserId(getApplicationContext()));
        call.enqueue(new Callback<UpgradedCartItems>() {
            @Override
            public void onResponse(Call<UpgradedCartItems> call, Response<UpgradedCartItems> response) {
                parentDialogListener.hideProgressDialog();
                int quant;
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().response.equals("success")) {

                        parentDialogListener.hideProgressDialog();

                        upgradedcartData = response.body().data.items;
                        // initProfileFragment();

                        for (int i = 0; i < response.body().data.items.size(); i++) {

                            if (response.body().data.items.get(i).qty instanceof String) {
                                totalcount = totalcount + (int) Float.parseFloat((String) response.body().data.items.get(i).qty);

                            } else {
                                totalcount = totalcount + (int) (response.body().data.items.get(i).qty);


                            }


                        }


                        MySharedPreferenceClass.setBedgeCount(getApplicationContext(), totalcount);
                        updatefix.updateBedgeCount();
                        MainActivity.notificationBadge.setVisibility(View.VISIBLE);
                        parentDialogListener.hideProgressDialog();


                    }

                    if (value != null && value.equals("fromcart")) {

                        System.out.println("inside----->>>+");
                        if (cartreference != null) {

                            cartreference.setUserHintcall();
                        }

                        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    } else {

                        initProfileFragment();
                    }


                }
            }


            @Override
            public void onFailure(Call<UpgradedCartItems> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }


    protected void hideSoftKeyboard(EditText input) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    private void logUser(String id, String userMail, String userName) {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        //Crashlytics.setUserIdentifier(id);
        //Crashlytics.setUserEmail(userMail);
        //Crashlytics.setUserName(userName);

        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
        crashlytics.setUserId(id);


    }

    public void appsflyer_event_login(String customer_id) {
        Map<String, Object> eventValue = new HashMap<String, Object>();

        eventValue.put(AFInAppEventParameterName.CUSTOMER_USER_ID, customer_id);
        AppsFlyerLib.getInstance().trackEvent(getActivity(), AFInAppEventType.LOGIN, eventValue);
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


    public static boolean checkPermission(String permission, Activity activity) {
        return ContextCompat.checkSelfPermission(activity, permission) ==
                PackageManager.PERMISSION_GRANTED;
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

                        String countrycode = response.body().data.get(i).cel_code/*.replace("+","")*/;

                        // countryname.add("(" + response.body().data.get(i).cel_code + ")" + " " + response.body().data.get(i).name);
                        countryCode.add(response.body().data.get(i).code);

                        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {

                            countryname.add(response.body().data.get(i).name + " " + "(" + countrycode /*+ "+"*/ + ")");
                        } else {

                            countryname.add("(" + countrycode /*+ "+" */ + ")" + " " + response.body().data.get(i).name);

                        }
                        placeholder.add(response.body().data.get(i).placeholder.replaceAll("\\s", ""));
                        celcode.add(response.body().data.get(i).cel_code);


                    }
                    /*Collections.reverse(countryname);
                    Collections.reverse(celcode);
                    Collections.reverse(countryCode);
                    Collections.reverse(placeholder);*/
                    initSpinner();


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


    public void initSpinner() {

        if (getActivity() != null) {


            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(), R.layout.spinnertextview2english, countryname);
                adp2.setDropDownViewResource(R.layout.spinnertextview2english);

                countryspinner.setAdapter(adp2);
            } else {
                ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(), R.layout.spinnertextview2, countryname);
                adp2.setDropDownViewResource(R.layout.checkedradiotextview);
                countryspinner.setAdapter(adp2);

            }

          for(int i = 0;i<countryCode.size();i++) {
              if(MySharedPreferenceClass.getSelectedCountryCode(getApplicationContext()).equalsIgnoreCase(countryCode.get(i))) {
                  Log.e("countrycode",MySharedPreferenceClass.getSelectedCountryCode(getApplicationContext()));
                  countryspinner.setSelection(i);
                  break;
              }
          }


            countryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    //Toast.makeText(getApplicationContext(),"Position"+ position, Toast.LENGTH_SHORT).show();


                    countryName = countryspinner.getSelectedItem().toString();
                    selctedposition = position;
                    edtmobile.setHint(placeholder.get(selctedposition));
                    //  mobilevalidationlength = placeholder.get(selctedposition).length();
                    // edtmobile.setFilters(new InputFilter[]{new InputFilter.LengthFilter(placeholder.get(selctedposition).length())});
                    edtmobile.setText("");
                    //edtMobile.setHint("رقم الجوال");
                    Log.e("Gender Name--------.>>", countryName);

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }

            });

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else if (requestCode == 1003) {
            twitteremail = data.getStringExtra("email");
            makeupgradeSignUpwithtwitter(twitterusername, twitterlastname, twitteremail, twitterId);

            //loginUpgradedProceedwithtwitter(email," ","twitter",twitterId);


        } else if (requestCode == 1002) {
            loginUpgradedProceed(" ", " ", "otp");
        } else {
            mTwitterBtn.onActivityResult(requestCode, resultCode, data);
            //callbackManager.onActivityResult(requestCode, resultCode, data);
            // super.onActivityResult(requestCode, resultCode, data);

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
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.e("TAG", "EmailId" + completedTask.getResult().getEmail()); //arpan@unyscape.com
            Log.e("TAG", "EmailId" + completedTask.getResult().getGivenName()); //Arpan
            Log.e("TAG", "EmailId" + completedTask.getResult().getFamilyName()); //Bhatia
            Log.e("TAG", "EmailId" + completedTask.getResult().getDisplayName());//Arpan Bhatia
            Log.e("TAG", "EmailId" + completedTask.getResult().getId()); //118072629512299493582

            googlefnama = completedTask.getResult().getGivenName();
            googlelname = completedTask.getResult().getFamilyName();
            googleemail = completedTask.getResult().getEmail();
            googlefullname = completedTask.getResult().getDisplayName();
            googleid = completedTask.getResult().getId();


            //Toast.makeText(getContext(), completedTask.getResult().getEmail(),Toast.LENGTH_LONG).show();
            //loginUpgradedProceedwithgoogle(completedTask.getResult().getEmail(), " ", "google", completedTask.getResult().getId());
            // Signed in successfully, show authenticated UI.
            //updateUI(account);

            makeupgradeSignUpwithgoogle(completedTask.getResult().getGivenName(), completedTask.getResult().getFamilyName(),
                    completedTask.getResult().getEmail(), completedTask.getResult().getId());


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("TAG", "signInResult:failed code=" + e.getStatusCode());
            // updateUI(null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mTwitterAuthConfig = new TwitterAuthConfig(getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret));
        TwitterConfig twitterConfig = new TwitterConfig.Builder(getContext())
                .twitterAuthConfig(mTwitterAuthConfig).debug(true)
                .build();
        Twitter.initialize(twitterConfig);
    }

    public void makeupgradeSignUpwithgoogle(String firstname, String lastname, String email, String id) {
        parentDialogListener.showProgressDialog();
/*
        {"param":{"firstname":"abhishek","lastname":"srivastava","email":"abhishek@unyscapegmail.com",
                "mobilenumber":"+918285785213","password":"admin@123","gender":1,"type":"","account_id":"","device_used":"ios"}}*/

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        /*BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);*/
        Map<Object, Object> mainparam = new HashMap<>();

        Map<String, String> map1 = new HashMap<>();


        map1.put("firstname", firstname);
        map1.put("lastname", lastname);
        map1.put("email", email);

        map1.put("mobilenumber", "");


        map1.put("password", "");


        map1.put("gender", "");
        map1.put("type", "google");
        map1.put("account_id", id);
        map1.put("device_used", "android");

        mainparam.put("param", map1);
        Call<SignUpResponse> call;
        if (Locale.equals("ar")) {
            call = apiService.getUpgradedSignUp(mainparam);
        } else {
            call = apiService.getUpgradedSignUpEnglish(mainparam);
        }


        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().response.equals("success")) {
                        loginUpgradedProceedwithgoogle(googleemail, " ", "google", googleid);

                        // Toast.makeText(getContext(), "اشترك بنجاح", Toast.LENGTH_SHORT).show();


                    } else {
                        parentDialogListener.hideProgressDialog();
                        //Toast.makeText(getContext(), response.body().message, Toast.LENGTH_SHORT).show();
                        signOut();


                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void makeupgradeSignUpwithtwitter(String firstname, String lastname, String email, String id) {
        parentDialogListener.showProgressDialog();
/*
        {"param":{"firstname":"abhishek","lastname":"srivastava","email":"abhishek@unyscapegmail.com",
                "mobilenumber":"+918285785213","password":"admin@123","gender":1,"type":"","account_id":"","device_used":"ios"}}*/

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        /*BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);*/
        Map<Object, Object> mainparam = new HashMap<>();

        Map<String, String> map1 = new HashMap<>();


        map1.put("firstname", firstname);
        map1.put("lastname", lastname);
        map1.put("email", email);

        map1.put("mobilenumber", "");


        map1.put("password", "");


        map1.put("gender", "");
        map1.put("type", "twitter");
        map1.put("account_id", id);
        map1.put("device_used", "android");

        mainparam.put("param", map1);


        Call<SignUpResponse> call;
        if (Locale.equals("ar")) {
            call = apiService.getUpgradedSignUp(mainparam);
        } else {
            call = apiService.getUpgradedSignUpEnglish(mainparam);
        }
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {


                if (response.body() != null) {
                    // parentDialogListener.hideProgressDialog();


                    if (response.body().response.equals("success")) {


                        // Toast.makeText(getContext(), "اشترك بنجاح", Toast.LENGTH_SHORT).show();
                        //loginUpgradedProceedwithtwitter(email," ","twitter",twitterId);
                        loginUpgradedProceedwithtwitter(twitteremail, " ", "twitter", twitterId);


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

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void sendcountryselction(String currencycode, Float rate) {
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

                if (response.body() != null && response.isSuccessful()) {

                    MySharedPreferenceClass.setSelectedCurrencyName(getContext(), currencycode);
                    MySharedPreferenceClass.setSelectedCurrencyRate(getContext(), rate);


                } else {


                }

            }


            @Override
            public void onFailure(Call<CurrencysetResponse> call, Throwable t) {

                Log.e(TAG, t.toString());

            }
        });


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


                    if (MySharedPreferenceClass.getSelectedCurrencyName(getContext()) == null) {


                        // MySharedPreferenceClass.setSelectedCountryCode(getApplicationContext(),response.body().baseCurrencyCode);


                        MySharedPreferenceClass.setSelectedCurrencyRate(getApplicationContext(), 1.0f);
                        MySharedPreferenceClass.setSelectedCurrencyName(getApplicationContext(), response.body().baseCurrencyCode);
                    }


                    getcountryDetail2();


                }

            }


            @Override
            public void onFailure(Call<GetCurrencyDetail> call, Throwable t) {

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


                    }


                }
            }

            @Override
            public void onFailure(Call<GetCountryCode> call, Throwable t) {
                //parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

}