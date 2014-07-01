package com.ccode.sib.news.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccode.sib.R;
import com.ccode.sib.news.models.News;
import com.squareup.picasso.Picasso;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

public class NewsListAdapter extends BaseAdapter {

	// Data Source
	private List<News> mDataSource = new ArrayList<News>();

	private Context mContext;
	private ViewHolder mViewHolder;

	public NewsListAdapter(Context context) throws IllegalArgumentException {
		if (context == null) {
			throw new IllegalArgumentException("Context cannot be null!");
		}

		mContext = context;
	}

	public void setData(List<News> news) {
		mDataSource.addAll(news);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mDataSource.size();
	}

	@Override
	public News getItem(int position) {
		return mDataSource.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mDataSource.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.items_news_list, parent, false);
			mViewHolder = new ViewHolder();
			initViewHolderChilds(convertView);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		News item = getItem(position);
		if (item != null) {
			fillViewHolderWithData(item);
		}

		return convertView;
	}

	private void fillViewHolderWithData(News item) {
		String title = item.getTitle();
		if (!TextUtils.isEmpty(title)) {
			mViewHolder.mTitleText.setText(title);
		}

		String date = item.getPublishedDate();
		if (!TextUtils.isEmpty(date)) {
			mViewHolder.mDateText.setText(date);
		}

		String imagePath = item.getDescription();
		if (!TextUtils.isEmpty(imagePath)) {
			Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
			Matcher m = p.matcher(imagePath);
			if (m.find()) {
				imagePath = m.group(1);
			}

			Picasso.with(mContext).load(imagePath).resizeDimen(R.dimen.grid_image_width, R.dimen.grid_image_height)
					.placeholder(R.drawable.logo).into(mViewHolder.mImage);
		}
	}

	private void initViewHolderChilds(View parent) {
		mViewHolder.mTitleText = (TextView) parent.findViewById(R.id.item_news_list_title);
		mViewHolder.mDateText = (TextView) parent.findViewById(R.id.item_news_list_date);
		mViewHolder.mImage = (ImageView) parent.findViewById(R.id.item_news_list_image);
	}

	private static class ViewHolder {
		private TextView mTitleText;
		private TextView mDateText;
		private ImageView mImage;
	}
}