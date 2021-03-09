/*
 * package com.sample.database;
 * 
 * import java.io.FileInputStream; import java.io.IOException; import
 * java.io.InputStream; import java.util.Properties;
 * 
 * import org.apache.log4j.Logger; import org.hibernate.Session; import
 * org.hibernate.SessionFactory; import org.hibernate.cfg.Configuration;
 * 
 * import com.sample.constants.Constants;
 * 
 * public enum DataBaseConnection { instance; final static Logger logger =
 * Logger.getLogger(DataBaseConnection.class); private static SessionFactory
 * factory;
 * 
 * public void createSession() {
 * 
 * Properties prop = new Properties(); InputStream input = null;
 * 
 * try {
 * 
 * input = new FileInputStream(Constants.HIBERNATEPATH); prop.load(input);
 * 
 * 
 * Configuration configuration = new Configuration();
 * 
 * configuration.setProperties(prop);
 * configuration.configure("./com/database/hibernate.cfg.xml");
 * 
 * factory = configuration.buildSessionFactory();
 * 
 * 
 * } catch (IOException ex) { ex.printStackTrace(); } finally { if (input !=
 * null) { try { input.close(); } catch (IOException e) { e.printStackTrace(); }
 * } }
 * 
 * 
 * 
 * }
 * 
 * 
 * 
 * 
 * 
 * 
 * public Session getSession() { Session session=null; try{
 * session=factory.openSession(); }catch (Exception ex) {
 * logger.error("Exception Occured: In method getSession " + ex.getMessage()); }
 * 
 * return session; }
 * 
 * 
 * 
 * }
 */