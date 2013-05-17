package com.masstransitproject.crosstown;

public interface IMessage {

	/**
	 * Mass transit expects the fully qualified class name to match the message
	 * urn. This requires that the namespace match the c# version of the message
	 * 
	 * Since it's not really practical to have the same packages on both sides
	 * we require that the java message classes implement this interface.
	 * 
	 * @return
	 */
	public String getDotNetNamespace();
}
