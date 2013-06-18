package com.masstransitproject.crosstown.handlers;

import com.masstransitproject.crosstown.transports.Connection;

public class ConnectionCallback<T> {

	private Connection conn;
	
	public ConnectionCallback(Connection c) {
		this.conn = c;
	}
	
	public void invoke() {}  //Nothing for now
 }
