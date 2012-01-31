package be.dabla.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.xml.ws.handler.MessageContext;

public class MockMessageContext implements MessageContext {
	private Map<String, Object> map;
	
	public MockMessageContext() {
		map = new HashMap<String,Object>();
		map.put("com.sun.xml.ws.api.addressing.messageId", "uuid:" + UUID.randomUUID());
		map.put("com.sun.xml.ws.api.addressing.to", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><wsa:EndpointReference xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Address xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">http://localhost:7001/bc-core-utils-bc1-sync-context-root/UserService</wsa:Address></wsa:EndpointReference>");
		map.put("com.sun.xml.ws.addressing.WsaPropertyBag.ReplyToFromRequest", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><wsa:EndpointReference xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Address>http://localhost:9999/UserMockService</wsa:Address></wsa:EndpointReference>");
	}
	
	@Override
	public Scope getScope(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setScope(String name, Scope scope) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return map.entrySet();
	}

	@Override
	public Object get(Object key) {
		return map.get(key);
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	public Object put(String key, Object value) {
		return map.put(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		map.putAll(m);
	}

	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<Object> values() {
		return map.values();
	}
}