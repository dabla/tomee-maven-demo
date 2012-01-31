package be.dabla.service.impl;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.naming.NamingException;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.Addressing;

import be.dabla.domain.MessageMDBSender;
import be.dabla.domain.MessageMDBSenderRemote;
import be.dabla.model.Message;
import be.dabla.service.AbstractAsynchronousWebService;
import be.dabla.service.MessageService;

@Stateless
@Addressing
@WebService(
		serviceName = "MessageService",
		portName = "MessageServicePort",
		targetNamespace = "urn:service.dabla.be",
		endpointInterface = "be.dabla.service.MessageService")
// http://biemond.blogspot.com/2011/02/building-asynchronous-web-service-with.html
public class MessageServiceImpl extends AbstractAsynchronousWebService<Message> implements MessageService {
	private static final Logger logger = Logger.getLogger(MessageServiceImpl.class.getName());

	@Resource
    WebServiceContext context;
	private final MessageMDBSenderRemote ejb;
	
	public MessageServiceImpl() throws NamingException {
		ejb = (MessageMDBSenderRemote)initialContext.lookup(MessageMDBSender.class.getSimpleName());
	}
	
	@Override
	public void create(final Message payload) {
		try {
			ejb.sendMessage(getAddress(payload));
		}
		catch(Exception e) {
			logger.severe(e.getMessage());
		}
	}
	
	protected WebServiceContext getWebServiceContext() {
		return context;
	}

	@Override
	public void callbackMessage(final Message payload) {
	}
}