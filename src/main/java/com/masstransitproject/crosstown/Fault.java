package com.masstransitproject.crosstown;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

//Copyright 2007-2011 Chris Patterson, Dru Sellers, Travis Smith, et. al.
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



/// <summary>
/// A fault is a system-generated message that is published when an exception occurs while processing a message.
/// </summary>
/// <typeparam name="TMessage">The type of message that threw the exception</typeparam>

public class Fault<TMessage extends Object>
implements
IFault<TMessage>,Serializable
{
    /// <summary>
    /// Creates a new fault message for the failed message
    /// </summary>
    /// <param name="ex">The exception thrown by the message consumer</param>
    /// <param name="message">The message that was being processed when the exception was thrown</param>
    public Fault(TMessage message, Exception ex)
    {
        setFailedMessage (message);
        setOccurredAt(new Date());
        
        
        setMessages(GetExceptionMessages(ex));
        setStackTrace(GetStackTrace(ex));
        setFaultType(getClass().getSimpleName());
    }

    protected Fault()
    {
    }

    /// <summary>
    /// The message that failed to be consumed
    /// </summary>
    private TMessage FailedMessage; 

    private String FaultType ;

    /// <summary>
    /// Messages associated with the exception
    /// </summary>
    private List<String> Messages ;

    /// <summary>
    /// When the exception occurred
    /// </summary>
    private Date OccurredAt ;

    /// <summary>
    /// A stack trace related to the exception
    /// </summary>
    private List<StackTraceElement> StackTrace ;

    public TMessage getFailedMessage() {
		return FailedMessage;
	}

	public void setFailedMessage(TMessage failedMessage) {
		FailedMessage = failedMessage;
	}

	public String getFaultType() {
		return FaultType;
	}

	public void setFaultType(String faultType) {
		FaultType = faultType;
	}

	public List<String> getMessages() {
		return Messages;
	}

	public void setMessages(List<String> messages) {
		Messages = messages;
	}

	public Date getOccurredAt() {
		return OccurredAt;
	}

	public void setOccurredAt(Date occurredAt) {
		OccurredAt = occurredAt;
	}

	public List<StackTraceElement> getStackTrace() {
		return StackTrace;
	}

	public void setStackTrace(List<StackTraceElement> stackTrace) {
		StackTrace = stackTrace;
	}

	static List<StackTraceElement> GetStackTrace(Exception ex)
    {
		List<StackTraceElement> result;// = new LinkedList<StackTraceElement>();
		StackTraceElement[] exStack = ex.getStackTrace();
		result = (exStack == null || exStack.length ==0 ? new LinkedList<StackTraceElement>() : 
			Arrays.asList(exStack));
//
//        Throwable innerException = ex.getCause();
//        while (innerException != null)
//        {
//        	StackTraceElement[] stackTrace = "InnerException Stack Trace: " + innerException.getStackTrace();
//            result.Add(stackTrace);
//
//            innerException = innerException.InnerException;
//        }

        return result;
    }

    static List<String> GetExceptionMessages(Exception ex)
    {
        List<String> result = new ArrayList<String>();
        result.add(ex.getMessage());

        Throwable innerException = ex.getCause();
        while (innerException != null)
        {
            result.add(innerException.getMessage());

            innerException = innerException.getCause();
        }

        return result;
    }
}

    