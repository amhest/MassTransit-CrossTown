package com.masstransitproject.crosstown;

import static org.junit.Assert.*;

import org.junit.Test;

import com.masstransitproject.crosstown.messages.RequestMessage;

public class MessageUrnTest {

	@Test
	public void urn_contains_dotnet_namespace() throws Exception {
		//"urn:message:My.DotNet.NameSpace:MyClassName")

		MessageUrn urn = new MessageUrn(RequestMessage.class);
		
		String s = urn.toString();
		
		assertTrue(s.contains(new RequestMessage().getExternalNamespace()));
		
	}

	@Test
	public void urn_from_java_string_contains_dotnet_namespace() throws Exception{
		MessageUrn urn = new MessageUrn("urn:message:com.masstransitproject.crosstown.messages:RequestMessage");
		
		
		
		assertEquals(RequestMessage.class, urn.getMessageClass());
	}
	
	@Test
	public void urn_from_string_contains_dotnet_namespace() throws Exception{
		
		//Need to spin up RequestMessage
		MessageUrn.registerMessageType(RequestMessage.class);
		
		MessageUrn urn = new MessageUrn("urn:message:MassTransit.TestFramework.Examples.Messages:RequestMessage");
		
		
		
		assertEquals(RequestMessage.class, urn.getMessageClass());
	}


}
