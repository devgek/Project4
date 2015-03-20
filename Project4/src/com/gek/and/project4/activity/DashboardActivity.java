package com.gek.and.project4.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.gek.and.geklib.view.ColorBar;
import com.gek.and.geklib.view.ColorBarView;
import com.gek.and.project4.R;
import com.gek.and.project4.app.Project4App;
import com.gek.and.project4.app.Summary;
import com.gek.and.project4.async.SummaryLoader;
import com.gek.and.project4.async.TimeBooker;
import com.gek.and.project4.card.ProjectCardArrayAdapter;
import com.gek.and.project4.entity.Project;
import com.gek.and.project4.model.BookedValues;
import com.gek.and.project4.model.ProjectCard;
import com.gek.and.project4.service.ProjectService;
import com.gek.and.project4.util.DateUtil;

public class DashboardActivity extends MainActivity {
	private static final String TAG = "DashboardActivity::";
	
	private ProjectService projectService;
	private ProjectCardArrayAdapter projectCardAdapter;
	private ListView projectCardListView;
	private View mainView;
	private TextView textViewToday;
	private TextView textViewWeek;
	private TextView textViewMonth;
	private ColorBarView colorBarViewToday;
	private ColorBarView colorBarViewWeek;
	private ColorBarView colorBarViewMonth;

	private Thread tickerThread;
	private boolean tickerThreadFlag = false;
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.tickerThreadFlag = false;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SummaryLoader summaryLoader = new SummaryLoader();
		summaryLoader.execute(new Object[] { this });

		setContentView(R.layout.main);
		mainView = this.findViewById(android.R.id.content);

		textViewToday = (TextView) findViewById(R.id.summary_title_today);
		textViewWeek = (TextView) findViewById(R.id.summary_title_week);
		textViewMonth = (TextView) findViewById(R.id.summary_title_month);

		colorBarViewToday = (ColorBarView) findViewById(R.id.colorBarToday);
		colorBarViewToday.setBarHeight(50);
		colorBarViewToday.setPaddingTop(10);
		colorBarViewWeek = (ColorBarView) findViewById(R.id.colorBarWeek);
		colorBarViewWeek.setBarHeight(50);
		colorBarViewWeek.setPaddingTop(10);
		colorBarViewMonth = (ColorBarView) findViewById(R.id.colorBarMonth);
		colorBarViewMonth.setBarHeight(50);
		colorBarViewMonth.setPaddingTop(10);

		projectCardListView = (ListView) findViewById(R.id.project_list_view);

		projectService = Project4App.getApp(this).getProjectService();

		projectCardAdapter = new ProjectCardArrayAdapter(getApplicationContext(), R.layout.project_card);
		projectCardAdapter.setProjectCardActivity(this);

		Project4App.getApp(this).setProjectCardList(projectService.getActiveProjects());
		projectCardAdapter.addAll(Project4App.getApp(this).getProjectCardList());

//		updateCardAdapter();

		projectCardListView.setAdapter(projectCardAdapter);

		ImageButton addProjectButton = (ImageButton) findViewById(R.id.button_add_project);
		addProjectButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addProject();
			}
		});
	}

	protected void addProject() {
		Intent intent = new Intent(this, ProjectDetailActivity.class);
		startActivityForResult(intent, 1000);
	}

	public void editProject(Long projectId) {
		Intent intent = new Intent(this, ProjectDetailActivity.class);
		intent.putExtra("projectId", projectId);
		startActivityForResult(intent, 2000);
	}

	public void bookProject(Long projectId) {
		TimeBooker timeBooker = new TimeBooker();
		timeBooker.execute(new Object[] { this, projectId });
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 1000 || requestCode == 2000) {
				updateCardAdapter();
				invalidateMainView();
			}
		}
	}

	private void updateCardAdapter() {
		projectCardAdapter.clear();
		projectCardAdapter.addAll(Project4App.getApp(this).getProjectCardList());
	}

	public void onPostSummaryLoad() {
		updateSummaryFields();

		Summary summary = Project4App.getApp(this).getSummary();
		if (summary.getRunningNow() != null) {
			updateRunningProject(summary.getRunningNow().getProjectId());
			updateCardAdapter();
		}

		invalidateMainView();
		
		startRunningBookingTickerThread();
	}

	public void onPostTimeBooking(Long projectId, boolean bookedStart) {
		Long startedProjectId = -1L;
		if (bookedStart) {
			startedProjectId = projectId;
			sendProjectStartedNotification(projectId);
		}
		updateRunningProject(startedProjectId);
		updateCardAdapter();
		updateSummaryFields();
		invalidateMainView();
	}

	private void sendProjectStartedNotification(Long projectId) {
		Project project = Project4App.getApp(this).getProjectService()
				.getProject(projectId);
		// Prepare intent which is triggered if the
		// notification is selected
		Intent intent = new Intent(this, DashboardActivity.class);
//		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack
		stackBuilder.addParentStack(DashboardActivity.class);
		// Adds the Intent to the top of the stack
		stackBuilder.addNextIntent(intent);
		// Gets a PendingIntent containing the entire back stack
		PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

		// Build notification
		// Actions are just fake
		Notification noti = new Notification.Builder(this)
				.setContentTitle(project.getCompany())
				.setContentText(project.getTitle())
				.setSmallIcon(android.R.drawable.stat_notify_more)
				.setContentIntent(pendingIntent).build();
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// hide the notification after its selected
		noti.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(0, noti);
	}

	private void updateRunningProject(Long projectId) {
		List<ProjectCard> cardList = Project4App.getApp(this).getProjectCardList();
		for (ProjectCard card : cardList) {
			if (card.getProject().getId().equals(projectId)) {
				card.setRunningNow(true);
			} else {
				card.setRunningNow(false);
			}
		}
	}

	private void invalidateMainView() {
		if (mainView != null) {
			mainView.invalidate();
			colorBarViewToday.invalidate();
			colorBarViewWeek.invalidate();
			colorBarViewMonth.invalidate();
		}
	}

	private void updateSummaryFields() {
		Summary summary = Project4App.getApp(this).getSummary();

		textViewToday.setText(DateUtil.getFormattedDay(summary.getInitDate()));
		textViewWeek.setText(DateUtil.getFormattedWeek(summary.getInitDate()));
		textViewMonth.setText(DateUtil.getFormattedMonth(summary.getInitDate()));
		
		TextView tvSummaryDay = (TextView) findViewById(R.id.summary_day);
		tvSummaryDay.setText(summary.getFormattedDay());

		TextView tvSummaryWeek = (TextView) findViewById(R.id.summary_week);
		tvSummaryWeek.setText(summary.getFormattedWeek());

		TextView tvSummaryMonth = (TextView) findViewById(R.id.summary_month);
		tvSummaryMonth.setText(summary.getFormattedMonth());

		colorBarViewToday.setColorBars(getColorBars(summary.getProjectsToday()));
		colorBarViewWeek.setColorBars(getColorBars(summary.getProjectsWeek()));
		colorBarViewMonth.setColorBars(getColorBars(summary.getProjectsMonth()));
	}

	private List<ColorBar> getColorBars(Map<Long, BookedValues> projects) {
		List<ColorBar> colorBars = new ArrayList<ColorBar>();

		Set<Map.Entry<Long, BookedValues>> entries = projects.entrySet();
		Iterator<Map.Entry<Long, BookedValues>> it = entries.iterator();
		while (it.hasNext()) {
			Map.Entry<Long, BookedValues> entry = (Map.Entry<Long, BookedValues>) it.next();
			Long projectId = entry.getKey();
			Project project = Project4App.getApp(this).getProjectService().getProject(projectId);
			int projectColor;
			if (project == null) {
				projectColor = Color.BLACK;
			} else {
				projectColor = Color.parseColor(project.getColor());
			}

			ColorBar bar = new ColorBar(entry.getValue().getComplete(), projectColor);
			colorBars.add(bar);
		}
		return colorBars;
	}
	
	private void startRunningBookingTickerThread() {
		final Handler handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					updateSummaryFields();
					invalidateMainView();
				}
			}
			
		};
		
		tickerThread = new Thread() {

			@Override
			public void run() {
				while (tickerThreadFlag) {
					try {
						Thread.sleep(60 * 1000);
						
						Summary summary = Project4App.getApp(DashboardActivity.this).getSummary();
						synchronized(summary) {
							if (summary.getRunningNow() != null) {
								summary.addBooking(summary.getRunningNow());
								handler.sendEmptyMessage(1);
							}
						}
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
					}
				}
				Log.i(TAG, "bookingTicker::run finished!");
			}
			
		};
		
		tickerThreadFlag = true;
		tickerThread.setName("bookingTicker");
		tickerThread.start();
	}

}
