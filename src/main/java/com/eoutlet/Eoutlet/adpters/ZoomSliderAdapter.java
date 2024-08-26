package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.fragments.ZoomImageFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ZoomSliderAdapter extends PagerAdapter {


    private Context context;
    private List<String> mydata;
    private int finalposition;

    long duration = 100;
    ImageView iv;
    private Matrix matrix = new Matrix();
    public ZoomSliderAdapter(Context context, List<String> productsImage,int finalposition) {
        this.context = context;
        this.mydata = productsImage;
        this.finalposition = finalposition;

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
    public Object instantiateItem(final ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.zoom_image_slider_layout, null);

        // iv = view.findViewById(R.id.slider_item);

        iv = (ImageView) view.findViewById(R.id.zoom_slider_item);


        // iv.setImage(ImageSource.resource(mydata.get(position)));

        Picasso.get().load(mydata.get(position)).placeholder(R.drawable.progress_animation).into(iv);





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
