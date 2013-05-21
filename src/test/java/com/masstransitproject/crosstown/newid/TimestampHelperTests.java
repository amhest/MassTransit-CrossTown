package com.masstransitproject.crosstown.newid;

import java.sql.Timestamp;
import java.util.Date;

import com.masstransitproject.crosstown.util.FineGrainTimestamp;

import junit.framework.TestCase;

public class TimestampHelperTests extends TestCase {

	public void testNoLossiness() {

		long currentNanos = System.nanoTime();
		Timestamp ts =  FineGrainTimestamp.fromNanos(
				currentNanos);
		String coarseTs = new Timestamp(new Date().getTime()).toString().replace(" ","T");

		// should be same minute to ensure that we are creating a real timestamp
		assertEquals(
				"Finegrain timestamp should be in same minute as traditional timestamp.",
				ts.toString().substring(0, coarseTs.lastIndexOf(':')),
				coarseTs.substring(0, coarseTs.lastIndexOf(':')));

		assertEquals("Lost fidelity converting " + currentNanos
				+ " to timestamp.", currentNanos, new FineGrainTimestamp()
				.getTotalNanos());

	}

	public void testToString() {

		long currentNanos = System.nanoTime();
		Timestamp ts =   FineGrainTimestamp.fromNanos(
				currentNanos);

		Timestamp coarseTs = new Timestamp(new Date().getTime());

		String fine = ts.toString();
		String course = coarseTs.toString();

		// prevent serious weirdness
		assertEquals(
				"Finegrain timestamp should be in same minute as traditional timestamp.",
				fine.substring(0, fine.lastIndexOf('.')),
				course.substring(0, course.lastIndexOf('.')));

		String nanoComponent = fine.subSequence(fine.lastIndexOf('.') + 1,
				fine.lastIndexOf('-')).toString();
		assertTrue(
				"Nano Component of Timestamp should be substring of total nanos."
						+ nanoComponent + " failed for " + currentNanos, String
						.valueOf(currentNanos).endsWith(nanoComponent));

	}

}
