package com.example.parser;
import java.util.Date;

public class CellLesson {
	// Предмет отсортирован по полям
	private boolean isSortedFlag = false;

	// Тип ячейки
	// -1 - не заполнена
	// 0 - не разделена
	// 1 - разделение по неделям - 2
	// 2 - разделение по подгруппам - 2
	// 3 - разделение по недеям, подгруппа разделена во второй неделе - 3
	// 4 - разделение по недеям, подгруппа разделена в первой неделе - 3
	// 5 - разделение по недеям и подгруппам - 4
	// 6 - разделение по неделям, вторая подгруппа разделена - 3 (записано: первая, потом вторая)
	// 7 - разделение по неделям, первая подгруппа разделена - 3 (записано: первая, потом вторая)
	private int typeOfCell = -1;
	// Название предмета или вся строка целиком(усли isReadyFlag==false)

	// Предмет
	private Lesson lesson;
	// Если есть разделение по подгруппам или по неделям
	private Lesson[] doubleLesson;
	// Если есть разделение по подгруппам и по неделям на 3 предмета
	private Lesson[] tripleLesson;
	// Если есть разделение по подгруппам и по неделям на 4 предмета
	private Lesson[] fourLesson;

	public boolean isSorted() {
		return isSortedFlag;
	}

	public int getTypeOfCell() {
		return typeOfCell;
	}

	public void setNotSortedLesson(Lesson les) {
		isSortedFlag = false;
		lesson = les;
	}

	public Lesson getNotSortedLesson() {
		if (isSortedFlag == false)
			return lesson;
		else
			return null;
	}

	// Один предмет
	public void setLesson(Lesson les) {
		isSortedFlag = true;
		typeOfCell = 0;
		lesson = les;
	}

	public Lesson getLesson() {
		if (isSortedFlag == true && typeOfCell == 0)
			return lesson;
		else
			return null;
	}

	// Два предмета
	private void setDoubleLesson(Lesson lesOne, Lesson lesTwo) {
		doubleLesson = new Lesson[] { lesOne, lesTwo };
	}

	public void setDoubleLessonByWeek(Lesson lesOne, Lesson lesTwo) {
		isSortedFlag = true;
		typeOfCell = 1;
		setDoubleLesson(lesOne, lesTwo);
	}

	public void setDoubleLessonByGroup(Lesson lesOne, Lesson lesTwo) {
		isSortedFlag = true;
		typeOfCell = 2;
		setDoubleLesson(lesOne, lesTwo);
	}

	public Lesson[] getDoubleLesson() {
		if (isSortedFlag == true && (typeOfCell == 1 || typeOfCell == 2))
			return doubleLesson;
		else
			return null;
	}

	// Три предмета
	private void setTripleLesson(Lesson lesOne, Lesson lesTwo, Lesson lesThree) {
		tripleLesson = new Lesson[] { lesOne, lesTwo, lesThree };
	}

	public void setTripleLessonSeparSecWeek(Lesson lesOne, Lesson lesTwo,
			Lesson lesThree) {
		isSortedFlag = true;
		typeOfCell = 3;
		setTripleLesson(lesOne, lesTwo, lesThree);
	}

	public void setTripleLessonSeparFirstWeek(Lesson lesOne, Lesson lesTwo,
			Lesson lesThree) {
		isSortedFlag = true;
		typeOfCell = 4;
		setTripleLesson(lesOne, lesTwo, lesThree);
	}

	public void setTripleLessonSeparSecGroup(Lesson lesOne, Lesson lesTwo,
			Lesson lesThree) {
		isSortedFlag = true;
		typeOfCell = 6;
		setTripleLesson(lesOne, lesTwo, lesThree);
	}

	public void setTripleLessonSeparFirstGroup(Lesson lesOne, Lesson lesTwo,
			Lesson lesThree) {
		isSortedFlag = true;
		typeOfCell = 7;
		setTripleLesson(lesOne, lesTwo, lesThree);
	}

	public Lesson[] getTripleLesson() {
		if (isSortedFlag == true
				&& (typeOfCell == 3 || typeOfCell == 4 || typeOfCell == 6 || typeOfCell == 7))
			return tripleLesson;
		else
			return null;
	}

	// Четыре предмета
	public void setFourLesson(Lesson lesOne, Lesson lesTwo, Lesson lesThree,
			Lesson lesFour) {
		isSortedFlag = true;
		typeOfCell = 5;
		fourLesson = new Lesson[] { lesOne, lesTwo, lesThree, lesFour };
	}

	public Lesson[] getFourLesson() {
		if (isSortedFlag == true && typeOfCell == 5)
			return fourLesson;
		else
			return null;
	}
}
