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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.api.request.ListItems;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;

import java.util.List;

public class HorizantalProductListAdapter extends RecyclerView.Adapter<HorizantalProductListAdapter.MyViewHolder> {
    List<ListItems> mydataset;
    Context mcontext;
    ViewListener viewListener;
    ExecuteFragment execute;

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
            singleproductclick = view.findViewById(R.id.singleproduct);

        }
    }

    public HorizantalProductListAdapter(Context context, List<ListItems> mydataset, ViewListener viewlistener) {
        this.mydataset = mydataset;
        mcontext = context;
        execute = (MainActivity) context;
        this.viewListener = viewlistener;


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizantal_product_list_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        try {
            holder.productname.setText(mydataset.get(position).name);


            if (mydataset.get(position).oldPrice.equals("0")) {
                holder.oldPrice.setVisibility(View.GONE);
            } else {
                //holder.oldPrice.setVisibility(View.VISIBLE);

                String mainprice = " ";
                if (mydataset.get(position).oldPrice.contains(",")) {
                    mainprice = mydataset.get(position).oldPrice.replaceAll(",", "");

                    holder.oldPrice.setText("SAR" + " " + (int) Float.parseFloat(mainprice));


                } else {

                    holder.oldPrice.setText("SAR" + " " + (int) Float.parseFloat(mydataset.get(position).oldPrice));


                }
                //holder.oldPrice.setText("SAR" + " " + mydataset.get(position).oldPrice);

            }


            /*String mainprice2 =" ";
            if(mydataset.get(position).price.contains(",")){
                mainprice2 = mydataset.get(position).price.replaceAll(",","");

                holder.newPrice.setText("SAR" + " " + (int) Float.parseFloat(mainprice2));



            }
            else {

                holder.newPrice.setText("SAR" + " " + (int) Float.parseFloat(mydataset.get(position).price));



            }*/
            if (mydataset.get(position).price.contains(",")) {
                String price = mydataset.get(position).price.replaceAll(",", "");

                holder.newPrice.setText("SAR" + " " + (int) Float.parseFloat(price));


            } else {
                holder.newPrice.setText("SAR" + " " + (int) Float.parseFloat(mydataset.get(position).price));
            }
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewListener.onClick(position, view);
                    //execute.ExecutFragmentListener(4);
                }
            });
        } catch (Exception e) {


            Log.e("Issue is--->", "Float Number Format");
        }

      //RequestOptions  requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
       try {
            Glide.with(holder.iv)
                    .load(mydataset.get(position).img).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)
                    .placeholder(R.drawable.progress_animation).override(1000, 1500)

                    .into(holder.iv);
        } catch (Exception e) {
        }



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

    @Override
    public long getItemId(int position) {
        return position;
    }

}
