package com.ccode.sib.base.adapters;

import android.widget.BaseAdapter;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

public abstract class InfiniteBaseAdapter extends BaseAdapter {

	// base adapter listener
	private Listener mListener;

	public void setInfiniteAdapterListener(Listener infiniteAdapterListener) {
		this.mListener = infiniteAdapterListener;
	}

	protected boolean isLastPosition(int position) {
		if (position > 0 && (getCount() - 1) == position) {
			return true;
		}
		return false;
	}

	protected void checkAndReportLastPosition(int position) {
		if (isLastPosition(position) && (getCount() < getTotalItems()) && mListener != null) {
			mListener.onLastItemReached();
		}
	}

	protected final int calculateNewTotal(int totalItems) {
		int currentItems = getCount();
		if (totalItems > currentItems) {
			return totalItems;
		} else {
			return currentItems;
		}
	}

	public abstract int getTotalItems();

	public interface Listener {
		public void onLastItemReached();
	}

}
