package com.gek.and.project4;

public class SimpleCSVTimeLogEntry implements TimeLogEntry {
	public static final String FIELD_SEPARATOR = ";";
	private String logText;
	private String logTime;
	private TimeLogData logData;
	
	public SimpleCSVTimeLogEntry(){
	}
	
	public SimpleCSVTimeLogEntry(String logText) {
		this.logText = logText;
		this.logTime = TimeLogUtil.getTimestampFormatted();
	}
	
	public String getLogText() {
		return logText;
	}

	public void setLogText(String logText) {
		this.logText = logText;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	@Override
	public String getLogString() {
		return getLogText() + FIELD_SEPARATOR + getLogTime();
	}

	@Override
	public void setLogString(String logString) throws Exception {
		String[] fields = logString.split(FIELD_SEPARATOR);
		
		if (fields.length < 2) {
			throw new Exception("Logstring must be: field1;field2");
		}
		
		logText = fields[0];
		logTime = fields[1];
	}

	@Override
	public String getTimeStamp() {
		return logTime;
	}

	@Override
	public String toString() {
		return getLogString();
	}

	@Override
	public TimeLogData getLogData() throws Exception {
		if (this.logData == null) {
			this.logData = parseLogData();
		}
		
		return this.logData;
	}

	private TimeLogData parseLogData() throws Exception {
		TimeLogData logData = new SimpleCSVTimeLogData(this);
		
		return logData;
	}

}
