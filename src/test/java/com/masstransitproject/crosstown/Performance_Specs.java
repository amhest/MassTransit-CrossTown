package com.masstransitproject.crosstown;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.masstransitproject.crosstown.context.ISendContext;
import com.masstransitproject.crosstown.context.SendContext;
import com.masstransitproject.crosstown.messages.SerializationTestMessage;
import com.masstransitproject.crosstown.serialization.IMessageSerializer;
import com.masstransitproject.crosstown.serialization.JsonMessageSerializer;

public class Performance_Specs  {

	private Logger log = LoggerFactory.getLogger(Performance_Specs.class);
	private IMessageSerializer _serializer = new JsonMessageSerializer();

	public void TestJust_how_fast_are_you() throws Exception {
		log.trace("Serializer: " + _serializer.getClass().getName());
		SerializationTestMessage message = new SerializationTestMessage();
		message.setDecimalValue(new BigDecimal("123.45"));
		message.setLongValue(98123213L);
		message.setBoolValue(true);
		message.setByteValue((byte) 127);
		message.setIntValue(123);
		message.setDateTimeValue(new Date(new Timestamp(2008, 9, 8, 7, 6, 5, 4)
				.getTime()));
		// message.setTimeSpanValue(30.Seconds(),
		message.setUUIDValue(UUID.randomUUID());
		message.setStringValue("Chris's Sample Code");
		message.setDoubleValue(1823.172);

		ISendContext ctx = new SendContext();
		// warm it up
		for (int i = 0; i < 10; i++) {
			byte[] data;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			_serializer.Serialize(output, message, ctx);
			data = output.toByteArray();

			ByteArrayInputStream input = new ByteArrayInputStream(data);
			_serializer.Deserialize(input);

		}

		long start = System.currentTimeMillis();

		final int iterations = 50000;

		for (int i = 0; i < iterations; i++) {

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			_serializer.Serialize(output, message, ctx);
		}

		long stop = System.currentTimeMillis();

		long perSecond = iterations * 1000 / (stop - start);

		String msg = String.format("Serialize: {0}ms, Rate: {1} m/s",
				new Object[] { (stop - start), perSecond });
		log.trace(msg);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		_serializer.Serialize(output, message, ctx);
		byte[] sample = output.toByteArray();

		start = System.currentTimeMillis();

		for (int i = 0; i < 50000; i++) {

			ByteArrayInputStream input = new ByteArrayInputStream(sample);
			_serializer.Deserialize(input);
		}

		stop = System.currentTimeMillis();

		perSecond = iterations * 1000 / (stop - start);

		msg = String.format("Deserialize: {0}ms, Rate: {1} m/s", new Object[] {
				(stop - start), perSecond });
		log.trace(msg);
	}
}
