package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.fragments.CheckOutConfirmationfragment;
import com.eoutlet.Eoutlet.fragments.LocationMapFragment;
import com.eoutlet.Eoutlet.fragments.NewAddressFragmentUpgrade;
import com.eoutlet.Eoutlet.fragments.UpgradeMapFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.UpgradedGetAddressList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class UpgradeNewAddressListAdapter extends RecyclerView.Adapter<UpgradeNewAddressListAdapter.MyViewHolder> {
    private List<UpgradedGetAddressList> upgradedGetAddressLists;
    private Context context;
    ViewListener viewListener;
    View itemView;
    String value;
    private static ImageView lastSelected;
    private LinearLayout lastselectedbackground;
    NewAddressFragmentUpgrade newAddressFragmentUpgrade;
    ArrayList<String> countrycode;
    String trimphonenumber;;
    public UpgradeNewAddressListAdapter(Context context, ArrayList<String>countrycode, NewAddressFragmentUpgrade newAddressFragmentUpgrade, List<UpgradedGetAddressList> upgradedGetAddressLists, String value, ViewListener viewListener) {
        this.context = context;
        this.countrycode = countrycode;
        this.newAddressFragmentUpgrade = newAddressFragmentUpgrade;
        this.upgradedGetAddressLists = upgradedGetAddressLists;
        this.value = value;

        this.viewListener = viewListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.upgrade_new_address_list_items_arabic, parent, false);
            } else {
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.upgrade_new_address_list_items, parent, false);
            }
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.upgrade_new_address_list_items_arabic, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (MySharedPreferenceClass.getSelectedAddressId(context).equals("0")) {
            if (position == 0) {
                lastSelected = holder.defaultAddressCheck;
                lastselectedbackground = holder.addressSelected;
                lastSelected.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_yellow_check));
                holder.addressSelected.setBackground(ContextCompat.getDrawable(context, R.drawable.addressselectbackground));
            } else {
                lastSelected.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_check_grey));
                holder.addressSelected.setBackground(ContextCompat.getDrawable(context, R.drawable.addressgreybackground));
            }

            MySharedPreferenceClass.setAddressname(context, upgradedGetAddressLists.get(0).getFirstname() + " "+ upgradedGetAddressLists.get(position).getLastname());
            MySharedPreferenceClass.setCityname(context, upgradedGetAddressLists.get(0).getCity());
            MySharedPreferenceClass.setStreetName(context, upgradedGetAddressLists.get(0).getStreet());
            MySharedPreferenceClass.setCountryname(context, upgradedGetAddressLists.get(0).getCountry());
            MySharedPreferenceClass.setAddressphone(context, upgradedGetAddressLists.get(0).getTelephone());
            MySharedPreferenceClass.setCountryId(context, upgradedGetAddressLists.get(0).getCountry_id());
            MySharedPreferenceClass.setfirstAdressname(context, upgradedGetAddressLists.get(0).getFirstname());
            MySharedPreferenceClass.setlastAdressname(context, upgradedGetAddressLists.get(0).getLastname());
            MySharedPreferenceClass.setSelecteAddressdId(context, upgradedGetAddressLists.get(0).getAddress_id());

        } else if (MySharedPreferenceClass.getSelectedAddressId(context).equals("null")) {
        }
        else {
            if (upgradedGetAddressLists.get(position).getAddress_id().equals(MySharedPreferenceClass.getSelectedAddressId(context))) {
                if (upgradedGetAddressLists.get(position).getAddress_id().equals(MySharedPreferenceClass.getSelectedAddressId(context))) {
                    lastSelected = holder.defaultAddressCheck;
                    lastselectedbackground = holder.addressSelected;
                    lastselectedbackground.setBackground(ContextCompat.getDrawable(context, R.drawable.addressselectbackground));
                    lastSelected.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_yellow_check));
                }


                    MySharedPreferenceClass.setAddressname(context, upgradedGetAddressLists.get(position).getFirstname() + " "+ upgradedGetAddressLists.get(position).getLastname());
                    MySharedPreferenceClass.setCityname(context, upgradedGetAddressLists.get(position).getCity());
                    MySharedPreferenceClass.setStreetName(context, upgradedGetAddressLists.get(position).getStreet());
                    MySharedPreferenceClass.setCountryname(context, upgradedGetAddressLists.get(position).getCountry());
                    MySharedPreferenceClass.setAddressphone(context, upgradedGetAddressLists.get(position).getTelephone());
                    MySharedPreferenceClass.setCountryId(context, upgradedGetAddressLists.get(position).getCountry_id());
                    MySharedPreferenceClass.setfirstAdressname(context, upgradedGetAddressLists.get(position).getFirstname());
                    MySharedPreferenceClass.setlastAdressname(context, upgradedGetAddressLists.get(position).getLastname());
                    MySharedPreferenceClass.setSelecteAddressdId(context, upgradedGetAddressLists.get(position).getAddress_id());

                if (value.equals("fromCheckout")) {
                    CheckOutConfirmationfragment.tvaddressName.setText(upgradedGetAddressLists.get(position).getFirstname() + " "+ upgradedGetAddressLists.get(position).getLastname());
                    CheckOutConfirmationfragment.tvstreet.setText(upgradedGetAddressLists.get(position).getStreet());
                    CheckOutConfirmationfragment.tvCity.setText(upgradedGetAddressLists.get(position).getCity());
                    CheckOutConfirmationfragment.tvCountry.setText(upgradedGetAddressLists.get(position).getCountry());
                    CheckOutConfirmationfragment.tvPhone.setText(upgradedGetAddressLists.get(position).getTelephone());

                }




            }
        }
        holder.userName.setText((upgradedGetAddressLists.get(position).getFirstname() != "" ? upgradedGetAddressLists.get(position).getFirstname() : "") + " " + (upgradedGetAddressLists.get(position).getLastname() != "" ? upgradedGetAddressLists.get(position).getLastname() : ""));
        //holder.userAddress.setText((upgradedGetAddressLists.get(position).getStreet() != "" ? upgradedGetAddressLists.get(position).getStreet() : "") + " " + (upgradedGetAddressLists.get(position).getCity() != "" ? upgradedGetAddressLists.get(position).getCity() : "") + " " + (upgradedGetAddressLists.get(position).getCountry() != "" ? upgradedGetAddressLists.get(position).getCountry() : ""));
        if (upgradedGetAddressLists.get(position).getStreet().toLowerCase().contains(upgradedGetAddressLists.get(position).getCity().toLowerCase())) {
           Log.e("cityname",upgradedGetAddressLists.get(position).getCity());
            upgradedGetAddressLists.get(position).setCity("");
        }
        if (upgradedGetAddressLists.get(position).getStreet().toLowerCase().contains(upgradedGetAddressLists.get(position).getCountry().toLowerCase())) {
            Log.e("countryname",upgradedGetAddressLists.get(position).getCountry());
            upgradedGetAddressLists.get(position).setCountry("");
        }


        holder.userAddress.setText((upgradedGetAddressLists.get(position).getArea() != "" ?
                upgradedGetAddressLists.get(position).getArea() : "") + " " +
                (upgradedGetAddressLists.get(position).getStreet() != "" ?
                        upgradedGetAddressLists.get(position).getStreet() : "") + " " +
                (upgradedGetAddressLists.get(position).getCity() != "" ?
                        upgradedGetAddressLists.get(position).getCity() : "") + " " +
                (upgradedGetAddressLists.get(position).getCountry() != "" ?
                        upgradedGetAddressLists.get(position).getCountry() : ""));


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewListener.onClick(position, holder.delete);

            }
        });
        holder.addressSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastSelected != null && lastselectedbackground != null) {
                    lastSelected.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_unselect_card));
                    lastselectedbackground.setBackground(ContextCompat.getDrawable(context, R.drawable.addressgreybackground));
                }
                lastSelected = holder.defaultAddressCheck;
                lastselectedbackground = holder.addressSelected;
                viewListener.onClick(position, holder.addressSelected);
                MySharedPreferenceClass.setSelecteAddressdId(context, upgradedGetAddressLists.get(position).getAddress_id());
                holder.defaultAddressCheck.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_yellow_check));
                holder.addressSelected.setBackground(ContextCompat.getDrawable(context, R.drawable.addressselectbackground));

            }
        });
        holder.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(upgradedGetAddressLists.get(position).getTelephone()!=null){
                   for(int i=0;i<countrycode.size();i++) {
                       if (upgradedGetAddressLists.get(position).getTelephone().contains(countrycode.get(i)))
                       {
                           trimphonenumber = upgradedGetAddressLists.get(position).getTelephone().replace(countrycode.get(i),"");
                           break;

                       }
                   }

               }
                Fragment prFrag = new UpgradeMapFragment();
                FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.upgradeNewAddressListLayout, prFrag).addToBackStack(null).detach(newAddressFragmentUpgrade).attach(newAddressFragmentUpgrade).commit();
                Bundle databund = new Bundle();
                //put your ArrayList data in bundle
                databund.putString("flag", "editAddress");
                databund.putString("addressId", upgradedGetAddressLists.get(position).getAddress_id());

                databund.putString("fname", upgradedGetAddressLists.get(position).getFirstname());
                databund.putString("lname", upgradedGetAddressLists.get(position).getLastname());
                databund.putString("street", upgradedGetAddressLists.get(position).getStreet());
                databund.putString("area", upgradedGetAddressLists.get(position).getArea() != null ? upgradedGetAddressLists.get(position).getArea().toString() : "");

                databund.putString("city", upgradedGetAddressLists.get(position).getCity());

                databund.putString("phone", trimphonenumber/*upgradedGetAddressLists.get(position).getTelephone()*/);

                databund.putString("countryId", upgradedGetAddressLists.get(position).getCountry_id());
                databund.putString("latitude", upgradedGetAddressLists.get(position).getLatitude());
                databund.putString("longitude", upgradedGetAddressLists.get(position).getLongitude());
                databund.putString("countryname",upgradedGetAddressLists.get(position).getCountry());
                prFrag.setArguments(databund);

            }
        });
    }

    @Override
    public int getItemCount() {
        return upgradedGetAddressLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userAddress;
        ImageView delete, defaultAddressCheck;
        LinearLayout addressSelected, editAddress, addressCheckContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            userAddress = itemView.findViewById(R.id.userAddress);
            defaultAddressCheck = itemView.findViewById(R.id.defaultAddressCheck);
            delete = itemView.findViewById(R.id.deleteCart);
            addressSelected = (LinearLayout) itemView.findViewById(R.id.addressSelected);
            editAddress = (LinearLayout) itemView.findViewById(R.id.editAddress);
            addressCheckContainer = (LinearLayout) itemView.findViewById(R.id.addressCheckContainer);
        }
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
