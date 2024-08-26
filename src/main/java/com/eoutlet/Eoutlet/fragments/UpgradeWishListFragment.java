package com.eoutlet.Eoutlet.fragments;

import android.content.Context;
import android.media.MediaDrm;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.adpters.WishListAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.ListItems;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.intrface.UpdateBedgeCount;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.AddtoCartResponse;
import com.eoutlet.Eoutlet.pojo.RemoveFromWishListResponse;
import com.eoutlet.Eoutlet.pojo.UpgradeWishListItems;
import com.eoutlet.Eoutlet.pojo.UpgradeWishListResponse;
import com.eoutlet.Eoutlet.pojo.UpgradedCartItems;
import com.eoutlet.Eoutlet.utility.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

public class UpgradeWishListFragment extends Fragment {
    private ImageView searchImage, backarrow;
    private Toolbar toolbar1;
    private View view;
    LinearLayout noWishListItem;
    private RecyclerView wishListRecyclerView;
    private List<UpgradeWishListItems> upgradeWishListItems = new ArrayList<>();
    private WishListAdapter wishListAdapter;
    public ParentDialogsListener parentDialogListener;
    int totalcount;
    private UpdateBedgeCount updatefix;
    String Locale;
    private static UpgradeWishListFragment instance = null;
    Boolean isClicked = false;

    public static UpgradeWishListFragment getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        instance = this;
        // Inflate the layout for this fragment
        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                Locale = "ar";
                view = inflater.inflate(R.layout.fragment_upgrade_wish_list_arabic, container, false);
            } else {
                Locale = "en";
                view = inflater.inflate(R.layout.fragment_upgrade_wish_list, container, false);
            }
        } else {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_upgrade_wish_list_arabic, container, false);
        }

        toolbar1 = view.findViewById(R.id.toolbar);
        wishListRecyclerView = view.findViewById(R.id.wishListRecyclerView);
        noWishListItem = view.findViewById(R.id.noWishListItem);
        searchImage = toolbar1.findViewById(R.id.serachbar);
        backarrow = toolbar1.findViewById((R.id.backarrow));

        updatefix = (MainActivity) getActivity();

        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment prFrag = new SearchResultFragment();
                Bundle databund = new Bundle();
                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(UpgradeWishListFragment.this.getId(), prFrag)
                        .commit();
            }
        });
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        getWishList();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            parentDialogListener = (ParentDialogsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " Activity's Parent should be Parent Activity");
        }
    }
    @Nullable
    String getUniqueID2() {
        UUID wideVineUuid = new UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L);
        try {
            MediaDrm wvDrm = new MediaDrm(wideVineUuid);
            byte[] wideVineId = wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID);


            String encodedString = Base64.encodeToString(wideVineId, Base64.DEFAULT);
            Log.i("Uniqueid", "Uniqueid" + encodedString);
            return encodedString;
        } catch (Exception e) {
            // Inspect exception
            return null;
        }

    }
    //    Api to get wishlist
    private void getWishList() {
//       if(!parentDialogListener.isProgressBarRunning())
        parentDialogListener.showProgressDialog();

        String mykey = getUniqueID2().replaceAll("[^A-Za-z0-9]","");
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Call<UpgradeWishListResponse> call;
        if(!MySharedPreferenceClass.getMyUserId(getContext()).equals(" ")){
         call = Locale == "ar" ? apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "mywishlist/" + MySharedPreferenceClass.getMyUserId(getContext())) : apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "mywishlist/" + MySharedPreferenceClass.getMyUserId(getContext()));
      }
        else {
            call = Locale == "ar" ? apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ARABIC + "getguestwishlist/" + "1" + mykey.toUpperCase() + "2/") : apiService.getUpgradeWishList(Constants.upgradedmediaSourceURL + Constants.UPGRADED_BASE_LINK_ENGLISH + "getguestwishlist/" + "1" + mykey.toUpperCase() + "2/");
        }


        call.enqueue(new Callback<UpgradeWishListResponse>() {
            @Override
            public void onResponse(Call<UpgradeWishListResponse> call, Response<UpgradeWishListResponse> response) {
                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();
                    if (response.body().getResponse().equals("success")) {

                        Log.e("get wishList", "success");
                        parentDialogListener.hideProgressDialog();
                        upgradeWishListItems = response.body().getData();
                        if (upgradeWishListItems != null && upgradeWishListItems.size() > 0) {

                            HomeFragmentNewDesign.wishListBadgeCount.setVisibility(View.VISIBLE);
                            HomeFragmentNewDesign.wishListBadgeCount.setText(String.valueOf(upgradeWishListItems.size()));

                            NewCategoryDesignFragments.categorywishListBadgeCount.setVisibility(View.VISIBLE);
                            NewCategoryDesignFragments.categorywishListBadgeCount.setText(String.valueOf(response.body().getData().size()));
                            if (ProductList.productListBadgeCount != null) {
                                ProductList.productListBadgeCount.setVisibility(View.VISIBLE);
                                ProductList.productListBadgeCount.setText(String.valueOf(response.body().getData().size()));
                            }
                            wishListRecyclerView.setVisibility(View.VISIBLE);
                            noWishListItem.setVisibility(View.GONE);
                            wishListAdapter = new WishListAdapter(getContext(), upgradeWishListItems, UpgradeWishListFragment.this);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false) {
                                @Override
                                protected boolean isLayoutRTL() {
                                    if (Locale == "ar") {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                }
                            };
                            wishListRecyclerView.setLayoutManager(gridLayoutManager);
                            wishListRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            wishListRecyclerView.setAdapter(wishListAdapter);
                            wishListAdapter.notifyDataSetChanged();
                        } else {
                            NewCategoryDesignFragments.categorywishListBadgeCount.setVisibility(View.GONE);
                           if( ProductList.productListBadgeCount!=null) {
                               ProductList.productListBadgeCount.setVisibility(View.GONE);
                           }
                            HomeFragmentNewDesign.wishListBadgeCount.setVisibility(View.GONE);
                            wishListRecyclerView.setVisibility(View.GONE);
                            noWishListItem.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Log.e("get wishList", "failed");
                        Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : " An error occurred - please try again", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpgradeWishListResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e("fail", t.getMessage());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : " An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
    }


    //    function called from adapter on click of add to cart button in wishlist
    public void addToCart(String item_id, String product_id, String type, String sku, String name, Integer old_price, Integer price) {

        if (isClicked == false) {
           parentDialogListener.showProgressDialog();
          //  isClicked = true;
            Map<Object, Object> map1 = new HashMap<>();
            map1.put("username", MySharedPreferenceClass.getAdminUsername(getApplicationContext()));
            map1.put("password", MySharedPreferenceClass.getAdminPassword(getApplicationContext()));

            BasicRequest apiService =
                    UpgradedBasicBuilder.getClient().create(BasicRequest.class);

            Call<String> call;
            if (Locale.equals("en")) {
                call = apiService.createtokenbyId(map1);
            } else {
                call = apiService.createtokenbyIdArabic(map1);
            }
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.body() != null) {
                   parentDialogListener.hideProgressDialog();
                        if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {
                            getQuoteID(response.body().toString(), type, sku, name, old_price, price, product_id, item_id);
                        } else {

                            upgradeaddtocartforguestuser(response.body().toString(), type, sku, name, old_price, price, product_id, item_id);

                        }
                    }
                    else {
                      //  isClicked = false;
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.errorBody().string());
                            String userMessage = jsonObject.getString("message");
                            Toast.makeText(getContext(), userMessage, Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        parentDialogListener.hideProgressDialog();

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    isClicked = false;
                    parentDialogListener.hideProgressDialog();
                    Log.e(TAG, t.toString());
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : " An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    //    function to get Quote Id
    public void getQuoteID(String authtoken, String type, String sku, String name, Integer old_price, Integer price, String product_id, String item_id) {
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
        Map<Object, Object> map1 = new HashMap<>();
        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        Call<String> call = apiService.getQuotebyId("Bearer" + " " + authtoken, map1);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    MySharedPreferenceClass.setQuoteId(getContext(), response.body().toString());
                    upgradeaddtocart(response.body().toString(), authtoken, type, sku, name, old_price, price, product_id, item_id);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                isClicked = false;
                Log.e(TAG, t.toString());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : " An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    //    Upgrade function to add item to cart
    public void upgradeaddtocart(String quoteId, String authtoken, String type, String sku, String name, Integer old_price, Integer price, String product_id, String item_id) {
        if (type.equals("simple")) {
            parentDialogListener.showProgressDialog();
            BasicRequest apiService =
                    UpgradedBasicBuilder.getClient().create(BasicRequest.class);

            Map<Object, Object> mainparam = new HashMap<>();
            Map<String, Object> map1 = new HashMap<>();
            map1.put("quote_id", Integer.parseInt(quoteId));
            map1.put("sku", sku);
            map1.put("qty", 1);
            mainparam.put("cart_item", map1);


            HashMap<String,String>  headers = new HashMap();
            headers.put("Authorization","Bearer" + " "+ authtoken);
            headers.put("Cookie",MySharedPreferenceClass.getCookies(getContext()));
            Call<AddtoCartResponse> call = apiService.upgradedaddtocart(headers, mainparam);
            call.enqueue(new Callback<AddtoCartResponse>() {
                @Override
                public void onResponse(Call<AddtoCartResponse> call, Response<AddtoCartResponse> response) {
                    parentDialogListener.hideProgressDialog();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
                                Toast.makeText(getContext(), "Item added in your cart", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "تم إضافة المنتج لحقيبة تسوقك", Toast.LENGTH_SHORT).show();
                            }


                            getUpgradeCartData(item_id);

                            if (ProductDetail.getInstance() != null) {
                                ProductDetail.getInstance().moengageAddtoCartEvent(price.toString(), sku, "1");
                                ProductDetail.getInstance().appsflyer_event_add_to_cart(price.toString(), sku, "1");
                                ProductDetail.getInstance().firebase_event_add_to_cart(price.toString(), sku, "1");
                            }
                        }
                    } else {
                        isClicked = false;
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
                public void onFailure(Call<AddtoCartResponse> call, Throwable t) {
                    isClicked = false;
                    parentDialogListener.hideProgressDialog();
                    Log.e(TAG, t.toString());
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : " An error occurred - please try again", Toast.LENGTH_LONG).show();

                }
            });
        } else {
            parentDialogListener.showProgressDialog();
            ListItems listItems = new ListItems();
            listItems.name = name;
            listItems.id = product_id;
            listItems.price = price.toString();
            listItems.oldPrice = old_price.toString();
            listItems.sku = sku;
            listItems.type = type;
            Fragment productDetail = new ProductDetail();
            FragmentTransaction mFragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
            mFragmentTransaction.replace(UpgradeWishListFragment.this.getId(), productDetail).addToBackStack(null).commit();
            Bundle databund = new Bundle();
            //put your ArrayList data in bundle
            databund.putString("fromwhere", "fromWishList");
            databund.putString("sku", sku);
            databund.putString("previouspage","FromWishListScreen");
            databund.putSerializable("catagoryobject", listItems);
            databund.putString("pid", product_id);
            databund.putString("item_id", item_id);
            productDetail.setArguments(databund);
            parentDialogListener.hideProgressDialog();
        }
    }


    public void upgradeaddtocartforguestuser(String authtoken, String type, String
            sku, String
                                                     name, Integer old_price, Integer price, String product_id, String item_id) {

        /*https://upgrade.eoutlet.com/rest/V1/carts/mine/items*/


        // parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Map<Object, Object> mainparam = new HashMap<>();

        Map<String, Object> map1 = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        Map<String, String> sizemap = new HashMap<>();

        ArrayList<Object> sizeandcolour = new ArrayList<>();
        Map<String, Object> map3 = new HashMap<>();
        Map<String, Object> map4 = new HashMap<>();
        if (type.equals("simple")) {


            map1.put("quote_id", String.valueOf(MySharedPreferenceClass.getCartId(getContext())));


            map1.put("sku", String.valueOf(sku));

            map1.put("qty", String.valueOf(1));

            mainparam.put("cart_item", map1);


            HashMap<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer" + " " + authtoken);
            // headers.put("Cookie",MySharedPreferenceClass.getCookies(getApplicationContext()));

            Call<AddtoCartResponse> call = apiService.upgradedaddtocart(headers, mainparam);
            call.enqueue(new Callback<AddtoCartResponse>() {
                @Override
                public void onResponse(Call<AddtoCartResponse> call, Response<AddtoCartResponse> response) {

                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            //parentDialogListener.hideProgressDialog();

//                    if (response.body().msg.equals("success")) {
                            if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
                                Toast.makeText(getContext(), "Item added in your cart", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "تم إضافة المنتج لحقيبة تسوقك", Toast.LENGTH_SHORT).show();
                            }
                            getUpgradeCartDataGuest(item_id);

                            // } else {
                            // Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_SHORT).show();


                            //}
                        }
                    } else {
                        isClicked = false;
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

                    parentDialogListener.hideProgressDialog();
                }


                @Override
                public void onFailure(Call<AddtoCartResponse> call, Throwable t) {
                    isClicked = false;
                    Log.e(TAG, t.toString());

                    Toast.makeText(getApplicationContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                            "An error occurred - please try again", Toast.LENGTH_LONG).show();
                    parentDialogListener.hideProgressDialog();
                }
            });
        } else {


            parentDialogListener.showProgressDialog();
            ListItems listItems = new ListItems();
            listItems.name = name;
            listItems.id = product_id;
            listItems.price = price.toString();
            listItems.oldPrice = old_price.toString();
            listItems.sku = sku;
            listItems.type = type;
            Fragment productDetail = new ProductDetail();
            FragmentTransaction mFragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
            mFragmentTransaction.replace(UpgradeWishListFragment.this.getId(), productDetail).addToBackStack(null).commit();
            Bundle databund = new Bundle();
            //put your ArrayList data in bundle
            databund.putString("fromwhere", "fromWishList");
            databund.putString("sku", sku);
            databund.putSerializable("catagoryobject", listItems);
            databund.putString("pid", product_id);
            databund.putString("item_id", item_id);
            productDetail.setArguments(databund);
            parentDialogListener.hideProgressDialog();

        }

    }

    //            function to get Upgraded cart data and refresh the count of cart items
    public void getUpgradeCartData(String item_id) {
        totalcount = 0;
//        parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);
        Map<String, String> query = new HashMap<>();
        query.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        Call<UpgradedCartItems> call = apiService.getUpgradedCartDetail(Constants.UPGRADED_HOST_LINK + "rest/en/V1/api/customercart/" + MySharedPreferenceClass.getMyUserId(getContext()));
        call.enqueue(new Callback<UpgradedCartItems>() {
            @Override
            public void onResponse(Call<UpgradedCartItems> call, Response<UpgradedCartItems> response) {
//                parentDialogListener.hideProgressDialog();
                if (response.body().response.equals("success")) {
                    dovibrate();
                    if (response.body().data.items.size() > 0) {
                        for (int i = 0; i < response.body().data.items.size(); i++) {
                            totalcount = totalcount + (int) (response.body().data.items.get(i).qty);
                        }
                        parentDialogListener.hideProgressDialog();
                        removeWishlist(item_id);
                        MySharedPreferenceClass.setBedgeCount(getContext(), totalcount);
                        updatefix.updateBedgeCount();
                        MainActivity.notificationBadge.setVisibility(View.VISIBLE);
                        Log.e("Total Value in Cart--->", String.valueOf(totalcount));
                    }
                } else {
                    isClicked = false;
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : " An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpgradedCartItems> call, Throwable t) {
                isClicked = false;
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : " An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    //            function to get Upgraded guest cart data and refresh the count of cart items
    public void getUpgradeCartDataGuest(String item_id) {
        totalcount = 0;
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", " ");
        query.put("mask_key", MySharedPreferenceClass.getMaskkey(getContext()));
        query.put("cart_id", String.valueOf(MySharedPreferenceClass.getCartId(getContext())));

        Call<UpgradedCartItems> call;
        call = Locale == "ar" ? apiService.getUpgradedCartDetailforguestuser(Constants.UPGRADED_HOST_LINK + "rest/ar/V1/api/guestcart/" + MySharedPreferenceClass.getCartId(getContext())) : apiService.getUpgradedCartDetailforguestuser(Constants.UPGRADED_HOST_LINK + "rest/en/V1/api/guestcart/" + MySharedPreferenceClass.getCartId(getContext()));

        call.enqueue(new Callback<UpgradedCartItems>() {
            @Override
            public void onResponse(Call<UpgradedCartItems> call, Response<UpgradedCartItems> response) {
                if (response.body().response.equals("success")) {
                    dovibrate();
                    if (response.body().data.items.size() > 0) {
                        for (int i = 0; i < response.body().data.items.size(); i++) {
                            totalcount = totalcount + (int) (response.body().data.items.get(i).qty);
                        }
                        parentDialogListener.hideProgressDialog();
                        removeWishlist(item_id);
                        MySharedPreferenceClass.setBedgeCount(getContext(), totalcount);
                        updatefix.updateBedgeCount();
                        MainActivity.notificationBadge.setVisibility(View.VISIBLE);
                        Log.e("Total Value Cart Guest", String.valueOf(totalcount));
                    }
                } else {
                    isClicked = false;
                    Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : " An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpgradedCartItems> call, Throwable t) {
                isClicked = false;
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : " An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    //    function to initiate vibrate
    public void dovibrate() {
        Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        assert v != null;
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(80,
                    VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(100);
        }
    }

    //  function to remove Item from wishlist
    public void removeWishlist(String item_id) {
//        parentDialogListener.showProgressDialog();
        String mykey = getUniqueID2().replaceAll("[^A-Za-z0-9]", "");
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);
        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        if (MySharedPreferenceClass.getMyUserId(getApplicationContext()).equals(" ")) {
            map1.put("device_id", "1" + mykey.toUpperCase() + "2");
        } else {
            map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        }

        map1.put("item_id", item_id);
        mainparam.put("param", map1);
        Call<RemoveFromWishListResponse> call;
        if (MySharedPreferenceClass.getMyUserId(getApplicationContext()).equals(" ")) {
            call = apiService.removeFromGuestWishlist(mainparam);
        } else {
            call = apiService.removeFromWishlist(mainparam);
        }
        call.enqueue(new Callback<RemoveFromWishListResponse>() {
            @Override
            public void onResponse(Call<RemoveFromWishListResponse> call, Response<RemoveFromWishListResponse> response) {
                isClicked = false;
//                parentDialogListener.hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().getResponse().equals("success") || response.body().getMsg().equals("success")) {
//                        Toast.makeText(getContext(), Locale == "ar" ? "Removed from Wishlist" : "Removed from Wishlist", Toast.LENGTH_LONG).show();
                        getWishList();
                    } else {
                        Log.e("RemoveFromWishList", "error");
                        Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : " An error occurred - please try again", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RemoveFromWishListResponse> call, Throwable t) {
                isClicked = false;
                parentDialogListener.hideProgressDialog();
                Log.e("RemoveFromWishList Fail", t.toString());
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" : " An error occurred - please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void opendetail(String item_id, String product_id, String type, String sku, String name, Integer old_price, Integer price) {

        parentDialogListener.showProgressDialog();
        ListItems listItems = new ListItems();
        listItems.name = name;
        listItems.id = product_id;
        listItems.price = price.toString();
        listItems.oldPrice = old_price.toString();
        listItems.sku = sku;
        listItems.type = type;
        Fragment productDetail = new ProductDetail();
        FragmentTransaction mFragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(UpgradeWishListFragment.this.getId(), productDetail).addToBackStack(null).commit();
        Bundle databund = new Bundle();
        //put your ArrayList data in bundle
        databund.putString("fromwhere", "fromWishList");
        databund.putString("sku", sku);
        databund.putSerializable("catagoryobject", listItems);
        databund.putString("pid", product_id);
        databund.putString("item_id", item_id);
        productDetail.setArguments(databund);
        parentDialogListener.hideProgressDialog();


    }
}