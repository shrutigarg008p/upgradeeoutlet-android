package com.eoutlet.Eoutlet.others;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


import com.eoutlet.Eoutlet.utility.ReceivedCookiesInterceptor;

import java.util.Collections;
import java.util.HashSet;

public class MySharedPreferenceClass {

    private static String MY_USERID = "UserID";

    private static String FIRST_NAME = "fname";
    private static String LAST_NAME = "lname";

    private static String MOBILE= "mobile";

    private static String USERNAME = "username";

    private static String PASSWORD = "password";

    private static String EMAIL = "email";

    private static String BEDGE_COUNT = "bedgecount";
    private static String ADDRESSNAME = "addressname";
    private static String ADDRESSFIRSTNAME = "addressfirstname";
    private static String ADDRESSLASTNAME = "addresslastname";


    private static String STREET = "street";
    private static  String CITY = "city";
    private  static String COUNTRY = "country";
    private  static String ADDRESSPHONE = "addressphon";
    private static String COUNTRYID = "countryid";
    private static String SELECTEDID = "selectedid";
    private static String MASK_KEY = "maskkey";
    private static String CART_ID = "cartid";
    private static String TOKEN_NAME= "tokenname";
    private static String WALLET_AMOUNT= "amount";

    private static  String COUNTRY_CODE = "countrycode";


    private static String CHECKBOX_FLAG= "checkboxflag";

    private static String LOGIN_TYPE= "logintype";


    private static String CHOOSEN_LANGUAGE= "en";


    private static String TOKEN= "token";

    private static String CUSTOMER_TOKEN= "customertoken";

    private static String QUOTE_ID = "quoteid";
    private static String CAT_ID = "catid";
    private static String SUBCAT_ID = "subcatid";

    private static String IS_FIRSTTIME = "isfirsttime";
    private static String SHARED_PRODUCT_ID = "sharedProductId";

    private static String SELECTED_CURRENCY_NAME = "currencyname";
    private static String SELECTED_COUNTRY_NAME = "countryname";



    private static String SELECTED_CURRENCY_RATE = "1.0f";


    private static String IS_APP_OPEN_FIRSTTIME = "isopenfirsttime";

    private static String COOKIE_SET = "cookieset";

    private static String SELECTED_COUNTRY_CODE = "selectedcountrycode";


    private static String ADMIN_USERNAME = "adminusername";
    private static String ADMIN_PASSWORD = "adminpassword";

    protected static final String KEY_COOKIES = "KEY_COOKIES";

    private static String DEEPLINKING_NOTIFICATION = "deeplinkingNotification";

    private static String DEEPLINKING_ID = "deeplinkingId";
    private static String DEEPLINKING_NAME = "deeplinkingName";
    private static String DEEPLINKING_PAGE = "deeplinkingPage";


    private static String PURCHASE_CATEGORY_NAMES = "purchase_category_names";
    private static String PURCHASE_CATEGORY_IDS = "purchase_category_ids";
    private static String PURCHASE_BRAND_NAME = "purchase_brand_name";


    public static String getPurchaseCategorynames(Context context) {
        return getPrefs(context).getString(PURCHASE_CATEGORY_NAMES, null);
    }

    public static void setPurchaseCategoryNames(Context context, String value) {
        getPrefs(context).edit().putString(PURCHASE_CATEGORY_NAMES, value).apply();
    }
    public static String getPurchaseCategoryIds(Context context) {
        return getPrefs(context).getString(PURCHASE_CATEGORY_IDS, null);
    }

    public static void setPurchaseBrandname(Context context, String value) {
        getPrefs(context).edit().putString(PURCHASE_BRAND_NAME, value).apply();
    }
    public static String getPurchaseBrandname(Context context) {
        return getPrefs(context).getString(PURCHASE_BRAND_NAME, null);
    }

    public static void setPurchaseCategoryIds(Context context, String value) {
        getPrefs(context).edit().putString(PURCHASE_CATEGORY_IDS, value).apply();
    }

    public static String getDeeplinkingNotification(Context context) {
        return getPrefs(context).getString(DEEPLINKING_NOTIFICATION, null);
    }

    public static void setDeeplinkingNotification(Context context, String value) {
        getPrefs(context).edit().putString(DEEPLINKING_NOTIFICATION, value).apply();
    }


    public static String getDeeplinkingId(Context context) {
        return getPrefs(context).getString(DEEPLINKING_ID, null);
    }

    public static void setDeeplinkingId(Context context, String catId) {
        getPrefs(context).edit().putString(DEEPLINKING_ID, catId).commit();
    }

    public static String getDeeplinkingName(Context context) {
        return getPrefs(context).getString(DEEPLINKING_NAME, null);
    }

    public static void setDeeplinkingName(Context context, String catId) {
        getPrefs(context).edit().putString(DEEPLINKING_NAME, catId).commit();
    }

    public static String getDeeplinkingPage(Context context) {
        return getPrefs(context).getString(DEEPLINKING_PAGE, null);
    }

    public static void setDeeplinkingPage(Context context, String catId) {
        getPrefs(context).edit().putString(DEEPLINKING_PAGE, catId).commit();
    }

    public static Boolean getisAppOpenFirstTime(Context context) {
        return getPrefs(context).getBoolean(IS_APP_OPEN_FIRSTTIME,false);
    }

    public static void setisAppOpenFirstTime(Context context,boolean catId) {
        getPrefs(context).edit().putBoolean(IS_APP_OPEN_FIRSTTIME,catId).commit();
    }



    public static String getAdminUsername(Context context) {
        return getPrefs(context).getString(ADMIN_USERNAME,  " ");
    }

    public static void setAdminUsername(Context context,String adminUsername) {
        getPrefs(context).edit().putString(ADMIN_USERNAME,adminUsername).apply();
    }

    public static String getAdminPassword(Context context) {
        return getPrefs(context).getString(ADMIN_PASSWORD,  " ");
    }

    public static void setAdminPassword(Context context,String adminPassword) {
        getPrefs(context).edit().putString(ADMIN_PASSWORD,adminPassword).apply();
    }



    public static Float getSelectedCurrencyRate(Context context) {

        return getPrefs(context).getFloat(SELECTED_CURRENCY_RATE,  1.0f);
    }

    public static void setSelectedCurrencyRate(Context context,  Float cookies) {
        getPrefs(context).edit().putFloat(SELECTED_CURRENCY_RATE, cookies).commit();
    }

    public static String getCookies(Context context) {

        return getPrefs(context).getString(COOKIE_SET, " ");
    }

    public static void setCookies(Context context, String cookies) {
        getPrefs(context).edit().putString(COOKIE_SET, cookies).apply();
    }
    public static String getSelectedCurrencyName(Context context) {

        return getPrefs(context).getString(SELECTED_CURRENCY_NAME, "SAR");
    }

    public static void setSelectedCurrencyName(Context context, String value) {
        getPrefs(context).edit().putString(SELECTED_CURRENCY_NAME, value).commit();
    }


    public static String getSelectedCountryCode(Context context) {

        return getPrefs(context).getString(SELECTED_COUNTRY_CODE, "SA");
    }

    public static void setSelectedCountryCode(Context context, String value) {
        getPrefs(context).edit().putString(SELECTED_COUNTRY_CODE, value).commit();
    }



    public static String getSelectedCountryName(Context context) {

        return getPrefs(context).getString(SELECTED_COUNTRY_NAME, " ");
    }

    public static void setSelectedCountryName(Context context, String value) {
        getPrefs(context).edit().putString(SELECTED_COUNTRY_NAME, value).commit();
    }

    public static String getSharedProductId(Context context) {

        return getPrefs(context).getString(SHARED_PRODUCT_ID, null);
    }

    public static void setSharedProductId(Context context, String value) {
        getPrefs(context).edit().putString(SHARED_PRODUCT_ID, value).commit();
    }
    public static Boolean getIsFirstTime(Context context) {
        return getPrefs(context).getBoolean(IS_FIRSTTIME,false);
    }

    public static void setIsFirstTime(Context context,boolean catId) {
        getPrefs(context).edit().putBoolean(IS_FIRSTTIME,catId).commit();
    }

    public static String getCatId(Context context) {
        return getPrefs(context).getString(CAT_ID," ");
    }

    public static void setCatId(Context context,String catId) {
        getPrefs(context).edit().putString(CAT_ID,catId).commit();
    }


    public static String getsubCatId(Context context) {
        return getPrefs(context).getString(SUBCAT_ID," ");
    }

    public static void setsubCatId(Context context,String catId) {
        getPrefs(context).edit().putString(SUBCAT_ID,catId).commit();
    }



    public static String getQuoteId(Context context) {
        return getPrefs(context).getString(QUOTE_ID," ");
    }

    public static void setQuoteId(Context context,String quoteId) {
        getPrefs(context).edit().putString(QUOTE_ID,quoteId).commit();
    }
    public static String getCustomerToken(Context context) {
        return getPrefs(context).getString(CUSTOMER_TOKEN," ");
    }

    public static void setCustomerToken(Context context,String token) {
        getPrefs(context).edit().putString(CUSTOMER_TOKEN,token).commit();
    }

    public static String getToken(Context context) {
        return getPrefs(context).getString(TOKEN," ");
    }

    public static void setToken(Context context,String token) {
        getPrefs(context).edit().putString(TOKEN,token).commit();
    }

    public static String getChoosenlanguage(Context context) {
        return getPrefs(context).getString(CHOOSEN_LANGUAGE,"en");
    }


    public static void setChoosenLanguage(Context context,String choosenlanguage) {
        getPrefs(context).edit().putString(CHOOSEN_LANGUAGE,choosenlanguage).commit();

    }

    public static String getLoginType(Context context) {
        return getPrefs(context).getString(LOGIN_TYPE," ");
    }


    public static void setLoginType(Context context,String logintype) {
        getPrefs(context).edit().putString(LOGIN_TYPE,logintype).commit();

    }

    public static String getWalletamount(Context context) {
        return getPrefs(context).getString(WALLET_AMOUNT," ");
    }


    public static void setWalletAmount(Context context,String amount) {
        getPrefs(context).edit().putString(WALLET_AMOUNT,amount).commit();

    }



    public static String getCountryCode(Context context) {
        return getPrefs(context).getString(COUNTRY_CODE," ");
    }


    public static void setCountryCode(Context context,String countrycode) {
        getPrefs(context).edit().putString(COUNTRY_CODE,countrycode).commit();

    }




    public static String getSelectedTokenName(Context context) {
        return getPrefs(context).getString(TOKEN_NAME," ");
    }

    public static void setSelecteTokenName(Context context,String tokenname) {
        getPrefs(context).edit().putString(TOKEN_NAME,tokenname).commit();

    }

    public static void setCheckbofflag(Context context,boolean checkboxflag) {
        getPrefs(context).edit().putBoolean(CHECKBOX_FLAG,checkboxflag).commit();

    }
    public static boolean getCheckboxflag(Context context) {
        return getPrefs(context).getBoolean(CHECKBOX_FLAG,false);
    }






    public static String getSelectedAddressId(Context context) {
        return getPrefs(context).getString(SELECTEDID,"0");
    }

    public static void setSelecteAddressdId(Context context,String selectedid) {
        getPrefs(context).edit().putString(SELECTEDID, selectedid).commit();

    }

    public static String getMaskkey(Context context) {


        return getPrefs(context).getString(MASK_KEY, null);


    }

    public static  void setMaskkey(Context context,String maskkey){
        getPrefs(context).edit().putString(MASK_KEY,maskkey).commit();




    }
    public static int getCartId(Context context) {


        return getPrefs(context).getInt(CART_ID, 0);


    }

    public static  void setCartId(Context context,int cartId) {
        getPrefs(context).edit().putInt(CART_ID, cartId).commit();

    }




        public static String getCountryId(Context context) {
        return getPrefs(context).getString(COUNTRYID,null);
    }

    public static void setCountryId(Context context,String countryId) {
        getPrefs(context).edit().putString(COUNTRYID, countryId).commit();

    }

    public static String getAdressphone(Context context) {
        return getPrefs(context).getString(ADDRESSPHONE," ");
    }

    public static void setAddressphone(Context context,String addressphone) {
        getPrefs(context).edit().putString(ADDRESSPHONE, addressphone).commit();

    }

    public static String getfirstAdressname(Context context) {
        return getPrefs(context).getString(ADDRESSFIRSTNAME,null);
    }

    public static void setfirstAdressname(Context context,String addressname) {
        getPrefs(context).edit().putString(ADDRESSFIRSTNAME, addressname).commit();

    }

    public static String getlastAdressname(Context context) {
        return getPrefs(context).getString(ADDRESSLASTNAME,null);
    }

    public static void setlastAdressname(Context context,String addressname) {
        getPrefs(context).edit().putString(ADDRESSLASTNAME, addressname).commit();

    }


    public static String getAdressname(Context context) {
        return getPrefs(context).getString(ADDRESSNAME,null);
    }

    public static void setAddressname(Context context,String addressname) {
        getPrefs(context).edit().putString(ADDRESSNAME, addressname).commit();

    }

    public static String getStreetname(Context context) {
        return getPrefs(context).getString(STREET, " ");
    }

    public static void setStreetName(Context context,String street) {
        getPrefs(context).edit().putString(STREET, street).commit();

    }

    public static String getCityName(Context context) {
        return getPrefs(context).getString(CITY, null);
    }

    public static void setCityname(Context context,String city) {
        getPrefs(context).edit().putString(CITY,city).commit();

    }


    public static String getCountryName(Context context) {
        return getPrefs(context).getString(COUNTRY, null);
    }

    public static void setCountryname(Context context, String countryname) {
        getPrefs(context).edit().putString(COUNTRY, countryname).commit();

    }








    public static int getBedgeCount(Context context) {
      return getPrefs(context).getInt(BEDGE_COUNT, 0);
    }

    public static void setBedgeCount(Context context,int bedgeCount) {
        getPrefs(context).edit().putInt(BEDGE_COUNT, bedgeCount).commit();

    }



    public static void setMobileNumber(Context context, String value) {


        getPrefs(context).edit().putString(MOBILE, value).commit();


    }

    public static String getMobileNumber(Context context) {


        return getPrefs(context).getString(MOBILE, " ");


    }






    public static String getMyPassword(Context context) {

        return getPrefs(context).getString(PASSWORD, null);
    }

    public static void setMyPassword(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(PASSWORD, value).commit();
    }




    public static String getMyUserName(Context context) {

        return getPrefs(context).getString(USERNAME, null);
    }

    public static void setMyUserName(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(USERNAME, value).commit();
    }









    public static String getEmail(Context context) {

        return getPrefs(context).getString(EMAIL, null);
    }

    public static void setEmail(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(EMAIL, value).commit();

    }



    @SuppressWarnings("static-access")
    public static SharedPreferences getPrefs(Context context) {




        return context.getSharedPreferences("UserDetails", context.MODE_PRIVATE);
    }

    ///////////////////////////////////////////////////////////
    //////////////For User ID/////////////////////////////////
    ///////////////////////////////////////////////////////////

    public static String getMyUserId(Context context) {

        return getPrefs(context).getString(MY_USERID, " ");
    }

    public static void setMyUserId(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(MY_USERID, value).commit();
    }





    public static void clear(Context context) {
        getPrefs(context).edit().clear().commit();
    }



    public static String getMyFirstNamePref(Context context) {

        return getPrefs(context).getString(FIRST_NAME, null);
    }

    public static void setMyFirstNamePref(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(FIRST_NAME, value).commit();
    }

    public static String getMyLastNamePref(Context context) {

        return getPrefs(context).getString(LAST_NAME, null);
    }

    public static void setMyLastNamePref(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(LAST_NAME, value).commit();
    }








}