package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eoutlet.Eoutlet.R;

import com.eoutlet.Eoutlet.fragments.NewHomeFragmentUpgrade;
import com.eoutlet.Eoutlet.fragments.ProductList;
import com.eoutlet.Eoutlet.pojo.Featured;


import java.io.Serializable;
import java.util.List;

public class FeaturedChoiceAdapter extends RecyclerView.Adapter<FeaturedChoiceAdapter.MyViewHolder> {
    private List<Featured> editorChoiceList;
    NewHomeFragmentUpgrade upgradeHomeScreen;
    private Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView editorChoiceImage;

        MyViewHolder(View view) {
            super(view);
            editorChoiceImage = view.findViewById(R.id.editorChoiceImage);
        }
    }

    public FeaturedChoiceAdapter(Context context, List<Featured> editorChoiceList, NewHomeFragmentUpgrade upgradeHomeScreen) {
        this.context = context;
        this.editorChoiceList = editorChoiceList;
        this.upgradeHomeScreen = upgradeHomeScreen;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.editor_choice_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Featured editorChoice = editorChoiceList.get(position);
//        Picasso.get().load(editorChoice.getImage()).into(holder.editorChoiceImage);
        try {
            Glide.with(holder.editorChoiceImage)
                    .load(editorChoice.image)/*.apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))*/.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)
                    .placeholder(R.drawable.progress_animation)/*.override(400, 1200)*/
                    .into(holder.editorChoiceImage);
        } catch (Exception e) {
        }

        if(position == 2 ){
          //  Log.e("Image URl is ",editorChoice.img);
        }
        holder.editorChoiceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Editor Choice Adapter", editorChoice.image);
                Fragment productList = new ProductList();
                FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.container2, productList).addToBackStack(null).commit();
                Bundle databund = new Bundle();
                //put your ArrayList data in bundle
                databund.putInt("productId", Integer.parseInt(editorChoiceList.get(position).id));
                databund.putString("name", editorChoiceList.get(position).name);
               // databund.putSerializable("childeren", (Serializable) editorChoiceList.get(position).children);
                databund.putString("fromwhere", "fromhome");
                databund.putString("previouspage","FromHomeScreen");
                databund.putString("onClick", "editorChoice");
                productList.setArguments(databund);
            }

        });

    }

    @Override
    public int getItemCount() {
        return editorChoiceList.size();
    }
}