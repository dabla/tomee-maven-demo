package be.dabla.mdb;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.soap.SOAPFaultException;

import be.dabla.domain.MessageEJBRemote;
import be.dabla.model.Message;
import be.dabla.service.MessageService;
import be.dabla.service.MessageServiceClient;
import be.dabla.service.handler.Address;
import be.dabla.service.handler.WSAddressingClientHandler;

// http://download.oracle.com/javaee/6/tutorial/doc/gipvi.html (jee6/ejb3.1 would make it a lot easier)
@MessageDriven(mappedName = "jms/tomee-maven-demo-q", activationConfig = {
		@ActivationConfigProperty(propertyName="destinationType",propertyValue="javax.jms.Queue"),
		@ActivationConfigProperty(propertyName="destination",propertyValue="jms/tomee-maven-demo-q")
})
public class MessageMDB implements MessageListener {
	private static final Logger logger = Logger.getLogger(MessageMDB.class.getName());
	final WSAddressingClientHandler handler;
	final MessageService port;
	
	public MessageMDB() {
		handler = new WSAddressingClientHandler();
		final List<Handler> handlerChain = new ArrayList<Handler>();
        handlerChain.add(handler);
        
		port = new MessageServiceClient().getMessageService();
        ((BindingProvider)port).getBinding().setHandlerChain(handlerChain);
	}
	
	@EJB
	MessageEJBRemote ejb;

	@Override
	public void onMessage(final javax.jms.Message msg) {
		logger.log(Level.INFO, "onMessage: {0}", msg);
		
		try {
			final Address<Message> address = (Address<Message>)((ObjectMessage)msg).getObject();
			
			logger.log(Level.FINE, "address: {0}", address);
			
			if (address != null) {
				final Message message = ejb.create(address.getContent());
				
				logger.log(Level.FINE, "message: {0}", message);
				
				if (message != null) {
					handler.setAddress(address);
					((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address.getReplyTo());    
				    port.callbackMessage(message);
	    	        
	    	        logger.log(Level.INFO, "Message with id {0} acknowledged...", message.getId());
				}
				else {
					throw new Exception("Address doesn't contain a message!");
				}
			}
		} 
		catch(SOAPFaultException e) {
			logger.warning(e.toString());
		}
		catch(Exception e) {
			logger.severe(e.toString());
			throw new RuntimeException(e);
		}
	}
}