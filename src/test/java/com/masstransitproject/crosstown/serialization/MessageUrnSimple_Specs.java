package com.masstransitproject.crosstown.serialization;

import org.junit.Assert;
import org.junit.Test;

import com.masstransitproject.crosstown.MessageUrn;
import com.masstransitproject.crosstown.messages.PingMessage;

public class MessageUrnSimple_Specs {

	@Test
	public void testSimpleMessage() throws Exception{
		MessageUrn urn = new MessageUrn( PingMessage.class);
		Assert.assertEquals(urn.toString(),
				"urn:message:MassTransit.TestFramework.Examples.Messages:PingMessage");
	}

}