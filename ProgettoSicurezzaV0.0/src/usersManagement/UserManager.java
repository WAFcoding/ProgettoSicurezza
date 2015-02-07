/**
 * 
 */
package usersManagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFileChooser;

import layout.LayoutControl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import connection.ClientConfig;
import connection.ConnectionFactory;
import connection.RegistrationClient;
import entities.Settings;
import util.CryptoUtility;
import util.HibernateUtil;
import util.KeyTool;
import util.PDFUtil;
import util.CryptoUtility.HASH_ALGO;

/**
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class UserManager {

	//private static final SessionFactory factory= createSession();
	private LayoutControl control;
	private User actualUser;
	private final static String keystore_pwd= "progettoSII";
	private final static String url= "localhost";//TODO UserManager: inserire il url nei settings
	
	public UserManager(LayoutControl p_control){
		this.control= p_control;
	} 	
	//===================LOGIN==================
	public boolean checkForLogin(String[] arg){
		String name= arg[0];
		String surname= arg[1];
		String password= arg[2];
		String code= arg[3];
		
		if(name.equals("First Name") || name.equals("") ||
				surname.equals("Second Name") || surname.equals("") ||
				password.equals("Password") || password.equals("") ||
				code.equals("Code") || code.equals("") ){
			control.setErrorLayout("Some fields are incorrect.", "HOME");
			return false;
		}
		
		return true;
	}
	
	public boolean login(String[] arg){
		boolean toReturn= false;
		String name= arg[0];
		String surname= arg[1];
		String password= arg[2];
		String code= arg[3];
		for(String t : arg){

			System.out.print(t + " ");
		}
		System.out.println("");
		if(checkForLogin(arg)){
			
			String dbPath= "";
			
			String hash_password= "";
			String hash_code= "";
			try {
				hash_password = CryptoUtility.hash(HASH_ALGO.SHA1, password);
				hash_code= CryptoUtility.hash(HASH_ALGO.SHA1, code);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			if(control.IsSettingsPathNull()){
				JFileChooser chooser= new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showOpenDialog(null);
				dbPath= chooser.getSelectedFile().getAbsolutePath();
				control.setActualDbPath(dbPath);
			}
			else{
				control.setActualSettings(control.getSettingsByCode(hash_code));
				dbPath= control.getActualDbPath();
			}

			if(!HibernateUtil.isCreated()){
				HibernateUtil.setDbPath(dbPath);
				HibernateUtil.decryptDb(hash_password);
				HibernateUtil.createSession();
			}
			
			Session session= HibernateUtil.getSessionFactory().openSession();
			Transaction tx= null;
			try {
				tx= session.beginTransaction();
				String hql= "from User where name = :p_name and surname = :p_surname";
				Query query= session.createQuery(hql);
				query.setParameter("p_name", name);
				query.setParameter("p_surname", surname);
				List<User> result= query.list();
				tx.commit();
				
				if(result.isEmpty()){
					control.setErrorLayout("user doesn't exist", "HOME");
				}
				else {		
					//System.out.println("user exists");
					for(User u : result){
						System.out.println("check user code " + u.getCode() + " equal insert code " + hash_code );
						if(u.getCode().equals(hash_code)){
							//controllo password
							System.out.println("check user pwd " + u.getPassword() + " equal insert pwd " + hash_password );
							if(u.getPassword().equals(hash_password)){
								System.out.println(name + " " + surname + " is logging in.");
								control.setActualSettings(u.getDir_def(), u.getDir_in(), u.getDir_out(), u.getDir_def(), url);
								actualUser= u;
								toReturn= true;
							}
							else
								System.out.println("password sbagliata.");
							
							break;
						}
						else
							System.out.println("codice sbagliato");
					}
				}
			} catch (Exception e) {
				if(tx != null) tx.rollback();
				e.printStackTrace();
			}
			finally{
				session.close();
				HibernateUtil.shutdown();
				HibernateUtil.encryptDb(hash_password);
			}
		}
		
		return toReturn;
	}
	//===================REGISTRATION==========================
	public void registration(String[] registration_value){
		
		String name= registration_value[0];
		String surname= registration_value[1];
		String password= registration_value[2];
		//String confirm_password= registration_value[3];
		String mail= registration_value[4];
		//String confirm_mail= registration_value[5];
		String city= registration_value[6];
		String country= registration_value[7];
		String country_code= registration_value[8];
		String organization= registration_value[9];
		String dir_def= registration_value[10];
		String dir_in= registration_value[11];
		String dir_out= registration_value[12];
		
		//genero la coppia di chiavi
		KeyPair key;
		String public_key= "";
		String private_key= "";
		String secid= "";
		try {
			key = getKey();
			public_key= CryptoUtility.toBase64(key.getPublic().getEncoded()).replace("\n", "");
			private_key= CryptoUtility.toBase64(key.getPrivate().getEncoded()).replace("\n", "");
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			
			e.printStackTrace();
		}
		
		//hash della password
		String hash_password= "";
		try {
			hash_password = CryptoUtility.hash(HASH_ALGO.SHA1, password);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//TODO UserManager: cambiare il nome del DB?
		if(!HibernateUtil.isCreated()){
			HibernateUtil.setDbPath(dir_def);
			HibernateUtil.createSession();
		}
		
		Session session= HibernateUtil.getSessionFactory().openSession();
		//genero il codice
		String code= generateCode(session, public_key);
		Transaction tx= null;
		try{
			//hash del code per autenticazione forte
			String hash_code= CryptoUtility.hash(HASH_ALGO.SHA1, code);
			
			tx= session.beginTransaction();
			
			User user= new User();
			user.setName(name);
			user.setSurname(surname);
			user.setMail(mail);
			user.setCode(hash_code);
			user.setCity(city);
			user.setCountry(country);
			user.setCountry_code(country_code);
			user.setOrganization(organization);
			user.setDir_def(dir_def);
			user.setDir_in(dir_in);
			user.setDir_out(dir_out);
			user.setPublicKey(public_key);
			user.setPrivateKey(private_key);
			user.setPassword(hash_password);
			user.setTrustLevel(-1);

			try(RegistrationClient reg = ConnectionFactory.getRegistrationServerConnection(url, keystore_pwd)) {
				secid = reg.submitRegistration(user);
				
				System.out.println("secureIdentifier:" + secid);
			} catch(Exception e) {
				//chiudera' automaticamente gli stream in caso di eccezione perchè RegistrationClient implementa "Closeable"
				e.printStackTrace();
			}
			user.setSecureId(secid);
			
			session.save(user);
			tx.commit();
			
			control.setActualUserCode(hash_code);
			control.setActualSettings(dir_def, dir_in, dir_out, dir_def, url);
			control.addSettings();
			control.saveSettings();
			/*
			//creo il keystore
			KeyStore ks = KeyTool.loadKeystore(ClientConfig.getInstance().getProperty(ClientConfig.KEYSTORE_PATH), keystore_pwd);
			KeyTool.addNewPrivateKey(ks, privateKey, cert, "giorgio_1", password.toCharArray());

			KeyTool.storeKeystore(ks, ClientConfig.getInstance().getProperty(ClientConfig.KEYSTORE_PATH), password);
			*/
			
			//TODO UserManager: encode del file serializzato
			
			createPdfResume(name, surname, password, code, mail, city, country, country_code, organization, 
							dir_def, dir_out, dir_in, public_key, private_key, secid);
			
			String registration_summary= "E' stato creato un pdf contenente i dati inseriti in " + dir_def + "/" + code + ".pdf";
			
			//stampa del contenuto del PDF a schermo
			control.setErrorLayout("Registration summary:\n" + registration_summary, "HOME");

			System.out.println("Stai registrando l'utente: " + registration_summary);
			
			//TODO UserMAnager: questo si fa quando si prelevano le informazioni di un utente per
			//criptare un documento
			/*UserPublicKeyKnown user_pbck= new UserPublicKeyKnown();
			user_pbck.setID_user("3");
			user_pbck.setPbc_key("yuuuuuuuuuuuuuuu");
			user_pbck.setUser(user);
			user.getPublicKeyKnown().add(user_pbck);
			session.save(user_pbck);
			*/
		}
		catch(Exception e){
			if(tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
			HibernateUtil.shutdown();
			HibernateUtil.encryptDb(hash_password);
		}		
		
		
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
	public static String generateCode(Session session, String pbc_key){

		Random rn= new Random();
		int start= rn.nextInt(200);//non dovrebbero esserci problemi, la chiave è a 255 quindi sto nel range
		String code= pbc_key.substring(start, start + 5);

		boolean isAlreadyUsed= true;
		while(isAlreadyUsed){
			if(codeIsPresent(code, session)){
				start= rn.nextInt(200);//non dovrebbero esserci problemi, la chiave è a 255 quindi sto nel range
				code= pbc_key.substring(start, start + 5);
			}
			else
				isAlreadyUsed= false;
		}

		return code;
	}
	/**
	 * Controlla che il code generato non sia gia utilizzato da altri utenti
	 * @param paramCode
	 * @return
	 */
	public static boolean codeIsPresent(String paramCode, Session session){

		String hql= "SELECT U.code FROM User U";
		Query query= session.createQuery(hql);
		List<String> result= query.list();
		try {
			paramCode= CryptoUtility.hash(HASH_ALGO.SHA1, paramCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.contains(paramCode);
	}

	public User getActualUser() {
		if(actualUser == null)
			return null;
		
		return actualUser;
	}

	public void setActualUser(User actualUser) {
		this.actualUser = actualUser;
	}
	
	public void printActualUser(){
		System.out.println(actualUser.getName() + " " + actualUser.getSurname() + "\n" +
							actualUser.getDir_def() + " " + actualUser.getDir_in() + " " + actualUser.getDir_out());
	}
	
	public void createPdfResume(String name, String surname, String password, String code, String mail, String city, 
			String country, String country_code, String organization, String dir_def, String dir_out, 
			String dir_in, String public_key, String private_key, String secid){
		
		try {
			PDFUtil.create(dir_def + "/" + code + ".pdf");
			if(PDFUtil.open()){
				PDFUtil.addCredentials(code, "Credentials", "Registration", 
										"ProgettoSicurezza", "ProgettoSicurezza");
				try {
					PDFUtil.createResumeTable(name, surname, password, code, mail, city, country, country_code, organization, dir_def, dir_out, dir_in, public_key, private_key, secid);
				} catch (IOException e) {
					e.printStackTrace();
				}
				PDFUtil.close();
			}
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
	}
/*
	public static void main(String[] args){
		System.out.println("prova hibernate");
		Session session= UserManager.getSessionFactory().openSession();
		Transaction tx= null;
		try{
			//tx= session.beginTransaction();
			
			/*User user= new User();
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
			
			String code= generateCode(session, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmW8LWgFUT3JjhkV4zulQX0bvMIfuQxTm5O40L2q+mulqvE55OlTGICgDxHDPjGmtICd70f5u+eO+vw3QKC9E1srmVnUW1V/1tw7gOUY5CfeFTIwX8he+4BLPP1+Mj9BvYiJqhceEKrIwkzNWh/0YWUFTnpgeqtdhQZtDx0HUN2QIDAQAB");
			if(codeIsPresent(code, session)){
				System.out.println(code + " is present");
			}
			else
				System.out.println(code + " is not present");
			//tx.commit();
			
			//System.out.println("ok");
		}
		catch(HibernateException e){
			//if(tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
		}
	}*/
}
