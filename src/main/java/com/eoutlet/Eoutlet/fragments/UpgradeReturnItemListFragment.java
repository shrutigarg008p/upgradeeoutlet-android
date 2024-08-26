package com.eoutlet.Eoutlet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.adpters.UpgradeReturnItemListAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.UpgradeReturnItemListDetails;
import com.eoutlet.Eoutlet.pojo.UpgradeReturnItemListResponse;
import com.eoutlet.Eoutlet.pojo.UpgradeReturnOrderCancelResponse;
import com.eoutlet.Eoutlet.utility.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpgradeReturnItemListFragment extends Fragment {
    RecyclerView returnItemListRecyclerView;
    LinearLayout noReturnList;
    UpgradeReturnItemListAdapter upgradeReturnItemListAdapter;
    View view;
    public ParentDialogsListener parentDialogListener;
    String Locale;
    private List<UpgradeReturnItemListDetails> upgradeReturnItemListDetails = new ArrayList<>();
    private ImageView searchImage, backarrow;
    private Toolbar toolbar1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                Locale = "ar";
                view = inflater.inflate(R.layout.fragment_upgrade_return_item_list_arabic, container, false);
            } else {
                Locale = "en";
                view = inflater.inflate(R.layout.fragment_upgrade_return_item_list, container, false);
            }
        } else {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_upgrade_return_item_list_arabic, container, false);
        }
        returnItemListRecyclerView = view.findViewById(R.id.returnItemListRecyclerView);
        noReturnList = view.findViewById(R.id.noReturnList);
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
        returnItemListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getReturnItemsList();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            parentDialogListener = (ParentDialogsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " Activity's Parent should be Parent Activity");
        }
    }


    private void getReturnItemsList() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<UpgradeReturnItemListResponse> call = Locale == "ar" ? apiService.getReturnItemList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "returnlist/" + MySharedPreferenceClass.getMyUserId(getContext())) : apiService.getReturnItemList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "returnlist/" + MySharedPreferenceClass.getMyUserId(getContext()));
        call.enqueue(new Callback<UpgradeReturnItemListResponse>() {
            @Override
            public void onResponse(Call<UpgradeReturnItemListResponse> call, Response<UpgradeReturnItemListResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().getResponse().equals("success")) {
                        Log.e("getReturnItemsList", "success");
                        parentDialogListener.hideProgressDialog();
                        upgradeReturnItemListDetails = response.body().getData();
                        if (upgradeReturnItemListDetails.size() > 0) {
                            noReturnList.setVisibility(View.GONE);
                            returnItemListRecyclerView.setVisibility(View.VISIBLE);
                            upgradeReturnItemListAdapter = new UpgradeReturnItemListAdapter(getContext(), upgradeReturnItemListDetails, UpgradeReturnItemListFragment.this);
                            returnItemListRecyclerView.setAdapter(upgradeReturnItemListAdapter);
                            upgradeReturnItemListAdapter.notifyDataSetChanged();
                        } else {
                            noReturnList.setVisibility(View.VISIBLE);
                            returnItemListRecyclerView.setVisibility(View.GONE);
//                            Toast.makeText(getContext(), Locale == "ar" ? "ليس لديك أي عودة في \"عوداتي\" لك " : "You have no return in your My Returns!", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Log.e("getReturnItemsList", "failed");
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpgradeReturnItemListResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void doCancelReturnRequest(String rma_id) {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<UpgradeReturnOrderCancelResponse> call = apiService.upgradeReturnOrderCancel(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK + "returncancel/" + rma_id);
        call.enqueue(new Callback<UpgradeReturnOrderCancelResponse>() {
            @Override
            public void onResponse(Call<UpgradeReturnOrderCancelResponse> call, Response<UpgradeReturnOrderCancelResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().getResponse().equals("success")) {
                        Log.e("cancel return request", "success");
                        Toast.makeText(getContext(), "Cancel return request Successfully", Toast.LENGTH_LONG).show();
                        getReturnItemsList();
                    } else {
                        parentDialogListener.hideProgressDialog();
                        Log.e("cancel return request", "failed");
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpgradeReturnOrderCancelResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
    }
}

