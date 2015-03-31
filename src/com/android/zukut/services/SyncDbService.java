//package com.android.zukut.services;
//
//import java.util.List;
//
//import org.json.JSONObject;
//
//import android.app.IntentService;
//import android.content.Intent;
//import android.os.IBinder;
//
//import com.android.zukut.api.io.PreferenceKeeper;
//import com.android.zukut.util.IAppConstant;
//
///**
// * Service for sync friend detail in database.
// **/
//public class SyncDbService extends IntentService {
//
//    public SyncDbService() {
//        super("SyncDbService");
//    }
//
//    private Intent gcmNotificationIntent;
//
//    // @Override
//    // public int onStartCommand(Intent intent, int flags, int startId) {
//    //
//    // long lastfst = intent.getExtras().getLong("lastfst");
//    //
//    // getFriends(lastfst);
//    //
//    // // set flag sync process is start.
//    // WeCamBaseActivity.isSyncDBProcessRunning = true;
//    //
//    // return START_NOT_STICKY;
//    // }
//
//    /**
//     * @param lastfst
//     */
//    private void getFriends(long lastfst) {
//        PreferenceKeeper keeper = new PreferenceKeeper(getApplicationContext());
//        String appVersion = Utils.getAppVersion(getApplicationContext());
//        FriendsSyncInput friendsSyncInput = new FriendsSyncInput(appVersion,
//                lastfst, keeper.getUserId(), keeper.getToken());
//
//        WeCamApiClient caller = WeCamApiClient.getClient();
//
//        String url = "http://" + WeCam_URLS.SERVER + WeCam_URLS.SYNC_FRIEND_URL
//                + "?" + friendsSyncInput.toJSON();
//
//        caller.get(url, this);
//
//    }
//
//    @Override
//    public void onCreate() {
//        // TODO Auto-generated method stub
//        super.onCreate();
//
//        gcmNotificationIntent = new Intent(
//                IAppConstant.getNotificationBroadCastAction());
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//    /**
//     * @param jsonResponse
//     */
//    public void invokeSyncDbService(String jsonResponse) {
//        try {
//            JSONObject object = new JSONObject(jsonResponse);
//            String status = object
//                    .getString(ApiResponseTags.API_CALL_STATUS_TAGS.STATUS);
//
//            if (status
//                    .equals(ApiResponseTags.API_CALL_STATUS_TAGS.STATUS_SUCCESS)) {
//                ApiOutput output = buildSyncFriendsOutput(object);
//
//                manageFriendDb((FriendsOutput) output);
//            } else {
//                WeCamBaseActivity.isSyncDBProcessRunning = false;
//                sendBroadcast(gcmNotificationIntent);
//                stopService(gcmNotificationIntent);
//            }
//
//        } catch (Exception e) {
//
//            return;
//        }
//
//    }
//
//    /**
//     * @param object
//     * @return output
//     */
//    private ApiOutput buildSyncFriendsOutput(JSONObject object) {
//        FriendsOutput output = new FriendsOutput();
//        if (output.createFromJson(object))
//            return output;
//        else
//            return null;
//    }
//
//    /**
//     * @param output
//     */
//    public void manageFriendDb(FriendsOutput output) {
//
//        FriendDao friendDao = FriendDao.getInstance();
//
//        List<Friend> addedFriend = output.getAddedfriends();
//        List<Friend> updatedFriend = output.getUpdatedfriends();
//
//        int[] deletFriendIds = output.getDeletedFriendIds();
//
//        if (addedFriend != null && addedFriend.size() > 0) {
//            friendDao.addFriend(addedFriend, this);
//        }
//
//        if (updatedFriend != null && updatedFriend.size() > 0) {
//            friendDao.updatedFriend(updatedFriend, this);
//        }
//
//        if (deletFriendIds != null && deletFriendIds.length > 0) {
//            friendDao.deletFriend(deletFriendIds, this);
//        }
//
//        PreferenceKeeper preferenceKeeper = new PreferenceKeeper(this);
//
//        preferenceKeeper.setLastFriendSyncTime(output.getLastLoginTime());
//
//        // set flag sync process is start.
//        WeCamBaseActivity.isSyncDBProcessRunning = false;
//        sendBroadcast(gcmNotificationIntent);
//        stopService(gcmNotificationIntent);
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        long lastfst = intent.getExtras().getLong("lastfst");
//
//        getFriends(lastfst);
//
//        // set flag sync process is start.
//        WeCamBaseActivity.isSyncDBProcessRunning = true;
//
//    }
//
//}
