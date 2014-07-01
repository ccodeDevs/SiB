package com.ccode.sib.base.activities;

import com.ccode.sib.base.fragments.BaseFragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

public abstract class BaseActivity extends ActionBarActivity {

	protected abstract void initUi();

	protected abstract void initListeners();

	protected abstract void initData();

	protected void replaceFragment(int layoutId, BaseFragment fragment, boolean addToBackStack) {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(layoutId, fragment);
		if (addToBackStack) {
			transaction.addToBackStack(fragment.getTag());
		}
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.commit();
	}

}