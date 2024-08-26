package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.listener.ViewListener;

import java.util.ArrayList;
import java.util.List;

public class RightCategoryBannerAdapter extends RecyclerView.Adapter<RightCategoryBannerAdapter.MyViewHolder> {

    private ArrayList<String> categoryname;
    Boolean isbrandselected;
    private Context mContext;
    ViewListener viewlistener;
    Boolean gridViewVisible;


    public RightCategoryBannerAdapter(Context context, ArrayList<String> categoryname, boolean isbrandselected, Boolean gridViewVisible, ViewListener viewlistener) {
        this.categoryname = categoryname;

        mContext = context;
        this.viewlistener = viewlistener;
        this.isbrandselected = isbrandselected;
        this.gridViewVisible = gridViewVisible;

    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView topcatname;
        private ImageView bottomlineImage;
        private LinearLayout parentlayout;



        public MyViewHolder(View view) {
            super(view);


            bottomlineImage =view.findViewById(R.id.bottomline);
            parentlayout = view.findViewById(R.id.parentLayout);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return gridViewVisible == false ? 1 : categoryname.size();
    }

    @NonNull
    @Override
    public RightCategoryBannerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      //  View itemView = LayoutInflater.from(mContext).inflate(R.layout.right_category_adapter, parent, false);

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.right_category_adapter, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RightCategoryBannerAdapter.MyViewHolder holder, int position) {
        if (gridViewVisible) {
            ViewGroup.LayoutParams params = holder.bottomlineImage.getLayoutParams();
            params.height = mContext.getResources().getDimensionPixelSize(R.dimen._110sdp);
            holder.bottomlineImage.setLayoutParams(params);
           // holder.bottomlineImage.getLayoutParams().height = R.dimen._100sdp;
        }
        if (isbrandselected) {
            ViewGroup.LayoutParams params = holder.bottomlineImage.getLayoutParams();
            params.height = mContext.getResources().getDimensionPixelSize(R.dimen._110sdp);
            holder.bottomlineImage.setLayoutParams(params);
            //holder.bottomlineImage.getLayoutParams().height = R.dimen._100sdp;
        }
        try {
            Glide.with(holder.bottomlineImage)
                    .load(categoryname.get(position))/*.placeholder(R.drawable.big_eoutlet_logo)*/.apply(RequestOptions.bitmapTransform(new RoundedCorners(80)).diskCacheStrategy(DiskCacheStrategy.ALL)).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)/*error(R.drawable.progress_animation)*/
                    /* .placeholder(R.drawable.progress_animation)*/.override(1000, 800)
                    .into(holder.bottomlineImage);
        } catch (Exception e) {
        }


        holder.parentlayout.setOnClickListener(v ->     viewlistener.onClick(position, v));
        //Picasso.get().load(categoryname.get(position)).into(holder.bottomlineImage);
    }

    @Override
    public void onBindViewHolder(@NonNull RightCategoryBannerAdapter.MyViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }
}