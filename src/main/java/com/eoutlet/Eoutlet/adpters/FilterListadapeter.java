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
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.pojo.BrandNameDetail;
import com.eoutlet.Eoutlet.pojo.Child;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FilterListadapeter extends RecyclerView.Adapter<FilterListadapeter.MyViewHolder> {
    List<String> mydataset;
    Context mContext;
    private int counter;

    private int selectedtopcategory;

    ViewListener viewListener;
    ExecuteFragment execute;

    LinearLayout lastselcted;
    TextView lasttextselected;
    ImageView lastImageselected;
    ImageView lasticonselcted;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, counterview;
        public ImageView iv;

        LinearLayout filterlistclick;


        public MyViewHolder(View view) {
            super(view);


            iv = view.findViewById(R.id.filterlisticon);
            title = view.findViewById(R.id.childname);
            filterlistclick = view.findViewById(R.id.filterlistclick);
            counterview = view.findViewById(R.id.counter);


        }
    }

    public FilterListadapeter(Context context, List<String> mydataset, int counter, int selectedtopcategory, ViewListener viewListener) {
        this.mydataset = mydataset;
        mContext = context;
        this.viewListener = viewListener;
        execute = (MainActivity) context;

        this.selectedtopcategory = selectedtopcategory;
        this.counter = counter;

    }

    @Override
    public FilterListadapeter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filterlist, parent, false);

        return new FilterListadapeter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(FilterListadapeter.MyViewHolder holder, final int position) {
       /* if (position == 0) {
            if (counter != 0) {

                holder.counterview.setVisibility(View.VISIBLE);
                holder.counterview.setText(String.valueOf("(" + counter + ")"));
                holder.iv.setImageResource(R.drawable.new_filter_white);


            }


            if (selectedtopcategory == 0) {
                holder.iv.setImageResource(R.drawable.new_filter_white);

            } else {
                holder.iv.setImageResource(R.drawable.new_filter_black);

            }


        }*/
        if (selectedtopcategory != 99) {
            if (selectedtopcategory == position) {


               /* if (position == 0) {
                    holder.iv.setImageResource(R.drawable.new_filter_white);
                } else if (position == 1) {
                    holder.iv.setImageResource(R.drawable.ic_sort_icon_white);


                }*/

                //holder.counterview.setTextColor(Color.parseColor("#ffffff"));
                holder.title.setTextColor(Color.parseColor("#FFFFFF"));
                holder.filterlistclick.setBackgroundResource(R.drawable.rectangular_border_black2);

                lastselcted = holder.filterlistclick;
               lasttextselected = holder.title;
            }
        }


        holder.title.setText(mydataset.get(position));
/*

        if (position == 0 || position == 1) {


            holder.iv.setVisibility(View.VISIBLE);
        } else {

            holder.iv.setVisibility(View.GONE);
        }

*/

        holder.filterlistclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (lastselcted != holder.filterlistclick) {


                    holder.filterlistclick.setBackgroundResource(R.drawable.rectangular_border_black2);
                    holder.title.setTextColor(Color.parseColor("#FFFFFF"));

                    if (lasttextselected != null && lastselcted != null) {
                        lastselcted.setBackgroundResource(R.drawable.rectangular_border_corner2);
                        lasttextselected.setTextColor(Color.parseColor("#000000"));
                    }

                   /* if (position == 0) {

                        holder.iv.setImageResource(R.drawable.new_filter_white);

                        if (lasticonselcted != null) {

                            lasticonselcted.setImageResource(R.drawable.ic_sort_icon_black);

                        }
                        lasticonselcted = holder.iv;


                    } else if (position == 1) {

                        holder.iv.setImageResource(R.drawable.ic_sort_icon_white);
                        if (lasticonselcted != null) {

                            lasticonselcted.setImageResource(R.drawable.new_filter_black);

                        }
                        lasticonselcted = holder.iv;


                    }*/

                    lastselcted = holder.filterlistclick;
                    lasttextselected = holder.title;

                    viewListener.onClick(position, v);
                }



            }

        });


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
}



