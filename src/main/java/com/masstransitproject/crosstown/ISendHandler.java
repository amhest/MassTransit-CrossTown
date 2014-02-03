package com.masstransitproject.crosstown;

/**
 * In .Net RecieveContexts leverage lambdas. Without function pointers in JDK
 * 1.6 We provide an interface to be invoked when ReceiveContext receives a
 * message.
 * 
 * @param <T>
 */
public interface ISendHandler<T> {

}
