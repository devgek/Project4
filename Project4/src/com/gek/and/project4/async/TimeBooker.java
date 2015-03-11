package com.gek.and.project4.async;

import android.os.AsyncTask;

import com.gek.and.project4.activity.ProjectCardActivity;
import com.gek.and.project4.app.Project4App;
import com.gek.and.project4.service.BookingService;


public class TimeBooker extends AsyncTask<Object, Void, Boolean> {
	private ProjectCardActivity parentActivity;
	Long projectId;
	
	@Override
	protected Boolean doInBackground(Object... params) {
		parentActivity = (ProjectCardActivity) params[0];
		projectId = (Long) params[1];
		
		BookingService bookingService = Project4App.getApp(parentActivity).getBookingService();
		boolean bookedStart = bookingService.bookNow(projectId);
		
		return Boolean.valueOf(bookedStart);
	}

	@Override
	protected void onPostExecute(Boolean bookedStart) {
		parentActivity.onPostTimeBooking(projectId, bookedStart);
	}

}
