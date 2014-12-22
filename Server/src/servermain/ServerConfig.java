package servermain;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ServerConfig {

	private ServerConfig() {
		if(!loadConfigurationFile())
			System.err.println("Failed to load configuration file");
		else { 
			System.out.println("Configuration loaded successfully");
			PropertyConfigurator.configure(p);
		}
	}

	public static Logger logger = Logger.getLogger(ServerConfig.class);
	private static ServerConfig _instance = null;
	private Properties p;
	public static final String HIB_USERNAME = "hibernate.connection.username";
	public static final String HIB_PASSWORD = "hibernate.connection.password";
	public static final String HIB_DB_URL = "hibernate.connection.url";
	public static final String HIB_SHOWSQL = "hibernate.show_sql";
	public static final String KEYSTORE_PATH = "security.keystorepath";
	
	
	public static ServerConfig getInstance() {
		if(_instance == null) {
			_instance = new ServerConfig();
		}
		return _instance;
	}
	
	protected synchronized boolean loadConfigurationFile() {
		File f = new File(System.getProperty("progettoSII.configuration"));
		if(!f.exists())
			return false;
		
		p = new Properties();
		try {
			//System.out.println(f.getAbsolutePath());
			p.load(new FileInputStream(f));
		} catch (IOException e) {
			e.printStackTrace();
			p=null;
			return false;
		}
		
		return true;
	}
	
	public String getProperty(String key) {
		if(p==null || !p.containsKey(key))
			return "";
		return p.getProperty(key);
	}
	
}
