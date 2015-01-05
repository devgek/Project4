package com.gek.and.project4.model;

import com.gek.and.project4.types.ProjectType;

public class ProjectFactory {
	public static Project createProject(ProjectType type) {
		Project project = new Project();
		project.setTitle(type.name());
		project.setSubTitle(type.getCode());
		project.setPriority(type.getPriority());
		project.setColor(type.getColor());
		
		return project;
	}
}
