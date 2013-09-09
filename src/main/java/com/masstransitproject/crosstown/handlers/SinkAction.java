package com.masstransitproject.crosstown.handlers;

public class SinkAction<T extends Object> {

	T _result;

	public SinkAction(T _result) {
		super();
		this._result = _result;
	}
}
