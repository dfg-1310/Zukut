package com.android.zukut.api.op;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.zukut.activity.ContactActivity;
import com.android.zukut.bo.User;
import com.android.zukut.httpClient.AppResponseListener;
import com.android.zukut.util.UserList;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Created by nishant on 1/2/15.
 * 
 * @param <T>
 */
public class ContactResponseHandler extends AppResponseListener<UserList> {

    private Class<UserList> t;

    protected ContactResponseHandler(Class<UserList> t, Context context) {
        super(UserList.class, context);
        this.t = t;
    }

    private static final String LOG_TAG = ContactResponseHandler.class.getSimpleName();

    @Override
    public void onError(ErrorObject error) {

    }

    @Override
    public void onSuccess(UserList t, Long serverTime) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onResponse(String response) {
        Log.i(LOG_TAG, "api response : " + response.toString());
        try {
            JSONObject obj = new JSONObject(response);

            if (obj.getInt("st") == 1) {

                JSONArray jsonArray = obj.getJSONArray("rs");

                UserList userList = new UserList();

                userList.setUser(getList(jsonArray));

                onSuccess(userList, 0l);
            } else {
                // onError(AppUtil.parseJson(obj.get("rs"), ErrorObject.class));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // if (obj.get("st").getAsInt() == 1) {
        // Gson gson = new Gson();
        // UserList users = (UserList) gson.fromJson(obj.get("rs"),
        // UserList.class);
        // } else {
        // onError(AppUtil.parseJson(obj.get("rs"), ErrorObject.class));
        // }
    }

    private List<User> getList(JSONArray jsonArray) {
        List<User> users = new ArrayList<User>();
        Gson gson = new Gson();
        for (int index = 0; index < jsonArray.length(); index++) {

            try {
                User user = gson.fromJson((jsonArray.getJSONObject(index)).toString(), User.class);
               if(user.getId() != ContactActivity.SELF_ID)
                users.add(user);
            } catch (JsonSyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return users;
    }

}
