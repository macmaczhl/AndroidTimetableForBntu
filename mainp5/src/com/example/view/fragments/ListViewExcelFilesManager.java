package com.example.view.fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.parser.LessonConverterFromParser;
import com.example.parser.MainParser;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;

public class ListViewExcelFilesManager {
	Activity activity;
	MainParser parser = null;

	String fileNames[] = null;

	String fileSheets[] = null;

	public ListViewExcelFilesManager(Activity act) {
		activity = act;
		createAdapterFiles();
	}

	private void createAdapterFiles() {
		this.fileNames = getExcelFilesFromDownloadFolder();
	}

	// Возвращает массив имен файлов в папке Download
	private String[] getExcelFilesFromDownloadFolder() {
		File directory = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/Download");
		File[] files = directory.listFiles();
		if (files == null)
			return new String[] { "No Excel files" };
		String arr[] = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			arr[i] = files[i].getName();
		}
		arr = setExcelFormats(arr);
		return arr;
	}

	// Отбирает Excel файлы
	private String[] setExcelFormats(String[] input) {
		List<String> fileNames = new ArrayList<String>();
		for (String str : input) {
			fileNames.add(str);
		}
		Iterator<String> iter = fileNames.iterator();
		while (iter.hasNext()) {
			String str = iter.next();
			if (!str.matches(".*\\.[X|x][L|l][S|s]x?")) {
				iter.remove();
			}
		}
		String buf[] = new String[fileNames.size()];
		for (int i = 0; i < fileNames.size(); i++)
			buf[i] = fileNames.get(i);

		return buf;
	}


	public String getFileName(int pos) {
		return fileNames[pos];
	}
	
	public String [] getFileNames() {
		return fileNames;
	}
	
	public String [] getSheetNames() {
		return fileSheets;
	}

	// Формирует массив из листов Excel файла
	public void formLists(int filePos) {
		try {
			parser = new MainParser(Environment.getExternalStorageDirectory()
					.getPath() + "/Download/" + getFileName(filePos));
		} catch (Exception e) {
			e.printStackTrace();
		}
		fileSheets = parser.getSheetNames();
	}

	public List<com.example.controller.Lesson> getLessons(int selectedFile,
			int selectedSheet, String group, int subgroup) {
		LessonConverterFromParser converter = new LessonConverterFromParser(
				parser, selectedSheet, group, subgroup);
		return converter.getConvertedLessons();
	}
}
