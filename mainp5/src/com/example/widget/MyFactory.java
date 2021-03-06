package com.example.widget;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.controller.Lesson;
import com.example.p5.R;
import com.example.xml.XMLSerialize;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

@SuppressLint("SimpleDateFormat")
public class MyFactory implements RemoteViewsFactory {
	List<Lesson> allLessons = new ArrayList<Lesson>();
	List<Lesson> listLessons;
	Context context;
	SimpleDateFormat sdf;
	int widgetID;

	MyFactory(Context ctx, Intent intent) {

		listLessons = new ArrayList<Lesson>();
		context = ctx;
		sdf = new SimpleDateFormat("HH:mm:ss");
		widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);

	}

	@Override
	public void onCreate() {

	}

	@Override
	public int getCount() {
		return listLessons.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}
	
	
	
	
	@Override
	public RemoteViews getViewAt(int position) {

		RemoteViews rView = new RemoteViews(context.getPackageName(),
				R.layout.item);
		
		rView.setTextViewText(R.id.tvDate, listLessons.get(position).getDate());
		rView.setTextViewText(R.id.tvCorpus, listLessons.get(position)
				.getCorpus());
		rView.setTextViewText(R.id.tvRoom, listLessons.get(position)
				.getClassRoom());
		Date currentDate = new Date();
		
		boolean ifCurrentLesson = false;
			Date dateTemp  = listLessons.get(position).getDateOriginal();
				try {
					ifCurrentLesson = MyProvider.calendar.get(Calendar.DAY_OF_YEAR)== Calendar.getInstance().get(Calendar.DAY_OF_YEAR) &&
							Lesson.compareDate(currentDate,listLessons.get(position).getDateOriginal())>0 &&
							Lesson.compareDate(currentDate,Lesson.AddDate(dateTemp, 1, 35))<0;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			

		if(ifCurrentLesson)
		{
			rView.setInt(R.id.tvDate, "setBackgroundColor", 
			        android.graphics.Color.RED);
			rView.setInt(R.id.tvSubj, "setBackgroundColor", 
			        android.graphics.Color.RED);
			rView.setInt(R.id.tvCorpus, "setBackgroundColor", 
			        android.graphics.Color.RED);
			rView.setInt(R.id.tvRoom, "setBackgroundColor", 
			        android.graphics.Color.RED);
		}
		else
		{
			rView.setInt(R.id.tvDate, "setBackgroundColor", 
			        Color.TRANSPARENT);
			rView.setInt(R.id.tvSubj, "setBackgroundColor", 
					Color.TRANSPARENT);
			rView.setInt(R.id.tvCorpus, "setBackgroundColor", 
					Color.TRANSPARENT);
			rView.setInt(R.id.tvRoom, "setBackgroundColor", 
					Color.TRANSPARENT);
		}
		
		
			
		
		
		switch (listLessons.get(position).getType()) {
		case 0:
			SpannableString spanString = new SpannableString(listLessons.get(
					position).getSubject());
			spanString.setSpan(new StyleSpan(Typeface.BOLD), 0,
					spanString.length(), 0);
			rView.setTextViewText(R.id.tvSubj, spanString);
			break;
		case 1:

			spanString = new SpannableString(listLessons.get(position)
					.getSubject());
			spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0,
					spanString.length(), 0);
			rView.setTextViewText(R.id.tvSubj, spanString);

			break;
		}
		return rView;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public void onDataSetChanged() {

		int week = MyProvider.calendar.get(Calendar.WEEK_OF_YEAR);// ������ - ������
		if (week % 2 == 0)
			week = 1;
		else
			week = 2;

		try {
			allLessons = XMLSerialize.read(context).list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			allLessons = new ArrayList();
		}
		listLessons.clear();

		try {
			for (Lesson obj : allLessons) {
				if (obj.getDay() == MyProvider.calendar.get(Calendar.DAY_OF_WEEK))
					if (obj.getWeek() == week || obj.getWeek() == 0)
						listLessons.add(obj);
			}

		} catch (Exception e) {

		}
	}

	@Override
	public void onDestroy() {

	}

}