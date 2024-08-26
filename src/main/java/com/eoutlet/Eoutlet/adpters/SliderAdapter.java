package com.eoutlet.Eoutlet.adpters;

import android.app.Activity;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;



import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.fragments.FilterFragmentNew;
import com.eoutlet.Eoutlet.fragments.ProductDetail;
import com.eoutlet.Eoutlet.fragments.ProductList;
import com.eoutlet.Eoutlet.fragments.ZoomImageFragment;
import com.eoutlet.Eoutlet.utility.TouchImageView;
import com.github.chrisbanes.photoview.PhotoView;
import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;
import com.vatsal.imagezoomer.ImageZoomButton;
import com.vatsal.imagezoomer.ZoomAnimation;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private List<String> mydata;

    long duration = 100;
    ImageView iv;
    private Matrix matrix = new Matrix();
    public SliderAdapter(Context context, List<String> productsImage) {
        this.context = context;
        this.mydata = productsImage;

    }

    @Override
    public int getCount() {
        return mydata.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image_slider_layout, null);

      // iv = view.findViewById(R.id.slider_item);

        iv = (ImageView) view.findViewById(R.id.slider_item);


        // iv.setImage(ImageSource.resource(mydata.get(position)));

        Picasso.get().load(mydata.get(position))/*.placeholder(R.drawable.progress_animation)*/.into(iv);


     iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MainActivity activity = (MainActivity) (context);
                FragmentManager fm = activity.getSupportFragmentManager();

                Bundle bund = new Bundle();
                bund.putStringArrayList("zoomimagelist",(ArrayList<String>) mydata);
                bund.putInt("position",position);
                 ZoomImageFragment alertDialog = new ZoomImageFragment();
                 alertDialog.setArguments(bund);
                alertDialog.show(fm, "fragment_alert");


            }
        });


        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, position);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }


}