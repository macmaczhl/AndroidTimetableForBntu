package com.example.parser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;

public class GroupParser {

	Sheet sh = null;
	int row;
	int col;

	List<CellRangeAddress> rangeAdreses;

	public GroupParser(Sheet sh, int row, int col) {
		this.sh = sh;
		this.row = row;
		this.col = col;
	}

	// Формирует готовую коллекцию всех предметов группы
	public List<CellLesson> getLessons() {
		List<CellLesson> list = new ArrayList<CellLesson>();

		rangeAdreses = sh.getMergedRegions();

		Iterator<CellRangeAddress> iter = rangeAdreses.iterator();
		int iterI = row + 2;
		while (iter.hasNext()) {
			CellRangeAddress cellRange = iter.next();

			if (!(col >= cellRange.getFirstColumn()
					&& col <= cellRange.getLastColumn() || col + 3 >= cellRange
					.getFirstColumn() && col + 3 <= cellRange.getLastColumn())) {
				iter.remove();
			}
			iterI++;
		}

		int bufRow = row + 2;

		list.add(getLesson(bufRow, col));

		int i = 0;
		int lastRow = sh.getLastRowNum();
		while (i < 28 && (bufRow + 9 < lastRow)) {
			if (sh != null) {
				boolean b = true;
				int rowPluser = 3;
				while (b) {
					Row curRow = sh.getRow(bufRow + rowPluser);
					Cell cell = curRow.getCell(col);
					if (cell != null) {
						CellStyle style = cell.getCellStyle();
						Cell nextCell = sh.getRow(bufRow + rowPluser + 1)
								.getCell(col);
						CellStyle styleNext;
						if (nextCell != null) {
							styleNext = nextCell.getCellStyle();
						} else
							styleNext = style;
						if ((style.getBorderBottom() == CellStyle.BORDER_MEDIUM || styleNext
								.getBorderTop() == CellStyle.BORDER_MEDIUM)
								|| (style.getBorderBottom() == CellStyle.BORDER_THIN || styleNext
										.getBorderTop() == CellStyle.BORDER_THIN)) {
							bufRow = bufRow + rowPluser + 1;
							list.add(getLesson(bufRow, col));
							// System.out.println(bufRow + "  " + col);
							b = false;
						} else {
							bufRow = bufRow + rowPluser + 2;
							list.add(getLesson(bufRow, col));
							// System.out.println(bufRow + "  " + col);
							b = false;
						}
					} else {
						bufRow = bufRow + rowPluser + 1;
						list.add(getLesson(bufRow, col));
						// System.out.println(bufRow + "  " + col);
						b = false;
					}
				}
			}
			i++;
		}
		// Удаление null-ов
		Iterator<CellLesson> iterForNullDel = list.iterator();
		while (iterForNullDel.hasNext()) {
			CellLesson cell = iterForNullDel.next();
			if (cell.getTypeOfCell() == 0) {
				if (cell.getLesson() == null)
					iterForNullDel.remove();
			}
		}
		return list;
	}

	// Определяет предмет в определенной позиции
	public CellLesson getLesson(int row, int col) {
		CellLesson cellLes = new CellLesson();
		if (sh != null) {
			if (isEmptyCellLesson(row, col)) {
				cellLes.setLesson(null);
			} else {
				// Берем время
				LessonTime time = getLessonTime(row);
				if (isFiveCellLesson(row, col)) {
					Lesson les = new Lesson(time, readAllCell(row, col, 5),
							null, null);
					cellLes.setNotSortedLesson(les);
				} else {
					switch (checkCellType(row, col)) {
					case 0:
						cellLes.setLesson(setLessonData(time, row, col, 4, 4));
						break;
					case 1:
						cellLes.setDoubleLessonByWeek(
								setLessonData(time, row, col, 4, 2),
								setLessonData(time, row + 2, col, 4, 2));
						break;
					case 2:
						cellLes.setDoubleLessonByGroup(
								setLessonData(time, row, col, 2, 4),
								setLessonData(time, row, col + 2, 2, 4));
						break;
					case 3:
						cellLes.setTripleLessonSeparSecWeek(
								setLessonData(time, row, col, 4, 2),
								setLessonData(time, row + 2, col, 2, 2),
								setLessonData(time, row + 2, col + 2, 2, 2));
						break;
					case 4:
						cellLes.setTripleLessonSeparFirstWeek(
								setLessonData(time, row, col, 2, 2),
								setLessonData(time, row, col + 2, 2, 2),
								setLessonData(time, row + 2, col, 4, 2));
						break;
					case 5:
						cellLes.setFourLesson(
								setLessonData(time, row, col, 2, 2),
								setLessonData(time, row, col + 2, 2, 2),
								setLessonData(time, row + 2, col, 2, 2),
								setLessonData(time, row + 2, col + 2, 2, 2));
						break;
					case 6:
						cellLes.setTripleLessonSeparSecGroup(
								setLessonData(time, row, col, 2, 4),
								setLessonData(time, row, col + 2, 2, 2),
								setLessonData(time, row + 2, col + 2, 2, 2));
						break;
					case 7:
						cellLes.setTripleLessonSeparFirstGroup(
								setLessonData(time, row, col, 2, 2),
								setLessonData(time, row + 2, col, 2, 2),
								setLessonData(time, row, col + 2, 2, 4));
						break;
					default:
						break;
					}
				}
			}
			// //////Дописать
			// считывание по областям
			// Считывание и формирование lessonov
			// Передать их в CellLesson
		}
		return cellLes;
	}

	// Если ячейка пустая
	private boolean isEmptyCellLesson(int row, int col) {
		for (CellRangeAddress range : rangeAdreses) {
			if ((row >= range.getFirstRow()) && (row <= range.getLastRow())
					&& (col >= range.getFirstColumn())
					&& (col <= range.getLastColumn())) {
				Cell cell = getCellFromPos(range.getFirstRow(),
						range.getFirstColumn());
				if (cell != null)
					if (!(cell.getStringCellValue().equals(""))) {
						return false;
					}
			}
		}

		Cell cell = null;
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				cell = getCellFromPos(row + i, col + j);
				if (cell != null) {
					if (!(cell.getStringCellValue().equals(""))) {
						return false;
					}
				}
			}
		return true;
	}

	// Если 5 строк в ячейке
	private boolean isFiveCellLesson(int row, int col) {
		Cell four = getCellFromPos(row + 3, col);
		Cell five = getCellFromPos(row + 4, col);
		Cell six = getCellFromPos(row + 5, col);
		if (four != null && five != null && six != null) {
			CellStyle cellFive = five.getCellStyle();
			CellStyle cellSix = six.getCellStyle();
			if (four.getCellStyle().getBorderBottom() == CellStyle.BORDER_NONE
					&& (cellFive.getBorderBottom() == CellStyle.BORDER_MEDIUM || cellSix
							.getBorderTop() == CellStyle.BORDER_MEDIUM)) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	// Считывает всю ячейку 4 или 5 строк в парам
	private String readAllCell(int row, int col, int rowCount) {
		String subjAll = "";
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < 4; j++) {
				Cell cell = getCellFromPos(row + i, col + j);
				if (cell != null) {
					if (!subjAll.isEmpty()) {
						if (subjAll.charAt(subjAll.length() - 1) != ' ') {
							subjAll += " ";
						}
					}

					subjAll += cell.getStringCellValue();

				}
			}
			// subjAll += " ";
		}
		return subjAll;
	}

	// Возвращает тип клетки
	private int checkCellType(int row, int col) {// /////////////////////////////************************************************
		boolean isSeparByWeek = cellSeparByWeek(row, col);
		boolean isSeparByGroup = cellSeparByGroup(row, col);

		if (!isSeparByWeek && !isSeparByGroup)// Нет разделений
			return 0;
		if (isSeparByWeek && !isSeparByGroup) // Только по неделям
			return 1;
		if (!isSeparByWeek && isSeparByGroup) // Только по группам
			return 2;
		for (CellRangeAddress range : rangeAdreses) {
			// Тип 3
			if (((row >= range.getFirstRow()) && (row <= range.getLastRow())
					&& (col >= range.getFirstColumn()) && (col <= range
					.getLastColumn()))
					&& (range.getLastRow() - range.getFirstRow() >= 1)
					&& (range.getLastColumn() - range.getFirstColumn() == 3)) {
				return 3;
			}
			// Тип 4
			if (((row + 2 >= range.getFirstRow())
					&& (row + 2 <= range.getLastRow())
					&& (col >= range.getFirstColumn()) && (col <= range
					.getLastColumn()))
					&& (range.getLastRow() - range.getFirstRow() >= 1)
					&& (range.getLastColumn() - range.getFirstColumn() == 3)) {
				return 4;
			}
			// Тип 6
			if ((row == range.getFirstRow() && col == range.getFirstColumn())
					&& (range.getLastRow() - range.getFirstRow() == 3)
					&& (range.getLastColumn() - range.getFirstColumn() == 1)) {
				return 6;
			}
			// Тип 6
			if ((row == range.getFirstRow() && (col + 2) == range
					.getFirstColumn())
					&& (range.getLastRow() - range.getFirstRow() == 3)
					&& (range.getLastColumn() - range.getFirstColumn() == 1)) {
				return 7;
			}
		}
		return 5;
	}

	// -----------------------
	// Разделена ли по неделям
	private boolean cellSeparByWeek(int row, int col) {
		boolean trueRangeRow = false;
		boolean trueRangeCol = false;
		for (CellRangeAddress range : rangeAdreses) {
			// подходит ли объединение по горизонтали
			if ((range.getLastRow() - range.getFirstRow() == 0)
					&& range.getLastRow() == row) {
				trueRangeRow = true;
				// подходит ли объединение по вертикали
				if ((range.getLastColumn() - range.getFirstColumn() >= 3)
						&& ((col >= range.getFirstColumn()) && (col <= range
								.getLastColumn()))) {
					trueRangeCol = true;
					break;
				}
			}
		}
		if (trueRangeRow && trueRangeCol)
			return true;
		else {
			trueRangeRow = false;
			trueRangeCol = false;
			for (CellRangeAddress range : rangeAdreses) {
				// подходит ли объединение по горизонтали
				if ((range.getLastRow() - range.getFirstRow() == 1)
						&& (range.getFirstRow() == row || range.getFirstRow() == (row + 2))) {

					trueRangeRow = true;
					Cell cellBuf = getCellFromPos(row + 1, col);
					if (cellBuf == null)
						return false;
					Cell cellBufTop = getCellFromPos(row + 2, col);
					if (cellBufTop == null)
						return false;
					short styleBuf = cellBuf.getCellStyle().getBorderBottom();
					short styleBufTop = cellBufTop.getCellStyle()
							.getBorderTop();
					// подходит ли объединение по вертикали
					if (((range.getLastColumn() - range.getFirstColumn() >= 1) && (((col >= range
							.getFirstColumn()) && (col <= range.getLastColumn())) || ((col + 2) >= range
							.getFirstColumn() && (col + 2) <= range
							.getLastColumn())))
							&& (styleBuf == CellStyle.BORDER_MEDIUM
									|| styleBuf == CellStyle.BORDER_THIN
									|| styleBufTop == CellStyle.BORDER_MEDIUM || styleBufTop == CellStyle.BORDER_THIN)) {
						trueRangeCol = true;
						break;
					} else if (((range.getLastColumn() - range.getFirstColumn() >= 1) && ((col + 2 >= range
							.getFirstColumn()) && (col + 2 <= range
							.getLastColumn())))
							&& (styleBuf == CellStyle.BORDER_MEDIUM
									|| styleBuf == CellStyle.BORDER_THIN
									|| styleBufTop == CellStyle.BORDER_MEDIUM || styleBufTop == CellStyle.BORDER_THIN)) {
						trueRangeCol = true;
						break;
					} else {
						if ((range.getLastRow() - range.getFirstRow() == 1)
								&& (range.getLastColumn()
										- range.getFirstColumn() == 1)
								&& (col + 2 >= range.getFirstColumn() && col + 2 <= range
										.getLastColumn())) {
							trueRangeCol = true;
							break;
						}
					}
				}
			}
			if (trueRangeRow && trueRangeCol)
				return true;
			else
				return false;
		}
	}

	// -----------------------
	// Разделена ли по группам
	private boolean cellSeparByGroup(int row, int col) {
		int bufRow = row;
		int bufCol = col;
		for (CellRangeAddress range : rangeAdreses) {
			for (int i = 0; i < 4; i++) // Для документов, где объединениы все
										// ячейки
			{
				switch (i) {
				case 0:
					bufRow = row;
					bufCol = col;
					break;
				case 1:
					bufRow = row;
					bufCol = col + 2;
				case 2:
					bufRow = row + 2;
					bufCol = col;
				case 3:
					bufRow = row + 2;
					bufCol = col + 2;
				}
				// подходит ли объединение
				if ((range.getFirstRow() == bufRow)
						&& (range.getFirstColumn() == bufCol)
						&& (range.getLastColumn() - range.getFirstColumn() == 1)
						&& (range.getLastRow() - range.getFirstRow() >= 1)) {
					return true;
				}
			}
			// Для документов, где есть одиночные ячейки
			for (int r = 0; r < 4; r++) {
				bufRow = row + r;
				for (int k = 0; k < 2; k++) {
					bufCol = col + k * 2;
					// подходит ли объединение
					if ((range.getFirstRow() == bufRow)
							&& (range.getFirstColumn() == bufCol)
							&& (range.getLastColumn() - range.getFirstColumn() == 1)
							&& (range.getLastRow() - range.getFirstRow() == 0)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// Возвращает ячейку в указанной позиции
	public Cell getCellFromPos(int row, int col) {
		if (sh != null) {
			Row curRow = sh.getRow(row);
			if (curRow != null) {
				Cell cell = curRow.getCell(col);
				return cell;
			}
			return null;
		} else
			return null;
	}

	// ------------------------------------------------
	// Считывание с ячеек

	// Считывает день и время с таблицы
	private LessonTime getLessonTime(int row) {
		// День
		int bufRow = row;
		String day = "";
		int dayNumber = 0;
		while (day.equals("") && (bufRow != 0)) {
			Cell cell = getCellFromPos(bufRow, 0);
			if (cell != null)
				day = cell.getStringCellValue();
			bufRow--;
		}
		if (day.equalsIgnoreCase("понедельник"))
			dayNumber = Calendar.MONDAY;
		if (day.equalsIgnoreCase("вторник"))
			dayNumber = Calendar.TUESDAY;
		if (day.equalsIgnoreCase("среда"))
			dayNumber = Calendar.WEDNESDAY;
		if (day.equalsIgnoreCase("четверг"))
			dayNumber = Calendar.THURSDAY;
		if (day.equalsIgnoreCase("пятница"))
			dayNumber = Calendar.FRIDAY;
		if (day.equalsIgnoreCase("суббота"))
			dayNumber = Calendar.SATURDAY;

		// Время
		int hour = 0;
		int minute = 0;
		Cell cell = getCellFromPos(row, 1);
		if (cell != null) {
			String timeStr;
			timeStr = cell.getStringCellValue();
			int doteIndex = timeStr.indexOf('.');
			if (doteIndex != -1) {
				hour = Integer.valueOf(timeStr.substring(0, doteIndex));
				minute = Integer.valueOf(timeStr.substring(doteIndex + 1,
						doteIndex + 3));
			}
		}

		return new LessonTime(dayNumber, hour, minute);
	}

	// Устанавливает предмет, корпус, аудиторию и время, если оно указано явно
	private Lesson setLessonData(LessonTime time, int row, int col,
			int countHor, int countVert) {
		Lesson les = null;
		String subject = readDataFromPos(row, col, countHor, countVert);
		if (subject.isEmpty()) {
			return null;
		} else {
			subject = resetTime(time, subject);
			les = new Lesson(time, subject, null, null);
			setLessonHousCorp(les);
		}
		return les;
	}

	// Читает фсе строки в заданном диапазоне
	private String readDataFromPos(int row, int col, int countHor, int countVert) {
		String str = "";
		Cell cell = null;
		for (int i = 0; i < countVert; i++) {
			for (int j = 0; j < countHor; j++) {
				cell = getCellFromPos(row + i, col + j);
				if (cell != null) {
					if (!str.isEmpty() && (str.charAt(str.length() - 1) != ' ')) {
						str += " ";
					}
					if (cell.getCellType() == Cell.CELL_TYPE_STRING)
						str += cell.getStringCellValue();
				}
			}
		}

		if (str.equals("")) {
			for (CellRangeAddress range : rangeAdreses) {
				if (row >= range.getFirstRow() && row <= range.getLastRow()
						&& col >= range.getFirstColumn()
						&& col <= range.getLastColumn()) {

					cell = getCellFromPos(range.getFirstRow(),
							range.getFirstColumn());
					if (cell != null) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING)
							str += cell.getStringCellValue();
					}
				}
			}
		}

		str = clearStringWithData(str);
		return str;
	}

	// Убирает "1 нед", "2 нед", лишние пробелы по концам или если их несколько
	// Заменяет абзац на пробел
	private String clearStringWithData(String strIn) {
		String firstWeek = "1 нед";
		String secWeek = "2 нед";
		int index = strIn.indexOf(firstWeek);
		if (index != -1) {
			strIn = strIn.replaceAll(firstWeek, "");
		}
		index = strIn.indexOf(secWeek);
		if (index != -1) {
			strIn = strIn.replaceAll(secWeek, "");
		}

		strIn = strIn.replaceAll("\n", " ");

		String strBufArr[] = strIn.split("\\s+");
		strIn = "";
		for (String bufStr : strBufArr) {
			strIn += bufStr + " ";
		}

		strIn = strIn.trim();
		if (strIn.equalsIgnoreCase("ф и з и ч е с к а я к у л ь т у р а"))
			return "Физическая культура";
		return strIn;
	}

	// Если в badStr есть время, то оно удаляется из строки и переписывается в
	// time
	private String resetTime(LessonTime time, String badStr) {
		// Если в строке есть время
		if (badStr.matches("\\d+.*")) {
			// System.out.println(badStr);
			int simbNumber = 0;
			// Если часы одинарны
			if (badStr.matches("\\d\\D.+"))
				simbNumber = 4;
			else
			// //Если часы не одинарны
			if (badStr.matches("\\d{2}\\D.+"))
				simbNumber = 5;
			String timeInStr = badStr.substring(0, simbNumber);
			badStr = badStr.substring(simbNumber);
			badStr = badStr.trim();

			int hour = 0;
			int minute = 0;
			int index = timeInStr.indexOf('-');
			if (index == -1) {
				index = timeInStr.indexOf('.');
			}
			hour = Integer.valueOf(timeInStr.substring(0, index));
			minute = Integer.valueOf(timeInStr.substring(index + 1, index + 3));
			time.setHour(hour);
			time.setMinute(minute);
		}
		return badStr;
	}

	// Берет из subject аудиторию и корпус
	// ставит их в поля и удаляет из subject
	private void setLessonHousCorp(Lesson les) {
		// Выделяем строку с аудиторией и корпусом
		String hAndC = "";
		String subject = les.getSubject();
		Pattern pt = Pattern
				.compile("\\s?(а|А|\\s|л|Л)(уд)?\\.?\\s?\\d+\\D{2,}\\d+\\S?");
		Matcher mt = pt.matcher(subject);
		if (mt.find()) {
			hAndC = mt.group();
			subject = subject.replaceAll(hAndC, "");
			les.setSubject(subject); // Устанавливаем законченный предмет
		}
		// Выделяем аудиторию
		Pattern ptClassroom = Pattern.compile("\\d+[а-я]?");
		Matcher mtClassroom = ptClassroom.matcher(hAndC);
		String classroom = "";
		String housing = "";
		if (mtClassroom.find()) {
			classroom = mtClassroom.group();
			les.setClassroom(classroom);
			housing = hAndC.replaceAll(classroom, "");

			mtClassroom = ptClassroom.matcher(housing);
			if (mtClassroom.find()) {
				les.setHousing(mtClassroom.group());
			}
		}

	}
}
