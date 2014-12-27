package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private static boolean isCreated;
	
	public static void createSession(String dbPath){
		try{
			//TODO HibernateUtil: decriptare il db, aggiungere al costruttore un campo password
			if(dbPath == null) dbPath= ".";
			
			dbPath= dbPath + "/0.db";
			System.out.println("Creo la sessionFactory Hibernate. Il dbPath= " + dbPath);
			Configuration configuration= new Configuration();
			configuration.setProperty("hibernate.connection.url", "jdbc:sqlite:"+dbPath);
			configuration.configure();
			ServiceRegistry serviceRegistry= new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			sessionFactory= configuration.buildSessionFactory(serviceRegistry);
			//probabile non serve
			if(sessionFactory != null )
				setCreated(true);
			else
				setCreated(false);
		}
		catch(Throwable e){
			System.err.println("Unable to create a session " + e.toString());
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory(){
		
		return sessionFactory;
	}

	public static void shutdown(){
		System.out.println("chiuso una sessionFactory Hibernate.");
		getSessionFactory().close();
		setCreated(false);
		//TODO HibernateUtil: criptare il db, passare una password come parametro
	}

	public static boolean isCreated() {
		return isCreated;
	}

	public static void setCreated(boolean isCreated) {
		HibernateUtil.isCreated = isCreated;
	}
	
}
