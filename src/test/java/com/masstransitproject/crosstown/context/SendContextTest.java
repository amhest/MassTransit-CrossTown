package com.masstransitproject.crosstown.context;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.UUID;

import org.junit.Test;

import com.masstransitproject.crosstown.messages.RequestMessage;

public class SendContextTest {


	@Test
	public void can_create() throws Exception{
		
		SendContext<RequestMessage> ctx = new SendContext<RequestMessage>(RequestMessage.class);
	}
	
	@Test
	public void can_serialize_to() throws Exception {
		SendContext<RequestMessage> ctx = new SendContext<RequestMessage>(RequestMessage.class);
		UUID random = UUID.randomUUID();
		ctx.getMessage().setCorrelationId(random);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		ctx.SerializeTo(baos);
		String s = new String(baos.toByteArray());
		assertTrue(s.contains("RequestMessage"));
		s.contains(random.toString());
	}

}
