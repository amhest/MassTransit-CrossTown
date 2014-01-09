package com.masstransitproject.crosstown.context;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import com.masstransitproject.crosstown.IEndpoint;
import com.masstransitproject.crosstown.serialization.IMessageTypeConverter;

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



    /// <summary>
    /// Receive context that allows receiving sinks to 
    /// </summary>
    public interface IReceiveContext<T extends Object> extends
        IConsumeContext<T>
    {
        InputStream getBodyStream();
        Collection<ISent<T>> getSent();

        Collection<IReceived> getReceived();

        UUID getId();

        /// <summary>
        /// True if the transport is transactional and will leave the message on the queue if an exception is thrown
        /// </summary>
        boolean IsTransactional();
        
        /// <summary>
        ///  The original message id that was consumed
        /// </summary>
        String getOriginalMessageId();

        /// <summary>
        /// Set the content type that was indicated by the transport message header
        /// </summary>
        /// <param name="value"></param>
        void SetContentType(String value);

        void SetMessageId(String value);

        void SetInputAddress(URI URI);

        void SetEndpoint(IEndpoint<T> endpoint);

        void SetRequestId(String value);

        void SetConversationId(String value);

        void SetCorrelationId(String value);

        void SetOriginalMessageId(String value);

        void SetSourceAddress(URI URI);

        void SetDestinationAddress(URI URI);

        void SetResponseAddress(URI URI);

        void SetFaultAddress(URI URI);

        void SetNetwork(String value);

        void SetRetryCount(int retryCount);

        void SetExpirationTime(Date value);

        void SetMessageType(String messageType);

        void SetHeader(String key, String value);

        /// <summary>
        /// Sets the context's body stream;
        /// useful for wrapped serializers 
        /// such as encrypting serializers
        /// and for testing.
        /// </summary>
        /// <param name="stream">Stream to replace the previous stream with</param>
        void SetBodyStream(InputStream stream);

        void CopyBodyTo(OutputStream stream);

        void SetMessageTypeConverter(IMessageTypeConverter messageTypeConverter);
//
//        /// <summary>
//        /// Notify that a fault needs to be sent, so that the endpoint can send it when
//        /// appropriate.
//        /// </summary>
//        /// <param name="faultAction"></param>
//        void NotifyFault(FaultSender<T> faultAction);
//
//        void NotifySend(ISendContext<T> context, IEndpointAddress address);
//
//
//        void NotifyPublish(IPublishContext<T> publishContext);
//
//        void NotifyConsume(IConsumeContext<T> consumeContext, String consumerType, String correlationId);
//
//        /// <summary>
//        /// Publish any pending faults for the context
//        /// </summary>
//        void ExecuteFaultActions(Collection<FaultSender> faultActions);
//
//        /// <summary>
//        /// Returns the fault actions that were added to the context dURIng message processing
//        /// </summary>
//        /// <returns></returns>
//        Collection<FaultSender<T>> GetFaultActions();
//        
//      /// <summary>
//      		/// Sets the contextual data based on what was found in the envelope. Used by the inbound
//      		/// transports as the receive context needs to be hydrated from the actual data that was 
//      		/// transferred through the transport as payload.
//      		/// </summary>
//      		/// <param name="context">The context to write data to, from the envelope</param>
//      		/// <param name="envelope">The envelope that contains the data to read into the context</param>
////              public static void SetUsingEnvelope(this IReceiveContext context, Envelope envelope);
////              {
////                  context.SetRequestId(envelope.RequestId);
////                  context.SetConversationId(envelope.ConversationId);
////                  context.SetCorrelationId(envelope.CorrelationId);
////                  context.SetSourceAddress(envelope.SourceAddress.ToURIOrNull());
////                  context.SetDestinationAddress(envelope.DestinationAddress.ToURIOrNull());
////                  context.SetResponseAddress(envelope.ResponseAddress.ToURIOrNull());
////                  context.SetFaultAddress(envelope.FaultAddress.ToURIOrNull());
////                  context.SetNetwork(envelope.Network);
////                  context.SetRetryCount(envelope.RetryCount);
////                  if (envelope.ExpirationTime.HasValue)
////                      context.SetExpirationTime(envelope.ExpirationTime.Value);
////
////                  foreach (var header in envelope.Headers)
////                  {
////                      context.SetHeader(header.Key, header.Value);
////                  }
////              }
    }
