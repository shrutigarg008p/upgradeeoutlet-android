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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.api.request.ListItems;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;

import java.util.List;

public class RelatedProductAdapter extends RecyclerView.Adapter<RelatedProductAdapter.MyViewHolder> {
    List<ListItems> mydataset;
    Context mContext;
    ViewListener viewListener;
    ExecuteFragment execute;
    View itemView;

    String locale= " ";
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre, oldprice, newprice;
        public ImageView iv;

        public MyViewHolder(View view) {
            super(view);
            iv = view.findViewById(R.id.man_image);
            title = view.findViewById(R.id.productname7);
            oldprice = view.findViewById(R.id.oldprice7);
            newprice = view.findViewById(R.id.newprice7);


        }
    }

    public void passData(Context context) {
        mContext = context;
        execute = (MainActivity) mContext;
    }

    public RelatedProductAdapter(Context context, List<ListItems> mydataset, ViewListener viewListener) {
        this.mydataset = mydataset;
        this.viewListener = viewListener;
        mContext = context;
        execute = (MainActivity) context;

    }


    @Override
    public RelatedProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (MySharedPreferenceClass.getChoosenlanguage(parent.getContext()).equals("ar")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.related_product_adapter, parent, false);

        } else {

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.related_product_adapter_englsih, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RelatedProductAdapter.MyViewHolder holder, final int position) {
        holder.title.setText(mydataset.get(position).name);
        try {


            if (mydataset.get(position).oldPrice.equals("0")) {
                holder.oldprice.setVisibility(View.GONE);
            } else {
                holder.oldprice.setVisibility(View.VISIBLE);

                String mainprice = " ";
                if (mydataset.get(position).oldPrice.contains(",")) {
                    mainprice = mydataset.get(position).oldPrice.replaceAll(",", "");

                    holder.oldprice.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()) + " " + Math.round(Float.parseFloat(mainprice) * MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext())));


                } else {


                    holder.oldprice.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()) + " " + Math.round(Float.parseFloat(mydataset.get(position).oldPrice) * MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext())));


               }


            }
            if (mydataset.get(position).price.contains(",")) {

                String newprice = mydataset.get(position).price.replaceAll(",", "");


                holder.newprice.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()) + " " + Math.round(Float.parseFloat(newprice) * MySharedPreferenceClass.getSelectedCurrencyRate(mContext)));


            } else {

                int priceaccordingtocurrency = Math.round(((Float.parseFloat(mydataset.get(position).price)*MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext()))));

               Log.e("priceis", mydataset.get(position).price);
                holder.newprice.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()) + " " + priceaccordingtocurrency);


            }

        } catch (Exception e) {
            Log.e("Exception is", e.toString());


        }


        Glide.with(holder.iv)
                .load(mydataset.get(position).img).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)
                .placeholder(R.drawable.progress_animation).override(1000, 1500)
                /*.apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))*/
                .into(holder.iv);
        //Picasso.get().load(mydataset.get(position).img)/*.placeholder(R.drawable.progress_animation)*//*.resize(480,720)*//*.centerCrop()*/.into(holder.iv);
        holder.iv.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
