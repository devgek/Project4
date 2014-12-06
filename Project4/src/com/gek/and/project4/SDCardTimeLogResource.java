package com.gek.and.project4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

public class SDCardTimeLogResource implements TimeLogResource {
	private static final String LOGFILE_ROOT = "/Project4";
	private static final String LOGFILE_NAME = "project4.txt";
	
	private File getLogFileOld() throws IOException {
		File sdcardRoot = new File(LOGFILE_ROOT);
		
		if (!sdcardRoot.exists()) {
			sdcardRoot.mkdirs();
		}

		File logFile = new File(sdcardRoot, LOGFILE_NAME);
		
		if (!logFile.exists()) {
			logFile.createNewFile();
		}
		
		return logFile;
 	}
	
	private File getLogFile() throws IOException {
		String state = Environment.getExternalStorageState();
		if (!Environment.MEDIA_MOUNTED.equals(state)){
			throw new IOException("ExternalStorageState is " + state);
		}
		
		File sdcardRoot = new File(Environment.getExternalStorageDirectory() + LOGFILE_ROOT);
		
		if (!sdcardRoot.exists()) {
			sdcardRoot.mkdirs();
		}
		File logFile = new File(sdcardRoot, LOGFILE_NAME);
		
		if (!logFile.exists()) {
			logFile.createNewFile();
		}
		
		return logFile;
 	}
	@Override
	public List<TimeLogEntry> getLogEntrys(Class logEntryClass) throws Exception {
		List<TimeLogEntry> logEntrys = new ArrayList<TimeLogEntry>();
		
		FileReader fr = new FileReader(getLogFile());
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		while ((line = br.readLine()) != null) {
			TimeLogEntry logEntry = (TimeLogEntry) logEntryClass.newInstance();
			
			logEntry.setLogString(line);
			
			logEntrys.add(logEntry);
		}
		
		br.close();
		
		return logEntrys;
	}

	@Override
	public void saveLogEntry(TimeLogEntry logEntry) throws Exception {
		FileWriter fw = new FileWriter(getLogFile(), true);
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(logEntry.getLogString());
		bw.newLine();
		
		bw.close();
	}

}
