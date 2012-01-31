package be.dabla.service;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;

import be.dabla.model.Message;

@Addressing
@WebService(targetNamespace = "urn:service.dabla.be")
public interface MessageService {
	@WebMethod(action = "create")
	@Oneway
	public void create(@WebParam(partName = "payload", name = "Message", targetNamespace = "urn:model.dabla.be")final Message payload);

	@WebMethod(action = "callbackMessage")
    @Oneway
    public void callbackMessage(@WebParam(partName = "payload", name = "Message", targetNamespace = "urn:model.dabla.be")final Message payload);
}