package com.masstransitproject.crosstown.context;

import java.util.Map.Entry;
import java.util.Set;

public interface IMessageHeaders {

	public abstract String get(Object key);

	public abstract String put(String key, String value);

	public abstract Set<Entry<String, String>> entrySet();

	public abstract Set<String> keySet();

}