package be.dabla.model;

import static org.junit.Assert.assertNotNull;

import javax.naming.NamingException;

import org.apache.openejb.OpenEJBTestCase;
import org.apache.openejb.api.LocalClient;
import org.junit.Test;

import be.dabla.model.Message;

@LocalClient
public class RequestControllerTest extends OpenEJBTestCase {
	@Test
	public void testPaginator() throws NamingException {
		final MessageController controller = new MessageController();
		final RepeatPaginator<Message> paginator = controller.getPaginator();
		assertNotNull(paginator);
		paginator.prev();
		paginator.next();
	}
}