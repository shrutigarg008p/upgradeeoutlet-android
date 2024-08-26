package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.text.Html;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.UpgradeFaqData;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.SingleItemRowHolder> {
    private Context context;
    private List<UpgradeFaqData> faqData;
    View v;
    String Locale;
    private int expandedPosition = -1;


    public FaqAdapter(Context context, List<UpgradeFaqData> faqData) {
        this.context = context;
        this.faqData = faqData;
    }


    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                Locale = "ar";
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_items_arabic, null);
            } else {
                Locale = "en";
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_items, null);
            }
        } else {
            Locale = "ar";
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_items_arabic, null);
        }
        return new SingleItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleItemRowHolder holder, final int position) {
        holder.faqAnswer.setVisibility(View.GONE);
        holder.faqQuestion.setText(Html.fromHtml(faqData.get(position).title).toString());
        holder.faqAnswer.setText(Html.fromHtml(faqData.get(position).content).toString());
        Linkify.addLinks(holder.faqAnswer, Linkify.ALL);
        final boolean isExpanded = position == expandedPosition;
        holder.faqAnswer.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.faqDropDownIcon.setRotation(Locale == "ar" ? isExpanded ? 180 : 0 : isExpanded ? 180 : 0);
        holder.itemView.setActivated(isExpanded);
        holder.faqCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandedPosition = isExpanded ? -1 : position;
                notifyDataSetChanged();
//                if (isExpanded == false) {
//                    holder.faqDropDownIcon.setRotation(0);
//                    holder.faqAnswer.setVisibility(View.VISIBLE);
//                    isExpanded = true;
//                } else {
//                    holder.faqDropDownIcon.setRotation(Locale == "ar" ? 90 : 270);
//                    holder.faqAnswer.setVisibility(View.GONE);
//                    isExpanded = false;
//                }

            }

        });
    }


    @Override
    public int getItemCount() {
        return faqData.size();
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        CardView faqCard;
        TextView faqQuestion, faqAnswer;
        ImageView faqDropDownIcon;

        public SingleItemRowHolder(@NonNull View itemView) {
            super(itemView);
            faqCard = itemView.findViewById(R.id.faqCard);
            faqQuestion = itemView.findViewById(R.id.faqQuestion);
            faqAnswer = itemView.findViewById(R.id.faqAnswer);
            faqDropDownIcon = itemView.findViewById(R.id.faqDropDownIcon);

        }
    }
}

