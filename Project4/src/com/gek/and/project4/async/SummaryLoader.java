package com.gek.and.project4.async;

import java.util.List;

import android.os.AsyncTask;

import com.gek.and.project4.activity.ProjectCardActivity;
import com.gek.and.project4.app.Project4App;
import com.gek.and.project4.app.Summary;
import com.gek.and.project4.entity.Booking;
import com.gek.and.project4.service.BookingService;


public class SummaryLoader extends AsyncTask<Object, Void, Void> {
	private ProjectCardActivity parentActivity;
	
	@Override
	protected Void doInBackground(Object... params) {
		parentActivity = (ProjectCardActivity) params[0];
		
		BookingService bookingService = Project4App.getApp(parentActivity).getBookingService();
		List<Booking> bookings = bookingService.getThisYear();
		
		Summary summary = Project4App.getApp(parentActivity).getSummary();
		summary.reset();
		
		for (Booking booking : bookings) {
			summary.addBooking(booking);
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		parentActivity.onPostSummaryLoad();
	}

}
