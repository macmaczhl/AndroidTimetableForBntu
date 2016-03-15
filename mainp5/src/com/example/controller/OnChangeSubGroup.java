package com.example.controller;

import com.example.p5.R;
import com.example.view.fragments.FragmentOne;
import com.example.xml.DataManager;
import com.example.xml.XMLSerialize;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class OnChangeSubGroup implements OnClickListener {
	private FragmentOne fragment;
	private int subGroup;
	public OnChangeSubGroup(FragmentOne fragment,int subGroup){
		this.fragment = fragment;
		this.subGroup = subGroup;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(subGroup==1){
			fragment.button1.setBackgroundResource(R.drawable.shapebutton2);
			fragment.button2.setBackgroundResource(R.drawable.shapebutton);
		}
		else{
			fragment.button2.setBackgroundResource(R.drawable.shapebutton2);
			fragment.button1.setBackgroundResource(R.drawable.shapebutton);
		}
			
		fragment.FillLessonAdapter(subGroup);
	}


}
