package com.ccode.sib.news.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.ccode.sib.R;
import com.ccode.sib.base.fragments.BaseFragment;
import com.ccode.sib.news.activities.NewsDetailsActivity;
import com.ccode.sib.news.adapters.NewsListAdapter;
import com.ccode.sib.news.managers.NewsDataManager;
import com.ccode.sib.news.managers.NewsDataManager.OnNewsLoadedListener;
import com.ccode.sib.news.models.News;
import com.ccode.sib.news.models.NewsWrapper;
import com.ccode.sib.volley.VolleyErrorHelper;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

// TO DO PullToRefresh, implement OnRefreshListener

public class NewsListFragment extends BaseFragment {

	// UI Widgets
	private ListView mListView;

	// Adapter
	private NewsListAdapter mListAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View parent = inflater.inflate(R.layout.fragment_news_list, null);
		initUi(parent);
		return parent;
	}

	@Override
	protected void initUi(View parent) {
		mListView = (ListView) parent.findViewById(R.id.fragment_news_list_listview);
		mListAdapter = new NewsListAdapter(getActivity());
		mListView.setAdapter(mListAdapter);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initListeners();
		initData();
	}

	@Override
	protected void initListeners() {
		mListView.setOnItemClickListener(mOnItemClickListener);
	}

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			handleNewsItemClick(position);
		}
	};

	private void handleNewsItemClick(int position) {
		News news = mListAdapter.getItem(position);
		if (news != null) {
			Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
			intent.putExtra(NewsDetailsFragment.KEY_BUNDLE_NEWS, news);
			startActivity(intent);
		}

	}

	@Override
	protected void initData() {
		NewsDataManager.getInstance(getActivity()).getNews(mNewsLoadedListener, true);
	}

	private OnNewsLoadedListener mNewsLoadedListener = new OnNewsLoadedListener() {

		@Override
		public void onResponse(NewsWrapper news) {
			handleNewsResponse(news);
		}

		@Override
		public void onError(VolleyError error) {
			VolleyErrorHelper.handleErrorWithToast(error, getActivity());
		}
	};

	private void handleNewsResponse(NewsWrapper news) {
		if (news == null) {
			return;
		}
		mListAdapter.setData(news.getChannel().getNews());
	}

	public static BaseFragment newInstance() {
		return new NewsListFragment();
	}

}