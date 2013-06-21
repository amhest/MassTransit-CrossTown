package com.masstransitproject.crosstown.messages;

import java.io.Serializable;

import com.masstransitproject.crosstown.ExternallyNamespaced;

@SuppressWarnings("serial")
public class DeleteMessage implements Serializable, ExternallyNamespaced {
	@Override
	public String getExternalNamespace() {
		return "MassTransit.TestFramework.Examples.Messages";
	}
}
