package com.ccode.sib.news.managers;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ccode.sib.R;
import com.ccode.sib.news.models.NewsWrapper;
import com.ccode.sib.volley.SimpleXmlRequest;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

public class NewsDataManager {

	private static NewsDataManager sAddManager;

	private Context mContext;
	private RequestQueue mQueue;

	private NewsWrapper mNewsWrapper;

	public synchronized static NewsDataManager getInstance(Context context) {
		if (sAddManager == null) {
			sAddManager = new NewsDataManager(context);
		}
		return sAddManager;
	}

	private NewsDataManager(Context context) {
		mContext = context;
		mQueue = Volley.newRequestQueue(mContext);
	}

	public void getNews(final OnNewsLoadedListener downloadListener, boolean forceNetwork) {
		if (mNewsWrapper != null && !forceNetwork) {
			if (downloadListener != null) {
				downloadListener.onResponse(mNewsWrapper);
				return;
			}
		}

		// String url = C.URL;
		String url = mContext.getResources().getString(R.string.url_news);
		SimpleXmlRequest<NewsWrapper> request = new SimpleXmlRequest<NewsWrapper>(Method.GET, url, NewsWrapper.class, null,
				new Response.Listener<NewsWrapper>() {

					@Override
					public void onResponse(NewsWrapper wrapper) {
						mNewsWrapper = wrapper;
						if (downloadListener != null) {
							downloadListener.onResponse(mNewsWrapper);
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						if (downloadListener != null) {
							downloadListener.onError(error);
						}
					}
				}, null);

		mQueue.add(request);
	}

	public interface OnNewsLoadedListener {

		public void onResponse(NewsWrapper news);

		public void onError(VolleyError error);
	}
}