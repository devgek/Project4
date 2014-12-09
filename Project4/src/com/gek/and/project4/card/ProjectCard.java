package com.gek.and.project4.card;

import java.util.ArrayList;
import java.util.List;

import com.gek.and.project4.types.ProjectType;

public class ProjectCard {
	private ProjectType type;
	
	public ProjectCard(ProjectType type) {
		this.type = type;
	}

	public ProjectType getType() {
		return type;
	}

	public static List<ProjectCard> getProjectList() {
		List<ProjectCard> cardList = new ArrayList<ProjectCard>();
		for (ProjectType type : ProjectType.getPriorityList()) {
			cardList.add(new ProjectCard(type));
		}
		
		return cardList;
	}
	
	public String getLine1() {
		return type.getCode();
	}
	
	public String getLine2() {
		return "line2";
	}
}
