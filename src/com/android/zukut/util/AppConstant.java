package com.android.zukut.util;

public class AppConstant {

	public static final String USER_LOGGED_IN = "user_logged_in";
	public static final String GCM_API_KEY = "AIzaSyDminYojH0zxgUeydc_pr1poCQFh-wXNoA";
	public static final String GCM_PROJECT_NUMBER = "41108754588";

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

}
