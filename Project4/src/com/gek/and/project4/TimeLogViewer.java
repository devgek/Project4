package com.gek.and.project4;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class TimeLogViewer extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			updateLogView();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void updateLogView() throws Exception {
        List<TimeLogEntry> logEntrys = TimeLogUtil.getLogEntrys();
		
		ArrayAdapter adapter = new ArrayAdapter(this, R.layout.rowview, logEntrys.toArray());
		
		setListAdapter(adapter);
	}
}
