package be.dabla.domain;

import javax.ejb.Local;
import javax.jms.JMSException;

import be.dabla.model.Message;
import be.dabla.service.handler.Address;

@Local
public interface MessageMDBSenderLocal {
	public void sendMessage(final Address<Message> address) throws JMSException;
}