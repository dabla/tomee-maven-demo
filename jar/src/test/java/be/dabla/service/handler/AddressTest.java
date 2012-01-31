package be.dabla.service.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import be.dabla.model.Message;
import be.dabla.service.handler.Address;

public class AddressTest {
	@Test
	public void testAddress() {
		final Address<Message> address = MockAddress.mock();
		assertEquals(address.getContent(), MockAddress.MESSAGE);
		assertEquals(address.getMessageId(), MockAddress.MESSAGE_ID);
		assertEquals(address.getTo(), MockAddress.ADDRESS);
		assertEquals(address.getReplyTo(), MockAddress.ADDRESS);
		
		assertFalse(address.equals(null));
		assertFalse(address.equals(new Address(null, null, null, null)));
		assertFalse(address.equals(new Address(MockAddress.MESSAGE, "", "", "")));
		assertTrue(address.equals(address));
		assertTrue(address.equals(MockAddress.mock()));
		
		address.hashCode();
	}
}