package com.masstransitproject.crosstown.util;

public class StopWatch {

	private long startNanos;
	public StopWatch() {
		
		startNanos = System.nanoTime();
	}
	
	public long elapsedMilliseconds() {
		
		return System.nanoTime() - startNanos;
	}

}
