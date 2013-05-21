package com.masstransitproject.crosstown.messages;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.masstransitproject.crosstown.IMessage;

// Copyright 2007-2010 The Apache Software Foundation.
//  
// Licensed under the Apache License, Version 2.0 (the "License"); you may not use 
// this file except in compliance with the License. You may obtain a copy of the 
// License at 
// 
//     http://www.apache.org/licenses/LICENSE-2.0 
// 
// Unless required by applicable law or agreed to in writing, software distributed 
// under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
// CONDITIONS OF ANY KIND, either express or implied. See the License for the 
// specific language governing permissions and limitations under the License.

@SuppressWarnings("serial")
public class SerializationTestMessage implements Serializable, IMessage {
	private UUID UUIDValue;
	private boolean BoolValue;
	private byte ByteValue;
	private String StringValue;
	private int IntValue;
	private long LongValue;
	private BigDecimal DecimalValue;
	private double DoubleValue;
	private Date DateTimeValue;
	// No Java Equivalent...yet.
	// private TimeSpan TimeSpanValue ;
	private BigDecimal MaybeMoney;

	@Override
	public String getDotNetNamespace() {
		return "MassTransit.TestFramework.Examples.Messages";
	}

	public UUID getUUIDValue() {
		return UUIDValue;
	}

	public void setUUIDValue(UUID uUIDValue) {
		UUIDValue = uUIDValue;
	}

	public boolean isBoolValue() {
		return BoolValue;
	}

	public void setBoolValue(boolean boolValue) {
		BoolValue = boolValue;
	}

	public byte getByteValue() {
		return ByteValue;
	}

	public void setByteValue(byte byteValue) {
		ByteValue = byteValue;
	}

	public String getStringValue() {
		return StringValue;
	}

	public void setStringValue(String stringValue) {
		StringValue = stringValue;
	}

	public int getIntValue() {
		return IntValue;
	}

	public void setIntValue(int intValue) {
		IntValue = intValue;
	}

	public long getLongValue() {
		return LongValue;
	}

	public void setLongValue(long longValue) {
		LongValue = longValue;
	}

	public BigDecimal getDecimalValue() {
		return DecimalValue;
	}

	public void setDecimalValue(BigDecimal decimalValue) {
		DecimalValue = decimalValue;
	}

	public double getDoubleValue() {
		return DoubleValue;
	}

	public void setDoubleValue(double doubleValue) {
		DoubleValue = doubleValue;
	}

	public Date getDateTimeValue() {
		return DateTimeValue;
	}

	public void setDateTimeValue(Date dateTimeValue) {
		DateTimeValue = dateTimeValue;
	}

	public BigDecimal getMaybeMoney() {
		return MaybeMoney;
	}

	public void setMaybeMoney(BigDecimal maybeMoney) {
		MaybeMoney = maybeMoney;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (BoolValue ? 1231 : 1237);
		result = prime * result + ByteValue;
		result = prime * result
				+ ((DateTimeValue == null) ? 0 : DateTimeValue.hashCode());
		result = prime * result
				+ ((DecimalValue == null) ? 0 : DecimalValue.hashCode());
		long temp;
		temp = Double.doubleToLongBits(DoubleValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + IntValue;
		result = prime * result + (int) (LongValue ^ (LongValue >>> 32));
		result = prime * result
				+ ((MaybeMoney == null) ? 0 : MaybeMoney.hashCode());
		result = prime * result
				+ ((StringValue == null) ? 0 : StringValue.hashCode());
		result = prime * result
				+ ((UUIDValue == null) ? 0 : UUIDValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SerializationTestMessage other = (SerializationTestMessage) obj;
		if (BoolValue != other.BoolValue)
			return false;
		if (ByteValue != other.ByteValue)
			return false;
		if (DateTimeValue == null) {
			if (other.DateTimeValue != null)
				return false;
		} else if (!DateTimeValue.equals(other.DateTimeValue))
			return false;
		if (DecimalValue == null) {
			if (other.DecimalValue != null)
				return false;
		} else if (!DecimalValue.equals(other.DecimalValue))
			return false;
		if (Double.doubleToLongBits(DoubleValue) != Double
				.doubleToLongBits(other.DoubleValue))
			return false;
		if (IntValue != other.IntValue)
			return false;
		if (LongValue != other.LongValue)
			return false;
		if (MaybeMoney == null) {
			if (other.MaybeMoney != null)
				return false;
		} else if (!MaybeMoney.equals(other.MaybeMoney))
			return false;
		if (StringValue == null) {
			if (other.StringValue != null)
				return false;
		} else if (!StringValue.equals(other.StringValue))
			return false;
		if (UUIDValue == null) {
			if (other.UUIDValue != null)
				return false;
		} else if (!UUIDValue.equals(other.UUIDValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SerializationTestMessage [UUIDValue=" + UUIDValue
				+ ", BoolValue=" + BoolValue + ", ByteValue=" + ByteValue
				+ ", StringValue=" + StringValue + ", IntValue=" + IntValue
				+ ", LongValue=" + LongValue + ", DecimalValue=" + DecimalValue
				+ ", DoubleValue=" + DoubleValue + ", DateTimeValue="
				+ DateTimeValue + ", MaybeMoney=" + MaybeMoney + "]";
	}

}