package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DbHibernateUtils {
	
	public static Session getTrustedUserDbSession() {
		Configuration configuration = new Configuration();
	    configuration.configure("trustedUserDbH.cfg.xml");
	    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
	            configuration.getProperties()).build();
	    SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    return sessionFactory.getCurrentSession();
	}
	
	public static Session getKeyLevelDbSession() {
		Configuration configuration = new Configuration();
	    configuration.configure("keyLevelDbH.cfg.xml");
	    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
	            configuration.getProperties()).build();
	    SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    return sessionFactory.getCurrentSession();
	}

}
