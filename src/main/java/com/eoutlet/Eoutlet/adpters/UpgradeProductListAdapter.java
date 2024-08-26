package com.eoutlet.Eoutlet.adpters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.fragments.HomeFragmentNewDesign;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.AddtoWishList;
import com.eoutlet.Eoutlet.pojo.ElasticSearchData;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class UpgradeProductListAdapter extends RecyclerView.Adapter<UpgradeProductListAdapter.MyViewHolder> {
    List<ElasticSearchData> mydataset;
    Context mcontext;
    ViewListener viewListener;
    ExecuteFragment execute;
    private ProgressDialog progressDialog1;

    public ArrayList<Integer> selctedwishlist = new ArrayList<>();
    View itemView;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre, productname, oldPrice, newPrice;
        public ImageView iv,heartbtn;;
        LinearLayout singleproductclick;

        RelativeLayout productListAddToWishlist;
        public MyViewHolder(View view) {
            super(view);
            iv = view.findViewById(R.id.product_list_image);
            productname = view.findViewById(R.id.productname);
            oldPrice = view.findViewById(R.id.oldprice);
            newPrice = view.findViewById(R.id.newprice);
            singleproductclick = view.findViewById(R.id.singleproduct);
            heartbtn = view.findViewById(R.id.heartbtn);
            productListAddToWishlist = view.findViewById(R.id.productListAddToWishlist);
        }
    }

    public UpgradeProductListAdapter(Context context, List<ElasticSearchData> elasticSearchData, ViewListener viewlistener) {
        this.mydataset = elasticSearchData;
        this.mcontext = context;
        this.execute = (MainActivity) context;
        this.viewListener = viewlistener;


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       if(MySharedPreferenceClass.getChoosenlanguage(parent.getContext()).equalsIgnoreCase("ar")) {
           itemView = LayoutInflater.from(parent.getContext())


                   .inflate(R.layout.product_list_item, parent, false);
       }
       else{
           itemView = LayoutInflater.from(parent.getContext())


                   .inflate(R.layout.product_list_item_english, parent, false);
       }


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.productListAddToWishlist.setVisibility(View.GONE);

        try {
            holder.productname.setText(mydataset.get(position).name);


            if (String.valueOf(mydataset.get(position).old_price).equals("0")) {
                holder.oldPrice.setVisibility(View.GONE);
            } else {
                holder.oldPrice.setVisibility(View.VISIBLE);

                String mainprice = " ";
                if (String.valueOf(mydataset.get(position).old_price).contains(",")) {
                    mainprice = String.valueOf(mydataset.get(position).old_price).replaceAll(",", "");

                    holder.oldPrice.setText("SAR" + " " + (int) Float.parseFloat(mainprice));


                } else {

                    holder.oldPrice.setText("SAR" + " " + (int) Float.parseFloat(String.valueOf(mydataset.get(position).old_price)));


                }

            }





            if (String.valueOf(mydataset.get(position).price).contains(",")) {
                String price = String.valueOf(mydataset.get(position).price).replaceAll(",", "");

                holder.newPrice.setText("SAR" + " " + (int) Float.parseFloat(price));


            } else {
                holder.newPrice.setText("SAR" + " " + (int) Float.parseFloat(String.valueOf(mydataset.get(position).price)));
            }
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewListener.onClick(position, view);
                    //execute.ExecutFragmentListener(4);
                }
            });
        } catch (Exception e) {


            Log.e("Issue is--->", "Float Number Format");
        }

        holder.productListAddToWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //viewListener.onClick(position, view);
                if(!MySharedPreferenceClass.getMyUserId(view.getContext()).equals(" ")) {
                    if(!selctedwishlist.contains(position)) {
                        addToWishList(mydataset.get(position).id,position,holder);


                    }
                    else
                        {
                        removeFromWishList(mydataset.get(position).id,position,holder);

                        }


                }
                else{
                    viewListener.onClick(position, view);

                }

            }
        });
        //RequestOptions  requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        try {
            Glide.with(holder.iv)
                    .load(mydataset.get(position).img).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)
                    .placeholder(R.drawable.progress_animation).override(1000, 1500)

                    .into(holder.iv);
        } catch (Exception e) {
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mydataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    //    add To wishlist function
    public void addToWishList(String id,int position,MyViewHolder holder) {
showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(mcontext));
        map1.put("product_id", id);
        mainparam.put("param", map1);

        Call<AddtoWishList> call = apiService.addtowishlist(mainparam);
        call.enqueue(new Callback<AddtoWishList>() {
            @Override
            public void onResponse(Call<AddtoWishList> call, Response<AddtoWishList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
              hideProgressDialog();
                        if (response.body().response.equals("success")) {
                            if(MySharedPreferenceClass.getChoosenlanguage(itemView.getContext()).equals("en")) {
                                Toast.makeText(itemView.getContext(), "Product added to wishlist.", Toast.LENGTH_LONG).show();


                            }
                            else{
                                Toast.makeText(itemView.getContext(), "تم اضافة المنتج إلى قائمة الأمنيات..", Toast.LENGTH_LONG).show();
                            }
                            selctedwishlist.add(position);

                            holder.heartbtn.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.heartselected));

                            // Update Wishlist Badge Count
                            HomeFragmentNewDesign.getInstance().getWishListItemCount();
                        }
                    } else {
                       hideProgressDialog();
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(mcontext, userMessage, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (response.code() == 401) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(mcontext, userMessage, Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AddtoWishList> call, Throwable t) {
               hideProgressDialog();
                Log.e(TAG, t.toString());

                if(MySharedPreferenceClass.getChoosenlanguage(itemView.getContext()).equals("en")) {
                    Toast.makeText(itemView.getContext(), "Product added to wishlist", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(itemView.getContext(), "تم اضافة المنتج إلى قائمة الأمنيات..", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void removeFromWishList(String id,int position,MyViewHolder holder) {
      showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(mcontext));
        map1.put("product_id", id);
        mainparam.put("param", map1);

        Call<AddtoWishList> call = apiService.removeFromwishlist(mainparam);
        call.enqueue(new Callback<AddtoWishList>() {
            @Override
            public void onResponse(Call<AddtoWishList> call, Response<AddtoWishList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                      hideProgressDialog();
                        if (response.body().response.equals("success")) {
                            if(MySharedPreferenceClass.getChoosenlanguage(itemView.getContext()).equals("en")) {
                                Toast.makeText(itemView.getContext(), "Product removed from wishlist.", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(itemView.getContext(), "تمت إزالة المنتج من قائمة الامنيات", Toast.LENGTH_LONG).show();
                            }



                            holder.heartbtn.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.productdetailheart));
                            selctedwishlist.remove(new Integer(position));
                            // Update Wishlist Badge Count
                            HomeFragmentNewDesign.getInstance().getWishListItemCount();
                        }
                    } else {
                    hideProgressDialog();
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(mcontext, userMessage, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (response.code() == 401) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(mcontext, userMessage, Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AddtoWishList> call, Throwable t) {

                Log.e(TAG, t.toString());
                if(MySharedPreferenceClass.getSelectedCurrencyName(mcontext).equals("ar")) {
                    Toast.makeText(mcontext, "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(mcontext, "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void showProgressDialog() {
        progressDialog1 = ProgressDialog.show( mcontext, "Null", "Null", false, false);
        progressDialog1.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        progressDialog1.setContentView( R.layout.progress_bar );
        progressDialog1.setCanceledOnTouchOutside(false);
    }
    public void hideProgressDialog() {

        try {



            //progressDialog.hide();
            progressDialog1.dismiss();
        } catch (Exception e) {

        }

    }
}
