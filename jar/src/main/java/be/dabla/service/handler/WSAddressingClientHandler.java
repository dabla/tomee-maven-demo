package be.dabla.service.handler;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class WSAddressingClientHandler implements SOAPHandler<SOAPMessageContext> {
	static final Logger logger = Logger.getLogger(WSAddressingClientHandler.class.getName());
	Address address = null;
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	public Set<QName> getHeaders() {
        return null;
    }

	public boolean handleMessage(final SOAPMessageContext context) {
		final Boolean outboundProperty = (Boolean)context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        
		logger.log(Level.FINE, "outboundProperty: {0}", outboundProperty);
        
        if (outboundProperty.booleanValue() && (getAddress() != null)) {
            try {
                SOAPHeader header = context.getMessage().getSOAPHeader();
                
		        if(header == null) {
		            header = context.getMessage().getSOAPPart().getEnvelope().addHeader();
		        }
		        
		        header.addHeaderElement(new QName("http://www.w3.org/2005/08/addressing","RelatesTo")).addTextNode(address.getMessageId());
		        
		        logger.log(Level.FINE, "parameters: {0}", getAddress().getParameters());
		        
		        if (getAddress().getParameters() != null) {
		        	final SOAPHeaderElement element = header.addHeaderElement(new QName("http://www.w3.org/2005/08/addressing","ReferenceParameters"));
		        	final Iterator<Map.Entry<String,String>> i = getAddress().getParameters().entrySet().iterator();
		        	
		        	while(i.hasNext()) {
		        		final Map.Entry<String,String> entry = i.next();
		        		
		        		logger.log(Level.FINE, "entry: {0}", entry);
		        		
		        		element.addChildElement(new QName("http://xmlns.oracle.com/sca/tracking/1.0",entry.getKey())).addTextNode(entry.getValue());
		        	}
		        }
            } catch (Exception e) {
            	logger.warning(e.getMessage());
            }
        }
        
        return true;
    }
    
    public boolean handleFault(final SOAPMessageContext smc) {
        return true;
    }

    public void close(final MessageContext messageContext) {
    	setAddress(null);
    }
}