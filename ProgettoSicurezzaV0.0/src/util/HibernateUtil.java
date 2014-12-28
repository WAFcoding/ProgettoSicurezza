package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import util.CryptoUtility.CRYPTO_ALGO;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private static boolean isCreated;
	private static String dbPath;
	
	public static void createSession(){
		try{
			
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
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isCreated() {
		return isCreated;
	}

	public static void setCreated(boolean isCreated) {
		HibernateUtil.isCreated = isCreated;
	}
	
	public static void encryptDb(String password){

		byte[] data;
		byte[] enc_data;
		try {
			FileInputStream fin= new FileInputStream(dbPath); 
			InputStream in= fin;
			data= IOUtils.toByteArray(in);
			enc_data= CryptoUtility.encrypt(CRYPTO_ALGO.AES, data, password);
			in.close();
			fin.close();
			
			FileOutputStream fout= new FileOutputStream(dbPath);
			OutputStream out= fout;
			out.write(enc_data);
			out.close();
			fout.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void decryptDb(String password){

		byte[] data;
		byte[] dec_data;
		try {
			FileInputStream fin= new FileInputStream(dbPath); 
			InputStream in= fin;
			data= IOUtils.toByteArray(in);
			dec_data= CryptoUtility.decrypt(CRYPTO_ALGO.AES, data, password);
			//TODO fare funzione per criptare che restituisca byte
			in.close();
			fin.close();
			
			FileOutputStream fout= new FileOutputStream(dbPath);
			OutputStream out= fout;
			out.write(dec_data);
			out.close();
			fout.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setDbPath(String p_dbPath){

		if(p_dbPath == null) dbPath= ".";
		else dbPath= p_dbPath;
		
		dbPath= dbPath + "/0.db";
	}
}
