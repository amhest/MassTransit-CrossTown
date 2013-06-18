package com.masstransitproject.crosstown;

import java.io.Serializable;

public class CorrelatedFault<TMessage extends Object & CorrelatedBy<TKey>, TKey> extends
Fault<TMessage> implements
CorrelatedBy<TKey>, Serializable
{
/// <summary>
/// Creates a new Fault message for the failed correlated message
/// </summary>
/// <param name="ex"></param>
/// <param name="message"></param>
public CorrelatedFault(TMessage message, Exception ex)
{    
	super(message, ex);

    setCorrelationId(message.getCorrelationId());

    setFaultType(this.getClass().getSimpleName());
}

CorrelatedFault()
{
}

private TKey getCorrelationId;

public TKey getCorrelationId() {
	return getCorrelationId;
}

public void setCorrelationId(TKey getCorrelationId) {
	this.getCorrelationId = getCorrelationId;
}


}


