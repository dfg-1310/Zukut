package com.android.zukut.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
	public static long SELF_ID;

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

		SELF_ID = new PreferenceKeeper(ContactActivity.this).getUserInfo()
				.getId();

		new SyncContact().execute();

		// getAllUsers();

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

										// sendBroadcast(startRingIntent);
										Bundle bundle = new Bundle();
										makeCall.setuId(user.getId());
										bundle.putParcelable(
												AppConstant.INTENT_EXTRAS.MAKE_CALL,
												makeCall);
										bundle.putString(
												AppConstant.INTENT_EXTRAS.FRIEND_NAME,
												user.getFullName());
										// bundle.putString(
										// AppConstant.INTENT_EXTRAS.FRIEND_ADDRESS,
										// userProfile.getLoc());
										// bundle.putInt(
										// AppConstant.INTENT_EXTRAS.FRIEND_ONLINE_STATUS,
										// userProfile.getWs());
										// bundle.putBoolean(
										// AppConstant.INTENT_EXTRAS.IS_FRIEND,
										// isFriend);
										// bundle.putString(
										// AppConstant.INTENT_EXTRAS.FRIEND_IMAGE_ID,
										// null);

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
						 showToast(error.getErrorMessage());
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

	@Override
	protected void onResume() {
		hideKeyBoard();
		super.onResume();
	}

	public void hideKeyBoard() {
		try {
			InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			if (inputManager.isAcceptingText()) {
				inputManager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method is used to read device contact.
	 * 
	 * @return List<Friend>
	 */
	private List<User> readDeviceContact() {
		List<User> friends = null;
		User user = null;
		ContentResolver cr = getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		String phone = "";
		String emailContact = "";
		String pId = "";
		if (cur.getCount() > 0) {
			friends = new ArrayList<User>();

			while (cur.moveToNext()) {
				phone = "";
				emailContact = "";
				pId = "";
				user = new User();


				pId = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				
				user.setFullName(cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

					Cursor pCur = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?", new String[] { pId }, null);
					while (pCur.moveToNext()) {
						if (phone.length() > 0) {
							phone += ",";
						}
						phone += (pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
								.trim());

						// System.out.println(" phone number :: " + phone);
					}
					if (phone != null && phone.length() > 0) {
						if(phone.contains(",")){
							phone = phone.split(",")[0];
						}
						user.setMobile(phone);
					}
					pCur.close();
				}

				if (user.getFullName() != null || user.getMobile() != null) {
					friends.add(user);
				}
				
				if(friends.size()>20){
					return friends;
				}
			}
		}
		return friends;
	}

	private class SyncContact extends AsyncTask<Void, Void, List<User>> {

		@Override
		protected List<User> doInBackground(Void... params) {
			List<User> users = readDeviceContact();

			return users;
		}

		@Override
		protected void onPostExecute(final List<User> result) {
			super.onPostExecute(result);

			AppRestClient.getClient().sendRequest(
					AppRequestBuilder.syncContact(new PreferenceKeeper(
							ContactActivity.this).getUserInfo().getId(),
							getContact(result),
							new ContactResponseHandler(
									UserList.class, ContactActivity.this) {
								@Override
								public void onSuccess(UserList response, Long serverTime) {
									userlist = response;
									compareList(response, result);
								}

								@Override
								public void onError(ErrorObject error) {
									// TODO Auto-generated method stub
									 showToast(error.getErrorMessage());
								}
							}), API_TAG);

		}

		protected void compareList(UserList response, List<User> result) {
			// TODO Auto-generated method stub
			for(User user : response.getUser()){
				for(User user1 : result){
					if(user.getMobile().equalsIgnoreCase(user1.getMobile())){
						user1.setSync(true);
						user1.setId(user.getId());
						user.setSync(true);
						return;
					}
				}
			}

			for(User user : response.getUser()){
				if(!user.isSync()){
					result.add(user);
				}
			}
			
			setDataInlist(result);
		}

		private String getContact(List<User> result) {
			String contact = "";
			for (User user : result) {

				if(user.getMobile() != null && !user.getMobile().equalsIgnoreCase("")){
				if (!contact.equalsIgnoreCase("")) {
					contact += ",";
				}
				contact += user.getMobile();
				}
			}
			return contact;
		}

	}

}
