package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.ReturnItemViewClickReturnItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ReturnItemListViewClickAdapter extends RecyclerView.Adapter<ReturnItemListViewClickAdapter.ItemRowHolder> {
    private Context context;
    View view;
    private List<ReturnItemViewClickReturnItem> returnItemViewClickReturnItems = new ArrayList<>();
    Boolean isDetailExpanded = false;


    public ReturnItemListViewClickAdapter(Context context, List<ReturnItemViewClickReturnItem> returnItemViewClickReturnItems) {
        this.context = context;
        this.returnItemViewClickReturnItems = returnItemViewClickReturnItems;
    }


    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.return_item_list_view_details_arabic, null);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.return_item_list_view_details, null);
            }
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.return_item_list_view_details_arabic, null);
        }
        ItemRowHolder mh = new ItemRowHolder(view, viewType);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, final int position) {
        ReturnItemViewClickReturnItem clickReturnItem = returnItemViewClickReturnItems.get(position);
        Picasso.get().load(clickReturnItem.getImage()).into(holder.itemImage);
        holder.itemName.setText(clickReturnItem.getName());
        holder.itemSku.setText(clickReturnItem.getSku());
        if (clickReturnItem.getQty_requested() != null) {
            holder.itemRequestQuantity.setText(clickReturnItem.getQty_requested().toString());
        } else {
            holder.itemRequestQuantity.setText("0");

        }
        if (clickReturnItem.getQty_authorized() != null) {
            holder.itemAuthorizedQuantity.setText(clickReturnItem.getQty_authorized().toString());
        } else {
            holder.itemAuthorizedQuantity.setText("0");
        }
        if (clickReturnItem.getQty_received() != null) {
            holder.itemReceivedQuantity.setText(clickReturnItem.getQty_received().toString());
        } else {
            holder.itemReceivedQuantity.setText("0");
        }
        if (clickReturnItem.getQty_approved() != null) {
            holder.itemApprovedQuantity.setText(clickReturnItem.getQty_approved().toString());
        } else {
            holder.itemApprovedQuantity.setText("0");
        }
        holder.returnStatusValue.setText(clickReturnItem.getStatus());

//        if (clickReturnItem.getStatus().equalsIgnoreCase("pending")) {
//            holder.returnStatusValue.setTextColor(context.getResources().getColor(R.color.greyText));
//            holder.returnStatusValue.setText("Pending");
//        } else if (clickReturnItem.getStatus().equalsIgnoreCase("closed")) {
//            holder.returnStatusValue.setTextColor(context.getResources().getColor(R.color.canceled));
//            holder.returnStatusValue.setText("Closed");
//        } else {
//            holder.returnStatusValue.setTextColor(context.getResources().getColor(R.color.success));
//            holder.returnStatusValue.setText("Received");
//        }
//        holder.itemReturnReasonValue.setText(clickReturnItem.getReason());
        holder.itemConditionValue.setText(clickReturnItem.getCondition());
        holder.itemResolutionValue.setText(clickReturnItem.getResolution());

        if (clickReturnItem.getOptions() != null) {
            holder.itemDetailsBox.setVisibility(View.VISIBLE);
            holder.itemMoreDetailRecyclerView.setVisibility(View.GONE);
            holder.itemDetailsBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isDetailExpanded == false) {
                        isDetailExpanded = true;
                        holder.itemDetailsIcon.setImageResource(R.drawable.ic_up_arrow);
                        holder.itemMoreDetailRecyclerView.setVisibility(View.VISIBLE);
                        ReturnItemListViewClickMoreDetails returnItemListViewClickMoreDetails = new ReturnItemListViewClickMoreDetails(clickReturnItem.getOptions(), context);
                        holder.itemMoreDetailRecyclerView.setHasFixedSize(true);
                        holder.itemMoreDetailRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        holder.itemMoreDetailRecyclerView.setAdapter(returnItemListViewClickMoreDetails);
                    } else {
                        holder.itemDetailsIcon.setImageResource(R.drawable.ic_down_arrow);
                        holder.itemMoreDetailRecyclerView.setVisibility(View.GONE);
                        isDetailExpanded = false;
                    }
                }
            });
        } else {
            holder.itemDetailsBox.setVisibility(View.GONE);
            holder.itemMoreDetailRecyclerView.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return returnItemViewClickReturnItems.size();
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        ImageView itemImage, itemDetailsIcon;
        LinearLayout itemDetailsBox;
        TextView itemName, itemSku, itemRequestQuantity, itemAuthorizedQuantity, itemReceivedQuantity, itemApprovedQuantity,
                returnStatusValue, itemReturnReasonValue, itemConditionValue, itemResolutionValue;
        RecyclerView itemMoreDetailRecyclerView;

        public ItemRowHolder(View v, final int viewType) {
            super(v);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemDetailsIcon = itemView.findViewById(R.id.itemDetailsIcon);
            itemDetailsBox = itemView.findViewById(R.id.itemDetailsBox);
            itemName = itemView.findViewById(R.id.itemName);
            itemSku = itemView.findViewById(R.id.itemSku);
            itemRequestQuantity = itemView.findViewById(R.id.itemRequestQuantity);
            itemAuthorizedQuantity = itemView.findViewById(R.id.itemAuthorizedQuantity);
            itemReceivedQuantity = itemView.findViewById(R.id.itemReceivedQuantity);
            itemApprovedQuantity = itemView.findViewById(R.id.itemApprovedQuantity);
            returnStatusValue = itemView.findViewById(R.id.returnStatusValue);
            itemReturnReasonValue = itemView.findViewById(R.id.itemReturnReasonValue);
            itemConditionValue = itemView.findViewById(R.id.itemConditionValue);
            itemResolutionValue = itemView.findViewById(R.id.itemResolutionValue);
            itemMoreDetailRecyclerView = itemView.findViewById(R.id.itemMoreDetailRecyclerView);


        }
    }
}

