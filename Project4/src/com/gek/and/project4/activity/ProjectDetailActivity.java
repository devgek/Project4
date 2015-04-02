package com.gek.and.project4.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gek.and.project4.R;
import com.gek.and.project4.app.Project4App;
import com.gek.and.project4.entity.Booking;
import com.gek.and.project4.entity.Project;
import com.gek.and.project4.model.ProjectCard;

public class ProjectDetailActivity extends Activity {
	private EditText editTextCustomer;
	private EditText editTextProject;
	private ImageButton buttonProjectColor;
	
	private long projectId;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    if (isModeNew()) {
		    inflater.inflate(R.menu.project_new, menu);
	    }
	    else {
		    inflater.inflate(R.menu.project_edit, menu);
	    }
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.action_cancel) {
			cancel();
			return true;
		} else if (itemId == R.id.action_discard) {
			confirmDeleteProject();
			return true;
		} else if (itemId == R.id.action_save) {
			saveProject();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.project_detail);
		
		Intent callingIntent = getIntent();
		this.projectId = callingIntent.getLongExtra("projectId", -1);
		
		editTextCustomer = (EditText) findViewById(R.id.projectDetailCustomerText);
		editTextProject = (EditText) findViewById(R.id.projectDetailProjectText);
		buttonProjectColor = (ImageButton) findViewById(R.id.projectDetailProjectColor);
		
		buttonProjectColor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				chooseProjectColor();
			}
		});
		
		prepareData();
		
		if (isModeNew()) {
			getActionBar().setTitle(R.string.title_project_add);
		}
		else {
			getActionBar().setTitle(R.string.title_project_change);
		}
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}


	private void chooseProjectColor() {
		Intent colorChooser = new Intent(getApplicationContext(), ColorPickerActivity.class);
		startActivityForResult(colorChooser, 1);
	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
				setProjectColorButton();
			}
		}
	}

	private boolean isModeNew() {
		return projectId < 0;
	}
	
	private void prepareData() {
		if (isModeNew()) {
			editTextCustomer.setText("");
			editTextProject.setText("");
			setProjectColor(getResources().getString(R.color.project_color_preselect));
		}
		else {
			Project editProject = Project4App.getApp(this).getProjectService().getProject(projectId);
			editTextCustomer.setText(editProject.getCompany());
			editTextProject.setText(editProject.getTitle());
			setProjectColor(editProject.getColor());
		}
	}

	private void setProjectColor(String colorString) {
		Project4App.getApp(this).setEditProjectColorString(colorString);
		setProjectColorButton();
	}

	private void setProjectColorButton() {
		String colorString = Project4App.getApp(this).getEditProjectColorString();
		GradientDrawable d = (GradientDrawable) buttonProjectColor.getBackground();
		d.setColor(Color.parseColor(colorString));
		buttonProjectColor.invalidate();
	}
	
	private String getProjectColor() {
//		GradientDrawable d = (GradientDrawable) imageButtonColor.getBackground();
//		int intColor = 0;
//		return String.format("#%06X", 0xFFFFFF & intColor);
		//workaround, because you can't get a solid color out of GradientDrawable
		return Project4App.getApp(this).getEditProjectColorString();
	}

	private void saveProject() {
		String customer = editTextCustomer.getText().toString();
		String title = editTextProject.getText().toString();
		
		if (customer.trim().equals("") || title.trim().equals("")) {
			Toast.makeText(getApplicationContext(), "Kunde und Projekt müssen angegeben werden!", Toast.LENGTH_SHORT).show();
			return;
		}

		String projectColor = getProjectColor();
		
		Project p = Project4App.getApp(this).getProjectService().addOrUpdateProject(projectId, customer, title, "", projectColor, 0);
		if (isModeNew()) {
			ProjectCard pCard = Project4App.getApp(this).getProjectService().toCard(p);
			List<ProjectCard> projectCardList = Project4App.getApp(this).getProjectCardList();
			projectCardList.add(pCard);
		}
		
		goBackWithResult(RESULT_OK, false);
	}
	
	private void deleteProject() {
		boolean deleted = Project4App.getApp(this).getProjectService().deleteProject(this.projectId);
		if (!deleted) {
			Toast.makeText(this, "Projekt konnte nicht gelöscht werden.", Toast.LENGTH_SHORT).show();
		}
		else {
			List<ProjectCard> projectCardListOld = Project4App.getApp(this).getProjectCardList();
			List<ProjectCard> projectCardList = new ArrayList<ProjectCard>();
			for (ProjectCard card : projectCardListOld) {
				if (card.getProject().getId().equals(this.projectId)) {
					continue;
				}
				projectCardList.add(card);
			}
			
			Project4App.getApp(this).setProjectCardList(projectCardList);
			goBackWithResult(RESULT_OK, true);
		}
	}
	
	private boolean isRunningProject() {
		Booking runningBooking = Project4App.getApp(this).getSummary().getRunningNow();
		return runningBooking != null && runningBooking.getProjectId().equals(this.projectId);
	}

	private void goBackWithResult(int result, boolean reloadSummary) {
		Intent back = new Intent(getApplicationContext(), DashboardActivity.class);
		back.putExtra("reloadSummary", reloadSummary);
		setResult(result, back);
		finish();
	}
	
	private void cancel() {
		finish();
	}
	
	public void confirmDeleteProject() {
		if (isRunningProject()) {
			Toast.makeText(this, "Das gerade laufende Projekt kann nicht gelöscht werden.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setTitle("Projekt löschen");
		ad.setMessage("Das Projekt und alle seine Zeitbuchungen werden gelöscht.");
		ad.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						deleteProject();
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

}
