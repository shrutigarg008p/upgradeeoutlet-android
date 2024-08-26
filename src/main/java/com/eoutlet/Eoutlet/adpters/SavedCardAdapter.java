package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.fragments.SavedCardFragment;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.SavedCardData;

import java.util.List;

public class SavedCardAdapter extends RecyclerView.Adapter<SavedCardAdapter.SingleItemRowHolder> {
    private Context context;
    String Locale;
    View v;
    List<SavedCardData> savedCardData;
    SavedCardFragment savedCardFragment;

    public SavedCardAdapter(Context context, List<SavedCardData> savedCardData, SavedCardFragment savedCardFragment) {
        this.context = context;
        this.savedCardData = savedCardData;
        this.savedCardFragment = savedCardFragment;
    }


    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                v = LayoutInflater.from(context).inflate(R.layout.saved_card_items_arabic, null);
                Locale = "ar";
            } else {
                v = LayoutInflater.from(context).inflate(R.layout.saved_card_items, null);
                Locale = "en";
            }
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.saved_card_items_arabic, null);
            Locale = "ar";
        }
        return new SingleItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleItemRowHolder holder, final int position) {
        holder.savedCardNumber.setText(savedCardData.get(position).card_number);
        holder.savedCardExpiry.setText(savedCardData.get(position).expirationDate);
        holder.deleteSavedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SavedCardFragment) savedCardFragment).deleteCard(savedCardData.get(position).id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return savedCardData.size();
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        LinearLayout deleteSavedCard;
        TextView savedCardNumber, savedCardExpiry;

        public SingleItemRowHolder(@NonNull View itemView) {
            super(itemView);
            deleteSavedCard = itemView.findViewById(R.id.deleteSavedCard);
            savedCardNumber = itemView.findViewById(R.id.savedCardNumber);
            savedCardExpiry = itemView.findViewById(R.id.savedCardExpiry);

        }
    }
}
