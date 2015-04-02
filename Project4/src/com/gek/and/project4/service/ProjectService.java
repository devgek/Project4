package com.gek.and.project4.service;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import com.gek.and.project4.dao.BookingDao;
import com.gek.and.project4.dao.DaoSession;
import com.gek.and.project4.dao.ProjectDao;
import com.gek.and.project4.entity.Project;
import com.gek.and.project4.model.ProjectCard;

public class ProjectService {
	ProjectDao projectDao;
	DaoSession daoSession;
	
	public ProjectService(DaoSession daoSession) {
		this.daoSession = daoSession;
		projectDao = daoSession.getProjectDao();
	}
	
	public List<ProjectCard> getActiveProjects() {
		List<ProjectCard> projectCards = new ArrayList<ProjectCard>();
		
		List<Project> projectEntities = projectDao.loadAll();
		for (Project projectEntity : projectEntities) {
			projectCards.add(toCard(projectEntity));
		}
		
		return projectCards;
	}
	
	public Project addOrUpdateProject(long projectId, String customer, String title, String subTitle, String color, int priority) {
		if (projectId < 0) {
			return addProject(customer, title, subTitle, color, priority);
		}
		
		Project p = getProject(projectId);
		p.setCompany(customer);
		p.setTitle(title);
		p.setSubTitle(subTitle);
		p.setColor(color);
		p.setPriority(priority);
		
		this.projectDao.update(p);
		
		return p;
	}
	
	public Project addProject(String customer, String title, String subTitle, String color, int priority) {
		Project p = new Project(null, title, subTitle, customer, color, priority);
		long id = this.projectDao.insert(p);
		p.setId(id);
		
		return p;
	}
	
	public Project getProject(long projectId) {
		return this.projectDao.loadByRowId(projectId);
	}
	
	public boolean deleteProject(long projectId) {
		SQLiteDatabase db = this.daoSession.getDatabase();
		boolean ok = true;
		
		db.beginTransaction();
		try {
			deleteAllBookingsForProject(projectId);
			this.projectDao.deleteByKey(projectId);
			db.setTransactionSuccessful();
		}
		catch(Exception e) {
			ok = false;
		}
		finally {
			db.endTransaction();
		}
		
		return ok;
	}
	
	private void deleteAllBookingsForProject(long projectId) {
		SQLiteDatabase db = this.daoSession.getDatabase();
		BookingDao bookingDao = this.daoSession.getBookingDao();

		String sql = "delete from " + bookingDao.getTablename() + " where " + BookingDao.Properties.ProjectId.columnName + " = " + projectId;
		db.execSQL(sql);
	}

	public ProjectCard toCard(Project project) {
		return new ProjectCard(project);
	}
}
