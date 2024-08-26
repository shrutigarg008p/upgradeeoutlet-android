package com.eoutlet.Eoutlet.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.UpgradeReturnResponseOrderFormOptions;

import java.util.List;

public class UpgradeReturnOrderMoreDetailsAdapter extends RecyclerView.Adapter<UpgradeReturnOrderMoreDetailsAdapter.SingleItemRowHolder> {

    private Context context;
    List<UpgradeReturnResponseOrderFormOptions> options;
    View view;
    String Locale;

    public UpgradeReturnOrderMoreDetailsAdapter(List<UpgradeReturnResponseOrderFormOptions> options, Context context) {
        this.context = context;
        this.options = options;
    }


    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                Locale = "ar";
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upgrade_return_order_items_details_arabic, null);
            } else {
                Locale = "en";
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upgrade_return_order_items_details, null);
            }
        } else {
            Locale = "ar";
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upgrade_return_order_items_details_arabic, null);
        }
        SingleItemRowHolder mh = new SingleItemRowHolder(view);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull SingleItemRowHolder holder, final int position) {
        holder.productDetailsTextView.setText(Locale == "ar" ? options.get(position).getValue() + " : " + options.get(position).getLabel() : options.get(position).getLabel() + " : " + options.get(position).getValue());

    }

    @Override
    public int getItemCount() {
        return (null != options ? options.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        private TextView productDetailsTextView;


        public SingleItemRowHolder(@NonNull View itemView) {
            super(itemView);
            productDetailsTextView = itemView.findViewById(R.id.productDetailsTextView);
        }
    }
}
