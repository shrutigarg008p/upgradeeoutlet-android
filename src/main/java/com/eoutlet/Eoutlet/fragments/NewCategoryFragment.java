package com.eoutlet.Eoutlet.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.adpters.NewExpandableListAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.ExpandableListDataPump;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.listener.ViewListener2;
import com.eoutlet.Eoutlet.listener.ViewListener5;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.CatagoryCollection;
import com.eoutlet.Eoutlet.pojo.CatagoryList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;


public class NewCategoryFragment extends Fragment {
    public ParentDialogsListener parentDialogListener;
    private  View v;
    private ImageView searchImage,backarrow;
    private ExpandableListView expandableListView;
    private NewExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    Toolbar toolbar,toolbar1;
    public static TextView toolbar_bedgetextcat,tooltext;
    View toolbarbeg;

    private boolean _hasLoadedOnce= false;
    private List<String> groupTitleList = new ArrayList<>();
    private List<String> groupImageList = new ArrayList<>();
    private List<String>  groupIdList = new ArrayList<>();
    private List<String> groupCaption = new ArrayList<>();
    private List<List<CatagoryList>> fullcatagory ;
    private Toolbar catagorytoolbar;
    private NewCategoryFragment newCategoryFragment;

    int listposition2 = 99;


    int expandedlistposition2 = 99;

    private NewCategoryFragment.OnFragmentInteractionListener mListener;

    public NewCategoryFragment() {

    }


    // TODO: Rename and change types and number of parameters
    public static NewCategoryFragment newInstance(String param1, String param2) {
        NewCategoryFragment fragment = new NewCategoryFragment();
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

    Context mContext;
    ExecuteFragment execute;


    public void passData(Context context) {
        mContext = context;
        execute = (MainActivity) mContext;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if(MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")){

            v = inflater.inflate(R.layout.fragment_new_category_english, container, false);

        }
        else
            {

            v = inflater.inflate(R.layout.fragment_new_category, container, false);

        }

        super.onCreateView(inflater,container,savedInstanceState);
        execute = (MainActivity)getActivity();
        newCategoryFragment = NewCategoryFragment.this;
        //toolbar1 = v.findViewById(R.id.catagorytoolbar);


        toolbar1 = v.findViewById(R.id.toolbar);
        searchImage = toolbar1.findViewById(R.id.serachbar);
        backarrow = toolbar1.findViewById((R.id.backarrow));

        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment prFrag = new SearchResultFragment();
                Bundle databund = new Bundle();


                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.categoryContainer, prFrag)
                        .commit();



            }
        });

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });







        expandableListView = (ExpandableListView) v.findViewById(R.id.expandableListView);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            parentDialogListener = (ParentDialogsListener) activity;

        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " Activity's Parent should be Parent Activity");
        }


    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void getUpgradedCatagoryDetail(final View v) {
        parentDialogListener.showProgressDialog();
        fullcatagory = new ArrayList<>();
        groupTitleList = new ArrayList<>();
        groupImageList = new ArrayList<>();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Call<CatagoryCollection> call;

        if(MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {

            call = apiService.getupgradedcatagoryDetailEnglish();
        }
        else {

             call = apiService.getupgradedcatagoryDetailArabic();
        }


        call.enqueue(new Callback<CatagoryCollection>() {
            @Override
            public void onResponse(Call<CatagoryCollection> call, Response<CatagoryCollection> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();

                    if (response.body().data.size() > 0) {

                        for (int i = 0; i < response.body().data.size(); i++) {
                            List<CatagoryList> catagory = new ArrayList<>();

                          /*  for (int j = 0; j < response.body().data.get(i).data.size(); j++) {

                                catagory.add(response.body().data.get(i).data.get(j));
                            }*/

                            fullcatagory.add(catagory);
                        }


                        initCatagoryList(fullcatagory, v);
                      /*  for (int i = 0; i < response.body().data.size(); i++) {
                            groupTitleList.add(response.body().data.get(i).name);
                            groupImageList.add(response.body().data.get(i).image);
                            groupIdList.add(response.body().data.get(i).id);
                            groupCaption.add(response.body().data.get(i).caption);
                        }*/


                    } else {
                        parentDialogListener.hideProgressDialog();


                    }
                } else {
                    parentDialogListener.hideProgressDialog();

                }
            }

            @Override
            public void onFailure(Call<CatagoryCollection> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });
    }
    /*public void getCatagoryDetail(final View v) {
        parentDialogListener.showProgressDialog();
        fullcatagory = new ArrayList<>();
        groupTitleList = new ArrayList<>();
        groupImageList = new ArrayList<>();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Call<CatagoryCollection> call = apiService.getcatagoryDetail();
        call.enqueue(new Callback<CatagoryCollection>() {
            @Override
            public void onResponse(Call<CatagoryCollection> call, Response<CatagoryCollection> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();

                    if (response.body().categorydata.size() > 0) {

                        for (int i = 0; i < response.body().categorydata.size(); i++) {
                            List<CatagoryList> catagory = new ArrayList<>();

                            for (int j = 0; j < response.body().categorydata.get(i).data.size(); j++) {

                                catagory.add(response.body().categorydata.get(i).data.get(j));
                            }

                            fullcatagory.add(catagory);
                        }


                        initCatagoryList(fullcatagory, v);
                        for (int i = 0; i < response.body().categorydata.size(); i++) {
                            groupTitleList.add(response.body().categorydata.get(i).name);
                            groupImageList.add(response.body().categorydata.get(i).image);
                            groupIdList.add(response.body().categorydata.get(i).id);
                            groupCaption.add(response.body().categorydata.get(i).caption);
                        }


                    } else {
                        parentDialogListener.hideProgressDialog();


                    }
                } else {
                    parentDialogListener.hideProgressDialog();

                }
            }

            @Override
            public void onFailure(Call<CatagoryCollection> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });
    }*/

    public void redirect(int pos){
        Fragment prFrag1 = new ProductList();
        Bundle databund1 = new Bundle();
        databund1.putInt("productId", Integer.parseInt(groupIdList.get(pos)));
        databund1.putString("name",groupTitleList.get(pos));
        databund1.putString("fromwhere","fromcatagory");
        prFrag1.setArguments(databund1);


        getFragmentManager()
                .beginTransaction().addToBackStack(null)
                .add(R.id.categoryContainer, prFrag1)
                .commit();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void initCatagoryList(final List<List<CatagoryList>> list, View v) {
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());

        expandableListAdapter = new NewExpandableListAdapter(getActivity(),newCategoryFragment, groupTitleList,groupImageList,groupCaption, list, new ViewListener2() {
            @Override
            public void onClick(int position, View view,String name,int mainposition,int k ) {
                int id = view.getId();


                switch (id) {

                    case R.id.textviewParent://button for message


                        //initCatagoryList(fullcatagory, v);

                        Fragment prFrag = new ProductList();
                        Bundle databund = new Bundle();
                        databund.putInt("productId", position);
                        databund.putString("name",name);
                        databund.putSerializable("childeren",(Serializable) list.get(mainposition));
                        databund.putString("fromwhere","fromcatagory");
                        databund.putString("adapterposition", String.valueOf(k));


                        prFrag.setArguments(databund);


                        getFragmentManager()
                                .beginTransaction().addToBackStack(null)
                                .add(R.id.categoryContainer, prFrag)
                                .commit();
                        break;

                    case R.id.groupbuttonclick:
                        Fragment prFrag1 = new ProductList();
                        Bundle databund1 = new Bundle();
                        databund1.putInt("productId", Integer.parseInt(groupIdList.get(position)));
                        databund1.putString("name",groupTitleList.get(position));
                        databund1.putString("fromwhere","fromcatagory");
                        prFrag1.setArguments(databund1);


                        getFragmentManager()
                                .beginTransaction().addToBackStack(null)
                                .add(R.id.categoryContainer, prFrag1)
                                .commit();
                        break;



                }
            }
        });


        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        expandableListView.setOnChildClickListener(  new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                return false;
            }
        });


        //It is used to open only one group chlids at a time.

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup){





                    expandableListView.collapseGroup(previousGroup);
                    previousGroup = groupPosition;
                }
                if(list.get(groupPosition).size()==0) {
                    Fragment prFrag1 = new ProductList();
                    Bundle databund1 = new Bundle();
                    databund1.putInt("productId", Integer.parseInt(groupIdList.get(groupPosition)));
                    databund1.putString("name", groupTitleList.get(groupPosition));
                    databund1.putString("fromwhere", "fromcatagory");

                    prFrag1.setArguments(databund1);


                    getFragmentManager()
                            .beginTransaction().addToBackStack(null)
                            .add(R.id.categoryContainer, prFrag1)
                            .commit();

                }
            }
        });
    }
    // your boolean field

    @Override
    public void setUserVisibleHint(boolean visible){
        super.setUserVisibleHint(visible);

        if (visible && isResumed()){

            searchImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Fragment prFrag = new SearchResultFragment();
                    Bundle databund = new Bundle();


                    getFragmentManager()
                            .beginTransaction().addToBackStack(null)
                            .replace(R.id.categoryContainer, prFrag)
                            .commit();



                    //Toast.makeText(getContext(),"dsfdsfdsfdsf",Toast.LENGTH_LONG).show();
                }
            });

        }
        if (visible && isResumed() && !_hasLoadedOnce){


             // getUpgradedCatagoryDetail(v);

              _hasLoadedOnce = true;

        }


    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onPause() {
        super.onPause();
        _hasLoadedOnce=false;

    }

}
