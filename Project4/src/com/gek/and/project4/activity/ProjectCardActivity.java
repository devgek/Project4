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
		System.out.println("onCreate");
		super.onCreate(savedInstanceState);
		System.out.println("vor setContentView");
		setContentView(R.layout.project_list);
		System.out.println("nach setContentView");
		projectListView = (ListView) findViewById(R.id.project_list_view);
		System.out.println("nach findview");
		ProjectCardArrayAdapter projectCardAdapter = new ProjectCardArrayAdapter(getApplicationContext(), R.layout.project_card);
		projectCardAdapter.addAll(ProjectCard.getProjectList());
		
		projectListView.setAdapter(projectCardAdapter);
	}

}
