package com.ccode.sib.news.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ccode.sib.R;
import com.ccode.sib.base.activities.BaseActivity;
import com.ccode.sib.news.adapters.SideMenuAdapter;
import com.ccode.sib.news.fragments.NewsGridFragment;
import com.ccode.sib.news.fragments.NewsListFragment;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

public class NewsActivity extends BaseActivity {

	// navigation drawer
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mSideMenuListView;
	private SideMenuAdapter mSideMenuAdapter;

	// flags
	private boolean switchViewToggleSelected = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			configureActionBar(actionBar);
		}

		initUi();
		initListeners();

		if (savedInstanceState == null) {
			showNewsFragment();
		}

	}

	private void configureActionBar(ActionBar actionBar) {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
	}

	@Override
	protected void initUi() {
		initSideMenu();
	}

	private void initSideMenu() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_news_drawer_layout);
		mSideMenuListView = (ListView) findViewById(R.id.activity_news_drawer_list);
		mSideMenuAdapter = new SideMenuAdapter(this);
		mSideMenuListView.setAdapter(mSideMenuAdapter);
	}

	@Override
	protected void initListeners() {
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name);
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mSideMenuListView.setOnItemClickListener(mItemClickListener);

	}

	private OnItemClickListener mItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			handleSideMenuItemClick(position);
		}
	};

	private void handleSideMenuItemClick(int position) {
		mDrawerLayout.closeDrawers();
	}

	private void showNewsFragment() {
		replaceFragment(R.id.activity_news_container, NewsGridFragment.newInstance(), false);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_news, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		case R.id.menu_news_switch_view:
			toggleSwitchViewClick(item);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void toggleSwitchViewClick(MenuItem item) {
		if (switchViewToggleSelected) {
			item.setIcon(R.drawable.ic_menu_selectall_holo_dark);
			switchViewToggleSelected = false;
			replaceFragment(R.id.activity_news_container, NewsGridFragment.newInstance(), false);
		} else {
			item.setIcon(R.drawable.ic_menu_selectall_holo_light);
			switchViewToggleSelected = true;
			replaceFragment(R.id.activity_news_container, NewsListFragment.newInstance(), false);
		}
	}

	@Override
	protected void initData() {
		// ok nothing here
	}
}