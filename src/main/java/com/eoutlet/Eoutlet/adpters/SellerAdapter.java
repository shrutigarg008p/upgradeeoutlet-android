package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.fragments.ProductDetail;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.SellerProductList;

import java.util.List;


public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.SingleItemRowHolder> {
    private Context context;
    private List<SellerProductList> sellerProductList;
    String Locale;
    View v;

    public SellerAdapter(Context context, List<SellerProductList> sellerProductList) {
        this.context = context;
        this.sellerProductList = sellerProductList;
    }


    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                v = LayoutInflater.from(context).inflate(R.layout.seller_list_item_arabic, null);
                Locale = "ar";
            } else {
                v = LayoutInflater.from(context).inflate(R.layout.seller_list_item, null);
                Locale = "en";
            }
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.seller_list_item_arabic, null);
            Locale = "ar";
        }
        return new SingleItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleItemRowHolder holder, final int position) {
        try {
            Glide.with(holder.sellerProductImage.getContext())
                    .load(sellerProductList.get(position).img).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).error(R.drawable.progress_animation)
                    .placeholder(R.drawable.progress_animation).override(500, 1000)
                    .into(holder.sellerProductImage);
        } catch (Exception e) {
        }
        holder.sellerProductName.setText(sellerProductList.get(position).name);
        if (sellerProductList.get(position).old_price == 0) {
            holder.sellerProductOldPrice.setVisibility(View.GONE);
        } else {
            holder.sellerProductOldPrice.setVisibility(View.GONE);
            holder.sellerProductOldPrice.setText(Locale == "ar" ? MySharedPreferenceClass.getSelectedCurrencyName(context) + " " + Math.round(sellerProductList.get(position).old_price*MySharedPreferenceClass.getSelectedCurrencyRate(context)) :MySharedPreferenceClass.getSelectedCurrencyName(context) + " " + MySharedPreferenceClass.getSelectedCurrencyName(context));
        }
        holder.sellerProductNewPrice.setText(Locale == "ar" ? MySharedPreferenceClass.getSelectedCurrencyName(context) + " " + Math.round(sellerProductList.get(position).price*MySharedPreferenceClass.getSelectedCurrencyRate(context)) : MySharedPreferenceClass.getSelectedCurrencyName(context) + " " + Math.round(sellerProductList.get(position).price*MySharedPreferenceClass.getSelectedCurrencyRate(context)));
        holder.sellerProductContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment productDetail = new ProductDetail();
                FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.sellerFrameLayout, productDetail).addToBackStack(null).commit();
                Bundle databund = new Bundle();
                //put your ArrayList data in bundle
                databund.putString("fromwhere", "fromhome");
                databund.putString("previouspage","FromSellerScreen");
                databund.putString("pid", sellerProductList.get(position).id);
                productDetail.setArguments(databund);

            }
        });
    }

    @Override
    public int getItemCount() {
        return sellerProductList.size();
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        ImageView sellerProductImage;
        CardView sellerProductContainer;
        TextView sellerProductName, sellerProductOldPrice, sellerProductNewPrice;

        public SingleItemRowHolder(@NonNull View itemView) {
            super(itemView);
            sellerProductImage = itemView.findViewById(R.id.sellerProductImage);
            sellerProductName = itemView.findViewById(R.id.sellerProductName);
            sellerProductOldPrice = itemView.findViewById(R.id.sellerProductOldPrice);
            sellerProductNewPrice = itemView.findViewById(R.id.sellerProductNewPrice);
            sellerProductContainer = itemView.findViewById(R.id.sellerProductContainer);
        }
    }
}
