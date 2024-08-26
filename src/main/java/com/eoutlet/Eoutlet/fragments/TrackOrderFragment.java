package com.eoutlet.Eoutlet.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.adpters.TrackOrderAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.OrderListItem;
import com.eoutlet.Eoutlet.pojo.UpgradeOrderListData;
import com.eoutlet.Eoutlet.pojo.UpgradeOrderListResponse;
import com.eoutlet.Eoutlet.utility.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrackOrderFragment extends Fragment {
    private RecyclerView recyclerView;
    LinearLayout noTrackOrderList;
    private Context context;
    private TrackOrderFragment trackOrderFragment;
    private TrackOrderAdapter trackOrderAdapter;
    private List<OrderListItem> customDataList = new ArrayList<>();
    private View rawView;
    private List<UpgradeOrderListData> upgradeOrderListData = new ArrayList<>();
    private List<UpgradeOrderListData> upgradeOrderListCompletedData = new ArrayList<>();
    public ParentDialogsListener parentDialogListener;
    private OnFragmentInteractionListener mListener;
    private ImageView backButton, serachbar;
    private Toolbar toolbar;
    String Locale;

    public TrackOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                Locale = "ar";
                rawView = inflater.inflate(R.layout.fragment_track_order_arabic, container, false);
            } else {
                Locale = "en";
                rawView = inflater.inflate(R.layout.fragment_track_order, container, false);
            }
        } else {
            Locale = "ar";
            rawView = inflater.inflate(R.layout.fragment_track_order_arabic, container, false);
        }
        recyclerView = rawView.findViewById(R.id.recyclertrack);
        noTrackOrderList = rawView.findViewById(R.id.noTrackOrderList);
        toolbar = rawView.findViewById(R.id.toolbar);
        serachbar = toolbar.findViewById(R.id.serachbar);
        backButton = toolbar.findViewById(R.id.backarrow);
        context = getActivity();
        trackOrderFragment = TrackOrderFragment.this;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.hasFixedSize();
        callOrderListApi();

        serachbar.setOnClickListener(new View.OnClickListener() {
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        return rawView;

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    private void callOrderListApi() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<UpgradeOrderListResponse> call = Locale == "ar" ? apiService.getUpgradeOrderList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "myorderlist/" + MySharedPreferenceClass.getMyUserId(getContext())) : apiService.getUpgradeOrderList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "myorderlist/" + MySharedPreferenceClass.getMyUserId(getContext()));
        //Call<UpgradeOrderListResponse> call = Locale == "ar" ? apiService.getUpgradeOrderList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "myorderlist/" + "83188") : apiService.getUpgradeOrderList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "myorderlist/" + "83188");
        call.enqueue(new Callback<UpgradeOrderListResponse>() {
            @Override
            public void onResponse(Call<UpgradeOrderListResponse> call, Response<UpgradeOrderListResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().getResponse().equals("success")) {
                        upgradeOrderListData = response.body().getData();
                        if (upgradeOrderListData.size() > 0) {
                            for (int i = 0; i < upgradeOrderListData.size(); i++) {
                                if (upgradeOrderListData.get(i).getStatus_code() != null) {
                                    if (upgradeOrderListData.get(i).getStatus_code().equalsIgnoreCase("complete")) {
                                        upgradeOrderListCompletedData.add(upgradeOrderListData.get(i));
                                    }
                                }
                            }
                        } else {
                            Log.e("upgradeOrderListData", "null data");
                        }
                        if (upgradeOrderListCompletedData.size() > 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            noTrackOrderList.setVisibility(View.GONE);
                            trackOrderAdapter = new TrackOrderAdapter(getContext(), upgradeOrderListCompletedData, trackOrderFragment);
                            recyclerView.setAdapter(trackOrderAdapter);
                            trackOrderAdapter.notifyDataSetChanged();
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            noTrackOrderList.setVisibility(View.VISIBLE);
                            Log.e("OrderListCompletedData", "null data");
                        }


                    } else {
                        parentDialogListener.hideProgressDialog();
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpgradeOrderListResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                Log.e("fail", t.getMessage());
            }
        });

    }
}
