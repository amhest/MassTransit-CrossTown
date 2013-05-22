package com.masstransitproject.crosstown.serialization;

import org.junit.Assert;
import org.junit.Test;

import com.masstransitproject.crosstown.MessageUrn;
import com.masstransitproject.crosstown.messages.PingMessage;

public class MessageUrn_Specs {

	@Test
	public void TestSimpleMessage() {
		String urn = MessageUrn.GetUrn(new PingMessage());
		Assert.assertEquals(urn,
				"urn:message:MassTransit.TestFramework.Examples.Messages:PingMessage");
	}

}