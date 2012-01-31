package org.apache.openejb;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.dbunit.DBUnitFixtureLoader;
import org.junit.After;
import org.junit.Before;

public abstract class OpenEJBTestCase {
	protected static final InitialContext initialContext;
	
	@PersistenceContext(unitName = "tomee-maven-demo-pu")
	protected EntityManager em;
	
	static {
		final Properties properties = new Properties();
		
        try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jndi.properties"));
			initialContext = new InitialContext(properties);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Before
	public void setUp() throws Exception {
        initialContext.bind("inject", this);
        
        new DBUnitFixtureLoader((UserTransaction)initialContext.lookup("java:comp/UserTransaction"), em).load();
    }
	
	@After
	public void tearDown() throws Exception {
        initialContext.unbind("inject");
    }
}