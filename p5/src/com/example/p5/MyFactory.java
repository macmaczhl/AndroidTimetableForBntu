package com.example.p5;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
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
		widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

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

		RemoteViews rView = new RemoteViews(context.getPackageName(), R.layout.item);

		rView.setTextViewText(R.id.tvDate, listLessons.get(position).getDate());
		rView.setTextViewText(R.id.tvCorpus, listLessons.get(position).getCorpus());
		rView.setTextViewText(R.id.tvRoom, listLessons.get(position).getClassRoom());
		switch(listLessons.get(position).getType())
		{
		case 0:
			SpannableString spanString = new SpannableString(listLessons.get(position).getSubject());
			spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
			rView.setTextViewText(R.id.tvSubj, spanString);
			break;
		case 1:
			
			spanString = new SpannableString(listLessons.get(position).getSubject());
			spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
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

		SimpleDateFormat sdf2 = new SimpleDateFormat("w");
		Date d = new Date();
		int week = Integer.valueOf(sdf2.format(d));//четный - первая
		if(week%2==0)
			week = 1;
		else
			week = 2;

		try {
			allLessons = XMLSerialize.read().list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			allLessons = new ArrayList(); 
		}
		listLessons.clear();

		try {
			for (Lesson obj : allLessons) {
				if (obj.getDay() == MyProvider.dayOfTheWeek)
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