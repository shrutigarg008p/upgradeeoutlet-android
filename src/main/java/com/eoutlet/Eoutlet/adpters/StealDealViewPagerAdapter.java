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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.fragments.ProductDetail;
import com.eoutlet.Eoutlet.fragments.ProductList;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;

import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.NewArrival;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.StealDeal;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;


public class StealDealViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<StealDeal> newArrivalList;
    private FirebaseAnalytics mFirebaseAnalytics;
    public StealDealViewPagerAdapter(Context context, List<StealDeal> newArrivalList) {
        this.context = context;
        this.newArrivalList = newArrivalList;
    }


    @Override
    public int getCount() {
        return newArrivalList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.new_arrival_custom_layout, null);
        ImageView imageViewNewArrival = (ImageView) view.findViewById(R.id.imageViewNewArrival);
        Picasso.get().load(newArrivalList.get(position).image).into(imageViewNewArrival);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        try {
//            Glide.with(imageView)
//                    .load(newArrivalList.get(position).getImage())/*.apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))*/.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)
//                    .placeholder(R.drawable.progress_animation)/*.override(400, 1200)*/
//                    .into(imageView);
//        } catch (Exception e) {
//        }
        imageViewNewArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase_click_banner(newArrivalList.get(position).name,newArrivalList.get(position).id);
                if(  newArrivalList.get(position).caption.equalsIgnoreCase("product_list")) {
                    Log.e("NewArrival Adapter", newArrivalList.get(position).image);
                    Fragment productList = new ProductList();
                    FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.container2, productList).addToBackStack(null).commit();
                    Bundle databund = new Bundle();
                    //put your ArrayList data in bundle
                    databund.putInt("productId", Integer.parseInt(newArrivalList.get(position).id));
                    //databund.putSerializable("childeren", (Serializable) newArrivalList);
                    databund.putString("fromwhere", "fromhome");
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putString("name", newArrivalList.get(position).name);
                    databund.putString("onClick", "newArrival");
                    productList.setArguments(databund);
                }
                else if(newArrivalList.get(position).caption.equalsIgnoreCase("product_link")){
                    Fragment productList = new ProductDetail();
                    FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.container2, productList).addToBackStack(null).commit();
                    Bundle databund = new Bundle();
                    //put your ArrayList data in bundle
                    databund.putString("pid",newArrivalList.get(position).id);
                    databund.putString("name", newArrivalList.get(position).name);
                    databund.putSerializable("childeren", (Serializable) newArrivalList);
                    databund.putString("fromwhere", "fromhome");
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putString("onClick", "mainBanner");
                    productList.setArguments(databund);
                    Log.e("pid is--->>>",newArrivalList.get(position).id);

                }
                else if(newArrivalList.get(position).caption.equalsIgnoreCase("category")){
                    MySharedPreferenceClass.setCatId(getApplicationContext(), newArrivalList.get(position).id);
                    navigationView.setSelectedItemId(R.id.catagory);

                }


                else if(newArrivalList.get(position).caption.equalsIgnoreCase("item_type")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();

                    //databund.putInt("productId", Integer.valueOf(newalldata.get(toplistmainposition).id));
                    databund.putInt("productId", Integer.parseInt(newArrivalList.get(position).id));
                    databund.putString("name",  newArrivalList.get(position).name);
                    databund.putString("category", " ");

                    databund.putString("fromwhere", "fromsubcategories");
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putString("adapterposition", " ");
                    databund.putString("attributvalue", "Shoes");
                    databund.putString("attributdisplay", "65");
                    databund.putString("attributcount","1");


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(newArrivalList.get(position).caption.equalsIgnoreCase("manufacturer")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();


                    databund.putInt("productId", Integer.parseInt(newArrivalList.get(position).id));
                    databund.putString("name",  newArrivalList.get(position).name);
                    databund.putString("category", newArrivalList.get(position).name);
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putString("fromwhere", "fromcatagorybrands");
                    databund.putString("adapterposition", "1");
                    databund.putString("attributvalue", newArrivalList.get(position).attribute.value);
                    databund.putString("attributdisplay", newArrivalList.get(position).attribute.display);
                    databund.putString("attributcount",newArrivalList.get(position).attribute.count.toString());


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(newArrivalList.get(position).caption.equalsIgnoreCase("size")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();


                    databund.putInt("productId", Integer.parseInt(newArrivalList.get(position).id));
                    databund.putString("name",  newArrivalList.get(position).name);
                    databund.putString("category", newArrivalList.get(position).name);
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putString("fromwhere", "fromcatagorysize");
                    databund.putString("adapterposition", "1");
                    databund.putString("attributvalue", newArrivalList.get(position).attribute.value);
                    databund.putString("attributdisplay", newArrivalList.get(position).attribute.display);
                    databund.putString("attributcount",newArrivalList.get(position).attribute.count.toString());


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(newArrivalList.get(position).caption.equalsIgnoreCase("subcategory")){

                    MySharedPreferenceClass.setCatId(getApplicationContext(), newArrivalList.get(position).id);
                    MySharedPreferenceClass.setsubCatId(getApplicationContext(),newArrivalList.get(position).attribute.value);


                    navigationView.setSelectedItemId(R.id.catagory);






                }
            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
    public void firebase_click_banner(String promotionname,String promotionid) {
        Bundle bundle = new Bundle();

        bundle.putString(FirebaseAnalytics.Param.PROMOTION_NAME, promotionname);
        bundle.putString(FirebaseAnalytics.Param.PROMOTION_ID, promotionid);
        //bundle.putString(FirebaseAnalytics.Param.ITEM_LOCATION_ID, Locale.getDefault().getCountry());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, bundle);


    }
}