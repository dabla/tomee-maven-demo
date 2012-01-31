package be.dabla.service.util;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.handler.MessageContext;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class MessageContextAddressingHelper {
	static final Logger logger = Logger.getLogger(MessageContextAddressingHelper.class.getName());
	
	final DocumentBuilder builder;
	final String messageId;
	final String to;
	final String replyTo;
	final Map<String,String> parameters;
	
	public MessageContextAddressingHelper(final MessageContext messageContext) throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		this.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
		logger.log(Level.FINE, "messageContext: {0}", messageContext);
		
		if (messageContext.containsKey("com.sun.xml.ws.api.addressing.messageId")) {
			messageId = (String)messageContext.get("com.sun.xml.ws.api.addressing.messageId");
			to = getAddress(messageContext.get("com.sun.xml.ws.api.addressing.to"));
			replyTo = getAddress(messageContext.get("com.sun.xml.ws.addressing.WsaPropertyBag.ReplyToFromRequest"));
			parameters = getReferenceParameters(messageContext.get("com.sun.xml.ws.addressing.WsaPropertyBag.ReplyToFromRequest"));
		}
		else {
			if (messageContext.containsKey("javax.xml.ws.addressing.context.inbound")) {
				messageId = getAttributedURITypeValue(messageContext.get("javax.xml.ws.addressing.context.inbound"), "getMessageID");
				to = getAttributedURITypeValue(messageContext.get("javax.xml.ws.addressing.context.inbound"), "getTo");
				replyTo = getEndpointReferenceTypeValue(messageContext.get("javax.xml.ws.addressing.context.inbound"), "getReplyTo");
			}
			else {
				messageId = null;
				to = null;
				replyTo = null;
			}
			
			parameters = null;
		}	
	}

	public String getMessageId() {
		return messageId;
	}
	
	public String getTo() {
		return to;
	}
	
	public String getReplyTo() {
		return replyTo;
	}
	
	public Map<String,String> getReferenceParameters() {
		return parameters;
	}
	
	protected String getAddress(final Object value) throws SAXException, IOException {
		final Node node =  findNode(value, "Address");
			
		if (node != null) {
			return node.getTextContent();
		}
		
		return null;
	}
	
	
	protected Map<String,String> getReferenceParameters(final Object value) throws SAXException, IOException {
		final Node node = findNode(value, "ReferenceParameters");
		
		if (node != null) {
			final Map<String,String> parameters = new HashMap<String,String>();
			final NodeList children = node.getChildNodes();
			
			for (int i = 0; i < children.getLength(); i++) {
				final int index = children.item(i).getNodeName().indexOf(':');

				parameters.put((index > -1 ? children.item(i).getNodeName().substring(index + 1) : children.item(i).getNodeName()), children.item(i).getTextContent());
			}
			
			return parameters;
		}
		
		return null;
	}
	
	protected Node findNode(final Object value, final String name) throws SAXException, IOException {
		if (value != null) {
			final NodeList list = toDocument(value.toString()).getChildNodes();
			
			for (int i = 0; i < list.getLength(); i++) {
				final NodeList children = list.item(i).getChildNodes();
				
				for (int j = 0; j < children.getLength(); j++) {
					if (children.item(j).getNodeName().endsWith(name)) {
						 return children.item(j);
					}
				}
			}
		}
		
		return null;
	}
	
	
	protected String getEndpointReferenceTypeValue(final Object instance, final String name) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		final Object object = instance.getClass().getDeclaredMethod(name, null).invoke(instance, null);
		
		if (object != null) {
			return getAttributedURITypeValue(object, "getAddress");
		}
		
		return null;
	}
	
	protected String getAttributedURITypeValue(final Object instance, final String name) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		final Object object = instance.getClass().getDeclaredMethod(name, null).invoke(instance, null);
		
		if (object != null) {
			return (String)object.getClass().getDeclaredMethod("getValue", null).invoke(object, null);
		}
		
		return null;
	}
	
	protected Document toDocument(final String value) throws SAXException, IOException {
        return builder.parse(new InputSource(new StringReader(value)));
    }
}