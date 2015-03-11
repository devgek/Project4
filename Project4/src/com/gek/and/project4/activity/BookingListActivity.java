package com.gek.and.project4.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gek.and.project4.R;
import com.gek.and.project4.menu.PeriodActionProvider.PeriodActionProviderListener;
import com.gek.and.project4.menu.ProjectActionProvider.ProjectActionProviderListener;

public class BookingListActivity extends FragmentActivity implements ProjectActionProviderListener, PeriodActionProviderListener {
	private static final String PERIOD_ITEM_POSITION = "period_item_position";
	private static final String PROJECT_ITEM_POSITION = "project_item_position";
	private int periodActionPosition;
	private int projectActionPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_frame);
		getActionBar().setDisplayShowTitleEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.booking, menu);
	    
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(PERIOD_ITEM_POSITION)) {
			periodActionPosition = savedInstanceState.getInt(PERIOD_ITEM_POSITION);
		}
		if (savedInstanceState.containsKey(PROJECT_ITEM_POSITION)) {
			projectActionPosition = savedInstanceState.getInt(PROJECT_ITEM_POSITION);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(PERIOD_ITEM_POSITION, periodActionPosition);
		outState.putInt(PROJECT_ITEM_POSITION, projectActionPosition);
	}

	@Override
	public void onProjectActionItemSelected(int position) {
		this.projectActionPosition = position;
		switchToFragment();
	}

	@Override
	public void onPeriodActionItemSelected(int position) {
		this.periodActionPosition = position;
		switchToFragment();
	}

	public void switchToFragment() {
		Fragment fragment = new  BookingListFragment(periodActionPosition, projectActionPosition);
		getFragmentManager().beginTransaction().replace(R.id.booking_frame_container, fragment).commit();
	}

}
