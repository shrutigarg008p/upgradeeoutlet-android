package com.eoutlet.Eoutlet.adpters;

import static com.eoutlet.Eoutlet.activities.MainActivity.navigationView;
import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.fragments.ProductDetail;
import com.eoutlet.Eoutlet.fragments.ProductList;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.LimitedStock;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.Serializable;
import java.util.List;

public class LimitedStockAdapter extends RecyclerView.Adapter<LimitedStockAdapter.MyViewHolder> {
    private List<LimitedStock> limitedStockList;
    private Context context;
    private FirebaseAnalytics mFirebaseAnalytics;
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView limitedStockImage;

        MyViewHolder(View view) {
            super(view);
            limitedStockImage = view.findViewById(R.id.limitedStockImage);
        }
    }

    public LimitedStockAdapter(Context context, List<LimitedStock> limitedStockList) {
        this.context = context;
        this.limitedStockList = limitedStockList;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.limited_stock_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LimitedStock limitedStock = limitedStockList.get(position);
        try {
            Glide.with(holder.limitedStockImage.getContext())
                    .load(limitedStock.image)/*.apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))*/.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)
                    .placeholder(R.drawable.progress_animation)/*.override(400, 1200)*/
                    .into(holder.limitedStockImage);
        } catch (Exception e) {
        }
        holder.limitedStockImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase_click_banner(limitedStockList.get(position).name,limitedStockList.get(position).id);
                if( limitedStockList.get(position).caption.equalsIgnoreCase("product_list")){
                Fragment productList = new ProductList();
                FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.container2, productList).addToBackStack(null).commit();
                Bundle databund = new Bundle();
                //put your ArrayList data in bundle
                databund.putInt("productId", Integer.parseInt(limitedStockList.get(position).id));
                databund.putString("name", limitedStockList.get(position).name);
                databund.putSerializable("childeren", (Serializable) limitedStockList);
                databund.putString("fromwhere", "fromhome");
                databund.putString("onClick", "limitedStock");
                productList.setArguments(databund);
            }
                else if(limitedStockList.get(position).caption.equalsIgnoreCase("product_link")){
                    Fragment productList = new ProductDetail();
                    FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.container2, productList).addToBackStack(null).commit();
                    Bundle databund = new Bundle();
                    //put your ArrayList data in bundle
                    databund.putString("pid",limitedStockList.get(position).id);
                    databund.putString("name", limitedStockList.get(position).name);
                    databund.putSerializable("childeren", (Serializable) limitedStockList);
                    databund.putString("fromwhere", "fromhome");
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putString("onClick", "mainBanner");
                    productList.setArguments(databund);
                    Log.e("pid is--->>>",limitedStockList.get(position).id);


                }
                else if(limitedStockList.get(position).caption.equalsIgnoreCase("category")){

                    MySharedPreferenceClass.setCatId(getApplicationContext(), limitedStockList.get(position).id);
                    navigationView.setSelectedItemId(R.id.catagory);
                }
                else if(limitedStockList.get(position).caption.equalsIgnoreCase("item_type")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();


                    databund.putInt("productId", Integer.parseInt(limitedStockList.get(position).id));
                    databund.putString("name",  limitedStockList.get(position).name);
                    databund.putString("category", limitedStockList.get(position).name);

                    databund.putString("fromwhere", "fromsubcategories");
                    databund.putString("adapterposition", "1");
                    databund.putString("attributvalue", limitedStockList.get(position).attribute.value);
                    databund.putString("attributdisplay", limitedStockList.get(position).attribute.display);
                    databund.putString("attributcount",limitedStockList.get(position).attribute.count.toString());


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(limitedStockList.get(position).caption.equalsIgnoreCase("manufacturer")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();


                    databund.putInt("productId", Integer.parseInt(limitedStockList.get(position).id));
                    databund.putString("name",  limitedStockList.get(position).name);
                    databund.putString("category", limitedStockList.get(position).name);

                    databund.putString("fromwhere", "fromcatagorybrands");
                    databund.putString("adapterposition", "1");
                    databund.putString("attributvalue", limitedStockList.get(position).attribute.value);
                    databund.putString("attributdisplay", limitedStockList.get(position).attribute.display);
                    databund.putString("attributcount",limitedStockList.get(position).attribute.count.toString());


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(limitedStockList.get(position).caption.equalsIgnoreCase("size")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();


                    databund.putInt("productId", Integer.parseInt(limitedStockList.get(position).id));
                    databund.putString("name",  limitedStockList.get(position).name);
                    databund.putString("category", limitedStockList.get(position).name);

                    databund.putString("fromwhere", "fromcatagorysize");
                    databund.putString("adapterposition", "1");
                    databund.putString("attributvalue", limitedStockList.get(position).attribute.value);
                    databund.putString("attributdisplay", limitedStockList.get(position).attribute.display);
                    databund.putString("attributcount",limitedStockList.get(position).attribute.count.toString());


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(limitedStockList.get(position).caption.equalsIgnoreCase("subcategory")){

                       MySharedPreferenceClass.setCatId(getApplicationContext(), limitedStockList.get(position).id);
                     MySharedPreferenceClass.setsubCatId(getApplicationContext(),limitedStockList.get(position).attribute.value);

                    //MySharedPreferenceClass.setCatId(getApplicationContext(),"10");
                    //MySharedPreferenceClass.setsubCatId(getApplicationContext(),"75");
                    navigationView.setSelectedItemId(R.id.catagory);




                }

            }
        });
    }
    public void firebase_click_banner(String promotionname,String promotionid) {
        Bundle bundle = new Bundle();

        bundle.putString(FirebaseAnalytics.Param.PROMOTION_NAME, promotionname);
        bundle.putString(FirebaseAnalytics.Param.PROMOTION_ID, promotionid);
        //bundle.putString(FirebaseAnalytics.Param.ITEM_LOCATION_ID, Locale.getDefault().getCountry());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, bundle);


    }
    @Override
    public int getItemCount() {
        return limitedStockList.size();
    }
}