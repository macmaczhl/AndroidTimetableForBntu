package com.example.view.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.controller.Lesson;
import com.example.controller.LessonAdapter;
import com.example.controller.OnChangeDay;
import com.example.controller.OnChangeSubGroup;
import com.example.p5.R;
import com.example.view.MainActivityNavig;
import com.example.xml.XMLSerialize;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class FragmentOne extends Fragment {
	View bufView;
	public LessonAdapter adapter;
	public Button button1;
	public Button button2;
	private Button buttonSunday;
	private Button buttonMonday;
	private Button buttonTuesday;
	private Button buttonWednesday;
	private Button buttonThursday;
	private Button buttonFriday;
	private Button buttonSaturday;
	public List<Button> listButton = new ArrayList<Button>();;
	public FragmentOne() {
	}
	
	public int getSubGroup(){
		return MainActivityNavig.subGroup;
	}
	
	public void setSubGroup(int subGroup){
		MainActivityNavig.subGroup = subGroup;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment1, container, false);
				
		buttonSunday = (Button)rootView.findViewById(R.id.buttonSunday);
		buttonMonday = (Button)rootView.findViewById(R.id.buttonMonday);
		buttonTuesday = (Button)rootView.findViewById(R.id.buttonTuesday);
		buttonWednesday = (Button)rootView.findViewById(R.id.buttonWednesday);
		buttonThursday = (Button)rootView.findViewById(R.id.buttonThursday);
		buttonFriday = (Button)rootView.findViewById(R.id.buttonFriday);
		buttonSaturday = (Button)rootView.findViewById(R.id.buttonSaturday);
		
		buttonSunday.setOnClickListener(new OnChangeDay(this,1));
		buttonMonday.setOnClickListener(new OnChangeDay(this,2));
		buttonTuesday.setOnClickListener(new OnChangeDay(this,3));
		buttonWednesday.setOnClickListener(new OnChangeDay(this,4));
		buttonThursday.setOnClickListener(new OnChangeDay(this,5));
		buttonFriday.setOnClickListener(new OnChangeDay(this,6));
		buttonSaturday.setOnClickListener(new OnChangeDay(this,7));
		
		listButton.add(buttonSunday);
		listButton.add(buttonMonday);
		listButton.add(buttonTuesday);
		listButton.add(buttonWednesday);
		listButton.add(buttonThursday);
		listButton.add(buttonFriday);
		listButton.add(buttonSaturday);
		

		setSelectedButton(listButton, MainActivityNavig.selected);
	
		button1 = (Button)rootView.findViewById(R.id.button1);
		button1.setOnClickListener(new OnChangeSubGroup(this, 1));
	    button2 = (Button)rootView.findViewById(R.id.button2);
		button2.setOnClickListener(new OnChangeSubGroup(this, 2));

		if(getSubGroup()==1){
			button1.setBackgroundResource(R.drawable.shapebutton2);
			button2.setBackgroundResource(R.drawable.shapebutton);
		}
		else{
			button2.setBackgroundResource(R.drawable.shapebutton2);
			button1.setBackgroundResource(R.drawable.shapebutton);
		}
		return rootView;
	}
	
	public void setSelectedButton(List<Button> listButton, int selected)
	{
		for(Button btn:listButton){
			btn.setBackgroundResource(R.drawable.shapebutton);
		}
		listButton.get(selected-1).setBackgroundResource(R.drawable.shapebutton2);
	}

	@Override
	public void onViewCreated(View viewMain, Bundle savedInstanceState) {
		// ////////////////
		View view = getActivity().findViewById(R.id.legend);
		view.setBackgroundResource(R.drawable.shapelegend);
		bufView = viewMain;
		FillLessonAdapter(getSubGroup());
	}

	public void FillLessonAdapter(int subGroup) {
		setSubGroup(subGroup);
		ListView mylist = (ListView) bufView.findViewById(R.id.gridView);
		try {
			
			List<Lesson> allLessons = new ArrayList<Lesson>();
			List<Lesson> currentList = new ArrayList<Lesson>();
			allLessons = XMLSerialize.read(this.getActivity()).lessonData.get(subGroup-1).list;
			Log.d("azaza23", String.valueOf(MainActivityNavig.selected));
			for (Lesson obj : allLessons) {
				if (obj.getDay() == MainActivityNavig.selected)
					currentList.add(obj);
			}
			adapter = new LessonAdapter(getActivity(),
					currentList);
			adapter.SetFragment(this);
			adapter.setNotifyOnChange(true);
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
