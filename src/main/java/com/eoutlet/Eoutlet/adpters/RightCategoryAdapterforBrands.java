package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;

import java.util.ArrayList;
import java.util.List;

public class RightCategoryAdapterforBrands extends RecyclerView.Adapter<RightCategoryAdapterforBrands.MyViewHolder> {

    private ArrayList<String> categoryname;
    private Context mContext;
    ArrayList<String> bannername;
    ViewListener viewlistener;




    public RightCategoryAdapterforBrands(Context context, ArrayList<String> categoryname, ArrayList<String> bannername, ViewListener viewlistener) {
        this.categoryname = categoryname;
        this.bannername = bannername;
        mContext = context;
        this.viewlistener =viewlistener;

        }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView topcatname,bannername;
        private ImageView bottomlineImage;
        private LinearLayout parentlayout;



        public MyViewHolder(View view) {
            super(view);


            bottomlineImage =view.findViewById(R.id.bottomline);
            parentlayout = view.findViewById(R.id.parentLayout);
            bannername = view.findViewById(R.id.bannername);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return categoryname.size() ;
    }

    @NonNull
    @Override
    public RightCategoryAdapterforBrands.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView;
        if(MySharedPreferenceClass.getChoosenlanguage(parent.getContext()).equals("en")) {

         itemView = LayoutInflater.from(mContext).inflate(R.layout.category_brand_item, parent, false);
        }
        else{
        itemView = LayoutInflater.from(mContext).inflate(R.layout.category_brand_item_arabic, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RightCategoryAdapterforBrands.MyViewHolder holder, int position) {
       /* try {
            Glide.with(holder.bottomlineImage)
                    .load(categoryname.get(position)).apply(RequestOptions.bitmapTransform(new RoundedCorners(80))).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)*//*error(R.drawable.progress_animation)*//*
                   *//* .placeholder(R.drawable.progress_animation)*//*.override(1000, 800)
                    .into(holder.bottomlineImage);
        } catch (Exception e) {
        }*/
        holder.bannername.setText(bannername.get(position));
        holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    viewlistener.onClick(position, v);
                }

        });
        //Picasso.get().load(categoryname.get(position)).into(holder.bottomlineImage);
    }

    @Override
    public void onBindViewHolder(@NonNull RightCategoryAdapterforBrands.MyViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }
}