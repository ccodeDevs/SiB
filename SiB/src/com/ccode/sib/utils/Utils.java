package com.ccode.sib.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ccode.sib.BuildConfig;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

public final class Utils {

	public static void doLog(Object o) {
		if (BuildConfig.DEBUG) {
			Log.d(C.APPLICATION_NAME, o.toString());
		}
	}

	public static void doLogException(Throwable e) {
		if (BuildConfig.DEBUG) {
			Log.e(C.APPLICATION_NAME, "Exception", e);
		}
	}

	public static void doLogException(String message, Throwable t) {
		if (BuildConfig.DEBUG) {
			Log.e(C.APPLICATION_NAME, message, t);
		}
	}

	public static void doToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void doToast(Context context, int stringResId) {
		Toast.makeText(context, stringResId, Toast.LENGTH_LONG).show();
	}
}