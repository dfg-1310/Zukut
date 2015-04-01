package com.android.zukut.util;

import com.android.zukut.bo.Notification;
import com.android.zukut.bo.User;

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
        return prefs.getBoolean(AppConstant.USER_LOGGED_IN, false);
    }

    public void setUSerLoggedIn(final boolean flag) {
        prefs.edit().putBoolean(AppConstant.USER_LOGGED_IN, flag).commit();
    }

    public void setGcmId(String gcmId) {
        prefs.edit().putString("gcmId", gcmId).commit();
    }

    public String getGcmId() {
        return prefs.getString("gcmId", "");
    }

    public void setUserInfo(User user) {
        prefs.edit().putString(AppConstant.FULL_NAME, user.getFullName()).commit();
        prefs.edit().putString(AppConstant.MOBILE_NUMBER, user.getMobile()).commit();
        prefs.edit().putLong(AppConstant.ID, user.getId()).commit();
    }

    public User getUserInfo() {
        User user = new User();
        user.setFullName(prefs.getString(AppConstant.FULL_NAME, ""));
        user.setId(prefs.getLong(AppConstant.ID, 1));
        user.setMobile(prefs.getString(AppConstant.MOBILE_NUMBER, ""));
        return user;
    }

    public void saveNotificatioInfo(Notification response) {
        prefs.edit().putString(AppConstant.DEVICE_ID, response.getdTok()).commit();
        prefs.edit().putString(AppConstant.DEVICE_TOKEN, response.getdId()).commit();
    }

    public Notification getNotificationInfo() {
        Notification user = new Notification();
        user.setdId(prefs.getString(AppConstant.DEVICE_ID, ""));
        user.setdTok(prefs.getString(AppConstant.DEVICE_TOKEN, ""));
        return user;
    }

}
