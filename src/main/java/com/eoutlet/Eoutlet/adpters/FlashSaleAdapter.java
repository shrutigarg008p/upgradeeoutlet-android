package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.api.request.ListItems;
import com.eoutlet.Eoutlet.fragments.NewHomeFragmentUpgrade;
import com.eoutlet.Eoutlet.fragments.ProductDetail;

import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.UpgradeHomeScreenFlashSale;

import java.util.List;

public class FlashSaleAdapter extends RecyclerView.Adapter<FlashSaleAdapter.MyViewHolder> {
    private List<UpgradeHomeScreenFlashSale> flashSaleList;
    private View itemView;
    Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView flashSaleItem, flashSaleItemPrice, flashSaleItemOldPrice;
        ImageView flashSaleImage;
        LinearLayout flashSaleContainer;

        MyViewHolder(View view) {
            super(view);
            flashSaleItem = view.findViewById(R.id.flashSaleItem);
            flashSaleItemPrice = view.findViewById(R.id.flashSaleItemPrice);
            flashSaleItemOldPrice = view.findViewById(R.id.flashSaleItemOldPrice);
            flashSaleImage = view.findViewById(R.id.flashSaleImage);
            flashSaleContainer = view.findViewById(R.id.flashSaleContainer);
        }
    }

    public FlashSaleAdapter(Context context, List<UpgradeHomeScreenFlashSale> flashSaleList, NewHomeFragmentUpgrade upgradeHomeScreen) {
        this.context = context;
        this.flashSaleList = flashSaleList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.flash_sale_list_arabic, parent, false);
            } else {

                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.flash_sale_list, parent, false);
            }
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.flash_sale_list_arabic, parent, false);
        }


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UpgradeHomeScreenFlashSale flashSale = flashSaleList.get(position);
        holder.flashSaleItem.setText(flashSale.getName());

        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                holder.flashSaleItemPrice.setText("SAR " + flashSale.getPrice().toString());
            } else if (MySharedPreferenceClass.getChoosenlanguage(context).equals("en")) {

                holder.flashSaleItemPrice.setText(flashSale.getPrice().toString() + " USD");
            } else {
                holder.flashSaleItemPrice.setText("SAR " + flashSale.getPrice().toString());
            }
        } else {
            holder.flashSaleItemPrice.setText("SAR " + flashSale.getPrice().toString());
        }
        if (flashSale.getOld_price().equals(0)) {
            holder.flashSaleItemOldPrice.setVisibility(View.GONE);
        } else {
            holder.flashSaleItemOldPrice.setVisibility(View.VISIBLE);
            if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
                if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                    holder.flashSaleItemOldPrice.setText("SAR " + flashSale.getOld_price().toString());
                } else if (MySharedPreferenceClass.getChoosenlanguage(context).equals("en")) {

                    holder.flashSaleItemOldPrice.setText(flashSale.getOld_price().toString() + " USD");
                } else {
                    holder.flashSaleItemOldPrice.setText("SAR " + flashSale.getOld_price().toString());
                }
            } else {
                holder.flashSaleItemOldPrice.setText("SAR " + flashSale.getOld_price().toString());
            }
        }

//        Picasso.get().load(flashSale.getImg()).into(holder.flashSaleImage);
        try {
            Glide.with(holder.flashSaleImage.getContext())
                    .load(flashSale.getImg())/*.apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))*/.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).error(R.drawable.progress_animation)
                    .placeholder(R.drawable.progress_animation)
                    .override(1000, 1200)
                    .into(holder.flashSaleImage);
        } catch (Exception e) {
        }
        holder.flashSaleContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Flash sale adapter", flashSaleList.get(position).getName());

                ListItems listItems = new ListItems();

                listItems.name = flashSaleList.get(position).getName();
                listItems.id = String.valueOf(flashSaleList.get(position).getId());
                listItems.price = String.valueOf(flashSaleList.get(position).getPrice());
                listItems.oldPrice = String.valueOf(flashSaleList.get(position).getOld_price());
                listItems.sku = String.valueOf(flashSaleList.get(position).getSku());

                Fragment productDetail = new ProductDetail();
                FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.upgradeHomeLayout, productDetail).addToBackStack(null).commit();
                Bundle databund = new Bundle();
                //put your ArrayList data in bundle
                databund.putString("fromwhere", "fromhome");
                databund.putString("sku", flashSaleList.get(position).getSku());
                databund.putSerializable("catagoryobject", listItems);
                databund.putString("pid", flashSaleList.get(position).getId());

                Log.e("flashSaleList sku", flashSaleList.get(position).getSku());
                Log.e("flashSaleList item_id", flashSaleList.get(position).getId());
                productDetail.setArguments(databund);


            }
        });
    }

    @Override
    public int getItemCount() {
        return flashSaleList.size();
    }
}