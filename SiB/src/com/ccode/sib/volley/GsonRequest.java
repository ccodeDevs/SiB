package com.ccode.sib.volley;

import java.io.UnsupportedEncodingException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.ccode.sib.volley.GsonUtils.GsonTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

public class GsonRequest<T> extends Request<T> {

	private static final String PROTOCOL_CHARSET = "latin-2";
	private static final String PROTOCOL_CONTENT_TYPE = String.format("application/json", PROTOCOL_CHARSET);

	private final Gson gson;

	private final Class<T> mClazz;
	private final String mRequestBody;
	private Map<String, String> mHeaders = new HashMap<String, String>();

	private final Listener<T> mListener;

	public GsonRequest(String url, Class<T> clazz, String jsonRequestBody, Listener<T> listener, ErrorListener errorListener) {
		this(jsonRequestBody == null ? Method.GET : Method.POST, url, clazz, jsonRequestBody, listener, errorListener);
	}

	public GsonRequest(int method, String url, Class<T> clazz, String jsonRequestBody, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		setShouldCache(false);

		mListener = listener;
		mRequestBody = jsonRequestBody;
		mClazz = clazz;

		setRetryPolicy(new DefaultRetryPolicy(6000, 1, 1f));

		this.gson = GsonUtils.createGsonInstance(EnumSet.of(GsonTypeAdapter.DATE));
	}

	@Override
	protected void deliverResponse(T response) {
		if (mListener != null) {
			mListener.onResponse(response);
		}
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			return Response.success(gson.fromJson(json, mClazz), HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return mHeaders;
	}

	@Override
	public String getBodyContentType() {
		return PROTOCOL_CONTENT_TYPE;
	}

	@Override
	public byte[] getBody() {
		try {
			return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
		} catch (UnsupportedEncodingException uee) {
			VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, PROTOCOL_CHARSET);
			return null;
		}
	}
}