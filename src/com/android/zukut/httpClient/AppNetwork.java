package com.android.zukut.httpClient;

import java.io.IOException;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.HurlStack;
import com.android.zukut.util.AppFile;
import com.android.zukut.util.PreferenceKeeper;

public class AppNetwork extends HurlStack {

	private static final String TAG = AppNetwork.class.getSimpleName();
	Context context;

	AppNetwork(Context context) {
		this.context = context;
	}

	@Override
	public HttpResponse performRequest(Request<?> request,
			Map<String, String> additionalHeaders) throws IOException,
			AuthFailureError {
		if (!(request instanceof AppHttpRequest)) {
			Log.i(TAG, "fetching image: " + request.getUrl().toString());
			return super.performRequest(request, additionalHeaders);
		}
		AppHttpRequest appRequest = (AppHttpRequest) request;
		HttpResponse resp;
		// additionalHeaders.put("cookie", PreferenceKeeper.getCookie());
		Log.i(TAG, "firing api : " + appRequest.toString());
		// if(appRequest.getFileParams() != null){
		// resp = getResponse(appRequest.getFileParams(),
		// appRequest.getParams(), additionalHeaders, request.getUrl());
		// }else{
		resp = super.performRequest(request, additionalHeaders);
		// }
		String cookie = getCookie(resp);
		// if(cookie!=null)
		// PreferenceKeeper.saveCookie(cookie);
		return resp;
	}

	private String getCookie(HttpResponse resp) {
		Header[] headers = resp.getHeaders("set-cookie");
		if (headers.length == 1) {
			return headers[0].getValue();
		}
		return null;
	}

}
