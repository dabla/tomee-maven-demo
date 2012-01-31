package be.dabla.domain;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import be.dabla.model.Message;
import be.dabla.service.handler.Address;

@Stateless(name="MessageMDBSender",mappedName="MessageMDBSender")
@Remote(MessageMDBSenderRemote.class)
public class MessageMDBSender implements MessageMDBSenderRemote {
	public static final int TIME_TO_LIVE = 120000; // set expiration time to 2 minutes
	private static final Logger logger = Logger.getLogger(MessageMDBSender.class.getName());
	@Resource(mappedName="jms/tomee-maven-demo-cf")
    ConnectionFactory connectionFactory;
    @Resource(mappedName="jms/tomee-maven-demo-q")
    Queue queue;

	public void sendMessage(final Address<Message> address) throws JMSException {
		logger.log(Level.INFO, "sendMessage: {0}", address);
		
		Connection connection = null;
		Session session = null;
		
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			final MessageProducer producer = session.createProducer(queue);
			producer.setTimeToLive(TIME_TO_LIVE);
			final ObjectMessage message = session.createObjectMessage(address);
			producer.send(message);
			
			logger.log(Level.FINE, "Message {0} send...", message);
		}
		finally {
			try {
				if (session != null) {
					session.close();
				}
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
	}
}