package com.eoutlet.Eoutlet.fragments;

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
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.MainBanner;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    List<MainBanner> mainBannerList;
    private FirebaseAnalytics mFirebaseAnalytics;
    public ViewPagerAdapter(Context context, List<MainBanner> mainBannerList) {
        this.context = context;
        this.mainBannerList = mainBannerList;
    }

    @Override
    public int getCount() {
        return mainBannerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        Picasso.get().load(mainBannerList.get(position).image).into(imageView);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase_click_banner(mainBannerList.get(position).name,mainBannerList.get(position).id);
                if(mainBannerList.get(position).caption.equalsIgnoreCase("product_list")) {
                    Fragment productList = new ProductList();
                    FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.container2, productList).addToBackStack(null).commit();
                    Bundle databund = new Bundle();
                    //put your ArrayList data in bundle
                    databund.putInt("productId", Integer.parseInt(mainBannerList.get(position).id));
                    databund.putString("name", mainBannerList.get(position).name);
                    databund.putSerializable("childeren", (Serializable) mainBannerList);
                    databund.putString("fromwhere", "fromhome");
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putString("onClick", "mainBanner");
                    productList.setArguments(databund);
                }

                else if(mainBannerList.get(position).caption.equalsIgnoreCase("product_link")){
                    Fragment productList = new ProductDetail();
                    FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.container2, productList).addToBackStack(null).commit();
                    Bundle databund = new Bundle();
                    //put your ArrayList data in bundle
                    databund.putString("pid",mainBannerList.get(position).id);
                    databund.putString("name", mainBannerList.get(position).name);
                    databund.putSerializable("childeren", (Serializable) mainBannerList);
                    databund.putString("fromwhere", "fromhome");
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putString("onClick", "mainBanner");
                    productList.setArguments(databund);
                    Log.e("pid is--->>>",mainBannerList.get(position).id);
                }
                else if(mainBannerList.get(position).caption.equalsIgnoreCase("category")){

                    MySharedPreferenceClass.setCatId(getApplicationContext(), mainBannerList.get(position).id);
                    navigationView.setSelectedItemId(R.id.catagory);

                }
                else if(mainBannerList.get(position).caption.equalsIgnoreCase("item_type")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();

                    databund.putString("previouspage","FromHomeScreen");
                    databund.putInt("productId", Integer.parseInt(mainBannerList.get(position).id));
                    databund.putString("name",  mainBannerList.get(position).name);
                    databund.putString("category", mainBannerList.get(position).name);

                    databund.putString("fromwhere", "fromsubcategories");
                    databund.putString("adapterposition", "1");
                    databund.putString("attributvalue", mainBannerList.get(position).attribute.value);
                    databund.putString("attributdisplay", mainBannerList.get(position).attribute.display);
                    databund.putString("attributcount",mainBannerList.get(position).attribute.count.toString());


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(mainBannerList.get(position).caption.equalsIgnoreCase("manufacturer")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();
                    databund.putString("previouspage","FromHomeScreen");

                    databund.putInt("productId", Integer.parseInt(mainBannerList.get(position).id));
                    databund.putString("name",  mainBannerList.get(position).name);
                    databund.putString("category", mainBannerList.get(position).name);

                    databund.putString("fromwhere", "fromcatagorybrands");
                    databund.putString("adapterposition", "1");
                    databund.putString("attributvalue", mainBannerList.get(position).attribute.value);
                    databund.putString("attributdisplay", mainBannerList.get(position).attribute.display);
                    databund.putString("attributcount",mainBannerList.get(position).attribute.count.toString());


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(mainBannerList.get(position).caption.equalsIgnoreCase("size")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();

                    databund.putString("previouspage","FromHomeScreen");
                    databund.putInt("productId", Integer.parseInt(mainBannerList.get(position).id));
                    databund.putString("name",  mainBannerList.get(position).name);
                    databund.putString("category", mainBannerList.get(position).name);

                    databund.putString("fromwhere", "fromcatagorysize");
                    databund.putString("adapterposition", "1");
                    databund.putString("attributvalue", mainBannerList.get(position).attribute.value);
                    databund.putString("attributdisplay", mainBannerList.get(position).attribute.display);
                    databund.putString("attributcount",mainBannerList.get(position).attribute.count.toString());


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(mainBannerList.get(position).caption.equalsIgnoreCase("subcategory")){

                   MySharedPreferenceClass.setCatId(getApplicationContext(), mainBannerList.get(position).id);
                   MySharedPreferenceClass.setsubCatId(getApplicationContext(),mainBannerList.get(position).attribute.value);

                  // MySharedPreferenceClass.setCatId(getApplicationContext(),"10");
                  //  MySharedPreferenceClass.setsubCatId(getApplicationContext(),"23423423");
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