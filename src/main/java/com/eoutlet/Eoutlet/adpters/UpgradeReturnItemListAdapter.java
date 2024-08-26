package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.fragments.ReturnItemListViewClickFragment;
import com.eoutlet.Eoutlet.fragments.UpgradeReturnItemListFragment;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.UpgradeReturnItemListDetails;

import java.util.ArrayList;
import java.util.List;


public class UpgradeReturnItemListAdapter extends RecyclerView.Adapter<UpgradeReturnItemListAdapter.ItemRowHolder> {
    private Context context;
    View view;
    String Locale;
    private List<UpgradeReturnItemListDetails> upgradeReturnItemListDetails = new ArrayList<>();
    UpgradeReturnItemListFragment upgradeReturnItemListFragment;

    public UpgradeReturnItemListAdapter(Context context, List<UpgradeReturnItemListDetails> upgradeReturnItemListDetails, UpgradeReturnItemListFragment upgradeReturnItemListFragment) {
        this.context = context;
        this.upgradeReturnItemListDetails = upgradeReturnItemListDetails;
        this.upgradeReturnItemListFragment = upgradeReturnItemListFragment;
    }


    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                Locale ="ar";
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upgrade_return_item_list_view_arabic, null);
            } else {
                Locale ="en";
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upgrade_return_item_list_view, null);
            }
        } else {
            Locale ="ar";
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upgrade_return_item_list_view_arabic, null);
        }
        ItemRowHolder mh = new ItemRowHolder(view, viewType);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, final int position) {
        holder.itemRmaId.setText(upgradeReturnItemListDetails.get(position).getRma_id());
        holder.itemOrderId.setText(upgradeReturnItemListDetails.get(position).getOrder_id());
        holder.itemCreatedDate.setText(upgradeReturnItemListDetails.get(position).getUpdated_at());
        if( upgradeReturnItemListDetails.get(position).getStatus_code().equalsIgnoreCase("processed_closed")){
            holder.itemStatusText.setText( Locale =="ar"?"تم الحل":"Resolved");

        }else{
            holder.itemStatusText.setText(upgradeReturnItemListDetails.get(position).getStatus());
        }
       // holder.itemStatusText.setText(upgradeReturnItemListDetails.get(position).getStatus());


        if (upgradeReturnItemListDetails.get(position).getStatus_code().equalsIgnoreCase("Pending") || upgradeReturnItemListDetails.get(position).getStatus_code().equalsIgnoreCase("Processing") || upgradeReturnItemListDetails.get(position).getStatus_code().equalsIgnoreCase("new")) {
            holder.itemStatusText.setTextColor(context.getResources().getColor(R.color.pending));
        } else if (upgradeReturnItemListDetails.get(position).getStatus_code().equalsIgnoreCase("received") || upgradeReturnItemListDetails.get(position).getStatus_code().equalsIgnoreCase("processed_closed") || upgradeReturnItemListDetails.get(position).getStatus_code().equalsIgnoreCase("resolved")) {
            holder.itemStatusText.setTextColor(context.getResources().getColor(R.color.success));
        } else {
            holder.itemStatusText.setTextColor(context.getResources().getColor(R.color.canceled));
        }
        holder.itemViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment returnItemListViewClickFragment = new ReturnItemListViewClickFragment();
                Bundle bundle = new Bundle();
                bundle.putString("rma_id", upgradeReturnItemListDetails.get(position).getRma_id());
                returnItemListViewClickFragment.setArguments(bundle);
                FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.profileContainer, returnItemListViewClickFragment).addToBackStack(null).commit();
            }
        });
        if (upgradeReturnItemListDetails.get(position).getStatus_code().equalsIgnoreCase("closed") || upgradeReturnItemListDetails.get(position).getStatus_code().equalsIgnoreCase("rejected") || upgradeReturnItemListDetails.get(position).getStatus_code().equalsIgnoreCase("processed_closed") || upgradeReturnItemListDetails.get(position).getStatus_code().equalsIgnoreCase("resolved")) {
            holder.itemCancelButton.setVisibility(View.GONE);
        } else {
            holder.itemCancelButton.setVisibility(View.VISIBLE);
        }

        holder.itemCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((UpgradeReturnItemListFragment) upgradeReturnItemListFragment).doCancelReturnRequest(upgradeReturnItemListDetails.get(position).getRma_id());

            }
        });


    }

    @Override
    public int getItemCount() {
        return upgradeReturnItemListDetails.size();
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView itemRmaId, itemOrderId, itemCreatedDate, itemStatusText, itemViewText, itemCancelText;
        FrameLayout itemViewButton, itemCancelButton;

        public ItemRowHolder(View v, final int viewType) {
            super(v);
            itemRmaId = itemView.findViewById(R.id.itemRmaId);
            itemOrderId = itemView.findViewById(R.id.itemOrderId);
            itemCreatedDate = itemView.findViewById(R.id.itemCreatedDate);
            itemStatusText = itemView.findViewById(R.id.itemStatusText);
            itemViewText = itemView.findViewById(R.id.itemViewText);
            itemCancelText = itemView.findViewById(R.id.itemCancelText);
            itemViewButton = itemView.findViewById(R.id.itemViewButton);
            itemCancelButton = itemView.findViewById(R.id.itemCancelButton);

        }
    }
}
