/**
 * 
 */
package usersManagement;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class UserManager {

	private ArrayList<User> users;//tutti gli utenti
	private HashMap<String, Integer> users_code;//i code associati agli utenti
	
	public UserManager(){
		this.users= new ArrayList<User>();
		this.users_code= new HashMap<String, Integer>();
	}
	
	//===================LOGIN==================
	public void checkForLogin(){
		
	}
	public void login(){
		System.out.println("stai effettuando il login");
	}
	//===================REGISTRATION===========

	public void registration(String[] registration_value){
		
		String firstname= registration_value[0];
		String secondname= registration_value[1];
		String password= registration_value[2];
		String confirm_password= registration_value[3];
		String mail= registration_value[4];
		String confirm_mail= registration_value[5];
		
		System.out.println("Stai registrando l'utente: " + firstname + ", "+ secondname);
		
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
		
		return null;
	}
	/**
	 * Controlla che il code generato non sia gia utilizzato da altri utenti
	 * @param paramCode
	 * @return
	 */
	public boolean codeIsPresent(String paramCode){
		
		return true;
	}
	
	//TODO seguire esempi sul server di giovanni per l'inserimento e la cancellazione dei record
	//dalla tabella
	
}
