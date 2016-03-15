package com.example.controller;

import java.util.Collections;
import java.util.Iterator;

import com.example.xml.DataManager;
import com.example.xml.XMLSerialize;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.View.OnLongClickListener;

public class OnRemoveListener implements OnLongClickListener {

	private LessonAdapter adapter;
	private int position;

	public OnRemoveListener(LessonAdapter adapter, int position) {
		// TODO Auto-generated constructor stub
		this.adapter = adapter;
		this.position = position;
	}

	@Override
	public boolean onLongClick(final View v) {
		Builder ad = new AlertDialog.Builder(adapter.act.getActivity());
		ad.setTitle("Удаление"); // заголовок
		ad.setMessage("Вы действительно хотите удалить запись?"); // сообщение
		ad.setPositiveButton("Да", new OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				int subGroup = 0;
				DataManager dataManager = null;
				try {
					subGroup = adapter.act.getSubGroup();
					dataManager = XMLSerialize.read(adapter.act.getActivity());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Iterator<Lesson> iter = dataManager.lessonData.get(subGroup - 1).list.iterator();
				while (iter.hasNext()) {
					Lesson lesson = iter.next();

					if (lesson.equals(adapter.getItem(position))) {
						adapter.remove(lesson);
						iter.remove();
						break;
					}
				}

				try {
					Collections.sort(dataManager.lessonData.get(subGroup - 1).list);
					XMLSerialize.write(dataManager, adapter.act.getActivity());
				} catch (Exception e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}

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

		return true;
	}

}
