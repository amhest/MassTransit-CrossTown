package com.masstransitproject.crosstown.transports;

import java.net.URI;

public class EndpointException extends AbstractUriException {

	public EndpointException() {
	}

	public EndpointException(URI uri) {
		super(uri);
	}

	public EndpointException(URI uri, String message) {
		super(uri, message);
	}

	public EndpointException(URI uri, String message, Exception innerException) {
		super(uri, message, innerException);
	}

}
