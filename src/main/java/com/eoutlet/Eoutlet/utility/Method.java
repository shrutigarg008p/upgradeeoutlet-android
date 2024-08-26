package com.eoutlet.Eoutlet.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.eoutlet.Eoutlet.activities.MainActivity;

import java.util.HashSet;

public class Method {
    protected void putStringPreference(Context context, String prefsName, String key, String value) {

        SharedPreferences preferences = context.getSharedPreferences(prefsName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key, value);
        editor.commit();
    }

    protected String getStringPreference(Context context, String prefsName,
                                         String key) {

        SharedPreferences preferences = context.getSharedPreferences(
                prefsName, Activity.MODE_PRIVATE);
        String value = preferences.getString(key, "");
        return value;
    }
}
