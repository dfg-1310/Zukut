package com.android.zukut.activity;

import android.app.Application;

import com.android.zukut.httpClient.AppRestClient;
import com.android.zukut.util.ZukutGcm;

public class BaseApplication extends Application {

    private static String LOGTAG = BaseApplication.class.getSimpleName();
    private static BaseApplication _instance;

    /**
     * Initializes the application level data
     */

    @Override
    public void onCreate() {
        super.onCreate();
        initializeVolley();
        _instance = this;
        getGCMRegId();
       
    }

    private void initializeVolley() {
        AppRestClient.init(getBaseContext());
    }

    public static BaseApplication get() {
        return _instance;
    }

    private void getGCMRegId() {
        ZukutGcm gcm = new ZukutGcm(getApplicationContext());
        if (gcm.checkPlayServices()) {
            gcm.getNewGcmid();
        }
    }

   
}
