package com.gek.and.project4.app;

import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.gek.and.project4.dao.BookingDao;
import com.gek.and.project4.dao.DaoMaster;
import com.gek.and.project4.dao.DaoMaster.DevOpenHelper;
import com.gek.and.project4.dao.DaoSession;
import com.gek.and.project4.dao.ProjectDao;
import com.gek.and.project4.model.ProjectCard;
import com.gek.and.project4.service.BookingService;
import com.gek.and.project4.service.ProjectService;

public class Project4App extends Application {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ProjectDao projectDao;
    private BookingDao bookingDao;
    private ProjectService projectService;
    private BookingService bookingService;
    private List<ProjectCard> projectCardList;
    private Summary summary = new Summary();
    private String editProjectColorString;

	@Override
	public void onCreate() {
		super.onCreate();
		
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "project4-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        projectDao = daoSession.getProjectDao();
        bookingDao = daoSession.getBookingDao();
        projectService = new ProjectService(projectDao);
        bookingService = new BookingService(bookingDao);
	}
	
	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public BookingDao getBookingDao() {
		return bookingDao;
	}

	public ProjectService getProjectService() {
		return projectService;
	}
	
	public List<ProjectCard> getProjectCardList() {
		return projectCardList;
	}

	public BookingService getBookingService() {
		return bookingService;
	}

	public void setProjectCardList(List<ProjectCard> projectCardList) {
		this.projectCardList = projectCardList;
	}

	public static Project4App getApp(Activity activity) {
		Project4App app = (Project4App) activity.getApplication();
		return app;
	}

	public Summary getSummary() {
		return summary;
	}

	public void setSummary(Summary summary) {
		this.summary = summary;
	}

	public String getEditProjectColorString() {
		return editProjectColorString;
	}

	public void setEditProjectColorString(String editProjectColorString) {
		this.editProjectColorString = editProjectColorString;
	}

}
