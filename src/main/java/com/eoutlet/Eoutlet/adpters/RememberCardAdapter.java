package com.eoutlet.Eoutlet.adpters;

import android.content.Context;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.eoutlet.Eoutlet.listener.ViewListener3;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.CardDetail;
import com.eoutlet.Eoutlet.pojo.Datum;
import com.eoutlet.Eoutlet.pojo.HomeCatagory1Param;
import com.eoutlet.Eoutlet.pojo.RememberCardDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RememberCardAdapter extends RecyclerView.Adapter<RememberCardAdapter.MyViewHolder> {
    List<CardDetail> mydataset;
    Context mContext;
    ExecuteFragment execute;
    ViewListener3 viewListener;
    ImageView lastselectd;
    private int lastselectedposition;
    private int selectedcardposition;
    private LinearLayout lastselectedcvvlayout;
    private String savedcardcvv;
    private View view;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, cardnumber;
        public ImageView iv, cardradiobutton;
        public CardView cardclick;
        private LinearLayout cvvlayout;
        private EditText savedcardcvv;



        LinearLayout onSelection;

        public MyViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            cardnumber = view.findViewById(R.id.cardnumber);
            cardclick = view.findViewById(R.id.remembercardclick);
            cardradiobutton = view.findViewById(R.id.cardradiobutton);
            cvvlayout = view.findViewById(R.id.cvvlayout);
            savedcardcvv  = view.findViewById(R.id.savedcardcvv);

        }
    }

    public void passData(Context context) {
        mContext = context;
        execute = (MainActivity) mContext;
    }

    public RememberCardAdapter(Context context, List<CardDetail> mydataset, int selectedcaedposition, ViewListener3 viewListener) {
        this.mydataset = mydataset;
        mContext = context;
        execute = (MainActivity) context;
        this.viewListener = viewListener;
        this.selectedcardposition = selectedcaedposition;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        if(MySharedPreferenceClass.getChoosenlanguage(parent.getContext()).equals("ar")) {
           itemView = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.card_item, parent, false);
       }
       else{
          itemView = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.card_item_arabic, parent, false);
       }

        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.savedcardcvv.addTextChangedListener(new TextWatcher() {



            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                //if(s.length() >=3)
                    savedcardcvv =  holder.savedcardcvv.getText().toString();
                holder.savedcardcvv.requestFocus();
                viewListener.onClick(position, view,savedcardcvv);
            }
        });

        // holder.name.setText(mydataset.get(position).name);

        holder.name.setText(mydataset.get(position).type);
        holder.cardnumber.setText(mydataset.get(position).cardNumber);

        holder.cardclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = v;


                    if (lastselectd == null) {

                        savedcardcvv = holder.savedcardcvv.getText().toString();

                        holder.cardradiobutton.setImageDrawable(mContext.getDrawable(R.drawable.ic_yellow_check));
                        lastselectedposition = position;
                        lastselectd = holder.cardradiobutton;
                        holder.cvvlayout.setVisibility(View.VISIBLE);

                        lastselectedcvvlayout = holder.cvvlayout;

                        viewListener.onClick(position, v, savedcardcvv);

                    } else if (holder.cardradiobutton != lastselectd) {

                        holder.cvvlayout.setVisibility(View.VISIBLE);

                        savedcardcvv = holder.savedcardcvv.getText().toString();
                        viewListener.onClick(position, v, savedcardcvv);

                        holder.cardradiobutton.setImageDrawable(mContext.getDrawable(R.drawable.ic_yellow_check));

                        if (lastselectd != null && lastselectd != holder.cardradiobutton) {
                            lastselectd.setImageDrawable(mContext.getDrawable(R.drawable.ic_circle));
                        }

                        if (lastselectedcvvlayout != null && lastselectedcvvlayout != holder.cvvlayout) {
                            lastselectedcvvlayout.setVisibility(View.GONE);

                        }

                        lastselectedposition = position;
                        lastselectd = holder.cardradiobutton;
                        lastselectedcvvlayout = holder.cvvlayout;


                    }

                //}
            }

        });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mydataset.size();
    }


}
