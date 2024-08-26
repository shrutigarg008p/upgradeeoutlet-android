package com.eoutlet.Eoutlet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.adpters.FaqAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.UpgradeFaqData;
import com.eoutlet.Eoutlet.pojo.UpgradeFaqResponse;
import com.eoutlet.Eoutlet.utility.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FAQFragment extends Fragment {
    RecyclerView faqRecycler;
    FaqAdapter faqAdapter;
    public ParentDialogsListener parentDialogListener;
    View view;
    private ImageView logo;
    String Locale;
    private List<UpgradeFaqData> faqData = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_faq, container, false);
        logo = view.findViewById(R.id.logo);

        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                Locale = "ar";
            } else {
                Locale = "en";
                logo.setImageDrawable(getContext().getDrawable(R.drawable.logo_english_black));

            }
        } else {
            Locale = "ar";
        }
        faqRecycler = view.findViewById(R.id.faqRecycler);

        faqRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        faqRecycler.hasFixedSize();
        getFaq();
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


    private void getFaq() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<UpgradeFaqResponse> call = Locale == "ar" ? apiService.getFaq(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "getfaq") : apiService.getFaq(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "getfaq");
        call.enqueue(new Callback<UpgradeFaqResponse>() {
            @Override
            public void onResponse(Call<UpgradeFaqResponse> call, Response<UpgradeFaqResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().response.equals("success")) {
                        Log.e("Faq request", "success");
                        faqData = response.body().data;
                        faqAdapter = new FaqAdapter(getContext(), faqData);
                        faqRecycler.setAdapter(faqAdapter);
                        faqAdapter.notifyDataSetChanged();
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
            public void onFailure(Call<UpgradeFaqResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

}
