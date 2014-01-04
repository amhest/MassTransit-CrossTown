package com.masstransitproject.crosstown;

import java.net.URI;

public interface IEndpointAddress {

	
	URI getUri();
	
	boolean isLocal();
	
	boolean isTransactional();
}
