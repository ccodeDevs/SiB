package com.ccode.sib.volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.ccode.sib.utils.Utils;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

public class SimpleXmlRequest<T> extends Request<T> {

	/** Charset for request. */
	private static final String PROTOCOL_CHARSET = "latin-2";

	/** Content type for request. */
	private static final String PROTOCOL_CONTENT_TYPE = String.format("text/plain; charset=%s", PROTOCOL_CHARSET);

	private final Class<T> mClazz;
	private Map<String, String> mHeaders;
	private Map<String, String> mParams;

	private final Listener<T> mListener;

	private String requestBody;

	public SimpleXmlRequest(String url, Class<T> clazz, Map<String, String> headers, Listener<T> listener, ErrorListener errorListener) {
		this(Method.GET, url, clazz, headers, null, listener, errorListener);
	}

	public SimpleXmlRequest(String url, Class<T> clazz, Map<String, String> headers, JSONObject requestBody, Listener<T> listener,
			ErrorListener errorListener) {
		this(requestBody == null ? Method.GET : Method.POST, url, clazz, headers, (requestBody == null) ? null : requestBody.toString(),
				listener, errorListener);
	}

	public SimpleXmlRequest(int method, String url, Class<T> clazz, Map<String, String> headers, String requestBody, Listener<T> listener,
			ErrorListener errorListener) {
		super(method, url, errorListener);
		mClazz = clazz;
		mHeaders = headers;
		mListener = listener;
		this.requestBody = requestBody;
	}

	public SimpleXmlRequest(int method, String url, Class<T> clazz, Map<String, String> headers, Listener<T> listener,
			ErrorListener errorListener, Map<String, String> params) {
		super(method, url, errorListener);
		mClazz = clazz;
		mListener = listener;
		mHeaders = headers;
		mParams = params;
	}

	@Override
	public String getUrl() {
		String url = handleUrl(super.getUrl());
		Utils.doLog("url: " + url);
		return url;
	}

	@SuppressWarnings("rawtypes")
	public String handleUrl(String url) {
		String newUrl = url;
		if (mParams != null) {
			for (Map.Entry entry : mParams.entrySet()) {
				newUrl += "&" + entry.getKey() + "=" + entry.getValue();
			}
		}
		return newUrl;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		if (mHeaders == null) {
			mHeaders = new HashMap<String, String>();
		}
		// mHeaders.put("Authorization", "auth");
		return mHeaders;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		Utils.doLog("params: " + mParams);
		return mParams;
	}

	public void setParameters(Map<String, String> params) {
		mParams = params;
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
			String xml = new String(response.data, PROTOCOL_CHARSET);
			T result = null;
			Serializer serializer = new Persister();
			result = serializer.read(mClazz, xml, false);
			return Response.success(result, VolleyHttpHeaderParser.parseCacheHeaders(response));
		} catch (Exception e) {
			return Response.error(new VolleyError(e));
		}
	}

	@Override
	public String getBodyContentType() {
		return PROTOCOL_CONTENT_TYPE;
	}

	@Override
	public byte[] getBody() {
		try {
			return requestBody == null ? null : requestBody.getBytes(PROTOCOL_CHARSET);
		} catch (UnsupportedEncodingException uee) {
			return null;
		}
	}

}
