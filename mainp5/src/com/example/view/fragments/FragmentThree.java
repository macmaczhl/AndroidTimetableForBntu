package com.example.view.fragments;

import com.example.p5.R;
import com.example.view.MainActivityNavig.MainSettingsFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentThree extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment2, container, false);
		//getFragmentManager().beginTransaction().replace(android.R.id.content, new MainSettingsFragment()).commit();

		return rootView;
	}

	@Override
	public void onViewCreated(View viewMain, Bundle savedInstanceState) {

	}
}
