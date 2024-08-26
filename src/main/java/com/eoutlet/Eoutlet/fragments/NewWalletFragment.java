package com.eoutlet.Eoutlet.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.PaymentGateway.PaymentActivity;

import com.eoutlet.Eoutlet.PaymentGateway.WalletAddActivty;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.adpters.FilterCatagoryListAdapter;
import com.eoutlet.Eoutlet.adpters.WalletAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.GetToken;
import com.eoutlet.Eoutlet.pojo.Transaction;
import com.eoutlet.Eoutlet.pojo.UpgradedTransaction;
import com.eoutlet.Eoutlet.pojo.UpgradedWalletHistory;
import com.eoutlet.Eoutlet.pojo.WalletHistory;
import com.eoutlet.Eoutlet.utility.Constants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;



public class NewWalletFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private String sdkToken;
    public ParentDialogsListener parentDialogListener;


    View view;
    private int totalprice ;
    String fortid;

    private EditText edtaddedamount;
    private TextView btnaddamount;

    private TextView wallettext;
    private RecyclerView walletdetailrecyclerview;
    private WalletAdapter mAdapter;

    private ImageView searchImage,backarrow;
    private Toolbar toolbar1;
    private String Locale = " ";

    public NewWalletFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewWalletFragment newInstance(String param1, String param2) {
        NewWalletFragment fragment = new NewWalletFragment();
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //Toast.makeText(getContext(),"dsfdsfds",Toast.LENGTH_SHORT).show();
        try {

            parentDialogListener = (ParentDialogsListener) activity;

        } catch (ClassCastException e) {

            throw new ClassCastException(activity.toString()
                    + " Activity's Parent should be Parent Activity");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment


        if(MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
            Locale = "en";
            view = inflater.inflate(R.layout.fragment_new_wallet_english, container, false);
        }
        else {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_new_wallet, container, false);
        }

        edtaddedamount = view.findViewById(R.id.edtamouttobeadded);
        btnaddamount = view.findViewById(R.id.btnAmountadded);
        wallettext = view.findViewById(R.id.walletamount);
        walletdetailrecyclerview = view.findViewById(R.id.walletdetail_recycler);

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



        getWalletHistory();

        btnaddamount.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (edtaddedamount.getText().toString().trim().length() > 0 && Integer.parseInt(edtaddedamount.getText().toString())>0) {

                    Fragment prFrag = new WalletPaymentOptions();
                    Bundle bundle = new Bundle();
                    bundle.putString("addamount", edtaddedamount.getText().toString().trim());

                    prFrag.setArguments(bundle);

                    getFragmentManager()
                            .beginTransaction().addToBackStack(null)
                            .add(NewWalletFragment.this.getId(), prFrag)
                            .commit();
                    //addmoneytocart(edtaddedamount.getText().toString());


                } else {

                    // Toast.makeText(getContext(), "Please fill an amount", Toast.LENGTH_SHORT).show();

                }

            }
        });


        return view;
    }


    public void getWalletHistory()
    {
/*https://upgrade.eoutlet.com/rest/V1/api/getwallet/83188*/
        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Map<String, String> map1 = new HashMap<>();
        map1.put("customer_id",MySharedPreferenceClass.getMyUserId(getContext()));
        Call< UpgradedWalletHistory > call = Locale == "en" ?
                apiService.getUpgradedwallethistory("https://upgrade.eoutlet.com/rest/en/V1/api/getwallet/" +
                        MySharedPreferenceClass.getMyUserId(getContext())) : apiService.getUpgradedwallethistory("https://upgrade.eoutlet.com/rest/ar/V1/api/getwallet/" +
                MySharedPreferenceClass.getMyUserId(getContext()));
       //Call<UpgradedWalletHistory> call = apiService.getUpgradedwallethistory("https://upgrade.eoutlet.com/rest/V1/api/getwallet/"+MySharedPreferenceClass.getMyUserId(getContext()));
        call.enqueue(new Callback<UpgradedWalletHistory>() {
            @Override
            public void onResponse(Call<UpgradedWalletHistory> call, Response<UpgradedWalletHistory> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();

                    wallettext.setText(MySharedPreferenceClass.getSelectedCurrencyName(getContext())+" "+
                            Math.round((response.body().data.amount)*MySharedPreferenceClass.getSelectedCurrencyRate(getContext())));

                    MySharedPreferenceClass.setWalletAmount(getContext(),response.body().data.amount.toString());





                    initrecycler(response.body().data.transaction);




                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<UpgradedWalletHistory> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void initrecycler(List<UpgradedTransaction> transaction)
    {


        walletdetailrecyclerview.setHasFixedSize(true);

        // use a linear layout manager

        walletdetailrecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));



        mAdapter = new WalletAdapter(view.getContext(),transaction);
        walletdetailrecyclerview.setAdapter(mAdapter);

    }




    public void addmoneytocart(String amount) {

        getToken();
    }


    public void getToken() {
        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Map<String, String> map1 = new HashMap<>();
        map1.put("device_id", " ");

        Call<GetToken> call = apiService.getTokenResponse(map1);
        call.enqueue(new Callback<GetToken>() {
            @Override
            public void onResponse(Call<GetToken> call, Response<GetToken> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();

                    sdkToken = response.body().sdkToken;

                    Intent i = new Intent(getContext(), WalletAddActivty.class);
                    Bundle bundle = new Bundle();


                    bundle.putString("totalprice", edtaddedamount.getText().toString());
                    bundle.putString("token", sdkToken);


                    i.putExtras(bundle);


                    startActivity(i);




                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<GetToken> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
                parentDialogListener.hideProgressDialog();
            }
        });
    }






}