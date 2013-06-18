package com.masstransitproject.crosstown.messages;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DeleteMessage implements Serializable, IMessage {
	@Override
	public String getDotNetNamespace() {
		return "MassTransit.TestFramework.Examples.Messages";
	}
}
