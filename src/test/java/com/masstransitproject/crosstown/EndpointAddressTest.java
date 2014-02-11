package com.masstransitproject.crosstown;

import static org.junit.Assert.*;

import java.net.URI;

import org.junit.Test;

public class EndpointAddressTest {

	private static final String URI_STRING = "amqp://user:pass@hostName:5672/vhost";
	
	@Test(expected=IllegalArgumentException.class)
	public void should_throw_on_null_uri() {
		URI uri = null;
		new EndpointAddressImpl(uri);
	}

	@Test(expected=IllegalArgumentException.class)
	public void should_throw_on_empty_string() {
		
		new EndpointAddressImpl("");
	}


	@Test
	public void good_uri_string() {
		
		EndpointAddressImpl ea = new EndpointAddressImpl(URI_STRING);
		
		assertEquals(URI_STRING,ea.getUri().toString());
	}

	
	@Test
	public void good_uri() {
		URI uri = URI.create(URI_STRING);
		EndpointAddressImpl ea = new EndpointAddressImpl(uri);
		
		assertEquals(uri,ea.getUri());
	}


	@Test
	public void testGetPath() {
		URI uri = URI.create(URI_STRING);
		EndpointAddressImpl ea = new EndpointAddressImpl(uri);
		
		assertEquals("vhost",ea.getPath());
	}

	@Test
	public void is_not_transactional() {
		URI uri = URI.create(URI_STRING);
		EndpointAddressImpl ea = new EndpointAddressImpl(uri);
		assertFalse(ea.isTransactional());
	}
	@Test
	public void is_transactional() {
		URI uri = URI.create(URI_STRING+"?tx=true");
		EndpointAddressImpl ea = new EndpointAddressImpl(uri);
		assertTrue(ea.isTransactional());
	}

	@Test
	public void is_not_local() {
		EndpointAddressImpl ea = new EndpointAddressImpl(URI_STRING);
		assertFalse(ea.isLocal());
	}

	@Test
	public void is_local() {
		EndpointAddressImpl ea = new EndpointAddressImpl("amqp://user:pass@localhost:5672/vhost");
		assertTrue(ea.isLocal());
	}

}
