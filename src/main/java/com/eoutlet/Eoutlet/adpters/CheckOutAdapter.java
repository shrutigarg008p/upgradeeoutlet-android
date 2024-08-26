package com.eoutlet.Eoutlet.adpters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.pojo.ViewCartData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CheckOutAdapter  extends RecyclerView.Adapter<CheckOutAdapter.MyViewHolder> {

    int mydataset[] = {R.drawable.pro1,R.drawable.pro2,R.drawable.pro3};
    Context mcontext;
    int totalprice,totalpricewithtax;
    ArrayList<String> pics;
    ArrayList<String> name;
    ArrayList<String> price;
    ArrayList<String> size;
    ArrayList<String> quantity;


    private List<ViewCartData> carData;
    ExecuteFragment execute;
    ViewListener viewListener;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView pname,pprice,psize,pquantity;

        public MyViewHolder(View view) {

            super(view);
            productImage = view.findViewById(R.id.product_image);
            pname = view.findViewById(R.id.coproductname);
            pprice = view.findViewById(R.id.conewPrice);
            psize = view.findViewById(R.id.cosize);
            pquantity = view.findViewById(R.id.coquantity);


        }}

    public CheckOutAdapter(ArrayList<String> pics,ArrayList<String> name,ArrayList<String> price,ArrayList<String> size,ArrayList<String> quantity) {

        this.pics = pics;
        this.name = name;
        this.price=price;
        this.size = size;
        this.quantity = quantity;

    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_checkout, parent, false);





        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            Glide.with(holder.productImage)
                    .load(pics.get(position)).apply(RequestOptions.bitmapTransform(new RoundedCorners(70))).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)
                    .placeholder(R.drawable.progress_animation).override(1000, 1500)

                    .into(holder.productImage);
        } catch (Exception e) {
        }
        //Picasso.get().load(pics.get(position)).into(holder.productImage);
        holder.pname.setText(name.get(position).toString());
        holder.pprice.setText("SAR"+" " +price.get(position));
        holder.psize.setText(size.get(position));

        holder.pquantity.setText(quantity.get(position));


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return pics.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

