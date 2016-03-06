package com.example.xml;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.example.controller.Lesson;
import com.example.widget.MyProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

@Root(name = "data")
public class DataManager {

	@Element(name = "subGroup")
	private int subGroup;
	@ElementList(inline = true, name = "lessons")
	public List<LessonData> lessonData = new ArrayList<LessonData>();
	public int getSubGroup(Context context)
	{
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		boolean swapGroups = sp.getBoolean("swapGroups", false);
			if(swapGroups){
				boolean swapWeeks = sp.getBoolean("swapWeeks", false);
				
				int week = MyProvider.calendar.get(Calendar.WEEK_OF_YEAR);// четный -
																			// первая
				if (week % 2 == 0)
					week = swapWeeks?2:1;
				else
					week = swapWeeks?1:2;

				Log.d("azaza23", String.valueOf(week));
				// первая
				if (week % 2 == 0)
					return 2;
				else
					return 1;
			}
		return subGroup;
	}

	public void setSubGroup(int subGroup)
	{
		this.subGroup = subGroup;
	}
	
	
	public DataManager(){
	}

}
