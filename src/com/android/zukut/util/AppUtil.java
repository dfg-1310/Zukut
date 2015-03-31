package com.android.zukut.util;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.os.SystemClock;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by tsingh on 21/1/15.
 */
public class AppUtil {

	private static long diff = 0L;

	private static final String LOG_TAG = AppUtil.class.getSimpleName();

	public static <T> T parseJson(String json, Class<T> tClass) {
		return new Gson().fromJson(json, tClass);
	}

	public static <T> T parseJson(JsonElement result, Class<T> tClass) {
		return new Gson().fromJson(result, tClass);
	}
	
	public static <T> T parseJson(String json, Type type) {
		return new Gson().fromJson(json, type);
	}

	public static JsonObject parseJson(String response) {
		JsonObject jo = null;
		JsonElement e = null;
		return new JsonParser().parse(response).getAsJsonObject();
	}

	public static JsonArray getJsonElement(String s) {
		return new JsonParser().parse(s).getAsJsonArray();
	}

	public static String getExt(String fileName) {
		String fileFormat = "";
		fileFormat = (fileName.split("[.]")[1]);
		return fileFormat;
	}

	public static String getJson(Object profile) {
		return new Gson().toJson(profile);
	}

	public static String getCommaSeparated(ArrayList<String> list) {
		StringBuffer buffer = new StringBuffer();
		if (list == null || list.size() < 1) {
			return "";
		}
		buffer.append(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			buffer.append(", ");
			buffer.append(list.get(i));
		}
		return buffer.toString();
	}

//	public static ErrorObject getUserNotLoggedInError(Context context) {
//		return new ErrorObject(AppConstant.ERROR_CODES.LOGIN_REQUIRED, context
//				.getResources().getString(R.string.login_required_message));
//
//	}

	/**
	 * 
	 * @param currentTime
	 *            in ms
	 * @param lastSeen
	 *            in ms
	 * @return
	 */
//	public static Integer getOnlineStatus(Long currentTime, Long lastSeen) {
//		if (currentTime == null || lastSeen == null)
//			return AppConstant.ONLINE_STATUS.OFFLINE;
//		if (currentTime - lastSeen > 2 * 60 * 1000) {
//			return AppConstant.ONLINE_STATUS.OFFLINE;
//		} else {
//			return AppConstant.ONLINE_STATUS.ONLINE;
//		}
//	}
//
//    public static Pos getSanfranciscoPos() {
//        return new Pos(-122.419416,37.774929);
//    }

	public static void setDiff(long l) {
		diff = l;
	}

	/**
	 * returns current time in nano/mili seconds
	 * @param isPubnubUnit
	 * @return
	 */
	public static long getCurrentTime(boolean isPubnubUnit) {
		long val;
		if (diff == 0L) {
			val =  System.currentTimeMillis() * 10000;
		} else {
			val = SystemClock.elapsedRealtime() * 10000 - diff;
		}
		if(isPubnubUnit){
			Log.i(LOG_TAG, "Current server time in nano : "+val + " diff : " + diff, null);
			return val;
		}else{
			Log.i(LOG_TAG, "Current server time in millis  : "+(val/10000)
					+ " diff : " + diff , null);
			return val/10000;
		}
	}

	public static String getUserFirstName(String userName) {
		if (userName != null && userName.contains(" "))
			return userName.substring(0, userName.indexOf(" "));
		else if (userName != null && !userName.contains(" "))
			return userName;
		else
			return "";
	}

	public static Boolean isLastWinkedTimeExpired(Long lastWinkedTime) {
		long ONE_DAY_TIME_IN_MILLI = 86400000;
		if (lastWinkedTime != null) {
			return getCurrentTime(false) - lastWinkedTime >= 4 * ONE_DAY_TIME_IN_MILLI;
		}
		return true;
	}

	public static String getAgeFromMS(Long dob) {
		if (dob == null)
			return null;

		Calendar c = Calendar.getInstance();
		int mCurrentYear = c.get(Calendar.YEAR);
		c.setTimeInMillis(dob);
		int mAddedYear = c.get(Calendar.YEAR);

		return String.valueOf((mCurrentYear - mAddedYear));
	}

	/**
	 * This method finds diff between given date and current time
	 * @return
	 */
	public static String getReadableTime(long milli) {
		//		long milis = date.getTime();
		String msg = null;
		long timeToShow = getMessageTime(milli) ;
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		cal.setTimeInMillis(timeToShow);
		Calendar cal1 = Calendar.getInstance(Locale.getDefault());

		int redcallday, today;

		redcallday = cal.get(Calendar.DAY_OF_YEAR);
		today = cal1.get(Calendar.DAY_OF_YEAR);

		if (redcallday == today) {
			// today
			DateFormat inputFormat = new SimpleDateFormat("HH:mm a");
			msg = inputFormat.format(timeToShow);
			return "Today | "+msg;
		} else {
			// old

			DateFormat inputFormat = new SimpleDateFormat("MMM dd");
			DateFormat inputFormat2 = new SimpleDateFormat("HH:mm a");
			msg = inputFormat.format(timeToShow)  +" | "+inputFormat2.format(timeToShow);
			return msg;
		}
	}
	public static String converdatetime(long millis)
	{
		String msg = null; 
		long diff;
		long timeToShow = getMessageTime(millis);
		Calendar cal=Calendar.getInstance(Locale.getDefault());
		cal.setTimeInMillis(timeToShow);

		diff=(Calendar.getInstance().getTimeInMillis() - cal.getTimeInMillis());
		long temp=diff/60000;
		if(temp>=60)
		{
			temp=temp/60;
			if(temp>=24)
			{
				DateFormat inputFormat = new SimpleDateFormat("MMM dd");
				msg = inputFormat.format(timeToShow);
			}
			else
				msg = "Today";
		}else{
			if(temp<=0){
				msg=" Now ";
			}
			else if(temp==1){
				msg=temp+" min ";
			}
			else{
				msg=temp+" mins ";
			}
		}
		return msg+"";
	}

	public static void showToast(Context mContext, String message) {
		if (mContext != null) {
			Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 
	 * @param time in mili seconds 
	 * @return
	 */
	private static long getMessageTime(long time){
		Log.i(LOG_TAG, "time : " + time, null);
		long d = getCurrentTime(false) - time;
		Log.i(LOG_TAG, "time difference : " + d, null);
		long currentTime = System.currentTimeMillis();
		Log.i(LOG_TAG, "currentTime : " + currentTime, null);
		return currentTime - d;
	}

	   public static double getDistance(double lat1, double lon1, double lat2, 
		    double lon2, boolean isKm) {
		double dist = 0.0;
		double radlat1 = Math.PI * lat1 / 180;
		double radlat2 = Math.PI * lat2 / 180;
		double theta = lon1 - lon2;
		double radtheta = Math.PI * theta / 180;
		dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1)
			* Math.cos(radlat2) * Math.cos(radtheta);
		dist = Math.acos(dist);
		dist = dist * 180 / Math.PI;
		dist = dist * 60 * 1.1515;
		if(isKm){
		    dist = dist * 1.609344;
		}
		return dist; 
	    }

	
	public static String getCallDuration(Long duration) {
		long sec = duration /1000;
		long s = sec % 60;
	    long m = (sec / 60) % 60;
	    long h = (sec / (60 * 60)) % 24;
	    if(h > 0)
	    return String.format("%d:%02d:%02d", h,m,s);
	    else
	    	return String.format("%02d:%02d", m,s);
	}
	
	public static InputFilter[] getEdittextFilters() {
	    InputFilter[] filters = new InputFilter[2];
        filters[0] = new InputFilter(){
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (end > start) {

                    char[] acceptedChars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9','-', '+',' '};

                    for (int index = start; index < end; index++) {                                         
                        if (!new String(acceptedChars).contains(String.valueOf(source.charAt(index)))) { 
                            return ""; 
                        }               
                    }
                }
                return null;
            }

        };
        filters[1]=new InputFilter.LengthFilter(15);
        return filters;
    }
}

