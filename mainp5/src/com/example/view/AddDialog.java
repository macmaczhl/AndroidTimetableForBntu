package com.example.view;

import java.util.Collections;

import com.example.controller.Lesson;
import com.example.p5.R;
import com.example.view.fragments.FragmentOne;
import com.example.xml.LessonData;
import com.example.xml.XMLSerialize;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
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
	LessonData lessonData;

	public AddDialog() {
		title = "Добавление пары";
		isEdit = false;
	}

	public AddDialog(int position) {
		title = "Изменение пары";
		isEdit = true;
		posEdit = position;
	}

	public void SetActivity(FragmentOne act) {
		this.act = act;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

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
		try {
			lessonData = XMLSerialize.read(act.getActivity());
			Log.d("issss", "???");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			lessonData = new LessonData();
		}
		if (isEdit) {
			EditText timeBox = (EditText) form.findViewById(R.id.item_time);
			EditText subjectBox = (EditText) form
					.findViewById(R.id.item_subject);
			EditText classroomBox = (EditText) form
					.findViewById(R.id.item_classroom);
			EditText corpusBox = (EditText) form.findViewById(R.id.item_corpus);

			spinner2.setSelection(lessonData.list.get(posEdit).getWeek());

			spinner3.setSelection(lessonData.list.get(posEdit).getType());

			timeBox.setText(lessonData.list.get(posEdit).getDate());
			subjectBox.setText(lessonData.list.get(posEdit).getSubject());
			classroomBox.setText(lessonData.list.get(posEdit).getClassRoom());
			corpusBox.setText(lessonData.list.get(posEdit).getCorpus());
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
		String time = null, subject = null, corpus = null, classroom = null;
		time = timeBox.getText().toString();
		subject = subjectBox.getText().toString();
		corpus = corpusBox.getText().toString();
		classroom = classroomBox.getText().toString();
		int dayOfWeek = FragmentOne.selected;
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
			lesson.FillLesson(time, subject, corpus, classroom, dayOfWeek,
					week, type);
			if (isEdit) {
				lessonData.list.set(posEdit, lesson);
			} else {
				lessonData.list.add(lesson);
			}
			Collections.sort(lessonData.list);
			XMLSerialize.write(lessonData, act.getActivity());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		act.ShowList();
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
