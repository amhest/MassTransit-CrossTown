package com.masstransitproject.crosstown.handlers;

import com.masstransitproject.crosstown.context.ISendContext;

public class UnsubscribeHandler<T> {

	private ISendContext<T> isc;
	
	public UnsubscribeHandler(ISendContext<T> isc) {
		this.isc = isc;
	}
	
	public void invoke() {}  //Nothing for now
 }
