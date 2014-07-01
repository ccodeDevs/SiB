package com.ccode.sib.splash.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccode.sib.R;
import com.ccode.sib.base.fragments.BaseFragment;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

public class SplashFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View parent = inflater.inflate(R.layout.fragment_splash, null);
		return parent;
	}

	@Override
	protected void initUi(View parent) {
		// ok nothing here
	}

	@Override
	protected void initListeners() {
		// ok nothing here
	}

	@Override
	protected void initData() {
		// ok nothing here
	}

	public static BaseFragment newInstance() {
		return new SplashFragment();
	}
}
