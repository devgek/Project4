package com.gek.and.project4;

import java.util.List;

public interface TimeLogResource {
	public void saveLogEntry(TimeLogEntry logEntry) throws Exception;
	List<TimeLogEntry> getLogEntrys(Class logEntryClass) throws Exception;
}
