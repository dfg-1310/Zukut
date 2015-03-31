package com.android.zukut.api.io;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceKeeper {

    private SharedPreferences prefs;
    Context context;

    public PreferenceKeeper(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public boolean isUSerLoggedIn() {
        return prefs.getBoolean(AppConstant.USER_LOGGED_IN,
                false);
    }

    public void setUSerLoggedIn(final boolean flag) {
        prefs.edit()
                .putBoolean(AppConstant.USER_LOGGED_IN,
                        flag).commit();
    }

}
