package com.gek.and.project4.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gek.and.project4.R;
import com.gek.and.project4.app.Project4App;
import com.gek.and.project4.entity.Booking;
import com.gek.and.project4.entity.Project;
import com.gek.and.project4.util.DateUtil;

public class BookingDetailActivity extends Activity {
	private TextView headLine;
	private EditText from;
	private EditText to;
	private EditText project;
	private EditText note;
	
	private Booking theBooking;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.booking_edit, menu);
	    
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.action_cancel) {
			cancel();
			return true;
		} else if (itemId == R.id.action_discard) {
			confirmDeleteBooking();
			return true;
		} else if (itemId == R.id.action_save) {
			saveBooking();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.booking_detail);
		
		this.theBooking = Project4App.getApp(this).getEditBooking();
		
		headLine = (TextView) findViewById(R.id.bookingDetailHeadLine);
		from	= (EditText) findViewById(R.id.bookingDetailFrom);
		to	= (EditText) findViewById(R.id.bookingDetailTo);
		project	= (EditText) findViewById(R.id.bookingDetailProject);
		note	= (EditText) findViewById(R.id.bookingDetailNote);
		
		prepareData();
		
		getActionBar().setTitle(R.string.title_booking_edit);
			
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}


	private void prepareData() {
		if (theBooking != null) {
			headLine.setText(DateUtil.getFormattedDayFull(theBooking.getFrom()));
			from.setText(DateUtil.getFormattedTime(theBooking.getFrom()));
			to.setText(theBooking.getTo() != null ? DateUtil.getFormattedTime(theBooking.getTo()) : "");
			Project p = Project4App.getApp(this).getProjectService().getProject(theBooking.getProjectId());
			project.setText(p.getCompany() + " / " + p.getTitle());
			note.setText(theBooking.getNote() != null ? theBooking.getNote() : "");
		}
	}

	private void saveBooking() {
		String note = this.note.getText().toString();
		this.theBooking.setNote(note);
		Project4App.getApp(this).getBookingService().updateBooking(this.theBooking);
		goBackWithResult(RESULT_OK, false);
	}
	
	private void cancel() {
		finish();
	}
	
	private void goBackWithResult(int result, boolean reloadList) {
		Intent back = new Intent(getApplicationContext(), BookingListActivity.class);
		back.putExtra("reloadList", reloadList);
		setResult(result, back);
		finish();
	}
	
	private boolean isRunningBooking() {
		Booking runningBooking = Project4App.getApp(this).getSummary().getRunningNow();
		return runningBooking != null && runningBooking.getId().equals(this.theBooking.getId());
	}

	public void confirmDeleteBooking() {
		if (!Project4App.getApp(this).isPro()) {
			Toast.makeText(this, "Diese Funktion ist nur in der Pro-Version verfügbar.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (isRunningBooking()) {
			Toast.makeText(this, "Die gerade laufende Zeitbuchung kann nicht gelöscht werden.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setTitle("Buchung löschen");
		ad.setMessage("Die Zeitbuchung wird gelöscht.");
		ad.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						deleteBooking();
						arg0.cancel();

					}
				});
		ad.setNegativeButton("Abbrechen",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.cancel();
					}
				});
		AlertDialog ad_echt = ad.create();
		ad_echt.show();
	}
	
	private void deleteBooking() {
		boolean deleted = Project4App.getApp(this).getBookingService().deleteBooking(this.theBooking);
		if (!deleted) {
			Toast.makeText(this, "Zeitbuchung konnte nicht gelöscht werden.", Toast.LENGTH_SHORT).show();
		}
		else {
			goBackWithResult(RESULT_OK, true);
		}
	}

}
