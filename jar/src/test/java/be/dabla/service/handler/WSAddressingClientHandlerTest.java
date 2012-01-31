package be.dabla.service.handler;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.xml.soap.SOAPException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.junit.Test;

import be.dabla.service.MockSOAPMessageContext;
import be.dabla.service.handler.WSAddressingClientHandler;

public class WSAddressingClientHandlerTest {
	@Test
	public void testHandler() throws SOAPException {
		final WSAddressingClientHandler handler = new WSAddressingClientHandler();
		assertNull(handler.getHeaders());
		assertNull(handler.getAddress());
		
		final SOAPMessageContext context = new MockSOAPMessageContext();
		handler.handleMessage(context);
		context.put(MessageContext.MESSAGE_OUTBOUND_PROPERTY, Boolean.valueOf(true));
		handler.handleMessage(context);
		handler.setAddress(MockAddress.mock());
		assertNotNull(handler.getAddress());
		handler.handleMessage(context);
		context.getMessage().getSOAPPart().getEnvelope().removeChild(context.getMessage().getSOAPHeader());
		handler.handleMessage(context);
		
		handler.handleFault(context);
		handler.close(context);
	}
}