package com.masstransitproject.crosstown;

//Copyright 2007-2012 Chris Patterson, Dru Sellers, Travis Smith, et. al.
//
//Licensed under the Apache License, Version 2.0 (the "License"); you may not use
//this file except in compliance with the License. You may obtain a copy of the 
//License at 
//
// http://www.apache.org/licenses/LICENSE-2.0 
//
//Unless required by applicable law or agreed to in writing, software distributed
//under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
//CONDITIONS OF ANY KIND, either express or implied. See the License for the 
//specific language governing permissions and limitations under the License.
import java.util.Date;
import java.util.List;

public interface IFault<TMessage>
{
    /// <summary>
    /// The type of fault that occurred
    /// </summary>
    String getFaultType();

    /// <summary>
    /// Messages associated with the exception
    /// </summary>
    List<String> getMessages();
    void setMessages(List<String> value);
    
    /// <summary>
    /// When the exception occurred
    /// </summary>
    Date getOccurredAt();
    void setOccurredAt(Date value);

    /// <summary>
    /// A stack trace related to the exception
    /// </summary>
    List<StackTraceElement> getStackTrace();
    void setStackTrace(List<StackTraceElement> value);

}