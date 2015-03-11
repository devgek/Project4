package com.gek.and.project4.app;

import java.util.Calendar;

import com.gek.and.project4.entity.Booking;
import com.gek.and.project4.util.DateUtil;

public class Summary {
	private Booking runningNow;
	private int minutesYear;
	private int minutesMonth;
	private int minutesWeek;
	private int minutesDay;
	private Calendar initDate;
	private int initYear;
	private int initMonth;
	private int initDay;
	private int initWeek;
	
	public Summary() {
		initDate = Calendar.getInstance();
		initYear = initDate.get(Calendar.YEAR);
		initMonth = initDate.get(Calendar.MONTH);
		initWeek = initDate.get(Calendar.WEEK_OF_YEAR);
		initDay = initDate.get(Calendar.DAY_OF_YEAR);
	}
	
	public void addBooking(Booking booking) {
		if (booking.getTo() == null) {
			runningNow = booking;
		}
		else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(booking.getFrom());
			
			if (isDay(cal)) {
				minutesDay += booking.getMinutes();
			}
			if (isWeek(cal)) {
				minutesWeek += booking.getMinutes();
			}
			if (isMonth(cal)) {
				minutesMonth += booking.getMinutes();
			}
			if (isYear(cal)) {
				minutesYear += booking.getMinutes();
			}
		}
	}
	
	public void reset() {
		this.minutesYear = 0;
		this.minutesMonth = 0;
		this.minutesWeek = 0;
		this.minutesDay = 0;
		this.runningNow = null;
	}

	public int getMinutesYear() {
		return minutesYear;
	}

	public int getMinutesMonth() {
		return minutesMonth;
	}
	
	public int getMinutesWeek() {
		return minutesWeek;
	}

	public int getMinutesDay() {
		return minutesDay;
	}
	
	public String getFormattedDay() {
		return DateUtil.getFormattedHM(minutesDay);
	}
	
	public String getFormattedWeek() {
		return DateUtil.getFormattedHM(minutesWeek);
	}
	
	public String getFormattedMonth() {
		return DateUtil.getFormattedHM(minutesMonth);
	}

	public Booking getRunningNow() {
		return runningNow;
	}

	public void setRunningNow(Booking runningNow) {
		this.runningNow = runningNow;
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
	
}
