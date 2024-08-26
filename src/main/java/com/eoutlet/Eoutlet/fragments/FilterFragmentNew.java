package com.eoutlet.Eoutlet.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.adpters.FilterCatagoryListAdapter;
import com.eoutlet.Eoutlet.adpters.FilterDetailAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.FilterDetail;
import com.eoutlet.Eoutlet.pojo.UpgradeFilterListData;
import com.eoutlet.Eoutlet.pojo.UpgradeFilterListResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class FilterFragmentNew extends DialogFragment implements Cloneable {
    View view;
    private FilterCatagoryListAdapter mAdapter;
    private FilterDetailAdapter mAdapter2;
    private LinearLayout filterclick;
    private int value;
    private String prevsize, previtemtype, prevmanufec;
    String Locale;
    StringBuilder manufecturer = new StringBuilder();
    StringBuilder item_type = new StringBuilder();
    StringBuilder size = new StringBuilder();
    private ProgressDialog progressDialog1;

    static boolean applied = false;

    Dialog dialog;

    private RecyclerView catagorylistRecycler;
    private RecyclerView catagorydetailRecycler;
    ProgressDialog progressDialog;
    public ParentDialogsListener parentDialogListener;
    LinearLayout apply;
    private List<String> listname = new ArrayList<>();
    private List<String> preselectedlistname = new ArrayList<>();

    private List<List<String>> mainCatList2 = new ArrayList<>();
    private List<List<String>> mainCatListvalue = new ArrayList<>();

    private List<Integer> countlist2 = new ArrayList<>();

    private HashMap<String, Integer> countlistmain = new HashMap<>();


    static private List<List<String>> mainCatListlastselected2 = new ArrayList<>();
    static private List<List<String>> mainCatListlastselected = new ArrayList<>();
    static private List<Integer> countlistlastselected = new ArrayList<>();


    private List<List<String>> mainCatListimported = new ArrayList<>();
    //private List<Integer> countlistimported = new ArrayList<>();
    private HashMap<String, Integer> countlistimported = new HashMap<>();
    private List<UpgradeFilterListData> fullList = new ArrayList<>();


    private List<String> itemcode = new ArrayList<>();

    private HashMap<String, List<String>> selectedvalue = new HashMap();


    private OnFragmentInteractionListener mListener;


    private int listlastselectedposiion = 0;

    public FilterFragmentNew() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FilterFragmentNew newInstance(String param1, String param2) {
        FilterFragmentNew fragment = new FilterFragmentNew();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        LayoutInflater inflater = getActivity().getLayoutInflater();

        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                Locale = "ar";
                view = inflater.inflate(R.layout.fragment_filter_arabic, null);
            } else {
                Locale = "en";
                view = inflater.inflate(R.layout.fragment_filter, null);
            }
        } else {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_filter_arabic, null);
        }
        builder.setView(view);
        initViews();

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        return dialog;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {

            parentDialogListener = (ParentDialogsListener) activity;

        } catch (ClassCastException e) {

            throw new ClassCastException(activity.toString()
                    + " Activity's Parent should be Parent Activity");
        }


    }


    public void initViews() {


        value = getArguments().getInt("catId");

        size.append(getArguments().getString("previoussize"));

        item_type.append(getArguments().getString("previousitemtype"));

        manufecturer.append(getArguments().getString("previousmanufecturer"));

        Log.e("prenman-->>", manufecturer.toString());
        Log.e("previtemtype", item_type.toString());
        Log.e("previsize", size.toString());


        catagorylistRecycler = view.findViewById(R.id.catagory_list);
        catagorydetailRecycler = view.findViewById(R.id.catagory_list_detail);
        filterclick = view.findViewById(R.id.clear);
        apply = view.findViewById(R.id.apply);

        mainCatList2.clear();
        countlist2.clear();
        fullList.clear();

        Log.e("maincatlistimported", mainCatListimported.size() + " ");
        mainCatListimported.clear();
        mainCatListimported = (List<List<String>>) getArguments().getSerializable("selctecatagory");

        if (mainCatListimported.size() > 0)
            Log.e("maincatlistimported", mainCatListimported.get(0).size() + " ");


        countlistimported = (HashMap<String, Integer>) getArguments().getSerializable("countlist");


        selectedvalue = (HashMap<String, List<String>>) getArguments().getSerializable("prevselectedvalue");


        Log.e("maincatlist2", mainCatList2.size() + " ");
        for (int i = 0; i < mainCatListimported.size(); i++) {
            List<String> l2 = new ArrayList<>(mainCatListimported.get(i));
            mainCatList2.add(l2);
        }
        if (mainCatList2.size() > 0)
            Log.e("maincatlist2", mainCatList2.get(0).size() + " ");


        countlistmain = new HashMap<>(countlistimported);



            getUpgardedCatogary(manufecturer.toString(), item_type.toString(), size.toString());

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainCatList2.size() > 0 && fullList.size() > 0) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    applied = true;


                    mainCatListlastselected.clear();
                    mainCatListlastselected = new ArrayList<>();

                    Log.e("mainCatList fromApply", mainCatList2.get(0).size() + " ");

                    for (int i = 0; i < mainCatList2.size(); i++) {
                        List<String> l2 = new ArrayList<>(mainCatList2.get(i));
                        mainCatListlastselected.add(l2);
                    }


                    bundle.putString("manufecturer", manufecturer.toString());
                    bundle.putString("size", size.toString());
                    bundle.putString("itemtype", item_type.toString());
                    bundle.putSerializable("selctecatagory", (Serializable) new ArrayList<>(mainCatListlastselected));
                    bundle.putSerializable("maincatagory", (Serializable) new ArrayList<>(fullList));
                    bundle.putSerializable("countlist", (Serializable) new HashMap<String, Integer>(countlistmain));
                    bundle.putSerializable("selectedvalue", (Serializable) new HashMap<>(selectedvalue));

                    intent.putExtra("BUNDLE", bundle);


                    getTargetFragment().onActivityResult(101, 100, intent);


                    getDialog().cancel();
                } else {
                    if (Locale.equals("ar")) {
                        Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });


        filterclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                clearall();


            }
        });


    }


    public void getUpgardedCatogary(final String manufacturer, final String item_type, final String size) {
        /*https://upgrade.eoutlet.com/rest/en/V1/api/getfilterapi/4*/

        /*https://upgrade.eoutlet.com/rest/ar/V1/api/filternavigationlist?useapi=1&id=11&size=5738*/
        showProgressDialog();

        listname.clear();
        countlist2.clear();


        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("id", String.valueOf(value));
        Log.e("id", String.valueOf(value));

        if (!item_type.equals("null") && !item_type.equals(" ")) {
            map1.put("item_type", item_type);
        } else {
            map1.put("item_type", "");
        }
        if (!manufacturer.equals("null") && !manufacturer.equals(" ")) {
            map1.put("manufacturer", manufacturer);
        } else {
            map1.put("manufacturer", "");
        }
        if (!size.equals("null") && !size.equals(" ")) {
            map1.put("size", size);
        } else {
            map1.put("size", "");
        }
        map1.put("useapi", "1");
        mainparam.put("param", map1);
        Call<UpgradeFilterListResponse> call = Locale == "ar" ? apiService.upgradeFilterListArabic(map1) : apiService.upgradeFilterList(map1);

        call.enqueue(new Callback<UpgradeFilterListResponse>() {
            @Override
            public void onResponse(Call<UpgradeFilterListResponse> call, Response<UpgradeFilterListResponse> response) {


                if (response.body() != null && response.body().data.size() > 0/*add new*/) {
                    hideProgressDialog();

                    fullList.clear();
                    fullList = response.body().data;

                    for (int i = 0; i < fullList.size(); i++) {
                        itemcode.add(fullList.get(i).code);

                        List<String> selectsublist = new ArrayList<>();

                        if (!selectedvalue.containsKey(itemcode.get(i))) {

                            selectedvalue.put(itemcode.get(i), selectsublist);
                        }


                    }

                    for (int i = 0; i < response.body().data.size(); i++) {

                        listname.add(response.body().data.get(i).name);


                        List<String> sublist = new ArrayList<>();
                        mainCatList2.add(sublist);

                        List<String> sublistvalue = new ArrayList<>();

                        mainCatListvalue.add(sublistvalue);


                        if (!countlistmain.containsKey(itemcode.get(i))) {

                            countlistmain.put(itemcode.get(i), 0);
                        }


                    }
                    Log.e("fromAPI hitting", mainCatList2.get(0).size() + " ");
                    for (int i = 0; i < fullList.size(); i++) {

                        countlist2.add(countlistmain.get(fullList.get(i).code));


                    }
                } else {
                    hideProgressDialog();


                    if (Locale.equals("ar")) {
                        Toast.makeText(getContext(), "لا يوجد سجلات", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "No record found", Toast.LENGTH_LONG).show();
                    }


                }

                initCatagoryListRecycler();


                List<String> catagoryselctionlist = new ArrayList<>();
                if (ProductList.isfromcategoryfilterply) {
                for (int j = 0; j < fullList.size(); j++) {
                    for (int i = 0; i < fullList.get(j).data.size(); i++) {


                            if (j == 1 && fullList.get(j).data.get(i).value.equals(manufacturer)) {
                                 //mainCatList2.get(j).clear();
                                mainCatList2.get(j).add("true");
                            } else if (j == 0 && fullList.get(j).data.get(i).value.equals(item_type)) {
                                //mainCatList2.get(j).clear();
                                mainCatList2.get(j).add("true");

                            }
                            else if(j == 2 && fullList.get(j).data.get(i).value.equals(size)) {
                                //mainCatList2.get(j).clear();
                                mainCatList2.get(j).add("true");

                            }

                            else {
                                mainCatList2.get(j).add("false");
                            }

                            mainCatListvalue.get(j).add(fullList.get(j).data.get(i).value);

                        }
                        /*else{
                            mainCatList2.get(j).add("false");
                            mainCatListvalue.get(j).add(fullList.get(j).data.get(i).value);
                        }*/
                    }
                    ProductList.isfromcategoryfilterply =false;
                }
                else{
                    for (int j = 0; j < fullList.size(); j++) {
                        for (int i = 0; i < fullList.get(j).data.size(); i++) {

                            mainCatList2.get(j).add("false");
                            mainCatListvalue.get(j).add(fullList.get(j).data.get(i).value);
                        }


                        }
                }


                Log.e("fromAPI hitting2", mainCatList2.get(0).size() + " ");


                if (fullList.size() > 0) {
                    for (int i = 0; i < fullList.get(0).data.size(); i++) {

                        catagoryselctionlist.add(fullList.get(0).data.get(i).display);

                    }

                    initCatagoryDetailAdapter(catagoryselctionlist, mainCatList2.get(0), 0);
                }


            }


            @Override
            public void onFailure(Call<UpgradeFilterListResponse> call, Throwable t) {
                hideProgressDialog();

                Log.e(TAG, t.toString());

                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    @SuppressLint("WrongConstant")
    public void initCatagoryListRecycler() {
        Log.e("listname", String.valueOf(listname.size()));


        catagorylistRecycler.setHasFixedSize(true);

        // use a linear layout manager

        catagorylistRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // specify an adapter (see also next example)
        mAdapter = new FilterCatagoryListAdapter(view.getContext(), listname, countlist2, listlastselectedposiion, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();


                switch (id) {

                    case R.id.maincatagoryclick://button for message


                        List<String> catagoryselctionlist = new ArrayList<>();
                        listlastselectedposiion = position;
                        Log.e("onclickselected----->>>", listlastselectedposiion + "");


                        for (int i = 0; i < fullList.get(position).data.size(); i++) {

                            catagoryselctionlist.add(fullList.get(position).data.get(i).display);

                        }

                        initCatagoryDetailAdapter(catagoryselctionlist, mainCatList2.get(position), position);
                        Log.e("Size is", catagoryselctionlist.size() + " ");
                        Log.e("Size is", mainCatList2.get(position).size() + " ");

                        break;
                }
            }
        });
        catagorylistRecycler.setAdapter(mAdapter);


    }


    @SuppressLint("WrongConstant")
    public void initCatagoryDetailAdapter(List<String> name, final List<String> selectionflag, final int mainlistposition) {

        catagorydetailRecycler.setHasFixedSize(true);


        catagorydetailRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // specify an adapter (see also next example)
        mAdapter2 = new FilterDetailAdapter(view.getContext(), name, selectionflag, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();


                switch (id) {

                    case R.id.filtercatagoryclick://button for message
                        mainCatListlastselected2.clear();


                        for (int i = 0; i < mainCatList2.size(); i++) {

                            List<String> l1 = new ArrayList<>(mainCatList2.get(i));
                            mainCatListlastselected2.add(l1);
                        }


                        countlistlastselected = new ArrayList<>(countlist2);


                        if (mainCatList2.get(listlastselectedposiion).get(position).equals("false")) {

                            mainCatList2.get(listlastselectedposiion).set(position, "true");

                            selectedvalue.put(itemcode.get(listlastselectedposiion), selectedvalue.get(itemcode.get(listlastselectedposiion))).add(mainCatListvalue.get(listlastselectedposiion).get(position));

                        }
                        else {

                            mainCatList2.get(listlastselectedposiion).set(position, "false");


                            selectedvalue.put(itemcode.get(listlastselectedposiion), selectedvalue.get(itemcode.get(listlastselectedposiion))).remove(mainCatListvalue.get(listlastselectedposiion).get(position));


                        }


                        if (!itemcode.contains("item_type")) {
                            item_type = new StringBuilder();


                        }
                        if (!itemcode.contains("manufacturer")) {
                            manufecturer = new StringBuilder();

                        }

                        if (!itemcode.contains("size")) {
                            size = new StringBuilder();

                        }

                        /*for(int i=0;i<mainCatList2.get(listlastselectedposiion).size();i++){
                            Log.e("mainCatList2 Size is", " "+mainCatList2.get(listlastselectedposiion).get(i));


                        }

*/
                        int mainvalue = 0;
                        for (int i = 0; i < mainCatList2.get(listlastselectedposiion).size(); i++) {


                            if (itemcode.get(listlastselectedposiion).equals("item_type")) {
                                //   Log.e("from itemtype",listname.get(listlastselectedposiion));

                                if (i == 0) {
                                    item_type = new StringBuilder();
                                    if (mainCatList2.get(listlastselectedposiion).get(i).equals("true")) {
                                        item_type = item_type.append(fullList.get(listlastselectedposiion).data.get(i).value);
                                        mainvalue++;

                                    }
                                } else {
                                    Log.e("mainCatList2 Size is", mainCatList2.get(listlastselectedposiion).size() + " ");
                                    Log.e("fullList Size is", fullList.get(listlastselectedposiion).data.size() + " ");


                                    if (mainCatList2.get(listlastselectedposiion).get(i).equals("true")) {

                                        item_type = item_type.append("," + fullList.get(listlastselectedposiion).data.get(i).value);

                                        mainvalue++;
                                    }
                                }


                            } else if (itemcode.get(listlastselectedposiion).equals("manufacturer")) {
                                Log.e("from Brand", listname.get(listlastselectedposiion));
                                if (i == 0) {
                                    manufecturer = new StringBuilder();
                                    if (mainCatList2.get(listlastselectedposiion).get(i).equals("true")) {
                                        mainvalue++;
                                        manufecturer = manufecturer.append(fullList.get(listlastselectedposiion).data.get(i).value);
                                    }
                                } else {
                                    if (mainCatList2.get(listlastselectedposiion).get(i).equals("true")) {
                                        manufecturer = manufecturer.append("," + fullList.get(listlastselectedposiion).data.get(i).value);
                                        mainvalue++;
                                    }
                                }
                            } else {

                                Log.e("from Size", listname.get(listlastselectedposiion));
                                if (i == 0) {
                                    size = new StringBuilder();
                                    if (mainCatList2.get(listlastselectedposiion).get(i).equals("true")) {
                                        mainvalue++;

                                        size = size.append(fullList.get(listlastselectedposiion).data.get(i).value);

                                    }
                                } else {
                                    if (mainCatList2.get(listlastselectedposiion).get(i).equals("true")) {
                                        mainvalue++;

                                        size = size.append("," + fullList.get(listlastselectedposiion).data.get(i).value);

                                    }

                                }
                            }


                        }
                        Log.e("manu", manufecturer.toString());
                        Log.e("itemtype", item_type.toString());
                        Log.e("size", size.toString());


                        countlistmain.put(fullList.get(listlastselectedposiion).code, mainvalue);


                        selectcatogoryafterfilterapply(manufecturer.toString(), item_type.toString(), size.toString());


                        break;
                }
            }
        });
        catagorydetailRecycler.setAdapter(mAdapter2);


    }


    public void selectcatogoryafterfilterapply(final String manufacturer, final String item_type, final String size) {
       Log.e("fromapply"+" ","fromapply");

        showProgressDialog();

        listname.clear();

        mainCatListvalue.clear();

        countlist2.clear();


        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("id", String.valueOf(value));
        map1.put("useapi", "1");
        Log.e("id", String.valueOf(value));

        if (!item_type.equals("null") && !item_type.equals(" ")) {
            map1.put("item_type", item_type);
        } else {
            map1.put("item_type", "");
        }
        if (!manufacturer.equals("null") && !manufacturer.equals(" ")) {
            map1.put("manufacturer", manufacturer);
        } else {
            map1.put("manufacturer", "");
        }
        if (!size.equals("null") && !size.equals(" ")) {
            map1.put("size", size);
        } else {
            map1.put("size", "");
        }

        mainparam.put("param", map1);
        Call<UpgradeFilterListResponse> call = Locale == "ar" ? apiService.upgradeFilterListArabic(map1) : apiService.upgradeFilterList(map1);
        call.enqueue(new Callback<UpgradeFilterListResponse>() {
            @Override
            public void onResponse(Call<UpgradeFilterListResponse> call, Response<UpgradeFilterListResponse> response) {


                if (response.body() != null) {
                    hideProgressDialog();
                    fullList = response.body().data;


                    for (int k = 0; k < fullList.size(); k++) {
                        if (itemcode.size() > 0) {
                            if (itemcode.get(listlastselectedposiion).equals(fullList.get(k).code)) {
                                listlastselectedposiion = k;
                            }
                        }

                    }


                    Log.e("lastselected------>>>>", listlastselectedposiion + "");


                    itemcode.clear();


                    for (int i = 0; i < fullList.size(); i++) {

                        itemcode.add(fullList.get(i).code);


                    }


                    for (int k = 0; k < countlistmain.size(); k++) {


                        if (!itemcode.contains(countlistmain.keySet().toArray()[k])) {

                            countlistmain.put(countlistmain.keySet().toArray()[k].toString(), 0);

                            for (int j = 0; j < selectedvalue.get(countlistmain.keySet().toArray()[k].toString()).size(); j++) {
                                selectedvalue.get(countlistmain.keySet().toArray()[k].toString()).clear();


                            }

                        }


                    }


                    for (int i = 0; i < fullList.size(); i++) {

                        countlist2.add(countlistmain.get(fullList.get(i).code));

                    }


                    for (int i = 0; i < response.body().data.size(); i++) {
                        Log.e("list name after", response.body().data.get(i).name);
                        listname.add(response.body().data.get(i).name);


                        List<String> sublist = new ArrayList<>();
                        mainCatList2.add(sublist);

                        List<String> sublistvalue = new ArrayList<>();
                        mainCatListvalue.add(sublistvalue);

                        // countlist2.add(0);


                    }


                } else {


                }


                initCatagoryListRecycler();


                List<String> catagoryselctionlist = new ArrayList<>();


                for (int j = 0; j < fullList.size(); j++) {
                    mainCatList2.get(j).clear();

                    for (int i = 0; i < fullList.get(j).data.size(); i++) {

                        if (selectedvalue.get(itemcode.get(j)).contains(fullList.get(j).data.get(i).value)) {

                            mainCatList2.get(j).add("true");
                        } else {
                            mainCatList2.get(j).add("false");
                        }

                        mainCatListvalue.get(j).add(fullList.get(j).data.get(i).value);

                    }
                }

                for (int i = 0; i < fullList.get(listlastselectedposiion).data.size(); i++) {

                    catagoryselctionlist.add(fullList.get(listlastselectedposiion).data.get(i).display);
                }


                Log.e("assfdsfsd", catagoryselctionlist.size() + " " + mainCatList2.get(listlastselectedposiion).size());

                initCatagoryDetailAdapter(catagoryselctionlist, mainCatList2.get(listlastselectedposiion), listlastselectedposiion);


            }


            @Override
            public void onFailure(Call<UpgradeFilterListResponse> call, Throwable t) {
                hideProgressDialog();
                Log.e(TAG, t.toString());

                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    public void clearall() {


        for (int i = 0; i < itemcode.size(); i++) {


            countlistmain.put(itemcode.get(i), 0);


        }


        for (int i = 0; i < mainCatList2.size(); i++) {


            for (int j = 0; j < mainCatList2.get(i).size(); j++) {

                mainCatList2.get(i).set(j, "false");

            }


        }
        if (mAdapter != null && mAdapter2 != null) {

            listlastselectedposiion = 0;
            getUpgardedCatogary(" ", " ", " ");
        }

        selectedvalue.clear();

        manufecturer = new StringBuilder();
        item_type = new StringBuilder();
        size = new StringBuilder();
        //item_type =new StringBuilder();

    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        // dialogDismissed is a Class level variable in the containing Activity,
        // must be set to false each time the DialogFragment is shown
        super.onDismiss(dialog);
    }


    public void showProgressDialog() {
        progressDialog1 = ProgressDialog.show(getActivity(), "Null", "Null", false, false);
        progressDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog1.setContentView(R.layout.progress_bar);
        progressDialog1.setCanceledOnTouchOutside(false);
    }


    public void hideProgressDialog() {

        try {


            //progressDialog.hide();
            progressDialog1.dismiss();
        } catch (Exception e) {

        }

    }
}
