package org.dbunit;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import java.security.CodeSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.eclipse.persistence.sessions.DatabaseLogin;
import org.eclipse.persistence.sessions.server.ServerSession;
import org.xml.sax.InputSource;

public class DBUnitFixtureLoader {

    private static final Logger logger = Logger.getLogger(DBUnitFixtureLoader.class.getName());
    private UserTransaction tx;
    private EntityManager em;

    public DBUnitFixtureLoader(final UserTransaction tx, final EntityManager em) {
        this.tx = tx;
        this.em = em;
    }

    public void load() throws Exception {
        if (tx != null) {
            try {
                tx.begin(); //start a new transaction

                // http://www.antoniogoncalves.org/xwiki/bin/view/Article/TestingJPA
                // http://forums.oracle.com/forums/thread.jspa?threadID=525646
                final ServerSession session = ((EntityManagerImpl) (em.getDelegate())).getServerSession();
                final DatabaseLogin login = session.getLogin();
                final Connection connection = (Connection) login.connectToDatasource(session.getAccessor(), session);

                try {
                    final Properties properties = new Properties();
                    properties.load(getClass().getClassLoader().getResourceAsStream("fixtures"));

                    if (!properties.isEmpty()){
                        for (final Object resource : properties.keySet()) {
                            final String name = "fixtures/" + resource;
                            logger.log(Level.INFO, "Adding fixture ''{0}''", name);

                            append(connection, new FlatXmlDataSet(getClass().getClassLoader().getResourceAsStream(name)));
                        }
                    }
                }
                catch(NullPointerException e) {
                    final CodeSource src = getClass().getProtectionDomain().getCodeSource();

                    System.out.println("CodeSource: " + src);

                    if (src != null) {
                        ZipInputStream zip = null;
                        ZipEntry entry = null;

                        try {
                            zip = new ZipInputStream(src.getLocation().openStream());

                            //
                            // Read each entry from the ZipInputStream until no more entry found
                            // indicated by a null return value of the getNextEntry() method.
                            //
                            while ((entry = zip.getNextEntry()) != null) {
                                if (!entry.isDirectory() && (entry.getName().indexOf("fixture") > -1) && entry.getName().endsWith(".xml")) {
                                    logger.log(Level.INFO, "Adding fixture ''{0}''", entry.getName());
                                    int size;
                                    final byte[] buffer = new byte[2048];
                                    final ByteArrayOutputStream bos = new ByteArrayOutputStream();

                                    while ((size = zip.read(buffer, 0, buffer.length)) != -1) {
                                        bos.write(buffer, 0, size);
                                    }
                                    
                                    bos.flush();
                                    bos.close();
                                    
                                    append(connection, new FlatXmlDataSet(new StringReader(new String(bos.toByteArray()).trim())));
                                }
                            }
                        } finally {
                            if (zip != null) {
                                zip.close();
                            }
                        }
                    }
                }
            } finally {
                tx.commit();
            }
        }
    }
    
    private void append(final Connection connection, final FlatXmlDataSet xmlDataSet) throws DatabaseUnitException, SQLException {
    	final DatabaseConnection databaseConnection = new DatabaseConnection(connection);
    	final ReplacementDataSet dataSet = new ReplacementDataSet(xmlDataSet);
        dataSet.addReplacementObject("NULL", null);

        DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataSet);
    }
}
