package com.gek.and.project4;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TimeLogUtil {
	public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getTimestampFormatted() {
		return df.format(System.currentTimeMillis());
	}
	
	public static Calendar parse(String logTime) throws Exception{
		Date d = df.parse(logTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		
		return cal;
	}
	
	public static List<TimeLogEntry> getLogEntrys() {
		TimeLogResource logResource = new SDCardTimeLogResource();
		
		List<TimeLogEntry> logEntrys;
		try {
			logEntrys = logResource.getLogEntrys(SimpleCSVTimeLogEntry.class);
		}
		catch (Exception e) {
			logEntrys = Collections.EMPTY_LIST;
		}
		
		Collections.sort(logEntrys, new TimeLogEntryComparator());
		
		return logEntrys;
	}
	

}
