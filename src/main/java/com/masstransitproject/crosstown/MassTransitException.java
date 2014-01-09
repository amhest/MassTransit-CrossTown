package com.masstransitproject.crosstown;

public class MassTransitException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MassTransitException(String string, Exception ex) {
		super( string,  ex);
	}

	public MassTransitException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MassTransitException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public MassTransitException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public MassTransitException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
