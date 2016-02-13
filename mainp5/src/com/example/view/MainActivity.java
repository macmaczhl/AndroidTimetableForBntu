package com.example.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.controller.Lesson;
import com.example.controller.LessonAdapter;
import com.example.p5.R;
import com.example.p5.R.array;
import com.example.p5.R.id;
import com.example.p5.R.layout;
import com.example.p5.R.menu;
import com.example.xml.XMLSerialize;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends Activity {
	Spinner spinner;
	public static int selected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getResources()
						.getStringArray(R.array.weekday));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setAdapter(adapter);

		Calendar calendar = Calendar.getInstance();
		spinner.setSelection(calendar.get(Calendar.DAY_OF_WEEK) - 1);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				selected = spinner.getSelectedItemPosition() + 1;
				if (selected == 8)
					selected = 7;
				ShowList();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonAdd:
			AddDialog dlg1 = new AddDialog();
			dlg1.show(getFragmentManager(), "228");
			dlg1.SetActivity(this);
			break;
		}
	}

	public void ShowList() {

		ListView mylist = (ListView) findViewById(R.id.gridView);
		try {
			List<Lesson> allLessons = new ArrayList<Lesson>();
			List<Lesson> currentList = new ArrayList<Lesson>();
			allLessons = XMLSerialize.read().list;

			for (Lesson obj : allLessons) {
				if (obj.getDay() == selected)
					currentList.add(obj);
			}
			LessonAdapter adapter = new LessonAdapter(this, currentList);
			adapter.SetActivity(this);
			if (allLessons.isEmpty())
				mylist.setAdapter(null);
			else {
				mylist.setAdapter(adapter);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mylist.setAdapter(null);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
