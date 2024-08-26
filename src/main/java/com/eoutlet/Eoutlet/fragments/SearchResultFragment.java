package com.eoutlet.Eoutlet.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.adpters.ProductListAdapter;
import com.eoutlet.Eoutlet.adpters.UpgradeProductListAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.CatagoryList;
import com.eoutlet.Eoutlet.api.request.ListItems;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.listener.PaginationListener;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.AddtoWishList;
import com.eoutlet.Eoutlet.pojo.ElasticSearchData;
import com.eoutlet.Eoutlet.pojo.ElasticSearchResponse;
import com.eoutlet.Eoutlet.pojo.UpgradedSearchItem;
import com.google.common.reflect.TypeToken;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.moe.pushlibrary.MoEHelper;
import com.moengage.core.Properties;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;


public class SearchResultFragment extends Fragment {
    private RecyclerView productListRecycler;
    private ProductListAdapter mAdapter;
    private UpgradeProductListAdapter madapter2;
    List<ListItems> catagoryList = new ArrayList<>();
    List<UpgradedSearchItem> upgradecatagoryList = new ArrayList<>();
    List<ElasticSearchData> elasticSearchData = new ArrayList<>();
    private SearchView serachview;
    ArrayList<String> temp = new ArrayList<>();
    AutoCompleteTextView autoCountry;
    GridLayoutManager lm;
    private FirebaseAnalytics mFirebaseAnalytics;
    private PaginationListener scrollListener;
    private OnFragmentInteractionListener mListener;
    private Toolbar toolbar;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    View view;
    String selectquery = " ";
    String selectqueryurl;
    int currentpage = 1;
    String Locale;
    private TextView notextfound;
    public ParentDialogsListener parentDialogListener;

    public SearchResultFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SearchResultFragment newInstance(String param1, String param2) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                Locale = "ar";
                view = inflater.inflate(R.layout.fragment_search_result_arabic, container, false);
            } else {
                Locale = "en";
                view = inflater.inflate(R.layout.fragment_search_result, container, false);
            }
        } else {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_search_result_arabic, container, false);
        }
        productListRecycler = view.findViewById(R.id.product_list_search_Recycler);
        serachview = view.findViewById(R.id.searchViewMain);
        notextfound = view.findViewById(R.id.notextfound);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        toolbar = getActivity().findViewById(R.id.common_toolbar);
        toolbar.setVisibility(View.GONE);
        autoCountry = (AutoCompleteTextView) view.findViewById(R.id.autosearch);
        //toolbar.setVisibility(View.GONE);
        serachview.onActionViewExpanded();

        list = new ArrayList<>();
        //list.add(" ");


        if (getArrayList("SearchHistory") == null) {
            saveArrayList(list, "SearchHistory");
        }

        list = getArrayList("SearchHistory");

        lm = new GridLayoutManager(view.getContext(), 2) {
            @Override
            protected boolean isLayoutRTL() {
                if (Locale == "en") {
                    return false;
                } else {
                    return true;
                }
            }
        };
        scrollListener = new PaginationListener(
                lm) {
            @Override
            public void onLoadMore(int current_page) {


                //model.getProductListData(value, getContext(),currentpage);


                currentpage++;
                // getSearchResult(String.valueOf(selectquery), currentpage);
                getUpgradedSearchResult(String.valueOf(selectquery), currentpage);

            }

        };

        productListRecycler.addOnScrollListener(scrollListener);


        listView = (ListView) view.findViewById(R.id.lv1);

        temp.clear();


        for (int i = list.size() - 1; i >= 0; i--) {

            temp.add(list.get(i));

        }

        //adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
        adapter = new ArrayAdapter<String>(getContext(), R.layout.search_textview, R.id.searchtext, temp);
        //adapter = new SearchResultAdapter(getContext(),list);
        listView.setAdapter(adapter);

        serachview.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    showInputMethod(v.findFocus());
                    listView.setVisibility(View.VISIBLE);
            }
        });


        serachview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                              @Override
                                              public boolean onQueryTextSubmit(String query) {


                                                  //scrollListener.resetState();


                                                  if (!getArrayList("SearchHistory").contains(query) && getArrayList("SearchHistory").size() < 10) {
                                                      //adapter.clear();


                                                      list.add(query);

                                                      temp.clear();


                                                      for (int i = list.size() - 1; i >= 0; i--) {

                                                          temp.add(list.get(i));

                                                      }


                                                      adapter = new ArrayAdapter<String>(getContext(), R.layout.search_textview, R.id.searchtext, temp);


                                                      listView.setAdapter(adapter);


                                                      listView.setVisibility(View.GONE);


                                                      saveArrayList(list, "SearchHistory");

                                                  } else if (!getArrayList("SearchHistory").contains(query)) {


                                                      list.remove(0);
                                                      list.add(query);

                                                      adapter = new ArrayAdapter<String>(getContext(), R.layout.search_textview, R.id.searchtext, list);


                                                      listView.setAdapter(adapter);


                                                      listView.setVisibility(View.GONE);


                                                      saveArrayList(list, "SearchHistory");


                                                  }


                                                  selectquery = query;
                                                  currentpage = 1;

                                                  if (query.length() > 0) {
                                                      catagoryList = new ArrayList<>();
                                                      //getSearchResult(query, currentpage);

                                                      getUpgradedSearchResult(query, currentpage);


                                                  } else {
                                                      Toast.makeText(getContext(), "لا يوجد تطابق", Toast.LENGTH_LONG).show();
                                                  }
                                                  return false;
                                              }

                                              @Override
                                              public boolean onQueryTextChange(String newText) {


                                                  if (newText.length() > 0) {

                                                      adapter.getFilter().filter(newText);
                                                      listView.setVisibility(View.VISIBLE);
                                                  } else {


                                                      temp.clear();


                                                      for (int i = list.size() - 1; i >= 0; i--) {

                                                          temp.add(list.get(i));

                                                      }
                                                      adapter = new ArrayAdapter<String>(getContext(), R.layout.search_textview, R.id.searchtext, temp);


                                                      listView.setAdapter(adapter);

                                                  }
                                                  return false;
                                              }
                                          }


        );


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {


                //Toast.makeText(getContext(),list.get(position)+position+adapter.getItem(position),Toast.LENGTH_SHORT).show();

                serachview.setQuery(adapter.getItem(position), true);


                scrollListener.resetValues();

                //productListRecycler.addOnScrollListener(scrollListener);


            }
        });


        return view;

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
        try {

            parentDialogListener = (ParentDialogsListener) context;

        } catch (ClassCastException e) {

            throw new ClassCastException(context.toString()
                    + " Activity's Parent should be Parent Activity");
        }


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

    public void getUpgradedSearchResult(final String querytext, final int currentpage) {


        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        map1.put("query", querytext);
        map1.put("page", currentpage);
        map1.put("page_size", 10);
        mainparam.put("param", map1);
        Call<ElasticSearchResponse> call = Locale == "ar" ? apiService.upgradeElasticSearchArabic(mainparam) : apiService.upgradeElasticSearch(mainparam);
        call.enqueue(new Callback<ElasticSearchResponse>() {
            @Override
            public void onResponse(Call<ElasticSearchResponse> call, Response<ElasticSearchResponse> response) {
                parentDialogListener.hideProgressDialog();
                selectqueryurl = response.body().query;
                if (response.body() != null) {
                    if (response.body().data != null && response.isSuccessful()) {
                  if(currentpage==1){
                      elasticSearchData.clear();
                  }
                        if (response.body().data.size() > 0) {
                            notextfound.setVisibility(View.GONE);
                            for (int i = 0; i < response.body().data.size(); i++) {
//                                upgradecatagoryList.add(response.body().data.get(i));
                                elasticSearchData.add(response.body().data.get(i));
                            }
                            if (currentpage == 1) {
                                scrollListener.resetState();

                                initupgradedRecycler(view, elasticSearchData);



                            } else {
                                madapter2.notifyDataSetChanged();
                            }

                        } else {
                            parentDialogListener.hideProgressDialog();

                              if(currentpage==1) {
                                  notextfound.setVisibility(View.VISIBLE);
                                  if (Locale.equals("ar")) {
                                      Toast.makeText(getContext(), "لا يوجد سجلات.", Toast.LENGTH_LONG).show();
                                  } else {
                                      Toast.makeText(getContext(), "No Product Found", Toast.LENGTH_LONG).show();
                                  }

                              }
                            if (currentpage == 1) {

                                upgradecatagoryList.clear();

                                initupgradedRecycler(view, elasticSearchData);
                            }
                        }

                    } else {
                        if(currentpage==1) {
                            if (Locale.equals("ar")) {
                                Toast.makeText(getContext(), "لا يوجد سجلات.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "No Product Found", Toast.LENGTH_LONG).show();
                            }
                        }

                        parentDialogListener.hideProgressDialog();
                        if (currentpage == 1) {

                            upgradecatagoryList.clear();

                            initupgradedRecycler(view, elasticSearchData);

                        }
                    }
                }
                firebase_view_search_result(selectqueryurl);
            }

            @Override
            public void onFailure(Call<ElasticSearchResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();

                parentDialogListener.hideProgressDialog();
                notextfound.setVisibility(View.VISIBLE);

                if(Locale.equals("ar")) {
                    Toast.makeText(getContext(), "لا يوجد سجلات.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), "No Product Found", Toast.LENGTH_LONG).show();
                }
                Log.e(TAG, t.toString());
            }
        });


    }



    public void initupgradedRecycler(View v, final List<ElasticSearchData> elasticSearchData) {
        productListRecycler.setHasFixedSize(true);
        // use a linear layout manager
        productListRecycler.setLayoutManager(/*new GridLayoutManager(v.getContext(), 2)*/lm);
        // specify an adapter (see also next example)
        madapter2 = new UpgradeProductListAdapter(getContext(), elasticSearchData, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();
                switch (id) {
                    case R.id.product_list_image://button for message

                        firebase_eventclclickUrl(elasticSearchData.get(position).product_url);

                        Fragment prFrag = new ProductDetail();
                        Bundle databund = new Bundle();
                        databund.putString("previouspage","FromSearchScreen");
                        databund.putString("sku", elasticSearchData.get(position).sku);
                        databund.putString("pid", String.valueOf(elasticSearchData.get(position).id));
                        databund.putString("itemurl",String.valueOf(elasticSearchData.get(position).product_url));

                        ListItems listItems = new ListItems();
                        listItems.name = elasticSearchData.get(position).name;
                        listItems.id = String.valueOf(elasticSearchData.get(position).id);
                        listItems.price = String.valueOf(elasticSearchData.get(position).price);
                        listItems.oldPrice = String.valueOf(elasticSearchData.get(position).old_price);
                        listItems.sku = String.valueOf(elasticSearchData.get(position).sku);
                        databund.putSerializable("catagoryobject", listItems);
                        prFrag.setArguments(databund);

                        getFragmentManager()
                                .beginTransaction().addToBackStack(null)
                                .add(SearchResultFragment.this.getId(), prFrag)
                                .commit();
                        break;
                    case R.id.productListAddToWishlist:

                        if (!MySharedPreferenceClass.getMyUserId(getContext()).equals(" ") ) {
                            addToWishList(elasticSearchData.get(position).id);

                        }
                        else {
                           //HomeFragmentNewDesign.getInstance().openSignInDialog(SearchResultFragment.this.getId());
                        }

                        //addToWishList(catagoryList.get(position).id);

                        break;
                }
            }
        });
        productListRecycler.setAdapter(madapter2);
        moengageSearch();
        firebase_view_search_result(selectqueryurl);

    }


    public void moengageSearch() {

        Properties properties = new Properties();

        properties.addAttribute("search_item", selectquery);

        MoEHelper.getInstance(getApplicationContext()).trackEvent("Search", properties);


    }
    public void appsflyer_event_search() {
        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put(AFInAppEventParameterName.CUSTOMER_USER_ID, MySharedPreferenceClass.getMyUserId(getContext()));
        AppsFlyerLib.getInstance().trackEvent(getActivity(), AFInAppEventType.SEARCH, eventValue);


    }
    public void firebase_eventclclickUrl(String serachurl) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, serachurl);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);


    }

    public void firebase_view_search_result(String serachvalue) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, serachvalue);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);
    }

    public void saveArrayList(ArrayList<String> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();

    }

    public ArrayList<String> getArrayList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toolbar.setVisibility(View.VISIBLE);
    }
    //    add To wishlist function
    public void addToWishList(String id) {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        map1.put("product_id", id);
        mainparam.put("param", map1);

        Call<AddtoWishList> call = apiService.addtowishlist(mainparam);
        call.enqueue(new Callback<AddtoWishList>() {
            @Override
            public void onResponse(Call<AddtoWishList> call, Response<AddtoWishList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        parentDialogListener.hideProgressDialog();
                        if (response.body().response.equals("success")) {
                            Toast.makeText(getContext(), "Added to Wishlist", Toast.LENGTH_LONG).show();
                            // Update Wishlist Badge Count
                            HomeFragmentNewDesign.getInstance().getWishListItemCount();
                        }
                    } else {
                        parentDialogListener.hideProgressDialog();
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (response.code() == 401) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AddtoWishList> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                if(Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void removeFromWishList(String id) {
        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        map1.put("product_id", id);
        mainparam.put("param", map1);

        Call<AddtoWishList> call = apiService.removeFromwishlist(mainparam);
        call.enqueue(new Callback<AddtoWishList>() {
            @Override
            public void onResponse(Call<AddtoWishList> call, Response<AddtoWishList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        parentDialogListener.hideProgressDialog();
                        if (response.body().response.equals("success")) {
                            Toast.makeText(getContext(), "Added to Wishlist", Toast.LENGTH_LONG).show();
                            // Update Wishlist Badge Count
                            HomeFragmentNewDesign.getInstance().getWishListItemCount();
                        }
                    } else {
                        parentDialogListener.hideProgressDialog();
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (response.code() == 401) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String userMessage = jsonObject.getString("message");
                                Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AddtoWishList> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                if(Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }



}
