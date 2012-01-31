package be.dabla.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.ejb.EJB;
import javax.naming.NamingException;

import org.apache.openejb.OpenEJBTestCase;
import org.apache.openejb.api.LocalClient;
import org.junit.Test;

import be.dabla.domain.MessageEJB;
import be.dabla.domain.MessageEJBRemote;
import be.dabla.model.Message;

@LocalClient
public class MessageEJBTest extends OpenEJBTestCase {
	@EJB
	private MessageEJBRemote ejb;

	@Test
	public void testCreate() {
		assertNull(ejb.create(null));
		
		try {
			ejb.create(new Message()); // NOT NULL
			fail("org.eclipse.persistence.exceptions.DatabaseException");
		}
		catch(Exception e) {
			
		}
		
		assertNotNull(ejb.create(new Message("Hello World")));
		assertNotNull(ejb.create(new Message("Hello David")));
	}
	
	@Test
	public void testCount() throws NamingException {
		assertNotNull(ejb.count());
	}
	
	@Test
	public void testFindAll() throws NamingException {
		assertNotNull(initialContext.lookup(MessageEJB.class.getSimpleName()));
		assertNotNull(ejb.findAll(0, 10));
	}
}