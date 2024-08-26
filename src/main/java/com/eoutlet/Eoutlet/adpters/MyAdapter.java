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
import com.eoutlet.Eoutlet.fragments.NewHomeFragmentUpgrade;
import com.eoutlet.Eoutlet.fragments.ProductList;

import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.UpgradedHomeScreenHighlightedSlider;

import java.io.Serializable;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<UpgradedHomeScreenHighlightedSlider> highlightedSliderList;
    private Context context;
    private View view;
    NewHomeFragmentUpgrade upgradeHomeScreen;

    public MyAdapter(Context context, List<UpgradedHomeScreenHighlightedSlider> highlightedSliderList, NewHomeFragmentUpgrade upgradeHomeScreen) {
        this.context = context;
        this.highlightedSliderList = highlightedSliderList;
        this.upgradeHomeScreen = upgradeHomeScreen;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                view = LayoutInflater.from(context).inflate(R.layout.center_view_pager_item_arabic, parent, false);
            } else if (MySharedPreferenceClass.getChoosenlanguage(context).equals("en")) {

                view = LayoutInflater.from(context).inflate(R.layout.center_view_pager_item, parent, false);
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.center_view_pager_item_arabic, parent, false);
            }
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.center_view_pager_item_arabic, parent, false);
        }

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UpgradedHomeScreenHighlightedSlider highlightedList = highlightedSliderList.get(position);
        holder.centerViewPagerItem.setText(highlightedList.getCaption());
//        holder.centerViewPagerItemPrice.setText(highlightedList.getPrice());
//        Picasso.get().load(highlightedList.getImage()).into(holder.centerViewPagerImage);
        try {
            Glide.with(holder.centerViewPagerImage.getContext())
                    .load(highlightedList.getImage())/*.apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))*/.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).error(R.drawable.progress_animation)
                    .placeholder(R.drawable.progress_animation)/*.override(400, 1200)*/
                    .into(holder.centerViewPagerImage);
        } catch (Exception e) {
        }
        holder.highlightedSlideContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Highlighted Adapter", highlightedSliderList.get(position).getCaption());
                Fragment productList = new ProductList();
                FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.upgradeHomeLayout, productList).addToBackStack(null).commit();
                Bundle databund = new Bundle();
                //put your ArrayList data in bundle
                databund.putInt("productId", Integer.parseInt(highlightedSliderList.get(position).getId()));
                databund.putSerializable("childeren", (Serializable) highlightedSliderList);
                databund.putString("fromwhere", "fromhome");
                databund.putString("onClick", "highlightedSlider");
                productList.setArguments(databund);
            }
        });

    }

    @Override
    public int getItemCount() {
        return highlightedSliderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView centerViewPagerCategory, centerViewPagerItem, centerViewPagerItemPrice;
        ImageView centerViewPagerImage;
        LinearLayout highlightedSlideContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            centerViewPagerCategory = itemView.findViewById(R.id.centerViewPagerCategory);
            centerViewPagerItem = itemView.findViewById(R.id.centerViewPagerItem);
            centerViewPagerItemPrice = itemView.findViewById(R.id.centerViewPagerItemPrice);
            centerViewPagerImage = itemView.findViewById(R.id.centerViewPagerImage);
            highlightedSlideContainer = itemView.findViewById(R.id.highlightedSlideContainer);
        }
    }
}