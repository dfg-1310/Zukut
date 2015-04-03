package com.android.zukut.activity;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.android.zukut.util.PushNotificationManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Service that performs tasks based on the GCM received.
 * 
 */

public class GcmIntentService extends IntentService {

    public static final String TAG = "GcmIntentService.java";
    private static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    static int gcmCount = 1;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    /**
     * Method that handles the received intent and filters messages based on
     * message type.
     */

    @Override
    protected void onHandleIntent(Intent intent) {
        // Log.e(TAG, "MESSAGE RECIEVED AT GCM INTENT SERVICE");
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) { // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that
             * GCM will be extended in the future with new message types, just
             * ignore any message types you're not interested in, or that you
             * don't recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                // sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                // sendNotification("Deleted messages on server: "
                // + extras.toString());
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {

                // Post notification of received message.

            	Log.d(TAG, " gcm response :: "+extras.toString());
            	
                String msg = extras.getString("message");
                String ids = extras.getString("ids");
                String roomId = extras.getString("roomId");

                int actionKey = -1;

                try {
                    actionKey = Integer.parseInt(extras.getString("akey"));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                PushNotificationManager pushNotificationManager = new PushNotificationManager();
                pushNotificationManager.manageGCMResponse(msg, ids, actionKey,
                        roomId, this);

                WakefulBroadcastReceiver.completeWakefulIntent(intent);
            }
        }

    }

}
