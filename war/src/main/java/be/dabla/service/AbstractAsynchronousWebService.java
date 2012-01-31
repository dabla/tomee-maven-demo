package be.dabla.service;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.ws.WebServiceContext;

import be.dabla.AbstractInitialContextProvider;
import be.dabla.service.handler.Address;
import be.dabla.service.util.MessageContextAddressingHelper;

public abstract class AbstractAsynchronousWebService<T> extends AbstractInitialContextProvider {
	static final Logger logger = Logger.getLogger(AbstractAsynchronousWebService.class.getName());
	
	protected abstract WebServiceContext getWebServiceContext();
	
	protected Address getAddress(final T content) {
		logger.log(Level.FINE, "content: ", content);
		
		if (content != null) {
			try {
				final MessageContextAddressingHelper helper = new MessageContextAddressingHelper(getWebServiceContext().getMessageContext());
				final String messageId = helper.getMessageId();
				final String replyTo = helper.getReplyTo();
				final String to = helper.getTo();
				final Map<String,String> parameters = helper.getReferenceParameters();
				
				logger.log(Level.FINE, "messageId: {0}", messageId);
				logger.log(Level.FINE, "replyTo: {0}", replyTo);
				logger.log(Level.FINE, "to: {0}", to);
				logger.log(Level.FINE, "parameters: {0}", parameters);
				
				if ((messageId != null) && (replyTo != null) && (to != null)) {
					
					return new Address<T>(content, messageId, to, replyTo, parameters);
				}
			}
			catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return null;
	}
}