package com.android.zukut.httpClient;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;
import com.android.zukut.util.AppFile;

public class AppHttpRequest extends StringRequest {

    public AppResponseListener getListener() {
        return listener;
    }

    private final AppResponseListener listener;
    private Map<String, AppFile> fileParams;
    private Map<String, String> params;
    private String url;

    public boolean isLoginRequired() {
        return loginRequired;
    }

    private boolean loginRequired;

    Map<String, AppFile> getFileParams() {
        return fileParams;
    }

    public AppHttpRequest addParam(String key, Object val) {
        if (params == null) {
            params = new HashMap<String, String>();
        }

        if (val != null)
            params.put(key, val.toString());
        return this;
    }

    public AppHttpRequest addFile(String key, AppFile file) {
        if (fileParams == null) {
            fileParams = new HashMap<String, AppFile>();
        }
        fileParams.put(key, file);
        return this;
    }

    @Override
    public String getUrl() {
        if (getMethod() == Method.GET && params != null) {
            String query = getQuery();
            return this.url + query;
        }
        return super.getUrl();
    }

    private String getQuery() {
        if (params != null) {
            StringBuilder b = new StringBuilder("?");
            for (String key : params.keySet()) {
                b.append(key).append("=").append(params.get(key) + "&");
            }

            return b.toString();
        }
        return null;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return this.params;
    }

    private AppHttpRequest(int method, String url, AppResponseListener listener) {
        super(method, url, listener, listener);
        this.listener = listener;
        this.url = url;
    }

    public static AppHttpRequest getGetRequest(String url,
            AppResponseListener listener) {
        return new AppHttpRequest(Method.GET, url, listener);
    }

    public static AppHttpRequest getPostRequest(String url,
            AppResponseListener listener) {
        return new AppHttpRequest(Method.POST, url, listener);
    }

    /**
     * Cancels this request. ResponseHandler will never be called if a request
     * is cancelled. This should be called on Activity's onStop
     */
    @Override
    public void cancel() {
        super.cancel();
    }

    public AppHttpRequest loginRequired() {
        this.loginRequired = true;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getUrl() + " " + this.getMethod());
        if(this.params!=null){
            sb.append("params : ").append(this.params.toString());
        }
        sb.append("query : " + this.getQuery()).append("isPost : " + (Method.POST==getMethod()));
        return sb.toString();
    }
}
