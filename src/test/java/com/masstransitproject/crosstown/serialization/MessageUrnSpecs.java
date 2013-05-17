package com.masstransitproject.crosstown.serialization;

import junit.framework.TestCase;

import com.masstransitproject.crosstown.MessageUrn;
import com.masstransitproject.crosstown.messages.PingMessage;

public class MessageUrnSpecs extends TestCase {

	public void TestSimpleMessage() {
		String urn = MessageUrn.GetUrn(new PingMessage());
		assertEquals(urn,
				"message:MassTransit.TestFramework.Examples.Messages:Ping");
	}

}