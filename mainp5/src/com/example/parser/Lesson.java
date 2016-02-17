package com.example.parser;


public class Lesson {
	private LessonTime time;
	private String subject;
	private String housing;
	private String classroom;

	public Lesson(LessonTime time, String subjname, String housing,
			String classroom) {
		this.time = time;
		this.subject = subjname;
		this.housing = housing;
		this.classroom = classroom;
	}

	public LessonTime getTime() {
		return time;
	}

	public void setTime(LessonTime time) {
		this.time = time;
	}

	public String getHousing() {
		return housing;
	}

	public void setHousing(String housing) {
		this.housing = housing;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subjname) {
		this.subject = subjname;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public String toString() {
		return subject;
	}
}
