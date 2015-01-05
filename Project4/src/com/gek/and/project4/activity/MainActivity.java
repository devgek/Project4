package com.gek.and.project4.activity;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gek.and.project4.R;
import com.gek.and.project4.SettingsActivity;
import com.gek.and.project4.util.ActivityUtil;

public class MainActivity extends Activity {
	private Map<String,?> settings;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_add_project:
	            addProject();
	            return true;
	        case R.id.action_settings:
	            changeSettings();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	    //		startActivity(item.getIntent());
//		return true;
	}

	private void changeSettings() {
    	ActivityUtil.startActivity(getApplicationContext(), SettingsActivity.class);
	}

	private void addProject() {
    	ActivityUtil.startActivity(getApplicationContext(), ProjectDetailActivity.class);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setTitle(R.string.title_project_dashboard);
		readSettings();
	}

	private void readSettings() {
		this.settings = ActivityUtil.getAllSettings(this);
  	}

	
	@Override
	protected void onResume() {
		readSettings();
		super.onResume();
	}

}
