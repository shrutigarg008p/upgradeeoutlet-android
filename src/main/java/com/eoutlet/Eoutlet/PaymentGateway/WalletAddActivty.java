package com.eoutlet.Eoutlet.PaymentGateway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.activities.ParentActivity;
import com.eoutlet.Eoutlet.activities.ThankyouActivity;
import com.eoutlet.Eoutlet.api.BasicBuilder;
import com.eoutlet.Eoutlet.api.request.BasicRequest;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.GetToken;
import com.eoutlet.Eoutlet.pojo.universalMessage;



import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class WalletAddActivty extends ParentActivity {


    private String totalprice = " ";
    String fortid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_wallet_add_activty);




    Bundle bundle = getIntent().getExtras();

    totalprice = bundle.getString("totalprice");




    //callSdk(fortrequest);
}


  /*  private void callSdk(FortRequest fortrequest) {
        try {
            FortSdk.getInstance().registerCallback(this, fortrequest,
                    Constants.payfortenvironment, 5, fortCallback, new FortInterfaces.OnTnxProcessed() {
                        @Override
                        public void onCancel(Map<String, Object> requestParamsMap, Map<String,
                                Object> responseMap) {

                            send_status_to_server("cancel", " ");
                            //TODO: handle me
                            finish();
                            Log.d("Cancelled ", responseMap.toString());

                        }

                        @Override
                        public void onSuccess(Map<String, Object> requestParamsMap, Map<String,
                                Object> fortResponseMap) {


                            //TODO: handle me
                            try {

                                fortid = fortResponseMap.get("fort_id").toString();
                                Log.i("FortId------>>>>", fortResponseMap.get("fort_id").toString());

                                send_status_to_server("success", fortid);









                            } catch (Exception e) {

                                Log.i("Exception ------>>>>", e.toString());
                                Toast.makeText(getApplicationContext(), "Payment Failed due to not received fortId", Toast.LENGTH_SHORT).show();
                                send_status_to_server("failed", " ");


                            }
                            Log.i("Success ", fortResponseMap.toString());


                        }

                        @Override
                        public void onFailure(Map<String, Object> requestParamsMap, Map<String,
                                Object> fortResponseMap) {
                            //TODO: handle me
                            finish();

                            send_status_to_server("failed", " ");


                            Log.e("Failure ", fortResponseMap.toString());
                        }
                    });
        } catch (Exception e) {
            Log.e("execute Payment", "call FortSdk", e);
        }
    }

*/
    private Map<String, Object> collectRequestMap(String sdkToken) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("command", "PURCHASE");
        requestMap.put("customer_email", MySharedPreferenceClass.getEmail(getBaseContext()));
        requestMap.put("currency", "SAR");


        requestMap.put("amount", String.valueOf((int) Float.parseFloat(totalprice) * 100));

        requestMap.put("language", "ar");
        requestMap.put("merchant_reference", "walletadded");
        requestMap.put("customer_name", MySharedPreferenceClass.getMyUserName(getBaseContext()));

        requestMap.put("eci", "ECOMMERCE");
        requestMap.put("order_description", "DESCRIPTION");

        /* "order_description_android"+"/"+Constants.app_version*/

        requestMap.put("sdk_token", sdkToken);



        return requestMap;
    }
    public void send_status_to_server(final String status, String fortId) {
        MySharedPreferenceClass.setBedgeCount(getApplicationContext(), 0);
        MainActivity.notificationBadge.setVisibility(View.GONE);

        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        //showProgressDialog();

        Map<String, String> map1 = new HashMap<>();
        map1.put("order_id", " ");
        map1.put("status", status);
        map1.put("transaction", fortId);


        Call<universalMessage> call = apiService.sendoredretoserver(map1);
        call.enqueue(new Callback<universalMessage>() {
            @Override
            public void onResponse(Call<universalMessage> call, Response<universalMessage> response) {


                if (response.body() != null) {


                    if (response.body().msg.equals("success")) {
                        hideProgressDialog();



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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {

        } else {

            send_status_to_server("cancel", " ");
           // Toast.makeText(getApplicationContext(),"No Data Found"+data.toString(),Toast.LENGTH_SHORT).show();

        }

    }

}
