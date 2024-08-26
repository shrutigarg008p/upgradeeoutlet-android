package com.eoutlet.Eoutlet.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.adpters.SliderAdapter;
import com.eoutlet.Eoutlet.adpters.ZoomSliderAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class ZoomImageFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM4 = "param4";
    Dialog dialog;
    private ImageView iv_close;
    private int position;
   private ViewPager viewPager;

    View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam4;
    private TextView currentpage;

    private ArrayList<String> zoomIamges;



    public ZoomImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param4 Parameter 4.
     * @return A new instance of fragment ZoomImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ZoomImageFragment newInstance(String param1, String param4) {
        ZoomImageFragment fragment = new ZoomImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam4 = getArguments().getString(ARG_PARAM4);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);

        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.fragment_zoom_image, null);
        iv_close = view.findViewById(R.id.imageView_close);
        currentpage = view.findViewById(R.id.currentpage);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        zoomIamges = getArguments().getStringArrayList("zoomimagelist");
        position = getArguments().getInt("position");

        intiViewPager(zoomIamges);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.indicatorzoom);
        tabLayout.setupWithViewPager(viewPager, true);



        builder.setView(view);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));


        return dialog;


        // return view;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    public void intiViewPager(final List<String> producudetail) {
        viewPager = view.findViewById(R.id.zoomviewPager);

        viewPager.setOffscreenPageLimit(producudetail.size());
        viewPager.setAdapter(new ZoomSliderAdapter(view.getContext(), producudetail,position));
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(position);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {




                currentpage.setText(position+1+"/"+producudetail.size());



            }

            public void onPageSelected(int position) {
                // Check if this is the page you want.
            }
        });

    }



    public class HackyDrawerLayout extends DrawerLayout {

        public HackyDrawerLayout(Context context) {
            super(context);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            try {
                return super.onInterceptTouchEvent(ev);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return false;
            }
        }
        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            try {
                return super.onTouchEvent(ev);
            } catch (IllegalArgumentException e) {
                //Fix for support lib bug, happening when onDestroy() is
                return true;
            }
        }
    }

}
