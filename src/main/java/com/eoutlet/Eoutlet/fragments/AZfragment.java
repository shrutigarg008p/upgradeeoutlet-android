package com.eoutlet.Eoutlet.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.adpters.AZAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.listener.ViewListener4;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.Alphanumeric;
import com.eoutlet.Eoutlet.pojo.BrandName;
import com.eoutlet.Eoutlet.pojo.BrandNameDetail;
import com.eoutlet.Eoutlet.utility.Constants;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;


public class AZfragment extends Fragment {
    private RecyclerView az_recycler;
    private AZAdapter azAdapter;
    public static TextView toolbar_bedgetextbrand, tooltext;
    private ExecuteFragment execute;
    private Context context;
    private Toolbar toolbar1;
    private boolean _hasLoadedOnce = false;
    private View view, toolbarbeg;
    private int totalrowsize = 0;
    private ArrayList<String> alphabetic = new ArrayList<>();
    private ProgressDialog progressDialog;
    private FirebaseAnalytics mFirebaseAnalytics;
    private HashMap<String, List<BrandNameDetail>> allnames = new HashMap<>();

    private ImageView searchImage, backarrow;
    public ParentDialogsListener parentDialogListener;
    private String Local = " ";

    // TODO: Rename and change types and number of parameters
    public static AZfragment newInstance(String param1, String param2) {
        AZfragment fragment = new AZfragment();
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
        getBrandName();
        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            Local = "en";
            view = inflater.inflate(R.layout.fragment_a_zfragment_english, container, false);
        } else {
            Local="ar";
            view = inflater.inflate(R.layout.fragment_a_zfragment, container, false);
        }
        context = getActivity();
        execute = (MainActivity) getActivity();
        System.out.println("context.... " + context);
        az_recycler = view.findViewById(R.id.az_recycler);
        toolbar1 = view.findViewById(R.id.toolbar);

        //toolbarbeg = toolbar.findViewById(R.id.toolbarbag);
        searchImage = toolbar1.findViewById(R.id.serachbar);


        toolbar1 = view.findViewById(R.id.toolbar);
        searchImage = toolbar1.findViewById(R.id.serachbar);
        backarrow = toolbar1.findViewById((R.id.backarrow));
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment prFrag = new SearchResultFragment();
                Bundle databund = new Bundle();


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .add(R.id.profileContainer, prFrag)
                        .commit();


            }
        });

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

       /* if (MySharedPreferenceClass.getDeeplinkingPage(getApplicationContext()) != null) {
            if (MySharedPreferenceClass.getDeeplinkingPage(getApplicationContext()).equalsIgnoreCase("brand")){
                MySharedPreferenceClass.setDeeplinkingNotification(getContext(), null);
                MySharedPreferenceClass.setDeeplinkingId(getContext(), null);
                MySharedPreferenceClass.setDeeplinkingName(getContext(), null);
                MySharedPreferenceClass.setDeeplinkingPage(getContext(), null);
            }

        }*/

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

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

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void getBrandName() {
//        parentDialogListener.showProgressDialog();
        showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        /*https://upgrade.eoutlet.com/rest/V1/api/brandlist*/
        //Call<BrandName> call = apiService.getHomeBrandDetail(Constants.HOST_LINK + Constants.BASE_LINK + "brandcatapi.php");

        Call<BrandName> call = apiService.getHomeBrandDetail(Constants.UPGRADED_HOST_LINK + Constants.UPGRADED_BASE_LINK + "brandlist");
        call.enqueue(new Callback<BrandName>() {
            @Override
            public void onResponse(Call<BrandName> call, Response<BrandName> response) {


                if (response.body() != null) {
//                    parentDialogListener.hideProgressDialog();
                    hideProgressDialog();
                    System.out.println("responsebrand...." + response.body().toString());
                    List<Alphanumeric> alphanumericlist = new ArrayList<>();


                    alphanumericlist = response.body().data;


                    if (response.body().data.size() > 1) {


                        for (int i = 0; i < response.body().data.size(); i++) {


                            alphabetic.add(response.body().data.get(i).alphabetic);


                        }


                        if (alphabetic.size() == alphanumericlist.size()) {


                            for (int i = 0; i < alphabetic.size(); i++) {


                                allnames.put(alphabetic.get(i), alphanumericlist.get(i).data);


                            }

                        }


                        for (int i = 0; i < alphabetic.size(); i++) {


                            for (int j = 0; j < allnames.get(alphabetic.get(i)).size(); j++) {

                                totalrowsize++;


                            }


                        }

                        totalrowsize = totalrowsize + alphabetic.size();

                        System.out.println(totalrowsize + "dsfsdfdsfdsfdsfsdfsdfsdfs");


                        initRecycler6(response.body().data, alphabetic);


                    } else {
//                        parentDialogListener.hideProgressDialog();
                        hideProgressDialog();

                    }
                } else {
//                    parentDialogListener.hideProgressDialog();
                    hideProgressDialog();

                }


            }


            @Override
            public void onFailure(Call<BrandName> call, Throwable t) {
//                parentDialogListener.hideProgressDialog();
                hideProgressDialog();
                Log.e(TAG, t.toString());

                if (Local.equals("ar")) {

                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void initRecycler6(final List<Alphanumeric> cat6, ArrayList<String> alphabetic) {

        az_recycler.setHasFixedSize(true);
        az_recycler.setNestedScrollingEnabled(false);
        az_recycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));


        az_recycler.setItemAnimator(new DefaultItemAnimator());
        // use a linear layout manager


        // specify an adapter (see also next example)
        azAdapter = new AZAdapter(getContext(), cat6, alphabetic, allnames, totalrowsize, new ViewListener4() {
            @Override
            public void onClick(int position, View view, String name, String ids) {
                int id = view.getId();


                switch (id) {

                    case R.id.parentLayout://button for message
                        if (!ids.equals("999")) {
                            Fragment prFrag = new ProductList();
                            Bundle databund = new Bundle();
                            databund.putInt("productId", Integer.parseInt(ids));
                            databund.putString("name", name);
                            databund.putString("previouspage","FromBrandScreen");
                            databund.putString("fromwhere", "frombrand");
                            prFrag.setArguments(databund);


                            getFragmentManager()
                                    .beginTransaction().addToBackStack(null)
                                    .replace(R.id.brandcontainer, prFrag)
                                    .commit();

                            firebase_click_brand(name,ids);


                        }

                        break;
                }
            }
        }


        );
        az_recycler.setAdapter(azAdapter);
        az_recycler.setNestedScrollingEnabled(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        _hasLoadedOnce = false;
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if(MainActivity.isaddressempty==true){
            MainActivity.isaddressempty = false;
        }
        if (visible && isResumed()) {

            searchImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Fragment prFrag = new SearchResultFragment();
                    Bundle databund = new Bundle();


                    getFragmentManager()
                            .beginTransaction().addToBackStack(null)
                            .add(R.id.brandcontainer, prFrag)
                            .commit();


                }
            });


        }

        if (visible && isResumed() && !_hasLoadedOnce) {


            //getBrandName();
            _hasLoadedOnce = true;

        }
    }


    private void showProgressDialog() {
        progressDialog = ProgressDialog.show(getActivity(), "Null", "Null", false, false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progress_bar);
        progressDialog.setCanceledOnTouchOutside(false);
    }


    private void hideProgressDialog() {
        try {
            progressDialog.dismiss();
        } catch (Exception e) {

        }

    }
    public void firebase_click_brand(String promotionname,String promotionid) {
        Bundle bundle = new Bundle();

        bundle.putString(FirebaseAnalytics.Param.PROMOTION_NAME, promotionname);
        bundle.putString(FirebaseAnalytics.Param.PROMOTION_ID, promotionid);
        //bundle.putString(FirebaseAnalytics.Param.ITEM_LOCATION_ID, Locale.getDefault().getCountry());
        mFirebaseAnalytics.logEvent("on_brand_selection", bundle);


    }

}

