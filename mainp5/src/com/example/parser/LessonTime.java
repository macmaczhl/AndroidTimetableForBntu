package com.example.parser;

public class LessonTime {
	private int day; //Calendar
	private int hour;
	private int minute;
	
	public LessonTime(int day, int hour, int minute)
	{
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}
	
	public int getDay()
	{
		return day;
	}
	public int getHour()
	{
		return hour;
	}
	public void setHour(int hour)
	{
		this.hour = hour;
	}
	public int getMinute()
	{
		return minute;
	}
	public void setMinute(int minute)
	{
		this.minute = minute;
	}
}
