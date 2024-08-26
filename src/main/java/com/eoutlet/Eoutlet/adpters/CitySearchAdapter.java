package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.fragments.UpgradeMapFragment;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;

import java.util.ArrayList;
import java.util.List;


public class CitySearchAdapter extends RecyclerView.Adapter<CitySearchAdapter.SingleItemRowHolder> {
    private Context context;
    List<String> cityList;
    List<String> cityListBackend;
    String Locale;
    View v;
    UpgradeMapFragment upgradeMapFragment;

    public CitySearchAdapter(Context context, List<String> cityList, List<String> cityListBackend, UpgradeMapFragment upgradeMapFragment) {
        this.context = context;
        this.cityList = cityList;
        this.cityListBackend = cityListBackend;
        this.upgradeMapFragment = upgradeMapFragment;
    }


    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                v = LayoutInflater.from(context).inflate(R.layout.city_search_list_item_arabic, parent, false);
                Locale = "ar";
            } else {
                v = LayoutInflater.from(context).inflate(R.layout.city_search_list_item, parent, false);
                Locale = "en";
            }
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.city_search_list_item_arabic, parent, false);
            Locale = "ar";
        }
        return new SingleItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleItemRowHolder holder, final int position) {
        holder.cityName.setText(cityList.get(position));
        holder.cityNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((UpgradeMapFragment) upgradeMapFragment).setCityName(cityList.get(position),  cityListBackend.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        TextView cityName;
        LinearLayout cityNameLayout;

        public SingleItemRowHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cityName);
            cityNameLayout = itemView.findViewById(R.id.cityNameLayout);
        }
    }

    public void filterList(ArrayList<String> filteredList) {
        cityList = filteredList;
        cityListBackend = filteredList;
        notifyDataSetChanged();
    }
}
