package com.example.view.fragments;

import com.example.p5.R;

import android.content.Context;
import android.graphics.Color;
import android.renderscript.Type.CubemapFace;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LvDownloadAdapter extends BaseAdapter {

	LayoutInflater lInflater;
	ListViewExcelFilesManager manager;
	TextView text;
	FragmentTwo fragment;
	
	int buferInt;
	View bufView;
	View lastSelectedView = null;
	
	public LvDownloadAdapter(Context context, ListViewExcelFilesManager manager, FragmentTwo fragment) {
		lInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.manager = manager;
		this.fragment = fragment;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub		
		if (fragment.state == 1)
			return manager.getFileNames().length;
		else
			return manager.getSheetNames().length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		buferInt = position;
		View view = convertView;
		if (view == null) {
			view = lInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
		}
		bufView = view;
		TextView text = (TextView) view.findViewById(android.R.id.text1);
		switch (fragment.state) {
		case 1:			
			text.setText(manager.getFileName(position));
			text.setOnClickListener(new OnClickListener() {
				int position = buferInt;
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					fragment.lvSelectedFile = position;
					manager.formLists(position);
					fragment.setState(2);
				}
			});
			break;
		
		case 2:
			text.setText(manager.getSheetNames()[position]);
			text.setOnClickListener(new OnClickListener() {		
				int position = buferInt;
				View view = bufView;
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					fragment.lvSelectedSheet = position;
					view.setBackgroundColor(Color.GRAY);
					if (lastSelectedView != null)
						lastSelectedView.setBackgroundColor(Color.TRANSPARENT);
					lastSelectedView = view;
					fragment.outp.setText("¬ведите номер группы");
				}
			});

			if (fragment.lvSelectedSheet == position)
				view.setBackgroundColor(Color.GRAY);
			else
				view.setBackgroundColor(Color.TRANSPARENT);
			break;
		}
		return view;
	}

}
