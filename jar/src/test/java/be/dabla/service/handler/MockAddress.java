package be.dabla.service.handler;

import be.dabla.model.Message;
import be.dabla.service.handler.Address;

public class MockAddress {
	public static final Message MESSAGE = new Message("Hello World");
	public static final String MESSAGE_ID = "uuid:cc633354-97d1-4940-a2a3-9b6d18b1fe81";
	public static final String ADDRESS = "http://www.w3.org/2005/08/addressing/anonymous";
	
	public static Address<Message> mock() {
		return new Address<Message>(MESSAGE, MESSAGE_ID, ADDRESS, ADDRESS);
	}
}