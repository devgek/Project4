package com.gek.and.project4.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.gek.and.project4.R;

public class ProjectDetailActivity extends Activity {
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.project, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_cancel:
	            cancel();
	            return true;
	        case R.id.action_save:
	            saveProject();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.project_detail);
		
		getActionBar().setTitle(R.string.title_project_add);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private void saveProject() {
		Toast.makeText(ProjectDetailActivity.this, R.string.comment_project_added, Toast.LENGTH_SHORT).show();
		finish();
	}
	
	private void cancel() {
		finish();
	}

}
