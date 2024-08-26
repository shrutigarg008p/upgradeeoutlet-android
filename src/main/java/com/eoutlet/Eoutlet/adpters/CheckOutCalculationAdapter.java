package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.TotalSegment;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CheckOutCalculationAdapter extends RecyclerView.Adapter<CheckOutCalculationAdapter.MyViewHolder> {
    List<TotalSegment> mydataset;
    Context mContext;
    ExecuteFragment execute;
    ViewListener viewListener;
    TextView lastselectedtextview;
    View itemView;
    int lastselectedposution = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, totalprice, discount;
        public RelativeLayout parentlayout;
        public ImageView iv;
        LinearLayout onSelection;

        public MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.title);
            totalprice = view.findViewById(R.id.totalprice);
            parentlayout = view.findViewById(R.id.parentlayout);


        }
    }

    public void passData(Context context) {
        mContext = context;
        execute = (MainActivity) mContext;
    }

    public CheckOutCalculationAdapter(Context context, List<TotalSegment> mydataset, ViewListener viewListener) {
        this.mydataset = mydataset;
        mContext = context;
        execute = (MainActivity) context;
        this.viewListener = viewListener;

    }


    @Override
    public CheckOutCalculationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.checkout_calculation_item, parent, false);
        } else {

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.checkout_calculation_arabic, parent, false);

        }

        return new CheckOutCalculationAdapter.MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(CheckOutCalculationAdapter.MyViewHolder holder, final int position) {
        if (mydataset.get(position).value == 0 || mydataset.get(position).code.equals("tax")   ) {

            if (!mydataset.get(position).code.equals("shipping")){
                holder.parentlayout.setVisibility(View.GONE);
            holder.totalprice.setVisibility(View.GONE);
            holder.title.setVisibility(View.GONE);
        }

            holder.title.setText(mydataset.get(position).title);

            int priceaccordingtocurrency = Math.round((Float.parseFloat(mydataset.get(position).value.toString()) * MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext())));

            if(!mydataset.get(position).code.equals("discount")) {
                holder.totalprice.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()) + " " + Math.round(Float.parseFloat(mydataset.get(position).value.toString())));
            }

            else{
                holder.totalprice.setText("-"+" "+MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()) + " " +Math.abs( Math.round(Float.parseFloat(mydataset.get(position).value.toString()))));
            }
        }

        else {
            holder.title.setText(mydataset.get(position).title);

            int priceaccordingtocurrency = Math.round((Float.parseFloat(mydataset.get(position).value.toString()) * MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext())));

           if(!mydataset.get(position).code.equals("discount")) {
               holder.totalprice.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()) + " " + Math.round(Float.parseFloat(mydataset.get(position).value.toString())));
           }

           else{
               holder.totalprice.setText("-"+" "+MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()) + " " +Math.abs( Math.round(Float.parseFloat(mydataset.get(position).value.toString()))));
           }
        }



        // holder.totalprice.setText(mydataset.get(position).value.toString());


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
