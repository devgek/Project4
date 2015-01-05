package com.gek.and.project4.card;

import java.util.ArrayList;
import java.util.List;

import com.gek.and.project4.model.Project;
import com.gek.and.project4.model.ProjectFactory;
import com.gek.and.project4.types.ProjectType;

public class ProjectCard {
	private Project project;
	
	public ProjectCard(Project project) {
		this.project = project;
	}

	public Project getProject() {
		return project;
	}

	public static List<ProjectCard> getProjectCardList() {
		List<ProjectCard> cardList = new ArrayList<ProjectCard>();
		for (ProjectType type : ProjectType.getPriorityList()) {
			cardList.add(new ProjectCard(ProjectFactory.createProject(type)));
		}
		
		return cardList;
	}
	
	public String getLine1() {
		return project.getTitle();
	}
	
	public String getLine2() {
		return "line2";
	}
}
