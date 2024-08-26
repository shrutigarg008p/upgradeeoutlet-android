package com.eoutlet.Eoutlet.adpters;

import android.app.ProgressDialog;
import android.content.Context;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaDrm;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.ListItems;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.fragments.HomeFragmentNewDesign;

import com.eoutlet.Eoutlet.fragments.ProductList;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.AddtoWishList;
import com.moe.pushlibrary.MoEHelper;
import com.moengage.core.Properties;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
    List<ListItems> mydataset;
    Context mcontext;
    ViewListener viewListener;
    ExecuteFragment execute;
    View itemView;
    public ArrayList<Integer> selctedwishlist = new ArrayList<>();
    private ProgressDialog progressDialog1;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre, productname, oldPrice, newPrice;
        public ImageView iv, heartbtn;
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

    public ProductListAdapter(Context context, List<ListItems> mydataset, ViewListener viewlistener) {
        this.mydataset = mydataset;
        mcontext = context;
        execute = (MainActivity) context;
        this.viewListener = viewlistener;


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (MySharedPreferenceClass.getChoosenlanguage(parent.getContext()).equals("ar")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_list_item, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_list_item_english, parent, false);
        }
        Log.e("oncreateviewholder invoked","");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        this.mydataset =mydataset;
        Log.e("onBindViewHolder",position+" ");
        for(int i=0;i<mydataset.size();i++){
           // Log.e("wish list flag is",mydataset.get(i).iswishlist+" "  );
            //Log.e("wish list flag size is",mydataset.size()+" "  );
        }
        try {
            holder.productname.setText(mydataset.get(position).name);


            if (mydataset.get(position).oldPrice.equals("0")) {
                holder.oldPrice.setVisibility(View.GONE);
            } else {
                holder.oldPrice.setVisibility(View.VISIBLE);

                String mainprice = " ";
                if (mydataset.get(position).oldPrice.contains(",")) {
                    mainprice = mydataset.get(position).oldPrice.replaceAll(",", "");


                    int priceaccordingtocurrency = Math.round((Float.parseFloat(mainprice) * MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext())));


                    holder.oldPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()) + " " + priceaccordingtocurrency);


                } else {
                    mainprice = mydataset.get(position).oldPrice;


                    int priceaccordingtocurrency = Math.round((Float.parseFloat(mainprice) * MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext())));


                    holder.oldPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()) + " " + priceaccordingtocurrency);


                }

            }


            if (mydataset.get(position).price.contains(",")) {
                String price = mydataset.get(position).price.replaceAll(",", "");

                Log.e("PriceinFloat", (Float.parseFloat(price) * MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext())) + " ");

                int priceaccordingtocurrency = Math.round(((Float.parseFloat(price) * MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext()))));


                holder.newPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()) + " " + priceaccordingtocurrency);


            } else {
                String price = mydataset.get(position).price;
                Log.e("PriceinFloat", (Float.parseFloat(price) * MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext())) + " ");

                int priceaccordingtocurrency = Math.round(((Float.parseFloat(price) * MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext()))));

                holder.newPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()) + " " + priceaccordingtocurrency);

            }

            if(mydataset.get(position).iswishlist){
                holder.heartbtn.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.heartselected));
               selctedwishlist.add(position);
            }
            else{
                holder.heartbtn.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.productdetailheart));


            }



          //  Log.e("wish list flag is",mydataset.get(position).iswishlist+" "  );


            holder.productListAddToWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   //viewListener.onClick(position, view);

                    getWishListFlag(position,holder);
                   /* if(!MySharedPreferenceClass.getMyUserId(view.getContext()).equals(" ")) {


                        if(!selctedwishlist.contains(position)) {
                            addToWishList(mydataset.get(position).id, position, holder);
                        }
                        else
                        {
                            removeFromWishList(mydataset.get(position).id,position,holder);

                        }


                    }
                    else{

                        if(!selctedwishlist.contains(position)) {
                            addtowishlistforGuestUser(mydataset.get(position).id,position,holder);
                        }
                        else{
                            removeFromWishList(mydataset.get(position).id,position,holder);

                        }



                    }*/

                }
            });
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewListener.onClick(position, view);

                }
            });
        } catch (Exception e) {


            Log.e("Issue is--->", "Float Number Format");
        }



        try {
            Glide.with(holder.iv.getContext())
                    .load(mydataset.get(position).img).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).error(R.drawable.progress_animation)
                    .placeholder(R.drawable.progress_animation).override(1000, 1500)

                    .into(holder.iv);
        } catch (Exception e) {
        }

    }

    @Override
    public void onViewRecycled(@NonNull MyViewHolder holder) {
        super.onViewRecycled(holder);
        Glide.with(holder.iv.getContext()).clear(holder.iv);
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



    public  void addToWishList(String id,int position,MyViewHolder holder) {

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(itemView.getContext()));
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


                        }
                    } else {
                        hideProgressDialog();
                    }

                    moengageAddtoWishListEvent(mydataset.get(position).sku,mydataset.get(position).name,mydataset.get(position).type);
                } else {
                    if (response.code() == 400) {
                        hideProgressDialog();
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
                        hideProgressDialog();
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
                hideProgressDialog();
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
    public void removeFromWishList(String id, int position, MyViewHolder holder) {

        String mykey = getUniqueID2().replaceAll("[^A-Za-z0-9]","");
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        if (MySharedPreferenceClass.getMyUserId(getApplicationContext()).equals(" ")){
            map1.put("device_id","1"+ mykey.toUpperCase()+"2");
        }
        else{
            map1.put("customer_id", MySharedPreferenceClass.getMyUserId(itemView.getContext()));


        }



        map1.put("product_id", id);
        mainparam.put("param", map1);

        Call<AddtoWishList> call;
        if (MySharedPreferenceClass.getMyUserId(getApplicationContext()).equals(" ")){
            call = apiService.removeFromGuestwishlist(mainparam);

        }
        else {
            call = apiService.removeFromwishlist(mainparam);
        }



        call.enqueue(new Callback<AddtoWishList>() {
            @Override
            public void onResponse(Call<AddtoWishList> call, Response<AddtoWishList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        hideProgressDialog();
                        if (response.body().msg.equals("success") || response.body().response.equals("success")) {
                            if(MySharedPreferenceClass.getChoosenlanguage( itemView.getContext()).equals("en")) {
                                Toast.makeText(itemView.getContext(), "Product removed from wishlist.", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(itemView.getContext(), "تمت إزالة المنتج من قائمة الامنيات", Toast.LENGTH_LONG).show();
                            }



                            holder.heartbtn.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.productdetailheart));
                            selctedwishlist.remove(new Integer(position));
                            if (MySharedPreferenceClass.getMyUserId(getApplicationContext()).equals(" ")) {
                                HomeFragmentNewDesign.getInstance().getWishListItemCountforguestUser();
                            }
                            else {
                                HomeFragmentNewDesign.getInstance().getWishListItemCount();
                            }
                            
                        }
                    } else {
                        hideProgressDialog();
                    }
                } else {
                    if (response.code() == 400) {
                        hideProgressDialog();
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
                        hideProgressDialog();
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
                hideProgressDialog();
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
        progressDialog1 = ProgressDialog.show(itemView.getContext(), "Null", "Null", false, false);
        progressDialog1.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        progressDialog1.setContentView( R.layout.progress_bar );
        progressDialog1.setCanceledOnTouchOutside(false);
    }
    @Nullable
    String getUniqueID2() {
        UUID wideVineUuid = new UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L);
        try {
            MediaDrm wvDrm = new MediaDrm(wideVineUuid);
            byte[] wideVineId = wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID);


            String encodedString = Base64.encodeToString(wideVineId, Base64.DEFAULT);
            Log.i("Uniqueid", "Uniqueid" + encodedString);
            return encodedString;
        } catch (Exception e) {
            // Inspect exception
            return null;
        }

    }
    public void addtowishlistforGuestUser(String id,int position,ProductListAdapter.MyViewHolder holder) {


        String myKey = getUniqueID2().toString().replaceAll("[^A-Za-z0-9]","");


        Log.e("mykey is",myKey);
/*{
      "param": {
          "device_id":"23476242784b24b2646v",
          "product_id": "52862"
      }
}
*/




        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("device_id", "1"+myKey.toUpperCase()+"2");
        map1.put("product_id", id);
        mainparam.put("param", map1);

        Call<AddtoWishList> call = apiService.addtowishlistforGuestUser(mainparam);
        call.enqueue(new Callback<AddtoWishList>() {
            @Override
            public void onResponse(Call<AddtoWishList> call, Response<AddtoWishList> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                      hideProgressDialog();
                        if (response.body().msg.equals("success")) {
                            // Update Wishlist Badge Count
                          HomeFragmentNewDesign.getInstance().getWishListItemCountforguestUser();

                            if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
                                Toast.makeText(itemView.getContext(), "Product added to Wishlist", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "تم اضافة المنتج إلى قائمة الأمنيات..", Toast.LENGTH_LONG).show();
                            }

                            selctedwishlist.add(position);

                            holder.heartbtn.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.heartselected));




                        }
                     hideProgressDialog();
                    } else {
             hideProgressDialog();


                    }
                    moengageAddtoWishListEvent(mydataset.get(position).sku,mydataset.get(position).name,mydataset.get(position).type);
                } else {

                    if (response.code() == 400) {
                 hideProgressDialog();
                        if (!response.isSuccessful()) {

                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(itemView.getContext(), userMessage, Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (response.code() == 401) {
                      hideProgressDialog();
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(itemView.getContext(), userMessage, Toast.LENGTH_LONG).show();

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
    public void hideProgressDialog() {

        try {



            //progressDialog.hide();
            progressDialog1.dismiss();
        } catch (Exception e) {

        }

    }

    public void getWishListFlag(int position,MyViewHolder holder) {

   showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();

        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(itemView.getContext()));
        String myKey = getUniqueID2().toString().replaceAll("[^A-Za-z0-9]", "");

        Call<String> call;
        if (MySharedPreferenceClass.getMyUserId(getApplicationContext()).equals(" ")) {
            call = apiService.getWishListFlag("https://upgrade.eoutlet.com/rest/en/V1/api/checkwishlist/" + "1" + myKey.toUpperCase() + "2" + "/0/" + mydataset.get(position).id);
        } else {
            call = apiService.getWishListFlag("https://upgrade.eoutlet.com/rest/en/V1/api/checkwishlist/" + "0/" + MySharedPreferenceClass.getMyUserId(itemView.getContext()) + "/" + mydataset.get(position).id);

        }

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null) {

                    if (response.body().toString().equals("0")) {
                        if(!MySharedPreferenceClass.getMyUserId(itemView.getContext()).equals(" ")) {
                            addToWishList(mydataset.get(position).id,position,holder);

                        }

                        else{
                            addtowishlistforGuestUser(mydataset.get(position).id,position,holder);


                        }




                    } else {
                        if(!MySharedPreferenceClass.getMyUserId(itemView.getContext()).equals(" ")) {
                            removeFromWishList(mydataset.get(position).id,position,holder);

                        }

                        else{
                            removeFromWishList(mydataset.get(position).id,position,holder);



                        }

                    }

                    //initRecycler(v,catagoryList);


                    //Log.d("Wish List Response is ------->>>>", response.body().toString());


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, t.toString());

                Toast.makeText(itemView.getContext(), MySharedPreferenceClass.getChoosenlanguage(itemView.getContext()) == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
             hideProgressDialog();
            }
        });


    }

    public void refreshList(){
        notifyDataSetChanged();
    }

    public  void moengageAddtoWishListEvent(String sku,String name,String type){

        ArrayList<String> catname = new ArrayList();

        Properties properties = new Properties();


        properties .addAttribute("sku",sku )

                .addAttribute("name", name)
                .addAttribute("type", type);



        MoEHelper.getInstance(getApplicationContext()).trackEvent("Add To Wishlist", properties);
        Log.e("sku",sku );
        Log.e("name", name);
       // Log.e("type", type);

    }
}
