package com.gek.and.project4;

import java.util.Calendar;

public class SimpleCSVTimeLogData implements TimeLogData {
	private String logId;
	private TimeLogDataType logDataType;
	private Calendar logTime;
	
	public SimpleCSVTimeLogData(SimpleCSVTimeLogEntry logEntry) throws Exception {
		if (logEntry.getLogString() == null || logEntry.getLogTime() == null) {
			throw new IllegalArgumentException("Cannot create SimpleCSVTimeLogData:" + logEntry);
		}
		
		String[] parts = logEntry.getLogText().split(SimpleCSVTimeLogEntry.FIELD_SEPARATOR);
		if (parts.length != 3) {
			throw new IllegalStateException("Cannot split SimpleCSVTimeLogEntry");
		}
		
		this.logId = parts[0];
		this.logDataType = TimeLogDataType.parse(parts[1]);
		this.logTime = TimeLogUtil.parse(logEntry.getLogTime());
	}
	
	@Override
	public String getLogId() {
		return this.logId;
	}

	@Override
	public TimeLogDataType getLogDataType() {
		return this.logDataType;
	}

	@Override
	public Calendar getLogTime() {
		return this.logTime;
	}

}
