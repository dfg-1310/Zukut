package com.android.zukut.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.zukut.R;
import com.android.zukut.api.op.ErrorObject;
import com.android.zukut.bo.Notification;
import com.android.zukut.bo.User;
import com.android.zukut.httpClient.AppRequestBuilder;
import com.android.zukut.httpClient.AppResponseListener;
import com.android.zukut.httpClient.AppRestClient;
import com.android.zukut.util.PreferenceKeeper;
import com.android.zukut.util.ZukutGcm;

public class LoginActivity extends Activity {

    private static final String API_TAG = "LoginActivity";
    private EditText unEditText;
    private EditText mnEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViewControls();
        setUpGCM(getApplicationContext());
    }

    private void initViewControls() {
        unEditText = (EditText) findViewById(R.id.activity_login_username_edittext);
        mnEditText = (EditText) findViewById(R.id.activity_login_mobileno_edittext);

        loginButton = (Button) findViewById(R.id.activity_login_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    protected void login() {
        if (!unEditText.getText().toString().equalsIgnoreCase("") && !mnEditText.getText().toString().equalsIgnoreCase("")) {
            AppRestClient.getClient().sendRequest(
                    AppRequestBuilder.login(unEditText.getText().toString().trim(), mnEditText.getText().toString().trim(), new AppResponseListener<User>(User.class, LoginActivity.this) {

                        @Override
                        public void onSuccess(User user, Long serverTime) {
                            saveUserInfo(user);
                        }

                        @Override
                        public void onError(ErrorObject error) {
                            // TODO Auto-generated method stub
                            showToast(error.getErrorMessage());
                        }
                    }), API_TAG);

        } else {
            showToast("Please enter valid input");
        }
    }

    protected void saveUserInfo(User user) {
        PreferenceKeeper keeper = new PreferenceKeeper(this);
        keeper.setUSerLoggedIn(true);
        keeper.setUserInfo(user);
        callNotification();
    }

    private void callNotification() {

        PreferenceKeeper keeper = new PreferenceKeeper(getApplicationContext());

        // TODO Auto-generated method stub
        AppRestClient.getClient().sendRequest(
                AppRequestBuilder.notification(1, 1, getDeviceId(), keeper.getGcmId(), keeper.getUserInfo().getId(), new AppResponseListener<Notification>(Notification.class, LoginActivity.this) {

                    @Override
                    public void onSuccess(Notification response, Long serverTime) {
                        new PreferenceKeeper(LoginActivity.this).saveNotificatioInfo(response);
                        startActivity(new Intent(LoginActivity.this, ContactActivity.class));

                    }

                    @Override
                    public void onError(ErrorObject error) {
                        // TODO Auto-generated method stub
                        showToast(error.getErrorMessage());
                    }
                }), API_TAG);

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to set up the GCM
     * 
     * @param context
     */
    public void setUpGCM(Context context) {
        ZukutGcm gcm = new ZukutGcm(context);
        if (gcm.checkPlayServices()) {
            gcm.getNewGcmid();
        }
    }

    /**
     * This method is used to return device imei id.In case of non calling
     * device same method return hardware serial number.
     * 
     * @return
     */

    public String getDeviceId() {
        String deviceId = ((TelephonyManager) LoginActivity.this.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (deviceId != null && !deviceId.equalsIgnoreCase("null")) {
            // System.out.println("imei number :: " + deviceId);
            return deviceId;
        }

        deviceId = android.os.Build.SERIAL;
        // System.out.println("serial id :: " + deviceId);
        return deviceId;

    }

}
