package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;

import java.util.List;

public class HomeAdapter1 extends RecyclerView.Adapter<HomeAdapter1.MyViewHolder> {
List<String> mydataset;
    Context mContext;
    ExecuteFragment execute;
    ViewListener viewListener;
    TextView lastselectedtextview;

    int lastselectedposution = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre,discount;
    TextView categoryButton;

        LinearLayout onSelection;

        public MyViewHolder(View view) {
            super(view);

            categoryButton = view.findViewById(R.id.categoryButton);



        }}
    public void passData(Context context) {
        mContext = context;
        execute = (MainActivity)mContext;
    }
    public HomeAdapter1(Context context, List<String>  mydataset, ViewListener viewListener) {
        this.mydataset = mydataset;
        mContext = context;
        execute = (MainActivity)context;
        this.viewListener = viewListener;

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item1, parent, false);*/
        View itemView;

        if(MySharedPreferenceClass.getChoosenlanguage(parent.getContext()).equals("ar")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_list, parent, false);
        }
        else{
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_list_english, parent, false);
        }
        return new MyViewHolder(itemView);



    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.categoryButton.setText(mydataset.get(position));
      //holder.title.setText(mydataset.get(position));
      if(position==0  && lastselectedtextview==null){



          //lastselectedtextview = holder.categoryButton;




      }
       // holder.discount.setText(mydataset.get(position).caption);


        holder.categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (lastselectedtextview == null  ) {
                    holder.categoryButton.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.categoryButton.setBackgroundResource(R.drawable.ic_golden_background);
                    lastselectedtextview = holder.categoryButton;
                }
            else if(lastselectedtextview!=holder.categoryButton) {
                holder.categoryButton.setTextColor(Color.parseColor("#FFFFFF"));
                holder.categoryButton.setBackgroundResource(R.drawable.ic_golden_background);
                lastselectedtextview.setTextColor(Color.parseColor("#000000"));
                lastselectedtextview.setBackgroundResource((R.drawable.mybutton));
                lastselectedtextview = holder.categoryButton;

            }


                viewListener.onClick(position, v);
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

    @Override
    public long getItemId(int position) {
        return position;
    }




}
