package com.eoutlet.Eoutlet.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaDrm;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.bumptech.glide.Glide;
import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.adpters.ProductListAdapter;
import com.eoutlet.Eoutlet.adpters.RelatedProductAdapter;
import com.eoutlet.Eoutlet.adpters.SizeAdapter;
import com.eoutlet.Eoutlet.adpters.SliderAdapter;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.api.request.CatagoryList;
import com.eoutlet.Eoutlet.api.request.ListItems;
import com.eoutlet.Eoutlet.api.request.UpgradedBasicBuilder;
import com.eoutlet.Eoutlet.intrface.ExecuteFragment;
import com.eoutlet.Eoutlet.intrface.ParentDialogsListener;
import com.eoutlet.Eoutlet.intrface.UpdateBedgeCount;
import com.eoutlet.Eoutlet.listener.ViewListener;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.AddtoCartResponse;
import com.eoutlet.Eoutlet.pojo.AddtoWishList;
import com.eoutlet.Eoutlet.pojo.ColorandSizeDetail;
import com.eoutlet.Eoutlet.pojo.ProductInfoResponse;
import com.eoutlet.Eoutlet.pojo.UpgradedCartItems;
import com.eoutlet.Eoutlet.utility.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.moe.pushlibrary.MoEHelper;
import com.moengage.core.Properties;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;


public class ProductDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Context mContext;
    ProductList  prodctlistcontext;
    private ExecuteFragment execute;
    private ViewPager viewPager;
    private ListItems catagoryList = new ListItems();
    private UpdateBedgeCount updatefix;
    private String Locale = " ";
    public boolean isaddedtowishList = false;
    public ScrollView scrollcontainer;

    private String newprice;
    private ImageView ivcolor;
    View toolbarbeg;
    private ArrayList<String> sizedata = new ArrayList<>();
    private ArrayList<String> indexdata = new ArrayList<>();
    private ArrayList<String> colordata = new ArrayList<>();
    private ArrayList<String> colorurl = new ArrayList<>();
    private ArrayList<Integer> salable = new ArrayList<>();
    private ArrayList<String> priceaccordingtosize = new ArrayList<>();
    private ArrayList<String> oldpriceaccordingtosize = new ArrayList<>();

    private ArrayList<String> indexdata2 = new ArrayList<>();
    private String choosensizeIndex = "";
    private String sizeattId = " ";
    private String colourattID = "";
    static String choosencolorIndex = " ";
    String producturl = " ";
    private RelatedProductAdapter mAdapter7;
    private TextView toolbar_bedgetextdetail, tooltext;
    int choosenquantity = 1;
    int totalcount;
    private SizeAdapter mAdapter;
    private LinearLayout colourandsize, relatedproducttext;
    private RecyclerView sizerecycler, relatedproduct;
    private RelativeLayout sizeofItem, addtowishlist2, productShare;
    private GridLayoutManager lm;
    private LinearLayoutManager llm;
    private String previouspage= " ";

    private String item_catagory_name, sellerId;
    private FirebaseAnalytics mFirebaseAnalytics;
    private TextView tvaddtocart;
    TextView sellerName, oldPrice, newPrice, description, productname, tvplus, tvfinalqty, selectedsizename, selectcoloname, sizeselectiontext,
            sizeofsimplaeitem, tv_sku, sizechart;
    LinearLayout sellerContainer;

    private FrameLayout tvminace;
    public static String value, pid, size, category_id,category_name,color, colorname, sizechartposition = " ", fromwhere, item_id, sizecharthtml;
    ;

    private ImageView searchImage, backarrow,sizechartimage;
    private Toolbar toolbar1;
    private String type;
    public ParentDialogsListener parentDialogListener;
    public ImageView product_heart;


    private List<String> myProductList = new ArrayList<>();
    private TextView viewmore, nameontop;
    int[] myImageList = new int[]{R.drawable.pro1, R.drawable.pro2, R.drawable.pro3, R.drawable.pro4, R.drawable.pro5, R.drawable.pro6,
            R.drawable.pro7, R.drawable.pro8, R.drawable.pro9, R.drawable.pro10};
    // TODO: Rename and change types of parameters
    View v;
    private static ProductDetail instance = null;

    private OnFragmentInteractionListener mListener;

    public ProductDetail() {
        // Required empty public constructor
    }
    public ProductDetail(ProductList  prodctlistcontext) {

        this.prodctlistcontext  = prodctlistcontext;

    }
    public void passData(Context context) {
        mContext = context;
        execute = (MainActivity) getActivity();
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

    public static ProductDetail newInstance(String param1, String param2) {
        ProductDetail fragment = new ProductDetail();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static ProductDetail getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        instance = this;


        // Inflate the layout for this fragment
        if (v == null) {

            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("en")) {
                Locale = "en";
                v = inflater.inflate(R.layout.fragment_product_detail_english, container, false);
            } else {
                Locale = "ar";
                v = inflater.inflate(R.layout.fragment_product_detail, container, false);
            }

            initViews();
            execute = (MainActivity) getActivity();
            //value = getArguments().getString("sku");
            type = getArguments().getString("type");
            pid = getArguments().getString("pid");
Log.e("pid is--->>>",pid);
            size = getArguments().getString("size");
            color = getArguments().getString("color");
            colorname = getArguments().getString("color_name");
            producturl =  getArguments().getString("itemurl");
            previouspage = getArguments().getString("previouspage");

            //sizechartposition = getArguments().getString("size_chart");

            getArguments().getString("fromwhere");
            item_id = getArguments().getString("item_id");


            updatefix = (MainActivity) getActivity();


            catagoryList = (ListItems) getArguments().getSerializable("catagoryobject");



            getupgradedProductDetail();




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




        }


        return v;
    }

    public void initSizeRecycler() {

        if (salable.get(0) == 1) {
            selectedsizename.setText(sizedata.get(0));
        }


        sizerecycler.setHasFixedSize(true);
        sizerecycler.setNestedScrollingEnabled(false);


        lm = new GridLayoutManager(v.getContext(), 5) {
            @Override
            protected boolean isLayoutRTL() {
                if (Locale == "ar") {
                    return true;
                } else {
                    return false;
                }
            }
        };
        sizerecycler.setLayoutManager(lm);




        // specify an adapter (see also next example)
        mAdapter = new SizeAdapter(v.getContext(), sizedata, indexdata, salable, new ViewListener() {
            @Override
            public void onClick(int position, View view) {
                int id = view.getId();


                switch (id) {

                    case R.id.sizeofItems://button for message


                        choosensizeIndex = indexdata.get(position);
                        choosencolorIndex = indexdata2.get(0);
                        selectedsizename.setText(sizedata.get(position));
                        int newfinalprice = Math.round((Float.parseFloat(priceaccordingtosize.get(position)) * MySharedPreferenceClass.getSelectedCurrencyRate(v.getContext())));
                        newPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(v.getContext()) + " " + newfinalprice);


                        if (oldpriceaccordingtosize.get(position).equals("0") || oldpriceaccordingtosize == priceaccordingtosize) {

                            oldPrice.setVisibility(View.GONE);


                        } else {
                            oldPrice.setVisibility(View.VISIBLE);
                            String mainprice = " ";
                            if (oldpriceaccordingtosize.contains(",")) {
                                mainprice = oldpriceaccordingtosize.get(position).replaceAll(",", "");


                                oldPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(v.getContext()) + " " + Math.round(Float.parseFloat(mainprice) * MySharedPreferenceClass.getSelectedCurrencyRate(v.getContext())));


                            } else {
                                oldPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(v.getContext()) + " " + Math.round(Float.parseFloat(oldpriceaccordingtosize.get(position)) * MySharedPreferenceClass.getSelectedCurrencyRate(v.getContext())));


                            }

                        }


                        break;
                }
            }
        });
        sizerecycler.setAdapter(mAdapter);

        //getRelatedproduct();
        getUpgardeRelatedproduct();


    }

    //Initializing Views
    public void initViews() {

        sellerContainer = v.findViewById(R.id.sellerContainer);
        sellerName = v.findViewById(R.id.sellerName);
        oldPrice = v.findViewById(R.id.productoldPrice);
        newPrice = v.findViewById(R.id.productnewprice);
        productname = v.findViewById(R.id.productName);
        description = v.findViewById(R.id.productDescription);
        tvaddtocart = v.findViewById(R.id.addtoCart);
        sizerecycler = v.findViewById(R.id.size_list_Recycler);
        colourandsize = v.findViewById(R.id.colorandsize);
        tvplus = v.findViewById(R.id.plus_button);
        tvminace = v.findViewById(R.id.minace_button);
        tvfinalqty = v.findViewById(R.id.selectedQuantitity);
        relatedproduct = v.findViewById(R.id.related_product);
        selectedsizename = v.findViewById(R.id.sizename);
        selectcoloname = v.findViewById(R.id.colorname);
        sizeselectiontext = v.findViewById(R.id.selectsizetext);
        ivcolor = v.findViewById(R.id.colorImage);
        relatedproducttext = v.findViewById(R.id.relatedproducttext);
        tv_sku = v.findViewById(R.id.skunumber);
        sizechart = v.findViewById(R.id.opensizechart);
        addtowishlist2 = v.findViewById(R.id.addtowishlist);
        productShare = v.findViewById(R.id.productShare);
        sizeofItem = v.findViewById(R.id.relativesimplesizeview);
        sizeofsimplaeitem = v.findViewById(R.id.sizeofsimpleItems);
        product_heart = v.findViewById(R.id.product_heart);
        toolbar1 = v.findViewById(R.id.toolbar);
        searchImage = toolbar1.findViewById(R.id.serachbar);
        backarrow = toolbar1.findViewById((R.id.backarrow));
        nameontop = toolbar1.findViewById(R.id.nameontop);
        viewmore = v.findViewById(R.id.viewmore);
        sizechartimage = v.findViewById(R.id.sizechartimage);



        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());


        sellerContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment sellerPage = new SellerPage();
                Bundle databund = new Bundle();
                Log.e("sellerId", sellerId);
                databund.putString("sellerId", sellerId);
                sellerPage.setArguments(databund);
                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.productContainer, sellerPage)
                        .commit();
            }
        });

        sizechart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSizeChartBottomSheet();


            }
        });
sizechartimage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        openSizeChartBottomSheet();




    }
});

        tvplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choosenquantity++;
                tvfinalqty.setText(String.valueOf(choosenquantity));


            }
        });
        tvminace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choosenquantity > 1) {

                    choosenquantity--;
                    tvfinalqty.setText(String.valueOf(choosenquantity));
                }


            }
        });
        try {
            tv_sku.setText(catagoryList.sku);


            if (catagoryList.price.contains(",")) {
                newprice = catagoryList.price.replaceAll(",", "");
                int newfinalprice = Math.round((Float.parseFloat(newprice) * MySharedPreferenceClass.getSelectedCurrencyRate(v.getContext())));
                newPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(v.getContext()) + " " + newfinalprice);


            } else {

                int newfinalprice = Math.round((Float.parseFloat(catagoryList.price) * MySharedPreferenceClass.getSelectedCurrencyRate(v.getContext())));
                newPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(v.getContext()) + " " + newfinalprice);


            }


            if (catagoryList.oldPrice.equals("0")) {

                oldPrice.setVisibility(View.GONE);


            } else {
                oldPrice.setVisibility(View.VISIBLE);
                String mainprice = " ";
                if (catagoryList.oldPrice.contains(",")) {
                    mainprice = catagoryList.oldPrice.replaceAll(",", "");

                    oldPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(v.getContext()) + " " + Math.round(Float.parseFloat(mainprice) * MySharedPreferenceClass.getSelectedCurrencyRate(v.getContext())));


                } else {
                    oldPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(v.getContext()) + " " + Math.round(Float.parseFloat(catagoryList.oldPrice) * MySharedPreferenceClass.getSelectedCurrencyRate(v.getContext())));


                }

            }
            productname.setText(catagoryList.name);
            nameontop.setText(catagoryList.name);
            nameontop.setVisibility(View.VISIBLE);

            description.setText(catagoryList.short_descrition);

            description.post(new Runnable() {
                @Override
                public void run() {
                    int lineCount = description.getLineCount();
                    if (lineCount > 2) {

                        viewmore.setVisibility(View.VISIBLE);
                        if (Locale.equals("ar")) {
                            viewmore.setText("قراءة المزيد…");
                        } else {
                            viewmore.setText("View More...");
                        }
                        description.setMaxLines(2);

                    } else {
                        description.setText(catagoryList.short_descrition);
                    }
                    // Use lineCount here
                }
            });
            viewmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (description.getLineCount() == 2) {
                        description.setText(catagoryList.short_descrition.trim());
                        viewmore.setVisibility(View.GONE);
                    } else {
                        description.setText(catagoryList.short_descrition.trim());
                        description.setMaxLines(20);
                        if (Locale.equals("ar")) {
                            viewmore.setText("قراءة المزيد…");
                        } else {
                            viewmore.setText("View More...");
                        }
                        viewmore.setVisibility(View.GONE);
                    }
                }
            });


        } catch (Exception e) {

            //Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_SHORT).show();


        }

        addtowishlist2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addtowishlist();
                if (!MySharedPreferenceClass.getMyUserId(getContext()).equals(" ")) {

                    if (!isaddedtowishList) {
                       addtowishlist();


                    } else {
                        removeFromWishList();


                    }

                } else {
                    if (!isaddedtowishList) {
                        addtowishlistforGuestUser();

                    }
                    else {
                        removeFromWishList();
                    }


                }

            }
        });

        productShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "https://eoutlet.onelink.me/YH5c?pid=product&c=Product%20Info&product_id=" + pid + "&sku=" + tv_sku.getText().toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        tvaddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!type.equals("simple")) {
                    if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {
                        if (choosensizeIndex != null && !choosensizeIndex.equals("")) {


                            getTokenID();

                        } else {
                            if (Locale.equals("ar")) {
                                Toast.makeText(getContext(), " الرجاء اختيار المقاس المطلوب أولا", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Please choose the required size first", Toast.LENGTH_SHORT).show();

                            }
                        }
                    } else {
                        if (choosensizeIndex != null && !choosensizeIndex.equals("")) {
                            // addtoCartguestUser();
                            //upgradeaddtocartforguestuser();

                            getTokenID();
                        } else {
                            if (Locale.equals("ar")) {
                                Toast.makeText(getContext(), "الرجاء اختيار المقاس المطلوب أولا", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Please choose the required size first", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }


                } else {

                    if (MySharedPreferenceClass.getMyUserId(getContext()) != " ") {


                        getTokenID();

                    } else {


                        getTokenID();

                    }


                }
            }


        });


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

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

    public void intiViewPager(final List<String> producudetail) {
        viewPager = v.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(myImageList.length);
        viewPager.setAdapter(new SliderAdapter(v.getContext(), producudetail));

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.indicator);
        tabLayout.setupWithViewPager(viewPager, true);


    }


    //Getting multiple Images of Particular Product here.
    public void getupgradedProductDetail() {

        //  parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Call<ProductInfoResponse> call;
        if (Locale.equals("en")) {
            call = apiService.getupgradedCataGoryDetail(Constants.upgradedmediaSourceURL + "rest/en/V1/api/productinfo/" + pid);
        } else {
            call = apiService.getupgradedCataGoryDetail(Constants.upgradedmediaSourceURL + "rest/ar/V1/api/productinfo/" + pid);
        }

        //The version always remain V1 in this


        call.enqueue(new Callback<ProductInfoResponse>() {
            @Override
            public void onResponse(Call<ProductInfoResponse> call, Response<ProductInfoResponse> response) {



                if (response.body().data.seller_name == null || response.body().data.seller_name.equalsIgnoreCase(" ")) {

                    sellerContainer.setVisibility(View.GONE);

                } else {
                    sellerContainer.setVisibility(View.VISIBLE);
                }
              category_id = response.body().data.category_id;
                category_name = response.body().data.category_name;
                type = response.body().data.type;
                value = response.body().data.sku;

                productname.setText(response.body().data.name);
                nameontop.setText(response.body().data.name);
                nameontop.setVisibility(View.VISIBLE);
                sellerName.setText(response.body().data.seller_name);
                sellerId = response.body().data.seller_id;
                sizechartposition = response.body().data.size_chart;

                sizecharthtml = response.body().data.size_chart_html;
                ListItems catagoryList = new ListItems();
                catagoryList.price = response.body().data.price.toString();
                catagoryList.oldPrice = response.body().data.oldPrice.toString();

                if(response.body().data.is_size_chart.equalsIgnoreCase("true")) {

                    if (sizecharthtml != null && !sizecharthtml.equals("")) {
                        sizechart.setVisibility(View.VISIBLE);
                        sizechartimage.setVisibility(View.VISIBLE);

                    }
                }
                description.setText(response.body().data.shortdescription);
                tv_sku.setText(value);
                if (catagoryList.price.contains(",")) {
                    newprice = catagoryList.price.replaceAll(",", "");
                    int newfinalprice = Math.round((Float.parseFloat(newprice) * MySharedPreferenceClass.getSelectedCurrencyRate(v.getContext())));
                    newPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(v.getContext()) + " " + newfinalprice);


                } else {

                    int newfinalprice = Math.round((Float.parseFloat(catagoryList.price) * MySharedPreferenceClass.getSelectedCurrencyRate(v.getContext())));
                    newPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(v.getContext()) + " " + newfinalprice);


                }

                if (catagoryList.oldPrice.equals("0")) {

                    oldPrice.setVisibility(View.GONE);


                } else {
                    oldPrice.setVisibility(View.VISIBLE);
                    String mainprice = " ";
                    if (catagoryList.oldPrice.contains(",")) {
                        mainprice = catagoryList.oldPrice.replaceAll(",", "");

                        oldPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(v.getContext()) + " " + Math.round(Float.parseFloat(mainprice) * MySharedPreferenceClass.getSelectedCurrencyRate(v.getContext())));


                    } else {
                        oldPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(v.getContext()) + " " + Math.round(Float.parseFloat(catagoryList.oldPrice) * MySharedPreferenceClass.getSelectedCurrencyRate(v.getContext())));


                    }

                }
                description.post(new Runnable() {
                    @Override
                    public void run() {
                        int lineCount = description.getLineCount();
                        if (lineCount > 2) {

                            viewmore.setVisibility(View.VISIBLE);
                            if (Locale.equals("ar")) {
                                viewmore.setText("قراءة المزيد…");
                            } else {
                                viewmore.setText("View More...");
                            }

                            description.setMaxLines(2);

                        } else {
                            description.setText(response.body().data.shortdescription);
                        }
                        // Use lineCount here
                    }
                });

                viewmore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (description.getLineCount() == 2) {
                            description.setText(response.body().data.shortdescription.trim());
                            viewmore.setVisibility(View.GONE);
                        } else {
                            description.setText(response.body().data.shortdescription.trim());
                            description.setMaxLines(20);
                            if (Locale.equals("ar")) {
                                viewmore.setText("قراءة المزيد…");
                            } else {
                                viewmore.setText("View More...");
                            }


                            viewmore.setVisibility(View.GONE);
                        }
                    }
                });
                if (response.body() != null) {

                    if (!type.equals("simple")) {
                        //getcolorandSize(value);
                        getupgradedcolorandSize(value);


                    } else {
                        selectcoloname.setText(colorname);

                        if (response.body().data.color != null) {
                            Glide.with(ivcolor)
                                    .load(response.body().data.color)/*.override(70, 70)*/
                                    .into(ivcolor);
                        }

                        // Picasso.get().load(color)/*.fit()*/.resize(70, 70).centerCrop().into(ivcolor);
                        selectedsizename.setText(response.body().data.size.toString());

                        // getRelatedproduct();

                        getUpgardeRelatedproduct();
                    }

                    if (response.body().response.equals("success")) {

                        if (type.equals("simple")) {
                            sizeofsimplaeitem.setText(size);

                            //colourandsize.setVisibility(View.GONE);


                        } else {
                            sizeofItem.setVisibility(View.GONE);
                            colourandsize.setVisibility(View.VISIBLE);

                        }


                        for (int i = 0; i < response.body().data.image.size(); i++) {


                            //myProductList.add(Constants.mediaSourceURL + "/pub/media/catalog/product/" + response.body().mediaGalleryEntries.get(i).file);
                            myProductList.add(response.body().data.image.get(i));


                        }
                        intiViewPager(myProductList);


                    } else {
                        parentDialogListener.hideProgressDialog();


                    }
                } else {
                    parentDialogListener.hideProgressDialog();


                }
                firebase_view_item(value, response.body().data.name, response.body().data.productId);

                moengageProductViewEvent(newprice, String.valueOf(value));
            }


            @Override
            public void onFailure(Call<ProductInfoResponse> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void getTokenID() {


        parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Map<Object, Object> map1 = new HashMap<>();
        map1.put("username", MySharedPreferenceClass.getAdminUsername(getApplicationContext()));
        map1.put("password", MySharedPreferenceClass.getAdminPassword(getApplicationContext()));

        Call<String> call;
        if (Locale.equals("en")) {
            call = apiService.createtokenbyId(map1);

        } else {
            call = apiService.createtokenbyIdArabic(map1);
        }
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null && response.isSuccessful()) {
                    // parentDialogListener.hideProgressDialog();


                    if (!MySharedPreferenceClass.getMyUserId(getApplicationContext()).equals(" ")) {
                        getQuoteID(response.body().toString());
                        //upgradeaddtocart(MySharedPreferenceClass.getQuoteId(getContext()),response.body().toString());

                    } else {
                        upgradeaddtocartforguestuser(response.body().toString());
                    }


                } else {

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
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    public void getupgradedcolorandSize(String value) {

        //parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();

        query.put("sku", value);
        Call<ColorandSizeDetail> call;
        if (Locale.equalsIgnoreCase("ar")) {
            call = apiService.getupgradedColorandSize("https://upgrade.eoutlet.com/rest/ar/V1/api/productsizecolor/" + value);
        } else {
            call = apiService.getupgradedColorandSize("https://upgrade.eoutlet.com/rest/en/V1/api/productsizecolor/" + value);
        }

        call.enqueue(new Callback<ColorandSizeDetail>() {
            @Override
            public void onResponse
                    (Call<ColorandSizeDetail> call, Response<ColorandSizeDetail> response) {


                if (response.body() != null) {
                    parentDialogListener.hideProgressDialog();


                    try {

                        if (response.body().data.size() > 0) {

                            sizeselectiontext.setVisibility(View.VISIBLE);
                            if (response.body().data.get(1) != null) {
                                for (int i = 0; i < response.body().data.get(1).data.size(); i++) {
                                    sizedata.add(response.body().data.get(1).data.get(i).name);
                                    indexdata.add(response.body().data.get(1).data.get(i).value_Index);
                                    salable.add(response.body().data.get(1).data.get(i).salable);
                                    priceaccordingtosize.add(String.valueOf(response.body().data.get(1).data.get(i).price));
                                    oldpriceaccordingtosize.add(String.valueOf(response.body().data.get(1).data.get(i).old_price));


                                }
                            }
                            for (int i = 0; i < response.body().data.get(0).data.size(); i++) {
                                colordata.add(response.body().data.get(0).data.get(i).name);

                                indexdata2.add(response.body().data.get(0).data.get(i).value_Index);
                                choosencolorIndex = indexdata2.get(0);/*value_index*/
                                colourattID = response.body().data.get(0).attrId.toString();
                                colorurl.add(response.body().data.get(0).data.get(i).image);

                                /*attr_id,value_index*/
                            }
                            if (salable.get(0) == 1) {

                                choosensizeIndex = response.body().data.get(1).data.get(0).value_Index;
                                sizeattId = response.body().data.get(1).attrId.toString();
                            } else {
                                sizeattId = response.body().data.get(1).attrId.toString();
                            }

                            choosencolorIndex = indexdata2.get(0);
                            selectcoloname.setText(colordata.get(0));
                            if (colorurl.get(0) != null && colorurl.get(0) != "" && colorurl.get(0) != " ") {
                                Picasso.get().load(colorurl.get(0))/*.fit()*/.resize(70, 70).centerCrop().into(ivcolor);
                            }
                            initSizeRecycler();

                            if(!sizecharthtml.equals("")) {
                                sizechart.setVisibility(View.VISIBLE);
                                sizechartimage.setVisibility(View.VISIBLE);
                            }


                        }
                    } catch (Exception e) {

                        parentDialogListener.hideProgressDialog();
                        Log.e("Exception--->", "There are some Exception");


                    }


                } else {
                    parentDialogListener.hideProgressDialog();


                }


            }


            @Override
            public void onFailure(Call<ColorandSizeDetail> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });

        getUpgardeRelatedproduct();

    }


    public void upgradeaddtocart(String quoteId, String authtoken) {

        /*https://upgrade.eoutlet.com/rest/V1/carts/mine/items*/


        //parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);


        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> mainparam1 = new HashMap<>();
        Map<Object, Object> mainparam2 = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        Map<String, String> sizemap = new HashMap<>();

        ArrayList<Object> sizeandcolour = new ArrayList<>();
        Map<String, Object> map3 = new HashMap<>();
        Map<String, Object> map4 = new HashMap<>();
        if (type.equals("simple")) {


            /* {"cart_item" :{"quote_id":191,"sku" : "Mo0074","qty" : 1}}*/



          /*  query.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
            query.put("sku", String.valueOf(value));
            query.put("type", type);
            query.put("qty", String.valueOf(choosenquantity));
            query.put("size", " ");
            query.put("color", " ");
            query.put("product_id", catagoryList.id);
            query.put("cart_id", "");
            query.put("mask_key", "");*/


            map1.put("quote_id", quoteId);

            map1.put("sku", String.valueOf(value));

            map1.put("qty", String.valueOf(choosenquantity));

            mainparam.put("cart_item", map1);


        } else {
            /*query.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
            query.put("sku", String.valueOf(value));
            query.put("size", choosensizeIndex);
            query.put("color", choosencolorIndex);
            query.put("type", type);
            query.put("qty", String.valueOf(choosenquantity));
            query.put("product_id", catagoryList.id);
            query.put("cart_id", "");
            query.put("mask_key", "");*/
/*

            {
                "cart_item": {
                "quote_id": 373,
                        "sku": "rc01196",
                        "qty": 1,
                        "product_option": {
                    "extension_attributes": {
                        "configurable_item_options": [
                        {"option_value": "5561",
                                "option_id": "93"},
                        {"option_value": "5586",
                                "option_id": "191"}
                ]
                    }
                }
            }
            }
*/

           /* {"cart_item" :{"quote_id":191,"sku" : "Mo0074","qty" : 1, "product_option" : {"extension_attributes":{"configurable_item_options" : {
                {"option_id" : 172, "option_value" : 23 } } } }}}
                */


            /* {"cart_item":{"quote_id":"277","qty":"1","sku":"rc01196","product_option":{"extension_attributes":{"configurable_item_options":
            {"option_value":"5561","option_id":"93"}}}}}*/


            map1.put("quote_id", quoteId);

            map1.put("sku", String.valueOf(value));

            map1.put("qty", String.valueOf(choosenquantity));

            map2.put("option_id", colourattID);//att_id

            map2.put("option_value", choosencolorIndex);//value_index

            sizemap.put("option_id", sizeattId);//att_id 191

            sizemap.put("option_value", choosensizeIndex);//value_index 55865586 choosensizeIndex

            sizeandcolour.add(map2);
            sizeandcolour.add(sizemap);


            map3.put("configurable_item_options", sizeandcolour);


            map4.put("extension_attributes", map3);

            map1.put("product_option", map4);


            mainparam.put("cart_item", map1);

        }
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer" + " " + authtoken);
        headers.put("Cookie", MySharedPreferenceClass.getCookies(getApplicationContext()));


        Call<AddtoCartResponse> call = apiService.upgradedaddtocart(headers, mainparam);
        call.enqueue(new Callback<AddtoCartResponse>() {
            @Override
            public void onResponse(Call<AddtoCartResponse> call, Response<AddtoCartResponse> response) {
                //parentDialogListener.hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        parentDialogListener.hideProgressDialog();


                        if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
                            Toast.makeText(getContext(), "Item added in your cart", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "تم إضافة المنتج لحقيبة تسوقك", Toast.LENGTH_SHORT).show();
                        }


//                    if (response.body().msg.equals("success")) {


                        appsflyer_event_add_to_cart(newprice, String.valueOf(value), String.valueOf(choosenquantity));
                        firebase_event_add_to_cart(newprice, String.valueOf(value), String.valueOf(choosenquantity));
                        moengageAddtoCartEvent(newprice, String.valueOf(value), String.valueOf(choosenquantity));

                        getUpgradeCartData();


                        // } else {
                        // Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_SHORT).show();
                        parentDialogListener.hideProgressDialog();


                        //}
                    }
                } else {
                    parentDialogListener.hideProgressDialog();
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
                        parentDialogListener.hideProgressDialog();
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
                Toast.makeText(getApplicationContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());

                //Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void upgradeaddtocartforguestuser(String authtoken) {

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


            map1.put("sku", String.valueOf(value));

            map1.put("qty", String.valueOf(choosenquantity));

            mainparam.put("cart_item", map1);


        } else {


            map1.put("quote_id", MySharedPreferenceClass.getCartId(getContext()));

            map1.put("sku", String.valueOf(value));

            map1.put("qty", String.valueOf(choosenquantity));

            map2.put("option_id", colourattID);//att_id

            map2.put("option_value", choosencolorIndex);//value_index

            sizemap.put("option_id", sizeattId);//att_id 191

            sizemap.put("option_value", choosensizeIndex);//value_index 55865586 choosensizeIndex

            sizeandcolour.add(map2);
            sizeandcolour.add(sizemap);


            map3.put("configurable_item_options", sizeandcolour);


            map4.put("extension_attributes", map3);

            map1.put("product_option", map4);


            mainparam.put("cart_item", map1);

        }

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


                        appsflyer_event_add_to_cart(newprice, String.valueOf(value), String.valueOf(choosenquantity));
                        firebase_event_add_to_cart(newprice, String.valueOf(value), String.valueOf(choosenquantity));
                        moengageAddtoCartEvent(newprice, String.valueOf(value), String.valueOf(choosenquantity));
                        //getCartData();
                        getUpgradedCartDataforGuestUser();


                        // } else {
                        // Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_SHORT).show();


                        //}
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

                parentDialogListener.hideProgressDialog();
            }


            @Override
            public void onFailure(Call<AddtoCartResponse> call, Throwable t) {

                Log.e(TAG, t.toString());

                Toast.makeText(getApplicationContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
                parentDialogListener.hideProgressDialog();
            }
        });


    }

    public void addtowishlist() {



/*{
    "param": {
        "customer_id": "83188",
        "product_id":"32018"
    }
}*/

        parentDialogListener.showProgressDialog();


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));
        map1.put("product_id", pid);
        mainparam.put("param", map1);

        Call<AddtoWishList> call = apiService.addtowishlist(mainparam);
        call.enqueue(new Callback<AddtoWishList>() {
            @Override
            public void onResponse(Call<AddtoWishList> call, Response<AddtoWishList> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        if (response.body().response.equals("success")) {
                            // Update Wishlist Badge Count
                            HomeFragmentNewDesign.getInstance().getWishListItemCount();

                            if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
                                Toast.makeText(getContext(), "Product added to Wishlist", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "تم اضافة المنتج إلى قائمة الأمنيات..", Toast.LENGTH_LONG).show();
                            }


                            product_heart.setImageDrawable(v.getContext().getResources().getDrawable(R.drawable.heartselected));
                            isaddedtowishList = true;

                        }
                        parentDialogListener.hideProgressDialog();
                    } else {
                        parentDialogListener.hideProgressDialog();


                    }

                    moengageAddtoWishListEvent();
                } else {

                    if (response.code() == 400) {
                        parentDialogListener.hideProgressDialog();
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
                        parentDialogListener.hideProgressDialog();
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
                Toast.makeText(getApplicationContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();

            }
        });


    }

    public void addtowishlistforGuestUser() {


        String myKey = getUniqueID2().toString().replaceAll("[^A-Za-z0-9]", "");


        Log.e("mykey is", myKey);
/*{
      "param": {
          "device_id":"23476242784b24b2646v",
          "product_id": "52862"
      }
}
*/

        parentDialogListener.showProgressDialog();


        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("device_id", "1" + myKey.toUpperCase() + "2");
        map1.put("product_id", pid);
        mainparam.put("param", map1);

        Call<AddtoWishList> call = apiService.addtowishlistforGuestUser(mainparam);
        call.enqueue(new Callback<AddtoWishList>() {
            @Override
            public void onResponse(Call<AddtoWishList> call, Response<AddtoWishList> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        if (response.body().msg.equals("success")) {
                            // Update Wishlist Badge Count
                            HomeFragmentNewDesign.getInstance().getWishListItemCountforguestUser();

                            if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
                                Toast.makeText(getContext(), "Product added to Wishlist", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "تم اضافة المنتج إلى قائمة الأمنيات..", Toast.LENGTH_LONG).show();
                            }


                            product_heart.setImageDrawable(v.getContext().getResources().getDrawable(R.drawable.heartselected));
                            isaddedtowishList = true;
                            moengageAddtoWishListEvent();
                        }
                        parentDialogListener.hideProgressDialog();
                    } else {
                        parentDialogListener.hideProgressDialog();


                    }
                } else {

                    if (response.code() == 400) {
                        parentDialogListener.hideProgressDialog();
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
                        parentDialogListener.hideProgressDialog();
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
                Toast.makeText(getApplicationContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();

            }
        });


    }
    public  void moengageAddtoWishListEvent(){

        ArrayList<String> catname = new ArrayList();

        Properties properties = new Properties();


        properties .addAttribute("sku",tv_sku.getText().toString() )

                .addAttribute("name", productname.getText().toString())
                .addAttribute("type", type);



        MoEHelper.getInstance(getApplicationContext()).trackEvent("Add To Wishlist", properties);
      Log.e("sku",tv_sku.getText().toString() );
        Log.e("name", productname.getText().toString());
        Log.e("type", type);

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

    public void getUpgradedCartDataforGuestUser() {
        // parentDialogListener.showProgressDialog();

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


                    fromwhere = getArguments().getString("fromwhere");
                    if (fromwhere != null) {
                        if (fromwhere.equalsIgnoreCase("fromWishList")) {
                            UpgradeWishListFragment.getInstance().removeWishlist(item_id);
                            fromwhere = "";

                        }
                    }
                    //dovibrate();
                    parentDialogListener.hideProgressDialog();
                    if (response.body().data.items.size() > 0) {
                        for (int i = 0; i < response.body().data.items.size(); i++) {


                            totalcount = totalcount + (int) (response.body().data.items.get(i).qty);


                        }
                        parentDialogListener.hideProgressDialog();
                        MySharedPreferenceClass.setBedgeCount(getContext(), totalcount);

                        updatefix.updateBedgeCount();
                        MainActivity.notificationBadge.setVisibility(View.VISIBLE);


                        Log.e("Total Value in Cart--->", String.valueOf(totalcount));


                    } else {
                        MainActivity.bedgetext.setVisibility(View.GONE);
                    }


                } else {
                    parentDialogListener.hideProgressDialog();
                    if (Locale.equals("ar")) {

                        Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Something went wrong-please try again ", Toast.LENGTH_LONG).show();
                    }

                }


            }


            @Override
            public void onFailure(Call<UpgradedCartItems> call, Throwable t) {
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());
                MainActivity.notificationBadge.setVisibility(View.GONE);
                MainActivity.bedgetext.setVisibility(View.GONE);
                MainActivity.toolbar_bedgetext.setVisibility(View.GONE);
            }
        });


    }


    public void getUpgradeCartData() {
        totalcount = 0;

        //  parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));


        /*https://upgrade.eoutlet.com/rest/en/V1/api/customercart/83188*/


        Call<UpgradedCartItems> call = apiService.getUpgradedCartDetail(Constants.UPGRADED_HOST_LINK + "rest/en/V1/api/customercart/" + MySharedPreferenceClass.getMyUserId(getContext()));
        call.enqueue(new Callback<UpgradedCartItems>() {
            @Override
            public void onResponse(Call<UpgradedCartItems> call, Response<UpgradedCartItems> response) {
                parentDialogListener.hideProgressDialog();

                if (response.body().response.equals("success")) {
                    //dovibrate();

                    if (response.body().data.items.size() > 0) {
                        for (int i = 0; i < response.body().data.items.size(); i++) {


                            totalcount = totalcount + (int) (response.body().data.items.get(i).qty);


                            // }


                        }
                        parentDialogListener.hideProgressDialog();
                        fromwhere = getArguments().getString("fromwhere");
                        item_id = getArguments().getString("item_id");

                        if (fromwhere != null) {
                            if (fromwhere.equalsIgnoreCase("fromWishList")) {
                                UpgradeWishListFragment.getInstance().removeWishlist(item_id);
                                fromwhere = "";
                            }
                        }
                        MySharedPreferenceClass.setBedgeCount(v.getContext(), totalcount);

                        updatefix.updateBedgeCount();
                        MainActivity.notificationBadge.setVisibility(View.VISIBLE);


                        Log.e("Total Value in Cart--->", String.valueOf(totalcount));


                    } else {


                        MainActivity.bedgetext.setVisibility(View.GONE);
                    }


                } else {

                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();

                }


            }


            @Override
            public void onFailure(Call<UpgradedCartItems> call, Throwable t) {
                parentDialogListener.hideProgressDialog();
                Log.e(TAG, t.toString());


                if (Locale.equals("ar")) {
                    Toast.makeText(getContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void getWishListFlag() {

        //parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();

        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getApplicationContext()));
        String myKey = getUniqueID2().toString().replaceAll("[^A-Za-z0-9]", "");

        Call<String> call;
        if (MySharedPreferenceClass.getMyUserId(getApplicationContext()).equals(" ")) {
            call = apiService.getWishListFlag("https://upgrade.eoutlet.com/rest/en/V1/api/checkwishlist/" + "1" + myKey.toUpperCase() + "2" + "/0/" + pid);
        } else {
            call = apiService.getWishListFlag("https://upgrade.eoutlet.com/rest/en/V1/api/checkwishlist/" + "0/" + MySharedPreferenceClass.getMyUserId(getContext()) + "/" + pid);

        }

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                parentDialogListener.hideProgressDialog();

                if (response.body() != null) {

                    if (response.body().toString().equals("0")) {

                        isaddedtowishList = false;


                    } else {

                        isaddedtowishList = true;
                        product_heart.setImageDrawable(v.getContext().getResources().getDrawable(R.drawable.heartselected));
                    }


                   // Log.d("Wish List Response is ------->>>>", response.body().toString());


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
                parentDialogListener.hideProgressDialog();
            }
        });


    }


    public void getQuoteID(String authtoken) {

        //parentDialogListener.showProgressDialog();
        BasicRequest apiService =
                UpgradedBasicBuilder.getClient().create(BasicRequest.class);

        Map<Object, Object> mainparam = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();

        map1.put("customer_id", MySharedPreferenceClass.getMyUserId(getContext()));


        Call<String> call = apiService.getQuotebyId("Bearer" + " " + authtoken, map1);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body() != null) {


                    MySharedPreferenceClass.setQuoteId(v.getContext(), response.body().toString());


                    upgradeaddtocart(response.body().toString(), authtoken);


                    Log.d("Response------->>>>", response.body().toString());


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, t.toString());

                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
                parentDialogListener.hideProgressDialog();
            }
        });


    }


    public void getUpgardeRelatedproduct() {
        //parentDialogListener.hideProgressDialog();
        // parentDialogListener.showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("pid", pid);
        Call<CatagoryList> call;

        if (Locale.equals("ar")) {
            call = apiService.getUpgradedRelatedProduct(Constants.UPGRADED_HOST_LINK + Constants.UPGRADED_BASE_LINK_ARABIC + "getrelatedproduct/" + pid);
        } else {
            call = apiService.getUpgradedRelatedProduct(Constants.UPGRADED_HOST_LINK + Constants.UPGRADED_BASE_LINK_ENGLISH + "getrelatedproduct/" + pid);
        }

        call.enqueue(new Callback<CatagoryList>() {
            @Override
            public void onResponse(Call<CatagoryList> call, Response<CatagoryList> response) {

                if (response.body() != null && response.body().response != null) {
                    if (response.body().response.equals("success")) {
                       // parentDialogListener.hideProgressDialog();


                        if (response.body().data.size() > 0) {
                            initrelatedProductrecycler(response.body().data);

                        }


                    } else {
                        relatedproducttext.setVisibility(View.GONE);
                        parentDialogListener.hideProgressDialog();


                    }


                }
            }

            @Override
            public void onFailure(Call<CatagoryList> call, Throwable t) {

                Log.e(TAG, t.toString());
                relatedproducttext.setVisibility(View.GONE);
                Toast.makeText(getContext(), Locale == "ar" ? "حدث خطأ - يرجي اعادة المحاولة" :
                        "An error occurred - please try again", Toast.LENGTH_LONG).show();
                parentDialogListener.hideProgressDialog();
                //Toast.makeText(getContext(), "No Items Found", Toast.LENGTH_LONG).show();
            }
        });

        getWishListFlag();
    }


    public void initrelatedProductrecycler(final List<ListItems> cat7) {

        relatedproduct.setHasFixedSize(true);
        relatedproduct.setNestedScrollingEnabled(false);

        relatedproduct.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false));

        mAdapter7 = new RelatedProductAdapter(mContext, cat7,


                new ViewListener() {
                    @Override
                    public void onClick(int position, View view) {
                        int id = view.getId();


                        switch (id) {

                            case R.id.man_image://button for message
                                Fragment prFrag = new ProductDetail();
                                Bundle databund = new Bundle();
                                databund.putString("sku", cat7.get(position).sku);
                                databund.putString("type", cat7.get(position).type);
                                databund.putString("pid", cat7.get(position).id);

                                databund.putString("size", cat7.get(position).size);
                                databund.putString("color", cat7.get(position).color);
                                databund.putString("color_name", cat7.get(position).color_name);
                                databund.putSerializable("catagoryobject", cat7.get(position));
                                prFrag.setArguments(databund);


                                getFragmentManager()
                                        .beginTransaction().addToBackStack(null)
                                        .replace(ProductDetail.this.getId(), prFrag)
                                        .commit();

                                break;
                        }
                    }
                }


        );
        relatedproduct.setAdapter(mAdapter7);


    }


    public void moengageAddtoCartEvent(String price, String sku, String quantity) {


        Properties properties = new Properties();

        properties.addAttribute("name", productname.getText().toString())

                .addAttribute("sku", sku)

                .addAttribute("type", type)
                .addAttribute("category Id", category_id)
                .addAttribute("category name", category_name)

                .addAttribute("price", newPrice.getText().toString())

                .addAttribute("currency", MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()))
                .addAttribute("in_stock", true)

                .addAttribute("time added to cart", new Date())
                .addAttribute("quantity", quantity);


        MoEHelper.getInstance(getApplicationContext()).trackEvent("Add To Cart", properties);


    }


    public void appsflyer_event_add_to_cart(String price, String sku, String quantity) {
        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put(AFInAppEventParameterName.PRICE, newPrice.getText().toString());
        eventValue.put(AFInAppEventParameterName.CONTENT_ID, sku);
        eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, " ");
        eventValue.put(AFInAppEventParameterName.CURRENCY, MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()));
        eventValue.put(AFInAppEventParameterName.QUANTITY, quantity);
        AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), AFInAppEventType.ADD_TO_CART, eventValue);
    }


    public void firebase_event_add_to_cart(String price, String sku, String quantity) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, sku);
        // bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, catagoryList.category_name);
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, catagoryList != null ? catagoryList.category_name : "");
        bundle.putString(FirebaseAnalytics.Param.QUANTITY, quantity);

        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, productname.getText().toString());
        bundle.putInt(FirebaseAnalytics.Param.VALUE, Integer.parseInt(newPrice.getText().toString().split(" ")[1]));
        bundle.putString(FirebaseAnalytics.Param.PRICE, newPrice.getText().toString().split(" ")[1]);
        //bundle.putString(FirebaseAnalytics.Param.ITEM_LOCATION_ID, Locale.getDefault().getCountry());
        bundle.putString(FirebaseAnalytics.Param.CURRENCY, MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()));
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, bundle);

    }

    public void firebase_view_item(String sku,String productname,String itemid) {

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, itemid);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,productname);
        bundle.putString("sku", sku);

        if(producturl!=null && !producturl.equalsIgnoreCase(" ")) {
            bundle.putString("producturl", producturl);
        }

        bundle.putString("isFrom", previouspage);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);


    }

    public void moengageProductViewEvent(String price, String sku ) {


        Properties properties = new Properties();

        properties.addAttribute("name", productname.getText().toString())

                .addAttribute("sku", sku)

                .addAttribute("type", type)
                .addAttribute("category Id", category_id)
                .addAttribute("category name", category_name)
                .addAttribute("price", newPrice.getText().toString())

                .addAttribute("currency", MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()))


                .addAttribute("time to view product", new Date());



        MoEHelper.getInstance(getApplicationContext()).trackEvent("Product View", properties);

   Log.e("cateid",category_id+" "+category_name);
    }

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

    public void removeFromWishList() {
        parentDialogListener.showProgressDialog();
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
        map1.put("product_id", pid);
        mainparam.put("param", map1);
        Call<AddtoWishList> call;
        if (MySharedPreferenceClass.getMyUserId(getApplicationContext()).equals(" ")) {
            call = apiService.removeFromGuestwishlist(mainparam);

        } else {
            call = apiService.removeFromwishlist(mainparam);
        }


        call.enqueue(new Callback<AddtoWishList>() {
            @Override
            public void onResponse(Call<AddtoWishList> call, Response<AddtoWishList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        parentDialogListener.hideProgressDialog();
                        if (response.body().msg.equals("success") || response.body().response.equals("success")) {
                            if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
                                Toast.makeText(getApplicationContext(), "Product removed from wishlist.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "تمت إزالة المنتج من قائمة الامنيات", Toast.LENGTH_LONG).show();
                            }

                            product_heart.setImageDrawable(v.getContext().getResources().getDrawable(R.drawable.productdetailheart));
                            isaddedtowishList = false;


                            // Update Wishlist Badge Count
                            if (MySharedPreferenceClass.getMyUserId(getApplicationContext()).equals(" ")) {
                                HomeFragmentNewDesign.getInstance().getWishListItemCountforguestUser();
                            } else {
                                HomeFragmentNewDesign.getInstance().getWishListItemCount();
                            }
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
                                Toast.makeText(getApplicationContext(), userMessage, Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getApplicationContext(), userMessage, Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AddtoWishList> call, Throwable t) {

                Log.e(TAG, t.toString());
                if (MySharedPreferenceClass.getSelectedCurrencyName(getApplicationContext()).equals("ar")) {
                    Toast.makeText(getApplicationContext(), "حدث خطأ - يرجي اعادة المحاولة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "An error occurred - please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void openSizeChartBottomSheet(){



            BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
            View sheetView;
       WebView threedpaymentwebview;
        ImageView downarrow;
            if (MySharedPreferenceClass.getChoosenlanguage(getApplicationContext()).equals("en")) {
                sheetView = this.getLayoutInflater().inflate(R.layout.fragment_size_chart_web_view, null);
            } else {
                sheetView = this.getLayoutInflater().inflate(R.layout.fragment_size_chart_web_view_arabic, null);
            }
        threedpaymentwebview = sheetView.findViewById(R.id.sizechartwebview);
            downarrow = sheetView.findViewById(R.id.downarrow);
        parentDialogListener.showProgressDialog();

       threedpaymentwebview.getSettings().setJavaScriptEnabled(true);
       //threedpaymentwebview.setInitialScale(150);
      //threedpaymentwebview.getSettings().setBuiltInZoomControls(true);
       // threedpaymentwebview.getSettings().setDisplayZoomControls(false);
        //threedpaymentwebview.getSettings().setUseWideViewPort(true);
       // threedpaymentwebview.getSettings().setLoadWithOverviewMode(true);

        threedpaymentwebview.clearCache(true);
        threedpaymentwebview.clearHistory();
        threedpaymentwebview.clearFormData();
        threedpaymentwebview.setWebViewClient(new MyBrowser());


        System.out.println("URL is-->>>" + Constants.HOST_LINK + "webservice/index/sizechart?" + "sizechart=" + sizechartposition);
        threedpaymentwebview.loadData(sizecharthtml, "text/html", "UTF-8");
        //threedpaymentwebview.loadUrl(Constants.HOST_LINK + "webservice/index/sizechart?" + "sizechart=" + sizechartposition);
        //threedpaymentwebview.loadUrl("https://upgrade.eoutlet.com//pub//media//4B9B5477-3B4C-4FDA-A8E7-C557583315EE.png");

       downarrow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mBottomSheetDialog.cancel();
    }
});
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
    }
    private class MyBrowser extends WebViewClient {


        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            //parentDialogListener.showProgressDialog();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return super.shouldOverrideUrlLoading(view, url);
/*
            view.loadUrl(url);



            return true;*/
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            parentDialogListener.hideProgressDialog();


        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(getContext(), "Error" + description + errorCode, Toast.LENGTH_SHORT).show();
        }


    }
}





