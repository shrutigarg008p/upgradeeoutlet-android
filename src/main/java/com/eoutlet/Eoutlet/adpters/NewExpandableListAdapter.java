package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.fragments.NewCategoryFragment;
import com.eoutlet.Eoutlet.fragments.ProductList;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener2;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.CatagoryList;
import com.eoutlet.Eoutlet.pojo.SelectedPosition;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewExpandableListAdapter extends BaseExpandableListAdapter {

    ExecuteFragment execute;
    private Context context;
    private List<String> expandableListTitle;
    private List<String> expandableImageList;
    private List<String> expandablecaption;
    private List<List<CatagoryList>> expandableListDetail;
    ViewListener2 viewListener;
    SelectedPosition selectedPosition;

    ArrayList<Integer> imagelist = new ArrayList<>();
    private ImageView selectedImageView;
    private NewCategoryFragment newCategoryFragment;


    public NewExpandableListAdapter(Context context,NewCategoryFragment newCategoryFragment, List<String> expandableListTitle, List<String> expandableLitImage,
                                    List<String> groupCaption,
                                    List<List<CatagoryList>> expandableListDetail, ViewListener2 viewListener) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.expandableImageList = expandableLitImage;
        this.expandablecaption = groupCaption;
        this.viewListener = viewListener;
        this.newCategoryFragment = newCategoryFragment;
        execute = (MainActivity) context;

        //imagelist.add(R.drawable.shop1);
       // imagelist.add(R.drawable.shop2);
    }


    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(listPosition)
                .get(expandedListPosition).name;

    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(final int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.new_list_item, null);
        }
        final LinearLayout imgeeclick = (LinearLayout) convertView.findViewById(R.id.listitem);
     /*   imgeeclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = expandableListDetail.get(listPosition).get(expandedListPosition).id;
                viewListener.onClick(Integer.parseInt(id), view, expandableListDetail.get(listPosition).get(expandedListPosition).name, listPosition);

                //execute.ExecutFragmentListener(3);

            }
        });*/

        selectedPosition = new SelectedPosition();
        final LinearLayout relativeView = (LinearLayout) convertView.findViewById(R.id.relativeView);
        relativeView.removeAllViews();
        for (int i = 0; i < expandableListDetail.get(listPosition).size(); i++) {
            int k = i;

            View view;
            if(MySharedPreferenceClass.getChoosenlanguage(convertView.getContext()).equals("en")){
             view = LayoutInflater.from(context).inflate(R.layout.textview_item_english, null);

            }

            else {
           view = LayoutInflater.from(context).inflate(R.layout.textview_item, null);
            }


            RelativeLayout textviewParent = view.findViewById(R.id.textviewParent);
            TextView mTextView = view.findViewById(R.id.expandedListItem);
            ImageView imageView = view.findViewById(R.id.tick_imagecategory);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedPosition != null && !selectedPosition.getPos().equalsIgnoreCase("")) {
                        if (selectedPosition.getPos().equalsIgnoreCase(String.valueOf(k))) {
                            selectedPosition.setPos(String.valueOf(k));
                            imageView.setVisibility(View.VISIBLE);
                            String id = expandableListDetail.get(listPosition).get(expandedListPosition).id;
                            viewListener.onClick(Integer.parseInt(expandableListDetail.get(listPosition).get(k).id), view, expandableListTitle.get(listPosition),listPosition,k);
                        } else {
                            selectedPosition.setPos(String.valueOf(k));
                            imageView.setVisibility(View.VISIBLE);
                            if (selectedImageView != null)
                                selectedImageView.setVisibility(View.GONE);
                            selectedImageView = imageView;
                            String id = expandableListDetail.get(listPosition).get(expandedListPosition).id;
                            viewListener.onClick(Integer.parseInt(expandableListDetail.get(listPosition).get(k).id), view, expandableListTitle.get(listPosition),listPosition,k);
                        }
                    } else {
                        selectedPosition.setPos(String.valueOf(k));
                        imageView.setVisibility(View.VISIBLE);
                        selectedImageView = imageView;
                        String id = expandableListDetail.get(listPosition).get(expandedListPosition).id;
                        viewListener.onClick(Integer.parseInt(expandableListDetail.get(listPosition).get(k).id), view,expandableListTitle.get(listPosition),listPosition,k);
                    }
                }

            });
            View view1 = view.findViewById(R.id.expandviewline);
            if (i == expandableListDetail.get(listPosition).size() - 1) {
                view1.setVisibility(View.GONE);
            } else {
                view1.setVisibility(View.VISIBLE);
            }
            mTextView.setText(expandableListDetail.get(listPosition).get(i).name);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            relativeView.addView(view, params);

        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        if (expandableListDetail.get(listPosition) != null && expandableListDetail.get(listPosition).size() > 0) {
            return 1;
        } else {
            return 0;
        }

    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(MySharedPreferenceClass.getChoosenlanguage(parent.getContext()).equals("en")){

                convertView = layoutInflater.inflate(R.layout.new_list_group_english, null);
            }

            else {
                convertView = layoutInflater.inflate(R.layout.new_list_group, null);

            }

        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);

        TextView categoryCount = (TextView) convertView
                .findViewById(R.id.categorycount);
        TextView listDescription = (TextView) convertView
                .findViewById(R.id.listDescription);

        ImageView cut_img = (ImageView) convertView.findViewById(R.id.catagory_image);
        ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
        //Picasso.get().load(expandableImageList.get(listPosition)).resize(180, 180).centerCrop().into(cut_img);

        try {
            Glide.with(cut_img)
                    .load(expandableImageList.get(listPosition)).apply(RequestOptions.bitmapTransform(new RoundedCorners(12)))/*.skipMemoryCache(true)*//*.diskCacheStrategy(DiskCacheStrategy.NONE)*/.error(R.drawable.progress_animation)
                    /* .placeholder(R.drawable.progress_animation)*/.override(180, 180)

                    .into(cut_img);
        } catch (Exception e) {
        }
        RelativeLayout groupbuttonclick =convertView.findViewById(R.id.groupbuttonclick);
        if (expandableListDetail.get(listPosition).size()==0){
            iv.setVisibility(View.GONE);
            categoryCount.setVisibility(View.GONE);

        }
        else {
            iv.setVisibility(View.VISIBLE);
            categoryCount.setVisibility(View.VISIBLE);
        }
        listDescription.setText(expandablecaption.get(listPosition));

        if(MySharedPreferenceClass.getChoosenlanguage(parent.getContext()).equals("en")){

            categoryCount.setText(expandableListDetail.get(listPosition).size() + " " + "Categories");
        }
        else {
            categoryCount.setText("الفئات" + " " + expandableListDetail.get(listPosition).size());
        }


        listTitleTextView.setText(listTitle);

        if (isExpanded) {
            iv.setImageResource(R.drawable.category_uparrow);


        } else {
            iv.setImageResource(R.drawable.ic_notification);
        }

       /* cut_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandableListDetail.get(listPosition).size()==0){
                   newCategoryFragment.redirect(listPosition);
                }
            }
        });*/




        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
