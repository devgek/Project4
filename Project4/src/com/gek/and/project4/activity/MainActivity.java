package com.gek.and.project4.activity;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.gek.and.project4.R;
import com.gek.and.project4.SettingsActivity;
import com.gek.and.project4.util.ActivityUtil;

public class MainActivity extends Activity {
	private Map<String,?> settings;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	  MenuItem item1 = menu.add(R.string.menuSettings);
	  {
	    item1.setIcon(android.R.drawable.ic_menu_preferences);
	    item1.setIntent(new Intent(this, SettingsActivity.class));
	  }
	  
	  MenuItem item2 = menu.add(R.string.menuStatistic);
	  {
	    item2.setIcon(android.R.drawable.ic_menu_search);
	    item2.setIntent(new Intent(getApplicationContext(), SettingsActivity.class));
	  }
	  
	  return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		startActivity(item.getIntent());
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
