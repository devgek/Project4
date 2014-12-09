package com.gek.and.project4;

import com.gek.and.project4.util.ActivityUtil;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends Activity {
	private String inputCustomerBegin;
	private String inputCustomerEnd;
	private String inputOfficeBegin;
	private String inputOfficeEnd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.settings);
		
		prepareInput();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
        Button buttonSave = (Button) findViewById(R.id.buttonSettingsSave);
        
        buttonSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getInput();
				
				savePreferences();
				
				finish();
			}

		});
	}
	
	private void prepareInput() {
		loadPreferences();
		
		EditText editCustomerBegin = (EditText) findViewById(R.id.inputSettingsButtonBeginCustomer);
		editCustomerBegin.setText(this.inputCustomerBegin);
		
		EditText editCustomerEnd = (EditText) findViewById(R.id.inputSettingsButtonEndCustomer);
		editCustomerEnd.setText(this.inputCustomerEnd);
		
		EditText editOfficeBegin = (EditText) findViewById(R.id.inputSettingsButtonBeginOffice);
		editOfficeBegin.setText(this.inputOfficeBegin);
		
		EditText editOfficeEnd = (EditText) findViewById(R.id.inputSettingsButtonEndOffice);
		editOfficeEnd.setText(this.inputOfficeEnd);
	}
	
	private void getInput() {
		EditText editCustomerBegin = (EditText) findViewById(R.id.inputSettingsButtonBeginCustomer);
		this.inputCustomerBegin = editCustomerBegin.getText().toString();
		
		EditText editCustomerEnd = (EditText) findViewById(R.id.inputSettingsButtonEndCustomer);
		this.inputCustomerEnd = editCustomerEnd.getText().toString();
		
		EditText editOfficeBegin = (EditText) findViewById(R.id.inputSettingsButtonBeginOffice);
		this.inputOfficeBegin = editOfficeBegin.getText().toString();
		
		EditText editOfficeEnd = (EditText) findViewById(R.id.inputSettingsButtonEndOffice);
		this.inputOfficeEnd = editOfficeEnd.getText().toString();
	}
	
	private void loadPreferences() {
		SharedPreferences sharedPrefs = ActivityUtil.getSharedPrefs(this);

		this.inputCustomerBegin = sharedPrefs.getString(AppConstants.SETTING_BUTTON_CUSTOMER_BEGIN, "Kommen Kunde");
		this.inputCustomerEnd = sharedPrefs.getString(AppConstants.SETTING_BUTTON_CUSTOMER_END, "Gehen Kunde");
		this.inputOfficeBegin = sharedPrefs.getString(AppConstants.SETTING_BUTTON_OFFICE_BEGIN, "Kommen HomeOffice");
		this.inputOfficeEnd = sharedPrefs.getString(AppConstants.SETTING_BUTTON_OFFICE_END, "Gehen HomeOffice");
	}

	private void savePreferences() {
		SharedPreferences sharedPrefs = ActivityUtil.getSharedPrefs(this);
		SharedPreferences.Editor prefEditor = sharedPrefs.edit();
		
		prefEditor.putString(AppConstants.SETTING_BUTTON_CUSTOMER_BEGIN, this.inputCustomerBegin);
		prefEditor.putString(AppConstants.SETTING_BUTTON_CUSTOMER_END, this.inputCustomerEnd);
		prefEditor.putString(AppConstants.SETTING_BUTTON_OFFICE_BEGIN, this.inputOfficeBegin);
		prefEditor.putString(AppConstants.SETTING_BUTTON_OFFICE_END, this.inputOfficeEnd);
		
		prefEditor.commit();
	}

}
