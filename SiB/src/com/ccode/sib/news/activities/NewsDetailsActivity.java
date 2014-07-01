package com.ccode.sib.news.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.ccode.sib.R;
import com.ccode.sib.base.activities.BaseActivity;
import com.ccode.sib.news.fragments.NewsDetailsFragment;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

// TO DO View Pager

public class NewsDetailsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_container);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle b = getIntent().getExtras();

		if (b == null) {
			finish();
			return;
		}

		NewsDetailsFragment f = NewsDetailsFragment.newInstance(b);
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, f).commit();

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void initUi() {

	}

	@Override
	protected void initListeners() {

	}

	@Override
	protected void initData() {

	}

}
