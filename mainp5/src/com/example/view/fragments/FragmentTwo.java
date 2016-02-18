package com.example.view.fragments;

import java.util.Collections;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.p5.R;
import com.example.xml.LessonData;
import com.example.xml.XMLSerialize;

public class FragmentTwo extends Fragment {

	Button loadButton;
	Button btnBackListView;
	TextView outp;
	Spinner spSubgroup;

	ListView lvFiles;
	int lvSelectedFile = -1;

	ListView lvSheets;
	int lvSelectedSheet = -1;

	int subgroup = 1;

	EditText etGroup;

	ListViewExcelFilesManager manager;

	int state = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment2, container, false);

		outp = (TextView) rootView.findViewById(R.id.textView1);
		etGroup = (EditText) rootView.findViewById(R.id.editTextGroup);
		lvFiles = (ListView) rootView.findViewById(R.id.listViewExelFiles);
		btnBackListView = (Button) rootView.findViewById(R.id.buttonBackList);
		loadButton = (Button) rootView.findViewById(R.id.loadButton);
		spSubgroup = (Spinner) rootView.findViewById(R.id.spinnerSubgroup);
		spSubgroup.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, new String[]{"1", "2"}));
		manager = new ListViewExcelFilesManager(getActivity());
		setState(1);
		
		btnBackListView.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if (state != 1)
					setState(1);
				lvSelectedSheet = -1;
			}
		});
		
		spSubgroup.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				subgroup = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		loadButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (state == 2) {
					try {
						if (!etGroup.getText().toString().equals("")) {
							List<com.example.controller.Lesson> lessonList = manager.getLessons(lvSelectedFile, lvSelectedSheet, etGroup.getText().toString(), subgroup);
							LessonData data = new LessonData(); data.list = lessonList;
							Collections.sort(data.list); 
							XMLSerialize.write(data, getActivity());
						}
					}
					catch (Exception e) { e.printStackTrace(); }
					outp.setText("Рассписание загружено");
				}
			}
		});		
		
		/*lvFiles.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (state == 1) {
					lvSelectedFile = position;
					manager.setSecondAdapterState();
				}
				if (state == 2) {
					lvSelectedSheet = position;
					outp.setText("File: " + lvSelectedFile + "Sheet: " + lvSelectedSheet);
				}
			}
		});*/
		/*
		 * // Обработчик кнопки loadButton = (Button)
		 * rootView.findViewById(R.id.loadButton); outp = (TextView)
		 * rootView.findViewById(R.id.textView1); etGroup = (EditText)
		 * rootView.findViewById(R.id.editTextGroup);
		 * 
		 * OnClickListener oclLoadBtn = new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { try {
		 * List<com.example.controller.Lesson> lessonList = manager
		 * .getLessons(lvSelectedFile, lvSelectedSheet,
		 * etGroup.getText().toString(), 1); for (com.example.controller.Lesson
		 * les : lessonList) { Log.d("OOO", les.toString()); } LessonData data =
		 * new LessonData(); data.list = lessonList;
		 * Collections.sort(data.list); XMLSerialize.write(data, getActivity());
		 * outp.setText("Yes"); } catch (Exception e) { e.printStackTrace();
		 * outp.setText("No"); } } }; loadButton.setOnClickListener(oclLoadBtn);
		 * 
		 * // Файлы manager = new ListViewExcelFilesManager(getActivity());
		 * 
		 * lvFiles = (ListView) rootView.findViewById(R.id.listViewExelFiles);
		 * lvSheets = (ListView)
		 * rootView.findViewById(R.id.listViewExcelSheets);
		 * 
		 * lvFiles.setAdapter(manager.getAdapterFiles());
		 * 
		 * lvFiles.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * public void onItemClick(AdapterView<?> parent, View view, int
		 * position, long id) { lvSelectedFile = position;
		 * 
		 * outp.setText("itemClick: position = " + position + ", id = " + id);
		 * int count = parent.getCount(); for (int i = 0; i < count; i++) {
		 * parent.getChildAt(i).setBackgroundColor(Color.TRANSPARENT); }
		 * view.setBackgroundColor(Color.GRAY);
		 * 
		 * // Листы manager.formLists(position);
		 * lvSheets.setAdapter(manager.getAdapterSheets()); } });
		 * 
		 * lvSheets.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * public void onItemClick(AdapterView<?> parent, View view, int
		 * position, long id) { lvSelectedSheet = position; } });
		 * 
		 * // ///////////////////////////
		 */return rootView;
	}

	@Override
	public void onViewCreated(View viewMain, Bundle savedInstanceState) {

	}

	public void setState(int state) {
		switch (state) {
		case 1:
			outp.setText("Выберите файл");
			break;
		case 2:
			outp.setText("Выберите лист");
			break;
		}
		this.state = state;

		LvDownloadAdapter adapter = new LvDownloadAdapter(getActivity(),
				manager, this);
		if (manager.getFileNames().length == 0) {
			lvFiles.setAdapter(null);
		} else
			lvFiles.setAdapter(adapter);
	}
}
