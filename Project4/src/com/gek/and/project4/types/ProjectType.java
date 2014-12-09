package com.gek.and.project4.types;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.gek.and.project4.comparator.ProjectTypeComparator;

public enum ProjectType {
	VVO("VVO", 3),
	PIPE("PIPE", 1),
	KAHRER_SOFTWARE("KAHRER_SOFTWARE", 2);
	
	private String code;
	private int priority;
	
	ProjectType(String code, int priority) {
		this.code = code;
		this.priority = priority;
	}

	public String getCode() {
		return code;
	}

	public int getPriority() {
		return priority;
	}
	
	public static List<ProjectType> getPriorityList() {
		List<ProjectType> projectTypeList = Arrays.asList(ProjectType.values());
		Collections.sort(projectTypeList, new ProjectTypeComparator());
		return projectTypeList;
	}
}
