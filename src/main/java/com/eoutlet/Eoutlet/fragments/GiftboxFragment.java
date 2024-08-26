package com.eoutlet.Eoutlet.fragments;


import android.app.Dialog;
import android.content.Context;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eoutlet.Eoutlet.R;

import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.Giftwrapdetail;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class GiftboxFragment extends DialogFragment {
    private View view;
    private Dialog dialog;
    private TextView title;
    private ParentDialogsListener parentDialogListener;
    private ImageView closeImage,giftboxImage;
    private RelativeLayout closebtn;

    public GiftboxFragment() {
        // Required empty public constructor
    }





        public static RePasswordVerifyOtp newInstance(String param1, String param2) {
            RePasswordVerifyOtp fragment = new RePasswordVerifyOtp();
            Bundle args = new Bundle();

            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {

            }
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)  {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            if(MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                view = inflater.inflate(R.layout.fragment_giftbox_english, null);
            }
            else {
                view = inflater.inflate(R.layout.fragment_giftbox, null);
            }

            builder.setView(view);

            dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.WHITE));
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_bg);

            closeImage = view.findViewById(R.id.closeImage1);
            giftboxImage = view.findViewById(R.id.giftboxImage);
            closebtn = view.findViewById(R.id.closebtn);
            title = view.findViewById(R.id.title);

            closeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });


            closebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
getGiftwrapdetails();
            return dialog;
        }









        @Override
        public void onAttach(Context context) {
            try {
                // Instantiate the NoticeDialogListener so we can send events to the host
                parentDialogListener = (ParentDialogsListener) context;

            } catch (ClassCastException e) {
                // The activity doesn't implement the interface, throw exception
                throw new ClassCastException(context.toString()
                        + " Activity's Parent should be Parent Activity");
            }
            super.onAttach(context);
        }

        @Override
        public void onDetach() {
            super.onDetach();

        }

        public interface OnFragmentInteractionListener {
            // TODO: Update argument type and name
            void onFragmentInteraction(Uri uri);
        }

    public void getGiftwrapdetails(){

        /*https://upgrade.eoutlet.com/rest/en/V1/mpGiftWrap/wrappers*/




        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);




        Call<Giftwrapdetail> call = apiService.getGiftWraDetails("Bearer"+" "+MySharedPreferenceClass.getToken(getContext()) ,"https://upgrade.eoutlet.com/rest/en/V1/mpGiftWrap/wrappers");//739


        call.enqueue(new Callback<Giftwrapdetail>() {
            @Override
            public void onResponse(Call<Giftwrapdetail> call, Response<Giftwrapdetail> response) {


                if (response.body() != null) {


                    if (response.isSuccessful() && response.body() != null) {
                        parentDialogListener.hideProgressDialog();
                        title.setText(response.body().items.get(0).name);
                        Glide.with(  giftboxImage )
                                .load("https://upgrade.eoutlet.com/pub/media/"+response.body().items.get(0).image).apply(RequestOptions.bitmapTransform(new RoundedCorners(80))).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)/*error(R.drawable.progress_animation)*/
                                /* .placeholder(R.drawable.progress_animation)*/.override(1000, 800)
                                .into(  giftboxImage );

                    } else {



                    }


                }


            }


            @Override
            public void onFailure(Call<Giftwrapdetail> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());


            }
        });


    }
}