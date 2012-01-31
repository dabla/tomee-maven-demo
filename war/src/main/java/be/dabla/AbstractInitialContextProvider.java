package be.dabla;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.naming.InitialContext;


public abstract class AbstractInitialContextProvider implements Serializable {
	protected static final InitialContext initialContext;
	
	static {
		final Properties properties = new Properties();
		
	    try {
	    	final InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("env-entry.properties");
	    	
	    	if (input != null) {
	    		properties.load(input);
	    	}
	    	
			initialContext = new InitialContext(properties);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}