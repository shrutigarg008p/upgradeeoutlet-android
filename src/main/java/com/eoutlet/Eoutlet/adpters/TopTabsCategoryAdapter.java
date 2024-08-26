package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;

import java.util.ArrayList;
import java.util.List;

public class TopTabsCategoryAdapter extends RecyclerView.Adapter<TopTabsCategoryAdapter.MyViewHolder> {

    private ArrayList<String> categoryname;
    private Context mContext;
    private View lastselctedposition;
    private TextView lastselctedtextposition;
    private ViewListener viewListener;
    private int catselectedposition;


    public TopTabsCategoryAdapter (Context context,int selectedposition, ArrayList<String> categoryname, ViewListener viewlistener) {
        this.categoryname = categoryname;
        mContext = context;
        this.viewListener = viewlistener;
        this.catselectedposition = selectedposition;


        }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView topcatname;
        private ImageView bottomlineImage;
        private LinearLayout parentlayout;
        private RelativeLayout topcatitem;



        public MyViewHolder(View view) {
            super(view);

            topcatname = view.findViewById(R.id.topcatname);
            bottomlineImage =view.findViewById(R.id.bottomline);
            parentlayout = view.findViewById(R.id.parentLayout);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return categoryname.size() ;
    }

    @NonNull
    @Override
    public TopTabsCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if(MySharedPreferenceClass.getChoosenlanguage(mContext).equals("en")) {
          itemView = LayoutInflater.from(mContext).inflate(R.layout.top_category_adapter, parent, false);
        }
        else{
            itemView = LayoutInflater.from(mContext).inflate(R.layout.top_category_adapter_arbic, parent, false);

        }


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TopTabsCategoryAdapter.MyViewHolder holder, int position) {
        holder.topcatname.setText(categoryname.get(position));
        if(position==catselectedposition){
           // holder.bottomlineImage.setVisibility(View.VISIBLE);
            holder.parentlayout.setBackground(mContext.getDrawable(R.drawable.yellow_shape_top_radius));
            holder.topcatname.setTextColor(Color.parseColor("#FFFFFF"));

            holder.topcatname.setAlpha(1.00f);

            lastselctedposition =/* holder.bottomlineImage*/holder.parentlayout;
            lastselctedtextposition =  holder.topcatname;
        }


      /*  holder.topcatname.setOnClickListener(new View.OnClickListener() {*/
            holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0){
                    holder.bottomlineImage.setVisibility(View.VISIBLE);
                    holder.topcatname.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.topcatname.setAlpha(1.00f);
                    // lastselctedposition.setVisibility(View.GONE);
                    lastselctedposition.setBackground(mContext.getDrawable(R.drawable.white_rectangle_corners));
                    lastselctedtextposition.setTextColor(Color.parseColor("#222B45"));
                    lastselctedtextposition.setAlpha(0.5f);
                    lastselctedposition = holder.parentlayout;
                    lastselctedtextposition = holder.topcatname;
                    holder.parentlayout.setBackground(mContext.getDrawable(R.drawable.yellow_shape_top_radius));
                    viewListener.onClick(position, v);
                }
                else if(lastselctedtextposition!=holder.topcatname) {
                    holder.bottomlineImage.setVisibility(View.VISIBLE);
                    holder.topcatname.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.topcatname.setAlpha(1.00f);
                   // lastselctedposition.setVisibility(View.GONE);
                    lastselctedposition.setBackground(mContext.getDrawable(R.drawable.white_rectangle_corners));
                    lastselctedtextposition.setTextColor(Color.parseColor("#222B45"));
                    lastselctedtextposition.setAlpha(0.5f);
                    lastselctedposition = holder.parentlayout;
                    lastselctedtextposition = holder.topcatname;
                    holder.parentlayout.setBackground(mContext.getDrawable(R.drawable.yellow_shape_top_radius));
                     viewListener.onClick(position, v);
                }
            }
        });

    }

    


}