package com.masstransitproject.crosstown;

import com.masstransitproject.crosstown.context.ISendContext;

public class SendCallback<T> {

	private ISendContext<T> isc;
	
	public SendCallback(ISendContext<T> isc) {
		this.isc = isc;
	}
	
	public void invoke() {}  //Nothing for now
 }
