package com.masstransitproject.crosstown.context;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;

import com.masstransitproject.crosstown.messages.RequestMessage;
import com.masstransitproject.crosstown.messages.ResponseMessage;
import com.masstransitproject.crosstown.newid.NewId;

public class SendContextTest {


	@Test
	public void can_create_with_identifiable_class() throws Exception{
		
		SendContextImpl<RequestMessage> sc = new SendContextImpl<RequestMessage>(RequestMessage.class);
	}
	@Test
	public void can_create_without_identifiable_class() throws Exception{
		
		assertFalse(Identifiable.class.isAssignableFrom(ResponseMessage.class));
		SendContextImpl<ResponseMessage> sc = new SendContextImpl<ResponseMessage>(ResponseMessage.class);
	}
	@Test
	public void can_create_with_identifiable_class_and_id_is_set() throws Exception{
		
		SendContextImpl<RequestMessage> sc = new SendContextImpl<RequestMessage>(RequestMessage.class);
		assertNotNull(sc.getMessage());
		assertNotNull(sc.getId());
		assertEquals(sc.getId(), sc.getMessage().getId());
	}
	@Test
	public void can_create_with_identifiable_object_and_id_is_set() throws Exception{
		
		RequestMessage msg = new RequestMessage();
		msg.setId(NewId.nextGuid());
		SendContextImpl<RequestMessage> sc = new SendContextImpl<RequestMessage>(msg);
		assertNotNull(sc.getMessage());
		assertNotNull(sc.getId());
		assertEquals(sc.getId(), msg.getId());
	}

	@Test(expected=IllegalStateException.class)
	public void fails_when_id_is_not_set() throws Exception{
		
		RequestMessage msg = new RequestMessage();
		//Not set msg.setId(NewId.nextGuid());
		msg.setId(null);  //Clear this out to mimic not being set
		SendContextImpl<RequestMessage> sc = new SendContextImpl<RequestMessage>(msg);
	}
	@Test
	public void can_create() throws Exception{
		
		new SendContextImpl<RequestMessage>(RequestMessage.class);
	}

	@Test
	public void can_serialize_to() throws Exception {
		SendContextImpl<RequestMessage> ctx = new SendContextImpl<RequestMessage>(RequestMessage.class);
		UUID random = UUID.randomUUID();
		ctx.getMessage().setCorrelationId(random);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		ctx.serializeTo(baos);
		String s = new String(baos.toByteArray());
		assertTrue(s.contains("RequestMessage"));
		s.contains(random.toString());
	}

}
