package com.gek.and.project4.app;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gek.and.project4.entity.Booking;
import com.gek.and.project4.model.BookedValues;
import com.gek.and.project4.util.DateUtil;

public class Summary {
	private Booking runningNow;
	private int runningMinutes;
	
	private int minutesYear;
	private int minutesMonth;
	private int minutesWeek;
	private int minutesDay;
	private Calendar initDate;
	private int initYear;
	private int initMonth;
	private int initDay;
	private int initWeek;
	private Map<Long, BookedValues> projectsToday;
	private Map<Long, BookedValues> projectsWeek;
	private Map<Long, BookedValues> projectsMonth;
	private boolean loaded;
	
	public Summary() {
		init();
	}
	
	public void init() {
		this.loaded = false;
		this.initDate = Calendar.getInstance();
		this.initYear = initDate.get(Calendar.YEAR);
		this.initMonth = initDate.get(Calendar.MONTH);
		this.initWeek = initDate.get(Calendar.WEEK_OF_YEAR);
		this.initDay = initDate.get(Calendar.DAY_OF_YEAR);
		this.minutesYear = 0;
		this.minutesMonth = 0;
		this.minutesWeek = 0;
		this.minutesDay = 0;
		this.runningNow = null;
		this.runningMinutes = 0;
		this.projectsMonth = new HashMap<Long, BookedValues>();
		this.projectsWeek = new HashMap<Long, BookedValues>();
		this.projectsToday = new HashMap<Long, BookedValues>();
	}
	
	public void loadNew(List<Booking> bookings) {
		this.init();
		
		for (Booking booking : bookings) {
			addBooking(booking);
		}
		
		this.loaded = true;
	}
	
	public void addBooking(Booking booking) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(booking.getFrom());
		
		if (booking.getTo() == null) {
			runningNow = booking;
			runningMinutes = DateUtil.getMinutes(booking.getFrom(), Calendar.getInstance().getTime());
			
			if (isDay(cal)) {
				addToMap(projectsToday, booking.getProjectId(), 0, runningMinutes);
			}
			if (isWeek(cal)) {
				addToMap(projectsWeek, booking.getProjectId(), 0, runningMinutes);
			}
			if (isMonth(cal)) {
				addToMap(projectsMonth, booking.getProjectId(), 0, runningMinutes);
			}
		}
		else {
			if (isDay(cal)) {
				minutesDay += booking.getMinutes();
				addToMap(projectsToday, booking.getProjectId(), booking.getMinutes(), 0);
			}
			if (isWeek(cal)) {
				minutesWeek += booking.getMinutes();
				addToMap(projectsWeek, booking.getProjectId(), booking.getMinutes(), 0);
			}
			if (isMonth(cal)) {
				minutesMonth += booking.getMinutes();
				addToMap(projectsMonth, booking.getProjectId(), booking.getMinutes(), 0);
			}
//			if (isYear(cal)) {
//				minutesYear += booking.getMinutes();
//			}
		}
	}
	
	public int getMinutesYear() {
		return minutesYear + runningMinutes;
	}

	public int getMinutesMonth() {
		return minutesMonth + runningMinutes;
	}
	
	public int getMinutesWeek() {
		return minutesWeek + runningMinutes;
	}

	public int getMinutesDay() {
		return minutesDay + runningMinutes;
	}
	
	public String getFormattedDay() {
		return DateUtil.getFormattedHM(minutesDay + runningMinutes);
	}
	
	public String getFormattedWeek() {
		return DateUtil.getFormattedHM(minutesWeek + runningMinutes);
	}
	
	public String getFormattedMonth() {
		return DateUtil.getFormattedHM(minutesMonth + runningMinutes);
	}

	public Booking getRunningNow() {
		return runningNow;
	}

	public void setRunningNow(Booking booking) {
		this.runningNow = booking;
		this.runningMinutes = 0;
	}
	
	public Map<Long, BookedValues> getProjectsToday() {
		return projectsToday;
	}

	public Map<Long, BookedValues> getProjectsWeek() {
		return projectsWeek;
	}

	public Map<Long, BookedValues> getProjectsMonth() {
		return projectsMonth;
	}

	public Calendar getInitDate() {
		return initDate;
	}

	public boolean isLoaded() {
		return loaded;
	}

	private boolean isYear(Calendar cal) {
		return (cal.get(Calendar.YEAR) == initYear);
	}

	private boolean isMonth(Calendar cal) {
		return (cal.get(Calendar.MONTH) == initMonth);
	}
	
	private boolean isWeek(Calendar cal) {
		return (cal.get(Calendar.WEEK_OF_YEAR) == initWeek);
	}

	private boolean isDay(Calendar cal) {
		return (cal.get(Calendar.DAY_OF_YEAR) == initDay);
	}
	
	private void addToMap(Map<Long, BookedValues> theMap, Long key, int finished, int running) {
		BookedValues values = null;
		
		if (theMap.get(key) != null) {
			values = theMap.get(key);
		}
		else {
			values = new BookedValues();
			theMap.put(key, values);
		}
		
		values.addFinished(finished);
		values.setRunning(running);
	}
	

}
