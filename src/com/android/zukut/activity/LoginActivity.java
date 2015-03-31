package com.android.zukut.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.zukut.R;
import com.android.zukut.api.op.ErrorObject;
import com.android.zukut.httpClient.AppRequestBuilder;
import com.android.zukut.httpClient.AppResponseListener;
import com.android.zukut.util.ZukutGcm;

public class LoginActivity extends Activity {

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
		if (!unEditText.getText().toString().equalsIgnoreCase("")
				&& !mnEditText.getText().toString().equalsIgnoreCase("")) {
			AppRequestBuilder.login(unEditText.getText().toString().trim(),
					mnEditText.getText().toString().trim(),
					new AppResponseListener<String>(String.class,
							LoginActivity.this) {

						@Override
						public void onSuccess(String t, Long serverTime) {
							showToast(t);
						}

						@Override
						public void onError(ErrorObject error) {
							// TODO Auto-generated method stub
							showToast(error.getErrorMessage());
						}
					});

		} else {
			showToast("Please enter valid input");
		}
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
}
