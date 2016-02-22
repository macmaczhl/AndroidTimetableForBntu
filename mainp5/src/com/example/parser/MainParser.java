package com.example.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MainParser {
	// Документ excel
	Workbook wb = null;

	public MainParser(String fileName) throws IOException {
		openFile(fileName);
	}

	public void openFile(String fileName) throws IOException {
		if (fileName.endsWith("xlsx")) {
			openXslx(fileName);
		} else
			openXsl(fileName);
	}

	private void openXsl(String fileName) throws IOException {
		InputStream in = new FileInputStream(fileName);
		wb = new HSSFWorkbook(in);
		in.close();
	}

	private void openXslx(String fileName) throws IOException {
		InputStream in = new FileInputStream(fileName);
		wb = new XSSFWorkbook(in);
		in.close();
	}

	public String[] getSheetNames() {
		String[] array = null;
		int number = 0;
		if (wb != null) {
			number = wb.getNumberOfSheets();
			array = new String[number];
			for (int i = 0; i < number; i++) {
				Sheet sh = wb.getSheetAt(i);
				String sheetName = sh.getSheetName();
				array[i] = sheetName;
			}
		}
		return array;
	}

	// Для хранения позиции ячейки
	class CellPosition {
		public CellPosition(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public int row;
		public int col;
	}

	// Возвращает номер ячейки, в которой находится группа
	private CellPosition findGroupCell(Sheet sh, String group) {
		CellPosition pos = new CellPosition(0, 0);

		Iterator<Row> it = sh.iterator();
		while (it.hasNext()) {
			Row row = it.next();
			Iterator<Cell> cells = row.iterator();
			while (cells.hasNext()) {
				Cell cell = cells.next();
				int cellType = cell.getCellType();
				// Если в строковом
				int groupLength = 8;
				if (group.length() > groupLength)
					group = group.substring(0, groupLength - 1);
				if (cellType == Cell.CELL_TYPE_NUMERIC) {
					double groupBuf = cell.getNumericCellValue();
					String groupBufStr = doubleToString(groupBuf);
					if (group.equals(groupBufStr)) {
						pos.row = cell.getRowIndex();
						pos.col = cell.getColumnIndex();
						return pos;
					}
				} else if (cellType == Cell.CELL_TYPE_STRING) {

					String valueStr = cell.getStringCellValue();
					if (valueStr.length() > groupLength)
						valueStr = valueStr.substring(0, groupLength);
					if (group.equals(valueStr)) {
						pos.row = cell.getRowIndex();
						pos.col = cell.getColumnIndex();
						return pos;
					}
				}
			}
		}
		return pos;
	}

	// В нужной форме
	private String doubleToString(double in) {
		int out = (int) in;
		return Integer.toString(out);
	}

	// Возвращает максимально готовую коллекцию ЯчейкоПредметов
	public List<CellLesson> getContentByGroup(int sheetNumber, String group) {
		Sheet sh = wb.getSheetAt(sheetNumber);
		List<CellLesson> list = null;
		
		CellPosition posGroup = new CellPosition(0, 0);
		posGroup = findGroupCell(sh, group);
		if (posGroup.col == 0 && posGroup.row == 0)
			return null;
		GroupParser gr = new GroupParser(sh, posGroup.row, posGroup.col);
		list = gr.getLessons();
		return list;
	}

}
