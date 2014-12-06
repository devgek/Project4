package com.gek.and.project4;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Project4Service extends Service {
	public static final String DEBUG_TAG = "Project4Service";
	public static final String DEBUG_PREFIX = DEBUG_TAG + "::";
	private static final int TIMELOG_SERVICE_NOTIFY = 0x1001;
	private static final String NOTIFICATION_TITLE = "Project4Service";
	
	private NotificationManager notifier = null;
	private Notification notification = new Notification();
	
	@Override
	public void onCreate() {
		Log.i(DEBUG_TAG, DEBUG_PREFIX + "onCreate");

		super.onCreate();
		
		notifier = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		notification.icon = android.R.drawable.stat_notify_more;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		String logTime="";
		try {
			Log.i(DEBUG_TAG, DEBUG_PREFIX + "onStart");
			
			super.onStart(intent, startId);
			
			SimpleCSVTimeLogEntry logEntry = new SimpleCSVTimeLogEntry(intent.getStringExtra(AppConstants.LOG_PAR_NAME));
			
			TimeLogResource logResource = new SDCardTimeLogResource();
			
			logResource.saveLogEntry(logEntry);
			
			sendNotification(logEntry.getLogString() + " was logged!");
		}
		catch (Exception e) {
			e.printStackTrace();
			sendNotification("Service failed with:" + e.getMessage());
		}
		finally {
			stopSelf();
		}
	}	

	private void sendNotification(String contentText) {
		try {
			notification.tickerText = contentText;
			
			Intent toLaunch = new Intent(AppConstants.INTENT_NAME_VIEWER);
			
			PendingIntent intentBack = PendingIntent.getActivity(this, 0, toLaunch, 0);
			
			notification.setLatestEventInfo(getApplicationContext(), NOTIFICATION_TITLE, contentText, intentBack);
			
			notifier.notify(TIMELOG_SERVICE_NOTIFY, notification);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
