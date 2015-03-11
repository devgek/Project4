package com.gek.and.project4.activity;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gek.and.project4.R;
import com.gek.and.project4.SettingsActivity;
import com.gek.and.project4.util.ActivityUtil;

public class MainActivity extends Activity {
	private Map<String, ?> settings;

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
		case R.id.action_list_bookings:
			listBookings();
			return true;
		case R.id.action_settings:
			changeSettings();
			return true;
		case R.id.action_help:
			showHelp();
			return true;
		case R.id.action_about:
			showAbout();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		// startActivity(item.getIntent());
		// return true;
	}

	private void listBookings() {
		Intent bookings = new Intent(this, BookingListActivity.class);
		bookings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(bookings);
	}

	private void showAbout() {
		// TODO Auto-generated method stub

	}

	private void showHelp() {
		// TODO Auto-generated method stub

	}

	private void changeSettings() {
		ActivityUtil.startActivity(getApplicationContext(),
				SettingsActivity.class);
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
