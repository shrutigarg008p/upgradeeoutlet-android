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

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.intrface.ViewlistenerCurrency;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.listener.ViewListener4;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.Alphanumeric;
import com.eoutlet.Eoutlet.pojo.BrandNameDetail;
import com.eoutlet.Eoutlet.pojo.ExchangeRate;
import com.eoutlet.Eoutlet.pojo.countryCodeDetail;
import com.eoutlet.Eoutlet.utility.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.MyViewHolder> {
    ExecuteFragment execute;

    private List<countryCodeDetail> mydataset;
    private List<ExchangeRate> exchangecountrylist;
    private Context context;
    private boolean newdatlisrsetflag = false;

    private List<countryCodeDetail> mynewdataset = new ArrayList<>();
    private List<String> avlaibelecountry;

    View itemView;

    ImageView lastselected;

    private ViewlistenerCurrency viewListener;


    public CurrencyListAdapter(Context context, List<countryCodeDetail> mydataset, List<ExchangeRate> exchangecountrylist,List<String> avlaibelecountry, ViewlistenerCurrency viewListener) {
        this.mydataset = mydataset;
        this.context = context;
        this.avlaibelecountry = avlaibelecountry;



        /*For Live*/
       /* countryCodeDetail addeddetail = new countryCodeDetail();
        if (MySharedPreferenceClass.getChoosenlanguage(context).equals("en")) {
            addeddetail.name ="Other";
        } else {
            addeddetail.name = "آخر";
        }
        addeddetail.flag = "pub/media/mobileapp/usa.png";
        addeddetail.currency = "USD";


        mydataset.add(addeddetail);*/

    /*this.exchangecountrylist = exchangecountrylist;
        for(int i =0;i<exchangecountrylist.size();i++)
        {
            newdatlisrsetflag = false;
            for(int j=0;j<mydataset.size();j++){

                if(exchangecountrylist.get(i).currencyTo.equals(mydataset.get(j).currency)){

                    mynewdataset.add(mydataset.get(j));

                    newdatlisrsetflag = true;


                }
            }
            if(!newdatlisrsetflag){
                countryCodeDetail countrydetail = new countryCodeDetail();

                countrydetail.name = "Other";
                countrydetail.flag = "pub/media/mobileapp/usa.png";
                countrydetail.currency = exchangecountrylist.get(i).currencyTo;


                mynewdataset.add(i,countrydetail);



                newdatlisrsetflag = true;

            }


        }*/


        this.viewListener = viewListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView currencyname;
        public ImageView azimage;
        public ImageView currencyselection, currencyselectionflag, countryslectionbar;
        public CardView remembercardclick;


        public MyViewHolder(View view) {
            super(view);

            currencyname = view.findViewById(R.id.currencyname);
            currencyselection = view.findViewById(R.id.currencyselection);
            currencyselectionflag = view.findViewById(R.id.currencyselectionflag);
            countryslectionbar = view.findViewById(R.id.selectionbar);
            remembercardclick = view.findViewById(R.id.remembercardclick);


        }
    }

    public void passData(Context context) {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        /*For Staging*/
       /* if (MySharedPreferenceClass.getChoosenlanguage(context).equals("en")) {
            mydataset.get(mydataset.size() - 1).name = "Other";
        } else {
            mydataset.get(mydataset.size() - 1).name = "آخر";
        }
        mydataset.get(mydataset.size() - 1).flag = "pub/media/mobileapp/usa.png";
        mydataset.get(mydataset.size() - 1).currency = "USD";*/










        try {
            Glide.with(holder.currencyselectionflag)
                    .load(Constants.upgradedmediaSourceURL + mydataset.get(position).flag).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)


                    .into(holder.currencyselectionflag);
        } catch (Exception e) {
        }

        holder.currencyname.setText(mydataset.get(position).name);


        if (MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()) != null) {
            if (MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()).equals(mydataset.get(position).currency)) {

                holder.countryslectionbar.setVisibility(View.VISIBLE);

                lastselected = holder.countryslectionbar;
            }

        }


        holder.remembercardclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lastselected != null) {
                    lastselected.setVisibility(View.GONE);
                }
                holder.countryslectionbar.setVisibility(View.VISIBLE);
                lastselected = holder.countryslectionbar;

                //MySharedPreferenceClass.setSelectedCurrencyName(itemView.getContext(),exchangecountrylist.get(position).currencyTo);
                //MySharedPreferenceClass.setSelectedCurrencyRate(itemView.getContext(),exchangecountrylist.get(position).rate);


                viewListener.onClick(position, v, mydataset.get(position).currency);


            }
        });


    }

    @Override
    public int getItemCount() {
        return mydataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}