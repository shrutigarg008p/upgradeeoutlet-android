package com.eoutlet.Eoutlet.adpters;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.api.request.ListItems;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.pojo.Recommended;
import com.eoutlet.Eoutlet.pojo.Recommendedlist;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter7 extends RecyclerView.Adapter<HomeAdapter7.MyViewHolder> {
List<Recommended> mydataset;
    Context mContext;
    ViewListener viewListener;
    ExecuteFragment execute;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre,oldprice,newprice;
        public ImageView iv;

        public MyViewHolder(View view) {
            super(view);
           iv = view.findViewById(R.id.man_image);
           title=view.findViewById(R.id.productname7);
           oldprice = view.findViewById(R.id.oldprice7);
           newprice = view.findViewById(R.id.newprice7);




        }}
    public void passData(Context context) {
        mContext = context;
        execute = (MainActivity)mContext;
    }
    public HomeAdapter7(Context context, List<Recommended>  mydataset, ViewListener viewListener) {
        this.mydataset = mydataset;
        this.viewListener = viewListener;
        mContext = context;
        execute = (MainActivity)context;

    }



    @Override
    public HomeAdapter7.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item7, parent, false);

        return new HomeAdapter7.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeAdapter7.MyViewHolder holder,final int position) {
   /*holder.title.setText(mydataset.get(position).name);
   try {


       if(mydataset.get(position).oldPrice.equals("0"))
       {
           holder.oldprice.setVisibility(View.GONE);
       }
       else{
           holder.oldprice.setVisibility(View.VISIBLE);

           String mainprice =" ";
           if(mydataset.get(position).oldPrice.contains(",")){
               mainprice = mydataset.get(position).oldPrice.replaceAll(",","");

               holder.oldprice.setText("SAR" + " " + (int) Float.parseFloat(mainprice));



           }
           else {

               holder.oldprice.setText("SAR" + " " + (int) Float.parseFloat(mydataset.get(position).oldPrice));



           }
           //holder.oldprice.setText("SAR" + " " + mydataset.get(position).oldPrice);




       }
       if(mydataset.get(position).price.contains(",")) {

        String newprice = mydataset.get(position).price.replaceAll(",","");


           //holder.oldprice.setText("SAR" + " " + (int) Float.parseFloat(mydataset.get(position).oldPrice));

           holder.newprice.setText("SAR" + " " + (int) Float.parseFloat(newprice));


       }
       else{



           holder.newprice.setText("SAR" + " " + (int) Float.parseFloat(mydataset.get(position).price));



       }

       }
   catch (Exception e){
       Log.e("Exception is",e.toString());


   }
*/

        Glide.with(holder.iv)
                .load(mydataset.get(position).getImage())
     .apply(RequestOptions.bitmapTransform(new RoundedCorners(15))).placeholder(R.drawable.rectangular_border3)
                .into(holder.iv);
        //Picasso.get().load(mydataset.get(position).img)/*.placeholder(R.drawable.progress_animation)*//*.resize(480,720)*//*.centerCrop()*/.into(holder.iv);
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
