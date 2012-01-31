package be.dabla.model;

import javax.naming.NamingException;

import be.dabla.domain.MessageEJB;
import be.dabla.domain.MessageEJBRemote;
import be.dabla.model.Message;

public class MessageController extends AbstractPaginationController<Message> {
	public MessageController() throws NamingException {
		super(new RepeatPaginator<Message>((MessageEJBRemote)initialContext.lookup(MessageEJB.class.getSimpleName())));
    }
}