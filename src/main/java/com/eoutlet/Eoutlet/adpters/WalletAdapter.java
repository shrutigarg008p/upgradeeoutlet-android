package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.UpgradedTransaction;

import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.MyViewHolder> {

    ExecuteFragment execute;
    View itemView;
    private List<UpgradedTransaction> transaction;
    String Locale;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView orderid,amount,date,type;
        public ImageView iv;
        LinearLayout onSelection;

        public MyViewHolder(View view) {
            super(view);
          orderid  = view.findViewById(R.id.latorderid);
          amount= view.findViewById(R.id.latamount);
            date = view.findViewById(R.id.latdate);
            type = view.findViewById(R.id.type);


        }
    }

    public WalletAdapter(Context context, List<UpgradedTransaction> mydataset) {


        execute = (MainActivity) context;
        transaction = mydataset;


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_wallet_item, parent, false);*/
        if (MySharedPreferenceClass.getChoosenlanguage(parent.getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(parent.getContext()).equals("ar")) {
                Locale = "ar";
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.new_wallet_item, parent, false);
            } else {
                Locale = "en";
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.new_wallet_item_arabic, parent, false);
            }
        } else {
            Locale = "ar";
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.new_wallet_item, parent, false);
        }
        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, final int position) {


        holder.orderid.setText("ORDER #"+" "+transaction.get(position).orderId);
        holder.amount.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()) +" "+Math.round((transaction.get(position).amount)*MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext())));

            holder.type.setText(transaction.get(position).action.toString());
        if(transaction.get(position).actionFlag.equals("1")) {
            holder.type.setTextColor(Color.parseColor("#A63735"));

        }


        //holder.date.setText(transaction.get(position).createAt.split(" ")[0]);

        holder.date.setText(transaction.get(position).createAt);
        /* holder.orderid.setText("Order"+ "201667677");
        holder.amount.setText("SAR"+" "+"270.0");
        holder.date.setText("20/07/2018");*/


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //return 10;
        return transaction.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}