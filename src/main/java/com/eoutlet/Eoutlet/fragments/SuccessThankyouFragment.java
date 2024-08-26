package com.eoutlet.Eoutlet.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.activities.ThankyouActivity;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.review.testing.FakeReviewManager;
import com.google.android.play.core.tasks.Task;


public class SuccessThankyouFragment extends DialogFragment {
    private View view;
    private Dialog dialog;
    private ParentDialogsListener parentDialogListener;
    private ImageView closeImage;
    private RelativeLayout ratingsubmit;
    private RatingBar ratingbar;
    private ReviewManager reviewManager;
    public SuccessThankyouFragment() {
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
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

       if(MySharedPreferenceClass.getChoosenlanguage(getContext()).equalsIgnoreCase("ar")) {
           view = inflater.inflate(R.layout.success_thankyou, null);
       }
       else{
           view = inflater.inflate(R.layout.success_thankyou_english, null);
       }
        ratingsubmit = view.findViewById(R.id.ratingsubmit);
        ratingbar = view.findViewById(R.id.ratingBar);
        ratingbar.setRating(4);

        builder.setView(view);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_bg);


        ratingsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ratingbar.getRating() >= 3) {
                    openplaystore();
                    //reviewManager = ReviewManagerFactory.create(getContext());

                    //showRateApp();
                }
                else if(ratingbar.getRating() < 3  && ratingbar.getRating() > 0) {

                    clearBackStack();
                    Intent in = new Intent(getContext(), MainActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(in);




                    Activity activity = getActivity();
                    if (isAdded() && activity != null) {
                        getActivity().finish();
                        dialog.cancel();
                    }
                }

                else {




                }


            }
        });

        return dialog;
    }

    private void clearBackStack() {

        if (getFragmentManager() != null) {
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

    }
    public void showRateApp() {
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();

                Task<Void> flow = reviewManager.launchReviewFlow(getActivity(), reviewInfo);
                flow.addOnCompleteListener(task1 -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                });
            } else {
                
                // There was some problem, continue regardless of the result.
                // show native rate app dialog on error
                //showRateAppFallbackDialog();
            }
        });
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


    public void openplaystore() {

        final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }


    }
}