package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;

import java.util.List;

public class FilterCatagoryListAdapter extends RecyclerView.Adapter<FilterCatagoryListAdapter.MyViewHolder> {
    List<String> catagorylist;
    ViewListener viewListener;
    TextView lastselected;
    LinearLayout lasteselctedmain;
    Context context;
    List<Integer> countlist;
    int lastselectionlistmain;
    View itemView;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;
        LinearLayout maincatagoryclick;

        public MyViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.listname);
            maincatagoryclick = view.findViewById(R.id.maincatagoryclick);

        }
    }

    public FilterCatagoryListAdapter(Context context, List<String> catagorylist, List<Integer> countlist, int lastselectedposition, ViewListener viewListener) {
        this.context = context;
        this.catagorylist = catagorylist;
        this.viewListener = viewListener;
        this.countlist = countlist;
        this.lastselectionlistmain = lastselectedposition;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.filter_catagory_item_arabic, parent, false);
            } else {
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.filter_catagory_item, parent, false);
            }
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.filter_catagory_item_arabic, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Log.e("list name", catagorylist.get(position));
        if (countlist.size() > 0) {
            if (countlist.get(position) != null) {
                if (countlist.get(position) == 0) {
                    holder.tv.setText(catagorylist.get(position));
                } else {
                    holder.tv.setText("(" + countlist.get(position).toString() + ")" + " " + catagorylist.get(position));
                }
            }
        } else {
        }





        if (position == lastselectionlistmain && lastselected == null) {

            holder.tv.setTextColor(Color.parseColor("#D8A664"));
            holder.maincatagoryclick.setBackgroundColor(Color.parseColor("#FFFFFF"));
            lastselected = holder.tv;
            lasteselctedmain = holder.maincatagoryclick;


        }

        holder.maincatagoryclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewListener.onClick(position, holder.maincatagoryclick);

                if (!holder.tv.equals(lastselected) && !holder.maincatagoryclick.equals(lasteselctedmain)) {
                    holder.tv.setTextColor(Color.parseColor("#D8A664"));
                    holder.maincatagoryclick.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    lastselected.setTextColor(Color.parseColor("#000000"));
                    lasteselctedmain.setBackgroundColor(Color.parseColor("#F5F5F5"));
                    lastselected = holder.tv;
                    lasteselctedmain = holder.maincatagoryclick;

                }


            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return catagorylist.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
