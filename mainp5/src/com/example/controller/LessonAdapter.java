package com.example.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.example.p5.R;
import com.example.view.AddDialog;
import com.example.view.MainActivity;
import com.example.xml.LessonData;
import com.example.xml.XMLSerialize;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class LessonAdapter extends BaseAdapter {

	private Context context;
	List<Lesson> lessons;
	TextView txtDate;
	TextView txtSubj;
	TextView txtRoom;
	TextView txtCorpus;
	TextView txtWeek;
	ImageButton buttonEdit;
	ImageButton buttonDel;
	LayoutInflater lInflater;
	int selected;
	List<Lesson> allLessons = new ArrayList<Lesson>();
	MainActivity act;
	AlertDialog.Builder ad;

	public void SetActivity(MainActivity act) {
		this.act = act;
	}

	public LessonAdapter(Context context, List<Lesson> lessons) {
		this.context = context;
		this.lessons = lessons;
		lInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lessons.size();
	}

	@Override
	public Object getItem(int position) {
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
			view.setBackgroundColor(0xFFF68000);
			break;
		case 1:
			view.setBackgroundColor(Color.YELLOW);
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

		selected = MainActivity.selected;

		Item item = new Item();
		item.txtDate = txtDate;
		item.txtSubj = txtSubj;
		item.txtCorpus = txtCorpus;
		item.txtRoom = txtRoom;
		item.txtWeek = txtWeek;

		buttonEdit = (ImageButton) view.findViewById(R.id.tvEditButton);
		buttonEdit.setTag(item);

		buttonDel = (ImageButton) view.findViewById(R.id.tvDeleteButton);
		buttonDel.setTag(item);

		buttonEdit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int posEdit = -1;
				Item item = (Item) v.getTag();
				String date = item.txtDate.getText().toString();
				String subj = item.txtSubj.getText().toString();
				String corpus = item.txtCorpus.getText().toString();
				String room = item.txtRoom.getText().toString();
				int week = Integer.valueOf(item.txtWeek.getText().toString());
				Lesson temp = new Lesson();
				temp.FillLesson(date, subj, corpus, room, selected, week, 0);
				try {
					allLessons = XMLSerialize.read().list;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int i = 0; i < allLessons.size(); i++) {
					if (allLessons.get(i).equals(temp))
						posEdit = i;
				}

				AddDialog dlg1 = new AddDialog(posEdit);
				dlg1.show(act.getFragmentManager(), "228");
				dlg1.SetActivity(act);

			}

		});
		buttonDel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				ad = new AlertDialog.Builder(context);
				ad.setTitle("Удаление"); // заголовок
				ad.setMessage("Вы действительно хотите удалить запись?"); // сообщение
				ad.setPositiveButton("Да", new OnClickListener() {
					public void onClick(DialogInterface dialog, int arg1) {
						Item item = (Item) v.getTag();
						String date = item.txtDate.getText().toString();
						String subj = item.txtSubj.getText().toString();
						String corpus = item.txtCorpus.getText().toString();
						String room = item.txtRoom.getText().toString();
						int week = Integer.valueOf(item.txtWeek.getText()
								.toString());
						Lesson temp = new Lesson();
						temp.FillLesson(date, subj, corpus, room, selected,
								week, 0);
						try {
							allLessons = XMLSerialize.read().list;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						Iterator<Lesson> iter = allLessons.iterator();
						while (iter.hasNext()) {
							Lesson lesson = iter.next();

							if (lesson.equals(temp)) {
								iter.remove();
								break;
							}
						}

						LessonData lessonData = new LessonData();
						lessonData.list = allLessons;
						try {
							Collections.sort(lessonData.list);
							XMLSerialize.write(lessonData);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						act.ShowList();

					}
				});
				ad.setNegativeButton("Нет", new OnClickListener() {
					public void onClick(DialogInterface dialog, int arg1) {
					}
				});
				ad.setCancelable(true);
				ad.setOnCancelListener(new OnCancelListener() {
					public void onCancel(DialogInterface dialog) {
					}
				});
				ad.show();

			}
		});

		return view;
	}

}
