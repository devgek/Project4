package com.gek.and.project4.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.gek.and.project4.R;
import com.gek.and.project4.card.ProjectCard;
import com.gek.and.project4.card.ProjectCardArrayAdapter;

public class ProjectCardActivity extends MainActivity {
	private ListView projectListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.project_list);
		projectListView = (ListView) findViewById(R.id.project_list_view);
		
		ProjectCardArrayAdapter projectCardAdapter = new ProjectCardArrayAdapter(getApplicationContext(), R.layout.project_card);
		projectCardAdapter.addAll(ProjectCard.getProjectCardList());
		
		projectListView.setAdapter(projectCardAdapter);
	}

}
