package com.gek.and.project4;

import java.util.Calendar;

public interface TimeLogData {
	public String getLogId();
	public TimeLogDataType getLogDataType();
	public Calendar getLogTime();
}
