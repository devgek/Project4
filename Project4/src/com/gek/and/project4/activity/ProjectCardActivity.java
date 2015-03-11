package com.gek.and.project4.activity;

import java.security.InvalidParameterException;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.gek.and.project4.R;
import com.gek.and.project4.app.Project4App;
import com.gek.and.project4.app.Summary;
import com.gek.and.project4.async.SummaryLoader;
import com.gek.and.project4.async.TimeBooker;
import com.gek.and.project4.card.ProjectCardArrayAdapter;
import com.gek.and.project4.model.ProjectCard;
import com.gek.and.project4.service.ProjectService;

public class ProjectCardActivity extends MainActivity {
	private ProjectService projectService;
	private ProjectCardArrayAdapter projectCardAdapter;
	private ListView projectCardListView;
	private View mainView;
	
	@Override
	protected void onResume() {
		super.onResume();
		SummaryLoader summaryLoader = new SummaryLoader();
		summaryLoader.execute(new Object[]{this});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		mainView = this.findViewById(android.R.id.content);
		
		projectCardListView = (ListView) findViewById(R.id.project_list_view);
		
		projectService = Project4App.getApp(this).getProjectService();
		
		projectCardAdapter = new ProjectCardArrayAdapter(getApplicationContext(), R.layout.project_card);
		projectCardAdapter.setProjectCardActivity(this);
		
		Project4App.getApp(this).setProjectCardList(projectService.getActiveProjects());
		
		updateCardAdapter();
		
		projectCardListView.setAdapter(projectCardAdapter);
		
		ImageButton addProjectButton = (ImageButton) findViewById(R.id.button_add_project);
		addProjectButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addProject();
			}
		});
	}
	
	protected void addProject() {
		Intent intent = new Intent(this, ProjectDetailActivity.class);
		startActivityForResult(intent, 1000);
  	}

	public void editProject(Long projectId) {
		Intent intent = new Intent(this, ProjectDetailActivity.class);
		intent.putExtra("projectId", projectId);
		startActivityForResult(intent, 2000);
  	}
	
	public void bookProject(Long projectId) {
		TimeBooker timeBooker = new TimeBooker();
		timeBooker.execute(new Object[]{this, projectId});
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 1000 || requestCode == 2000) {
				updateCardAdapter();
//				Toast.makeText(this, R.string.comment_project_added, Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void updateCardAdapter() {
		projectCardAdapter.clear();
		projectCardAdapter.addAll(Project4App.getApp(this).getProjectCardList());
	}
	
	public void onPostSummaryLoad() {
		Summary summary = Project4App.getApp(this).getSummary();
		updateSummaryFields(summary);
		
		if (summary.getRunningNow() != null) {
			updateRunningProject(summary.getRunningNow().getProjectId());
			updateCardAdapter();
		}
		
		invalidateMainView();
	}
	
	public void onPostTimeBooking(Long projectId, boolean bookedStart) {
		Long startedProjectId = -1L;
		if (bookedStart) {
			startedProjectId = projectId;
		}
		updateRunningProject(startedProjectId);
		updateCardAdapter();
		invalidateMainView();
	}

	private void updateRunningProject(Long projectId) {
		List<ProjectCard> cardList = Project4App.getApp(this).getProjectCardList();
		for (ProjectCard card : cardList) {
			if (card.getProject().getId().equals(projectId)) {
				card.setRunningNow(true);
			}
			else {
				card.setRunningNow(false);
			}
		}
	}

	private void invalidateMainView() {
		if (mainView != null) {
			mainView.invalidate();
		}
	}
	
	private void updateSummaryFields(Summary summary) {
		TextView tvSummaryDay = (TextView) findViewById(R.id.summary_day);
		tvSummaryDay.setText(summary.getFormattedDay());

		TextView tvSummaryWeek = (TextView) findViewById(R.id.summary_week);
		tvSummaryWeek.setText(summary.getFormattedWeek());

		TextView tvSummaryMonth = (TextView) findViewById(R.id.summary_month);
		tvSummaryMonth.setText(summary.getFormattedMonth());
	}
}
