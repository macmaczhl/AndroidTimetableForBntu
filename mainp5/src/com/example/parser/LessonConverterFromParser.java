package com.example.parser;

import java.util.ArrayList;
import java.util.List;

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
	// Получить готовые лессоны
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
					com.example.controller.Lesson fillLesson0 = new com.example.controller.Lesson();
					convertLesson(les0, fillLesson0, 0, 0);
					convertedList.add(fillLesson0);
					break;
				case 1:
					Lesson [] les1 = cellLesson.getDoubleLesson();
					com.example.controller.Lesson fillLesson1 = new com.example.controller.Lesson();
					convertLesson(les1[0], fillLesson1, 1, 0);
					convertedList.add(fillLesson1);
					
					com.example.controller.Lesson fillLesson12 = new com.example.controller.Lesson();
					convertLesson(les1[1], fillLesson12, 2, 0);
					convertedList.add(fillLesson12);
					break;
				case 2:
					Lesson [] les2 = cellLesson.getDoubleLesson();
					if (subgroup == 1) {
						com.example.controller.Lesson fillLesson2 = new com.example.controller.Lesson();
						convertLesson(les2[0], fillLesson2, 0, 0);
						convertedList.add(fillLesson2);
					}
					else {
						com.example.controller.Lesson fillLesson22 = new com.example.controller.Lesson();
						convertLesson(les2[1], fillLesson22, 0, 0);
						convertedList.add(fillLesson22);
					}
					break;
				case 3:
					Lesson [] les3 = cellLesson.getTripleLesson();
					com.example.controller.Lesson fillLesson31 = new com.example.controller.Lesson();
					convertLesson(les3[0], fillLesson31, 1, 0);
					convertedList.add(fillLesson31);
					
					if (subgroup == 1) {
						com.example.controller.Lesson fillLesson32 = new com.example.controller.Lesson();
						convertLesson(les3[1], fillLesson32, 2, 0);
						convertedList.add(fillLesson32);
					}
					else {
						com.example.controller.Lesson fillLesson33 = new com.example.controller.Lesson();
						convertLesson(les3[2], fillLesson33, 2, 0);
						convertedList.add(fillLesson33);
					}
					break;
				case 4:
					Lesson [] les4 = cellLesson.getTripleLesson();
					com.example.controller.Lesson fillLesson41 = new com.example.controller.Lesson();
					if (subgroup == 1) {
						convertLesson(les4[0], fillLesson41, 1, 0);
						convertedList.add(fillLesson41);
					}
					else {
						com.example.controller.Lesson fillLesson42 = new com.example.controller.Lesson();
						convertLesson(les4[1], fillLesson42, 1, 0);
						convertedList.add(fillLesson42);
					}
					
					com.example.controller.Lesson fillLesson43 = new com.example.controller.Lesson();
					convertLesson(les4[2], fillLesson43, 2, 0);
					convertedList.add(fillLesson43);
					break;
				case 5:
					Lesson [] les5 = cellLesson.getFourLesson();
					com.example.controller.Lesson fillLesson51 = new com.example.controller.Lesson();
					if (subgroup == 1) {
						convertLesson(les5[0], fillLesson51, 1, 0);
						convertedList.add(fillLesson51);
						
						com.example.controller.Lesson fillLesson52 = new com.example.controller.Lesson();
						convertLesson(les5[2], fillLesson52, 2, 0);
						convertedList.add(fillLesson52);
					}
					else {
						com.example.controller.Lesson fillLesson53 = new com.example.controller.Lesson();
						convertLesson(les5[1], fillLesson53, 1, 0);
						convertedList.add(fillLesson53);
						
						com.example.controller.Lesson fillLesson54 = new com.example.controller.Lesson();
						convertLesson(les5[3], fillLesson54, 2, 0);
						convertedList.add(fillLesson54);
					}
					break;
				case 6:
					Lesson [] les6 = cellLesson.getTripleLesson();
					com.example.controller.Lesson fillLesson61 = new com.example.controller.Lesson();
					if (subgroup == 1) {
						convertLesson(les6[0], fillLesson61, 0, 0);
						convertedList.add(fillLesson61);
					}
					else {
						com.example.controller.Lesson fillLesson62 = new com.example.controller.Lesson();
						convertLesson(les6[1], fillLesson62, 1, 0);
						convertedList.add(fillLesson62);
						
						com.example.controller.Lesson fillLesson63 = new com.example.controller.Lesson();
						convertLesson(les6[2], fillLesson63, 2, 0);
						convertedList.add(fillLesson63);
					}
					break;
				case 7:
					Lesson [] les7 = cellLesson.getTripleLesson();
					com.example.controller.Lesson fillLesson71 = new com.example.controller.Lesson();
					if (subgroup == 1) {
						convertLesson(les7[0], fillLesson71, 1, 0);
						convertedList.add(fillLesson71);
						
						com.example.controller.Lesson fillLesson72 = new com.example.controller.Lesson();
						convertLesson(les7[1], fillLesson72, 2, 0);
						convertedList.add(fillLesson72);
					}
					else {						
						com.example.controller.Lesson fillLesson73 = new com.example.controller.Lesson();
						convertLesson(les7[2], fillLesson73, 0, 0);
						convertedList.add(fillLesson73);
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
		to.FillLesson(convertTime(from.getTime()), from.getSubject(), from.getHousing(), from.getClassroom(), from.getTime().getDay(), week, type);
	}
}
