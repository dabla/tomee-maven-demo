package be.dabla.service;

import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;


public class MockSOAPMessageContext extends MockMessageContext implements SOAPMessageContext {
	SOAPMessage message = null;
	
	public MockSOAPMessageContext() throws SOAPException {
		super();
		
		put(MessageContext.MESSAGE_OUTBOUND_PROPERTY, Boolean.valueOf(false));		
		setMessage(MessageFactory.newInstance().createMessage());
	}
	
	@Override
	public Object[] getHeaders(QName header, JAXBContext context, boolean allRoles) {
		return null;
	}

	@Override
	public SOAPMessage getMessage() {
		return message;
	}

	@Override
	public Set<String> getRoles() {
		return null;
	}

	@Override
	public void setMessage(SOAPMessage message) {
		this.message = message;
	}
}