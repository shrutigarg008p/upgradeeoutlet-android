package com.eoutlet.Eoutlet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.adpters.ReturnItemListViewClickAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.ReturnItemViewClickResponse;
import com.eoutlet.Eoutlet.pojo.ReturnItemViewClickReturnItem;
import com.eoutlet.Eoutlet.utility.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnItemListViewClickFragment extends Fragment {
    RecyclerView returnItemListViewRecyclerView;
    ReturnItemListViewClickAdapter returnItemListViewClickAdapter;
    public ParentDialogsListener parentDialogListener;
    private List<ReturnItemViewClickReturnItem> returnItemViewClickReturnItems = new ArrayList<>();
    String rma_id, Locale;
    View view;
    private ImageView searchImage, backarrow;
    private Toolbar toolbar1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                Locale = "ar";
                view = inflater.inflate(R.layout.fragment_return_item_list_view_click_arabic, container, false);
            } else {
                Locale = "en";
                view = inflater.inflate(R.layout.fragment_return_item_list_view_click, container, false);
            }
        } else {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_return_item_list_view_click_arabic, container, false);
        }
        returnItemListViewRecyclerView = view.findViewById(R.id.returnItemListViewRecyclerView);
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
        returnItemListViewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rma_id = getArguments().getString("rma_id");
        getReturnItemView(rma_id);
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

    private void getReturnItemView(String rma_id) {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<ReturnItemViewClickResponse> call = Locale == "ar" ? apiService.getReturnItemViewClickDetails(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "returnview/" + rma_id) : apiService.getReturnItemViewClickDetails(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "returnview/" + rma_id);
        call.enqueue(new Callback<ReturnItemViewClickResponse>() {
            @Override
            public void onResponse(Call<ReturnItemViewClickResponse> call, Response<ReturnItemViewClickResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().getResponse().equals("success")) {
                        Log.e("getReturnItemView", "success");
                        parentDialogListener.hideProgressDialog();
                        returnItemViewClickReturnItems = response.body().getData().getReturnitems();
                        returnItemListViewClickAdapter = new ReturnItemListViewClickAdapter(getContext(), returnItemViewClickReturnItems);
                        returnItemListViewRecyclerView.setAdapter(returnItemListViewClickAdapter);
                        returnItemListViewClickAdapter.notifyDataSetChanged();
                    } else {
                        parentDialogListener.hideProgressDialog();
                        Log.e("getReturnItemView", "failed");
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnItemViewClickResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
    }
}