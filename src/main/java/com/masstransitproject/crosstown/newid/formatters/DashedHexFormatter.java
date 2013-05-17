// Copyright 2007-2012 Chris Patterson, Dru Sellers, Travis Smith, et. al.
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
package com.masstransitproject.crosstown.newid.formatters;

import com.masstransitproject.crosstown.newid.INewIdFormatter;

public class DashedHexFormatter implements INewIdFormatter {
	final int _alpha;
	final char _prefix;
	final char _suffix;
	int _length;

	public DashedHexFormatter() {
		this('\0', '\0', false);
	}

	public DashedHexFormatter(char prefix /* = '\0' */, char suffix /* = '\0' */,
			boolean upperCase) {
		if (prefix == '\0' || suffix == '\0') {
			_prefix = '\0';
			_suffix = '\0';
			_length = 36;
		} else {
			_prefix = prefix;
			_suffix = suffix;
			_length = 38;
		}

		_alpha = upperCase ? 'A' : 'a';
	}

	@Override
	public String Format(byte[] bytes) {
		char[] result = new char[_length];

		int i = 0;
		int offset = 0;
		if (_prefix != '\0')
			result[offset++] = _prefix;
		for (; i < 4; i++) {
			int value = bytes[i];
			result[offset++] = HexToChar(value >> 4, _alpha);
			result[offset++] = HexToChar(value, _alpha);
		}
		result[offset++] = '-';
		for (; i < 6; i++) {
			int value = bytes[i];
			result[offset++] = HexToChar(value >> 4, _alpha);
			result[offset++] = HexToChar(value, _alpha);
		}
		result[offset++] = '-';
		for (; i < 8; i++) {
			int value = bytes[i];
			result[offset++] = HexToChar(value >> 4, _alpha);
			result[offset++] = HexToChar(value, _alpha);
		}
		result[offset++] = '-';
		for (; i < 10; i++) {
			int value = bytes[i];
			result[offset++] = HexToChar(value >> 4, _alpha);
			result[offset++] = HexToChar(value, _alpha);
		}
		result[offset++] = '-';
		for (; i < 16; i++) {
			int value = bytes[i];
			result[offset++] = HexToChar(value >> 4, _alpha);
			result[offset++] = HexToChar(value, _alpha);
		}
		if (_suffix != '\0')
			result[offset++] = _suffix;

		return new String(result, 0, _length);
	}

	static char HexToChar(int value, int alpha) {
		value = value & 0xf;
		return (char) ((value > 9) ? value - 10 + alpha : value + 0x30);
	}
}
