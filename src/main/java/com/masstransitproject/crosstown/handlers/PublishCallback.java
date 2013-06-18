package com.masstransitproject.crosstown.handlers;

import com.masstransitproject.crosstown.context.ISendContext;

public class PublishCallback<T> {

	private ISendContext<T> isc;
	
	public PublishCallback(ISendContext<T> isc) {
		this.isc = isc;
	}
	
	public void invoke() {}  //Nothing for now
 }
