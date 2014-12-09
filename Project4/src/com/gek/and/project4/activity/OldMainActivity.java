package com.gek.and.project4.activity;

import java.util.List;
import java.util.Map;

import com.gek.and.project4.AppConstants;
import com.gek.and.project4.R;
import com.gek.and.project4.SettingsActivity;
import com.gek.and.project4.TimeLogEntry;
import com.gek.and.project4.TimeLogUtil;
import com.gek.and.project4.R.id;
import com.gek.and.project4.R.layout;
import com.gek.and.project4.R.string;
import com.gek.and.project4.util.ActivityUtil;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class OldMainActivity extends ListActivity {
	private Map<String,?> settings;
	private Button buttonCustomerBegin;
	private Button buttonCustomerEnd;
	private Button buttonOfficeBegin;
	private Button buttonOfficeEnd;
	
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
		
		setContentView(R.layout.main);
		
        initButtons();
        
		readSettings();

        List<TimeLogEntry> logEntrys = TimeLogUtil.getLogEntrys();
        
		ArrayAdapter adapter = new ArrayAdapter(this, R.layout.rowview, logEntrys.toArray());
		
		setListAdapter(adapter);
	}

	private void readSettings() {
		this.settings = ActivityUtil.getAllSettings(this);
        setButtonText();
	}

	private void initButtons() {
        buttonCustomerBegin = (Button) findViewById(R.id.buttonCustomerBegin);
        buttonCustomerBegin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ActivityUtil.logThis(OldMainActivity.this, buttonCustomerBegin.getText().toString());
				
				finish();
			}
		});

        buttonCustomerEnd = (Button) findViewById(R.id.buttonCustomerEnd);
        buttonCustomerEnd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ActivityUtil.logThis(OldMainActivity.this, buttonCustomerEnd.getText().toString());
				
				finish();
			}
		});

        buttonOfficeBegin = (Button) findViewById(R.id.buttonOfficeBegin);
        buttonOfficeBegin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ActivityUtil.logThis(OldMainActivity.this, buttonOfficeBegin.getText().toString());
				
				finish();
			}
		});

        buttonOfficeEnd = (Button) findViewById(R.id.buttonOfficeEnd);
        buttonOfficeEnd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ActivityUtil.logThis(OldMainActivity.this, buttonOfficeEnd.getText().toString());
				
				finish();
			}
		});
	}
	
	private void setButtonText() {
		String butCustomerBegin = (String) settings.get(AppConstants.SETTING_BUTTON_CUSTOMER_BEGIN);
		String butCustomerEnd = (String) settings.get(AppConstants.SETTING_BUTTON_CUSTOMER_END);
		String butOfficeBegin = (String) settings.get(AppConstants.SETTING_BUTTON_OFFICE_BEGIN);
		String butOfficeEnd = (String) settings.get(AppConstants.SETTING_BUTTON_OFFICE_END);
		
        buttonCustomerBegin.setText(butCustomerBegin == null ? "Kommen Kunde 1" : butCustomerBegin);
        buttonCustomerEnd.setText(butCustomerEnd == null ? "Gehen Kunde 1" : butCustomerEnd);
        buttonOfficeBegin.setText(butCustomerBegin == null ? "Kommen Kunde 2" : butOfficeBegin);
        buttonOfficeEnd.setText(butCustomerEnd == null ? "Gehen Kunde 2" : butOfficeEnd);
	}
	
	@Override
	protected void onResume() {
		readSettings();
		super.onResume();
	}

}
