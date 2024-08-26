package com.eoutlet.Eoutlet;

import android.app.Application;
import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.moengage.core.LogLevel;
import com.moengage.core.MoEngage;
import com.moengage.core.config.InAppConfig;
import com.moengage.core.config.LogConfig;
import com.moengage.core.config.NotificationConfig;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AFApplication extends Application {
    private static final String AF_DEV_KEY = "Qy2rqKhCQPVNLdyt7Rb5ra";
    public static String shortlink1 = " ";

    @Override
    public void onCreate() {
        boolean isDarkThemeOn = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        UiModeManager uiManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);

        if (Build.VERSION.SDK_INT <= 22) {
            uiManager.enableCarMode(0);
        }

        if (isDarkThemeOn) {
            Log.e("isDarkThemeOn", String.valueOf(isDarkThemeOn));
            uiManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
        } else {
            Log.e("isDarkThemeOn", String.valueOf(isDarkThemeOn));
            uiManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
        }
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate();




        Set<Class<?>> inAppOptOut = new HashSet<>();
        inAppOptOut.add(MainActivity.class);
        MoEngage moEngage =
                new MoEngage.Builder(this, "KEG4KR2O2J5DU5YTIC07PS1P")
                        .configureNotificationMetaData(new NotificationConfig(R.drawable.component, R.drawable.icon_new2, R.color.colorPrimary, "sound", true, true, true))
//                        .enablePartnerIntegration(IntegrationPartner.SEGMENT)
                      /*  .configureLogs(new LogConfig(LogLevel.VERBOSE, false))*/
//                        .configureFcm(new FcmConfig(true))
                        .configureLogs(new LogConfig(LogLevel.VERBOSE, true))
                      .configureInApps(new InAppConfig(true, inAppOptOut))
                        .build();
        MoEngage.initialise(moEngage);
        AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {


            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {

                for (String attrName : conversionData.keySet()) {
                    Log.d("LOG_TAG", "attribute: " + attrName + " = " + conversionData.get(attrName));


                }

            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d("LOG_TAG", "error getting conversion data: " + errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> conversionData) {

                for (String attrName : conversionData.keySet()) {
                    Log.d("LOG_TAG", "attribute: " + attrName + " = " + conversionData.get(attrName));


                    if(attrName.equals("shortlink")) {
                        shortlink1 = conversionData.get(attrName) + " ";
                        Toast.makeText(getBaseContext(), "" + shortlink1, Toast.LENGTH_SHORT).show();
                    }

                }

            }


            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d("LOG_TAG", "error onAttributionFailure : " + errorMessage);
            }
        };

        AppsFlyerLib.getInstance().init(AF_DEV_KEY, conversionListener, getApplicationContext());
        AppsFlyerLib.getInstance().startTracking(this);
        AppsFlyerLib.getInstance().setDebugLog(true);


/*        //ADD_TO_CART

        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put(AFInAppEventParameterName.PRICE, 350);
        eventValue.put(AFInAppEventParameterName.CONTENT_ID, "001");
        eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, "shirt");
        eventValue.put(AFInAppEventParameterName.CURRENCY, "USD");
        eventValue.put(AFInAppEventParameterName.QUANTITY, 3);
        AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), AFInAppEventType.ADD_TO_CART, eventValue);


        //Purchase

        Map<String, Object> eventValuepurchase = new HashMap<String, Object>();
        eventValue.put(AFInAppEventParameterName.PRICE, 350);
        eventValue.put(AFInAppEventParameterName.CONTENT_ID, "221");
// for multiple product categories, set the param value as: // new String {"221", "124"}
        eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, "shirt");
// for multiple product categories,, set the param value as: new String {"shoes", "pants"}
        eventValue.put(AFInAppEventParameterName.CURRENCY, "USD");
        eventValue.put(AFInAppEventParameterName.QUANTITY, 2);
// for multiple product categories, set the param value as: new int {2, 5}
        eventValue.put(AFInAppEventParameterName.RECEIPT_ID, "X123ABC");
        eventValue.put("af_order_id", "X123ABC");
        AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), AFInAppEventType.PURCHASE, eventValuepurchase);*/

    }
}
