package com.eoutlet.Eoutlet.adpters;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class NewProfileAdapter extends RecyclerView.Adapter<NewProfileAdapter.MyViewHolder> {

    Context mContext;
    public List<String> profiletext;
    public List<String> profilehinttext;

    List<Integer> profileIcon;
    ExecuteFragment execute;
    int mydataset[];
    ViewListener viewlistener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,titlehint, year, genre;
        public ImageView iv, arrow_imageview, instagramImage, twitterImage,fbImage;
        public RelativeLayout parentlayout;

        LinearLayout edtprofileItemClick;
        public LinearLayout linearbackground;

        public MyViewHolder(View view) {
            super(view);
            iv = view.findViewById(R.id.profileImage);
            title = view.findViewById(R.id.profileName);
            titlehint = view.findViewById(R.id.hinttext);
            linearbackground = view.findViewById(R.id.red_background);
            edtprofileItemClick = view.findViewById(R.id.profileItemClick);
            arrow_imageview = view.findViewById(R.id.arrow_imageview);
            instagramImage = view.findViewById(R.id.instagramImage);
            twitterImage = view.findViewById(R.id.twitterImage);
            fbImage = view.findViewById(R.id.fbImage);
            parentlayout = view.findViewById(R.id.parentlayout);


        }
    }

    public void passData(Context context) {
        mContext = context;
        execute = (MainActivity) mContext;
    }

    public NewProfileAdapter(Context context, List<String> profiletext,  List<String> profilehinttext, int profileicon[], ViewListener viewListener) {

        this.profiletext = profiletext;
        this.profilehinttext = profilehinttext;
        this.mydataset = profileicon;
        mContext = context;
        this.viewlistener = viewListener;


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (MySharedPreferenceClass.getChoosenlanguage(parent.getContext()).equals("en")) {
            /*itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.new_profile_item_english, parent, false);*/

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.upgrade_profile_adapter_item, parent, false);

        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.upgrade_new_profile_item, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {





        if (position == 0  ) {
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams)holder.parentlayout.getLayoutParams();
            relativeParams.bottomMargin=70;
            relativeParams.topMargin = 30;
            holder.parentlayout.setLayoutParams(relativeParams);
        }
        if(position == 11){


            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams)holder.parentlayout.getLayoutParams();
            relativeParams.topMargin=150;
            holder.parentlayout.setLayoutParams(relativeParams);
            holder.titlehint.setVisibility(View.GONE);
        }
        if(position==12){
            holder.titlehint.setVisibility(View.GONE);
        }
        if(position==13){
            holder.titlehint.setVisibility(View.GONE);
        }
        if(position == 14){

            holder.titlehint.setVisibility(View.GONE);

            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams)holder.parentlayout.getLayoutParams();
            relativeParams.bottomMargin=150;
            holder.parentlayout.setLayoutParams(relativeParams);

        }


        if (position == 11) {
          //  holder.arrow_imageview.setVisibility(View.GONE);
            holder.twitterImage.setVisibility(View.VISIBLE);
            holder.instagramImage.setVisibility(View.VISIBLE);
            holder.fbImage.setVisibility(View.VISIBLE);
            holder.fbImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  try {
                        if (isAppInstalled(getApplicationContext(), "com.facebook.orca") || isAppInstalled(mContext, "com.facebook.katana")
                                || isAppInstalled(mContext, "com.example.facebook") || isAppInstalled(mContext, "com.facebook.android")) {
                            Uri uri = Uri.parse("https://www.facebook.com/EoutletCare");
                            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                            likeIng.setPackage("fb://page/EoutletCare");
                            //mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/1017099195060327")));
                        } else {
                            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/EoutletCare")));
                        }
                    }catch (Exception e){e.printStackTrace();}

*/





                    Uri uri = Uri.parse("https://www.facebook.com/EoutletCare");
                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                    likeIng.setPackage("com.facebook.katana");

                    try {
                        mContext.startActivity(likeIng);
                    } catch (ActivityNotFoundException e) {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.facebook.com/EoutletCare")));
                    }



                }


            });
            holder.instagramImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("instagramImage", "instagramImage");
                    Uri uri = Uri.parse("http://instagram.com/_u/eoutletcare");
                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                    likeIng.setPackage("com.instagram.android");

                    try {
                        mContext.startActivity(likeIng);
                    } catch (ActivityNotFoundException e) {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.instagram.com/eoutletcare/")));
                    }
                }
            });
            holder.twitterImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("twitterImage", "twitterImage");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=858678197977829376"));
                    intent.setPackage("com.twitter.android");
                    try {
                        mContext.startActivity(intent);
                    } catch (Exception e) {
                        // no Twitter app, revert to browser
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://twitter.com/eoutletcare")));
                    }

                }
            });
        } else {
            holder.arrow_imageview.setVisibility(View.VISIBLE);
            holder.twitterImage.setVisibility(View.GONE);
            holder.instagramImage.setVisibility(View.GONE);
        }


     /*   if(position == 4){

            holder.edtprofileItemClick.setVisibility(View.GONE);



        }*/
        holder.title.setText(profiletext.get(position));
        holder.titlehint.setText(profilehinttext.get(position));
        //Picasso.get().load(profileIcon.get(position)).into(holder.iv);
        holder.iv.setImageDrawable(mContext.getResources().getDrawable(mydataset[position]));
        //holder.iv.setImageResource(mydataset[position]);


        holder.edtprofileItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewlistener.onClick(position, v);
            }
        });


       /*Used for Guest User Case*/

        if( MySharedPreferenceClass.getMyUserId(getApplicationContext()).equals(" ")) {

            if(/*position == 1 ||*/ position == 2 || position == 3|| position == 4|| position == 5|| position == 6||
                    position == 7 || position == 14 || position==13
            )
            {
                holder.parentlayout.setVisibility(View.GONE);
            }


        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return profiletext.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public  boolean isAppInstalled(Context context, String packageName) {
        try {
           mContext.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}