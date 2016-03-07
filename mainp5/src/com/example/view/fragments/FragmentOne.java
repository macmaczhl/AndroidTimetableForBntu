package com.example.view.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.controller.Lesson;
import com.example.controller.LessonAdapter;
import com.example.p5.R;
import com.example.xml.XMLSerialize;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class FragmentOne extends Fragment {
	Spinner spinner;
	public static int selected;
	int subGroup;
	View bufView;

	public FragmentOne() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment1, container, false);

		return rootView;
	}

	@Override
	public void onViewCreated(View viewMain, Bundle savedInstanceState) {
		// ////////////////
		View view = getActivity().findViewById(R.id.legend);
		view.setBackgroundResource(R.drawable.shapelegend);
		bufView = viewMain;
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, getResources()
						.getStringArray(R.array.weekday));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner = (Spinner) viewMain.findViewById(R.id.spinner1);
		spinner.setAdapter(adapter);

		Calendar calendar = Calendar.getInstance();
		spinner.setSelection(calendar.get(Calendar.DAY_OF_WEEK) - 1);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				selected = spinner.getSelectedItemPosition() + 1;
				if (selected == 8)
					selected = 7;
				ShowList();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		// /////////////////
	}

	public void ShowList() {

		ListView mylist = (ListView) bufView.findViewById(R.id.gridView);
		try {
			List<Lesson> allLessons = new ArrayList<Lesson>();
			List<Lesson> currentList = new ArrayList<Lesson>();
			subGroup = XMLSerialize.read(this.getActivity()).getSubGroup(getActivity());
			allLessons = XMLSerialize.read(this.getActivity()).lessonData.get(subGroup-1).list;

			for (Lesson obj : allLessons) {
				if (obj.getDay() == selected)
					currentList.add(obj);
			}
			LessonAdapter adapter = new LessonAdapter(getActivity(),
					currentList);
			adapter.SetActivity(this);
			if (allLessons.isEmpty())
				mylist.setAdapter(null);
			else {
				mylist.setAdapter(adapter);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mylist.setAdapter(null);
		}
	}

}
