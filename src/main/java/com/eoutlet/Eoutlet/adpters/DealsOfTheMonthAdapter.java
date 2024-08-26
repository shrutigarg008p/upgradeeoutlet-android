package com.eoutlet.Eoutlet.adpters;

import static com.eoutlet.Eoutlet.activities.MainActivity.navigationView;
import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.fragments.ProductDetail;
import com.eoutlet.Eoutlet.fragments.ProductList;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class DealsOfTheMonthAdapter extends RecyclerView.Adapter<DealsOfTheMonthAdapter.MyViewHolder> {
    private List<com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.MonthDeal> monthDealList;
    private Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView dealsOfTheMonthImage;

        MyViewHolder(View view) {
            super(view);
            dealsOfTheMonthImage = view.findViewById(R.id.dealsOfTheMonthImage);
        }
    }

    public DealsOfTheMonthAdapter(Context context, List<com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.MonthDeal> monthDealList) {
        this.context = context;
        this.monthDealList = monthDealList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deals_of_the_month_list, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Picasso.get().load(monthDealList.get(position).image).into(holder.dealsOfTheMonthImage);
        holder.dealsOfTheMonthImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(  monthDealList.get(position).caption.equalsIgnoreCase("product_list")) {

                Fragment productList = new ProductList();
                FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.container2, productList).addToBackStack(null).commit();
                Bundle databund = new Bundle();
                //put your ArrayList data in bundle
                databund.putInt("productId", Integer.parseInt(monthDealList.get(position).id));
                databund.putString("name", monthDealList.get(position).name);
                databund.putSerializable("childeren", (Serializable) monthDealList);
                databund.putString("fromwhere", "fromhome");
                    databund.putString("previouspage","FromHomeScreen");
                databund.putString("onClick", "dealOfTheMonth");
                productList.setArguments(databund);
            }

                else if(monthDealList.get(position).caption.equalsIgnoreCase("product_link")){
                    Fragment productList = new ProductDetail();
                    FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.container2, productList).addToBackStack(null).commit();
                    Bundle databund = new Bundle();
                    //put your ArrayList data in bundle
                    databund.putString("pid",monthDealList.get(position).id);
                    databund.putString("name", monthDealList.get(position).name);
                    databund.putSerializable("childeren", (Serializable) monthDealList);
                    databund.putString("fromwhere", "fromhome");
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putString("onClick", "mainBanner");
                    productList.setArguments(databund);
                    Log.e("pid is--->>>",monthDealList.get(position).id);


                }
                else if(monthDealList.get(position).caption.equalsIgnoreCase("category")){

                    MySharedPreferenceClass.setCatId(getApplicationContext(), monthDealList.get(position).id);
                    navigationView.setSelectedItemId(R.id.catagory);
                }

                else if(monthDealList.get(position).caption.equalsIgnoreCase("item_type")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();


                    databund.putInt("productId", Integer.parseInt(monthDealList.get(position).id));
                    databund.putString("name",  monthDealList.get(position).name);
                    databund.putString("category", monthDealList.get(position).name);
                    databund.putString("previouspage","HomeScreen");
                    databund.putString("fromwhere", "fromsubcategories");
                    databund.putString("adapterposition", "1");
                    databund.putString("attributvalue", monthDealList.get(position).attribute.value);
                    databund.putString("attributdisplay", monthDealList.get(position).attribute.display);
                    databund.putString("attributcount",monthDealList.get(position).attribute.count.toString());


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(monthDealList.get(position).caption.equalsIgnoreCase("manufacturer")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();


                    databund.putInt("productId", Integer.parseInt(monthDealList.get(position).id));
                    databund.putString("name",  monthDealList.get(position).name);
                    databund.putString("category", monthDealList.get(position).name);
                    databund.putString("previouspage","HomeScreen");
                    databund.putString("fromwhere", "fromcatagorybrands");
                    databund.putString("adapterposition", "1");
                    databund.putString("attributvalue", monthDealList.get(position).attribute.value);
                    databund.putString("attributdisplay", monthDealList.get(position).attribute.display);
                    databund.putString("attributcount",monthDealList.get(position).attribute.count.toString());


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(monthDealList.get(position).caption.equalsIgnoreCase("size")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();


                    databund.putInt("productId", Integer.parseInt(monthDealList.get(position).id));
                    databund.putString("name",  monthDealList.get(position).name);
                    databund.putString("category", monthDealList.get(position).name);
                    databund.putString("previouspage","HomeScreen");
                    databund.putString("fromwhere", "fromcatagorysize");
                    databund.putString("adapterposition", "1");
                    databund.putString("attributvalue", monthDealList.get(position).attribute.value);
                    databund.putString("attributdisplay", monthDealList.get(position).attribute.display);
                    databund.putString("attributcount",monthDealList.get(position).attribute.count.toString());


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(monthDealList.get(position).caption.equalsIgnoreCase("subcategory")){

                    MySharedPreferenceClass.setCatId(getApplicationContext(), monthDealList.get(position).id);
                    MySharedPreferenceClass.setsubCatId(getApplicationContext(),monthDealList.get(position).attribute.value);

                    //MySharedPreferenceClass.setCatId(getApplicationContext(),"10");
                    //MySharedPreferenceClass.setsubCatId(getApplicationContext(),"75");
                    navigationView.setSelectedItemId(R.id.catagory);




                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return monthDealList.size();
    }
}