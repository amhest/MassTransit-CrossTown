package com.masstransitproject.crosstown.messages;

import java.io.Serializable;

import com.masstransitproject.crosstown.IMessage;

@SuppressWarnings("serial")
public class UpdateAcceptedMessage implements Serializable, IMessage {
	@Override
	public String getDotNetNamespace() {
		return "MassTransit.TestFramework.Examples.Messages";
	}
}
