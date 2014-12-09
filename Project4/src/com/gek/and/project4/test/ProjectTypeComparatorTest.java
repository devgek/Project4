package com.gek.and.project4.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gek.and.project4.comparator.ProjectTypeComparator;
import com.gek.and.project4.types.ProjectType;


	public class ProjectTypeComparatorTest {

	@Test
	public void testCompare() {
		ProjectTypeComparator c = new ProjectTypeComparator();
		int result1 = c.compare(ProjectType.PIPE, ProjectType.VVO);
		int result2 = c.compare(ProjectType.PIPE, ProjectType.PIPE);
		int result3 = c.compare(ProjectType.VVO, ProjectType.KAHRER_SOFTWARE);
		assertTrue(result1 == -1);
		assertTrue(result2 == 0);
		assertTrue(result3 == 1);
	}

}
