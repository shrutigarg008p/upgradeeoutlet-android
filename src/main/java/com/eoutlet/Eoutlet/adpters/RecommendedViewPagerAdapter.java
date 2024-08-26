
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
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.FeatureBrand;
import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.Recommended;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;


public class RecommendedViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<FeatureBrand> recommendedList;
    private FirebaseAnalytics mFirebaseAnalytics;
    public RecommendedViewPagerAdapter(Context context, List<FeatureBrand> recommendedList) {
        this.context = context;
        this.recommendedList = recommendedList;
    }

    @Override
    public int getCount() {
        return recommendedList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.recommended_custom_layout, null);
        ImageView imageViewRecommended = (ImageView) view.findViewById(R.id.imageViewRecommended);
        Picasso.get().load(recommendedList.get(position).image).into(imageViewRecommended);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

//        try {
//            Glide.with(imageView)
//                    .load(recommendedList.get(position).getImage())/*.apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))*/.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.progress_animation)
//                    .placeholder(R.drawable.progress_animation)/*.override(400, 1200)*/
//                    .into(imageView);
//        } catch (Exception e) {
//        }
        imageViewRecommended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase_click_banner(recommendedList.get(position).name,recommendedList.get(position).id);
                if(recommendedList.get(position).caption.equalsIgnoreCase("product_list")) {
                Fragment productList = new ProductList();
                FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.container2, productList).addToBackStack(null).commit();
                Bundle databund = new Bundle();
                //put your ArrayList data in bundle
                databund.putInt("productId", Integer.parseInt(recommendedList.get(position).id));
                databund.putString("name", recommendedList.get(position).name);
                databund.putSerializable("childeren", (Serializable) recommendedList);
                databund.putString("fromwhere", "fromhome");
                    databund.putString("previouspage","FromHomeScreen");
                databund.putString("onClick", "recommended");
                productList.setArguments(databund);
            }
                else if(recommendedList.get(position).caption.equalsIgnoreCase("product_link")){
                    Fragment productList = new ProductDetail();
                    FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.container2, productList).addToBackStack(null).commit();
                    Bundle databund = new Bundle();
                    //put your ArrayList data in bundle
                    databund.putString("pid",recommendedList.get(position).id);
                    databund.putString("name", recommendedList.get(position).name);
                    databund.putSerializable("childeren", (Serializable) recommendedList);
                    databund.putString("fromwhere", "fromhome");
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putString("onClick", "mainBanner");
                    productList.setArguments(databund);
                    Log.e("pid is--->>>",recommendedList.get(position).id);


                }
                else if(recommendedList.get(position).caption.equalsIgnoreCase("category")){

                    MySharedPreferenceClass.setCatId(getApplicationContext(), recommendedList.get(position).id);
                    navigationView.setSelectedItemId(R.id.catagory);
                }
                else if(recommendedList.get(position).caption.equalsIgnoreCase("item_type")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();


                    databund.putInt("productId", Integer.parseInt(recommendedList.get(position).id));
                    databund.putString("name",  recommendedList.get(position).name);
                    databund.putString("category", recommendedList.get(position).name);
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putString("fromwhere", "fromsubcategories");
                    databund.putString("adapterposition", "1");
                    databund.putString("attributvalue", recommendedList.get(position).attribute.value);
                    databund.putString("attributdisplay", recommendedList.get(position).attribute.display);
                    databund.putString("attributcount",recommendedList.get(position).attribute.count.toString());


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(recommendedList.get(position).caption.equalsIgnoreCase("manufacturer")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();
                    databund.putString("previouspage","FromHomeScreen");

                    databund.putInt("productId", Integer.parseInt(recommendedList.get(position).id));
                    databund.putString("name",  recommendedList.get(position).name);
                    databund.putString("category", recommendedList.get(position).name);

                    databund.putString("fromwhere", "fromcatagorybrands");
                    databund.putString("adapterposition", "1");
                    databund.putString("attributvalue", recommendedList.get(position).attribute.value);
                    databund.putString("attributdisplay", recommendedList.get(position).attribute.display);
                    databund.putString("attributcount",recommendedList.get(position).attribute.count.toString());


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(recommendedList.get(position).caption.equalsIgnoreCase("size")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();

                    databund.putString("previouspage","FromHomeScreen");
                    databund.putInt("productId", Integer.parseInt(recommendedList.get(position).id));
                    databund.putString("name",  recommendedList.get(position).name);
                    databund.putString("category", recommendedList.get(position).name);

                    databund.putString("fromwhere", "fromcatagorysize");
                    databund.putString("adapterposition", "1");
                    databund.putString("attributvalue", recommendedList.get(position).attribute.value);
                    databund.putString("attributdisplay", recommendedList.get(position).attribute.display);
                    databund.putString("attributcount",recommendedList.get(position).attribute.count.toString());


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(recommendedList.get(position).caption.equalsIgnoreCase("subcategory")){

                    MySharedPreferenceClass.setCatId(getApplicationContext(), recommendedList.get(position).id);
                    MySharedPreferenceClass.setsubCatId(getApplicationContext(),recommendedList.get(position).attribute.value);


                    navigationView.setSelectedItemId(R.id.catagory);




                }
            }
            }
              );
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