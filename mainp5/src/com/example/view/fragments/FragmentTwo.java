package com.example.view.fragments;

import java.io.File;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.p5.R;
import com.example.parser.*;

public class FragmentTwo extends Fragment {

	Button loadButton;
	TextView outp;
	ListView lvFiles;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.fragment2, container,
				false);
		
		//Обработчик кнопки
		loadButton = (Button) rootView.findViewById(R.id.loadButton);
		outp = (TextView) rootView.findViewById(R.id.textView1);
		
		OnClickListener oclLoadBtn = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Download/4kurs2016.xls");
				List<CellLesson> list = null;
				try {
					MainParser test = new MainParser(Environment.getExternalStorageDirectory().getPath() + "/Download/rapis-4k_man.xls");
					list = test.getContentByGroup(0, "107212");
					
				}
				catch (Exception e) {}
				if (list != null)
					outp.setText((list.get(0).getLesson().toString()));
			}
		};
		loadButton.setOnClickListener(oclLoadBtn);
		
		
		//Файлы
		ListViewExcelFilesManager manager = new ListViewExcelFilesManager(getActivity());
		
		lvFiles = (ListView) rootView.findViewById(R.id.listViewExelFiles);
		lvFiles.setAdapter(manager.getAdapter());
		
		return rootView;
	}
	
	@Override
	public void onViewCreated(View viewMain, Bundle savedInstanceState) {
		
	}
}
