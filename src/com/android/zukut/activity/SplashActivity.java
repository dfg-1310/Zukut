package com.android.zukut.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.android.zukut.R;
import com.android.zukut.util.PreferenceKeeper;

public class SplashActivity extends Activity {

	// time to display the splash screen in ms
		protected int _splashTime = 1000;
		private Handler handler;

		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_splash);

			/**
			 * create thread to show splash for a fixed time period
			 */
			Thread splashThread = new Thread() {

				@Override
				public void run() {
					try {
						sleep(_splashTime);
					} catch (InterruptedException e) {
					} finally {
						handler.sendEmptyMessage(1);
					}
				}
			};
			splashThread.start();

			/**
			 * handler which is called after the execution of splash thread
			 */
			handler = new Handler() {

				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					
					PreferenceKeeper keeper = new PreferenceKeeper(getApplicationContext());
					
					if(keeper.isUSerLoggedIn()){
						launchActivity(ContactActivity.class);
					}else{
						launchActivity(LoginActivity.class);
					}
				}

				private void launchActivity(Class<? extends Activity> classObj) {
					Intent intent = new Intent(SplashActivity.this,	classObj);
					startActivity(intent);
					finish();
				}

			};
		}

		@Override
		public void onBackPressed() {
			super.onBackPressed();
		}


}
