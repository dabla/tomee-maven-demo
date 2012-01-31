package be.dabla.service;

import static org.apache.cxf.ws.addressing.JAXWSAConstants.CLIENT_ADDRESSING_PROPERTIES;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.support.JaxWsEndpointImpl;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.AddressingPropertiesImpl;
import org.apache.cxf.ws.addressing.WSAddressingFeature;
import org.apache.openejb.OpenEJBTestCase;
import org.junit.After;
import org.junit.Before;


public abstract class WebServiceTestCase<T> extends OpenEJBTestCase {
	public abstract T getPort();
	
	/*@Before
	public void setUp() throws Exception {
        super.setUp();
        
        final Client client = ClientProxy.getClient(getPort());
        final Endpoint endpoint = client.getEndpoint();
		endpoint.getInInterceptors().add(new SAAJInInterceptor());
		endpoint.getOutInterceptors().add(new SAAJOutInterceptor());
		
		enableWSAddressing();
    }
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		
		final Endpoint endpoint = ClientProxy.getClient(getPort()).getEndpoint();
		endpoint.getInInterceptors().clear();
		endpoint.getOutInterceptors().clear();
		
		disableWSAddressing();
    }
	
	protected void enableWSAddressing() {
		final Client client = ClientProxy.getClient(getPort());
        final Endpoint endpoint = client.getEndpoint();
		final WSAddressingFeature addressingFeature = new WSAddressingFeature();
		addressingFeature.setAddressingRequired(true);
		final AddressingProperties maps = new AddressingPropertiesImpl();
		maps.setReplyTo(endpoint.getEndpointInfo().getTarget());
		((BindingProvider)getPort()).getRequestContext().put(CLIENT_ADDRESSING_PROPERTIES, maps);
		((JaxWsEndpointImpl)client.getEndpoint()).getFeatures().add(addressingFeature);
	}
	
	protected void disableWSAddressing() {
		final Client client = ClientProxy.getClient(getPort());
		((BindingProvider)getPort()).getRequestContext().clear();
		((JaxWsEndpointImpl)client.getEndpoint()).getFeatures().clear();
	}*/
}