package com.ccode.sib.volley;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.ccode.sib.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

public class VolleyErrorHelper {

	public static void handleErrorWithToast(Object error, Context context) {
		if (context != null) {
			Toast.makeText(context, getMessage(error, context), Toast.LENGTH_LONG).show();
		}
	}

	public static void handleErrorWithTextView(Object error, Context context, TextView textView) {
		if (context != null) {
			textView.setText(getMessage(error, context));
		}
	}

	public static String getMessage(Object error, Context context) {
		boolean is401 = false;

		try {
			is401 = ((NoConnectionError) error).getMessage().contains("authentication");
		} catch (Exception e) {
		}
		if (context != null) {
			if (is401) {
				return context.getResources().getString(R.string.error_auth);
			}
			if (error instanceof String) {
				return (String) error;
			} else if (error instanceof TimeoutError) {
				return context.getResources().getString(R.string.error_timeout);
			} else if (isServerProblem(error)) {
				return handleServerError(error, context);
			} else if (isNetworkProblem(error)) {
				return context.getResources().getString(R.string.error_no_internet);
			}
			return context.getResources().getString(R.string.error);
		}
		return null;
	}

	private static boolean isNetworkProblem(Object error) {
		return (error instanceof NetworkError) || (error instanceof NoConnectionError);
	}

	private static boolean isServerProblem(Object error) {
		return (error instanceof ServerError) || (error instanceof AuthFailureError);
	}

	private static String handleServerError(Object err, Context context) {
		VolleyError error = (VolleyError) err;

		NetworkResponse response = error.networkResponse;

		if (response != null) {
			switch (response.statusCode) {
			case 404:
			case 422:
			case 401:
			case 400:
				try {
					// server might return error like this { "error":
					// "Some error occured" } Use "Gson" to parse the result
					Map<String, String> result = new Gson().fromJson(new String(response.data), new TypeToken<Map<String, String>>() {
					}.getType());

					if (result != null) {
						if (result.containsKey("error")) {
							return result.get(error);
						}
						if (result.containsKey("message")) {
							return result.get("message");
						}
						if (result.containsKey("error_message")) {
							return result.get("error_message");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return error.getMessage();

			default:
				return context.getResources().getString(R.string.error);
			}
		}
		return context.getResources().getString(R.string.error);
	}
}