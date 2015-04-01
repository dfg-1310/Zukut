package com.android.zukut.httpClient;

import com.android.zukut.bo.Notification;
import com.android.zukut.bo.User;
import com.android.zukut.util.UserList;


public class AppRequestBuilder {

	private static final String BASE_URL = "http://45.56.78.242:8080/zukut/";

	
	public static AppHttpRequest login(String username, String phoneNumber,
			AppResponseListener<User> appResponseListener) {
		return AppHttpRequest
				.getPostRequest(BASE_URL + "loginSignup", appResponseListener)
				.addParam("name", username).addParam("contact", phoneNumber);
	}

	public static AppHttpRequest getAllUsers(
			AppResponseListener<UserList> appResponseListener) {
		return AppHttpRequest.getGetRequest(BASE_URL + "getAllUsers",
				appResponseListener);
	}

	public static AppHttpRequest accept(String uId, String fromUid, String sId,
			String dId, String chId, String token, String appVer,
			AppResponseListener<String> appResponseListener) {
		return AppHttpRequest
				.getGetRequest(BASE_URL + "zcAccept", appResponseListener)
				.addParam("uId", uId).addParam("fromUid", fromUid)
				.addParam("sId", sId).addParam("dId", dId)
				.addParam("chId", chId).addParam("token", token)
				.addParam("appVer", appVer);
	}

	public static AppHttpRequest call(String uId, String dId, String toUid,
			String iText, boolean isUf, String token, String fromUName,
			String toUName, String msg, String appver,
			AppResponseListener<String> appResponseListener) {
		return AppHttpRequest
				.getGetRequest(BASE_URL + "zcCall", appResponseListener)
				.addParam("uId", uId).addParam("dId", dId)
				.addParam("toUid", toUid).addParam("iText", iText)
				.addParam("uf", isUf).addParam("token", token)
				.addParam("fromUName", fromUName).addParam("toUName", toUName)
				.addParam("msg", msg).addParam("appver", appver);
	}

	public static AppHttpRequest callDtl(String fromUid, String dtl,
			String chId, String uId, String token, String appver,
			AppResponseListener<String> appResponseListener) {
		return AppHttpRequest
				.getGetRequest(BASE_URL + "zcDtl", appResponseListener)
				.addParam("fromUid", fromUid).addParam("dtl", dtl)
				.addParam("chId", chId).addParam("uId", uId)
				.addParam("token", token).addParam("appver", appver);
	}

	public static AppHttpRequest callEnd(String sId, String endUid,
			String chId, String uId, String token,
			AppResponseListener<String> appResponseListener) {
		return AppHttpRequest
				.getGetRequest(BASE_URL + "zcEnd", appResponseListener)
				.addParam("sId", sId).addParam("endUid", endUid)
				.addParam("chId", chId).addParam("uId", uId)
				.addParam("token", token);
	}

	public static AppHttpRequest callReject(String uId, String fromUid,
			String toUid, String sId, String chId, String dId, String token,
			String appver, AppResponseListener<String> appResponseListener) {
		return AppHttpRequest
				.getGetRequest(BASE_URL + "zcReject", appResponseListener)
				.addParam("uId", uId).addParam("fromUid", fromUid)
				.addParam("toUid", toUid).addParam("sId", sId)
				.addParam("chId", chId).addParam("dId", dId)
				.addParam("token", token);
	}

	public static AppHttpRequest notification(int enabled, int plateform,
			String deviceId, String deviceToken, long uId,
			AppResponseListener<Notification> appResponseListener) {
		return AppHttpRequest
				.getPostRequest(BASE_URL + "notification", appResponseListener)
				.addParam("enabled", enabled).addParam("plateform", plateform)
				.addParam("deviceId", deviceId)
				.addParam("deviceToken", deviceToken).addParam("uId", uId);
	}

}
