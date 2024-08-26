package com.eoutlet.Eoutlet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.GetCategoryCode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

public class ShopByFragment extends Fragment {
    public ParentDialogsListener parentDialogListener;
    View view;
    TextView shopwomen, shopmen, shopkids, shopchild;
    FrameLayout shopByApplyButton;
    LinearLayout shopByContainer;
    String selectedCategory = "";
    private String menId, womenId, babiesId, childerenId;
    //    function to select one category
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeColorBack(shopwomen);
            changeColorBack(shopmen);
            changeColorBack(shopkids);
            changeColorBack(shopchild);
            switch (v.getId()) {
                case R.id.shopwomen:
                    changeBackgroundColor(shopwomen);
                    selectedCategory = womenId;
                    break;
                case R.id.shopmen:
                    changeBackgroundColor(shopmen);
                    selectedCategory = menId;
                    break;
                case R.id.shopkids:
                    changeBackgroundColor(shopkids);
                    selectedCategory = babiesId;
                    break;
                case R.id.shopchild:
                    changeBackgroundColor(shopchild);
                    selectedCategory = childerenId;
                    break;

            }

        }
    };
    private ImageView searchImage, backarrow;
    private Toolbar toolbar1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                view = inflater.inflate(R.layout.fragment_shop_by_arabic, container, false);
            } else {
                view = inflater.inflate(R.layout.fragment_shop_by, container, false);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_shop_by_arabic, container, false);
        }
        shopwomen = view.findViewById(R.id.shopwomen);
        shopmen = view.findViewById(R.id.shopmen);
        shopkids = view.findViewById(R.id.shopkids);
        shopchild = view.findViewById(R.id.shopchild);
        shopByApplyButton = view.findViewById(R.id.shopByApplyButton);
        shopByContainer = view.findViewById(R.id.shopByContainer);
        shopByContainer.setVisibility(View.GONE);
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
        getUpgradedLatestversionCode();
        shopwomen.setOnClickListener(listener);
        shopmen.setOnClickListener(listener);
        shopkids.setOnClickListener(listener);
        shopchild.setOnClickListener(listener);
        shopByApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySharedPreferenceClass.setCatId(getApplicationContext(), selectedCategory);
                getActivity().recreate();
                ((MainActivity) getContext()).navigationView.setSelectedItemId(R.id.home);
            }
        });

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

    // function to get category Name
    private void getUpgradedLatestversionCode() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Call<GetCategoryCode> call = apiService.getUpgradedLatestAppVersion();
        call.enqueue(new Callback<GetCategoryCode>() {
            @Override
            public void onResponse(Call<GetCategoryCode> call, Response<GetCategoryCode> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().response.equals("success")) {
                        menId = response.body().menId;
                        womenId = response.body().womenId;
                        childerenId = response.body().childrenId;
                        babiesId = response.body().babiesId;
                        if (MySharedPreferenceClass.getCatId(getContext()) != null) {
                            selectedCategory = MySharedPreferenceClass.getCatId(getContext());
                            if (MySharedPreferenceClass.getCatId(getContext()).equals(womenId)) {
                                shopwomen.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_selectbtnbackground));
                            } else if (MySharedPreferenceClass.getCatId(getContext()).equals(menId)) {
                                shopmen.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_selectbtnbackground));
                            } else if (MySharedPreferenceClass.getCatId(getContext()).equals(babiesId)) {
                                shopkids.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_selectbtnbackground));
                            } else {
                                shopchild.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_selectbtnbackground));
                            }
                        }
                        shopByContainer.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCategoryCode> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
            }
        });
    }

    private void changeColorBack(TextView textView) {
        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_unselectbtnbackground));

    }

    private void changeBackgroundColor(TextView textView) {
        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_selectbtnbackground));
    }

}