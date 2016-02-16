package com.example.parser;
import java.util.Date;

public class CellLesson {
	// ������� ������������ �� �����
	private boolean isSortedFlag = false;

	// ��� ������
	// -1 - �� ���������
	// 0 - �� ���������
	// 1 - ���������� �� ������� - 2
	// 2 - ���������� �� ���������� - 2
	// 3 - ���������� �� ������, ��������� ��������� �� ������ ������ - 3
	// 4 - ���������� �� ������, ��������� ��������� � ������ ������ - 3
	// 5 - ���������� �� ������ � ���������� - 4
	// 6 - ���������� �� �������, ������ ��������� ��������� - 3 (��������: ������, ����� ������)
	// 7 - ���������� �� �������, ������ ��������� ��������� - 3 (��������: ������, ����� ������)
	private int typeOfCell = -1;
	// �������� �������� ��� ��� ������ �������(���� isReadyFlag==false)

	// �������
	private Lesson lesson;
	// ���� ���� ���������� �� ���������� ��� �� �������
	private Lesson[] doubleLesson;
	// ���� ���� ���������� �� ���������� � �� ������� �� 3 ��������
	private Lesson[] tripleLesson;
	// ���� ���� ���������� �� ���������� � �� ������� �� 4 ��������
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

	// ���� �������
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

	// ��� ��������
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

	// ��� ��������
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

	// ������ ��������
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
