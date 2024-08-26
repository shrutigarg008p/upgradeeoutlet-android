package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;

import java.util.List;

public class FilterDetailAdapter extends RecyclerView.Adapter<FilterDetailAdapter.MyViewHolder> {


    List<String> catagorylist;
    List<String> selectionflag;
    ViewListener viewListener;
    Context mcontext;
    View itemView;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;
        private ImageView checkselector;
        LinearLayout filtercatagoryclick;

        public MyViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.filtercatagorydetail);
            checkselector = view.findViewById(R.id.checkselctor);
            filtercatagoryclick = view.findViewById(R.id.filtercatagoryclick);

        }
    }

    public FilterDetailAdapter(Context context, List<String> catagorylist, List<String> selectionflag, ViewListener viewListener) {
        this.catagorylist = catagorylist;
        mcontext = context;
        this.selectionflag = selectionflag;
        this.viewListener = viewListener;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (MySharedPreferenceClass.getChoosenlanguage(mcontext) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(mcontext).equals("ar")) {
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.filter_catagory_detail_arabic, parent, false);
            } else {
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.filter_catagory_detail, parent, false);
            }
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.filter_catagory_detail_arabic, parent, false);
        }
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv.setText(catagorylist.get(position));


        if (selectionflag.get(position).equals("false")) {
            holder.checkselector.setImageDrawable(ContextCompat.getDrawable(mcontext, R.drawable.ic_unchecked_full));
            holder.tv.setTextColor(Color.parseColor("#000000"));


        } else if (selectionflag.get(position).equals("true")) {
            holder.checkselector.setImageDrawable(ContextCompat.getDrawable(mcontext, R.drawable.ic_checked));
            holder.tv.setTextColor(Color.parseColor("#D8A664"));

        }

        holder.filtercatagoryclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (selectionflag.get(position).equals("false")) {

                    holder.checkselector.setImageDrawable(ContextCompat.getDrawable(mcontext, R.drawable.ic_checked));
                    holder.tv.setTextColor(Color.parseColor("#D8A664"));
                } else if (selectionflag.get(position).equals("true")) {

                    holder.checkselector.setImageDrawable(ContextCompat.getDrawable(mcontext, R.drawable.ic_unchecked_full));
                    holder.tv.setTextColor(Color.parseColor("#000000"));


                }
                viewListener.onClick(position, holder.filtercatagoryclick);
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

    /*@Override
    public long getItemId(int position) {
        return position;
    }
*/

}
