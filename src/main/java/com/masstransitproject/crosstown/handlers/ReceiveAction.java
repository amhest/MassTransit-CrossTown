package com.masstransitproject.crosstown.handlers;

import com.masstransitproject.crosstown.context.ISendContext;

public class ReceiveAction<T extends Object> {

	private ISendContext<T> isc;
    private int retryCount;
    
	public ReceiveAction(ISendContext<T> isc) {
		this.isc = isc;
	}

	public ReceiveAction() {
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
