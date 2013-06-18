package com.masstransitproject.crosstown.handlers;

import com.masstransitproject.crosstown.context.ISendContext;

public class SendCallback<T extends Object> {

	private ISendContext<T> isc;
    private int retryCount;
    
	public SendCallback(ISendContext<T> isc) {
		this.isc = isc;
	}

	public SendCallback() {
		this.isc = null;
	}
	public void invoke() {}  //Nothing for now


	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	
	
 }
