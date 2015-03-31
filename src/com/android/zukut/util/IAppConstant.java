package com.android.zukut.util;

import java.util.HashMap;

import android.text.TextUtils;

public class IAppConstant {

    public static final String MIX_PANEL_TOKEN = "03366f9e71e6ce30982fbd51053bdc26";

    /** Could be PROD,STAGING,DEV */
    public static String ENV = "environmentToSetByJenkins";

    // String GCM_SENDER_ID = "229022141858";// dev
    // String GCM_SENDER_ID = "238625476820";// prod
    // String GCM_SENDER_ID = "354371483578";// staging

    public static Long CALL_REMINDER_DELAY_TIME = (long) (5000);

    public static String FLURRY_API_KEY = "MD8QMWCKZ57N5BMPYKSN";

    public static String SESSION_EXPIRED_ERROR_CODE = "ST001";

    public static String LOGGED_IN_DiFF_SYSTEM_ERROR_CODE = "ITE001";
    public static String LOG_IN_FIRST_ERROR_CODE = "GLF001";

    public static String LOGIN_LOGOUT_ERROR_CODE = "LLE003";

    public static String SESSION_TIMED_OUT_ERROR_CODE = "ST002";
    public static long RINGTONE_DELAY = 45000;

    public static long PROGRESS_BAR_DELAY = 60000;

    public static String GP_LOGIN_CONSTANTS = "#gplus";
    public static String OPEN_TOK_API_KEY = "44565882";

    public interface ApiCallsConstant {

        int count = 50;
        int buffer = 25;
    }

    public interface ApiCallsConstantForMap {
        int count = 1000;
    }

    public interface UserStatus {
        int OFFLINE = 0;
        int ONLINE = 2;
        int ONLINE_FOR_FRIENDS = 1;
    }

    public interface DialogConstant {
        int TAG_TYPE = 000;
        int LOCATION_SERVICE_ACTION = 101;
        int COULD_NOT_GET_LOCATION_ACTION = 102;
        int SESSION_EXPIRED_ACTION = 103;
        int END_CALL_SCREEN_ACTION = 104;
        int EXIT_FROM_END_CALL_SCREEN_ACTION = 104;
        int END_CALL_START_PROFILE_ACTIVITY = 105;
        int EXIT_FROM_INCOMING_CALL_SCREEN_ACTION = 107;
        int GENERAL_NO_ACTION = -1;
        int REMOVE_ACCOUNT_ACTION = 106;
        int ADD_FRIEND_CONFIRMATION_ACTION = 200;
        // int LOGOUT_CONFIRMATION_ACTION = 201;
        int MAKE_CALL_FAILED_RESPONSE = 108;
        int SHARE_LOCATION_ACTION = 109;
        // int STOP_SHARE_LOCATION_ACTION=111;
        int SHARE_LOCATION_ACTION_FOR_NEAR_ME = 111;
        int ENABLE_LOCATION_SERVICES = 110;
        int REMOVE_TAG_DIALOG = 112;
        int PURCHASE_DIALOG = 113;
        int SOCIAL_ACC_DIALOG = 114;
        // int ENABLE_LOCATION_SERVICES_FOR_OLD_USER=111;
        int INVITE_NON_WECAM_FRIENDS_ACTION = 115;
        int CLEAR_ALL_NOTY_ACTION = 116;
        int REFRESH_INVITE_FRIEND_LIST = 117;

        int CLEAR_SIGN_UP_PASSWORD = 118;

        int CLEAR_CURRENT_PASSWORD = 250;
        int CLEAR_NEW_PASSWORDS = 251;

        int INVALID_EMAIL_ERROR_ACTION = 252;

        int CHAT_SCREEN_WEB_RTC_CHAT_NOT_SUPPORTED = 253;

        int CALL_SCREEN_WEB_RTC_CHAT_NOT_SUPPORTED = 254;

        int LOGIN_BACK_AGAIN_NEW_USER_CONFIRMATION = 119;

        int LOGIN_LOGOUT_ERROR = 500;

        int NO_ACTION = 300;
    }

    // public interface BroadcastAction {
    // String GCM_NOTIFICATION_ACTION =
    // "com.mobicules.wecam.gcm_notofication";
    // String SYNC_PROCESS_COMPLETED_ACTION =
    // "com.mobicules.wecam.sync_process_completed";
    // String START_RING_ACTION = "com.mobicules.wecam.start_ring";
    // String STOP_RING_ACTION = "com.mobicules.wecam.stop_ring";
    // String CALL_SCREEN_VIEW_ACTION =
    // "com.mobicules.wecam.call_screen_view";
    // String INCOMING_CALL_POP_SCREEN_VIEW_ACTION =
    // "com.mobicules.wecam.incoming_call_pop_up_screen_view";
    // String START_CHAT_ACTION = "com.mobicules.wecam_start_chat";
    // String END_CHAT_ACTION = "com.mobicules.wecam_end_chat";
    // String SEND_NEW_MSG_ACTION = "com.mobicules.wecam_send_new_msg";
    // String RECEIVE_NEW_MSG_ACTION =
    // "com.mobicules.wecam_receive_new_msg";
    // String UPDATE_CHAT_COUNT_ACTION =
    // "com.mobicules.wecam.update_chat_count";
    // String UPDATE_USER_PROFILE_ACTION =
    // "com.mobicules.wecam.update_user_profile";
    // String UPDATE_NOTIFY_COUNT_ACTION =
    // "com.mobicules.wecam.update_notification_count";
    // String UPDATE_CHAT_NOTYFY_ACTION =
    // "com.mobicules.wecam.chat_notification";
    // String ALGOTOK_GET_CHAT_HISTORY_ACTION =
    // "com.mobicules.wecam.algotok_get_chat_history";
    // String ALGOTOK_CHAT_HISTORY_AVAIALBLE_ACTION =
    // "com.mobicules.wecam.algotok_chat_history_available";
    // String ALGOTOK_CHAT_READY = "com.mobicules.wecam.algotok_chat_ready";
    // String ALGOTOK_ROOM_DELETED_ACTION =
    // "com.mobicules.wecam.algotok_room_deleted";
    // String ALGOTOK_ROOM_MSG_ACTION =
    // "com.mobicules.wecam.algotok_room_msg";

    // String ALGOTOK_CHAT_TYPING_START_ACTION =
    // "com.mobicules.wecam.algotok_chat_typing_start";
    // String ALGOTOK_CHAT_TYPING_STOP_ACTION =
    // "com.mobicules.wecam.algotok_chat_typing_stop";
    // String ALGOTOK_SEND_TYPING_START_ACTION =
    // "com.mobicules.wecam.algotok_send_typing_start";
    // String ALGOTOK_SEND_TYPING_STOP_ACTION =
    // "com.mobicules.wecam.algotok_send_typing_stop";
    // }

    public static String getSendTypingStopBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.algotok_send_typing_stop";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.algotok_send_typing_stop";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.algotok_send_typing_stop";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.algotok_send_typing_stop";
        }
        return broadCastAction;

    }

    public static String getSendTypingStartBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.algotok_send_typing_start";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.algotok_send_typing_start";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.algotok_send_typing_start";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.algotok_send_typing_start";
        }
        return broadCastAction;

    }

    public static String getChatTypingStopBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.algotok_chat_typing_stop";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.algotok_chat_typing_stop";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.algotok_chat_typing_stop";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.algotok_chat_typing_stop";
        }
        return broadCastAction;

    }

    public static String getChatTypingStartBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.algotok_chat_typing_start";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.algotok_chat_typing_start";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.algotok_chat_typing_start";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.algotok_chat_typing_start";
        }
        return broadCastAction;

    }

    public static String getRoomMessageBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.algotok_room_msg";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.algotok_room_msg";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.algotok_room_msg";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.algotok_room_msg";
        }
        return broadCastAction;

    }

    public static String getRoomDeletedBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.algotok_room_deleted";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.algotok_room_deleted";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.algotok_room_deleted";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.algotok_room_deleted";
        }
        return broadCastAction;

    }

    public static String getChatReadyBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.algotok_chat_ready";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.algotok_chat_ready";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.algotok_chat_ready";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.algotok_chat_ready";
        }
        return broadCastAction;

    }

    public static String getChatHistoryAvailableBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.algotok_chat_history_available";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.algotok_chat_history_available";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.algotok_chat_history_available";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.algotok_chat_history_available";
        }
        return broadCastAction;

    }

    public static String getChatHistoryBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.algotok_get_chat_history";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.algotok_get_chat_history";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.algotok_get_chat_history";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.algotok_get_chat_history";
        }
        return broadCastAction;

    }

    public static String getUpdateChatNotifyBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.chat_notification";// default
                                                                         // to
                                                                         // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.chat_notification";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.chat_notification";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.chat_notification";
        }
        return broadCastAction;

    }

    public static String getNotificationBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.gcm_notofication";// default
                                                                        // to
                                                                        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.gcm_notofication";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.gcm_notofication";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.gcm_notofication";
        }
        return broadCastAction;

    }

    // public static String getSyncProcessCompletedBroadCastAction() {
    // String broadCastAction = "com.mobicules.wecam.sync_process_completed";//
    // default
    // // to
    // // dev
    // if (TextUtils.equals(ENV, "PROD")) {
    // broadCastAction = "com.wecamchat.wecam.sync_process_completed";
    // } else if (TextUtils.equals(ENV, "STAGING")) {
    // broadCastAction = "com.wecamchat.wecamstaging.sync_process_completed";
    // } else if (TextUtils.equals(ENV, "DEV")) {
    // broadCastAction = "com.mobicules.wecam.sync_process_completed";
    // }
    // return broadCastAction;
    //
    // }

    public static String getStartRingBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.stop_ring";// default
                                                                 // to
                                                                 // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.stop_ring";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.stop_ring";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.stop_ring";
        }
        return broadCastAction;

    }

    public static String getStopRingBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.start_ring";// default
                                                                  // to
                                                                  // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.start_ring";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.start_ring";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.start_ring";
        }
        return broadCastAction;

    }

    public static String getCallScreenViewBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.call_screen_view";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.call_screen_view";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.call_screen_view";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.call_screen_view";
        }
        return broadCastAction;

    }

    public static String getCallPopUpViewActionBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.incoming_call_pop_up_screen_view";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.incoming_call_pop_up_screen_view";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.incoming_call_pop_up_screen_view";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.incoming_call_pop_up_screen_view";
        }
        return broadCastAction;

    }

    public static String getStartChatBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam_start_chat";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam_start_chat";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging_start_chat";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam_start_chat";
        }
        return broadCastAction;

    }

    public static String getEndChatBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam_end_chat";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam_end_chat";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging_end_chat";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam_end_chat";
        }
        return broadCastAction;

    }

    public static String getSendNewMessageBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam_send_new_msg";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam_send_new_msg";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging_send_new_msg";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam_send_new_msg";
        }
        return broadCastAction;

    }

    public static String getReceiveNewMessageBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam_receive_new_msg";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam_receive_new_msg";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging_receive_new_msg";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam_receive_new_msg";
        }
        return broadCastAction;

    }

    public static String getUpdateChatCountBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.update_chat_count";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.update_chat_count";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.update_chat_count";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.update_chat_count";
        }
        return broadCastAction;

    }

    public static String getUpdateUSerProfileBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.update_user_profile";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.update_user_profile";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.update_user_profile";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.update_user_profile";
        }
        return broadCastAction;

    }

    public static String getUpdateNotificationCountBroadCastAction() {
        String broadCastAction = "com.mobicules.wecam.update_notification_count";// default
        // to
        // dev
        if (TextUtils.equals(ENV, "PROD")) {
            broadCastAction = "com.wecamchat.wecam.update_notification_count";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            broadCastAction = "com.wecamchat.wecamstaging.update_notification_count";
        } else if (TextUtils.equals(ENV, "DEV")) {
            broadCastAction = "com.mobicules.wecam.update_notification_count";
        }
        return broadCastAction;

    }

    public static HashMap<String, String> twitter_Constants() {
        HashMap<String, String> twitterConstantsMap = new HashMap<String, String>();

        // default to dev
        twitterConstantsMap.put("CONSUMER_KEY", "oYj03yiZs9ngNda4zCZYVA");
        twitterConstantsMap.put("CONSUMER_SECRET",
                "YygoqkLA3lkZeUD14RPy7mLtPWdI6kFtiLWLmd8k");

        if (TextUtils.equals(ENV, "PROD")) {
            twitterConstantsMap.put("CONSUMER_KEY", "Aw1XZJuFwSsGTJldmQ8lQ");
            twitterConstantsMap.put("CONSUMER_SECRET",
                    "oTpKKjsQVghRdAHSyurAewm80rMHK2qwdmKaqIqho");
        } else if (TextUtils.equals(ENV, "STAGING")) {
            twitterConstantsMap
                    .put("CONSUMER_KEY", "e4vlfDhYi5iGRsCL85ErWY5cj");
            twitterConstantsMap.put("CONSUMER_SECRET",
                    "QNUjqaqM3NjhvXSW1FjKMWdPVpquOS6DeuF14J2g0epa3SQYJT");
        } else if (TextUtils.equals(ENV, "DEV")) {
            twitterConstantsMap.put("CONSUMER_KEY", "oYj03yiZs9ngNda4zCZYVA");
            twitterConstantsMap.put("CONSUMER_SECRET",
                    "YygoqkLA3lkZeUD14RPy7mLtPWdI6kFtiLWLmd8k");
        }
        return twitterConstantsMap;
    }

    public static HashMap<String, String> linkedin_Constants() {
        HashMap<String, String> linkedInConstantsMap = new HashMap<String, String>();

        // default to dev

        linkedInConstantsMap.put("LINKEDIN_KEY", "75hy2jjc2oym8n");
        linkedInConstantsMap.put("LINKEDIN_SECRET", "MsymFfOGtoKPjZJC");

        if (TextUtils.equals(ENV, "PROD")) {
            linkedInConstantsMap.put("LINKEDIN_KEY", "75b8kgu419s1eb");
            linkedInConstantsMap.put("LINKEDIN_SECRET", "G4mFDoieDuORsZaW");
        } else if (TextUtils.equals(ENV, "STAGING")) {
            linkedInConstantsMap.put("LINKEDIN_KEY", "75n4xsgwguag4o");
            linkedInConstantsMap.put("LINKEDIN_SECRET", "FtVzmjMJanq6NW2w");
        } else if (TextUtils.equals(ENV, "DEV")) {
            linkedInConstantsMap.put("LINKEDIN_KEY", "75hy2jjc2oym8n");
            linkedInConstantsMap.put("LINKEDIN_SECRET", "MsymFfOGtoKPjZJC");
        }

        linkedInConstantsMap
                .put("LINKEDIN_PERMISSION",
                        "r_fullprofile r_emailaddress w_messages rw_nus rw_groups r_network r_contactinfo rw_company_admin r_basicprofile r_basicprofile");

        return linkedInConstantsMap;

    }

    public static String facebookAppId_Constant() {
        String appId = "190754931120783";// default to dev

        if (TextUtils.equals(ENV, "PROD")) {
            appId = "1412435819002904";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            appId = "568748469900049";
        } else if (TextUtils.equals(ENV, "DEV")) {
            appId = "190754931120783";
        }
        return appId;

    }

    public static String hockeyId_Constant() {
        // default to dev

        String appId = "8f8b75f4b071b0f910253289c6d857d8";
        if (TextUtils.equals(ENV, "PROD")) {
            appId = "5407f32ddc7b41795d8f22ea0d04adc9";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            appId = "e32b9c74e1e3540d683bf1c7fb24b0de";
        } else if (TextUtils.equals(ENV, "DEV")) {
            appId = "8f8b75f4b071b0f910253289c6d857d8";
        }
        return appId;

    }

    public static String IP() {
        String appId = "50.116.60.99";// default to dev
        if (TextUtils.equals(ENV, "PROD")) {
            appId = "23.239.24.213";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            appId = "173.255.199.238";
        } else if (TextUtils.equals(ENV, "DEV")) {
            appId = "50.116.60.99";
        }
        return appId;

    }

    public static String SFX_IP() {
        String appId = "50.116.60.99";// default to dev
        if (TextUtils.equals(ENV, "PROD")) {
            appId = "23.239.13.195";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            appId = "173.255.199.238";
        } else if (TextUtils.equals(ENV, "DEV")) {
            appId = "50.116.60.99";
        }
        return appId;

    }

    public static String GCM_SENDER_Id_Constant() {
        String appId = "229022141858";// default to dev
        if (TextUtils.equals(ENV, "PROD")) {
            appId = "238625476820";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            appId = "354371483578";
        } else if (TextUtils.equals(ENV, "DEV")) {
            appId = "229022141858";
        }
        return appId;

    }

    public static String CONTENT_PROVIDER_AUTHORITY_NAME() {
        String packageName = "com.mobicules.wecam.contentprovider";// default to
                                                                   // dev
        if (TextUtils.equals(ENV, "PROD")) {
            packageName = "com.wecamchat.wecam.contentprovider";
        } else if (TextUtils.equals(ENV, "STAGING")) {
            packageName = "com.wecamchat.wecamstaging.contentprovider";
        } else if (TextUtils.equals(ENV, "DEV")) {
            packageName = "com.mobicules.wecam.contentprovider";
        }
        return packageName;

    }

    /**
     * 
     * @author abhilash holds the login types available
     */
    public enum LoginType {
        facebook(0), googlePlus(1), twitter(2), linkdn(3), email(4);

        private int value;

        private LoginType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

    }

    public interface LoginConstants {
        String GOOGLE_PLUS = "#gplus";
        String TWITTER = "#twtr";
        String LINKEDIN = "#linkedIn";
        String FACEBOOK = "#fb";
        String EMAIL = "#email";
    }

    public enum HelpVideoStatusUpdateType {
        seen(1), deleted(2);

        private int value;

        private HelpVideoStatusUpdateType(int value) {
            this.setValue(value);
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public enum AccountTypes {
        facebook("0"), gplus("1"), twtr("2"), wecam("3"), lnkd("4"), socialac(
                "5"), emailcontact("6");

        private String value;

        private AccountTypes(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    // constant for start activity for result
    public interface ActivityResultConstants {
        // int LOCAL_FRIENDS_SEARCH = 0;
        // int GLOBAL_FRIENDS_RESULT = 2;
        int LOCATION_SEARCH_REQST_CODE = 3;
        // int INVITE_FRIEND_PROFILE = 4;
        // int LOCAL_FRIEND_PROFILE = 5;
        // int GLOBAL_FRIEND_PROFILE = 6;
    }

    public enum UserListSources {
        friends, tags
    }

    public interface LOCATION_CONSTANTS {

        long MINIMUM_TIME_INTERVAL = 300000;// 5 min
        float MINIMUM_DISTANCE = 1000;// 1km
    }

    public enum SideMenuNavOptions {
        userprofile, notifications, tags, socialaccounts, aboutUs, none, trending;
    }

    public interface IN_APP_PURCHASE_CONSTANTS {
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxAaU+Q/auW5+t+nl5o1sC4gpiuvUaKb6fKw00BWk4dLIQffsxTMp8Ukd6MzeJOnNIAvcStIEi/owX+emo2fqJBIB7P2EqDUOyQOlnhjiG3wZwjoDLYMO2nWltFeryDnBJJtmIUf+1VW1A48wmPLWShtesP1GHsd2zApHFtyLTeAYNwp1v2QXnPZLlmFWwNQ4op1h0zrmmS0+hG2+JWZnhP1YGN16XJOKJBGJ3txl3+x+ZCUh8nGMwxCoGiLRqkUYQTvSfQd3EQLNzquiXGiIMiEAtigyDOeny5r/b+rxEqb6qxb+yUrIIZdzbMXiVRvzogyx6iAu9teJzWrbrWj/wQIDAQAB";
        String SKU_REMOVE_ADS = "remove_ads.1";
    }

    public interface APP_INVITE_MESSAGE {
        String INVITE_MESAGE = "Check out WeCam. New social video chat app I'm using to video call with friends. Free download for iPhone or Android WeCamChat.com";
        String WECAM_LINK = "WeCamChat.com";
    }

    public interface IAPP_VERSION {
        String APP_VERSION_KEY = "appVer";

        String ANDROID_VERSION_FLAG = "1";
    }

    public interface RangebarThumbs {
        int LEFT_INDEX = 4;
        int RIGHT_INDEX = 37;
    }

    public interface NearMeMode {
        int LIST = 0;
        int MAP = 1;
        int GRID = 2;
    }

    public interface MapSelectionMode {
        int PUBLIC_TO_ALL = 0;
        int FRIENDS_ONLY = 1;
    }

    public enum FontFace {
        AlteHaas, HelveticaWorld, HelveticaRegular, HelveticaBold, HelveticaItalic
    }

    public long DOB_TIMESTAMP_OFFSET = 567648000000l;

    public enum SignUPStateTypes {
        signUp("0"), addAccount("1"), addTag("2");

        private String value;

        private SignUPStateTypes(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum SocialFriendType {
        fb(1), twitter(3), linkedIn(4), gPlus(2);

        private int value;

        private SocialFriendType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static int MESSAGE_LIST_DISTANCE_OFFSET = 5;

    public interface LOGGER {
        String appName = "WeCam";
        boolean shouldUploadToServer = true;
        boolean shouldSaveInSD = true;
        boolean shoulsSaveInFile = true;
    }

    public static String timeOutMssg = "Unable to communicate with the server, please try again later.";
    public static String internetNotAvailable = "Please check internet connection.";
    public static String some_error_occured = "Some error occured, Please try again later.";

}
