package com.example.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.IStabilityClassifier;

import android.util.Log;

public class LessonConverterFromParser {
	MainParser parser = null;
	int sheetNumber;
	String group;
	int subgroup;
	
	public LessonConverterFromParser(MainParser parser, int sheetNumber, String group, int subgroup) {
		this.parser = parser;
		this.sheetNumber = sheetNumber;
		this.group = group;
		this.subgroup = subgroup;
	}
	// �������� ������� �������
	public List<com.example.controller.Lesson> getConvertedLessons() {
		List<com.example.controller.Lesson> convertedList = new ArrayList<com.example.controller.Lesson>();
		
		List <CellLesson> listCellLessons = parser.getContentByGroup(sheetNumber, group);
		com.example.controller.Lesson fillLesson = new com.example.controller.Lesson();
		for (CellLesson cellLesson : listCellLessons) {
			if (!(cellLesson.isSorted())) {
				Lesson bufLess = cellLesson.getLesson();
				fillLesson.FillLesson(convertTime(bufLess.getTime()), bufLess.getSubject(), "", "", bufLess.getTime().getDay(), 0, 0);
			}
			else {
				switch (cellLesson.getTypeOfCell()) {
				case 0:
					Lesson les0 = cellLesson.getLesson();
					fillLesson = new com.example.controller.Lesson();
					convertLesson(les0, fillLesson, 0, 0);
					//Log.d("OOO", fillLesson.toString());
					convertedList.add(fillLesson);
					break;
				case 1:
					Lesson [] les1 = cellLesson.getDoubleLesson();
					fillLesson = new com.example.controller.Lesson();
					convertLesson(les1[0], fillLesson, 1, 0);
					convertedList.add(fillLesson);
					
					fillLesson = new com.example.controller.Lesson();
					convertLesson(les1[1], fillLesson, 2, 0);
					convertedList.add(fillLesson);
					break;
				case 2:
					Lesson [] les2 = cellLesson.getDoubleLesson();
					if (subgroup == 1) {
						fillLesson = new com.example.controller.Lesson();
						convertLesson(les2[0], fillLesson, 0, 0);
						convertedList.add(fillLesson);
					}
					else {
						fillLesson = new com.example.controller.Lesson();
						convertLesson(les2[1], fillLesson, 0, 0);
						convertedList.add(fillLesson);
					}
					break;
				case 3:
					Lesson [] les3 = cellLesson.getTripleLesson();
					fillLesson = new com.example.controller.Lesson();
					convertLesson(les3[0], fillLesson, 1, 0);
					convertedList.add(fillLesson);
					
					if (subgroup == 1) {
						fillLesson = new com.example.controller.Lesson();
						convertLesson(les3[1], fillLesson, 2, 0);
						convertedList.add(fillLesson);
					}
					else {
						fillLesson = new com.example.controller.Lesson();
						convertLesson(les3[2], fillLesson, 2, 0);
						convertedList.add(fillLesson);
					}
					break;
				case 4:
					Lesson [] les4 = cellLesson.getTripleLesson();
					fillLesson = new com.example.controller.Lesson();
					if (subgroup == 1) {
						convertLesson(les4[0], fillLesson, 1, 0);
						convertedList.add(fillLesson);
					}
					else {
						fillLesson = new com.example.controller.Lesson();
						convertLesson(les4[1], fillLesson, 1, 0);
						convertedList.add(fillLesson);
					}
					
					fillLesson = new com.example.controller.Lesson();
					convertLesson(les4[2], fillLesson, 2, 0);
					convertedList.add(fillLesson);
					break;
				case 5:
					Lesson [] les5 = cellLesson.getFourLesson();
					fillLesson = new com.example.controller.Lesson();
					if (subgroup == 1) {
						convertLesson(les5[0], fillLesson, 1, 0);
						convertedList.add(fillLesson);
						
						fillLesson = new com.example.controller.Lesson();
						convertLesson(les5[2], fillLesson, 2, 0);
						convertedList.add(fillLesson);
					}
					else {
						fillLesson = new com.example.controller.Lesson();
						convertLesson(les5[1], fillLesson, 1, 0);
						convertedList.add(fillLesson);
						
						fillLesson = new com.example.controller.Lesson();
						convertLesson(les5[3], fillLesson, 2, 0);
						convertedList.add(fillLesson);
					}
					break;
				case 6:
					Lesson [] les6 = cellLesson.getTripleLesson();
					fillLesson = new com.example.controller.Lesson();
					if (subgroup == 1) {
						convertLesson(les6[0], fillLesson, 0, 0);
						convertedList.add(fillLesson);
					}
					else {
						fillLesson = new com.example.controller.Lesson();
						convertLesson(les6[1], fillLesson, 1, 0);
						convertedList.add(fillLesson);
						
						fillLesson = new com.example.controller.Lesson();
						convertLesson(les6[2], fillLesson, 2, 0);
						convertedList.add(fillLesson);
					}
					break;
				case 7:
					Lesson [] les7 = cellLesson.getTripleLesson();
					fillLesson = new com.example.controller.Lesson();
					if (subgroup == 1) {
						convertLesson(les7[0], fillLesson, 1, 0);
						convertedList.add(fillLesson);
						
						fillLesson = new com.example.controller.Lesson();
						convertLesson(les7[1], fillLesson, 2, 0);
						convertedList.add(fillLesson);
					}
					else {						
						fillLesson = new com.example.controller.Lesson();
						convertLesson(les7[2], fillLesson, 0, 0);
						convertedList.add(fillLesson);
					}
					break;
				}
			}
		}		
		return convertedList;
	}
	
	private String convertTime(LessonTime time) {
		return String.valueOf(time.getHour()) + ":" + String.valueOf(time.getMinute());
	}
	
	private void convertLesson(Lesson from, com.example.controller.Lesson to, int week, int type) {
		String time = convertTime(from.getTime());
		String subject = from.getSubject();
		if (subject == null)
			subject = " ";
		String housing = from.getHousing();
		if (housing == null)
			housing = " ";
		String classroom = from.getClassroom();
		if (classroom == null)
			classroom = " ";
		int day = from.getTime().getDay();
		to.FillLesson(time, subject, housing, classroom, day, week, type);
	}
}
