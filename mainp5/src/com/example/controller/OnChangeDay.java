package com.example.controller;

import com.example.view.MainActivityNavig;
import com.example.view.fragments.FragmentOne;

import android.view.View;
import android.view.View.OnClickListener;

public class OnChangeDay implements OnClickListener {
	private FragmentOne fragment;
	private int day;
	
	public OnChangeDay(FragmentOne fragment,int day){
		this.fragment = fragment;
		this.day = day;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		MainActivityNavig.selected = day;
		fragment.FillLessonAdapter(fragment.getSubGroup());
		fragment.setSelectedButton(fragment.listButton, day);
		
	}
	
}
