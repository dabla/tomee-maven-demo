package be.dabla.service;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(name = "MessageService", targetNamespace = "urn:service.dabla.be", wsdlLocation="file:/tomee-maven-demo/war/src/main/resources/wsdl/MessageService.wsdl") 
public class MessageServiceClient extends Service {
    public final static URL WSDL_LOCATION = Thread.currentThread().getContextClassLoader().getResource("wsdl/MessageService.wsdl");
    public final static QName SERVICE = new QName("urn:service.dabla.be", "MessageService");
    public final static QName PORT = new QName("urn:service.dabla.be", "MessageServicePort");
    
    public MessageServiceClient(URL wsdlLocation) {
        super(WSDL_LOCATION, SERVICE);
    }

    public MessageServiceClient(URL wsdlLocation, QName serviceName) {
        super(WSDL_LOCATION, serviceName);
    }

    public MessageServiceClient() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns MessageService
     */
    @WebEndpoint
    public MessageService getMessageService() {
        return super.getPort(PORT, MessageService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MessageService
     */
    @WebEndpoint
    public MessageService getMessageService(WebServiceFeature... features) {
        return super.getPort(PORT, MessageService.class, features);
    }
}