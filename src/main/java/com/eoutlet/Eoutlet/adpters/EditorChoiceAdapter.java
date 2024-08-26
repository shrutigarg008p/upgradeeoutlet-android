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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.fragments.ProductDetail;
import com.eoutlet.Eoutlet.fragments.ProductList;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.Serializable;
import java.util.List;

public class EditorChoiceAdapter extends RecyclerView.Adapter<EditorChoiceAdapter.MyViewHolder> {
    private List<com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.Recommended> editorChoiceList;
    private Context context;
    private FirebaseAnalytics mFirebaseAnalytics;
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView editorChoiceImage;

        MyViewHolder(View view) {
            super(view);
            editorChoiceImage = view.findViewById(R.id.editorChoiceImage);
        }
    }

    public EditorChoiceAdapter(Context context, List<com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.Recommended> editorChoiceList) {
        this.context = context;
        this.editorChoiceList = editorChoiceList;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.editor_choice_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            Glide.with(holder.editorChoiceImage.getContext())
                    .load(editorChoiceList.get(position).image)/*.apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))*/.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).error(R.drawable.progress_animation)
                    .placeholder(R.drawable.progress_animation)
                    .override(1000, 1200)
                    .into(holder.editorChoiceImage);
        } catch (Exception e) {
        }
        holder.editorChoiceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase_click_banner(editorChoiceList.get(position).name,editorChoiceList.get(position).id);
                if(  editorChoiceList.get(position).caption.equalsIgnoreCase("product_list")) {
                Fragment productList = new ProductList();
                FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.container2, productList).addToBackStack(null).commit();
                Bundle databund = new Bundle();
                //put your ArrayList data in bundle
                databund.putInt("productId", Integer.parseInt(editorChoiceList.get(position).id));
                databund.putString("name", editorChoiceList.get(position).name);
                //databund.putSerializable("childeren", (Serializable) editorChoiceList);
                databund.putString("fromwhere", "fromhome");
                    databund.putString("previouspage","FromHomeScreen");
                databund.putString("onClick", "editorChoice");
                productList.setArguments(databund);
            }
                else if(editorChoiceList.get(position).caption.equalsIgnoreCase("product_link")){
                    Fragment productList = new ProductDetail();
                    FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.container2, productList).addToBackStack(null).commit();
                    Bundle databund = new Bundle();
                    //put your ArrayList data in bundle
                    databund.putString("pid",editorChoiceList.get(position).id);
                    databund.putString("name", editorChoiceList.get(position).name);
                    databund.putSerializable("childeren", (Serializable) editorChoiceList);
                    databund.putString("fromwhere", "fromhome");
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putString("onClick", "mainBanner");
                    productList.setArguments(databund);
                    Log.e("pid is--->>>",editorChoiceList.get(position).id);


                }

                else if(editorChoiceList.get(position).caption.equalsIgnoreCase("item_type")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();


                    databund.putInt("productId", Integer.parseInt(editorChoiceList.get(position).id));
                    databund.putString("name",  editorChoiceList.get(position).name);
                    databund.putString("category", editorChoiceList.get(position).name);

                    databund.putString("fromwhere", "fromsubcategories");
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putString("adapterposition", "1");
                    databund.putString("attributvalue", editorChoiceList.get(position).attribute.value);
                    databund.putString("attributdisplay", editorChoiceList.get(position).attribute.display);
                    databund.putString("attributcount",editorChoiceList.get(position).attribute.count.toString());


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(editorChoiceList.get(position).caption.equalsIgnoreCase("category")){

                    MySharedPreferenceClass.setCatId(getApplicationContext(), editorChoiceList.get(position).id);
                    navigationView.setSelectedItemId(R.id.catagory);
                }
                else if(editorChoiceList.get(position).caption.equalsIgnoreCase("manufacturer")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();


                    databund.putInt("productId", Integer.parseInt(editorChoiceList.get(position).id));
                    databund.putString("name",  editorChoiceList.get(position).name);
                    databund.putString("category", editorChoiceList.get(position).name);
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putString("fromwhere", "fromcatagorybrands");
                    databund.putString("adapterposition", "1");
                    databund.putString("attributvalue", editorChoiceList.get(position).attribute.value);
                    databund.putString("attributdisplay", editorChoiceList.get(position).attribute.display);
                    databund.putString("attributcount",editorChoiceList.get(position).attribute.count.toString());


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(editorChoiceList.get(position).caption.equalsIgnoreCase("size")){
                    Fragment prFrag = new ProductList();
                    Bundle databund = new Bundle();


                    databund.putInt("productId", Integer.parseInt(editorChoiceList.get(position).id));
                    databund.putString("name",  editorChoiceList.get(position).name);
                    databund.putString("category", editorChoiceList.get(position).name);
                    databund.putString("previouspage","FromHomeScreen");
                    databund.putString("fromwhere", "fromcatagorysize");
                    databund.putString("adapterposition", "1");
                    databund.putString("attributvalue", editorChoiceList.get(position).attribute.value);
                    databund.putString("attributdisplay", editorChoiceList.get(position).attribute.display);
                    databund.putString("attributcount",editorChoiceList.get(position).attribute.count.toString());


                    prFrag.setArguments(databund);



                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.container2, prFrag)
                            .commit();

                }
                else if(editorChoiceList.get(position).caption.equalsIgnoreCase("subcategory")){

                    MySharedPreferenceClass.setCatId(getApplicationContext(), editorChoiceList.get(position).id);
                     MySharedPreferenceClass.setsubCatId(getApplicationContext(),editorChoiceList.get(position).attribute.value);

                    //MySharedPreferenceClass.setCatId(getApplicationContext(),"10");
                    //MySharedPreferenceClass.setsubCatId(getApplicationContext(),"75");
                    navigationView.setSelectedItemId(R.id.catagory);




                }


            }

        });

    }

    @Override
    public int getItemCount() {
        return editorChoiceList.size();
    }


    public void firebase_click_banner(String promotionname,String promotionid) {
        Bundle bundle = new Bundle();

        bundle.putString(FirebaseAnalytics.Param.PROMOTION_NAME, promotionname);
        bundle.putString(FirebaseAnalytics.Param.PROMOTION_ID, promotionid);
        //bundle.putString(FirebaseAnalytics.Param.ITEM_LOCATION_ID, Locale.getDefault().getCountry());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, bundle);


    }
}