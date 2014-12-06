package com.gek.and.project4;

import java.util.Comparator;

public class TimeLogEntryComparator implements Comparator<TimeLogEntry> {

	@Override
	public int compare(TimeLogEntry object1, TimeLogEntry object2) {
		return object2.getTimeStamp().compareTo(object1.getTimeStamp());
	}

}
