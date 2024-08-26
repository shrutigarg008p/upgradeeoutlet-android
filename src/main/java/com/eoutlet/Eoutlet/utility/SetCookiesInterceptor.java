package com.eoutlet.Eoutlet.utility;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SetCookiesInterceptor extends Activity implements Interceptor {


    public static final String PREF_COOKIES = "PREF_COOKIES";
    // We're storing our stuff in a preference made just for cookies called PREF_COOKIES.
    private Context context;



    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

      //  HashSet<String> preferences =  MySharedPreferenceClass.getCookies2(getApplicationContext());
/*
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
        }*/

        return chain.proceed(builder.build());
    }
}