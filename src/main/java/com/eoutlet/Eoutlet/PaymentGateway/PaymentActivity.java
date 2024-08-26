package com.eoutlet.Eoutlet.PaymentGateway;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.activities.ParentActivity;
import com.eoutlet.Eoutlet.activities.ThankyouActivity;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.OrderResponse;
import com.eoutlet.Eoutlet.pojo.PayFortData;
import com.eoutlet.Eoutlet.pojo.ViewCartData;
import com.eoutlet.Eoutlet.pojo.universalMessage;
import com.eoutlet.Eoutlet.utility.Constants;
import com.google.gson.Gson;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Dispatcher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

public class PaymentActivity extends ParentActivity {

    String hashkey;

    private final Gson gson = new Gson();
    private static Retrofit retrofit = null;
    private static Dispatcher dispatcher = null;
    private String coupencode;
    String fortid;
    private final static String TEST_TOKEN_URL = "https://sbpaymentservices.payfort.com/FortAPI/paymentApi";

    String deviceId = "", sdkToken = "";

    private final static String KEY_MERCHANT_IDENTIFIER = "merchant_identifier";
    private final static String KEY_SERVICE_COMMAND = "service_command";
    private final static String KEY_DEVICE_ID = "device_id";
    private final static String KEY_LANGUAGE = "language";
    private final static String KEY_ACCESS_CODE = "access_code";
    private final static String KEY_SIGNATURE = "signature";
    public final static String AUTHORIZATION = "AUTHORIZATION";
    public final static String PURCHASE = "PURCHASE";
    private final static String SDK_TOKEN = "SDK_TOKEN";
    private String totalprice = " ";
    private String totalpricewithouttax = " ";
    private ArrayList<ViewCartData> getdata = new ArrayList<>();

   /*  private final static String SHA_REQUEST_PHRASE = "TESTSHAIN";//staging
    private final static String MERCHANT_IDENTIFIER = "uRelyHKU";//stagging
    String ACCESS_CODE = "Se8GBcDzHUIqpRvyzkmT";// Stagging
    public final static String SHA_RESPONSE_PHRASE = "asdadseeerg";//staging*/

    // String payfortenvironment = FortSdk.ENVIRONMENT.TEST;


    // String payfortenvironment = FortSdk.ENVIRONMENT.PRODUCTION;


    String ACCESS_CODE = "tRqsph44b6b0YOkiwLpL";//Live
    private final static String MERCHANT_IDENTIFIER = "VwaZsGPf";  //Live
    private final static String SHA_REQUEST_PHRASE = "HhpttQZXa6KakNY6pzj64VK3XRY5bFPxHj6";//Live
    public final static String SHA_RESPONSE_PHRASE = "AVj74KbkeBtbYMPBT8gEUUTn82VRLx2mngV";//Live


    String LANGUAGE_TYPE = "ar";
    private final static String SHA_TYPE = "SHA-256";


    public final static String CURRENCY_TYPE = "SAR";


    String shippingmethod, orderid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);


        deviceId = " ";


        Log.d("DeviceId ", deviceId);
        Bundle bundle = getIntent().getExtras();
        totalprice = bundle.getString("price");
        shippingmethod = bundle.getString("shippingmethod");

        orderid = bundle.getString("oredrId");
        coupencode = bundle.getString("couponcode");
        getdata = (ArrayList<ViewCartData>) bundle.getSerializable("mainData");




    }


    public String getTokenParams() {
        JSONObject jsonObject = new JSONObject();
        try {
            String device_id = " ";
            String concatenatedString = SHA_REQUEST_PHRASE +

                    KEY_ACCESS_CODE + "=" + ACCESS_CODE +
                    KEY_DEVICE_ID + "=" + device_id +
                    KEY_LANGUAGE + "=" + LANGUAGE_TYPE +
                    KEY_MERCHANT_IDENTIFIER + "=" + MERCHANT_IDENTIFIER +
                    KEY_SERVICE_COMMAND + "=" + SDK_TOKEN +
                    SHA_REQUEST_PHRASE;

            jsonObject.put(KEY_SERVICE_COMMAND, SDK_TOKEN);
            jsonObject.put(KEY_MERCHANT_IDENTIFIER, MERCHANT_IDENTIFIER);
            jsonObject.put(KEY_ACCESS_CODE, ACCESS_CODE);
            String signature = getSignatureSHA256(concatenatedString);
            jsonObject.put(KEY_SIGNATURE, signature);
            jsonObject.put(KEY_DEVICE_ID, device_id);
            jsonObject.put(KEY_LANGUAGE, LANGUAGE_TYPE);

            Log.e("concatenatedString", concatenatedString);
            Log.e("signature", signature);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("JsonString", String.valueOf(jsonObject));
        return String.valueOf(jsonObject);
    }


    private class GetTokenFromServer extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... postParams) {
            String response = "";
            try {
                HttpURLConnection conn;
                URL url = new URL(postParams[0].replace(" ", "%20"));
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("content-type", "application/json");

                String str = getTokenParams();
                byte[] outputInBytes = str.getBytes("UTF-8");
                OutputStream os = conn.getOutputStream();
                os.write(outputInBytes);
                os.close();
                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = conn.getInputStream();
                    response = convertStreamToString(inputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            hideProgressDialog();
            Log.e("Response", response + "");
            try {

                try {
                    PayFortData payFortData = gson.fromJson(response, PayFortData.class);
                    if (!TextUtils.isEmpty(payFortData.sdkToken)) {
                        sdkToken = payFortData.sdkToken;

                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //callSdk(fortrequest);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String convertStreamToString(InputStream inputStream) {
        if (inputStream == null)
            return null;
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream), 1024);
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    private static String getSignatureSHA256(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(SHA_TYPE);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            return String.format("%0" + (messageDigest.length * 2) + 'x', new BigInteger(1, messageDigest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


   /* private void callSdk(FortRequest fortrequest) {
        try {
            FortSdk.getInstance().registerCallback(this, fortrequest,
                    Constants.payfortenvironment, 5, fortCallback, new FortInterfaces.OnTnxProcessed() {
                        @Override
                        public void onCancel(Map<String, Object> requestParamsMap, Map<String,
                                Object> responseMap) {

                            send_status_to_server("cancel", " ");
                            //TODO: handle me
                            //finish();
                            Log.d("Cancelled ", responseMap.toString());
                        }

                        @Override
                        public void onSuccess(Map<String, Object> requestParamsMap, Map<String,
                                Object> fortResponseMap) {


                            //TODO: handle me
                            try {
                                //JSONObject jobj = new JSONObject(fortResponseMap.toString());
                                fortid = fortResponseMap.get("fort_id").toString();
                                Log.i("FortId------>>>>", fortResponseMap.get("fort_id").toString());

                                send_status_to_server("success", fortid);



                                if(fortResponseMap.get("token_name")!=null){


                                    MySharedPreferenceClass.setSelecteTokenName(getApplicationContext(),fortResponseMap.get("token_name").toString());


                                }

                                else
                                {

                                    MySharedPreferenceClass.setSelecteTokenName(getApplicationContext()," ");

                                }




                                //placeorder();

                            } catch (Exception e) {

                                Log.i("Exception ------>>>>", e.toString());
                                Toast.makeText(getApplicationContext(), "Payment Failed due to not received fortId", Toast.LENGTH_SHORT).show();
                                send_status_to_server("failed", " ");


                            }
                            Log.i("Success ", fortResponseMap.toString());

                            // Intent in = new Intent(getApplicationContext(), ThankyouActivity.class);

                            //getApplicationContext().startActivity(in);
                            //finish();
                        }

                        @Override
                        public void onFailure(Map<String, Object> requestParamsMap, Map<String,
                                Object> fortResponseMap) {
                            //TODO: handle me

                            send_status_to_server("failed", " ");


                            Log.e("Failure ", fortResponseMap.toString());
                        }
                    });
        } catch (Exception e) {
            Log.e("execute Payment", "call FortSdk", e);
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {

        } else {

            send_status_to_server("cancel", " ");
            //Toast.makeText(getApplicationContext(),"No Data Found"+data.toString(),Toast.LENGTH_SHORT).show();

        }

    }

    private Map<String, Object> collectRequestMap(String sdkToken) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("command", "PURCHASE");
        requestMap.put("customer_email", MySharedPreferenceClass.getEmail(getBaseContext()));
        requestMap.put("currency", "SAR");
        requestMap.put("amount", String.valueOf((int) Float.parseFloat(totalprice) * 100));
        requestMap.put("language", "ar");
        requestMap.put("merchant_reference", orderid);
        requestMap.put("customer_name", MySharedPreferenceClass.getMyUserName(getBaseContext()));
        //requestMap.put("customer_ip", "172.150.16.10");
        //requestMap.put("payment_option", "VISA");
        // requestMap.put("payment_option", "MASTERCARD");
        requestMap.put("eci", "ECOMMERCE");
        requestMap.put("order_description", "DESCRIPTION");

       /* "order_description_android"+"/"+Constants.app_version*/

        requestMap.put("sdk_token", sdkToken);


        if( MySharedPreferenceClass.getSelectedTokenName(getBaseContext())!=" ") {
            requestMap.put("token_name", MySharedPreferenceClass.getSelectedTokenName(getBaseContext()));
        }

        return requestMap;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }


    public void placeorder() {

        HashMap<Object, Object> initialorder = new HashMap<>();
        initialorder.put("user_id", MySharedPreferenceClass.getMyUserId(this));
        initialorder.put("payment_method", "payfort_fort_cc");
        initialorder.put("shipping_method", shippingmethod);
        initialorder.put("devicetype", "android");
        initialorder.put("coupon", " ");

        HashMap<Object, Object> payment = new HashMap<>();
        payment.put("firstname", MySharedPreferenceClass.getMyFirstNamePref(this));
        payment.put("lastname", MySharedPreferenceClass.getMyLastNamePref(this));
        payment.put("city", MySharedPreferenceClass.getCityName(this));

        payment.put("country_id", "SA");
        payment.put("region", "Riyadh");
        payment.put("telephone", MySharedPreferenceClass.getMobileNumber(this));

        HashMap<String, String> street = new HashMap<>();
        street.put("0", MySharedPreferenceClass.getStreetname(this));
        street.put("1", " ");
        payment.put("street", street);

        initialorder.put("payment", payment);

        HashMap<Object, Object> shipping = new HashMap<>();

        shipping.put("firstname", MySharedPreferenceClass.getAdressname(this));
        shipping.put("lastname", MySharedPreferenceClass.getAdressname(this));
        shipping.put("street", street);
        shipping.put("city", MySharedPreferenceClass.getCityName(this));
        shipping.put("country_id", MySharedPreferenceClass.getCountryId(this));
        shipping.put("region", MySharedPreferenceClass.getCountryId(getApplicationContext()));
        shipping.put("telephone", MySharedPreferenceClass.getAdressphone(this));

        initialorder.put("shipping", shipping);


        HashMap<String, String> products = new HashMap<>();


        ArrayList<Object> mainarraylist = new ArrayList<>();


        for (int i = 0; i < getdata.size(); i++) {

            HashMap<String, Object> superattribute = new HashMap<>();
            //HashMap<String,String>  size = new HashMap<>();
            HashMap<String, String> colorandsize = new HashMap<>();

            if (getdata.get(i).option.size() > 0) {
                colorandsize.put(getdata.get(i).option.get(0).color.id.toString(), getdata.get(i).option.get(0).color.value.toString());
                colorandsize.put(getdata.get(i).option.get(0).size.id.toString(), getdata.get(i).option.get(0).size.value.toString());
                superattribute.put("super_attribute", colorandsize);

            }

            superattribute.put("product_id", getdata.get(i).productId);
            superattribute.put("qty", getdata.get(i).qty);

            mainarraylist.add(superattribute);

        }


        initialorder.put("products", mainarraylist);
        initialorder.put("transaction_id", fortid);


        showProgressDialog();

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> query = new HashMap<>();
        query.put("customer_id", MySharedPreferenceClass.getMyUserId(this));


        Call<OrderResponse> call = apiService.createorder(initialorder);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {


                if (response.body() != null) {


                   /* if (response.body().msg.equals("success")) {
                        hideProgressDialog();
                        //MySharedPreferenceClass.setAddressname(getContext(),null);
                        // MySharedPreferenceClass.setCityname(getContext(), null);
                        // MySharedPreferenceClass.setStreetName(getContext(), null);
                        // MySharedPreferenceClass.setCountryname(getContext(), null);
                        // MySharedPreferenceClass.setCountryId(getContext(), null);

                        MySharedPreferenceClass.setBedgeCount(getApplicationContext(), 0);
                        Toast.makeText(getApplicationContext(), "Order Placed Successful", Toast.LENGTH_LONG).show();

                        Intent in = new Intent(PaymentActivity.this, ThankyouActivity.class);
                        in.putExtra("orderId", response.body().orderId);

                        in.putExtra("totalvalue",Integer.valueOf(totalprice));
                        startActivity(in);
                        finish();


                    } else {
                        hideDialog();
                        Toast.makeText(getApplicationContext(), response.body().msg, Toast.LENGTH_LONG).show();

                    }*/


                }


            }


            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getApplicationContext(), "Something Went Wrong,please try again", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void send_status_to_server(final String status, String fortId) {
        MySharedPreferenceClass.setBedgeCount(getApplicationContext(), 0);
        MainActivity.notificationBadge.setVisibility(View.GONE);

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        showProgressDialog();

        Map<String, String> map1 = new HashMap<>();
        map1.put("order_id", orderid);
        map1.put("status", status);
        map1.put("transaction", fortId);


        Call<universalMessage> call = apiService.sendoredretoserver(map1);
        call.enqueue(new Callback<universalMessage>() {
            @Override
            public void onResponse(Call<universalMessage> call, Response<universalMessage> response) {


                if (response.body() != null) {


                    if (response.body().msg.equals("success")) {
                        hideProgressDialog();
                        //MySharedPreferenceClass.setAddressname(getContext(),null);
                        // MySharedPreferenceClass.setCityname(getContext(), null);
                        // MySharedPreferenceClass.setStreetName(getContext(), null);
                        // MySharedPreferenceClass.setCountryname(getContext(), null);
                        // MySharedPreferenceClass.setCountryId(getContext(), null);


                        Intent in = new Intent(PaymentActivity.this, ThankyouActivity.class);
                        in.putExtra("orderId", orderid);
                        in.putExtra("statusflag", status);
                        in.putExtra("coupencode",coupencode);
                        startActivity(in);
                        finish();


                    } else {
                        hideDialog();
                        Toast.makeText(getApplicationContext(), response.body().msg, Toast.LENGTH_LONG).show();

                    }


                }


            }


            @Override
            public void onFailure(Call<universalMessage> call, Throwable t) {
                hideProgressDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getApplicationContext(), "Something Went Wrong,please try again", Toast.LENGTH_LONG).show();
            }
        });


    }
}
