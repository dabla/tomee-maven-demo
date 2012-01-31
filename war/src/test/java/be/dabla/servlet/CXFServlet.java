package be.dabla.servlet;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jws.WebService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.xml.ws.Endpoint;

import org.apache.cxf.BusFactory;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

public class CXFServlet extends CXFNonSpringServlet {
	private static final Logger logger = Logger.getLogger(CXFServlet.class.getName());
	
	private String packageName;
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void init()
	    throws ServletException
	{
	    setPackageName(getServletConfig().getInitParameter("package"));
	}
	
	@Override
    public void loadBus(final ServletConfig servletConfig) throws ServletException {
        super.loadBus(servletConfig);        
        
        BusFactory.setDefaultBus(bus);
        
        try {
        	logger.log(Level.FINE, "packageName: {0}", getPackageName());
        	
        	for (final Class implementation : getClasses(getPackageName())) {
        		final WebService annotation = (WebService)implementation.getAnnotation(WebService.class);
        		
        		logger.log(Level.FINE, "annotation: {0}", annotation);
        		
        		// only accept classes which are annotated with the WebService annotation
        		if (annotation != null) {
	        		logger.log(Level.INFO, "Publishing endpoint ''{0}'' for class ''{1}''.", new Object[]{annotation.serviceName(),implementation.getName()});
	        		
	            	Endpoint.publish("/" + annotation.serviceName(), implementation.newInstance());
        		}
            }
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}
	
	/**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
	 * @throws URISyntaxException 
     */
    private static Class[] getClasses(final String packageName)
            throws ClassNotFoundException, IOException, URISyntaxException {
        final Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packageName.replace('.', '/'));
        final List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            dirs.add(new File(resources.nextElement().toURI()));
        }
        final ArrayList<Class> classes = new ArrayList<Class>();
        for (final File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
    	final List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        final File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + '.' + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}