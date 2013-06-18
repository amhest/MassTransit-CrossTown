package com.masstransitproject.crosstown.transports;

public abstract class TransportFactory {

	public abstract IInboundTransport createInbound();
	public abstract IOutboundTransport createOutbound();
}
