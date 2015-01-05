package com.gek.and.project4.card;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gek.and.project4.R;
import com.gek.and.project4.TimeLogViewer;
import com.gek.and.project4.util.ActivityUtil;

public class ProjectCardArrayAdapter extends ArrayAdapter<ProjectCard> {
	static class CardViewHolder {
		TextView line1;
		TextView line2;
		ImageView colorBar;
	}

	public ProjectCardArrayAdapter(Context context, int resource) {
		super(context, resource);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		CardViewHolder viewHolder;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.project_card, parent, false);
			
			handleLongClick(row);

			viewHolder = new CardViewHolder();
			viewHolder.line1 = (TextView) row
					.findViewById(R.id.project_card_line1);
			viewHolder.line2 = (TextView) row
					.findViewById(R.id.project_card_line2);
			viewHolder.colorBar = (ImageView) row.findViewById(R.id.project_card_color_bar);
			
			row.setTag(viewHolder);
		} else {
			viewHolder = (CardViewHolder) row.getTag();
		}
		
		ProjectCard card = getItem(position);

		viewHolder.line1.setText(card.getProject().getTitle());
		viewHolder.line2.setText(card.getProject().getSubTitle());
		viewHolder.colorBar.setBackgroundColor(Color.parseColor(card.getProject().getColor()));
		return row;
	}

	private void handleLongClick(View row) {
		row.setOnLongClickListener(new View.OnLongClickListener(){
		    @Override
		    public boolean onLongClick(View v) {
		    	ActivityUtil.startActivity(getContext(), TimeLogViewer.class);
		    	return true;
		    }
		});
	}
	@Override
	public ProjectCard getItem(int position) {
		return super.getItem(position);
	}

}
