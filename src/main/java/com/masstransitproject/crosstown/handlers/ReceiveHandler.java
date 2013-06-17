package com.masstransitproject.crosstown.handlers;

import com.masstransitproject.crosstown.context.ISendContext;

public class ReceiveHandler<T> {

	private ISendContext<T> isc;
	
	public ReceiveHandler(ISendContext<T> isc) {
		this.isc = isc;
	}
	
	public void invoke() {}  //Nothing for now
 }
