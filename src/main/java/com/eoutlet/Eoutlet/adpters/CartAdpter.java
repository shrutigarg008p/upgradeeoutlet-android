package com.eoutlet.Eoutlet.adpters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.Size;
import com.eoutlet.Eoutlet.pojo.ViewCartData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartAdpter  extends RecyclerView.Adapter<CartAdpter.MyViewHolder> {

    int mydataset[] = {R.drawable.pro1,R.drawable.pro2,R.drawable.pro3};
    Context mcontext;
    List<Size> option;
    View itemView;
   public static ArrayList<Object>  qtyarray = new ArrayList<>();
    int totalprice,totalpricewithtax;
    static  int netqty;



  private List<ViewCartData> carData;
    ExecuteFragment execute;
    ViewListener viewListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  textoutstock, title, oldPrice, newPrice,size,quantity,cart_plus,cart_minace,cart_selectedQuantity;
        public ImageView iv;
       CardView cartcontainer;
       LinearLayout sizealter,mainitemclcik;
        View outstockbar;
        ImageView deleteItemfromCart;
        public MyViewHolder(View view) {
            super(view);



            iv = view.findViewById(R.id.product_image);
            title= view.findViewById(R.id.productname);
            oldPrice = view.findViewById(R.id.oldPrice);
            newPrice = view.findViewById(R.id.newPrice);
            size = view.findViewById(R.id.size);
            cartcontainer = view.findViewById(R.id.cartItemContainer);

           quantity = view.findViewById(R.id.quantity);
           deleteItemfromCart = view.findViewById(R.id.deleteItemfromCart);

           cart_plus = view.findViewById(R.id.cart_plus_button);
           cart_minace = view.findViewById(R.id.cart_minace_button);
           cart_selectedQuantity = view.findViewById(R.id.cart_selectedQuantity);

           outstockbar = view.findViewById(R.id.outstockbar);
           textoutstock = view.findViewById(R.id.textoutstock);
           sizealter =view.findViewById(R.id.sizealter);
           mainitemclcik = view.findViewById(R.id.mainitemclick);

        }}

    public CartAdpter (Context context, List<ViewCartData> cartData, ViewListener viewListener) {

        mcontext = context;
        this.carData = cartData;
        this.totalprice = totalprice;
        this.totalpricewithtax = totalpricewithtax;
         this.viewListener = viewListener;
         this.option = option;
         qtyarray.clear();

    }



    @Override
    public CartAdpter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);

        return new CartAdpter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartAdpter.MyViewHolder holder,final int position) {

int quant;
        try {
            Glide.with(holder.iv)
                    .load(carData.get(position).img).apply(RequestOptions.bitmapTransform(new RoundedCorners(80))).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)
                   /* .placeholder(R.drawable.progress_animation)*/.override(1000, 1500)

                    .into(holder.iv);
        } catch (Exception e) {
        }

        holder.title.setText(carData.get(position).name);
        if(carData.get(position).instock==0){

           holder.outstockbar.setVisibility(View.VISIBLE);
            holder.textoutstock.setVisibility(View.VISIBLE);




        }


        if(carData.get(position).option.size()>0) {
            if (carData.get(position).qty instanceof String) {

            }
            if(carData.get(position).option.get(0).size.label!=null) {
                holder.size.setText(carData.get(position).option.get(0).size.label.toString());
            }

        }
        if (carData.get(position).qty instanceof String) {


            quant = (int)Float.parseFloat((String) carData.get(position).qty);
        }
        else
        {

            quant = (int)carData.get(position).qty;
        }




        try {

                int newprice = (int) Float.parseFloat(carData.get(position).price);
                int oldprice = (int) carData.get(position).old_price;



            if (oldprice == 0 || newprice == oldprice ) {
                holder.oldPrice.setVisibility(View.GONE);
            } else {
                holder.oldPrice.setVisibility(View.VISIBLE);

                holder.oldPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()) + " " +(int) Float.parseFloat(carData.get(position).old_price.toString()));


            }
        }
        catch (Exception e){}


            holder.newPrice.setText((MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext())) + " " + (int) Float.parseFloat(carData.get(position).price));




        holder.quantity.setText(carData.get(position).qty+":");
        holder.cart_selectedQuantity.setText(carData.get(position).qty.toString());

        qtyarray.add(carData.get(position).qty);

        holder.cart_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int quantity = (Integer) qtyarray.get(position);

                int finalquantity = quantity+1;

                System.out.println(finalquantity+"sdasdasdsdasdasd");
                //qtyarray.set(position,finalquantity);
                System.out.println(qtyarray.get(position)+"2312312323");

              //  holder.cart_selectedQuantity.setText(String.valueOf(finalquantity));

                 viewListener.onClick(position,v);




            }
        });

        holder.mainitemclcik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewListener.onClick(position, v);
            }
        });

        holder.cart_minace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int quantity = (Integer) qtyarray.get(position);

                int finalquantity = quantity-1;
                System.out.println(finalquantity + "sdasdasdsdasdasd");

                if((Integer) qtyarray.get(position)>1) {




                    System.out.println(qtyarray.get(position) + "2312312323");

                   // holder.cart_selectedQuantity.setText(String.valueOf(finalquantity));
                    viewListener.onClick(position, v);


                }

                else {

                    viewListener.onClick(position,v);


                }


            }
        });


        holder.deleteItemfromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                viewListener.onClick(position,v);


            }
        });




    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return carData.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
