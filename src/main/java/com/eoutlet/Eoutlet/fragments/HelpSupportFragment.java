package com.eoutlet.Eoutlet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.adpters.HelpSupportAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.HelpSupport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class HelpSupportFragment extends Fragment {

    RecyclerView helpsupportRecycler;
    HelpSupportAdapter helpsupportadapter;
    public ParentDialogsListener parentDialogListener;
    private ImageView searchImage, backarrow;
    private Toolbar toolbar1;
    View view;
    String Locale;
    int menuicon[] = {R.drawable.ic_dasboard, R.drawable.ic_address_book, R.drawable.ic_notification, R.drawable.ic_logout};
    List<String> profileString;
    List<String> dataContent = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                Locale = "ar";
                view = inflater.inflate(R.layout.fragment_help_support_arabic, container, false);
            } else {
                Locale = "en";
                view = inflater.inflate(R.layout.fragment_help_support, container, false);
            }
        } else {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_help_support_arabic, container, false);
        }
        helpsupportRecycler = view.findViewById(R.id.helpsupportRecycler);
        toolbar1 = view.findViewById(R.id.toolbar);
        searchImage = toolbar1.findViewById(R.id.serachbar);
        backarrow = toolbar1.findViewById((R.id.backarrow));
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment prFrag = new SearchResultFragment();
                Bundle databund = new Bundle();
                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.profileContainer, prFrag)
                        .commit();
            }
        });

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });


        profileString = new ArrayList<>();
        if (Locale == "ar") {
            profileString.add("إتصل بنا");
            profileString.add("سياسة الشحن والتوصيل ");
            profileString.add("سياسة الاستبدال والاسترجاع ");
            profileString.add("الاسئلة المتكررة");

        } else {
            profileString.add("Contact Us");
            profileString.add("Shipping and Delivery Policy");
            profileString.add("Exchange and Return Policy");
            profileString.add("FAQ");


        }
        helpsupportRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        helpsupportRecycler.setLayoutManager(mLayoutManager);
        helpsupportRecycler.setItemAnimator(new DefaultItemAnimator());
        helpsupportadapter = new HelpSupportAdapter(getContext(), profileString, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();
                switch (id) {
                    case R.id.helpItemClick://button for message
                        if (position == 0) {
                            Fragment shippingFragment = new ContactUsFragment();
                            getFragmentManager()
                                    .beginTransaction().addToBackStack(null)
                                    .replace(R.id.helpContainer, shippingFragment)
                                    .commit();

                        } else if (position == 1) {
                            Fragment faqFragment = new ShippingFragment();
                            getFragmentManager()
                                    .beginTransaction().addToBackStack(null)
                                    .replace(R.id.helpContainer, faqFragment)
                                    .commit();

                        } else if (position == 2) {
                            Fragment replacementFragment = new ReplacementFragment();
                            getFragmentManager()
                                    .beginTransaction().addToBackStack(null)
                                    .replace(R.id.helpContainer, replacementFragment)
                                    .commit();

                        } else if (position == 3) {
                            Fragment replacementFragment = new FAQFragment();
                            getFragmentManager()
                                    .beginTransaction().addToBackStack(null)
                                    .replace(R.id.helpContainer, replacementFragment)
                                    .commit();
                        }
                        break;
                }

            }
        });
        helpsupportRecycler.setAdapter(helpsupportadapter);


        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

            parentDialogListener = (ParentDialogsListener) context;

        } catch (ClassCastException e) {

            throw new ClassCastException(context.toString()
                    + " Activity's Parent should be Parent Activity");
        }

    }

    public void getHelpSupport() {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<HelpSupport> call = apiService.getHelpSupport();
        call.enqueue(new Callback<HelpSupport>() {
            @Override
            public void onResponse(Call<HelpSupport> call, Response<HelpSupport> response) {
                if (response.body() != null) {

                    parentDialogListener.hideProgressDialog();
                    if (response.body().getMsg().equalsIgnoreCase("success")) {
                        if (response.body().getData() != null) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                //profileString.add(String.valueOf(response.body().getData().get(i).getTitle()));
                                dataContent.add(response.body().getData().get(i).getContent());
                                helpsupportadapter.notifyDataSetChanged();
                            }

                            if (Locale == "ar") {
                                profileString.add("الشحن والتوصيل");
                                profileString.add("الأسئلة الشائعة");
                                profileString.add("سياسة الاستبدال والاسترجاع");
                                profileString.add("تواصل معنا");
                            } else {
                                profileString.add("Shipping and Delivery");
                                profileString.add("Common Questions");
                                profileString.add("Exchange and Return Policy");
                                profileString.add("Contact Us");
                            }
                        }

                    } else {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<HelpSupport> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }
}
