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
import com.eoutlet.Eoutlet.api.request.ListItems;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter5 extends RecyclerView.Adapter<HomeAdapter5.MyViewHolder> {
    List<ListItems> mydataset;
    ViewListener viewListener;

    Context mContext;
    ExecuteFragment execute;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre,oldprice,newprice;
        public ImageView iv;

        public MyViewHolder(View view) {
            super(view);
            iv = view.findViewById(R.id.mainimage);
            title =view.findViewById(R.id.productname5);
            oldprice = view.findViewById(R.id.oldprice5);
            newprice = view.findViewById(R.id.newprice5);





        }}
    public void passData(Context context) {
        mContext = context;
        execute = (MainActivity)mContext;
    }
    public HomeAdapter5(Context context, List<ListItems> mydataset, ViewListener viewListener) {
        this.mydataset = mydataset;
        mContext = context;
        execute = (MainActivity)context;
        this.viewListener=viewListener;


    }



    @Override
    public HomeAdapter5.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item5, parent, false);

        return new HomeAdapter5.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeAdapter5.MyViewHolder holder,final int position) {

        Picasso.get().load(mydataset.get(position).img)./*resize(480,720).*//*placeholder(R.drawable.progress_animation).*//*centerCrop().*/into(holder.iv);
        holder.title.setText(mydataset.get(position).name);
        try {


            if(mydataset.get(position).oldPrice.equals("0"))
            {
                holder.oldprice.setVisibility(View.GONE);
            }
            else{
                holder.oldprice.setVisibility(View.VISIBLE);

                holder.oldprice.setText("SAR" + " " + mydataset.get(position).oldPrice);




            }

            // holder.oldprice.setText("SAR" + " " + (int) Float.parseFloat(mydataset.get(position).oldPrice));
            holder.newprice.setText("SAR" + " " + (int) Float.parseFloat(mydataset.get(position).price));

        }
        catch (Exception e){}
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewListener.onClick(position,v);
            }
        });




        // - get element from your dataset at this position
        // - replace the contents of the view with that element


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mydataset.size();
    }


}
