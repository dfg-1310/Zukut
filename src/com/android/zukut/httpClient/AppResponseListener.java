package com.android.zukut.httpClient;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.zukut.api.op.ErrorObject;
import com.android.zukut.bo.User;
import com.android.zukut.util.AppUtil;
import com.google.gson.JsonObject;


public abstract class AppResponseListener<T> implements
        Response.Listener<String>, Response.ErrorListener {

    private static final String LOG_TAG = AppResponseListener.class.getSimpleName();
    private final Class<T> t;
    private Context context;

    @Override
    public void onErrorResponse(VolleyError error) {
        _onError(new ErrorObject("cncws",
                "Oops, could not communicate with server"));
    }

    protected AppResponseListener(Class<T> t, Context context) {
        this.t = t;
        this.context = context;
    }

     
    @Override
    public void onResponse(String response) {
        Log.i(LOG_TAG, "api response : " + response.toString());
        JsonObject obj = AppUtil.parseJson(response);
        if (obj.get("st").getAsInt() == 1) {
            onSuccess(AppUtil.parseJson(obj.get("rs"), t),
                    AppUtil.parseJson(obj.get("time"), Long.class));
        } else {
            _onError(AppUtil.parseJson(obj.get("rs"), ErrorObject.class));
        }
    }

    void _onError(ErrorObject error) {

        Log.i(LOG_TAG, "api error response : " + error.toString());

//        if (context != null && context instanceof AvidBaseActivity) {
//            ((AvidBaseActivity) context).hideProgressBarApiBase();
//        }
//        ErrorHandler handler = ErrorHanderFactory.getHandler(error
//                .getErrorCode());
//        if (handler != null) {
//            handler.handleError(error, context);
//        } else {
            onError(error);
//        }
    }

    public abstract void onSuccess(T t, Long serverTime);

    public abstract void onError(ErrorObject error);

}
