package com.example.view;

import java.util.Collections;

import com.example.controller.Lesson;
import com.example.controller.LessonAdapter;
import com.example.p5.R;
import com.example.view.fragments.FragmentOne;
import com.example.xml.DataManager;
import com.example.xml.LessonData;
import com.example.xml.XMLSerialize;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AddDialog extends DialogFragment implements
		DialogInterface.OnClickListener {
	private View form = null;
	String title;
	Spinner spinner2;
	Spinner spinner3;
	FragmentOne act;
	boolean isEdit;
	int posEdit;
	DataManager dataManager;
	int subGroup;


	public AddDialog() {
		title = "Добавление пары";
		isEdit = false;
		
	}

	public AddDialog(int position) {
		title = "Изменение пары";
		isEdit = true;
		posEdit = position;
	}

	public void SetFragment(FragmentOne act) {
		this.act = act;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(act.getActivity());
		form = getActivity().getLayoutInflater().inflate(R.layout.dialogadd,
				null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		spinner2 = (Spinner) form.findViewById(R.id.spinner3);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				getActivity(), R.array.week,
				android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter2);

		spinner3 = (Spinner) form.findViewById(R.id.spinner4);
		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
				getActivity(), R.array.types,
				android.R.layout.simple_spinner_item);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner3.setAdapter(adapter3);

		EditText durationBox = (EditText) form.findViewById(R.id.item_duration);
		durationBox.setText(sp.getString("duration", "95"));
		try {
			dataManager = XMLSerialize.read(act.getActivity());
			subGroup = act.getSubGroup();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dataManager = new DataManager();
			dataManager.lessonData.add(new LessonData());
			dataManager.lessonData.add(new LessonData());
			dataManager.setSubGroup(1);
			subGroup = 1;
		}
		if (isEdit) {
			EditText timeBox = (EditText) form.findViewById(R.id.item_time);
			EditText subjectBox = (EditText) form
					.findViewById(R.id.item_subject);
			EditText classroomBox = (EditText) form
					.findViewById(R.id.item_classroom);
			EditText corpusBox = (EditText) form.findViewById(R.id.item_corpus);

			spinner2.setSelection(dataManager.lessonData.get(subGroup-1).list.get(posEdit).getWeek());

			spinner3.setSelection(dataManager.lessonData.get(subGroup-1).list.get(posEdit).getType());

			timeBox.setText(dataManager.lessonData.get(subGroup-1).list.get(posEdit).getDate());
			subjectBox.setText(dataManager.lessonData.get(subGroup-1).list.get(posEdit).getSubject());
			classroomBox.setText(dataManager.lessonData.get(subGroup-1).list.get(posEdit).getClassRoom());
			corpusBox.setText(dataManager.lessonData.get(subGroup-1).list.get(posEdit).getCorpus());
			durationBox.setText(String.valueOf(dataManager.lessonData.get(subGroup-1).list.get(posEdit).getDuration()));
		}

		return (builder.setTitle(title).setView(form)
				.setPositiveButton(android.R.string.ok, this)
				.setNegativeButton(android.R.string.cancel, null).create());
	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub

		EditText timeBox = (EditText) form.findViewById(R.id.item_time);
		EditText subjectBox = (EditText) form.findViewById(R.id.item_subject);
		EditText corpusBox = (EditText) form.findViewById(R.id.item_corpus);
		EditText classroomBox = (EditText) form
				.findViewById(R.id.item_classroom);
		EditText durationBox = (EditText) form.findViewById(R.id.item_duration);
		String time = null, subject = null, corpus = null, classroom = null,duration = null;
		time = timeBox.getText().toString();
		subject = subjectBox.getText().toString();
		corpus = corpusBox.getText().toString();
		classroom = classroomBox.getText().toString();
		duration = durationBox.getText().toString();
		int dayOfWeek = MainActivityNavig.selected;
		int week = spinner2.getSelectedItemPosition();
		int type = spinner3.getSelectedItemPosition();

		if (subject.isEmpty())
			subject = " ";
		if (corpus.isEmpty())
			corpus = " ";
		if (classroom.isEmpty())
			classroom = " ";

		try {
			Lesson lesson = new Lesson();
			lesson.fillLesson(time, subject, corpus, classroom, dayOfWeek,
					week, type,duration);
			if (isEdit) {
				dataManager.lessonData.get(subGroup-1).list.set(posEdit, lesson);
			} else {
				dataManager.lessonData.get(subGroup-1).list.add(lesson);
			}
			Collections.sort(dataManager.lessonData.get(subGroup-1).list);
			XMLSerialize.write(dataManager, act.getActivity());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		act.FillLessonAdapter(subGroup);
	}

	@Override
	public void onDismiss(DialogInterface unused) {
		super.onDismiss(unused);
	}

	@Override
	public void onCancel(DialogInterface unused) {
		super.onCancel(unused);
	}

}
