package com.example.controller;

import java.util.List;

import com.example.view.AddDialog;
import com.example.xml.XMLSerialize;

import android.view.View;
import android.view.View.OnClickListener;

public class OnEditListener implements OnClickListener {
	
	private LessonAdapter adapter;
	private int position;
	
	public OnEditListener(LessonAdapter adapter, int position) {
		// TODO Auto-generated constructor stub
		this.adapter = adapter;
		this.position = position;
	}
	@Override
	public void onClick(View v) {
		int subGroup;
		List<Lesson> allLessons = null;
		int posEdit = -1;
		try {
			subGroup = adapter.act.getSubGroup();
			allLessons = XMLSerialize.read(adapter.act.getActivity()).lessonData.get(subGroup-1).list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < allLessons.size(); i++) {
			if (allLessons.get(i).equals(adapter.getItem(position)))
				posEdit = i;
		}
		AddDialog dlg1 = new AddDialog(posEdit);
		dlg1.show(adapter.act.getFragmentManager(), "228");
		dlg1.SetFragment(adapter.act);	
		
	}

}
