package com.masstransitproject.crosstown.context;

import java.util.Set;
import java.util.Map.Entry;

public interface IMessageHeaders {

	public abstract String get(Object key);

	public abstract String put(String key, String value);

	public abstract Set<Entry<String, String>> entrySet();

	public abstract Set<String> keySet();

}