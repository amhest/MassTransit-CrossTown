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

public class ZBase32Parser extends Base32Parser {
	private static final String CONVERT_CHARS = "ybndrfg8ejkmcpqxot1uwisza345h769YBNDRFG8EJKMCPQXOT1UWISZA345H769";

	private static final String TRANSPOSE_CHARS = "ybndrfg8ejkmcpqx0tlvwis2a345h769YBNDRFG8EJKMCPQX0TLVWIS2A345H769";

	public ZBase32Parser() {
		this(false);
	}

	public ZBase32Parser(boolean handleTransposedCharacters) {
		super(handleTransposedCharacters ? CONVERT_CHARS + TRANSPOSE_CHARS
				: CONVERT_CHARS);

	}
}
