package com.eoutlet.Eoutlet.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
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
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.adpters.NewOrderListAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.intrface.UpdateBedgeCount;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.OrderListItem;
import com.eoutlet.Eoutlet.pojo.UpgradeCancelOrderResponse;
import com.eoutlet.Eoutlet.pojo.UpgradeOrderListData;
import com.eoutlet.Eoutlet.pojo.UpgradeOrderListResponse;
import com.eoutlet.Eoutlet.pojo.UpgradeReorderResponse;
import com.eoutlet.Eoutlet.pojo.UpgradedCartItems;
import com.eoutlet.Eoutlet.utility.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class NewOrderListFragment extends Fragment implements NewOrderListAdapter.OnItemClickListener {
    List<OrderListItem> customDataList = new ArrayList<>();
    View rawView;
    private RecyclerView recyclerView;
    LinearLayout noOrderList;
    private Context context;
    private NewOrderListFragment testFragment;
    public ParentDialogsListener parentDialogListener;
    private NewOrderListAdapter orderListAdapter;
    int totalcount;
    private UpdateBedgeCount updatefix;
    private ImageView searchImage, backarrow;
    private Toolbar toolbar1;
    private List<UpgradeOrderListData> upgradeOrderListData = new ArrayList<>();
    String Locale;

    public NewOrderListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                Locale = "ar";
                rawView = inflater.inflate(R.layout.fragment_new_order_list_arabic, container, false);
            } else {
                Locale = "en";
                rawView = inflater.inflate(R.layout.fragment_new_order_list, container, false);
            }
        } else {
            Locale = "ar";
            rawView = inflater.inflate(R.layout.fragment_new_order_list_arabic, container, false);
        }
        recyclerView = rawView.findViewById(R.id.recyclerView);
        noOrderList = rawView.findViewById(R.id.noOrderList);
        context = getActivity();
        testFragment = NewOrderListFragment.this;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.hasFixedSize();
        updatefix = (MainActivity) getActivity();
        toolbar1 = rawView.findViewById(R.id.toolbar);
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

        callOrderListApi();
        return rawView;

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

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //    function to get list of orders
    private void callOrderListApi() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<UpgradeOrderListResponse> call = Locale == "ar" ? apiService.getUpgradeOrderList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "myorderlist/" + MySharedPreferenceClass.getMyUserId(getContext())) : apiService.getUpgradeOrderList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "myorderlist/" + MySharedPreferenceClass.getMyUserId(getContext()));
        call.enqueue(new Callback<UpgradeOrderListResponse>() {
            @Override
            public void onResponse(Call<UpgradeOrderListResponse> call, Response<UpgradeOrderListResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().getResponse().equals("success")) {
                        upgradeOrderListData = response.body().getData();
                        if (upgradeOrderListData.size() > 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            noOrderList.setVisibility(View.GONE);
                            orderListAdapter = new NewOrderListAdapter(getContext(), upgradeOrderListData, testFragment);
                            recyclerView.setAdapter(orderListAdapter);
                            orderListAdapter.notifyDataSetChanged();
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            noOrderList.setVisibility(View.VISIBLE);
//                            Toast.makeText(getContext(), Locale == "ar" ? "انت لا تجد أي مطلوب في \"مطلوبي\" لك " : "You have no order in your My Orders!", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        parentDialogListener.hideProgressDialog();
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpgradeOrderListResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position, Boolean value) {
    }

    //    function to cancel order
    public void callCancelOrderApi(String OrderId, final int pos) {



        parentDialogListener.showProgressDialog();
        final Handler handler    = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                BasicRequest apiService =
                        BasicBuilder.getClient().create(BasicRequest.class);
                Call<UpgradeCancelOrderResponse> call = apiService.upgradeCancelOrder(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK + "ordercancel/" + OrderId);
                call.enqueue(new Callback<UpgradeCancelOrderResponse>() {
                    @Override
                    public void onResponse(Call<UpgradeCancelOrderResponse> call, Response<UpgradeCancelOrderResponse> response) {
                        if (response.body() != null) {
                            parentDialogListener.hideProgressDialog();
                            if (response.body().getResponse().equals("success")) {
                                Log.e("cancel address", "success");
                                Toast.makeText(getContext(), "Order Cancelled Successfully", Toast.LENGTH_LONG).show();
                                callOrderListApi();
                            } else {
                                parentDialogListener.hideProgressDialog();
                                Log.e("cancel address", "failed");
                            }
                        } else {
                            parentDialogListener.hideProgressDialog();
                            Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpgradeCancelOrderResponse> call, Throwable t) {
                        parentDialogListener.hideProgressDialog();
                        Log.e("fail", t.getMessage());
                        Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }, 5000);

    }

    //      function to reorder
    public void doReorder(String Increment_id, final int pos) {
        Log.e("reorder", Increment_id);
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        map1.put("order_id", Increment_id);
        mainparam.put("param", map1);
        Call<UpgradeReorderResponse> call = Locale == "ar" ? apiService.upgradeReorderArabic(mainparam) : apiService.upgradeReorder(mainparam);
        call.enqueue(new Callback<UpgradeReorderResponse>() {
            @Override
            public void onResponse(Call<UpgradeReorderResponse> call, Response<UpgradeReorderResponse> response) {
                parentDialogListener.hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().getMsg().equalsIgnoreCase("success")) {
                        Log.e("reordered", "successfully");
                        Toast.makeText(getContext(), response.body().getData(), Toast.LENGTH_LONG).show();
                        getUpgradeCartData();
                    } else {
                        Log.e("reordered", "not successful");
                        Toast.makeText(getContext(), response.body().getData(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpgradeReorderResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("RemoveFromWishList Fail", t.toString());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });

    }

    //            function to get Upgraded cart data and refresh the count of cart items
    public void getUpgradeCartData() {
        totalcount = 0;
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Map<String, String> query = new HashMap<>();
        query.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        Call<UpgradedCartItems> call = apiService.getUpgradedCartDetail(Constants.UPGRADED_HOST_LINK + "rest/en/V1/api/customercart/" + MySharedPreferenceClass.getMyUserId(getContext()));
        call.enqueue(new Callback<UpgradedCartItems>() {
            @Override
            public void onResponse(Call<UpgradedCartItems> call, Response<UpgradedCartItems> response) {
                parentDialogListener.hideProgressDialog();
                if (response.body().response.equals("success")) {
                    dovibrate();
                    if (response.body().data.items.size() > 0) {
                        for (int i = 0; i < response.body().data.items.size(); i++) {
                            totalcount = totalcount + (int) (response.body().data.items.get(i).qty);
                        }
                        MySharedPreferenceClass.setBedgeCount(getContext(), totalcount);
                        updatefix.updateBedgeCount();
                        MainActivity.notificationBadge.setVisibility(View.VISIBLE);
                        Log.e("Total Value in Cart--->", String.valueOf(totalcount));
                    }
                } else {
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpgradedCartItems> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    //    function to initiate vibrate
    public void dovibrate() {
        Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        assert v != null;
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(80,
                    VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(100);
        }
    }

}
