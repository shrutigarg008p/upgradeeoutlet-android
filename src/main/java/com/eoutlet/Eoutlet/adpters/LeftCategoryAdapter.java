package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.fragments.NewCategoryDesignFragments;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;

import java.util.ArrayList;
import java.util.List;

public class LeftCategoryAdapter extends RecyclerView.Adapter<LeftCategoryAdapter.MyViewHolder> {

    private ArrayList<String> categoryname;
    private Context mContext;

    private  TextView lastselctedtextposition;
    private  ImageView lastselctedposition;
    private ViewListener viewlistener;
    private int selectedposition;
    View itemView;
    public LeftCategoryAdapter(Context context, ArrayList<String> categoryname, ViewListener viewlistener,int leftselectionposition) {
        this.categoryname = categoryname;
        mContext = context;
        this.viewlistener = viewlistener;
        selectedposition = leftselectionposition;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView topcatname;
        private ImageView bottomlineImage;

        private LinearLayout parentlayout;

        public MyViewHolder(View view) {
            super(view);

            topcatname = view.findViewById(R.id.topcatname);
            bottomlineImage = view.findViewById(R.id.bottomline);
            parentlayout = view.findViewById(R.id.parentLayout);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return categoryname.size();
    }

    @NonNull
    @Override
    public LeftCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if(MySharedPreferenceClass.getChoosenlanguage(mContext).equals("en")) {
          itemView = LayoutInflater.from(mContext).inflate(R.layout.left_category_adapter, parent, false);
        }
        else{
            itemView = LayoutInflater.from(mContext).inflate(R.layout.left_category_adapter_arabic, parent, false);

        }


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeftCategoryAdapter.MyViewHolder holder, int position) {
        holder.topcatname.setText(categoryname.get(position));
        if (position == 0 && lastselctedtextposition == null && !NewCategoryDesignFragments.isbrandselected) {


           holder.topcatname.setTextColor(Color.parseColor("#D8A664"));

            holder.bottomlineImage.setImageResource(R.drawable.ic_selectcategorybackground);
            lastselctedposition = holder.bottomlineImage;
            lastselctedtextposition = holder.topcatname;

        }
        else if(position == selectedposition ){
            if (lastselctedposition != null) {
                lastselctedposition.setImageResource(R.drawable.ic_greycheck);
                lastselctedtextposition.setTextColor(Color.parseColor("#222B45"));

            }
            holder.topcatname.setTextColor(Color.parseColor("#D8A664"));

            holder.bottomlineImage.setImageResource(R.drawable.ic_selectcategorybackground);
            lastselctedposition = holder.bottomlineImage;
            lastselctedtextposition = holder.topcatname;

           viewlistener.onClick(position,itemView);
        }


        holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (lastselctedtextposition != holder.topcatname) {

                        holder.topcatname.setTextColor(Color.parseColor("#D8A664"));

                        holder.bottomlineImage.setImageResource(R.drawable.ic_selectcategorybackground);

                        if (lastselctedposition != null) {
                            lastselctedposition.setImageResource(R.drawable.ic_greycheck);
                            lastselctedtextposition.setTextColor(Color.parseColor("#222B45"));

                        }
                        lastselctedposition = holder.bottomlineImage;
                        lastselctedtextposition = holder.topcatname;

                    }
                    viewlistener.onClick(position, v);

                /*else{

                    holder.topcatname.setTextColor(Color.parseColor("#D8A664"));

                    holder.bottomlineImage.setImageResource(R.drawable.ic_selectcategorybackground);
                    lastselctedposition = holder.bottomlineImage;
                    lastselctedtextposition = holder.topcatname;

                }*/
            }

        });
    }

    @Override
    public void onBindViewHolder(@NonNull LeftCategoryAdapter.MyViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

    }
}