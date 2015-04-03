package com.android.zukut.services;

import android.app.IntentService;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.android.zukut.R;
import com.android.zukut.activity.CallActivity;
import com.android.zukut.activity.IncomingCallPopUpActivity;
import com.android.zukut.bo.GCMResponse;
import com.android.zukut.util.AppConstant;
import com.android.zukut.util.AppConstant.INTENT_EXTRAS;
import com.android.zukut.util.PreferenceKeeper;

/**
 * Service for sync friend.
 **/
public class SyncFriendsService extends IntentService {

	public SyncFriendsService() {
		super("SyncFriendsService");
	}

	private Intent gcmNotificationIntent;

	private Intent startRingIntent;

	private Intent stopRingIntent;

	private String tag = "SyncFriendsService";
	private int count = 0;

	@Override
	public void onCreate() {
		super.onCreate();
		startRingIntent = new Intent(AppConstant.getStartRingBroadCastAction());
		stopRingIntent = new Intent(AppConstant.getStopRingBroadCastAction());
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void manageWeCamApp(GCMResponse gcmResponse) {

		// System.out.println(" GCM RESPONSE :: " + gcmResponse.toString());

		/**
		 * 4. make call 5. call accepted 6. call rejected 7. call ends
		 * 
		 * 
		 */

		int actionKey = gcmResponse.getAkey();

		if (actionKey >= 4 && actionKey <= 16) {
			manageCall(gcmResponse);
		} else if (actionKey == 17) {
			// manageNotificationCount(gcmResponse);
		}
		// else if (actionKey == 18) {
		// callReportUserApi();
		else if (actionKey == 19) {
			manageChat(gcmResponse);
		} else {
			// manageWeCamDb(gcmResponse);
		}
	}

	/**
	 * @param gcmResponse
	 */
	private void manageChat(GCMResponse gcmResponse) {
		// UPDATE_CHAT_NOTYFY_ACTION
		//
		// Intent updateUserProfileIntent = new Intent(
		// IAppConstant.getUpdateChatNotifyBroadCastAction());
		// updateUserProfileIntent.putExtra(INTENT_EXTRAS.GCM_RESPONSE,
		// gcmResponse);
		// sendBroadcast(updateUserProfileIntent);

	}

	/**
	 * @param gcmResponse
	 */
	// private void manageNotificationCount(GCMResponse gcmResponse) {
	//
	// PreferenceKeeper preferenceKeeper = new PreferenceKeeper(this);
	// try {
	//
	// int notifyCount = Integer.parseInt(gcmResponse.getIds());
	// // check notification count is greater than 0.
	// // if (notifyCount > 0) {
	// // playNotificationSound();
	// // preferenceKeeper.setNotificationCount(notifyCount);
	// // BedgeUtil.bedgeUpdate(this);
	// // }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// preferenceKeeper.setNotificationCount(0);
	// }
	// Intent updateUserProfileIntent = new Intent(
	// IAppConstant.getUpdateUSerProfileBroadCastAction());
	// updateUserProfileIntent.putExtra(INTENT_EXTRAS.USER_PROFILE, "null");
	// sendBroadcast(updateUserProfileIntent);
	//
	// }

	/**
	 * @param gcmResponse
	 */
	// private void manageWeCamDb(GCMResponse gcmResponse) {
	// if (gcmResponse.getAkey() == 3) {
	// deleteUserFromDb(gcmResponse.getIds(), gcmResponse.getAkey());
	// } else if (gcmResponse.getAkey() == 2) {
	// long userId = new PreferenceKeeper(getApplicationContext())
	// .getUserId();
	// if (Long.parseLong(gcmResponse.getIds()) == userId) {
	// getFriends(gcmResponse, true);
	// } else {
	// getFriends(gcmResponse, false);
	// }
	// } else if (gcmResponse.getAkey() == 1) {
	// getFriends(gcmResponse, false);
	// }
	// }

	/**
	 * @param gcmResponse
	 */
	private void manageCall(GCMResponse gcmResponse) {

		Handler handler = null;
		switch (gcmResponse.getAkey()) {
		case 4:// make call

			// TODO : uncomment here

			Intent intent = new Intent(this, IncomingCallPopUpActivity.class);
			intent.putExtra(INTENT_EXTRAS.GCM_RESPONSE, gcmResponse);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
			startActivity(intent);

			sendBroadcast(startRingIntent);
			stopService(startRingIntent);

			break;

		case 5:// accepted call
		case 6:// rejected call
		case 7:// end call
		case 10:// call not attended from another user
			// TODO : uncomment here
			handler = CallActivity.getCallResponseHandler();
			if (handler != null) {
				Message message = new Message();
				message.obj = gcmResponse;
				handler.sendMessage(message);
			}
			sendBroadcast(stopRingIntent);
			break;

		case 11: // missed call
		case 12:
		case 16:
			// TODO : uncomment here
			handler = IncomingCallPopUpActivity.getMissCallHandler();
			if (handler != null) {
				Message message = new Message();
				message.obj = gcmResponse;
				handler.sendMessage(message);
			}
			sendBroadcast(stopRingIntent);
			break;

		default:

			break;
		}
	}

	/**
	 * @param ids
	 * @param type
	 */
	// private void deleteUserFromDb(String ids, int type) {
	// FriendDao friendDao = FriendDao.getInstance();
	// friendDao.deletFriend(getIds(ids), SyncFriendsService.this);
	//
	// sendBroadcast(gcmNotificationIntent);
	// stopService(gcmNotificationIntent);
	// }

	/**
	 * @param ids
	 */
	private String[] getIds(String ids) {
		String[] userIds = ids.split(",");
		return userIds;
	}

	/**
	 * @param ids
	 * @param type
	 * @param isSelfProfile
	 */
	// private void getFriends(GCMResponse gcmResponse, boolean isSelfProfile) {
	//
	// PreferenceKeeper preferenceKeeper = new PreferenceKeeper(this);
	// String appVersion = Utils.getAppVersion(getApplicationContext());
	// ProfileInput profileInput = null;
	// if (isSelfProfile) {
	// profileInput = new ProfileInput(appVersion, gcmResponse.getIds(),
	// preferenceKeeper.getUserId(), preferenceKeeper.getToken(),
	// 1, 0);
	// } else {
	// profileInput = new ProfileInput(appVersion, gcmResponse.getIds(),
	// preferenceKeeper.getUserId(), preferenceKeeper.getToken(),
	// 0, 0);
	//
	// }
	//
	// String url = "http://" + WeCam_URLS.SERVER + WeCam_URLS.PROFILE_URL
	// + "?" + profileInput.toJSON();
	//
	// WeCamApiClient caller = WeCamApiClient.getClient();
	//
	// caller.get(url, this, isSelfProfile, gcmResponse);
	//
	// }

	/**
	 * @param friends
	 * @param type
	 */
	// public void manageDb(List<Friend> friends, final GCMResponse gcmResponse)
	// {
	// if (friends != null) {
	// // System.out.println(" jsonResponse :: " + friends);
	//
	// FriendDao friendDao = FriendDao.getInstance();
	// // Handler handler = FriendsActivity.getGcmResponseHandler();
	//
	// switch (gcmResponse.getAkey()) {
	// case 1:
	// friendDao.addFriend(friends, SyncFriendsService.this);
	//
	// /*
	// * if (handler != null) { Message message = new Message();
	// * message.obj = "" + type; handler.sendMessage(message); }
	// */
	// int size = friends.size();
	// long[] listOfFriendIds = new long[size];
	// for (int i = 0; i < size; i++) {
	// Friend currentFriend = friends.get(i);
	// listOfFriendIds[i] = currentFriend.getuId();
	// }
	// gcmNotificationIntent.putExtra(INTENT_EXTRAS.FRIEND_LIST_ARRAY,
	// listOfFriendIds);
	// sendBroadcast(gcmNotificationIntent);
	// stopService(gcmNotificationIntent);
	// break;
	// case 2:
	// friendDao.updatedFriend(friends, SyncFriendsService.this);
	// /*
	// * if (handler != null) { Message message = new Message();
	// * message.obj = "" + type; handler.sendMessage(message); }
	// */
	// int size2 = friends.size();
	// long[] listOfFriendIds2 = new long[size2];
	// for (int i = 0; i < size2; i++) {
	// Friend currentFriend = friends.get(i);
	// listOfFriendIds2[i] = currentFriend.getuId();
	// }
	// gcmNotificationIntent.putExtra(INTENT_EXTRAS.FRIEND_LIST_ARRAY,
	// listOfFriendIds2);
	//
	// sendBroadcast(gcmNotificationIntent);
	// stopService(gcmNotificationIntent);
	//
	// break;
	// default:
	// break;
	// }
	// }
	// }

	/**
	 * @param jsonResponse
	 * @param type
	 * @param isSelfProfile
	 */
	// public void invokeGcmService(String jsonResponse, boolean isSelfProfile,
	// GCMResponse gcmResponse) {
	// // Log.i("SyncFriendService", "Response" + jsonResponse);
	// List<Friend> friends = getFriendsFromJsonResponse(jsonResponse);
	//
	// if (isSelfProfile) {
	// saveUserProfileInPref(friends.get(0));
	// } else {
	// manageDb(friends, gcmResponse);
	// }
	// }

	/**
	 * @param jsonResponse
	 */
	// private static List<Friend> getFriendsFromJsonResponse(String
	// jsonResponse) {
	//
	// JSONObject object = null;
	// try {
	// object = new JSONObject(jsonResponse);
	// String status = object
	// .getString(ApiResponseTags.API_CALL_STATUS_TAGS.STATUS);
	//
	// if (status
	// .equals(ApiResponseTags.API_CALL_STATUS_TAGS.STATUS_SUCCESS)) {
	//
	// ProfileOutput output = new ProfileOutput();
	// output.createFromJson(object);
	//
	// List<Friend> friends = output.getUserProfiles();
	//
	// return friends;
	//
	// } else {
	//
	// }
	//
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	//
	// return null;
	// }

	/**
	 * @param userProfile
	 */
	// private void saveUserProfileInPref(Friend userProfile) {
	// Intent updateUserProfileIntent = new Intent(
	// IAppConstant.getUpdateUSerProfileBroadCastAction());
	// updateUserProfileIntent.putExtra(INTENT_EXTRAS.USER_PROFILE,
	// userProfile);
	// sendBroadcast(updateUserProfileIntent);
	// }

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * play the notification bell
	 */
	private void playNotificationSound() {
		Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"
				+ R.raw.tone);
		Ringtone ring = RingtoneManager.getRingtone(getApplicationContext(),
				uri);

		ring.play();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		GCMResponse gcmResponse = (GCMResponse) intent.getExtras()
				.getParcelable(INTENT_EXTRAS.GCM_RESPONSE);
		if (gcmResponse.getAkey() == 2) {
			Log.e(tag, "gcmresponse:" + gcmResponse.toString() + ":::count:"
					+ (++count));
		}

		PreferenceKeeper keeper = new PreferenceKeeper(this);

		// check server end ***************
		if (keeper.isUSerLoggedIn()) {
			manageWeCamApp(gcmResponse);
		}

	}

}
