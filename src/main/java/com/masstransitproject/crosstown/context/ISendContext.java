package com.masstransitproject.crosstown.context;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

// Copyright 2007-2011 Chris Patterson, Dru Sellers, Travis Smith, et. al.
//
// Licensed under the Apache License, Version 2.0 (the "License"); you may not use 
// this file except in compliance with the License. You may obtain a copy of the 
// License at 
//
// http://www.apache.org/licenses/LICENSE-2.0 
//
// Unless required by applicable law or agreed to in writing, software distributed 
// under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
// CONDITIONS OF ANY KIND, either express or implied. See the License for the 
// specific language governing permissions and limitations under the License.

public interface ISendContext<T extends Object> extends IMessageContext<T> {

	public abstract UUID getId();

	public abstract void setDeclaringMessageType(Class<T> declaringMessageType);

	public Class<T> getDeclaringMessageType();

	public abstract void SerializeTo(OutputStream stream) throws IOException;

	@Override
	public abstract T getMessage();

	public abstract List<Class> GetMessageTypes();

	public void SetReceiveContext(IReceiveContext<T> receiveContext);

	public abstract String getOriginalMessageId();

	public abstract void setRetryCount(int value);
}