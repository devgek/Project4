package com.gek.and.project4.async;

import android.os.AsyncTask;

import com.gek.and.project4.activity.DashboardActivity;
import com.gek.and.project4.app.Project4App;
import com.gek.and.project4.app.Summary;
import com.gek.and.project4.entity.Booking;
import com.gek.and.project4.service.BookingService;


public class TimeBooker extends AsyncTask<Object, Void, Boolean> {
	private DashboardActivity parentActivity;
	Long projectId;
	
	@Override
	protected Boolean doInBackground(Object... params) {
		Thread.currentThread().setName("TimeBooker");

		parentActivity = (DashboardActivity) params[0];
		projectId = (Long) params[1];
		
		Summary summary = Project4App.getApp(parentActivity).getSummary();
		BookingService bookingService = Project4App.getApp(parentActivity).getBookingService();

		boolean bookStart = summary.getRunningNow() == null || !summary.getRunningNow().getProjectId().equals(projectId);

		if (summary.getRunningNow() != null) {
			Booking stopped = bookingService.bookStop(summary.getRunningNow());
			summary.addBooking(stopped);
			summary.setRunningNow(null);
		}
		
		if (bookStart) {
			Booking newBooking = bookingService.bookStart(projectId);
			summary.addBooking(newBooking);
		}
		
		return Boolean.valueOf(bookStart);
	}

	@Override
	protected void onPostExecute(Boolean bookedStart) {
		parentActivity.onPostTimeBooking(projectId, bookedStart);
	}

}