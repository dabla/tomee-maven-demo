package be.dabla.service.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import be.dabla.service.MockMessageContext;
import be.dabla.service.util.MessageContextAddressingHelper;

public class MessageContextAddressingHelperTest {
	@Test
	public void testHelper() throws Exception {
		final MessageContextAddressingHelper helper = new MessageContextAddressingHelper(new MockMessageContext());
		
		assertNotNull(helper.getMessageId());
		assertNotNull(helper.getReplyTo());
		assertNotNull(helper.getTo());
	}
}