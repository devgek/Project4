package com.gek.and.project4.util;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.gek.and.project4.AppConstants;
import com.gek.and.project4.TimeLogViewer;

public class ActivityUtil {
	private static final String SETTINGS_FILE = "project4-settings";

	public static void logThis(Activity activity, String logString) {
		Intent serviceIntent = new Intent(AppConstants.INTENT_NAME_SERVICE);
		
		serviceIntent.putExtra(AppConstants.LOG_PAR_NAME, logString);
		
		activity.getApplicationContext().startService(serviceIntent);
	}

	public static Map<String, ?> getAllSettings(Activity activity) {
		SharedPreferences sharedPrefs = getSharedPrefs(activity);
		return sharedPrefs.getAll();
	}
	
	public static SharedPreferences getSharedPrefs(Activity activity) {
		return activity.getSharedPreferences(SETTINGS_FILE, Activity.MODE_WORLD_READABLE);
	}

	public static void startActivity(Context context, Class activityClass) {
    	Intent intent = new Intent(context, activityClass);
    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
