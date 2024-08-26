package com.eoutlet.Eoutlet.adpters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.pojo.BrandNameDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter6 extends RecyclerView.Adapter<HomeAdapter6.MyViewHolder> {
   List<BrandNameDetail> mydataset;
    Context mContext;

    ViewListener viewListener;
    ExecuteFragment execute;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        public ImageView iv;

        public MyViewHolder(View view) {
            super(view);


            iv = view.findViewById(R.id.brandImage);


        }}
    public void passData(Context context) {
        mContext = context;
        execute = (MainActivity)mContext;
    }
    public HomeAdapter6(Context context, List<BrandNameDetail> mydataset, ViewListener viewListener) {
        this.mydataset = mydataset;
        mContext = context;
        this.viewListener = viewListener;
        execute = (MainActivity)context;

    }



    @Override
    public HomeAdapter6.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item6, parent, false);

        return new HomeAdapter6.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeAdapter6.MyViewHolder holder, final int position) {

        Picasso.get().load(mydataset.get(position).img).placeholder(R.drawable.placeholder).into(holder.iv);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewListener.onClick(position,v);
            }
        });


        //Picasso.get().load(mydataset[position]).into(holder.iv);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mydataset.size();
    }


}
