package com.example.widget;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.p5.R;
import com.example.p5.R.id;
import com.example.p5.R.layout;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

@SuppressLint("SimpleDateFormat")
public class MyProvider extends AppWidgetProvider {
	String[] days = { "", "воскресенье", "понедельник", "вторник", "среда",
			"четверг", "пятница", "суббота" };
	final static String ACTION_PREV = "com.example.p5.clkPrev";
	final static String ACTION_NEXT = "com.example.p5.clkNext";
	final static String ACTION_CURRENT = "com.example.p5.clkCurr";
	int week;
	SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
	static Calendar calendar = Calendar.getInstance();
	public static int dayOfTheWeek;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		dayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);
		for (int i : appWidgetIds) {
			updateWidget(context, appWidgetManager, i);
		}
	}

	void updateWidget(Context context, AppWidgetManager appWidgetManager,
			int appWidgetId) {
		RemoteViews rv = new RemoteViews(context.getPackageName(),
				R.layout.widget);
		rv.setTextViewText(R.id.tvUpdate, days[dayOfTheWeek]);

		Intent updIntent = new Intent(context, MyProvider.class);
		updIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		updIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
				new int[] { appWidgetId });

		setUpdatePrev(rv, context, appWidgetId);
		setUpdateNext(rv, context, appWidgetId);
		setUpdateTV(rv, context, appWidgetId);

		setList(rv, context, appWidgetId);

		appWidgetManager.updateAppWidget(appWidgetId, rv);

		appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,
				R.id.lvList);

	}

	void setUpdateTV(RemoteViews rv, Context context, int appWidgetId) {

		Intent updIntent = new Intent(context, MyProvider.class);
		updIntent.setAction(ACTION_CURRENT);
		updIntent.putExtra("widgetId", new int[] { appWidgetId });
		PendingIntent updPIntent = PendingIntent.getBroadcast(context,
				appWidgetId, updIntent, 0);
		rv.setOnClickPendingIntent(R.id.tvUpdate, updPIntent);
	}

	void setUpdatePrev(RemoteViews rv, Context context, int appWidgetId) {

		Intent updIntent = new Intent(context, MyProvider.class);
		updIntent.setAction(ACTION_PREV);
		updIntent.putExtra("widgetId", new int[] { appWidgetId });
		PendingIntent updPIntent = PendingIntent.getBroadcast(context,
				appWidgetId, updIntent, 0);
		rv.setOnClickPendingIntent(R.id.tvPrev, updPIntent);
	}

	void setUpdateNext(RemoteViews rv, Context context, int appWidgetId) {

		Intent updIntent = new Intent(context, MyProvider.class);
		updIntent.setAction(ACTION_NEXT);
		updIntent.putExtra("widgetId", new int[] { appWidgetId });
		PendingIntent updPIntent = PendingIntent.getBroadcast(context,
				appWidgetId, updIntent, 0);
		rv.setOnClickPendingIntent(R.id.tvNext, updPIntent);
	}

	void setList(RemoteViews rv, Context context, int appWidgetId) {
		Intent adapter = new Intent(context, MyService.class);
		adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		Uri data = Uri.parse(adapter.toUri(Intent.URI_INTENT_SCHEME));
		adapter.setData(data);
		rv.setRemoteAdapter(R.id.lvList, adapter);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);

		int[] mas = intent.getIntArrayExtra("widgetId");
		int mAppWidgetId = -1;
		if (mas != null)
			mAppWidgetId = mas[0];

		if (intent.getAction().equalsIgnoreCase(ACTION_PREV)) {
			dayOfTheWeek--;

			if (dayOfTheWeek == 0)
				dayOfTheWeek = 7;

		}
		if (intent.getAction().equalsIgnoreCase(ACTION_NEXT)) {

			dayOfTheWeek++;
			if (dayOfTheWeek == 8)
				dayOfTheWeek = 1;

		} else if (intent.getAction().equalsIgnoreCase(ACTION_CURRENT)) {

			dayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);

		}
		Log.d("azaza2", String.valueOf(mAppWidgetId));
		updateWidget(context, AppWidgetManager.getInstance(context),
				mAppWidgetId);

	}

}