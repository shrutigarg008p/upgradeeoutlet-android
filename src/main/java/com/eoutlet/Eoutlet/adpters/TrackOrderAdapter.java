package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.fragments.TrackOrderFragment;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.UpgradeOrderListData;

import java.util.ArrayList;
import java.util.List;


public class TrackOrderAdapter extends RecyclerView.Adapter<TrackOrderAdapter.MyViewHolder> {
    private Context context;
    TrackOrderFragment trackOrderFragment;
    private List<UpgradeOrderListData> upgradeOrderListCompletedData = new ArrayList<>();
    private TrackOrderFragment testFragment;
    public ParentDialogsListener parentDialogListener;
    private int expandedPosition = -1;
    private RecyclerView recyclerView = null;
    View itemView;
    String Locale;

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Boolean value);
    }

    public TrackOrderAdapter(Context context, List<UpgradeOrderListData> upgradeOrderListCompletedData, TrackOrderFragment trackOrderFragment) {
        this.upgradeOrderListCompletedData = upgradeOrderListCompletedData;
        this.context = context;
        this.trackOrderFragment = trackOrderFragment;
        parentDialogListener = (ParentDialogsListener) context;
    }

    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                Locale = "ar";
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track_order_arabic, parent, false);
            } else {
                Locale = "en";
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track_order, parent, false);
            }
        } else {
            Locale = "ar";
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track_order_arabic, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.orderidtrackorder.setText(Locale == "ar" ? upgradeOrderListCompletedData.get(position).getIncrement_id() : upgradeOrderListCompletedData.get(position).getIncrement_id());
        holder.totalamounttrackorder.setText(Locale == "ar" ? upgradeOrderListCompletedData.get(position).getGrand_total() + MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()) : Math.round(upgradeOrderListCompletedData.get(position).getGrand_total()*MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext())) + MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()));

        final boolean isExpanded = position == expandedPosition;
        holder.itemView.setActivated(isExpanded);
        holder.layoutChildtrackorder.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.textTrackOrder.setRotation(isExpanded ? 270 : 90);
        holder.mainheaderview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                expandedPosition = isExpanded ? -1 : position;
                notifyDataSetChanged();
                holder.layoutChildtrackorder.removeAllViews();
                View view1 = trackOrderFragment.getLayoutInflater().inflate(R.layout.child_track_order, null);
                if (!isExpanded) {

                    System.out.println(upgradeOrderListCompletedData.get(position).getTracking_url());
                    WebView webviewtrackorder = view1.findViewById(R.id.webviewtrackorder);
                    CookieManager.getInstance().removeAllCookies(null);
                    CookieManager.getInstance().flush();
                    webviewtrackorder.getSettings().setJavaScriptEnabled(true);
                    webviewtrackorder.getSettings().setUseWideViewPort(true);
                    webviewtrackorder.getSettings().setLoadWithOverviewMode(true);
                    webviewtrackorder.clearCache(true);
                    webviewtrackorder.clearHistory();
                    webviewtrackorder.clearFormData();
                    webviewtrackorder.setWebViewClient(new MyBrowser());

                    webviewtrackorder.loadUrl(upgradeOrderListCompletedData.get(position).getTracking_url());
                    holder.layoutChildtrackorder.addView(view1);
                    parentDialogListener.showProgressDialog();
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return upgradeOrderListCompletedData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView textTrackOrder;
        private LinearLayout layoutChildtrackorder, mainheaderview;
        TextView orderidtrackorder, totalamounttrackorder;

        public MyViewHolder(View view) {
            super(view);
            textTrackOrder = view.findViewById(R.id.textTrackOrder);
            layoutChildtrackorder = view.findViewById(R.id.layoutChildtrackorder);
            orderidtrackorder = view.findViewById(R.id.orderidtrackorder);
            totalamounttrackorder = view.findViewById(R.id.totalamounttrackorder);
            mainheaderview = view.findViewById(R.id.mainHeaderView);
        }
    }

    private class MyBrowser extends WebViewClient {


        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            //  parentDialogListener.showProgressDialog();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return super.shouldOverrideUrlLoading(view, url);
/*
            view.loadUrl(url);
            return true;*/
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            parentDialogListener.hideProgressDialog();

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(context, "Error" + description + errorCode, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
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



