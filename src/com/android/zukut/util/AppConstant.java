package com.android.zukut.util;



public class AppConstant {

	public static final String USER_LOGGED_IN = "user_logged_in";
	public static final String GCM_API_KEY = "AIzaSyDminYojH0zxgUeydc_pr1poCQFh-wXNoA";
	public static final String GCM_PROJECT_NUMBER = "41108754588";
	public static final String FULL_NAME = "fullname";
	public static final String MOBILE_NUMBER = "mobilenumber";
	public static final String ID = "id";
	public static final String DEVICE_ID = "deviceid";
	public static final String DEVICE_TOKEN = "devicetoken";
	public static final String OPEN_TOK_API_KEY = "44565882";
	  public static long RINGTONE_DELAY = 45000;

	public interface IErrorCode {
		String defaultErrorCode = "ER001";
		String notInternetConnErrorCode = "ER002";
	}

	public interface IErrorMessage {
		String defaultErrorMessage = "Some error occured, Please try again.";
		String notInternetConnectMessage = "Please check internet connection.";
	}

	public interface INTENT_EXTRAS {
		String FRIEND_ID = "friend_id";
		String GCM_RESPONSE = "gcm_response";
		String FRIEND_NAME = "friendName";
		String FRIEND_ADDRESS = "friendAddress";
		String FRIEND_ONLINE_STATUS = "friend_status";
		String FRIEND = "friend";
		String INVITE_FRIEND = "invite_friend";
		String USER_PROFILE = "user_profile";

		String SEARCH_FILTER_AGE_TO = "search_filter_age_to";
		String SEARCH_FILTER_AGE_FROM = "search_filter_age_from";
		String SEARCH_FILTER_GENDER = "search_filter_gender";
		String SEARCH_FILTER_TEXT = "search_filter_text";
		String SEARCH_FILTER_STYPE = "search_filter_stype";
		String SEARCH_FILTER_INTERESTED_IN = "search_filter_interested_in";
		String SEARCH_FILTER_RELATIONSHIP = "search_filter_relationship";
		String SEARCH_FILTER_LOCATION = "search_filter_location";
		String USER_SEARCH_TYPE_ENUM = "user_search_enum_type";
		String TAG_STRING = "tag";
		String IS_FRIEND = "isFriend";
		String FRIEND_IMAGE_ID = "friend_img";
		String TAGS_LIST = "tagsList";
		String SUGGESTED_TAGS_LIST = "suggestedTagsList";
		String FOOTER_SELECTION_ENUM = "footer_selection_enum";
		String FRIEND_LIST_ARRAY = "friendListArray";

		String VIDEO_URL = "video_url";
		String NOTIFICATION_ID = "notificationId";
		String SEEN_STATUS = "seenStatus";

		String CALL_DETAIL = "callDetail";
		String MAKE_CALL = "makeCall";
		String RESPOSE = "response";

		String FILE_PATH = "filepath";
		String RESULT = "result";

		String IS_USER_INFO_STORED = "is_user_info_stored";

		String RTC_TOKEN = "rtc_token";
		String DEVICE_ID = "device_id";
		String NEW_UNREAD_MSG = "new_unread_msg";
		String RTC_SESSION = "rtc_session";
		String RTC_NEW_MESSGE = "rtc_new_msg";
		String RTC_HISTORY_LAST_TIMESTAMP = "chatHistoryTimestamp";
		String RTC_HISTORY_COUNT = "chatHistoryCount";
		String RTC_ROOM_ID = "rtcRoomId";
		String RTC_CHAT_HISTORY = "rtc_chat_history";
	}

	public static String getStartRingBroadCastAction() {
		String broadCastAction = "com.android.zukut.stop_ring";// default
		return broadCastAction;
	}

	public static String getStopRingBroadCastAction() {
		String broadCastAction = "com.android.zukut.start_ring";// default
		return broadCastAction;
	}

	public static String getCallScreenViewBroadCastAction() {
		String broadCastAction = "com.android.zukut.call_screen_view";// default
		return broadCastAction;
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

	public enum FontFace {
		AlteHaas, HelveticaWorld, HelveticaRegular, HelveticaBold, HelveticaItalic
	}

	   public static String getCallPopUpViewActionBroadCastAction() {
	        String broadCastAction = "com.android.zukut.incoming_call_pop_up_screen_view";// default
	        return broadCastAction;

	    }
	
}
