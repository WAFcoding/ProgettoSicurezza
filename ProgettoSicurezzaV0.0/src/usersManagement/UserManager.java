/**
 * 
 */
package usersManagement;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import util.CryptoUtility;

/**
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class UserManager {

	private ArrayList<User> users;//tutti gli utenti
	private static final SessionFactory factory= createSession();
	
	public UserManager(){
		this.users= new ArrayList<User>();
	} 
	//___________________Gestione DB Hibernate______________________________________________	
	public static SessionFactory createSession(){

		try{
			Configuration configuration= new Configuration();
			configuration.configure();
			ServiceRegistry serviceRegistry= new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			return configuration.buildSessionFactory(serviceRegistry);
		}
		catch(Throwable e){
			System.err.println("Unable to create a session " + e);
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static SessionFactory getSessionFactory(){
		return factory;
	}
	
	public static void closeSession(){
		getSessionFactory().close();
	}
	//___________________________________________________________________________________
	
	//===================LOGIN==================
	public void checkForLogin(){
		
	}
	public void login(){
		System.out.println("stai effettuando il login");
	}
	//===================REGISTRATION===========

	public void registration(String[] registration_value){
		
		String name= registration_value[0];
		String surname= registration_value[1];
		String password= registration_value[2];
		String confirm_password= registration_value[3];
		String mail= registration_value[4];
		String confirm_mail= registration_value[5];
		String city= registration_value[6];
		String country= registration_value[7];
		String country_code= registration_value[8];
		String organization= registration_value[9];
		String dir_def= registration_value[10];
		String dir_in= registration_value[11];
		String dir_put= registration_value[12];
		
		KeyPair key;
		String public_key= "";
		String private_key= "";
		try {
			key = getKey();
			public_key= key.getPublic().toString();
			private_key= key.getPrivate().toString();
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			
			e.printStackTrace();
		}
		//TODO UserManager: eseguire la query, dal db prelevare l'utente appena inserito, creare user, aggiungerlo alla lista
		
		
		System.out.println("Stai registrando l'utente: " + name + ", "+ surname + ", con chiave pubblica " + public_key + " e chiave private " + private_key);
		
	}
	
	private KeyPair getKey() throws NoSuchAlgorithmException, NoSuchProviderException{
		return CryptoUtility.genKeyPairRSA();
	}
	/**
	 * controlla che la password e la confirm_password siano identiche
	 * @param password
	 * @param confirm_password
	 * @return
	 */
	public boolean confirmPassword(String password, String confirm_password){
		
		return password.equals(confirm_password);
	}
	/**
	 * controlla che la password rispetti la struttura di almeno 8 caratteri
	 * con lettere maiuscole e numeri
	 * @param password
	 * @return
	 */
	public boolean checkPassword(String password){
		
		if(password.length() < 8){
			return false;
		}
		
		if(!containsNumbers(password)){
			return false;
		}
		
		if(!containsUppercase(password)){
			return false;
		}
		
		return true;
	}
	
	public boolean containsNumbers(String password){
		
		String[] numbers= {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		
		for(String n : numbers){
			if(password.contains(n)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean containsUppercase(String password){
		
		char[] c_pwd= password.toCharArray();
		for(char c : c_pwd){
			if(Character.isUpperCase(c)){
				return true;
			}
		}
		
		return false;
	}
	/**
	 * controlla che la mail e confirm_mail siano identiche
	 * @param mail
	 * @param confirm_mail
	 * @return
	 */
	public boolean confirmMail(String mail, String confirm_mail){
		
		return mail.equals(confirm_mail);
	}

	//===================CODE==============================================
	/**
	 * Genera il code, un codice a 5 cifre univoco utilizzato per
	 * l'autenticazione forte e come nome del keystore
	 * @param user
	 * @param password
	 * @return il code
	 */
	public String generateCode(String user, String password){
		
		return "00000";
	}
	/**
	 * Controlla che il code generato non sia gia utilizzato da altri utenti
	 * @param paramCode
	 * @return
	 */
	public boolean codeIsPresent(String paramCode){
		//TODO eseguire una query che preleva tutti i code nella tabella degli utenti
		return true;
	}
	
	public static void main(String[] args){
		System.out.println("prova hibernate");
		Session session= UserManager.getSessionFactory().openSession();
		Transaction tx= null;
		try{
			tx= session.beginTransaction();
			
			User user= new User();
			user.setID(0);
			user.setName("giov");
			user.setSurname("ros");
			user.setMail("yeah@tu.it");
			user.setCode("00002");
			user.setCity("rome");
			user.setCountry("Italy");
			user.setCountry_code("IT");
			user.setOrganization("yours");
			user.setDir_def("uyt");
			user.setDir_in("uyt");
			user.setDir_out("oiu");
			user.setPublicKey("ooo");
			user.setPrivateKey("iii");
			session.save(user);
			
			UserPublicKeyKnown user_pbck= new UserPublicKeyKnown();
			user_pbck.setID_user("3");
			user_pbck.setPbc_key("yuuuuuuuuuuuuuuu");
			user_pbck.setUser(user);
			user.getPublicKeyKnown().add(user_pbck);
			session.save(user_pbck);
			
			tx.commit();
			
			System.out.println("ok");
		}
		catch(HibernateException e){
			if(tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
		}
	}
}
