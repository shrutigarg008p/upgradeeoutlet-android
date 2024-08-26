package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.fragments.NewHomeFragmentUpgrade;
import com.eoutlet.Eoutlet.fragments.ProductList;

import com.eoutlet.Eoutlet.pojo.UpgradedHomeScreenMainBanner;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    List<UpgradedHomeScreenMainBanner> mainBannerList = new ArrayList<>();


    public ViewPagerAdapter(Context context, List<UpgradedHomeScreenMainBanner> mainBannerList, NewHomeFragmentUpgrade upgradeHomeScreen) {
        this.context = context;
        this.mainBannerList = mainBannerList;

    }

    @Override
    public int getCount() {
        return mainBannerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        Picasso.get().load(mainBannerList.get(position).getImage()).into(imageView);

//        try {
//            Glide.with(imageView)
//                    .load(mainBannerList.get(position).getImage())/*.apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))*/.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)
//                    .placeholder(R.drawable.progress_animation)/*.override(400, 1200)*/
//
//                    .into(imageView);
//        } catch (Exception e) {
//        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Main Banner Adapter", mainBannerList.get(position).getImage());
                Fragment productList = new ProductList();
                FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.upgradeHomeLayout, productList).addToBackStack(null).commit();
                Bundle databund = new Bundle();
                //put your ArrayList data in bundle
                databund.putInt("productId", Integer.parseInt(mainBannerList.get(position).getId()));
                databund.putSerializable("childeren", (Serializable) mainBannerList);
                databund.putString("fromwhere", "fromhome");
                databund.putString("onClick", "mainBanner");
                productList.setArguments(databund);
            }
        });
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}