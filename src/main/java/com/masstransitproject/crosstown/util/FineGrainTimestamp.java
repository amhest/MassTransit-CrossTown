package com.masstransitproject.crosstown.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class FineGrainTimestamp extends Timestamp {

	
	private static final long serialVersionUID = 1L;
	
	//These two need to be set at as close to the same time as possible
	/** Starting value of fine-grain timer
	 */
	private static final long baseNanos; 
	
	/**This is current time in Millis padded out with nanos values */
	private static final long baseMillisWithNanos;  
		
	static { 

		//This will be used to calculate an interval for a later time
		baseNanos = System.nanoTime();
		long baseMillis = System.currentTimeMillis();
		
		//Combine nanos from system timer with actual millis to create baseline
		baseMillisWithNanos = (baseMillis * 1000000) + (baseNanos % 1000000);

	}

	private ThreadLocal<DateFormat> defaultFormatter = new ThreadLocal<DateFormat>();

	public static void init() {
		//Do nothing for now.  Ensures statics are seeded.
	}
	
	protected FineGrainTimestamp(long currentTimeInMillis) {

		super(currentTimeInMillis);

	}
	protected FineGrainTimestamp(long currentTimeZeroMillis, int nanos) {

		super(currentTimeZeroMillis);
		this.setNanos(nanos);

	}

	public static FineGrainTimestamp fromNanos(long timeInNanos) {
		

		long delta;
		if (timeInNanos == -1) {
			//This is current time
			delta = System.nanoTime() - baseNanos;
		} else {
			//This is a unix time on same scale as timeInMillis
			delta = timeInNanos - baseMillisWithNanos;
		}
		long newTotalNanos = baseMillisWithNanos + delta;
		long dateZeroMills = newTotalNanos / 1000000000 * 1000;
		
		int nanosDelta = (int) ( newTotalNanos % 1000000000);
		
		return new FineGrainTimestamp(dateZeroMills,nanosDelta);
	}

	public static FineGrainTimestamp fromMillis(long timeInMillis) {
		return new FineGrainTimestamp(timeInMillis);
	}


	@Override
	public String toString() {

		// TODO Need to localize and UTC better.

		if (defaultFormatter.get() == null) {

			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSSSZ");
			final TimeZone utc = TimeZone.getTimeZone("UTC");
			sdf.setTimeZone(utc);

			defaultFormatter.set(sdf);
		}
		String s = defaultFormatter.get().format(this);
		return s.subSequence(0, s.lastIndexOf('.') + 1)
				+ String.valueOf(this.getNanos())
				+ "+0000";
	}

	public static FineGrainTimestamp getInstance() {
		
		return fromNanos(-1);
	   
	}
	
	public long getTotalNanos() {

		// Zero out the millis, pad and then add nanos.
		long total =  (getTime() / 1000 * 1000000000) + getNanos();
		return total;
	}

}
