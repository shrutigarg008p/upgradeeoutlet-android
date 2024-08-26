package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.ReturnItemViewClickReturnItemOptions;

import java.util.List;


public class ReturnItemListViewClickMoreDetails extends RecyclerView.Adapter<ReturnItemListViewClickMoreDetails.SingleItemRowHolder> {

    private Context context;
    List<ReturnItemViewClickReturnItemOptions> options;
    View view;
    String Locale;

    public ReturnItemListViewClickMoreDetails(List<ReturnItemViewClickReturnItemOptions> options, Context context) {
        this.context = context;
        this.options = options;
    }


    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                Locale = "ar";
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.return_item_details_arabic, null);
            } else {
                Locale = "en";
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.return_item_details, null);
            }
        } else {
            Locale = "ar";
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.return_item_details_arabic, null);
        }
        SingleItemRowHolder mh = new SingleItemRowHolder(view);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull SingleItemRowHolder holder, final int position) {
        Log.e("label", options.get(position).getLabel());
        Log.e("value", options.get(position).getValue());
        if (Locale == "ar") {
            holder.itemDetailsTextView.setText(options.get(position).getValue() + " : " + options.get(position).getLabel());
        } else {
            holder.itemDetailsTextView.setText(options.get(position).getLabel() + " : " + options.get(position).getValue());
        }

    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        private TextView itemDetailsTextView;


        public SingleItemRowHolder(@NonNull View itemView) {
            super(itemView);
            itemDetailsTextView = itemView.findViewById(R.id.itemDetailsTextView);
        }
    }
}
