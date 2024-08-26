package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.api.request.ListItems;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class NewArrivalAdapter extends RecyclerView.Adapter<NewArrivalAdapter.MyViewHolder> {
    List<ListItems> mydataset;
    Context mcontext;
    ViewListener viewListener;
    ExecuteFragment execute;
    View itemView;
    String Locale;


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (MySharedPreferenceClass.getChoosenlanguage(parent.getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(parent.getContext()).equals("ar")) {
                Locale = "ar";
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.new_arrival_item_arabic, parent, false);
            } else {
                Locale = "en";
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.new_arrival_item, parent, false);
            }
        } else {
            Locale = "ar";
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.new_arrival_item_arabic, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre, productname, oldPrice, newPrice;
        public ImageView iv;
        LinearLayout singleproductclick;

        public MyViewHolder(View view) {
            super(view);
            iv = view.findViewById(R.id.product_list_image);
            productname = view.findViewById(R.id.productname);
            oldPrice = view.findViewById(R.id.oldprice);
            newPrice = view.findViewById(R.id.newprice);
            singleproductclick = view.findViewById(R.id.mainimageclick);


        }
    }

    public NewArrivalAdapter(Context context, List<ListItems> mydataset, ViewListener viewlistener) {
        this.mydataset = mydataset;
        mcontext = context;
        execute = (MainActivity) context;
        this.viewListener = viewlistener;


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        try {
            holder.productname.setText(mydataset.get(position).name);


            if (mydataset.get(position).oldPrice.equals("0")) {
                holder.oldPrice.setVisibility(View.GONE);
            } else {
                holder.oldPrice.setVisibility(View.VISIBLE);

                String mainprice = " ";
                if (mydataset.get(position).oldPrice.contains(",")) {
                    mainprice = mydataset.get(position).oldPrice.replaceAll(",", "");

                    holder.oldPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()) + " " + Math.round(MySharedPreferenceClass.getSelectedCurrencyRate(getApplicationContext())*  Float.parseFloat(mainprice)));


                } else {

                    holder.oldPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()) + " " +Math.round(MySharedPreferenceClass.getSelectedCurrencyRate(getApplicationContext())*  Float.parseFloat(mydataset.get(position).oldPrice)));


                }


            }


            if (mydataset.get(position).price.contains(",")) {
                String price = mydataset.get(position).price.replaceAll(",", "");

                holder.newPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()) + " " +  Math.round(MySharedPreferenceClass.getSelectedCurrencyRate(getApplicationContext())* Float.parseFloat(price)));


            } else {
                holder.newPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()) + " " +  Math.round( MySharedPreferenceClass.getSelectedCurrencyRate(getApplicationContext())* Float.parseFloat(mydataset.get(position).price)));
            }
          /*  holder.singleproductclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    viewListener.onClick(position, view);

                }
            });*/
        } catch (Exception e) {


            Log.e("Issue is--->", "Float Number Format");
        }
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewListener.onClick(position, v);
            }
        });

        try {
            Glide.with(holder.iv)
                    .load(mydataset.get(position).img)/*.apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))*/.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)
                    .placeholder(R.drawable.progress_animation)/*.override(400, 1200)*/

                    .into(holder.iv);
        } catch (Exception e) {
        }


    }

    @Override
    public int getItemCount() {
        return mydataset.size();
    }
}
