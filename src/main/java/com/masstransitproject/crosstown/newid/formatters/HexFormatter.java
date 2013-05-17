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

public class HexFormatter implements INewIdFormatter {
	final int _alpha;

	public HexFormatter() {
		this(false);
	}

	public HexFormatter(boolean upperCase) {
		_alpha = upperCase ? 'A' : 'a';
	}

	@Override
	public String Format(byte[] bytes) {
		char[] result = new char[32];

		int offset = 0;
		for (int i = 0; i < 16; i++) {
			byte value = bytes[i];
			result[offset++] = HexToChar(value >> 4, _alpha);
			result[offset++] = HexToChar(value, _alpha);
		}

		return new String(result, 0, 32);
	}

	static char HexToChar(int value, int alpha) {
		value = value & 0xf;
		return (char) ((value > 9) ? value - 10 + alpha : value + 0x30);
	}
}
