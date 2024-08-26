package com.eoutlet.Eoutlet.viewmodels;

import android.content.Context;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.activities.ParentActivity;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.CatagoryList;
import com.eoutlet.Eoutlet.api.request.ListItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ProductListViewModel extends ViewModel {
    MainActivity mcontext;
    SwipeRefreshLayout mswipe;
    int current_page;

    private MutableLiveData<List<ListItems>> currentName;

    private List<ListItems> productsLists = new ArrayList<>();
    ;

    public MutableLiveData<List<ListItems>> getCurrentName() {
        if (currentName == null) {
            currentName = new MutableLiveData<List<ListItems>>();
        }
        return currentName;
    }

    public void getProductListData(int Value, Context context/*, SwipeRefreshLayout mswipe*/,int current_page) {
        mcontext = (MainActivity) context;
        this.mswipe = mswipe;
        this.current_page = current_page;
        ProductListData pp = new ProductListData();
        List<ListItems> ll = pp.getProductList(String.valueOf(Value), mcontext);


    }

    class ProductListData extends ParentActivity {


        public List<ListItems> getProductList(String productId, final MainActivity mcontext) {
           // productsLists = new ArrayList<>();

            mcontext.showProgressDialog();


            BasicRequest apiService =
                    BasicBuilder.getClient().create(BasicRequest.class);

            Map<String, String> map = new HashMap<>();
            map.put("cat_id", productId);
            map.put("page",String.valueOf(current_page));


            Call<CatagoryList> call = apiService.getCataGoryList(map);
            call.enqueue(new Callback<CatagoryList>() {
                @Override
                public void onResponse(Call<CatagoryList> call, Response<CatagoryList> response) {


                    if (response.body() != null) {
                        mcontext.hideProgressDialog();

                        if (response.body().data.size() > 0) {

                            for (int i = 0; i < response.body().data.size(); i++) {
                                productsLists.add(response.body().data.get(i));
                                currentName.setValue(productsLists);
                            }






                        } else {
                            mcontext.hideProgressDialog();

                            Toast.makeText(mcontext, "لا يوجد سجلات.", Toast.LENGTH_SHORT).show();


                        }
                    } else {
                        mcontext.hideProgressDialog();


                    }


                }

                @Override
                public void onFailure(Call<CatagoryList> call, Throwable t) {
                    mcontext.hideProgressDialog();
                    Log.e(TAG, t.toString());
                    System.out.println("Error Dta....." + t.getMessage());


                    Toast.makeText(mcontext, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            return productsLists;
        }
    }

}
