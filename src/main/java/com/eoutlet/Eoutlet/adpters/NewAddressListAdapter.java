package com.eoutlet.Eoutlet.adpters;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;

import java.util.List;

public class NewAddressListAdapter extends RecyclerView.Adapter<NewAddressListAdapter.MyViewHolder> {

    int mydataset[] = {R.drawable.pro1,R.drawable.pro2,R.drawable.pro3};
    Context mcontext;
    int totalprice,totalpricewithtax;


    private List<String> name;
    private List<String> street;
    private List<String> city;
    private List<String> country;
    private List<String> phone;
    private List<String> addressid;

    ExecuteFragment execute;
    ViewListener viewListener;
    private static  ImageView lastSelected ;
    private RelativeLayout lastselectedbackground;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, namestreet, namecity,namecountry,namephone;
        public ImageView iv,selector,delete;
    RelativeLayout cardbackground;

        public MyViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            namestreet = view.findViewById(R.id.street);
            namecity= view.findViewById(R.id.city);
            namecountry= view.findViewById(R.id.country);
            namephone= view.findViewById(R.id.phone);
            cardbackground = view.findViewById(R.id.cardBackground);
            delete =view.findViewById(R.id.deleteCart);
            selector = view.findViewById(R.id.id_selector);









        }}

    public NewAddressListAdapter(Context context, List<String> name,
                                 List<String> street,
                                 List<String> city,

                                 List<String> country,
                                 List<String> phone, List<String> addressid,

                                 ViewListener viewListener) {

        mcontext = context;
        this.name = name;
        this.city = city;
        this.street = street;
        this.country = country;
        this.phone = phone;
        this.totalpricewithtax = totalpricewithtax;
        this.viewListener = viewListener;
        this.addressid = addressid;

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_address_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if(MySharedPreferenceClass.getSelectedAddressId(mcontext).equals("0")){

            if(position==0){
                lastSelected = holder.selector;
                lastselectedbackground = holder.cardbackground;
                holder.cardbackground.setBackground(ContextCompat.getDrawable(mcontext,R.drawable.addressselectbackground));
                lastSelected.setImageDrawable(ContextCompat.getDrawable(mcontext,R.drawable.ic_yellow_check));

            }

            else
                {

                   lastselectedbackground.setBackground(ContextCompat.getDrawable(mcontext,R.drawable.addressgreybackground));
                    lastSelected.setImageDrawable(ContextCompat.getDrawable(mcontext,R.drawable.ic_unselect_card));

                }

        }
        else if(MySharedPreferenceClass.getSelectedAddressId(mcontext).equals("null"))
        {
            //lastselectedbackground.setBackground(ContextCompat.getDrawable(mcontext,R.drawable.addressgreybackground));
//            lastSelected.setImageDrawable(ContextCompat.getDrawable(mcontext,R.drawable.ic_unselect_card));
        }

        else {
           if(addressid.get(position).equals(MySharedPreferenceClass.getSelectedAddressId(mcontext)))
           {
               if(addressid.get(position).equals(MySharedPreferenceClass.getSelectedAddressId(mcontext))) {
                   lastSelected=holder.selector;
                   lastselectedbackground = holder.cardbackground;


                   lastselectedbackground.setBackground(ContextCompat.getDrawable(mcontext,R.drawable.addressselectbackground));
                   lastSelected.setImageDrawable(ContextCompat.getDrawable(mcontext,R.drawable.ic_yellow_check));



               }

                 // lastSelected=holder.selector;


                //  holder.selector.setImageDrawable(ContextCompat.getDrawable(mcontext, R.mipmap.radio_on));


           }

        }



        holder.name.setText(name.get(position).toString());
                 holder.namestreet.setText(street.get(position).toString()) /*+" " +country.get(position).toString() )*/;
                holder.namecity.setText(city.get(position).toString());
                 holder.namecountry.setText(country.get(position).toString());
                 holder.namephone.setText("رقم الجوال : "+phone.get(position).toString());

        /*if(!((position%2) ==0)){
            holder.cardbackground.setBackground(ContextCompat.getDrawable(mcontext,R.drawable.addressgreybackground));
            holder.selector.setImageDrawable(ContextCompat.getDrawable(mcontext,R.drawable.ic_uncheck));

        }*/
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewListener.onClick(position,holder.delete);


            }
        });
                 holder.delete.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         viewListener.onClick(position,holder.delete);


                     }
                 });


                 holder.cardbackground.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                         if(lastSelected!=null  && lastselectedbackground!=null) {
                             lastSelected.setImageDrawable(ContextCompat.getDrawable(mcontext,R.drawable.ic_unselect_card));
                             lastselectedbackground.setBackground(ContextCompat.getDrawable(mcontext,R.drawable.addressgreybackground));
                         }
                         lastSelected = holder.selector;
                         lastselectedbackground = holder.cardbackground;

                         viewListener.onClick(position,holder.cardbackground);



                         MySharedPreferenceClass.setSelecteAddressdId(mcontext,addressid.get(position));
                         holder.selector.setImageDrawable(ContextCompat.getDrawable(mcontext, R.drawable.ic_yellow_check));
                         holder.cardbackground.setBackground(ContextCompat.getDrawable(mcontext,R.drawable.addressselectbackground));





                     }
                 });





    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return name.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
