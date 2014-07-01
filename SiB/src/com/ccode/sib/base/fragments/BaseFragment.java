package com.ccode.sib.base.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

public abstract class BaseFragment extends Fragment {

	protected abstract void initUi(View parent);

	protected abstract void initListeners();

	protected abstract void initData();

	protected void replaceFragment(int layoutId, BaseFragment fragment, boolean addToBackStack) {
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(layoutId, fragment);
		if (addToBackStack) {
			transaction.addToBackStack(fragment.getTag());
		}
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.commit();
	}

}
