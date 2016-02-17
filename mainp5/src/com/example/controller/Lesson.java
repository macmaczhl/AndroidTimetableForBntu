package com.example.controller;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@SuppressLint("SimpleDateFormat")
@Root(name = "lesson")
public class Lesson implements Comparable<Lesson> {
	private Date time;
	private int duration;
	private String subject;
	private String corpus;
	private String classroom;
	private int day;
	private int type;
	private int week;

	@SuppressLint("SimpleDateFormat")
	public void fillLesson(String time, String subjname, String corpus,
			String classroom, int day, int week, int type,String duration) {
		DateFormat format = new SimpleDateFormat("HH:mm");
		Date timetemp = new Date();
		try {
			timetemp = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		this.time = timetemp;
		this.subject = subjname;
		this.classroom = classroom;
		this.day = day;
		this.week = week;
		this.corpus = corpus;
		this.type = type;
		this.duration = Integer.valueOf(duration);
	}

	@SuppressLint("SimpleDateFormat")
	public Lesson() {
	}

	public String toString() {
		DateFormat format = new SimpleDateFormat("hh:mm");
		return format.format(time) + "   " + classroom + "   " + subject;
	}

	@Element(name = "subject")
	public String getSubject() {
		return subject;
	}

	@Element(name = "subject")
	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Element(name = "day")
	public int getDay() {
		return day;
	}

	@Element(name = "day")
	public void setDay(int day) {
		this.day = day;
	}
	
	
	@Element(name = "duration")
	public int getDuration() {
		return duration;
	}

	@Element(name = "duration")
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	

	@SuppressLint("SimpleDateFormat")
	@Element(name = "date")
	public String getDate() {
		DateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(time);
	}

	public Date getDateOriginal() {
		return time;
	}

	@Element(name = "date")
	public void setDate(String time) {
		DateFormat format = new SimpleDateFormat("HH:mm");
		Date timetemp = new Date();
		try {
			timetemp = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		this.time = timetemp;
	}

	@Element(name = "corpus")
	public String getCorpus() {
		return corpus;
	}

	@Element(name = "corpus")
	public void setCorpus(String corpus) {
		this.corpus = corpus;
	}

	@Element(name = "classroom")
	public String getClassRoom() {
		return classroom;
	}

	@Element(name = "classroom")
	public void setClassRoom(String classroom) {
		this.classroom = classroom;
	}

	@Element(name = "week")
	public void setWeek(int week) {
		this.week = week;
	}

	@Element(name = "week")
	public int getWeek() {
		return week;
	}

	@Element(name = "type")
	public void setType(int type) {
		this.type = type;
	}

	@Element(name = "type")
	public int getType() {
		return type;
	}

	@Override
	public boolean equals(Object obj) {
		Lesson lesson = (Lesson) obj;
		if (this.time.equals(lesson.time) && this.day == lesson.day
				&& this.subject.equals(lesson.subject)
				&& this.corpus.equals(lesson.corpus)
				&& this.classroom.equals(lesson.classroom)
				&& this.week == lesson.week)
			return true;
		else
			return false;
	}

	@Override
	public int compareTo(Lesson arg0) {
		return this.time.compareTo(arg0.time);
	}

	static public int compareDate(Date arg1, Date arg2) {
		DateFormat format = new SimpleDateFormat("HH");
		if (Integer.valueOf(format.format(arg1)) < Integer.valueOf(format
				.format(arg2)))
			return -1;
		if (Integer.valueOf(format.format(arg1)) > Integer.valueOf(format
				.format(arg2)))
			return 1;
		if (Integer.valueOf(format.format(arg1)) == Integer.valueOf(format
				.format(arg2))) {
			format = new SimpleDateFormat("mm");
			if (Integer.valueOf(format.format(arg1)) < Integer.valueOf(format
					.format(arg2)))
				return -1;
			if (Integer.valueOf(format.format(arg1)) > Integer.valueOf(format
					.format(arg2)))
				return 1;
			return 0;
		}
		return 0;
	}
	
	public Date getEndOfLesson() throws ParseException
	{
		Date timeOfEnd = time;
		return addDate(timeOfEnd,duration);
	}
	
	public String getEndOfLessonString() throws ParseException
	{
		DateFormat format = new SimpleDateFormat("HH:mm");
		Date timeOfEnd = time;
		return format.format(addDate(timeOfEnd,duration));
	}

	static private Date addDate(Date date, int mins)
			throws ParseException {
		int hour;
		int min;
		DateFormat format = new SimpleDateFormat("HH");
		hour = Integer.valueOf(format.format(date));
		format = new SimpleDateFormat("mm");
		min = Integer.valueOf(format.format(date));

		min += mins;
		while (min >= 60) {
			min -= 60;
			hour++;
		}

		format = new SimpleDateFormat("HH:mm");
		if (min >= 10)
			date = format.parse(String.valueOf(hour) + ":"
					+ String.valueOf(min));
		else
			date = format.parse(String.valueOf(hour) + ":0"
					+ String.valueOf(min));
		return date;

	}

}
