package com.ccode.sib.volley;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

public class GsonUtils {

	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	public enum GsonTypeAdapter {
		DATE
	}

	public static Gson createGsonInstance() {
		return createGsonInstance(null);
	}

	public static Gson createGsonInstance(Set<GsonTypeAdapter> adapters) {
		if (adapters == null) {
			return new Gson();
		}

		GsonBuilder gsonBuilder = new GsonBuilder();
		if (adapters.contains(GsonTypeAdapter.DATE)) {
			gsonBuilder.registerTypeAdapter(Date.class, createDateSerializer()).registerTypeAdapter(Date.class, createDateDeserializer());
		}
		return gsonBuilder.create();
	}

	private static final SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

	private static JsonSerializer<Date> createDateSerializer() {
		return new JsonSerializer<Date>() {
			@Override
			public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
				if (src == null) {
					return null;
				}

				return new JsonPrimitive(formatter.format(src));
			}
		};
	}

	private static JsonDeserializer<Date> createDateDeserializer() {
		return new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				if (json == null) {
					return null;
				}

				try {
					return formatter.parse(json.getAsString());
				} catch (ParseException e) {
					return null;
				}
			}
		};
	}
}