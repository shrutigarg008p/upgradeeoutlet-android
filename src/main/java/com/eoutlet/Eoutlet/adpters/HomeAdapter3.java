package com.eoutlet.Eoutlet.adpters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.api.request.ListItems;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter3 extends RecyclerView.Adapter<HomeAdapter3.MyViewHolder> {
    List<ListItems> mydataset;
    ViewListener viewListener;
    Context mContext;
    ExecuteFragment execute;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre,oldprice,newprice;
        public ImageView iv;

        public MyViewHolder(View view) {
            super(view);

            iv = view.findViewById(R.id.mostwantedImage);
            title = view.findViewById(R.id.productname3);
            oldprice = view.findViewById(R.id.oldprice3);
            newprice = view.findViewById(R.id.newprice3);




        }}
    public void passData(Context context) {
        mContext = context;
        execute = (MainActivity)mContext;
    }
    public HomeAdapter3(Context context, List<ListItems> mydataset, ViewListener viewlistener) {
        this.mydataset = mydataset;
        mContext = context;
        execute = (MainActivity)context;
        this.viewListener = viewlistener;

    }



    @Override
    public HomeAdapter3.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item3, parent, false);

        return new HomeAdapter3.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeAdapter3.MyViewHolder holder,final int position) {
        holder.title.setText(mydataset.get(position).name);
        try {
            String newoldprice=mydataset.get(position).oldPrice.replace(","," ");

            if(mydataset.get(position).oldPrice.equals("0"))
            {
                holder.oldprice.setVisibility(View.GONE);
            }
            else{
                holder.oldprice.setVisibility(View.VISIBLE);

                holder.oldprice.setText("SAR" + " " + mydataset.get(position).oldPrice);




            }


            //holder.oldprice.setText("SAR" + " " + mydataset.get(position).oldPrice);
           // holder.oldprice.setText("SAR" + " " + (int) Float.parseFloat(newoldprice));
            holder.newprice.setText("SAR" + " " + (int) Float.parseFloat(mydataset.get(position).price));
        }
        catch (Exception e){

            Toast.makeText(mContext,"Price Format is not Correct",Toast.LENGTH_SHORT).show();

        }

        Picasso.get().load(mydataset.get(position).img)/*.placeholder(R.drawable.progress_animation)*//*.resize(480,720)*//*.centerCrop()*/.into(holder.iv);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewListener.onClick(position,v);
            }
        });




    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mydataset.size();
    }


}
