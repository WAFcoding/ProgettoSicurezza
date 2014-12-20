package db;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import servermain.ServerConfig;

public class DbHibernateUtils {
	
	/**
	 * Configura Hibernate usando il file di configurazione fornito.
	 * @param dbConf	Il nome del file di configurazione senza estensione.
	 * @param dbConf	Il nome del DB.
	 * @return	La configurazione pronta.
	 */
	private static Configuration configureHibernate(String dbConf, String dbName) {
		Configuration configuration = new Configuration();
	    configuration.configure(DbHibernateUtils.class.getResource("/resources/" + dbConf + ".xml"));
	    
	    Properties p = new Properties();
	    
	    p.put(ServerConfig.HIB_USERNAME, ServerConfig.getInstance().getProperty(ServerConfig.HIB_USERNAME));
	    p.put(ServerConfig.HIB_SHOWSQL, ServerConfig.getInstance().getProperty(ServerConfig.HIB_SHOWSQL));
	    p.put(ServerConfig.HIB_PASSWORD, ServerConfig.getInstance().getProperty(ServerConfig.HIB_PASSWORD));
	    p.put(ServerConfig.HIB_DB_URL, "jdbc:mysql://" + ServerConfig.getInstance().getProperty(ServerConfig.HIB_DB_URL) + "/" + dbName);
	    configuration.addProperties(p);

	    return configuration;
	}
	
	/**
	 * Genera una sessione per la connessione al DB con Hibernate.
	 * @param conf	La configurazione da utilizzare.
	 * @return	La sessione generata.
	 */
	private static Session generateHibernateSession(Configuration conf) {
	    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
	            conf.getProperties()).build();
	    SessionFactory sessionFactory = conf.buildSessionFactory(serviceRegistry);
	    return sessionFactory.getCurrentSession();
	}
	
	public static Session getTrustedUserDbSession() {
	    return generateHibernateSession(configureHibernate("trustedUserDbH.cfg", "trustedUsersDb"));
	}
	
	public static Session getKeyLevelDbSession() {
		return generateHibernateSession(configureHibernate("keyLevelDbH.cfg", "keyLevelDb"));
	}

}
