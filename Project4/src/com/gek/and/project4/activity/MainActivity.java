package com.gek.and.project4.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gek.and.geklib.util.WorkaroundActionOverflow;
import com.gek.and.project4.R;
import com.gek.and.project4.app.Project4App;

public class MainActivity extends Activity {
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.action_list_bookings) {
			listBookings();
			return true;
		} else if (itemId == R.id.action_settings) {
			changeSettings();
			return true;
		} else if (itemId == R.id.action_about) {
			showAbout();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	private void listBookings() {
		Intent bookings = new Intent(this, BookingListActivity.class);
		bookings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(bookings);
	}

	private void showAbout() {
		Intent about = new Intent(this, com.gek.and.geklib.activity.DefaultAboutActivity.class);
		
		Project4App theApp = Project4App.getApp(this);
		
		about.putExtra("gek_about_appName", getResources().getString(R.string.app_name));
		about.putExtra("gek_about_icon", R.drawable.ic_launcher_clock);
		about.putExtra("gek_about_header_line1", theApp.getVersion());
		about.putExtra("gek_about_header_line2", theApp.getCopyright());
		about.putExtra("gek_about_header_line3", theApp.getDeveloper());
		about.putExtra("gek_about_content", theApp.loadHtmlAboutContent());
		
		startActivity(about);
	}

	private void changeSettings() {
		Intent settings = new Intent(this, SettingsActivity.class);
		settings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(settings);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setTitle(R.string.title_project_dashboard);
		
		WorkaroundActionOverflow.execute(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
