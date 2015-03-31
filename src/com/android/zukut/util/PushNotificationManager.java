package com.android.zukut.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.android.zukut.R;
import com.android.zukut.bo.GCMResponse;
import com.android.zukut.services.SyncFriendsService;
import com.android.zukut.util.AppConstant.INTENT_EXTRAS;

public class PushNotificationManager {

    private String messageNotificationPanel = null;
    private static final int NOTIFICATION_ID = 1;
    private int sumMessage = 0;
    public static HashMap<String, Integer> hashMapNoti = new HashMap<String, Integer>();

    private String ns = Context.NOTIFICATION_SERVICE;
    private NotificationManager mNotificationManager;
    private int icon = R.drawable.ic_launcher;
    private long when;
    private Notification notification = null;
    private RemoteViews contentView = null;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
    private String formattedDate = null;
    private Intent notificationIntent, syncFriendServiceIntent;
    private PendingIntent contentIntent = null;
    private GCMResponse gcmResponse;

    public void manageGCMResponse(String msg, String ids, int actionKey,
            String roomId, Context context) {

        gcmResponse = new GCMResponse();

        gcmResponse.setAkey(actionKey);
        gcmResponse.setIds(ids);
        gcmResponse.setMessage(msg);
        gcmResponse.setRoomId(roomId);

        syncFriendServiceIntent = new Intent(context, SyncFriendsService.class);
        syncFriendServiceIntent.putExtra(INTENT_EXTRAS.GCM_RESPONSE,
                gcmResponse);
        context.startService(syncFriendServiceIntent);

//        if (gcmResponse.getAkey() == 19)
//            createNotification(context);
    }

//    private void createNotification(Context context) {
//
//        formattedDate = dateFormat.format(new Date()).toString();
//
//        Integer value = hashMapNoti.get(gcmResponse.getRoomId());
//
//        if (value == null) {
//            hashMapNoti.put(gcmResponse.getRoomId(), 1);
//
//        } else {
//            hashMapNoti.put(gcmResponse.getRoomId(), ++value);
//        }
//
//        Set<?> mapSet = hashMapNoti.entrySet();
//
//        Iterator<?> mapIterator = mapSet.iterator();
//
//        while (mapIterator.hasNext()) {
//            Map.Entry<?, ?> mapEntry = (Map.Entry<?, ?>) mapIterator.next();
//
//            sumMessage = sumMessage + (Integer) mapEntry.getValue();
//
//        }
//
//        mNotificationManager = (NotificationManager) context
//                .getSystemService(ns);
//
//        when = System.currentTimeMillis();
//        notification = new Notification(icon,
//                context.getString(R.string.wecam_txt), when);
//
//        contentView = new RemoteViews(context.getPackageName(),
//                R.layout.custom_notification);
//
//        contentView.setTextViewText(R.id.time, formattedDate);
//        String con = context.getString(R.string.notification_con);
//        String msg = context.getString(R.string.notification_msg);
//
//        if (sumMessage <= 1) {
//            msg = "message";
//        }
//
//        if (hashMapNoti.size() > 1) {
//
//            messageNotificationPanel = sumMessage + " " + msg + " from "
//                    + hashMapNoti.size() + " " + con;
//        } else {
//            messageNotificationPanel = sumMessage + " " + msg;
//        }
//        contentView.setTextViewText(R.id.notification_message,
//                messageNotificationPanel);
//
//        notification.contentView = contentView;
//
//        notificationIntent = new Intent(context, ChatRecentsActivity.class);
//
//        contentIntent = PendingIntent.getActivity(context, 0,
//                notificationIntent, 0);
//
//        notification.contentIntent = contentIntent;
//
//        notification.flags |= Notification.FLAG_AUTO_CANCEL; // notification
//        notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
//        notification.sound = Uri.parse("android.resource://"
//                + context.getPackageName() + "/" + R.raw.tone);
//
//        mNotificationManager.notify(NOTIFICATION_ID, notification);
//
//    }

}
