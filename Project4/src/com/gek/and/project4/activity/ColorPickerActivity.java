package com.gek.and.project4.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gek.and.project4.R;
import com.gek.and.project4.app.Project4App;
import com.gek.and.project4.util.ColorUtil;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.ColorPicker.OnColorChangedListener;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;

public class ColorPickerActivity extends Activity implements
		OnColorChangedListener {
	private ColorPicker picker;
	private SVBar svBar;
	private OpacityBar opacityBar;
	private Button buttonCancel;
	private Button buttonSelect;

	private String oldColorHex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.color_picker);

		setTitle(R.string.title_project_color_picker);
		
		picker = (ColorPicker) findViewById(R.id.picker);
		svBar = (SVBar) findViewById(R.id.svbar);
		opacityBar = (OpacityBar) findViewById(R.id.opacitybar);
		buttonCancel = (Button) findViewById(R.id.buttonColorCancel);
		buttonSelect = (Button) findViewById(R.id.buttonColorSelect);

		oldColorHex = Project4App.getApp(this).getEditProjectColorString();
		int oldColor = Color.parseColor(oldColorHex);
		picker.setColor(oldColor);
		picker.setOldCenterColor(oldColor);
//		picker.addSVBar(svBar);
//		picker.addOpacityBar(opacityBar);
		picker.setOnColorChangedListener(this);

		buttonSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String hexColor = ColorUtil.getHex(picker.getColor());
				Project4App.getApp(ColorPickerActivity.this).setEditProjectColorString(hexColor);
				
				goBackOk();
			}
		});
		
		buttonCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Project4App.getApp(ColorPickerActivity.this).setEditProjectColorString(oldColorHex);
				
				finish();
			}
		});

	}

	private void goBackOk() {
		Intent back = new Intent(getApplicationContext(), ProjectCardActivity.class);
		setResult(RESULT_OK, back);
		finish();
	}

	@Override
	public void onColorChanged(int arg0) {
		// TODO Auto-generated method stub

	}

}
