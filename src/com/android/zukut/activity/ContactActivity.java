package com.android.zukut.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.zukut.R;
import com.android.zukut.adapter.UserAdapter;
import com.android.zukut.api.op.ContactResponseHandler;
import com.android.zukut.api.op.ErrorObject;
import com.android.zukut.bo.MakeCall;
import com.android.zukut.bo.User;
import com.android.zukut.httpClient.AppRequestBuilder;
import com.android.zukut.httpClient.AppResponseListener;
import com.android.zukut.httpClient.AppRestClient;
import com.android.zukut.util.AppConstant;
import com.android.zukut.util.PreferenceKeeper;
import com.android.zukut.util.UserList;

public class ContactActivity extends Activity {

	private static final String API_TAG = "ContactActivity";
	private ListView listView;
	private UserList userlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_apponiments);

		listView = (ListView) findViewById(R.id.activity_my_appoinment_listview);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				makeCall(userlist.getUser().get(arg2));
			}
		});

		getAllUsers();

	}

	protected void makeCall(final User user) {

		PreferenceKeeper keeper = new PreferenceKeeper(getApplicationContext());

		AppRestClient.getClient().sendRequest(
				AppRequestBuilder.call(keeper.getUserInfo().getId(), keeper
						.getNotificationInfo().getdId(), user.getId(), null,
						true, keeper.getNotificationInfo().getdTok(), keeper
								.getUserInfo().getFullName(), user
								.getFullName(), null, null,
						new AppResponseListener<MakeCall>(MakeCall.class,
								ContactActivity.this) {

							@Override
							public void onSuccess(MakeCall makeCall,
									Long serverTime) {
								if (makeCall != null) {
									if (makeCall.getCs().equalsIgnoreCase("OK")) {

//										sendBroadcast(startRingIntent);
										Bundle bundle = new Bundle();
										makeCall.setuId(user.getId());
										bundle.putParcelable(
												AppConstant.INTENT_EXTRAS.MAKE_CALL,
												makeCall);
//										bundle.putString(
//												AppConstant.INTENT_EXTRAS.FRIEND_NAME,
//												userProfile.getNm());
//										bundle.putString(
//												AppConstant.INTENT_EXTRAS.FRIEND_ADDRESS,
//												userProfile.getLoc());
//										bundle.putInt(
//												AppConstant.INTENT_EXTRAS.FRIEND_ONLINE_STATUS,
//												userProfile.getWs());
//										bundle.putBoolean(
//												AppConstant.INTENT_EXTRAS.IS_FRIEND,
//												isFriend);
//										bundle.putString(
//												AppConstant.INTENT_EXTRAS.FRIEND_IMAGE_ID,
//												null);

										Intent intent = new Intent(
												ContactActivity.this,
												CallActivity.class);
										intent.putExtras(bundle);
										startActivity(intent);
									} else {
										String errorMessage = makeCall
												.getCsMsg();
										showToast(errorMessage);
									}
								}
							}

							@Override
							public void onError(ErrorObject error) {
								// TODO Auto-generated method stub
								showToast(error.getErrorMessage());
							}
						}), API_TAG);
	}

	private void getAllUsers() {

		AppRestClient.getClient().sendRequest(
				AppRequestBuilder.getAllUsers(new ContactResponseHandler(
						UserList.class, ContactActivity.this) {
					@Override
					public void onSuccess(UserList response, Long serverTime) {
						userlist = response;
						setDataInlist(response.getUser());
					}

					@Override
					public void onError(ErrorObject error) {
						// TODO Auto-generated method stub
						// showToast(error.getErrorMessage());
					}
				}), API_TAG);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void setDataInlist(List<User> list) {
		UserAdapter adpAdapter = new UserAdapter(ContactActivity.this, list);

		listView.setAdapter(adpAdapter);

	}

	private void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

}
