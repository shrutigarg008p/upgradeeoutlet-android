package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;

import java.util.List;

public class HelpSupportAdapter extends RecyclerView.Adapter<HelpSupportAdapter.MyViewHolder> {

    Context context;
    private List<String> profiletext;
    View itemView;
    String Locale;
    List<Integer> profileIcon;
    ExecuteFragment execute;
    int mydataset[];
    ViewListener viewlistener;

    public HelpSupportAdapter(Context context, List<String> profileString, ViewListener viewListener) {
        this.context = context;
        this.profiletext = profileString;
        this.viewlistener = viewListener;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        public ImageView iv;
        RelativeLayout helpItemClick;


        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.helpitemName);
            helpItemClick = view.findViewById(R.id.helpItemClick);
        }
    }

    public void passData(Context context) {
        context = context;
        execute = (MainActivity) context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                Locale = "ar";
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_support_item_arabic, parent, false);
            } else {
                Locale = "en";
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_support_item, parent, false);
            }
        } else {
            Locale = "ar";
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_support_item_arabic, parent, false);
        }
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.title.setText(profiletext.get(position));
        //Picasso.get().load(profileIcon.get(position)).into(holder.iv);

        holder.helpItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewlistener.onClick(position, v);
            }
        });
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return profiletext.size();
    }
}