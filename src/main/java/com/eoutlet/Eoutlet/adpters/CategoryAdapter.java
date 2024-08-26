package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.fragments.NewHomeFragmentUpgrade;
import com.eoutlet.Eoutlet.fragments.ProductList;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.Child;
import com.eoutlet.Eoutlet.pojo.UpgradedHomeScreenCategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<UpgradedHomeScreenCategory> categoryList;
    private Context mContext;
    NewHomeFragmentUpgrade upgradeHomeScreen;
    private List<List<Child>> childerenofcatagory = new ArrayList<>();
    String Locale;
    View itemView;


    class MyViewHolder extends RecyclerView.ViewHolder {
        Button categoryButton;

        MyViewHolder(View view) {
            super(view);
            categoryButton = view.findViewById(R.id.categoryButton);
        }
    }

    public CategoryAdapter(List<UpgradedHomeScreenCategory> categoryList, Context context, NewHomeFragmentUpgrade upgradeHomeScreen) {
        this.categoryList = categoryList;
        this.mContext = context;
        this.upgradeHomeScreen = upgradeHomeScreen;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (MySharedPreferenceClass.getChoosenlanguage(mContext) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(mContext).equals("ar")) {
                Locale = "ar";
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.category_list_arabic, parent, false);
            } else {
                Locale = "en";
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.category_list, parent, false);
            }
        } else {
            Locale = "ar";
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_list_arabic, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UpgradedHomeScreenCategory category = categoryList.get(position);
        holder.categoryButton.setText(category.getName());
        holder.categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Category Adapter", categoryList.get(position).getName());
                Fragment productList = new ProductList();
                FragmentTransaction mFragmentTransaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.upgradeHomeLayout, productList).addToBackStack(null).commit();
                Bundle databund = new Bundle();
                //put your ArrayList data in bundle
                databund.putInt("productId", Integer.parseInt(categoryList.get(position).getId()));
                databund.putSerializable("childeren", (Serializable) categoryList);
                databund.putString("fromwhere", "fromhome");
                databund.putString("onClick", "category");
                productList.setArguments(databund);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}