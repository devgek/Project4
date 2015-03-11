package com.gek.and.project4.activity;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.gek.and.project4.R;
import com.gek.and.project4.app.Project4App;
import com.gek.and.project4.card.BookingListArrayAdapter;
import com.gek.and.project4.entity.Booking;
import com.gek.and.project4.model.ProjectCard;
import com.gek.and.project4.service.BookingService;
import com.gek.and.project4.types.PeriodType;
import com.gek.and.project4.util.DateUtil;
import com.gek.and.project4.util.SummaryUtil;

public class BookingListFragment extends Fragment{
	private int periodPosition;
	private int projectPosition;
	
	public BookingListFragment(int periodPosition, int projectPosition) {
		super();
		this.periodPosition = periodPosition;
		this.projectPosition = projectPosition;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.booking_frame_content, container,  false);
		
		TextView textViewSummaryTitle = (TextView) contentView.findViewById(R.id.booking_summary_title);
		TextView textViewSummary = (TextView) contentView.findViewById(R.id.booking_summary_hm);
	    ListView v = (ListView) contentView.findViewById(R.id.booking_list_view);

	    String[] titles = getResources().getStringArray(R.array.periodSummaryTitle);
	    textViewSummaryTitle.setText(titles[this.periodPosition]);
	    
		List<Booking> bookingList = getData();
		int minutes = SummaryUtil.getMinutes(bookingList, getProjectIdFromPosition(this.projectPosition));
		textViewSummary.setText(DateUtil.getFormattedHM(minutes));
		
		BookingListArrayAdapter bookingListAdapter = new BookingListArrayAdapter(R.layout.booking_row, getActivity());
		bookingListAdapter.addAll(bookingList);

		v.setAdapter(bookingListAdapter);

	    return contentView;
	}

	private Long getProjectIdFromPosition(int pos) {
		if (pos == 0) return -1L;
		
		ProjectCard card = Project4App.getApp(getActivity()).getProjectCardList().get(pos - 1);
		return card.getProject().getId();
	}

	private List<Booking> getData() {
		BookingService bookingService = Project4App.getApp(getActivity()).getBookingService();
		return bookingService.getFiltered(PeriodType.fromInt(this.periodPosition), getProjectIdFromPosition(this.projectPosition));
	}
}
