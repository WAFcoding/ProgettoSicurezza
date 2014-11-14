/**
 * 
 */
package usersManagement;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class User {
	//chiave privata 
	//chiave pubblica
	//le chiavi pubbliche
	//i percorsi delle cartelle 
	
	private KeyPair keys;
	private HashMap<String, String > publicKeys;//la chiave e' l'user, il valore e' la chiave pubblica, TODO User: va serializzata l'hashmap

	private String dir_def, dir_in, dir_out, name, surname, country, country_code, city, organization, mail, code;
	public User(KeyPair keys, String dir_def, String dir_in, String dir_out, String name, String surname, String country,
					String country_code, String city, String organization, String mail, String code){
		
		this.publicKeys= new HashMap<String, String>();
		setKeys(keys);
		setDir_def(dir_def);
		setDir_in(dir_in);
		setDir_out(dir_out);
		setName(name);
		setSurname(surname);
		setCountry(country);
		setCountry_code(country_code);
		setCity(city);
		setOrganization(organization);
		setMail(mail);
		setCode(code);
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	/**
	 * @return the dir_def
	 */
	public String getDir_def() {
		return dir_def;
	}
	/**
	 * @param dir_def the dir_def to set
	 */
	public void setDir_def(String dir_def) {
		this.dir_def = dir_def;
	}
	/**
	 * @return the dir_in
	 */
	public String getDir_in() {
		return dir_in;
	}
	/**
	 * @param dir_in the dir_in to set
	 */
	public void setDir_in(String dir_in) {
		this.dir_in = dir_in;
	}
	/**
	 * @return the dir_out
	 */
	public String getDir_out() {
		return dir_out;
	}
	/**
	 * @param dir_out the dir_out to set
	 */
	public void setDir_out(String dir_out) {
		this.dir_out = dir_out;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the country_code
	 */
	public String getCountry_code() {
		return country_code;
	}
	/**
	 * @param country_code the country_code to set
	 */
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}
	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	/**
	 * Inserisce l'utente e la sua chiave pubblica, se l'utente e' presente
	 * viene sovrascritto il precedente valore
	 * @param user
	 * @param paramPblKey
	 */
	public void addPublicKey(String user, String paramPblKey){

		this.publicKeys.put(user, paramPblKey);
	}
	
	/**
	 * @return the publicKeys
	 */
	public HashMap<String, String> getPublicKeys() {
		return publicKeys;
	}
	
	public KeyPair getKeys() {
		return keys;
	}
	
	public void setKeys(KeyPair keys) {
		this.keys = keys;
	}
}
