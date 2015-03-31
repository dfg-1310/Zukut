package com.android.zukut.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.android.zukut.R;
import com.android.zukut.adapter.UserAdapter;
import com.android.zukut.api.op.ErrorObject;
import com.android.zukut.bo.User;
import com.android.zukut.httpClient.AppRequestBuilder;
import com.android.zukut.httpClient.AppResponseListener;

public class ContactActivity extends Activity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_apponiments);

		listView = (ListView) findViewById(R.id.activity_my_appoinment_listview);

		getAllUsers();

	}

	private void getAllUsers() {
		AppRequestBuilder.getAllUsers(new AppResponseListener<String>(
				String.class, ContactActivity.this) {

			@Override
			public void onSuccess(String t, Long serverTime) {
				// setDataInlist(new ArrayList<User>());
			}

			@Override
			public void onError(ErrorObject error) {
				// TODO Auto-generated method stub
				// showToast(error.getErrorMessage());
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void setDaataInlist(List<User> list) {
		UserAdapter adpAdapter = new UserAdapter(ContactActivity.this, list);

		listView.setAdapter(adpAdapter);

	}

}
