package com.example.view;

import java.util.Calendar;

import com.example.p5.R;
import com.example.view.MainActivityNavig.MainSettingsFragment;
import com.example.view.fragments.FragmentOne;
import com.example.view.fragments.FragmentTwo;
import com.example.widget.MyProvider;
import com.example.xml.XMLSerialize;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.AlertDialog.Builder;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class MainActivityNavig extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	public static int subGroup;
	FragmentOne fragment1 = null;
	FragmentTwo fragment2 = null;
	public static int selected;

	boolean isPrefOpen = false;

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity_navig);
		selected = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		Editor ed = sp.edit();
		ed.putString("duration", "95");
		ed.commit();
		
		

		try {
			subGroup = XMLSerialize.read(this).getSubGroup(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static class MainSettingsFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences);
		}
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();

		switch (position) {
		case 0:
			if (fragment1 == null)
				fragment1 = new FragmentOne();
			fragmentManager.beginTransaction().replace(R.id.container, fragment1).commit();
			break;
		case 1:
			if (fragment2 == null)
				fragment2 = new FragmentTwo();
			fragmentManager.beginTransaction().replace(R.id.container, fragment2).commit();
			break;
		}
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main_activity_navig, menu);
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		FragmentOne fragment1 = null;
		FragmentManager fragmentManager = getFragmentManager();
		int id = item.getItemId();
		if (item.getItemId() == R.id.action_preferences) {
			isPrefOpen = !isPrefOpen;
			if (isPrefOpen) {
				this.findViewById(R.id.action_preferences).setBackgroundColor(Color.RED);
				fragmentManager.beginTransaction().replace(R.id.container, new MainSettingsFragment()).commit();
			} else {
				this.findViewById(R.id.action_preferences).setBackgroundColor(Color.TRANSPARENT);
				if (fragment1 == null)
					fragment1 = new FragmentOne();
				fragmentManager.beginTransaction().replace(R.id.container, fragment1).commit();
			}
			return true;
		}
		if (item.getItemId() == R.id.action_add) {
			AddDialog dlg1 = new AddDialog();
			if (fragment1 == null)
				fragment1 = new FragmentOne();
			fragmentManager.beginTransaction().replace(R.id.container, fragment1).commit();
			dlg1.show(fragment1.getFragmentManager(), "228");
			dlg1.SetFragment(fragment1);
			return true;
		}
		if (item.getItemId() == R.id.action_about) {
			Builder ad = new AlertDialog.Builder(this);
			ad.setTitle("���� ����������"); // ���������
			ad.setMessage("������ 1.0\n������������: \n������ �������� \n���� ��������");
			ad.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_activity_navig, container, false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivityNavig) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
		}
	}

}
