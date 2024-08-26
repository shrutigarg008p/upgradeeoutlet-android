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

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.adpters.SavedCardAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.DeleteSavedCardResponse;
import com.eoutlet.Eoutlet.pojo.SavedCardData;
import com.eoutlet.Eoutlet.pojo.SavedCardResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SavedCardFragment extends Fragment {
    View view;
    String Locale;
    private ImageView searchImage, backarrow;
    private Toolbar toolbar1;
    RecyclerView savedCardsRecyclerView;
    private SavedCardAdapter savedCardAdapter;
    private ParentDialogsListener parentDialogListener;
    private List<SavedCardData> savedCardData = new ArrayList<>();
    LinearLayout noSavedCard;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                Locale = "ar";
                view = inflater.inflate(R.layout.fragment_saved_card_arabic, container, false);
            } else {
                Locale = "en";
                view = inflater.inflate(R.layout.fragment_saved_card, container, false);
            }
        } else {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_saved_card_arabic, container, false);
        }
        savedCardsRecyclerView = view.findViewById(R.id.savedCardsRecyclerView);
        noSavedCard = view.findViewById(R.id.noSavedCard);
        toolbar1 = view.findViewById(R.id.toolbar);
        searchImage = toolbar1.findViewById(R.id.serachbar);
        backarrow = toolbar1.findViewById((R.id.backarrow));

        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment prFrag = new SearchResultFragment();
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

        savedCardsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getSavedCardList();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            parentDialogListener = (ParentDialogsListener) context;

        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " Activity's Parent should be Parent Activity");
        }
        super.onAttach(context);
    }


    private void getSavedCardList() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Call<SavedCardResponse> call = Locale == "ar" ? apiService.getSavedCards("https://upgrade.eoutlet.com/rest/ar/V1/api/storedsavecards/" + MySharedPreferenceClass.getMyUserId(getContext())) : apiService.getSavedCards("https://upgrade.eoutlet.com/rest/en/V1/api/storedsavecards/" + MySharedPreferenceClass.getMyUserId(getContext()));
        call.enqueue(new Callback<SavedCardResponse>() {
            @Override
            public void onResponse(Call<SavedCardResponse> call, Response<SavedCardResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().msg.equalsIgnoreCase("success")) {
                        Log.e("saved card list", "success");
                        savedCardData = response.body().data;
                        if (savedCardData.size() > 0) {
                            savedCardsRecyclerView.setVisibility(View.VISIBLE);
                            noSavedCard.setVisibility(View.GONE);
                            savedCardAdapter = new SavedCardAdapter(getContext(), savedCardData, SavedCardFragment.this);
                            savedCardsRecyclerView.setAdapter(savedCardAdapter);
                            savedCardAdapter.notifyDataSetChanged();
                        } else {
                            savedCardsRecyclerView.setVisibility(View.GONE);
                            noSavedCard.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Log.e("saved card list", "fail");
                        Toast.makeText(getContext(), response.body().msg, Toast.LENGTH_LONG).show();
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<SavedCardResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
            }
        });
    }

    public void deleteCard(String id) {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<DeleteSavedCardResponse> call = Locale == "ar" ? apiService.deleteSavedCard("https://upgrade.eoutlet.com/rest/ar/V1/api/deletestoredcard/" + id) : apiService.deleteSavedCard("https://upgrade.eoutlet.com/rest/en/V1/api/deletestoredcard/" + id);

        //Call<DeleteSavedCardResponse> call = Locale == "ar" ? apiService.deleteSavedCard("https://upgrade.eoutlet.com/rest/ar/V1/api/deletestoredcard/2") : apiService.deleteSavedCard("https://upgrade.eoutlet.com/rest/en/V1/api/deletestoredcard/2");
        call.enqueue(new Callback<DeleteSavedCardResponse>() {
            @Override
            public void onResponse(Call<DeleteSavedCardResponse> call, Response<DeleteSavedCardResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().message.equalsIgnoreCase("success")) {
                        Log.e("delete Saved card", "success");
                        Toast.makeText(getContext(), Locale == "ar" ? "Card Deleted Successfully" : "Card Deleted Successfully", Toast.LENGTH_LONG).show();
                        getSavedCardList();
                    } else {
                        Log.e("delete Saved card", "success");
                        Toast.makeText(getContext(), response.body().message, Toast.LENGTH_LONG).show();
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<DeleteSavedCardResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
            }
        });
    }
}