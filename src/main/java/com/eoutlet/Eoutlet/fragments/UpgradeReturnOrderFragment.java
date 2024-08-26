package com.eoutlet.Eoutlet.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.adpters.UpgradeReturnOrderAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.UpgradeReturnOrderFormResponse;
import com.eoutlet.Eoutlet.pojo.UpgradeReturnOrderResponse;
import com.eoutlet.Eoutlet.pojo.UpgradeReturnResponseFormOrderItems;
import com.eoutlet.Eoutlet.utility.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpgradeReturnOrderFragment extends Fragment {
    private View view;
    private RecyclerView returnOrderRecyclerView;
    EditText returnOrderCommentBox;
    CheckBox returnPolicyCheckBox;
    TextView termsAndConditionText;
    FrameLayout returnOrderSubmitButton;
    LinearLayout returnOrderContainer;
    private UpgradeReturnOrderAdapter upgradeReturnOrderAdapter;
    public ParentDialogsListener parentDialogListener;
    private List<UpgradeReturnResponseFormOrderItems> upgradeReturnResponseFormOrderItems = new ArrayList<>();
    String Order_Id, order_id;
    boolean returnPolicyChecked = false;
    String Locale;
    private ImageView searchImage, backarrow;
    private Toolbar toolbar1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                Locale = "ar";
                view = inflater.inflate(R.layout.fragment_upgrade_return_order_arabic, container, false);
            } else {
                Locale = "en";
                view = inflater.inflate(R.layout.fragment_upgrade_return_order, container, false);
            }
        } else {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_upgrade_return_order_arabic, container, false);
        }
//        ((MainActivity) getContext()).items.clear();
        ((MainActivity) getContext()).requestItems.clear();
        returnOrderCommentBox = view.findViewById(R.id.returnOrderCommentBox);
        returnPolicyCheckBox = view.findViewById(R.id.returnPolicyCheckBox);
        termsAndConditionText = view.findViewById(R.id.termsAndConditionText);
        returnOrderSubmitButton = view.findViewById(R.id.returnOrderSubmitButton);
        returnOrderRecyclerView = view.findViewById(R.id.returnOrderRecyclerView);
        returnOrderContainer = view.findViewById(R.id.returnOrderContainer);
        returnOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        returnOrderContainer.setVisibility(View.GONE);
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
        returnPolicyCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    returnPolicyChecked = true;
                } else {
                    returnPolicyChecked = false;
                }

            }
        });
        order_id = getArguments().getString("order_id");
        getReturnItemsList(order_id);
        returnOrderSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MainActivity) getContext()).requestItems.size() > 0) {
                    for (int i = 0; i < ((MainActivity) getContext()).requestItems.size(); i++) {
                       /* if (((MainActivity) getContext()).requestItems.get(i).get("reason_id") == null || ((MainActivity) getContext()).requestItems.get(i).get("reason_id") == "") {
                            Toast.makeText(getContext(), Locale == "ar" ? "يرجى تحديد السبب الصحيح للعودة" : "Please select valid return reason", Toast.LENGTH_LONG).show();
                            break;
                        } else if (((MainActivity) getContext()).requestItems.get(i).get("condition_id") == null || ((MainActivity) getContext()).requestItems.get(i).get("condition_id") == "") {
                            Toast.makeText(getContext(), Locale == "ar" ? "يرجى تحديد الحالة الصحيحة للعنصر" : "Please select valid item condition", Toast.LENGTH_LONG).show();
                            break;
                        }*/ /*else if (((MainActivity) getContext()).requestItems.get(i).get("resolution_id") == null || ((MainActivity) getContext()).requestItems.get(i).get("resolution_id") == "") {
                            Toast.makeText(getContext(), Locale == "ar" ? "يرجى تحديد دقة صحيحة" : "Please select valid resolution", Toast.LENGTH_LONG).show();
                            break;
                        } *//*else {*/
                            if (returnPolicyChecked == true) {

                                Log.e("active",((MainActivity) getContext()).requestItems.get(i).get("active").toString());
                                Log.e("order_item_id",((MainActivity) getContext()).requestItems.get(i).get("order_item_id").toString());
                                Log.e("reason",((MainActivity) getContext()).requestItems.get(i).get("reason_id").toString());
                                Log.e("condition ",((MainActivity) getContext()).requestItems.get(i).get("condition_id").toString());
                                Log.e("reso",((MainActivity) getContext()).requestItems.get(i).get("resolution_id").toString());

                                orderReturn();
                            } else {
                                dovibrate();
                                Toast.makeText(getContext(), Locale == "ar" ? "يرجى تحديد السبب الصحيح للعودة" : "Please accept return policy", Toast.LENGTH_LONG).show();
                            }
                        }
                  //  }
                } else {
                    Toast.makeText(getContext(), Locale == "ar" ? "يرجى قبول سياسة العودة" : "Please select item to return", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private void dovibrate() {
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

    private void getReturnItemsList(String order_id) {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<UpgradeReturnOrderFormResponse> call = Locale == "ar" ? apiService.getUpgradeReturnItemsList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "returnformdata/" + order_id) : apiService.getUpgradeReturnItemsList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "returnformdata/" + order_id);
        call.enqueue(new Callback<UpgradeReturnOrderFormResponse>() {
            @Override
            public void onResponse(Call<UpgradeReturnOrderFormResponse> call, Response<UpgradeReturnOrderFormResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().getResponse().equals("success")) {
                        returnOrderContainer.setVisibility(View.VISIBLE);
                        Log.e("getReturnItemsList", "success");
                        parentDialogListener.hideProgressDialog();
                        Order_Id = response.body().getData().getOrder_id();
                        upgradeReturnResponseFormOrderItems = response.body().getData().getOrderitems();
                        upgradeReturnOrderAdapter = new UpgradeReturnOrderAdapter(getContext(), upgradeReturnResponseFormOrderItems, UpgradeReturnOrderFragment.this);
                        returnOrderRecyclerView.setAdapter(upgradeReturnOrderAdapter);
                        upgradeReturnOrderAdapter.notifyDataSetChanged();
                    } else {
                        returnOrderContainer.setVisibility(View.GONE);
                        Log.e("getReturnItemsList", "failed");
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpgradeReturnOrderFormResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void orderReturn() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
        HashMap<Object, Object> mainparam = new HashMap<>();
        HashMap<Object, Object> map = new HashMap<>();

        map.put("order_id", order_id);
        map.put("comment", returnOrderCommentBox.getText().toString());
        map.put("agree_return_policy", "1");
        ArrayList<Object> items = new ArrayList<>();
        if (((MainActivity) getContext()).requestItems.size() > 0) {
            for (int i = 0; i < ((MainActivity) getContext()).requestItems.size(); i++) {
                HashMap<String, String> itemDetails = new HashMap<>();
                itemDetails.put("active", ((MainActivity) getContext()).requestItems.get(i).get("active").toString());
                itemDetails.put("order_item_id", ((MainActivity) getContext()).requestItems.get(i).get("order_item_id").toString());
                itemDetails.put("qty_requested", ((MainActivity) getContext()).requestItems.get(i).get("qty_requested").toString());
                itemDetails.put("reason_id", ((MainActivity) getContext()).requestItems.get(i).get("reason_id").toString());
                itemDetails.put("condition_id", ((MainActivity) getContext()).requestItems.get(i).get("condition_id").toString());
                itemDetails.put("resolution_id", ((MainActivity) getContext()).requestItems.get(i).get("resolution_id").toString());
                items.add(itemDetails);
            }
        }

        map.put("items", items);
        mainparam.put("param", map);


        Call<UpgradeReturnOrderResponse> call = Locale == "ar" ? apiService.upgradeReturnOrderArabic(mainparam) : apiService.upgradeReturnOrder(mainparam);
        call.enqueue(new Callback<UpgradeReturnOrderResponse>() {
            @Override
            public void onResponse(Call<UpgradeReturnOrderResponse> call, Response<UpgradeReturnOrderResponse> response) {
                parentDialogListener.hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().getResponse().equals("success")) {
                        Toast.makeText(getContext(), Locale == "ar" ?"تم انشاء الاسترجاع بنجاح": "Returned created Successfully.", Toast.LENGTH_LONG).show();
                        getReturnItemsList(order_id);
                    } else {
                        Log.e("Return Order", "error");
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpgradeReturnOrderResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("Return Order Fail", t.toString());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : "An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

}