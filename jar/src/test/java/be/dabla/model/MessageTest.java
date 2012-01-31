package be.dabla.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import be.dabla.model.Message;

public class MessageTest {
	@Test
	public void testEquals() throws Exception {
		final Message request = new Message();
		
		request.hashCode();
		assertFalse(request.equals(null));
		assertTrue(request.equals(request));
		assertTrue(request.equals(new Message()));
		
		request.setDescription("");
		assertNotNull(request.getDescription());
	}
}