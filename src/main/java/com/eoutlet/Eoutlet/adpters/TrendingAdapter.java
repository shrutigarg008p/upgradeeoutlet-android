package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.pojo.Datum;
import com.eoutlet.Eoutlet.pojo.Datum2;
import com.eoutlet.Eoutlet.pojo.Trending;

import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.MyViewHolder> {
    List<Datum2> mydataset;
    Context mContext;
    ExecuteFragment execute;
    ViewListener viewListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre,discount,trendingtetxt;
        public ImageView iv;
        LinearLayout onSelection;

        public MyViewHolder(View view) {
            super(view);
            iv = view.findViewById(R.id.home1);
            title = view.findViewById(R.id.catagoryname1);
            discount = view.findViewById(R.id.discount);
            onSelection = view.findViewById(R.id.selectCatagory1);
            trendingtetxt = view.findViewById(R.id.trendingtext);




        }}
    public void passData(Context context) {
        mContext = context;
        execute = (MainActivity)mContext;
    }
    public TrendingAdapter(Context context, List<Datum2>  mydataset, ViewListener viewListener) {
        this.mydataset = mydataset;
        mContext = context;
        execute = (MainActivity)context;
        this.viewListener = viewListener;

    }



    @Override
    public TrendingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trending_adapter_item, parent, false);

        return new TrendingAdapter.MyViewHolder(itemView);



    }

    @Override
    public void onBindViewHolder(TrendingAdapter.MyViewHolder holder, final int position) {


        Glide.with(holder.iv)
                .load(mydataset.get(position).image)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(55)))
                .into(holder.iv);

        // Picasso.get().load(mydataset.get(position).image).resize(230,230).transform(new RoundedCornersTransform(this)).into(holder.iv);

       // holder.title.setText(mydataset.get(position).name);
       // holder.discount.setText(mydataset.get(position).caption);
        holder.trendingtetxt.setText(mydataset.get(position).name);


        holder.onSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewListener.onClick(position, v);
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mydataset.size();
    }








}
