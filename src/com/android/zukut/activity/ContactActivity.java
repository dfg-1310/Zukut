package com.android.zukut.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import com.android.zukut.R;
import com.android.zukut.adapter.UserAdapter;
import com.android.zukut.api.op.ContactResponseHandler;
import com.android.zukut.api.op.ErrorObject;
import com.android.zukut.bo.User;
import com.android.zukut.httpClient.AppRequestBuilder;
import com.android.zukut.httpClient.AppRestClient;
import com.android.zukut.util.UserList;

public class ContactActivity extends Activity {

    private static final String API_TAG = "ContactActivity";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apponiments);

        listView = (ListView) findViewById(R.id.activity_my_appoinment_listview);

        getAllUsers();

    }

    private void getAllUsers() {

        AppRestClient.getClient().sendRequest(AppRequestBuilder.getAllUsers(new ContactResponseHandler(UserList.class, ContactActivity.this) {

            @Override
            public void onSuccess(UserList response, Long serverTime) {
                 setDataInlist(response.getUser());

                showToast(response.toString());
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
