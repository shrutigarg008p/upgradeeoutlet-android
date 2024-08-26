package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
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
import com.eoutlet.Eoutlet.listener.ViewListener4;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.Alphanumeric;
import com.eoutlet.Eoutlet.pojo.BrandNameDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AZAdapter extends RecyclerView.Adapter<AZAdapter.MyViewHolder> {
    ExecuteFragment execute;

    private List<Alphanumeric> mydataset;
    private ArrayList<String> alphabetic;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();
    View itemView;
    String Locale;


    private int totalrowsize;
    HashMap<String, List<BrandNameDetail>> allnames = new HashMap<>();
    Context mContext;
    private

    ViewListener4 viewListener;


    public AZAdapter(Context context, List<Alphanumeric> mydataset, ArrayList<String> alphabetic, HashMap<String, List<BrandNameDetail>> allnames, int totaolrowsize, ViewListener4 viewListener) {
        this.mydataset = mydataset;
        this.alphabetic = alphabetic;
        this.allnames = allnames;
        this.totalrowsize = totaolrowsize;
        mContext = context;
        this.viewListener = viewListener;
        execute = (MainActivity) context;

        for (int i = 0; i < alphabetic.size(); i++) {
            names.add(alphabetic.get(i));
            ids.add("999");

            for (int j = 0; j < allnames.get(alphabetic.get(i)).size(); j++) {

                names.add(allnames.get(alphabetic.get(i)).get(j).name);
                ids.add(allnames.get(alphabetic.get(i)).get(j).id);


            }


        }


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView aztext, year, genre;
        public ImageView azimage;
        LinearLayout parentLayout;

        public MyViewHolder(View view) {
            super(view);
            parentLayout = view.findViewById(R.id.parentLayout);
            // azimage = view.findViewById(R.id.azimage);
            aztext = view.findViewById(R.id.aztext);
        }
    }

    public void passData(Context context) {
        mContext = context;
        execute = (MainActivity) mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (MySharedPreferenceClass.getChoosenlanguage(mContext) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(mContext).equals("ar")) {
                Locale = "ar";
                itemView = LayoutInflater.from(mContext).inflate(R.layout.item_az_row_arabic, parent, false);
            } else {
                Locale = "en";
                itemView = LayoutInflater.from(mContext).inflate(R.layout.item_az_row, parent, false);
            }
        } else {
            Locale = "ar";
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_az_row_arabic, parent, false);
        }


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //DisplayMetrics displayMetrics = new DisplayMetrics();
        //((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //int height = displayMetrics.heightPixels;
        //int width = displayMetrics.widthPixels;
        //int spacingBetweenItems =dpTopixel(16,mContext);
        //int spacingMargin = dpTopixel(16,mContext);
        // int span_count = 2;
        //int imageWidth = (width - 1*spacingBetweenItems - 2*spacingMargin )/span_count;


        // holder.parentLayout.getLayoutParams().width = imageWidth;

        //holder.aztext.setText(mydataset.get(position).data.get(position).name);

        if (ids.size() > position) {
            if (ids.get(position).equals("999")) {

                holder.aztext.setTextColor(Color.parseColor("#D8A664"));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
               if(MySharedPreferenceClass.getChoosenlanguage(mContext).equals("ar")) {
                   params.setMargins(10, 10, 50, 10);
               }
               else{
                   params.setMargins(50, 10, 10, 10);
               }

                holder.aztext.setLayoutParams(params);


            }
        }

       /* if(names.get(position).length()==1){

            holder.aztext.setTextColor(Color.parseColor("#D8A664"));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(50,10,10,10);
            holder.aztext.setLayoutParams(params);



        }*/


        holder.aztext.setText(names.get(position));


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewListener.onClick(position, v, names.get(position), ids.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return totalrowsize;
    }

    public int dpTopixel(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}