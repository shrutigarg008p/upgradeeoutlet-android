package com.eoutlet.Eoutlet.adpters;

import android.content.Context;

import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HomeSliderAdapter extends PagerAdapter {

    private Context context;
    private int mydata[];
    private int currnet=0;
    private LayoutInflater inflater;
    ViewListener viewListener;
    private ArrayList<String> IMAGES;




    public HomeSliderAdapter(Context context, ArrayList<String> IMAGES, ViewListener listener) {


        this.context = context;
        this.mydata = mydata;
        this.IMAGES=IMAGES;
        this.viewListener = listener;

        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {

        if(IMAGES.size()>1) {
            return Integer.MAX_VALUE;
        }
        else {

            return  1;

        }

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (currnet>IMAGES.size()-1){
           currnet=0;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.home_slider_item, false);
        View view = inflater.inflate(R.layout.home_slider_item, container, false);


        ImageView iv = view.findViewById(R.id.home_slider_item);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //Toast.makeText(view.getContext(),position+"click",Toast.LENGTH_SHORT).show();


                //viewListener.onClick(currnet-1, v);

                viewListener.onClick(position%IMAGES.size(),v);





            }
        });






        //iv.setImageResource(IMAGES.get(currnet));


        try {
            Glide.with(iv)
                    .load(IMAGES.get(currnet)).error(R.drawable.progress_animation).fitCenter()
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.progress_animation)
                    .into(iv);
        } catch (Exception e) {
        }

    currnet++;

//        value++;




        ((ViewPager)container).addView(view);




/*
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, position);*/


        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        /*ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);*/
        container.removeView((View) object);

    }


    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }



}


