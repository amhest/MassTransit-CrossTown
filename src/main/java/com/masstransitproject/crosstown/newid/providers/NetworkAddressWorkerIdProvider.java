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
package com.masstransitproject.crosstown.newid.providers;

import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Enumeration;

import com.masstransitproject.crosstown.newid.IWorkerIdProvider;

public class NetworkAddressWorkerIdProvider implements IWorkerIdProvider {
	@Override
	public byte[] GetWorkerId(int index) throws IOException {
		return GetNetworkAddress(index);
	}

	static byte[] GetNetworkAddress(int index) throws IOException {
		Enumeration<NetworkInterface> interfaces = NetworkInterface
				.getNetworkInterfaces();

		while (interfaces.hasMoreElements()) {
			NetworkInterface nic = interfaces.nextElement();

			byte[] address = nic.getHardwareAddress();
			if (address != null && address.length > 0) {
				return address;
			}

		}

		throw new IOException(
				"Unable to find usable network adapter for unique address");

	}

}
