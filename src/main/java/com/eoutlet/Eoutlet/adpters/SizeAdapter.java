package com.eoutlet.Eoutlet.adpters;

import android.content.Context;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;

import java.util.ArrayList;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.MyViewHolder> {


    private ArrayList<String> sizeData;
    private ArrayList<String> indexData;
    private ArrayList<Integer> salable;

    ExecuteFragment execute;
    Context context;
    ViewListener viewListener;

    private static TextView lastSelected = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;
      FrameLayout sizemain;
      RelativeLayout relativesizemain;

        public MyViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.sizeofItems);
            sizemain = view.findViewById(R.id.sizemain);
            relativesizemain = view.findViewById(R.id.relativesizeview);


        }
    }

    public SizeAdapter(Context context, ArrayList<String> sizeData, ArrayList<String> indexdata,ArrayList<Integer> salable, ViewListener viewListener) {
        this.sizeData = sizeData;
        this.indexData = indexdata;
        this.salable = salable;
        this.context = context;
        lastSelected = null;

        this.viewListener = viewListener;
    }


    @Override
    public SizeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.size_item, parent, false);

        return new SizeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SizeAdapter.MyViewHolder holder, final int position) {


        holder.tv.setText(sizeData.get(position));
        if (position == 0 && salable.get(position)!=0 && lastSelected == null) {

             lastSelected = holder.tv;

            holder.tv.setTextColor(ContextCompat.getColor(context, R.color.white));

            holder.tv.setBackground(context.getResources().getDrawable(R.drawable.red_rectangle2));

        }
        int width =   holder.tv.getMeasuredWidth();;


        System.out.println("Width is--->"+width);

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastSelected != holder.tv) {
                    holder.tv.setTextColor(ContextCompat.getColor(context, R.color.white));
                    holder.tv.setBackground(context.getResources().getDrawable(R.drawable.red_rectangle2));
                    if(lastSelected!=null) {
                        lastSelected.setTextColor(ContextCompat.getColor(context, R.color.colour_black));
                        //lastSelected.setBackground(context.getResources().getDrawable(R.drawable.grey_filled_rectangle2));
                        lastSelected.setBackground(context.getResources().getDrawable(R.drawable.grey_filled_rectangle));
                    }

                    lastSelected = holder.tv;
                    viewListener.onClick(position, v);
                }
            }
        });

        if(salable.get(position)==0)
        {

   // holder.sizemain.setForeground(context.getResources().getDrawable(R.drawable.red_incline_cut_line));
             holder.tv.setEnabled(false);

            holder.tv.setBackground(context.getResources().getDrawable(R.drawable.cut_line_background));

            if (Build.VERSION.SDK_INT < 23) {

                holder.tv.setVisibility(View.GONE);

            }


        }
        else {

            if (Build.VERSION.SDK_INT >= 23) {

                holder.relativesizemain.setForeground(null);


            }








        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return sizeData.size();
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
