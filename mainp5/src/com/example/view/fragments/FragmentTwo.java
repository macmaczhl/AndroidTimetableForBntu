package com.example.view.fragments;

import java.util.Collections;
import java.util.List;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.p5.R;
import com.example.parser.CellLesson;
import com.example.parser.MainParser;
import com.example.xml.LessonData;
import com.example.xml.XMLSerialize;

public class FragmentTwo extends Fragment {

	Button loadButton;
	TextView outp;

	ListView lvFiles;
	int lvSelectedFile = -1;

	ListView lvSheets;
	int lvSelectedSheet = -1;

	EditText etGroup;

	ListViewExcelFilesManager manager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment2, container, false);
		// ///////////////////////////////////////////////////////////
		// Обработчик кнопки
		loadButton = (Button) rootView.findViewById(R.id.loadButton);
		outp = (TextView) rootView.findViewById(R.id.textView1);
		etGroup = (EditText) rootView.findViewById(R.id.editTextGroup);

		OnClickListener oclLoadBtn = new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					List<com.example.controller.Lesson> lessonList = manager
							.getLessons(lvSelectedFile, lvSelectedSheet,
									etGroup.getText().toString(), 1);
					for (com.example.controller.Lesson les : lessonList) {
						Log.d("OOO", les.toString());
					}
					LessonData data = new LessonData();
					data.list = lessonList;
					Collections.sort(data.list);
					XMLSerialize.write(data, getActivity());
					outp.setText("Yes");
				} catch (Exception e) {
					e.printStackTrace();
					outp.setText("No");
				}
			}
		};
		loadButton.setOnClickListener(oclLoadBtn);

		// Файлы
		manager = new ListViewExcelFilesManager(getActivity());

		lvFiles = (ListView) rootView.findViewById(R.id.listViewExelFiles);
		lvSheets = (ListView) rootView.findViewById(R.id.listViewExcelSheets);

		lvFiles.setAdapter(manager.getAdapterFiles());

		lvFiles.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				lvSelectedFile = position;

				outp.setText("itemClick: position = " + position + ", id = "
						+ id);
				int count = parent.getCount();
				for (int i = 0; i < count; i++) {
					parent.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
				}
				view.setBackgroundColor(Color.GRAY);

				// Листы
				manager.formLists(position);
				lvSheets.setAdapter(manager.getAdapterSheets());
			}
		});

		lvSheets.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				lvSelectedSheet = position;
			}
		});

		// ///////////////////////////
		return rootView;
	}

	@Override
	public void onViewCreated(View viewMain, Bundle savedInstanceState) {

	}
}
