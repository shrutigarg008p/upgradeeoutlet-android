package com.eoutlet.Eoutlet.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.eoutlet.Eoutlet.R;

import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.GetCountryCode;
import com.eoutlet.Eoutlet.pojo.OtpResponse;
import com.eoutlet.Eoutlet.pojo.SignUpResponse;
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
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.moe.pushlibrary.MoEHelper;
import com.moengage.core.Properties;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;


public class Signup extends Fragment {
    ArrayAdapter<String> adp2 = null;

    // TODO: Rename and change types of parameters
    private View v;
    private Context context;
    private Context mContext;
    static TwitterLoginButton mTwitterBtn;
    private SaveAddressFragment.OnFragmentInteractionListener mListener;
    private List<String> countryname;
    private List<String> countryCode;
    private List<String> celcode;
    private List<String> placeholder;
    Spinner countryspinner;
    public ParentDialogsListener parentDialogListener;
    private LinearLayout signIn;
    static String jsonstring;
    private boolean isAllConditionFulfilled;
    private String countryName;

    private String value;
    public static CallbackManager callbackManager;
    private LoginButton fbloginbutton;
    int selectedgender = 1;

    TwitterAuthConfig mTwitterAuthConfig;
    private String twitterId, twitteremail, twitterfirstname, twitterlastname, twitterusername;
    private int selctedposition = 0;
    //private int mobilevalidationlength;

    private ExecuteFragment execute;
    private TextView tvSignUp, tvtab_signin;

    private EditText edtFirstName, edtLastName, edtEmail, edtPassword, edtConfirmPassword, edtMobile;
    private TextView gender;

    private ImageView checkmr, checkmrs, checkms;
    private LinearLayout selectmr, selectmrs, selectms, mr_strip, mrs_strip, ms_strip;
    private Button fb, applesigin, googlelogin;
    SignInButton signInButton;
    int RC_SIGN_IN = 100;
    private GoogleSignInClient mGoogleSignInClient;

    public Signup() {
        // Required empty public constructor
    }

    public void passData(Context context) {
        mContext = context;
    }


    // TODO: Rename and change types and number of parameters
    public static Signup newInstance(String param1, String param2) {
        Signup fragment = new Signup();
        Bundle args = new Bundle();

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
        // v = inflater.inflate(R.layout.activity_signup, container, false);


        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {

            v = inflater.inflate(R.layout.signup_new, container, false);
        } else if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
            v = inflater.inflate(R.layout.signup_new_english, container, false);

        } else {
            v = inflater.inflate(R.layout.signup_new, container, false);
        }

        signInButton = v.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        value = getArguments().getString("flag");
        intiViews(v);
        getcountryDetail();
        execute = (MainActivity) mContext;

        mTwitterAuthConfig = new TwitterAuthConfig(getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret));
        TwitterConfig twitterConfig = new TwitterConfig.Builder(getContext())
                .twitterAuthConfig(mTwitterAuthConfig)
                .build();
        Twitter.initialize(twitterConfig);
        mTwitterBtn.setTextSize(14);
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
            mTwitterBtn.setText("المتابعة مع تويتر  ");
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
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void intiViews(View v) {
        signIn = v.findViewById(R.id.signiin);
        edtFirstName = v.findViewById(R.id.firstName);
        edtEmail = v.findViewById(R.id.eMail);
        edtLastName = v.findViewById(R.id.lastName);
        edtPassword = v.findViewById(R.id.password);
        edtConfirmPassword = v.findViewById(R.id.confirmpassword);
        edtMobile = v.findViewById(R.id.mobile);
        tvSignUp = v.findViewById(R.id.signUp);
        countryspinner = v.findViewById(R.id.countrySpinner);
        gender = v.findViewById(R.id.gender);
        tvtab_signin = v.findViewById(R.id.btn_signin);
        checkmr = v.findViewById(R.id.check_mr);
        checkmrs = v.findViewById(R.id.check_mrs);
        checkms = v.findViewById(R.id.check_ms);
        mTwitterBtn = v.findViewById(R.id.twitter_login_button);
        selectmr = v.findViewById(R.id.select_mr);
        selectmrs = v.findViewById(R.id.select_mrs);
        selectms = v.findViewById(R.id.select_ms);

        mr_strip = v.findViewById(R.id.strip_mr);
        mrs_strip = v.findViewById(R.id.strip_mrs);
        ms_strip = v.findViewById(R.id.strip_ms);

        fb = (Button) v.findViewById(R.id.fb);
        googlelogin = (Button) v.findViewById(R.id.googlelogin);
        applesigin = (Button) v.findViewById(R.id.applesignin);
        callbackManager = CallbackManager.Factory.create();

        fbloginbutton = (LoginButton) v.findViewById(R.id.fbsignin);
        fbloginbutton.setLoginBehavior(LoginBehavior.WEB_ONLY);
        mTwitterBtn.setTextSize(15);
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
            mTwitterBtn.setText("تواصل مع تويتر");
            mTwitterBtn.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "HelveticaLight.ttf"));

        } else {
            mTwitterBtn.setText("Continue with Twitter");
            mTwitterBtn.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Mulish_Regular.ttf"));
            mTwitterBtn.setPadding((int) getResources().getDimension(R.dimen._50sdp), 0, (int) getResources().getDimension(R.dimen._50sdp), 0);
        }

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbloginbutton.performClick();
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


        googlelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        selectmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkmr.setVisibility(View.VISIBLE);
                checkmrs.setVisibility(View.GONE);
                checkms.setVisibility(View.GONE);
                LinearLayout childView = (LinearLayout) getLayoutInflater().inflate(R.layout.indicator_strip, null);
                mr_strip.removeAllViews();
                mr_strip.addView(childView);


                LinearLayout childView1 = (LinearLayout) getLayoutInflater().inflate(R.layout.white_indicator_strip, null);
                mrs_strip.removeAllViews();
                mrs_strip.addView(childView1);
                LinearLayout childView2 = (LinearLayout) getLayoutInflater().inflate(R.layout.white_indicator_strip, null);
                ms_strip.removeAllViews();
                ms_strip.addView(childView2);
            }
        });
        selectmrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkmr.setVisibility(View.GONE);
                checkmrs.setVisibility(View.VISIBLE);
                checkms.setVisibility(View.GONE);


                LinearLayout childView = (LinearLayout) getLayoutInflater().inflate(R.layout.indicator_strip, null);
                mrs_strip.removeAllViews();
                mrs_strip.addView(childView);


                LinearLayout childView1 = (LinearLayout) getLayoutInflater().inflate(R.layout.white_indicator_strip, null);
                mr_strip.removeAllViews();
                mr_strip.addView(childView1);
                LinearLayout childView2 = (LinearLayout) getLayoutInflater().inflate(R.layout.white_indicator_strip, null);
                ms_strip.removeAllViews();
                ms_strip.addView(childView2);
            }
        });
        selectms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkmr.setVisibility(View.GONE);
                checkmrs.setVisibility(View.GONE);
                checkms.setVisibility(View.VISIBLE);
                LinearLayout childView = (LinearLayout) getLayoutInflater().inflate(R.layout.indicator_strip, null);
                ms_strip.removeAllViews();
                ms_strip.addView(childView);


                LinearLayout childView1 = (LinearLayout) getLayoutInflater().inflate(R.layout.white_indicator_strip, null);
                mrs_strip.removeAllViews();
                mrs_strip.addView(childView1);
                LinearLayout childView2 = (LinearLayout) getLayoutInflater().inflate(R.layout.white_indicator_strip, null);
                mr_strip.removeAllViews();
                mr_strip.addView(childView2);
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        tvtab_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment prFrag = new LoginNewFragment();
                Bundle bundl = new Bundle();

                prFrag.setArguments(bundl);

                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(Signup.this.getId(), prFrag)
                        .commit();


            }
        });


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment prFrag = new LoginNewFragment();
                Bundle bundl = new Bundle();

                prFrag.setArguments(bundl);

                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(Signup.this.getId(), prFrag)
                        .commit();


                //execute.ExecutFragmentListener(1);

            }
        });

        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                initgenderDialog();


            }
        });


        mTwitterBtn.setCallback(new com.twitter.sdk.android.core.Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {


                Toast.makeText(getContext(), "Signed in to twitter successful", Toast.LENGTH_LONG).show();

                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();


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

                        if (twitteremail == null) {
                            DialogFragment prFrag = new EmailAddressDialog();
                            Bundle bund = new Bundle();

                            prFrag.setArguments(bund);

                            prFrag.setTargetFragment(Signup.this, 1003);
                            prFrag.show(getFragmentManager(), "signup");


                        } else {
                            //loginUpgradedProceedwithtwitter(email," ","twitter",twitterId);
                            makeupgradeSignUpwithtwitter(twitterusername, twitterusername, twitteremail, twitterId);


                        }


                        // Toast.makeText(getContext(), email + username + profileImageUrl, Toast.LENGTH_LONG).show();


                        //Log.i("Json> User  ", email + "-->" + profileImageUrl + "-->" + username + "-->" + "");

                        Gson gson = new Gson();
                        String jsonData = gson.toJson(user);
                        Log.i("Json> User  ", jsonData + "");


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
                            String userId = response.getJSONObject().getString("id");

                            String profileURL = "";

                            Log.d("email", email + "----->>>>");
                            Log.d("email", firstName + "----->>>>");
                            Log.d("email", lastName + "----->>>>");
                            Log.d("userId", userId + "----->>>>");
                            //Toast.makeText(getContext(),email+" "+firstName+" "+lastName+" "+userId,Toast.LENGTH_LONG).show();


                            makeupgradeSignUpwithfacebok(firstName, lastName, email, userId);
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void initgenderDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


        String[] genders = {"رجل", " سيدة"};

        builder.setItems(genders, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int type) {


                if (type == 0) {
                    gender.setText("رجل");

                    selectedgender = 1;

                } else if (type == 1) {

                    gender.setText("سيدة");
                    selectedgender = 2;


                } else if (type == 2) {
                    selectedgender = 3;

                    gender.setText("غير محدد");


                }


            }
        });
        AlertDialog dialog = builder.create();
        /*TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        messageText.setTypeface(null, Typeface.BOLD);*/
        dialog.show();


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
                        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {

                            countryname.add(response.body().data.get(i).name + " " + "(" + countrycode /*+ "+"*/ + ")");
                        } else {
                            countryname.add("(" + countrycode /*+ "+" */ + ")" + " " + response.body().data.get(i).name);

                        }
                        placeholder.add(response.body().data.get(i).placeholder.replaceAll("\\s", ""));
                        celcode.add(response.body().data.get(i).cel_code);


                    }

                   /* Collections.reverse(countryCode);
                    Collections.reverse(countryname);
                    Collections.reverse(placeholder);
                    Collections.reverse(celcode);*/
                    // mobilevalidationlength = placeholder.get(0).length();
                    parentDialogListener.hideProgressDialog();


                    initSpinner();


                }
            }

            @Override
            public void onFailure(Call<GetCountryCode> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "Connection Timeout", Toast.LENGTH_LONG).show();
            }
        });


    }


    public void initSpinner() {
        if (getContext() != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                adp2 = new ArrayAdapter<String>(getContext(), R.layout.spinnertextview2english, countryname);
                parentDialogListener.hideProgressDialog();


                adp2.setDropDownViewResource(R.layout.spinnertextview2english);


                countryspinner.setAdapter(adp2);
            } else {

                adp2 = new ArrayAdapter<String>(getContext(), R.layout.spinnertextview2, countryname);
                parentDialogListener.hideProgressDialog();


                adp2.setDropDownViewResource(R.layout.spinnertextview2);


                countryspinner.setAdapter(adp2);
            }

            for (int i = 0; i < countryCode.size(); i++) {
                if (MySharedPreferenceClass.getSelectedCountryCode(getApplicationContext()).equalsIgnoreCase(countryCode.get(i))) {
                    Log.e("countrycode", MySharedPreferenceClass.getSelectedCountryCode(getApplicationContext()));
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
                    edtMobile.setHint(placeholder.get(selctedposition));
                    // mobilevalidationlength = placeholder.get(selctedposition).length();
                    // edtMobile.setFilters(new InputFilter[]{new InputFilter.LengthFilter(placeholder.get(selctedposition).length())});
                    edtMobile.setText("");
                    //edtMobile.setHint("رقم الجوال");
                    Log.e("Gender Name--------.>>", countryName);

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }

            });
        }

    }

    public void register() {

        if (!edtMobile.getText().toString().isEmpty()) {

            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                isAllConditionFulfilled = Util.checkTextViewValidation(edtFirstName,
                        "Please enter first name.")
                        && Util.checkTextViewValidation(edtLastName,
                        "Please enter last name.")
                        && Util.checkTextViewValidation(edtEmail,
                        "Please enter a valid email address")
                        && Util.isEmailValid(edtEmail)
                        && Util.checkTextViewValidation(edtPassword, "Please type your password")
                        && Util.checkTextViewValidation(edtConfirmPassword,
                        "Please enter the password") && Util.isPasswordLengthCorrect(edtPassword, "Password must be at least 7 digits")
                        && Util.isPasswordMatched(edtPassword, edtConfirmPassword,
                        "Password doesn't match with confirm password.")


                        && Util.checkTextViewValidation(edtMobile,
                        "Please enter the mobile number")
                        /*&& Util.checkMobileValidation(edtMobile, mobilevalidationlength,
                        "Please enter the valid mobile number")*/;
            } else {
                isAllConditionFulfilled = Util.checkTextViewValidation(edtFirstName, "من فضلك ادخل الاسم الاول")
                        && Util.checkTextViewValidation(edtLastName, "من فضلك ادخل الاسم الأخير")
                        && Util.checkTextViewValidation(edtEmail, "الرجاء إدخال بريد إلكتروني صحيح")
                        && Util.isEmailValid(edtEmail)
                        && Util.checkTextViewValidation(edtPassword, "برجاء كتابة كلمة السر")
                        && Util.checkTextViewValidation(edtConfirmPassword, "برجاء ادخال كود التأكيد") && Util.isPasswordLengthCorrect(edtPassword, "كلمة السر يجب أن لا تقل عن 7 أرقام")
                        && Util.isPasswordMatched(edtPassword, edtConfirmPassword, "كلمة المرور لا تتطابق مع تأكيد كلمة المرور")


                        && Util.checkTextViewValidation(edtMobile, "من فضلك أدخل رقم الجوال")
                /* && Util.checkMobileValidation(edtMobile, mobilevalidationlength, "من فضلك أدخل رقم الجوال")*/;
            }
            /* && Util.checkTextViewValidation(gender, "اختر صنف");*/

        } else {

            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {

                isAllConditionFulfilled = Util.checkTextViewValidation(edtFirstName,
                        "Please write the first name")
                        && Util.checkTextViewValidation(edtLastName,
                        "Please write the last name")
                        && Util.checkTextViewValidation(edtEmail,
                        "Please enter a working email address")
                        && Util.isEmailValid(edtEmail)
                        && Util.checkTextViewValidation(edtPassword, "Please type your password")
                        && Util.checkTextViewValidation(edtConfirmPassword,
                        "Please enter the password") &&
                        Util.isPasswordLengthCorrect(edtPassword, "Password must be at least 7 digits")
                        && Util.isPasswordMatched(edtPassword, edtConfirmPassword,
                        "The password does not match the first word");

            } else {

                isAllConditionFulfilled = Util.checkTextViewValidation(edtFirstName, "برجاء كتابة الاسم الاول")
                        && Util.checkTextViewValidation(edtLastName, "برجاء كتابة الاسم الاخير")
                        && Util.checkTextViewValidation(edtEmail, "الرجاء إدخال بريد إلكتروني صحيح")
                        && Util.isEmailValid(edtEmail)
                        && Util.checkTextViewValidation(edtPassword, "برجاء كتابة كلمة السر")
                        && Util.checkTextViewValidation(edtConfirmPassword, "برجاء ادخال كود التأكيد") && Util.isPasswordLengthCorrect(edtPassword, "كلمة السر يجب أن لا تقل عن 7 أرقام")
                        && Util.isPasswordMatched(edtPassword, edtConfirmPassword, "كلمة المرور لا تتطابق مع الكلمة الاولى");
            }


          /*  && Util.checkTextViewValidation(edtMobile, "من فضلك أدخل رقم الجوال")
            && Util.checkMobileValidation(edtMobile, mobilevalidationlength, "من فضلك أدخل رقم الجوال");*/


        }


        if (!isAllConditionFulfilled) {
            if (Util.view_final != null) {

                Util.view_final.requestFocus();
            }


            return;
        }

       /* if(!edtMobile.getText().toString().isEmpty()) {
            opengetOtpDialog();

        }
        else {*/

        makeupgradeSignUp();
        // }

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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void sendOtp() {

        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Map<String, String> map1 = new HashMap<>();
        map1.put("mobile", celcode.get(selctedposition) + edtMobile.getText().toString());
        map1.put("resend", "0");


        Call<OtpResponse> call = apiService.sendotp(map1);
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().success) {


                        Toast.makeText(getContext(), "تم ارسال الكود بنجاح", Toast.LENGTH_SHORT).show();


                        DialogFragment prFrag = new Otp_Verificationfragment();
                        Bundle bund = new Bundle();
                        bund.putString("flag", value);
                        bund.putString("firstname", edtFirstName.getText().toString());
                        bund.putString("lastname", edtLastName.getText().toString());
                        bund.putString("email", edtEmail.getText().toString());
                        bund.putString("password", edtPassword.getText().toString());
                        bund.putString("mobile", celcode.get(selctedposition) + edtMobile.getText().toString());
                        bund.putString("gender", /*"1"*/ String.valueOf(selectedgender));

                        prFrag.setArguments(bund);

                        prFrag.setTargetFragment(Signup.this, 1001);
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


    public void makeupgradeSignUp() {
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


        map1.put("firstname", edtFirstName.getText().toString());
        map1.put("lastname", edtLastName.getText().toString());
        map1.put("email", edtEmail.getText().toString());
        if (!edtMobile.getText().toString().isEmpty()) {
            map1.put("mobilenumber", celcode.get(selctedposition) + edtMobile.getText().toString());
        } else {
            map1.put("mobilenumber", "");

        }
        map1.put("password", edtPassword.getText().toString());


        map1.put("gender", String.valueOf(selectedgender));
        map1.put("type", "");
        map1.put("account_id", String.valueOf(selectedgender));
        map1.put("device_used", "android");

        mainparam.put("param", map1);


        Call<SignUpResponse> call;
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
            call = apiService.getUpgradedSignUp(mainparam);
        } else {
            call = apiService.getUpgradedSignUpEnglish(mainparam);
        }
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().response != null && response.body().response.equals("success")) {
                        if (response.body().data.mobilenumber != null) {
                            moengagesignupevent(response.body().data.firstname,
                                    response.body().data.lastname,
                                    response.body().data.email,

                                    response.body().data.mobilenumber.toString(),

                                    response.body().data.gender,
                                    "3434344"
                            );
                        } else {
                            moengagesignupevent(response.body().data.firstname,
                                    response.body().data.lastname,
                                    response.body().data.email,

                                   " ",

                                    response.body().data.gender,
                                    "3434344"
                            );

                        }
                        // Toast.makeText(getContext(), "اشترك بنجاح", Toast.LENGTH_SHORT).show();


                        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        Fragment prFrag = new LoginNewFragment();

                        Bundle bundle = new Bundle();
                        bundle.putString("email", edtEmail.getText().toString());
                        bundle.putString("password", edtPassword.getText().toString());
                        bundle.putString("flag", value);


                        prFrag.setArguments(bundle);


                        getFragmentManager()
                                .beginTransaction().addToBackStack(null)
                                .replace(R.id.profileContainer, prFrag)
                                .commit();

                    } else {
                        parentDialogListener.hideProgressDialog();
                        Toast.makeText(getContext(), response.body().message, Toast.LENGTH_SHORT).show();


                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()) == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void moengagesignupevent(String firstname, String lastname, String email, String mobilenumber, String gender, String type) {
        /* firstName, lastName, email, mobileNumber, gender, type*/
        Properties properties = new Properties();

        properties.addAttribute("firstName", firstname);
        properties.addAttribute("lastName", lastname);
        properties.addAttribute("email", email);
        properties.addAttribute("mobileNumber", mobilenumber);
        properties.addAttribute("gender", gender);
        properties.addAttribute("type", type);

        MoEHelper.getInstance(getApplicationContext()).trackEvent("Signup", properties);
    }

    public void makeupgradeSignUpwithfacebok(String firstname, String lastname, String email, String id) {
        parentDialogListener.showProgressDialog();
/*
        {"param":{"firstname":"abhishek","lastname":"srivastava","email":"abhishek@unyscapegmail.com",
                "mobilenumber":"+918285785213","password":"admin@123","gender":1,"type":"","account_id":"","device_used":"ios"}}*/

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Map<Object, Object> mainparam = new HashMap<>();

        Map<String, String> map1 = new HashMap<>();


        map1.put("firstname", firstname);
        map1.put("lastname", lastname);
        map1.put("email", email);

        map1.put("mobilenumber", "");


        map1.put("password", "");


        map1.put("gender", "");
        map1.put("type", "facebook");
        map1.put("account_id", id);
        map1.put("device_used", "android");

        mainparam.put("param", map1);


        Call<SignUpResponse> call;
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
            call = apiService.getUpgradedSignUp(mainparam);
        } else {
            call = apiService.getUpgradedSignUpEnglish(mainparam);
        }
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if ((response.body().response.equals("success"))) {
                        moengagesignupevent(response.body().data.firstname,
                                response.body().data.lastname,
                                response.body().data.email,
                                response.body().data.mobilenumber.toString(),
                                response.body().data.gender,
                                "facebook"
                        );

                        // Toast.makeText(getContext(), "اشترك بنجاح", Toast.LENGTH_SHORT).show();


                        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        Fragment prFrag = new LoginNewFragment();


                        Bundle bundle = new Bundle();
                        bundle.putString("email", email);
                        bundle.putString("password", "");
                        bundle.putString("flag", "fromfacebook");


                        prFrag.setArguments(bundle);


                        getFragmentManager()
                                .beginTransaction()/*.addToBackStack(null)*/
                                .replace(R.id.profileContainer, prFrag)
                                .commit();


                    } else {
                        parentDialogListener.hideProgressDialog();
                        //Toast.makeText(getContext(), response.body().message, Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        Fragment prFrag = new LoginNewFragment();


                        Bundle bundle = new Bundle();
                        bundle.putString("email", email);
                        bundle.putString("password", "");
                        bundle.putString("flag", "fromfacebook");


                        prFrag.setArguments(bundle);


                        getFragmentManager()
                                .beginTransaction()/*.addToBackStack(null)*/
                                .replace(R.id.profileContainer, prFrag)
                                .commit();


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
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
            call = apiService.getUpgradedSignUp(mainparam);
        } else {
            call = apiService.getUpgradedSignUpEnglish(mainparam);
        }
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if ((response.body().response.equals("success"))) {

                        moengagesignupevent(response.body().data.firstname,
                                response.body().data.lastname,
                                response.body().data.email,
                                response.body().data.mobilenumber.toString(),
                                response.body().data.gender,
                                "google"
                        );

                        // Toast.makeText(getContext(), "اشترك بنجاح", Toast.LENGTH_SHORT).show();


                        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        Fragment prFrag = new LoginNewFragment();


                        Bundle bundle = new Bundle();
                        bundle.putString("email", email);
                        bundle.putString("password", "");
                        bundle.putString("flag", "fromgoogle");
                        bundle.putString("id", id);


                        prFrag.setArguments(bundle);


                        getFragmentManager()
                                .beginTransaction()/*.addToBackStack(null)*/
                                .replace(R.id.profileContainer, prFrag)
                                .commit();


                    } else {
                        parentDialogListener.hideProgressDialog();
                        //Toast.makeText(getContext(), response.body().message, Toast.LENGTH_SHORT).show();
                        signOut();
                        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        Fragment prFrag = new LoginNewFragment();


                        Bundle bundle = new Bundle();
                        bundle.putString("email", email);
                        bundle.putString("password", "");
                        bundle.putString("flag", "fromgoogle");
                        bundle.putString("id", id);


                        prFrag.setArguments(bundle);


                        getFragmentManager()
                                .beginTransaction()/*.addToBackStack(null)*/
                                .replace(R.id.profileContainer, prFrag)
                                .commit();


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

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(getApplicationContext(),"You have been Logout",Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void opengetOtpDialog() {

        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        alertDialogBuilder.setMessage("التأكد من صحة رقم الجوال");
        //alertDialogBuilder.setTitle("التأكد من صحة رقم الجوال");


        alertDialogBuilder.setPositiveButton("الحصول على الكود",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        if (!edtMobile.getText().toString().isEmpty()) {

                            sendOtp();
                        } else {


                        }

                    }
                });

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
        // LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        // positiveButtonLL.gravity = Gravity.CENTER;
        //positiveButton.setLayoutParams(positiveButtonLL);
        positiveButton.setGravity(Gravity.CENTER);

        final Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setVisibility(View.GONE);


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
        } else {

            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);

        }

        if (requestCode == 1003) {
            twitteremail = data.getStringExtra("email");
            makeupgradeSignUpwithtwitter(twitterusername, twitterlastname, twitteremail, twitterId);

            //loginUpgradedProceedwithtwitter(email," ","twitter",twitterId);
        } else {
            mTwitterBtn.onActivityResult(requestCode, resultCode, data);


        }


    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.e("TAG", "EmailId" + completedTask.getResult().getEmail()); //arpan@unyscape.com
            Log.e("TAG", "EmailId" + completedTask.getResult().getGivenName()); //Arpan
            Log.e("TAG", "EmailId" + completedTask.getResult().getFamilyName()); //Bhatia
            Log.e("TAG", "EmailId" + completedTask.getResult().getDisplayName());//Arpan Bhatia
            Log.e("TAG", "EmailId" + completedTask.getResult().getId()); //118072629512299493582

            //Toast.makeText(getContext(), completedTask.getResult().getEmail(),Toast.LENGTH_LONG).show();

            makeupgradeSignUpwithgoogle(completedTask.getResult().getGivenName(), completedTask.getResult().getFamilyName(),
                    completedTask.getResult().getEmail(), completedTask.getResult().getId());

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("TAG", "signInResult:failed code=" + e.getStatusCode());
            // updateUI(null);
        }
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
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("ar")) {
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

                        if ((response.body().response.equals("success"))) {

                            moengagesignupevent(response.body().data.firstname,
                                    response.body().data.lastname,
                                    response.body().data.email,
                                    response.body().data.mobilenumber.toString(),
                                    response.body().data.gender,
                                    "twitter"
                            );

                            // Toast.makeText(getContext(), "اشترك بنجاح", Toast.LENGTH_SHORT).show();


                            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            Fragment prFrag = new LoginNewFragment();


                            Bundle bundle = new Bundle();
                            bundle.putString("email", twitteremail);
                            bundle.putString("password", "");
                            bundle.putString("flag", "fromtwitter");
                            bundle.putString("id", id);


                            prFrag.setArguments(bundle);


                            getFragmentManager()
                                    .beginTransaction()/*.addToBackStack(null)*/
                                    .replace(R.id.profileContainer, prFrag)
                                    .commit();


                        } else {
                            parentDialogListener.hideProgressDialog();
                            //Toast.makeText(getContext(), response.body().message, Toast.LENGTH_SHORT).show();
                            signOut();
                            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            Fragment prFrag = new LoginNewFragment();


                            Bundle bundle = new Bundle();
                            bundle.putString("email", email);
                            bundle.putString("password", "");
                            bundle.putString("flag", "fromgoogle");
                            bundle.putString("id", id);


                            prFrag.setArguments(bundle);


                            getFragmentManager()
                                    .beginTransaction()/*.addToBackStack(null)*/
                                    .replace(R.id.profileContainer, prFrag)
                                    .commit();


                        }


                    } else {
                        parentDialogListener.hideProgressDialog();


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


}
