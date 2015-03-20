package com.gek.and.project4.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.util.Log;

import com.gek.and.project4.dao.BookingDao;
import com.gek.and.project4.dao.BookingDao.Properties;
import com.gek.and.project4.entity.Booking;
import com.gek.and.project4.types.PeriodType;
import com.gek.and.project4.util.DateUtil;

import de.greenrobot.dao.query.QueryBuilder;

public class BookingService {
	private static final String TAG = "Project4:BookingService::";
	private BookingDao bookingDao;
	
	public BookingService(BookingDao bookingDao) {
		this.bookingDao = bookingDao;
	}
	
	public boolean bookNow(long projectId) {
		boolean bookedStart = false;
		Booking lastOpenBooking = getLastOpenBooking();
		if (lastOpenBooking != null) {
			bookStop(lastOpenBooking);
		}
		
		if (lastOpenBooking == null || !lastOpenBooking.getProjectId().equals(projectId)) {
			Booking newBooking = bookStart(projectId);
			bookedStart = true;
		}
		return bookedStart;
	}
	
	public Booking getLastOpenBooking() {
		QueryBuilder<Booking> qb = this.bookingDao.queryBuilder();
		qb.where(Properties.To.isNull()).orderDesc(Properties.From);
		return qb.unique();
	}

	public List<Booking> getFiltered(PeriodType periodType, Long projectId) {
		List<Booking> bookingList;
		
		switch (periodType) {
		case TODAY: bookingList = getToday();break;
		case WEEK: bookingList = getThisWeek();break;
		case MONTH: bookingList = getThisMonth();break;
		case YEAR: bookingList = getThisYear();break;
		default: bookingList = new ArrayList<Booking>();
		}
		
		if (projectId != null && projectId > -1) {
			bookingList = filterProject(bookingList, projectId);
		}
		return bookingList;
	}
	
	private List<Booking> filterProject(List<Booking> bookingList, Long projectId) {
		List<Booking> filteredList = new ArrayList<Booking>();
		
		for (Booking booking : bookingList) {
			if (booking.getProjectId().equals(projectId)) {
				filteredList.add(booking);
			}
		}
		
		return filteredList;
	}

	public List<Booking> getToday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.AM_PM, 0);
		
		Log.i(TAG, "compare date today: " + cal.toString());
		Log.i(TAG, "compare date today formatted: " + DateUtil.getFormattedDateTime(cal.getTime()));

		return getGreaterEqual(cal);
	}
	
	public List<Booking> getThisWeek() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.AM_PM, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		Log.i(TAG, "compare date week formatted: " + DateUtil.getFormattedDateTime(cal.getTime()));
		return getGreaterEqual(cal);
	}
	
	public List<Booking> getThisMonth() {
		Calendar calFrom = Calendar.getInstance();
		int year = calFrom.get(Calendar.YEAR);
		int month = calFrom.get(Calendar.MONTH);
		calFrom.clear();
		calFrom.set(Calendar.YEAR, year);
		calFrom.set(Calendar.MONTH, month);
		
		Calendar calTo = Calendar.getInstance();
		calTo.clear();
		calTo.set(Calendar.YEAR, year);
		calTo.set(Calendar.MONTH, month + 1);
		
		return getBeetween(calFrom, calTo);
	}
	
	public List<Booking> getThisYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		cal.clear();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return getGreaterEqual(cal);
	}
	
	private List<Booking> getGreaterEqual(Calendar compareDate) {
		QueryBuilder<Booking> qb = this.bookingDao.queryBuilder();
//		qb.where(Properties.From.ge(cal.getTime())).where(Properties.To.isNotNull()).orderAsc(Properties.From);
		qb.where(Properties.From.ge(compareDate.getTime())).orderDesc(Properties.From);
		return qb.list();
	}
	
	private List<Booking> getBeetween(Calendar fromDate, Calendar toDate) {
		QueryBuilder<Booking> qb = this.bookingDao.queryBuilder();
//		qb.where(Properties.From.ge(cal.getTime())).where(Properties.To.isNotNull()).orderAsc(Properties.From);
		qb.where(Properties.From.ge(fromDate.getTime())).where(Properties.From.lt(toDate.getTime())).orderDesc(Properties.From);
		return qb.list();
	}
	
	public Booking bookStart(long projectId) {
		Booking start = new Booking();
		start.setProjectId(projectId);
		start.setFrom(new Date());
		long bookingId = this.bookingDao.insert(start);
		if (bookingId > 0) {
			Log.i(TAG, "Project started at:" + start.getFrom());
			return this.bookingDao.load(bookingId);
		}
		else {
			return null;
		}
	}

	public Booking bookStop(Booking lastOpenBooking) {
		lastOpenBooking.setTo(new Date());
		lastOpenBooking.setMinutes(DateUtil.getMinutes(lastOpenBooking.getFrom(), lastOpenBooking.getTo()));
		this.bookingDao.update(lastOpenBooking);
		Log.i(TAG, "Project stopped at:" + lastOpenBooking.getTo());
		return lastOpenBooking;
	}

}
