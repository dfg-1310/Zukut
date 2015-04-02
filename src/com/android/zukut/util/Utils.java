package com.android.zukut.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.android.zukut.R;
import com.android.zukut.util.AppConstant.FontFace;

public class Utils {

	static Typeface AlteHaas;
	static Typeface HelveticaWorld;
	static Typeface HelveticaRegular;
	static Typeface HelveticaBold;
	static Typeface HelveticaItalic;
	static NotificationManager mNotificationManager;
	static RemoteViews contentView;
	static final int NOTIFICATION_ID = 1;

	public static String getSelectedValue(String[] args, int position) {

		try {
			return args[position];
		} catch (Exception e) {
			return "";
		}

	}

	public static Typeface getTypeface(FontFace name, Context context) {

		switch (name) {
		case AlteHaas:
			if (AlteHaas == null) {
				AlteHaas = Typeface.createFromAsset(context.getAssets(),
						"AlteHaasGroteskBold.ttf");
			}
			return AlteHaas;

		case HelveticaRegular:
			if (HelveticaRegular == null) {
				HelveticaRegular = Typeface.createFromAsset(
						context.getAssets(), "HelveticaNeueRegular.ttf");
			}
			return HelveticaRegular;

		case HelveticaBold:
			if (HelveticaBold == null) {
				HelveticaBold = Typeface.createFromAsset(context.getAssets(),
						"HelveticaNeueBold.ttf");
			}
			return HelveticaBold;

		case HelveticaWorld:
			if (HelveticaWorld == null) {
				HelveticaWorld = Typeface.createFromAsset(context.getAssets(),
						"HelveticaWorldRegular.ttf");
			}
			return HelveticaWorld;

		case HelveticaItalic:
			if (HelveticaItalic == null) {
				HelveticaItalic = Typeface.createFromAsset(context.getAssets(),
						"HelveticaNeueItalic.ttf");
			}
			return HelveticaItalic;

		default:
			return null;
		}
	}
}
