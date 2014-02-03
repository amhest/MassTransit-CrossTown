package com.masstransitproject.crosstown;

import static org.junit.Assert.*;

import java.net.URI;

import org.junit.Test;

public class EndpointAddressTest {

	private static final String URI_STRING = "amqp://user:pass@hostName:5672/vhost";
	
	@Test(expected=IllegalArgumentException.class)
	public void should_throw_on_null_uri() {
		URI uri = null;
		new EndpointAddress(uri);
	}

	@Test(expected=IllegalArgumentException.class)
	public void should_throw_on_empty_string() {
		
		new EndpointAddress("");
	}


	@Test
	public void good_uri_string() {
		
		EndpointAddress ea = new EndpointAddress(URI_STRING);
		
		assertEquals(URI_STRING,ea.getUri().toString());
	}

	
	@Test
	public void good_uri() {
		URI uri = URI.create(URI_STRING);
		EndpointAddress ea = new EndpointAddress(uri);
		
		assertEquals(uri,ea.getUri());
	}


	@Test
	public void testGetPath() {
		URI uri = URI.create(URI_STRING);
		EndpointAddress ea = new EndpointAddress(uri);
		
		assertEquals("vhost",ea.getPath());
	}

	@Test
	public void is_not_transactional() {
		URI uri = URI.create(URI_STRING);
		EndpointAddress ea = new EndpointAddress(uri);
		assertFalse(ea.isTransactional());
	}
	@Test
	public void is_transactional() {
		URI uri = URI.create(URI_STRING+"?tx=true");
		EndpointAddress ea = new EndpointAddress(uri);
		assertTrue(ea.isTransactional());
	}

	@Test
	public void is_not_local() {
		EndpointAddress ea = new EndpointAddress(URI_STRING);
		assertFalse(ea.isLocal());
	}

	@Test
	public void is_local() {
		EndpointAddress ea = new EndpointAddress("amqp://user:pass@localhost:5672/vhost");
		assertTrue(ea.isLocal());
	}

}
