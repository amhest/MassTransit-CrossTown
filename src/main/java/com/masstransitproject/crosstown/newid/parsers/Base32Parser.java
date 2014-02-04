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
package com.masstransitproject.crosstown.newid.parsers;

import com.masstransitproject.crosstown.newid.INewIdParser;
import com.masstransitproject.crosstown.newid.NewId;

public class Base32Parser implements INewIdParser {
	private static final String CONVERT_CHARS = "abcdefghijklmnopqrstuvwxyz234567ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";

	private static final String HEX_CHARS = "0123456789ABCDEF";

	private final String _chars;

	public Base32Parser(String chars) {
		if (chars.length() % 32 != 0)
			throw new IllegalArgumentException(
					"The characters must be a multiple of 32");

		_chars = chars;
	}

	public Base32Parser() {
		_chars = CONVERT_CHARS;
	}

	@Override
	public NewId parse(String text) {
		if (text == null || text.isEmpty())
			throw new IllegalArgumentException("The String was null or empty");

		char[] buffer = new char[32];

		int bufferOffset = 0;
		int offset = 0;
		long number;
		for (int i = 0; i < 6; ++i) {
			number = 0;
			for (int j = 0; j < 4; j++) {
				int index = _chars.indexOf(text.charAt(offset + j));
				if (index < 0)
					throw new IllegalArgumentException(
							"Tracking number contains invalid characters");

				number = number * 32 + (index % 32);
			}

			ConvertLongToBase16(buffer, bufferOffset, number, 5);

			offset += 4;
			bufferOffset += 5;
		}

		number = 0;
		for (int j = 0; j < 2; j++) {
			int index = _chars.indexOf(text.charAt(offset + j));
			if (index < 0)
				throw new IllegalArgumentException(
						"Tracking number contains invalid characters");

			number = number * 32 + (index % 32);
		}
		ConvertLongToBase16(buffer, bufferOffset, number, 2);

		return new NewId(new String(buffer, 0, 32));
	}

	private static void ConvertLongToBase16(char[] buffer, int offset, long value,
			int count) {
		for (int i = count - 1; i >= 0; i--) {
			int index = (int) (value % 16);
			buffer[offset + i] = HEX_CHARS.charAt(index);
			value /= 16;
		}
	}

}