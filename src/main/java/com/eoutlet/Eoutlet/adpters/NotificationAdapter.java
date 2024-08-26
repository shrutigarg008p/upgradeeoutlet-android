package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.fragments.NotificationInboxFragment;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.moengage.addon.inbox.model.InboxMessage;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.SingleItemRowHolder> {
    private Context context;
    List<InboxMessage> messageList;
    NotificationInboxFragment notificationInboxFragment;
    View view;
    String Locale;

    public NotificationAdapter(Context context, List<InboxMessage> messageList, NotificationInboxFragment notificationInboxFragment) {
        this.context = context;
        this.messageList = messageList;
        this.notificationInboxFragment = notificationInboxFragment;

    }

    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                Locale = "ar";
                view = LayoutInflater.from(context).inflate(R.layout.notification_item_arabic, null);
            } else {
                Locale = "en";
                view = LayoutInflater.from(context).inflate(R.layout.notification_item, null);
            }
        } else {
            Locale = "ar";
            view = LayoutInflater.from(context).inflate(R.layout.notification_item_arabic, null);
        }
        return new SingleItemRowHolder(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull SingleItemRowHolder holder, int position) {
//        Log.e("getCampaignId", messageList.get(position).getCampaignId());
//        Log.e("getExpiry", messageList.get(position).getExpiry());
//        Log.e("getReceivedTime", messageList.get(position).getReceivedTime());
//        Log.e("getTag", messageList.get(position).getTag());
//        Log.e("getId", String.valueOf(messageList.get(position).getId()));
//        Log.e("getMediaContent", String.valueOf(messageList.get(position).getMediaContent()));
//        Log.e("getTextContent", String.valueOf(messageList.get(position).getTextContent()));
//        Log.e("isClicked", String.valueOf(messageList.get(position).isClicked()));
        Log.e("payload", String.valueOf(messageList.get(0).getPayload()));
//        holder.notificationTitle.setText(messageList.get(position).getTextContent().getTitle());
//        Log.e("component1", String.valueOf(messageList.get(position).component1()));
//        Log.e("component2", String.valueOf(messageList.get(position).component2()));
//        Log.e("component3", String.valueOf(messageList.get(position).component3()));
//        Log.e("messageList", String.valueOf(messageList.get(position).toString()));
        try {
            holder.notificationTitle.setText(messageList.get(position).getPayload().getString("gcm_title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.notificationSubTitle.setVisibility(View.VISIBLE);
            holder.notificationSubTitle.setText(messageList.get(position).getPayload().getString("gcm_subtext"));
        } catch (JSONException e) {
            e.printStackTrace();
            holder.notificationSubTitle.setVisibility(View.GONE);
        }
        try {
            holder.notificationMessage.setText(messageList.get(position).getPayload().getString("gcm_alert"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (messageList.get(position).getMediaContent() != null) {
            if (messageList.get(position).getMediaContent().getMediaType().toString().equalsIgnoreCase("Image")) {
                holder.notificationImageBox.setVisibility(View.VISIBLE);
                ((LinearLayout.LayoutParams) holder.notificationDataBox.getLayoutParams()).weight = (float) 1.3;
                Picasso.get().load(messageList.get(position).getMediaContent().getUrl()).into(holder.notificationImage);
            } else {
                ((LinearLayout.LayoutParams) holder.notificationDataBox.getLayoutParams()).weight = (float) 2;
                holder.notificationImageBox.setVisibility(View.GONE);
            }
        } else {
            ((LinearLayout.LayoutParams) holder.notificationDataBox.getLayoutParams()).weight = (float) 2;
            holder.notificationImageBox.setVisibility(View.GONE);
        }


        Log.e("date", String.valueOf(Date.from(Instant.parse(messageList.get(position).getReceivedTime()))));
        Log.e("dateNew", String.valueOf(Instant.parse(messageList.get(position).getReceivedTime())));


        Integer hour = Date.from(Instant.parse(messageList.get(position).getReceivedTime())).getHours();
        Integer minutes = Date.from(Instant.parse(messageList.get(position).getReceivedTime())).getMinutes();
        Integer date = Date.from(Instant.parse(messageList.get(position).getReceivedTime())).getDate();
        Integer month = Date.from(Instant.parse(messageList.get(position).getReceivedTime())).getMonth();
        //Integer year = Date.from(Instant.parse(messageList.get(position).getReceivedTime())).getYear();
        Integer year = Integer.valueOf(takeLast(String.valueOf(Date.from(Instant.parse(messageList.get(position).getReceivedTime()))),4));




        //        messageList.get(position).getReceivedTime().substring(0, 10)
        holder.notificationDate.setText(date.toString() + "/" + month.toString() + "/" + year.toString() + ", " + hour.toString() + ":" + minutes.toString() + " " + (hour > 12 || hour < 24 ? "PM" : "AM"));

//        if (messageList.get(position).isClicked() == true) {
//            holder.notificationContainer.setBackground(ContextCompat.getDrawable(context, R.drawable.moe_card_background));
//        } else {
//            holder.notificationContainer.setBackground(ContextCompat.getDrawable(context, R.color.grey_colour));
//        }

//        holder.notificationContainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("notification", messageList.get(position).getCampaignId());
//                if (messageList.get(position).isClicked() == true) {
//                    Log.e("Notification", "already read");
//                } else {
////                    MoEInboxHelper.getInstance().trackMessageClicked(context, messageList.get(position));
////                    ((Activity) context).recreate();
////                    ((MainActivity) context).navigationView.setSelectedItemId(R.id.home);
//                }
//            }
//        });
//
//        holder.notificationCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MoEInboxHelper.getInstance().deleteMessage(context, messageList.get(position));
////                notifyItemRemoved(position);
//                messageList.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, messageList.size());
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        //
//        LinearLayout deleteSavedCard;
//        TextView savedCardNumber, savedCardExpiry;
        TextView notificationTitle, notificationSubTitle;
        TextView notificationDate;
        TextView notificationMessage;
        TextView couponCode;
        Button btnCopy;
        boolean hasCouponCode;
        String code;
        RelativeLayout couponAction;
        CardView notificationContainer;
        ImageView notificationImage, notificationCancel;
        LinearLayout notificationImageBox, notificationDataBox;

        public SingleItemRowHolder(@NonNull View itemView) {
            super(itemView);
            notificationTitle = itemView.findViewById(R.id.notificationTitle);
            notificationSubTitle = itemView.findViewById(R.id.notificationSubTitle);
            notificationDate = itemView.findViewById(R.id.notificationDate);
            notificationMessage = itemView.findViewById(R.id.notificationMessage);
            couponCode = itemView.findViewById(R.id.couponCode);
            btnCopy = itemView.findViewById(R.id.btnCopy);
            couponAction = itemView.findViewById(R.id.couponAction);
            notificationContainer = itemView.findViewById(R.id.notificationContainer);
            notificationImage = itemView.findViewById(R.id.notificationImage);
            notificationCancel = itemView.findViewById(R.id.notificationCancel);
            notificationImageBox = itemView.findViewById(R.id.notification_image_box);
            notificationDataBox = itemView.findViewById(R.id.notification_data_box);


        }



    }

    public static String takeLast(String value, int count) {
        if (value == null || value.trim().length() == 0 || count < 1) {
            return "";
        }

        if (value.length() > count) {
            return value.substring(value.length() - count);
        } else {
            return value;
        }
    }
}
