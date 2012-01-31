package be.dabla.domain;

import javax.ejb.Local;

import be.dabla.model.Message;

@Local
public interface MessageEJBLocal extends EJBLocal<Message> {
	public Message findById(final Long id);
	public Message create(final Message entity);
	public void delete(final Message Message);
	public Message update(final Message entity);
}