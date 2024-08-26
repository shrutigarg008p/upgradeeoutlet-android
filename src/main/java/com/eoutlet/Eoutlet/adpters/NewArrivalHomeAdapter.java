package com.eoutlet.Eoutlet.adpters;

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

import com.eoutlet.Eoutlet.fragments.ProductList;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.pojo.Datum;
import com.eoutlet.Eoutlet.pojo.EditorChoice;

import java.util.List;

public class NewArrivalHomeAdapter extends RecyclerView.Adapter<NewArrivalHomeAdapter.MyViewHolder> {
    private List<Datum> newArrivalItems;
   Object upgradeHomeScreen;
    private Context context;
    private ViewListener viewListener;
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView newarrivalHomeItem;

        MyViewHolder(View view) {
            super(view);
            newarrivalHomeItem = view.findViewById(R.id.home_recycler_image);
        }
    }

    public NewArrivalHomeAdapter(Context context, List<Datum> newarrivalitem, ViewListener viewlistener) {
        this.context = context;
        this.newArrivalItems = newarrivalitem;
        this.viewListener = viewlistener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_arrival_home_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {
            Glide.with(holder.newarrivalHomeItem.getContext())
                    .load(newArrivalItems.get(position).image)/*.apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))*/.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).error(R.drawable.progress_animation)
                    .placeholder(R.drawable.progress_animation)
                    .override(1000, 1200)
                    .into(holder.newarrivalHomeItem);
        } catch (Exception e) {
        }
        holder.newarrivalHomeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                viewListener.onClick(position,v);

            }

        });

    }

    @Override
    public int getItemCount() {
        return newArrivalItems.size();
    }
}