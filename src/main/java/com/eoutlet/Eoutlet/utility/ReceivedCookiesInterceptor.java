package com.eoutlet.Eoutlet.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;

import java.io.IOException;
import java.nio.channels.AcceptPendingException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor extends Activity implements Interceptor {
    HashSet<String> cookies = new HashSet<>();
    Response originalResponse;
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain != null) {
            originalResponse = chain.proceed(chain.request());
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                cookies = new HashSet<>();
                for (String header : originalResponse.headers("Set-Cookie")) {
                    cookies.add(header);
                }


            }
         //  MySharedPreferenceClass.setCookies2(getBaseContext(),cookies);

        }
        return originalResponse;
    }}





