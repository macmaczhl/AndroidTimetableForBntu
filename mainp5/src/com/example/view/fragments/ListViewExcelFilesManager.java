package com.example.view.fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;

public class ListViewExcelFilesManager {
	Activity activity;
	ArrayAdapter<String> adapter;
	public ListViewExcelFilesManager(Activity act) {
		activity = act;
		createAdapter();
	}
	
	private void createAdapter()
	{
		adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, getExcelFilesFromDownloadFolder());
	}
	
	private String [] getExcelFilesFromDownloadFolder()
	{
		File directory = new File(Environment.getExternalStorageDirectory().getPath() + "/Download");
		File [] files = directory.listFiles();
		if (files == null)
			return new String[]{"No Excel files"};
		String arr[] = new String[files.length];
		for (int i = 0; i < files.length; i++)
		{
			arr[i] = files[i].getName();
		}
		arr = setExcelFormats(arr);
		return arr;
	}
	
	private String [] setExcelFormats(String [] input)
	{
		List<String> fileNames = new ArrayList<String>();
		for (String str : input) 
		{
			fileNames.add(str);
		}
		Iterator<String> iter = fileNames.iterator();
		while(iter.hasNext())
		{
			String str = iter.next();
			if (!str.matches(".*\\.[X|x][L|l][S|s]x?"))
			{
				iter.remove();
			}
		}
		String buf [] = new String[fileNames.size()];
		for (int i = 0; i < fileNames.size(); i++)
			buf[i] = fileNames.get(i);
		
		return buf;
	}
	
	public ArrayAdapter<String> getAdapter()
	{
		return adapter;
	}
}
