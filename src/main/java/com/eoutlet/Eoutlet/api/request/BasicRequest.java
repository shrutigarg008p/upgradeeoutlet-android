package com.eoutlet.Eoutlet.api.request;


import com.eoutlet.Eoutlet.pojo.*;

import com.eoutlet.Eoutlet.pojo.getNewHomeScreenData.GetNewHomeScreenData;
import com.eoutlet.Eoutlet.utility.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


public interface BasicRequest {

    @GET(Constants.BASE_LINK + "guesttoken.php")
    Call<GuestUser> getGuestToken();

    /*https://upgrade.eoutlet.com/rest/V1/directory/currency*/
    @GET("rest/V1/directory/currency")
    Call<GetCurrencyDetail> getCurrency();


    @POST("rest/en/V1/api/setquotecurrency")
    Call<CurrencysetResponse> postUserCurrencySelection(@HeaderMap Map<String,String> headers, @Body Map<Object, Object> query);

    @POST("rest/ar/V1/api/setquotecurrency")
    Call<CurrencysetResponse> postUserCurrencySelectionforarabic(@HeaderMap Map<String,String> headers, @Body Map<Object, Object> query);


    @GET(Constants.UPGRADED_BASE_LINK_ARABIC + "customguesttoken")
    Call<GuestUser> getUpgradedGuestToken();

    @POST(Constants.UPGRADED_BASE_LINK + "delmyaccnt")
    Call<DeleteCustomerAccount> deleteUserAccount(@Header("Authorization") String contentRange,
                                      @Body Map<Object, Object> query);


    @GET(Constants.BASE_LINK + "customertoken.php")
    Call<GuestUser> getGuestTokenforthankyou(@QueryMap Map<String, String> map1);



    @GET(Constants.BASE_LINK + "categoryapi.php")
    Call<GetHorizantalCategory> getcatagoryDetailHome();




    @GET
    Call<ColorandSizeDetail> getupgradedColorandSize(@Url String url);
/*http://upgrade.eoutlet.com/rest/en/V1/api/categorylist*/
    @GET(Constants.UPGRADED_BASE_LINK_ARABIC + "categorylist/")
    Call<CatagoryCollection> getupgradedcatagoryDetailArabic();

    @GET(Constants.UPGRADED_BASE_LINK_ENGLISH + "categorylist/")
    Call<CatagoryCollection> getupgradedcatagoryDetailEnglish();
    /*http://upgrade.eoutlet.com/rest/en/V1/api/categorylist*/
    @GET(Constants.UPGRADED_BASE_LINK_ARABIC + "categorylist/")
    Call<UpgradeCatgoryresponse> getNewupgradedcatagoryDetailArabic();

    @GET(Constants.UPGRADED_BASE_LINK_ENGLISH + "categorylist/")
    Call<UpgradeCatgoryresponse> getNewupgradedcatagoryDetailEnglish();


    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "productlistapi.php")
    Call<CatagoryList> getCataGoryList(@FieldMap Map<String, String> fields);


    // @GET(Constants.UPGRADED_BASE_LINK_ARABIC + "productlist")
    @GET
    Call<CatagoryList> getupgradedCataGoryListArabic(@Url String url);


    // @GET(Constants.UPGRADED_BASE_LINK_ENGLISH + "productlist")
    @GET
    Call<CatagoryList> getupgradedCataGoryListEnglish(@Url String url);




    @GET
    Call<ProductInfoResponse> getupgradedCataGoryDetail(@Url String url);







    @POST("rest/en/V1/carts/mine/items")
    Call<AddtoCartResponse> upgradedaddtocart(@HeaderMap Map<String,String> headers, @Body Map<Object, Object> query);

    @GET(Constants.BASE_LINK + "search.php")
    Call<CatagoryList> getSearchResult(@QueryMap Map<String, String> query);

    @GET
    Call<Upgradedsearchresult> getUpgradedSearchResult(@Url String url);

    @GET(Constants.BASE_LINK + "getcustomerwallet.php")
    Call<WalletHistory> getwallethistory(@QueryMap Map<String, String> query);

    @GET
    Call<UpgradedWalletHistory> getUpgradedwallethistory(@Url String url);



    @GET(Constants.BASE_LINK + "getcurrentversion.php")
    Call<AppCurrentVersion> getLatestAppVersion();


    @GET(Constants.UPGRADED_BASE_LINK + "getcurrentversion")
    Call<GetCategoryCode> getUpgradedLatestAppVersion();


    @GET(Constants.BASE_LINK + "viewcart.php")
    Call<ViewCart1> getCartDetail(@QueryMap Map<String, String> query);


    @GET
    Call<UpgradedCartItems> getUpgradedCartDetailforguestuser(@Url String url);
    @GET
    Call<UpgradedCartItems> getUpgradedCartDetail(@Url String url);



/*http://upgrade.eoutlet.com/rest/ar/V1/api/getrelatedproduct/432*/
@GET
    Call<CatagoryList> getUpgradedRelatedProduct(@Url String url);








    @GET
    Call<BrandName> getHomeBrandDetail(@Url String url);



    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "signup.php")
    Call<SignUpResponse> getSignUp(@FieldMap Map<String, String> map1);


    @POST("rest/en/V1/integration/admin/token")
    Call<String> createtokenbyId(@Body Map<Object, Object> query);
    @POST("rest/ar/V1/integration/admin/token")
    Call<String> createtokenbyIdArabic(@Body Map<Object, Object> query);
   /* @POST("rest/V1/integration/customer/token")
    Call<String> createcustomertoken(@Body Map<Object, Object> query);*/


    @POST("/paymentsurcharge/checkout/applysurcharge") //https://upgrade.eoutlet.com/paymentsurcharge/checkout/applysurcharge
    Call<ClearCodSelection> clearcodselection(@Body Map<Object, Object> query);


    @POST("rest/en/V1/api/getcustomertoken")
    Call<String> createcustomertoken(@Body Map<Object, Object> query);


    @POST("rest/ar/V1/api/getcustomertoken")
    Call<String> createcustomertokeninarabic(@Body Map<Object, Object> query);


    @POST("/rest/en/V1/api/addmoneytowallet")
    Call<universalMessage2> doupgradedwalletOrder(@Body Map<Object, Object> query);


    @POST("/rest/V1/api/sendstcotp")
    Call<OrderResponseStc> initiateStcOrder(@Body Map<Object, Object> query);


    @POST("/rest/V1/api/checkoutcardpayment")
    Call<UpgardeCheckoutOrderResponse> initiateCheckoutOrder(@Body Map<Object, Object> query);

    @POST("/rest/en/V1/api/paypalpayment")
    Call<PaypalPaymentResponse> dopaypalpayment(@Body Map<Object, Object> query);

    @POST("/rest/en/V1/api/walletpayment")
    Call< WalletResponse> doUpgradedpaymentwithwallet(@Body Map<Object, Object> query);
    @POST("/rest/ar/V1/api/walletpayment")
    Call< WalletResponse> doUpgradedpaymentwithwalletinArabic(@Body Map<Object, Object> query);

    @POST("/rest/en/V1/api/selectwallet")
    Call<ApplyWalletResult> dowalletupdate(@HeaderMap Map<String,String> headerse,@Body Map<Object, Object> query);
    @POST("/rest/ar/V1/api/selectwallet")
    Call<ApplyWalletResult> dowalletupdateforarabic(@HeaderMap Map<String,String> headerse,@Body Map<Object, Object> query);
    //https://upgrade.eoutlet.com/rest/en/V1/paymentsurcharge/fee/

    @POST("/rest/en/V1/paymentsurcharge/fee")
    Call<String> getcodecharges(@Body Map<Object, Object> query);
    @POST("/rest/ar/V1/paymentsurcharge/fee")
    Call<String> getcodechargesinarabic(@Body Map<Object, Object> query);


    @GET
    Call<ArrayList<TamaraPaymentType>> getTamaraPaymentsTypes(@Header("Authorization") String contentRange,@Url String url);

    @GET
    Call<GetTamaraCheckoutResponse> doTamaraCheckoutOrder(@HeaderMap Map<String,String> headerse,@Url String url);


    @GET
    Call<ArrayList<TamaraPaymentType>> getTamaraOrderId(@Header("Authorization") String contentRange,@Url String url);

    @POST("/rest/V1/api/checkoutcardresponse")
    Call<WalletPaymentStatus> UpgradeGetStatusAPi(@Body Map<Object, Object> query);

    @POST("/rest/en/V1/api/paypalresponse")
    Call<WalletPaymentStatus> UpgradeGetStatusAPiforPaypal(@Body Map<Object, Object> query);



    @POST("rest/en/V1/carts/mine/payment-information")
    Call<String> createcustomerOrder(@HeaderMap Map<String,String> headerse,@Body Map<String, Object> query);


    @POST("rest/ar/V1/carts/mine/payment-information")
    Call<String> createcustomerOrderforarabic(@HeaderMap Map<String,String> headerse,@Body Map<String, Object> query);


    @POST("rest/ar/V1/carts/mine/payment-information")
    Call<String> createcustomerOrderforarbic(@HeaderMap Map<String,String> headerse,@Body Map<String, Object> query);



    @POST("rest/en/V1/carts/mine/payment-information")
    Call<String> createcustomerOrderforwallet(@HeaderMap Map<String,String> headerse,@Header("walletmethod") String contentRange1,@Body Map<String, Object> query);

    @POST("rest/ar/V1/carts/mine/payment-information")
    Call<String> createcustomerOrderforwalletforarabic(@HeaderMap Map<String,String> headerse,@Header("walletmethod") String contentRange1,@Body Map<String, Object> query);


    @GET
    Call<GetOrderdetail> getOrderDetail(@Header("Authorization") String contentRange, @Url String url);
/*https://upgrade.eoutlet.com/rest/V1/api/codpayment*/

    @POST(Constants.UPGRADED_BASE_LINK_ENGLISH + "codpayment")
    Call<GetOrderdetailforCod> getOrderDetailforCod(@HeaderMap Map<String,String> headers ,@Body Map<Object, Object> query);

    @POST(Constants.UPGRADED_BASE_LINK_ARABIC + "codpayment")
    Call<GetOrderdetailforCod> getOrderDetailforCodforArbic(@HeaderMap Map<String,String> headers ,@Body Map<Object, Object> query);


    @POST("rest/V1/carts/mine")
    Call<String> getQuotebyId(@Header("Authorization") String contentRange, @Body Map<Object, Object> query);
    @GET
    Call<String> getWishListFlag(@Url String url);


    @POST("https://api-sandbox.tamara.co/checkout")
    Call<TamaraPaymentUrlResponse> getTamaraCheckoutUrl(@Header("Authorization") String contentRange, @Body Map<Object, Object> query);

    @POST("https://upgrade.eoutlet.com/rest/V1/api/tamarapayment")
    Call<TamaraOrderResponse > sendTamarSatustoWeb( @Body Map<Object, Object> query);

    @POST(Constants.UPGRADED_BASE_LINK_ARABIC + "createaccount/")
    Call<SignUpResponse> getUpgradedSignUp(@Body Map<Object, Object> map1);



    @POST(Constants.UPGRADED_BASE_LINK_ENGLISH + "createaccount/")
    Call<SignUpResponse> getUpgradedSignUpEnglish(@Body Map<Object, Object> map1);

    /*https://upgrade.eoutlet.com/rest/V1/api/addtowishlist*/
    @POST(Constants.UPGRADED_BASE_LINK_ENGLISH + "addtowishlist")
    Call<AddtoWishList> addtowishlist(@Body Map<Object, Object> map1);

    /*https://upgrade.eoutlet.com/rest/V1/api/addtowishlist*/
    @POST(Constants.UPGRADED_BASE_LINK_ENGLISH + "addguestwishlist")
    Call<AddtoWishList> addtowishlistforGuestUser(@Body Map<Object, Object> map1);
    /*https://upgrade.eoutlet.com/rest/V1/api/addtowishlist*/


    @POST(Constants.UPGRADED_BASE_LINK_ENGLISH + "untickwishlist")
    Call<AddtoWishList> removeFromwishlist(@Body Map<Object, Object> map1);
    @POST(Constants.UPGRADED_BASE_LINK_ENGLISH + "untickguestwishlist")
    Call<AddtoWishList> removeFromGuestwishlist(@Body Map<Object, Object> map1);
    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "getpayfortsdktoken.php")
    Call<GetToken> getTokenResponse(@FieldMap Map<String, String> map1);

    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "clearcart.php")
    Call<CancelOrderListResponse> clearAllItems(@FieldMap Map<String, String> map1);
    // get shipping and delivery policy
    @GET
    Call<ShippingAndDeliveryResponse> getShippingAndDelivery(@Url String url);




    // get exchange and return policy
    @GET
    Call<ExchangeAndReturnResponse> getExchangeAndReturn(@Url String url);

    // get about us details
    @GET
    Call<AboutUsResponse> getAboutUs(@Url String url);
    // get about us details
    @GET
    Call<AboutUsResponse> getMergeWishList(@Url String url);

    @POST(Constants.UPGRADED_HOST_LINK + "rest/V1/api/changepassword")
    Call<ResetPasswordresponse> resetnewPassword(@Body Map<Object, Object> map1);


    @DELETE
    Call<Boolean> upgradedeleteItemfromCart(@Header("Authorization") String contentRange ,@Url String url);

    @GET
    Call<Giftwrapdetail> getGiftWraDetails(@Header("Authorization") String contentRange , @Url String url);


/*https://upgrade.eoutlet.com/rest/V1/carts/mine/items/305*/
    @PUT
    Call<UpgradeQuantity> UpgradedchangequantityfromCart(@Header("Authorization") String contentRange , @Body Map<Object, Object> map, @Url String url);
   /*  @POST("/rest/en/V1/carts/mine/mpgiftwrap-update-wrap")*/


    @POST("/rest/en/V1/carts/mine/mpgiftwrap-update-wrap")
    Call<UpgradeQuantity> UpgradedGiftWrap(@HeaderMap Map<String,String> headers , @Body Map<Object, Object> map);

    @POST("/rest/ar/V1/carts/mine/mpgiftwrap-update-wrap")
    Call<UpgradeQuantity> UpgradedGiftWrapArabic(@HeaderMap Map<String,String> headers , @Body Map<Object, Object> map);


    @PUT
    Call<UpgradeQuantity> UpgradedchangequantityfromCartforguestUser(@Header("Authorization") String contentRange , @Body Map<Object, Object> map, @Url String url);




    @POST(Constants.UPGRADED_HOST_LINK +"rest/en/V1/api/" + "forgetpassword")
    Call<ResetEmailPasswordresponse> upgradedforGotPassword(@Body HashMap<Object, Object> map1);
    @POST(Constants.UPGRADED_HOST_LINK +"rest/ar/V1/api/" + "forgetpassword")
    Call<ResetEmailPasswordresponse> upgradedforGotPasswordarabic(@Body HashMap<Object, Object> map1);
    @FormUrlEncoded
    @POST("paymentApi")
    Call<SignUpResponse> getToken(@FieldMap Map<String, String> map1);

    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "updateprofile.php")
    Call<ChangePassword> saveUpdateInfo(@FieldMap Map<String, String> map1);


    @POST(Constants.UPGRADED_BASE_LINK + "updateprofile")
    Call<ChangePassword> saveUpgardeUpdateInfo(@Body Map<Object,Object> map1);


    @POST(Constants.UPGRADED_BASE_LINK + "changepassword")
    Call<ChangePasswordInside> saveNewPassword(@Body Map<Object,Object> map1);


    @GET(Constants.BASE_LINK + "addresslist.php?")
    Call<AddressList> getAddressInfo(@QueryMap Map<String, String> map1);


    @POST(Constants.UPGRADED_BASE_LINK + "stccartretain")
    Call<StcRetainApi> getRetainorderfromStc(@Body Map<Object, Object> map1);


    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "addaddress.php")
    Call<SaveAddress> saveAddress(@FieldMap Map<String, String> map1);

    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "deleteaddress.php")
    Call<ChangePassword> deleteAddress(@FieldMap Map<String, String> map1);



    @POST(Constants.UPGRADED_BASE_LINK_ENGLISH + "customerlogin")
    Call<UpgradeLoginResponse> doupgradeLoginforEnglish(@Body Map<Object, Object> query);

    @POST(Constants.UPGRADED_BASE_LINK_ARABIC + "customerlogin")
    Call<UpgradeLoginResponse> doupgradeLogin(@Body Map<Object, Object> query);


    @POST("/rest/en/V1/carts/mine/estimate-shipping-methods")
    Call<List<GetShippingMethod>> getUpgradedShippingMethod( @HeaderMap Map<String,String> headers,@Body Map<Object, Object> query);

    @POST("/rest/ar/V1/carts/mine/estimate-shipping-methods")
    Call<List<GetShippingMethod>> getUpgradedShippingMethodArabic(@HeaderMap Map<String,String> headers,@Body Map<Object, Object> query);




    @POST("/rest/en/V1/carts/mine/shipping-information")
    Call<GetPaymentInfo> getUpgradedPaymentMethod(@HeaderMap Map<String,String> headers, @Body Map<Object, Object> query);

    @POST("/rest/ar/V1/carts/mine/shipping-information")
    Call<GetPaymentInfo> getUpgradedPaymentMethodArabic(@HeaderMap Map<String,String> headers, @Body Map<Object, Object> query);


    @GET(Constants.UPGRADED_BASE_LINK_ENGLISH + "countrylist")
    Call<GetCountryCode> getCountryDetailEnglish();

    @GET(Constants.UPGRADED_BASE_LINK_ARABIC + "countrylist")
    Call<GetCountryCode> getCountryDetailArabic();

    @POST(Constants.UPGRADED_BASE_LINK + "getprofile")
    Call<GetUserProfile> getUpgradedUserProfile(@Body Map<Object, Object> map1);


    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "deviceregister.php")
    Call<LoginResponse> deviceregister(@FieldMap Map<String, String> body);


    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "addmoney.php")
    Call<AddMoneyResponse> addmoney(@Field("amount") String amount, @Field("customer_id") String customer_id, @Field("remember_me") String remember_me, @Field("card_detail[number]") String number, @Field("card_detail[name]") String name, @Field("card_detail[expiry]") String expiry, @Field("card_detail[securitycode]") String securitycode);

    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "addmoneywithcheckout.php")
    Call<WalletOrderResponse> addmoneywithcheckout(@Field("amount") String amount, @Field("paymentmethod") String paymentmethod, @Field("token_name") String token_name, @Field("customer_id") String customer_id, @Field("remember_me") String remember_me, @Field("card_detail[number]") String number, @Field("card_detail[name]") String name, @Field("card_detail[expiry]") String expiry, @Field("card_detail[securitycode]") String securitycode);


    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "addmoneywithcheckout.php")
    Call<WalletOrderResponse> addmoneyfromsavedcardwithcheckout(@Field("amount") String amount, @Field("paymentmethod") String paymentmethod, @Field("token_name") String token_name, @Field("customer_id") String customer_id, @Field("remember_me") String remember_me, @Field("card_detail[number]") String number, @Field("card_detail[name]") String name, @Field("card_detail[expiry]") String expiry, @Field("card_detail[securitycode]") String securitycode);

    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "addmoney.php")
    Call<AddMoneyResponse> addmoneyfromsavedcard(@Field("amount") String amount, @Field("token_name") String tokenname, @Field("customer_id") String customer_id, @Field("remember_me") String remember_me, @Field("card_detail[number]") String number, @Field("card_detail[name]") String name, @Field("card_detail[expiry]") String expiry, @Field("card_detail[securitycode]") String securitycode);

    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "addmoney.php")
    Call<AddMoneyResponse> addmoneyfromsavedcard(@Field("amount") String amount, @Field("paymentmethod") String paymentmethod, @Field("token_name") String tokenname, @Field("customer_id") String customer_id, @Field("remember_me") String remember_me, @Field("card_detail[number]") String number, @Field("card_detail[name]") String name, @Field("card_detail[expiry]") String expiry, @Field("card_detail[securitycode]") String securitycode);

/*

    @POST(Constants.BASE_LINK + "addmoney.php")
    Call<LoginResponse>addmoney(@Body HashMap<Object, Object> body);
*/


    @POST(Constants.BASE_LINK + "createorderwithcheckout.php")
    Call<OrderResponse> createorder(@Body HashMap<Object, Object> body);


    @POST(Constants.BASE_LINK + "createorderwithcheckout.php")
    Call<CheckoutOrderResponse> createorderwithcheckout(@Body HashMap<Object, Object> body);

    @POST(Constants.BASE_LINK + "createorderwithcheckout.php")
    Call<CheckoutOrderResponse2> createorderwithcheckout2(@Body HashMap<Object, Object> body);

    @POST(Constants.BASE_LINK + "createorder.php")
    Call<OrderResponseCod> createorderCod(@Body HashMap<Object, Object> body);

    //Upgraded Get return item view Details
    @GET
    Call<UpgradeFaqResponse> getFaq(@Url String url);
    @POST(Constants.BASE_LINK + "createorder.php")
    Call<OrderResponseWallet> createorderWallet(@Body HashMap<Object, Object> body);

    @POST(Constants.BASE_LINK + "createorderwithstc.php")
    Call<OrderResponseStc> createorderwithstc(@Body HashMap<Object, Object> body);


    @POST(Constants.BASE_LINK + "stc_response.php")
    Call<StcOrderResponse> dopaymentwithstc(@Body Map<String, String> query);

    @POST("/rest/V1/api/getstcresponse")
    Call<StcOrderResponse> Upgradedopaymentwithstc(@Body Map<Object, Object> query);

    @POST(Constants.BASE_LINK + "statusupdate.php")
    Call<universalMessage> sendoredretoserver(@QueryMap Map<String, String> query);


    @GET(Constants.BASE_LINK + "getcoupon.php")
    Call<ApplyCoupenResult> applycode(@QueryMap Map<String, String> query);

    @PUT
    Call<Boolean> Upgradeapplycode(@HeaderMap Map<String,String> headers,@Url String url);

    @DELETE
    Call<Boolean> Upgraderemovecode(@HeaderMap Map<String,String> headers,@Url String url);

    @GET(Constants.BASE_LINK + "getshipping-estimation.php")
    Call<GetShippingCharge> getshippingchargebycountry(@QueryMap Map<String, String> query);

    @GET(Constants.BASE_LINK + "filterapi.php")
    Call<FilterDetail> getFilterCatagoryList(@QueryMap Map<String, String> query);


    @GET
    Call<FilterDetail> getUpgardeFilterCatagoryList(@Url String url);


    @POST(Constants.BASE_LINK + "filter.php")
    Call<CatagoryList> getFilterProductList(@Body HashMap<Object, Object> body);

    @POST(Constants.UPGRADED_BASE_LINK_ENGLISH + "getfilterdata")
    Call<CatagoryList> getUpgradedFilterProductList(@Body Map<Object, Object> body);


    @POST(Constants.UPGRADED_BASE_LINK_ARABIC + "getfilterdata")
    Call<CatagoryList> getUpgradedFilterProductListArabic(@Body Map<Object, Object> body);

    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "stc_resendotp.php")
    Call<StcResendOtpResponse> stcresendotp(@FieldMap Map<String, String> map1);

    @FormUrlEncoded
    @POST(Constants.HOST_LINK + "vsms/app/send/")
    Call<OtpResponse> sendotp(@FieldMap Map<String, String> map1);

    @FormUrlEncoded
    @POST(Constants.HOST_LINK + "vsms/app/verify/")
    Call<VerificationResponse> otpverification(@FieldMap Map<String, String> map1);

    @FormUrlEncoded
    @POST(Constants.HOST_LINK + "vsms/app/sendaddress/")
    Call<OtpResponse> otp_for_address_mob_change(@FieldMap Map<String, String> map1);

    @FormUrlEncoded
    @POST(Constants.HOST_LINK + "vsms/app/sendforgotpassword/")
    Call<OtpResponse> sendotpforresetPassword(@FieldMap Map<String, String> map1);

    @GET
    Call<OtpResponse> sendupgradeotpforresetPassword(@Url String url);


    @FormUrlEncoded
    @POST(Constants.HOST_LINK + "vsms/app/sendlogin/")
    Call<OtpResponse> sendotpforLogin(@FieldMap Map<String, String> map1);

   /* @FormUrlEncoded
    @POST(Constants.UPGRADED_HOST_LINK + "vsms/app/sendlogin/")
    Call<OtpResponse> upgradesendotpforLogin(@FieldMap Map<String, String> map1);*/

    @GET/*(Constants.UPGRADED_HOST_LINK + "vsms/app/sendlogin/")*/
    Call<OtpResponse> upgradesendotpforLogin(@Url String url/*@QueryMap Map<String, String> map1*/);

    @FormUrlEncoded
    @POST(Constants.HOST_LINK + "vsms/app/verifyforgotpassword/")
    Call<Verifyforgetpasswordresponse> otpverificationforresetPassword(@FieldMap Map<String, String> map1);


   @GET
    Call<Verifyforgetpasswordresponse> upgradeotpverificationforresetPassword(@Url String url);


    @GET
    Call<Verifyforgetpasswordresponse> otpverificationforLogin(@Url String url/*@FieldMap Map<String, String> map1*/);

    @FormUrlEncoded
    @POST(Constants.UPGRADED_HOST_LINK + "vsms/app/verifylogin/")
    Call<Verifyforgetpasswordresponse> upgradeotpverificationforLogin(@FieldMap Map<String, String> map1);

    @FormUrlEncoded
    @POST(Constants.HOST_LINK + "vsms/app/verifyaddress/")
    Call<Verifyforgetpasswordresponse> otpverificationfoAddress(@FieldMap Map<String, String> map1);


    @GET(Constants.BASE_LINK + "help.php")
    Call<HelpSupport> getHelpSupport();


    @GET(Constants.BASE_LINK + "contact.php")
    Call<ContactMessage> contactMessage(@QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "getcustomerorders.php")
    Call<OrderListResponse> orderList(@FieldMap Map<String, String> map1);


    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "getcustomertrackingorder.php")
    Call<OrderListResponse> orderTrackingList(@FieldMap Map<String, String> map1);


    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "ordercancel.php")
    Call<CancelOrderListResponse> canceorderList(@FieldMap Map<String, String> map1);

    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "createreorder.php")
    Call<ReorderResponse> reorder(@FieldMap Map<String, String> map1);


    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "checkout_paymentstatus.php")
    Call<WalletPaymentStatus> getcheckoutpaymentstatus(@FieldMap Map<String, String> map1);

    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "checkout_statusupdate.php")
    Call<WalletPaymentStatus> updatecheckoutpaymentstatus(@FieldMap Map<String, String> map1);

    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "getremembermecards.php")
    Call<RememberCardName> getremembercards(@FieldMap Map<String, String> map1);


    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "checkout_statusupdate.php")
    Call<WalletPaymentStatus> updatecheckoutfailedpaymentstatus(@FieldMap Map<String, String> map1);


    //    Upgraded Home Page Api
    @GET
    Call<UpgradedHomeScreenResponse> getUpgradedHomeScreenData(@Url String url);
    //    Upgraded Home Page Api
    @GET
    Call<GetScreolledHomePageData> getUpgradedScrolledHomeScreenData(@Url String url);

    @GET
    Call<NewSingleScreenData> getUpgradedSingleHomeScreenData(@Url String url);
    @GET
    Call<NewSingleScreenData> getUpgradedSingleHomeScreenDataEnglish(@Url String url);
    @GET
    Call<UpgardeSavedCardDetail> getUpgradedSavedCard(@Url String url);

    // Upgraded Add Address Api
    @POST("https://upgrade.eoutlet.com/rest/V1/api/addaddress")
    Call<UpgradeAddAddressResponse> getUpgradeAddAddress(@Body RequestBody key);

    //Upgraded Get Address Api
    @GET
    Call<UpgradeGetAddressResponse> getUpgradedAddress(@Url String url);

    //  delete address
    @GET
    Call<UpgradeDeleteAddress> upgradeDeleteAddress(@Url String url);

    //Upgraded Get wishlist Items
    @GET
    Call<UpgradeWishListResponse> getUpgradeWishList(@Url String url);


    //Upgraded Get New Arrival Data
    @GET
    Call<GetNewArrivalHomeData> getNewArrivalData(@Url String url);
    @GET
    Call<GetPopUpData> getPopupData(@Url String url);


  //For Remove item from WishList

    @POST(Constants.UPGRADED_BASE_LINK + "removetowishlist")
    Call<RemoveFromWishListResponse> removeFromWishlist(@Body Map<Object, Object> map1);

    //For Remove item from Guest WishList

    @POST(Constants.UPGRADED_BASE_LINK + "deleteguestwishlist")
    Call<RemoveFromWishListResponse> removeFromGuestWishlist(@Body Map<Object, Object> map1);
    //Upgraded Get Order List Api
    @GET
    Call<UpgradeOrderListResponse> getUpgradeOrderList(@Url String url);

    //Upgraded Cancel Order  Api
    @GET
    Call<UpgradeCancelOrderResponse> upgradeCancelOrder(@Url String url);

    //Upgraded Get return items list form data
    @GET
    Call<UpgradeReturnOrderFormResponse> getUpgradeReturnItemsList(@Url String url);

    //Upgraded Return Order  Api
    @POST(Constants.UPGRADED_BASE_LINK_ENGLISH + "returncreate")
    Call<UpgradeReturnOrderResponse> upgradeReturnOrder(@Body Map<Object, Object> map1);

    //Upgraded Return Order  Api
    @POST(Constants.UPGRADED_BASE_LINK_ARABIC + "returncreate")
    Call<UpgradeReturnOrderResponse> upgradeReturnOrderArabic(@Body Map<Object, Object> map1);

    //Upgraded Get Return Order List Api
    @GET
    Call<UpgradeReturnItemListResponse> getReturnItemList(@Url String url);

    //Upgraded Get return item view Details
    @GET
    Call<ReturnItemViewClickResponse> getReturnItemViewClickDetails(@Url String url);

    //Upgraded Return Order Cancel  Api
    @GET
    Call<UpgradeReturnOrderCancelResponse> upgradeReturnOrderCancel(@Url String url);

    //Upgrade Reorder Api
    @POST(Constants.UPGRADED_BASE_LINK_ENGLISH + "reorder")
    Call<UpgradeReorderResponse> upgradeReorder(@Body Map<Object, Object> map1);

    //Upgrade Reorder Api
    @POST(Constants.UPGRADED_BASE_LINK_ARABIC + "reorder")
    Call<UpgradeReorderResponse> upgradeReorderArabic(@Body Map<Object, Object> map1);

    //    Upgrade contact us api
    @POST(Constants.UPGRADED_BASE_LINK_ENGLISH + "contactus")
    Call<ContactMessage> upgradeContactUs(@Body Map<Object, Object> map1);


    //Upgraded get list of Saved Cards
    @GET
    Call<SavedCardResponse> getSavedCards(@Url String url);
    //Upgraded delete Saved Cards
    @GET
    Call<DeleteSavedCardResponse> deleteSavedCard(@Url String url);

    //    Upgrade Elastic Search API English
    @POST(Constants.UPGRADED_HOST_LINK + Constants.UPGRADED_BASE_LINK_ENGLISH + "getsearchresult")
    Call<ElasticSearchResponse> upgradeElasticSearch(@Body Map<Object, Object> map1);

    //    Upgrade Elastic Search API Arabic
    @POST(Constants.UPGRADED_HOST_LINK + Constants.UPGRADED_BASE_LINK_ARABIC + "getsearchresult")
    Call<ElasticSearchResponse> upgradeElasticSearchArabic(@Body Map<Object, Object> map1);

    //Upgraded Get return item view Details
    @GET
    Call<SellerResponse> getSellerData(@Url String url);
    // Upgraded Add Address Api Arabic
    @POST("https://upgrade.eoutlet.com/rest/ar/V1/api/addaddress")
    Call<UpgradeAddAddressResponse> getUpgradeAddAddressArabic(@Body RequestBody key);
    // Upgraded Edit Address Api Arabic
    @POST("https://upgrade.eoutlet.com/rest/ar/V1/api/editaddress")
    Call<EditAddressResponse> editAddressArabic(@Body RequestBody key);
    // Upgraded Edit Address Api English
    @POST("https://upgrade.eoutlet.com/rest/en/V1/api/editaddress")
    Call<EditAddressResponse> editAddress(@Body RequestBody key);

    @GET
    Call<SendOtpResponse> sendOtp(@Url String url);

    //Upgraded resend otp for address verification
    @GET
    Call<SendOtpResponse> reSendOtp(@Url String url);

    //Upgraded verify otp
    @GET
    Call<VerifyOtpResponse> verifyOtp(@Url String url);

  /*  //    Upgrade get filter data sidebar arabic
    @POST(Constants.UPGRADED_HOST_LINK + Constants.UPGRADED_BASE_LINK_ARABIC + "filternavigation")
    Call<UpgradeFilterListResponse> upgradeFilterListArabic(@Body Map<Object, Object> map1);*/

    //    Upgrade get filter data sidebar arabic
    @GET(Constants.UPGRADED_HOST_LINK + Constants.UPGRADED_BASE_LINK_ARABIC + "filternavigationlist")
    Call<UpgradeFilterListResponse> upgradeFilterListArabic(@QueryMap Map<String, String> map1);

 /*   //    Upgrade get filter data sidebar english
    @POST(Constants.UPGRADED_HOST_LINK + Constants.UPGRADED_BASE_LINK_ENGLISH + "filternavigation")
    Call<UpgradeFilterListResponse> upgradeFilterList(@Body Map<Object, Object> map1);*/
    //    Upgrade get filter data sidebar english
    @GET(Constants.UPGRADED_HOST_LINK + Constants.UPGRADED_BASE_LINK_ENGLISH + "filternavigationlist")
    Call<UpgradeFilterListResponse> upgradeFilterList(@QueryMap Map<String, String> map1);
    //Upgraded get cities List
    @GET
    Call<GetCitiesResponse> getCitiesList(@Url String url);
    //    Upgrade verify number English
    @POST(Constants.UPGRADED_HOST_LINK + Constants.UPGRADED_BASE_LINK_ENGLISH + "isnumberverified")
    Call<NumberVerificationResponse> verifyNumber(@Body Map<Object, Object> map1);

    //    Upgrade verify number Arabic
    @POST(Constants.UPGRADED_HOST_LINK + Constants.UPGRADED_BASE_LINK_ENGLISH + "isnumberverified")
    Call<NumberVerificationResponse> verifyNumberArabic(@Body Map<Object, Object> map1);

    // get privacy policy
    @GET
    Call<PrivacyPolicyResponse> getPrivacyPolicy(@Url String url);

    //   new home screen api
    @GET
    Call<GetNewHomeScreenData> getNewHomeScreenData(@Url String url);

    /*/V1/api/getnexthomepage/:cate_id
    method: get*/
}