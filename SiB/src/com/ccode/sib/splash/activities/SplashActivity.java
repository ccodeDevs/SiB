package com.ccode.sib.splash.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.WindowManager;

import com.android.volley.VolleyError;
import com.ccode.sib.R;
import com.ccode.sib.base.activities.BaseActivity;
import com.ccode.sib.news.activities.NewsActivity;
import com.ccode.sib.news.managers.NewsDataManager;
import com.ccode.sib.news.managers.NewsDataManager.OnNewsLoadedListener;
import com.ccode.sib.news.models.NewsWrapper;
import com.ccode.sib.splash.fragments.SplashFragment;
import com.ccode.sib.utils.SystemUiHider;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

public class SplashActivity extends BaseActivity {

	private SystemUiHider mSystemUiHider;

	private Handler mNextActivityHandler;
	private Handler mShowNotificationBarHandler;

	private long getDataFromBackendStartTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			configureActionBar(actionBar);
		}

		initUi();

		if (savedInstanceState == null) {
			addSplashFragment();
		}
	}

	private void configureActionBar(ActionBar actionBar) {
		actionBar.hide();
	}

	@Override
	protected void initUi() {
		mNextActivityHandler = new Handler();
		mShowNotificationBarHandler = new Handler();
		initUiHider();
	}

	private void initUiHider() {
		View anchorView = findViewById(R.id.ui_hider_anchor);
		mSystemUiHider = SystemUiHider.getInstance(this, anchorView, SystemUiHider.FLAG_HIDE_NAVIGATION);
		mSystemUiHider.setup();
		mShowNotificationBarHandler.postDelayed(showNotifRunnable, 1000);
	}

	private void addSplashFragment() {
		replaceFragment(R.id.activity_splash_content_container, SplashFragment.newInstance(), false);
	}

	@Override
	protected void onStop() {
		super.onStop();
		cancelNextActivityOpeningTask();
	}

	private void cancelNextActivityOpeningTask() {
		mNextActivityHandler.removeCallbacks(mRunnable);
	}

	@Override
	protected void onStart() {
		super.onStart();
		getNewsFromBackendTask();
	}

	protected void getNewsFromBackendTask() {
		getDataFromBackendStartTime = System.currentTimeMillis();
		NewsDataManager.getInstance(this).getNews(mNewsLoadedListener, true);
	}

	private OnNewsLoadedListener mNewsLoadedListener = new OnNewsLoadedListener() {

		@Override
		public void onResponse(NewsWrapper news) {
			handleNewsResponse(news);
		}

		@Override
		public void onError(VolleyError error) {
			handleNewsErrorLoading(error);
		}
	};

	private void handleNewsResponse(NewsWrapper news) {
		calculateNextActivityStart();
	}

	private void handleNewsErrorLoading(VolleyError error) {
		calculateNextActivityStart();
	}

	private void calculateNextActivityStart() {
		long difference = System.currentTimeMillis() - getDataFromBackendStartTime;

		if (difference > 2000) {
			startNextActivity();
		} else {
			mNextActivityHandler.postDelayed(mRunnable, difference);
		}
	}

	private void startNextActivity() {
		mSystemUiHider.hide();

		Intent intent = new Intent(this, NewsActivity.class);
		startActivity(intent);
		finish();
	}

	private Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			startNextActivity();
		}
	};

	private Runnable showNotifRunnable = new Runnable() {

		@Override
		public void run() {
			getWindow()
					.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		}
	};

	@Override
	protected void initListeners() {
		// ok nothing
	}

	@Override
	protected void initData() {
		// ok nothing
	}
}