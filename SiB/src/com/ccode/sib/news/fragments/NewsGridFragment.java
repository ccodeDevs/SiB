package com.ccode.sib.news.fragments;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.android.volley.VolleyError;
import com.ccode.sib.R;
import com.ccode.sib.base.fragments.BaseFragment;
import com.ccode.sib.news.activities.NewsDetailsActivity;
import com.ccode.sib.news.adapters.NewsGridAdapter;
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

public class NewsGridFragment extends BaseFragment implements OnRefreshListener {

	// Pull to refresh
	private PullToRefreshLayout mPullToRefreshLayout;

	// UI Widgets
	private GridView mGridView;

	// Adapter
	private NewsGridAdapter mGridAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View parent = inflater.inflate(R.layout.fragment_news_grid, null);
		initUi(parent);
		return parent;
	}

	@Override
	protected void initUi(View parent) {
		mGridView = (GridView) parent.findViewById(R.id.fragment_news_grid_gridview);
		mGridAdapter = new NewsGridAdapter(getActivity());
		mGridView.setAdapter(mGridAdapter);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewGroup viewGroup = (ViewGroup) view;
		mPullToRefreshLayout = new PullToRefreshLayout(viewGroup.getContext());
		ActionBarPullToRefresh.from(getActivity()).insertLayoutInto(viewGroup).listener(this).setup(mPullToRefreshLayout);
		initListeners();
		initData();
	}

	@Override
	protected void initListeners() {
		mGridView.setOnItemClickListener(mOnItemClickListener);
	}

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			handleNewsItemClick(position);
		}
	};

	private void handleNewsItemClick(int position) {
		News news = mGridAdapter.getItem(position);
		if (news != null) {
			Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
			intent.putExtra(NewsDetailsFragment.KEY_BUNDLE_NEWS, news);
			startActivity(intent);
		}
	}

	@Override
	protected void initData() {
		refreshData(false);
	}

	private void refreshData(Boolean forceNetwork) {
		mPullToRefreshLayout.setRefreshing(true);
		NewsDataManager.getInstance(getActivity()).getNews(mNewsLoadedListener, forceNetwork);

	}

	private OnNewsLoadedListener mNewsLoadedListener = new OnNewsLoadedListener() {

		@Override
		public void onResponse(NewsWrapper news) {
			mPullToRefreshLayout.setRefreshComplete();
			handleNewsResponse(news);
		}

		@Override
		public void onError(VolleyError error) {
			mPullToRefreshLayout.setRefreshComplete();
			VolleyErrorHelper.handleErrorWithToast(error, getActivity());
		}
	};

	private void handleNewsResponse(NewsWrapper news) {
		if (news == null) {
			return;
		}

		mGridAdapter.setData(news.getChannel().getNews());
	}

	public void onRefreshStarted(View view) {
		refreshData(true);
	}

	public static BaseFragment newInstance() {
		return new NewsGridFragment();
	}
}