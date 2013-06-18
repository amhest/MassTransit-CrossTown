package com.masstransitproject.crosstown.handlers;

import java.net.URI;

import com.masstransitproject.crosstown.IServiceBus;
import com.masstransitproject.crosstown.context.SendContext;


public class FaultSender<T extends Object>{

	
	
	public FaultSender(IServiceBus bus, URI faultAddress, URI responseAddress,
			String requestId, T message) {
		super();
		this.bus = bus;
		this.faultAddress = faultAddress;
		this.responseAddress = responseAddress;
		this.requestId = requestId;
		this.message = message;
	}


	private final IServiceBus bus;
	private final URI faultAddress;
	private final URI responseAddress;
	private final String requestId;
	private final T message;
	
	public IServiceBus getBus() {
		return bus;
	}


	public URI getFaultAddress() {
		return faultAddress;
	}


	public URI getResponseAddress() {
		return responseAddress;
	}


	public String getRequestId() {
		return requestId;
	}


	public T getMessage() {
		return message;
	}


	public void send(SendContext context) {
    if (faultAddress != null)
    {
    	context.setSourceAddress(bus.getEndpoint().getAddress().getUri());
        context.setRequestId(requestId);
        
        bus.GetEndpoint(faultAddress).Send(context);
    }
    else if (responseAddress != null)
    {
    	context.setSourceAddress(bus.getEndpoint().getAddress().getUri());
        context.setRequestId(requestId);
        bus.GetEndpoint(responseAddress).Send(context);
    }
    else
    {
    	context.setRequestId(requestId);
        bus.Publish( context);
    }
}

	
	public void invoke() {}  //Nothing for now
 }
