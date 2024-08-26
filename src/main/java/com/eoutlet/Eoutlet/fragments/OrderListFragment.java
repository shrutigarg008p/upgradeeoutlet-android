package com.eoutlet.Eoutlet.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.adpters.OrderListAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.intrface.UpdateBedgeCount;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.CancelOrderListResponse;
import com.eoutlet.Eoutlet.pojo.CustomData;
import com.eoutlet.Eoutlet.pojo.OrderListItem;
import com.eoutlet.Eoutlet.pojo.OrderListResponse;
import com.eoutlet.Eoutlet.pojo.OtpResponse;
import com.eoutlet.Eoutlet.pojo.ReorderResponse;
import com.eoutlet.Eoutlet.pojo.ViewCart1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderListFragment extends Fragment implements OrderListAdapter.OnItemClickListener {
    List<OrderListItem> customDataList = new ArrayList<>();
    View rawView;
    private RecyclerView recyclerView;
    private Context context;
    private OrderListFragment testFragment;
    public ParentDialogsListener parentDialogListener;
    OrderListAdapter orderListAdapter;
    int totalcount;
    private UpdateBedgeCount updatefix;
    public OrderListFragment() {
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
        rawView = inflater.inflate(R.layout.fragment_order_list, container, false);
        recyclerView = rawView.findViewById(R.id.recyclerView);
        context = getActivity();
        testFragment = OrderListFragment.this;

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.hasFixedSize();

        updatefix = (MainActivity) getActivity();
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

    private void callOrderListApi() {


        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Map<String, String> map1 = new HashMap<>();

        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(context));


        Call<OrderListResponse> call = apiService.orderList(map1);
        call.enqueue(new Callback<OrderListResponse>() {
            @Override
            public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().getMsg().equalsIgnoreCase("success")) {

                        List<OrderListItem> data = response.body().getData();
                        if (data.size() > 0 || data != null) {
                            customDataList.addAll(data);
                        }
//                            customDataList.addAll(response.body().getData());


                        recyclerView.setAdapter(new OrderListAdapter(context, customDataList, testFragment));

//                            orderListAdapter.notifyDataSetChanged();


                    } else {
                        parentDialogListener.hideProgressDialog();


                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<OrderListResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("Failure..", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();


            }
        });


    }

    @Override
    public void onItemClick(View view, int position, Boolean value) {


    }

    public void callCancelOrderApi(String OrderId, final int pos) {

        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Map<String, String> map1 = new HashMap<>();

        map1.put("order_id", OrderId);


        Call<CancelOrderListResponse> call = apiService.canceorderList(map1);
        call.enqueue(new Callback<CancelOrderListResponse>() {
            @Override
            public void onResponse(Call<CancelOrderListResponse> call, Response<CancelOrderListResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().getMsg().equalsIgnoreCase("success")) {
                        customDataList.remove(pos);

                        recyclerView.setAdapter(new OrderListAdapter(context, customDataList, testFragment));


                    } else {
                        parentDialogListener.hideProgressDialog();


                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<CancelOrderListResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("Failure..", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();


            }
        });
    }


    public void doReorder(String OrderId, final int pos) {

        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Map<String, String> map1 = new HashMap<>();

        map1.put("order_id", OrderId);
        map1.put("customer_id",MySharedPreferenceClass.getMyUserId(getContext()));


        Call<ReorderResponse> call = apiService.reorder(map1);
        call.enqueue(new Callback<ReorderResponse>() {
            @Override
            public void onResponse(Call<ReorderResponse> call, Response<ReorderResponse> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    if (response.body().getMsg().equalsIgnoreCase("success")) {
                        Toast.makeText(getContext(),response.body().getdata().toString(),Toast.LENGTH_LONG).show();
                        getCartData();







                    } else {
                        parentDialogListener.hideProgressDialog();

                        Toast.makeText(getContext(),response.body().getdata().toString(),Toast.LENGTH_LONG).show();


                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<ReorderResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("Failure..", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();


            }
        });
    }


    public void getCartData() {
        totalcount = 0;

        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));

        Call<ViewCart1> call = apiService.getCartDetail(query);
        call.enqueue(new Callback<ViewCart1>() {
            @Override
            public void onResponse(Call<ViewCart1> call, Response<ViewCart1> response) {
                parentDialogListener.hideProgressDialog();

                if (response.body().msg.equals("success")) {


                    if (response.body().data.size() > 0) {
                        for (int i = 0; i < response.body().data.size(); i++) {

                            if (response.body().data.get(i).qty instanceof String) {

                                totalcount = totalcount + (int) Float.parseFloat((String) response.body().data.get(i).qty);

                            } else {


                                totalcount = totalcount + (int) (response.body().data.get(i).qty);


                            }


                            //   totalcount = totalcount+(int)response.body().data.get(i).qty;


                        }
                        parentDialogListener.hideProgressDialog();
                        MySharedPreferenceClass.setBedgeCount(getContext(), totalcount);

                        updatefix.updateBedgeCount();
                        MainActivity.notificationBadge.setVisibility(View.VISIBLE);

                        Log.e("Total Value in Cart--->", String.valueOf(totalcount));


                    }


                } else {
                    //  parentDialogListener.hideProgressDialog();
                    // updatefix.updateBedgeCount();
                    // MainActivity.notificationBadge.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();

                }


            }


            @Override
            public void onFailure(Call<ViewCart1> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });
    }
}
