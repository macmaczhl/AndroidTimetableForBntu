package com.example.controller;

import java.util.List;

import com.example.p5.R;
import com.example.view.fragments.FragmentOne;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LessonAdapter extends ArrayAdapter<Lesson> {

	List<Lesson> lessons;
	TextView txtDate;
	TextView txtSubj;
	TextView txtRoom;
	TextView txtCorpus;
	TextView txtWeek;
	LayoutInflater lInflater;
	FragmentOne act;

	public void SetFragment(FragmentOne act) {
		this.act = act;
	}

	public LessonAdapter(Context context, List<Lesson> lessons) {
		super(context, 0, lessons);
		this.lessons = lessons;
		lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lessons.size();
	}

	@Override
	public Lesson getItem(int position) {
		// TODO Auto-generated method stub
		return lessons.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		if (view == null) {
			view = lInflater.inflate(R.layout.item2, parent, false);
		}
		Lesson p = lessons.get(position);
		switch (p.getType()) {
		case 0:
			// view.setBackgroundColor(0xFFF68000);
			view.setBackgroundResource(R.drawable.selectlect);
			break;
		case 1:
			view.setBackgroundResource(R.drawable.selectpract);
			break;
		}

		txtDate = (TextView) view.findViewById(R.id.tvDate);
		txtDate.setText(p.getDate());

		txtSubj = (TextView) view.findViewById(R.id.tvSubj);
		txtSubj.setText(p.getSubject());

		txtCorpus = (TextView) view.findViewById(R.id.tvCorpus);
		txtCorpus.setText(p.getCorpus());

		txtRoom = (TextView) view.findViewById(R.id.tvRoom);
		txtRoom.setText(p.getClassRoom());

		txtWeek = (TextView) view.findViewById(R.id.tvWeek);
		txtWeek.setText(String.valueOf(p.getWeek()));

		view.setOnClickListener(new OnEditListener(this, position));
		view.setOnLongClickListener(new OnRemoveListener(this, position));

		return view;
	}

}
