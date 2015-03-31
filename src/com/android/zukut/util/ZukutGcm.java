package com.android.zukut.util;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class ZukutGcm {

    private Context context;
    private GoogleCloudMessaging gcm;

    // private final String TAG = "WeCamGcm.java";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public ZukutGcm(Context context) {
        this.context = context;
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If it
     * doesn't, display a dialog that allows users to download the APK from the
     * Google Play Store or enable it in the device's system settings.
     */
    public boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode,
                        (Activity) context, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                // Log.i(TAG, "This device is not supported.");
                ((Activity) context).finish();
            }
            return false;
        }
        return true;
    }

    /**
     * generates a gcm registration id
     */
    public void getNewGcmid() {
        new GCMIdGeneratorTask().execute();
    }

    private class GCMIdGeneratorTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String regid = "";
            try {

                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }

                regid = gcm.register(IAppConstant.GCM_SENDER_Id_Constant());

                // not needed
                // You should send the registration ID to your server over HTTP,
                // so it can use GCM/HTTP or CCS to send messages to your app.
                // The request to your server should be authenticated if your
                // app
                // is using accounts.
                // sendRegistrationIdToBackend();

                // store current registration id

            } catch (IOException ex) {
                // Log.e(TAG, ex.getMessage());
                // If there is an error, don't just keep trying to register.
                // Require the user to click a button again, or perform
                // exponential back-off.
            }
            // Log.i(TAG, "gcm reg id:" + regid);
            System.out.println("gcm reg id:" + regid);
            if (regid != null && regid.trim().length() > 0) {
                new PreferenceKeeper(context).setGcmId(regid);
            }

            return regid;
        }

    }

}
