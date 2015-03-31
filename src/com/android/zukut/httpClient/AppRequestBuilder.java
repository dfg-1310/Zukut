package com.android.zukut.httpClient;

import android.R.bool;

public class AppRequestBuilder {

	private static final String BASE_URL = "http://45.56.78.242:8080/zukut/";

	// public static AppHttpRequest getNearMe(NearMeInput input, Filter filter,
	// AppResponseListener<BrowseUserResult.UserList> appResponseListener) {
	// AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL
	// + "/user/find/near", appResponseListener);
	// Pos loc = PreferenceKeeper.getLocation();
	// if (filter.getLat() != null) {
	// request.addParam("lat", filter.getLat());
	// } else if (loc != null) {
	// request.addParam("lat", loc.getLat());
	// }
	// if (filter.getLng() != null) {
	// request.addParam("lat", filter.getLng());
	// } else if (loc != null) {
	//
	// request.addParam("lng", loc.getLng());
	// }
	// request.addParam("limit", input.getLimit());
	// request.addParam("skip", input.getSkip());
	// request.addParam("maxDistance", String.valueOf(filter.getLocRange()));
	// request.addParam("minDistance", String.valueOf(0));
	// request.addParam("minAge", filter.getAgeMin());
	// request.addParam("maxAge", filter.getAgeMax());
	//
	// if (filter.isHideInActUser()) {
	// request.addParam("active", filter.isHideInActUser());
	// }
	// if (filter.isWink()) {
	// request.addParam("isWinkedBy", filter.isWink());
	// }
	// if (filter.isFav()) {
	// request.addParam("isFavBy", filter.isFav());
	// }
	// if (filter.isHot()) {
	// request.addParam("isHot", filter.isHot());
	// }
	// request.addParam("metaData", filter.getMetaData());
	// return request;
	//
	// }
	//
	// public static AppHttpRequest registerMeWithImage(String filePath,
	// String name, String deviceId, AppResponseListener<RegisterOutput>
	// listener) {
	// AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL
	// + "/me/register", listener);
	// if (name != null) {
	// request.addParam("name", name);
	// }
	// if (deviceId != null) {
	// request.addParam("did", deviceId);
	// }
	// if (filePath != null) {
	// request.addFile("media", new AppFile(new File(filePath), "image/"
	// + getExt(filePath)));
	// }
	// request.addParam("pt", 1);
	// return request;
	// }
	//
	// public static AppHttpRequest registerMeWithVideo(String filePath,
	// String name, String deviceId,int rotation,
	// AppResponseListener<RegisterOutput> listener) {
	// AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL
	// + "/me/register", listener);
	// if (name != null) {
	// request.addParam("name", name);
	// }
	// if (deviceId != null) {
	// request.addParam("did", deviceId);
	// }
	// if (filePath != null) {
	// request.addFile("media", new AppFile(new File(filePath), "video/"
	// + getExt(filePath)));
	// }
	//
	// request.addParam("rotate", rotation);
	//
	// request.addParam("pt", 1);
	// return request;
	// }
	//
	// private static String getExt(String fileName) {
	// String fileFormat = "";
	// fileFormat = (fileName.split("[.]")[1]);
	// return fileFormat;
	// }
	//
	// public static AppHttpRequest editMyProfile(Profile input,
	// AppResponseListener<String> listener) {
	// AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL
	// + "/me/profile/edit", listener);
	// if (input.getDob() != null) {
	// request.addParam("dob", input.getDob() + "");
	// }
	// if (input.getName() != null) {
	// request.addParam("name", input.getName());
	// }
	// if (input.getPos() != null && input.getPos().getLat() != null) {
	// request.addParam("lat", input.getPos().getLat() + "");
	// }
	// if (input.getPos() != null && input.getPos().getLng() != null) {
	// request.addParam("lng", input.getPos().getLng() + "");
	// }
	// if (input.getMetaData() != null) {
	// String metaData = AppUtil.getJson(input.getMetaData());
	// request.addParam("metaData", metaData);
	// }
	// if (input.getSm() != null) {
	// request.addParam("sm", input.getSm());
	// }
	//
	// return request;
	// }
	//
	// public static AppHttpRequest getHotUnhot(int flag,
	// AppResponseListener<String> appResponseListener) {
	// return AppHttpRequest.getGetRequest(BASE_URL + "/me/hot/" + flag,
	// appResponseListener);
	// }
	//
	// public static AppHttpRequest register(String filePath, String uName,
	// String phone, String mimeType, String deviceId,int rotation,
	// AppResponseListener<RegisterOutput> listener) {
	// AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL
	// + "/me/register", listener);
	// if (uName != null) {
	// request.addParam("name", uName);
	// }
	// if (deviceId != null) {
	// request.addParam("did", deviceId);
	// }
	//
	// request.addParam("rotate",rotation);
	//
	// if (filePath != null) {
	// request.addFile("media", new AppFile(new File(filePath), mimeType));
	// }
	// request.addParam("pt", 1);
	// return request;
	// }
	//
	// public static AppHttpRequest addPhone(String phone,
	// AppResponseListener<String> listener) {
	// return AppHttpRequest.getPostRequest(BASE_URL + "/me/phone/add",
	// listener).addParam("phone", phone);
	// }
	//
	// public static AppHttpRequest verifyPhone(String phone, String phVC,
	// AppResponseListener<String> listener) {
	// return AppHttpRequest
	// .getPostRequest(BASE_URL + "/me/phone/verify", listener)
	// .addParam("phone", phone).addParam("vc", phVC);
	// }
	//
	// public static AppHttpRequest uploadUserImage(String filePath, int
	// position,
	// AppResponseListener<MediaContent> appResponseListener) {
	// return AppHttpRequest.getPostRequest(
	// BASE_URL + "/me/pic/upload/" + position, appResponseListener)
	// .addFile(
	// "image",
	// new AppFile(new File(filePath), "image/"
	// + getExt(filePath)));
	// }
	//
	// public static AppHttpRequest uploadUserVideo(String filePath, int
	// position,int rotation,
	// AppResponseListener<MediaContent> appResponseListener) {
	// return AppHttpRequest.getPostRequest(
	// BASE_URL + "/me/video/upload/" + position, appResponseListener).
	// addParam("rotate", rotation)
	// .addFile(
	// "video",
	// new AppFile(new File(filePath), "video/"
	// + getExt(filePath)));
	// }
	//
	// public static AppHttpRequest addContact(String phone, String email,
	// AppResponseListener<String> listener) {
	// AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL
	// + "/me/contact/add", listener);
	//
	// if (phone != null) {
	// request.addParam("phone", phone);
	// }
	// if (!PreferenceKeeper.getInstance().isProfileActivate()) {
	// request.addParam("amILoggedOut", true);
	// }
	// // if (email != null) {
	// // request.addParam("email", email);
	// // }
	//
	// return request;
	// }
	//
	// public static AppHttpRequest verifyContact(String phone, String email,
	// String phVC, AppResponseListener<?> listener) {
	// AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL
	// + "/me/contact/verify", listener);
	// if (phone != null) {
	// request.addParam("phone", phone);
	// }
	// if (email != null) {
	// request.addParam("email", email);
	// }
	// if (phVC != null) {
	// request.addParam("phVC", phVC);
	// }
	// return request;
	// }
	//
	// public static AppHttpRequest claimCode(String claimCode,
	// AppResponseListener<RegisterOutput> listener) {
	// return AppHttpRequest.getPostRequest(BASE_URL + "/claimCode/use",
	// listener).addParam("claimCode", claimCode);
	// }
	//
	// public static AppHttpRequest findUsersWithin(FindUsersInput input,
	// AppResponseListener<BrowseUserResult.UserList> listener) {
	//
	// AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL
	// + "/user/find/within/", listener);
	// Filter filter = input.getFilter();
	// request.addParam("lngBottomLeft", input.getLngBottomLeft());
	// request.addParam("latBottomLeft", input.getLatBottomLeft());
	// request.addParam("lngUpperRight", input.getLngUpperRight());
	// request.addParam("latUpperRight", input.getLatUpperRight());
	// request.addParam("minAge", filter.getAgeMin());
	// request.addParam("maxAge", filter.getAgeMax());
	//
	// if (filter.isHot()) {
	// request.addParam("isHot", filter.isHot());
	// }
	// if (filter.isHideInActUser()) {
	// request.addParam("active", filter.isHideInActUser());
	// }
	// if (filter.isFav()) {
	// request.addParam("isFavBy", filter.isFav());
	// }
	// if (filter.isWink()) {
	// request.addParam("isWinkedBy", filter.isWink());
	// }
	// request.addParam("limit", input.getLimit());
	// request.addParam("skip", input.getSkip());
	// request.addParam("metaData", filter.getMetaData());
	// return request;
	// }
	//
	// public static AppHttpRequest wink(String userId,
	// AppResponseListener<String> listener, boolean isForWinkGame) {
	// return AppHttpRequest.getGetRequest(BASE_URL + "/user/wink/" + userId+
	// "/" + isForWinkGame,
	// listener).loginRequired();
	// }
	//
	// public static AppHttpRequest fav(String userId, int isFav,
	// AppResponseListener<String> listener) {
	// return AppHttpRequest.getGetRequest(
	// BASE_URL + "/user/fav/" + userId + "/" + isFav, listener)
	// .loginRequired();
	// }
	//
	// public static AppHttpRequest block(String userId, int isBlock,
	// AppResponseListener<String> listener) {
	// return AppHttpRequest.getGetRequest(
	// BASE_URL + "/user/block/" + userId + "/" + isBlock, listener)
	// .loginRequired();
	// }
	//
	// public static AppHttpRequest editPrivateNote(String userId, String note,
	// AppResponseListener<String> listener) {
	// return AppHttpRequest
	// .getPostRequest(BASE_URL + "/user/note/edit/" + userId,
	// listener).addParam("note", note).loginRequired();
	// }
	//
	// public static AppHttpRequest getUserProfile(String userId,
	// AppResponseListener<Profile> listener) {
	// return AppHttpRequest.getGetRequest(
	// BASE_URL + "/user/profile/" + userId, listener).loginRequired();
	// }
	//
	// public static AppHttpRequest profileSync(String deviceId,
	// AppResponseListener<ProfileSyncOutput> listener) {
	// AppHttpRequest request = AppHttpRequest.getGetRequest(BASE_URL
	// + "/me/profile/sync", listener);
	// if (deviceId != null) {
	// request.addParam("did", deviceId);
	// }
	// request.addParam("pt", 1);
	// return request;
	// }
	//
	// public static AppHttpRequest createRoom(String userId,
	// AppResponseListener<String> listener) {
	// return AppHttpRequest.getGetRequest(
	// BASE_URL + "/room/create/" + userId, listener);
	// }
	//
	// public static AppHttpRequest getWinkCount(
	// AppResponseListener<Integer> listener) {
	// return AppHttpRequest.getGetRequest(BASE_URL + "/me/winked/by/count",
	// listener);
	// }
	//
	// public static AppHttpRequest getLocationsBasedOnString(String loc,
	// LocationResponseHandler appResponseListener) {
	// return AppHttpRequest.getGetRequest(
	// "http://gd.geobytes.com/AutoCompleteCity?q="
	// + URLEncoder.encode(loc), appResponseListener);
	// }
	//
	// public static AppHttpRequest libraryMediaUpload(String filePath,
	// String type,int rotation, AppResponseListener<MediaContent> listener) {
	// AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL
	// + "/me/library/media/upload", listener);
	// if (type != null) {
	// request.addParam("type", type);
	// if(type.equalsIgnoreCase("video")){
	// request.addParam("rotate", rotation);
	// }
	// }
	//
	// if (filePath != null) {
	// request.addFile("media", new AppFile(new File(filePath), "image/"
	// + getExt(filePath)));
	// }
	// request.addParam("pt", 1);
	// return request;
	// }
	//
	// public static AppHttpRequest meLibraryMediaDelete(String deleteKey,
	// AppResponseListener<String> listener) {
	//
	// return AppHttpRequest.getGetRequest(BASE_URL
	// + "/me/library/media/delete/" + deleteKey, listener);
	//
	// }
	//
	// public static AppHttpRequest meMediaPrimary(String moveKey,
	// AppResponseListener<MediaContent> listener) {
	//
	// AppHttpRequest request = AppHttpRequest.getPostRequest(
	// BASE_URL + "/me/profile/primary/00", listener).addParam("key",
	// moveKey);
	//
	// return request;
	//
	// }
	//
	// public static AppHttpRequest updateDeviceId(String deviceId,
	// AppResponseListener<String> listener) {
	// AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL
	// + "/me/device/update", listener);
	// if (deviceId != null) {
	// request.addParam("did", deviceId);
	// }
	// request.addParam("pt", 1);
	// return request;
	// }
	//
	// public static AppHttpRequest createCall(String userId, boolean
	// isVideoCall,
	// AppResponseListener<CallMessage> appResponseListener) {
	//
	// if (isVideoCall) {
	// return AppHttpRequest.getGetRequest(BASE_URL + "/call/start/"
	// + userId + "/video", appResponseListener);
	// } else {
	// return AppHttpRequest.getGetRequest(BASE_URL + "/call/start/"
	// + userId + "/audio", appResponseListener);
	//
	// }
	// }
	//
	// public static AppHttpRequest endCall(String sessionId,
	// AppResponseListener<Integer> appResponseListener) {
	// return AppHttpRequest.getGetRequest(
	// BASE_URL + "/call/end/" + sessionId, appResponseListener);
	// }
	//
	// public static AppHttpRequest acceptCall(String sessionId,
	// AppResponseListener<Integer> appResponseListener) {
	// return AppHttpRequest.getGetRequest(BASE_URL + "/call/accept/"
	// + sessionId, appResponseListener);
	// }
	//
	// // public static AppHttpRequest declineCall(String sessionId,
	// // AppResponseListener<Integer> appResponseListener) {
	// // return AppHttpRequest.getGetRequest(BASE_URL + "/call/decline/"
	// // + sessionId, appResponseListener);
	// // }
	//
	// public static AppHttpRequest informActiveCall(String sessionId,
	// AppResponseListener<Integer> appResponseListener) {
	// return AppHttpRequest.getGetRequest(BASE_URL + "/call/active/"
	// + sessionId, appResponseListener);
	// }
	//
	// public static AppHttpRequest getCallerDetails(String callId,
	// AppResponseListener<CallMessage> appResponseListener) {
	// return AppHttpRequest.getGetRequest(
	// BASE_URL + "/call/detail/" + callId, appResponseListener);
	// }
	//
	// public static AppHttpRequest getAllUserProfile(String allUID,
	// AppResponseListener<Profile[]> appResponseListener) {
	// AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL
	// + "/user/profiles", appResponseListener);
	// request.addParam("userIds", allUID);
	// return request;
	// }
	//
	// public static AppHttpRequest getWinkGameUsers(Pos pos,int limit,Filter
	// filter,
	// AppResponseListener<BrowseUserResult.UserList> appResponseListener) {
	// AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL
	// + "/user/find/wink/game", appResponseListener);
	// if (pos != null) {
	// request.addParam("lng", pos.getLng());
	// request.addParam("lat", pos.getLat());
	// }
	// request.addParam("limit", limit);
	// request.addParam("minAge", filter.getAgeMin());
	// request.addParam("maxAge", filter.getAgeMax());
	//
	// if (filter.isHideInActUser()) {
	// request.addParam("active", filter.isHideInActUser());
	// }
	// if (filter.isHot()) {
	// request.addParam("isHot", filter.isHot());
	// }
	// request.addParam("metaData", filter.getMetaData());
	//
	//
	// return request;
	// }
	//
	// public static AppHttpRequest deleteRoom(String roomId,
	// AppResponseListener<String> appResponseListener) {
	// return AppHttpRequest.getGetRequest(
	// BASE_URL + "/room/delete/" + roomId, appResponseListener);
	// }
	//
	//
	// public static AppHttpRequest dndSettigsApi(boolean isDnd,
	// AppResponseListener<String> appResponseListener) {
	// String dndId = (isDnd == true ? "1" : "0");
	// return AppHttpRequest.getGetRequest(BASE_URL + "/me/dnd/" + dndId,
	// appResponseListener);
	// }
	//
	// public static AppHttpRequest deleteMyAccountApi(
	// AppResponseListener<String> appResponseListener) {
	// return AppHttpRequest.getGetRequest(BASE_URL + "/me/deactivate/",
	// appResponseListener);
	// }
	//
	// public static AppHttpRequest notInterestedApi(String
	// userId,AppResponseListener<String> listener){
	// return
	// AppHttpRequest.getGetRequest(BASE_URL+"/user/"+userId+"/not/intersted",
	// listener).loginRequired();
	// }
	//
	// public static AppHttpRequest mediaUpload(String filePath, String
	// mediaType,String roomId,String fileFormat,int rotation, boolean isSD,
	// AppResponseListener<MediaContent> appResponseListener) {
	// AppHttpRequest request = AppHttpRequest.getPostRequest(
	// BASE_URL + "/room/media/upload", appResponseListener)
	// .addFile("media", new AppFile(new File(filePath), fileFormat))
	// .addParam("type", mediaType).addParam("roomId", roomId).addParam("isSD",
	// isSD);
	//
	// if(mediaType.equalsIgnoreCase("video")){
	// request.addParam("rotate",rotation);
	// }
	// return request;
	// }
	//
	// public static AppHttpRequest updateLRT(String roomId, long currentPNTime,
	// AppResponseListener<String> appResponseListener) {
	// return AppHttpRequest.getGetRequest(BASE_URL +
	// "/room/"+roomId+"/update/lrt/"+currentPNTime,
	// appResponseListener);
	// }
	//
	public static AppHttpRequest login(String username, String phoneNumber,
			AppResponseListener<String> appResponseListener) {
		return AppHttpRequest
				.getPostRequest(BASE_URL + "loginSignup", appResponseListener)
				.addParam("name", username).addParam("contact", phoneNumber);
	}

	public static AppHttpRequest getAllUsers(
			AppResponseListener<String> appResponseListener) {
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

	public static AppHttpRequest notification(boolean enabled, String plateform,
			String deviceId, String deviceToken, String uId,
			AppResponseListener<String> appResponseListener) {
		return AppHttpRequest
				.getPostRequest(BASE_URL + "notification", appResponseListener)
				.addParam("enabled", enabled).addParam("plateform", plateform)
				.addParam("deviceId", deviceId)
				.addParam("deviceToken", deviceToken).addParam("uId", uId);
	}

}
