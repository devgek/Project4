package com.gek.and.project4.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static final DateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");
	public static final DateFormat dfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String getFormattedDateTime(Date d) {
		return dfDateTime.format(d);
	}

	public static String getFormattedDate(Date d) {
		return dfDate.format(d);
	}
	
	public static String getFormattedTime(Date d) {
		return d != null ? dfTime.format(d) : "";
	}
	
	public static String getFormattedHM(int minutes) {
		int hours = (int) Math.floor(minutes / 60);
		int mins = minutes - (hours * 60);
		return formatHM(hours, mins);
	}
	
	private static String formatHM(int hours, int minutes) {
		return String.format("%02d:%02d", hours, minutes);
	}
}
