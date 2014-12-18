package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	
	public static void createSession(String dbPath){
		try{
			Configuration configuration= new Configuration();
			configuration.setProperty("hibernate.connection.url", "jdbc:sqlite:"+dbPath);
			configuration.configure();
			ServiceRegistry serviceRegistry= new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			sessionFactory= configuration.buildSessionFactory(serviceRegistry);
		}
		catch(Throwable e){
			System.err.println("Unable to create a session " + e.toString());
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown(){
		getSessionFactory().close();
	}
}
