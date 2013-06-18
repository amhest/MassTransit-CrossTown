package com.masstransitproject.crosstown.handlers;

import com.masstransitproject.crosstown.transports.Connection;

public class ConnectionPolicyCallback<T> {

	private Connection conn;
	
	public ConnectionPolicyCallback(Connection c) {
		this.conn = c;
	}
	
	public void invoke() {}  //Nothing for now
 }
