package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
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
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.ShopByCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeCategorySelectionAdapter extends RecyclerView.Adapter<HomeCategorySelectionAdapter.MyViewHolder> {
    List<ShopByCategory> shopByCategoryList;
    Context mContext;
    ExecuteFragment execute;
    ViewListener viewListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoryItemName;
        ImageView categoryItemImage;
        LinearLayout categoryItemContainer;

        public MyViewHolder(View view) {
            super(view);
            categoryItemName = view.findViewById(R.id.category_item_name);
            categoryItemImage = view.findViewById(R.id.category_item_image);
            categoryItemContainer = view.findViewById(R.id.category_item_container);
        }
    }

    public void passData(Context context) {
        mContext = context;
        execute = (MainActivity) mContext;
    }

    public HomeCategorySelectionAdapter(Context context, List<ShopByCategory> shopByCategoryList, ViewListener viewListener) {
        this.shopByCategoryList = shopByCategoryList;
        mContext = context;
        execute = (MainActivity) context;
        this.viewListener = viewListener;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_by_category_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Picasso.get().load(shopByCategoryList.get(position).image).into(holder.categoryItemImage);
        holder.categoryItemName.setText(shopByCategoryList.get(position).name);
        holder.categoryItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewListener.onClick(position, v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopByCategoryList.size();
    }

}
