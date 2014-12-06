package com.gek.and.project4;

public interface TimeLogEntry {
	public String getLogString();
	public String getTimeStamp();
	public void setLogString(String logString) throws Exception;
	public TimeLogData getLogData() throws Exception;
}
