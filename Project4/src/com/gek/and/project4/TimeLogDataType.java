package com.gek.and.project4;

public enum TimeLogDataType {
	FROM("f"), TO("t");
	private TimeLogDataType(String logType) {
		this.logType = logType;
	}
	
	public static TimeLogDataType parse(String logType) {
		if ("f".equals(logType)) return FROM;
		if ("t".equals(logType)) return TO;
		
		throw new IllegalArgumentException("Cannot parse logType " + logType);
	}
	
	public String getLogType() {
		return this.logType;
	}
	
	private String logType;
}
