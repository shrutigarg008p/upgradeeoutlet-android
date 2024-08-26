package com.eoutlet.Eoutlet.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.adpters.SellerAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.SellerProductList;
import com.eoutlet.Eoutlet.pojo.SellerResponse;
import com.eoutlet.Eoutlet.utility.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SellerPage extends Fragment {
    View view;
    RecyclerView sellerRecyclerView;
    private ImageView searchImage, backarrow, sellerImage;
    TextView sellerName, sellerEmail, sellerAddress, toolbarText, sellerRating;
    private Toolbar toolbar1;
    private SellerAdapter sellerAdapter;
    public ParentDialogsListener parentDialogListener;
    private String Locale, sellerId;
    LinearLayout sellerLinearLayout;
    private List<SellerProductList> sellerProductList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                Locale = "ar";
                view = inflater.inflate(R.layout.fragment_seller_page_arabic, container, false);
            } else {
                Locale = "en";
                view = inflater.inflate(R.layout.fragment_seller_page, container, false);
            }
        } else {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_seller_page_arabic, container, false);
        }
        sellerLinearLayout = view.findViewById(R.id.sellerLinearLayout);
        sellerRecyclerView = view.findViewById(R.id.sellerRecyclerView);
        sellerImage = view.findViewById(R.id.sellerImage);
        sellerName = view.findViewById(R.id.sellerName);
        sellerEmail = view.findViewById(R.id.sellerEmail);
        sellerAddress = view.findViewById(R.id.sellerAddress);
        sellerRating = view.findViewById(R.id.sellerRating);
        toolbar1 = view.findViewById(R.id.toolbar);
        searchImage = toolbar1.findViewById(R.id.serachbar);
        backarrow = toolbar1.findViewById((R.id.backarrow));
        toolbarText = toolbar1.findViewById((R.id.toolbarText));
        sellerLinearLayout.setVisibility(View.GONE);
        sellerId = getArguments().getString("sellerId");
        Log.e("sellerId sellerPage", sellerId);
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
        sellerRecyclerView.setHasFixedSize(true);
        getSellerData();
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

    private void getSellerData() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<SellerResponse> call = Locale == "ar" ? apiService.getSellerData(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "sellerinfo/" + sellerId) : apiService.getSellerData(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "sellerinfo/" + sellerId);
        call.enqueue(new Callback<SellerResponse>() {
            @Override
            public void onResponse(Call<SellerResponse> call, Response<SellerResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().msg.equals("success")) {
                        sellerLinearLayout.setVisibility(View.VISIBLE);
                        Log.e("seller response", "success");
//                        toolbarText.setText(response.body().data.profile.public_name);
                        Picasso.get().load("https://upgrade.eoutlet.com" + response.body().data.profile.logo).into(sellerImage);
                        sellerName.setText(response.body().data.profile.public_name);
                        sellerEmail.setText(response.body().data.profile.email);

                        sellerAddress.setText(response.body().data.profile.steet + ", " + response.body().data.profile.city + ", " + response.body().data.profile.region + ", " + response.body().data.profile.country_id);

                        sellerRating.setText(response.body().data.profile.rating.toString());
                        sellerProductList = response.body().data.products;
                        sellerAdapter = new SellerAdapter(getContext(), sellerProductList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false) {
                            @Override
                            protected boolean isLayoutRTL() {
                                if (Locale == "ar") {
                                    return true;
                                } else {
                                    return false;
                                }

                            }
                        };
                        sellerRecyclerView.setLayoutManager(gridLayoutManager);
                        sellerRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        sellerRecyclerView.setAdapter(sellerAdapter);
                        sellerAdapter.notifyDataSetChanged();
                    } else {
                        parentDialogListener.hideProgressDialog();
                        Log.e("seller response", "failed");
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SellerResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    //grid styling
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

}
