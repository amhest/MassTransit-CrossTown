package com.masstransitproject.crosstown.serialization;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import com.masstransitproject.crosstown.messages.RequestMessage;

public class EnvelopeTest {

	@Test
	public void can_set_message_types() {
		
		List<Class<?>> l = new ArrayList<Class<?>>();
		RequestMessage rm = new RequestMessage();
		l.add(rm.getClass());
		UUID random = UUID.randomUUID();
		rm.setCorrelationId(random);
		
		Envelope e = new Envelope(rm,l);
		
		assertNotNull(e.getMessageType());
		assertTrue(e.getMessageType().get(0).contains("RequestMessage"));
	}
	


}
