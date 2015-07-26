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
	
	public List<ProjectCard> getAllProjects(Long runningProjectId) {
		return getProjects(true, runningProjectId);
	}
	
	public List<ProjectCard> getActiveProjects(Long runningProjectId) {
		return getProjects(false, runningProjectId);
	}

	private List<ProjectCard> getProjects(boolean all, Long runningProjectId) {
		List<ProjectCard> projectCards = new ArrayList<ProjectCard>();
		
		List<Project> projectEntities = projectDao.loadAll();
		for (Project projectEntity : projectEntities) {
			if (all || projectEntity.getActive().equals(Boolean.TRUE)) {
				projectCards.add(toCard(projectEntity, runningProjectId));
			}
		}
		
		return projectCards;
	}

	public Project addOrUpdateProject(long projectId, String customer, String title, String subTitle, String color, int priority, boolean active) {
		if (projectId < 0) {
			return addProject(customer, title, subTitle, color, priority, active);
		}
		
		Project p = getProject(projectId);
		p.setCompany(customer);
		p.setTitle(title);
		p.setSubTitle(subTitle);
		p.setColor(color);
		p.setPriority(priority);
		p.setActive(Boolean.valueOf(active));
		
		this.projectDao.update(p);
		
		return p;
	}
	
	public Project addProject(String customer, String title, String subTitle, String color, int priority, boolean active) {
		Project p = new Project(null, title, subTitle, customer, color, priority, Boolean.valueOf(active));
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

	public ProjectCard toCard(Project project, Long runningProjectId) {
		ProjectCard card = new ProjectCard(project);
		card.setRunningNow(runningProjectId != null && runningProjectId.longValue() == project.getId().longValue());
		
		return card;
	}
}
