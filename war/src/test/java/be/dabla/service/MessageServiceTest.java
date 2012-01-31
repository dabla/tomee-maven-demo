package be.dabla.service;

import javax.xml.ws.WebServiceRef;

import org.apache.openejb.api.LocalClient;
import org.junit.Test;

import be.dabla.model.Message;

@LocalClient
public class MessageServiceTest extends WebServiceTestCase<MessageService> {
	@WebServiceRef(wsdlLocation = "http://127.0.0.1:4204/MessageServiceImpl?wsdl")
	private MessageService port;
	
	public MessageService getPort() {
		return port;
	}
	
	//http://svn.apache.org/repos/asf/openejb/tags/openejb-3.1.1/examples/simple-webservice/src/main/java/org/superbiz/calculator/CalculatorImpl.java
	@Test
	public void testCreate() throws Exception {
		getPort().create(new Message("Hello David"));
	}
	
	@Test
	public void testCallbackMessage() {
		getPort().callbackMessage(new Message("Hello David"));
	}
}