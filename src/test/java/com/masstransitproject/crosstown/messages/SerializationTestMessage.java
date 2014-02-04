package com.masstransitproject.crosstown.messages;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.masstransitproject.crosstown.ExternallyNamespaced;

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
public class SerializationTestMessage implements Serializable, ExternallyNamespaced {
	private UUID uuidValue;
	private boolean boolValue;
	private byte byteValue;
	private String stringValue;
	private int intValue;
	private long longValue;
	private BigDecimal decimalValue;
	private double doubleValue;
	private Date dateTimeValue;
	// No Java Equivalent...yet.
	// private TimeSpan TimeSpanValue ;
	private BigDecimal maybeMoney;

	@Override
	public String getExternalNamespace() {
		return "MassTransit.TestFramework.Examples.Messages";
	}

	public UUID getUuidValue() {
		return uuidValue;
	}

	public void setUuidValue(UUID uuidValue) {
		this.uuidValue = uuidValue;
	}

	public boolean isBoolValue() {
		return boolValue;
	}

	public void setBoolValue(boolean boolValue) {
		this.boolValue = boolValue;
	}

	public byte getByteValue() {
		return byteValue;
	}

	public void setByteValue(byte byteValue) {
		this.byteValue = byteValue;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public long getLongValue() {
		return longValue;
	}

	public void setLongValue(long longValue) {
		this.longValue = longValue;
	}

	public BigDecimal getDecimalValue() {
		return decimalValue;
	}

	public void setDecimalValue(BigDecimal decimalValue) {
		this.decimalValue = decimalValue;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public Date getDateTimeValue() {
		return dateTimeValue;
	}

	public void setDateTimeValue(Date dateTimeValue) {
		this.dateTimeValue = dateTimeValue;
	}

	public BigDecimal getMaybeMoney() {
		return maybeMoney;
	}

	public void setMaybeMoney(BigDecimal maybeMoney) {
		this.maybeMoney = maybeMoney;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (boolValue ? 1231 : 1237);
		result = prime * result + byteValue;
		result = prime * result
				+ ((dateTimeValue == null) ? 0 : dateTimeValue.hashCode());
		result = prime * result
				+ ((decimalValue == null) ? 0 : decimalValue.hashCode());
		long temp;
		temp = Double.doubleToLongBits(doubleValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + intValue;
		result = prime * result + (int) (longValue ^ (longValue >>> 32));
		result = prime * result
				+ ((maybeMoney == null) ? 0 : maybeMoney.hashCode());
		result = prime * result
				+ ((stringValue == null) ? 0 : stringValue.hashCode());
		result = prime * result
				+ ((uuidValue == null) ? 0 : uuidValue.hashCode());
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
		if (boolValue != other.boolValue)
			return false;
		if (byteValue != other.byteValue)
			return false;
		if (dateTimeValue == null) {
			if (other.dateTimeValue != null)
				return false;
		} else if (!dateTimeValue.equals(other.dateTimeValue))
			return false;
		if (decimalValue == null) {
			if (other.decimalValue != null)
				return false;
		} else if (!decimalValue.equals(other.decimalValue))
			return false;
		if (Double.doubleToLongBits(doubleValue) != Double
				.doubleToLongBits(other.doubleValue))
			return false;
		if (intValue != other.intValue)
			return false;
		if (longValue != other.longValue)
			return false;
		if (maybeMoney == null) {
			if (other.maybeMoney != null)
				return false;
		} else if (!maybeMoney.equals(other.maybeMoney))
			return false;
		if (stringValue == null) {
			if (other.stringValue != null)
				return false;
		} else if (!stringValue.equals(other.stringValue))
			return false;
		if (uuidValue == null) {
			if (other.uuidValue != null)
				return false;
		} else if (!uuidValue.equals(other.uuidValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SerializationTestMessage [uuidValue=" + uuidValue
				+ ", boolValue=" + boolValue + ", byteValue=" + byteValue
				+ ", stringValue=" + stringValue + ", intValue=" + intValue
				+ ", longValue=" + longValue + ", decimalValue=" + decimalValue
				+ ", doubleValue=" + doubleValue + ", dateTimeValue="
				+ dateTimeValue + ", maybeMoney=" + maybeMoney + "]";
	}


}