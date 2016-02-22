package com.example.view.fragments;

import java.util.Collections;
import java.util.List;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import com.example.p5.R;
import com.example.xml.LessonData;
import com.example.xml.XMLSerialize;

public class FragmentTwo extends Fragment {

	Button btnImport;
	Button btnExport;
	
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
		
		btnImport = (Button) rootView.findViewById(R.id.btn_import);
		btnExport = (Button) rootView.findViewById(R.id.btn_export);

		outp = (TextView) rootView.findViewById(R.id.textView1);
		etGroup = (EditText) rootView.findViewById(R.id.editTextGroup);
		lvFiles = (ListView) rootView.findViewById(R.id.listViewExelFiles);
		btnBackListView = (Button) rootView.findViewById(R.id.buttonBackList);
		loadButton = (Button) rootView.findViewById(R.id.loadButton);
		ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, new String[]{"1", "2"});
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSubgroup = (Spinner) rootView.findViewById(R.id.spinnerSubgroup);
		spSubgroup.setAdapter(adapter);
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
				subgroup = position + 1;
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
							if (lvSelectedSheet == -1)
							{
								Toast.makeText(getActivity(), "Выберите лист", Toast.LENGTH_SHORT).show();
								return;
							}
							List<com.example.controller.Lesson> lessonList = null;
							String group = etGroup.getText().toString();
							group = group.trim();
							lessonList = manager.getLessons(lvSelectedFile, lvSelectedSheet, group, subgroup);
							
							if (lessonList == null) {
								Toast.makeText(getActivity(), "Такой группы не существует", Toast.LENGTH_SHORT).show();
								return;
							}
							
							LessonData data = new LessonData(); data.list = lessonList;
							Collections.sort(data.list); 
							XMLSerialize.write(data, getActivity());
						}
						else {
							Toast.makeText(getActivity(), "Такой группы не существует", Toast.LENGTH_SHORT).show();
							return;
						}							
					}
					catch (Exception e) { e.printStackTrace(); }
					outp.setText("Рассписание загружено");
					Toast.makeText(getActivity(), "Рассписание загружено", Toast.LENGTH_SHORT).show();
				}
			}
		});		
		/////////////////////////////////////
		// Импорт Экспорт
		btnExport.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LessonData data = new LessonData();
				try {
					data = XMLSerialize.read(getActivity());
					XMLSerialize.write(data, "rasp");
				}
				catch (Exception e) { e.printStackTrace(); }
				Toast.makeText(getActivity(), "Рассписание экспортировано", Toast.LENGTH_SHORT).show();
			}
		});
		
		btnImport.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LessonData data = null; 
				try {
					data = XMLSerialize.read(getActivity(), "rasp");
					XMLSerialize.write(data, getActivity());
				}
				catch (Exception e) { e.printStackTrace(); }
				Toast.makeText(getActivity(), "Рассписание импортировано", Toast.LENGTH_SHORT).show();
			}
		});
		
		return rootView;
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
