package com.masstransitproject.crosstown.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FineGrainTimestamp extends Timestamp {

	private static final int conversionFactor;

	static { // This really just ensures that the JVM is internally
				// consistent

		// Need to do some machinations to not double-count nanos
		long nanos = System.nanoTime();
		long millis = System.currentTimeMillis();
		long secs = (millis / 1000);

		// Calculate this in case the granularity of nanos drifts
		conversionFactor = (int) Math.pow(10, String.valueOf(nanos).length()
				- (String.valueOf(secs).length()));
	}

	private ThreadLocal<DateFormat> defaultFormatter = new ThreadLocal<DateFormat>();

	private static long getWithZeroMillins(long currentTimeInNanos) {
		long nanosMask = (currentTimeInNanos / conversionFactor)
				* conversionFactor;
		int nanosOnly = (int) (currentTimeInNanos - nanosMask);
		long currentTimeZeroMillis = currentTimeInNanos / conversionFactor
				* 1000;

		return currentTimeZeroMillis;
	}

	private static int getNanosOnly(long currentTimeInNanos) {
		long nanosMask = (currentTimeInNanos / conversionFactor)
				* conversionFactor;
		int nanosOnly = (int) (currentTimeInNanos - nanosMask);
		return nanosOnly;
	}

	public FineGrainTimestamp(long currentTimeInNanos) {

		super(getWithZeroMillins(currentTimeInNanos));
		this.setNanos(getNanosOnly(currentTimeInNanos));

	}

	public long getTotalNanos() {

		// Zero out the millis, pad and then add nanos.
		return (getTime() / 1000 * conversionFactor) + getNanos();
	}

	@Override
	public String toString() {

		// TODO Need to localize and UTC better.

		if (defaultFormatter.get() == null) {

			defaultFormatter.set(new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSSSZ"));
		}
		String s = defaultFormatter.get().format(this);
		return s.subSequence(0, s.lastIndexOf('.') + 1)
				+ String.valueOf(this.getNanos())
				+ s.subSequence(s.lastIndexOf('-'), s.length());
	}

	public FineGrainTimestamp() {
		this(System.nanoTime());
	}

}
